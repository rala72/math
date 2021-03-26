package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.jetbrains.annotations.NotNull;

public class TestLineSegment extends LineSegment<Number> {
    public TestLineSegment(@NotNull Point<Number> b) {
        super(new TestAbstractArithmetic(), b);
    }

    public TestLineSegment(@NotNull Point<Number> a, @NotNull Point<Number> b) {
        super(new TestAbstractArithmetic(), a, b);
    }
}
