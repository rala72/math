package io.rala.math.utils.arithmetic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class IntegerArithmeticTest {
    private static IntegerArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new IntegerArithmetic();
    }

    @Test
    void fromInt1() {
        Assertions.assertEquals(1, arithmetic.fromInt(1));
    }

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
}
