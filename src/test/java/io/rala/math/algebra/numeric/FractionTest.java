package io.rala.math.algebra.numeric;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.algebra.TestFraction;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FractionTest {
    // region constructors, getter and setter

    @Test
    void constructorWithNu() {
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

    // region negate, inverse and simplify

    @Test
    void negate() {
        assertFraction(new TestFraction(1).negate(), -1, 1);
    }

    @Test
    void inverse() {
        assertFraction(new TestFraction(1, 2).inverse(), 2, 1);
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

    // region add and subtract

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
        Assertions.assertEquals(new TestFraction(3, 2d),
            new TestFraction(1, 1).add(new TestFraction(1, 2))
        );
    }

    @Test
    void addWithDoubleFraction() {
        AbstractResultArithmetic<Double, Double> arithmetic =
            new DoubleArithmetic().toResultArithmetic();
        Assertions.assertEquals(new Fraction<>(arithmetic, 3d, 2d),
            new Fraction<>(arithmetic, 1d, 1d)
                .add(new Fraction<>(arithmetic, 1d, 2d))
        );
    }

    @Test
    void subtractWithNumerator() {
        Assertions.assertEquals(
            new TestFraction(0, 1d),
            new TestFraction(2).subtract(2, 1)
        );
    }

    @Test
    void subtractWithXAndY() {
        Assertions.assertEquals(
            new TestFraction(1, 1d),
            new TestFraction(2, 1).subtract(1, 1)
        );
    }

    @Test
    void subtractWithFraction() {
        Assertions.assertEquals(new TestFraction(3, 2d),
            new TestFraction(2, 1).subtract(new TestFraction(1, 2))
        );
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyWithNumerator() {
        Assertions.assertEquals(
            new TestFraction(2, 1),
            new TestFraction(1).multiply(2)
        );
    }

    @Test
    void multiplyWithXAndY() {
        Assertions.assertEquals(
            new TestFraction(3, 8),
            new TestFraction(1, 2).multiply(3, 4)
        );
    }

    @Test
    void multiplyWithFraction() {
        Assertions.assertEquals(new TestFraction(4, 6),
            new TestFraction(1, 2)
                .multiply(new TestFraction(4, 3))
        );
    }

    @Test
    void divideWithNumerator() {
        Assertions.assertEquals(
            new TestFraction(1, 2),
            new TestFraction(1).divide(2)
        );
    }

    @Test
    void divideWithXAndY() {
        Assertions.assertEquals(
            new TestFraction(4, 6),
            new TestFraction(1, 2).divide(3, 4)
        );
    }

    @Test
    void divideWithFraction() {
        Assertions.assertEquals(new TestFraction(3, 8),
            new TestFraction(1, 2)
                .divide(new TestFraction(4, 3))
        );
    }

    // endregion

    // region pow and root

    @Test
    void pow2OfFraction1_2() {
        assertFraction(new TestFraction(1, 2).pow(2), 1, 4);
    }

    @Test
    void root2OfFraction1_4() {
        assertFraction(new TestFraction(1, 4).root(2), 1, 2);
    }

    // endregion

    // region map and copy

    @Test
    void mapOfFraction() {
        TestFraction fraction = new TestFraction(0.5, 1.5);
        Fraction<Integer, Integer> result = new Fraction<>(
            new IntegerArithmetic().toResultArithmetic(), 0, 1
        );
        Assertions.assertEquals(result, fraction.map(
            new IntegerArithmetic().toResultArithmetic(), Number::intValue
        ));
    }

    @Test
    void mapValuesOfFractionWithArithmetic() {
        TestFraction fraction = new TestFraction(0.5, 1.5);
        Fraction<Integer, Number> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new TestAbstractArithmetic(), Number::intValue
            ), 0, 1
        );
        Assertions.assertEquals(result, fraction.mapValues(
            new IntegerArithmetic(), Number::intValue, Integer::intValue
        ));
    }

    @Test
    void mapValuesOfFractionWithResultArithmetic() {
        TestFraction fraction = new TestFraction(0.5, 1.5);
        Fraction<Integer, Number> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new TestAbstractArithmetic(), Number::intValue
            ), 0, 1
        );
        Assertions.assertEquals(result, fraction.mapValues(
            AbstractResultArithmetic.of(
                new IntegerArithmetic(), new TestAbstractArithmetic(), Number::intValue
            ), Number::intValue
        ));
    }

    @Test
    void mapValueOfFraction() {
        TestFraction fraction = new TestFraction(0.5, 1.5);
        Fraction<Number, Integer> result = new Fraction<>(
            AbstractResultArithmetic.of(
                new TestAbstractArithmetic(), new IntegerArithmetic(), Number::intValue
            ), 0.5, 1.5
        );
        Assertions.assertEquals(result, fraction.mapValue(
            new IntegerArithmetic(), Number::intValue
        ));
    }

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
