package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;

import java.math.BigDecimal;

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
     * @param center center point of circle
     * @see Circle#Circle(AbstractArithmetic, Point)
     */
    public BigDecimalCircle(Point<BigDecimal> center) {
        super(new BigDecimalArithmetic(), center);
    }

    /**
     * @param radius radius of circle
     * @see Circle#Circle(AbstractArithmetic, Number)
     */
    public BigDecimalCircle(BigDecimal radius) {
        super(new BigDecimalArithmetic(), radius);
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
     * @param center center point of circle
     * @param radius radius of circle
     * @see Circle#Circle(AbstractArithmetic, Point, Number)
     */
    public BigDecimalCircle(Point<BigDecimal> center, BigDecimal radius) {
        super(new BigDecimalArithmetic(), center, radius);
    }

    // endregion
}
