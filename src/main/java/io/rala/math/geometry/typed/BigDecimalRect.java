package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Rect;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a rect in 2d area with point a, b &amp; size
 * storing {@link BigDecimal}
 *
 * @since 1.0.0
 */
public class BigDecimalRect extends Rect<BigDecimal> {
    // region constructors

    /**
     * @param height height of rect
     * @param width  width of rect
     * @see Rect#Rect(AbstractArithmetic, Number, Number)
     * @since 1.0.0
     */
    public BigDecimalRect(@NotNull BigDecimal height, @NotNull BigDecimal width) {
        super(BigDecimalArithmetic.getInstance(), height, width);
    }

    /**
     * @param height  height of rect
     * @param width   width of rect
     * @param context context of {@link BigDecimalArithmetic}
     * @see Rect#Rect(AbstractArithmetic, Number, Number)
     * @since 1.0.0
     */
    public BigDecimalRect(
        @NotNull BigDecimal height, @NotNull BigDecimal width, @NotNull MathContext context
    ) {
        super(new BigDecimalArithmetic(context), height, width);
    }

    /**
     * @param a    a of rect
     * @param b    b of rect
     * @param size height of rect
     * @see Rect#Rect(AbstractArithmetic, Point, Point, Number)
     * @since 1.0.0
     */
    public BigDecimalRect(
        @NotNull Point<BigDecimal> a, @NotNull Point<BigDecimal> b, @NotNull BigDecimal size
    ) {
        super(BigDecimalArithmetic.getInstance(), a, b, size);
    }

    /**
     * @param a       a of rect
     * @param b       b of rect
     * @param size    height of rect
     * @param context context of {@link BigDecimalArithmetic}
     * @see Rect#Rect(AbstractArithmetic, Point, Point, Number)
     * @since 1.0.0
     */
    public BigDecimalRect(
        @NotNull Point<BigDecimal> a, @NotNull Point<BigDecimal> b,
        @NotNull BigDecimal size, @NotNull MathContext context
    ) {
        super(new BigDecimalArithmetic(context), a, b, size);
    }

    // endregion
}
