package io.rala.math.geometry;

import io.rala.math.utils.Copyable;

import java.util.Objects;

/**
 * class which holds a line in a 2d area with m &amp; b<br>
 * <code>y=m*x+b</code><br>
 * if line is vertical m is considered to be {@link Double#NaN}<br>
 * <code>y=b</code>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Line implements Copyable<Line>, Comparable<Line> {
    // region attributes

    private double m;
    private double b;

    // endregion

    // region constructor

    /**
     * creates a vertical line storing
     * <code>m</code> as {@link Double#NaN} and
     * <code>b</code> as <code>x</code>
     *
     * @param x x value of line
     */
    public Line(double x) {
        this(Double.NaN, x);
    }

    /**
     * creates a line with given slope/gradient and y-intercept
     *
     * @param m slope/gradient of line
     * @param b y-intercept of line
     */
    public Line(double m, double b) {
        this.m = m;
        this.b = b;
    }

    // endregion

    // region getter and setter

    /**
     * @return m value of line
     */
    public double getM() {
        return m;
    }

    /**
     * @param m new m value of line
     */
    public void setM(double m) {
        this.m = m;
    }

    /**
     * @return b value of line
     */
    public double getB() {
        return b;
    }

    /**
     * @param b new b value of line
     */
    public void setB(double b) {
        this.b = b;
    }

    // endregion

    // region isHorizontal and isVertical

    /**
     * @return <code>true</code> if {@link #getM()} returns <code>0</code>
     */
    public boolean isHorizontal() {
        return getM() == 0;
    }

    /**
     * @return <code>true</code> if {@link #getM()} returns {@link Double#NaN}
     */
    public boolean isVertical() {
        return Double.isNaN(getM());
    }


    // endregion

    // region calculateX and calculateY

    /**
     * calculates x value of provided y value
     *
     * @param y y value to get x value
     * @return x=(y-b)/m
     */
    public double calculateX(double y) {
        return (y - getB()) / getM();
    }

    /**
     * calculates y value of provided x value
     *
     * @param x x value to get y value
     * @return y=m*x+b
     */
    public double calculateY(double x) {
        return getM() * x + getB();
    }

    // endregion

    // region intersection

    /**
     * @param line line to check if intersection exists
     * @return <code>true</code> if <code>m</code> is not equal
     */
    public boolean hasIntersection(Line line) {
        return getM() != line.getM();
    }

    /**
     * @param line line to intersect
     * @return intersection or <code>null</code>
     * if {@link #hasIntersection(Line)} is <code>false</code>
     */
    public Point intersection(Line line) {
        if (!hasIntersection(line)) return null;
        double x = -(getB() - line.getB()) / (getM() - line.getM());
        return new Point(x, calculateY(x));
    }

    // endregion

    // region toLineSegment

    /**
     * @param fromX starting index of line segment
     * @param toX   end index of line segment
     * @return new line segment instance in given boundaries
     * @see #calculateY(double)
     */
    public LineSegment toLineSegmentUsingX(double fromX, double toX) {
        return new LineSegment(
            new Point(fromX, calculateY(fromX)),
            new Point(toX, calculateY(toX))
        );
    }

    /**
     * @param fromY starting index of line segment
     * @param toY   end index of line segment
     * @return new line segment instance in given boundaries
     * @see #calculateX(double)
     */
    public LineSegment toLineSegmentUsingY(double fromY, double toY) {
        return new LineSegment(
            new Point(calculateX(fromY), fromY),
            new Point(calculateX(toY), toY)
        );
    }

    // endregion

    // region copy

    @Override
    public Line copy() {
        return new Line(getM(), getB());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Double.compare(line.getM(), getM()) == 0 &&
            Double.compare(line.getB(), getB()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getM(), getB());
    }

    @Override
    public String toString() {
        return "y=" + getM() + "*x" + (0 <= getB() ? "+" : "") + getB();
    }

    @Override
    public int compareTo(Line o) {
        int compare = Double.compare(getM(), o.getM());
        if (compare != 0) return compare;
        return Double.compare(getB(), o.getB());
    }

    // endregion
}
