package io.rala.math.geometry;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.Rotatable;
import io.rala.math.utils.Validatable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

/**
 * class which holds a vector in a 2d area with x &amp; y
 *
 * @param <T> number class
 * @since 1.0.0
 */
public class Vector<T extends Number> implements Validatable, Rotatable<T, Vector<T>>,
    Copyable<Vector<T>>, Comparable<Vector<T>>, Serializable {

    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private T x;
    private T y;

    // endregion

    // region constructors

    /**
     * calls {@link #Vector(AbstractArithmetic, Number)} with {@code 0}
     *
     * @param arithmetic arithmetic for calculations
     * @see #Vector(AbstractArithmetic, Number)
     * @see #Vector(AbstractArithmetic, Number, Number)
     * @since 1.0.0
     */
    public Vector(@NotNull AbstractArithmetic<T> arithmetic) {
        this(arithmetic, arithmetic.zero());
    }

    /**
     * calls {@link #Vector(AbstractArithmetic, Number, Number)}
     * with the value at x and y
     *
     * @param arithmetic arithmetic for calculations
     * @param xy         value to be used in
     *                   {@link #Vector(AbstractArithmetic, Number, Number)}
     *                   at x and y
     * @see #Vector(AbstractArithmetic)
     * @see #Vector(AbstractArithmetic, Number, Number)
     * @since 1.0.0
     */
    public Vector(@NotNull AbstractArithmetic<T> arithmetic, @NotNull T xy) {
        this(arithmetic, xy, xy);
    }

    /**
     * creates a vector with given x and y values
     *
     * @param arithmetic arithmetic for calculations
     * @param x          x value of vector
     * @param y          y value of vector
     * @see #Vector(AbstractArithmetic)
     * @see #Vector(AbstractArithmetic, Number)
     * @since 1.0.0
     */
    public Vector(@NotNull AbstractArithmetic<T> arithmetic, @NotNull T x, @NotNull T y) {
        this.arithmetic = arithmetic;
        setX(x);
        setY(y);
    }

    // endregion

    // region getter and setter

    /**
     * @return stored arithmetic
     * @since 1.0.0
     */
    @NotNull
    public AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

    /**
     * @return x value of vector
     * @since 1.0.0
     */
    @NotNull
    public T getX() {
        return x;
    }

    /**
     * @param x new x value of vector
     * @since 1.0.0
     */
    public void setX(@NotNull T x) {
        this.x = x;
    }

    /**
     * @return y value of vector
     * @since 1.0.0
     */
    @NotNull
    public T getY() {
        return y;
    }

    /**
     * @param y new y value of vector
     * @since 1.0.0
     */
    public void setY(@NotNull T y) {
        this.y = y;
    }

    /**
     * @param xy new x and y value of vector
     * @since 1.0.0
     */
    public void setXY(T xy) {
        setX(xy);
        setY(xy);
    }

    // endregion

    // region length, add, subtract and multiply

    /**
     * @return length of vector based on pythagoras
     * @since 1.0.0
     */
    @NotNull
    public T length() {
        return getArithmetic().root2(
            getArithmetic().sum(
                getArithmetic().power(getX(), 2),
                getArithmetic().power(getY(), 2)
            )
        );
    }

    /**
     * calls {@link #add(Number, Number)} with given value
     *
     * @param xy x and y value to add
     * @return new vector with sum of current and given parameters
     * @see #add(Number, Number)
     * @see #add(Vector)
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> add(@NotNull T xy) {
        return add(xy, xy);
    }

    /**
     * calls {@link #add(Vector)} with given values
     *
     * @param x x value to add
     * @param y y value to add
     * @return new vector with sum of current and given parameters
     * @see #add(Number)
     * @see #add(Vector)
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> add(@NotNull T x, @NotNull T y) {
        return add(new Vector<>(getArithmetic(), x, y));
    }

    /**
     * @param vector vector value to add
     * @return new vector with sum of current and given vector
     * @see #add(Number)
     * @see #add(Number, Number)
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> add(@NotNull Vector<T> vector) {
        return new Vector<>(getArithmetic(),
            getArithmetic().sum(getX(), vector.getX()),
            getArithmetic().sum(getY(), vector.getY())
        );
    }

    /**
     * calls {@link #subtract(Number, Number)} with given value
     *
     * @param xy x and y value to subtract
     * @return new vector with difference of current and given parameters
     * @see #subtract(Number, Number)
     * @see #subtract(Vector)
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> subtract(@NotNull T xy) {
        return subtract(xy, xy);
    }

    /**
     * calls {@link #subtract(Vector)} with given values
     *
     * @param x x value to subtract
     * @param y y value to subtract
     * @return new vector with difference of current and given parameters
     * @see #subtract(Number)
     * @see #subtract(Vector)
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> subtract(@NotNull T x, @NotNull T y) {
        return subtract(new Vector<>(getArithmetic(), x, y));
    }

    /**
     * @param vector vector value to subtract
     * @return new vector with difference of current and given vector
     * @see #subtract(Number)
     * @see #subtract(Number, Number)
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> subtract(@NotNull Vector<T> vector) {
        return add(vector.inverse());
    }

    /**
     * @param i value to multiply with x &amp; y
     * @return new vector with multiplied x &amp; y values
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> multiply(@NotNull T i) {
        return new Vector<>(getArithmetic(),
            getArithmetic().product(getX(), i),
            getArithmetic().product(getY(), i)
        );
    }

    // endregion

    // region inverse

    /**
     * @return new vector which has inverse x &amp; y values
     * @see #inverseX()
     * @see #inverseY()
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> inverse() {
        return inverseX().inverseY();
    }

    /**
     * @return new vector which has inverse x value
     * @see #inverse()
     * @see #inverseY()
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> inverseX() {
        return new Vector<>(getArithmetic(), getArithmetic().negate(getX()), getY());
    }

    /**
     * @return new vector which has inverse y value
     * @see #inverse()
     * @see #inverseX()
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> inverseY() {
        return new Vector<>(getArithmetic(), getX(), getArithmetic().negate(getY()));
    }

    // endregion

    // region absolute, normal and normalized

    /**
     * @return new vector with absolute values
     * @see Math#abs(double)
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> absolute() {
        return new Vector<>(getArithmetic(),
            getArithmetic().absolute(getX()),
            getArithmetic().absolute(getY())
        );
    }

    /**
     * @return new normal vector rotated left
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> normalLeft() {
        return new Vector<>(getArithmetic(), getArithmetic().negate(getY()), getX());
    }

    /**
     * @return new normal vector rotated right
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> normalRight() {
        return new Vector<>(getArithmetic(), getY(), getArithmetic().negate(getX()));
    }

    /**
     * @return new normalized vector
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> normalized() {
        return new Vector<>(getArithmetic(),
            getArithmetic().quotient(getX(), length()),
            getArithmetic().quotient(getY(), length())
        );
    }

    // endregion

    // region scalarProduct and angle

    /**
     * @param vector to calc scalar product
     * @return scalar product
     * @since 1.0.0
     */
    @NotNull
    public T scalarProduct(@NotNull Vector<T> vector) {
        return getArithmetic().sum(
            getArithmetic().product(getX(), vector.getX()),
            getArithmetic().product(getY(), vector.getY())
        );
    }

    /**
     * @param vector vector to calc angle between
     * @return angle in {@code rad} between vectors
     * @since 1.0.0
     */
    @NotNull
    public T angle(@NotNull Vector<T> vector) {
        return getArithmetic().acos(
            getArithmetic().quotient(
                scalarProduct(vector),
                getArithmetic().product(length(), vector.length())
            )
        );
    }

    // endregion

    // region isZeroVector and asComplex

    /**
     * @return {@code true} if both params cast to {@code int} are zero
     * @since 1.0.0
     */
    public boolean isZeroVector() {
        return getArithmetic().isZero(getX()) && getArithmetic().isFinite(getY());
    }

    /**
     * @return new complex representing
     * {@link #getX()} as {@code re} and
     * {@link #getY()} as {@code im}
     * @since 1.0.0
     */
    @NotNull
    public Complex<T> asComplex() {
        return new Complex<>(getArithmetic(), getX(), getY());
    }

    // endregion

    // region map, isValid, rotate and copy

    /**
     * @param arithmetic arithmetic for calculations
     * @param map        mapping function to convert current values to new one
     * @param <NT>       new number class
     * @return mapped vector
     * @since 1.0.0
     */
    @NotNull
    public <NT extends Number> Vector<NT> map(
        @NotNull AbstractArithmetic<NT> arithmetic, @NotNull Function<T, NT> map
    ) {
        return new Vector<>(
            arithmetic,
            map.apply(getX()),
            map.apply(getY())
        );
    }

    @Override
    public boolean isValid() {
        return getArithmetic().isFinite(getX()) && getArithmetic().isFinite(getY());
    }

    @Override
    @NotNull
    public Vector<T> rotate(@NotNull T phi) {
        return rotate(new Point<>(getArithmetic()), phi);
    }

    @Override
    @NotNull
    public Vector<T> rotate(@NotNull Point<T> center, @NotNull T phi) {
        T cosPhi = getArithmetic().cos(phi);
        T sinPhi = getArithmetic().sin(phi);
        T newX = getArithmetic().difference(
            getArithmetic().product(cosPhi, getX()),
            getArithmetic().product(sinPhi, getY())
        );
        T newY = getArithmetic().sum(
            getArithmetic().product(sinPhi, getX()),
            getArithmetic().product(cosPhi, getY())
        );
        return new Vector<>(getArithmetic(), newX, newY);
    }

    @Override
    @NotNull
    public Vector<T> copy() {
        return new Vector<>(getArithmetic(), getX(), getY());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector<?>)) return false;
        Vector<?> vector = (Vector<?>) o;
        return Objects.equals(getX(), vector.getX()) &&
            Objects.equals(getY(), vector.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    @NotNull
    public String toString() {
        return getX() + ":" + getY();
    }

    @Override
    public int compareTo(@NotNull Vector<T> o) {
        int diffX = getArithmetic().compare(getX(), o.getX());
        if (diffX != 0) return diffX;
        return getArithmetic().compare(getY(), o.getY());
    }

    // endregion
}
