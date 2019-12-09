package io.rala.math.utils.arithmetic;

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

    @Test
    void fromInt1() {
        Assertions.assertEquals(BigInteger.ONE, arithmetic.fromInt(1));
    }

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
}
