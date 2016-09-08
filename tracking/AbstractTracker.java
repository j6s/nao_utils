package de.dhbw.wwi13b.shared.tracking;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALTracker;

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
            List<Float> position = tracker.getTargetPosition();
            if (position.size() == 0) {
                return Float.MAX_VALUE;
            }
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
     * Returns the distance List of the tracked object relative to the current position. [x, y, z]
     * If no values are available returns the list [0,0,0]
     *
     * @see ALTracker#getTargetPosition()
     *
     *
     * @param meters
     * @return
     * @throws InterruptedException
     * @throws CallError
     */
    public List<Float> waitUntilCloseEnough(float meters) throws DistanceToCloseException {
        if (meters < this.distance) {
            throw new DistanceToCloseException("The given distance must be at least " + this.distance + " - " + meters + " given");
        }
        while (true) {
            if (getDistanceToObject() < meters) {
                List<Float> position = new ArrayList<>();
                position.add(0f);
                position.add(0f);
                position.add(0f);
                return position;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // Thread was interrupted: we do not need this method anymore
                List<Float> position = new ArrayList<>();
                position.add(0f);
                position.add(0f);
                position.add(0f);
                return position;
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
