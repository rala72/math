package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Triangle;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a triangle in a 2d area with points a, b &amp; c
 * storing {@link BigDecimal}
 *
 * @since 1.0.0
 */
public class BigDecimalTriangle extends Triangle<BigDecimal> {
    // region constructors

    /**
     * @param a a of triangle
     * @param b b of triangle
     * @param c c of triangle
     * @see Triangle#Triangle(AbstractArithmetic, Point, Point, Point)
     * @since 1.0.0
     */
    public BigDecimalTriangle(
        @NotNull Point<BigDecimal> a, @NotNull Point<BigDecimal> b, @NotNull Point<BigDecimal> c
    ) {
        super(BigDecimalArithmetic.getInstance(), a, b, c);
    }

    /**
     * @param a       a of triangle
     * @param b       b of triangle
     * @param c       c of triangle
     * @param context context of {@link BigDecimalArithmetic}
     * @see Triangle#Triangle(AbstractArithmetic, Point, Point, Point)
     * @since 1.0.0
     */
    public BigDecimalTriangle(
        @NotNull Point<BigDecimal> a, @NotNull Point<BigDecimal> b,
        @NotNull Point<BigDecimal> c, @NotNull MathContext context
    ) {
        super(new BigDecimalArithmetic(context), a, b, c);
    }

    // endregion
}
