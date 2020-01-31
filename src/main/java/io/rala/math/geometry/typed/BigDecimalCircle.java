package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a circle a in 2d area with center &amp; radius
 * storing {@link BigDecimal}
 */
public class BigDecimalCircle extends Circle<BigDecimal> {
    // region constructors

    /**
     * @see Circle#Circle(AbstractArithmetic)
     */
    public BigDecimalCircle() {
        super(new BigDecimalArithmetic());
    }

    /**
     * @param context context of {@link BigDecimalArithmetic}
     * @see Circle#Circle(AbstractArithmetic)
     */
    public BigDecimalCircle(MathContext context) {
        super(new BigDecimalArithmetic(context));
    }

    /**
     * @param center center point of circle
     * @see Circle#Circle(AbstractArithmetic, Point)
     */
    public BigDecimalCircle(Point<BigDecimal> center) {
        super(new BigDecimalArithmetic(), center);
    }

    /**
     * @param center  center point of circle
     * @param context context of {@link BigDecimalArithmetic}
     * @see Circle#Circle(AbstractArithmetic, Point)
     */
    public BigDecimalCircle(Point<BigDecimal> center, MathContext context) {
        super(new BigDecimalArithmetic(context), center);
    }

    /**
     * @param radius radius of circle
     * @see Circle#Circle(AbstractArithmetic, Number)
     */
    public BigDecimalCircle(BigDecimal radius) {
        super(new BigDecimalArithmetic(), radius);
    }

    /**
     * @param radius  radius of circle
     * @param context context of {@link BigDecimalArithmetic}
     * @see Circle#Circle(AbstractArithmetic, Number)
     */
    public BigDecimalCircle(BigDecimal radius, MathContext context) {
        super(new BigDecimalArithmetic(context), radius);
    }

    /**
     * @param center center point of circle
     * @param point  point on circle
     * @see Circle#Circle(AbstractArithmetic, Point, Point)
     */
    public BigDecimalCircle(Point<BigDecimal> center, Point<BigDecimal> point) {
        super(new BigDecimalArithmetic(), center, point);
    }

    /**
     * @param center  center point of circle
     * @param point   point on circle
     * @param context context of {@link BigDecimalArithmetic}
     * @see Circle#Circle(AbstractArithmetic, Point, Point)
     */
    public BigDecimalCircle(
        Point<BigDecimal> center, Point<BigDecimal> point, MathContext context
    ) {
        super(new BigDecimalArithmetic(), center, point);
    }

    /**
     * @param center center point of circle
     * @param radius radius of circle
     * @see Circle#Circle(AbstractArithmetic, Point, Number)
     */
    public BigDecimalCircle(Point<BigDecimal> center, BigDecimal radius) {
        super(new BigDecimalArithmetic(), center, radius);
    }

    /**
     * @param center  center point of circle
     * @param radius  radius of circle
     * @param context context of {@link BigDecimalArithmetic}
     * @see Circle#Circle(AbstractArithmetic, Point, Number)
     */
    public BigDecimalCircle(
        Point<BigDecimal> center, BigDecimal radius, MathContext context
    ) {
        super(new BigDecimalArithmetic(context), center, radius);
    }

    // endregion
}
