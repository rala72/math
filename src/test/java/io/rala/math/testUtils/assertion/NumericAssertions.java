package io.rala.math.testUtils.assertion;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.algebra.numeric.Fraction;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(complex.getRe()).as("re is invalid").isEqualTo(re);
        assertThat(complex.getIm()).as("im is invalid").isEqualTo(im);
    }

    /**
     * asserts that fraction has expected values
     */
    public static <T extends Number, R extends Number> void assertFraction(
        Fraction<T, R> fraction, T no, T de
    ) {
        assertThat(fraction.getNumerator()).as("no is invalid").isEqualTo(no);
        assertThat(fraction.getDenominator()).as("de is invalid").isEqualTo(de);
    }
}
