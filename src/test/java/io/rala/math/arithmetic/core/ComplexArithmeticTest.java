package io.rala.math.arithmetic.core;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.testUtils.arithmetic.TestComplexArithmetic;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComplexArithmeticTest {
    private ComplexArithmetic<Number> arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new TestComplexArithmetic();
    }

    // region fromInt, fromDouble and signum

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

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteReM1Im0() {
        Assertions.assertEquals(create(1d, -0d), arithmetic.absolute(create(-1, 0)));
    }

    @Test
    void negate() {
        Assertions.assertEquals(create(-1d, -1d), arithmetic.negate(create(1, 1)));
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

    @Test
    void min() {
        Assertions.assertEquals(
            create(1, 1), arithmetic.min(create(1, 1), create(1, 1))
        );
        Assertions.assertEquals(
            create(1, 1), arithmetic.min(create(1, 1), create(2, 2))
        );
        Assertions.assertEquals(
            create(1, 1), arithmetic.min(create(2, 2), create(1, 1))
        );
    }

    @Test
    void max() {
        Assertions.assertEquals(
            create(1, 1), arithmetic.max(create(1, 1), create(1, 1))
        );
        Assertions.assertEquals(
            create(2, 2), arithmetic.max(create(1, 1), create(2, 2))
        );
        Assertions.assertEquals(
            create(2, 2), arithmetic.max(create(2, 2), create(1, 1))
        );
    }

    @Test
    void isZero() {
        Assertions.assertTrue(arithmetic.isZero(create(0d, 0d)));
        Assertions.assertTrue(arithmetic.isZero(create(-0d, -0d)));
        Assertions.assertFalse(arithmetic.isZero(create(1d, 1d)));
    }

    @Test
    void isEqual() {
        Assertions.assertTrue(
            arithmetic.isEqual(create(0d, 0d), create(0d, 0d))
        );
        Assertions.assertTrue(
            arithmetic.isEqual(create(-0d, -0d), create(0d, 0d))
        );
        Assertions.assertTrue(
            arithmetic.isEqual(create(-0d, -0d), create(-0d, -0d))
        );
        Assertions.assertFalse(
            arithmetic.isEqual(create(1d, 1d), create(0d, 0d))
        );
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        Assertions.assertEquals(
            create(3d, 3d),
            arithmetic.sum(create(1, 1), create(2, 2))
        );
    }

    @Test
    void sum123() {
        Assertions.assertEquals(
            create(6d, 6d),
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
            create(-1d, -1d),
            arithmetic.difference(create(1, 1), create(2, 2))
        );
    }

    @Test
    void product12() {
        Assertions.assertEquals(
            create(0d, 4d),
            arithmetic.product(create(1, 1), create(2, 2))
        );
    }

    @Test
    void product123() {
        Assertions.assertEquals(
            create(-12d, 12d),
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
            create(0d, 0d),
            arithmetic.modulo(create(1, 1), create(2, 2))
        );
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        Assertions.assertEquals(
            create(-3.2162452993532737e-16, 2.0000000000000004),
            arithmetic.power(create(1, 1), 2)
        );
    }

    @Test
    void root21() {
        Assertions.assertEquals(
            create(1.0986841134678098, 0.45508986056222733),
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

    // region gcd and lcm

    @Test
    void gcd() {
        Assertions.assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.gcd(create(1, 2), create(3, 4))
        ); // assert exception message?
    }

    @Test
    void lcm() {
        Assertions.assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.lcm(create(1, 2), create(3, 4))
        ); // assert exception message?
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

    // region override

    @Test
    void equalsOfArithmetic() {
        Assertions.assertEquals(
            new TestComplexArithmetic(), new TestComplexArithmetic()
        );
    }

    @Test
    void hashCodeOfArithmetic() {
        Assertions.assertEquals(-33521727,
            new TestComplexArithmetic().hashCode()
        );
    }

    @Test
    void toStringOfArithmetic() {
        Assertions.assertEquals("TestComplexArithmetic",
            new TestComplexArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
            new TestComplexArithmetic(),
            TestComplexArithmetic.class
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
