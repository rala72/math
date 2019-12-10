package io.rala.math.algebra;

import io.rala.math.geometry.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DoubleComplexTest {
    private static final double DELTA = 0.00001;

    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertComplex(new DoubleComplex());
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
        Assertions.assertEquals(1, new DoubleComplex(1.1, 2.2).intValue());
    }

    @Test
    void longValueOfComplexWithX1_1Y2_2() {
        Assertions.assertEquals(1, new DoubleComplex(1.1, 2.2).longValue());
    }

    @Test
    void floatValueOfComplexWithX1_1Y2_2() {
        Assertions.assertEquals((float) 1.1d,
            new DoubleComplex(1.1, 2.2).floatValue()
        );
    }

    @Test
    void doubleValueOfComplexWithX1_1Y2_2() {
        Assertions.assertEquals(1.1d,
            new DoubleComplex(1.1, 2.2).doubleValue()
        );
    }

    // endregion

    // region absoluteValue, argument, signum, conjugation and reciprocal

    @Test
    void absoluteValueOfComplexWithoutParameter() {
        Assertions.assertEquals(0, new DoubleComplex().absoluteValue());
    }

    @Test
    void absoluteValueOfComplexX1Y1() {
        Assertions.assertEquals(Math.sqrt(2d),
            new DoubleComplex(1d, 1d).absoluteValue()
        );
    }

    @Test
    void absoluteValueOfComplexX1Y0() {
        Assertions.assertEquals(1, new DoubleComplex(1d, 0d).absoluteValue());
    }

    @Test
    void argumentOfComplexWithoutParameter() {
        Assertions.assertEquals(Double.NaN, new DoubleComplex().argument());
    }

    @Test
    void argumentOfComplexX1Y1() {
        Assertions.assertEquals(Math.PI / 4,
            new DoubleComplex(1d, 1d).argument(),
            DELTA
        );
    }

    @Test
    void argumentOfComplexX1Y0() {
        Assertions.assertEquals(0, new DoubleComplex(1d, 0d).argument());
    }

    @Test
    void signumOfComplexWithoutParameter() {
        Assertions.assertEquals(new DoubleComplex(),
            new DoubleComplex().signum()
        );
    }

    @Test
    void signumOfComplexX1Y1() {
        double sqrt2half = 0.7071067811865475; // Math.sqrt(2d) / 2
        Assertions.assertEquals(new DoubleComplex(sqrt2half, sqrt2half),
            new DoubleComplex(1d, 1d).signum()
        );
    }

    @Test
    void signumOfComplexX1Y0() {
        Assertions.assertEquals(new DoubleComplex(1d, 0d),
            new DoubleComplex(1d, 0d).signum()
        );
    }

    @Test
    void complexSignumOfComplexWithoutParameter() {
        Assertions.assertEquals(0,
            new DoubleComplex().complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX1Y1() {
        Assertions.assertEquals(1,
            new DoubleComplex(1d, 1d).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexXMinus1Y0() {
        Assertions.assertEquals(-1,
            new DoubleComplex(-1d, 0d).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX0Y1() {
        Assertions.assertEquals(1,
            new DoubleComplex(0d, 1d).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX0YMinus1() {
        Assertions.assertEquals(-1,
            new DoubleComplex(0d, -1d).complexSignum()
        );
    }

    @Test
    void conjugationOfComplexWithRe1Im2() {
        Assertions.assertEquals(
            new DoubleComplex(1d, -2d),
            new DoubleComplex(1d, 2d).conjugation()
        );
    }

    @Test
    void reciprocalOfComplexWithoutParameter() {
        Assertions.assertEquals(
            new DoubleComplex(Double.NaN, Double.NaN),
            new DoubleComplex().reciprocal()
        );
    }

    @Test
    void reciprocalOfComplexX1Y1() {
        Assertions.assertEquals(new DoubleComplex(0.5, -0.5),
            new DoubleComplex(1d, 1d).reciprocal()
        );
    }

    @Test
    void reciprocalOfComplexX1Y0() {
        Assertions.assertEquals(new DoubleComplex(1d, -0d),
            new DoubleComplex(1d, 0d).reciprocal()
        );
    }

    // endregion

    // region add and subtract

    @Test
    void addWithXY() {
        Assertions.assertEquals(
            new DoubleComplex(2d, 2d),
            new DoubleComplex().add(2d, 2d)
        );
    }

    @Test
    void addWithXAndY() {
        Assertions.assertEquals(
            new DoubleComplex(2d, 2d),
            new DoubleComplex(1d, 1d).add(1d, 1d)
        );
    }

    @Test
    void addWithDoubleComplex() {
        Assertions.assertEquals(new DoubleComplex(2d, 2d),
            new DoubleComplex(1d, 0d).add(new DoubleComplex(1d, 2d))
        );
    }

    @Test
    void subtractWithXY() {
        Assertions.assertEquals(
            new DoubleComplex(),
            new DoubleComplex(2d, 2d).subtract(2d, 2d)
        );
    }

    @Test
    void subtractWithXAndY() {
        Assertions.assertEquals(
            new DoubleComplex(1d, 1d),
            new DoubleComplex(2d, 2d).subtract(1d, 1d)
        );
    }

    @Test
    void subtractWithDoubleComplex() {
        Assertions.assertEquals(
            new DoubleComplex(1d, 0d),
            new DoubleComplex(2d, 2d).subtract(new DoubleComplex(1d, 2d))
        );
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyZeroComplexWith1() {
        Assertions.assertEquals(
            new DoubleComplex(),
            new DoubleComplex().multiply(1d)
        );
    }

    @Test
    void multiplyComplexWith0() {
        Assertions.assertEquals(
            new DoubleComplex(),
            new DoubleComplex(1d, 1d).multiply(0d)
        );
    }

    @Test
    void multiplyComplexWith1() {
        Assertions.assertEquals(
            new DoubleComplex(1d, 1d),
            new DoubleComplex(1d, 1d).multiply(1d)
        );
    }

    @Test
    void multiplyComplexWithMinus1() {
        Assertions.assertEquals(
            new DoubleComplex(-2d, -1d),
            new DoubleComplex(2d, 1d).multiply(-1d)
        );
    }

    @Test
    void multiplyComplexWithDoubleComplex() {
        Assertions.assertEquals(
            new DoubleComplex(0d, 5d),
            new DoubleComplex(1d, 2d).multiply(new DoubleComplex(2d, 1d))
        );
    }

    @Test
    void divideZeroComplexWith1() {
        Assertions.assertEquals(
            new DoubleComplex(),
            new DoubleComplex().divide(1d)
        );
    }

    @Test
    void divideComplexWith0() {
        Assertions.assertEquals(
            new DoubleComplex(
                Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY
            ),
            new DoubleComplex(1d, 1d).divide(0d)
        );
    }

    @Test
    void divideComplexWith1() {
        Assertions.assertEquals(
            new DoubleComplex(1d, 1d),
            new DoubleComplex(1d, 1d).divide(1d)
        );
    }

    @Test
    void divideComplexWithMinus1() {
        Assertions.assertEquals(
            new DoubleComplex(-2d, -1d),
            new DoubleComplex(2d, 1d).divide(-1d)
        );
    }

    @Test
    void divideComplexWithDoubleComplex() {
        Assertions.assertEquals(
            new DoubleComplex(0.8, 0.6),
            new DoubleComplex(1d, 2d).divide(new DoubleComplex(2d, 1d))
        );
    }

    // endregion

    // region pow and root

    @Test
    void pow8OfComplexWithRe1Im1() {
        Assertions.assertEquals(
            new DoubleComplex(16.000000000000007, 1.0291984957930479E-14),
            new DoubleComplex(1d, 1d).pow(8)
        );
    }

    @Test
    void pow5OfComplexWithRe3Im4() {
        Assertions.assertEquals(
            new DoubleComplex(-236.99999999999898, -3116d),
            new DoubleComplex(3d, 4d).pow(5)
        );
    }

    @Test
    void root3OfComplexWithRe1Im0() {
        Assertions.assertEquals(
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
        Assertions.assertEquals(
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
        Assertions.assertEquals(
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
        Assertions.assertEquals(
            new DoubleComplex(-1d, 2d),
            new DoubleComplex(1d, 2d).inverseRe()
        );
    }

    @Test
    void inverseImOfComplexWithRe1Im2() {
        Assertions.assertEquals(
            new DoubleComplex(1d, -2d),
            new DoubleComplex(1d, 2d).inverseIm()
        );
    }

    @Test
    void inverseReImOfComplexWithRe1Im2() {
        Assertions.assertEquals(
            new DoubleComplex(-1d, -2d),
            new DoubleComplex(1d, 2d).inverse()
        );
    }

    // endregion

    // region static of, asVector, static ofVector

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
        Assertions.assertEquals(1, complex.absoluteValue());
        Assertions.assertEquals(2, complex.argument());
    }

    @Test
    void asVectorOfComplexWithRe1Im2() {
        Assertions.assertEquals(new Vector(1, 2),
            new DoubleComplex(1d, 2d).asVector()
        );
    }

    @Test
    void ofVectorOfVectorWithX1Y2() {
        Assertions.assertEquals(new DoubleComplex(1d, 2d),
            Complex.ofVector(new Vector(1, 2))
        );
    }

    @Test
    void asAndOfVectorWithoutChangeAreSuperfluous() {
        // better word than of superfluous?
        Complex<Double> complex = new DoubleComplex(1d, 2d);
        Assertions.assertEquals(complex, Complex.ofVector(complex.asVector()));
    }

    // endregion

    // region isValid and copy

    @Test
    void isValidWithZero() {
        Assertions.assertTrue(new DoubleComplex().isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new DoubleComplex(
                Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY
            ).isValid()
        );
    }

    @Test
    void copyOfComplexWithReIm() {
        Complex<Double> complex = new DoubleComplex(2d, 3d);
        Assertions.assertEquals(complex, complex.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfComplexWithReIm() {
        Complex<Double> complex = new DoubleComplex(2d, 3d);
        Assertions.assertEquals(
            complex,
            new DoubleComplex(2d, 3d)
        );
        Assertions.assertNotEquals(
            complex,
            new DoubleComplex(3d, 2d)
        );
    }

    @Test
    void hashCodeOfComplexWithReIm() {
        Assertions.assertEquals(
            525249,
            new DoubleComplex(2d, 3d).hashCode()
        );
    }

    @Test
    void toStringOfComplexWithReIm() {
        Complex<Double> complex = new DoubleComplex(2d, 3d);
        Assertions.assertEquals("2.0+3.0*i", complex.toString());
    }

    @Test
    void compareToOfComplexWithReIm() {
        Complex<Double> complex = new DoubleComplex(2d, 3d);
        Assertions.assertEquals(
            0, complex.compareTo(new DoubleComplex(2d, 3d))
        );
        Assertions.assertEquals(
            -1, complex.compareTo(new DoubleComplex(3d, 1d))
        );
        Assertions.assertEquals(
            1, complex.compareTo(new DoubleComplex(2d, 1d))
        );
    }

    // endregion


    // region assert

    private static void assertComplex(Complex<Double> complex) {
        assertComplex(complex, 0, 0);
    }

    private static void assertComplex(Complex<Double> complex, double re, double im) {
        Assertions.assertEquals(re, complex.getRe(), "re is invalid");
        Assertions.assertEquals(im, complex.getIm(), "im is invalid");
    }

    // endregion
}
