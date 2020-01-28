package io.rala.math.arithmetic.core;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

class BigDecimalArithmeticTest {
    private static BigDecimalArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new BigDecimalArithmetic();
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        Assertions.assertEquals(BigDecimal.ONE, arithmetic.fromInt(1));
    }

    @Test
    void fromDouble1_1() {
        Assertions.assertEquals(BigDecimal.valueOf(1.1), arithmetic.fromDouble(1.1));
    }

    @Test
    void signum1() {
        Assertions.assertEquals(1, arithmetic.signum(BigDecimal.ONE));
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        Assertions.assertEquals(BigDecimal.ONE, arithmetic.absolute(BigDecimal.ONE.negate()));
    }

    @Test
    void negate1() {
        Assertions.assertEquals(BigDecimal.ONE.negate(), arithmetic.negate(BigDecimal.ONE));
    }

    @Test
    void compare() {
        Assertions.assertEquals(
            0, arithmetic.compare(BigDecimal.ONE, BigDecimal.ONE)
        );
        Assertions.assertEquals(
            -1, arithmetic.compare(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
        Assertions.assertEquals(
            1, arithmetic.compare(BigDecimal.valueOf(2), BigDecimal.ONE)
        );
    }

    @Test
    void isZero() {
        Assertions.assertTrue(arithmetic.isZero(BigDecimal.ZERO));
        Assertions.assertTrue(arithmetic.isZero(BigDecimal.ZERO.negate()));
        Assertions.assertFalse(arithmetic.isZero(BigDecimal.ONE));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        Assertions.assertEquals(BigDecimal.valueOf(3),
            arithmetic.sum(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
    }

    @Test
    void sum123() {
        Assertions.assertEquals(BigDecimal.valueOf(6),
            arithmetic.sum(BigDecimal.ONE, BigDecimal.valueOf(2), BigDecimal.valueOf(3))
        );
    }

    @Test
    void difference12() {
        Assertions.assertEquals(BigDecimal.valueOf(-1),
            arithmetic.difference(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
    }

    @Test
    void product12() {
        Assertions.assertEquals(BigDecimal.valueOf(2),
            arithmetic.product(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
    }

    @Test
    void product123() {
        Assertions.assertEquals(BigDecimal.valueOf(6),
            arithmetic.product(BigDecimal.ONE, BigDecimal.valueOf(2), BigDecimal.valueOf(3))
        );
    }

    @Test
    void quotient12() {
        Assertions.assertEquals(BigDecimal.valueOf(0.5),
            arithmetic.quotient(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
    }

    @Test
    void modulo12() {
        Assertions.assertEquals(BigDecimal.ONE,
            arithmetic.modulo(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        Assertions.assertEquals(BigDecimal.ONE, arithmetic.power(BigDecimal.ONE, 2));
    }

    @Test
    void root21() {
        Assertions.assertEquals(
            BigDecimal.ONE.sqrt(new MathContext(10)),
            arithmetic.root2(BigDecimal.ONE)
        );
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        Assertions.assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.gcd(BigDecimal.valueOf(3), BigDecimal.valueOf(4))
        ); // assert exception message?
    }

    @Test
    void lcm() {
        Assertions.assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.gcd(BigDecimal.valueOf(3), BigDecimal.valueOf(4))
        ); // assert exception message?
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        Assertions.assertEquals(new BigDecimalArithmetic(), new BigDecimalArithmetic());
        Assertions.assertNotEquals(new BigDecimalArithmetic(),
            new BigDecimalArithmetic(new MathContext(5))
        );
    }

    @Test
    void hashCodeOfArithmetic() {
        // hashCode of RoundingMode enum changing after every start
        Assertions.assertEquals(
            new BigDecimalArithmetic().hashCode(),
            new BigDecimalArithmetic().hashCode()
        );
    }

    @Test
    void toStringOfArithmetic() {
        Assertions.assertEquals("BigDecimalArithmetic",
            new BigDecimalArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
            new BigDecimalArithmetic(),
            BigDecimalArithmetic.class
        );
    }

    // endregion
}
