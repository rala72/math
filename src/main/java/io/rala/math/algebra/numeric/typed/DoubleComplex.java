package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import org.jetbrains.annotations.NotNull;

/**
 * class which holds a real and an imaginary part of a complex number
 * storing {@link Double}
 *
 * @since 1.0.0
 */
public class DoubleComplex extends Complex<Double> {
    // region constructor

    /**
     * @see Complex#Complex(AbstractArithmetic)
     * @since 1.0.0
     */
    public DoubleComplex() {
        super(DoubleArithmetic.getInstance());
    }

    /**
     * @param re real part
     * @see Complex#Complex(AbstractArithmetic, Number)
     * @since 1.1.0
     */
    public DoubleComplex(double re) {
        super(DoubleArithmetic.getInstance(), re);
    }

    /**
     * @param re real part
     * @param im imaginary part
     * @see Complex#Complex(AbstractArithmetic, Number, Number)
     * @since 1.0.0
     */
    public DoubleComplex(double re, Double im) {
        super(DoubleArithmetic.getInstance(), re, im);
    }

    /**
     * creates a new complex based on given one
     *
     * @param complex complex to copy
     * @see Complex#Complex(Complex)
     * @since 1.0.0
     */
    public DoubleComplex(@NotNull Complex<Double> complex) {
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
    @NotNull
    public static DoubleComplex of(double absoluteValue, double argument) {
        return new DoubleComplex(
            Complex.of(DoubleArithmetic.getInstance(), absoluteValue, argument)
        );
    }

    // endregion
}
