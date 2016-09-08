package de.dhbw.wwi13b.shared.util;

/**
 * @deprecated
 */
public class MathUtil {

    /**
     * Converts a degree value to radian
     * @param deg
     * @return
     * @deprecated use {@link Math#toRadians(double)}
     */
    public static float deg2rad(float deg) {
        return (float)(deg * (Math.PI / 180));
    }
    
    /**
     * Converts a radian value to degrees
     * @param rad
     * @return
     * @deprecated use {@link Math#toDegrees(double)}
     */
    public static float rad2deg(float rad) {
        return (float)(rad * 180 / Math.PI);
    }

}
