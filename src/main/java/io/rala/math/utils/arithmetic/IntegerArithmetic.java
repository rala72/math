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
}
