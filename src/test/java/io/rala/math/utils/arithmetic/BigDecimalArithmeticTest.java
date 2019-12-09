package io.rala.math.utils.arithmetic;

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

    // region fromInt, fromDouble, signum and negate

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

    @Test
    void negate1() {
        Assertions.assertEquals(BigDecimal.ONE.negate(), arithmetic.negate(BigDecimal.ONE));
    }

    // endregion

    // region sum, difference, product and quotient

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

    // endregion

    // region exponent and root

    @Test
    void exponent12() {
        Assertions.assertEquals(BigDecimal.ONE, arithmetic.exponent(BigDecimal.ONE, 2));
    }

    @Test
    void root21() {
        Assertions.assertEquals(
            BigDecimal.ONE.sqrt(new MathContext(10)),
            arithmetic.root2(BigDecimal.ONE)
        );
    }

    // endregion
}
