package io.rala.math.arithmetic.core;

import io.rala.math.arithmetic.AbstractArithmetic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class DoubleArithmeticTest {
    private static DoubleArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new DoubleArithmetic();
    }

    @Test
    void getInstance() {
        assertEquals(arithmetic, DoubleArithmetic.getInstance());
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        assertEquals(1, arithmetic.fromInt(1));
    }

    @Test
    void fromDouble1_1() {
        assertEquals(1.1, arithmetic.fromDouble(1.1));
    }

    @Test
    void signum1() {
        assertEquals(1, arithmetic.signum(1d));
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertEquals(1, arithmetic.absolute(-1d));
    }

    @Test
    void negate1() {
        assertEquals(-1, arithmetic.negate(1d));
    }

    @Test
    void compare() {
        assertEquals(
            0, arithmetic.compare(1d, 1d)
        );
        assertEquals(
            -1, arithmetic.compare(1d, 2d)
        );
        assertEquals(
            1, arithmetic.compare(2d, 1d)
        );
    }

    @Test
    void min() {
        assertEquals(
            1, arithmetic.min(1d, 1d)
        );
        assertEquals(
            1, arithmetic.min(1d, 2d)
        );
        assertEquals(
            1, arithmetic.min(2d, 1d)
        );
    }

    @Test
    void max() {
        assertEquals(
            1, arithmetic.max(1d, 1d)
        );
        assertEquals(
            2, arithmetic.max(1d, 2d)
        );
        assertEquals(
            2, arithmetic.max(2d, 1d)
        );
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
        assertFalse(arithmetic.isEqual(1d, 0d));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertEquals(3, arithmetic.sum(1d, 2d));
    }

    @Test
    void sum123() {
        assertEquals(6, arithmetic.sum(1d, 2d, 3d));
    }

    @Test
    void difference12() {
        assertEquals(-1, arithmetic.difference(1d, 2d));
    }

    @Test
    void product12() {
        assertEquals(2, arithmetic.product(1d, 2d));
    }

    @Test
    void product123() {
        assertEquals(6, arithmetic.product(1d, 2d, 3d));
    }

    @Test
    void quotient12() {
        assertEquals(0.5d, arithmetic.quotient(1d, 2d));
    }

    @Test
    void modulo12() {
        assertEquals(1, arithmetic.modulo(1d, 2d));
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertEquals(1, arithmetic.power(1d, 2));
    }

    @Test
    void root21() {
        assertEquals(
            Math.sqrt(1),
            arithmetic.root2(1d)
        );
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.gcd(3d, 4d)
        ); // assert exception message?
    }

    @Test
    void lcm() {
        assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.lcm(3d, 4d)
        ); // assert exception message?
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertEquals(new DoubleArithmetic(), new DoubleArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        assertEquals(1072694209,
            new DoubleArithmetic().hashCode()
        );
    }

    @Test
    void toStringOfArithmetic() {
        assertEquals("DoubleArithmetic",
            new DoubleArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        assertSerializable(
            new DoubleArithmetic(),
            DoubleArithmetic.class
        );
    }

    // endregion
}
