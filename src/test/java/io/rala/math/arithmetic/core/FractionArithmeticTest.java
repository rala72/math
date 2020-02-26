package io.rala.math.arithmetic.core;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.testUtils.algebra.TestFraction;
import io.rala.math.testUtils.arithmetic.TestFractionArithmetic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class FractionArithmeticTest {
    private static FractionArithmetic<Number, Number> arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new TestFractionArithmetic();
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        assertEquals(new TestFraction(1d), arithmetic.fromInt(1));
    }

    @Test
    void fromDouble1_1() {
        assertEquals(new TestFraction(11d, 10d),
            arithmetic.fromDouble(1.1)
        );
    }

    @Test
    void signum1() {
        assertEquals(1, arithmetic.signum(new TestFraction(1)));
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertEquals(new TestFraction(1d),
            arithmetic.absolute(new TestFraction(-1d))
        );
    }

    @Test
    void negate1() {
        assertEquals(new TestFraction(-1d),
            arithmetic.negate(new TestFraction(1d))
        );
    }

    @Test
    void compare() {
        TestFraction complex = new TestFraction(2, 3);
        assertEquals(0,
            arithmetic.compare(complex, new TestFraction(2, 3))
        );
        assertEquals(-1,
            arithmetic.compare(complex, new TestFraction(3, 1))
        );
        assertEquals(1,
            arithmetic.compare(complex, new TestFraction(1, 2))
        );
    }

    @Test
    void min() {
        assertEquals(new TestFraction(1),
            arithmetic.min(new TestFraction(1), new TestFraction(1))
        );
        assertEquals(new TestFraction(1),
            arithmetic.min(new TestFraction(1), new TestFraction(2))
        );
        assertEquals(new TestFraction(1),
            arithmetic.min(new TestFraction(2), new TestFraction(1))
        );
    }

    @Test
    void max() {
        assertEquals(new TestFraction(1),
            arithmetic.max(new TestFraction(1), new TestFraction(1))
        );
        assertEquals(new TestFraction(2),
            arithmetic.max(new TestFraction(1), new TestFraction(2))
        );
        assertEquals(new TestFraction(2),
            arithmetic.max(new TestFraction(2), new TestFraction(1))
        );
    }

    @Test
    void isZero() {
        assertTrue(arithmetic.isZero(new TestFraction(0d)));
        assertTrue(arithmetic.isZero(new TestFraction(-0d)));
        assertFalse(arithmetic.isZero(new TestFraction(1d)));
    }

    @Test
    void isEqual() {
        assertTrue(
            arithmetic.isEqual(
                new TestFraction(0d),
                new TestFraction(0d)
            )
        );
        assertTrue(
            arithmetic.isEqual(
                new TestFraction(-0d),
                new TestFraction(0d)
            )
        );
        assertTrue(
            arithmetic.isEqual(
                new TestFraction(-0d),
                new TestFraction(-0d)
            )
        );
        assertFalse(
            arithmetic.isEqual(
                new TestFraction(1d),
                new TestFraction(0d)
            )
        );
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertEquals(new TestFraction(3d, 1d),
            arithmetic.sum(new TestFraction(1d), new TestFraction(2d))
        );
    }

    @Test
    void sum123() {
        assertEquals(new TestFraction(6d, 1d),
            arithmetic.sum(
                new TestFraction(1d),
                new TestFraction(2d),
                new TestFraction(3d)
            )
        );
    }

    @Test
    void difference12() {
        assertEquals(new TestFraction(-1d, 1d),
            arithmetic.difference(new TestFraction(1d), new TestFraction(2d))
        );
    }

    @Test
    void product12() {
        assertEquals(new TestFraction(2d, 1d),
            arithmetic.product(new TestFraction(1d), new TestFraction(2d))
        );
    }

    @Test
    void product123() {
        assertEquals(new TestFraction(6d, 1d),
            arithmetic.product(
                new TestFraction(1d),
                new TestFraction(2d),
                new TestFraction(3d))
        );
    }

    @Test
    void quotient12() {
        assertEquals(new TestFraction(1d, 2d),
            arithmetic.quotient(new TestFraction(1d), new TestFraction(2d))
        );
    }

    @Test
    void modulo12() {
        assertEquals(new TestFraction(0d, 2d),
            arithmetic.modulo(new TestFraction(1d), new TestFraction(2d))
        );
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertEquals(new TestFraction(1d, 1d),
            arithmetic.power(new TestFraction(1d), 2)
        );
    }

    @Test
    void root21() {
        assertEquals(
            new TestFraction(Math.sqrt(1), Math.sqrt(1)),
            arithmetic.root2(new TestFraction(1d))
        );
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.gcd(new TestFraction(3d), new TestFraction(4d))
        ); // assert exception message?
    }

    @Test
    void lcm() {
        assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.lcm(new TestFraction(3d), new TestFraction(4d))
        ); // assert exception message?
    }

    // endregion

    // region static of

    @Test
    void ofNumerator() {
        Fraction<Integer, Integer> ofArithmetic =
            Fraction.of(new IntegerArithmetic(), 1);
        assertNotNull(ofArithmetic.getNumerator());
        assertNotNull(ofArithmetic.getDenominator());
        assertEquals(1, ofArithmetic.getNumerator());
        assertEquals(1, ofArithmetic.getDenominator());
    }

    @Test
    void ofNumeratorAndDenominator() {
        Fraction<Integer, Integer> ofArithmetic =
            Fraction.of(new IntegerArithmetic(), 1, 2);
        assertNotNull(ofArithmetic.getNumerator());
        assertNotNull(ofArithmetic.getDenominator());
        assertEquals(1, ofArithmetic.getNumerator());
        assertEquals(2, ofArithmetic.getDenominator());
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertEquals(
            new TestFractionArithmetic(),
            new TestFractionArithmetic()
        );
    }

    @Test
    void hashCodeOfArithmetic() {
        assertEquals(-1173293087, new TestFractionArithmetic().hashCode());
    }

    @Test
    void toStringOfArithmetic() {
        assertEquals("TestFractionArithmetic",
            new TestFractionArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        assertSerializable(
            new TestFractionArithmetic(),
            TestFractionArithmetic.class
        );
    }

    // endregion
}
