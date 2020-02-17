package io.rala.math.utils;

/**
 * collection of unit conversion functions
 */
public class Units {
    private Units() {
    }

    /**
     * @param a grad to convert
     * @return {@code 2*&pi;/360 * a}
     */
    public static double gradToRad(double a) {
        return (2 * Math.PI * a) / 360;
    }

    /**
     * @param a radians to convert
     * @return {@code 360/2*&pi; * a}
     */
    public static double radToGrad(double a) {
        return (360 * a) / (2 * Math.PI);
    }
}
