package io.rala.math.arithmetic;

/**
 * class which defines required arithmetic for calculations
 *
 * @param <T> number class of arithmetic
 */
public abstract class AbstractArithmetic<T extends Number> {
    // region fromInt, fromDouble and signum

    /**
     * @param a value from integer
     * @return number as <code>T</code>
     * @throws NotImplementedException if operation is not implemented
     */
    public abstract T fromInt(int a);

    /**
     * @param a value from double
     * @return number as <code>T</code>
     * @throws NotImplementedException if operation is not implemented
     */
    public abstract T fromDouble(double a);

    /**
     * @param a value to get signum
     * @return -1 if negative, 0 if zero or 1 if positive
     * @throws NotImplementedException if operation is not implemented
     */
    public abstract double signum(T a);

    // endregion

    // region number constants

    /**
     * <i>in general the additive identity element</i>
     *
     * @return <code>0</code>
     * @see #fromInt(int)
     */
    public final T zero() {
        return fromInt(0);
    }

    /**
     * <i>in general the multiplicative identity element</i>
     *
     * @return <code>1</code>
     * @see #fromInt(int)
     */
    public final T one() {
        return fromInt(1);
    }

    // endregion

    // region absolute, negate and compare

    /**
     * @param a value to make absolute
     * @return absolute value of a
     * @throws NotImplementedException if operation is not implemented
     */
    public T absolute(T a) {
        return signum(a) < 0 ? negate(a) : a;
    }

    /**
     * @param a value to negate
     * @return <code>-a</code>
     * @throws NotImplementedException if operation is not implemented
     */
    public T negate(T a) {
        return product(a, fromInt(-1));
    }

    /**
     * @param a first value of comparison
     * @param b second value of comparison
     * @return {@link Comparable#compareTo(Object)}
     * @throws NotImplementedException if operation is not implemented
     * @see Comparable#compareTo(Object)
     */
    public int compare(T a, T b) {
        return Double.compare(a.doubleValue(), b.doubleValue());
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    /**
     * @param a first value of sum
     * @param b second value of sum
     * @return <code>a+b</code>
     * @throws NotImplementedException if operation is not implemented
     */
    public abstract T sum(T a, T b);

    /**
     * @param a first value of sum
     * @param b second value of sum
     * @param c third value of sum
     * @return <code>a+b+c</code>
     * @throws NotImplementedException if operation is not implemented
     * @see #sum(Number, Number)
     */
    public T sum(T a, T b, T c) {
        return sum(sum(a, b), c);
    }

    /**
     * @param a first value of difference
     * @param b second value of difference
     * @return <code>a-b</code>
     * @throws NotImplementedException if operation is not implemented
     */
    public abstract T difference(T a, T b);

    /**
     * @param a first value of product
     * @param b second value of product
     * @return <code>a*b</code>
     * @throws NotImplementedException if operation is not implemented
     */
    public abstract T product(T a, T b);

    /**
     * @param a first value of product
     * @param b second value of product
     * @param c third value of product
     * @return <code>a*b*c</code>
     * @throws NotImplementedException if operation is not implemented
     * @see #product(Number, Number)
     */
    public T product(T a, T b, T c) {
        return product(product(a, b), c);
    }

    /**
     * @param a first value of quotient
     * @param b second value of quotient
     * @return <code>a/b</code>
     * @throws NotImplementedException if operation is not implemented
     */
    public abstract T quotient(T a, T b);

    /**
     * @param a first value of quotient
     * @param b second value of quotient
     * @return reminder of division like <code>r=a-q*b</code>
     * @throws NotImplementedException if operation is not implemented
     */
    public T modulo(T a, T b) {
        T quotient = quotient(a, b);
        T product = product(quotient, b);
        return difference(a, product);
    }

    // endregion

    // region power and root

    /**
     * @param a basis of power
     * @param b exponent of power
     * @return <code>a^b</code>
     * @throws NotImplementedException if operation is not implemented
     */
    public abstract T power(T a, int b);

    /**
     * @param a value
     * @param b degree of root
     * @return <code>sqrt(a)</code>
     * @throws NotImplementedException if operation is not implemented
     */
    public abstract T root(T a, int b);

    /**
     * @param a value
     * @return <code>sqrt(a)</code>
     * @throws NotImplementedException if operation is not implemented
     */
    public T root2(T a) {
        return root(a, 2);
    }

    // endregion

    // region isFinite, isInfinite and isNaN

    /**
     * @param a value to check
     * @return <code>true</code> if value is finite
     * @throws NotImplementedException if operation is not implemented
     * @see Double#isInfinite(double)
     */
    public boolean isFinite(T a) {
        return Double.isFinite(a.doubleValue());
    }

    /**
     * @param a value to check
     * @return <code>true</code> if value is infinite
     * @throws NotImplementedException if operation is not implemented
     * @see Double#isInfinite(double)
     */
    public boolean isInfinite(T a) {
        return Double.isInfinite(a.doubleValue());
    }

    /**
     * @param a value to check
     * @return <code>true</code> if value is NaN
     * @throws NotImplementedException if operation is not implemented
     * @see Double#isNaN(double)
     */
    public boolean isNaN(T a) {
        return Double.isNaN(a.doubleValue());
    }

    // endregion

    // region gcd and lcm

    /**
     * @param a first value
     * @param b second value
     * @return greatest common divisor
     * @throws NotImplementedException if operation is not implemented
     */
    public abstract T gcd(T a, T b);

    /**
     * @param a first value
     * @param b second value
     * @return least common multiple
     * @throws NotImplementedException if operation is not implemented
     */
    public T lcm(T a, T b) {
        return quotient(absolute(product(a, b)), gcd(a, b));
    }

    // endregion

    // region trigonometry

    /**
     * @param a value to calc sin from
     * @return <code>sin(a)</code>
     * @throws NotImplementedException if operation is not implemented
     * @see Math#sin(double)
     */
    public T sin(T a) {
        return fromDouble(Math.sin(a.doubleValue()));
    }

    /**
     * @param a value to calc cos from
     * @return <code>cos(a)</code>
     * @throws NotImplementedException if operation is not implemented
     * @see Math#cos(double)
     */
    public T cos(T a) {
        return fromDouble(Math.cos(a.doubleValue()));
    }

    /**
     * @param a value to calc tan from
     * @return <code>tan(a)</code>
     * @throws NotImplementedException if operation is not implemented
     * @see Math#tan(double)
     */
    public T tan(T a) {
        return fromDouble(Math.tan(a.doubleValue()));
    }

    /**
     * @param a value to calc asin from
     * @return <code>asin(a)</code>
     * @throws NotImplementedException if operation is not implemented
     * @see Math#asin(double)
     */
    public T asin(T a) {
        return fromDouble(Math.asin(a.doubleValue()));
    }

    /**
     * @param a value to calc acos from
     * @return <code>acos(a)</code>
     * @throws NotImplementedException if operation is not implemented
     * @see Math#acos(double)
     */
    public T acos(T a) {
        return fromDouble(Math.acos(a.doubleValue()));
    }

    /**
     * @param a value to calc acos from
     * @return <code>atan(a)</code>
     * @throws NotImplementedException if operation is not implemented
     * @see Math#atan(double)
     */
    public T atan(T a) {
        return fromDouble(Math.atan(a.doubleValue()));
    }


    /**
     * @param a value to calc sin from
     * @return <code>sinh(a)</code>
     * @throws NotImplementedException if operation is not implemented
     * @see Math#sinh(double)
     */
    public T sinh(T a) {
        return fromDouble(Math.sinh(a.doubleValue()));
    }

    /**
     * @param a value to calc cos from
     * @return <code>cosh(a)</code>
     * @throws NotImplementedException if operation is not implemented
     * @see Math#cosh(double)
     */
    public T cosh(T a) {
        return fromDouble(Math.cosh(a.doubleValue()));
    }

    /**
     * @param a value to calc tan from
     * @return <code>tanh(a)</code>
     * @throws NotImplementedException if operation is not implemented
     * @see Math#tanh(double)
     */
    public T tanh(T a) {
        return fromDouble(Math.tanh(a.doubleValue()));
    }

    // endregion

    // region toResultArithmetic

    /**
     * @return {@link AbstractResultArithmetic} with current arithmetic
     */
    public AbstractResultArithmetic<T, T> toResultArithmetic() {
        return new AbstractResultArithmetic<>(this, this) {
            @Override
            public T fromT(T a) {
                return a;
            }
        };
    }

    // endregion

    /**
     * thrown if operation is not supported
     */
    public static class NotImplementedException extends RuntimeException {
    }
}
