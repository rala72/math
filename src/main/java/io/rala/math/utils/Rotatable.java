package io.rala.math.utils;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.geometry.Point;
import org.jetbrains.annotations.NotNull;

/**
 * This interface allows to generate rotate instances.
 *
 * @param <N> number class of {@code T}
 * @param <T> class to rotate
 * @since 1.0.0
 */
public interface Rotatable<N extends Number, T> {
    /**
     * @param phi angle in radiant
     * @return a new instance with rotated properties
     * @implSpec default implementation should call
     * {@link #rotate(Point, Number)}
     * with {@link Point#Point(AbstractArithmetic)}
     * @since 1.0.0
     */
    @NotNull
    T rotate(@NotNull N phi);

    /**
     * @param center rotation center to rotate - class without position ignore this value
     * @param phi    angle in radiant
     * @return a new instance with rotated properties
     * @since 1.0.0
     */
    @NotNull
    T rotate(@NotNull Point<N> center, @NotNull N phi);
}
