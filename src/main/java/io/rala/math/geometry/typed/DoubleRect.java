package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Rect;

/**
 * class which holds a rect in 2d area with point a, b &amp; size
 * storing {@link Double}
 */
public class DoubleRect extends Rect<Double> {
    // region constructors

    /**
     * @param height height of rect
     * @param width  width of rect
     * @see Rect#Rect(AbstractArithmetic, Number, Number)
     */
    public DoubleRect(double height, double width) {
        super(new DoubleArithmetic(), height, width);
    }

    /**
     * @param a    a of rect
     * @param b    b of rect
     * @param size height of rect
     * @see Rect#Rect(AbstractArithmetic, Point, Point, Number)
     */
    public DoubleRect(Point<Double> a, Point<Double> b, double size) {
        super(new DoubleArithmetic(), a, b, size);
    }

    // endregion
}
