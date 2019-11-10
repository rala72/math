package io.rala.math.utils;

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
    T rotate(double phi);
}
