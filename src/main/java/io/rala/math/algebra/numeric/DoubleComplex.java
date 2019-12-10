package io.rala.math.algebra.numeric;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;

/**
 * class which holds a real and a imaginary part of a complex number
 * storing {@link Double}
 */
@SuppressWarnings("unused")
public class DoubleComplex extends Complex<Double> {
    // region constructor

    /**
     * @see Complex#Complex(AbstractArithmetic)
     */
    public DoubleComplex() {
        super(new DoubleArithmetic());
    }

    /**
     * creates a new complex with real and imaginary part
     *
     * @param re real part
     * @param im imaginary part
     * @see Complex#Complex(AbstractArithmetic, Number, Number)
     */
    public DoubleComplex(Double re, Double im) {
        super(new DoubleArithmetic(), re, im);
    }

    /**
     * creates a new complex based on given one
     *
     * @param complex complex to copy
     * @see Complex#Complex(Complex)
     */
    public DoubleComplex(Complex<Double> complex) {
        super(complex.getArithmetic(), complex.getRe(), complex.getIm());
    }

    // endregion

    // region static of

    /**
     * @param absoluteValue absolute value of complex
     * @param argument      argument of vector
     * @return new complex based on absoluteValue and argument
     */
    public static DoubleComplex of(double absoluteValue, double argument) {
        return new DoubleComplex(
            Complex.of(new DoubleArithmetic(), absoluteValue, argument)
        );
    }

    // endregion
}
