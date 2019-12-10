package io.rala.math.arithmetic;

import io.rala.math.testUtils.TestAbstractArithmetic;
import io.rala.math.testUtils.TestAbstractResultArithmetic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AbstractResultArithmeticTest {
    private AbstractResultArithmetic<Number, Number> arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new TestAbstractResultArithmetic();
    }

    @Test
    void getArithmetic() {
        Assertions.assertTrue(arithmetic.getTArithmetic() instanceof TestAbstractArithmetic);
        Assertions.assertTrue(arithmetic.getRArithmetic() instanceof TestAbstractArithmetic);
    }

    // region sum, difference, product, quotient and modulo

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
        Assertions.assertEquals(0.5, arithmetic.quotient(1, 2));
    }

    @Test
    void modulo12() {
        Assertions.assertEquals(1, arithmetic.modulo(1, 2));
    }

    // endregion

    // region static of

    @Test
    void of() {
        AbstractResultArithmetic<Number, Number> ofArithmetic = AbstractResultArithmetic.of(
            new TestAbstractArithmetic(),
            new TestAbstractArithmetic(),
            number -> number
        );
        Assertions.assertTrue(ofArithmetic.getTArithmetic() instanceof TestAbstractArithmetic);
        Assertions.assertTrue(ofArithmetic.getRArithmetic() instanceof TestAbstractArithmetic);
        Assertions.assertEquals(0, ofArithmetic.fromT(0));
        Assertions.assertEquals(-0d, ofArithmetic.fromT(-0d));
    }

    // endregion
}
