package io.rala.math.testUtils.assertion;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.geometry.*;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.Assertions;
import org.opentest4j.AssertionFailedError;

/**
 * assertions for {@link io.rala.math.geometry} package
 */
@SuppressWarnings("unused")
public class GeometryAssertions {
    public static final double DELTA = 0.00001;
    private static final AbstractArithmetic<Number> ARITHMETIC = new TestAbstractArithmetic();

    private GeometryAssertions() {
    }

    // region circle

    /**
     * @see Circle#Circle(AbstractArithmetic)
     * @see #assertCircle(Circle, Point)
     */
    public static void assertCircle(Circle<Number> circle) {
        assertCircle(circle, new Point<>(ARITHMETIC));
    }

    /**
     * @see Circle#Circle(AbstractArithmetic, Point, Number)
     * @see #assertCircle(Circle, Point, Number)
     */
    public static void assertCircle(Circle<Number> circle, Point<Number> center) {
        assertCircle(circle, center, 1);
    }

    /**
     * @see Circle#Circle(AbstractArithmetic, Number)
     * @see #assertCircle(Circle, Point, Number)
     */
    public static void assertCircle(Circle<Number> circle, Number radius) {
        assertCircle(circle, new Point<>(ARITHMETIC), radius);
    }

    /**
     * asserts that circle has expected values
     */
    public static void assertCircle(Circle<Number> circle, Point<Number> center, Number radius) {
        assertEquals(center, circle.getCenter(), "center is invalid");
        Assertions.assertEquals(radius.doubleValue(), circle.getRadius().doubleValue(),
            DELTA, "radius is invalid"
        );
    }

    // endregion

    // region lineSegment

    /**
     * @see LineSegment#LineSegment(AbstractArithmetic, Point)
     * @see #assertLineSegment(LineSegment, Point, Point)
     */
    public static void assertLineSegment(LineSegment<Number> lineSegment, Point<Number> b) {
        assertLineSegment(lineSegment, new Point<>(ARITHMETIC), b);
    }

    /**
     * asserts that lineSegment has expected values
     */
    public static void assertLineSegment(
        LineSegment<Number> lineSegment, Point<Number> a, Point<Number> b
    ) {
        assertEquals(a, lineSegment.getA(), "a is invalid");
        assertEquals(b, lineSegment.getB(), "b is invalid");
    }

    // endregion

    // region line

    /**
     * asserts that line has expected values
     */
    public static void assertLine(Line<Number> line, Number m, Number b) {
        Assertions.assertEquals(m, line.getM(), "m is invalid");
        Assertions.assertEquals(b, line.getB(), "b is invalid");
    }

    // endregion

    // region point

    /**
     * @see Point#Point(AbstractArithmetic)
     * @see #assertPoint(Point, Number)
     */
    public static void assertPoint(Point<Number> point) {
        assertPoint(point, 0);
    }

    /**
     * @see Point#Point(AbstractArithmetic, Number, Number)
     * @see #assertPoint(Point, Number, Number)
     */
    public static void assertPoint(Point<Number> point, Number xy) {
        assertPoint(point, xy, xy);
    }

    /**
     * asserts that point has expected values
     */
    public static void assertPoint(Point<Number> point, Number x, Number y) {
        Assertions.assertEquals(x.doubleValue(), point.getX().doubleValue(),
            DELTA, "x is invalid"
        );
        Assertions.assertEquals(y.doubleValue(), point.getY().doubleValue(),
            DELTA, "y is invalid"
        );
    }

    // endregion

    // region rect

    /**
     * @see Rect#Rect(AbstractArithmetic, Point, Point, Number)
     * @see #assertRect(Rect, Point, Point, Number)
     */
    public static void assertRect(Rect<Number> rect, Number height, Number width) {
        assertRect(rect,
            new Point<>(ARITHMETIC),
            new Point<>(ARITHMETIC, width, 0),
            height
        );
    }

    /**
     * asserts that rect has expected values
     */
    public static void assertRect(
        Rect<Number> rect, Point<Number> a, Point<Number> b, Number size
    ) {
        assertEquals(a, rect.getA(), "a is invalid");
        assertEquals(b, rect.getB(), "b is invalid");
        Assertions.assertEquals(size.doubleValue(), rect.getSize().doubleValue(),
            DELTA, "size is invalid"
        );
    }

    // endregion

    // region triangle

    /**
     * asserts that triangle has expected values
     */
    public static void assertTriangle(
        Triangle<Number> triangle, Point<Number> a, Point<Number> b, Point<Number> c
    ) {
        assertEquals(a, triangle.getA(), "a is invalid");
        assertEquals(b, triangle.getB(), "b is invalid");
        assertEquals(c, triangle.getC(), "c is invalid");
    }

    // endregion

    // region vector

    /**
     * @see Vector#Vector(AbstractArithmetic)
     * @see #assertVector(Vector, Number)
     */
    public static void assertVector(Vector<Number> vector) {
        assertVector(vector, 0);
    }

    /**
     * @see Vector#Vector(AbstractArithmetic, Number, Number)
     * @see #assertVector(Vector, Number, Number)
     */
    public static void assertVector(Vector<Number> vector, Number xy) {
        assertVector(vector, xy, xy);
    }

    /**
     * asserts that vector has expected values
     */
    public static void assertVector(Vector<Number> vector, Number x, Number y) {
        Assertions.assertEquals(x.doubleValue(), vector.getX().doubleValue(), DELTA, "x is invalid");
        Assertions.assertEquals(y.doubleValue(), vector.getY().doubleValue(), DELTA, "y is invalid");
    }

    // endregion

    // region private assertEquals

    private static void assertEquals(Point<Number> expected, Point<Number> actual) {
        assertEquals(actual, expected, null);
    }

    private static void assertEquals(
        Point<Number> expected, Point<Number> actual, String message
    ) {
        if (message == null) message = "point is invalid";
        try {
            assertPoint(actual,
                expected.getX().doubleValue(),
                expected.getY().doubleValue()
            );
        } catch (AssertionFailedError error) { // better way?
            Assertions.fail(message + " [" + error.getMessage() + "]", error);
        }
    }

    // endregion
}
