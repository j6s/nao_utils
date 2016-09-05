package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;
import de.dhbw.wwi13b.shared.thread.Runnable;
import de.dhbw.wwi13b.shared.thread.Thread;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple abstraction over walking and walking related tasks.
 * Provides an easy interface for walking related tasks that is
 * based on degree instead of radian values.
 *
 * @author Johannes Hertenstein
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
     *
     * @throws CallError
     * @throws InterruptedException
     */
    public void init() throws CallError, InterruptedException {
        super.init();
        this.posture(Posture.STAND_INIT);
    }

    /**
     * Turns right (Clockwise) by x degrees
     *
     * @param degree    Angle to turn by in degrees
     * @throws InterruptedException
     * @throws CallError
     */
    public void turnLeft(float degree) throws InterruptedException, CallError {
        float rad = MathUtil.deg2rad(degree);
        log("turnLeft " + degree + "deg (" + rad + " rad)");
        this.motion.moveTo(0f, 0f, rad);
    }

    /**
     * Turns to the left, but slowly
     * @param degree
     * @throws InterruptedException
     * @throws CallError
     */
    public void turnLeftSlow(int degree) throws InterruptedException, CallError {

        // slight adjustment for to slow turning.
        // TODO remove this
        degree = (int) (degree * 1.25);
        float theta = (float) ((degree / Math.abs(degree)) * .05);

        List<List<Float>> steps = new ArrayList<>();
        List<Float> step = new ArrayList<>();
        step.add(0f); // X
        step.add(0f); // Y
        step.add(theta);

        List<Float> s = new ArrayList<>();
        s.add(0.01f);

        List<String> legs = new ArrayList<>();
        boolean leftLeg = true;

        for (float i = 0; i < Math.abs(Math.toRadians(degree)); i += Math.abs(theta)) {
            steps.add(step);
            legs.add(leftLeg ? "LLeg" : "RLeg");
            leftLeg = !leftLeg;
        }

        System.out.println(steps);
        System.out.println(legs);

        this.motion.setFootStepsWithSpeed(legs, steps, s, false);

        while (this.motion.moveIsActive()) {
            Thread.sleep(200);
        }
    }

    public void turnRightSlow(int degrees) throws CallError, InterruptedException {
        this.turnLeftSlow(-1 * degrees);
    }


    /**
     * Turns left (Counterclockwise) by x degrees
     * @param degree    Anple to turn by in degrees
     * @throws CallError
     * @throws InterruptedException
     */
    public void turnRight(int degree) throws CallError, InterruptedException {
        this.turnLeft(-1 * degree);
    }

    /**
     * Walks x meters forward.
     *
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

    /**
     * Continuously turns in 20 degree increments and waits for a short while.
     * Use when searching for items.
     *
     * call Thread.interrupt on the returned thread when done.
     * @return
     */
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

    /**
     * Turns the body in order to face a certain target
     * The position is a list of coordinates in the form of [x, y]
     * @param position
     */
    public void turnTowards(List<Float> position) throws CallError, InterruptedException {
        log("turnTowards " + position);
        float deg = (float) Math.toDegrees(Math.atan(position.get(1) / position.get(0)));
        this.turnLeft(deg);
    }

    public ALMotion getMotion() {
        return motion;
    }

}
