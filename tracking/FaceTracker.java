package de.dhbw.wwi13b.shared.tracking;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import de.dhbw.wwi13b.shared.logging.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracking instance that tracks faces
 *
 * @example
 * FaceTracker tracker = new FaceTracker(application.session());
 * tracker.startTracking();
 *
 * // wait until we are 50cm in front of a person
 * tracker.waitUntilCloseEnough(.5f);
 * tracker.stop();
 */
public class FaceTracker extends AbstractTracker<Float> {

    public static String TAG = "FaceTracker";

    public FaceTracker(Session session) throws Exception {
        super(session);
    }

    /**
     * Simple shortcut that sets the width of a face to 15cm
     * which is the average width of a face according to our quick measurements
     *
     * @throws CallError
     * @throws InterruptedException
     */
    public void startTracking() throws CallError, InterruptedException {
        this.startTracking(.15f);
    }

    /**
     * Tracks the face of a person. The width of the face is needed to calculate the
     * distance. Use {@link FaceTracker#startTracking()} for an easier tracking method
     *
     * @param faceSize
     * @throws InterruptedException
     * @throws CallError
     */
    @Override
    public void startTracking(Float faceSize) throws InterruptedException, CallError {
        Log.debug(TAG, "Start tracking faces with size " + faceSize);

        tracker.unregisterAllTargets();
        tracker.registerTarget("Face", faceSize);

        if(!tracker.isSearchEnabled()) {
            tracker.toggleSearch(true);
        }

        tracker.setMode("Move");
        tracker.setRelativePosition(getPositionList());
        tracker.track("Face");
    }
}
