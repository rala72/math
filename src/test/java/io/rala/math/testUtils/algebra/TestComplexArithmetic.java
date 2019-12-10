package io.rala.math.testUtils.algebra;

import io.rala.math.arithmetic.core.ComplexArithmetic;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;

@SuppressWarnings("unused")
public class TestComplexArithmetic extends ComplexArithmetic<Number> {
    public TestComplexArithmetic() {
        super(new TestAbstractArithmetic());
    }
}
