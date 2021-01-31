package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a line segment in a 2d area with points a &amp; b
 * storing BigDecimal
 *
 * @since 1.0.0
 */
public class BigDecimalLineSegment extends LineSegment<BigDecimal> {
    // region constructors

    /**
     * @param b b value to be used in
     *          {@link LineSegment#LineSegment(AbstractArithmetic, Point, Point)} at b
     * @see LineSegment#LineSegment(AbstractArithmetic, Point)
     * @since 1.0.0
     */
    public BigDecimalLineSegment(Point<BigDecimal> b) {
        super(BigDecimalArithmetic.getInstance(), b);
    }

    /**
     * @param b       b value to be used in
     *                {@link LineSegment#LineSegment(AbstractArithmetic, Point, Point)}
     *                at b
     * @param context context of {@link BigDecimalArithmetic}
     * @see LineSegment#LineSegment(AbstractArithmetic, Point)
     * @since 1.0.0
     */
    public BigDecimalLineSegment(Point<BigDecimal> b, MathContext context) {
        super(new BigDecimalArithmetic(context), b);
    }

    /**
     * @param a a value of line segment
     * @param b b value of line segment
     * @see LineSegment#LineSegment(AbstractArithmetic, Point, Point)
     * @since 1.0.0
     */
    public BigDecimalLineSegment(Point<BigDecimal> a, Point<BigDecimal> b) {
        super(BigDecimalArithmetic.getInstance(), a, b);
    }

    /**
     * @param a       a value of line segment
     * @param b       b value of line segment
     * @param context context of {@link BigDecimalArithmetic}
     * @see LineSegment#LineSegment(AbstractArithmetic, Point, Point)
     * @since 1.0.0
     */
    public BigDecimalLineSegment(
        Point<BigDecimal> a, Point<BigDecimal> b, MathContext context
    ) {
        super(new BigDecimalArithmetic(context), a, b);
    }

    // endregion
}
