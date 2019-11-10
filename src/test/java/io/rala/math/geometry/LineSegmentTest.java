package io.rala.math.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LineSegmentTest {
    // region constructors, getter and setter

    @Test
    void testConstructors() {
        assertLineSegment(new LineSegment());
        assertLineSegment(new LineSegment(new Point(1)), new Point(1));
        assertLineSegment(new LineSegment(new Point(2), new Point(2)), new Point(2));
        assertLineSegment(
            new LineSegment(
                new Point(2, 2), new Point(3, 3)
            ),
            new Point(2), new Point(3)
        );
    }

    @Test
    void testGetterAndSetter() {
        LineSegment lineSegment = new LineSegment();
        assertLineSegment(lineSegment);
        lineSegment.setA(new Point(1));
        assertLineSegment(lineSegment, new Point(1), new Point());
        lineSegment.setB(new Point(2));
        assertLineSegment(lineSegment, new Point(1), new Point(2));
        lineSegment.setAB(new Point(3));
        assertLineSegment(lineSegment, new Point(3));
    }

    // endregion

    // region length and copy

    @Test
    void testLength() {
        Assertions.assertEquals(0, new LineSegment(new Point(1, 2)).length());
        Assertions.assertEquals(1.4142135623730951, new LineSegment(new Point(1, 2), new Point(2, 1)).length());
        Assertions.assertEquals(2.8284271247461903, new LineSegment(new Point(3, 4), new Point(1, 2)).length());
    }

    @Test
    void testCopy() {
        LineSegment lineSegment = new LineSegment(new Point(1, 2), new Point(3, 4));
        Assertions.assertEquals(lineSegment, lineSegment.copy());
    }

    // endregion

    // region override

    @Test
    void testEquals() {
        LineSegment lineSegment = new LineSegment(new Point(2), new Point(3));
        Assertions.assertEquals(
            lineSegment,
            new LineSegment(new Point(2), new Point(3))
        );
        Assertions.assertNotEquals(
            lineSegment,
            new LineSegment(new Point(3), new Point(2))
        );
    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(
            16808929,
            new LineSegment(new Point(2), new Point(3)).hashCode()
        );
    }

    @Test
    void testToString() {
        LineSegment lineSegment = new LineSegment(new Point(2), new Point(3));
        Assertions.assertEquals("2.0:2.0 3.0:3.0", lineSegment.toString());
    }

    @Test
    void testCompareTo() {
        LineSegment lineSegment = new LineSegment(new Point(2), new Point(3));
        Assertions.assertEquals(
            0, lineSegment.compareTo(new LineSegment(new Point(2), new Point(3)))
        );
        Assertions.assertEquals(
            -1, lineSegment.compareTo(new LineSegment(new Point(3), new Point(4)))
        );
        Assertions.assertEquals(
            1, lineSegment.compareTo(new LineSegment(new Point(1), new Point(0)))
        );
    }

    // endregion


    // region assert

    private static void assertLineSegment(LineSegment lineSegment) {
        assertLineSegment(lineSegment, new Point());
    }

    private static void assertLineSegment(LineSegment lineSegment, Point ab) {
        assertLineSegment(lineSegment, ab, ab);
    }

    private static void assertLineSegment(LineSegment lineSegment, Point a, Point b) {
        Assertions.assertEquals(a, lineSegment.getA());
        Assertions.assertEquals(b, lineSegment.getB());
    }

    // endregion
}
