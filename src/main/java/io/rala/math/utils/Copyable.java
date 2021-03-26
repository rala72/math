package io.rala.math.utils;

import org.jetbrains.annotations.NotNull;

/**
 * This interface allows to generate copy instances.
 *
 * @param <T> class to copy
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface Copyable<T> {
    /**
     * @return a new instance with same properties
     * @since 1.0.0
     */
    @NotNull
    T copy();
}
