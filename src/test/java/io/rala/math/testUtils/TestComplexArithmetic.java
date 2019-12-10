package io.rala.math.testUtils;

import io.rala.math.arithmetic.ComplexArithmetic;

@SuppressWarnings("unused")
public class TestComplexArithmetic extends ComplexArithmetic<Number> {
    public TestComplexArithmetic() {
        super(new TestAbstractArithmetic());
    }
}
