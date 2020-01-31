package io.rala.math.utils;

import io.rala.math.geometry.Point;

/**
 * This interface allows to generate rotate instances.
 *
 * @param <T> class to rotate
 */
public interface Rotatable<T> {
    /**
     * @param phi angle in radiant
     * @return a new instance with rotated properties
     * @implSpec default implementation calls
     * {@link #rotate(Point, double)} with {@link Point#Point()}
     */
    default T rotate(double phi) {
        return rotate(new Point(), phi);
    }

    /**
     * @param center rotation center to rotate - class without position ignore this value
     * @param phi    angle in radiant
     * @return a new instance with rotated properties
     */
    T rotate(Point center, double phi);
}
