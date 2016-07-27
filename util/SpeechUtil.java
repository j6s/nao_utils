package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by j on 7/27/16.
 */
public class SpeechUtil {

    /**
     * Speech instance
     */
    protected ALSpeechRecognition speech;

    /**
     * Memory instance
     */
    protected ALMemory memory;

    /**
     * Mapping from enum to identifier Strings
     */
    protected Map<Language, String> mapping = new HashMap<>();


    public SpeechUtil(Session session) throws Exception {
        this.speech = new ALSpeechRecognition(session);
        this.memory = new ALMemory(session);
        this.mapping.put(Language.ENGLISH, "English");
        this.mapping.put(Language.GERMAN, "German");
    }

    /**
     * Set's the language based on the Enum
     * @param language
     * @throws InterruptedException
     * @throws CallError
     */
    public void setLanguage(Language language) throws InterruptedException, CallError {
        this.speech.setLanguage(this.mapping.get(language));
    }

    /**
     * Calls onSpeech with 5 seconds time
     * @param words
     * @param callback
     * @return
     * @throws CallError
     * @throws InterruptedException
     */
    public Thread onSpeech(List<String> words, SpeechRecognitionCallback callback) throws CallError, InterruptedException {
        return this.onSpeech(words, 5, callback);
    }

    /**
     * Listens for a set list of words for a set amount of time.
     * Returns the Thread that was started for listening
     * @param words
     * @param seconds
     * @param callback
     * @return
     * @throws InterruptedException
     * @throws CallError
     */
    public Thread onSpeech(List<String> words, int seconds, SpeechRecognitionCallback callback) throws InterruptedException, CallError {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("IN HERE");
                    speech.setWordListAsVocabulary(words);
                    speech.subscribe("BLA");
                    memory.subscribeToEvent("WordRecognized", new EventCallback() {
                        @Override
                        public void onEvent(Object o) throws InterruptedException, CallError {
                            ArrayList<Object> list = (ArrayList<Object>)o;
                            callback.onSpeech((String)list.get(0));
                            System.out.println((String)list.get(0));
                        }
                    });
                    Thread.sleep(seconds * 1000);
                    speech.unsubscribe("BLA");
                } catch (CallError callError) {
                    callError.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        return thread;
    }

}
