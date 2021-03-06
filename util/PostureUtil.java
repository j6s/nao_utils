package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import de.dhbw.wwi13b.shared.logging.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstraction over AlMotion and AlRobotPosture that handles the
 * robot going into different postures.
 * This implementation does not rely on Magic Strings and uses
 * the {@link Posture} enum instead.
 * The {@link WalkUtil} extends this class and thus contains all of the
 * same functionality
 *
 * @author Johannes Hertenstein
 * @see Posture
 * @see WalkUtil
 */
public class PostureUtil {

    /**
     * Mapping of Posture enum to Stirng identifiers
     */
    protected Map<Posture, String> mapping = new HashMap<>();

    /**
     * Posture service instance
     */
    protected ALRobotPosture posture;

    /**
     * ALMotion service instance
     */
    protected ALMotion motion;

    /**
     * TTS instance
     */
    protected ALTextToSpeech tts;

    protected static String TAG = "PostureUtil";


    public PostureUtil(Session session) throws Exception {
        this.posture = new ALRobotPosture(session);
        this.motion = new ALMotion(session);
        this.tts = new ALTextToSpeech(session);

        this.mapping.put(Posture.CROUCH, "Crouch");
        this.mapping.put(Posture.LYING_BACK, "LyingBack");
        this.mapping.put(Posture.LYING_BELLY, "LyingBelly");
        this.mapping.put(Posture.SIT, "Sit");
        this.mapping.put(Posture.SIT_ON_CHAIR, "SitOnChair");
        this.mapping.put(Posture.SIT_RELAX, "SitRelax");
        this.mapping.put(Posture.STAND, "Stand");
        this.mapping.put(Posture.STAND_INIT, "StandInit");
        this.mapping.put(Posture.STAND_ZERO, "StandZero");
    }

    /**
     * Initializes the posture: Waking up and settings stiffness
     * @throws InterruptedException
     * @throws CallError
     */
    public void init() throws InterruptedException, CallError {
        Log.debug(TAG, "init: waking up");
        this.motion.wakeUp();

        Log.debug(TAG, "init: settings stiffness");
        this.motion.stiffnessInterpolation("Body", 1f, 1f);
    }

    /**
     * Demo of all postures - cycles of all postures every 5 seconds
     * Use with caution
     * @throws InterruptedException
     * @throws CallError
     */
    public void demo() throws InterruptedException, CallError {
        this.tts.say("Demo posture");
        List postures = posture.getPostureList();
        for(Object p : postures) {
            this.tts.say(p.toString());
            this.goToPostureRaw(p.toString());
            Thread.sleep(5000);
        }

    }

    /**
     * Raw goToPosture call that accepts Strings
     * @param posture
     * @throws InterruptedException
     * @throws CallError
     */
    public void goToPostureRaw(String posture) throws InterruptedException, CallError {
        Log.debug(TAG, "going to posture " + posture);
        this.posture.stopMove();
        this.posture.goToPosture(posture, 1f);
    }

    /**
     * goes to a posture
     * @param posture
     * @throws CallError
     * @throws InterruptedException
     */
    public void posture(Posture posture) throws CallError, InterruptedException {
        this.goToPostureRaw(this.mapping.get(posture));
    }

    /**
     * Rests the POS robot
     * @throws InterruptedException
     * @throws CallError
     */
    public void rest() throws InterruptedException, CallError {
        Log.debug(TAG, "going to rest posture");
        this.motion.rest();
    }

    /**
     * Gets the current posture
     * @return
     * @throws InterruptedException
     * @throws CallError
     */
    public Posture currentPosture() throws InterruptedException, CallError {
        String posture = this.posture.getPosture();
        for (Posture p : this.mapping.keySet()) {
            if (this.mapping.get(p).equals(posture)) {
                return p;
            }
        }
        return null;
    }

}
