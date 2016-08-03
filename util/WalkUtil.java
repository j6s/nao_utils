package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;

/**
 * Simple abstraction over walking and walking related tasks.
 */
public class WalkUtil extends PostureUtil {

    /**
     * Motion instance
     */
    protected ALMotion motion;

    public WalkUtil(Session session) throws Exception {
        super(session);
        this.motion = new ALMotion(session);
    }

    /**
     * Initializes the walking behaviour. Applies the correct settings
     * for the robot to start walking.
     * @throws CallError
     * @throws InterruptedException
     */
    public void init() throws CallError, InterruptedException {
        super.init();
        this.posture(Posture.STAND);
    }

    /**
     * Turns right (Clockwise) by x degrees
     * @param degree
     * @throws InterruptedException
     * @throws CallError
     */
    public void turnLeft(int degree) throws InterruptedException, CallError {
        float rad = MathUtil.deg2rad(degree);
        this.motion.moveTo(0f, 0f, rad);
    }

    /**
     * Turns left (Counterclockwise) by x degrees
     * @param degree
     * @throws CallError
     * @throws InterruptedException
     */
    public void turnRight(int degree) throws CallError, InterruptedException {
        this.turnLeft(-1 * degree);
    }

    /**
     * Walks x meters forward.
     * @param meter
     * @throws InterruptedException
     * @throws CallError
     */
    public void walk(float meter) throws InterruptedException, CallError {
        this.motion.moveTo(meter, 0f, 0f);
    }

}
