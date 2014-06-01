package org.supply.simulator.sandbox.prototype.util;

/**
 * Created by Alex on 6/1/2014.
 */
public class MathUtilities {

    private static final double PI = 3.14159265358979323846;

    public static float coTangent(float angle) {
        return (float)(1f / Math.tan(angle));
    }

    public static float degreesToRadians(float degrees) {
        return degrees * (float)(PI / 180d);
    }

    public static double getPi() {
        return PI;
    }
}


