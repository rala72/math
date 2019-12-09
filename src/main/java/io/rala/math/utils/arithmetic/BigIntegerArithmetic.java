package io.rala.math.utils.arithmetic;

import io.rala.math.MathX;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * class which handles {@link BigInteger} arithmetic
 */
@SuppressWarnings("unused")
public class BigIntegerArithmetic extends AbstractArithmetic<BigInteger> {
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

    // region sum, difference, product and quotient

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

    // endregion

    // region exponent and root

    @Override
    public BigInteger exponent(BigInteger a, int b) {
        return a.pow(b);
    }

    @Override
    public BigInteger root(BigInteger a, int b) {
        return MathX.root(new BigDecimal(a), b).toBigInteger();
    }

    // endregion
}
