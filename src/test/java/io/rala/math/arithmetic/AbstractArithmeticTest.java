package io.rala.math.arithmetic;

import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractArithmeticTest {
    private AbstractArithmetic<Number> arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new TestAbstractArithmetic();
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt0() {
        Assertions.assertEquals(0, arithmetic.fromInt(0));
    }

    @Test
    void fromDouble0() {
        Assertions.assertEquals(0d, arithmetic.fromDouble(0));
    }

    @Test
    void signum1() {
        Assertions.assertEquals(1, arithmetic.signum(1));
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        Assertions.assertEquals(1, arithmetic.absolute(-1));
    }

    @Test
    void negate1() {
        Assertions.assertEquals(-1, arithmetic.negate(1));
    }

    @Test
    void compare() {
        Assertions.assertEquals(
            0, arithmetic.compare(1, 1)
        );
        Assertions.assertEquals(
            -1, arithmetic.compare(1, 2)
        );
        Assertions.assertEquals(
            1, arithmetic.compare(2, 1)
        );
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        Assertions.assertEquals(3, arithmetic.sum(1, 2));
    }

    @Test
    void sum123() {
        Assertions.assertEquals(6, arithmetic.sum(1, 2, 3));
    }

    @Test
    void difference12() {
        Assertions.assertEquals(-1, arithmetic.difference(1, 2));
    }

    @Test
    void product12() {
        Assertions.assertEquals(2, arithmetic.product(1, 2));
    }

    @Test
    void product123() {
        Assertions.assertEquals(6, arithmetic.product(1, 2, 3));
    }

    @Test
    void quotient12() {
        Assertions.assertEquals(0.5, arithmetic.quotient(1, 2));
    }

    @Test
    void modulo12() {
        Assertions.assertEquals(1, arithmetic.modulo(1, 2));
    }

    // endregion

    // region exponent and root

    @Test
    void exponent12() {
        Assertions.assertEquals(1, arithmetic.exponent(1, 2));
    }

    @Test
    void root21() {
        Assertions.assertEquals(1d, arithmetic.root2(1));
    }

    // endregion

    // region isFinite, isInfinite and isNaN

    @Test
    void isFinite() {
        Assertions.assertTrue(arithmetic.isFinite(0));
        Assertions.assertFalse(arithmetic.isFinite(Double.POSITIVE_INFINITY));
        Assertions.assertFalse(arithmetic.isFinite(Double.NaN));
    }

    @Test
    void isInfinite() {
        Assertions.assertFalse(arithmetic.isInfinite(0));
        Assertions.assertTrue(arithmetic.isInfinite(Double.POSITIVE_INFINITY));
        Assertions.assertFalse(arithmetic.isInfinite(Double.NaN));
    }

    @Test
    void isNaN() {
        Assertions.assertFalse(arithmetic.isNaN(0));
        Assertions.assertFalse(arithmetic.isNaN(Double.POSITIVE_INFINITY));
        Assertions.assertTrue(arithmetic.isNaN(Double.NaN));
    }

    // endregion

    // region trigonometry

    @Test
    void sinOf1() {
        Assertions.assertEquals(Math.sin(1), arithmetic.sin(1));
    }

    @Test
    void cosOf1() {
        Assertions.assertEquals(Math.cos(1), arithmetic.cos(1));
    }

    @Test
    void tanOf1() {
        Assertions.assertEquals(Math.tan(1), arithmetic.tan(1));
    }

    @Test
    void asinOf1() {
        Assertions.assertEquals(Math.asin(1), arithmetic.asin(1));
    }

    @Test
    void acosOf1() {
        Assertions.assertEquals(Math.acos(1), arithmetic.acos(1));
    }

    @Test
    void atanOf1() {
        Assertions.assertEquals(Math.atan(1), arithmetic.atan(1));
    }

    @Test
    void sinhOf1() {
        Assertions.assertEquals(Math.sinh(1), arithmetic.sinh(1));
    }

    @Test
    void coshOf1() {
        Assertions.assertEquals(Math.cosh(1), arithmetic.cosh(1));
    }

    @Test
    void tanhOf1() {
        Assertions.assertEquals(Math.tanh(1), arithmetic.tanh(1));
    }

    // endregion

    // region toResultArithmetic

    @Test
    void toResultArithmetic() {
        AbstractResultArithmetic<Number, Number> resultArithmetic =
            arithmetic.toResultArithmetic();
        Assertions.assertEquals(arithmetic, resultArithmetic.getTArithmetic());
        Assertions.assertEquals(arithmetic, resultArithmetic.getRArithmetic());
        Assertions.assertEquals(0, resultArithmetic.fromT(0));
        Assertions.assertEquals(-0d, resultArithmetic.fromT(-0d));
    }

    // endregion

    @Test
    void notImplementedException() {
        Assertions.assertThrows(AbstractArithmetic.NotImplementedException.class, () -> {
            throw new AbstractArithmetic.NotImplementedException();
        });
    }
}
