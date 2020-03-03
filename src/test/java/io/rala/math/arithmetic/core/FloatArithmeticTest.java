package io.rala.math.arithmetic.core;

import io.rala.math.arithmetic.AbstractArithmetic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class FloatArithmeticTest {
    private FloatArithmetic arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new FloatArithmetic();
    }

    @Test
    void getInstance() {
        assertEquals(arithmetic, FloatArithmetic.getInstance());
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        assertEquals(1, arithmetic.fromInt(1));
    }

    @Test
    void fromDouble1_1() {
        assertEquals(1.1f, arithmetic.fromDouble(1.1));
    }

    @Test
    void signum1() {
        assertEquals(1, arithmetic.signum(1f));
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertEquals(1, arithmetic.absolute(-1f));
    }

    @Test
    void negate1() {
        assertEquals(-1, arithmetic.negate(1f));
    }

    @Test
    void compare() {
        assertEquals(0, arithmetic.compare(1f, 1f));
        assertEquals(-1, arithmetic.compare(1f, 2f));
        assertEquals(1, arithmetic.compare(2f, 1f));
    }

    @Test
    void min() {
        assertEquals(1, arithmetic.min(1f, 1f));
        assertEquals(1, arithmetic.min(1f, 2f));
        assertEquals(1, arithmetic.min(2f, 1f));
    }

    @Test
    void max() {
        assertEquals(1, arithmetic.max(1f, 1f));
        assertEquals(2, arithmetic.max(1f, 2f));
        assertEquals(2, arithmetic.max(2f, 1f));
    }

    @Test
    void isZero() {
        assertTrue(arithmetic.isZero(0f));
        assertTrue(arithmetic.isZero(-0f));
        assertFalse(arithmetic.isZero(1f));
    }

    @Test
    void isEqual() {
        assertTrue(arithmetic.isEqual(0f, 0f));
        assertTrue(arithmetic.isEqual(-0f, 0f));
        assertTrue(arithmetic.isEqual(-0f, -0f));
        assertFalse(arithmetic.isEqual(1f, 0f));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertEquals(3, arithmetic.sum(1f, 2f));
    }

    @Test
    void sum123() {
        assertEquals(6, arithmetic.sum(1f, 2f, 3f));
    }

    @Test
    void difference12() {
        assertEquals(-1, arithmetic.difference(1f, 2f));
    }

    @Test
    void product12() {
        assertEquals(2, arithmetic.product(1f, 2f));
    }

    @Test
    void product123() {
        assertEquals(6, arithmetic.product(1f, 2f, 3f));
    }

    @Test
    void quotient12() {
        assertEquals(0.5f, arithmetic.quotient(1f, 2f));
    }

    @Test
    void modulo12() {
        assertEquals(1, arithmetic.modulo(1f, 2f));
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertEquals(1, arithmetic.power(1f, 2));
    }

    @Test
    void root21() {
        assertEquals((float) Math.sqrt(1), arithmetic.root2(1f));
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.gcd(3f, 4f)
        ); // assert exception message?
    }

    @Test
    void lcm() {
        assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.lcm(3f, 4f)
        ); // assert exception message?
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertEquals(new FloatArithmetic(), new FloatArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        assertEquals(1065354177, new FloatArithmetic().hashCode());
    }

    @Test
    void toStringOfArithmetic() {
        assertEquals("FloatArithmetic", new FloatArithmetic().toString());
    }

    @Test
    void serializable() {
        assertSerializable(new FloatArithmetic(), FloatArithmetic.class);
    }

    // endregion
}
