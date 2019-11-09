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
        lineSegment.setP1(new Point(1));
        assertLineSegment(lineSegment, new Point(1), new Point());
        lineSegment.setP2(new Point(2));
        assertLineSegment(lineSegment, new Point(1), new Point(2));
        lineSegment.setP12(new Point(3));
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
            -1,
            0, lineSegment.compareTo(new LineSegment(new Point(2), new Point(1)))
        );
        Assertions.assertEquals(
            1,
            0, lineSegment.compareTo(new LineSegment(new Point(1), new Point(0)))
        );
    }

    // endregion


    // region assert

    private static void assertLineSegment(LineSegment lineSegment) {
        assertLineSegment(lineSegment, new Point());
    }

    private static void assertLineSegment(LineSegment lineSegment, Point p12) {
        assertLineSegment(lineSegment, p12, p12);
    }

    private static void assertLineSegment(LineSegment lineSegment, Point p1, Point p2) {
        Assertions.assertEquals(p1, lineSegment.getP1());
        Assertions.assertEquals(p2, lineSegment.getP2());
    }

    // endregion
}
