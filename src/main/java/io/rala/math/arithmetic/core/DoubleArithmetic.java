package io.rala.math.arithmetic.core;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.exception.NotSupportedException;
import org.jetbrains.annotations.NotNull;

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
    @NotNull
    public static DoubleArithmetic getInstance() {
        if (instance == null) instance = new DoubleArithmetic();
        return instance;
    }

    // endregion

    // region fromInt, fromDouble and signum

    @Override
    @NotNull
    public Double fromInt(int a) {
        return (double) a;
    }

    @Override
    @NotNull
    public Double fromDouble(double a) {
        return a;
    }

    @Override
    public double signum(@NotNull Double a) {
        return Math.signum(a);
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Override
    @NotNull
    public Double sum(@NotNull Double a, @NotNull Double b) {
        return a + b;
    }

    @Override
    @NotNull
    public Double difference(@NotNull Double a, @NotNull Double b) {
        return a - b;
    }

    @Override
    @NotNull
    public Double product(@NotNull Double a, @NotNull Double b) {
        return a * b;
    }

    @Override
    @NotNull
    public Double quotient(@NotNull Double a, @NotNull Double b) {
        return a / b;
    }

    @Override
    @NotNull
    public Double modulo(@NotNull Double a, @NotNull Double b) {
        return a % b;
    }

    // endregion

    // region power and root

    @Override
    @NotNull
    public Double power(@NotNull Double a, int b) {
        return Math.pow(a, b);
    }

    @Override
    @NotNull
    public Double root(@NotNull Double a, int b) {
        return MathX.root(a, b);
    }

    // endregion

    // region gcd

    @Override
    @NotNull
    public Double gcd(@NotNull Double a, @NotNull Double b) {
        throw new NotSupportedException();
    }

    // endregion
}
