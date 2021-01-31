package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Rect;

/**
 * class which holds a rect in 2d area with point a, b &amp; size
 * storing {@link Double}
 *
 * @since 1.0.0
 */
public class DoubleRect extends Rect<Double> {
    // region constructors

    /**
     * @param height height of rect
     * @param width  width of rect
     * @see Rect#Rect(AbstractArithmetic, Number, Number)
     * @since 1.0.0
     */
    public DoubleRect(double height, double width) {
        super(DoubleArithmetic.getInstance(), height, width);
    }

    /**
     * @param a    a of rect
     * @param b    b of rect
     * @param size height of rect
     * @see Rect#Rect(AbstractArithmetic, Point, Point, Number)
     * @since 1.0.0
     */
    public DoubleRect(Point<Double> a, Point<Double> b, double size) {
        super(DoubleArithmetic.getInstance(), a, b, size);
    }

    // endregion
}
