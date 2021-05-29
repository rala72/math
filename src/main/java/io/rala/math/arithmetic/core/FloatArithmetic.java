package io.rala.math.arithmetic.core;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.exception.NotSupportedException;
import org.jetbrains.annotations.NotNull;

/**
 * class which handles {@link Float} arithmetic
 *
 * @since 1.0.0
 */
public class FloatArithmetic extends AbstractArithmetic<Float> {
    // region singleton

    private static FloatArithmetic instance;

    /**
     * @return default instance
     * @since 1.0.0
     */
    @NotNull
    public static FloatArithmetic getInstance() {
        if (instance == null) instance = new FloatArithmetic();
        return instance;
    }

    // endregion

    // region fromInt, fromDouble and signum

    @Override
    @NotNull
    public Float fromInt(int a) {
        return (float) a;
    }

    @Override
    @NotNull
    public Float fromDouble(double a) {
        return (float) a;
    }

    @Override
    public double signum(@NotNull Float a) {
        return Math.signum(a);
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Override
    @NotNull
    public Float sum(@NotNull Float a, @NotNull Float b) {
        return a + b;
    }

    @Override
    @NotNull
    public Float difference(@NotNull Float a, @NotNull Float b) {
        return a - b;
    }

    @Override
    @NotNull
    public Float product(@NotNull Float a, @NotNull Float b) {
        return a * b;
    }

    @Override
    @NotNull
    public Float quotient(@NotNull Float a, @NotNull Float b) {
        return a / b;
    }

    @Override
    @NotNull
    public Float modulo(@NotNull Float a, @NotNull Float b) {
        return a % b;
    }

    // endregion

    // region power and root

    @Override
    @NotNull
    public Float power(@NotNull Float a, int b) {
        return (float) Math.pow(a, b);
    }

    @Override
    @NotNull
    public Float root(@NotNull Float a, int b) {
        return (float) MathX.root(a, b);
    }

    // endregion

    // region gcd

    @Override
    @NotNull
    public Float gcd(@NotNull Float a, @NotNull Float b) {
        throw new NotSupportedException();
    }

    // endregion
}
