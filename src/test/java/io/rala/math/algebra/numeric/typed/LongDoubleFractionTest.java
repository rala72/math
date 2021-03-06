package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.arithmetic.core.LongArithmetic;
import io.rala.math.testUtils.assertion.NumericAssertions;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(IllegalArgumentException.class,
            () -> new LongDoubleFraction(1, 0)
        ); // assert exception message?
    }

    @Test
    void constructorWithFraction() {
        assertFraction(new LongDoubleFraction(new LongDoubleFraction(2L, 3L)), 2, 3);
    }

    @Test
    void createAndSetNumerator() {
        LongDoubleFraction complex = new LongDoubleFraction(1);
        assertFraction(complex);
        complex.setNumerator(2L);
        assertFraction(complex, 2, 1);
    }

    @Test
    void createAndSetNumeratorNull() {
        LongDoubleFraction complex = new LongDoubleFraction(1);
        assertFraction(complex);
        assertThrows(IllegalArgumentException.class,
            () -> complex.setNumerator(null)
        ); // assert exception message?
    }

    @Test
    void createAndSetDenominator() {
        LongDoubleFraction complex = new LongDoubleFraction(1);
        assertFraction(complex);
        complex.setDenominator(3L);
        assertFraction(complex, 1, 3);
    }

    @Test
    void createAndSetDenominatorZero() {
        LongDoubleFraction complex = new LongDoubleFraction(1);
        assertFraction(complex);
        assertThrows(IllegalArgumentException.class,
            () -> complex.setDenominator(0L)
        ); // assert exception message?
    }

    @Test
    void createAndSetDenominatorNull() {
        LongDoubleFraction complex = new LongDoubleFraction(1, 2);
        assertFraction(complex, 1, 2);
        complex.setDenominator(null);
        assertFraction(complex, 1, 1);
    }

    // endregion

    // region value

    @Test
    void valueOf1_2() {
        assertEquals(0.5, new LongDoubleFraction(1, 2).value());
    }

    @Test
    void intValueOf1_2() {
        assertEquals(0, new LongDoubleFraction(1, 2).intValue());
    }

    @Test
    void longValueOf1_2() {
        assertEquals(0, new LongDoubleFraction(1, 2).longValue());
    }

    @Test
    void floatValueOf1_2() {
        assertEquals(0.5, new LongDoubleFraction(1, 2).floatValue());
    }

    @Test
    void doubleValueOf1_2() {
        assertEquals(0.5, new LongDoubleFraction(1, 2).doubleValue());
    }

    // endregion

    // region negate, inverse and simplify

    @Test
    void negate() {
        assertFraction(new LongDoubleFraction(1).negate(), -1, 1);
    }

    @Test
    void inverse() {
        assertFraction(new LongDoubleFraction(1, 2).inverse(), 2, 1);
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
        assertEquals(
            new LongDoubleFraction(2, 1L),
            new LongDoubleFraction(1).add(1L, 1L)
        );
    }

    @Test
    void addWithXAndY() {
        assertEquals(
            new LongDoubleFraction(2, 1L),
            new LongDoubleFraction(1, 1).add(1L, 1L)
        );
    }

    @Test
    void addWithFraction() {
        assertEquals(new LongDoubleFraction(3, 2L),
            new LongDoubleFraction(1, 1)
                .add(new LongDoubleFraction(1, 2))
        );
    }

    @Test
    void subtractWithNumerator() {
        assertEquals(
            new LongDoubleFraction(0, 1L),
            new LongDoubleFraction(2).subtract(2L, 1L)
        );
    }

    @Test
    void subtractWithXAndY() {
        assertEquals(
            new LongDoubleFraction(1, 1L),
            new LongDoubleFraction(2, 1).subtract(1L, 1L)
        );
    }

    @Test
    void subtractWithFraction() {
        assertEquals(new LongDoubleFraction(3, 2L),
            new LongDoubleFraction(2, 1)
                .subtract(new LongDoubleFraction(1, 2))
        );
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyWithNumerator() {
        assertEquals(
            new LongDoubleFraction(2, 1),
            new LongDoubleFraction(1).multiply(2L)
        );
    }

    @Test
    void multiplyWithXAndY() {
        assertEquals(
            new LongDoubleFraction(3, 8),
            new LongDoubleFraction(1, 2).multiply(3L, 4L)
        );
    }

    @Test
    void multiplyWithFraction() {
        assertEquals(new LongDoubleFraction(4, 6),
            new LongDoubleFraction(1, 2)
                .multiply(new LongDoubleFraction(4, 3))
        );
    }

    @Test
    void divideWithNumerator() {
        assertEquals(
            new LongDoubleFraction(1, 2),
            new LongDoubleFraction(1).divide(2L)
        );
    }

    @Test
    void divideWithXAndY() {
        assertEquals(
            new LongDoubleFraction(4, 6),
            new LongDoubleFraction(1, 2).divide(3L, 4L)
        );
    }

    @Test
    void divideWithFraction() {
        assertEquals(new LongDoubleFraction(3, 8),
            new LongDoubleFraction(1, 2)
                .divide(new LongDoubleFraction(4, 3))
        );
    }

    // endregion

    // region pow and root

    @Test
    void pow2OfFraction1_2() {
        assertFraction(new LongDoubleFraction(1, 2).pow(2), 1, 4);
    }

    @Test
    void root2OfFraction1_4() {
        assertFraction(new LongDoubleFraction(1, 4).root(2), 1, 2);
    }

    // endregion

    // region map and copy

    @Test
    void mapOfFraction() {
        LongDoubleFraction fraction = new LongDoubleFraction(0, 1);
        Fraction<Integer, Integer> result = new Fraction<>(
            new IntegerArithmetic().toResultArithmetic(), 0, 1
        );
        assertEquals(result, fraction.map(
            new IntegerArithmetic().toResultArithmetic(), Number::intValue
        ));
    }

    @Test
    void mapValuesOfFractionWithArithmetic() {
        LongDoubleFraction fraction = new LongDoubleFraction(0, 1);
        Fraction<Integer, Double> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new DoubleArithmetic(), Integer::doubleValue
            ), 0, 1
        );
        assertEquals(result, fraction.mapValues(
            new IntegerArithmetic(), Long::intValue, Integer::doubleValue
        ));
    }

    @Test
    void mapValuesOfFractionWithResultArithmetic() {
        LongDoubleFraction fraction = new LongDoubleFraction(0, 1);
        Fraction<Integer, Double> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new DoubleArithmetic(), Integer::doubleValue
            ), 0, 1
        );
        assertEquals(result, fraction.mapValues(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new DoubleArithmetic(), Integer::doubleValue
            ), Number::intValue
        ));
    }

    @Test
    void mapValueOfFraction() {
        LongDoubleFraction fraction = new LongDoubleFraction(0, 1);
        Fraction<Long, Integer> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new LongArithmetic(), new IntegerArithmetic(), Long::intValue
            ), 0L, 1L
        );
        assertEquals(result, fraction.mapValue(
            new IntegerArithmetic(), Number::intValue
        ));
    }

    @Test
    void copyOfFractionWithNuDe() {
        LongDoubleFraction complex = new LongDoubleFraction(2, 3);
        assertEquals(complex, complex.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfFractionWithNuDe() {
        LongDoubleFraction complex = new LongDoubleFraction(2, 3);
        assertEquals(complex, new LongDoubleFraction(2, 3));
        assertNotEquals(complex, new LongDoubleFraction(3, 2));
    }

    @Test
    void hashCodeOfFractionWithNuDe() {
        assertEquals(1026, new LongDoubleFraction(2, 3).hashCode());
    }

    @Test
    void toStringOfFractionWithNuDe() {
        LongDoubleFraction complex = new LongDoubleFraction(2, 3);
        assertEquals("2/3", complex.toString());
    }

    @Test
    void compareToOfFractionWithNuDe() {
        LongDoubleFraction complex = new LongDoubleFraction(2, 3);
        assertEquals(0, complex.compareTo(new LongDoubleFraction(2, 3)));
        assertEquals(-1, complex.compareTo(new LongDoubleFraction(3, 1)));
        assertEquals(1, complex.compareTo(new LongDoubleFraction(1, 2)));
    }

    @Test
    void serializable() {
        assertSerializable(
            new LongDoubleFraction(2, 3),
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
