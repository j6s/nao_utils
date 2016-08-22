package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;

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

    /**
     * Map from the Joint enum to the Magic strings
     */
    private Map<Joint, String> mapping = new HashMap<>();


    public JointUtil(Session session) throws Exception {
        this.motion = new ALMotion(session);

        this.mapping.put(Joint.HEADJAW, "HeadYaw");
        this.mapping.put(Joint.HEADPITCH, "HeadPitch");
    }

    /**
     * Initializes all of the joints
     * @throws InterruptedException
     * @throws CallError
     */
    public void init() throws InterruptedException, CallError {
        for(Joint joint : this.mapping.keySet()) {
            this.motion.setStiffnesses(this.mapping.get(joint), 1f);
            this.setAngle(joint, 0);
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
        this.setAngle(joint, angle, .2f);
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
        this.changeAngleSync(joint, angle, 2f);
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
        this.motion.angleInterpolation(this.mapping.get(joint), Math.toRadians(angle), speed, false);
    }

}
