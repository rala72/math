package io.rala.math.arithmetic;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import io.rala.math.testUtils.arithmetic.TestAbstractResultArithmetic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractResultArithmeticTest {
    private AbstractResultArithmetic<Number, Number> arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new TestAbstractResultArithmetic();
    }

    @Test
    void getArithmetic() {
        assertTrue(
            arithmetic.getTArithmetic() instanceof TestAbstractArithmetic
        );
        assertTrue(
            arithmetic.getRArithmetic() instanceof TestAbstractArithmetic
        );
    }

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertEquals(3d, arithmetic.sum(1, 2));
    }

    @Test
    void sum123() {
        assertEquals(6d, arithmetic.sum(1, 2, 3));
    }

    @Test
    void difference12() {
        assertEquals(-1d, arithmetic.difference(1, 2));
    }

    @Test
    void product12() {
        assertEquals(2d, arithmetic.product(1, 2));
    }

    @Test
    void product123() {
        assertEquals(6d, arithmetic.product(1, 2, 3));
    }

    @Test
    void quotient12() {
        assertEquals(0.5, arithmetic.quotient(1, 2));
    }

    @Test
    void modulo12() {
        assertEquals(0d, arithmetic.modulo(1, 2));
    }

    // endregion

    // region map

    @Test
    void mapToInteger() {
        AbstractResultArithmetic<Integer, Number> mapped =
            arithmetic.map(new IntegerArithmetic(), Number::intValue);
        assertTrue(mapped.getTArithmetic() instanceof IntegerArithmetic);
        assertEquals(0.5, arithmetic.quotient(1, 2));
        assertEquals(0.5, mapped.quotient(1, 2));
    }

    @Test
    void mapResultToInteger() {
        AbstractResultArithmetic<Number, Integer> mapped =
            arithmetic.mapResult(new IntegerArithmetic(), Number::intValue);
        assertTrue(mapped.getRArithmetic() instanceof IntegerArithmetic);
        assertEquals(0.5, arithmetic.quotient(1, 2));
        assertEquals(0, mapped.quotient(1, 2));
    }

    // endregion

    // region static of

    @Test
    void of() {
        AbstractResultArithmetic<Number, Number> ofArithmetic =
            AbstractResultArithmetic.of(
                new TestAbstractArithmetic(),
                new TestAbstractArithmetic(),
                number -> number
            );
        assertTrue(
            ofArithmetic.getTArithmetic() instanceof TestAbstractArithmetic
        );
        assertTrue(
            ofArithmetic.getRArithmetic() instanceof TestAbstractArithmetic
        );
        assertEquals(0, ofArithmetic.fromT(0));
        assertEquals(-0d, ofArithmetic.fromT(-0d));
    }

    // endregion

    // region override

    @Test
    void equalsOfAbstractResultArithmetic() {
        assertEquals(
            new TestAbstractResultArithmetic(),
            new TestAbstractResultArithmetic()
        );
    }

    @Test
    void hashCodeOfAbstractResultArithmetic() {
        assertEquals(-33522719, new TestAbstractResultArithmetic().hashCode());
    }

    @Test
    void toStringOfAbstractResultArithmetic() {
        String toString = "TestAbstractResultArithmetic{" +
            "tArithmetic=TestAbstractArithmetic, " +
            "rArithmetic=TestAbstractArithmetic}";
        assertEquals(toString,
            new TestAbstractResultArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        assertSerializable(
            new TestAbstractResultArithmetic(),
            TestAbstractResultArithmetic.class
        );
    }

    // endregion
}
