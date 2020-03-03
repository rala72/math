package io.rala.math.arithmetic.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class LongArithmeticTest {
    private LongArithmetic arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new LongArithmetic();
    }

    @Test
    void getInstance() {
        assertEquals(arithmetic, LongArithmetic.getInstance());
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        assertEquals(1, arithmetic.fromInt(1));
    }

    @Test
    void fromDouble1_1() {
        assertEquals(1, arithmetic.fromDouble(1.1));
    }

    @Test
    void signum1() {
        assertEquals(1, arithmetic.signum(1L));
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertEquals(1, arithmetic.absolute(-1L));
    }

    @Test
    void negate1() {
        assertEquals(-1, arithmetic.negate(1L));
    }

    @Test
    void compare() {
        assertEquals(0, arithmetic.compare(1L, 1L));
        assertEquals(-1, arithmetic.compare(1L, 2L));
        assertEquals(1, arithmetic.compare(2L, 1L));
    }

    @Test
    void min() {
        assertEquals(1, arithmetic.min(1L, 1L));
        assertEquals(1, arithmetic.min(1L, 2L));
        assertEquals(1, arithmetic.min(2L, 1L));
    }

    @Test
    void max() {
        assertEquals(1, arithmetic.max(1L, 1L));
        assertEquals(2, arithmetic.max(1L, 2L));
        assertEquals(2, arithmetic.max(2L, 1L));
    }

    @Test
    void isZero() {
        assertTrue(arithmetic.isZero(0L));
        assertTrue(arithmetic.isZero(-0L));
        assertFalse(arithmetic.isZero(1L));
    }

    @Test
    void isEqual() {
        assertTrue(arithmetic.isEqual(0L, 0L));
        assertTrue(arithmetic.isEqual(-0L, 0L));
        assertTrue(arithmetic.isEqual(-0L, -0L));
        assertFalse(arithmetic.isEqual(1L, 0L));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertEquals(3, arithmetic.sum(1L, 2L));
    }

    @Test
    void sum123() {
        assertEquals(6, arithmetic.sum(1L, 2L, 3L));
    }

    @Test
    void difference12() {
        assertEquals(-1, arithmetic.difference(1L, 2L));
    }

    @Test
    void product12() {
        assertEquals(2, arithmetic.product(1L, 2L));
    }

    @Test
    void product123() {
        assertEquals(6, arithmetic.product(1L, 2L, 3L));
    }

    @Test
    void quotient12() {
        assertEquals(0, arithmetic.quotient(1L, 2L));
    }

    @Test
    void modulo12() {
        assertEquals(1, arithmetic.modulo(1L, 2L));
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertEquals(1, arithmetic.power(1L, 2));
    }

    @Test
    void root21() {
        assertEquals((long) Math.sqrt(1), arithmetic.root2(1L));
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertEquals(1, arithmetic.gcd(3L, 4L));
    }

    @Test
    void lcm() {
        assertEquals(12, arithmetic.lcm(3L, 4L));
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertEquals(new LongArithmetic(), new LongArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        assertEquals(962, new LongArithmetic().hashCode());
    }

    @Test
    void toStringOfArithmetic() {
        assertEquals("LongArithmetic", new LongArithmetic().toString());
    }

    @Test
    void serializable() {
        assertSerializable(new LongArithmetic(), LongArithmetic.class);
    }

    // endregion
}
