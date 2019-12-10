package io.rala.math.arithmetic.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FloatArithmeticTest {
    private static FloatArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new FloatArithmetic();
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        Assertions.assertEquals(1, arithmetic.fromInt(1));
    }

    @Test
    void fromDouble1_1() {
        Assertions.assertEquals(1.1f, arithmetic.fromDouble(1.1));
    }

    @Test
    void signum1() {
        Assertions.assertEquals(1, arithmetic.signum(1f));
    }

    // endregion

    // region absolute and negate

    @Test
    void absoluteM1() {
        Assertions.assertEquals(1, arithmetic.absolute(-1f));
    }

    @Test
    void negate1() {
        Assertions.assertEquals(-1, arithmetic.negate(1f));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

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

    @Test
    void modulo12() {
        Assertions.assertEquals(1, arithmetic.modulo(1f, 2f));
    }

    // endregion

    // region exponent and root

    @Test
    void exponent12() {
        Assertions.assertEquals(1, arithmetic.exponent(1f, 2));
    }

    @Test
    void root21() {
        Assertions.assertEquals(
            (float) Math.sqrt(1),
            arithmetic.root2(1f)
        );
    }

    // endregion
}
