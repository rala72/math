package io.rala.math.testUtils;

import io.rala.math.utils.arithmetic.AbstractArithmetic;

@SuppressWarnings("unused")
public class TestAbstractArithmetic extends AbstractArithmetic<Number> {
    // region fromInt, fromDouble and signum

    @Override
    public Number fromInt(int a) {
        return (double) a;
    }

    @Override
    public Number fromDouble(double a) {
        return a;
    }

    @Override
    public double signum(Number a) {
        return Math.signum(a.intValue());
    }

    // endregion

    // region sum, difference, product and quotient

    @Override
    public Number sum(Number a, Number b) {
        return a.intValue() + b.intValue();
    }

    @Override
    public Number difference(Number a, Number b) {
        return a.intValue() - b.intValue();
    }

    @Override
    public Number product(Number a, Number b) {
        return a.intValue() * b.intValue();
    }

    @Override
    public Number quotient(Number a, Number b) {
        throw new NotImplementedException();
    }

    // endregion

    // region exponent and root

    @Override
    public Number exponent(Number a, int b) {
        return Math.toIntExact((long) Math.pow(a.doubleValue(), b));
    }

    @Override
    public Number root2(Number a) {
        return Math.sqrt(a.doubleValue());
    }

    // endregion
}