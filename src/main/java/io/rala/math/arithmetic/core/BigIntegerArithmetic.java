package io.rala.math.arithmetic.core;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * class which handles {@link BigInteger} arithmetic
 *
 * @since 1.0.0
 */
public class BigIntegerArithmetic extends AbstractArithmetic<BigInteger> {
    // region singleton

    private static BigIntegerArithmetic instance;

    /**
     * @return default instance
     * @since 1.0.0
     */
    @NotNull
    public static BigIntegerArithmetic getInstance() {
        if (instance == null) instance = new BigIntegerArithmetic();
        return instance;
    }

    // endregion

    // region fromInt, fromDouble and signum

    @Override
    @NotNull
    public BigInteger fromInt(int a) {
        return BigInteger.valueOf(a);
    }

    @Override
    @NotNull
    public BigInteger fromDouble(double a) {
        return BigInteger.valueOf((long) a);
    }

    @Override
    public double signum(@NotNull BigInteger a) {
        return a.signum();
    }

    // endregion

    // region absolute, negate and compare

    @Override
    @NotNull
    public BigInteger absolute(@NotNull BigInteger a) {
        return a.abs();
    }

    @Override
    @NotNull
    public BigInteger negate(@NotNull BigInteger a) {
        return a.negate();
    }

    @Override
    public int compare(@NotNull BigInteger a, @NotNull BigInteger b) {
        return a.compareTo(b);
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Override
    @NotNull
    public BigInteger sum(@NotNull BigInteger a, @NotNull BigInteger b) {
        return a.add(b);
    }

    @Override
    @NotNull
    public BigInteger difference(@NotNull BigInteger a, @NotNull BigInteger b) {
        return a.subtract(b);
    }

    @Override
    @NotNull
    public BigInteger product(@NotNull BigInteger a, @NotNull BigInteger b) {
        return a.multiply(b);
    }

    @Override
    @NotNull
    public BigInteger quotient(@NotNull BigInteger a, @NotNull BigInteger b) {
        return a.divide(b);
    }

    @Override
    @NotNull
    public BigInteger modulo(@NotNull BigInteger a, @NotNull BigInteger b) {
        return a.mod(b);
    }

    // endregion

    // region power and root

    @Override
    @NotNull
    public BigInteger power(@NotNull BigInteger a, int b) {
        return a.pow(b);
    }

    @Override
    @NotNull
    public BigInteger root(@NotNull BigInteger a, int b) {
        return MathX.root(new BigDecimal(a), b).toBigInteger();
    }

    // endregion

    // region gcd

    @Override
    @NotNull
    public BigInteger gcd(@NotNull BigInteger a, @NotNull BigInteger b) {
        return MathX.gcd(a, b);
    }

    // endregion
}
