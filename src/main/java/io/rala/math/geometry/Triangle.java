package io.rala.math.geometry;

import java.util.Objects;

/**
 * triangle in 2d area
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Triangle {
    // region attributes

    private Point a;
    private Point b;
    private Point c;

    // endregion

    // region constructors

    /**
     * creates a new triangle with given a, b and c
     *
     * @param a a of triangle
     * @param b b of triangle
     * @param c c of triangle
     */
    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    // endregion

    // region getter and setter

    /**
     * @return a of triangle
     */
    public Point getA() {
        return a;
    }

    /**
     * @param a new a of triangle
     */
    public void setA(Point a) {
        this.a = a;
    }

    /**
     * @return b of triangle
     */
    public Point getB() {
        return b;
    }

    /**
     * @param b new b of triangle
     */
    public void setB(Point b) {
        this.b = b;
    }

    /**
     * @return c of triangle
     */
    public Point getC() {
        return c;
    }

    /**
     * @param c new c of triangle
     */
    public void setC(Point c) {
        this.c = c;
    }

    // endregion

    // region edges
    // line segments

    /**
     * @return line segment from b to c
     */
    public LineSegment getLineSegmentA() {
        return new LineSegment(getB(), getC());
    }

    /**
     * @return line segment from a to c
     */
    public LineSegment getLineSegmentB() {
        return new LineSegment(getA(), getC());
    }

    /**
     * @return line segment from a to b
     */
    public LineSegment getLineSegmentC() {
        return new LineSegment(getA(), getB());
    }

    // endregion

    // region area, circumference, circumRadius and inRadius

    /**
     * @return <code>sqrt(s*(s-a)*(s-b)*(s-c))</code>
     */
    public double getArea() {
        final double s = getCircumference() / 2;
        return Math.sqrt(s *
            (s - getLineSegmentA().length()) *
            (s - getLineSegmentB().length()) *
            (s - getLineSegmentC().length())
        );
    }

    /**
     * @return <code>a+b+c</code>
     */
    public double getCircumference() {
        return getLineSegmentA().length() +
            getLineSegmentB().length() +
            getLineSegmentC().length();
    }

    /**
     * @return <code>(a*b*c)/A</code>
     */
    public double getCircumRadius() {
        return (getLineSegmentA().length() *
            getLineSegmentB().length() *
            getLineSegmentC().length()) / (4 * getArea());
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
        return new Triangle(getA().copy(), getB().copy(), getC().copy());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return Objects.equals(getA(), triangle.getA()) &&
            Objects.equals(getB(), triangle.getB()) &&
            Objects.equals(getC(), triangle.getC());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getA(), getB(), getC());
    }

    @Override
    public String toString() {
        return getA() + " " + getB() + " " + getC();
    }

    // endregion
}
