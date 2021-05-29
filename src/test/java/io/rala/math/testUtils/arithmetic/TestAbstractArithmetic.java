package io.rala.math.testUtils.arithmetic;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;
import org.jetbrains.annotations.NotNull;

public class TestAbstractArithmetic extends AbstractArithmetic<Number> {
    // region fromInt, fromDouble and signum

    @Override
    @NotNull
    public Number fromInt(int a) {
        return fromDouble(a);
    }

    @Override
    @NotNull
    public Number fromDouble(double a) {
        return a;
    }

    @Override
    public double signum(@NotNull Number a) {
        return Math.signum(a.doubleValue());
    }

    // endregion

    // region sum, difference, product and quotient

    @Override
    @NotNull
    public Number sum(@NotNull Number a, @NotNull Number b) {
        return a.doubleValue() + b.doubleValue();
    }

    @Override
    @NotNull
    public Number difference(@NotNull Number a, @NotNull Number b) {
        return a.doubleValue() - b.doubleValue();
    }

    @Override
    @NotNull
    public Number product(@NotNull Number a, @NotNull Number b) {
        return a.doubleValue() * b.doubleValue();
    }

    @Override
    @NotNull
    public Number quotient(@NotNull Number a, @NotNull Number b) {
        return a.doubleValue() / b.doubleValue();
    }

    // endregion

    // region exponent and root

    @Override
    @NotNull
    public Number power(@NotNull Number a, int b) {
        return Math.pow(a.doubleValue(), b);
    }

    @Override
    @NotNull
    public Number root(@NotNull Number a, int b) {
        return MathX.root(a.doubleValue(), b);
    }

    // endregion

    // region gcd

    @Override
    @NotNull
    public Number gcd(@NotNull Number a, @NotNull Number b) {
        return MathX.gcd(a.longValue(), b.longValue());
    }

    // endregion
}
