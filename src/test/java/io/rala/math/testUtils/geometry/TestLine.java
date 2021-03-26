package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.Line;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestLine extends Line<Number> {
    public TestLine(@NotNull Number x) {
        super(new TestAbstractArithmetic(), x);
    }

    public TestLine(@Nullable Number m, @NotNull Number b) {
        super(new TestAbstractArithmetic(), m, b);
    }
}
