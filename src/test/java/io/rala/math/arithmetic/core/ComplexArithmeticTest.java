package io.rala.math.arithmetic.core;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.exception.NotSupportedException;
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
        assertThat(arithmetic.fromInt(0)).isEqualTo(create());
    }

    @Test
    void fromDouble0() {
        assertThat(arithmetic.fromDouble(0)).isEqualTo(create(0d, 0d));
    }

    @Test
    void signum() {
        assertThat(arithmetic.signum(create(1, 1))).isEqualTo(1);
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteReM1Im0() {
        assertThat(arithmetic.absolute(create(-1, 0))).isEqualTo(create(1d, -0d));
    }

    @Test
    void negate() {
        assertThat(arithmetic.negate(create(1, 1))).isEqualTo(create(-1d, -1d));
    }

    @Test
    void compare() {
        assertThat(arithmetic.compare(create(1, 1), create(1, 1))).isEqualTo(0);
        assertThat(arithmetic.compare(create(1, 1), create(2, 2))).isEqualTo(-1);
        assertThat(arithmetic.compare(create(2, 2), create(1, 1))).isEqualTo(1);
    }

    @Test
    void min() {
        assertThat(arithmetic.min(create(1, 1), create(1, 1))).isEqualTo(create(1, 1));
        assertThat(arithmetic.min(create(1, 1), create(2, 2))).isEqualTo(create(1, 1));
        assertThat(arithmetic.min(create(2, 2), create(1, 1))).isEqualTo(create(1, 1));
    }

    @Test
    void max() {
        assertThat(arithmetic.max(create(1, 1), create(1, 1))).isEqualTo(create(1, 1));
        assertThat(arithmetic.max(create(1, 1), create(2, 2))).isEqualTo(create(2, 2));
        assertThat(arithmetic.max(create(2, 2), create(1, 1))).isEqualTo(create(2, 2));
    }

    @Test
    void isZero() {
        assertThat(arithmetic.isZero(create(0d, 0d))).isTrue();
        assertThat(arithmetic.isZero(create(-0d, -0d))).isTrue();
        assertThat(arithmetic.isZero(create(1d, 1d))).isFalse();
    }

    @Test
    void isEqual() {
        assertThat(arithmetic.isEqual(create(0d, 0d), create(0d, 0d))).isTrue();
        assertThat(arithmetic.isEqual(create(-0d, -0d), create(0d, 0d))).isTrue();
        assertThat(arithmetic.isEqual(create(-0d, -0d), create(-0d, -0d))).isTrue();
        assertThat(arithmetic.isEqual(create(1d, 1d), create(0d, 0d))).isFalse();
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertThat(arithmetic.sum(create(1, 1), create(2, 2))).isEqualTo(create(3d, 3d));
    }

    @Test
    void sum123() {
        assertThat(arithmetic.sum(
            create(1, 1),
            create(2, 2),
            create(3, 3)
        )).isEqualTo(create(6d, 6d));
    }

    @Test
    void difference12() {
        assertThat(arithmetic.difference(create(1, 1), create(2, 2))).isEqualTo(create(-1d, -1d));
    }

    @Test
    void product12() {
        assertThat(arithmetic.product(create(1, 1), create(2, 2))).isEqualTo(create(0d, 4d));
    }

    @Test
    void product123() {
        assertThat(arithmetic.product(create(1, 1), create(2, 2), create(3, 3))).isEqualTo(create(-12d, 12d));
    }

    @Test
    void quotient12() {
        assertThat(arithmetic.quotient(create(1, 1), create(2, 2))).isEqualTo(create(0.5, 0d));
    }

    @Test
    void modulo12() {
        assertThat(arithmetic.modulo(create(1, 1), create(2, 2))).isEqualTo(create(0d, 0d));
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertThat(arithmetic.power(create(1, 1), 2)).isEqualTo(create(-3.2162452993532737e-16, 2.0000000000000004));
    }

    @Test
    void root21() {
        assertThat(arithmetic.root2(create(1, 1))).isEqualTo(create(1.0986841134678098, 0.45508986056222733));
    }

    // endregion

    // region isFinite, isInfinite and isNaN

    @Test
    void isFinite() {
        assertThat(arithmetic.isFinite(create(0, 0))).isTrue();
        assertThat(arithmetic.isFinite(
            create(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        )).isFalse();
        assertThat(arithmetic.isFinite(
            create(Double.NaN, Double.NaN)
        )).isFalse();
    }

    @Test
    void isInfinite() {
        assertThat(arithmetic.isInfinite(create(0, 0))).isFalse();
        assertThat(arithmetic.isInfinite(
            create(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        )).isTrue();
        assertThat(arithmetic.isInfinite(
            create(Double.NaN, Double.NaN)
        )).isFalse();
    }

    @Test
    void isNaN() {
        assertThat(arithmetic.isNaN(create(0, 0))).isFalse();
        assertThat(arithmetic.isNaN(
            create(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        )).isFalse();
        assertThat(arithmetic.isNaN(
            create(Double.NaN, Double.NaN)
        )).isTrue();
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> arithmetic.gcd(create(1, 2), create(3, 4))); // assert exception message?
    }

    @Test
    void lcm() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> arithmetic.lcm(create(1, 2), create(3, 4))); // assert exception message?
    }

    // endregion

    // region trigonometry

    @Test
    void sinOf1() {
        assertThat(arithmetic.sin(create(1, 1))).isEqualTo(create(Math.sin(1), 0d));
    }

    @Test
    void cosOf1() {
        assertThat(arithmetic.cos(create(1, 1))).isEqualTo(create(Math.cos(1), 0d));
    }

    @Test
    void tanOf1() {
        assertThat(arithmetic.tan(create(1, 1))).isEqualTo(create(Math.tan(1), 0d));
    }

    @Test
    void asinOf1() {
        assertThat(arithmetic.asin(create(1, 1))).isEqualTo(create(Math.asin(1), 0d));
    }

    @Test
    void acosOf1() {
        assertThat(arithmetic.acos(create(1, 1))).isEqualTo(create(Math.acos(1), 0d));
    }

    @Test
    void atanOf1() {
        assertThat(arithmetic.atan(create(1, 1))).isEqualTo(create(Math.atan(1), 0d));
    }

    @Test
    void sinhOf1() {
        assertThat(arithmetic.sinh(create(1, 1))).isEqualTo(create(Math.sinh(1), 0d));
    }

    @Test
    void coshOf1() {
        assertThat(arithmetic.cosh(create(1, 1))).isEqualTo(create(Math.cosh(1), 0d));
    }

    @Test
    void tanhOf1() {
        assertThat(arithmetic.tanh(create(1, 1))).isEqualTo(create(Math.tanh(1), 0d));
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
        assertThat(new TestComplexArithmetic().toString()).isEqualTo("TestComplexArithmetic");
    }

    @Test
    void serializable() {
        assertSerializable(
            new TestComplexArithmetic(),
            TestComplexArithmetic.class
        );
    }

    // endregion


    // region create

    private Complex<Number> create() {
        return new Complex<>(arithmetic.getArithmetic());
    }

    private Complex<Number> create(Number a, Number b) {
        return new Complex<>(arithmetic.getArithmetic(), a, b);
    }

    // endregion
}
