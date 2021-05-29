package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.Point;
import io.rala.math.geometry.Triangle;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.jetbrains.annotations.NotNull;

public class TestTriangle extends Triangle<Number> {
    public TestTriangle(@NotNull Point<Number> a, @NotNull Point<Number> b, @NotNull Point<Number> c) {
        super(new TestAbstractArithmetic(), a, b, c);
    }
}
