package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Triangle;

/**
 * class which holds a triangle in a 2d area with points a, b &amp; c
 * storing {@link Double}
 *
 * @since 1.0.0
 */
public class DoubleTriangle extends Triangle<Double> {
    // region constructors

    /**
     * @param a a of triangle
     * @param b b of triangle
     * @param c c of triangle
     * @see Triangle#Triangle(AbstractArithmetic, Point, Point, Point)
     * @since 1.0.0
     */
    public DoubleTriangle(Point<Double> a, Point<Double> b, Point<Double> c) {
        super(DoubleArithmetic.getInstance(), a, b, c);
    }

    // endregion
}
