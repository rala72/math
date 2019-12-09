package io.rala.math.utils.arithmetic;

import java.math.BigInteger;

/**
 * class which handles {@link BigInteger} arithmetic
 */
@SuppressWarnings("unused")
public class BigIntegerArithmetic extends AbstractArithmetic<BigInteger> {
    @Override
    public BigInteger fromInt(int a) {
        return BigInteger.valueOf(a);
    }

    @Override
    public double signum(BigInteger a) {
        return a.signum();
    }

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
    public BigInteger exponent(BigInteger a, int b) {
        return a.pow(b);
    }

    @Override
    public BigInteger root2(BigInteger a) {
        return a.sqrt();
    }
}
