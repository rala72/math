package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.arithmetic.core.BigIntegerArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatFraction;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BigIntegerBigDecimalFractionTest {
    // region constructors, getter and setter

    @Test
    void constructorWithNu() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE))
            .hasNumeratorWithOne().hasDenominatorWithOne();
    }

    @Test
    void constructorWithNuMathContext5() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE, new MathContext(5)))
            .hasNumeratorWithOne().hasDenominatorWithOne();
    }

    @Test
    void constructorWithDifferentNuDeParameter() {
        assertThatFraction(
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3))
        ).hasNumerator(BigInteger.TWO).hasDenominator(BigInteger.valueOf(3));
    }


    @Test
    void constructorWithNuDeMathContext5() {
        assertThatFraction(
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO, new MathContext(5))
        ).hasNumeratorWithOne().hasDenominator(BigInteger.TWO);
    }

    @Test
    void constructorWithZeroDeParameter() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.ZERO))
            .withMessage(ExceptionMessages.FRACTION_DENOMINATOR_HAS_TO_BE_NON_ZERO);
    }

    @Test
    void constructorWithFraction() {
        assertThatFraction(new BigIntegerBigDecimalFraction(
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3)))
        ).hasNumerator(BigInteger.TWO).hasDenominator(BigInteger.valueOf(3));
    }

    @Test
    void createAndSetNumerator() {
        BigIntegerBigDecimalFraction fraction =
            new BigIntegerBigDecimalFraction(BigInteger.ONE);
        assertThatFraction(fraction).hasNumeratorWithOne().hasDenominatorWithOne();
        fraction.setNumerator(BigInteger.TWO);
        assertThatFraction(fraction).hasNumerator(BigInteger.TWO).hasDenominatorWithOne();
    }

    @Test
    void createAndSetDenominator() {
        BigIntegerBigDecimalFraction fraction =
            new BigIntegerBigDecimalFraction(BigInteger.ONE);
        assertThatFraction(fraction).hasNumeratorWithOne().hasDenominatorWithOne();
        fraction.setDenominator(BigInteger.valueOf(3));
        assertThatFraction(fraction).hasNumeratorWithOne().hasDenominator(BigInteger.valueOf(3));
    }

    @Test
    void createAndSetDenominatorZero() {
        BigIntegerBigDecimalFraction fraction =
            new BigIntegerBigDecimalFraction(BigInteger.ONE);
        assertThatFraction(fraction).hasNumeratorWithOne().hasDenominatorWithOne();
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> fraction.setDenominator(BigInteger.ZERO))
            .withMessage(ExceptionMessages.FRACTION_DENOMINATOR_HAS_TO_BE_NON_ZERO);
    }

    @Test
    void createAndSetDenominatorNull() {
        BigIntegerBigDecimalFraction fraction =
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO);
        assertThatFraction(fraction).hasNumeratorWithOne().hasDenominator(BigInteger.TWO);
        fraction.setDenominator(null);
        assertThatFraction(fraction).hasNumeratorWithOne().hasDenominatorWithOne();
    }

    // endregion

    // region value

    @Test
    void valueOf1_2() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            .value()).isEqualTo(BigDecimal.valueOf(0.5));
    }

    @Test
    void intValueOf1_2() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            .intValue()).isZero();
    }

    @Test
    void longValueOf1_2() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            .longValue()).isZero();
    }

    @Test
    void floatValueOf1_2() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO).floatValue())
            .isEqualTo(0.5f);
    }

    @Test
    void doubleValueOf1_2() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            .doubleValue()).isEqualTo(0.5);
    }

    // endregion

    // region negate, inverse and simplify

    @Test
    void negate() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE).negate())
            .hasNumerator(BigInteger.ONE.negate()).hasDenominatorWithOne();
    }

    @Test
    void inverse() {
        assertThatFraction(
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO).inverse()
        ).hasNumerator(BigInteger.TWO).hasDenominatorWithOne();
    }

    @Test
    void simplify() {
        assertThatFraction(new BigIntegerBigDecimalFraction(
            BigInteger.TWO, BigInteger.valueOf(4)
        ).simplify())
            .hasNumeratorWithOne().hasDenominator(BigInteger.TWO);
    }

    // endregion

    // region add and subtract

    @Test
    void addWithNumerator() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE)
            .add(BigInteger.ONE, BigInteger.ONE)
        ).isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE));
    }

    @Test
    void addWithXAndY() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.ONE)
            .add(BigInteger.ONE, BigInteger.ONE)
        ).isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE));
    }

    @Test
    void addWithFraction() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.ONE).add(
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
        )).isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.valueOf(3), BigInteger.TWO));
    }

    @Test
    void subtractWithNumerator() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.TWO)
            .subtract(BigInteger.TWO, BigInteger.ONE))
            .isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.ZERO, BigInteger.ONE));
    }

    @Test
    void subtractWithXAndY() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE)
            .subtract(BigInteger.ONE, BigInteger.ONE))
            .isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.ONE));
    }

    @Test
    void subtractWithFraction() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE).subtract(
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
        )).isEqualTo(new BigIntegerBigDecimalFraction(
            BigInteger.valueOf(3), BigInteger.TWO
        ));
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyWithNumerator() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE).multiply(BigInteger.TWO))
            .isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE));
    }

    @Test
    void multiplyWithXAndY() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            .multiply(BigInteger.valueOf(3), BigInteger.valueOf(4))
        ).isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.valueOf(3), BigInteger.valueOf(8)));
    }

    @Test
    void multiplyWithFraction() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            .multiply(new BigIntegerBigDecimalFraction(
                BigInteger.valueOf(4), BigInteger.valueOf(3)
            ))
        ).isEqualTo(new BigIntegerBigDecimalFraction(
            BigInteger.valueOf(4), BigInteger.valueOf(6)
        ));
    }

    @Test
    void divideWithNumerator() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE).divide(BigInteger.TWO))
            .isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO));
    }

    @Test
    void divideWithXAndY() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            .divide(BigInteger.valueOf(3), BigInteger.valueOf(4))
        ).isEqualTo(new BigIntegerBigDecimalFraction(
            BigInteger.valueOf(4), BigInteger.valueOf(6)
        ));
    }

    @Test
    void divideWithFraction() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            .divide(new BigIntegerBigDecimalFraction(
                BigInteger.valueOf(4), BigInteger.valueOf(3)
            ))
        ).isEqualTo(new BigIntegerBigDecimalFraction(
            BigInteger.valueOf(3), BigInteger.valueOf(8)
        ));
    }

    // endregion

    // region pow and root

    @Test
    void pow2OfFraction1_2() {
        assertThatFraction(
            new BigIntegerBigDecimalFraction(
                BigInteger.ONE, BigInteger.TWO
            ).pow(2)
        ).hasNumeratorWithOne().hasDenominator(BigInteger.valueOf(4));
    }

    @Test
    void root2OfFraction1_4() {
        assertThatFraction(
            new BigIntegerBigDecimalFraction(
                BigInteger.ONE, BigInteger.valueOf(4)
            ).root(2)
        ).hasNumeratorWithOne().hasDenominator(BigInteger.TWO);
    }

    // endregion

    // region map, isValid and copy

    @Test
    void mapOfFraction() {
        BigIntegerBigDecimalFraction fraction =
            new BigIntegerBigDecimalFraction(BigInteger.ZERO, BigInteger.ONE);
        Fraction<Integer, Integer> result = new Fraction<>(
            new IntegerArithmetic().toResultArithmetic(), 0, 1
        );
        assertThatFraction(fraction.map(
            new IntegerArithmetic().toResultArithmetic(), Number::intValue
        )).isEqualTo(result);
    }

    @Test
    void mapValuesOfFractionWithArithmetic() {
        BigIntegerBigDecimalFraction fraction =
            new BigIntegerBigDecimalFraction(BigInteger.ZERO, BigInteger.ONE);
        Fraction<Integer, BigDecimal> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new BigDecimalArithmetic(), BigDecimal::valueOf
            ), 0, 1
        );
        assertThatFraction(fraction.mapValues(
            new IntegerArithmetic(), BigInteger::intValue, BigDecimal::valueOf
        )).isEqualTo(result);
    }

    @Test
    void mapValuesOfFractionWithResultArithmetic() {
        BigIntegerBigDecimalFraction fraction =
            new BigIntegerBigDecimalFraction(BigInteger.ZERO, BigInteger.ONE);
        Fraction<Integer, BigDecimal> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new BigDecimalArithmetic(), BigDecimal::valueOf
            ), 0, 1
        );
        assertThatFraction(fraction.mapValues(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new BigDecimalArithmetic(), BigDecimal::valueOf
            ), Number::intValue
        )).isEqualTo(result);
    }

    @Test
    void mapValueOfFraction() {
        BigIntegerBigDecimalFraction fraction =
            new BigIntegerBigDecimalFraction(BigInteger.ZERO, BigInteger.ONE);
        Fraction<BigInteger, Integer> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new BigIntegerArithmetic(), new IntegerArithmetic(), BigInteger::intValue
            ), BigInteger.ZERO, BigInteger.ONE
        );
        assertThatFraction(fraction.mapValue(
            new IntegerArithmetic(), Number::intValue
        )).isEqualTo(result);
    }

    @Test
    void isValidWithZero() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.ZERO)).isValid();
    }

    @Test
    void copyOfFractionWithNuDe() {
        assertCopyable(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3)));
    }

    // endregion

    // region override

    @Test
    void equalsOfFractionWithNuDe() {
        assertThatFraction(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3)))
            .isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3)))
            .isNotEqualTo(new BigIntegerBigDecimalFraction(BigInteger.valueOf(3), BigInteger.TWO));
    }

    @Test
    void hashCodeOfFractionWithNuDe() {
        assertThat(new BigIntegerBigDecimalFraction(
            BigInteger.TWO, BigInteger.valueOf(3)
        ).hashCode()).isEqualTo(1026);
    }

    @Test
    void toStringOfFractionWithNuDe() {
        BigIntegerBigDecimalFraction fraction =
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3));
        assertThatFraction(fraction).hasToString("2/3");
    }

    @Test
    void compareToOfFractionWithNuDe() {
        BigIntegerBigDecimalFraction fraction =
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3));
        assertThatFraction(fraction)
            .isEqualByComparingTo(new BigIntegerBigDecimalFraction(
                BigInteger.TWO, BigInteger.valueOf(3)
            ))
            .isLessThan(new BigIntegerBigDecimalFraction(
                BigInteger.valueOf(3), BigInteger.ONE
            ))
            .isGreaterThan(new BigIntegerBigDecimalFraction(
                BigInteger.ONE, BigInteger.valueOf(3)
            ));
    }

    @Test
    void serializable() {
        assertSerializable(
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3)),
            BigIntegerBigDecimalFraction.class
        );
    }

    // endregion
}
