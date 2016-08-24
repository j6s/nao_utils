package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALSonar;

import java.util.List;

/**
 * Created by maxdome on 8/24/16.
 */
public class SonarUtil {
    /**
     * ALMotion service instance
     */
    protected ALSonar sonar;

    public SonarUtil(Session session) throws Exception {
        this.sonar = new ALSonar(session);
    }

    public List<Float> getFilterdValues() throws Exception{
        /**
         * get FilteredValues, rightside, leftside
         */
        return sonar.getFilteredValues();
    }



}
