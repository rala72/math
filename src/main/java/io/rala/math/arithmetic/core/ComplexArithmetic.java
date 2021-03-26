package io.rala.math.arithmetic.core;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.exception.NotSupportedException;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * class which handles {@link Complex} arithmetic
 *
 * @param <T> number class
 * @since 1.0.0
 */
public class ComplexArithmetic<T extends Number> extends AbstractArithmetic<Complex<T>> {
    private final AbstractArithmetic<T> arithmetic;

    /**
     * creates a new instance
     *
     * @param arithmetic arithmetic of complex
     * @since 1.0.0
     */
    public ComplexArithmetic(@NotNull AbstractArithmetic<T> arithmetic) {
        this.arithmetic = arithmetic;
    }

    /**
     * @return stored {@link AbstractArithmetic} of {@link Complex}
     * @since 1.0.0
     */
    public AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

    // region fromInt, fromDouble and signum

    @Override
    @NotNull
    public Complex<T> fromInt(int a) {
        return new Complex<>(getArithmetic(),
            getArithmetic().fromInt(a),
            getArithmetic().zero()
        );
    }

    @Override
    @NotNull
    public Complex<T> fromDouble(double a) {
        return new Complex<>(getArithmetic(),
            getArithmetic().fromDouble(a),
            getArithmetic().fromDouble(0)
        );
    }

    @Override
    public double signum(@NotNull Complex<T> a) {
        return a.complexSignum();
    }

    // endregion

    // region compare

    @Override
    public int compare(@NotNull Complex<T> a, @NotNull Complex<T> b) {
        return a.compareTo(b);
    }

    // endregion

    // region sum, difference, product and quotient

    @Override
    @NotNull
    public Complex<T> sum(@NotNull Complex<T> a, @NotNull Complex<T> b) {
        return a.add(b);
    }

    @Override
    @NotNull
    public Complex<T> difference(@NotNull Complex<T> a, @NotNull Complex<T> b) {
        return a.subtract(b);
    }

    @Override
    @NotNull
    public Complex<T> product(@NotNull Complex<T> a, @NotNull Complex<T> b) {
        return a.multiply(b);
    }

    @Override
    @NotNull
    public Complex<T> quotient(@NotNull Complex<T> a, @NotNull Complex<T> b) {
        return a.divide(b);
    }

    // endregion

    // region power and root

    @Override
    @NotNull
    public Complex<T> power(@NotNull Complex<T> a, int b) {
        return a.pow(b);
    }

    @Override
    @NotNull
    public Complex<T> root(@NotNull Complex<T> a, int b) {
        return a.root(b).get(0);
    }

    // endregion

    // region gcd

    @Override
    @NotNull
    public Complex<T> gcd(@NotNull Complex<T> a, @NotNull Complex<T> b) {
        throw new NotSupportedException();
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
