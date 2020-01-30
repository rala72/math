package io.rala.math.testUtils.arithmetic;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;

public class TestAbstractArithmetic extends AbstractArithmetic<Number> {
    // region fromInt, fromDouble and signum

    @Override
    public Number fromInt(int a) {
        return fromDouble(a);
    }

    @Override
    public Number fromDouble(double a) {
        return a;
    }

    @Override
    public double signum(Number a) {
        return Math.signum(a.doubleValue());
    }

    // endregion

    // region sum, difference, product and quotient

    @Override
    public Number sum(Number a, Number b) {
        return a.doubleValue() + b.doubleValue();
    }

    @Override
    public Number difference(Number a, Number b) {
        return a.doubleValue() - b.doubleValue();
    }

    @Override
    public Number product(Number a, Number b) {
        return a.doubleValue() * b.doubleValue();
    }

    @Override
    public Number quotient(Number a, Number b) {
        return a.doubleValue() / b.doubleValue();
    }

    // endregion

    // region exponent and root

    @Override
    public Number power(Number a, int b) {
        return Math.toIntExact((long) Math.pow(a.doubleValue(), b));
    }

    @Override
    public Number root(Number a, int b) {
        return MathX.root(a.doubleValue(), b);
    }

    // endregion

    // region gcd

    @Override
    public Number gcd(Number a, Number b) {
        return MathX.gcd(a.longValue(), b.longValue());
    }

    // endregion
}
