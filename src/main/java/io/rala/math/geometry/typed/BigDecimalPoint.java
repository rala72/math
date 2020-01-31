package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.Point;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a point in a 2d area with x &amp; y
 * storing {@link BigDecimal}
 */
public class BigDecimalPoint extends Point<BigDecimal> {
    // region constructors

    /**
     * @see Point#Point(AbstractArithmetic)
     */
    public BigDecimalPoint() {
        super(new BigDecimalArithmetic());
    }

    /**
     * @param context context of {@link BigDecimalArithmetic}
     * @see Point#Point(AbstractArithmetic)
     */
    public BigDecimalPoint(MathContext context) {
        super(new BigDecimalArithmetic(context));
    }

    /**
     * @param xy value to be used in
     *           {@link Point#Point(AbstractArithmetic, Number, Number)} at x and y
     * @see Point#Point(AbstractArithmetic, Number)
     */
    public BigDecimalPoint(BigDecimal xy) {
        super(new BigDecimalArithmetic(), xy);
    }

    /**
     * @param xy      value to be used in
     *                {@link Point#Point(AbstractArithmetic, Number, Number)} at x and y
     * @param context context of {@link BigDecimalArithmetic}
     * @see Point#Point(AbstractArithmetic, Number)
     */
    public BigDecimalPoint(BigDecimal xy, MathContext context) {
        super(new BigDecimalArithmetic(context), xy);
    }

    /**
     * @param x x value of point
     * @param y y value of point
     * @see Point#Point(AbstractArithmetic, Number, Number)
     */
    public BigDecimalPoint(BigDecimal x, BigDecimal y) {
        super(new BigDecimalArithmetic(), x, y);
    }

    /**
     * @param x       x value of point
     * @param y       y value of point
     * @param context context of {@link BigDecimalArithmetic}
     * @see Point#Point(AbstractArithmetic, Number, Number)
     */
    public BigDecimalPoint(BigDecimal x, BigDecimal y, MathContext context) {
        super(new BigDecimalArithmetic(context), x, y);
    }

    // endregion
}