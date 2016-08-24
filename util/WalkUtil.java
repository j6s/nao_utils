package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;
import de.dhbw.wwi13b.shared.util.thread.Runnable;
import de.dhbw.wwi13b.shared.util.thread.Thread;

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

    /**
     * enabled or disabled Arm movement by walk
     * @param leftArm, rightArm
     * @throws InterruptedException
     * @throws CallError
     */
    public void setMoveArmsEnabled(boolean leftArm, boolean rightArm) throws InterruptedException,CallError {
        this.motion.setMoveArmsEnabled(leftArm, rightArm);
    }

    public Thread scan() {
        final boolean[] scan = {true};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int increment = 20;

                while (scan[0]) {
                    try {
                        turnRight(increment);
                        Thread.sleep(500);
                    } catch (CallError callError) {
                        callError.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onInterrupt() throws CallError, InterruptedException {
                scan[0] = false;
                motion.stopMove();
                motion.stopWalk();
            }
        });

        thread.start();
        return thread;
    }

}
