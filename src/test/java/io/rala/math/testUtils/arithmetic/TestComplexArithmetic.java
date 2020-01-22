package io.rala.math.testUtils.arithmetic;

import io.rala.math.arithmetic.core.ComplexArithmetic;

public class TestComplexArithmetic extends ComplexArithmetic<Number> {
    public TestComplexArithmetic() {
        super(new TestAbstractArithmetic());
    }
}
