package io.rala.math.testUtils;

import io.rala.math.algebra.Complex;

public class TestComplex extends Complex<Number> {
    public TestComplex() {
        super(new TestAbstractArithmetic());
    }

    public TestComplex(Number re, Number im) {
        super(new TestAbstractArithmetic(), re, im);
    }
}
