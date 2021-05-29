package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.Point;
import io.rala.math.geometry.Rect;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.jetbrains.annotations.NotNull;

public class TestRect extends Rect<Number> {
    public TestRect(@NotNull Number height, @NotNull Number width) {
        super(new TestAbstractArithmetic(), height, width);
    }

    public TestRect(@NotNull Point<Number> a, @NotNull Point<Number> b, @NotNull Number size) {
        super(new TestAbstractArithmetic(), a, b, size);
    }
}
