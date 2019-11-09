package io.rala.math.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TriangleTest {
    // region constructors, getter and setter

    @Test
    void testConstructors() {
        assertTriangle(
            new Triangle(new Point(2), new Point(3), new Point(4)),
            new Point(2), new Point(3), new Point(4)
        );
    }

    @Test
    void testGetterAndSetter() {
        Triangle triangle = new Triangle(new Point(), new Point(), new Point());
        assertTriangle(triangle, new Point(), new Point(), new Point());
        triangle.setP1(new Point(1));
        assertTriangle(triangle, new Point(1), new Point(), new Point());
        triangle.setP2(new Point(2));
        assertTriangle(triangle, new Point(1), new Point(2), new Point());
        triangle.setP3(new Point(3));
        assertTriangle(triangle, new Point(1), new Point(2), new Point(3));
    }

    // endregion

    // region edges

    @Test
    void testEdges() {
        Triangle triangle = new Triangle(new Point(), new Point(1), new Point(2));
        Assertions.assertEquals(
            new LineSegment(new Point(), new Point(1)),
            triangle.getLineSegmentOfP1P2()
        );
        Assertions.assertEquals(
            new LineSegment(new Point(), new Point(2)),
            triangle.getLineSegmentOfP1P3()
        );
        Assertions.assertEquals(
            new LineSegment(new Point(1), new Point(2)),
            triangle.getLineSegmentOfP2P3()
        );
    }

    // endregion

    // region area, circumference, circumRadius, inRadius

    @Test
    void testGetArea() {
        Triangle triangle = new Triangle(new Point(), new Point(0, 1), new Point(1, 1));
        Assertions.assertEquals(0.49999999999999983, triangle.getArea());
    }

    @Test
    void testGetCircumference() {
        Triangle triangle = new Triangle(new Point(), new Point(0, 1), new Point(1, 1));
        Assertions.assertEquals(3.414213562373095, triangle.getCircumference());
    }

    @Test
    void testGetCircumRadius() {
        Triangle triangle = new Triangle(new Point(), new Point(0, 1), new Point(1, 1));
        Assertions.assertEquals(0.7071067811865478, triangle.getCircumRadius());
    }

    @Test
    void testGetInRadius() {
        Triangle triangle = new Triangle(new Point(), new Point(0, 1), new Point(1, 1));
        Assertions.assertEquals(0.29289321881345237, triangle.getInRadius());
    }

    // endregion

    // region copy

    @Test
    void testCopy() {
        Triangle triangle = new Triangle(new Point(1, 2), new Point(3, 4), new Point(5, 6));
        Assertions.assertEquals(triangle, triangle.copy());
    }

    // endregion

    // region override

    @Test
    void testEquals() {
        Triangle triangle = new Triangle(new Point(2), new Point(3), new Point(4));
        Assertions.assertEquals(
            triangle,
            new Triangle(new Point(2), new Point(3), new Point(4))
        );
        Assertions.assertNotEquals(
            triangle,
            new Triangle(new Point(3), new Point(2), new Point(4))
        );
    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(
            554632192,
            new Triangle(new Point(2), new Point(3), new Point(4)).hashCode()
        );
    }

    @Test
    void testToString() {
        Triangle triangle = new Triangle(new Point(2), new Point(3), new Point(4));
        Assertions.assertEquals("2.0:2.0 3.0:3.0 4.0:4.0", triangle.toString());
    }

    // endregion


    // region assert

    private static void assertTriangle(Triangle triangle, Point p1, Point p2, Point p3) {
        Assertions.assertEquals(p1, triangle.getP1());
        Assertions.assertEquals(p2, triangle.getP2());
        Assertions.assertEquals(p3, triangle.getP3());
    }

    // endregion
}
