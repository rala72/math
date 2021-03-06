package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.geometry.Line;

/**
 * class which holds a line in a 2d area with m &amp; b
 * storing {@link Double}<br>
 * {@code y=m*x+b}<br>
 * if line is vertical m is considered to be {@code null}<br>
 * {@code y=b}
 *
 * @since 1.0.0
 */
public class DoubleLine extends Line<Double> {
    // region constructors

    /**
     * @param x x value of line
     * @see Line#Line(AbstractArithmetic, Number)
     * @since 1.0.0
     */
    public DoubleLine(double x) {
        super(DoubleArithmetic.getInstance(), x);
    }

    /**
     * @param m m value of line
     * @param b b value of line
     * @see Line#Line(AbstractArithmetic, Number, Number)
     * @since 1.0.0
     */
    public DoubleLine(Double m, double b) {
        super(DoubleArithmetic.getInstance(), m, b);
    }

    // endregion
}
