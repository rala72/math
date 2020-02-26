package io.rala.math.arithmetic;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class AbstractArithmeticTest {
    private AbstractArithmetic<Number> arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new TestAbstractArithmetic();
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt0() {
        assertEquals(0d, arithmetic.fromInt(0));
    }

    @Test
    void fromDouble0() {
        assertEquals(0d, arithmetic.fromDouble(0));
    }

    @Test
    void signum1() {
        assertEquals(1, arithmetic.signum(1));
    }

    // endregion

    // region number constants

    @Test
    void zero() {
        assertEquals(0d, arithmetic.zero());
    }

    @Test
    void one() {
        assertEquals(1d, arithmetic.one());
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertEquals(1d, arithmetic.absolute(-1));
    }

    @Test
    void negate1() {
        assertEquals(-1d, arithmetic.negate(1));
    }

    @Test
    void compare() {
        assertEquals(0, arithmetic.compare(1, 1));
        assertEquals(-1, arithmetic.compare(1, 2));
        assertEquals(1, arithmetic.compare(2, 1));
    }

    @Test
    void min() {
        assertEquals(1, arithmetic.min(1, 1));
        assertEquals(1, arithmetic.min(1, 2));
        assertEquals(1, arithmetic.min(2, 1));
    }

    @Test
    void max() {
        assertEquals(1, arithmetic.max(1, 1));
        assertEquals(2, arithmetic.max(1, 2));
        assertEquals(2, arithmetic.max(2, 1));
    }

    @Test
    void isZero() {
        assertTrue(arithmetic.isZero(0d));
        assertTrue(arithmetic.isZero(-0d));
        assertFalse(arithmetic.isZero(1d));
    }

    @Test
    void isEqual() {
        assertTrue(arithmetic.isEqual(0d, 0d));
        assertTrue(arithmetic.isEqual(-0d, 0d));
        assertTrue(arithmetic.isEqual(-0d, -0d));
        assertFalse(arithmetic.isEqual(0, 0d));
        assertFalse(arithmetic.isEqual(1d, 0d));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertEquals(3d, arithmetic.sum(1, 2));
    }

    @Test
    void sum123() {
        assertEquals(6d, arithmetic.sum(1, 2, 3));
    }

    @Test
    void sumFrom1To9() {
        assertEquals(
            IntStream.rangeClosed(1, 9).sum(),
            arithmetic.sum(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)).intValue()
        );
    }

    @Test
    void difference12() {
        assertEquals(-1d, arithmetic.difference(1, 2));
    }

    @Test
    void product12() {
        assertEquals(2d, arithmetic.product(1, 2));
    }

    @Test
    void product123() {
        assertEquals(6d, arithmetic.product(1, 2, 3));
    }

    @Test
    void productFrom1To9() {
        assertEquals(
            IntStream.rangeClosed(1, 9).reduce(1, (left, right) -> left * right),
            arithmetic.product(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)).intValue()
        );
    }

    @Test
    void quotient12() {
        assertEquals(0.5, arithmetic.quotient(1, 2));
    }

    @Test
    void modulo12() {
        assertEquals(0d, arithmetic.modulo(1, 2));
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertEquals(1d, arithmetic.power(1, 2));
    }

    @Test
    void root21() {
        assertEquals(1d, arithmetic.root2(1));
    }

    // endregion

    // region isFinite, isInfinite and isNaN

    @Test
    void isFinite() {
        assertTrue(arithmetic.isFinite(0));
        assertFalse(arithmetic.isFinite(Double.POSITIVE_INFINITY));
        assertFalse(arithmetic.isFinite(Double.NaN));
    }

    @Test
    void isInfinite() {
        assertFalse(arithmetic.isInfinite(0));
        assertTrue(arithmetic.isInfinite(Double.POSITIVE_INFINITY));
        assertFalse(arithmetic.isInfinite(Double.NaN));
    }

    @Test
    void isNaN() {
        assertFalse(arithmetic.isNaN(0));
        assertFalse(arithmetic.isNaN(Double.POSITIVE_INFINITY));
        assertTrue(arithmetic.isNaN(Double.NaN));
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertEquals(1L, arithmetic.gcd(3, 4));
    }

    @Test
    void lcm() {
        assertEquals(12d, arithmetic.lcm(3, 4));
    }

    // endregion

    // region trigonometry

    @Test
    void sinOf1() {
        assertEquals(Math.sin(1), arithmetic.sin(1));
    }

    @Test
    void cosOf1() {
        assertEquals(Math.cos(1), arithmetic.cos(1));
    }

    @Test
    void tanOf1() {
        assertEquals(Math.tan(1), arithmetic.tan(1));
    }

    @Test
    void asinOf1() {
        assertEquals(Math.asin(1), arithmetic.asin(1));
    }

    @Test
    void acosOf1() {
        assertEquals(Math.acos(1), arithmetic.acos(1));
    }

    @Test
    void atanOf1() {
        assertEquals(Math.atan(1), arithmetic.atan(1));
    }

    @Test
    void sinhOf1() {
        assertEquals(Math.sinh(1), arithmetic.sinh(1));
    }

    @Test
    void coshOf1() {
        assertEquals(Math.cosh(1), arithmetic.cosh(1));
    }

    @Test
    void tanhOf1() {
        assertEquals(Math.tanh(1), arithmetic.tanh(1));
    }

    // endregion

    // region toResultArithmetic

    @Test
    void toResultArithmetic() {
        AbstractResultArithmetic<Number, Number> resultArithmetic =
            arithmetic.toResultArithmetic();
        assertEquals(arithmetic, resultArithmetic.getTArithmetic());
        assertEquals(arithmetic, resultArithmetic.getRArithmetic());
        assertEquals(0, resultArithmetic.fromT(0));
        assertEquals(-0d, resultArithmetic.fromT(-0d));
    }

    @Test
    void toResultArithmeticWithTarget() {
        IntegerArithmetic targetArithmetic = new IntegerArithmetic();
        AbstractResultArithmetic<Number, Integer> resultArithmetic =
            arithmetic.toResultArithmetic(targetArithmetic, Number::intValue);
        assertEquals(arithmetic, resultArithmetic.getTArithmetic());
        assertEquals(targetArithmetic, resultArithmetic.getRArithmetic());
        assertEquals(0, resultArithmetic.fromT(0));
        assertEquals(0, resultArithmetic.fromT(-0d));
    }

    // endregion

    // region override

    @Test
    void equalsOfAbstractArithmetic() {
        assertEquals(
            new TestAbstractArithmetic(),
            new TestAbstractArithmetic()
        );
    }

    @Test
    void hashCodeOfAbstractArithmetic() {
        assertEquals(1072694209, new TestAbstractArithmetic().hashCode());
    }

    @Test
    void toStringOfAbstractArithmetic() {
        assertEquals("TestAbstractArithmetic",
            new TestAbstractArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        assertSerializable(
            new TestAbstractArithmetic(),
            TestAbstractArithmetic.class
        );
    }

    // endregion

    @Test
    void notImplementedException() {
        assertThrows(AbstractArithmetic.NotImplementedException.class, () -> {
            throw new AbstractArithmetic.NotImplementedException();
        });
    }
}
