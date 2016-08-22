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
    protected ALMotion motion;

    public GrabUtils(Session session) throws Exception{
        this.motion = new ALMotion(session);

    }

    public void liftArms() throws Exception {
        /**
         * Left Arm
         */
        motion.setAngles("LShoulderPitch",MathUtil.deg2rad(0f),0.4f);
        motion.setAngles("LShoulderRoll",MathUtil.deg2rad(0f),0.4f);
        motion.setAngles("LElbowYaw",MathUtil.deg2rad(-90f),0.4f);
        motion.setAngles("LElbowRoll",MathUtil.deg2rad(-20f),0.4f);

        /**
         * Right Arm
         */
        motion.setAngles("RShoulderPitch",MathUtil.deg2rad(0f),0.4f);
        motion.setAngles("RShoulderRoll",MathUtil.deg2rad(0f),0.4f);
        motion.setAngles("RElbowYaw",MathUtil.deg2rad(90f),0.4f);
        motion.setAngles("RElbowRoll",MathUtil.deg2rad(20f),0.4f);
    }

    public void openHands() throws Exception {
        /**
        * Openhands
        */
        motion.setAngles("LHand",MathUtil.deg2rad(100f),0.4f);
        motion.setAngles("RHand",MathUtil.deg2rad(100f),0.4f);
    }

    public void grab() throws Exception {
        /**
         * Grab
         */
        motion.setAngles("RShoulderRoll",MathUtil.deg2rad(30f),0.4f);
        motion.setAngles("LShoulderRoll",MathUtil.deg2rad(-30f),0.4f);
    }

    public void liftObject() throws Exception{
        /**
         * lift grabed Object
         */
        motion.setAngles("RShoulderPitch",MathUtil.deg2rad(-20f),0.4f);
        motion.setAngles("LShoulderPitch",MathUtil.deg2rad(-20f),0.4f);


    }

}
