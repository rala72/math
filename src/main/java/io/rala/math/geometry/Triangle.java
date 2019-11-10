package io.rala.math.geometry;

import io.rala.math.utils.Copyable;
import io.rala.math.utils.Movable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * class which holds a triangle in a 2d area with points a, b &amp; c
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Triangle implements Copyable<Triangle>, Movable<Triangle>, Comparable<Triangle> {
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

    public LineSegment getHeightA() {
        double aL = edgeA().length();
        double a_ = Math.sqrt(
            Math.pow(edgeC().length(), 2) -
                Math.pow(getHeightALength(), 2)
        );
        double c_ = Math.sqrt(
            Math.pow(aL, 2) - Math.pow(getHeightCLength(), 2)
        );
        double lengthRatio = a_ < aL ? a_ / aL : aL / a_;
        double requestedX = getHeightCLength() * lengthRatio;
        double requestedY = c_ / lengthRatio;
        return new LineSegment(getA(),
            new Point(getA().getX() + requestedX, getA().getY() + requestedY)
        );
    }

    public LineSegment getHeightB() {
        double bL = edgeB().length();
        double b_ = Math.sqrt(
            Math.pow(edgeC().length(), 2) -
                Math.pow(getHeightBLength(), 2)
        );
        double c_ = Math.sqrt(
            Math.pow(bL, 2) -
                Math.pow(getHeightCLength(), 2)
        );
        double lengthRatio = b_ < bL ? b_ / bL : bL / b_;
        double requestedX = c_ * lengthRatio;
        double requestedY = getHeightCLength() * lengthRatio;
        return new LineSegment(getB(),
            new Point(getB().getX() + requestedX, getB().getY() - requestedY)
        );
    }

    public LineSegment getHeightC() {
        double bC = edgeC().length();
        double c_ = Math.sqrt(
            Math.pow(edgeA().length(), 2) -
                Math.pow(getHeightCLength(), 2)
        );
        double a_ = Math.sqrt(
            Math.pow(bC, 2) -
                Math.pow(getHeightALength(), 2)
        );
        double lengthRatio = c_ < bC ? c_ / bC : bC / c_;
        double requestedX = a_ / lengthRatio;
        double requestedY = getHeightALength() * lengthRatio;
        return new LineSegment(getC(),
            new Point(getC().getX() - requestedX, getC().getY() + requestedY)
        );
    }

    protected double getHeightALength() {
        return 2 * area() / edgeA().length();
    }

    protected double getHeightBLength() {
        return 2 * area() / edgeB().length();
    }

    protected double getHeightCLength() {
        return 2 * area() / edgeC().length();
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

    @Override
    public Triangle move(Vector vector) {
        return new Triangle(getA().move(vector), getB().move(vector), getC().move(vector));
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
