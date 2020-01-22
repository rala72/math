package io.rala.math.arithmetic.core;

import io.rala.math.testUtils.SerializableTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class IntegerArithmeticTest {
    private static IntegerArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new IntegerArithmetic();
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

    @Test
    void isZero() {
        Assertions.assertTrue(arithmetic.isZero(0));
        Assertions.assertTrue(arithmetic.isZero(-0));
        Assertions.assertFalse(arithmetic.isZero(1));
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
        Assertions.assertEquals(0, arithmetic.quotient(1, 2));
    }

    @Test
    void modulo12() {
        Assertions.assertEquals(1, arithmetic.modulo(1, 2));
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        Assertions.assertEquals(1, arithmetic.power(1, 2));
    }

    @Test
    void root21() {
        Assertions.assertEquals(
            (int) Math.sqrt(1),
            arithmetic.root2(1)
        );
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        Assertions.assertEquals(1, arithmetic.gcd(3, 4));
    }

    @Test
    void lcm() {
        Assertions.assertEquals(12, arithmetic.lcm(3, 4));
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        Assertions.assertEquals(new IntegerArithmetic(), new IntegerArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        Assertions.assertEquals(962,
            new IntegerArithmetic().hashCode()
        );
    }

    @Test
    void toStringOfArithmetic() {
        Assertions.assertEquals("IntegerArithmetic",
            new IntegerArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        SerializableTestUtils.verify(
            new IntegerArithmetic(),
            IntegerArithmetic.class
        );
    }

    // endregion
}
