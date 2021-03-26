package io.rala.math.testUtils.arithmetic;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import org.jetbrains.annotations.NotNull;

public class TestAbstractResultArithmetic extends AbstractResultArithmetic<Number, Number> {
    public TestAbstractResultArithmetic() {
        super(new TestAbstractArithmetic(), new TestAbstractArithmetic());
    }

    @Override
    @NotNull
    public Number fromT(@NotNull Number a) {
        return a;
    }
}
