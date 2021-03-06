package io.rala.math.testUtils.assertion;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.geometry.*;
import org.opentest4j.AssertionFailedError;

import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * assertions for {@link io.rala.math.geometry} package
 */
@SuppressWarnings("unused")
public class GeometryAssertions {
    public static final double DELTA = 0.00001;
    public static final MathContext CONTEXT = new MathContext(10);

    private GeometryAssertions() {
    }

    // region circle

    /**
     * @see Circle#Circle(AbstractArithmetic)
     * @see #assertCircle(Circle, Point)
     */
    public static <T extends Number> void assertCircle(Circle<T> circle) {
        assertCircle(circle, new Point<>(circle.getArithmetic()));
    }

    /**
     * @see Circle#Circle(AbstractArithmetic, Point, Number)
     * @see #assertCircle(Circle, Point, Number)
     */
    public static <T extends Number> void assertCircle(Circle<T> circle, Point<T> center) {
        assertCircle(circle, center, circle.getArithmetic().one());
    }

    /**
     * @see Circle#Circle(AbstractArithmetic, Number)
     * @see #assertCircle(Circle, Point, Number)
     */
    public static <T extends Number> void assertCircle(Circle<T> circle, T radius) {
        assertCircle(circle, new Point<>(circle.getArithmetic()), radius);
    }

    /**
     * asserts that circle has expected values
     */
    public static <T extends Number> void assertCircle(
        Circle<T> circle, Point<T> center, T radius
    ) {
        assertEqualsPoint(center, circle.getCenter(), "center is invalid");
        assertEquals(radius.doubleValue(), circle.getRadius().doubleValue(),
            DELTA, "radius is invalid"
        );
    }

    // endregion

    // region lineSegment

    /**
     * @see LineSegment#LineSegment(AbstractArithmetic, Point)
     * @see #assertLineSegment(LineSegment, Point, Point)
     */
    public static <T extends Number> void assertLineSegment(
        LineSegment<T> lineSegment, Point<T> b
    ) {
        assertLineSegment(lineSegment, new Point<>(lineSegment.getArithmetic()), b);
    }

    /**
     * asserts that lineSegment has expected values
     */
    public static <T extends Number> void assertLineSegment(
        LineSegment<T> lineSegment, Point<T> a, Point<T> b
    ) {
        assertEqualsPoint(a, lineSegment.getA(), "a is invalid");
        assertEqualsPoint(b, lineSegment.getB(), "b is invalid");
    }

    // endregion

    // region line

    /**
     * asserts that line has expected values
     */
    public static <T extends Number> void assertLine(Line<T> line, T m, T b) {
        assertEquals(m, line.getM(), "m is invalid");
        assertEquals(b, line.getB(), "b is invalid");
    }

    // endregion

    // region point

    /**
     * @see Point#Point(AbstractArithmetic)
     * @see #assertPoint(Point, Number)
     */
    public static <T extends Number> void assertPoint(Point<T> point) {
        assertPoint(point, point.getArithmetic().zero());
    }

    /**
     * @see Point#Point(AbstractArithmetic, Number, Number)
     * @see #assertPoint(Point, Number, Number)
     */
    public static <T extends Number> void assertPoint(Point<T> point, T xy) {
        assertPoint(point, xy, xy);
    }

    /**
     * asserts that point has expected values
     */
    public static <T extends Number> void assertPoint(Point<T> point, T x, T y) {
        assertEquals(x.doubleValue(), point.getX().doubleValue(),
            DELTA, "x is invalid"
        );
        assertEquals(y.doubleValue(), point.getY().doubleValue(),
            DELTA, "y is invalid"
        );
    }

    // endregion

    // region rect

    /**
     * @see Rect#Rect(AbstractArithmetic, Point, Point, Number)
     * @see #assertRect(Rect, Point, Point, Number)
     */
    public static <T extends Number> void assertRect(Rect<T> rect, T height, T width) {
        assertRect(rect,
            new Point<>(rect.getArithmetic()),
            new Point<>(rect.getArithmetic(), width, rect.getArithmetic().zero()),
            height
        );
    }

    /**
     * asserts that rect has expected values
     */
    public static <T extends Number> void assertRect(
        Rect<T> rect, Point<T> a, Point<T> b, T size
    ) {
        assertEqualsPoint(a, rect.getA(), "a is invalid");
        assertEqualsPoint(b, rect.getB(), "b is invalid");
        assertEquals(size.doubleValue(), rect.getSize().doubleValue(),
            DELTA, "size is invalid"
        );
    }

    // endregion

    // region triangle

    /**
     * asserts that triangle has expected values
     */
    public static <T extends Number> void assertTriangle(
        Triangle<T> triangle, Point<T> a, Point<T> b, Point<T> c
    ) {
        assertEqualsPoint(a, triangle.getA(), "a is invalid");
        assertEqualsPoint(b, triangle.getB(), "b is invalid");
        assertEqualsPoint(c, triangle.getC(), "c is invalid");
    }

    // endregion

    // region vector

    /**
     * @see Vector#Vector(AbstractArithmetic)
     * @see #assertVector(Vector, Number)
     */
    public static <T extends Number> void assertVector(Vector<T> vector) {
        assertVector(vector, vector.getArithmetic().zero());
    }

    /**
     * @see Vector#Vector(AbstractArithmetic, Number, Number)
     * @see #assertVector(Vector, Number, Number)
     */
    public static <T extends Number> void assertVector(Vector<T> vector, T xy) {
        assertVector(vector, xy, xy);
    }

    /**
     * asserts that vector has expected values
     */
    public static <T extends Number> void assertVector(Vector<T> vector, T x, T y) {
        assertEquals(
            x.doubleValue(), vector.getX().doubleValue(),
            DELTA, "x is invalid"
        );
        assertEquals(
            y.doubleValue(), vector.getY().doubleValue(),
            DELTA, "y is invalid"
        );
    }

    // endregion

    // region private assertEquals

    private static void assertEqualsPoint(Point<Number> expected, Point<Number> actual) {
        assertEqualsPoint(actual, expected, null);
    }

    private static <T extends Number> void assertEqualsPoint(
        Point<T> expected, Point<T> actual, String message
    ) {
        if (message == null) message = "point is invalid";
        try {
            assertPoint(actual,
                expected.getX(),
                expected.getY()
            );
        } catch (AssertionFailedError error) { // better way?
            fail(message + " [" + error.getMessage() + "]", error);
        }
    }

    // endregion
}
