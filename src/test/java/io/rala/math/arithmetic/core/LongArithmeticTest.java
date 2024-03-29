package io.rala.math.arithmetic.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class LongArithmeticTest {
    private LongArithmetic arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new LongArithmetic();
    }

    @Test
    void getInstance() {
        assertThat(LongArithmetic.getInstance()).isEqualTo(arithmetic);
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        assertThat(arithmetic.fromInt(1)).isOne();
    }

    @Test
    void fromDouble1_1() {
        assertThat(arithmetic.fromDouble(1.1)).isOne();
    }

    @Test
    void signum1() {
        assertThat(arithmetic.signum(1L)).isOne();
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertThat(arithmetic.absolute(-1L)).isOne();
    }

    @Test
    void negate1() {
        assertThat(arithmetic.negate(1L)).isEqualTo(-1);
    }

    @Test
    void compare() {
        assertThat(arithmetic.compare(1L, 1L)).isZero();
        assertThat(arithmetic.compare(1L, 2L)).isEqualTo(-1);
        assertThat(arithmetic.compare(2L, 1L)).isOne();
    }

    @Test
    void min() {
        assertThat(arithmetic.min(1L, 1L)).isOne();
        assertThat(arithmetic.min(1L, 2L)).isOne();
        assertThat(arithmetic.min(2L, 1L)).isOne();
    }

    @Test
    void max() {
        assertThat(arithmetic.max(1L, 1L)).isOne();
        assertThat(arithmetic.max(1L, 2L)).isEqualTo(2);
        assertThat(arithmetic.max(2L, 1L)).isEqualTo(2);
    }

    @Test
    void isZero() {
        assertThat(arithmetic.isZero(0L)).isTrue();
        assertThat(arithmetic.isZero(-0L)).isTrue();
        assertThat(arithmetic.isZero(1L)).isFalse();
    }

    @Test
    void isEqual() {
        assertThat(arithmetic.isEqual(0L, 0L)).isTrue();
        assertThat(arithmetic.isEqual(-0L, 0L)).isTrue();
        assertThat(arithmetic.isEqual(-0L, -0L)).isTrue();
        assertThat(arithmetic.isEqual(1L, 0L)).isFalse();
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
        assertThat(arithmetic.quotient(1L, 2L)).isZero();
    }

    @Test
    void modulo12() {
        assertThat(arithmetic.modulo(1L, 2L)).isOne();
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertThat(arithmetic.power(1L, 2)).isOne();
    }

    @Test
    void root21() {
        assertThat(arithmetic.root2(1L)).isEqualTo((long) Math.sqrt(1));
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThat(arithmetic.gcd(3L, 4L)).isOne();
    }

    @Test
    void lcm() {
        assertThat(arithmetic.lcm(3L, 4L)).isEqualTo(12);
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertThat(new LongArithmetic()).isEqualTo(new LongArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        assertThat(new LongArithmetic().hashCode()).isEqualTo(962);
    }

    @Test
    void toStringOfArithmetic() {
        assertThat(new LongArithmetic()).hasToString("LongArithmetic");
    }

    @Test
    void serializable() {
        assertSerializable(new LongArithmetic(), LongArithmetic.class);
    }

    // endregion
}
