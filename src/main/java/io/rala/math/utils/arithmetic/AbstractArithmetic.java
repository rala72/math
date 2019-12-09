package io.rala.math.utils.arithmetic;

/**
 * class which defines required arithmetic for calculations
 *
 * @param <T> number class of arithmetic
 */
@SuppressWarnings({"unused", "SameParameterValue", "UnusedReturnValue"})
public abstract class AbstractArithmetic<T extends Number> {
    // region fromInt, signum and negate

    /**
     * @param a value from integer
     * @return number as <code>T</code>
     * @throws NotImplementedException if operation is not implemented
     */
    public abstract T fromInt(int a);

    /**
     * @param a value to get signum
     * @return -1 if negative, 0 if zero or 1 if positive
     */
    public abstract double signum(T a);

    /**
     * @param a value to negate
     * @return <code>-a</code>
     */
    public T negate(T a) {
        return product(a, fromInt(-1));
    }

    // endregion

    // region sum, difference, product and quotient

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

    // endregion

    // region exponent and root

    /**
     * @param a basis of exponent
     * @param b power of exponent
     * @return <code>a^b</code>
     */
    public abstract T exponent(T a, int b);

    /**
     * @param a value
     * @return <code>sqrt(a)</code>
     */
    public abstract T root2(T a);

    // endregion

    /**
     * thrown if operation is not supported
     */
    public static class NotImplementedException extends RuntimeException {
    }
}
