package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;

public class TestVector extends Vector<Number> {
    public TestVector() {
        super(new TestAbstractArithmetic());
    }

    public TestVector(Number xy) {
        super(new TestAbstractArithmetic(), xy);
    }

    public TestVector(Number x, Number y) {
        super(new TestAbstractArithmetic(), x, y);
    }
}
