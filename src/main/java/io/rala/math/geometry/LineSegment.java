package io.rala.math.geometry;

import io.rala.math.utils.Copyable;
import io.rala.math.utils.Movable;

import java.util.List;
import java.util.Objects;

/**
 * class which holds a line segment in a 2d area with points a &amp; b
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class LineSegment implements Copyable<LineSegment>, Movable<LineSegment>, Comparable<LineSegment> {
    // region attributes

    private Point a;
    private Point b;

    // endregion

    // region constructors

    /**
     * calls {@link #LineSegment(Point)} with {@link Point#Point()}
     *
     * @see #LineSegment(Point)
     * @see #LineSegment(Point, Point)
     */
    public LineSegment() {
        this(new Point());
    }

    /**
     * calls {@link #LineSegment(Point, Point)} with the value at a and b
     *
     * @param ab ab value to be used in {@link #LineSegment(Point, Point)} at a and b
     * @see #LineSegment()
     * @see #LineSegment(Point, Point)
     */
    public LineSegment(Point ab) {
        this(ab, ab);
    }

    /**
     * creates a line segment with given a and b values
     *
     * @param a a value of line segment
     * @param b b value of line segment
     * @see #LineSegment()
     * @see #LineSegment(Point)
     */
    public LineSegment(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    // endregion

    // region getter and setter

    /**
     * @return a value of line segment
     */
    public Point getA() {
        return a;
    }

    /**
     * @param a new a value of line segment
     */
    public void setA(Point a) {
        this.a = a;
    }

    /**
     * @return b value of line segment
     */
    public Point getB() {
        return b;
    }

    /**
     * @param b new b value of line segment
     */
    public void setB(Point b) {
        this.b = b;
    }

    /**
     * @param ab new a and b value of line segment
     */
    public void setAB(Point ab) {
        setA(ab);
        setB(ab);
    }

    // endregion

    // region length, halvingPoint and distributionPoint

    /**
     * @return length of vector based on pythagoras
     */
    public double length() {
        return Math.sqrt(
            Math.pow(getA().getX() - getB().getX(), 2) +
                Math.pow(getA().getY() - getB().getY(), 2)
        );
    }

    /**
     * @return <code>(A+B)/2</code>
     */
    public Point halvingPoint() {
        return new Point(
            (getA().getX() + getB().getX()) / 2,
            (getA().getY() + getB().getY()) / 2
        );
    }

    /**
     * @param d proportion of distribution
     * @return <code>(1-d)*A+d*B</code>
     */
    public Point distributionPoint(double d) {
        return new Point(
            (1d - d) * getA().getX() + d * getB().getX(),
            (1d - d) * getA().getY() + d * getB().getY()
        );
    }

    // endregion

    // region move and copy

    @Override
    public LineSegment move(Vector vector) {
        return new LineSegment(getA().move(vector), getB().move(vector));
    }

    @Override
    public LineSegment copy() {
        return new LineSegment(getA().copy(), getB().copy());
    }

    // endregion

    // region override

    @Override
    public int compareTo(LineSegment o) {
        Point min = List.of(a, b).stream().min(Point::compareTo).get();
        Point minO = List.of(o.a, o.b).stream().min(Point::compareTo).get();
        int i = min.compareTo(minO);
        if (i != 0) return i;
        Point max = List.of(a, b).stream().max(Point::compareTo).get();
        Point maxO = List.of(o.a, o.b).stream().max(Point::compareTo).get();
        return max.compareTo(maxO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineSegment that = (LineSegment) o;
        return Objects.equals(getA(), that.getA()) &&
            Objects.equals(getB(), that.getB());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getA(), getB());
    }

    @Override
    public String toString() {
        return a + " " + b;
    }

    // endregion
}
