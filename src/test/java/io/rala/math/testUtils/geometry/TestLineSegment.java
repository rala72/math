package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;

public class TestLineSegment extends LineSegment<Number> {
    public TestLineSegment(Point<Number> b) {
        super(new TestAbstractArithmetic(), b);
    }

    public TestLineSegment(Point<Number> a, Point<Number> b) {
        super(new TestAbstractArithmetic(), a, b);
    }
}
