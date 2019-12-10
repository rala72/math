package io.rala.math.algebra.numeric;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.utils.Copyable;

import java.util.Objects;

/**
 * class which holds a numerator and denominator
 *
 * @param <T> number class of fraction elements
 * @param <V> number class of value
 */
@SuppressWarnings("unused")
public class Fraction<T extends Number, V extends Number> extends Number
    implements Copyable<Fraction<T, V>>, Comparable<Fraction<T, V>> {
    protected static final String EXCEPTION_NUMERATOR_NOT_NULL = "numerator must not be null";
    protected static final String EXCEPTION_DENOMINATOR_NOT_ZERO = "denominator must not be zero";

    // region attributes

    private final AbstractResultArithmetic<T, V> arithmetic;
    private T numerator;
    private T denominator;

    // endregion

    // region constructors

    /**
     * calls {@link #Fraction(AbstractResultArithmetic, Number, Number)} with given numerator and <code>null</code>
     *
     * @param arithmetic arithmetic for calculations
     * @param numerator  numerator of fraction
     */
    public Fraction(AbstractResultArithmetic<T, V> arithmetic, T numerator) {
        this(arithmetic, numerator, null);
    }

    /**
     * creates a new fraction with numerator and denominator<br>
     * denominator may be <code>null</code> - in this case <code>1</code> is used
     *
     * @param arithmetic  arithmetic for calculations
     * @param numerator   numerator of fraction
     * @param denominator denominator of fraction
     */
    public Fraction(AbstractResultArithmetic<T, V> arithmetic, T numerator, T denominator) {
        this.arithmetic = arithmetic;
        if (numerator == null)
            throw new IllegalArgumentException(EXCEPTION_NUMERATOR_NOT_NULL);
        this.numerator = numerator;
        this.denominator = denominator == null ?
            arithmetic.getTArithmetic().fromInt(1) : denominator;
        if (this.denominator.equals(arithmetic.getTArithmetic().fromInt(0)))
            throw new IllegalArgumentException(EXCEPTION_DENOMINATOR_NOT_ZERO);
    }

    /**
     * creates a new fraction based on given one
     *
     * @param fraction fraction to copy
     */
    protected Fraction(Fraction<T, V> fraction) {
        this(fraction.getArithmetic(), fraction.getNumerator(), fraction.getDenominator());
    }

    // endregion

    // region getter and setter

    /**
     * @return numerator of fraction
     */
    public T getNumerator() {
        return numerator;
    }

    /**
     * @param numerator new numerator of fraction
     */
    public void setNumerator(T numerator) {
        if (numerator == null)
            throw new IllegalArgumentException(EXCEPTION_NUMERATOR_NOT_NULL);
        this.numerator = numerator;
    }

    /**
     * @return denominator of fraction
     */
    public T getDenominator() {
        return denominator;
    }

    /**
     * @param denominator new denominator of fraction
     */
    public void setDenominator(T denominator) {
        if (denominator == null)
            denominator = getArithmetic().getTArithmetic().fromInt(1);
        if (denominator.equals(getArithmetic().getTArithmetic().fromInt(0)))
            throw new IllegalArgumentException(EXCEPTION_DENOMINATOR_NOT_ZERO);
        this.denominator = denominator;
    }

    /**
     * @return current {@link AbstractResultArithmetic}
     */
    protected AbstractResultArithmetic<T, V> getArithmetic() {
        return arithmetic;
    }

    // endregion

    // region value

    /**
     * @return calculated value
     */
    public V value() {
        return getArithmetic().quotient(getNumerator(), getDenominator());
    }

    @Override
    public int intValue() {
        return value().intValue();
    }

    @Override
    public long longValue() {
        return value().longValue();
    }

    @Override
    public float floatValue() {
        return value().floatValue();
    }

    @Override
    public double doubleValue() {
        return value().doubleValue();
    }

    // endregion

    // region simplify

    /**
     * both numbers are divided through {@link AbstractArithmetic#gcd(Number, Number)}
     *
     * @return simplified {@link Fraction} or {@link #copy()}
     */
    public Fraction<T, V> simplify() {
        AbstractArithmetic<T> tArithmetic = getArithmetic().getTArithmetic();
        T gcd;
        try {
            gcd = tArithmetic.gcd(getNumerator(), getDenominator());
        } catch (AbstractArithmetic.NotImplementedException e) {
            return copy();
        }
        T newNumerator = tArithmetic.quotient(getNumerator(), gcd);
        T newDenominator = tArithmetic.quotient(getDenominator(), gcd);
        return new Fraction<>(getArithmetic(), newNumerator, newDenominator);
    }

    // endregion

    // region copy

    @Override
    public Fraction<T, V> copy() {
        return new Fraction<>(this);
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fraction)) return false;
        Fraction<?, ?> fraction = (Fraction<?, ?>) o;
        return Objects.equals(getNumerator(), fraction.getNumerator()) &&
            Objects.equals(getDenominator(), fraction.getDenominator());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumerator(), getDenominator());
    }

    @Override
    public String toString() {
        return getNumerator() + "/" + getDenominator();
    }

    @Override
    public int compareTo(Fraction<T, V> o) {
        return getArithmetic().getRArithmetic().compare(value(), o.value());
    }

// endregion
}
