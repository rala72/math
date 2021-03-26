package io.rala.math.algebra.numeric;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.Validatable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * class which holds a real and a imaginary part of a complex number
 *
 * @param <T> number class
 * @since 1.0.0
 */
public class Complex<T extends Number> extends Number implements Validatable,
    Copyable<Complex<T>>, Comparable<Complex<T>> {

    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private T re;
    private T im;

    // endregion

    // region constructors

    /**
     * calls {@link #Complex(AbstractArithmetic, Number, Number)} with {@code 0}
     *
     * @param arithmetic arithmetic for calculations
     * @since 1.0.0
     */
    public Complex(@NotNull AbstractArithmetic<T> arithmetic) {
        this(arithmetic,
            arithmetic.zero(), arithmetic.zero()
        );
    }

    /**
     * creates a complex number with real and imaginary part
     *
     * @param arithmetic arithmetic for calculations
     * @param re         real part
     * @param im         imaginary part
     * @since 1.0.0
     */
    public Complex(@NotNull AbstractArithmetic<T> arithmetic, @NotNull T re, @NotNull T im) {
        this.arithmetic = arithmetic;
        setRe(re);
        setIm(im);
    }

    /**
     * creates a new complex based on given one
     *
     * @param complex complex to copy
     * @since 1.0.0
     */
    protected Complex(@NotNull Complex<T> complex) {
        this(complex.getArithmetic(), complex.getRe(), complex.getIm());
    }

    // endregion

    // region getter and setter

    /**
     * @return stored {@link AbstractArithmetic}
     * @since 1.0.0
     */
    @NotNull
    public AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

    /**
     * @return real part of complex number
     * @since 1.0.0
     */
    @NotNull
    public T getRe() {
        return re;
    }

    /**
     * @param re new real part of complex number
     * @since 1.0.0
     */
    public void setRe(@NotNull T re) {
        this.re = re;
    }

    /**
     * @return imaginary part of complex number
     * @since 1.0.0
     */
    @NotNull
    public T getIm() {
        return im;
    }

    /**
     * @param im new imaginary part of complex number
     * @since 1.0.0
     */
    public void setIm(@NotNull T im) {
        this.im = im;
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

    // region absoluteValue, argument and signum

    /**
     * @return absolute <i>(modulus)</i> value of complex based on pythagoras
     * @since 1.0.0
     */
    @NotNull
    public T absoluteValue() {
        return getArithmetic().root2(getArithmetic().sum(
            getArithmetic().power(getRe(), 2),
            getArithmetic().power(getIm(), 2)
        ));
    }

    /**
     * @return argument value of complex
     * @since 1.0.0
     */
    @NotNull
    public T argument() {
        return getArithmetic().product(
            getArithmetic().fromDouble(getArithmetic().signum(getIm())),
            getArithmetic().acos(getArithmetic().quotient(getRe(), absoluteValue()))
        );
    }

    /**
     * @return {@code 0} if z is zero otherwise {@code z/|z|}
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> signum() {
        if (getArithmetic().isZero(getRe()) &&
            getArithmetic().isZero(getIm()))
            return new Complex<>(getArithmetic());
        return divide(absoluteValue());
    }

    /**
     * @return if re is not {@code 0} {@code sign(re)} otherwise {@code sign(im)}
     * @see Math#signum(double)
     * @since 1.0.0
     */
    public double complexSignum() {
        return !getArithmetic().isZero(getRe()) ?
            getArithmetic().signum(getRe()) : getArithmetic().signum(getIm());
    }

    // endregion

    // region conjugation and reciprocal

    /**
     * @return {@link #inverseIm()}
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> conjugation() {
        return inverseIm();
    }

    /**
     * @return new reciprocal of complex
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> reciprocal() {
        T d = getArithmetic().sum(
            getArithmetic().power(getRe(), 2),
            getArithmetic().power(getIm(), 2)
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
     * @param re real value to add
     * @param im imaginary value to add
     * @return new complex with sum of current and given parameters
     * @see #add(Complex)
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> add(@NotNull T re, @NotNull T im) {
        return add(new Complex<>(getArithmetic(), re, im));
    }

    /**
     * @param complex complex value to add
     * @return new complex with sum of current and given complex
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> add(@NotNull Complex<T> complex) {
        return new Complex<>(getArithmetic(),
            getArithmetic().sum(getRe(), complex.getRe()),
            getArithmetic().sum(getIm(), complex.getIm())
        );
    }

    /**
     * calls {@link #subtract(Complex)} with given values
     *
     * @param re real value to subtract
     * @param im imaginary value to subtract
     * @return new complex with difference of current and given parameters
     * @see #subtract(Complex)
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> subtract(@NotNull T re, @NotNull T im) {
        return subtract(new Complex<>(getArithmetic(), re, im));
    }

    /**
     * @param complex complex value to subtract
     * @return new complex with difference of current and given complex
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> subtract(@NotNull Complex<T> complex) {
        return add(complex.inverse());
    }

    // endregion

    // region multiply and divide

    /**
     * @param i value to multiply with real &amp; imaginary
     * @return new complex with multiplied real &amp; imaginary values
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> multiply(@NotNull T i) {
        return new Complex<>(getArithmetic(),
            getArithmetic().product(getRe(), i),
            getArithmetic().product(getIm(), i)
        );
    }

    /**
     * @param complex complex value to multiply
     * @return new complex with multiplied real &amp; imaginary values
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> multiply(@NotNull Complex<T> complex) {
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
     * @param i value to divide real &amp; imaginary through
     * @return new complex with divided real &amp; imaginary values
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> divide(@NotNull T i) {
        return new Complex<>(getArithmetic(),
            getArithmetic().quotient(getRe(), i),
            getArithmetic().quotient(getIm(), i)
        );
    }

    /**
     * @param complex complex value to divide through
     * @return new complex which is quotient of the division
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> divide(@NotNull Complex<T> complex) {
        return new Complex<>(getArithmetic(),
            getArithmetic().quotient(
                getArithmetic().sum(
                    getArithmetic().product(getRe(), complex.getRe()),
                    getArithmetic().product(getIm(), complex.getIm())
                ),
                getArithmetic().sum(
                    getArithmetic().power(complex.getRe(), 2),
                    getArithmetic().power(complex.getIm(), 2)
                )
            ),
            getArithmetic().quotient(
                getArithmetic().difference(
                    getArithmetic().product(complex.getRe(), getIm()),
                    getArithmetic().product(getRe(), complex.getIm())
                ),
                getArithmetic().sum(
                    getArithmetic().power(complex.getRe(), 2),
                    getArithmetic().power(complex.getIm(), 2)
                )
            )
        );
    }

    // endregion

    // region pow and root

    /**
     * @param n number of power
     * @return new complex with powered real &amp; imaginary values
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> pow(int n) {
        return Complex.of(getArithmetic(),
            getArithmetic().power(absoluteValue(), n),
            getArithmetic().product(argument(), getArithmetic().fromInt(n))
        );
    }

    /**
     * @param n number of root
     * @return new complex with rooted real &amp; imaginary values
     * @see MathX#root(double, int)
     * @since 1.0.0
     */
    @NotNull
    @Unmodifiable
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
        return Collections.unmodifiableList(list);
    }

    // endregion

    // region inverse

    /**
     * @return new complex which has inverse real &amp; imaginary values
     * @see #inverseRe()
     * @see #inverseIm()
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> inverse() {
        return inverseRe().inverseIm();
    }

    /**
     * @return new complex which has inverse real value
     * @see #inverse()
     * @see #inverseIm()
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> inverseRe() {
        return new Complex<>(getArithmetic(), getArithmetic().negate(getRe()), getIm());
    }

    /**
     * @return new complex which has inverse imaginary value
     * @see #inverse()
     * @see #inverseRe()
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> inverseIm() {
        return new Complex<>(getArithmetic(), getRe(), getArithmetic().negate(getIm()));
    }

    // endregion

    // region static of and asVector

    /**
     * @param arithmetic    arithmetic for calculations
     * @param absoluteValue absolute value of complex
     * @param argument      argument of vector
     * @param <T>           number class of values
     * @return new complex based on absoluteValue and argument
     * @since 1.0.0
     */
    @NotNull
    public static <T extends Number> Complex<T> of(
        @NotNull AbstractArithmetic<T> arithmetic, @NotNull T absoluteValue, @NotNull T argument
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
     * {@link #getRe()} as {@code x} and
     * {@link #getIm()} as {@code y}
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> asVector() {
        return new Vector<>(getArithmetic(), getRe(), getIm());
    }

    // endregion

    // region map, isValid and copy

    /**
     * @param arithmetic arithmetic for calculations
     * @param map        mapping function to convert current values to new one
     * @param <NT>       new number class
     * @return new mapped complex
     * @since 1.0.0
     */
    @NotNull
    public <NT extends Number> Complex<NT> map(
        @NotNull AbstractArithmetic<NT> arithmetic, @NotNull Function<T, NT> map
    ) {
        return new Complex<>(arithmetic,
            map.apply(getRe()),
            map.apply(getIm())
        );
    }

    @Override
    public boolean isValid() {
        return getArithmetic().isFinite(getRe()) && getArithmetic().isFinite(getIm());
    }

    @Override
    @NotNull
    public Complex<T> copy() {
        return new Complex<>(this);
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
    @NotNull
    public String toString() {
        boolean hasSign = getArithmetic().compare(
            getArithmetic().zero(), getIm()
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
