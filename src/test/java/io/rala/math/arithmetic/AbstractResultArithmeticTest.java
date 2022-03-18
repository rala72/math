package io.rala.math.arithmetic;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import io.rala.math.testUtils.arithmetic.TestAbstractResultArithmetic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class AbstractResultArithmeticTest {
    private AbstractResultArithmetic<Number, Number> arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new TestAbstractResultArithmetic();
    }

    @Test
    void getArithmetic() {
        assertThat(arithmetic.getTArithmetic()).isInstanceOf(TestAbstractArithmetic.class);
        assertThat(arithmetic.getRArithmetic()).isInstanceOf(TestAbstractArithmetic.class);
    }

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertThat(arithmetic.sum(1, 2)).isEqualTo(3d);
    }

    @Test
    void sum123() {
        assertThat(arithmetic.sum(1, 2, 3)).isEqualTo(6d);
    }

    @Test
    void difference12() {
        assertThat(arithmetic.difference(1, 2)).isEqualTo(-1d);
    }

    @Test
    void product12() {
        assertThat(arithmetic.product(1, 2)).isEqualTo(2d);
    }

    @Test
    void product123() {
        assertThat(arithmetic.product(1, 2, 3)).isEqualTo(6d);
    }

    @Test
    void quotient12() {
        assertThat(arithmetic.quotient(1, 2)).isEqualTo(0.5);
    }

    @Test
    void modulo12() {
        assertThat(arithmetic.modulo(1, 2)).isEqualTo(0d);
    }

    // endregion

    // region map

    @Test
    void mapToInteger() {
        AbstractResultArithmetic<Integer, Number> mapped =
            arithmetic.map(new IntegerArithmetic(), Number::intValue);
        assertThat(mapped.getTArithmetic()).isInstanceOf(IntegerArithmetic.class);
        assertThat(arithmetic.quotient(1, 2)).isEqualTo(0.5);
        assertThat(mapped.quotient(1, 2)).isEqualTo(0.5);
    }

    @Test
    void mapResultToInteger() {
        AbstractResultArithmetic<Number, Integer> mapped =
            arithmetic.mapResult(new IntegerArithmetic(), Number::intValue);
        assertThat(mapped.getRArithmetic()).isInstanceOf(IntegerArithmetic.class);
        assertThat(arithmetic.quotient(1, 2)).isEqualTo(0.5);
        assertThat(mapped.quotient(1, 2)).isZero();
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
        assertThat(ofArithmetic.getTArithmetic()).isInstanceOf(TestAbstractArithmetic.class);
        assertThat(ofArithmetic.getRArithmetic()).isInstanceOf(TestAbstractArithmetic.class);
        assertThat(ofArithmetic.fromT(0)).isEqualTo(0);
        assertThat(ofArithmetic.fromT(-0d)).isEqualTo(-0d);
    }

    // endregion

    // region override

    @Test
    void equalsOfAbstractResultArithmetic() {
        assertThat(new TestAbstractResultArithmetic()).isEqualTo(new TestAbstractResultArithmetic());
    }

    @Test
    void hashCodeOfAbstractResultArithmetic() {
        assertThat(new TestAbstractResultArithmetic().hashCode()).isEqualTo(-33522719);
    }

    @Test
    void toStringOfAbstractResultArithmetic() {
        String toString = "TestAbstractResultArithmetic{" +
            "tArithmetic=TestAbstractArithmetic, " +
            "rArithmetic=TestAbstractArithmetic}";
        assertThat(new TestAbstractResultArithmetic()).hasToString(toString);
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
