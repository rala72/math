package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.arithmetic.core.LongArithmetic;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatFraction;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class LongDoubleFractionTest {
    // region constructors, getter and setter

    @Test
    void constructorWithNu() {
        assertThatFraction(new LongDoubleFraction(1))
            .hasNumeratorWithOne().hasDenominatorWithOne();
    }

    @Test
    void constructorWithDifferentNuDeParameter() {
        assertThatFraction(new LongDoubleFraction(2L, 3L))
            .hasNumerator(2L).hasDenominator(3L);
    }

    @Test
    void constructorWithZeroDeParameter() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new LongDoubleFraction(1, 0L))
            .withMessage(ExceptionMessages.FRACTION_DENOMINATOR_HAS_TO_BE_NON_ZERO);
    }

    @Test
    void constructorWithFraction() {
        assertThatFraction(new LongDoubleFraction(new LongDoubleFraction(2L, 3L)))
            .hasNumerator(2L).hasDenominator(3L);
    }

    @Test
    void createAndSetNumerator() {
        LongDoubleFraction fraction = new LongDoubleFraction(1);
        assertThatFraction(fraction).hasNumeratorWithOne().hasDenominatorWithOne();
        fraction.setNumerator(2L);
        assertThatFraction(fraction)
            .hasNumerator(2L).hasDenominatorWithOne();
    }

    @Test
    void createAndSetDenominator() {
        LongDoubleFraction fraction = new LongDoubleFraction(1);
        assertThatFraction(fraction).hasNumeratorWithOne().hasDenominatorWithOne();
        fraction.setDenominator(3L);
        assertThatFraction(fraction)
            .hasNumeratorWithOne().hasDenominator(3L);
    }

    @Test
    void createAndSetDenominatorZero() {
        LongDoubleFraction fraction = new LongDoubleFraction(1);
        assertThatFraction(fraction).hasNumeratorWithOne().hasDenominatorWithOne();
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> fraction.setDenominator(0L))
            .withMessage(ExceptionMessages.FRACTION_DENOMINATOR_HAS_TO_BE_NON_ZERO);
    }

    @Test
    void createAndSetDenominatorNull() {
        LongDoubleFraction fraction = new LongDoubleFraction(1, 2L);
        assertThatFraction(fraction)
            .hasNumeratorWithOne().hasDenominator(2L);
        fraction.setDenominator(null);
        assertThatFraction(fraction)
            .hasNumeratorWithOne().hasDenominatorWithOne();
    }

    // endregion

    // region value

    @Test
    void valueOf1_2() {
        assertThat(new LongDoubleFraction(1, 2L).value()).isEqualTo(0.5);
    }

    @Test
    void intValueOf1_2() {
        assertThat(new LongDoubleFraction(1, 2L).intValue()).isZero();
    }

    @Test
    void longValueOf1_2() {
        assertThat(new LongDoubleFraction(1, 2L).longValue()).isZero();
    }

    @Test
    void floatValueOf1_2() {
        assertThat(new LongDoubleFraction(1, 2L).floatValue()).isEqualTo(0.5f);
    }

    @Test
    void doubleValueOf1_2() {
        assertThat(new LongDoubleFraction(1, 2L).doubleValue()).isEqualTo(0.5);
    }

    // endregion

    // region negate, inverse and simplify

    @Test
    void negate() {
        assertThatFraction(new LongDoubleFraction(1).negate())
            .hasNumerator(-1L).hasDenominatorWithOne();
    }

    @Test
    void inverse() {
        assertThatFraction(new LongDoubleFraction(1, 2L).inverse())
            .hasNumerator(2L).hasDenominatorWithOne();
    }

    @Test
    void simplify() {
        assertThatFraction(
            new LongDoubleFraction(2L, 4L).simplify()
        ).hasNumeratorWithOne().hasDenominator(2L);
    }

    // endregion

    // region add and subtract

    @Test
    void addWithNumerator() {
        assertThatFraction(new LongDoubleFraction(1).add(1L, 1L))
            .isEqualTo(new LongDoubleFraction(2, 1L));
    }

    @Test
    void addWithXAndY() {
        assertThatFraction(new LongDoubleFraction(1, 1L).add(1L, 1L))
            .isEqualTo(new LongDoubleFraction(2, 1L));
    }

    @Test
    void addWithFraction() {
        assertThatFraction(new LongDoubleFraction(1, 1L)
            .add(new LongDoubleFraction(1, 2L))
        )
            .isEqualTo(new LongDoubleFraction(3, 2L));
    }

    @Test
    void subtractWithNumerator() {
        assertThatFraction(new LongDoubleFraction(2).subtract(2L, 1L))
            .isEqualTo(new LongDoubleFraction(0, 1L));
    }

    @Test
    void subtractWithXAndY() {
        assertThatFraction(new LongDoubleFraction(2, 1L).subtract(1L, 1L))
            .isEqualTo(new LongDoubleFraction(1, 1L));
    }

    @Test
    void subtractWithFraction() {
        assertThatFraction(new LongDoubleFraction(2, 1L)
            .subtract(new LongDoubleFraction(1, 2L))
        ).isEqualTo(new LongDoubleFraction(3, 2L));
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyWithNumerator() {
        assertThatFraction(new LongDoubleFraction(1).multiply(2L))
            .isEqualTo(new LongDoubleFraction(2, 1L));
    }

    @Test
    void multiplyWithXAndY() {
        assertThatFraction(new LongDoubleFraction(1, 2L).multiply(3L, 4L))
            .isEqualTo(new LongDoubleFraction(3, 8L));
    }

    @Test
    void multiplyWithFraction() {
        assertThatFraction(new LongDoubleFraction(1, 2L)
            .multiply(new LongDoubleFraction(4, 3L))
        ).isEqualTo(new LongDoubleFraction(4, 6L));
    }

    @Test
    void divideWithNumerator() {
        assertThatFraction(new LongDoubleFraction(1).divide(2L))
            .isEqualTo(new LongDoubleFraction(1, 2L));
    }

    @Test
    void divideWithXAndY() {
        assertThatFraction(new LongDoubleFraction(1, 2L).divide(3L, 4L))
            .isEqualTo(new LongDoubleFraction(4, 6L));
    }

    @Test
    void divideWithFraction() {
        assertThatFraction(new LongDoubleFraction(1, 2L)
            .divide(new LongDoubleFraction(4, 3L))
        ).isEqualTo(new LongDoubleFraction(3, 8L));
    }

    // endregion

    // region pow and root

    @Test
    void pow2OfFraction1_2() {
        assertThatFraction(new LongDoubleFraction(1, 2L).pow(2))
            .hasNumeratorWithOne().hasDenominator(4L);
    }

    @Test
    void root2OfFraction1_4() {
        assertThatFraction(new LongDoubleFraction(1, 4L).root(2))
            .hasNumeratorWithOne().hasDenominator(2L);
    }

    // endregion

    // region map, isValid and copy

    @Test
    void mapOfFraction() {
        LongDoubleFraction fraction = new LongDoubleFraction(0, 1L);
        Fraction<Integer, Integer> result = new Fraction<>(
            new IntegerArithmetic().toResultArithmetic(), 0, 1
        );
        assertThatFraction(fraction.map(
            new IntegerArithmetic().toResultArithmetic(), Number::intValue
        )).isEqualTo(result);
    }

    @Test
    void mapValuesOfFractionWithArithmetic() {
        LongDoubleFraction fraction = new LongDoubleFraction(0, 1L);
        Fraction<Integer, Double> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new DoubleArithmetic(), Integer::doubleValue
            ), 0, 1
        );
        assertThatFraction(fraction.mapValues(
            new IntegerArithmetic(), Long::intValue, Integer::doubleValue
        )).isEqualTo(result);
    }

    @Test
    void mapValuesOfFractionWithResultArithmetic() {
        LongDoubleFraction fraction = new LongDoubleFraction(0, 1L);
        Fraction<Integer, Double> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new DoubleArithmetic(), Integer::doubleValue
            ), 0, 1
        );
        assertThatFraction(fraction.mapValues(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new DoubleArithmetic(), Integer::doubleValue
            ), Number::intValue
        )).isEqualTo(result);
    }

    @Test
    void mapValueOfFraction() {
        LongDoubleFraction fraction = new LongDoubleFraction(0, 1L);
        Fraction<Long, Integer> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new LongArithmetic(), new IntegerArithmetic(), Long::intValue
            ), 0L, 1L
        );
        assertThatFraction(fraction.mapValue(
            new IntegerArithmetic(), Number::intValue
        )).isEqualTo(result);
    }

    @Test
    void isValidWithZero() {
        assertThatFraction(new LongDoubleFraction(0, 1L)).isValid();
    }

    @Test
    void copyOfFractionWithNuDe() {
        assertCopyable(new LongDoubleFraction(2, 3L));
    }

    // endregion

    // region override

    @Test
    void equalsOfFractionWithNuDe() {
        assertThatFraction(new LongDoubleFraction(2, 3L))
            .isEqualTo(new LongDoubleFraction(2, 3L))
            .isNotEqualTo(new LongDoubleFraction(3, 2L));
    }

    @Test
    void hashCodeOfFractionWithNuDe() {
        assertThat(new LongDoubleFraction(2, 3L).hashCode()).isEqualTo(1026);
    }

    @Test
    void toStringOfFractionWithNuDe() {
        LongDoubleFraction fraction = new LongDoubleFraction(2, 3L);
        assertThatFraction(fraction).hasToString("2/3");
    }

    @Test
    void compareToOfFractionWithNuDe() {
        LongDoubleFraction fraction = new LongDoubleFraction(2, 3L);
        assertThatFraction(fraction)
            .isEqualByComparingTo(new LongDoubleFraction(2, 3L))
            .isLessThan(new LongDoubleFraction(3, 1L))
            .isGreaterThan(new LongDoubleFraction(1, 2L));
    }

    @Test
    void serializable() {
        assertSerializable(
            new LongDoubleFraction(2, 3L),
            LongDoubleFraction.class
        );
    }

    // endregion
}
