package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.Vector;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a vector in a 2d area with x &amp; y
 * storing {@link BigDecimal}
 */
public class BigDecimalVector extends Vector<BigDecimal> {
    // region constructors

    /**
     * @see Vector#Vector(AbstractArithmetic)
     */
    public BigDecimalVector() {
        super(BigDecimalArithmetic.getInstance());
    }

    /**
     * @param context context of {@link BigDecimalArithmetic}
     * @see Vector#Vector(AbstractArithmetic)
     */
    public BigDecimalVector(MathContext context) {
        super(new BigDecimalArithmetic(context));
    }

    /**
     * @param xy value to be used in
     *           {@link Vector#Vector(AbstractArithmetic, Number, Number)} at x and y
     * @see Vector#Vector(AbstractArithmetic, Number)
     */
    public BigDecimalVector(BigDecimal xy) {
        super(BigDecimalArithmetic.getInstance(), xy);
    }

    /**
     * @param xy      value to be used in
     *                {@link Vector#Vector(AbstractArithmetic, Number, Number)}
     *                at x and y
     * @param context context of {@link BigDecimalArithmetic}
     * @see Vector#Vector(AbstractArithmetic, Number)
     */
    public BigDecimalVector(BigDecimal xy, MathContext context) {
        super(new BigDecimalArithmetic(context), xy);
    }

    /**
     * @param x x value of vector
     * @param y y value of vector
     * @see Vector#Vector(AbstractArithmetic, Number, Number)
     */
    public BigDecimalVector(BigDecimal x, BigDecimal y) {
        super(BigDecimalArithmetic.getInstance(), x, y);
    }

    /**
     * @param x       x value of vector
     * @param y       y value of vector
     * @param context context of {@link BigDecimalArithmetic}
     * @see Vector#Vector(AbstractArithmetic, Number, Number)
     */
    public BigDecimalVector(BigDecimal x, BigDecimal y, MathContext context) {
        super(new BigDecimalArithmetic(context), x, y);
    }

    // endregion
}
