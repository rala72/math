package io.rala.math.utils.arithmetic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class BigDecimalArithmeticTest {
    private static BigDecimalArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new BigDecimalArithmetic();
    }

    @Test
    void fromInt1() {
        Assertions.assertEquals(BigDecimal.ONE, arithmetic.fromInt(1));
    }

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
}
