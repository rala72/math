package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.Line;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;

public class TestLine extends Line<Number> {
    public TestLine(Number x) {
        super(new TestAbstractArithmetic(), x);
    }

    public TestLine(Number m, Number b) {
        super(new TestAbstractArithmetic(), m, b);
    }
}
