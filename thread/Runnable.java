package de.dhbw.wwi13b.shared.thread;

import com.aldebaran.qi.CallError;

/**
 * Created by j on 8/22/16.
 */
public interface Runnable extends java.lang.Runnable {

    public void onInterrupt() throws CallError, InterruptedException;

}
