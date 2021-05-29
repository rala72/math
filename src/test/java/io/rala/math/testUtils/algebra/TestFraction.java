package io.rala.math.testUtils.algebra;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.testUtils.arithmetic.TestAbstractResultArithmetic;
import org.jetbrains.annotations.NotNull;

public class TestFraction extends Fraction<Number, Number> {
    public TestFraction(@NotNull Number numerator) {
        super(new TestAbstractResultArithmetic(), numerator);
    }

    public TestFraction(@NotNull Number numerator, @NotNull Number denominator) {
        super(new TestAbstractResultArithmetic(), numerator, denominator);
    }

    public TestFraction(@NotNull Fraction<Number, Number> fraction) {
        super(fraction);
    }
}
