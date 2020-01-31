package io.rala.math.testUtils.assertion;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.algebra.numeric.Fraction;
import org.junit.jupiter.api.Assertions;

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
        Assertions.assertEquals(re, complex.getRe(), "re is invalid");
        Assertions.assertEquals(im, complex.getIm(), "im is invalid");
    }

    /**
     * asserts that fraction has expected values
     */
    public static <T extends Number, R extends Number> void assertFraction(
        Fraction<T, R> fraction, T no, T de
    ) {
        Assertions.assertEquals(no, fraction.getNumerator(), "no is invalid");
        Assertions.assertEquals(de, fraction.getDenominator(), "de is invalid");
    }
}
