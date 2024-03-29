package io.rala.math.arithmetic;

import io.rala.math.exception.NotSupportedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.StreamSupport;

/**
 * class which defines required arithmetic for calculations
 *
 * @param <T> number class of arithmetic
 * @since 1.0.0
 */
public abstract class AbstractArithmetic<T extends Number> implements Serializable {
    /**
     * creates a new {@link AbstractArithmetic}
     *
     * @since 1.0.0
     */
    protected AbstractArithmetic() {
    }

    // region fromInt, fromDouble and signum

    /**
     * @param a value from integer
     * @return number as {@code T}
     * @since 1.0.0
     */
    @NotNull
    public abstract T fromInt(int a);

    /**
     * @param a value from double
     * @return number as {@code T}
     * @since 1.0.0
     */
    @NotNull
    public abstract T fromDouble(double a);

    /**
     * @param a value to get signum
     * @return {@code -1} if negative, {@code 0} if zero or {@code 1} if positive
     * @throws NotSupportedException if operation is not supported
     * @since 1.0.0
     */
    public abstract double signum(@NotNull T a);

    // endregion

    // region number constants

    /**
     * <i>in general the additive identity element</i>
     *
     * @return {@code 0}
     * @see #fromInt(int)
     * @since 1.0.0
     */
    @NotNull
    public final T zero() {
        return fromInt(0);
    }

    /**
     * <i>in general the multiplicative identity element</i>
     *
     * @return {@code 1}
     * @see #fromInt(int)
     * @since 1.0.0
     */
    @NotNull
    public final T one() {
        return fromInt(1);
    }

    // endregion

    // region absolute, negate and compare

    /**
     * @param a value to make absolute
     * @return absolute value of a
     * @throws NotSupportedException if operation is not supported
     * @since 1.0.0
     */
    @NotNull
    public T absolute(@NotNull T a) {
        return signum(a) < 0 || Double.valueOf(signum(a)).equals(-0d) ? negate(a) : a;
    }

    /**
     * @param a value to negate
     * @return {@code -a}
     * @throws NotSupportedException if operation is not supported
     * @since 1.0.0
     */
    @NotNull
    public T negate(@NotNull T a) {
        return product(a, fromInt(-1));
    }

    /**
     * @param a first value of comparison
     * @param b second value of comparison
     * @return {@link Comparable#compareTo(Object)}
     * @throws NotSupportedException if operation is not supported
     * @implSpec default implementation uses {@link Double#compare(double, double)}
     * @see Comparable#compareTo(Object)
     * @since 1.0.0
     */
    public int compare(@NotNull T a, @NotNull T b) {
        return Double.compare(a.doubleValue(), b.doubleValue());
    }

    /**
     * @param a first value of comparison
     * @param b second value of comparison
     * @return min value
     * @implSpec {@link #compare(Number, Number)} {@code <= 0 ? a : b}
     * @since 1.0.0
     */
    @NotNull
    public T min(@NotNull T a, @NotNull T b) {
        return compare(a, b) <= 0 ? a : b;
    }

    /**
     * @param a first value of comparison
     * @param b second value of comparison
     * @return max value
     * @implSpec {@link #compare(Number, Number)} {@code < 0 ? b : a}
     * @since 1.0.0
     */
    @NotNull
    public T max(@NotNull T a, @NotNull T b) {
        return compare(a, b) < 0 ? b : a;
    }

    /**
     * @param a number to check
     * @return {@code true} if {@code abs(a)} is {@code 0}
     * @see #absolute(Number)
     * @since 1.0.0
     */
    public boolean isZero(@NotNull T a) {
        return zero().equals(absolute(a));
    }

    /**
     * @param a first value of comparison
     * @param b second value of comparison
     * @return {@code true} if both values are equal
     * @since 1.0.0
     */
    public boolean isEqual(@Nullable T a, @Nullable T b) {
        return a == null && b == null ||
            a != null && a.equals(b) ||
            a != null && b != null &&
                isZero(a) && isZero(b);
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    /**
     * @param a first value of sum
     * @param b second value of sum
     * @return {@code a+b}
     * @since 1.0.0
     */
    @NotNull
    public abstract T sum(@NotNull T a, @NotNull T b);

    /**
     * @param a first value of sum
     * @param b second value of sum
     * @param c third value of sum
     * @return {@code a+b+c}
     * @implSpec default implementation uses {@link #sum(Number, Number)} twice
     * @see #sum(Number, Number)
     * @since 1.0.0
     */
    @NotNull
    public T sum(@NotNull T a, @NotNull T b, @NotNull T c) {
        return sum(sum(a, b), c);
    }

    /**
     * @param iterable iterable to sum
     * @return sum or {@link #zero()} if empty
     * @implSpec default implementation iterates over all elements
     * and starts with {@code 0}
     * @since 1.0.0
     */
    @NotNull
    public T sum(@NotNull Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
            .reduce(zero(), this::sum);
    }

    /**
     * @param a first value of difference
     * @param b second value of difference
     * @return {@code a-b}
     * @since 1.0.0
     */
    @NotNull
    public abstract T difference(@NotNull T a, @NotNull T b);

    /**
     * @param a first value of product
     * @param b second value of product
     * @return {@code a*b}
     * @since 1.0.0
     */
    @NotNull
    public abstract T product(@NotNull T a, @NotNull T b);

    /**
     * @param a first value of product
     * @param b second value of product
     * @param c third value of product
     * @return {@code a*b*c}
     * @implSpec default implementation uses {@link #product(Number, Number)} twice
     * @see #product(Number, Number)
     * @since 1.0.0
     */
    @NotNull
    public T product(@NotNull T a, @NotNull T b, @NotNull T c) {
        return product(product(a, b), c);
    }

    /**
     * @param iterable iterable to multiply
     * @return product or {@link #one()} if empty
     * @implSpec default implementation iterates over all elements
     * and starts with {@code 1}
     * @since 1.0.0
     */
    @NotNull
    public T product(@NotNull Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
            .reduce(one(), this::product);
    }

    /**
     * @param a first value of quotient
     * @param b second value of quotient
     * @return {@code a/b}
     * @since 1.0.0
     */
    @NotNull
    public abstract T quotient(@NotNull T a, @NotNull T b);

    /**
     * @param a first value of quotient
     * @param b second value of quotient
     * @return reminder of division like {@code r=a-q*b}
     * @throws NotSupportedException if operation is not supported
     * @since 1.0.0
     */
    @NotNull
    public T modulo(@NotNull T a, @NotNull T b) {
        T quotient = quotient(a, b);
        T product = product(quotient, b);
        return difference(a, product);
    }

    // endregion

    // region power and root

    /**
     * @param a basis of power
     * @param b exponent of power
     * @return {@code a^b}
     * @throws NotSupportedException if operation is not supported
     * @since 1.0.0
     */
    @NotNull
    public abstract T power(@NotNull T a, int b);

    /**
     * @param a value
     * @param b degree of root
     * @return {@code root(a, b)}
     * @throws NotSupportedException if operation is not supported
     * @since 1.0.0
     */
    @NotNull
    public abstract T root(@NotNull T a, int b);

    /**
     * @param a value
     * @return {@code sqrt(a)}
     * @throws NotSupportedException if operation is not supported
     * @see #root(Number, int)
     * @since 1.0.0
     */
    @NotNull
    public T root2(@NotNull T a) {
        return root(a, 2);
    }

    // endregion

    // region isFinite, isInfinite and isNaN

    /**
     * @param a value to check
     * @return {@code true} if value is finite
     * @implSpec default implementation uses {@link Double#isInfinite(double)}
     * @since 1.0.0
     */
    public boolean isFinite(@NotNull T a) {
        return Double.isFinite(a.doubleValue());
    }

    /**
     * @param a value to check
     * @return {@code true} if value is infinite
     * @implSpec default implementation uses {@link Double#isInfinite(double)}
     * @since 1.0.0
     */
    public boolean isInfinite(@NotNull T a) {
        return Double.isInfinite(a.doubleValue());
    }

    /**
     * @param a value to check
     * @return {@code true} if value is NaN
     * @implSpec default implementation uses {@link Double#isNaN(double)}
     * @since 1.0.0
     */
    public boolean isNaN(@NotNull T a) {
        return Double.isNaN(a.doubleValue());
    }

    // endregion

    // region gcd and lcm

    /**
     * @param a first value
     * @param b second value
     * @return greatest common divisor
     * @throws NotSupportedException if operation is not supported
     * @since 1.0.0
     */
    @NotNull
    public abstract T gcd(@NotNull T a, @NotNull T b);

    /**
     * @param a first value
     * @param b second value
     * @return least common multiple
     * @throws NotSupportedException if operation is not supported
     * @implSpec default implementation uses {@code abs(a*b)/gcd(a,b)}
     * @since 1.0.0
     */
    @NotNull
    public T lcm(@NotNull T a, @NotNull T b) {
        return quotient(absolute(product(a, b)), gcd(a, b));
    }

    // endregion

    // region trigonometry

    /**
     * @param a value to calc sin from
     * @return {@code sin(a)}
     * @throws NotSupportedException if operation is not supported
     * @implSpec default implementation uses {@link Math#sin(double)}
     * @since 1.0.0
     */
    @NotNull
    public T sin(@NotNull T a) {
        return fromDouble(Math.sin(a.doubleValue()));
    }

    /**
     * @param a value to calc cos from
     * @return {@code cos(a)}
     * @throws NotSupportedException if operation is not supported
     * @implSpec default implementation uses {@link Math#cos(double)}
     * @since 1.0.0
     */
    @NotNull
    public T cos(@NotNull T a) {
        return fromDouble(Math.cos(a.doubleValue()));
    }

    /**
     * @param a value to calc tan from
     * @return {@code tan(a)}
     * @throws NotSupportedException if operation is not supported
     * @implSpec default implementation uses {@link Math#tan(double)}
     * @since 1.0.0
     */
    @NotNull
    public T tan(@NotNull T a) {
        return fromDouble(Math.tan(a.doubleValue()));
    }

    /**
     * @param a value to calc asin from
     * @return {@code asin(a)}
     * @throws NotSupportedException if operation is not supported
     * @implSpec default implementation uses {@link Math#asin(double)}
     * @since 1.0.0
     */
    @NotNull
    public T asin(@NotNull T a) {
        return fromDouble(Math.asin(a.doubleValue()));
    }

    /**
     * @param a value to calc acos from
     * @return {@code acos(a)}
     * @throws NotSupportedException if operation is not supported
     * @implSpec default implementation uses {@link Math#acos(double)}
     * @since 1.0.0
     */
    @NotNull
    public T acos(@NotNull T a) {
        return fromDouble(Math.acos(a.doubleValue()));
    }

    /**
     * @param a value to calc atan from
     * @return {@code atan(a)}
     * @throws NotSupportedException if operation is not supported
     * @implSpec default implementation uses {@link Math#atan(double)}
     * @since 1.0.0
     */
    @NotNull
    public T atan(@NotNull T a) {
        return fromDouble(Math.atan(a.doubleValue()));
    }

    /**
     * @param a value to calc sin from
     * @return {@code sinh(a)}
     * @throws NotSupportedException if operation is not supported
     * @implSpec default implementation uses {@link Math#sinh(double)}
     * @since 1.0.0
     */
    @NotNull
    public T sinh(@NotNull T a) {
        return fromDouble(Math.sinh(a.doubleValue()));
    }

    /**
     * @param a value to calc cos from
     * @return {@code cosh(a)}
     * @throws NotSupportedException if operation is not supported
     * @implSpec default implementation uses {@link Math#cosh(double)}
     * @since 1.0.0
     */
    @NotNull
    public T cosh(@NotNull T a) {
        return fromDouble(Math.cosh(a.doubleValue()));
    }

    /**
     * @param a value to calc tan from
     * @return {@code tanh(a)}
     * @throws NotSupportedException if operation is not supported
     * @implSpec default implementation uses {@link Math#tanh(double)}
     * @since 1.0.0
     */
    @NotNull
    public T tanh(@NotNull T a) {
        return fromDouble(Math.tanh(a.doubleValue()));
    }

    // endregion

    // region toResultArithmetic

    /**
     * @return {@link AbstractResultArithmetic} with current arithmetic
     * @see AbstractResultArithmetic#of(AbstractArithmetic, AbstractArithmetic, Function)
     * @since 1.0.0
     */
    @NotNull
    public AbstractResultArithmetic<T, T> toResultArithmetic() {
        return AbstractResultArithmetic.of(this, this, t -> t);
    }

    /**
     * @param arithmetic arithmetic for target
     * @param map        mapping function to convert {@code T} to {@code R}
     * @param <R>        number class of target
     * @return {@link AbstractResultArithmetic} wich returns {@code V}
     * @see AbstractResultArithmetic#of(AbstractArithmetic, AbstractArithmetic, Function)
     * @see #toResultArithmetic()
     * @see AbstractResultArithmetic#mapResult(AbstractArithmetic, Function)
     * @since 1.0.0
     */
    @NotNull
    public <R extends Number> AbstractResultArithmetic<T, R> toResultArithmetic(
        @NotNull AbstractArithmetic<R> arithmetic, @NotNull Function<T, R> map
    ) {
        return AbstractResultArithmetic.of(this, arithmetic, map);
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof AbstractArithmetic<?> &&
            getClass().equals(o.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(zero(), one());
    }

    @Override
    @NotNull
    public String toString() {
        return getClass().getSimpleName();
    }

    // endregion
}
