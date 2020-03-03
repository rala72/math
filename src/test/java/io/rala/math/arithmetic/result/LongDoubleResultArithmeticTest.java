package io.rala.math.arithmetic.result;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LongDoubleResultArithmeticTest {
    private LongDoubleResultArithmetic arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new LongDoubleResultArithmetic();
    }

    @Test
    void getInstance() {
        assertEquals(arithmetic,
            LongDoubleResultArithmetic.getInstance()
        );
    }

    // region fromT

    @Test
    void fromT1() {
        assertEquals(1, arithmetic.fromT(1L));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertEquals(3, arithmetic.sum(1L, 2L));
    }

    @Test
    void sum123() {
        assertEquals(6, arithmetic.sum(1L, 2L, 3L));
    }

    @Test
    void difference12() {
        assertEquals(-1, arithmetic.difference(1L, 2L));
    }

    @Test
    void product12() {
        assertEquals(2, arithmetic.product(1L, 2L));
    }

    @Test
    void product123() {
        assertEquals(6, arithmetic.product(1L, 2L, 3L));
    }

    @Test
    void quotient12() {
        assertEquals(0.5, arithmetic.quotient(1L, 2L));
    }

    @Test
    void modulo12() {
        assertEquals(1, arithmetic.modulo(1L, 2L));
    }

    // endregion

    // region map

    @Test
    void mapToInteger() {
        AbstractResultArithmetic<Integer, Double> mapped =
            arithmetic.map(new IntegerArithmetic(), Number::doubleValue);
        assertTrue(mapped.getTArithmetic() instanceof IntegerArithmetic);
        assertEquals(0.5,
            arithmetic.quotient(1L, 2L)
        );
        assertEquals(0.5,
            mapped.quotient(1, 2)
        );
    }

    @Test
    void mapResultToInteger() {
        AbstractResultArithmetic<Long, Integer> mapped =
            arithmetic.mapResult(new IntegerArithmetic(), Long::intValue);
        assertTrue(mapped.getRArithmetic() instanceof IntegerArithmetic);
        assertEquals(0.5, arithmetic.quotient(1L, 2L));
        assertEquals(0, mapped.quotient(1L, 2L));
    }

    // endregion

    // region override

    @Test
    void equalsOfAbstractResultArithmetic() {
        assertEquals(
            new LongDoubleResultArithmetic(),
            new LongDoubleResultArithmetic()
        );
    }

    @Test
    void hashCodeOfAbstractResultArithmetic() {
        assertEquals(1072724992, new LongDoubleResultArithmetic().hashCode());
    }

    @Test
    void toStringOfAbstractResultArithmetic() {
        String toString = "LongDoubleResultArithmetic{" +
            "tArithmetic=LongArithmetic, " +
            "rArithmetic=DoubleArithmetic}";
        assertEquals(toString,
            new LongDoubleResultArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        assertSerializable(
            new LongDoubleResultArithmetic(),
            LongDoubleResultArithmetic.class
        );
    }

    // endregion
}
