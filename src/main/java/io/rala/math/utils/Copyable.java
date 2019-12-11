package io.rala.math.utils;

/**
 * This interface allows to generate copy instances.
 *
 * @param <T> class to copy
 */
@SuppressWarnings("unused")
public interface Copyable<T> {
    /**
     * @return a new instance with same properties
     */
    T copy();
}
