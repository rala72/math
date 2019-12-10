package io.rala.math.testUtils;

import io.rala.math.algebra.numeric.Complex;

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
