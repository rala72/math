package io.rala.math.arithmetic.core;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;

/**
 * class which handles {@link Float} arithmetic
 */
@SuppressWarnings("unused")
public class FloatArithmetic extends AbstractArithmetic<Float> {
    // region fromInt, fromDouble and signum

    @Override
    public Float fromInt(int a) {
        return (float) a;
    }

    @Override
    public Float fromDouble(double a) {
        return (float) a;
    }

    @Override
    public double signum(Float a) {
        return Math.signum(a);
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Override
    public Float sum(Float a, Float b) {
        return a + b;
    }

    @Override
    public Float difference(Float a, Float b) {
        return a - b;
    }

    @Override
    public Float product(Float a, Float b) {
        return a * b;
    }

    @Override
    public Float quotient(Float a, Float b) {
        return a / b;
    }

    @Override
    public Float modulo(Float a, Float b) {
        return a % b;
    }

    // endregion

    // region power and root

    @Override
    public Float power(Float a, int b) {
        return (float) Math.pow(a, b);
    }

    @Override
    public Float root(Float a, int b) {
        return (float) MathX.root(a, b);
    }

    // endregion

    // region gcd

    @Override
    public Float gcd(Float a, Float b) {
        throw new NotImplementedException();
    }

    // endregion
}
