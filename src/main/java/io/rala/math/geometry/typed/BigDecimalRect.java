package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Rect;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a rect in 2d area with point a, b &amp; size
 * storing {@link BigDecimal}
 */
public class BigDecimalRect extends Rect<BigDecimal> {
    // region constructors

    /**
     * @param height height of rect
     * @param width  width of rect
     * @see Rect#Rect(AbstractArithmetic, Number, Number)
     */
    public BigDecimalRect(BigDecimal height, BigDecimal width) {
        super(BigDecimalArithmetic.getInstance(), height, width);
    }

    /**
     * @param height  height of rect
     * @param width   width of rect
     * @param context context of {@link BigDecimalArithmetic}
     * @see Rect#Rect(AbstractArithmetic, Number, Number)
     */
    public BigDecimalRect(BigDecimal height, BigDecimal width, MathContext context) {
        super(new BigDecimalArithmetic(context), height, width);
    }

    /**
     * @param a    a of rect
     * @param b    b of rect
     * @param size height of rect
     * @see Rect#Rect(AbstractArithmetic, Point, Point, Number)
     */
    public BigDecimalRect(Point<BigDecimal> a, Point<BigDecimal> b, BigDecimal size) {
        super(BigDecimalArithmetic.getInstance(), a, b, size);
    }

    /**
     * @param a       a of rect
     * @param b       b of rect
     * @param size    height of rect
     * @param context context of {@link BigDecimalArithmetic}
     * @see Rect#Rect(AbstractArithmetic, Point, Point, Number)
     */
    public BigDecimalRect(
        Point<BigDecimal> a, Point<BigDecimal> b, BigDecimal size, MathContext context
    ) {
        super(new BigDecimalArithmetic(context), a, b, size);
    }

    // endregion
}
