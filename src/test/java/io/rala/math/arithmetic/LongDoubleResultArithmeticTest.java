package io.rala.math.arithmetic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LongDoubleResultArithmeticTest {
    private static LongDoubleResultArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new LongDoubleResultArithmetic();
    }

    // region fromT

    @Test
    void fromT1() {
        Assertions.assertEquals(1, arithmetic.fromT(1L));
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
        Assertions.assertEquals(0.5, arithmetic.quotient(1L, 2L));
    }

    @Test
    void modulo12() {
        Assertions.assertEquals(1, arithmetic.modulo(1L, 2L));
    }

    // endregion
}
