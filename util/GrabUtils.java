package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;
import de.dhbw.wwi13b.shared.logging.Log;

/**
 * Created by maxdome on 8/4/16.
 */
public class GrabUtils {

    private static String TAG = "GrabUtils";

    /**
     * ALMotion service instance
     */
    protected JointUtil motion;

    public GrabUtils(Session session) throws Exception {
        this.motion = new JointUtil(session);
    }

    /**
     * Grabs a light object with a width of 6.6 cm
     * @throws Exception
     */
    public void takeObject() throws Exception {
        Log.debug(TAG, "takeObject");
        this.liftArms();
        this.openHands();
        Thread.sleep(500);

        this.grabObject();
        Thread.sleep(500);
        this.liftObject();
    }

    /**
     * Sets the angles of the arms to a tight position.
     * The hands are narrow and close to the torso.
     * @throws Exception
     */
    public void holdObjectTight()throws Exception {
        Log.debug(TAG, "holdObjectTight");
        /**
         * Left Arm
         */
        motion.setAngle(Joint.LSHOULDERPITCH, 60);
        motion.setAngle(Joint.LSHOULDERROLL, 13);
        motion.setAngle(Joint.LELBOWYAW, -20);
        motion.setAngle(Joint.LELBOWROLL, -85);
        motion.setAngle(Joint.LWRISTYAW, -20);

        /**
         * Right Arm
         */
        motion.setAngle(Joint.RSHOULDERPITCH, 60);
        motion.setAngle(Joint.RSHOULDERROLL, -13);
        motion.setAngle(Joint.RELBOWYAW, 20);
        motion.setAngle(Joint.RELBOWROLL, 85);
        motion.setAngle(Joint.RWRISTYAW, 20);

    }

    /**
     * The hands are moved to the side and then up.
     * @throws Exception
     */
    public void liftArmsSideWays() throws Exception {
        Log.debug(TAG, "liftArmsSideWays");
        /**
         * Left Arm
         */
        motion.setAngle(Joint.LSHOULDERPITCH, 25);
        motion.setAngle(Joint.LSHOULDERROLL, 60);

        /**
         * Right Arm
         */
        motion.setAngle(Joint.RSHOULDERPITCH, 25);
        motion.setAngle(Joint.RSHOULDERROLL, -60);

    }

    /**
     * Lifts the arms up
     * @throws Exception
     */
    public void liftArms() throws Exception {
        Log.debug(TAG, "liftArms");
        /**
         * Left Arm
         */
        motion.setAngle(Joint.LSHOULDERPITCH, 25);
        motion.setAngle(Joint.LSHOULDERROLL, 25);
        motion.setAngle(Joint.LELBOWYAW, -30);
        motion.setAngle(Joint.LELBOWROLL, -50);
        motion.setAngle(Joint.LWRISTYAW, -60);

        /**
         * Right Arm
         */
        motion.setAngle(Joint.RSHOULDERPITCH, 25);
        motion.setAngle(Joint.RSHOULDERROLL, -25);
        motion.setAngle(Joint.RELBOWYAW, 30);
        motion.setAngle(Joint.RELBOWROLL, 50);
        motion.setAngle(Joint.RWRISTYAW, 60);
        /**
         * openHands
         */
        this.openHands();
    }

    /**
     * Spreads his fingers as wide as possible.
     * Arms won't be moved.
     * @throws Exception
     */
    public void openHands() throws Exception {
        Log.debug(TAG, "openHands");
        /**
        * Openhands
        */
        motion.setAngle(Joint.LHAND, 100);
        motion.setAngle(Joint.RHAND, 100);
    }

    /**
     * Grabs an object with the size of 6.6cms.
     * Can't hold heavy objects.
     * Be careful when grabbing far away from the body, Nao might fall.
     * @throws Exception
     */
    public void grabObject() throws Exception {
        Log.debug(TAG, "grabObject");
        /**
         * Grab
         */
        motion.setAngle(Joint.LSHOULDERROLL, 4);
        motion.setAngle(Joint.RSHOULDERROLL, -4);
    }

    /**
     * Moves the hands away from each other.
     * @throws Exception
     */
    public void dropObject() throws Exception {
        Log.debug(TAG, "dropObject");
        /**
         * dropObject
         */
        motion.setAngle(Joint.LSHOULDERROLL, -10);
        motion.setAngle(Joint.RSHOULDERROLL, 10);
    }

    /**
     * Rotates arms up.
     * Doesn't change the angle/distance between the arms.
     * @throws Exception
     */
    public void liftObject() throws Exception {
        Log.debug(TAG, "liftObject");
        /**
         * lift grabbed Object
         */
        motion.setAngle(Joint.RSHOULDERPITCH, -20);
        motion.setAngle(Joint.LSHOULDERPITCH, -20);
    }

    /**
     * Lowers the arms down.
     * Doesn't change the angle/distance between the arms.
     * @throws Exception
     */
    public void lowerObject() throws Exception {
        Log.debug(TAG, "lowerObject");

        /**
         * lift grabbed Object
         */
        motion.setAngle(Joint.RSHOULDERPITCH, 0);
        motion.setAngle(Joint.LSHOULDERPITCH, 0);
    }
}
