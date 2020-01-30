package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.Point;

import java.math.BigDecimal;

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
     * @param xy value to be used in
     *           {@link Point#Point(AbstractArithmetic, Number, Number)} at x and y
     * @see Point#Point(AbstractArithmetic, Number)
     */
    public BigDecimalPoint(BigDecimal xy) {
        super(new BigDecimalArithmetic(), xy);
    }

    /**
     * @param x x value of point
     * @param y y value of point
     * @see Point#Point(AbstractArithmetic, Number, Number)
     */
    public BigDecimalPoint(BigDecimal x, BigDecimal y) {
        super(new BigDecimalArithmetic(), x, y);
    }

    // endregion
}
