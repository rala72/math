package io.rala.math.geometry;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.Movable;
import io.rala.math.utils.Rotatable;
import io.rala.math.utils.Validatable;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * class which holds a line segment in a 2d area with points a &amp; b
 *
 * @param <T> number class
 */
public class LineSegment<T extends Number> implements Validatable,
    Movable<T, LineSegment<T>>, Rotatable<T, LineSegment<T>>,
    Copyable<LineSegment<T>>, Comparable<LineSegment<T>>, Serializable {
    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private Point<T> a;
    private Point<T> b;

    // endregion

    // region constructors

    /**
     * calls {@link #LineSegment(AbstractArithmetic, Point, Point)} with
     * {@link Point#Point(AbstractArithmetic)} and the value at b
     *
     * @param arithmetic arithmetic for calculations
     * @param b          b value to be used in
     *                   {@link #LineSegment(AbstractArithmetic, Point, Point)} at b
     * @see #LineSegment(AbstractArithmetic, Point, Point)
     */
    public LineSegment(AbstractArithmetic<T> arithmetic, Point<T> b) {
        this(arithmetic, new Point<>(arithmetic), b);
    }

    /**
     * creates a line segment with given a and b values
     *
     * @param arithmetic arithmetic for calculations
     * @param a          a value of line segment
     * @param b          b value of line segment
     * @see #LineSegment(AbstractArithmetic, Point)
     */
    public LineSegment(AbstractArithmetic<T> arithmetic, Point<T> a, Point<T> b) {
        this.arithmetic = arithmetic;
        setA(a);
        setB(b);
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
     * @return a value of line segment
     */
    public Point<T> getA() {
        return a;
    }

    /**
     * @param a new a value of line segment
     */
    public void setA(Point<T> a) {
        this.a = a;
    }

    /**
     * @return b value of line segment
     */
    public Point<T> getB() {
        return b;
    }

    /**
     * @param b new b value of line segment
     */
    public void setB(Point<T> b) {
        this.b = b;
    }

    // endregion

    // region length, halvingPoint and distributionPoint

    /**
     * @return length of line segment based on pythagoras
     */
    public T length() {
        return getArithmetic().root2(
            getArithmetic().sum(
                getArithmetic().power(
                    getArithmetic().difference(
                        getA().getX(), getB().getX()
                    ),
                    2
                ),
                getArithmetic().power(
                    getArithmetic().difference(
                        getA().getY(), getB().getY()
                    ),
                    2
                )
            )
        );
    }

    /**
     * @return {@code (A+B)/2}
     */
    public Point<T> halvingPoint() {
        return new Point<>(getArithmetic(),
            getArithmetic().quotient(
                getArithmetic().sum(getA().getX(), getB().getX()),
                getArithmetic().fromInt(2)
            ),
            getArithmetic().quotient(
                getArithmetic().sum(getA().getY(), getB().getY()),
                getArithmetic().fromInt(2)
            )
        );
    }

    /**
     * @param d proportion of distribution
     * @return {@code (1-d)*A+d*B}
     */
    public Point<T> distributionPoint(T d) {
        T dT = getArithmetic().difference(getArithmetic().one(), d);
        return new Point<>(getArithmetic(),
            getArithmetic().sum(
                getArithmetic().product(dT, getA().getX()),
                getArithmetic().product(d, getB().getX())
            ),
            getArithmetic().sum(
                getArithmetic().product(dT, getA().getY()),
                getArithmetic().product(d, getB().getY())
            )
        );
    }

    // endregion

    // region flip, toLine

    /**
     * @return a new line segment with flipped points
     */
    public LineSegment<T> flip() {
        return new LineSegment<>(getArithmetic(), getB(), getA());
    }

    /**
     * may return a new Line with {@link Double#NaN}
     * as {@code m} if the line is vertical -
     * {@code b} is corresponding {@code x}
     *
     * @return new line instance
     */
    public Line<T> toLine() {
        if (Objects.equals(getA().getX(), getB().getX()))
            return new Line<>(getArithmetic(), null, getA().getX());
        T m = getArithmetic().quotient(
            getArithmetic().difference(
                getB().getY(),
                getA().getY()
            ),
            getArithmetic().difference(
                getB().getX(),
                getA().getX()
            )
        );
        T b = getArithmetic().difference(
            getA().getY(),
            getArithmetic().product(m, getA().getX())
        );
        return new Line<>(getArithmetic(), m, b);
    }

    // endregion

    // region map, isValid, move, rotate and copy

    /**
     * @param arithmetic arithmetic for calculations
     * @param map        mapping function to convert current values to new one
     * @param <NT>       new number class
     * @return mapped lineSegment
     */
    public <NT extends Number> LineSegment<NT> map(
        AbstractArithmetic<NT> arithmetic, Function<T, NT> map
    ) {
        return new LineSegment<>(
            arithmetic,
            getA().map(arithmetic, map),
            getB().map(arithmetic, map)
        );
    }

    @Override
    public boolean isValid() {
        return getA().isValid() && getB().isValid();
    }

    @Override
    public LineSegment<T> move(T x, T y) {
        return move(new Vector<>(getArithmetic(), x, y));
    }

    @Override
    public LineSegment<T> move(Vector<T> vector) {
        return new LineSegment<>(getArithmetic(),
            getA().move(vector), getB().move(vector)
        );
    }

    @Override
    public LineSegment<T> rotate(T phi) {
        return rotate(new Point<>(getArithmetic()), phi);
    }

    @Override
    public LineSegment<T> rotate(Point<T> center, T phi) {
        return new LineSegment<>(
            getArithmetic(), getA().rotate(center, phi),
            getB().rotate(center, phi)
        );
    }

    @Override
    public LineSegment<T> copy() {
        return new LineSegment<>(getArithmetic(), getA().copy(), getB().copy());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineSegment<?>)) return false;
        LineSegment<?> lineSegment = (LineSegment<?>) o;
        return Objects.equals(getA(), lineSegment.getA()) &&
            Objects.equals(getB(), lineSegment.getB());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getA(), getB());
    }

    @Override
    public String toString() {
        return a + " " + b;
    }

    @Override
    public int compareTo(LineSegment<T> o) {
        Point<T> min = List.of(a, b).stream().min(Point::compareTo).get();
        Point<T> minO = List.of(o.a, o.b).stream().min(Point::compareTo).get();
        int i = min.compareTo(minO);
        if (i != 0) return i;
        Point<T> max = List.of(a, b).stream().max(Point::compareTo).get();
        Point<T> maxO = List.of(o.a, o.b).stream().max(Point::compareTo).get();
        return max.compareTo(maxO);
    }

    // endregion
}
