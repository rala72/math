package io.rala.math.utils.arithmetic;

import io.rala.math.testUtils.TestAbstractArithmetic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractArithmeticTest {
    private AbstractArithmetic<Number> arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new TestAbstractArithmetic();
    }

    // region fromInt, signum and negate

    @Test
    void implementedIntegerFromInt0() {
        Assertions.assertEquals(0d, arithmetic.fromInt(0));
    }

    @Test
    void implementedIntegerSignum1() {
        Assertions.assertEquals(1, arithmetic.signum(1));
    }

    @Test
    void implementedIntegerNegate1() {
        Assertions.assertEquals(-1, arithmetic.negate(1));
    }

    // endregion

    // region sum, difference, product and quotient

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

    // endregion

    // region exponent and root

    @Test
    void implementedIntegerExponent12() {
        Assertions.assertEquals(1, arithmetic.exponent(1, 2));
    }

    @Test
    void implementedIntegerRoot21() {
        Assertions.assertEquals(1d, arithmetic.root2(1));
    }

    // endregion
}
