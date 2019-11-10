package io.rala.math.utils;

import io.rala.math.geometry.Vector;

/**
 * This interface allows to move instances.
 *
 * @param <T> class to move
 */
public interface Movable<T> {
    /**
     * @param xy x and y value to use for movement
     * @return a new instance moved by given value
     * @see #move(double, double)
     * @see #move(Vector)
     */
    // calls {@link #move(double, double)} with value at x and y</i>
    default T move(double xy) {
        return move(xy, xy);
    }

    /**
     * @param x x value to use for movement
     * @param y y value to use for movement
     * @return a new instance moved by values
     * @see #move(double)
     * @see #move(Vector)
     */
    // calls {@link #move(Vector)} with vector
    default T move(double x, double y) {
        return move(new Vector(x, y));
    }

    /**
     * @param vector vector to use for movement
     * @return a new instance moved by given vector
     * @see #move(double)
     * @see #move(double, double)
     */
    T move(Vector vector);
}
