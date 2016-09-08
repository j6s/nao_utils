package de.dhbw.wwi13b.shared.tracking;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j on 9/8/16.
 */
public class LandMarkTracker extends AbstractTracker<Integer> {

    public LandMarkTracker(Session session) throws Exception {
        super(session);
    }

    /**
     * Shorthand for tracking a single landmark. Uses the default size of 9cm
     *
     * @param landmarkId
     * @throws InterruptedException
     * @throws CallError
     */
    @Override
    public void startTracking(Integer landmarkId) throws InterruptedException, CallError {
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
        System.out.println("Start tracking landmark " + landmarkIds);

        tracker.unregisterAllTargets();

        List<Object> mark = new ArrayList<>();
        mark.add(landmarkSize);
        mark.add(landmarkIds);
        tracker.registerTarget("LandMark", mark);

        if(!tracker.isSearchEnabled()) {
            tracker.toggleSearch(true);
        }

        tracker.setMode("Move");
        tracker.setRelativePosition(getPositionList());
        tracker.track("LandMark");
    }
}
