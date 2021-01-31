package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a real and a imaginary part of a complex number
 * storing {@link BigDecimal}
 *
 * @since 1.0.0
 */
public class BigDecimalComplex extends Complex<BigDecimal> {
    // region constructor

    /**
     * @see Complex#Complex(AbstractArithmetic)
     * @since 1.0.0
     */
    public BigDecimalComplex() {
        super(BigDecimalArithmetic.getInstance());
    }

    /**
     * @param context context of {@link BigDecimalArithmetic}
     * @see Complex#Complex(AbstractArithmetic)
     * @since 1.0.0
     */
    public BigDecimalComplex(MathContext context) {
        super(new BigDecimalArithmetic(context));
    }

    /**
     * @param re real part
     * @param im imaginary part
     * @see Complex#Complex(AbstractArithmetic, Number, Number)
     * @since 1.0.0
     */
    public BigDecimalComplex(BigDecimal re, BigDecimal im) {
        super(BigDecimalArithmetic.getInstance(), re, im);
    }

    /**
     * @param re      real part
     * @param im      imaginary part
     * @param context context of {@link BigDecimalArithmetic}
     * @see Complex#Complex(AbstractArithmetic, Number, Number)
     * @since 1.0.0
     */
    public BigDecimalComplex(BigDecimal re, BigDecimal im, MathContext context) {
        super(new BigDecimalArithmetic(context), re, im);
    }

    /**
     * creates a new complex based on given one
     *
     * @param complex complex to copy
     * @see Complex#Complex(Complex)
     * @since 1.0.0
     */
    public BigDecimalComplex(Complex<BigDecimal> complex) {
        super(complex.getArithmetic(), complex.getRe(), complex.getIm());
    }

    // endregion

    // region static of

    /**
     * @param absoluteValue absolute value of complex
     * @param argument      argument of vector
     * @return new complex based on absoluteValue and argument
     * @since 1.0.0
     */
    public static BigDecimalComplex of(BigDecimal absoluteValue, BigDecimal argument) {
        return new BigDecimalComplex(
            Complex.of(BigDecimalArithmetic.getInstance(), absoluteValue, argument)
        );
    }

    // endregion
}
