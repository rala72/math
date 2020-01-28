package io.rala.math.arithmetic.result;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LongDoubleResultArithmeticTest {
    private static LongDoubleResultArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new LongDoubleResultArithmetic();
    }

    // region fromT

    @Test
    void fromT1() {
        Assertions.assertEquals(1, arithmetic.fromT(1L));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        Assertions.assertEquals(3, arithmetic.sum(1L, 2L));
    }

    @Test
    void sum123() {
        Assertions.assertEquals(6, arithmetic.sum(1L, 2L, 3L));
    }

    @Test
    void difference12() {
        Assertions.assertEquals(-1, arithmetic.difference(1L, 2L));
    }

    @Test
    void product12() {
        Assertions.assertEquals(2, arithmetic.product(1L, 2L));
    }

    @Test
    void product123() {
        Assertions.assertEquals(6, arithmetic.product(1L, 2L, 3L));
    }

    @Test
    void quotient12() {
        Assertions.assertEquals(0.5, arithmetic.quotient(1L, 2L));
    }

    @Test
    void modulo12() {
        Assertions.assertEquals(1, arithmetic.modulo(1L, 2L));
    }

    // endregion

    // region map

    @Test
    void mapToInteger() {
        AbstractResultArithmetic<Integer, Double> mapped =
            arithmetic.map(new IntegerArithmetic(), Number::doubleValue);
        Assertions.assertTrue(mapped.getTArithmetic() instanceof IntegerArithmetic);
        Assertions.assertEquals(0.5,
            arithmetic.quotient(1L, 2L)
        );
        Assertions.assertEquals(0.5,
            mapped.quotient(1, 2)
        );
    }

    @Test
    void mapResultToInteger() {
        AbstractResultArithmetic<Long, Integer> mapped =
            arithmetic.mapResult(new IntegerArithmetic(), Long::intValue);
        Assertions.assertTrue(mapped.getRArithmetic() instanceof IntegerArithmetic);
        Assertions.assertEquals(0.5, arithmetic.quotient(1L, 2L));
        Assertions.assertEquals(0, mapped.quotient(1L, 2L));
    }

    // endregion

    // region override

    @Test
    void equalsOfAbstractResultArithmetic() {
        Assertions.assertEquals(
            new LongDoubleResultArithmetic(),
            new LongDoubleResultArithmetic()
        );
    }

    @Test
    void hashCodeOfAbstractResultArithmetic() {
        Assertions.assertEquals(1072724992,
            new LongDoubleResultArithmetic().hashCode()
        );
    }

    @Test
    void toStringOfAbstractResultArithmetic() {
        String toString = "LongDoubleResultArithmetic{" +
            "tArithmetic=LongArithmetic, " +
            "rArithmetic=DoubleArithmetic}";
        Assertions.assertEquals(toString,
            new LongDoubleResultArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
            new LongDoubleResultArithmetic(),
            LongDoubleResultArithmetic.class
        );
    }

    // endregion
}
