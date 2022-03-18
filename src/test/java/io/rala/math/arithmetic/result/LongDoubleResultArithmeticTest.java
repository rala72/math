package io.rala.math.arithmetic.result;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class LongDoubleResultArithmeticTest {
    private LongDoubleResultArithmetic arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new LongDoubleResultArithmetic();
    }

    @Test
    void getInstance() {
        assertThat(LongDoubleResultArithmetic.getInstance()).isEqualTo(arithmetic);
    }

    // region fromT

    @Test
    void fromT1() {
        assertThat(arithmetic.fromT(1L)).isOne();
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertThat(arithmetic.sum(1L, 2L)).isEqualTo(3);
    }

    @Test
    void sum123() {
        assertThat(arithmetic.sum(1L, 2L, 3L)).isEqualTo(6);
    }

    @Test
    void difference12() {
        assertThat(arithmetic.difference(1L, 2L)).isEqualTo(-1);
    }

    @Test
    void product12() {
        assertThat(arithmetic.product(1L, 2L)).isEqualTo(2);
    }

    @Test
    void product123() {
        assertThat(arithmetic.product(1L, 2L, 3L)).isEqualTo(6);
    }

    @Test
    void quotient12() {
        assertThat(arithmetic.quotient(1L, 2L)).isEqualTo(0.5);
    }

    @Test
    void modulo12() {
        assertThat(arithmetic.modulo(1L, 2L)).isOne();
    }

    // endregion

    // region map

    @Test
    void mapToInteger() {
        AbstractResultArithmetic<Integer, Double> mapped =
            arithmetic.map(new IntegerArithmetic(), Number::doubleValue);
        assertThat(mapped.getTArithmetic()).isInstanceOf(IntegerArithmetic.class);
        assertThat(arithmetic.quotient(1L, 2L)).isEqualTo(0.5);
        assertThat(mapped.quotient(1, 2)).isEqualTo(0.5);
    }

    @Test
    void mapResultToInteger() {
        AbstractResultArithmetic<Long, Integer> mapped =
            arithmetic.mapResult(new IntegerArithmetic(), Long::intValue);
        assertThat(mapped.getRArithmetic()).isInstanceOf(IntegerArithmetic.class);
        assertThat(arithmetic.quotient(1L, 2L)).isEqualTo(0.5);
        assertThat(mapped.quotient(1L, 2L)).isZero();
    }

    // endregion

    // region override

    @Test
    void equalsOfAbstractResultArithmetic() {
        assertThat(new LongDoubleResultArithmetic()).isEqualTo(new LongDoubleResultArithmetic());
    }

    @Test
    void hashCodeOfAbstractResultArithmetic() {
        assertThat(new LongDoubleResultArithmetic().hashCode()).isEqualTo(1072724992);
    }

    @Test
    void toStringOfAbstractResultArithmetic() {
        String toString = "LongDoubleResultArithmetic{" +
            "tArithmetic=LongArithmetic, " +
            "rArithmetic=DoubleArithmetic}";
        assertThat(new LongDoubleResultArithmetic()).hasToString(toString);
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
