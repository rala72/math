package io.rala.math.algebra.numeric;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.testUtils.algebra.TestFraction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FractionTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertFraction(new TestFraction(1));
    }

    @Test
    void constructorWithDifferentNuDeParameter() {
        assertFraction(new TestFraction(2d, 3d), 2, 3);
    }

    @Test
    void constructorWithNullNuParameter() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new TestFraction((Number) null)
        ); // assert exception message?
    }

    @Test
    void constructorWithZeroDeParameter() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new TestFraction(1, 0)
        ); // assert exception message?
    }

    @Test
    void constructorWithFraction() {
        assertFraction(new TestFraction(new TestFraction(2d, 3d)), 2, 3);
    }

    @Test
    void createAndSetNumerator() {
        TestFraction complex = new TestFraction(1);
        assertFraction(complex);
        complex.setNumerator(2);
        assertFraction(complex, 2, 1);
    }

    @Test
    void createAndSetNumeratorNull() {
        TestFraction complex = new TestFraction(1);
        assertFraction(complex);
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> complex.setNumerator(null)
        ); // assert exception message?
    }

    @Test
    void createAndSetDenominator() {
        TestFraction complex = new TestFraction(1);
        assertFraction(complex);
        complex.setDenominator(3);
        assertFraction(complex, 1, 3);
    }

    @Test
    void createAndSetDenominatorZero() {
        TestFraction complex = new TestFraction(1);
        assertFraction(complex);
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> complex.setDenominator(0)
        ); // assert exception message?
    }

    @Test
    void createAndSetDenominatorNull() {
        TestFraction complex = new TestFraction(1, 2);
        assertFraction(complex, 1, 2);
        complex.setDenominator(null);
        assertFraction(complex, 1, 1);
    }

    // endregion

    // region value

    @Test
    void valueOf1_2() {
        Assertions.assertEquals(0.5, new TestFraction(1, 2).value());
    }

    @Test
    void intValueOf1_2() {
        Assertions.assertEquals(0, new TestFraction(1, 2).intValue());
    }

    @Test
    void longValueOf1_2() {
        Assertions.assertEquals(0, new TestFraction(1, 2).longValue());
    }

    @Test
    void floatValueOf1_2() {
        Assertions.assertEquals(0.5, new TestFraction(1, 2).floatValue());
    }

    @Test
    void doubleValueOf1_2() {
        Assertions.assertEquals(0.5, new TestFraction(1, 2).doubleValue());
    }

    // endregion

    // region negate and simplify

    @Test
    void negate() {
        assertFraction(new TestFraction(1).negate(), -1, 1);
    }

    @Test
    void simplify() {
        assertFraction(new TestFraction(2, 4).simplify(), 1, 2);
    }

    @Test
    void simplifyDoubleFraction() {
        Fraction<Double, Double> fraction = new Fraction<>(
            new DoubleArithmetic().toResultArithmetic(), 2d, 4d
        );
        Assertions.assertEquals(fraction, fraction.simplify());
    }

    // endregion

    // region add

    @Test
    void addWithNumerator() {
        Assertions.assertEquals(
            new TestFraction(2, 1d),
            new TestFraction(1).add(1, 1)
        );
    }

    @Test
    void addWithXAndY() {
        Assertions.assertEquals(
            new TestFraction(2, 1d),
            new TestFraction(1, 1).add(1, 1)
        );
    }

    @Test
    void addWithFraction() {
        Assertions.assertEquals(new TestFraction(4, 2d),
            new TestFraction(1, 1).add(new TestFraction(1, 2))
        );
    }

    @Test
    void addWithDoubleFraction() {
        AbstractResultArithmetic<Double, Double> arithmetic =
            new DoubleArithmetic().toResultArithmetic();
        Assertions.assertEquals(new Fraction<>(arithmetic, 4d, 2d),
            new Fraction<>(arithmetic, 1d, 1d)
                .add(new Fraction<>(arithmetic, 1d, 2d))
        );
    }

    // endregion

    // region isValid and copy

    @Test
    void copyOfFractionWithNuDe() {
        TestFraction complex = new TestFraction(2, 3);
        Assertions.assertEquals(complex, complex.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfFractionWithNuDe() {
        TestFraction complex = new TestFraction(2, 3);
        Assertions.assertEquals(
            complex,
            new TestFraction(2, 3)
        );
        Assertions.assertNotEquals(
            complex,
            new TestFraction(3, 2)
        );
    }

    @Test
    void hashCodeOfFractionWithNuDe() {
        Assertions.assertEquals(
            1026,
            new TestFraction(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfFractionWithNuDe() {
        TestFraction complex = new TestFraction(2, 3);
        Assertions.assertEquals("2/3", complex.toString());
    }

    @Test
    void compareToOfFractionWithNuDe() {
        TestFraction complex = new TestFraction(2, 3);
        Assertions.assertEquals(
            0, complex.compareTo(new TestFraction(2, 3))
        );
        Assertions.assertEquals(
            -1, complex.compareTo(new TestFraction(3, 1))
        );
        Assertions.assertEquals(
            1, complex.compareTo(new TestFraction(1, 2))
        );
    }

    // endregion

    // region protected

    @Test
    void simplifySignum() {
        assertFraction(new TestFraction(1, 1), 1, 1);
        assertFraction(new TestFraction(1, -1), -1, 1);
        assertFraction(new TestFraction(-1, 1), -1, 1);
        assertFraction(new TestFraction(-1, -1), 1, 1);
    }

    // endregion


    // region assert

    private static void assertFraction(TestFraction fraction) {
        assertFraction(fraction, 1, 1);
    }

    private static void assertFraction(Fraction<Number, Number> fraction, double no, double de) {
        Assertions.assertEquals(no, fraction.getNumerator().doubleValue(), "no is invalid");
        Assertions.assertEquals(de, fraction.getDenominator().doubleValue(), "de is invalid");
    }

    // endregion
}
