package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.Line;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a line in a 2d area with m &amp; b
 * storing {@link BigDecimal}<br>
 * {@code y=m*x+b}<br>
 * if line is vertical m is considered to be {@code null}<br>
 * {@code y=b}
 */
public class BigDecimalLine extends Line<BigDecimal> {
    // region constructors

    /**
     * @param x x value of line
     * @see Line#Line(AbstractArithmetic, Number)
     */
    public BigDecimalLine(BigDecimal x) {
        super(BigDecimalArithmetic.getInstance(), x);
    }

    /**
     * @param x       x value of line
     * @param context context of {@link BigDecimalArithmetic}
     * @see Line#Line(AbstractArithmetic, Number)
     */
    public BigDecimalLine(BigDecimal x, MathContext context) {
        super(new BigDecimalArithmetic(context), x);
    }

    /**
     * @param m m value of line
     * @param b b value of line
     * @see Line#Line(AbstractArithmetic, Number, Number)
     */
    public BigDecimalLine(BigDecimal m, BigDecimal b) {
        super(BigDecimalArithmetic.getInstance(), m, b);
    }

    /**
     * @param m       m value of line
     * @param b       b value of line
     * @param context context of {@link BigDecimalArithmetic}
     * @see Line#Line(AbstractArithmetic, Number, Number)
     */
    public BigDecimalLine(BigDecimal m, BigDecimal b, MathContext context) {
        super(new BigDecimalArithmetic(context), m, b);
    }

    // endregion
}
