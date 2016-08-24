package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;
import de.dhbw.wwi13b.shared.util.thread.Runnable;
import de.dhbw.wwi13b.shared.util.thread.Thread;

import java.util.HashMap;
import java.util.Map;


/**
 * Utility to move single joints
 */
public class JointUtil {

    /**
     * The ALMotion
     */
    private ALMotion motion;

    private WalkUtil walk;

    /**
     * Map from the Joint enum to the Magic strings
     */
    private Map<Joint, String> mapping = new HashMap<>();


    public JointUtil(Session session) throws Exception {
        this.walk = new WalkUtil(session);
        this.motion = new ALMotion(session);
        this.mapping.put(Joint.HEADYAW, "HeadYaw");
        this.mapping.put(Joint.HEADPITCH, "HeadPitch");
        this.mapping.put(Joint.LSHOULDERPITCH, "LShoulderPitch");
        this.mapping.put(Joint.LSHOULDERROLL, "LShoulderRoll");
        this.mapping.put(Joint.LELBOWYAW, "LElbowYaw");
        this.mapping.put(Joint.LELBOWROLL, "LElbowRoll");
        this.mapping.put(Joint.LWRISTYAW, "LWristYaw");
        this.mapping.put(Joint.LHAND, "LHand");
        this.mapping.put(Joint.LHIPYAWPITCH, "LHipYawPitch");
        this.mapping.put(Joint.LHIPROLL, "LHipRoll");
        this.mapping.put(Joint.LHIPPITCH, "LHipPitch");
        this.mapping.put(Joint.LKNEEPITCH, "LKneePitch");
        this.mapping.put(Joint.LANKLEPITCH, "LAnklePitch");
        this.mapping.put(Joint.LANKLEROLL, "LAnkleRoll");
        this.mapping.put(Joint.RHIPYAWPITCH, "RHipYawPitch");
        this.mapping.put(Joint.RHIPROLL, "RHipRoll");
        this.mapping.put(Joint.RHIPPITCH, "RHipPitch");
        this.mapping.put(Joint.RKNEEPITCH, "RKneePitch");
        this.mapping.put(Joint.RANKLEPITCH, "RAnklePitch");
        this.mapping.put(Joint.RANKLEROLL, "RAnkleRoll");
        this.mapping.put(Joint.RSHOULDERPITCH, "RShoulderPitch");
        this.mapping.put(Joint.RSHOULDERROLL, "RShoulderRoll");
        this.mapping.put(Joint.RELBOWYAW, "RElbowYaw");
        this.mapping.put(Joint.RELBOWROLL, "RElbowRoll");
        this.mapping.put(Joint.RWRISTYAW, "RWristYaw");
        this.mapping.put(Joint.RHAND, "RHand");
    }

    /**
     * Initializes all of the joints
     * @throws InterruptedException
     * @throws CallError
     */
    public void init() throws InterruptedException, CallError {
        for(Joint joint : this.mapping.keySet()) {
            this.motion.setStiffnesses(this.mapping.get(joint), 1f);
            this.motion.angleInterpolation(this.mapping.get(joint), 0, .4f, true);
        }
    }

    /**
     * Sets the absolute angle with a predefined speed
     * @param joint
     * @param angle
     * @throws CallError
     * @throws InterruptedException
     */
    public void setAngle(Joint joint, int angle) throws CallError, InterruptedException {
        this.setAngle(joint, angle, .4f);
    }

    /**
     * Sets the absolute angle with a custom speed
     * @param joint
     * @param angle
     * @param speed
     * @throws InterruptedException
     * @throws CallError
     */
    public void setAngle(Joint joint, int angle, float speed) throws InterruptedException, CallError {
        this.motion.setAngles(this.mapping.get(joint), MathUtil.deg2rad(angle), speed);
    }

    /**
     * Sets the relative angle with a predefined speed
     * @param joint
     * @param angle
     * @throws CallError
     * @throws InterruptedException
     */
    public void changeAngle(Joint joint, int angle) throws CallError, InterruptedException {
        this.changeAngle(joint, angle, .2f);
    }

    /**
     * Sets the relative angle with a custom speed
     * @param joint
     * @param angle
     * @param speed
     * @throws InterruptedException
     * @throws CallError
     */
    public void changeAngle(Joint joint, int angle, float speed) throws InterruptedException, CallError {
        this.motion.changeAngles(this.mapping.get(joint), Math.toRadians(angle), speed);
    }

    /**
     * Sets the relative angle with a predefined speed
     * Blocking method: Only returns after the movement was done
     *
     * @param joint
     * @param angle
     * @throws CallError
     * @throws InterruptedException
     */
    public void changeAngleSync(Joint joint, int angle) throws CallError, InterruptedException {
        this.changeAngleSync(joint, angle, .2f);
    }

    /**
     * Sets the relative angle with a custom speed
     * Blocking method: Only returns after the movement was done
     *
     * @param joint
     * @param angle
     * @param speed
     * @throws InterruptedException
     * @throws CallError
     */
    public void changeAngleSync(Joint joint, int angle, float speed) throws InterruptedException, CallError {
        speed = 2 / speed;
        this.motion.angleInterpolation(this.mapping.get(joint), Math.toRadians(angle), speed, false);
    }


    public void stopMoving() throws InterruptedException, CallError {
        this.motion.stopMove();
    }

}
