package io.rala.math.arithmetic.core;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;
import org.jetbrains.annotations.NotNull;

/**
 * class which handles {@link Integer} arithmetic
 *
 * @since 1.0.0
 */
public class IntegerArithmetic extends AbstractArithmetic<Integer> {
    // region singleton

    private static IntegerArithmetic instance;

    /**
     * @return default instance
     * @since 1.0.0
     */
    @NotNull
    public static IntegerArithmetic getInstance() {
        if (instance == null) instance = new IntegerArithmetic();
        return instance;
    }

    // endregion

    // region fromInt, fromDouble and signum

    @Override
    @NotNull
    public Integer fromInt(int a) {
        return a;
    }

    @Override
    @NotNull
    public Integer fromDouble(double a) {
        return (int) a;
    }

    @Override
    public double signum(@NotNull Integer a) {
        return Math.signum(a);
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Override
    @NotNull
    public Integer sum(@NotNull Integer a, @NotNull Integer b) {
        return a + b;
    }

    @Override
    @NotNull
    public Integer difference(@NotNull Integer a, @NotNull Integer b) {
        return a - b;
    }

    @Override
    @NotNull
    public Integer product(@NotNull Integer a, @NotNull Integer b) {
        return a * b;
    }

    @Override
    @NotNull
    public Integer quotient(@NotNull Integer a, @NotNull Integer b) {
        return a / b;
    }

    @Override
    @NotNull
    public Integer modulo(@NotNull Integer a, @NotNull Integer b) {
        return a % b;
    }

    // endregion

    // region power and root

    @Override
    @NotNull
    public Integer power(@NotNull Integer a, int b) {
        return Math.toIntExact((long) Math.pow(a, b));
    }

    @Override
    @NotNull
    public Integer root(@NotNull Integer a, int b) {
        return (int) MathX.root(a, b);
    }

    // endregion

    // region gcd

    @Override
    @NotNull
    public Integer gcd(@NotNull Integer a, @NotNull Integer b) {
        return MathX.gcd(a, b);
    }

    // endregion
}
