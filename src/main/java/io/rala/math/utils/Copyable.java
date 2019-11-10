package io.rala.math.utils;

/**
 * This interfaces allow to generate copy instances of it.
 *
 * @param <T> class to copy
 */
public interface Copyable<T> {
    /**
     * @return a new instance with same properties
     */
    T copy();
}
