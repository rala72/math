package io.rala.math.geometry;

import java.util.Objects;

/**
 * class which holds a triangle in a 2d area with points a, b &amp; c
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
    public LineSegment edgeA() {
        return new LineSegment(getB(), getC());
    }

    /**
     * @return line segment from a to c
     */
    public LineSegment edgeB() {
        return new LineSegment(getA(), getC());
    }

    /**
     * @return line segment from a to b
     */
    public LineSegment edgeC() {
        return new LineSegment(getA(), getB());
    }

    // endregion

    // region area, circumference, circumRadius and inRadius

    /**
     * @return <code>sqrt(s*(s-a)*(s-b)*(s-c))</code>
     */
    public double area() {
        final double s = circumference() / 2;
        return Math.sqrt(s *
            (s - edgeA().length()) *
            (s - edgeB().length()) *
            (s - edgeC().length())
        );
    }

    /**
     * @return <code>a+b+c</code>
     */
    public double circumference() {
        return edgeA().length() +
            edgeB().length() +
            edgeC().length();
    }

    /**
     * @return <code>(a*b*c)/A</code>
     */
    public double circumRadius() {
        return (edgeA().length() *
            edgeB().length() *
            edgeC().length()) / (4 * area());
    }

    /**
     * @return <code>A/(r/2)</code>
     */
    public double inRadius() {
        return area() / (circumference() / 2);
    }

    // endregion

    // region move and copy

    /**
     * @param vector vector to use for movement
     * @return new triangle moved by given vector
     */
    public Triangle move(Vector vector) {
        return new Triangle(getA().move(vector), getB().move(vector), getC().move(vector));
    }

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
