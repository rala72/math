package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;

/**
 * class which holds a line segment in a 2d area with points a &amp; b
 * storing Double
 *
 * @since 1.0.0
 */
public class DoubleLineSegment extends LineSegment<Double> {
    // region constructors

    /**
     * @param b b value to be used in
     *          {@link LineSegment#LineSegment(AbstractArithmetic, Point, Point)} at b
     * @see LineSegment#LineSegment(AbstractArithmetic, Point)
     * @since 1.0.0
     */
    public DoubleLineSegment(Point<Double> b) {
        super(DoubleArithmetic.getInstance(), b);
    }

    /**
     * @param a a value of line segment
     * @param b b value of line segment
     * @see LineSegment#LineSegment(AbstractArithmetic, Point, Point)
     * @since 1.0.0
     */
    public DoubleLineSegment(Point<Double> a, Point<Double> b) {
        super(DoubleArithmetic.getInstance(), a, b);
    }

    // endregion
}
