package io.rala.math.algebra.numeric;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.SerializableTestUtils;
import io.rala.math.testUtils.algebra.TestComplex;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ComplexTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertComplex(new TestComplex());
    }

    @Test
    void constructorWithDifferentReImParameter() {
        assertComplex(new TestComplex(2d, 3d), 2, 3);
    }

    @Test
    void constructorWithComplex() {
        assertComplex(new TestComplex(new TestComplex(2d, 3d)), 2, 3);
    }

    @Test
    void createAndSetRe() {
        TestComplex complex = new TestComplex();
        assertComplex(complex);
        complex.setRe(1);
        assertComplex(complex, 1, 0);
    }

    @Test
    void createAndSetIm() {
        TestComplex complex = new TestComplex();
        assertComplex(complex);
        complex.setIm(2);
        assertComplex(complex, 0, 2);
    }

    // endregion

    // region value

    @Test
    void intValueOfComplexWithX1_1Y2_2() {
        Assertions.assertEquals(1,
            new TestComplex(1.1, 2.2).intValue()
        );
    }

    @Test
    void longValueOfComplexWithX1_1Y2_2() {
        Assertions.assertEquals(1,
            new TestComplex(1.1, 2.2).longValue()
        );
    }

    @Test
    void floatValueOfComplexWithX1_1Y2_2() {
        Assertions.assertEquals((float) 1.1d,
            new TestComplex(1.1, 2.2).floatValue()
        );
    }

    @Test
    void doubleValueOfComplexWithX1_1Y2_2() {
        Assertions.assertEquals(1.1d,
            new TestComplex(1.1, 2.2).doubleValue()
        );
    }

    // endregion

    // region absoluteValue, argument and signum

    @Test
    void absoluteValueOfComplexWithoutParameter() {
        Assertions.assertEquals(0d,
            new TestComplex().absoluteValue()
        );
    }

    @Test
    void absoluteValueOfComplexX1Y1() {
        Assertions.assertEquals(Math.sqrt(2d),
            new TestComplex(1, 1).absoluteValue()
        );
    }

    @Test
    void absoluteValueOfComplexX1Y0() {
        Assertions.assertEquals(1d,
            new TestComplex(1, 0).absoluteValue()
        );
    }

    @Test
    void argumentOfComplexWithoutParameter() {
        Assertions.assertEquals(0, new TestComplex().argument());
    }

    @Test
    void argumentOfComplexX1Y1() {
        Assertions.assertEquals(0d,
            new TestComplex(1, 1).argument().doubleValue()
        );
    }

    @Test
    void argumentOfComplexX1Y0() {
        Assertions.assertEquals(0, new TestComplex(1, 0).argument());
    }

    @Test
    void signumOfComplexWithoutParameter() {
        Assertions.assertEquals(new TestComplex(),
            new TestComplex().signum()
        );
    }

    @Test
    void signumOfComplexX1Y1() {
        double sqrt2half = 0.7071067811865475; // Math.sqrt(2d) / 2
        Assertions.assertEquals(new TestComplex(sqrt2half, sqrt2half),
            new TestComplex(1, 1).signum()
        );
    }

    @Test
    void signumOfComplexX1Y0() {
        Assertions.assertEquals(new TestComplex(1d, 0d),
            new TestComplex(1, 0).signum()
        );
    }

    @Test
    void complexSignumOfComplexWithoutParameter() {
        Assertions.assertEquals(0,
            new TestComplex().complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX1Y1() {
        Assertions.assertEquals(1,
            new TestComplex(1, 1).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexXMinus1Y0() {
        Assertions.assertEquals(-1,
            new TestComplex(-1, 0).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX0Y1() {
        Assertions.assertEquals(1,
            new TestComplex(0, 1).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX0YMinus1() {
        Assertions.assertEquals(-1,
            new TestComplex(0, -1).complexSignum()
        );
    }

    // endregion

    // region conjugation and reciprocal

    @Test
    void conjugationOfComplexWithRe1Im2() {
        Assertions.assertEquals(
            new TestComplex(1, -2),
            new TestComplex(1, 2).conjugation()
        );
    }

    @Test
    void reciprocalOfComplexWithoutParameter() {
        Assertions.assertEquals(
            new TestComplex(Double.NaN, Double.NaN),
            new TestComplex().reciprocal()
        );
    }

    @Test
    void reciprocalOfComplexX1Y1() {
        Assertions.assertEquals(new TestComplex(0.5, -0.5),
            new TestComplex(1, 1).reciprocal()
        );
    }

    @Test
    void reciprocalOfComplexX1Y0() {
        Assertions.assertEquals(new TestComplex(1d, 0d),
            new TestComplex(1, 0).reciprocal()
        );
    }

    // endregion

    // region add and subtract

    @Test
    void addWithXY() {
        Assertions.assertEquals(
            new TestComplex(2, 2),
            new TestComplex().add(2, 2)
        );
    }

    @Test
    void addWithXAndY() {
        Assertions.assertEquals(
            new TestComplex(2, 2),
            new TestComplex(1, 1).add(1, 1)
        );
    }

    @Test
    void addWithComplex() {
        Assertions.assertEquals(new TestComplex(2, 2),
            new TestComplex(1, 0).add(new TestComplex(1, 2))
        );
    }

    @Test
    void subtractWithXY() {
        Assertions.assertEquals(
            new TestComplex(),
            new TestComplex(2, 2).subtract(2, 2)
        );
    }

    @Test
    void subtractWithXAndY() {
        Assertions.assertEquals(
            new TestComplex(1, 1),
            new TestComplex(2, 2).subtract(1, 1)
        );
    }

    @Test
    void subtractWithComplex() {
        Assertions.assertEquals(new TestComplex(1, 0),
            new TestComplex(2, 2).subtract(new TestComplex(1, 2))
        );
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyZeroComplexWith1() {
        Assertions.assertEquals(
            new TestComplex(), new TestComplex().multiply(1)
        );
    }

    @Test
    void multiplyComplexWith0() {
        Assertions.assertEquals(
            new TestComplex(),
            new TestComplex(1, 1).multiply(0)
        );
    }

    @Test
    void multiplyComplexWith1() {
        Assertions.assertEquals(
            new TestComplex(1, 1),
            new TestComplex(1, 1).multiply(1)
        );
    }

    @Test
    void multiplyComplexWithMinus1() {
        Assertions.assertEquals(
            new TestComplex(-2, -1),
            new TestComplex(2, 1).multiply(-1)
        );
    }

    @Test
    void multiplyComplexWithComplex() {
        Assertions.assertEquals(
            new TestComplex(0, 5),
            new TestComplex(1, 2).multiply(new TestComplex(2, 1))
        );
    }

    @Test
    void divideZeroComplexWith1() {
        Assertions.assertEquals(
            new TestComplex(0d, 0d),
            new TestComplex().divide(1)
        );
    }

    @Test
    void divideComplexWith0() {
        Assertions.assertEquals(
            new TestComplex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY),
            new TestComplex(1, 1).divide(0)
        );
    }

    @Test
    void divideComplexWith1() {
        Assertions.assertEquals(
            new TestComplex(1d, 1d),
            new TestComplex(1, 1).divide(1)
        );
    }

    @Test
    void divideComplexWithMinus1() {
        Assertions.assertEquals(
            new TestComplex(-2d, -1d),
            new TestComplex(2, 1).divide(-1)
        );
    }

    @Test
    void divideComplexWithComplex() {
        Assertions.assertEquals(
            new TestComplex(0.8, 0.6),
            new TestComplex(1, 2).divide(new TestComplex(2, 1))
        );
    }

    // endregion

    // region pow and root

    @Test
    void pow8OfComplexWithRe1Im1() {
        Assertions.assertEquals(
            new TestComplex(16, 0),
            new TestComplex(1, 1).pow(8)
        );
    }

    @Test
    void pow5OfComplexWithRe3Im4() {
        Assertions.assertEquals(
            new TestComplex(3125, 0),
            new TestComplex(3, 4).pow(5)
        );
    }

    @Test
    void root3OfComplexWithRe1Im0() {
        Assertions.assertEquals(
            List.of(
                new TestComplex(1, 0),
                new TestComplex(0, 0),
                new TestComplex(0, 0)
            ),
            new TestComplex(1, 0).root(3)
        );
    }

    @Test
    void root4OfComplexWithRe1Im0() {
        Assertions.assertEquals(
            List.of(
                new TestComplex(1, 0),
                new TestComplex(0, 0),
                new TestComplex(0, 0),
                new TestComplex(0, 0)
            ),
            new TestComplex(1, 0).root(4)
        );
    }

    @Test
    void root2OfComplexWithReMinus1ImSqrt3() {
        Assertions.assertEquals(
            List.of(
                new TestComplex(0, 0),
                new TestComplex(0, 0)
            ),
            new TestComplex(-1, Math.sqrt(3)).root(2)
        );
    }

    // endregion

    // region inverse

    @Test
    void inverseReOfComplexWithRe1Im2() {
        Assertions.assertEquals(
            new TestComplex(-1, 2),
            new TestComplex(1, 2).inverseRe()
        );
    }

    @Test
    void inverseImOfComplexWithRe1Im2() {
        Assertions.assertEquals(
            new TestComplex(1, -2),
            new TestComplex(1, 2).inverseIm()
        );
    }

    @Test
    void inverseReImOfComplexWithRe1Im2() {
        Assertions.assertEquals(
            new TestComplex(-1, -2),
            new TestComplex(1, 2).inverse()
        );
    }

    // endregion

    // region static of, asVector, static ofVector

    @Test
    void ofAb3AndAr50() {
        assertComplex(
            Complex.of(new TestAbstractArithmetic(), 3, 50),
            0,
            0
        );
    }

    @Test
    void ofWithSelfValidation() {
        Complex<Number> complex = Complex.of(new TestAbstractArithmetic(),
            1, 2
        );
        Assertions.assertEquals(0d, complex.absoluteValue());
        Assertions.assertEquals(0, complex.argument());
    }

    @Test
    void asVectorOfComplexWithRe1Im2() {
        Assertions.assertEquals(new Vector(1, 2),
            new TestComplex(1, 2).asVector()
        );
    }

    @Test
    void ofVectorOfVectorWithX1Y2() {
        Complex<Number> complex = new TestComplex(1d, 2d);
        Assertions.assertEquals(complex,
            Complex.ofVector(new Vector(1, 2))
        );
    }

    @Test
    void asAndOfVectorWithoutChangeAreSuperfluous() {
        // better word than of superfluous?
        Complex<Number> complex = new TestComplex(1d, 2d);
        Assertions.assertEquals(complex, Complex.ofVector(complex.asVector()));
    }

    // endregion

    // region map

    @Test
    void mapOfComplexWithR0_5Im1_5() {
        TestComplex complex = new TestComplex(0.5, 1.5);
        Complex<Integer> result =
            new Complex<>(new IntegerArithmetic(), 0, 1);
        Assertions.assertEquals(result,
            complex.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    // endregion

    // region isValid and copy

    @Test
    void isValidWithZero() {
        Assertions.assertTrue(new TestComplex().isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new TestComplex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void copyOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        Assertions.assertEquals(complex, complex.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        Assertions.assertEquals(
            complex,
            new TestComplex(2, 3)
        );
        Assertions.assertNotEquals(
            complex,
            new TestComplex(3, 2)
        );
    }

    @Test
    void hashCodeOfComplexWithReIm() {
        Assertions.assertEquals(
            1026,
            new TestComplex(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        Assertions.assertEquals("2+3*i", complex.toString());
    }

    @Test
    void compareToOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        Assertions.assertEquals(
            0, complex.compareTo(new TestComplex(2, 3))
        );
        Assertions.assertEquals(
            -1, complex.compareTo(new TestComplex(3, 1))
        );
        Assertions.assertEquals(
            1, complex.compareTo(new TestComplex(2, 1))
        );
    }

    @Test
    void serializable() {
        SerializableTestUtils.verify(
            new TestComplex(),
            TestComplex.class
        );
    }

    // endregion


    // region assert

    private static void assertComplex(TestComplex complex) {
        assertComplex(complex, 0, 0);
    }

    private static void assertComplex(Complex<Number> complex, double re, double im) {
        Assertions.assertEquals(re, complex.getRe().doubleValue(), "re is invalid");
        Assertions.assertEquals(im, complex.getIm().doubleValue(), "im is invalid");
    }

    // endregion
}
