package io.rala.math.arithmetic.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class IntegerArithmeticTest {
    private IntegerArithmetic arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new IntegerArithmetic();
    }

    @Test
    void getInstance() {
        assertThat(IntegerArithmetic.getInstance()).isEqualTo(arithmetic);
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
        assertThat(arithmetic.signum(1)).isOne();
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertThat(arithmetic.absolute(-1)).isOne();
    }

    @Test
    void negate1() {
        assertThat(arithmetic.negate(1)).isEqualTo(-1);
    }

    @Test
    void compare() {
        assertThat(arithmetic.compare(1, 1)).isZero();
        assertThat(arithmetic.compare(1, 2)).isEqualTo(-1);
        assertThat(arithmetic.compare(2, 1)).isOne();
    }

    @Test
    void min() {
        assertThat(arithmetic.min(1, 1)).isOne();
        assertThat(arithmetic.min(1, 2)).isOne();
        assertThat(arithmetic.min(2, 1)).isOne();
    }

    @Test
    void max() {
        assertThat(arithmetic.max(1, 1)).isOne();
        assertThat(arithmetic.max(1, 2)).isEqualTo(2);
        assertThat(arithmetic.max(2, 1)).isEqualTo(2);
    }

    @Test
    void isZero() {
        assertThat(arithmetic.isZero(0)).isTrue();
        assertThat(arithmetic.isZero(-0)).isTrue();
        assertThat(arithmetic.isZero(1)).isFalse();
    }

    @Test
    void isEqual() {
        assertThat(arithmetic.isEqual(0, 0)).isTrue();
        assertThat(arithmetic.isEqual(-0, 0)).isTrue();
        assertThat(arithmetic.isEqual(-0, -0)).isTrue();
        assertThat(arithmetic.isEqual(1, 0)).isFalse();
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertThat(arithmetic.sum(1, 2)).isEqualTo(3);
    }

    @Test
    void sum123() {
        assertThat(arithmetic.sum(1, 2, 3)).isEqualTo(6);
    }

    @Test
    void difference12() {
        assertThat(arithmetic.difference(1, 2)).isEqualTo(-1);
    }

    @Test
    void product12() {
        assertThat(arithmetic.product(1, 2)).isEqualTo(2);
    }

    @Test
    void product123() {
        assertThat(arithmetic.product(1, 2, 3)).isEqualTo(6);
    }

    @Test
    void quotient12() {
        assertThat(arithmetic.quotient(1, 2)).isZero();
    }

    @Test
    void modulo12() {
        assertThat(arithmetic.modulo(1, 2)).isOne();
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertThat(arithmetic.power(1, 2)).isOne();
    }

    @Test
    void root21() {
        assertThat(arithmetic.root2(1)).isEqualTo((int) Math.sqrt(1));
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThat(arithmetic.gcd(3, 4)).isOne();
    }

    @Test
    void lcm() {
        assertThat(arithmetic.lcm(3, 4)).isEqualTo(12);
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertThat(new IntegerArithmetic()).isEqualTo(new IntegerArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        assertThat(new IntegerArithmetic().hashCode()).isEqualTo(962);
    }

    @Test
    void toStringOfArithmetic() {
        assertThat(new IntegerArithmetic()).hasToString("IntegerArithmetic");
    }

    @Test
    void serializable() {
        assertSerializable(new IntegerArithmetic(), IntegerArithmetic.class);
    }

    // endregion
}
