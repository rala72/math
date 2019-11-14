package io.rala.math.geometry;

import io.rala.math.utils.Copyable;
import io.rala.math.utils.Movable;
import io.rala.math.utils.Rotatable;
import io.rala.math.utils.Validatable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * class which holds a triangle in a 2d area with points a, b &amp; c
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Triangle implements Validatable, Movable<Triangle>, Rotatable<Triangle>,
    Copyable<Triangle>, Comparable<Triangle> {
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

    // region vertexes

    /**
     * @return <code>(A+B+C)/3</code>
     */
    public Point centerOfGravity() {
        return new Point(
            (getA().getX() + getB().getX() + getC().getX()) / 3,
            (getA().getY() + getB().getY() + getC().getY()) / 3
        );
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

    // region isValid, move, rotate and copy

    @Override
    public boolean isValid() {
        return getA().isValid() && getB().isValid() && getC().isValid() &&
            edgeA().length() < edgeB().length() + edgeC().length() &&
            edgeB().length() < edgeA().length() + edgeC().length() &&
            edgeC().length() < edgeA().length() + edgeB().length();
    }

    @Override
    public Triangle move(Vector vector) {
        return new Triangle(getA().move(vector), getB().move(vector), getC().move(vector));
    }

    @Override
    public Triangle rotate(Point center, double phi) {
        return new Triangle(
            getA().rotate(center, phi),
            getB().rotate(center, phi),
            getC().rotate(center, phi)
        );
    }

    @Override
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

    @Override
    public int compareTo(Triangle o) {
        int compare = Double.compare(area(), o.area());
        if (compare != 0) return compare;
        List<Point> s = List.of(getA(), getB(), getC())
            .stream().sorted().collect(Collectors.toList());
        List<Point> sO = List.of(o.getA(), o.getB(), o.getC())
            .stream().sorted().collect(Collectors.toList());
        for (int i = 0; i < s.size(); i++) {
            Point p = s.get(i);
            Point pO = sO.get(i);
            int c = p.compareTo(pO);
            if (c != 0) return c;
        }
        return 0;
    }

    // endregion
}
