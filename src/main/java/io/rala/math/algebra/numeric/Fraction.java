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
        setNumerator(numerator);
        setDenominator(denominator);

        if (getNumerator().equals(arithmetic.getTArithmetic().zero()) &&
            !getDenominator().equals(arithmetic.getTArithmetic().one()))
            setDenominator(arithmetic.getTArithmetic().one());
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
            denominator = getArithmetic().getTArithmetic().one();
        if (denominator.equals(getArithmetic().getTArithmetic().zero()))
            throw new IllegalArgumentException(EXCEPTION_DENOMINATOR_NOT_ZERO);
        this.denominator = denominator;

        simplifySignum();
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

    // region negate, inverse and simplify

    /**
     * @return new fraction with negated {@link #getNumerator()}
     */
    public Fraction<T, V> negate() {
        return new Fraction<>(
            getArithmetic(),
            getArithmetic().getTArithmetic().negate(getNumerator()),
            getDenominator()
        );
    }

    /**
     * @return new fraction with flipped <code>numerator</code> and <code>denominator</code>
     */
    public Fraction<T, V> inverse() {
        return new Fraction<>(getArithmetic(), getDenominator(), getNumerator());
    }

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

    // region add and subtract

    /**
     * calls {@link #add(Fraction)} with given values
     *
     * @param numerator   numerator value to add
     * @param denominator denominator value to add
     * @return new fraction with sum of current and given parameters
     * @see #add(Fraction)
     */
    public Fraction<T, V> add(T numerator, T denominator) {
        return add(new Fraction<>(getArithmetic(), numerator, denominator));
    }

    /**
     * @param fraction fraction to add
     * @return new fraction with sum of current and given fraction
     */
    public Fraction<T, V> add(Fraction<T, V> fraction) {
        AbstractArithmetic<T> tArithmetic = getArithmetic().getTArithmetic();
        T lcm;
        try {
            lcm = tArithmetic.lcm(getDenominator(), fraction.getDenominator());
        } catch (AbstractArithmetic.NotImplementedException e) {
            lcm = tArithmetic.product(getDenominator(), fraction.getDenominator());
        }
        T t1 = tArithmetic.product(getNumerator(),
            tArithmetic.quotient(lcm, getDenominator())
        );
        T t2 = tArithmetic.product(fraction.getNumerator(),
            tArithmetic.quotient(lcm, fraction.getDenominator())
        );
        T sum = tArithmetic.sum(t1, t2);
        return new Fraction<>(getArithmetic(), sum, lcm);
    }

    /**
     * calls {@link #subtract(Fraction)} with given values
     *
     * @param numerator   numerator value to subtract
     * @param denominator denominator value to subtract
     * @return new fraction with difference of current and given parameters
     * @see #subtract(Fraction)
     */
    public Fraction<T, V> subtract(T numerator, T denominator) {
        return subtract(new Fraction<>(getArithmetic(), numerator, getDenominator()));
    }

    /**
     * @param fraction fraction to subtract
     * @return new fraction with difference of current and given fraction
     */
    public Fraction<T, V> subtract(Fraction<T, V> fraction) {
        return add(fraction.negate());
    }

    // endregion

    // region multiply and divide

    /**
     * calls {@link #multiply(Fraction)} with given value
     *
     * @param numerator numerator value to multiply
     * @return new fraction with product of current and given parameter
     * @see #multiply(Fraction)
     */
    public Fraction<T, V> multiply(T numerator) {
        return multiply(new Fraction<>(getArithmetic(), numerator));
    }

    /**
     * calls {@link #multiply(Fraction)} with given values
     *
     * @param numerator   numerator value to multiply
     * @param denominator denominator value to multiply
     * @return new fraction with product of current and given parameters
     * @see #multiply(Fraction)
     */
    public Fraction<T, V> multiply(T numerator, T denominator) {
        return multiply(new Fraction<>(getArithmetic(), numerator, denominator));
    }

    /**
     * @param fraction fraction to multiply
     * @return new fraction with product of current and given fraction
     */
    public Fraction<T, V> multiply(Fraction<T, V> fraction) {
        AbstractArithmetic<T> tArithmetic = getArithmetic().getTArithmetic();
        T newNumerator = tArithmetic.product(
            getNumerator(), fraction.getNumerator()
        );
        T newDenominator = tArithmetic.product(
            getDenominator(), fraction.getDenominator()
        );
        return new Fraction<>(getArithmetic(), newNumerator, newDenominator);
    }

    /**
     * calls {@link #divide(Fraction)} with given value
     *
     * @param numerator numerator value to multiply
     * @return new fraction with quotient of current and given parameter
     * @see #divide(Fraction)
     */
    public Fraction<T, V> divide(T numerator) {
        return divide(new Fraction<>(getArithmetic(), numerator));
    }

    /**
     * calls {@link #divide(Fraction)} with given values
     *
     * @param numerator   numerator value to multiply
     * @param denominator denominator value to multiply
     * @return new fraction with quotient of current and given parameters
     * @see #divide(Fraction)
     */
    public Fraction<T, V> divide(T numerator, T denominator) {
        return divide(new Fraction<>(getArithmetic(), numerator, denominator));
    }

    /**
     * @param fraction fraction to divide
     * @return new fraction with quotient of current and given fraction
     */
    public Fraction<T, V> divide(Fraction<T, V> fraction) {
        return multiply(fraction.inverse());
    }

    // endregion

    // region pow and root

    /**
     * @param n number of power
     * @return <code>pow(numerator,n)/pow(denominator,n)</code>
     */
    public Fraction<T, V> pow(int n) {
        AbstractArithmetic<T> tArithmetic = getArithmetic().getTArithmetic();
        T newNumerator = tArithmetic.power(getNumerator(), n);
        T newDenominator = tArithmetic.power(getDenominator(), n);
        return new Fraction<>(getArithmetic(), newNumerator, newDenominator);
    }

    /**
     * @param n degree of root
     * @return <code>root(numerator,n)/root(denominator,n)</code>
     */
    public Fraction<T, V> root(int n) {
        AbstractArithmetic<T> tArithmetic = getArithmetic().getTArithmetic();
        T newNumerator = tArithmetic.root(getNumerator(), n);
        T newDenominator = tArithmetic.root(getDenominator(), n);
        return new Fraction<>(getArithmetic(), newNumerator, newDenominator);
    }

    // endregion

    // region static of

    /**
     * @param arithmetic arithmetic of calculations
     * @param numerator  numerator of fraction
     * @param <T>        number class of fraction elements
     * @return new fraction based on arguments
     * @see #of(AbstractArithmetic, Number, Number)
     * @see AbstractArithmetic#toResultArithmetic()
     */
    public static <T extends Number> Fraction<T, T> of(
        AbstractArithmetic<T> arithmetic, T numerator
    ) {
        return new Fraction<>(arithmetic.toResultArithmetic(), numerator);
    }

    /**
     * @param arithmetic  arithmetic of calculations
     * @param numerator   numerator of fraction
     * @param denominator denominator of fraction
     * @param <T>         number class of fraction elements
     * @return new fraction based on arguments
     * @see #of(AbstractArithmetic, Number)
     * @see AbstractArithmetic#toResultArithmetic()
     */
    public static <T extends Number> Fraction<T, T> of(
        AbstractArithmetic<T> arithmetic, T numerator, T denominator
    ) {
        return new Fraction<>(arithmetic.toResultArithmetic(), numerator, denominator);
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

    // region protected

    /**
     * ensures that if there is a signum it is on {@link #getNumerator()}
     */
    protected void simplifySignum() {
        if (getArithmetic().getTArithmetic().signum(denominator) < 0) {
            denominator = arithmetic.getTArithmetic().negate(denominator);
            numerator = arithmetic.getTArithmetic().negate(numerator);
        }
    }

    // endregion
}
