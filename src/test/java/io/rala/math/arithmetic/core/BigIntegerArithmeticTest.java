package io.rala.math.arithmetic.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class BigIntegerArithmeticTest {
    private BigIntegerArithmetic arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new BigIntegerArithmetic();
    }

    @Test
    void getInstance() {
        assertThat(BigIntegerArithmetic.getInstance()).isEqualTo(arithmetic);
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        assertThat(arithmetic.fromInt(1)).isEqualTo(BigInteger.ONE);
    }

    @Test
    void fromDouble1_1() {
        assertThat(arithmetic.fromDouble(1.1)).isEqualTo(BigInteger.ONE);
    }

    @Test
    void signum1() {
        assertThat(arithmetic.signum(BigInteger.ONE)).isEqualTo(1);
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertThat(arithmetic.absolute(BigInteger.ONE.negate())).isEqualTo(BigInteger.ONE);
    }

    @Test
    void negate1() {
        assertThat(arithmetic.negate(BigInteger.ONE)).isEqualTo(BigInteger.ONE.negate());
    }

    @Test
    void compare() {
        assertThat(arithmetic.compare(BigInteger.ONE, BigInteger.ONE)).isEqualTo(0);
        assertThat(arithmetic.compare(BigInteger.ONE, BigInteger.TWO)).isEqualTo(-1);
        assertThat(arithmetic.compare(BigInteger.TWO, BigInteger.ONE)).isEqualTo(1);
    }

    @Test
    void min() {
        assertThat(arithmetic.min(BigInteger.ONE, BigInteger.ONE)).isEqualTo(BigInteger.ONE);
        assertThat(arithmetic.min(BigInteger.ONE, BigInteger.TWO)).isEqualTo(BigInteger.ONE);
        assertThat(arithmetic.min(BigInteger.TWO, BigInteger.ONE)).isEqualTo(BigInteger.ONE);
    }

    @Test
    void max() {
        assertThat(arithmetic.max(BigInteger.ONE, BigInteger.ONE)).isEqualTo(BigInteger.ONE);
        assertThat(arithmetic.max(BigInteger.ONE, BigInteger.TWO)).isEqualTo(BigInteger.TWO);
        assertThat(arithmetic.max(BigInteger.TWO, BigInteger.ONE)).isEqualTo(BigInteger.TWO);
    }

    @Test
    void isZero() {
        assertThat(arithmetic.isZero(BigInteger.ZERO)).isTrue();
        assertThat(arithmetic.isZero(BigInteger.ZERO.negate())).isTrue();
        assertThat(arithmetic.isZero(BigInteger.ONE)).isFalse();
    }

    @Test
    void isEqual() {
        assertThat(arithmetic.isEqual(BigInteger.ZERO, BigInteger.ZERO)).isTrue();
        assertThat(arithmetic.isEqual(BigInteger.ZERO.negate(), BigInteger.ZERO)).isTrue();
        assertThat(arithmetic.isEqual(BigInteger.ZERO.negate(), BigInteger.ZERO.negate())).isTrue();
        assertThat(arithmetic.isEqual(BigInteger.ONE, BigInteger.ZERO)).isFalse();
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertThat(arithmetic.sum(BigInteger.ONE, BigInteger.TWO)).isEqualTo(BigInteger.valueOf(3));
    }

    @Test
    void sum123() {
        assertThat(arithmetic.sum(BigInteger.ONE, BigInteger.TWO, BigInteger.valueOf(3))).isEqualTo(BigInteger.valueOf(6));
    }

    @Test
    void difference12() {
        assertThat(arithmetic.difference(BigInteger.ONE, BigInteger.TWO)).isEqualTo(BigInteger.valueOf(-1));
    }

    @Test
    void product12() {
        assertThat(arithmetic.product(BigInteger.ONE, BigInteger.TWO)).isEqualTo(BigInteger.TWO);
    }

    @Test
    void product123() {
        assertThat(arithmetic.product(BigInteger.ONE, BigInteger.TWO, BigInteger.valueOf(3))).isEqualTo(BigInteger.valueOf(6));
    }

    @Test
    void quotient12() {
        assertThat(arithmetic.quotient(BigInteger.ONE, BigInteger.TWO)).isEqualTo(BigInteger.ZERO);
    }

    @Test
    void modulo12() {
        assertThat(arithmetic.modulo(BigInteger.ONE, BigInteger.TWO)).isEqualTo(BigInteger.ONE);
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertThat(arithmetic.power(BigInteger.ONE, 2)).isEqualTo(BigInteger.ONE);
    }

    @Test
    void root21() {
        assertThat(arithmetic.root2(BigInteger.ONE)).isEqualTo(BigInteger.ONE.sqrt());
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThat(arithmetic.gcd(BigInteger.valueOf(3), BigInteger.valueOf(4))).isEqualTo(BigInteger.ONE);
    }

    @Test
    void lcm() {
        assertThat(arithmetic.lcm(BigInteger.valueOf(3), BigInteger.valueOf(4))).isEqualTo(BigInteger.valueOf(12));
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertThat(new BigIntegerArithmetic()).isEqualTo(new BigIntegerArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        assertThat(new BigIntegerArithmetic().hashCode()).isEqualTo(962);
    }

    @Test
    void toStringOfArithmetic() {
        assertThat(new BigIntegerArithmetic().toString()).isEqualTo("BigIntegerArithmetic");
    }

    @Test
    void serializable() {
        assertSerializable(
            new BigIntegerArithmetic(),
            BigIntegerArithmetic.class
        );
    }

    // endregion
}
