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
     * @return
     * @throws InterruptedException
     * @throws CallError
     */
    public float getDistanceToObject() throws InterruptedException, CallError {
        List<Float> position = tracker.getTargetPosition();
        if (position.size() == 0) {
            return Float.MAX_VALUE;
        }
        return position.get(0);
    }

    /**
     * Blocks the current thread until the robot is within the given distance of the target.
     *
     * Warning: This will permanently block the thread, if the given distance is less than
     * the distance that was used to start the tracker.
     *
     * @param meters
     * @return
     * @throws InterruptedException
     * @throws CallError
     */
    public List<Float> waitUntilCloseEnough(float meters) throws InterruptedException, CallError {
        while (true) {
            if (getDistanceToObject() < meters) {
                return tracker.getTargetPosition();
            }
            Thread.sleep(50);
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
