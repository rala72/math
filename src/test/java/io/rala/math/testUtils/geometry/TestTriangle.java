package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.Point;
import io.rala.math.geometry.Triangle;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;

public class TestTriangle extends Triangle<Number> {
    public TestTriangle(Point<Number> a, Point<Number> b, Point<Number> c) {
        super(new TestAbstractArithmetic(), a, b, c);
    }
}
