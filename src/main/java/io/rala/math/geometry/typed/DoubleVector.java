package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.geometry.Vector;

/**
 * class which holds a vector in a 2d area with x &amp; y
 * storing {@link Double}
 */
public class DoubleVector extends Vector<Double> {
    // region constructors

    /**
     * @see Vector#Vector(AbstractArithmetic)
     */
    public DoubleVector() {
        super(DoubleArithmetic.getInstance());
    }

    /**
     * @param xy value to be used in
     *           {@link Vector#Vector(AbstractArithmetic, Number, Number)} at x and y
     * @see Vector#Vector(AbstractArithmetic, Number)
     */
    public DoubleVector(double xy) {
        super(DoubleArithmetic.getInstance(), xy);
    }

    /**
     * @param x x value of vector
     * @param y y value of vector
     * @see Vector#Vector(AbstractArithmetic, Number, Number)
     */
    public DoubleVector(double x, double y) {
        super(DoubleArithmetic.getInstance(), x, y);
    }

    // endregion
}
