package io.rala.math.arithmetic.result;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

class BigIntegerBigDecimalResultArithmeticTest {
    private static BigIntegerBigDecimalResultArithmetic arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new BigIntegerBigDecimalResultArithmetic();
    }

    @Test
    void constructorWithMathContext() {
        BigIntegerBigDecimalResultArithmetic arithmetic =
            new BigIntegerBigDecimalResultArithmetic(new MathContext(5));
        Assertions.assertTrue(arithmetic.getRArithmetic() instanceof BigDecimalArithmetic);
        BigDecimalArithmetic rArithmetic = (BigDecimalArithmetic) arithmetic.getRArithmetic();
        Assertions.assertEquals(5, rArithmetic.getMathContext().getPrecision());
    }

    // region fromT

    @Test
    void fromT1() {
        Assertions.assertEquals(BigDecimal.ONE, arithmetic.fromT(BigInteger.ONE));
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        Assertions.assertEquals(BigDecimal.valueOf(3),
            arithmetic.sum(BigInteger.ONE, BigInteger.valueOf(2))
        );
    }

    @Test
    void sum123() {
        Assertions.assertEquals(BigDecimal.valueOf(6),
            arithmetic.sum(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(3))
        );
    }

    @Test
    void difference12() {
        Assertions.assertEquals(BigDecimal.valueOf(-1),
            arithmetic.difference(BigInteger.ONE, BigInteger.valueOf(2))
        );
    }

    @Test
    void product12() {
        Assertions.assertEquals(BigDecimal.valueOf(2),
            arithmetic.product(BigInteger.ONE, BigInteger.valueOf(2))
        );
    }

    @Test
    void product123() {
        Assertions.assertEquals(BigDecimal.valueOf(6),
            arithmetic.product(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(3))
        );
    }

    @Test
    void quotient12() {
        Assertions.assertEquals(BigDecimal.valueOf(0.5),
            arithmetic.quotient(BigInteger.ONE, BigInteger.valueOf(2))
        );
    }

    @Test
    void modulo12() {
        Assertions.assertEquals(BigDecimal.ONE,
            arithmetic.modulo(BigInteger.ONE, BigInteger.valueOf(2))
        );
    }

    // endregion

    // region map

    @Test
    void mapToInteger() {
        AbstractResultArithmetic<BigInteger, Integer> mapped =
            arithmetic.map(new IntegerArithmetic(), BigInteger::intValue);
        Assertions.assertTrue(mapped.getRArithmetic() instanceof IntegerArithmetic);
        Assertions.assertEquals(BigDecimal.valueOf(0.5),
            arithmetic.quotient(BigInteger.ONE, BigInteger.TWO)
        );
        Assertions.assertEquals(0,
            mapped.quotient(BigInteger.ONE, BigInteger.TWO)
        );
    }

    // endregion

    // region override

    @Test
    void equalsOfAbstractResultArithmetic() {
        Assertions.assertEquals(
            new BigIntegerBigDecimalResultArithmetic(),
            new BigIntegerBigDecimalResultArithmetic()
        );
    }

    @Test
    void hashCodeOfAbstractResultArithmetic() {
        // hashCode of RoundingMode enum changing after every start
        Assertions.assertEquals(
            new BigIntegerBigDecimalResultArithmetic().hashCode(),
            new BigIntegerBigDecimalResultArithmetic().hashCode()
        );
    }

    @Test
    void toStringOfAbstractResultArithmetic() {
        String toString = "BigIntegerBigDecimalResultArithmetic{" +
            "tArithmetic=BigIntegerArithmetic, " +
            "rArithmetic=BigDecimalArithmetic}";
        Assertions.assertEquals(toString,
            new BigIntegerBigDecimalResultArithmetic().toString()
        );
    }

    // endregion
}
