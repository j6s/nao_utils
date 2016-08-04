package de.dhbw.wwi13b.shared.util;

/**
 * Created by j on 8/3/16.
 */
public class MathUtil {

    /**
     * Converts a degree value to radian
     * @param deg
     * @return
     */
    public static float deg2rad(float deg) {
        return (float)(deg * (Math.PI / 180));
    }
    
    /**
     * Converts a radian value to degrees
     * @param rad
     * @return 
     */
    public static float rad2deg(float rad) {
        return (float)(rad * 180 / Math.PI);
    }

}
