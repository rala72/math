package io.rala.math.arithmetic.core;

import io.rala.math.exception.NotSupportedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FloatArithmeticTest {
    private FloatArithmetic arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new FloatArithmetic();
    }

    @Test
    void getInstance() {
        assertThat(FloatArithmetic.getInstance()).isEqualTo(arithmetic);
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        assertThat(arithmetic.fromInt(1)).isEqualTo(1);
    }

    @Test
    void fromDouble1_1() {
        assertThat(arithmetic.fromDouble(1.1)).isEqualTo(1.1f);
    }

    @Test
    void signum1() {
        assertThat(arithmetic.signum(1f)).isEqualTo(1);
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertThat(arithmetic.absolute(-1f)).isEqualTo(1);
    }

    @Test
    void negate1() {
        assertThat(arithmetic.negate(1f)).isEqualTo(-1);
    }

    @Test
    void compare() {
        assertThat(arithmetic.compare(1f, 1f)).isEqualTo(0);
        assertThat(arithmetic.compare(1f, 2f)).isEqualTo(-1);
        assertThat(arithmetic.compare(2f, 1f)).isEqualTo(1);
    }

    @Test
    void min() {
        assertThat(arithmetic.min(1f, 1f)).isEqualTo(1);
        assertThat(arithmetic.min(1f, 2f)).isEqualTo(1);
        assertThat(arithmetic.min(2f, 1f)).isEqualTo(1);
    }

    @Test
    void max() {
        assertThat(arithmetic.max(1f, 1f)).isEqualTo(1);
        assertThat(arithmetic.max(1f, 2f)).isEqualTo(2);
        assertThat(arithmetic.max(2f, 1f)).isEqualTo(2);
    }

    @Test
    void isZero() {
        assertThat(arithmetic.isZero(0f)).isTrue();
        assertThat(arithmetic.isZero(-0f)).isTrue();
        assertThat(arithmetic.isZero(1f)).isFalse();
    }

    @Test
    void isEqual() {
        assertThat(arithmetic.isEqual(0f, 0f)).isTrue();
        assertThat(arithmetic.isEqual(-0f, 0f)).isTrue();
        assertThat(arithmetic.isEqual(-0f, -0f)).isTrue();
        assertThat(arithmetic.isEqual(1f, 0f)).isFalse();
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertThat(arithmetic.sum(1f, 2f)).isEqualTo(3);
    }

    @Test
    void sum123() {
        assertThat(arithmetic.sum(1f, 2f, 3f)).isEqualTo(6);
    }

    @Test
    void difference12() {
        assertThat(arithmetic.difference(1f, 2f)).isEqualTo(-1);
    }

    @Test
    void product12() {
        assertThat(arithmetic.product(1f, 2f)).isEqualTo(2);
    }

    @Test
    void product123() {
        assertThat(arithmetic.product(1f, 2f, 3f)).isEqualTo(6);
    }

    @Test
    void quotient12() {
        assertThat(arithmetic.quotient(1f, 2f)).isEqualTo(0.5f);
    }

    @Test
    void modulo12() {
        assertThat(arithmetic.modulo(1f, 2f)).isEqualTo(1);
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertThat(arithmetic.power(1f, 2)).isEqualTo(1);
    }

    @Test
    void root21() {
        assertThat(arithmetic.root2(1f)).isEqualTo((float) Math.sqrt(1));
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> arithmetic.gcd(3f, 4f)); // assert exception message?
    }

    @Test
    void lcm() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> arithmetic.lcm(3f, 4f)); // assert exception message?
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertThat(new FloatArithmetic()).isEqualTo(new FloatArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        assertThat(new FloatArithmetic().hashCode()).isEqualTo(1065354177);
    }

    @Test
    void toStringOfArithmetic() {
        assertThat(new FloatArithmetic().toString()).isEqualTo("FloatArithmetic");
    }

    @Test
    void serializable() {
        assertSerializable(new FloatArithmetic(), FloatArithmetic.class);
    }

    // endregion
}
