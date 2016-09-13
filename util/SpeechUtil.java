package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALAutonomousMoves;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * SpeechUtil:
 * Abstraction over ALSpeechRecognition for recognizing
 * words and reacting to those.
 * This abstraction does not use magic strings for the languages
 * and provides an easy lambda interface for listening for strings.
 *
 * For another layer of abstraction over Speech Recognition have a
 * look at the SpeechRecognitionRouter
 *
 * @see de.dhbw.wwi13b.shared.router.SpeechRecognitionRouter
 * @author Johannes Hertenstein
 */
public class SpeechUtil {

    /**
     * Speech instance
     */
    protected ALSpeechRecognition speech;

    /**
     * Autonomous Moves instance
     */
    protected ALAutonomousMoves autonomMoves;

    /**
     * Memory instance
     */
    protected ALMemory memory;

    /**
     * Mapping from enum to identifier Strings
     */
    protected Map<Language, String> mapping = new HashMap<>();

    protected long eventId;


    public SpeechUtil(Session session) throws Exception {
        this.speech = new ALSpeechRecognition(session);
        this.autonomMoves = new ALAutonomousMoves(session);
        this.memory = new ALMemory(session);

        for(String language : this.speech.getAvailableLanguages()) {
            if(language.equals("English")) {
                this.mapping.put(Language.ENGLISH, "English");
            }
            if(language.equals("German")) {
                this.mapping.put(Language.GERMAN, "German");
            }
        }
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
     */
    public Thread onSpeech(List<String> words, Function<String, Void> callback) throws CallError, InterruptedException {
        return this.onSpeech(words, 5, callback);
    }

    /**
     * Listens for a set list of words for a set amount of time.
     * Returns the Thread that was started for listening
     * Disable Autonome Movement while Listening
     * @param words
     * @param seconds
     * @param callback
     * @return
     */
    public Thread onSpeech(List<String> words, int seconds, Function<String, Void> callback) throws InterruptedException, CallError {
        autonomMoves.setExpressiveListeningEnabled(false);
        Thread thread = new Thread(() -> {
            try {
                //To avoid arm engine;
                speech.setWordListAsVocabulary(words);
                speech.subscribe("BLA");
                eventId = memory.subscribeToEvent("WordRecognized", o -> {
                    ArrayList<Object> list = (ArrayList<Object>)o;
                    callback.apply((String)list.get(0));
                    System.out.println((String)list.get(0));
                });
                Thread.sleep(seconds * 1000);
                speech.unsubscribe("BLA");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        return thread;
    }

    public void stop() throws CallError, InterruptedException {
        speech.unsubscribe("BLA");
        memory.unsubscribeToEvent(eventId);
    }

}
