package io.rala.math.geometry;

import io.rala.math.utils.Copyable;
import io.rala.math.utils.Validatable;

import java.io.Serializable;
import java.util.Objects;

/**
 * class which holds a line in a 2d area with m &amp; b<br>
 * {@code y=m*x+b}<br>
 * if line is vertical m is considered to be {@link Double#NaN}<br>
 * {@code y=b}
 */
public class Line implements Validatable, Copyable<Line>, Comparable<Line>, Serializable {
    // region attributes

    private double m;
    private double b;

    // endregion

    // region constructor

    /**
     * creates a vertical line storing
     * {@code m} as {@link Double#NaN} and
     * {@code b} as {@code x}
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
        setM(m);
        setB(b);
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
     * @return {@code true} if {@link #getM()} returns {@code 0}
     */
    public boolean isHorizontal() {
        return getM() == 0;
    }

    /**
     * @return {@code true} if {@link #getM()} returns {@link Double#NaN}
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

    // region normal

    /**
     * @return normal line without changing {@link #getB()}
     */
    public Line normal() {
        double m = isVertical() ? 0 :
            isHorizontal() ? Double.NaN :
                -1 / getM();
        return new Line(m, getB());
    }

    /**
     * @param point point on line
     * @return normal line through given point
     * @see #normal()
     */
    public Line normal(Point point) {
        Line normal = normal();
        normal.setB(
            normal.isVertical() ? point.getX() :
                point.getY() - normal.getM() * point.getX()
        );
        return normal;
    }

    // endregion

    // region intersection

    /**
     * @param line line to check if intersection exists
     * @return {@code true} if {@code m} is not equal
     */
    public boolean hasIntersection(Line line) {
        return (!isVertical() || !line.isVertical()) && getM() != line.getM();
    }

    /**
     * @param line line to intersect
     * @return intersection or {@code null}
     * if {@link #hasIntersection(Line)} is {@code false}
     */
    public Point intersection(Line line) {
        if (!hasIntersection(line)) return null;
        if (isVertical())
            return new Point(getB(), line.calculateY(getB()));
        if (line.isVertical())
            return new Point(line.getB(), calculateY(line.getB()));
        double x = -(getB() - line.getB()) / (getM() - line.getM());
        return new Point(x, calculateY(x));
    }

    /**
     * @param line line to intersect
     * @return intersection angle in {@code rad} or {@link Double#NaN}
     * if there is no intersection
     */
    public double intersectionAngle(Line line) {
        if (!hasIntersection(line)) return Double.NaN;
        if (isVertical() || line.isVertical()) {
            // calculated like y-axis
            double m = isVertical() ? line.getM() : getM();
            return Math.PI / 2 - Math.atan(Math.abs(m));
        }
        double tan = (getM() - line.getM()) / (1 + getM() * line.getM());
        return Math.atan(Math.abs(tan));
    }

    // endregion

    // region hasPoint

    /**
     * @param point point to check if on line
     * @return {@code true} if point is on line
     */
    public boolean hasPoint(Point point) {
        return isVertical() && getB() == point.getX() ||
            calculateX(point.getY()) == point.getX() ||
            calculateY(point.getX()) == point.getY();
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

    // region isValid, copy

    @Override
    public boolean isValid() {
        return !Double.isInfinite(getM()) && Double.isFinite(getB());
    }

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
        return "y=" + (isVertical() ? "" :
            getM() + "*x" + (0 <= getB() ? "+" : "")
        ) + getB();
    }

    @Override
    public int compareTo(Line o) {
        int compare = Double.compare(getM(), o.getM());
        if (compare != 0) return compare;
        return Double.compare(getB(), o.getB());
    }

    // endregion
}
