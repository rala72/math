package io.rala.math.algebra;

import io.rala.math.MathX;
import io.rala.math.geometry.Vector;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.Validatable;
import io.rala.math.utils.arithmetic.AbstractArithmetic;
import io.rala.math.utils.arithmetic.DoubleArithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * class which holds a real and a imaginary part of a complex number
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Complex<T extends Number> extends Number implements Validatable,
    Copyable<Complex<T>>, Comparable<Complex<T>> {
    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private T re;
    private T im;

    // endregion

    // region constructors

    /**
     * calls {@link #Complex(AbstractArithmetic, Number, Number)} with <code>0</code>
     */
    public Complex(AbstractArithmetic<T> arithmetic) {
        this(arithmetic,
            arithmetic.fromInt(0), arithmetic.fromInt(0)
        );
    }

    /**
     * creates a complex number with real and imaginary part
     *
     * @param arithmetic arithmetic to use
     * @param re         real part
     * @param im         imaginary part
     */
    public Complex(AbstractArithmetic<T> arithmetic, T re, T im) {
        this.arithmetic = arithmetic;
        this.re = re;
        this.im = im;
    }

    // endregion

    // region getter and setter

    /**
     * @return real part of complex number
     */
    public T getRe() {
        return re;
    }

    /**
     * @param re new real part of complex number
     */
    public void setRe(T re) {
        this.re = re;
    }

    /**
     * @return imaginary part of complex number
     */
    public T getIm() {
        return im;
    }

    /**
     * @param im new imaginary part of complex number
     */
    public void setIm(T im) {
        this.im = im;
    }

    /**
     * @return current {@link AbstractArithmetic}
     */
    protected AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

    // endregion

    // region value

    @Override
    public int intValue() {
        return getRe().intValue();
    }

    @Override
    public long longValue() {
        return getRe().longValue();
    }

    @Override
    public float floatValue() {
        return getRe().floatValue();
    }

    @Override
    public double doubleValue() {
        return getRe().doubleValue();
    }

    // endregion

    // region absoluteValue, argument conjugation and reciprocal

    /**
     * @return absolute <i>(modulus)</i> value of complex based on pythagoras
     */
    public T absoluteValue() {
        return getArithmetic().root2(getArithmetic().sum(
            getArithmetic().exponent(getRe(), 2),
            getArithmetic().exponent(getIm(), 2)
        ));
    }

    /**
     * @return argument value of complex
     */
    public T argument() {
        return getArithmetic().product(
            getArithmetic().fromDouble(getArithmetic().signum(getIm())),
            getArithmetic().acos(getArithmetic().quotient(getRe(), absoluteValue()))
        );
    }

    /**
     * @return {@link #inverseIm()}
     */
    public Complex<T> conjugation() {
        return inverseIm();
    }

    /**
     * @return new reciprocal of complex
     */
    public Complex<T> reciprocal() {
        T d = getArithmetic().sum(
            getArithmetic().exponent(getRe(), 2),
            getArithmetic().exponent(getIm(), 2)
        );
        return new Complex<>(getArithmetic(),
            getArithmetic().quotient(getRe(), d),
            getArithmetic().quotient(getArithmetic().negate(getIm()), d)
        );
    }

    // endregion

    // region add and subtract

    /**
     * calls {@link #add(Complex)} with given values
     *
     * @param re re value to add
     * @param im im value to add
     * @return new complex with sum of current and given parameters
     * @see #add(Complex)
     */
    public Complex<T> add(T re, T im) {
        return add(new Complex<>(getArithmetic(), re, im));
    }

    /**
     * @param complex complex value to add
     * @return new complex with sum of current and given complex
     * @see #add(T, T)
     */
    public Complex<T> add(Complex<T> complex) {
        return new Complex<>(getArithmetic(),
            getArithmetic().sum(getRe(), complex.getRe()),
            getArithmetic().sum(getIm(), complex.getIm())
        );
    }

    /**
     * calls {@link #subtract(Complex)} with given values
     *
     * @param re re value to subtract
     * @param im im value to subtract
     * @return new complex with difference of current and given parameters
     * @see #subtract(Complex)
     */
    public Complex<T> subtract(T re, T im) {
        return subtract(new Complex<>(getArithmetic(), re, im));
    }

    /**
     * @param complex complex value to subtract
     * @return new complex with difference of current and given complex
     * @see #subtract(T, T)
     */
    public Complex<T> subtract(Complex<T> complex) {
        return add(complex.inverse());
    }

    // endregion

    // region multiply and divide

    /**
     * @param i value to multiply with re &amp; im
     * @return new complex with multiplied re &amp; im values
     */
    public Complex<T> multiply(T i) {
        return new Complex<>(getArithmetic(),
            getArithmetic().product(getRe(), i),
            getArithmetic().product(getIm(), i)
        );
    }

    /**
     * @param complex complex value to multiply
     * @return new complex with multiplied re &amp; im values
     */
    public Complex<T> multiply(Complex<T> complex) {
        return new Complex<>(getArithmetic(),
            getArithmetic().difference(
                getArithmetic().product(getRe(), complex.getRe()),
                getArithmetic().product(getIm(), complex.getIm())
            ),
            getArithmetic().sum(
                getArithmetic().product(getRe(), complex.getIm()),
                getArithmetic().product(getIm(), complex.getRe())
            )
        );
    }

    /**
     * @param i value to divide re &amp; im through
     * @return new complex with divided re &amp; im values
     */
    public Complex<T> divide(T i) {
        return new Complex<>(getArithmetic(),
            getArithmetic().quotient(getRe(), i),
            getArithmetic().quotient(getIm(), i)
        );
    }

    /**
     * @param complex complex value to divide through
     * @return new complex which is quotient of the division
     */
    public Complex<T> divide(Complex<T> complex) {
        return new Complex<>(getArithmetic(),
            getArithmetic().quotient(
                getArithmetic().sum(
                    getArithmetic().product(getRe(), complex.getRe()),
                    getArithmetic().product(getIm(), complex.getIm())
                ),
                getArithmetic().sum(
                    getArithmetic().exponent(complex.getRe(), 2),
                    getArithmetic().exponent(complex.getIm(), 2)
                )
            ),
            getArithmetic().quotient(
                getArithmetic().difference(
                    getArithmetic().product(complex.getRe(), getIm()),
                    getArithmetic().product(getRe(), complex.getIm())
                ),
                getArithmetic().sum(
                    getArithmetic().exponent(complex.getRe(), 2),
                    getArithmetic().exponent(complex.getIm(), 2)
                )
            )
        );
    }

    // endregion

    // region pow and root

    /**
     * @param n number of power
     * @return new complex with powered re &amp; im values
     */
    public Complex<T> pow(int n) {
        return Complex.of(getArithmetic(),
            getArithmetic().exponent(absoluteValue(), n),
            getArithmetic().product(argument(), getArithmetic().fromInt(n))
        );
    }

    /**
     * @param n number of root
     * @return new complex with rooted re &amp; im values
     * @see MathX#root(double, int)
     */
    public List<Complex<T>> root(int n) {
        List<Complex<T>> list = new ArrayList<>(n - 1);
        T r = getArithmetic().root(absoluteValue(), n);
        for (int i = 0; i < n; i++)
            list.add(Complex.of(getArithmetic(),
                r,
                getArithmetic().quotient(
                    getArithmetic().sum(argument(),
                        getArithmetic().product(
                            getArithmetic().fromInt(2),
                            getArithmetic().fromDouble(Math.PI),
                            getArithmetic().fromInt(i)
                        )
                    ), getArithmetic().fromInt(n)
                )
            ));
        return list;
    }

    // endregion

    // region inverse

    /**
     * @return new complex which has inverse re &amp; im values
     * @see #inverseRe()
     * @see #inverseIm()
     */
    public Complex<T> inverse() {
        return inverseRe().inverseIm();
    }

    /**
     * @return new complex which has inverse re value
     * @see #inverse()
     * @see #inverseIm()
     */
    public Complex<T> inverseRe() {
        return new Complex<>(getArithmetic(), getArithmetic().negate(getRe()), getIm());
    }

    /**
     * @return new complex which has inverse im value
     * @see #inverse()
     * @see #inverseRe()
     */
    public Complex<T> inverseIm() {
        return new Complex<>(getArithmetic(), getRe(), getArithmetic().negate(getIm()));
    }

    // endregion

    // region static of, asVector, static ofVector

    /**
     * @param absoluteValue absolute value of complex
     * @param argument      argument of vector
     * @return new complex based on absoluteValue and argument
     */
    public static <T extends Number> Complex<T> of(
        AbstractArithmetic<T> arithmetic, T absoluteValue, T argument
    ) {
        return new Complex<>(arithmetic,
            arithmetic.product(
                absoluteValue,
                arithmetic.cos(argument)
            ),
            arithmetic.product(
                absoluteValue,
                arithmetic.sin(argument)
            )
        );
    }

    /**
     * @return new vector representing
     * {@link #getRe()} as <code>x</code> and
     * {@link #getIm()} as <code>y</code>
     */
    public Vector asVector() {
        return new Vector(getRe().doubleValue(), getIm().doubleValue());
    }

    /**
     * @param vector vector to convert to {@link Complex}
     * @return new complex using
     * {@link Vector#getX()} as <code>re</code> and
     * {@link Vector#getY()} as <code>im</code>
     */
    public static Complex<Double> ofVector(Vector vector) {
        return new Complex<>(new DoubleArithmetic(), vector.getX(), vector.getY());
    }

    // endregion

    // region isValid and copy

    @Override
    public boolean isValid() {
        return getArithmetic().isFinite(getRe()) && getArithmetic().isFinite(getIm());
    }

    @Override
    public Complex<T> copy() {
        return new Complex<>(getArithmetic(), getRe(), getIm());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Complex<?>)) return false;
        Complex<?> complex = (Complex<?>) o;
        return getRe().equals(complex.getRe()) &&
            getIm().equals(complex.getIm());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRe(), getIm());
    }

    @Override
    public String toString() {
        boolean hasSign = getArithmetic().compare(
            getArithmetic().fromInt(0), getIm()
        ) <= 0;
        return getRe() + (hasSign ? "+" : "") + getIm() + "*i";
    }

    @Override
    public int compareTo(Complex<T> o) {
        int compare = getArithmetic().compare(getRe(), o.getRe());
        if (compare != 0) return compare;
        return getArithmetic().compare(getIm(), o.getIm());
    }

    // endregion
}
