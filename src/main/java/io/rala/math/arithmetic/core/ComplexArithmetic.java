package io.rala.math.arithmetic.core;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.AbstractArithmetic;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * class which handles {@link BigDecimal} arithmetic
 *
 * @param <T> number class
 */
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
            getArithmetic().zero()
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

    // region compare

    @Override
    public int compare(Complex<T> a, Complex<T> b) {
        return a.compareTo(b);
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

    // region power and root

    @Override
    public Complex<T> power(Complex<T> a, int b) {
        return a.pow(b);
    }

    @Override
    public Complex<T> root(Complex<T> a, int b) {
        return a.root(b).get(0);
    }

    // endregion

    // region gcd

    @Override
    public Complex<T> gcd(Complex<T> a, Complex<T> b) {
        throw new NotImplementedException();
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplexArithmetic)) return false;
        if (!super.equals(o)) return false;
        ComplexArithmetic<?> that = (ComplexArithmetic<?>) o;
        return Objects.equals(getArithmetic(), that.getArithmetic());
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(getArithmetic());
    }


    // endregion
}
