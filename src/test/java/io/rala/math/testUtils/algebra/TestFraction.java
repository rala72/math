package io.rala.math.testUtils.algebra;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.testUtils.arithmetic.TestAbstractResultArithmetic;

@SuppressWarnings("unused")
public class TestFraction extends Fraction<Number, Number> {
    public TestFraction(Number numerator) {
        super(new TestAbstractResultArithmetic(), numerator);
    }

    public TestFraction(Number numerator, Number denominator) {
        super(new TestAbstractResultArithmetic(), numerator, denominator);
    }

    public TestFraction(Fraction<Number, Number> fraction) {
        super(fraction);
    }
}
