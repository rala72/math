package io.rala.math.algebra;

import io.rala.math.geometry.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ComplexTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertComplex(new Complex());
    }

    @Test
    void constructorWithDifferentReImParameter() {
        assertComplex(new Complex(2, 3), 2, 3);
    }

    @Test
    void createAndSetRe() {
        Complex complex = new Complex();
        assertComplex(complex);
        complex.setRe(1);
        assertComplex(complex, 1, 0);
    }

    @Test
    void createAndSetIm() {
        Complex complex = new Complex();
        assertComplex(complex);
        complex.setIm(2);
        assertComplex(complex, 0, 2);
    }

    // endregion

    // region absoluteValue, argument, conjugation and reciprocal

    @Test
    void absoluteValueOfComplexWithoutParameter() {
        Assertions.assertEquals(0, new Complex().absoluteValue());
    }

    @Test
    void absoluteValueOfComplexX1Y1() {
        Assertions.assertEquals(1.4142135623730951,
            new Complex(1, 1).absoluteValue()
        );
    }

    @Test
    void absoluteValueOfComplexX1Y0() {
        Assertions.assertEquals(1, new Complex(1, 0).absoluteValue());
    }

    @Test
    void argumentOfComplexWithoutParameter() {
        Assertions.assertEquals(Double.NaN, new Complex().argument());
    }

    @Test
    void argumentOfComplexX1Y1() {
        Assertions.assertEquals(0.7853981633974484, // Math.PI / 4
            new Complex(1, 1).argument()
        );
    }

    @Test
    void argumentOfComplexX1Y0() {
        Assertions.assertEquals(0, new Complex(1, 0).argument());
    }

    @Test
    void conjugationOfComplexWithRe1Im2() {
        Assertions.assertEquals(
            new Complex(1, -2),
            new Complex(1, 2).conjugation()
        );
    }

    @Test
    void reciprocalOfComplexWithoutParameter() {
        Assertions.assertEquals(
            new Complex(Double.NaN, Double.NaN),
            new Complex().reciprocal()
        );
    }

    @Test
    void reciprocalOfComplexX1Y1() {
        Assertions.assertEquals(new Complex(0.5, -0.5),
            new Complex(1, 1).reciprocal()
        );
    }

    @Test
    void reciprocalOfComplexX1Y0() {
        Assertions.assertEquals(new Complex(1, -0d),
            new Complex(1, 0).reciprocal()
        );
    }

    // endregion

    // region add and subtract

    @Test
    void addWithXY() {
        Assertions.assertEquals(new Complex(2, 2), new Complex().add(2, 2));
    }

    @Test
    void addWithXAndY() {
        Assertions.assertEquals(new Complex(2, 2), new Complex(1, 1).add(1, 1));
    }

    @Test
    void addWithComplex() {
        Assertions.assertEquals(new Complex(2, 2),
            new Complex(1, 0).add(new Complex(1, 2))
        );
    }

    @Test
    void subtractWithXY() {
        Assertions.assertEquals(new Complex(), new Complex(2, 2).subtract(2, 2));
    }

    @Test
    void subtractWithXAndY() {
        Assertions.assertEquals(new Complex(1, 1), new Complex(2, 2).subtract(1, 1));
    }

    @Test
    void subtractWithComplex() {
        Assertions.assertEquals(new Complex(1, 0),
            new Complex(2, 2).subtract(new Complex(1, 2))
        );
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyZeroComplexWith1() {
        Assertions.assertEquals(new Complex(), new Complex().multiply(1));
    }

    @Test
    void multiplyComplexWith0() {
        Assertions.assertEquals(new Complex(), new Complex(1, 1).multiply(0));
    }

    @Test
    void multiplyComplexWith1() {
        Assertions.assertEquals(new Complex(1, 1), new Complex(1, 1).multiply(1));
    }

    @Test
    void multiplyComplexWithMinus1() {
        Assertions.assertEquals(new Complex(-2, -1), new Complex(2, 1).multiply(-1));
    }

    @Test
    void multiplyComplexWithComplex() {
        Assertions.assertEquals(
            new Complex(0, 5),
            new Complex(1, 2).multiply(new Complex(2, 1))
        );
    }

    @Test
    void divideZeroComplexWith1() {
        Assertions.assertEquals(new Complex(), new Complex().divide(1));
    }

    @Test
    void divideComplexWith0() {
        Assertions.assertEquals(
            new Complex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY),
            new Complex(1, 1).divide(0)
        );
    }

    @Test
    void divideComplexWith1() {
        Assertions.assertEquals(new Complex(1, 1), new Complex(1, 1).divide(1));
    }

    @Test
    void divideComplexWithMinus1() {
        Assertions.assertEquals(new Complex(-2, -1), new Complex(2, 1).divide(-1));
    }

    @Test
    void divideComplexWithComplex() {
        Assertions.assertEquals(
            new Complex(0.8, 0.6),
            new Complex(1, 2).divide(new Complex(2, 1))
        );
    }

    // endregion

    // region inverse

    @Test
    void inverseReOfComplexWithRe1Im2() {
        Assertions.assertEquals(new Complex(-1, 2), new Complex(1, 2).inverseRe());
    }

    @Test
    void inverseImOfComplexWithRe1Im2() {
        Assertions.assertEquals(new Complex(1, -2), new Complex(1, 2).inverseIm());
    }

    @Test
    void inverseReImOfComplexWithRe1Im2() {
        Assertions.assertEquals(new Complex(-1, -2), new Complex(1, 2).inverse());
    }

    // endregion

    // region asVector, static ofVector

    @Test
    void asVectorOfComplexWithRe1Im2() {
        Assertions.assertEquals(new Vector(1, 2),
            new Complex(1, 2).asVector()
        );
    }

    @Test
    void ofVectorOfVectorWithX1Y2() {
        Assertions.assertEquals(new Complex(1, 2),
            Complex.ofVector(new Vector(1, 2))
        );
    }

    @Test
    void asAndOfVectorWithoutChangeAreSuperfluous() {
        // better word than of superfluous?
        Complex complex = new Complex(1, 2);
        Assertions.assertEquals(complex, Complex.ofVector(complex.asVector()));
    }

    // endregion

    // region copy

    @Test
    void copyOfComplexWithReIm() {
        Complex complex = new Complex(2, 3);
        Assertions.assertEquals(complex, complex.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfComplexWithReIm() {
        Complex complex = new Complex(2, 3);
        Assertions.assertEquals(
            complex,
            new Complex(2, 3)
        );
        Assertions.assertNotEquals(
            complex,
            new Complex(3, 2)
        );
    }

    @Test
    void hashCodeOfComplexWithReIm() {
        Assertions.assertEquals(
            525249,
            new Complex(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfComplexWithReIm() {
        Complex complex = new Complex(2, 3);
        Assertions.assertEquals("2.0+3.0*i", complex.toString());
    }

    @Test
    void compareToOfComplexWithReIm() {
        Complex complex = new Complex(2, 3);
        Assertions.assertEquals(
            0, complex.compareTo(new Complex(2, 3))
        );
        Assertions.assertEquals(
            -1, complex.compareTo(new Complex(3, 1))
        );
        Assertions.assertEquals(
            1, complex.compareTo(new Complex(2, 1))
        );
    }

    // endregion


    // region assert

    private static void assertComplex(Complex complex) {
        assertComplex(complex, 0, 0);
    }

    private static void assertComplex(Complex complex, double re, double im) {
        Assertions.assertEquals(re, complex.getRe(), "re is invalid");
        Assertions.assertEquals(im, complex.getIm(), "im is invalid");
    }

    // endregion
}
