package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.arithmetic.core.BigIntegerArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.assertion.NumericAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

class BigIntegerBigDecimalFractionTest {
    // region constructors, getter and setter

    @Test
    void constructorWithNu() {
        assertFraction(new BigIntegerBigDecimalFraction(BigInteger.ONE));
    }

    @Test
    void constructorWithDifferentNuDeParameter() {
        assertFraction(
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3)),
            BigInteger.TWO, BigInteger.valueOf(3)
        );
    }

    @Test
    void constructorWithNullNuParameter() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new BigIntegerBigDecimalFraction((BigInteger) null)
        ); // assert exception message?
    }

    @Test
    void constructorWithZeroDeParameter() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.ZERO)
        ); // assert exception message?
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
    void createAndSetNumeratorNull() {
        BigIntegerBigDecimalFraction complex =
            new BigIntegerBigDecimalFraction(BigInteger.ONE);
        assertFraction(complex);
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> complex.setNumerator(null)
        ); // assert exception message?
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
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> complex.setDenominator(BigInteger.ZERO)
        ); // assert exception message?
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
        Assertions.assertEquals(BigDecimal.valueOf(0.5),
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
                .value()
        );
    }

    @Test
    void intValueOf1_2() {
        Assertions.assertEquals(0,
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
                .intValue()
        );
    }

    @Test
    void longValueOf1_2() {
        Assertions.assertEquals(0,
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
                .longValue()
        );
    }

    @Test
    void floatValueOf1_2() {
        Assertions.assertEquals(0.5,
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
                .floatValue()
        );
    }

    @Test
    void doubleValueOf1_2() {
        Assertions.assertEquals(0.5,
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
                .doubleValue()
        );
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
        Assertions.assertEquals(
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE),
            new BigIntegerBigDecimalFraction(BigInteger.ONE)
                .add(BigInteger.ONE, BigInteger.ONE)
        );
    }

    @Test
    void addWithXAndY() {
        Assertions.assertEquals(
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE),
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.ONE)
                .add(BigInteger.ONE, BigInteger.ONE)
        );
    }

    @Test
    void addWithFraction() {
        Assertions.assertEquals(
            new BigIntegerBigDecimalFraction(BigInteger.valueOf(3), BigInteger.TWO),
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.ONE).add(
                new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            )
        );
    }

    @Test
    void subtractWithNumerator() {
        Assertions.assertEquals(
            new BigIntegerBigDecimalFraction(BigInteger.ZERO, BigInteger.ONE),
            new BigIntegerBigDecimalFraction(BigInteger.TWO)
                .subtract(BigInteger.TWO, BigInteger.ONE)
        );
    }

    @Test
    void subtractWithXAndY() {
        Assertions.assertEquals(
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.ONE),
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE)
                .subtract(BigInteger.ONE, BigInteger.ONE)
        );
    }

    @Test
    void subtractWithFraction() {
        Assertions.assertEquals(new BigIntegerBigDecimalFraction(
                BigInteger.valueOf(3), BigInteger.TWO
            ),
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE).subtract(
                new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
            )
        );
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyWithNumerator() {
        Assertions.assertEquals(
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.ONE),
            new BigIntegerBigDecimalFraction(BigInteger.ONE).multiply(BigInteger.TWO)
        );
    }

    @Test
    void multiplyWithXAndY() {
        Assertions.assertEquals(
            new BigIntegerBigDecimalFraction(
                BigInteger.valueOf(3), BigInteger.valueOf(8)
            ),
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
                .multiply(BigInteger.valueOf(3), BigInteger.valueOf(4))
        );
    }

    @Test
    void multiplyWithFraction() {
        Assertions.assertEquals(new BigIntegerBigDecimalFraction(
                BigInteger.valueOf(4), BigInteger.valueOf(6)
            ),
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
                .multiply(new BigIntegerBigDecimalFraction(
                    BigInteger.valueOf(4), BigInteger.valueOf(3)
                ))
        );
    }

    @Test
    void divideWithNumerator() {
        Assertions.assertEquals(
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO),
            new BigIntegerBigDecimalFraction(BigInteger.ONE).divide(BigInteger.TWO)
        );
    }

    @Test
    void divideWithXAndY() {
        Assertions.assertEquals(
            new BigIntegerBigDecimalFraction(
                BigInteger.valueOf(4), BigInteger.valueOf(6)
            ),
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
                .divide(BigInteger.valueOf(3), BigInteger.valueOf(4))
        );
    }

    @Test
    void divideWithFraction() {
        Assertions.assertEquals(new BigIntegerBigDecimalFraction(
                BigInteger.valueOf(3), BigInteger.valueOf(8)
            ),
            new BigIntegerBigDecimalFraction(BigInteger.ONE, BigInteger.TWO)
                .divide(new BigIntegerBigDecimalFraction(
                    BigInteger.valueOf(4), BigInteger.valueOf(3)
                ))
        );
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
        Assertions.assertEquals(result, fraction.map(
            new IntegerArithmetic().toResultArithmetic(), Number::intValue
        ));
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
        Assertions.assertEquals(result, fraction.mapValues(
            new IntegerArithmetic(), BigInteger::intValue, BigDecimal::valueOf
        ));
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
        Assertions.assertEquals(result, fraction.mapValues(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new BigDecimalArithmetic(), BigDecimal::valueOf
            ), Number::intValue
        ));
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
        Assertions.assertEquals(result, fraction.mapValue(
            new IntegerArithmetic(), Number::intValue
        ));
    }

    @Test
    void copyOfFractionWithNuDe() {
        BigIntegerBigDecimalFraction complex =
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3));
        Assertions.assertEquals(complex, complex.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfFractionWithNuDe() {
        BigIntegerBigDecimalFraction complex =
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3));
        Assertions.assertEquals(
            complex,
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3))
        );
        Assertions.assertNotEquals(
            complex,
            new BigIntegerBigDecimalFraction(BigInteger.valueOf(3), BigInteger.TWO)
        );
    }

    @Test
    void hashCodeOfFractionWithNuDe() {
        Assertions.assertEquals(
            1026,
            new BigIntegerBigDecimalFraction(
                BigInteger.TWO, BigInteger.valueOf(3)
            ).hashCode()
        );
    }

    @Test
    void toStringOfFractionWithNuDe() {
        BigIntegerBigDecimalFraction complex =
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3));
        Assertions.assertEquals("2/3", complex.toString());
    }

    @Test
    void compareToOfFractionWithNuDe() {
        BigIntegerBigDecimalFraction complex =
            new BigIntegerBigDecimalFraction(BigInteger.TWO, BigInteger.valueOf(3));
        Assertions.assertEquals(
            0, complex.compareTo(new BigIntegerBigDecimalFraction(
                BigInteger.TWO, BigInteger.valueOf(3)
            ))
        );
        Assertions.assertEquals(
            -1, complex.compareTo(new BigIntegerBigDecimalFraction(
                BigInteger.valueOf(3), BigInteger.ONE
            ))
        );
        Assertions.assertEquals(
            1, complex.compareTo(new BigIntegerBigDecimalFraction(
                BigInteger.ONE, BigInteger.valueOf(3)
            ))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
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
