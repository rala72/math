package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.geometry.Vector;

/**
 * class which holds a vector in a 2d area with x &amp; y
 * storing {@link Double}
 *
 * @since 1.0.0
 */
public class DoubleVector extends Vector<Double> {
    // region constructors

    /**
     * @see Vector#Vector(AbstractArithmetic)
     * @since 1.0.0
     */
    public DoubleVector() {
        super(DoubleArithmetic.getInstance());
    }

    /**
     * @param xy value to be used in
     *           {@link Vector#Vector(AbstractArithmetic, Number, Number)} at x and y
     * @see Vector#Vector(AbstractArithmetic, Number)
     * @since 1.0.0
     */
    public DoubleVector(double xy) {
        super(DoubleArithmetic.getInstance(), xy);
    }

    /**
     * @param x x value of vector
     * @param y y value of vector
     * @see Vector#Vector(AbstractArithmetic, Number, Number)
     * @since 1.0.0
     */
    public DoubleVector(double x, double y) {
        super(DoubleArithmetic.getInstance(), x, y);
    }

    // endregion
}
