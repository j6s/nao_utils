/**
 * Created by huck on 17.08.16.
 * Recognition for Landmarks
 */
package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.aldebaran.qi.helper.proxies.ALVisionRecognition;
import de.dhbw.wwi13b.shared.logging.Log;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class RecognitionUtil {

    protected static String TAG = "RecognitionUtil";

    /**
     * Speech instance
     */
    protected ALVisionRecognition recognition;

    /**
     * Memory instance
     */
    protected ALMemory memory;

    protected ALLandMarkDetection landmark;
    protected ALTextToSpeech tts;
    /**
     * Mapping from enum to identifier Strings
     */
    protected Map<Language, String> mapping = new HashMap<>();


    public RecognitionUtil(Session session) throws Exception {
        this.recognition = new ALVisionRecognition(session);
        this.memory = new ALMemory(session);
        this.tts = new ALTextToSpeech(session);
        this.landmark = new ALLandMarkDetection(session);

    }

    //runs until Landmark is detected, passes ID and Coords via LMCoords via callback
    public Thread onRecognizeLandmark(Function<LMCoords, Void> callback) {
        Thread thread = new Thread(() -> {
            try {
                landmark.subscribe("iSub", 500, 0.0f);

                memory.subscribeToEvent("LandmarkDetected", o -> {
                    ArrayList<Object> list = (ArrayList<Object>) o;
                    if (!list.isEmpty()) {
                        Log.debug(TAG, "found landmark");
                        ArrayList<Object> item1 = (ArrayList<Object>) list.get(1);

                        LMCoords coords = LMCoords.fromLandmarkInfo(item1);


                        Log.debug(TAG, "root item:");
                        Log.debug(TAG, item1.toString());
                        Log.debug(TAG, "coords (?): ");
                        Log.debug(TAG, item1.get(0).toString());

                        landmark.unsubscribe("iSub");

                        callback.apply(coords);
                        return;
                    } else {
                        Log.warn(TAG, "No landmark found");
                    }
                });
                //to keep thread alife
                //Thread.sleep(15 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        return thread;
    }

}
