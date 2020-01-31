package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;

import java.math.BigDecimal;

/**
 * class which holds a line segment in a 2d area with points a &amp; b
 * storing BigDecimal
 */
public class BigDecimalLineSegment extends LineSegment<BigDecimal> {
    // region constructors

    /**
     * @param b b value to be used in
     *          {@link LineSegment#LineSegment(AbstractArithmetic, Point, Point)} at b
     * @see LineSegment#LineSegment(AbstractArithmetic, Point)
     */
    public BigDecimalLineSegment(Point<BigDecimal> b) {
        super(new BigDecimalArithmetic(), b);
    }

    /**
     * @param a a value of line segment
     * @param b b value of line segment
     * @see LineSegment#LineSegment(AbstractArithmetic, Point, Point)
     */
    public BigDecimalLineSegment(Point<BigDecimal> a, Point<BigDecimal> b) {
        super(new BigDecimalArithmetic(), a, b);
    }

    // endregion
}
