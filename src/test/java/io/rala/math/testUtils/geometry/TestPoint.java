package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.Point;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;

public class TestPoint extends Point<Number> {
    public TestPoint() {
        super(new TestAbstractArithmetic());
    }

    public TestPoint(Number xy) {
        super(new TestAbstractArithmetic(), xy);
    }

    public TestPoint(Number x, Number y) {
        super(new TestAbstractArithmetic(), x, y);
    }
}
