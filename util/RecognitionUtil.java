/**
 * Created by huck on 17.08.16.
 * Recognition for Landmarks
 */
package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.aldebaran.qi.helper.proxies.ALVisionRecognition;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class RecognitionUtil {

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
                System.out.println("IN REC");
                landmark.subscribe("iSub", 500, 0.0f);

                memory.subscribeToEvent("LandmarkDetected", o -> {
                    ArrayList<Object> list = (ArrayList<Object>) o;
                    if (!list.isEmpty()) {
                        System.out.println("has landmark ");
                        ArrayList<Object> item1 = (ArrayList<Object>) list.get(1);

                        LMCoords coords = LMCoords.fromLandmarkInfo(item1);


                        System.out.println("root item:");
                        System.out.println(item1.toString());
                        System.out.println("coords (?): ");
                        System.out.println(item1.get(0).toString());

                        landmark.unsubscribe("iSub");

                        callback.apply(coords);
                        return;
                    } else {
                     System.out.println("no landmark");
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
