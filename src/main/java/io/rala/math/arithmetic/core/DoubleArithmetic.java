package io.rala.math.arithmetic.core;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;

/**
 * class which handles {@link Double} arithmetic
 */
@SuppressWarnings("unused")
public class DoubleArithmetic extends AbstractArithmetic<Double> {
    // region fromInt, fromDouble and signum

    @Override
    public Double fromInt(int a) {
        return (double) a;
    }

    @Override
    public Double fromDouble(double a) {
        return a;
    }

    @Override
    public double signum(Double a) {
        return Math.signum(a);
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Override
    public Double sum(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double difference(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double product(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double quotient(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double modulo(Double a, Double b) {
        return a % b;
    }

    // endregion

    // region exponent and root

    @Override
    public Double exponent(Double a, int b) {
        return Math.pow(a, b);
    }

    @Override
    public Double root(Double a, int b) {
        return MathX.root(a, b);
    }

    // endregion
}
