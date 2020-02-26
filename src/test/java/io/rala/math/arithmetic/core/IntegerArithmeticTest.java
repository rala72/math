package io.rala.math.arithmetic.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class IntegerArithmeticTest {
    private static IntegerArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new IntegerArithmetic();
    }

    @Test
    void getInstance() {
        assertEquals(arithmetic, IntegerArithmetic.getInstance());
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
        assertEquals(1, arithmetic.signum(1));
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertEquals(1, arithmetic.absolute(-1));
    }

    @Test
    void negate1() {
        assertEquals(-1, arithmetic.negate(1));
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
        assertTrue(arithmetic.isZero(0));
        assertTrue(arithmetic.isZero(-0));
        assertFalse(arithmetic.isZero(1));
    }

    @Test
    void isEqual() {
        assertTrue(arithmetic.isEqual(0, 0));
        assertTrue(arithmetic.isEqual(-0, 0));
        assertTrue(arithmetic.isEqual(-0, -0));
        assertFalse(arithmetic.isEqual(1, 0));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertEquals(3, arithmetic.sum(1, 2));
    }

    @Test
    void sum123() {
        assertEquals(6, arithmetic.sum(1, 2, 3));
    }

    @Test
    void difference12() {
        assertEquals(-1, arithmetic.difference(1, 2));
    }

    @Test
    void product12() {
        assertEquals(2, arithmetic.product(1, 2));
    }

    @Test
    void product123() {
        assertEquals(6, arithmetic.product(1, 2, 3));
    }

    @Test
    void quotient12() {
        assertEquals(0, arithmetic.quotient(1, 2));
    }

    @Test
    void modulo12() {
        assertEquals(1, arithmetic.modulo(1, 2));
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertEquals(1, arithmetic.power(1, 2));
    }

    @Test
    void root21() {
        assertEquals((int) Math.sqrt(1), arithmetic.root2(1));
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertEquals(1, arithmetic.gcd(3, 4));
    }

    @Test
    void lcm() {
        assertEquals(12, arithmetic.lcm(3, 4));
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertEquals(new IntegerArithmetic(), new IntegerArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        assertEquals(962, new IntegerArithmetic().hashCode());
    }

    @Test
    void toStringOfArithmetic() {
        assertEquals("IntegerArithmetic", new IntegerArithmetic().toString());
    }

    @Test
    void serializable() {
        assertSerializable(new IntegerArithmetic(), IntegerArithmetic.class);
    }

    // endregion
}
