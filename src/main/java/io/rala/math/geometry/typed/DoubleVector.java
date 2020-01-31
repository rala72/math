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
        super(new DoubleArithmetic());
    }

    /**
     * @param xy value to be used in
     *           {@link Vector#Vector(AbstractArithmetic, Number, Number)} at x and y
     * @see Vector#Vector(AbstractArithmetic, Number)
     */
    public DoubleVector(double xy) {
        super(new DoubleArithmetic(), xy);
    }

    /**
     * @param x x value of vector
     * @param y y value of vector
     * @see Vector#Vector(AbstractArithmetic, Number, Number)
     */
    public DoubleVector(double x, double y) {
        super(new DoubleArithmetic(), x, y);
    }

    // endregion
}
