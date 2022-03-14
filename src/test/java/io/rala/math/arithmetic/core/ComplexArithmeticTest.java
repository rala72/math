package io.rala.math.arithmetic.core;

import io.rala.math.exception.NotSupportedException;
import io.rala.math.testUtils.algebra.TestComplex;
import io.rala.math.testUtils.arithmetic.TestComplexArithmetic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
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
        assertThat(arithmetic.fromInt(0)).isEqualTo(new TestComplex());
    }

    @Test
    void fromDouble0() {
        assertThat(arithmetic.fromDouble(0)).isEqualTo(new TestComplex(0d, 0d));
    }

    @Test
    void signum() {
        assertThat(arithmetic.signum(new TestComplex(1, 1))).isOne();
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteReM1Im0() {
        assertThat(arithmetic.absolute(new TestComplex(-1, 0)))
            .isEqualTo(new TestComplex(1d, -0d));
    }

    @Test
    void negate() {
        assertThat(arithmetic.negate(new TestComplex(1, 1)))
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
        assertThat(arithmetic.min(new TestComplex(1, 1), new TestComplex(1, 1))).isEqualTo(new TestComplex(1, 1));
        assertThat(arithmetic.min(new TestComplex(1, 1), new TestComplex(2, 2))).isEqualTo(new TestComplex(1, 1));
        assertThat(arithmetic.min(new TestComplex(2, 2), new TestComplex(1, 1))).isEqualTo(new TestComplex(1, 1));
    }

    @Test
    void max() {
        assertThat(arithmetic.max(new TestComplex(1, 1), new TestComplex(1, 1))).isEqualTo(new TestComplex(1, 1));
        assertThat(arithmetic.max(new TestComplex(1, 1), new TestComplex(2, 2))).isEqualTo(new TestComplex(2, 2));
        assertThat(arithmetic.max(new TestComplex(2, 2), new TestComplex(1, 1))).isEqualTo(new TestComplex(2, 2));
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
        assertThat(arithmetic.sum(new TestComplex(1, 1), new TestComplex(2, 2))).isEqualTo(new TestComplex(3d, 3d));
    }

    @Test
    void sum123() {
        assertThat(arithmetic.sum(
            new TestComplex(1, 1),
            new TestComplex(2, 2),
            new TestComplex(3, 3)
        )).isEqualTo(new TestComplex(6d, 6d));
    }

    @Test
    void difference12() {
        assertThat(arithmetic.difference(new TestComplex(1, 1), new TestComplex(2, 2))).isEqualTo(new TestComplex(-1d, -1d));
    }

    @Test
    void product12() {
        assertThat(arithmetic.product(new TestComplex(1, 1), new TestComplex(2, 2))).isEqualTo(new TestComplex(0d, 4d));
    }

    @Test
    void product123() {
        assertThat(arithmetic.product(
            new TestComplex(1, 1),
            new TestComplex(2, 2),
            new TestComplex(3, 3))
        ).isEqualTo(new TestComplex(-12d, 12d));
    }

    @Test
    void quotient12() {
        assertThat(arithmetic.quotient(new TestComplex(1, 1), new TestComplex(2, 2))).isEqualTo(new TestComplex(0.5, 0d));
    }

    @Test
    void modulo12() {
        assertThat(arithmetic.modulo(new TestComplex(1, 1), new TestComplex(2, 2))).isEqualTo(new TestComplex(0d, 0d));
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertThat(arithmetic.power(new TestComplex(1, 1), 2))
            .isEqualTo(new TestComplex(-3.2162452993532737e-16, 2.0000000000000004));
    }

    @Test
    void root21() {
        assertThat(arithmetic.root2(new TestComplex(1, 1)))
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
            .isThrownBy(() -> arithmetic.gcd(new TestComplex(1, 2), new TestComplex(3, 4))); // assert exception message?
    }

    @Test
    void lcm() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> arithmetic.lcm(new TestComplex(1, 2), new TestComplex(3, 4))); // assert exception message?
    }

    // endregion

    // region trigonometry

    @Test
    void sinOf1() {
        assertThat(arithmetic.sin(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.sin(1), 0d));
    }

    @Test
    void cosOf1() {
        assertThat(arithmetic.cos(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.cos(1), 0d));
    }

    @Test
    void tanOf1() {
        assertThat(arithmetic.tan(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.tan(1), 0d));
    }

    @Test
    void asinOf1() {
        assertThat(arithmetic.asin(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.asin(1), 0d));
    }

    @Test
    void acosOf1() {
        assertThat(arithmetic.acos(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.acos(1), 0d));
    }

    @Test
    void atanOf1() {
        assertThat(arithmetic.atan(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.atan(1), 0d));
    }

    @Test
    void sinhOf1() {
        assertThat(arithmetic.sinh(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.sinh(1), 0d));
    }

    @Test
    void coshOf1() {
        assertThat(arithmetic.cosh(new TestComplex(1, 1)))
            .isEqualTo(new TestComplex(Math.cosh(1), 0d));
    }

    @Test
    void tanhOf1() {
        assertThat(arithmetic.tanh(new TestComplex(1, 1)))
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
