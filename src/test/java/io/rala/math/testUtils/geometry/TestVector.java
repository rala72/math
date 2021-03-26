package io.rala.math.testUtils.geometry;

import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.jetbrains.annotations.NotNull;

public class TestVector extends Vector<Number> {
    public TestVector() {
        super(new TestAbstractArithmetic());
    }

    public TestVector(@NotNull Number xy) {
        super(new TestAbstractArithmetic(), xy);
    }

    public TestVector(@NotNull Number x, @NotNull Number y) {
        super(new TestAbstractArithmetic(), x, y);
    }
}
