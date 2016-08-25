package de.dhbw.wwi13b.shared.thread;

import com.aldebaran.qi.CallError;

/**
 * Created by j on 8/22/16.
 */
public class Thread extends java.lang.Thread {

    private Runnable runnable;

    public Thread(Runnable runnable) {
        super(runnable);
        this.runnable = runnable;
    }

    public void interrupt() {
        try {
            this.runnable.onInterrupt();
        } catch (CallError callError) {
            callError.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.interrupt();
    }

}
