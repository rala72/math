package io.rala.math.arithmetic.core;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;

/**
 * class which handles {@link Long} arithmetic
 */
@SuppressWarnings("unused")
public class LongArithmetic extends AbstractArithmetic<Long> {
    // region fromInt, fromDouble and signum

    @Override
    public Long fromInt(int a) {
        return (long) a;
    }

    @Override
    public Long fromDouble(double a) {
        return (long) a;
    }

    @Override
    public double signum(Long a) {
        return Math.signum(a);
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Override
    public Long sum(Long a, Long b) {
        return a + b;
    }

    @Override
    public Long difference(Long a, Long b) {
        return a - b;
    }

    @Override
    public Long product(Long a, Long b) {
        return a * b;
    }

    @Override
    public Long quotient(Long a, Long b) {
        return a / b;
    }

    @Override
    public Long modulo(Long a, Long b) {
        return a % b;
    }

    // endregion

    // region power and root

    @Override
    public Long power(Long a, int b) {
        return (long) Math.pow(a, b);
    }

    @Override
    public Long root(Long a, int b) {
        return (long) MathX.root(a, b);
    }

    // endregion

    // region gcd

    @Override
    public Long gcd(Long a, Long b) {
        return MathX.gcd(a, b);
    }

    // endregion
}
