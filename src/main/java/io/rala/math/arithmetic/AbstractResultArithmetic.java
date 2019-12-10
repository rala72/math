package io.rala.math.arithmetic;

import java.util.function.Function;

/**
 * class which defines required arithmetic for calculations
 *
 * @param <T> number class of arithmetic input
 * @param <R> number class of arithmetic result
 */
@SuppressWarnings("unused")
public abstract class AbstractResultArithmetic<T extends Number, R extends Number> {

    private final AbstractArithmetic<T> tArithmetic;
    private final AbstractArithmetic<R> rArithmetic;

    /**
     * creates a new {@link AbstractResultArithmetic} with given two arithmetic
     *
     * @param tArithmetic t arithmetic
     * @param rArithmetic r arithmetic
     */
    public AbstractResultArithmetic(
        AbstractArithmetic<T> tArithmetic, AbstractArithmetic<R> rArithmetic
    ) {
        this.tArithmetic = tArithmetic;
        this.rArithmetic = rArithmetic;
    }

    /**
     * @return current t {@link AbstractArithmetic}
     */
    public AbstractArithmetic<T> getTArithmetic() {
        return tArithmetic;
    }

    /**
     * @return current r {@link AbstractArithmetic}
     */
    public AbstractArithmetic<R> getRArithmetic() {
        return rArithmetic;
    }

    /**
     * @param a value to convert
     * @return value as <code>R</code>
     */
    public abstract R fromT(T a);

    // region sum, difference, product, quotient and modulo

    /**
     * @param a first value of sum
     * @param b second value of sum
     * @return <code>a+b</code>
     * @throws AbstractArithmetic.NotImplementedException if operation is not implemented
     * @see AbstractArithmetic#sum(Number, Number)
     */
    public R sum(T a, T b) {
        return getRArithmetic().sum(fromT(a), fromT(b));
    }

    /**
     * @param a first value of sum
     * @param b second value of sum
     * @param c third value of sum
     * @return <code>a+b+c</code>
     * @throws AbstractArithmetic.NotImplementedException if operation is not implemented
     * @see #sum(Number, Number)
     * @see AbstractArithmetic#sum(Number, Number, Number)
     */
    public R sum(T a, T b, T c) {
        return getRArithmetic().sum(fromT(a), fromT(b), fromT(c));
    }

    /**
     * @param a first value of difference
     * @param b second value of difference
     * @return <code>a-b</code>
     * @throws AbstractArithmetic.NotImplementedException if operation is not implemented
     * @see AbstractArithmetic#difference(Number, Number)
     */
    public R difference(T a, T b) {
        return getRArithmetic().difference(fromT(a), fromT(b));
    }

    /**
     * @param a first value of product
     * @param b second value of product
     * @return <code>a*b</code>
     * @throws AbstractArithmetic.NotImplementedException if operation is not implemented
     * @see AbstractArithmetic#product(Number, Number)
     */
    public R product(T a, T b) {
        return getRArithmetic().product(fromT(a), fromT(b));
    }

    /**
     * @param a first value of product
     * @param b second value of product
     * @param c third value of product
     * @return <code>a*b*c</code>
     * @throws AbstractArithmetic.NotImplementedException if operation is not implemented
     * @see #product(Number, Number)
     * @see AbstractArithmetic#product(Number, Number, Number)
     */
    public R product(T a, T b, T c) {
        return getRArithmetic().product(fromT(a), fromT(b), fromT(c));
    }

    /**
     * @param a first value of quotient
     * @param b second value of quotient
     * @return <code>a/b</code>
     * @throws AbstractArithmetic.NotImplementedException if operation is not implemented
     * @see AbstractArithmetic#quotient(Number, Number)
     */
    public R quotient(T a, T b) {
        return getRArithmetic().quotient(fromT(a), fromT(b));
    }

    /**
     * @param a first value of quotient
     * @param b second value of quotient
     * @return reminder of division like <code>r=a-q*b</code>
     * @throws AbstractArithmetic.NotImplementedException if operation is not implemented
     * @see AbstractArithmetic#modulo(Number, Number)
     */
    public R modulo(T a, T b) {
        return getRArithmetic().modulo(fromT(a), fromT(b));
    }

    // endregion

    // region map

    /**
     * @param arithmetic arithmetic for target
     * @param map        mapping function to convert current result to new one
     * @param <V>        number class of target
     * @return new {@link AbstractResultArithmetic} witch returns <code>V</code>
     */
    public <V extends Number> AbstractResultArithmetic<T, V> map(
        AbstractArithmetic<V> arithmetic, Function<T, V> map
    ) {
        return AbstractResultArithmetic.of(getTArithmetic(), arithmetic, map);
    }

    // endregion

    // region static of

    public static <T extends Number, R extends Number> AbstractResultArithmetic<T, R> of(
        AbstractArithmetic<T> tArithmetic, AbstractArithmetic<R> rArithmetic, Function<T, R> map
    ) {
        return new AbstractResultArithmetic<>(tArithmetic, rArithmetic) {
            @Override
            public R fromT(T a) {
                return map.apply(a);
            }
        };
    }

    // endregion
}
