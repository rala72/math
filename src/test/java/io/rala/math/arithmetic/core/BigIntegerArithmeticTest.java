package io.rala.math.arithmetic.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

class BigIntegerArithmeticTest {
    private static BigIntegerArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new BigIntegerArithmetic();
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        Assertions.assertEquals(BigInteger.ONE, arithmetic.fromInt(1));
    }

    @Test
    void fromDouble1_1() {
        Assertions.assertEquals(BigInteger.ONE, arithmetic.fromDouble(1.1));
    }

    @Test
    void signum1() {
        Assertions.assertEquals(1, arithmetic.signum(BigInteger.ONE));
    }

    // endregion

    // region absolute and negate

    @Test
    void absoluteM1() {
        Assertions.assertEquals(BigInteger.ONE, arithmetic.absolute(BigInteger.ONE.negate()));
    }

    @Test
    void negate1() {
        Assertions.assertEquals(BigInteger.ONE.negate(), arithmetic.negate(BigInteger.ONE));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        Assertions.assertEquals(BigInteger.valueOf(3),
            arithmetic.sum(BigInteger.ONE, BigInteger.TWO)
        );
    }

    @Test
    void sum123() {
        Assertions.assertEquals(BigInteger.valueOf(6),
            arithmetic.sum(BigInteger.ONE, BigInteger.TWO, BigInteger.valueOf(3))
        );
    }

    @Test
    void difference12() {
        Assertions.assertEquals(BigInteger.valueOf(-1),
            arithmetic.difference(BigInteger.ONE, BigInteger.TWO)
        );
    }

    @Test
    void product12() {
        Assertions.assertEquals(BigInteger.TWO,
            arithmetic.product(BigInteger.ONE, BigInteger.TWO)
        );
    }

    @Test
    void product123() {
        Assertions.assertEquals(BigInteger.valueOf(6),
            arithmetic.product(BigInteger.ONE, BigInteger.TWO, BigInteger.valueOf(3))
        );
    }

    @Test
    void quotient12() {
        Assertions.assertEquals(BigInteger.ZERO,
            arithmetic.quotient(BigInteger.ONE, BigInteger.TWO)
        );
    }

    @Test
    void modulo12() {
        Assertions.assertEquals(BigInteger.ONE,
            arithmetic.modulo(BigInteger.ONE, BigInteger.TWO)
        );
    }

    // endregion

    // region exponent and root

    @Test
    void exponent12() {
        Assertions.assertEquals(BigInteger.ONE, arithmetic.exponent(BigInteger.ONE, 2));
    }

    @Test
    void root21() {
        Assertions.assertEquals(
            BigInteger.ONE.sqrt(),
            arithmetic.root2(BigInteger.ONE)
        );
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        Assertions.assertEquals(BigInteger.ONE,
            arithmetic.gcd(BigInteger.valueOf(3), BigInteger.valueOf(4))
        );
    }

    @Test
    void lcm() {
        Assertions.assertEquals(BigInteger.valueOf(12),
            arithmetic.lcm(BigInteger.valueOf(3), BigInteger.valueOf(4))
        );
    }

    // endregion
}
