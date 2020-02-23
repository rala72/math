package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.geometry.Point;

/**
 * class which holds a point in a 2d area with x &amp; y
 * storing {@link Double}
 */
public class DoublePoint extends Point<Double> {
    // region constructors

    /**
     * @see Point#Point(AbstractArithmetic)
     */
    public DoublePoint() {
        super(DoubleArithmetic.getInstance());
    }

    /**
     * @param xy value to be used in
     *           {@link Point#Point(AbstractArithmetic, Number, Number)} at x and y
     * @see Point#Point(AbstractArithmetic, Number)
     */
    public DoublePoint(double xy) {
        super(DoubleArithmetic.getInstance(), xy);
    }

    /**
     * @param x x value of point
     * @param y y value of point
     * @see Point#Point(AbstractArithmetic, Number, Number)
     */
    public DoublePoint(double x, double y) {
        super(DoubleArithmetic.getInstance(), x, y);
    }

    // endregion
}
