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
