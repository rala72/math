package io.rala.math.testUtils.algebra;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestComplex extends Complex<Number> {
    public TestComplex() {
        super(new TestAbstractArithmetic());
    }

    public TestComplex(@NotNull Number re) {
        super(new TestAbstractArithmetic(), re);
    }

    public TestComplex(@NotNull Number re, @Nullable Number im) {
        super(new TestAbstractArithmetic(), re, im);
    }

    public TestComplex(@NotNull Complex<Number> complex) {
        super(complex);
    }
}
