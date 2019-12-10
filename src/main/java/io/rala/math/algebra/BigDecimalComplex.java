package io.rala.math.algebra;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.BigDecimalArithmetic;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a real and a imaginary part of a complex number
 * storing {@link BigDecimal}
 */
@SuppressWarnings("unused")
public class BigDecimalComplex extends Complex<BigDecimal> {
    // region constructor

    /**
     * @see Complex#Complex(AbstractArithmetic)
     */
    public BigDecimalComplex() {
        super(new BigDecimalArithmetic());
    }

    /**
     * @param context context of {@link BigDecimalArithmetic}
     * @see Complex#Complex(AbstractArithmetic)
     */
    public BigDecimalComplex(MathContext context) {
        super(new BigDecimalArithmetic(context));
    }

    /**
     * creates a new complex with real and imaginary part
     *
     * @param re real part
     * @param im imaginary part
     * @see Complex#Complex(AbstractArithmetic, Number, Number)
     */
    public BigDecimalComplex(BigDecimal re, BigDecimal im) {
        super(new BigDecimalArithmetic(), re, im);
    }

    /**
     * creates a new complex with real and imaginary part
     *
     * @param re      real part
     * @param im      imaginary part
     * @param context context of {@link BigDecimalArithmetic}
     * @see Complex#Complex(AbstractArithmetic, Number, Number)
     */
    public BigDecimalComplex(BigDecimal re, BigDecimal im, MathContext context) {
        super(new BigDecimalArithmetic(context), re, im);
    }

    /**
     * creates a new complex based on given one
     *
     * @param complex complex to copy
     * @see Complex#Complex(Complex)
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
     */
    public static BigDecimalComplex of(BigDecimal absoluteValue, BigDecimal argument) {
        return new BigDecimalComplex(
            Complex.of(new BigDecimalArithmetic(), absoluteValue, argument)
        );
    }

    // endregion
}
