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
     */
    default T rotate(double phi) {
        return rotate(new Point(), phi);
    }

    /**
     * @param center rotation center
     * @param phi    angle in radiant
     * @return a new instance with rotated properties
     */
    T rotate(Point center, double phi);
}
