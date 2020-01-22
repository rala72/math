package io.rala.math.arithmetic.core;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.testUtils.SerializableTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FloatArithmeticTest {
    private static FloatArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new FloatArithmetic();
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        Assertions.assertEquals(1, arithmetic.fromInt(1));
    }

    @Test
    void fromDouble1_1() {
        Assertions.assertEquals(1.1f, arithmetic.fromDouble(1.1));
    }

    @Test
    void signum1() {
        Assertions.assertEquals(1, arithmetic.signum(1f));
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        Assertions.assertEquals(1, arithmetic.absolute(-1f));
    }

    @Test
    void negate1() {
        Assertions.assertEquals(-1, arithmetic.negate(1f));
    }

    @Test
    void compare() {
        Assertions.assertEquals(
            0, arithmetic.compare(1f, 1f)
        );
        Assertions.assertEquals(
            -1, arithmetic.compare(1f, 2f)
        );
        Assertions.assertEquals(
            1, arithmetic.compare(2f, 1f)
        );
    }

    @Test
    void isZero() {
        Assertions.assertTrue(arithmetic.isZero(0f));
        Assertions.assertTrue(arithmetic.isZero(-0f));
        Assertions.assertFalse(arithmetic.isZero(1f));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        Assertions.assertEquals(3, arithmetic.sum(1f, 2f));
    }

    @Test
    void sum123() {
        Assertions.assertEquals(6, arithmetic.sum(1f, 2f, 3f));
    }

    @Test
    void difference12() {
        Assertions.assertEquals(-1, arithmetic.difference(1f, 2f));
    }

    @Test
    void product12() {
        Assertions.assertEquals(2, arithmetic.product(1f, 2f));
    }

    @Test
    void product123() {
        Assertions.assertEquals(6, arithmetic.product(1f, 2f, 3f));
    }

    @Test
    void quotient12() {
        Assertions.assertEquals(0.5f, arithmetic.quotient(1f, 2f));
    }

    @Test
    void modulo12() {
        Assertions.assertEquals(1, arithmetic.modulo(1f, 2f));
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        Assertions.assertEquals(1, arithmetic.power(1f, 2));
    }

    @Test
    void root21() {
        Assertions.assertEquals(
            (float) Math.sqrt(1),
            arithmetic.root2(1f)
        );
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        Assertions.assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.gcd(3f, 4f)
        ); // assert exception message?
    }

    @Test
    void lcm() {
        Assertions.assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.lcm(3f, 4f)
        ); // assert exception message?
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        Assertions.assertEquals(new FloatArithmetic(), new FloatArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        Assertions.assertEquals(1065354177,
            new FloatArithmetic().hashCode()
        );
    }

    @Test
    void toStringOfArithmetic() {
        Assertions.assertEquals("FloatArithmetic",
            new FloatArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        SerializableTestUtils.verify(
            new FloatArithmetic(),
            FloatArithmetic.class
        );
    }

    // endregion
}
