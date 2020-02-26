package io.rala.math.arithmetic.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class BigIntegerArithmeticTest {
    private static BigIntegerArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new BigIntegerArithmetic();
    }

    @Test
    void getInstance() {
        assertEquals(arithmetic, BigIntegerArithmetic.getInstance());
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        assertEquals(BigInteger.ONE, arithmetic.fromInt(1));
    }

    @Test
    void fromDouble1_1() {
        assertEquals(BigInteger.ONE, arithmetic.fromDouble(1.1));
    }

    @Test
    void signum1() {
        assertEquals(1, arithmetic.signum(BigInteger.ONE));
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertEquals(BigInteger.ONE,
            arithmetic.absolute(BigInteger.ONE.negate())
        );
    }

    @Test
    void negate1() {
        assertEquals(BigInteger.ONE.negate(),
            arithmetic.negate(BigInteger.ONE)
        );
    }

    @Test
    void compare() {
        assertEquals(
            0, arithmetic.compare(BigInteger.ONE, BigInteger.ONE)
        );
        assertEquals(
            -1, arithmetic.compare(BigInteger.ONE, BigInteger.TWO)
        );
        assertEquals(
            1, arithmetic.compare(BigInteger.TWO, BigInteger.ONE)
        );
    }

    @Test
    void min() {
        assertEquals(
            BigInteger.ONE, arithmetic.min(BigInteger.ONE, BigInteger.ONE)
        );
        assertEquals(
            BigInteger.ONE, arithmetic.min(BigInteger.ONE, BigInteger.TWO)
        );
        assertEquals(
            BigInteger.ONE, arithmetic.min(BigInteger.TWO, BigInteger.ONE)
        );
    }

    @Test
    void max() {
        assertEquals(
            BigInteger.ONE, arithmetic.max(BigInteger.ONE, BigInteger.ONE)
        );
        assertEquals(
            BigInteger.TWO, arithmetic.max(BigInteger.ONE, BigInteger.TWO)
        );
        assertEquals(
            BigInteger.TWO, arithmetic.max(BigInteger.TWO, BigInteger.ONE)
        );
    }

    @Test
    void isZero() {
        assertTrue(arithmetic.isZero(BigInteger.ZERO));
        assertTrue(arithmetic.isZero(BigInteger.ZERO.negate()));
        assertFalse(arithmetic.isZero(BigInteger.ONE));
    }

    @Test
    void isEqual() {
        assertTrue(
            arithmetic.isEqual(BigInteger.ZERO, BigInteger.ZERO)
        );
        assertTrue(
            arithmetic.isEqual(BigInteger.ZERO.negate(), BigInteger.ZERO)
        );
        assertTrue(
            arithmetic.isEqual(BigInteger.ZERO.negate(), BigInteger.ZERO.negate())
        );
        assertFalse(
            arithmetic.isEqual(BigInteger.ONE, BigInteger.ZERO)
        );
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertEquals(BigInteger.valueOf(3),
            arithmetic.sum(BigInteger.ONE, BigInteger.TWO)
        );
    }

    @Test
    void sum123() {
        assertEquals(BigInteger.valueOf(6),
            arithmetic.sum(BigInteger.ONE, BigInteger.TWO, BigInteger.valueOf(3))
        );
    }

    @Test
    void difference12() {
        assertEquals(BigInteger.valueOf(-1),
            arithmetic.difference(BigInteger.ONE, BigInteger.TWO)
        );
    }

    @Test
    void product12() {
        assertEquals(BigInteger.TWO,
            arithmetic.product(BigInteger.ONE, BigInteger.TWO)
        );
    }

    @Test
    void product123() {
        assertEquals(BigInteger.valueOf(6),
            arithmetic.product(BigInteger.ONE, BigInteger.TWO, BigInteger.valueOf(3))
        );
    }

    @Test
    void quotient12() {
        assertEquals(BigInteger.ZERO,
            arithmetic.quotient(BigInteger.ONE, BigInteger.TWO)
        );
    }

    @Test
    void modulo12() {
        assertEquals(BigInteger.ONE,
            arithmetic.modulo(BigInteger.ONE, BigInteger.TWO)
        );
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertEquals(BigInteger.ONE, arithmetic.power(BigInteger.ONE, 2));
    }

    @Test
    void root21() {
        assertEquals(
            BigInteger.ONE.sqrt(),
            arithmetic.root2(BigInteger.ONE)
        );
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertEquals(BigInteger.ONE,
            arithmetic.gcd(BigInteger.valueOf(3), BigInteger.valueOf(4))
        );
    }

    @Test
    void lcm() {
        assertEquals(BigInteger.valueOf(12),
            arithmetic.lcm(BigInteger.valueOf(3), BigInteger.valueOf(4))
        );
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertEquals(new BigIntegerArithmetic(), new BigIntegerArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        assertEquals(962,
            new BigIntegerArithmetic().hashCode()
        );
    }

    @Test
    void toStringOfArithmetic() {
        assertEquals("BigIntegerArithmetic",
            new BigIntegerArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        assertSerializable(
            new BigIntegerArithmetic(),
            BigIntegerArithmetic.class
        );
    }

    // endregion
}
