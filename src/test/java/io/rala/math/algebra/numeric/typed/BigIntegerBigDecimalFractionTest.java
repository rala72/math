package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.arithmetic.core.BigIntegerArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.assertion.NumericAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BigIntegerBigDecimalFractionTest {
    // region constructors, getter and setter

    @Test
    void constructorWithNu() {
        assertFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE));
    }

    @Test
    void constructorWithNuMathContext5() {
        assertFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE, new MathContext(5)));
    }

    @Test
    void constructorWithDifferentNuDeParameter() {
        assertFraction(
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3)),
            BigInteger.TWO, BigInteger.valueOf(3)
        );
    }


    @Test
    void constructorWithNuDeMathContext5() {
        assertFraction(
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO, new MathContext(5)),
            BigInteger.ONE, BigInteger.TWO
        );
    }

    @Test
    void constructorWithZeroDeParameter() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.ZERO)); // assert exception message?
    }

    @Test
    void constructorWithFraction() {
        assertFraction(new BigIntegerBigDecimalFraction(
                new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3))),
            BigInteger.TWO, BigInteger.valueOf(3)
        );
    }

    @Test
    void createAndSetNumerator() {
        BigIntegerBigDecimalFraction complex =
            new BigIntegerBigDecimalFraction(BigInteger.ONE);
        assertFraction(complex);
        complex.setNumerator(BigInteger.TWO);
        assertFraction(complex, BigInteger.TWO, BigInteger.ONE);
    }

    @Test
    void createAndSetDenominator() {
        BigIntegerBigDecimalFraction complex =
            new BigIntegerBigDecimalFraction(BigInteger.ONE);
        assertFraction(complex);
        complex.setDenominator(BigInteger.valueOf(3));
        assertFraction(complex, BigInteger.ONE, BigInteger.valueOf(3));
    }

    @Test
    void createAndSetDenominatorZero() {
        BigIntegerBigDecimalFraction complex =
            new BigIntegerBigDecimalFraction(BigInteger.ONE);
        assertFraction(complex);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> complex.setDenominator(BigInteger.ZERO)); // assert exception message?
    }

    @Test
    void createAndSetDenominatorNull() {
        BigIntegerBigDecimalFraction complex =
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO);
        assertFraction(complex, BigInteger.ONE, BigInteger.TWO);
        complex.setDenominator(null);
        assertFraction(complex, BigInteger.ONE, BigInteger.ONE);
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
        assertFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE).negate(),
            BigInteger.ONE.negate(), BigInteger.ONE
        );
    }

    @Test
    void inverse() {
        assertFraction(
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO).inverse(),
            BigInteger.TWO, BigInteger.ONE
        );
    }

    @Test
    void simplify() {
        assertFraction(new BigIntegerBigDecimalFraction(
            BigInteger.TWO, BigInteger.valueOf(4)
        ).simplify(), BigInteger.ONE, BigInteger.TWO);
    }

    // endregion

    // region add and subtract

    @Test
    void addWithNumerator() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE)
            .add(BigInteger.ONE, BigInteger.ONE)).isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE));
    }

    @Test
    void addWithXAndY() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.ONE)
            .add(BigInteger.ONE, BigInteger.ONE)).isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE));
    }

    @Test
    void addWithFraction() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.ONE).add(
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
        )).isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.valueOf(3), BigInteger.TWO));
    }

    @Test
    void subtractWithNumerator() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.TWO)
            .subtract(BigInteger.TWO, BigInteger.ONE))
            .isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.ZERO, BigInteger.ONE));
    }

    @Test
    void subtractWithXAndY() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE)
            .subtract(BigInteger.ONE, BigInteger.ONE))
            .isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.ONE));
    }

    @Test
    void subtractWithFraction() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE).subtract(
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
        )).isEqualTo(new BigIntegerBigDecimalFraction(
            BigInteger.valueOf(3), BigInteger.TWO
        ));
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyWithNumerator() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE).multiply(BigInteger.TWO))
            .isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE));
    }

    @Test
    void multiplyWithXAndY() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            .multiply(BigInteger.valueOf(3), BigInteger.valueOf(4))).isEqualTo(new BigIntegerBigDecimalFraction(
            BigInteger.valueOf(3), BigInteger.valueOf(8)
        ));
    }

    @Test
    void multiplyWithFraction() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            .multiply(new BigIntegerBigDecimalFraction(
                BigInteger.valueOf(4), BigInteger.valueOf(3)
            ))).isEqualTo(new BigIntegerBigDecimalFraction(
            BigInteger.valueOf(4), BigInteger.valueOf(6)
        ));
    }

    @Test
    void divideWithNumerator() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE).divide(BigInteger.TWO))
            .isEqualTo(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO));
    }

    @Test
    void divideWithXAndY() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            .divide(BigInteger.valueOf(3), BigInteger.valueOf(4))).isEqualTo(new BigIntegerBigDecimalFraction(
            BigInteger.valueOf(4), BigInteger.valueOf(6)
        ));
    }

    @Test
    void divideWithFraction() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            .divide(new BigIntegerBigDecimalFraction(
                BigInteger.valueOf(4), BigInteger.valueOf(3)
            ))).isEqualTo(new BigIntegerBigDecimalFraction(
            BigInteger.valueOf(3), BigInteger.valueOf(8)
        ));
    }

    // endregion

    // region pow and root

    @Test
    void pow2OfFraction1_2() {
        assertFraction(
            new BigIntegerBigDecimalFraction(
                BigInteger.ONE, BigInteger.TWO
            ).pow(2),
            BigInteger.ONE, BigInteger.valueOf(4)
        );
    }

    @Test
    void root2OfFraction1_4() {
        assertFraction(
            new BigIntegerBigDecimalFraction(
                BigInteger.ONE, BigInteger.valueOf(4)
            ).root(2),
            BigInteger.ONE, BigInteger.TWO
        );
    }

    // endregion

    // region map and copy

    @Test
    void mapOfFraction() {
        BigIntegerBigDecimalFraction fraction =
            new BigIntegerBigDecimalFraction(BigInteger.ZERO, BigInteger.ONE);
        Fraction<Integer, Integer> result = new Fraction<>(
            new IntegerArithmetic().toResultArithmetic(), 0, 1
        );
        assertThat(fraction.map(
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
        assertThat(fraction.mapValues(
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
        assertThat(fraction.mapValues(
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
        assertThat(fraction.mapValue(
            new IntegerArithmetic(), Number::intValue
        )).isEqualTo(result);
    }

    @Test
    void copyOfFractionWithNuDe() {
        BigIntegerBigDecimalFraction complex =
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3));
        assertThat(complex.copy()).isEqualTo(complex);
    }

    // endregion

    // region override

    @Test
    void equalsOfFractionWithNuDe() {
        assertThat(new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3)))
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
        BigIntegerBigDecimalFraction complex =
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3));
        assertThat(complex).hasToString("2/3");
    }

    @Test
    void compareToOfFractionWithNuDe() {
        BigIntegerBigDecimalFraction complex =
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3));
        assertThat(complex)
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


    // region assert

    private static void assertFraction(BigIntegerBigDecimalFraction fraction) {
        assertFraction(fraction, BigInteger.ONE, BigInteger.ONE);
    }

    private static void assertFraction(
        Fraction<BigInteger, BigDecimal> fraction, BigInteger no, BigInteger de
    ) {
        NumericAssertions.assertFraction(fraction, no, de);
    }

    // endregion
}
