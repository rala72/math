package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.Point;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.jetbrains.annotations.NotNull;

public class TestPoint extends Point<Number> {
    public TestPoint() {
        super(new TestAbstractArithmetic());
    }

    public TestPoint(@NotNull Number xy) {
        super(new TestAbstractArithmetic(), xy);
    }

    public TestPoint(@NotNull Number x, @NotNull Number y) {
        super(new TestAbstractArithmetic(), x, y);
    }
}
