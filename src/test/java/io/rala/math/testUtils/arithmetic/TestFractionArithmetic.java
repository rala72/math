package io.rala.math.testUtils.arithmetic;

import io.rala.math.arithmetic.core.FractionArithmetic;

public class TestFractionArithmetic extends FractionArithmetic<Number, Number> {
    public TestFractionArithmetic() {
        super(new TestAbstractResultArithmetic());
    }
}
