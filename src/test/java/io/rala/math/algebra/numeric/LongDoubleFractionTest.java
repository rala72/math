package io.rala.math.algebra.numeric;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LongDoubleFractionTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertFraction(new LongDoubleFraction(1));
    }

    @Test
    void constructorWithDifferentNuDeParameter() {
        assertFraction(new LongDoubleFraction(2L, 3L), 2, 3);
    }

    @Test
    void constructorWithZeroDeParameter() {
        Assertions.assertThrows(IllegalArgumentException.class,
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
        Assertions.assertThrows(IllegalArgumentException.class,
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
        Assertions.assertThrows(IllegalArgumentException.class,
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
        Assertions.assertEquals(0.5, new LongDoubleFraction(1, 2).value());
    }

    @Test
    void intValueOf1_2() {
        Assertions.assertEquals(0, new LongDoubleFraction(1, 2).intValue());
    }

    @Test
    void longValueOf1_2() {
        Assertions.assertEquals(0, new LongDoubleFraction(1, 2).longValue());
    }

    @Test
    void floatValueOf1_2() {
        Assertions.assertEquals(0.5, new LongDoubleFraction(1, 2).floatValue());
    }

    @Test
    void doubleValueOf1_2() {
        Assertions.assertEquals(0.5, new LongDoubleFraction(1, 2).doubleValue());
    }

    // endregion

    // region simplify

    @Test
    void simplify() {
        assertFraction(
            new LongDoubleFraction(2L, 4L).simplify(),
            1L, 2L
        );
    }

    // endregion

    // region isValid and copy

    @Test
    void copyOfFractionWithNuDe() {
        LongDoubleFraction complex = new LongDoubleFraction(2, 3);
        Assertions.assertEquals(complex, complex.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfFractionWithNuDe() {
        LongDoubleFraction complex = new LongDoubleFraction(2, 3);
        Assertions.assertEquals(
            complex,
            new LongDoubleFraction(2, 3)
        );
        Assertions.assertNotEquals(
            complex,
            new LongDoubleFraction(3, 2)
        );
    }

    @Test
    void hashCodeOfFractionWithNuDe() {
        Assertions.assertEquals(
            1026,
            new LongDoubleFraction(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfFractionWithNuDe() {
        LongDoubleFraction complex = new LongDoubleFraction(2, 3);
        Assertions.assertEquals("2/3", complex.toString());
    }

    @Test
    void compareToOfFractionWithNuDe() {
        LongDoubleFraction complex = new LongDoubleFraction(2, 3);
        Assertions.assertEquals(
            0, complex.compareTo(new LongDoubleFraction(2, 3))
        );
        Assertions.assertEquals(
            -1, complex.compareTo(new LongDoubleFraction(3, 1))
        );
        Assertions.assertEquals(
            1, complex.compareTo(new LongDoubleFraction(1, 2))
        );
    }

    // endregion

    // region protected

    @Test
    void simplifySignum() {
        assertFraction(new LongDoubleFraction(1L, 1L), 1, 1);
        assertFraction(new LongDoubleFraction(1L, -1L), -1, 1);
        assertFraction(new LongDoubleFraction(-1L, 1L), -1, 1);
        assertFraction(new LongDoubleFraction(-1L, -1L), 1, 1);
    }

    // endregion


    // region assert

    private static void assertFraction(LongDoubleFraction fraction) {
        assertFraction(fraction, 1, 1);
    }

    private static void assertFraction(Fraction<Long, Double> fraction, long no, long de) {
        Assertions.assertEquals(no, fraction.getNumerator().doubleValue(), "no is invalid");
        Assertions.assertEquals(de, fraction.getDenominator().doubleValue(), "de is invalid");
    }

    // endregion
}
