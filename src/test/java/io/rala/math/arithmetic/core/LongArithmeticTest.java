package io.rala.math.arithmetic.core;

import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LongArithmeticTest {
    private static LongArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new LongArithmetic();
    }

    @Test
    void getInstance() {
        Assertions.assertEquals(arithmetic, LongArithmetic.getInstance());
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        Assertions.assertEquals(1, arithmetic.fromInt(1));
    }

    @Test
    void fromDouble1_1() {
        Assertions.assertEquals(1, arithmetic.fromDouble(1.1));
    }

    @Test
    void signum1() {
        Assertions.assertEquals(1, arithmetic.signum(1L));
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        Assertions.assertEquals(1, arithmetic.absolute(-1L));
    }

    @Test
    void negate1() {
        Assertions.assertEquals(-1, arithmetic.negate(1L));
    }

    @Test
    void compare() {
        Assertions.assertEquals(
            0, arithmetic.compare(1L, 1L)
        );
        Assertions.assertEquals(
            -1, arithmetic.compare(1L, 2L)
        );
        Assertions.assertEquals(
            1, arithmetic.compare(2L, 1L)
        );
    }

    @Test
    void min() {
        Assertions.assertEquals(
            1, arithmetic.min(1L, 1L)
        );
        Assertions.assertEquals(
            1, arithmetic.min(1L, 2L)
        );
        Assertions.assertEquals(
            1, arithmetic.min(2L, 1L)
        );
    }

    @Test
    void max() {
        Assertions.assertEquals(
            1, arithmetic.max(1L, 1L)
        );
        Assertions.assertEquals(
            2, arithmetic.max(1L, 2L)
        );
        Assertions.assertEquals(
            2, arithmetic.max(2L, 1L)
        );
    }

    @Test
    void isZero() {
        Assertions.assertTrue(arithmetic.isZero(0L));
        Assertions.assertTrue(arithmetic.isZero(-0L));
        Assertions.assertFalse(arithmetic.isZero(1L));
    }

    @Test
    void isEqual() {
        Assertions.assertTrue(arithmetic.isEqual(0L, 0L));
        Assertions.assertTrue(arithmetic.isEqual(-0L, 0L));
        Assertions.assertTrue(arithmetic.isEqual(-0L, -0L));
        Assertions.assertFalse(arithmetic.isEqual(1L, 0L));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        Assertions.assertEquals(3, arithmetic.sum(1L, 2L));
    }

    @Test
    void sum123() {
        Assertions.assertEquals(6, arithmetic.sum(1L, 2L, 3L));
    }

    @Test
    void difference12() {
        Assertions.assertEquals(-1, arithmetic.difference(1L, 2L));
    }

    @Test
    void product12() {
        Assertions.assertEquals(2, arithmetic.product(1L, 2L));
    }

    @Test
    void product123() {
        Assertions.assertEquals(6, arithmetic.product(1L, 2L, 3L));
    }

    @Test
    void quotient12() {
        Assertions.assertEquals(0, arithmetic.quotient(1L, 2L));
    }

    @Test
    void modulo12() {
        Assertions.assertEquals(1, arithmetic.modulo(1L, 2L));
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        Assertions.assertEquals(1, arithmetic.power(1L, 2));
    }

    @Test
    void root21() {
        Assertions.assertEquals(
            (long) Math.sqrt(1),
            arithmetic.root2(1L)
        );
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        Assertions.assertEquals(1, arithmetic.gcd(3L, 4L));
    }

    @Test
    void lcm() {
        Assertions.assertEquals(12, arithmetic.lcm(3L, 4L));
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        Assertions.assertEquals(new LongArithmetic(), new LongArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        Assertions.assertEquals(962,
            new LongArithmetic().hashCode()
        );
    }

    @Test
    void toStringOfArithmetic() {
        Assertions.assertEquals("LongArithmetic",
            new LongArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
            new LongArithmetic(),
            LongArithmetic.class
        );
    }

    // endregion
}
