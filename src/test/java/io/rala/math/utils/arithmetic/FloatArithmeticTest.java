package io.rala.math.utils.arithmetic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FloatArithmeticTest {
    private static FloatArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new FloatArithmetic();
    }

    @Test
    void fromInt1() {
        Assertions.assertEquals(1, arithmetic.fromInt(1));
    }

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
}
