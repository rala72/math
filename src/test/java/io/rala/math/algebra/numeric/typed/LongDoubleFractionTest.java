package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.arithmetic.core.LongArithmetic;
import io.rala.math.testUtils.assertion.NumericAssertions;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class LongDoubleFractionTest {
    // region constructors, getter and setter

    @Test
    void constructorWithNu() {
        assertFraction(new LongDoubleFraction(1));
    }

    @Test
    void constructorWithDifferentNuDeParameter() {
        assertFraction(new LongDoubleFraction(2L, 3L), 2, 3);
    }

    @Test
    void constructorWithZeroDeParameter() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new LongDoubleFraction(1, 0L)); // assert exception message?
    }

    @Test
    void constructorWithFraction() {
        assertFraction(new LongDoubleFraction(new LongDoubleFraction(2L, 3L)), 2, 3);
    }

    @Test
    void createAndSetNumerator() {
        LongDoubleFraction fraction = new LongDoubleFraction(1);
        assertFraction(fraction);
        fraction.setNumerator(2L);
        assertFraction(fraction, 2, 1);
    }

    @Test
    void createAndSetDenominator() {
        LongDoubleFraction fraction = new LongDoubleFraction(1);
        assertFraction(fraction);
        fraction.setDenominator(3L);
        assertFraction(fraction, 1, 3);
    }

    @Test
    void createAndSetDenominatorZero() {
        LongDoubleFraction fraction = new LongDoubleFraction(1);
        assertFraction(fraction);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> fraction.setDenominator(0L)); // assert exception message?
    }

    @Test
    void createAndSetDenominatorNull() {
        LongDoubleFraction fraction = new LongDoubleFraction(1, 2L);
        assertFraction(fraction, 1, 2);
        fraction.setDenominator(null);
        assertFraction(fraction, 1, 1);
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
        assertFraction(new LongDoubleFraction(1).negate(), -1, 1);
    }

    @Test
    void inverse() {
        assertFraction(new LongDoubleFraction(1, 2L).inverse(), 2, 1);
    }

    @Test
    void simplify() {
        assertFraction(
            new LongDoubleFraction(2L, 4L).simplify(),
            1L, 2L
        );
    }

    // endregion

    // region add and subtract

    @Test
    void addWithNumerator() {
        assertThat(new LongDoubleFraction(1).add(1L, 1L)).isEqualTo(new LongDoubleFraction(2, 1L));
    }

    @Test
    void addWithXAndY() {
        assertThat(new LongDoubleFraction(1, 1L).add(1L, 1L)).isEqualTo(new LongDoubleFraction(2, 1L));
    }

    @Test
    void addWithFraction() {
        assertThat(new LongDoubleFraction(1, 1L)
            .add(new LongDoubleFraction(1, 2L))).isEqualTo(new LongDoubleFraction(3, 2L));
    }

    @Test
    void subtractWithNumerator() {
        assertThat(new LongDoubleFraction(2).subtract(2L, 1L)).isEqualTo(new LongDoubleFraction(0, 1L));
    }

    @Test
    void subtractWithXAndY() {
        assertThat(new LongDoubleFraction(2, 1L).subtract(1L, 1L)).isEqualTo(new LongDoubleFraction(1, 1L));
    }

    @Test
    void subtractWithFraction() {
        assertThat(new LongDoubleFraction(2, 1L)
            .subtract(new LongDoubleFraction(1, 2L))).isEqualTo(new LongDoubleFraction(3, 2L));
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyWithNumerator() {
        assertThat(new LongDoubleFraction(1).multiply(2L)).isEqualTo(new LongDoubleFraction(2, 1L));
    }

    @Test
    void multiplyWithXAndY() {
        assertThat(new LongDoubleFraction(1, 2L).multiply(3L, 4L)).isEqualTo(new LongDoubleFraction(3, 8L));
    }

    @Test
    void multiplyWithFraction() {
        assertThat(new LongDoubleFraction(1, 2L)
            .multiply(new LongDoubleFraction(4, 3L))).isEqualTo(new LongDoubleFraction(4, 6L));
    }

    @Test
    void divideWithNumerator() {
        assertThat(new LongDoubleFraction(1).divide(2L)).isEqualTo(new LongDoubleFraction(1, 2L));
    }

    @Test
    void divideWithXAndY() {
        assertThat(new LongDoubleFraction(1, 2L).divide(3L, 4L)).isEqualTo(new LongDoubleFraction(4, 6L));
    }

    @Test
    void divideWithFraction() {
        assertThat(new LongDoubleFraction(1, 2L)
            .divide(new LongDoubleFraction(4, 3L))).isEqualTo(new LongDoubleFraction(3, 8L));
    }

    // endregion

    // region pow and root

    @Test
    void pow2OfFraction1_2() {
        assertFraction(new LongDoubleFraction(1, 2L).pow(2), 1, 4);
    }

    @Test
    void root2OfFraction1_4() {
        assertFraction(new LongDoubleFraction(1, 4L).root(2), 1, 2);
    }

    // endregion

    // region map, isValid and copy

    @Test
    void mapOfFraction() {
        LongDoubleFraction fraction = new LongDoubleFraction(0, 1L);
        Fraction<Integer, Integer> result = new Fraction<>(
            new IntegerArithmetic().toResultArithmetic(), 0, 1
        );
        assertThat(fraction.map(
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
        assertThat(fraction.mapValues(
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
        assertThat(fraction.mapValues(
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
        assertThat(fraction.mapValue(
            new IntegerArithmetic(), Number::intValue
        )).isEqualTo(result);
    }

    @Test
    void isValidWithZero() {
        assertThat(new LongDoubleFraction(0, 1L).isValid()).isTrue();
    }

    @Test
    void copyOfFractionWithNuDe() {
        LongDoubleFraction fraction = new LongDoubleFraction(2, 3L);
        assertThat(fraction.copy()).isEqualTo(fraction);
    }

    // endregion

    // region override

    @Test
    void equalsOfFractionWithNuDe() {
        assertThat(new LongDoubleFraction(2, 3L))
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
        assertThat(fraction).hasToString("2/3");
    }

    @Test
    void compareToOfFractionWithNuDe() {
        LongDoubleFraction fraction = new LongDoubleFraction(2, 3L);
        assertThat(fraction)
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


    // region assert

    private static void assertFraction(LongDoubleFraction fraction) {
        assertFraction(fraction, 1, 1);
    }

    private static void assertFraction(
        Fraction<Long, Double> fraction, long no, long de
    ) {
        NumericAssertions.assertFraction(fraction, no, de);
    }

    // endregion
}
