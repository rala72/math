package io.rala.math.testUtils.arithmetic;

import io.rala.math.arithmetic.AbstractResultArithmetic;

public class TestAbstractResultArithmetic extends AbstractResultArithmetic<Number, Number> {
    public TestAbstractResultArithmetic() {
        super(new TestAbstractArithmetic(), new TestAbstractArithmetic());
    }

    @Override
    public Number fromT(Number a) {
        return a;
    }
}
