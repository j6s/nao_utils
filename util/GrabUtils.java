package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;

/**
 * Created by maxdome on 8/4/16.
 */
public class GrabUtils {

    /**
     * ALMotion service instance
     */
    protected JointUtil motion;

    public GrabUtils(Session session) throws Exception {
        this.motion = new JointUtil(session);
    }

    public void liftArms() throws Exception {
        /**
         * Left Arm
         */
        motion.setAngle(Joint.LSHOULDERPITCH, 0);
        motion.setAngle(Joint.LSHOULDERROLL, 0);
        motion.setAngle(Joint.LELBOWYAW, -90);
        motion.setAngle(Joint.LELBOWROLL, -20);

        /**
         * Right Arm
         */
        motion.setAngle(Joint.RSHOULDERPITCH, 0);
        motion.setAngle(Joint.RSHOULDERROLL, 0);
        motion.setAngle(Joint.RELBOWYAW, 90);
        motion.setAngle(Joint.RELBOWROLL, 20);
    }

    public void openHands() throws Exception {
        /**
        * Openhands
        */
        motion.setAngle(Joint.LHAND, 100);
        motion.setAngle(Joint.RHAND, 100);
    }

    public void grabObject() throws Exception {
        /**
         * Grab
         */
        motion.setAngle(Joint.RSHOULDERROLL, 30);
        motion.setAngle(Joint.LSHOULDERROLL, -30);
    }

    public void dropObject() throws Exception {
        /**
         * dropObject
         */
        motion.setAngle(Joint.RSHOULDERROLL, 0);
        motion.setAngle(Joint.LSHOULDERROLL, 0);
    }

    public void liftObject() throws Exception{
        /**
         * lift grabed Object
         */
        motion.setAngle(Joint.RSHOULDERPITCH, -20);
        motion.setAngle(Joint.LSHOULDERPITCH, -20);
    }

    public void lowerObject() throws Exception{
        /**
         * lift grabed Object
         */
        motion.setAngle(Joint.RSHOULDERPITCH, 0);
        motion.setAngle(Joint.LSHOULDERPITCH, 0);
    }
}
