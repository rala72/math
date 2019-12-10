package io.rala.math.testUtils;

import io.rala.math.arithmetic.AbstractResultArithmetic;

@SuppressWarnings("unused")
public class TestAbstractResultArithmetic extends AbstractResultArithmetic<Number, Number> {
    public TestAbstractResultArithmetic() {
        super(new TestAbstractArithmetic(), new TestAbstractArithmetic());
    }

    @Override
    public Number fromT(Number a) {
        return a;
    }
}
