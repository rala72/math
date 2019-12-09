package io.rala.math.utils;

/**
 * class which defines required arithmetic for calculations
 *
 * @param <T> number class of arithmetic
 */
@SuppressWarnings("unused")
public abstract class AbstractArithmetic<T extends Number> {
    /**
     * @param a value from integer
     * @return number as <code>T</code>
     * @throws NotImplementedException if operation is not implemented
     */
    protected abstract T fromInt(int a);

    /**
     * @param a first value of sum
     * @param b second value of sum
     * @return <code>a+b</code>
     * @throws NotImplementedException if operation is not implemented
     */
    protected abstract T sum(T a, T b);

    /**
     * @param a first value of sum
     * @param b second value of sum
     * @param c third value of sum
     * @return <code>a+b+c</code>
     * @throws NotImplementedException if operation is not implemented
     * @see #sum(Number, Number)
     */
    protected T sum(T a, T b, T c) {
        return sum(sum(a, b), c);
    }

    /**
     * @param a first value of difference
     * @param b second value of difference
     * @return <code>a-b</code>
     * @throws NotImplementedException if operation is not implemented
     */
    protected abstract T difference(T a, T b);

    /**
     * @param a first value of product
     * @param b second value of product
     * @return <code>a*b</code>
     * @throws NotImplementedException if operation is not implemented
     */
    protected abstract T product(T a, T b);

    /**
     * @param a first value of product
     * @param b second value of product
     * @param c third value of product
     * @return <code>a*b*c</code>
     * @throws NotImplementedException if operation is not implemented
     * @see #product(Number, Number)
     */
    protected T product(T a, T b, T c) {
        return product(product(a, b), c);
    }

    /**
     * @param a first value of quotient
     * @param b second value of quotient
     * @return <code>a/b</code>
     * @throws NotImplementedException if operation is not implemented
     */
    protected abstract T quotient(T a, T b);

    /**
     * thrown if operation is not supported
     */
    public static class NotImplementedException extends RuntimeException {
    }
}
