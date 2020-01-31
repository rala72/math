package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.Point;
import io.rala.math.geometry.Rect;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;

public class TestRect extends Rect<Number> {
    public TestRect(Number height, Number width) {
        super(new TestAbstractArithmetic(), height, width);
    }

    public TestRect(Point<Number> a, Point<Number> b, Number size) {
        super(new TestAbstractArithmetic(), a, b, size);
    }
}
