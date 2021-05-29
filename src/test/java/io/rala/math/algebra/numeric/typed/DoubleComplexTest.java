package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.assertion.NumericAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class DoubleComplexTest {
    private static final double DELTA = 0.00001;

    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertComplex(new DoubleComplex());
    }

    @Test
    void constructorWithReParameter() {
        assertComplex(new DoubleComplex(2d), 2, 0);
    }

    @Test
    void constructorWithDifferentReImParameter() {
        assertComplex(new DoubleComplex(2d, 3d), 2, 3);
    }

    @Test
    void constructorWithComplex() {
        assertComplex(new DoubleComplex(new DoubleComplex(2d, 3d)), 2, 3);
    }

    @Test
    void createAndSetRe() {
        Complex<Double> complex = new DoubleComplex();
        assertComplex(complex);
        complex.setRe(1d);
        assertComplex(complex, 1, 0);
    }

    @Test
    void createAndSetIm() {
        Complex<Double> complex = new DoubleComplex();
        assertComplex(complex);
        complex.setIm(2d);
        assertComplex(complex, 0, 2);
    }

    // endregion

    // region value

    @Test
    void intValueOfComplexWithX1_1Y2_2() {
        assertEquals(1, new DoubleComplex(1.1, 2.2).intValue());
    }

    @Test
    void longValueOfComplexWithX1_1Y2_2() {
        assertEquals(1, new DoubleComplex(1.1, 2.2).longValue());
    }

    @Test
    void floatValueOfComplexWithX1_1Y2_2() {
        assertEquals((float) 1.1,
            new DoubleComplex(1.1, 2.2).floatValue()
        );
    }

    @Test
    void doubleValueOfComplexWithX1_1Y2_2() {
        assertEquals(1.1,
            new DoubleComplex(1.1, 2.2).doubleValue()
        );
    }

    // endregion

    // region absoluteValue, argument, signum, conjugation and reciprocal

    @Test
    void absoluteValueOfComplexWithoutParameter() {
        assertEquals(0, new DoubleComplex().absoluteValue());
    }

    @Test
    void absoluteValueOfComplexX1Y1() {
        assertEquals(Math.sqrt(2d),
            new DoubleComplex(1d, 1d).absoluteValue()
        );
    }

    @Test
    void absoluteValueOfComplexX1Y0() {
        assertEquals(1, new DoubleComplex(1d, 0d).absoluteValue());
    }

    @Test
    void argumentOfComplexWithoutParameter() {
        assertEquals(Double.NaN, new DoubleComplex().argument());
    }

    @Test
    void argumentOfComplexX1Y1() {
        assertEquals(Math.PI / 4,
            new DoubleComplex(1d, 1d).argument(),
            DELTA
        );
    }

    @Test
    void argumentOfComplexX1Y0() {
        assertEquals(0, new DoubleComplex(1d, 0d).argument());
    }

    @Test
    void signumOfComplexWithoutParameter() {
        assertEquals(new DoubleComplex(),
            new DoubleComplex().signum()
        );
    }

    @Test
    void signumOfComplexX1Y1() {
        double sqrt2half = 0.7071067811865475; // Math.sqrt(2d) / 2
        assertEquals(new DoubleComplex(sqrt2half, sqrt2half),
            new DoubleComplex(1d, 1d).signum()
        );
    }

    @Test
    void signumOfComplexX1Y0() {
        assertEquals(new DoubleComplex(1d, 0d),
            new DoubleComplex(1d, 0d).signum()
        );
    }

    @Test
    void complexSignumOfComplexWithoutParameter() {
        assertEquals(0,
            new DoubleComplex().complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX1Y1() {
        assertEquals(1,
            new DoubleComplex(1d, 1d).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexXMinus1Y0() {
        assertEquals(-1,
            new DoubleComplex(-1d, 0d).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX0Y1() {
        assertEquals(1,
            new DoubleComplex(0d, 1d).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX0YMinus1() {
        assertEquals(-1,
            new DoubleComplex(0d, -1d).complexSignum()
        );
    }

    @Test
    void conjugationOfComplexWithRe1Im2() {
        assertEquals(
            new DoubleComplex(1d, -2d),
            new DoubleComplex(1d, 2d).conjugation()
        );
    }

    @Test
    void reciprocalOfComplexWithoutParameter() {
        assertEquals(
            new DoubleComplex(Double.NaN, Double.NaN),
            new DoubleComplex().reciprocal()
        );
    }

    @Test
    void reciprocalOfComplexX1Y1() {
        assertEquals(new DoubleComplex(0.5, -0.5),
            new DoubleComplex(1d, 1d).reciprocal()
        );
    }

    @Test
    void reciprocalOfComplexX1Y0() {
        assertEquals(new DoubleComplex(1d, -0d),
            new DoubleComplex(1d, 0d).reciprocal()
        );
    }

    // endregion

    // region add and subtract

    @Test
    void addWithXY() {
        assertEquals(
            new DoubleComplex(2d, 2d),
            new DoubleComplex().add(2d, 2d)
        );
    }

    @Test
    void addWithXAndY() {
        assertEquals(
            new DoubleComplex(2d, 2d),
            new DoubleComplex(1d, 1d).add(1d, 1d)
        );
    }

    @Test
    void addWithDoubleComplex() {
        assertEquals(new DoubleComplex(2d, 2d),
            new DoubleComplex(1d, 0d).add(new DoubleComplex(1d, 2d))
        );
    }

    @Test
    void subtractWithXY() {
        assertEquals(
            new DoubleComplex(),
            new DoubleComplex(2d, 2d).subtract(2d, 2d)
        );
    }

    @Test
    void subtractWithXAndY() {
        assertEquals(
            new DoubleComplex(1d, 1d),
            new DoubleComplex(2d, 2d).subtract(1d, 1d)
        );
    }

    @Test
    void subtractWithDoubleComplex() {
        assertEquals(
            new DoubleComplex(1d, 0d),
            new DoubleComplex(2d, 2d).subtract(new DoubleComplex(1d, 2d))
        );
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyZeroComplexWith1() {
        assertEquals(
            new DoubleComplex(),
            new DoubleComplex().multiply(1d)
        );
    }

    @Test
    void multiplyComplexWith0() {
        assertEquals(
            new DoubleComplex(),
            new DoubleComplex(1d, 1d).multiply(0d)
        );
    }

    @Test
    void multiplyComplexWith1() {
        assertEquals(
            new DoubleComplex(1d, 1d),
            new DoubleComplex(1d, 1d).multiply(1d)
        );
    }

    @Test
    void multiplyComplexWithMinus1() {
        assertEquals(
            new DoubleComplex(-2d, -1d),
            new DoubleComplex(2d, 1d).multiply(-1d)
        );
    }

    @Test
    void multiplyComplexWithDoubleComplex() {
        assertEquals(
            new DoubleComplex(0d, 5d),
            new DoubleComplex(1d, 2d).multiply(new DoubleComplex(2d, 1d))
        );
    }

    @Test
    void divideZeroComplexWith1() {
        assertEquals(
            new DoubleComplex(),
            new DoubleComplex().divide(1d)
        );
    }

    @Test
    void divideComplexWith0() {
        assertEquals(
            new DoubleComplex(
                Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY
            ),
            new DoubleComplex(1d, 1d).divide(0d)
        );
    }

    @Test
    void divideComplexWith1() {
        assertEquals(
            new DoubleComplex(1d, 1d),
            new DoubleComplex(1d, 1d).divide(1d)
        );
    }

    @Test
    void divideComplexWithMinus1() {
        assertEquals(
            new DoubleComplex(-2d, -1d),
            new DoubleComplex(2d, 1d).divide(-1d)
        );
    }

    @Test
    void divideComplexWithDoubleComplex() {
        assertEquals(
            new DoubleComplex(0.8, 0.6),
            new DoubleComplex(1d, 2d).divide(new DoubleComplex(2d, 1d))
        );
    }

    // endregion

    // region pow and root

    @Test
    void pow8OfComplexWithRe1Im1() {
        assertEquals(
            new DoubleComplex(16.000000000000007, 1.0291984957930479E-14),
            new DoubleComplex(1d, 1d).pow(8)
        );
    }

    @Test
    void pow5OfComplexWithRe3Im4() {
        assertEquals(
            new DoubleComplex(-236.99999999999898, -3116d),
            new DoubleComplex(3d, 4d).pow(5)
        );
    }

    @Test
    void root3OfComplexWithRe1Im0() {
        assertEquals(
            List.of(
                new DoubleComplex(1d, 0d),
                new DoubleComplex(-0.4999999999999998, 0.8660254037844387),
                new DoubleComplex(-0.5000000000000004, -0.8660254037844384)
            ),
            new DoubleComplex(1d, 0d).root(3)
        );
    }

    @Test
    void root4OfComplexWithRe1Im0() {
        assertEquals(
            List.of(
                new DoubleComplex(1d, 0d),
                new DoubleComplex(6.123233995736766E-17, 1d),
                new DoubleComplex(-1d, 1.2246467991473532E-16),
                new DoubleComplex(-1.8369701987210297E-16, -1d)
            ),
            new DoubleComplex(1d, 0d).root(4)
        );
    }

    @Test
    void root2OfComplexWithReMinus1ImSqrt3() {
        assertEquals(
            List.of(
                new DoubleComplex(0.7071067811865474, 1.224744871391589),
                new DoubleComplex(-0.7071067811865469, -1.2247448713915892)
            ),
            new DoubleComplex(-1d, Math.sqrt(3)).root(2)
        );
    }

    // endregion

    // region inverse

    @Test
    void inverseReOfComplexWithRe1Im2() {
        assertEquals(
            new DoubleComplex(-1d, 2d),
            new DoubleComplex(1d, 2d).inverseRe()
        );
    }

    @Test
    void inverseImOfComplexWithRe1Im2() {
        assertEquals(
            new DoubleComplex(1d, -2d),
            new DoubleComplex(1d, 2d).inverseIm()
        );
    }

    @Test
    void inverseReImOfComplexWithRe1Im2() {
        assertEquals(
            new DoubleComplex(-1d, -2d),
            new DoubleComplex(1d, 2d).inverse()
        );
    }

    // endregion

    // region static of and asVector

    @Test
    void ofAb3AndAr50() {
        assertComplex(
            DoubleComplex.of(3d, 50d),
            2.89489808547634,
            -0.7871245611117863
        );
    }

    @Test
    void ofWithSelfValidation() {
        Complex<Double> complex = DoubleComplex.of(1d, 2d);
        assertEquals(1, complex.absoluteValue());
        assertEquals(2, complex.argument());
    }

    @Test
    void asVectorOfComplexWithRe1Im2() {
        assertEquals(
            new Vector<>(new DoubleArithmetic(), 1d, 2d),
            new DoubleComplex(1d, 2d).asVector()
        );
    }

    // endregion

    // region map, isValid and copy

    @Test
    void mapOfComplexWithR0_5Im1_5() {
        DoubleComplex complex = new DoubleComplex(0.5, 1.5);
        Complex<Integer> result =
            new Complex<>(new IntegerArithmetic(), 0, 1);
        assertEquals(result,
            complex.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZero() {
        assertTrue(new DoubleComplex().isValid());
    }

    @Test
    void isValidWithInfValues() {
        assertFalse(
            new DoubleComplex(
                Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY
            ).isValid()
        );
    }

    @Test
    void copyOfComplexWithReIm() {
        Complex<Double> complex = new DoubleComplex(2d, 3d);
        assertEquals(complex, complex.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfComplexWithReIm() {
        Complex<Double> complex = new DoubleComplex(2d, 3d);
        assertEquals(complex, new DoubleComplex(2d, 3d));
        assertNotEquals(complex, new DoubleComplex(3d, 2d));
    }

    @Test
    void hashCodeOfComplexWithReIm() {
        assertEquals(525249, new DoubleComplex(2d, 3d).hashCode());
    }

    @Test
    void toStringOfComplexWithReIm() {
        Complex<Double> complex = new DoubleComplex(2d, 3d);
        assertEquals("2.0+3.0*i", complex.toString());
    }

    @Test
    void compareToOfComplexWithReIm() {
        Complex<Double> complex = new DoubleComplex(2d, 3d);
        assertEquals(0, complex.compareTo(new DoubleComplex(2d, 3d)));
        assertEquals(-1, complex.compareTo(new DoubleComplex(3d, 1d)));
        assertEquals(1, complex.compareTo(new DoubleComplex(2d, 1d)));
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleComplex(), DoubleComplex.class);
    }

    // endregion


    // region assert

    private static void assertComplex(Complex<Double> complex) {
        assertComplex(complex, 0, 0);
    }

    private static void assertComplex(Complex<Double> complex, double re, double im) {
        NumericAssertions.assertComplex(complex, re, im);
    }

    // endregion
}
