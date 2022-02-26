package io.rala.math.arithmetic.core;

import io.rala.math.exception.NotSupportedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BigDecimalArithmeticTest {
    private BigDecimalArithmetic arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new BigDecimalArithmetic();
    }

    @Test
    void getInstance() {
        assertThat(BigDecimalArithmetic.getInstance()).isEqualTo(arithmetic);
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        assertThat(arithmetic.fromInt(1)).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void fromDouble1_1() {
        assertThat(arithmetic.fromDouble(1.1)).isEqualTo(BigDecimal.valueOf(1.1));
    }

    @Test
    void signum1() {
        assertThat(arithmetic.signum(BigDecimal.ONE)).isEqualTo(1);
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertThat(arithmetic.absolute(BigDecimal.ONE.negate())).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void negate1() {
        assertThat(arithmetic.negate(BigDecimal.ONE)).isEqualTo(BigDecimal.ONE.negate());
    }

    @Test
    void compare() {
        assertThat(arithmetic.compare(BigDecimal.ONE, BigDecimal.ONE)).isEqualTo(0);
        assertThat(arithmetic.compare(BigDecimal.ONE, BigDecimal.valueOf(2))).isEqualTo(-1);
        assertThat(arithmetic.compare(BigDecimal.valueOf(2), BigDecimal.ONE)).isEqualTo(1);
    }

    @Test
    void min() {
        assertThat(arithmetic.min(BigDecimal.ONE, BigDecimal.ONE)).isEqualTo(BigDecimal.ONE);
        assertThat(arithmetic.min(BigDecimal.ONE, BigDecimal.valueOf(2))).isEqualTo(BigDecimal.ONE);
        assertThat(arithmetic.min(BigDecimal.valueOf(2), BigDecimal.ONE)).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void max() {
        assertThat(arithmetic.max(BigDecimal.ONE, BigDecimal.ONE)).isEqualTo(BigDecimal.ONE);
        assertThat(arithmetic.max(BigDecimal.ONE, BigDecimal.valueOf(2))).isEqualTo(BigDecimal.valueOf(2));
        assertThat(arithmetic.max(BigDecimal.valueOf(2), BigDecimal.ONE)).isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void isZero() {
        assertThat(arithmetic.isZero(BigDecimal.ZERO)).isTrue();
        assertThat(arithmetic.isZero(BigDecimal.ZERO.negate())).isTrue();
        assertThat(arithmetic.isZero(BigDecimal.ONE)).isFalse();
    }

    @Test
    void isEqual() {
        assertThat(arithmetic.isEqual(BigDecimal.ZERO, BigDecimal.ZERO)).isTrue();
        assertThat(arithmetic.isEqual(BigDecimal.ZERO.negate(), BigDecimal.ZERO)).isTrue();
        assertThat(arithmetic.isEqual(BigDecimal.ZERO.negate(), BigDecimal.ZERO.negate())).isTrue();
        assertThat(arithmetic.isEqual(BigDecimal.ONE, BigDecimal.ZERO)).isFalse();
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertThat(arithmetic.sum(BigDecimal.ONE, BigDecimal.valueOf(2))).isEqualTo(BigDecimal.valueOf(3));
    }

    @Test
    void sum123() {
        assertThat(arithmetic.sum(BigDecimal.ONE, BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
            .isEqualTo(BigDecimal.valueOf(6));
    }

    @Test
    void difference12() {
        assertThat(arithmetic.difference(BigDecimal.ONE, BigDecimal.valueOf(2))).isEqualTo(BigDecimal.valueOf(-1));
    }

    @Test
    void product12() {
        assertThat(arithmetic.product(BigDecimal.ONE, BigDecimal.valueOf(2))).isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void product123() {
        assertThat(arithmetic.product(
            BigDecimal.ONE,
            BigDecimal.valueOf(2),
            BigDecimal.valueOf(3)
        )).isEqualTo(BigDecimal.valueOf(6));
    }

    @Test
    void quotient12() {
        assertThat(arithmetic.quotient(BigDecimal.ONE, BigDecimal.valueOf(2))).isEqualTo(BigDecimal.valueOf(0.5));
    }

    @Test
    void modulo12() {
        assertThat(arithmetic.modulo(BigDecimal.ONE, BigDecimal.valueOf(2))).isEqualTo(BigDecimal.ONE);
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertThat(arithmetic.power(BigDecimal.ONE, 2)).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void root21() {
        assertThat(arithmetic.root2(BigDecimal.ONE)).isEqualTo(BigDecimal.ONE.sqrt(new MathContext(10)));
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> arithmetic.gcd(BigDecimal.valueOf(3), BigDecimal.valueOf(4))); // assert exception message?
    }

    @Test
    void lcm() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> arithmetic.gcd(BigDecimal.valueOf(3), BigDecimal.valueOf(4))); // assert exception message?
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertThat(new BigDecimalArithmetic()).isEqualTo(new BigDecimalArithmetic());
        assertThat(new BigDecimalArithmetic(new MathContext(5))).isNotEqualTo(new BigDecimalArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        // hashCode of RoundingMode enum changing after every start
        assertThat(new BigDecimalArithmetic().hashCode()).isEqualTo(new BigDecimalArithmetic().hashCode());
    }

    @Test
    void toStringOfArithmetic() {
        assertThat(new BigDecimalArithmetic().toString()).isEqualTo("BigDecimalArithmetic");
    }

    @Test
    void serializable() {
        assertSerializable(
            new BigDecimalArithmetic(),
            BigDecimalArithmetic.class
        );
    }

    // endregion
}
