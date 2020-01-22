package io.rala.math.arithmetic.core;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.testUtils.SerializableTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DoubleArithmeticTest {
    private static DoubleArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new DoubleArithmetic();
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        Assertions.assertEquals(1, arithmetic.fromInt(1));
    }

    @Test
    void fromDouble1_1() {
        Assertions.assertEquals(1.1, arithmetic.fromDouble(1.1));
    }

    @Test
    void signum1() {
        Assertions.assertEquals(1, arithmetic.signum(1d));
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        Assertions.assertEquals(1, arithmetic.absolute(-1d));
    }

    @Test
    void negate1() {
        Assertions.assertEquals(-1, arithmetic.negate(1d));
    }

    @Test
    void compare() {
        Assertions.assertEquals(
            0, arithmetic.compare(1d, 1d)
        );
        Assertions.assertEquals(
            -1, arithmetic.compare(1d, 2d)
        );
        Assertions.assertEquals(
            1, arithmetic.compare(2d, 1d)
        );
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        Assertions.assertEquals(3, arithmetic.sum(1d, 2d));
    }

    @Test
    void sum123() {
        Assertions.assertEquals(6, arithmetic.sum(1d, 2d, 3d));
    }

    @Test
    void difference12() {
        Assertions.assertEquals(-1, arithmetic.difference(1d, 2d));
    }

    @Test
    void product12() {
        Assertions.assertEquals(2, arithmetic.product(1d, 2d));
    }

    @Test
    void product123() {
        Assertions.assertEquals(6, arithmetic.product(1d, 2d, 3d));
    }

    @Test
    void quotient12() {
        Assertions.assertEquals(0.5d, arithmetic.quotient(1d, 2d));
    }

    @Test
    void modulo12() {
        Assertions.assertEquals(1, arithmetic.modulo(1d, 2d));
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        Assertions.assertEquals(1, arithmetic.power(1d, 2));
    }

    @Test
    void root21() {
        Assertions.assertEquals(
            Math.sqrt(1),
            arithmetic.root2(1d)
        );
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        Assertions.assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.gcd(3d, 4d)
        ); // assert exception message?
    }

    @Test
    void lcm() {
        Assertions.assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.lcm(3d, 4d)
        ); // assert exception message?
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        Assertions.assertEquals(new DoubleArithmetic(), new DoubleArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        Assertions.assertEquals(1072694209,
            new DoubleArithmetic().hashCode()
        );
    }

    @Test
    void toStringOfArithmetic() {
        Assertions.assertEquals("DoubleArithmetic",
            new DoubleArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        SerializableTestUtils.verify(
            new DoubleArithmetic(),
            DoubleArithmetic.class
        );
    }

    // endregion
}
