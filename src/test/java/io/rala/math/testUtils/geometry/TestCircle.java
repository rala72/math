package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.jetbrains.annotations.NotNull;

public class TestCircle extends Circle<Number> {
    public TestCircle() {
        super(new TestAbstractArithmetic());
    }

    public TestCircle(@NotNull Point<Number> center) {
        super(new TestAbstractArithmetic(), center);
    }

    public TestCircle(@NotNull Number radius) {
        super(new TestAbstractArithmetic(), radius);
    }

    public TestCircle(@NotNull Point<Number> center, @NotNull Point<Number> point) {
        super(new TestAbstractArithmetic(), center, point);
    }

    public TestCircle(@NotNull Point<Number> center, @NotNull Number radius) {
        super(new TestAbstractArithmetic(), center, radius);
    }
}
