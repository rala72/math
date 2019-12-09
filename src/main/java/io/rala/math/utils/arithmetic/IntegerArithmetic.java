package io.rala.math.utils.arithmetic;

/**
 * class which handles {@link Integer} arithmetic
 */
@SuppressWarnings("unused")
public class IntegerArithmetic extends AbstractArithmetic<Integer> {
    @Override
    public Integer fromInt(int a) {
        return a;
    }

    @Override
    public double signum(Integer a) {
        return Math.signum(a);
    }

    @Override
    public Integer sum(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public Integer difference(Integer a, Integer b) {
        return a - b;
    }

    @Override
    public Integer product(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer quotient(Integer a, Integer b) {
        return a / b;
    }

    @Override
    public Integer exponent(Integer a, int b) {
        return Math.toIntExact((long) Math.pow(a, b));
    }

    @Override
    public Integer root2(Integer a) {
        return (int) Math.sqrt(a);
    }
}
