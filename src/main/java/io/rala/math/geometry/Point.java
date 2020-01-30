package io.rala.math.geometry;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.Movable;
import io.rala.math.utils.Rotatable;
import io.rala.math.utils.Validatable;

import java.io.Serializable;
import java.util.Objects;

/**
 * class which holds a point in a 2d area with x &amp; y
 *
 * @param <T> number class
 */
public class Point<T extends Number> implements Validatable,
    Movable<T, Point<T>>, Rotatable<T, Point<T>>,
    Copyable<Point<T>>, Comparable<Point<T>>, Serializable {
    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private T x;
    private T y;

    // endregion

    // region constructors

    /**
     * calls {@link #Point(AbstractArithmetic, Number)} with {@code 0}
     *
     * @param arithmetic arithmetic for calculations
     * @see #Point(AbstractArithmetic, Number)
     * @see #Point(AbstractArithmetic, Number, Number)
     */
    public Point(AbstractArithmetic<T> arithmetic) {
        this(arithmetic, arithmetic.zero());
    }

    /**
     * calls {@link #Point(AbstractArithmetic, Number, Number)} with the value at x and y
     *
     * @param arithmetic arithmetic for calculations
     * @param xy         value to be used in
     *                   {@link #Point(AbstractArithmetic, Number, Number)} at x and y
     * @see #Point(AbstractArithmetic)
     * @see #Point(AbstractArithmetic, Number, Number)
     */
    public Point(AbstractArithmetic<T> arithmetic, T xy) {
        this(arithmetic, xy, xy);
    }

    /**
     * creates a point with given x and y values
     *
     * @param arithmetic arithmetic for calculations
     * @param x          x value of point
     * @param y          y value of point
     * @see #Point(AbstractArithmetic)
     * @see #Point(AbstractArithmetic, Number)
     */
    public Point(AbstractArithmetic<T> arithmetic, T x, T y) {
        this.arithmetic = arithmetic;
        setX(x);
        setY(y);
    }

    // endregion

    // region getter and setter

    /**
     * @return stored arithmetic
     */
    public AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

    /**
     * @return x value of point
     */
    public T getX() {
        return x;
    }

    /**
     * @param x new x value of point
     */
    public void setX(T x) {
        this.x = x;
    }

    /**
     * @return y value of point
     */
    public T getY() {
        return y;
    }

    /**
     * @param y new y value of point
     */
    public void setY(T y) {
        this.y = y;
    }

    /**
     * @param xy new x and y value of point
     */
    public void setXY(T xy) {
        setX(xy);
        setY(xy);
    }

    // endregion

    // region isValid, move, rotate and copy

    @Override
    public boolean isValid() {
        return getArithmetic().isFinite(getX()) && getArithmetic().isFinite(getY());
    }

    @Override
    public Point<T> move(T x, T y) {
        return move(new Vector<>(getArithmetic(), x, y));
    }

    @Override
    public Point<T> move(Vector<T> vector) {
        return new Point<>(getArithmetic(),
            getArithmetic().sum(getX(), vector.getX()),
            getArithmetic().sum(getY(), vector.getY())
        );
    }

    @Override
    public Point<T> rotate(T phi) {
        return rotate(new Point<>(getArithmetic()), phi);
    }

    @Override
    public Point<T> rotate(Point<T> center, T phi) {
        Vector<T> vector = new Vector<>(getArithmetic(), center.getX(), center.getY());
        Point<T> moved = move(vector.inverse());
        T cosPhi = getArithmetic().cos(phi);
        T sinPhi = getArithmetic().sin(phi);
        T newX = getArithmetic().difference(
            getArithmetic().product(cosPhi, moved.getX()),
            getArithmetic().product(sinPhi, moved.getY())
        );
        T newY = getArithmetic().sum(
            getArithmetic().product(sinPhi, moved.getX()),
            getArithmetic().product(cosPhi, moved.getY())
        );
        return new Point<>(getArithmetic(), newX, newY).move(vector);
    }

    @Override
    public Point<T> copy() {
        return new Point<>(getArithmetic(), getX(), getY());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point<?>)) return false;
        Point<?> point = (Point<?>) o;
        return Objects.equals(getX(), point.getX()) &&
            Objects.equals(getY(), point.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return getX() + "|" + getY();
    }

    @Override
    public int compareTo(Point<T> o) {
        int diffX = getArithmetic().compare(getX(), o.getX());
        if (diffX != 0) return diffX;
        return getArithmetic().compare(getY(), o.getY());
    }

    // endregion
}
