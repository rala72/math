package io.rala.math.testUtils;

import io.rala.math.utils.arithmetic.AbstractArithmetic;

@SuppressWarnings("unused")
public class TestAbstractArithmetic extends AbstractArithmetic<Integer> {
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
        throw new NotImplementedException();
    }
}
