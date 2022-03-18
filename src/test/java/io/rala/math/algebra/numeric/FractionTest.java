package io.rala.math.algebra.numeric;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.algebra.TestFraction;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatFraction;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FractionTest {
    // region constructors, getter and setter

    @Test
    void constructorWithNu() {
        assertThatFraction(new TestFraction(1d))
            .hasNumeratorWithOne()
            .hasDenominatorWithOne();
    }

    @Test
    void constructorWithDifferentNuDeParameter() {
        assertThatFraction(new TestFraction(2, 3))
            .hasNumerator(2).hasDenominator(3);
    }

    @Test
    void constructorWithZeroDeParameter() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestFraction(1d, 0d))
            .withMessage(ExceptionMessages.FRACTION_DENOMINATOR_HAS_TO_BE_NON_ZERO);
    }

    @Test
    void constructorWithFraction() {
        assertThatFraction(new TestFraction(new TestFraction(2, 3)))
            .hasNumerator(2).hasDenominator(3);
    }

    @Test
    void createAndSetNumerator() {
        TestFraction fraction = new TestFraction(1d);
        assertThatFraction(fraction).hasNumeratorWithOne().hasDenominatorWithOne();
        fraction.setNumerator(2);
        assertThatFraction(fraction)
            .hasNumerator(2).hasDenominatorWithOne();
    }

    @Test
    void createAndSetDenominator() {
        TestFraction fraction = new TestFraction(1d);
        assertThatFraction(fraction).hasNumeratorWithOne().hasDenominatorWithOne();
        fraction.setDenominator(3);
        assertThatFraction(fraction)
            .hasNumeratorWithOne().hasDenominator(3);
    }

    @Test
    void createAndSetDenominatorZero() {
        TestFraction fraction = new TestFraction(1);
        assertThatFraction(fraction)
            .hasNumeratorWithOne().hasDenominatorWithOne();
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> fraction.setDenominator(0d))
            .withMessage(ExceptionMessages.FRACTION_DENOMINATOR_HAS_TO_BE_NON_ZERO);
    }

    @Test
    void createAndSetDenominatorNull() {
        TestFraction fraction = new TestFraction(1, 2);
        assertThatFraction(fraction)
            .hasNumeratorWithOne().hasDenominator(2);
        fraction.setDenominator(null);
        assertThatFraction(fraction)
            .hasNumeratorWithOne().hasDenominatorWithOne();
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
        assertThatFraction(new TestFraction(1).negate())
            .hasNumerator(-1d).hasDenominatorWithOne();
    }

    @Test
    void inverse() {
        assertThatFraction(new TestFraction(1, 2).inverse())
            .hasNumerator(2).hasDenominator(1);
    }

    @Test
    void simplify() {
        assertThatFraction(new TestFraction(2, 4).simplify())
            .hasNumeratorWithOne().hasDenominator(2d);
    }

    @Test
    void simplifyDoubleFraction() {
        Fraction<Double, Double> fraction = new Fraction<>(
            new DoubleArithmetic().toResultArithmetic(), 2d, 4d
        );
        assertThatFraction(fraction.simplify()).isEqualTo(fraction);
    }

    // endregion

    // region add and subtract

    @Test
    void addWithNumerator() {
        assertThatFraction(new TestFraction(1).add(1, 1))
            .isEqualTo(new TestFraction(2d, 1d));
    }

    @Test
    void addWithXAndY() {
        assertThatFraction(new TestFraction(1, 1).add(1, 1))
            .isEqualTo(new TestFraction(2d, 1d));
    }

    @Test
    void addWithFraction() {
        assertThatFraction(new TestFraction(1, 1)
            .add(new TestFraction(1, 2))
        ).isEqualTo(new TestFraction(3d, 2d));
    }

    @Test
    void addWithDoubleFraction() {
        AbstractResultArithmetic<Double, Double> arithmetic =
            new DoubleArithmetic().toResultArithmetic();
        assertThatFraction(new Fraction<>(arithmetic, 1d, 1d)
            .add(new Fraction<>(arithmetic, 1d, 2d))
        ).isEqualTo(new Fraction<>(arithmetic, 3d, 2d));
    }

    @Test
    void subtractWithNumerator() {
        assertThatFraction(new TestFraction(2).subtract(2, 1))
            .isEqualTo(new TestFraction(0d, 1d));
    }

    @Test
    void subtractWithXAndY() {
        assertThatFraction(new TestFraction(2, 1).subtract(1, 1))
            .isEqualTo(new TestFraction(1d, 1d));
    }

    @Test
    void subtractWithFraction() {
        assertThatFraction(new TestFraction(2, 1)
            .subtract(new TestFraction(1, 2))
        )
            .isEqualTo(new TestFraction(3d, 2d));
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyWithNumerator() {
        assertThatFraction(new TestFraction(1).multiply(2))
            .isEqualTo(new TestFraction(2d, 1d));
    }

    @Test
    void multiplyWithXAndY() {
        assertThatFraction(new TestFraction(1, 2).multiply(3, 4))
            .isEqualTo(new TestFraction(3d, 8d));
    }

    @Test
    void multiplyWithFraction() {
        assertThatFraction(new TestFraction(1, 2)
            .multiply(new TestFraction(4, 3))
        ).isEqualTo(new TestFraction(4d, 6d));
    }

    @Test
    void divideWithNumerator() {
        assertThatFraction(new TestFraction(1).divide(2))
            .isEqualTo(new TestFraction(1d, 2d));
    }

    @Test
    void divideWithXAndY() {
        assertThatFraction(new TestFraction(1, 2).divide(3, 4))
            .isEqualTo(new TestFraction(4d, 6d));
    }

    @Test
    void divideWithFraction() {
        assertThatFraction(new TestFraction(1, 2)
            .divide(new TestFraction(4, 3))
        ).isEqualTo(new TestFraction(3d, 8d));
    }

    // endregion

    // region pow and root

    @Test
    void pow2OfFraction1_2() {
        assertThatFraction(new TestFraction(1, 2).pow(2))
            .hasNumeratorWithOne().hasDenominator(4d);
    }

    @Test
    void root2OfFraction1_4() {
        assertThatFraction(new TestFraction(1, 4).root(2))
            .hasNumeratorWithOne().hasDenominator(2d);
    }

    // endregion

    // region map, isValid and copy

    @Test
    void mapOfFraction() {
        TestFraction fraction = new TestFraction(0.5, 1.5);
        Fraction<Integer, Integer> result = new Fraction<>(
            new IntegerArithmetic().toResultArithmetic(), 0, 1
        );
        assertThatFraction(fraction.map(
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
        assertThatFraction(fraction.mapValues(
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
        assertThatFraction(fraction.mapValues(
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
        assertThatFraction(fraction.mapValue(
            new IntegerArithmetic(), Number::intValue
        )).isEqualTo(result);
    }

    @Test
    void isValidWithZero() {
        assertThatFraction(new TestFraction(0)).isValid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatFraction(new TestFraction(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY))
            .isInvalid();
    }

    @Test
    void copyOfFractionWithNuDe() {
        assertCopyable(new TestFraction(2, 3));
    }

    // endregion

    // region override

    @Test
    void equalsOfFractionWithNuDe() {
        assertThatFraction(new TestFraction(2, 3))
            .isEqualTo(new TestFraction(2, 3))
            .isNotEqualTo(new TestFraction(3, 2));
    }

    @Test
    void hashCodeOfFractionWithNuDe() {
        assertThat(new TestFraction(2, 3).hashCode()).isEqualTo(1026);
    }

    @Test
    void toStringOfFractionWithNuDe() {
        TestFraction fraction = new TestFraction(2, 3);
        assertThatFraction(fraction).hasToString("2/3");
    }

    @Test
    void compareToOfFractionWithNuDe() {
        TestFraction fraction = new TestFraction(2, 3);
        assertThatFraction(fraction)
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
        assertThatFraction(fraction.createFromArithmetic(2, 3))
            .hasNumerator(2).hasDenominator(3);
    }

    @Test
    void createFromArithmeticWithZeroDeParameter() {
        TestFraction fraction = new TestFraction(1);
        assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> fraction.createFromArithmetic(1d, 0d))
            .withMessage(ExceptionMessages.FRACTION_DENOMINATOR_HAS_TO_BE_NON_ZERO);
    }

    @Test
    void simplifySignum() {
        assertThatFraction(new TestFraction(1, 1))
            .hasNumeratorWithOne().hasDenominatorWithOne();
        assertThatFraction(new TestFraction(1, -1))
            .hasNumerator(-1d).hasDenominatorWithOne();
        assertThatFraction(new TestFraction(-1, 1))
            .hasNumerator(-1).hasDenominatorWithOne();
        assertThatFraction(new TestFraction(-1, -1))
            .hasNumeratorWithOne().hasDenominatorWithOne();
    }

    // endregion
}
