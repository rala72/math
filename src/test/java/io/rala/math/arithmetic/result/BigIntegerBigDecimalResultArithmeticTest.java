package io.rala.math.arithmetic.result;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class BigIntegerBigDecimalResultArithmeticTest {
    private BigIntegerBigDecimalResultArithmetic arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new BigIntegerBigDecimalResultArithmetic();
    }

    @Test
    void getInstance() {
        assertThat(BigIntegerBigDecimalResultArithmetic.getInstance()).isEqualTo(arithmetic);
    }

    @Test
    void constructorWithMathContext() {
        BigIntegerBigDecimalResultArithmetic arithmetic =
            new BigIntegerBigDecimalResultArithmetic(new MathContext(5));
        assertThat(arithmetic.getRArithmetic() instanceof BigDecimalArithmetic).isTrue();
        BigDecimalArithmetic rArithmetic =
            (BigDecimalArithmetic) arithmetic.getRArithmetic();
        assertThat(rArithmetic.getMathContext().getPrecision()).isEqualTo(5);
    }

    // region fromT

    @Test
    void fromT1() {
        assertThat(arithmetic.fromT(BigInteger.ONE)).isEqualTo(BigDecimal.ONE);
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertThat(arithmetic.sum(BigInteger.ONE, BigInteger.valueOf(2))).isEqualTo(BigDecimal.valueOf(3));
    }

    @Test
    void sum123() {
        assertThat(arithmetic.sum(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(3)))
            .isEqualTo(BigDecimal.valueOf(6));
    }

    @Test
    void difference12() {
        assertThat(arithmetic.difference(BigInteger.ONE, BigInteger.valueOf(2))).isEqualTo(BigDecimal.valueOf(-1));
    }

    @Test
    void product12() {
        assertThat(arithmetic.product(BigInteger.ONE, BigInteger.valueOf(2))).isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void product123() {
        assertThat(arithmetic.product(
            BigInteger.ONE,
            BigInteger.valueOf(2),
            BigInteger.valueOf(3)
        )).isEqualTo(BigDecimal.valueOf(6));
    }

    @Test
    void quotient12() {
        assertThat(arithmetic.quotient(BigInteger.ONE, BigInteger.valueOf(2))).isEqualTo(BigDecimal.valueOf(0.5));
    }

    @Test
    void modulo12() {
        assertThat(arithmetic.modulo(BigInteger.ONE, BigInteger.valueOf(2))).isEqualTo(BigDecimal.ONE);
    }

    // endregion

    // region map

    @Test
    void mapToInteger() {
        AbstractResultArithmetic<Integer, BigDecimal> mapped =
            arithmetic.map(new IntegerArithmetic(), BigDecimal::valueOf);
        assertThat(mapped.getTArithmetic() instanceof IntegerArithmetic).isTrue();
        assertThat(arithmetic.quotient(BigInteger.ONE, BigInteger.TWO)).isEqualTo(BigDecimal.valueOf(0.5));
        assertThat(mapped.quotient(1, 2)).isEqualTo(BigDecimal.valueOf(0.5));
    }

    @Test
    void mapResultToInteger() {
        AbstractResultArithmetic<BigInteger, Integer> mapped =
            arithmetic.mapResult(new IntegerArithmetic(), BigInteger::intValue);
        assertThat(mapped.getRArithmetic() instanceof IntegerArithmetic).isTrue();
        assertThat(arithmetic.quotient(BigInteger.ONE, BigInteger.TWO)).isEqualTo(BigDecimal.valueOf(0.5));
        assertThat(mapped.quotient(BigInteger.ONE, BigInteger.TWO)).isEqualTo(0);
    }

    // endregion

    // region override

    @Test
    void equalsOfAbstractResultArithmetic() {
        assertThat(new BigIntegerBigDecimalResultArithmetic()).isEqualTo(new BigIntegerBigDecimalResultArithmetic());
    }

    @Test
    void hashCodeOfAbstractResultArithmetic() {
        // hashCode of RoundingMode enum changing after every start
        assertThat(new BigIntegerBigDecimalResultArithmetic().hashCode())
            .isEqualTo(new BigIntegerBigDecimalResultArithmetic().hashCode());
    }

    @Test
    void toStringOfAbstractResultArithmetic() {
        String toString = "BigIntegerBigDecimalResultArithmetic{" +
            "tArithmetic=BigIntegerArithmetic, " +
            "rArithmetic=BigDecimalArithmetic}";
        assertThat(new BigIntegerBigDecimalResultArithmetic().toString()).isEqualTo(toString);
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
