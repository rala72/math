package io.rala.math.geometry;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.Validatable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

/**
 * class which holds a line in a 2d area with m &amp; b<br>
 * {@code y=m*x+b}<br>
 * if line is vertical m is considered to be {@code null}<br>
 * {@code x=b}
 *
 * @param <T> number class
 * @since 1.0.0
 */
public class Line<T extends Number> implements Validatable,
    Copyable<Line<T>>, Comparable<Line<T>>, Serializable {

    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private T m;
    private T b;

    // endregion

    // region constructor

    /**
     * creates a vertical line storing
     * {@code m} as {@code null} and
     * {@code b} as {@code x}
     *
     * @param arithmetic arithmetic for calculations
     * @param x          x value of line
     * @since 1.0.0
     */
    public Line(@NotNull AbstractArithmetic<T> arithmetic, T x) {
        this(arithmetic, null, x);
    }

    /**
     * creates a line with given slope/gradient and y-intercept
     *
     * @param arithmetic arithmetic for calculations
     * @param m          slope/gradient of line
     * @param b          y-intercept of line
     * @since 1.0.0
     */
    public Line(@NotNull AbstractArithmetic<T> arithmetic, @Nullable T m, @NotNull T b) {
        this.arithmetic = arithmetic;
        setM(m);
        setB(b);
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
     * @return m value of line - may return {@code null} if {@link #isVertical()}
     * @since 1.0.0
     */
    @Nullable
    public T getM() {
        return m;
    }

    /**
     * @param m new m value of line - use {@code null} if {@link #isVertical()}
     * @since 1.0.0
     */
    public void setM(@Nullable T m) {
        this.m = m != null && getArithmetic().isFinite(m) ? m : null;
    }

    /**
     * @return b value of line
     * @since 1.0.0
     */
    @NotNull
    public T getB() {
        return b;
    }

    /**
     * @param b new b value of line
     * @since 1.0.0
     */
    public void setB(@NotNull T b) {
        this.b = b;
    }

    // endregion

    // region isHorizontal and isVertical

    /**
     * @return {@code true} if {@link #getM()} returns {@code 0}
     * @since 1.0.0
     */
    public boolean isHorizontal() {
        return !isVertical() && getM() != null && getArithmetic().isZero(getM());
    }

    /**
     * @return {@code true} if {@link #getM()} returns {@code null}
     * @since 1.0.0
     */
    public boolean isVertical() {
        return getM() == null;
    }

    // endregion

    // region calculateX and calculateY

    /**
     * calculates x value of provided y value
     *
     * @param y y value to get x value
     * @return {@code x=(y-b)/m}, {@link #getB()} if {@link #isVertical()}
     * or may return {@code null} if {@link #isHorizontal()}
     * and {@code y} is not on line
     * @since 1.0.0
     */
    @Nullable
    public T calculateX(@NotNull T y) {
        if (isVertical()) return getB();
        if (isHorizontal())
            return getArithmetic().isEqual(getB(), y) ? getB() : null;
        return getArithmetic().quotient(
            getArithmetic().difference(y, getB()), Objects.requireNonNull(getM())
        );
    }

    /**
     * calculates y value of provided x value
     *
     * @param x x value to get y value
     * @return {@code y=m*x+b}, {@link #getB()} if {@link #isHorizontal()}
     * or may return {@code null} if {@link #isVertical()}
     * and {@code x} is not on line
     * @since 1.0.0
     */
    @Nullable
    public T calculateY(@NotNull T x) {
        if (isHorizontal()) return getB();
        if (isVertical())
            return getArithmetic().isEqual(getB(), x) ? getB() : null;
        return getArithmetic().sum(
            getArithmetic().product(Objects.requireNonNull(getM()), x),
            getB()
        );
    }

    // endregion

    // region normal

    /**
     * @return normal line without changing {@link #getB()}
     * @since 1.0.0
     */
    @NotNull
    public Line<T> normal() {
        T newM = getArithmetic().zero();
        if (!isVertical())
            newM = isHorizontal() ? null :
                getArithmetic().quotient(
                    getArithmetic().fromInt(-1),
                    Objects.requireNonNull(getM())
                );
        return new Line<>(getArithmetic(), newM, getB());
    }

    /**
     * @param point point on line
     * @return normal line through given point
     * @see #normal()
     * @since 1.0.0
     */
    @NotNull
    public Line<T> normal(@NotNull Point<T> point) {
        Line<T> normal = normal();
        normal.setB(
            normal.isVertical() ? point.getX() :
                getArithmetic().difference(
                    point.getY(),
                    getArithmetic().product(
                        Objects.requireNonNull(normal.getM()),
                        point.getX()
                    )
                )
        );
        return normal;
    }

    // endregion

    // region intersection

    /**
     * @param line line to check if intersection exists
     * @return {@code true} if {@code m} is not equal
     * @since 1.0.0
     */
    public boolean hasIntersection(@NotNull Line<T> line) {
        return (!isVertical() || !line.isVertical()) &&
            !getArithmetic().isEqual(getM(), line.getM());
    }

    /**
     * @param line line to intersect
     * @return intersection or {@code null}
     * if {@link #hasIntersection(Line)} is {@code false}
     * @since 1.0.0
     */
    @Nullable
    public Point<T> intersection(@NotNull Line<T> line) {
        if (!hasIntersection(line)) return null;
        if (isVertical())
            return new Point<>(getArithmetic(), getB(),
                Objects.requireNonNull(line.calculateY(getB()))
            );
        if (line.isVertical())
            return new Point<>(getArithmetic(), line.getB(),
                Objects.requireNonNull(calculateY(line.getB()))
            );
        T x = getArithmetic().quotient(
            getArithmetic().negate(
                getArithmetic().difference(getB(), line.getB())
            ),
            getArithmetic().difference(
                Objects.requireNonNull(getM()),
                Objects.requireNonNull(line.getM())
            )
        );
        return new Point<>(getArithmetic(), x,
            Objects.requireNonNull(calculateY(x))
        );
    }

    /**
     * @param line line to intersect
     * @return intersection angle in {@code rad} or {@code null}
     * if there is no intersection
     * @since 1.0.0
     */
    @Nullable
    public T intersectionAngle(@NotNull Line<T> line) {
        if (!hasIntersection(line)) return null;
        if (isVertical() || line.isVertical()) {
            // calculated like y-axis
            T newM = isVertical() ? line.getM() : getM();
            return getArithmetic().difference(
                getArithmetic().quotient(
                    getArithmetic().fromDouble(Math.PI),
                    getArithmetic().fromInt(2)
                ),
                getArithmetic().atan(getArithmetic().absolute(
                    Objects.requireNonNull(newM))
                )
            );
        }
        T tan = getArithmetic().quotient(
            getArithmetic().difference(
                Objects.requireNonNull(getM()),
                Objects.requireNonNull(line.getM())
            ),
            getArithmetic().sum(
                getArithmetic().one(),
                getArithmetic().product(getM(), line.getM())
            )
        );
        return getArithmetic().atan(getArithmetic().absolute(tan));
    }

    // endregion

    // region hasPoint

    /**
     * @param point point to check if on line
     * @return {@code true} if point is on line
     * @since 1.0.0
     */
    public boolean hasPoint(@NotNull Point<T> point) {
        return isVertical() && getArithmetic().isEqual(getB(), point.getX()) ||
            getArithmetic().isEqual(calculateX(point.getY()), point.getX()) ||
            getArithmetic().isEqual(calculateY(point.getX()), point.getY());
    }

    // endregion

    // region toLineSegment

    /**
     * @param fromX starting index of line segment
     * @param toX   end index of line segment
     * @return new line segment instance in given boundaries
     * @see #calculateY(Number)
     * @since 1.0.0
     */
    @NotNull
    public LineSegment<T> toLineSegmentUsingX(@NotNull T fromX, @NotNull T toX) {
        return new LineSegment<>(
            getArithmetic(),
            new Point<>(getArithmetic(), fromX,
                Objects.requireNonNull(calculateY(fromX))
            ),
            new Point<>(getArithmetic(), toX,
                Objects.requireNonNull(calculateY(toX))
            )
        );
    }

    /**
     * @param fromY starting index of line segment
     * @param toY   end index of line segment
     * @return new line segment instance in given boundaries
     * @see #calculateX(Number)
     * @since 1.0.0
     */
    @NotNull
    public LineSegment<T> toLineSegmentUsingY(@NotNull T fromY, @NotNull T toY) {
        return new LineSegment<>(
            getArithmetic(),
            new Point<>(getArithmetic(),
                Objects.requireNonNull(calculateX(fromY)),
                fromY
            ),
            new Point<>(getArithmetic(),
                Objects.requireNonNull(calculateX(toY)),
                toY
            )
        );
    }

    // endregion

    // region map, isValid, copy

    /**
     * @param arithmetic arithmetic for calculations
     * @param map        mapping function to convert current values to new one
     *                   - {@code null} handling already done for {@link #getM()}
     * @param <NT>       new number class
     * @return mapped point
     * @since 1.0.0
     */
    @NotNull
    public <NT extends Number> Line<NT> map(
        @NotNull AbstractArithmetic<NT> arithmetic, @NotNull Function<T, NT> map
    ) {
        return new Line<>(
            arithmetic,
            getM() == null ? null : map.apply(getM()),
            map.apply(getB())
        );
    }

    @Override
    public boolean isValid() {
        return (getM() == null || getArithmetic().isFinite(getM())) &&
            getArithmetic().isFinite(getB());
    }

    @Override
    @NotNull
    public Line<T> copy() {
        return new Line<>(getArithmetic(), getM(), getB());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line<?>)) return false;
        Line<?> line = (Line<?>) o;
        return Objects.equals(getM(), line.getM()) &&
            Objects.equals(getB(), line.getB());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getM(), getB());
    }

    @Override
    @NotNull
    public String toString() {
        String nonVerticalString = "";
        if (!isVertical()) {
            boolean hasPositiveB =
                getArithmetic().compare(getArithmetic().zero(), getB()) <= 0;
            nonVerticalString = getM() + "*x" + (hasPositiveB ? "+" : "");
        }
        return "y=" + nonVerticalString + getB();
    }

    @Override
    public int compareTo(@NotNull Line<T> o) {
        if (getM() != null && o.getM() != null) {
            int compare = getArithmetic().compare(getM(), o.getM());
            if (compare != 0) return compare;
        } else if (getM() == null) return -1;
        else if (o.getM() == null) return 1;
        return getArithmetic().compare(getB(), o.getB());
    }

    // endregion
}
