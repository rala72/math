package io.rala.math.algebra.numeric;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.algebra.TestComplex;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.NumericAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class ComplexTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertComplex(new TestComplex());
    }

    @Test
    void constructorWithDifferentReImParameter() {
        assertComplex(new TestComplex(2, 3), 2, 3);
    }

    @Test
    void constructorWithComplex() {
        assertComplex(new TestComplex(new TestComplex(2, 3)), 2, 3);
    }

    @Test
    void createAndSetRe() {
        TestComplex complex = new TestComplex();
        assertComplex(complex);
        complex.setRe(1);
        assertComplex(complex, 1, 0d);
    }

    @Test
    void createAndSetIm() {
        TestComplex complex = new TestComplex();
        assertComplex(complex);
        complex.setIm(2);
        assertComplex(complex, 0d, 2);
    }

    // endregion

    // region value

    @Test
    void intValueOfComplexWithX1_1Y2_2() {
        assertEquals(1,
            new TestComplex(1.1, 2.2).intValue()
        );
    }

    @Test
    void longValueOfComplexWithX1_1Y2_2() {
        assertEquals(1,
            new TestComplex(1.1, 2.2).longValue()
        );
    }

    @Test
    void floatValueOfComplexWithX1_1Y2_2() {
        assertEquals((float) 1.1d,
            new TestComplex(1.1, 2.2).floatValue()
        );
    }

    @Test
    void doubleValueOfComplexWithX1_1Y2_2() {
        assertEquals(1.1d,
            new TestComplex(1.1, 2.2).doubleValue()
        );
    }

    // endregion

    // region absoluteValue, argument and signum

    @Test
    void absoluteValueOfComplexWithoutParameter() {
        assertEquals(0d,
            new TestComplex().absoluteValue()
        );
    }

    @Test
    void absoluteValueOfComplexX1Y1() {
        assertEquals(Math.sqrt(2d),
            new TestComplex(1, 1).absoluteValue()
        );
    }

    @Test
    void absoluteValueOfComplexX1Y0() {
        assertEquals(1d,
            new TestComplex(1, 0).absoluteValue()
        );
    }

    @Test
    void argumentOfComplexWithoutParameter() {
        assertEquals(Double.NaN, new TestComplex().argument());
    }

    @Test
    void argumentOfComplexX1Y1() {
        assertEquals(Math.PI / 4,
            new TestComplex(1, 1).argument().doubleValue(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void argumentOfComplexX1Y0() {
        assertEquals(0d, new TestComplex(1, 0).argument());
    }

    @Test
    void signumOfComplexWithoutParameter() {
        assertEquals(new TestComplex(),
            new TestComplex().signum()
        );
    }

    @Test
    void signumOfComplexX1Y1() {
        double sqrt2half = 0.7071067811865475; // Math.sqrt(2d) / 2
        assertEquals(new TestComplex(sqrt2half, sqrt2half),
            new TestComplex(1, 1).signum()
        );
    }

    @Test
    void signumOfComplexX1Y0() {
        assertEquals(new TestComplex(1d, 0d),
            new TestComplex(1, 0).signum()
        );
    }

    @Test
    void complexSignumOfComplexWithoutParameter() {
        assertEquals(0,
            new TestComplex().complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX1Y1() {
        assertEquals(1,
            new TestComplex(1, 1).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexXMinus1Y0() {
        assertEquals(-1,
            new TestComplex(-1, 0).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX0Y1() {
        assertEquals(0,
            new TestComplex(0, 1).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX0YMinus1() {
        assertEquals(0,
            new TestComplex(0, -1).complexSignum()
        );
    }

    // endregion

    // region conjugation and reciprocal

    @Test
    void conjugationOfComplexWithRe1Im2() {
        assertEquals(
            new TestComplex(1, -2d),
            new TestComplex(1, 2).conjugation()
        );
    }

    @Test
    void reciprocalOfComplexWithoutParameter() {
        assertEquals(
            new TestComplex(Double.NaN, Double.NaN),
            new TestComplex().reciprocal()
        );
    }

    @Test
    void reciprocalOfComplexX1Y1() {
        assertEquals(new TestComplex(0.5, -0.5),
            new TestComplex(1, 1).reciprocal()
        );
    }

    @Test
    void reciprocalOfComplexX1Y0() {
        assertEquals(new TestComplex(1d, -0d),
            new TestComplex(1, 0).reciprocal()
        );
    }

    // endregion

    // region add and subtract

    @Test
    void addWithXY() {
        assertEquals(
            new TestComplex(2d, 2d),
            new TestComplex().add(2, 2)
        );
    }

    @Test
    void addWithXAndY() {
        assertEquals(
            new TestComplex(2d, 2d),
            new TestComplex(1, 1).add(1, 1)
        );
    }

    @Test
    void addWithComplex() {
        assertEquals(new TestComplex(2d, 2d),
            new TestComplex(1, 0).add(new TestComplex(1, 2))
        );
    }

    @Test
    void subtractWithXY() {
        assertEquals(
            new TestComplex(0d, 0d),
            new TestComplex(2, 2).subtract(2, 2)
        );
    }

    @Test
    void subtractWithXAndY() {
        assertEquals(
            new TestComplex(1d, 1d),
            new TestComplex(2, 2).subtract(1, 1)
        );
    }

    @Test
    void subtractWithComplex() {
        assertEquals(new TestComplex(1d, 0d),
            new TestComplex(2, 2).subtract(new TestComplex(1, 2))
        );
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyZeroComplexWith1() {
        assertEquals(
            new TestComplex(0d, 0d), new TestComplex().multiply(1)
        );
    }

    @Test
    void multiplyComplexWith0() {
        assertEquals(
            new TestComplex(0d, 0d),
            new TestComplex(1, 1).multiply(0)
        );
    }

    @Test
    void multiplyComplexWith1() {
        assertEquals(
            new TestComplex(1d, 1d),
            new TestComplex(1, 1).multiply(1)
        );
    }

    @Test
    void multiplyComplexWithMinus1() {
        assertEquals(
            new TestComplex(-2d, -1d),
            new TestComplex(2, 1).multiply(-1)
        );
    }

    @Test
    void multiplyComplexWithComplex() {
        assertEquals(
            new TestComplex(0d, 5d),
            new TestComplex(1, 2).multiply(new TestComplex(2, 1))
        );
    }

    @Test
    void divideZeroComplexWith1() {
        assertEquals(
            new TestComplex(0d, 0d),
            new TestComplex().divide(1)
        );
    }

    @Test
    void divideComplexWith0() {
        assertEquals(
            new TestComplex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY),
            new TestComplex(1, 1).divide(0)
        );
    }

    @Test
    void divideComplexWith1() {
        assertEquals(
            new TestComplex(1d, 1d),
            new TestComplex(1, 1).divide(1)
        );
    }

    @Test
    void divideComplexWithMinus1() {
        assertEquals(
            new TestComplex(-2d, -1d),
            new TestComplex(2, 1).divide(-1)
        );
    }

    @Test
    void divideComplexWithComplex() {
        assertEquals(
            new TestComplex(0.8, 0.6),
            new TestComplex(1, 2).divide(new TestComplex(2, 1))
        );
    }

    // endregion

    // region pow and root

    @Test
    void pow8OfComplexWithRe1Im1() {
        assertEquals(
            new TestComplex(16.000000000000007, 1.0291984957930479e-14),
            new TestComplex(1, 1).pow(8)
        );
    }

    @Test
    void pow5OfComplexWithRe3Im4() {
        assertEquals(
            new TestComplex(-236.99999999999898d, -3116d),
            new TestComplex(3, 4).pow(5)
        );
    }

    @Test
    void root3OfComplexWithRe1Im0() {
        assertEquals(
            List.of(
                new TestComplex(1d, 0d),
                new TestComplex(-0.4999999999999998, 0.8660254037844387),
                new TestComplex(-0.5000000000000004, -0.8660254037844384)
            ),
            new TestComplex(1, 0).root(3)
        );
    }

    @Test
    void root4OfComplexWithRe1Im0() {
        assertEquals(
            List.of(
                new TestComplex(1d, 0d),
                new TestComplex(6.123233995736766e-17, 1d),
                new TestComplex(-1d, 1.2246467991473532e-16),
                new TestComplex(-1.8369701987210297e-16, -1d)
            ),
            new TestComplex(1, 0).root(4)
        );
    }

    @Test
    void root2OfComplexWithReMinus1ImSqrt3() {
        assertEquals(
            List.of(
                new TestComplex(0.7071067811865474, 1.224744871391589),
                new TestComplex(-0.7071067811865469, -1.2247448713915892)
            ),
            new TestComplex(-1, Math.sqrt(3)).root(2)
        );
    }

    // endregion

    // region inverse

    @Test
    void inverseReOfComplexWithRe1Im2() {
        assertEquals(
            new TestComplex(-1d, 2),
            new TestComplex(1, 2).inverseRe()
        );
    }

    @Test
    void inverseImOfComplexWithRe1Im2() {
        assertEquals(
            new TestComplex(1, -2d),
            new TestComplex(1, 2).inverseIm()
        );
    }

    @Test
    void inverseReImOfComplexWithRe1Im2() {
        assertEquals(
            new TestComplex(-1d, -2d),
            new TestComplex(1, 2).inverse()
        );
    }

    // endregion

    // region static of and asVector

    @Test
    void ofAb3AndAr50() {
        assertComplex(
            Complex.of(new TestAbstractArithmetic(), 3, 50),
            2.89489808547634,
            -0.7871245611117863
        );
    }

    @Test
    void ofWithSelfValidation() {
        Complex<Number> complex = Complex.of(new TestAbstractArithmetic(),
            1, 2
        );
        assertEquals(1d, complex.absoluteValue());
        assertEquals(2d, complex.argument());
    }

    @Test
    void asVectorOfComplexWithRe1Im2() {
        assertEquals(new Vector<>(new TestAbstractArithmetic(), 1, 2),
            new TestComplex(1, 2).asVector()
        );
    }

    @Test
    void asVectorAndAsComplexWithoutChangeAreSuperfluous() {
        // better word than of superfluous?
        Complex<Number> complex = new TestComplex(1d, 2d);
        assertEquals(complex, complex.asVector().asComplex());
    }

    // endregion

    // region map, isValid and copy

    @Test
    void mapOfComplexWithR0_5Im1_5() {
        TestComplex complex = new TestComplex(0.5, 1.5);
        Complex<Integer> result =
            new Complex<>(new IntegerArithmetic(), 0, 1);
        assertEquals(result,
            complex.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZero() {
        assertTrue(new TestComplex().isValid());
    }

    @Test
    void isValidWithInfValues() {
        assertFalse(
            new TestComplex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void copyOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        assertEquals(complex, complex.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        assertEquals(complex, new TestComplex(2, 3));
        assertNotEquals(complex, new TestComplex(3, 2));
    }

    @Test
    void hashCodeOfComplexWithReIm() {
        assertEquals(1026, new TestComplex(2, 3).hashCode());
    }

    @Test
    void toStringOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        assertEquals("2+3*i", complex.toString());
    }

    @Test
    void compareToOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        assertEquals(0, complex.compareTo(new TestComplex(2, 3)));
        assertEquals(-1, complex.compareTo(new TestComplex(3, 1)));
        assertEquals(1, complex.compareTo(new TestComplex(2, 1)));
    }

    @Test
    void serializable() {
        assertSerializable(new TestComplex(), TestComplex.class);
    }

    // endregion


    // region assert

    private static void assertComplex(TestComplex complex) {
        assertComplex(complex, 0d, 0d);
    }

    private static void assertComplex(Complex<Number> complex, Number re, Number im) {
        NumericAssertions.assertComplex(complex, re, im);
    }

    // endregion
}
