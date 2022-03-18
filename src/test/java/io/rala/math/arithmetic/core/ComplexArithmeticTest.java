package io.rala.math.arithmetic.core;

import io.rala.math.exception.NotSupportedException;
import io.rala.math.testUtils.algebra.TestComplex;
import io.rala.math.testUtils.arithmetic.TestComplexArithmetic;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatComplex;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ComplexArithmeticTest {
    private ComplexArithmetic<Number> arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new TestComplexArithmetic();
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt0() {
        assertThatComplex(arithmetic.fromInt(0)).isEqualTo(new TestComplex());
    }

    @Test
    void fromDouble0() {
        assertThatComplex(arithmetic.fromDouble(0)).isEqualTo(new TestComplex(0d, 0d));
    }

    @Test
    void signum() {
        assertThat(arithmetic.signum(new TestComplex(1, 1))).isOne();
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteReM1Im0() {
        assertThatComplex(arithmetic.absolute(new TestComplex(-1, 0)))
            .isEqualTo(new TestComplex(1d, -0d));
    }

    @Test
    void negate() {
        assertThatComplex(arithmetic.negate(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(-1d, -1d));
    }

    @Test
    void compare() {
        assertThat(arithmetic.compare(
            new TestComplex(1, 1),
            new TestComplex(1, 1))
        ).isZero();
        assertThat(arithmetic.compare(new TestComplex(1, 1),
            new TestComplex(2, 2))
        ).isEqualTo(-1);
        assertThat(arithmetic.compare(new TestComplex(2, 2),
            new TestComplex(1, 1))
        ).isOne();
    }

    @Test
    void min() {
        assertThatComplex(arithmetic.min(new TestComplex(1, 1), new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(1, 1));
        assertThatComplex(arithmetic.min(new TestComplex(1, 1), new TestComplex(2, 2)))
            .isEqualTo(new TestComplex(1, 1));
        assertThatComplex(arithmetic.min(new TestComplex(2, 2), new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(1, 1));
    }

    @Test
    void max() {
        assertThatComplex(arithmetic.max(new TestComplex(1, 1), new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(1, 1));
        assertThatComplex(arithmetic.max(new TestComplex(1, 1), new TestComplex(2, 2)))
            .isEqualTo(new TestComplex(2, 2));
        assertThatComplex(arithmetic.max(new TestComplex(2, 2), new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(2, 2));
    }

    @Test
    void isZero() {
        assertThat(arithmetic.isZero(new TestComplex(0d, 0d))).isTrue();
        assertThat(arithmetic.isZero(new TestComplex(-0d, -0d))).isTrue();
        assertThat(arithmetic.isZero(new TestComplex(1d, 1d))).isFalse();
    }

    @Test
    void isEqual() {
        assertThat(arithmetic.isEqual(
            new TestComplex(0d, 0d),
            new TestComplex(0d, 0d))
        ).isTrue();
        assertThat(arithmetic.isEqual(
            new TestComplex(-0d, -0d),
            new TestComplex(0d, 0d))
        ).isTrue();
        assertThat(arithmetic.isEqual(
            new TestComplex(-0d, -0d),
            new TestComplex(-0d, -0d))
        ).isTrue();
        assertThat(arithmetic.isEqual(
            new TestComplex(1d, 1d),
            new TestComplex(0d, 0d))
        ).isFalse();
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertThatComplex(arithmetic.sum(new TestComplex(1, 1), new TestComplex(2, 2)))
            .isEqualTo(new TestComplex(3d, 3d));
    }

    @Test
    void sum123() {
        assertThatComplex(arithmetic.sum(
            new TestComplex(1, 1),
            new TestComplex(2, 2),
            new TestComplex(3, 3)
        )).isEqualTo(new TestComplex(6d, 6d));
    }

    @Test
    void difference12() {
        assertThatComplex(arithmetic.difference(new TestComplex(1, 1), new TestComplex(2, 2)))
            .isEqualTo(new TestComplex(-1d, -1d));
    }

    @Test
    void product12() {
        assertThatComplex(arithmetic.product(new TestComplex(1, 1), new TestComplex(2, 2)))
            .isEqualTo(new TestComplex(0d, 4d));
    }

    @Test
    void product123() {
        assertThatComplex(arithmetic.product(
            new TestComplex(1, 1),
            new TestComplex(2, 2),
            new TestComplex(3, 3))
        ).isEqualTo(new TestComplex(-12d, 12d));
    }

    @Test
    void quotient12() {
        assertThatComplex(arithmetic.quotient(new TestComplex(1, 1), new TestComplex(2, 2)))
            .isEqualTo(new TestComplex(0.5, 0d));
    }

    @Test
    void modulo12() {
        assertThatComplex(arithmetic.modulo(new TestComplex(1, 1), new TestComplex(2, 2)))
            .isEqualTo(new TestComplex(0d, 0d));
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertThatComplex(arithmetic.power(new TestComplex(1, 1), 2))
            .hasRe(-3.2162452993532737e-16).hasIm(2d);
    }

    @Test
    void root21() {
        assertThatComplex(arithmetic.root2(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(1.0986841134678098, 0.45508986056222733));
    }

    // endregion

    // region isFinite, isInfinite and isNaN

    @Test
    void isFinite() {
        assertThat(arithmetic.isFinite(new TestComplex(0, 0))).isTrue();
        assertThat(arithmetic.isFinite(
            new TestComplex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        )).isFalse();
        assertThat(arithmetic.isFinite(
            new TestComplex(Double.NaN, Double.NaN)
        )).isFalse();
    }

    @Test
    void isInfinite() {
        assertThat(arithmetic.isInfinite(new TestComplex(0, 0))).isFalse();
        assertThat(arithmetic.isInfinite(
            new TestComplex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        )).isTrue();
        assertThat(arithmetic.isInfinite(
            new TestComplex(Double.NaN, Double.NaN)
        )).isFalse();
    }

    @Test
    void isNaN() {
        assertThat(arithmetic.isNaN(new TestComplex(0, 0))).isFalse();
        assertThat(arithmetic.isNaN(
            new TestComplex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        )).isFalse();
        assertThat(arithmetic.isNaN(
            new TestComplex(Double.NaN, Double.NaN)
        )).isTrue();
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> arithmetic.gcd(new TestComplex(1, 2), new TestComplex(3, 4)))
            .withMessage(ExceptionMessages.METHOD_NOT_SUPPORTED);
    }

    @Test
    void lcm() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> arithmetic.lcm(new TestComplex(1, 2), new TestComplex(3, 4)))
            .withMessage(ExceptionMessages.METHOD_NOT_SUPPORTED);
    }

    // endregion

    // region trigonometry

    @Test
    void sinOf1() {
        assertThatComplex(arithmetic.sin(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.sin(1), 0d));
    }

    @Test
    void cosOf1() {
        assertThatComplex(arithmetic.cos(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.cos(1), 0d));
    }

    @Test
    void tanOf1() {
        assertThatComplex(arithmetic.tan(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.tan(1), 0d));
    }

    @Test
    void asinOf1() {
        assertThatComplex(arithmetic.asin(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.asin(1), 0d));
    }

    @Test
    void acosOf1() {
        assertThatComplex(arithmetic.acos(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.acos(1), 0d));
    }

    @Test
    void atanOf1() {
        assertThatComplex(arithmetic.atan(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.atan(1), 0d));
    }

    @Test
    void sinhOf1() {
        assertThatComplex(arithmetic.sinh(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.sinh(1), 0d));
    }

    @Test
    void coshOf1() {
        assertThatComplex(arithmetic.cosh(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.cosh(1), 0d));
    }

    @Test
    void tanhOf1() {
        assertThatComplex(arithmetic.tanh(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.tanh(1), 0d));
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertThat(new TestComplexArithmetic()).isEqualTo(new TestComplexArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        assertThat(new TestComplexArithmetic().hashCode()).isEqualTo(-33521727);
    }

    @Test
    void toStringOfArithmetic() {
        assertThat(new TestComplexArithmetic()).hasToString("TestComplexArithmetic");
    }

    @Test
    void serializable() {
        assertSerializable(
            new TestComplexArithmetic(),
            TestComplexArithmetic.class
        );
    }

    // endregion
}
