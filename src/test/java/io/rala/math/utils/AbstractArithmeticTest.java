package io.rala.math.utils;

import io.rala.math.testUtils.TestAbstractArithmetic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AbstractArithmeticTest {
    private AbstractArithmetic<Integer> arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new TestAbstractArithmetic();
    }

    @Test
    void implementedFromInt0() {
        Assertions.assertEquals(0, arithmetic.fromInt(0));
    }

    @Test
    void implementedIntegerSum12() {
        Assertions.assertEquals(3, arithmetic.sum(1, 2));
    }

    @Test
    void implementedIntegerSum123() {
        Assertions.assertEquals(6, arithmetic.sum(1, 2, 3));
    }

    @Test
    void implementedIntegerDifference12() {
        Assertions.assertEquals(-1, arithmetic.difference(1, 2));
    }

    @Test
    void implementedIntegerProduct12() {
        Assertions.assertEquals(2, arithmetic.product(1, 2));
    }

    @Test
    void implementedIntegerProduct123() {
        Assertions.assertEquals(6, arithmetic.product(1, 2, 3));
    }

    @Test
    void implementedIntegerQuotient12() {
        Assertions.assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.quotient(1, 2)
        );
    }
}
