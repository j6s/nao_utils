package de.dhbw.wwi13b.shared.tracking;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALSonar;
import com.aldebaran.qi.helper.proxies.ALTracker;
import de.dhbw.wwi13b.shared.util.SonarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction over ALTracker that allows easy tracking
 */
public abstract class AbstractTracker<T> {

    /**
     * Session of the current naoqi application
     */
    private Session session;

    /**
     * Instance of ALTracker that is used internalyl
     */
    protected ALTracker tracker;

    /**
     * Distance to the object
     * Alternatively use -0.3f
     */
    private float distance = .2f;

    /**
     * Tolerance when walking towards an object
     */
    private float tolerance = .03f;

    public AbstractTracker(Session session) throws Exception {
        this.tracker = new ALTracker(session);
        this.session = session;
    }

    /**
     * Returns the list that is to be used with ALTracker#setRelativePosition
     * @return
     */
    protected List<Float> getPositionList() {
        List<Float> position = new ArrayList<>();
        position.add(-1 * distance);
        position.add(0f);
        position.add(0f);
        position.add(tolerance);
        position.add(tolerance);
        position.add(tolerance);
        return position;
    }

    /**
     * Tracking method that is to be implemented by every concrete tracker
     *
     * @param setting
     * @throws InterruptedException
     * @throws CallError
     */
    public abstract void startTracking(T setting) throws InterruptedException, CallError;


    /**
     * Stops the tracking
     *
     * @throws InterruptedException
     * @throws CallError
     */
    public void stopTracking() throws InterruptedException, CallError {
        tracker.stopTracker();
        tracker.unregisterAllTargets();
        tracker.setEffector("None");
    }

    /**
     * Returns the distance to the object that is being tracked.
     * If no object is currently being tracked Float.MAX_VALUE is being returned.
     * Returning MAX_VALUE is done on purpose to make usage easier:
     * This way a manual null check is not needed.
     *
     * Also returns MAX_VALUE, if an error occured inside of the aldabaran SDK
     *
     * @return
     * @throws InterruptedException
     * @throws CallError
     */
    public float getDistanceToObject() {
        try {
            // 0 = FRAME_TORSO
            List<Float> position = tracker.getTargetPosition(0);
            if (position.size() == 0) {
                return Float.MAX_VALUE;
            }

            // The tracker can not possibly be closer than 10cm
            // This check has to made, because sometimes this method returns wrong / negative
            // values
            if (position.get(0) <= 0.1) {
                return Float.MAX_VALUE;
            }

            System.out.println(position);
            return position.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return Float.MAX_VALUE;
        }
    }

    /**
     * Blocks the current thread until the robot is within the given distance of the target.
     *
     * Throws DistanceToCloseException, if the given distance is less than the distance, that was used
     * to initialize the tracker: In this instance the method would block the current thread indefinitely
     *
     * @see ALTracker#getTargetPosition()
     *
     *
     * @param meters
     * @return
     * @throws InterruptedException
     * @throws CallError
     */
    public void waitUntilCloseEnough(float meters, int timeoutSeconds) throws DistanceToCloseException {

        long endTime = System.currentTimeMillis() + timeoutSeconds * 1000;

        if (meters < this.distance) {
            throw new DistanceToCloseException("The given distance must be at least " + this.distance + " - " + meters + " given");
        }
        while (true) {
            // exit, if we are close enough
            if (getDistanceToObject() < meters) {
                return;
            }

            // exit, if the timeout has been reached
            if (System.currentTimeMillis() > endTime) {
                return;
            }

            // sleep for 50ms then check again. If Thread was interrupted, then
            // we don't need this anymore.
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // Thread was interrupted: we do not need this method anymore
                return;
            }
        }
    }

    // ===== GETTER & SETTER - self explanatory

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getTolerance() {
        return tolerance;
    }

    public void setTolerance(float tolerance) {
        this.tolerance = tolerance;
    }

}
