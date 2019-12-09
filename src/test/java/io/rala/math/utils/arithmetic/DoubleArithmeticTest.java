package io.rala.math.utils.arithmetic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DoubleArithmeticTest {
    private static DoubleArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new DoubleArithmetic();
    }

    @Test
    void fromInt1() {
        Assertions.assertEquals(1, arithmetic.fromInt(1));
    }

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
}
