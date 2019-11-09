package io.rala.math.geometry;

import java.util.Objects;

/**
 * triangle in 2d area
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Triangle {
    // region attributes

    private Point p1;
    private Point p2;
    private Point p3;

    // endregion

    // region constructors

    /**
     * creates a new triangle with given p1, p2 and p3
     *
     * @param p1 p1 of triangle
     * @param p2 p2 of triangle
     * @param p3 p3 of triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    // endregion

    // region getter and setter

    /**
     * @return p1 of triangle
     */
    public Point getP1() {
        return p1;
    }

    /**
     * @param p1 new p1 of triangle
     */
    public void setP1(Point p1) {
        this.p1 = p1;
    }

    /**
     * @return p2 of triangle
     */
    public Point getP2() {
        return p2;
    }

    /**
     * @param p2 new p2 of triangle
     */
    public void setP2(Point p2) {
        this.p2 = p2;
    }

    /**
     * @return p3 of triangle
     */
    public Point getP3() {
        return p3;
    }

    /**
     * @param p3 new p3 of triangle
     */
    public void setP3(Point p3) {
        this.p3 = p3;
    }

    // endregion

    // region edges
    // line segments

    /**
     * @return line segment from p1 to p2
     */
    public LineSegment getLineSegmentOfP1P2() {
        return new LineSegment(getP1(), getP2());
    }

    /**
     * @return line segment from p1 to p3
     */
    public LineSegment getLineSegmentOfP1P3() {
        return new LineSegment(getP1(), getP3());
    }

    /**
     * @return line segment from p2 to p3
     */
    public LineSegment getLineSegmentOfP2P3() {
        return new LineSegment(getP2(), getP3());
    }

    // endregion

    // region area, circumference, circumRadius and inRadius

    /**
     * @return <code>sqrt(s*(s-a)*(s-b)*(s-c))</code>
     */
    public double getArea() {
        final double s = getCircumference() / 2;
        return Math.sqrt(s *
            (s - getLineSegmentOfP1P2().length()) *
            (s - getLineSegmentOfP1P3().length()) *
            (s - getLineSegmentOfP2P3().length())
        );
    }

    /**
     * @return <code>a+b+c</code>
     */
    public double getCircumference() {
        return getLineSegmentOfP1P2().length() +
            getLineSegmentOfP1P3().length() +
            getLineSegmentOfP2P3().length();
    }

    /**
     * @return <code>(a*b*c)/A</code>
     */
    public double getCircumRadius() {
        return (getLineSegmentOfP1P2().length() *
            getLineSegmentOfP1P3().length() *
            getLineSegmentOfP2P3().length()) / (4 * getArea());
    }

    /**
     * @return <code>A/(r/2)</code>
     */
    public double getInRadius() {
        return getArea() / (getCircumference() / 2);
    }

    // endregion

    // region copy

    /**
     * @return new triangle with same values
     */
    public Triangle copy() {
        return new Triangle(getP1().copy(), getP2().copy(), getP3().copy());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return Objects.equals(getP1(), triangle.getP1()) &&
            Objects.equals(getP2(), triangle.getP2()) &&
            Objects.equals(getP3(), triangle.getP3());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getP1(), getP2(), getP3());
    }

    @Override
    public String toString() {
        return getP1() + " " + getP2() + " " + getP3();
    }

    // endregion
}
