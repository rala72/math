package io.rala.math.utils;

/**
 * collection of unit conversion functions
 */
public class Units {
    // TO DO: move to root package?
    private Units() {
    }

    /**
     * @param a grad to convert
     * @return <code>2*&pi;/360 * a</code>
     */
    public static double gradToRad(double a) {
        return (2 * Math.PI * a) / 360;
    }

    /**
     * @param a radians to convert
     * @return <code>360/2*&pi; * a</code>
     */
    public static double radToGrad(double a) {
        return (360 * a) / (2 * Math.PI);
    }
}
