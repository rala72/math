package io.rala.math.arithmetic.core;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.exception.NotSupportedException;
import io.rala.math.testUtils.algebra.TestFraction;
import io.rala.math.testUtils.arithmetic.TestFractionArithmetic;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatFraction;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FractionArithmeticTest {
    private FractionArithmetic<Number, Number> arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new TestFractionArithmetic();
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        assertThatFraction(arithmetic.fromInt(1)).isEqualTo(new TestFraction(1d));
    }

    @Test
    void fromDouble1_1() {
        assertThatFraction(arithmetic.fromDouble(1.1)).isEqualTo(new TestFraction(11d, 10d));
    }

    @Test
    void signum1() {
        assertThat(arithmetic.signum(new TestFraction(1))).isOne();
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertThatFraction(arithmetic.absolute(new TestFraction(-1d))).isEqualTo(new TestFraction(1d));
    }

    @Test
    void negate1() {
        assertThatFraction(arithmetic.negate(new TestFraction(1d))).isEqualTo(new TestFraction(-1d));
    }

    @Test
    void compare() {
        TestFraction complex = new TestFraction(2, 3);
        assertThat(arithmetic.compare(complex, new TestFraction(2, 3))).isZero();
        assertThat(arithmetic.compare(complex, new TestFraction(3, 1))).isEqualTo(-1);
        assertThat(arithmetic.compare(complex, new TestFraction(1, 2))).isOne();
    }

    @Test
    void min() {
        assertThatFraction(arithmetic.min(new TestFraction(1), new TestFraction(1)))
            .isEqualTo(new TestFraction(1));
        assertThatFraction(arithmetic.min(new TestFraction(1), new TestFraction(2)))
            .isEqualTo(new TestFraction(1));
        assertThatFraction(arithmetic.min(new TestFraction(2), new TestFraction(1)))
            .isEqualTo(new TestFraction(1));
    }

    @Test
    void max() {
        assertThatFraction(arithmetic.max(new TestFraction(1), new TestFraction(1)))
            .isEqualTo(new TestFraction(1));
        assertThatFraction(arithmetic.max(new TestFraction(1), new TestFraction(2)))
            .isEqualTo(new TestFraction(2));
        assertThatFraction(arithmetic.max(new TestFraction(2), new TestFraction(1)))
            .isEqualTo(new TestFraction(2));
    }

    @Test
    void isZero() {
        assertThat(arithmetic.isZero(new TestFraction(0d))).isTrue();
        assertThat(arithmetic.isZero(new TestFraction(-0d))).isTrue();
        assertThat(arithmetic.isZero(new TestFraction(1d))).isFalse();
    }

    @Test
    void isEqual() {
        assertThat(arithmetic.isEqual(
            new TestFraction(0d),
            new TestFraction(0d)
        )).isTrue();
        assertThat(arithmetic.isEqual(
            new TestFraction(-0d),
            new TestFraction(0d)
        )).isTrue();
        assertThat(arithmetic.isEqual(
            new TestFraction(-0d),
            new TestFraction(-0d)
        )).isTrue();
        assertThat(arithmetic.isEqual(
            new TestFraction(1d),
            new TestFraction(0d)
        )).isFalse();
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertThatFraction(arithmetic.sum(new TestFraction(1d), new TestFraction(2d)))
            .isEqualTo(new TestFraction(3d, 1d));
    }

    @Test
    void sum123() {
        assertThatFraction(arithmetic.sum(
            new TestFraction(1d),
            new TestFraction(2d),
            new TestFraction(3d)
        )).isEqualTo(new TestFraction(6d, 1d));
    }

    @Test
    void difference12() {
        assertThatFraction(arithmetic.difference(new TestFraction(1d), new TestFraction(2d)))
            .isEqualTo(new TestFraction(-1d, 1d));
    }

    @Test
    void product12() {
        assertThatFraction(arithmetic.product(new TestFraction(1d), new TestFraction(2d)))
            .isEqualTo(new TestFraction(2d, 1d));
    }

    @Test
    void product123() {
        assertThatFraction(arithmetic.product(
            new TestFraction(1d),
            new TestFraction(2d),
            new TestFraction(3d))
        ).isEqualTo(new TestFraction(6d, 1d));
    }

    @Test
    void quotient12() {
        assertThatFraction(arithmetic.quotient(new TestFraction(1d), new TestFraction(2d)))
            .isEqualTo(new TestFraction(1d, 2d));
    }

    @Test
    void modulo12() {
        assertThatFraction(arithmetic.modulo(new TestFraction(1d), new TestFraction(2d)))
            .isEqualTo(new TestFraction(0d, 2d));
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertThatFraction(arithmetic.power(new TestFraction(1d), 2))
            .isEqualTo(new TestFraction(1d, 1d));
    }

    @Test
    void root21() {
        assertThatFraction(arithmetic.root2(new TestFraction(1d)))
            .isEqualTo(new TestFraction(Math.sqrt(1), Math.sqrt(1)));
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> arithmetic.gcd(new TestFraction(3d), new TestFraction(4d)))
            .withMessage(ExceptionMessages.METHOD_NOT_SUPPORTED);
    }

    @Test
    void lcm() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> arithmetic.lcm(new TestFraction(3d), new TestFraction(4d)))
            .withMessage(ExceptionMessages.METHOD_NOT_SUPPORTED);
    }

    // endregion

    // region static of

    @Test
    void ofNumerator() {
        Fraction<Integer, Integer> ofArithmetic =
            Fraction.of(new IntegerArithmetic(), 1);
        assertThat(ofArithmetic.getNumerator()).isNotNull();
        assertThat(ofArithmetic.getDenominator()).isNotNull();
        assertThat(ofArithmetic.getNumerator()).isOne();
        assertThat(ofArithmetic.getDenominator()).isOne();
    }

    @Test
    void ofNumeratorAndDenominator() {
        Fraction<Integer, Integer> ofArithmetic =
            Fraction.of(new IntegerArithmetic(), 1, 2);
        assertThat(ofArithmetic.getNumerator()).isNotNull();
        assertThat(ofArithmetic.getDenominator()).isNotNull();
        assertThat(ofArithmetic.getNumerator()).isOne();
        assertThat(ofArithmetic.getDenominator()).isEqualTo(2);
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertThat(new TestFractionArithmetic()).isEqualTo(new TestFractionArithmetic());
    }

    @Test
    void hashCodeOfArithmetic() {
        assertThat(new TestFractionArithmetic().hashCode()).isEqualTo(-1173293087);
    }

    @Test
    void toStringOfArithmetic() {
        assertThat(new TestFractionArithmetic()).hasToString("TestFractionArithmetic");
    }

    @Test
    void serializable() {
        assertSerializable(
            new TestFractionArithmetic(),
            TestFractionArithmetic.class
        );
    }

    // endregion
}
