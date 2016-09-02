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

    public void takeObject() throws Exception {
        this.liftArms();
        this.openHands();
        Thread.sleep(500);

        this.grabObject();
        Thread.sleep(500);
        this.liftObject();
    }

    public void holdObjectTight()throws Exception {
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

    public void liftArmsSideWays() throws Exception {
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

    public void liftArms() throws Exception {
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
        motion.setAngle(Joint.LSHOULDERROLL, 4);
        motion.setAngle(Joint.RSHOULDERROLL, -4);
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
