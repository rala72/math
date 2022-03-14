package io.rala.math.algebra.numeric;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.exception.NotSupportedException;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.Validatable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

/**
 * class which holds a numerator and denominator
 *
 * @param <T> number class of fraction elements
 * @param <V> number class of value
 * @since 1.0.0
 */
public class Fraction<T extends Number, V extends Number> extends Number
    implements Validatable, Copyable<Fraction<T, V>>, Comparable<Fraction<T, V>> {
    protected static final String EXCEPTION_DENOMINATOR_IS_ZERO =
        "denominator has to be non-zero";

    // region attributes

    private final AbstractResultArithmetic<T, V> arithmetic;
    private T numerator;
    private T denominator;

    // endregion

    // region constructors

    /**
     * calls {@link #Fraction(AbstractResultArithmetic, Number, Number)}
     * with given numerator and {@code null}
     *
     * @param arithmetic arithmetic for calculations
     * @param numerator  numerator of fraction
     * @since 1.0.0
     */
    public Fraction(@NotNull AbstractResultArithmetic<T, V> arithmetic, @NotNull T numerator) {
        this(arithmetic, numerator, null);
    }

    /**
     * creates a new fraction with numerator and denominator<br>
     * denominator may be {@code null} - in this case {@code 1} is used
     *
     * @param arithmetic  arithmetic for calculations
     * @param numerator   numerator of fraction
     * @param denominator denominator of fraction
     * @since 1.0.0
     */
    public Fraction(
        @NotNull AbstractResultArithmetic<T, V> arithmetic,
        @NotNull T numerator, @Nullable T denominator
    ) {
        this.arithmetic = arithmetic;
        setNumerator(numerator);
        setDenominator(denominator);

        if (arithmetic.getTArithmetic().isZero(getNumerator()) &&
            !arithmetic.getTArithmetic().one().equals(getDenominator()))
            setDenominator(arithmetic.getTArithmetic().one());
    }

    /**
     * creates a new fraction based on given one
     *
     * @param fraction fraction to copy
     * @since 1.0.0
     */
    protected Fraction(@NotNull Fraction<T, V> fraction) {
        this(fraction.getArithmetic(),
            fraction.getNumerator(), fraction.getDenominator()
        );
    }

    // endregion

    // region getter and setter

    /**
     * @return stored {@link AbstractResultArithmetic}
     * @since 1.0.0
     */
    @NotNull
    public AbstractResultArithmetic<T, V> getArithmetic() {
        return arithmetic;
    }

    /**
     * @return numerator of fraction
     * @since 1.0.0
     */
    @NotNull
    public T getNumerator() {
        return numerator;
    }

    /**
     * @param numerator new numerator of fraction
     * @since 1.0.0
     */
    public void setNumerator(@NotNull T numerator) {
        this.numerator = numerator;
    }

    /**
     * @return denominator of fraction
     * @since 1.0.0
     */
    @NotNull
    public T getDenominator() {
        return denominator;
    }

    /**
     * @param denominator new denominator of fraction
     * @throws IllegalArgumentException if denominator is {@code 0}
     * @since 1.0.0
     */
    public void setDenominator(@Nullable T denominator) {
        if (denominator == null)
            denominator = getArithmetic().getTArithmetic().one();
        if (getArithmetic().getTArithmetic().isZero(denominator))
            throw new IllegalArgumentException(EXCEPTION_DENOMINATOR_IS_ZERO);
        this.denominator = denominator;

        simplifySignum();
    }

    // endregion

    // region value

    /**
     * @return calculated value
     * @since 1.0.0
     */
    @NotNull
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
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> negate() {
        return createFromArithmetic(
            getArithmetic().getTArithmetic().negate(getNumerator()),
            getDenominator()
        );
    }

    /**
     * @return new fraction with flipped {@code numerator} and {@code denominator}
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> inverse() {
        return createFromArithmetic(getDenominator(), getNumerator());
    }

    /**
     * both numbers are divided through {@link AbstractArithmetic#gcd(Number, Number)}
     *
     * @return simplified {@link Fraction} or {@link #copy()}
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> simplify() {
        AbstractArithmetic<T> tArithmetic = getArithmetic().getTArithmetic();
        T gcd;
        try {
            gcd = tArithmetic.gcd(getNumerator(), getDenominator());
        } catch (NotSupportedException e) {
            return copy();
        }
        T newNumerator = tArithmetic.quotient(getNumerator(), gcd);
        T newDenominator = tArithmetic.quotient(getDenominator(), gcd);
        return createFromArithmetic(newNumerator, newDenominator);
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
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> add(@NotNull T numerator, @Nullable T denominator) {
        return add(new Fraction<>(getArithmetic(), numerator, denominator));
    }

    /**
     * @param fraction fraction to add
     * @return new fraction with sum of current and given fraction
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> add(@NotNull Fraction<T, V> fraction) {
        AbstractArithmetic<T> tArithmetic = getArithmetic().getTArithmetic();
        T lcm;
        try {
            lcm = tArithmetic.lcm(getDenominator(), fraction.getDenominator());
        } catch (NotSupportedException e) {
            lcm = tArithmetic.product(getDenominator(), fraction.getDenominator());
        }
        T t1 = tArithmetic.product(getNumerator(),
            tArithmetic.quotient(lcm, getDenominator())
        );
        T t2 = tArithmetic.product(fraction.getNumerator(),
            tArithmetic.quotient(lcm, fraction.getDenominator())
        );
        T sum = tArithmetic.sum(t1, t2);
        return createFromArithmetic(sum, lcm);
    }

    /**
     * calls {@link #subtract(Fraction)} with given values
     *
     * @param numerator   numerator value to subtract
     * @param denominator denominator value to subtract
     * @return new fraction with difference of current and given parameters
     * @see #subtract(Fraction)
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> subtract(@NotNull T numerator, @Nullable T denominator) {
        return subtract(new Fraction<>(getArithmetic(), numerator, denominator));
    }

    /**
     * @param fraction fraction to subtract
     * @return new fraction with difference of current and given fraction
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> subtract(@NotNull Fraction<T, V> fraction) {
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
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> multiply(@NotNull T numerator) {
        return multiply(new Fraction<>(getArithmetic(), numerator));
    }

    /**
     * calls {@link #multiply(Fraction)} with given values
     *
     * @param numerator   numerator value to multiply
     * @param denominator denominator value to multiply
     * @return new fraction with product of current and given parameters
     * @see #multiply(Fraction)
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> multiply(@NotNull T numerator, @Nullable T denominator) {
        return multiply(new Fraction<>(getArithmetic(), numerator, denominator));
    }

    /**
     * @param fraction fraction to multiply
     * @return new fraction with product of current and given fraction
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> multiply(@NotNull Fraction<T, V> fraction) {
        AbstractArithmetic<T> tArithmetic = getArithmetic().getTArithmetic();
        T newNumerator = tArithmetic.product(
            getNumerator(), fraction.getNumerator()
        );
        T newDenominator = tArithmetic.product(
            getDenominator(), fraction.getDenominator()
        );
        return createFromArithmetic(newNumerator, newDenominator);
    }

    /**
     * calls {@link #divide(Fraction)} with given value
     *
     * @param numerator numerator value to multiply
     * @return new fraction with quotient of current and given parameter
     * @see #divide(Fraction)
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> divide(@NotNull T numerator) {
        return divide(new Fraction<>(getArithmetic(), numerator));
    }

    /**
     * calls {@link #divide(Fraction)} with given values
     *
     * @param numerator   numerator value to multiply
     * @param denominator denominator value to multiply
     * @return new fraction with quotient of current and given parameters
     * @see #divide(Fraction)
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> divide(@NotNull T numerator, @Nullable T denominator) {
        return divide(new Fraction<>(getArithmetic(), numerator, denominator));
    }

    /**
     * @param fraction fraction to divide
     * @return new fraction with quotient of current and given fraction
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> divide(@NotNull Fraction<T, V> fraction) {
        return multiply(fraction.inverse());
    }

    // endregion

    // region pow and root

    /**
     * @param n number of power
     * @return {@code pow(numerator,n)/pow(denominator,n)}
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> pow(int n) {
        AbstractArithmetic<T> tArithmetic = getArithmetic().getTArithmetic();
        T newNumerator = tArithmetic.power(getNumerator(), n);
        T newDenominator = tArithmetic.power(getDenominator(), n);
        return createFromArithmetic(newNumerator, newDenominator);
    }

    /**
     * @param n degree of root
     * @return {@code root(numerator,n)/root(denominator,n)}
     * @since 1.0.0
     */
    @NotNull
    public Fraction<T, V> root(int n) {
        AbstractArithmetic<T> tArithmetic = getArithmetic().getTArithmetic();
        T newNumerator = tArithmetic.root(getNumerator(), n);
        T newDenominator = tArithmetic.root(getDenominator(), n);
        return createFromArithmetic(newNumerator, newDenominator);
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
     * @since 1.0.0
     */
    @NotNull
    public static <T extends Number> Fraction<T, T> of(
        @NotNull AbstractArithmetic<T> arithmetic, @NotNull T numerator
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
     * @since 1.0.0
     */
    @NotNull
    public static <T extends Number> Fraction<T, T> of(
        @NotNull AbstractArithmetic<T> arithmetic,
        @NotNull T numerator, @Nullable T denominator
    ) {
        return new Fraction<>(arithmetic.toResultArithmetic(), numerator, denominator);
    }

    // endregion

    // region map, isValid and copy

    /**
     * @param arithmetic arithmetic for calculations
     * @param map        mapping function to convert current values to new one
     * @param <NT>       new number class
     * @param <NV>       new value class
     * @return mapped fraction
     * @since 1.0.0
     */
    @NotNull
    public <NT extends Number, NV extends Number> Fraction<NT, NV> map(
        @NotNull AbstractResultArithmetic<NT, NV> arithmetic, @NotNull Function<T, NT> map
    ) {
        return new Fraction<>(
            arithmetic,
            map.apply(getNumerator()),
            map.apply(getDenominator())
        );
    }

    /**
     * @param arithmetic arithmetic for calculations
     * @param mapTR      mapping function to convert current values to new one
     * @param mapRV      mapping function to convert current values to new one
     * @param <NT>       new number class
     * @return mapped fraction
     * @see #mapValues(AbstractResultArithmetic, Function)
     * @see AbstractResultArithmetic#map(AbstractArithmetic, Function)
     * @since 1.0.0
     */
    @NotNull
    public <NT extends Number> Fraction<NT, V> mapValues(
        @NotNull AbstractArithmetic<NT> arithmetic,
        @NotNull Function<T, NT> mapTR, @NotNull Function<NT, V> mapRV
    ) {
        return mapValues(getArithmetic().map(arithmetic, mapRV), mapTR);
    }

    /**
     * @param arithmetic arithmetic for calculations
     * @param map        mapping function to convert current values to new one
     * @param <NT>       new number class
     * @return mapped fraction
     * @see #map(AbstractResultArithmetic, Function)
     * @since 1.0.0
     */
    @NotNull
    public <NT extends Number> Fraction<NT, V> mapValues(
        @NotNull AbstractResultArithmetic<NT, V> arithmetic, @NotNull Function<T, NT> map
    ) {
        return map(arithmetic, map);
    }

    /**
     * @param arithmetic arithmetic for calculations
     * @param map        mapping function to convert current values to new one
     * @param <NV>       new number class
     * @return mapped fraction
     * @see AbstractResultArithmetic#mapResult(AbstractArithmetic, Function)
     * @since 1.0.0
     */
    @NotNull
    public <NV extends Number> Fraction<T, NV> mapValue(
        @NotNull AbstractArithmetic<NV> arithmetic, @NotNull Function<T, NV> map
    ) {
        return new Fraction<>(
            getArithmetic().mapResult(arithmetic, map),
            getNumerator(), getDenominator()
        );
    }

    @Override
    public boolean isValid() {
        AbstractArithmetic<T> arithmetic = getArithmetic().getTArithmetic();
        return arithmetic.isFinite(getNumerator()) && arithmetic.isFinite(getDenominator());
    }

    @Override
    @NotNull
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
    @NotNull
    public String toString() {
        return getNumerator() + "/" + getDenominator();
    }

    @Override
    public int compareTo(@NotNull Fraction<T, V> o) {
        return getArithmetic().getRArithmetic().compare(value(), o.value());
    }

    // endregion

    // region protected

    /**
     * @param numerator   numerator of fraction
     * @param denominator denominator of fraction
     * @return new fraction
     * @throws ArithmeticException      if denominator is {@code 0}
     * @throws IllegalArgumentException if constructor throws one
     * @since 1.0.0
     */
    @NotNull
    protected final Fraction<T, V> createFromArithmetic(@NotNull T numerator, @Nullable T denominator) {
        try {
            return new Fraction<>(getArithmetic(), numerator, denominator);
        } catch (IllegalArgumentException e) {
            throw new ArithmeticException(e.getMessage());
        }
    }

    /**
     * ensures that if there is a signum it is on {@link #getNumerator()}
     *
     * @since 1.0.0
     */
    protected void simplifySignum() {
        if (getArithmetic().getTArithmetic().signum(getDenominator()) < 0) {
            setDenominator(getArithmetic().getTArithmetic().negate(getDenominator()));
            setNumerator(getArithmetic().getTArithmetic().negate(getNumerator()));
        }
    }

    // endregion
}
