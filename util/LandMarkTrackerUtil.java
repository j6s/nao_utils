package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j on 8/24/16.
 */
public class LandMarkTrackerUtil {

    /**
     * Session of the current naoqi application
     */
    private Session session;

    /**
     * Instance of ALTracker that is used internalyl
     */
    private ALTracker tracker;

    /**
     * Distance to the object
     */
    private float distance = .2f;

    /**
     * Tollerance when walking towards an object
     */
    private float tolerance = .03f;


    public LandMarkTrackerUtil(Session session) throws Exception {
        this.session = session;
        this.tracker = new ALTracker(session);
    }

    /**
     * Shorthand for tracking a single landmark. Uses the default size of 9cm
     *
     * @param landmarkId
     * @throws InterruptedException
     * @throws CallError
     */
    public void startTracking(int landmarkId) throws InterruptedException, CallError {
        List<Integer> ids = new ArrayList<>();
        ids.add(landmarkId);
        this.startTracking(ids);
    }

    /**
     * Shorthand for landmark tracking. Uses a default size of 9cm
     *
     * @param landmarkIds
     * @throws CallError
     * @throws InterruptedException
     */
    public void startTracking(List<Integer> landmarkIds) throws CallError, InterruptedException {
        this.startTracking(landmarkIds, .09f);
    }

    /**
     * Starts tracking the given landmark ids with the given sizes
     * Walks towards a landmark until it is within the distance that was
     * configured.
     *
     * @param landmarkIds
     * @param landmarkSize
     * @throws InterruptedException
     * @throws CallError
     */
    public void startTracking(List<Integer> landmarkIds, float landmarkSize) throws InterruptedException, CallError {
        List<Object> mark = new ArrayList<>();
        mark.add(landmarkSize);
        mark.add(landmarkIds);

        List<Float> position = new ArrayList<>();
        position.add(distance);
        position.add(0f);
        position.add(0f);
        position.add(tolerance);
        position.add(tolerance);

        tracker.removeAllTargets();
        tracker.addTarget("LandMark", mark);
        tracker.setMode("Move");
        tracker.setRelativePosition(position);
        tracker.track("LandMark");

    }

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
