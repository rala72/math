package io.rala.math.arithmetic.core;

import io.rala.math.algebra.Complex;
import io.rala.math.arithmetic.AbstractArithmetic;

import java.math.BigDecimal;

/**
 * class which handles {@link BigDecimal} arithmetic
 *
 * @param <T> number class
 */
@SuppressWarnings("unused")
public class ComplexArithmetic<T extends Number> extends AbstractArithmetic<Complex<T>> {
    private final AbstractArithmetic<T> arithmetic;

    /**
     * creates a new instance
     *
     * @param arithmetic arithmetic of complex
     */
    public ComplexArithmetic(AbstractArithmetic<T> arithmetic) {
        this.arithmetic = arithmetic;
    }

    /**
     * @return current {@link AbstractArithmetic} of {@link Complex}
     */
    public AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

    // region fromInt, fromDouble and signum

    @Override
    public Complex<T> fromInt(int a) {
        return new Complex<>(getArithmetic(),
            getArithmetic().fromInt(a),
            getArithmetic().fromInt(0)
        );
    }

    @Override
    public Complex<T> fromDouble(double a) {
        return new Complex<>(getArithmetic(),
            getArithmetic().fromDouble(a),
            getArithmetic().fromDouble(0)
        );
    }

    @Override
    public double signum(Complex<T> a) {
        return a.complexSignum();
    }

    // endregion

    // region sum, difference, product and quotient

    @Override
    public Complex<T> sum(Complex<T> a, Complex<T> b) {
        return a.add(b);
    }

    @Override
    public Complex<T> difference(Complex<T> a, Complex<T> b) {
        return a.subtract(b);
    }

    @Override
    public Complex<T> product(Complex<T> a, Complex<T> b) {
        return a.multiply(b);
    }

    @Override
    public Complex<T> quotient(Complex<T> a, Complex<T> b) {
        return a.divide(b);
    }

    // endregion

    // region exponent and root

    @Override
    public Complex<T> exponent(Complex<T> a, int b) {
        return a.pow(b);
    }

    @Override
    public Complex<T> root(Complex<T> a, int b) {
        return a.root(b).get(0);
    }

    // endregion
}
