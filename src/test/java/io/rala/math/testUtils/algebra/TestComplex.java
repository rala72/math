package io.rala.math.testUtils.algebra;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;

public class TestComplex extends Complex<Number> {
    public TestComplex() {
        super(new TestAbstractArithmetic());
    }

    public TestComplex(Number re, Number im) {
        super(new TestAbstractArithmetic(), re, im);
    }

    public TestComplex(Complex<Number> complex) {
        super(complex);
    }
}
