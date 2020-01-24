package io.rala.math.testUtils.assertion;

import io.rala.math.geometry.*;
import org.junit.jupiter.api.Assertions;

/**
 * assertions for {@link io.rala.math.geometry} package
 */
public class GeometryAssertions {
    public static final double DELTA = 0.00001;

    private GeometryAssertions() {
    }

    // region circle

    /**
     * @see Circle#Circle()
     * @see #assertCircle(Circle, Point)
     */
    public static void assertCircle(Circle circle) {
        assertCircle(circle, new Point());
    }

    /**
     * @see Circle#Circle(Point, double)
     * @see #assertCircle(Circle, Point, double)
     */
    public static void assertCircle(Circle circle, Point center) {
        assertCircle(circle, center, 1);
    }

    /**
     * @see Circle#Circle(double)
     * @see #assertCircle(Circle, Point, double)
     */
    @SuppressWarnings("SameParameterValue")
    public static void assertCircle(Circle circle, double radius) {
        assertCircle(circle, new Point(), radius);
    }

    /**
     * asserts that circle has expected values
     */
    public static void assertCircle(Circle circle, Point center, double radius) {
        Assertions.assertEquals(center, circle.getCenter(), "center is invalid");
        Assertions.assertEquals(radius, circle.getRadius(), DELTA, "radius is invalid");
    }

    // endregion

    // region lineSegment

    /**
     * @see LineSegment#LineSegment(Point)
     * @see #assertLineSegment(LineSegment, Point, Point)
     */
    public static void assertLineSegment(LineSegment lineSegment, Point b) {
        assertLineSegment(lineSegment, new Point(), b);
    }

    /**
     * asserts that lineSegment has expected values
     */
    public static void assertLineSegment(LineSegment lineSegment, Point a, Point b) {
        Assertions.assertEquals(a, lineSegment.getA(), "a is invalid");
        Assertions.assertEquals(b, lineSegment.getB(), "b is invalid");
    }

    // endregion

    // region line

    /**
     * asserts that line has expected values
     */
    public static void assertLine(Line line, double m, double b) {
        Assertions.assertEquals(m, line.getM(), "m is invalid");
        Assertions.assertEquals(b, line.getB(), "b is invalid");
    }

    // endregion

    // region point

    /**
     * @see Point#Point()
     * @see #assertPoint(Point, double)
     */
    public static void assertPoint(Point point) {
        assertPoint(point, 0);
    }

    /**
     * @see Point#Point(double, double)
     * @see #assertPoint(Point, double, double)
     */
    public static void assertPoint(Point point, double xy) {
        assertPoint(point, xy, xy);
    }

    /**
     * asserts that point has expected values
     */
    public static void assertPoint(Point point, double x, double y) {
        Assertions.assertEquals(x, point.getX(), DELTA, "x is invalid");
        Assertions.assertEquals(y, point.getY(), DELTA, "y is invalid");
    }

    // endregion

    // region rect

    /**
     * @see Rect#Rect(Point, Point, double)
     * @see #assertRect(Rect, Point, Point, double)
     */
    public static void assertRect(Rect rect, double height, double width) {
        assertRect(rect, new Point(), new Point(width, 0), height);
    }

    /**
     * asserts that rect has expected values
     */
    public static void assertRect(Rect rect, Point a, Point b, double size) {
        Assertions.assertEquals(a, rect.getA(), "a is invalid");
        Assertions.assertEquals(b, rect.getB(), "b is invalid");
        Assertions.assertEquals(size, rect.getSize(), "size is invalid");
    }

    // endregion

    // region triangle

    /**
     * asserts that triangle has expected values
     */
    public static void assertTriangle(Triangle triangle, Point a, Point b, Point c) {
        Assertions.assertEquals(a, triangle.getA(), "a is invalid");
        Assertions.assertEquals(b, triangle.getB(), "b is invalid");
        Assertions.assertEquals(c, triangle.getC(), "c is invalid");
    }

    // endregion

    // region vector

    /**
     * @see Vector#Vector()
     * @see #assertVector(Vector, double)
     */
    public static void assertVector(Vector vector) {
        assertVector(vector, 0);
    }

    /**
     * @see Vector#Vector(double, double)
     * @see #assertVector(Vector, double, double)
     */
    public static void assertVector(Vector vector, double xy) {
        assertVector(vector, xy, xy);
    }

    /**
     * asserts that vector has expected values
     */
    public static void assertVector(Vector vector, double x, double y) {
        Assertions.assertEquals(x, vector.getX(), DELTA, "x is invalid");
        Assertions.assertEquals(y, vector.getY(), DELTA, "y is invalid");
    }

    // endregion
}
