package io.rala.math.geometry;

import io.rala.math.utils.Copyable;
import io.rala.math.utils.Movable;
import io.rala.math.utils.Rotatable;
import io.rala.math.utils.Validatable;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * class which holds a triangle in a 2d area with points a, b &amp; c
 */
public class Triangle implements Validatable, Movable<Triangle>, Rotatable<Triangle>,
    Copyable<Triangle>, Comparable<Triangle>, Serializable {
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
        setA(a);
        setB(b);
        setC(c);
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
     * @return {@code (A+B+C)/3}
     */
    public Point centerOfGravity() {
        return new Point(
            (getA().getX() + getB().getX() + getC().getX()) / 3,
            (getA().getY() + getB().getY() + getC().getY()) / 3
        );
    }

    // endregion

    // region edges and altitudes

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

    /**
     * @return line segment of altitude {@code a} starting at {@link #getA()}
     */
    public LineSegment altitudeA() {
        return getAltitude(edgeA().toLine(), getA());
    }

    /**
     * @return line segment of altitude {@code b} starting at {@link #getB()}
     */
    public LineSegment altitudeB() {
        return getAltitude(edgeB().toLine(), getB());
    }

    /**
     * @return line segment of altitude {@code c} starting at {@link #getC()}
     */
    public LineSegment altitudeC() {
        return getAltitude(edgeC().toLine(), getC());
    }

    /**
     * @param edge  edge to get altitude from
     * @param point point to get altitude from
     * @return altitude starting at {@code point} and ending at intersection with {@code edge}
     */
    protected LineSegment getAltitude(Line edge, Point point) {
        Line altitudeLine = edge.normal(point);
        return new LineSegment(point, altitudeLine.intersection(edge));
    }

    // endregion

    // region angles

    /**
     * calculates angle using law of cosines
     *
     * @return angle in {@code rad} at point {@code A}
     */
    public double angleAlpha() {
        double dividend = Math.pow(edgeA().length(), 2) -
            Math.pow(edgeB().length(), 2) -
            Math.pow(edgeC().length(), 2);
        double divisor = -2 * edgeB().length() * edgeC().length();
        return Math.acos(dividend / divisor);
    }

    /**
     * calculates angle using law of cosines
     *
     * @return angle in {@code rad} at point {@code B}
     */
    public double angleBeta() {
        double dividend = Math.pow(edgeB().length(), 2) -
            Math.pow(edgeA().length(), 2) -
            Math.pow(edgeC().length(), 2);
        double divisor = -2 * edgeA().length() * edgeC().length();
        return Math.acos(dividend / divisor);
    }

    /**
     * calculates angle using law of cosines
     *
     * @return angle in {@code rad} at point {@code C}
     */
    public double angleGamma() {
        double dividend = Math.pow(edgeC().length(), 2) -
            Math.pow(edgeA().length(), 2) -
            Math.pow(edgeB().length(), 2);
        double divisor = -2 * edgeA().length() * edgeB().length();
        return Math.acos(dividend / divisor);
    }

    // endregion

    // region area and circumference

    /**
     * @return {@code sqrt(s*(s-a)*(s-b)*(s-c))}
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
     * @return {@code a+b+c}
     */
    public double circumference() {
        return edgeA().length() +
            edgeB().length() +
            edgeC().length();
    }

    // endregion

    // region centroid and orthoCenter

    /**
     * @return {@code ( (xA+xB+xC)/3, (yA+yB+yC)/3 )}
     */
    public Point centroid() {
        return new Point(
            (getA().getX() + getB().getX() + getC().getX()) / 3,
            (getA().getY() + getB().getY() + getC().getY()) / 3
        );
    }

    /**
     * @return intersection from {@link #altitudeA()} and {@link #altitudeB()}
     * @see Line#intersection(Line)
     */
    public Point orthoCenter() {
        return altitudeA().toLine().intersection(altitudeB().toLine());
    }

    // endregion

    // region circumCircle and inCircle

    /**
     * @return circum circle of triangle
     */
    public Circle circumCircle() {
        return new Circle(circumCirclePoint(), circumCircleRadius());
    }

    /**
     * @return in circle of triangle
     */
    public Circle inCircle() {
        return new Circle(inCirclePoint(), inCircleRadius());
    }

    /**
     * @return {@code (a*b*c)/A}
     */
    protected double circumCircleRadius() {
        return (edgeA().length() *
            edgeB().length() *
            edgeC().length()) / (4 * area());
    }

    /**
     * @return {@code A/(r/2)}
     */
    protected double inCircleRadius() {
        return area() / (circumference() / 2);
    }

    /**
     * @return {@code ( (a2*(By-Cy)+b2*(Cy-Ay)+c2*(Ay-By))/d,
     * (a2*(Bx-Cx)+b2*(Cx-Ax)+c2*(Ax-Bx))/d )}
     * where {@code N2=Nx^2+Ny^2} with {@code N in [ABC]}
     * and {@code d=Ax*(By-Cy)+Bx*(Cy-Ay)+Cx*(Ay-By)}
     */
    protected Point circumCirclePoint() {
        double d = 2 * (
            getA().getX() * (getB().getY() - getC().getY()) +
                getB().getX() * (getC().getY() - getA().getY()) +
                getC().getX() * (getA().getY() - getB().getY())
        );
        double a2 = (Math.pow(getA().getX(), 2) + Math.pow(getA().getY(), 2));
        double b2 = (Math.pow(getB().getX(), 2) + Math.pow(getB().getY(), 2));
        double c2 = (Math.pow(getC().getX(), 2) + Math.pow(getC().getY(), 2));
        return new Point(
            (a2 * (getB().getY() - getC().getY()) +
                b2 * (getC().getY() - getA().getY()) +
                c2 * (getA().getY() - getB().getY())
            ) / d,
            (a2 * (getB().getX() - getC().getX()) +
                b2 * (getC().getX() - getA().getX()) +
                c2 * (getA().getX() - getB().getX())
            ) / d
        );
    }

    /**
     * @return {@code ( (a*xA+b*xB+c*xC)/p, (a*yA+b*yB+c*yC)/p )} where {@code p=a+b+c}
     */
    protected Point inCirclePoint() {
        double p = circumference();
        return new Point(
            (edgeA().length() * getA().getX() +
                edgeB().length() * getB().getX() +
                edgeC().length() * getC().getX()
            ) / p,
            (edgeA().length() * getA().getY() +
                edgeB().length() * getB().getY() +
                edgeC().length() * getC().getY()
            ) / p
        );
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
