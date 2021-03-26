package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a circle a in 2d area with center &amp; radius
 * storing {@link BigDecimal}
 *
 * @since 1.0.0
 */
public class BigDecimalCircle extends Circle<BigDecimal> {
    // region constructors

    /**
     * @see Circle#Circle(AbstractArithmetic)
     * @since 1.0.0
     */
    public BigDecimalCircle() {
        super(BigDecimalArithmetic.getInstance());
    }

    /**
     * @param context context of {@link BigDecimalArithmetic}
     * @see Circle#Circle(AbstractArithmetic)
     * @since 1.0.0
     */
    public BigDecimalCircle(@NotNull MathContext context) {
        super(new BigDecimalArithmetic(context));
    }

    /**
     * @param center center point of circle
     * @see Circle#Circle(AbstractArithmetic, Point)
     * @since 1.0.0
     */
    public BigDecimalCircle(@NotNull Point<BigDecimal> center) {
        super(BigDecimalArithmetic.getInstance(), center);
    }

    /**
     * @param center  center point of circle
     * @param context context of {@link BigDecimalArithmetic}
     * @see Circle#Circle(AbstractArithmetic, Point)
     * @since 1.0.0
     */
    public BigDecimalCircle(@NotNull Point<BigDecimal> center, @NotNull MathContext context) {
        super(new BigDecimalArithmetic(context), center);
    }

    /**
     * @param radius radius of circle
     * @see Circle#Circle(AbstractArithmetic, Number)
     * @since 1.0.0
     */
    public BigDecimalCircle(@NotNull BigDecimal radius) {
        super(BigDecimalArithmetic.getInstance(), radius);
    }

    /**
     * @param radius  radius of circle
     * @param context context of {@link BigDecimalArithmetic}
     * @see Circle#Circle(AbstractArithmetic, Number)
     * @since 1.0.0
     */
    public BigDecimalCircle(@NotNull BigDecimal radius, @NotNull MathContext context) {
        super(new BigDecimalArithmetic(context), radius);
    }

    /**
     * @param center center point of circle
     * @param point  point on circle
     * @see Circle#Circle(AbstractArithmetic, Point, Point)
     * @since 1.0.0
     */
    public BigDecimalCircle(@NotNull Point<BigDecimal> center, @NotNull Point<BigDecimal> point) {
        super(BigDecimalArithmetic.getInstance(), center, point);
    }

    /**
     * @param center  center point of circle
     * @param point   point on circle
     * @param context context of {@link BigDecimalArithmetic}
     * @see Circle#Circle(AbstractArithmetic, Point, Point)
     * @since 1.0.0
     */
    public BigDecimalCircle(
        @NotNull Point<BigDecimal> center, @NotNull Point<BigDecimal> point,
        @NotNull MathContext context
    ) {
        super(new BigDecimalArithmetic(context), center, point);
    }

    /**
     * @param center center point of circle
     * @param radius radius of circle
     * @see Circle#Circle(AbstractArithmetic, Point, Number)
     * @since 1.0.0
     */
    public BigDecimalCircle(@NotNull Point<BigDecimal> center, @NotNull BigDecimal radius) {
        super(BigDecimalArithmetic.getInstance(), center, radius);
    }

    /**
     * @param center  center point of circle
     * @param radius  radius of circle
     * @param context context of {@link BigDecimalArithmetic}
     * @see Circle#Circle(AbstractArithmetic, Point, Number)
     * @since 1.0.0
     */
    public BigDecimalCircle(
        @NotNull Point<BigDecimal> center, @NotNull BigDecimal radius,
        @NotNull MathContext context
    ) {
        super(new BigDecimalArithmetic(context), center, radius);
    }

    // endregion
}
