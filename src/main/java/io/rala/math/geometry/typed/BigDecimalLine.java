package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.Line;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a line in a 2d area with m &amp; b
 * storing {@link BigDecimal}<br>
 * {@code y=m*x+b}<br>
 * if line is vertical m is considered to be {@code null}<br>
 * {@code y=b}
 *
 * @since 1.0.0
 */
public class BigDecimalLine extends Line<BigDecimal> {
    // region constructors

    /**
     * @param x x value of line
     * @see Line#Line(AbstractArithmetic, Number)
     * @since 1.0.0
     */
    public BigDecimalLine(@NotNull BigDecimal x) {
        super(BigDecimalArithmetic.getInstance(), x);
    }

    /**
     * @param x       x value of line
     * @param context context of {@link BigDecimalArithmetic}
     * @see Line#Line(AbstractArithmetic, Number)
     * @since 1.0.0
     */
    public BigDecimalLine(@NotNull BigDecimal x, @NotNull MathContext context) {
        super(new BigDecimalArithmetic(context), x);
    }

    /**
     * @param m m value of line
     * @param b b value of line
     * @see Line#Line(AbstractArithmetic, Number, Number)
     * @since 1.0.0
     */
    public BigDecimalLine(@Nullable BigDecimal m, @NotNull BigDecimal b) {
        super(BigDecimalArithmetic.getInstance(), m, b);
    }

    /**
     * @param m       m value of line
     * @param b       b value of line
     * @param context context of {@link BigDecimalArithmetic}
     * @see Line#Line(AbstractArithmetic, Number, Number)
     * @since 1.0.0
     */
    public BigDecimalLine(
        @Nullable BigDecimal m, @NotNull BigDecimal b, @NotNull MathContext context
    ) {
        super(new BigDecimalArithmetic(context), m, b);
    }

    // endregion
}
