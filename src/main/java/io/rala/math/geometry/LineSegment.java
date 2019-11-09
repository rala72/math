package io.rala.math.geometry;

import java.util.List;
import java.util.Objects;

/**
 * line segment in a 2d area
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class LineSegment implements Comparable<LineSegment> {
    // region attributes

    private Point p1;
    private Point p2;

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
     * calls {@link #LineSegment(Point, Point)} with the value at p1 and p2
     *
     * @param p12 p12 value to be used in {@link #LineSegment(Point, Point)} at p1 and p2
     * @see #LineSegment()
     * @see #LineSegment(Point, Point)
     */
    public LineSegment(Point p12) {
        this(p12, p12);
    }

    /**
     * creates a line segment with given p1 and p2 values
     *
     * @param p1 p1 value of line segment
     * @param p2 p2 value of line segment
     * @see #LineSegment()
     * @see #LineSegment(Point)
     */
    public LineSegment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    // endregion

    // region getter and setter

    /**
     * @return p1 value of line segment
     */
    public Point getP1() {
        return p1;
    }

    /**
     * @param p1 new p1 value of line segment
     */
    public void setP1(Point p1) {
        this.p1 = p1;
    }

    /**
     * @return p2 value of line segment
     */
    public Point getP2() {
        return p2;
    }

    /**
     * @param p2 new p2 value of line segment
     */
    public void setP2(Point p2) {
        this.p2 = p2;
    }

    /**
     * @param p12 new p1 and p2 value of line segment
     */
    public void setP12(Point p12) {
        setP1(p12);
        setP2(p12);
    }

    // endregion

    // region length and copy

    /**
     * @return length of vector based on pythagoras
     */
    public double length() {
        return Math.sqrt(
            Math.pow(getP1().getX() - getP2().getX(), 2) +
                Math.pow(getP1().getY() - getP2().getY(), 2)
        );
    }

    /**
     * @return new line segment with same values
     */
    public LineSegment copy() {
        return new LineSegment(getP1().copy(), getP2().copy());
    }

    // endregion

    // region override

    @Override
    public int compareTo(LineSegment o) {
        Point min = List.of(p1, p2).stream().min(Point::compareTo).get();
        Point minO = List.of(o.p1, o.p2).stream().min(Point::compareTo).get();
        int i = min.compareTo(minO);
        if (i != 0) return i;
        Point max = List.of(p1, p2).stream().max(Point::compareTo).get();
        Point maxO = List.of(o.p1, o.p2).stream().max(Point::compareTo).get();
        return max.compareTo(maxO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineSegment that = (LineSegment) o;
        return Objects.equals(getP1(), that.getP1()) &&
            Objects.equals(getP2(), that.getP2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getP1(), getP2());
    }

    @Override
    public String toString() {
        return p1 + " " + p2;
    }

    // endregion
}
