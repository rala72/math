package io.rala.math.arithmetic.core;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * class which handles {@link BigInteger} arithmetic
 */
public class BigIntegerArithmetic extends AbstractArithmetic<BigInteger> {
    private static BigIntegerArithmetic instance;

    /**
     * @return default instance
     */
    public static BigIntegerArithmetic getInstance() {
        if (instance == null) instance = new BigIntegerArithmetic();
        return instance;
    }

    // region fromInt, fromDouble and signum

    @Override
    public BigInteger fromInt(int a) {
        return BigInteger.valueOf(a);
    }

    @Override
    public BigInteger fromDouble(double a) {
        return BigInteger.valueOf((long) a);
    }

    @Override
    public double signum(BigInteger a) {
        return a.signum();
    }

    // endregion

    // region absolute, negate and compare

    @Override
    public BigInteger absolute(BigInteger a) {
        return a.abs();
    }

    @Override
    public BigInteger negate(BigInteger a) {
        return a.negate();
    }

    @Override
    public int compare(BigInteger a, BigInteger b) {
        return a.compareTo(b);
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Override
    public BigInteger sum(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger difference(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger product(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger quotient(BigInteger a, BigInteger b) {
        return a.divide(b);
    }

    @Override
    public BigInteger modulo(BigInteger a, BigInteger b) {
        return a.mod(b);
    }

    // endregion

    // region power and root

    @Override
    public BigInteger power(BigInteger a, int b) {
        return a.pow(b);
    }

    @Override
    public BigInteger root(BigInteger a, int b) {
        return MathX.root(new BigDecimal(a), b).toBigInteger();
    }

    // endregion

    // region gcd

    @Override
    public BigInteger gcd(BigInteger a, BigInteger b) {
        return MathX.gcd(a, b);
    }

    // endregion
}
