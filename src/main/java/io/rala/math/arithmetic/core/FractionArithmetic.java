package io.rala.math.arithmetic.core;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.exception.NotSupportedException;

import java.util.Objects;

/**
 * class which handles {@link Fraction} arithmetic
 *
 * @param <T> number class of values
 * @param <R> number class of result of {@link Number} methods
 * @since 1.0.0
 */
public class FractionArithmetic<T extends Number, R extends Number>
    extends AbstractArithmetic<Fraction<T, R>> {
    private final AbstractResultArithmetic<T, R> arithmetic;

    /**
     * creates a new {@link AbstractResultArithmetic} with given two arithmetic
     *
     * @param arithmetic arithmetic of {@link Fraction}
     * @since 1.0.0
     */
    public FractionArithmetic(AbstractResultArithmetic<T, R> arithmetic) {
        this.arithmetic = arithmetic;
    }

    /**
     * @return stored {@link AbstractResultArithmetic}
     * @since 1.0.0
     */
    public AbstractResultArithmetic<T, R> getArithmetic() {
        return arithmetic;
    }

    // region fromInt, fromDouble and signum

    @Override
    public Fraction<T, R> fromInt(int a) {
        return new Fraction<>(getArithmetic(),
            getArithmetic().getTArithmetic().fromInt(a)
        );
    }

    @Override
    public Fraction<T, R> fromDouble(double a) {
        String string = Double.toString(a);
        String nominatorString = string.replaceAll("[.,]", "");
        double nominator = Double.parseDouble(nominatorString);
        String denominatorString = string.replaceFirst("\\d+\\.", "");
        double denominator = denominatorString.length() * 10d;
        return new Fraction<>(getArithmetic(),
            getArithmetic().getTArithmetic().fromDouble(nominator),
            getArithmetic().getTArithmetic().fromDouble(denominator)
        ).simplify();
    }

    @Override
    public double signum(Fraction<T, R> a) {
        return getArithmetic().getTArithmetic().signum(a.getNumerator());
    }

    // endregion

    // region negate and compare

    @Override
    public Fraction<T, R> negate(Fraction<T, R> a) {
        return a.negate();
    }

    @Override
    public int compare(Fraction<T, R> a, Fraction<T, R> b) {
        return a.compareTo(b);
    }

    // endregion

    // region sum, difference, product and quotient

    @Override
    public Fraction<T, R> sum(Fraction<T, R> a, Fraction<T, R> b) {
        return a.add(b);
    }

    @Override
    public Fraction<T, R> difference(Fraction<T, R> a, Fraction<T, R> b) {
        return a.subtract(b);
    }

    @Override
    public Fraction<T, R> product(Fraction<T, R> a, Fraction<T, R> b) {
        return a.multiply(b);
    }

    @Override
    public Fraction<T, R> quotient(Fraction<T, R> a, Fraction<T, R> b) {
        return a.divide(b);
    }

    // endregion

    // region power and root

    @Override
    public Fraction<T, R> power(Fraction<T, R> a, int b) {
        return a.pow(b);
    }

    @Override
    public Fraction<T, R> root(Fraction<T, R> a, int b) {
        return a.root(b);
    }

    // endregion

    // region gcd

    @Override
    public Fraction<T, R> gcd(Fraction<T, R> a, Fraction<T, R> b) {
        throw new NotSupportedException();
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FractionArithmetic)) return false;
        if (!super.equals(o)) return false;
        FractionArithmetic<?, ?> that = (FractionArithmetic<?, ?>) o;
        return Objects.equals(getArithmetic(), that.getArithmetic());
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(getArithmetic());
    }

    // endregion
}
