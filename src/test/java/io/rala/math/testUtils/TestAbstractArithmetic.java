package io.rala.math.testUtils;

import io.rala.math.utils.AbstractArithmetic;

@SuppressWarnings("unused")
public class TestAbstractArithmetic extends AbstractArithmetic<Integer> {
    @Override
    protected Integer fromInt(int a) {
        return a;
    }

    @Override
    protected Integer sum(Integer a, Integer b) {
        return a + b;
    }

    @Override
    protected Integer difference(Integer a, Integer b) {
        return a - b;
    }

    @Override
    protected Integer product(Integer a, Integer b) {
        return a * b;
    }

    @Override
    protected Integer quotient(Integer a, Integer b) {
        throw new NotImplementedException();
    }
}
