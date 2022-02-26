package io.rala.math.algebra.numeric;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.algebra.TestFraction;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import io.rala.math.testUtils.assertion.NumericAssertions;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FractionTest {
    // region constructors, getter and setter

    @Test
    void constructorWithNu() {
        assertFraction(new TestFraction(1d));
    }

    @Test
    void constructorWithDifferentNuDeParameter() {
        assertFraction(new TestFraction(2, 3), 2, 3);
    }

    @Test
    void constructorWithZeroDeParameter() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestFraction(1d, 0d)); // assert exception message?
    }

    @Test
    void constructorWithFraction() {
        assertFraction(new TestFraction(new TestFraction(2, 3)), 2, 3);
    }

    @Test
    void createAndSetNumerator() {
        TestFraction complex = new TestFraction(1d);
        assertFraction(complex);
        complex.setNumerator(2);
        assertFraction(complex, 2, 1d);
    }

    @Test
    void createAndSetDenominator() {
        TestFraction complex = new TestFraction(1d);
        assertFraction(complex);
        complex.setDenominator(3);
        assertFraction(complex, 1d, 3);
    }

    @Test
    void createAndSetDenominatorZero() {
        TestFraction complex = new TestFraction(1);
        assertFraction(complex, 1, 1d);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> complex.setDenominator(0d)); // assert exception message?
    }

    @Test
    void createAndSetDenominatorNull() {
        TestFraction complex = new TestFraction(1, 2);
        assertFraction(complex, 1, 2);
        complex.setDenominator(null);
        assertFraction(complex, 1, 1d);
    }

    // endregion

    // region value

    @Test
    void valueOf1_2() {
        assertThat(new TestFraction(1, 2).value()).isEqualTo(0.5);
    }

    @Test
    void intValueOf1_2() {
        assertThat(new TestFraction(1, 2).intValue()).isZero();
    }

    @Test
    void longValueOf1_2() {
        assertThat(new TestFraction(1, 2).longValue()).isZero();
    }

    @Test
    void floatValueOf1_2() {
        assertThat(new TestFraction(1, 2).floatValue()).isEqualTo(0.5f);
    }

    @Test
    void doubleValueOf1_2() {
        assertThat(new TestFraction(1, 2).doubleValue()).isEqualTo(0.5);
    }

    // endregion

    // region negate, inverse and simplify

    @Test
    void negate() {
        assertFraction(new TestFraction(1).negate(), -1d, 1d);
    }

    @Test
    void inverse() {
        assertFraction(new TestFraction(1, 2).inverse(), 2, 1);
    }

    @Test
    void simplify() {
        assertFraction(new TestFraction(2, 4).simplify(), 1d, 2d);
    }

    @Test
    void simplifyDoubleFraction() {
        Fraction<Double, Double> fraction = new Fraction<>(
            new DoubleArithmetic().toResultArithmetic(), 2d, 4d
        );
        assertThat(fraction.simplify()).isEqualTo(fraction);
    }

    // endregion

    // region add and subtract

    @Test
    void addWithNumerator() {
        assertThat(new TestFraction(1).add(1, 1)).isEqualTo(new TestFraction(2d, 1d));
    }

    @Test
    void addWithXAndY() {
        assertThat(new TestFraction(1, 1).add(1, 1)).isEqualTo(new TestFraction(2d, 1d));
    }

    @Test
    void addWithFraction() {
        assertThat(new TestFraction(1, 1).add(new TestFraction(1, 2))).isEqualTo(new TestFraction(3d, 2d));
    }

    @Test
    void addWithDoubleFraction() {
        AbstractResultArithmetic<Double, Double> arithmetic =
            new DoubleArithmetic().toResultArithmetic();
        assertThat(new Fraction<>(arithmetic, 1d, 1d)
            .add(new Fraction<>(arithmetic, 1d, 2d))).isEqualTo(new Fraction<>(arithmetic, 3d, 2d));
    }

    @Test
    void subtractWithNumerator() {
        assertThat(new TestFraction(2).subtract(2, 1)).isEqualTo(new TestFraction(0d, 1d));
    }

    @Test
    void subtractWithXAndY() {
        assertThat(new TestFraction(2, 1).subtract(1, 1)).isEqualTo(new TestFraction(1d, 1d));
    }

    @Test
    void subtractWithFraction() {
        assertThat(new TestFraction(2, 1).subtract(new TestFraction(1, 2))).isEqualTo(new TestFraction(3d, 2d));
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyWithNumerator() {
        assertThat(new TestFraction(1).multiply(2)).isEqualTo(new TestFraction(2d, 1d));
    }

    @Test
    void multiplyWithXAndY() {
        assertThat(new TestFraction(1, 2).multiply(3, 4)).isEqualTo(new TestFraction(3d, 8d));
    }

    @Test
    void multiplyWithFraction() {
        assertThat(new TestFraction(1, 2)
            .multiply(new TestFraction(4, 3))).isEqualTo(new TestFraction(4d, 6d));
    }

    @Test
    void divideWithNumerator() {
        assertThat(new TestFraction(1).divide(2)).isEqualTo(new TestFraction(1d, 2d));
    }

    @Test
    void divideWithXAndY() {
        assertThat(new TestFraction(1, 2).divide(3, 4)).isEqualTo(new TestFraction(4d, 6d));
    }

    @Test
    void divideWithFraction() {
        assertThat(new TestFraction(1, 2)
            .divide(new TestFraction(4, 3))).isEqualTo(new TestFraction(3d, 8d));
    }

    // endregion

    // region pow and root

    @Test
    void pow2OfFraction1_2() {
        assertFraction(new TestFraction(1, 2).pow(2), 1d, 4d);
    }

    @Test
    void root2OfFraction1_4() {
        assertFraction(new TestFraction(1, 4).root(2), 1d, 2d);
    }

    // endregion

    // region map and copy

    @Test
    void mapOfFraction() {
        TestFraction fraction = new TestFraction(0.5, 1.5);
        Fraction<Integer, Integer> result = new Fraction<>(
            new IntegerArithmetic().toResultArithmetic(), 0, 1
        );
        assertThat(fraction.map(
            new IntegerArithmetic().toResultArithmetic(), Number::intValue
        )).isEqualTo(result);
    }

    @Test
    void mapValuesOfFractionWithArithmetic() {
        TestFraction fraction = new TestFraction(0.5, 1.5);
        Fraction<Integer, Number> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new TestAbstractArithmetic(), Number::intValue
            ), 0, 1
        );
        assertThat(fraction.mapValues(
            new IntegerArithmetic(), Number::intValue, Integer::intValue
        )).isEqualTo(result);
    }

    @Test
    void mapValuesOfFractionWithResultArithmetic() {
        TestFraction fraction = new TestFraction(0.5, 1.5);
        Fraction<Integer, Number> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new TestAbstractArithmetic(), Number::intValue
            ), 0, 1
        );
        assertThat(fraction.mapValues(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new TestAbstractArithmetic(), Number::intValue
            ), Number::intValue
        )).isEqualTo(result);
    }

    @Test
    void mapValueOfFraction() {
        TestFraction fraction = new TestFraction(0.5, 1.5);
        Fraction<Number, Integer> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new TestAbstractArithmetic(), new IntegerArithmetic(), Number::intValue
            ), 0.5, 1.5
        );
        assertThat(fraction.mapValue(
            new IntegerArithmetic(), Number::intValue
        )).isEqualTo(result);
    }

    @Test
    void copyOfFractionWithNuDe() {
        TestFraction complex = new TestFraction(2, 3);
        assertThat(complex.copy()).isEqualTo(complex);
    }

    // endregion

    // region override

    @Test
    void equalsOfFractionWithNuDe() {
        TestFraction complex = new TestFraction(2, 3);
        assertThat(new TestFraction(2, 3)).isEqualTo(complex);
        assertThat(new TestFraction(3, 2)).isNotEqualTo(complex);
    }

    @Test
    void hashCodeOfFractionWithNuDe() {
        assertThat(new TestFraction(2, 3).hashCode()).isEqualTo(1026);
    }

    @Test
    void toStringOfFractionWithNuDe() {
        TestFraction complex = new TestFraction(2, 3);
        assertThat(complex).hasToString("2/3");
    }

    @Test
    void compareToOfFractionWithNuDe() {
        TestFraction complex = new TestFraction(2, 3);
        assertThat(complex)
            .isEqualByComparingTo(new TestFraction(2, 3))
            .isLessThan(new TestFraction(3, 1))
            .isGreaterThan(new TestFraction(1, 2));
    }

    @Test
    void serializable() {
        assertSerializable(new TestFraction(2, 3), TestFraction.class);
    }

    // endregion

    // region protected

    @Test
    void createFromArithmeticWithDifferentNuDeParameter() {
        TestFraction fraction = new TestFraction(1);
        assertFraction(fraction.createFromArithmetic(2, 3), 2, 3);
    }

    @Test
    void createFromArithmeticWithZeroDeParameter() {
        TestFraction fraction = new TestFraction(1);
        assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> fraction.createFromArithmetic(1d, 0d)); // assert exception message?
    }

    @Test
    void simplifySignum() {
        assertFraction(new TestFraction(1, 1), 1, 1);
        assertFraction(new TestFraction(1, -1), -1d, 1d);
        assertFraction(new TestFraction(-1, 1), -1, 1);
        assertFraction(new TestFraction(-1, -1), 1d, 1d);
    }

    // endregion


    // region assert

    private static void assertFraction(TestFraction fraction) {
        assertFraction(fraction, 1d, 1d);
    }

    private static void assertFraction(
        Fraction<Number, Number> fraction, Number no, Number de
    ) {
        NumericAssertions.assertFraction(fraction, no, de);
    }

    // endregion
}
