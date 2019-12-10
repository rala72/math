package io.rala.math.arithmetic.core;

import io.rala.math.algebra.Complex;
import io.rala.math.testUtils.TestComplexArithmetic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComplexArithmeticTest {
    private ComplexArithmetic<Number> arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new TestComplexArithmetic();
    }

    // region fromInt, fromDouble signum, negate and compare

    @Test
    void fromInt0() {
        Assertions.assertEquals(create(), arithmetic.fromInt(0));
    }

    @Test
    void fromDouble0() {
        Assertions.assertEquals(create(0d, 0d), arithmetic.fromDouble(0));
    }

    @Test
    void signum() {
        Assertions.assertEquals(1, arithmetic.signum(create(1, 1)));
    }

    @Test
    void negate() {
        Assertions.assertEquals(create(-1, -1), arithmetic.negate(create(1, 1)));
    }

    @Test
    void compare() {
        Assertions.assertEquals(
            0, arithmetic.compare(create(1, 1), create(1, 1))
        );
        Assertions.assertEquals(
            -1, arithmetic.compare(create(1, 1), create(2, 2))
        );
        Assertions.assertEquals(
            1, arithmetic.compare(create(2, 2), create(1, 1))
        );
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        Assertions.assertEquals(
            create(3, 3),
            arithmetic.sum(create(1, 1), create(2, 2))
        );
    }

    @Test
    void sum123() {
        Assertions.assertEquals(
            create(6, 6),
            arithmetic.sum(
                create(1, 1),
                create(2, 2),
                create(3, 3)
            )
        );
    }

    @Test
    void difference12() {
        Assertions.assertEquals(
            create(-1, -1),
            arithmetic.difference(create(1, 1), create(2, 2))
        );
    }

    @Test
    void product12() {
        Assertions.assertEquals(
            create(0, 4),
            arithmetic.product(create(1, 1), create(2, 2))
        );
    }

    @Test
    void product123() {
        Assertions.assertEquals(
            create(-12, 12),
            arithmetic.product(create(1, 1), create(2, 2), create(3, 3))
        );
    }

    @Test
    void quotient12() {
        Assertions.assertEquals(
            create(0.5, 0d),
            arithmetic.quotient(create(1, 1), create(2, 2))
        );
    }

    @Test
    void modulo12() {
        Assertions.assertEquals(
            create(1, 1),
            arithmetic.modulo(create(1, 1), create(2, 2))
        );
    }

    // endregion

    // region exponent and root

    @Test
    void exponent12() {
        Assertions.assertEquals(
            create(2, 0),
            arithmetic.exponent(create(1, 1), 2)
        );
    }

    @Test
    void root21() {
        Assertions.assertEquals(
            create(1, 0),
            arithmetic.root2(create(1, 1))
        );
    }

    // endregion

    // region isFinite, isInfinite and isNaN

    @Test
    void isFinite() {
        Assertions.assertTrue(arithmetic.isFinite(create(0, 0)));
        Assertions.assertFalse(arithmetic.isFinite(
            create(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        ));
        Assertions.assertFalse(arithmetic.isFinite(
            create(Double.NaN, Double.NaN)
        ));
    }

    @Test
    void isInfinite() {
        Assertions.assertFalse(arithmetic.isInfinite(create(0, 0)));
        Assertions.assertTrue(arithmetic.isInfinite(
            create(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        ));
        Assertions.assertFalse(arithmetic.isInfinite(
            create(Double.NaN, Double.NaN)
        ));
    }

    @Test
    void isNaN() {
        Assertions.assertFalse(arithmetic.isNaN(create(0, 0)));
        Assertions.assertFalse(arithmetic.isNaN(
            create(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        ));
        Assertions.assertTrue(arithmetic.isNaN(
            create(Double.NaN, Double.NaN)
        ));
    }

    // endregion

    // region trigonometry

    @Test
    void sinOf1() {
        Assertions.assertEquals(
            create(Math.sin(1), 0d),
            arithmetic.sin(create(1, 1))
        );
    }

    @Test
    void cosOf1() {
        Assertions.assertEquals(
            create(Math.cos(1), 0d),
            arithmetic.cos(create(1, 1))
        );
    }

    @Test
    void tanOf1() {
        Assertions.assertEquals(
            create(Math.tan(1), 0d),
            arithmetic.tan(create(1, 1))
        );
    }

    @Test
    void asinOf1() {
        Assertions.assertEquals(
            create(Math.asin(1), 0d),
            arithmetic.asin(create(1, 1))
        );
    }

    @Test
    void acosOf1() {
        Assertions.assertEquals(
            create(Math.acos(1), 0d),
            arithmetic.acos(create(1, 1))
        );
    }

    @Test
    void atanOf1() {
        Assertions.assertEquals(
            create(Math.atan(1), 0d),
            arithmetic.atan(create(1, 1))
        );
    }

    @Test
    void sinhOf1() {
        Assertions.assertEquals(
            create(Math.sinh(1), 0d),
            arithmetic.sinh(create(1, 1))
        );
    }

    @Test
    void coshOf1() {
        Assertions.assertEquals(
            create(Math.cosh(1), 0d),
            arithmetic.cosh(create(1, 1))
        );
    }

    @Test
    void tanhOf1() {
        Assertions.assertEquals(
            create(Math.tanh(1), 0d),
            arithmetic.tanh(create(1, 1))
        );
    }

    // endregion


    // region create

    private Complex<Number> create() {
        return new Complex<>(arithmetic.getArithmetic());
    }

    private Complex<Number> create(Number a, Number b) {
        return new Complex<>(arithmetic.getArithmetic(), a, b);
    }

    // endregion
}
