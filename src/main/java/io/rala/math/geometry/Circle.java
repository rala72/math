package io.rala.math.geometry;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.Movable;
import io.rala.math.utils.Rotatable;
import io.rala.math.utils.Validatable;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

/**
 * class which holds a circle a in 2d area with center &amp; radius
 *
 * @param <T> number class
 * @since 1.0.0
 */
public class Circle<T extends Number> implements Validatable,
    Movable<T, Circle<T>>, Rotatable<T, Circle<T>>,
    Copyable<Circle<T>>, Comparable<Circle<T>>, Serializable {

    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private Point<T> center;
    private T radius;

    // endregion

    // region constructors

    /**
     * calls {@link #Circle(AbstractArithmetic, Point)} with
     * {@link Point#Point(AbstractArithmetic)}
     *
     * @param arithmetic arithmetic for calculations
     * @since 1.0.0
     */
    public Circle(AbstractArithmetic<T> arithmetic) {
        this(arithmetic, new Point<>(arithmetic));
    }

    /**
     * calls {@link #Circle(AbstractArithmetic, Point, Number)} with radius {@code 1}
     *
     * @param arithmetic arithmetic for calculations
     * @param center     center point of circle
     * @since 1.0.0
     */
    public Circle(AbstractArithmetic<T> arithmetic, Point<T> center) {
        this(arithmetic, center, arithmetic.one());
    }

    /**
     * calls {@link #Circle(AbstractArithmetic, Point, Number)} with
     * {@link Point#Point(AbstractArithmetic)}
     *
     * @param arithmetic arithmetic for calculations
     * @param radius     radius of circle
     * @since 1.0.0
     */
    public Circle(AbstractArithmetic<T> arithmetic, T radius) {
        this(arithmetic, new Point<>(arithmetic), radius);
    }

    /**
     * calls {@link #Circle(AbstractArithmetic, Point, Number)}
     * with {@link LineSegment#length()} as radius
     *
     * @param arithmetic arithmetic for calculations
     * @param center     center point of circle
     * @param point      point on circle
     * @since 1.0.0
     */
    public Circle(AbstractArithmetic<T> arithmetic, Point<T> center, Point<T> point) {
        this(arithmetic, center, new LineSegment<>(arithmetic, center, point).length());
    }

    /**
     * creates a new circle with given center and radius
     *
     * @param arithmetic arithmetic for calculations
     * @param center     center point of circle
     * @param radius     radius of circle
     * @since 1.0.0
     */
    public Circle(AbstractArithmetic<T> arithmetic, Point<T> center, T radius) {
        this.arithmetic = arithmetic;
        setCenter(center);
        setRadius(radius);
    }

    // endregion

    // region getter and setter

    /**
     * @return stored arithmetic
     * @since 1.0.0
     */
    public AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

    /**
     * @return center of circle
     * @since 1.0.0
     */
    public Point<T> getCenter() {
        return center;
    }

    /**
     * @param center new center of circle
     * @since 1.0.0
     */
    public void setCenter(Point<T> center) {
        this.center = center;
    }

    /**
     * @return radius of circle
     * @since 1.0.0
     */
    public T getRadius() {
        return radius;
    }

    /**
     * @param radius new radius of circle
     * @since 1.0.0
     */
    public void setRadius(T radius) {
        this.radius = radius;
    }

    /**
     * @return {@link #getRadius()}{@code *2}
     * @since 1.0.0
     */
    public T getDiameter() {
        return getArithmetic().product(
            getRadius(), getArithmetic().fromInt(2)
        );
    }

    /**
     * calls {@link #setRadius(Number)} with {@code diameter/2}
     *
     * @param diameter new diameter of circle
     * @see #setRadius(Number)
     * @since 1.0.0
     */
    public void setDiameter(T diameter) {
        setRadius(getArithmetic().quotient(
            diameter, getArithmetic().fromInt(2)
        ));
    }

    // endregion

    // region area and circumference

    /**
     * @return &pi;{@code *r^2}
     * @since 1.0.0
     */
    public T area() {
        return getArithmetic().product(
            getArithmetic().fromDouble(Math.PI),
            getArithmetic().power(getRadius(), 2)
        );
    }

    /**
     * @return {@code 2*}&pi;{@code *r}
     * @since 1.0.0
     */
    public T circumference() {
        return getArithmetic().product(
            getArithmetic().fromInt(2),
            getArithmetic().fromDouble(Math.PI),
            getRadius()
        );
    }

    // endregion

    // region isUnitCircle

    /**
     * @return {@code true} if {@link #getRadius()} is 1
     * @since 1.0.0
     */
    public boolean isUnitCircle() {
        return getArithmetic().one().equals(getRadius());
    }

    // endregion

    // region map, isValid, move, rotate and copy

    /**
     * @param arithmetic arithmetic for calculations
     * @param map        mapping function to convert current values to new one
     * @param <NT>       new number class
     * @return mapped circle
     * @since 1.0.0
     */
    public <NT extends Number> Circle<NT> map(
        AbstractArithmetic<NT> arithmetic, Function<T, NT> map
    ) {
        return new Circle<>(
            arithmetic,
            getCenter().map(arithmetic, map),
            map.apply(getRadius())
        );
    }

    @Override
    public boolean isValid() {
        return getCenter().isValid() && getArithmetic().isFinite(getRadius()) &&
            getArithmetic().compare(getArithmetic().fromInt(0), getRadius()) <= 0;
    }

    @Override
    public Circle<T> move(T x, T y) {
        return new Circle<>(getArithmetic(), getCenter().move(x, y), getRadius());
    }

    @Override
    public Circle<T> rotate(T phi) {
        return rotate(new Point<>(getArithmetic()), phi);
    }

    @Override
    public Circle<T> rotate(Point<T> center, T phi) {
        return new Circle<>(getArithmetic(),
            getCenter().rotate(center, phi), getRadius()
        );
    }

    @Override
    public Circle<T> copy() {
        return new Circle<>(getArithmetic(), getCenter().copy(), getRadius());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circle<?>)) return false;
        Circle<?> circle = (Circle<?>) o;
        return Objects.equals(getCenter(), circle.getCenter()) &&
            Objects.equals(getRadius(), circle.getRadius());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCenter(), getRadius());
    }

    @Override
    public String toString() {
        return getCenter() + " " + getRadius();
    }

    @Override
    public int compareTo(Circle<T> o) {
        int compare = getArithmetic().compare(getRadius(), o.getRadius());
        if (compare != 0) return compare;
        return getCenter().compareTo(o.getCenter());
    }

    // endregion
}
