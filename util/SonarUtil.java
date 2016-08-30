package de.dhbw.wwi13b.shared.util;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALSonar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxdome on 8/24/16.
 */
public class SonarUtil {
    /**
     * ALMotion service instance
     */
    protected ALSonar sonar;
    protected  ALMemory memory;

    public SonarUtil(Session session) throws Exception {
        this.sonar = new ALSonar(session);
        this.memory = new ALMemory(session);
    }


    /**
     * get distaces, leftside, rightside
     */
    public List<Float> getDistances() throws Exception{

        sonar.subscribe("SONAR");

        Object ol = memory.getData("Device/SubDeviceList/US/Left/Sensor/Value");
        Object or = memory.getData("Device/SubDeviceList/US/Right/Sensor/Value");

        sonar.unsubscribe("SONAR");

        List<Float> values = new ArrayList<>();
        values.add(Float.parseFloat(ol.toString()));
        values.add(Float.parseFloat(or.toString()));

        return values;
    }

    public float getDistancesMean() throws Exception {
        List<Float> values = this.getDistances();
        float total = 0;
        for (float val : values) {
            total += val;
        }

        System.out.println(total/values.size());

        return total / values.size();
    }



}
