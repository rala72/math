package io.rala.math.utils;

import io.rala.math.geometry.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * This interface allows to move instances.
 *
 * @param <N> number class of {@code T}
 * @param <T> class to move
 * @since 1.0.0
 */
public interface Movable<N extends Number, T> {
    /**
     * @param xy x and y value to use for movement
     * @return a new instance moved by given value
     * @implSpec default implementation calls {@link #move(Number, Number)}
     * with value at x and y
     * @see #move(Number, Number)
     * @see #move(Vector)
     * @since 1.0.0
     */
    @NotNull
    default T move(@NotNull N xy) {
        return move(xy, xy);
    }

    /**
     * @param x x value to use for movement
     * @param y y value to use for movement
     * @return a new instance moved by values
     * @see #move(Number)
     * @see #move(Vector)
     * @since 1.0.0
     */
    @NotNull
    T move(@NotNull N x, @NotNull N y);

    /**
     * @param vector vector to use for movement
     * @return a new instance moved by given vector
     * @implSpec default implementation calls
     * {@link #move(Number, Number)} with vector attributes
     * @see #move(Number)
     * @see #move(Number, Number)
     * @since 1.0.0
     */
    @NotNull
    default T move(@NotNull Vector<N> vector) {
        return move(vector.getX(), vector.getY());
    }
}
