package io.rala.math.testUtils.assertion;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.algebra.numeric.Fraction;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * assertions for {@link io.rala.math.algebra.numeric} package
 */
public class NumericAssertions {
    private NumericAssertions() {
    }

    /**
     * asserts that complex has expected values
     */
    public static <T extends Number> void assertComplex(Complex<T> complex, T re, T im) {
        assertEquals(re, complex.getRe(), "re is invalid");
        assertEquals(im, complex.getIm(), "im is invalid");
    }

    /**
     * asserts that fraction has expected values
     */
    public static <T extends Number, R extends Number> void assertFraction(
        Fraction<T, R> fraction, T no, T de
    ) {
        assertEquals(no, fraction.getNumerator(), "no is invalid");
        assertEquals(de, fraction.getDenominator(), "de is invalid");
    }
}
