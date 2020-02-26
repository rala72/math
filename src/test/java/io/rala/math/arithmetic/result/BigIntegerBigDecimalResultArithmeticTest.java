package io.rala.math.arithmetic.result;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BigIntegerBigDecimalResultArithmeticTest {
    private static BigIntegerBigDecimalResultArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new BigIntegerBigDecimalResultArithmetic();
    }

    @Test
    void getInstance() {
        assertEquals(arithmetic,
            BigIntegerBigDecimalResultArithmetic.getInstance()
        );
    }

    @Test
    void constructorWithMathContext() {
        BigIntegerBigDecimalResultArithmetic arithmetic =
            new BigIntegerBigDecimalResultArithmetic(new MathContext(5));
        assertTrue(
            arithmetic.getRArithmetic() instanceof BigDecimalArithmetic
        );
        BigDecimalArithmetic rArithmetic =
            (BigDecimalArithmetic) arithmetic.getRArithmetic();
        assertEquals(5, rArithmetic.getMathContext().getPrecision());
    }

    // region fromT

    @Test
    void fromT1() {
        assertEquals(BigDecimal.ONE, arithmetic.fromT(BigInteger.ONE));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertEquals(BigDecimal.valueOf(3),
            arithmetic.sum(BigInteger.ONE, BigInteger.valueOf(2))
        );
    }

    @Test
    void sum123() {
        assertEquals(BigDecimal.valueOf(6),
            arithmetic.sum(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(3))
        );
    }

    @Test
    void difference12() {
        assertEquals(BigDecimal.valueOf(-1),
            arithmetic.difference(BigInteger.ONE, BigInteger.valueOf(2))
        );
    }

    @Test
    void product12() {
        assertEquals(BigDecimal.valueOf(2),
            arithmetic.product(BigInteger.ONE, BigInteger.valueOf(2))
        );
    }

    @Test
    void product123() {
        assertEquals(BigDecimal.valueOf(6),
            arithmetic.product(
                BigInteger.ONE,
                BigInteger.valueOf(2),
                BigInteger.valueOf(3)
            )
        );
    }

    @Test
    void quotient12() {
        assertEquals(BigDecimal.valueOf(0.5),
            arithmetic.quotient(BigInteger.ONE, BigInteger.valueOf(2))
        );
    }

    @Test
    void modulo12() {
        assertEquals(BigDecimal.ONE,
            arithmetic.modulo(BigInteger.ONE, BigInteger.valueOf(2))
        );
    }

    // endregion

    // region map

    @Test
    void mapToInteger() {
        AbstractResultArithmetic<Integer, BigDecimal> mapped =
            arithmetic.map(new IntegerArithmetic(), BigDecimal::valueOf);
        assertTrue(mapped.getTArithmetic() instanceof IntegerArithmetic);
        assertEquals(BigDecimal.valueOf(0.5),
            arithmetic.quotient(BigInteger.ONE, BigInteger.TWO)
        );
        assertEquals(BigDecimal.valueOf(0.5),
            mapped.quotient(1, 2)
        );
    }

    @Test
    void mapResultToInteger() {
        AbstractResultArithmetic<BigInteger, Integer> mapped =
            arithmetic.mapResult(new IntegerArithmetic(), BigInteger::intValue);
        assertTrue(mapped.getRArithmetic() instanceof IntegerArithmetic);
        assertEquals(BigDecimal.valueOf(0.5),
            arithmetic.quotient(BigInteger.ONE, BigInteger.TWO)
        );
        assertEquals(0,
            mapped.quotient(BigInteger.ONE, BigInteger.TWO)
        );
    }

    // endregion

    // region override

    @Test
    void equalsOfAbstractResultArithmetic() {
        assertEquals(
            new BigIntegerBigDecimalResultArithmetic(),
            new BigIntegerBigDecimalResultArithmetic()
        );
    }

    @Test
    void hashCodeOfAbstractResultArithmetic() {
        // hashCode of RoundingMode enum changing after every start
        assertEquals(
            new BigIntegerBigDecimalResultArithmetic().hashCode(),
            new BigIntegerBigDecimalResultArithmetic().hashCode()
        );
    }

    @Test
    void toStringOfAbstractResultArithmetic() {
        String toString = "BigIntegerBigDecimalResultArithmetic{" +
            "tArithmetic=BigIntegerArithmetic, " +
            "rArithmetic=BigDecimalArithmetic}";
        assertEquals(toString,
            new BigIntegerBigDecimalResultArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        assertSerializable(
            new BigIntegerBigDecimalResultArithmetic(),
            BigIntegerBigDecimalResultArithmetic.class
        );
    }

    // endregion
}
