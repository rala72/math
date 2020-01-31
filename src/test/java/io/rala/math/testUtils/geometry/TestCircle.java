package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;

public class TestCircle extends Circle<Number> {
    public TestCircle() {
        super(new TestAbstractArithmetic());
    }

    public TestCircle(Point<Number> center) {
        super(new TestAbstractArithmetic(), center);
    }

    public TestCircle(Number radius) {
        super(new TestAbstractArithmetic(), radius);
    }

    public TestCircle(Point<Number> center, Point<Number> point) {
        super(new TestAbstractArithmetic(), center, point);
    }

    public TestCircle(Point<Number> center, Number radius) {
        super(new TestAbstractArithmetic(), center, radius);
    }
}
