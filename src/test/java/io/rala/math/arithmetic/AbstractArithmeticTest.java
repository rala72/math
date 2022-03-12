package io.rala.math.arithmetic;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class AbstractArithmeticTest {
    private AbstractArithmetic<Number> arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new TestAbstractArithmetic();
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt0() {
        assertThat(arithmetic.fromInt(0)).isEqualTo(0d);
    }

    @Test
    void fromDouble0() {
        assertThat(arithmetic.fromDouble(0)).isEqualTo(0d);
    }

    @Test
    void signum1() {
        assertThat(arithmetic.signum(1)).isOne();
    }

    // endregion

    // region number constants

    @Test
    void zero() {
        assertThat(arithmetic.zero()).isEqualTo(0d);
    }

    @Test
    void one() {
        assertThat(arithmetic.one()).isEqualTo(1d);
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertThat(arithmetic.absolute(-1)).isEqualTo(1d);
    }

    @Test
    void negate1() {
        assertThat(arithmetic.negate(1)).isEqualTo(-1d);
    }

    @Test
    void compare() {
        assertThat(arithmetic.compare(1, 1)).isZero();
        assertThat(arithmetic.compare(1, 2)).isEqualTo(-1);
        assertThat(arithmetic.compare(2, 1)).isOne();
    }

    @Test
    void min() {
        assertThat(arithmetic.min(1, 1)).isEqualTo(1);
        assertThat(arithmetic.min(1, 2)).isEqualTo(1);
        assertThat(arithmetic.min(2, 1)).isEqualTo(1);
    }

    @Test
    void max() {
        assertThat(arithmetic.max(1, 1)).isEqualTo(1);
        assertThat(arithmetic.max(1, 2)).isEqualTo(2);
        assertThat(arithmetic.max(2, 1)).isEqualTo(2);
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
        assertThat(arithmetic.isEqual(0, 0d)).isFalse();
        assertThat(arithmetic.isEqual(1d, 0d)).isFalse();
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertThat(arithmetic.sum(1, 2)).isEqualTo(3d);
    }

    @Test
    void sum123() {
        assertThat(arithmetic.sum(1, 2, 3)).isEqualTo(6d);
    }

    @Test
    void sumFrom1To9() {
        assertThat(arithmetic.sum(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)).intValue()).isEqualTo(IntStream.rangeClosed(1, 9).sum());
    }

    @Test
    void difference12() {
        assertThat(arithmetic.difference(1, 2)).isEqualTo(-1d);
    }

    @Test
    void product12() {
        assertThat(arithmetic.product(1, 2)).isEqualTo(2d);
    }

    @Test
    void product123() {
        assertThat(arithmetic.product(1, 2, 3)).isEqualTo(6d);
    }

    @Test
    void productFrom1To9() {
        assertThat(arithmetic.product(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)).intValue())
            .isEqualTo(IntStream.rangeClosed(1, 9).reduce(1, (left, right) -> left * right));
    }

    @Test
    void quotient12() {
        assertThat(arithmetic.quotient(1, 2)).isEqualTo(0.5);
    }

    @Test
    void modulo12() {
        assertThat(arithmetic.modulo(1, 2)).isEqualTo(0d);
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertThat(arithmetic.power(1, 2)).isEqualTo(1d);
    }

    @Test
    void root21() {
        assertThat(arithmetic.root2(1)).isEqualTo(1d);
    }

    // endregion

    // region isFinite, isInfinite and isNaN

    @Test
    void isFinite() {
        assertThat(arithmetic.isFinite(0)).isTrue();
        assertThat(arithmetic.isFinite(Double.POSITIVE_INFINITY)).isFalse();
        assertThat(arithmetic.isFinite(Double.NaN)).isFalse();
    }

    @Test
    void isInfinite() {
        assertThat(arithmetic.isInfinite(0)).isFalse();
        assertThat(arithmetic.isInfinite(Double.POSITIVE_INFINITY)).isTrue();
        assertThat(arithmetic.isInfinite(Double.NaN)).isFalse();
    }

    @Test
    void isNaN() {
        assertThat(arithmetic.isNaN(0)).isFalse();
        assertThat(arithmetic.isNaN(Double.POSITIVE_INFINITY)).isFalse();
        assertThat(arithmetic.isNaN(Double.NaN)).isTrue();
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThat(arithmetic.gcd(3, 4)).isEqualTo(1L);
    }

    @Test
    void lcm() {
        assertThat(arithmetic.lcm(3, 4)).isEqualTo(12d);
    }

    // endregion

    // region trigonometry

    @Test
    void sinOf1() {
        assertThat(arithmetic.sin(1)).isEqualTo(Math.sin(1));
    }

    @Test
    void cosOf1() {
        assertThat(arithmetic.cos(1)).isEqualTo(Math.cos(1));
    }

    @Test
    void tanOf1() {
        assertThat(arithmetic.tan(1)).isEqualTo(Math.tan(1));
    }

    @Test
    void asinOf1() {
        assertThat(arithmetic.asin(1)).isEqualTo(Math.asin(1));
    }

    @Test
    void acosOf1() {
        assertThat(arithmetic.acos(1)).isEqualTo(Math.acos(1));
    }

    @Test
    void atanOf1() {
        assertThat(arithmetic.atan(1)).isEqualTo(Math.atan(1));
    }

    @Test
    void sinhOf1() {
        assertThat(arithmetic.sinh(1)).isEqualTo(Math.sinh(1));
    }

    @Test
    void coshOf1() {
        assertThat(arithmetic.cosh(1)).isEqualTo(Math.cosh(1));
    }

    @Test
    void tanhOf1() {
        assertThat(arithmetic.tanh(1)).isEqualTo(Math.tanh(1));
    }

    // endregion

    // region toResultArithmetic

    @Test
    void toResultArithmetic() {
        AbstractResultArithmetic<Number, Number> resultArithmetic =
            arithmetic.toResultArithmetic();
        assertThat(resultArithmetic.getTArithmetic()).isEqualTo(arithmetic);
        assertThat(resultArithmetic.getRArithmetic()).isEqualTo(arithmetic);
        assertThat(resultArithmetic.fromT(0)).isEqualTo(0);
        assertThat(resultArithmetic.fromT(-0d)).isEqualTo(-0d);
    }

    @Test
    void toResultArithmeticWithTarget() {
        IntegerArithmetic targetArithmetic = new IntegerArithmetic();
        AbstractResultArithmetic<Number, Integer> resultArithmetic =
            arithmetic.toResultArithmetic(targetArithmetic, Number::intValue);
        assertThat(resultArithmetic.getTArithmetic()).isEqualTo(arithmetic);
        assertThat(resultArithmetic.getRArithmetic()).isEqualTo(targetArithmetic);
        assertThat(resultArithmetic.fromT(0)).isZero();
        assertThat(resultArithmetic.fromT(-0d)).isZero();
    }

    // endregion

    // region override

    @Test
    void equalsOfAbstractArithmetic() {
        assertThat(new TestAbstractArithmetic()).isEqualTo(new TestAbstractArithmetic());
    }

    @Test
    void hashCodeOfAbstractArithmetic() {
        assertThat(new TestAbstractArithmetic().hashCode()).isEqualTo(1072694209);
    }

    @Test
    void toStringOfAbstractArithmetic() {
        assertThat(new TestAbstractArithmetic()).hasToString("TestAbstractArithmetic");
    }

    @Test
    void serializable() {
        assertSerializable(
            new TestAbstractArithmetic(),
            TestAbstractArithmetic.class
        );
    }

    // endregion
}
