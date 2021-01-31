package io.rala.math.arithmetic.core;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.exception.NotSupportedException;

/**
 * class which handles {@link Double} arithmetic
 *
 * @since 1.0.0
 */
public class DoubleArithmetic extends AbstractArithmetic<Double> {
    // region singleton

    private static DoubleArithmetic instance;

    /**
     * @return default instance
     * @since 1.0.0
     */
    public static DoubleArithmetic getInstance() {
        if (instance == null) instance = new DoubleArithmetic();
        return instance;
    }

    // endregion

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

    // region power and root

    @Override
    public Double power(Double a, int b) {
        return Math.pow(a, b);
    }

    @Override
    public Double root(Double a, int b) {
        return MathX.root(a, b);
    }

    // endregion

    // region gcd

    @Override
    public Double gcd(Double a, Double b) {
        throw new NotSupportedException();
    }

    // endregion
}
