package io.rala.math.utils.arithmetic;

/**
 * class which handles {@link Long} arithmetic
 */
@SuppressWarnings("unused")
public class LongArithmetic extends AbstractArithmetic<Long> {
    @Override
    public Long fromInt(int a) {
        return (long) a;
    }

    @Override
    public double signum(Long a) {
        return Math.signum(a);
    }

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
    public Long exponent(Long a, int b) {
        return (long) Math.pow(a, b);
    }

    @Override
    public Long root2(Long a) {
        return (long) Math.sqrt(a);
    }
}
