package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.Vector;

import java.math.BigDecimal;

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
        super(new BigDecimalArithmetic());
    }

    /**
     * @param xy value to be used in
     *           {@link Vector#Vector(AbstractArithmetic, Number, Number)} at x and y
     * @see Vector#Vector(AbstractArithmetic, Number)
     */
    public BigDecimalVector(BigDecimal xy) {
        super(new BigDecimalArithmetic(), xy);
    }

    /**
     * @param x x value of vector
     * @param y y value of vector
     * @see Vector#Vector(AbstractArithmetic, Number, Number)
     */
    public BigDecimalVector(BigDecimal x, BigDecimal y) {
        super(new BigDecimalArithmetic(), x, y);
    }

    // endregion
}
