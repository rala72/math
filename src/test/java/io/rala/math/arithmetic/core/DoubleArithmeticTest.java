package io.rala.math.arithmetic.core;

import io.rala.math.exception.NotSupportedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DoubleArithmeticTest {
    private DoubleArithmetic arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new DoubleArithmetic();
    }

    @Test
    void getInstance() {
        assertThat(DoubleArithmetic.getInstance()).isEqualTo(arithmetic);
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        assertThat(arithmetic.fromInt(1)).isEqualTo(1);
    }

    @Test
    void fromDouble1_1() {
        assertThat(arithmetic.fromDouble(1.1)).isEqualTo(1.1);
    }

    @Test
    void signum1() {
        assertThat(arithmetic.signum(1d)).isEqualTo(1);
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertThat(arithmetic.absolute(-1d)).isEqualTo(1);
    }

    @Test
    void negate1() {
        assertThat(arithmetic.negate(1d)).isEqualTo(-1);
    }

    @Test
    void compare() {
        assertThat(arithmetic.compare(1d, 1d)).isZero();
        assertThat(arithmetic.compare(1d, 2d)).isEqualTo(-1);
        assertThat(arithmetic.compare(2d, 1d)).isEqualTo(1);
    }

    @Test
    void min() {
        assertThat(arithmetic.min(1d, 1d)).isEqualTo(1);
        assertThat(arithmetic.min(1d, 2d)).isEqualTo(1);
        assertThat(arithmetic.min(2d, 1d)).isEqualTo(1);
    }

    @Test
    void max() {
        assertThat(arithmetic.max(1d, 1d)).isEqualTo(1);
        assertThat(arithmetic.max(1d, 2d)).isEqualTo(2);
        assertThat(arithmetic.max(2d, 1d)).isEqualTo(2);
    }

    @Test
    void isZero() {
        assertThat(arithmetic.isZero(0d)).isTrue();
        assertThat(arithmetic.isZero(-0d)).isTrue();
        assertThat(arithmetic.isZero(1d)).isFalse();
    }

    @Test
    void isEqual() {
        assertThat(arithmetic.isEqual(0d, 0d)).isTrue();
        assertThat(arithmetic.isEqual(-0d, 0d)).isTrue();
        assertThat(arithmetic.isEqual(-0d, -0d)).isTrue();
        assertThat(arithmetic.isEqual(1d, 0d)).isFalse();
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertThat(arithmetic.sum(1d, 2d)).isEqualTo(3);
    }

    @Test
    void sum123() {
        assertThat(arithmetic.sum(1d, 2d, 3d)).isEqualTo(6);
    }

    @Test
    void difference12() {
        assertThat(arithmetic.difference(1d, 2d)).isEqualTo(-1);
    }

    @Test
    void product12() {
        assertThat(arithmetic.product(1d, 2d)).isEqualTo(2);
    }

    @Test
    void product123() {
        assertThat(arithmetic.product(1d, 2d, 3d)).isEqualTo(6);
    }

    @Test
    void quotient12() {
        assertThat(arithmetic.quotient(1d, 2d)).isEqualTo(0.5);
    }

    @Test
    void modulo12() {
        assertThat(arithmetic.modulo(1d, 2d)).isEqualTo(1);
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertThat(arithmetic.power(1d, 2)).isEqualTo(1);
    }

    @Test
    void root21() {
        assertThat(arithmetic.root2(1d)).isEqualTo(Math.sqrt(1));
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> arithmetic.gcd(3d, 4d)); // assert exception message?
    }

    @Test
    void lcm() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> arithmetic.lcm(3d, 4d)); // assert exception message?
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertThat(new DoubleArithmetic()).isEqualTo(new DoubleArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        assertThat(new DoubleArithmetic().hashCode()).isEqualTo(1072694209);
    }

    @Test
    void toStringOfArithmetic() {
        assertThat(new DoubleArithmetic()).hasToString("DoubleArithmetic");
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleArithmetic(), DoubleArithmetic.class);
    }

    // endregion
}
