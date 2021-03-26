package io.rala.math.arithmetic.core;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;
import org.jetbrains.annotations.NotNull;

/**
 * class which handles {@link Long} arithmetic
 *
 * @since 1.0.0
 */
public class LongArithmetic extends AbstractArithmetic<Long> {
    // region singleton

    private static LongArithmetic instance;

    /**
     * @return default instance
     * @since 1.0.0
     */
    @NotNull
    public static LongArithmetic getInstance() {
        if (instance == null) instance = new LongArithmetic();
        return instance;
    }

    // endregion

    // region fromInt, fromDouble and signum

    @Override
    @NotNull
    public Long fromInt(int a) {
        return (long) a;
    }

    @Override
    @NotNull
    public Long fromDouble(double a) {
        return (long) a;
    }

    @Override
    public double signum(@NotNull Long a) {
        return Math.signum(a);
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Override
    @NotNull
    public Long sum(@NotNull Long a, @NotNull Long b) {
        return a + b;
    }

    @Override
    @NotNull
    public Long difference(@NotNull Long a, @NotNull Long b) {
        return a - b;
    }

    @Override
    @NotNull
    public Long product(@NotNull Long a, @NotNull Long b) {
        return a * b;
    }

    @Override
    @NotNull
    public Long quotient(@NotNull Long a, @NotNull Long b) {
        return a / b;
    }

    @Override
    @NotNull
    public Long modulo(@NotNull Long a, @NotNull Long b) {
        return a % b;
    }

    // endregion

    // region power and root

    @Override
    @NotNull
    public Long power(@NotNull Long a, int b) {
        return (long) Math.pow(a, b);
    }

    @Override
    @NotNull
    public Long root(@NotNull Long a, int b) {
        return (long) MathX.root(a, b);
    }

    // endregion

    // region gcd

    @Override
    @NotNull
    public Long gcd(@NotNull Long a, @NotNull Long b) {
        return MathX.gcd(a, b);
    }

    // endregion
}
