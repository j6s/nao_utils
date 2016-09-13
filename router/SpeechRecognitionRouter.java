package de.dhbw.wwi13b.shared.router;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import de.dhbw.wwi13b.shared.logging.Log;
import de.dhbw.wwi13b.shared.util.Language;
import de.dhbw.wwi13b.shared.util.SpeechUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * SpeechRecognitionRouter: Simple router that routes nao Speech
 * Recognition to Functions
 *
 * @example
 * SpeechRecognitionRouter router = new SpeechRecognitionRouter(application.session(), Language.GERMAN);
 *
 * router.register("test", (word) -> {
 *     System.out.println("Robotor said test");
 *     return null;
 * });
 * router.register("bier", (word) -> {
 *     System.out.println("Roboter said bier");
 *     return null;
 * });
 *
 * router.startListening();
 */
public class SpeechRecognitionRouter {

    private static String TAG = "SpeecRecognitionRouter";

    /**
     * Callbacks that are registered at the nao.
     * The funtions will be run, whenever that word will be
     * detected
     */
    private Map<String, Function<String, Void>> callbacks = new HashMap();

    /**
     * Instance of the SpeechUtil
     */
    private SpeechUtil speech;

    /**
     * Thread for the current speech recognition
     */
    private Thread recognitionThread;

    public SpeechRecognitionRouter(Session session) throws Exception {
        this(session, Language.ENGLISH);
    }

    public SpeechRecognitionRouter(Session session, Language language) throws Exception {
        this.speech = new SpeechUtil(session);
        this.speech.setLanguage(language);
        Log.debug(TAG, "New Router initialized");
    }

    /**
     * Registers a new word with a new callback
     * @param word
     * @param callback
     */
    public void register(String word, Function<String, Void> callback) throws InterruptedException, CallError {
        Log.debug(TAG, "Word >>" + word + "<< registered");
        this.callbacks.put(word, callback);
        if (this.recognitionThread != null) {
            this.stopListening();
            this.startListening();
        }
    }

    /**
     * Starts actively listening.
     * The listening process will be restarted every once
     * in a while and will run until stopListening ist invoked
     */
    public Thread startListening() throws CallError, InterruptedException {
        Log.info(TAG, "starting to listen");
        int seconds = 60;
        List<String> words = new ArrayList<>(callbacks.keySet());

        this.recognitionThread = speech.onSpeech(words, seconds, (word) -> {
            Log.debug(TAG, "Word >>" + word + "<< detected");
            Function<String, Void> callback = callbacks.get(word);
            if (callback != null) {
                Log.info(TAG, "Executing callback for word >>" + word + "<<");
                stopListening();
                callback.apply(word);
            } else {
                Log.error(TAG, "No callback for word >>" + word + "<< found");
            }
            return null;
        });

        // restart listening process after it stopped
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep((seconds - 1) * 1000);
                    if (recognitionThread != null) {
                        log("restarting listening");
                        stopListening();
                        startListening();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).run(); */
        return this.recognitionThread;
    }

    /**
     * Stops the listening process
     */
    public void stopListening() {
        Log.info(TAG, "stopping to listen");
        try {
            speech.stop();
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.recognitionThread.stop();
        this.recognitionThread.interrupt();
    }



}
