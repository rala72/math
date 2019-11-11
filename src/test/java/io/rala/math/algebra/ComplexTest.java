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
