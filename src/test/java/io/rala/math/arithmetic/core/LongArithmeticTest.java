package io.rala.math.arithmetic.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LongArithmeticTest {
    private static LongArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new LongArithmetic();
    }

    // region fromInt, fromDouble, signum and negate

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

    @Test
    void negate1() {
        Assertions.assertEquals(-1, arithmetic.negate(1L));
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

    // region exponent and root

    @Test
    void exponent12() {
        Assertions.assertEquals(1, arithmetic.exponent(1L, 2));
    }

    @Test
    void root21() {
        Assertions.assertEquals(
            (long) Math.sqrt(1),
            arithmetic.root2(1L)
        );
    }

    // endregion
}
