package io.rala.math.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PointTest {
    // region constructors, getter and setter

    @Test
    void testConstructors() {
        assertPoint(new Point());
        assertPoint(new Point(1), 1);
        assertPoint(new Point(2, 2), 2);
        assertPoint(new Point(2, 3), 2, 3);
    }

    @Test
    void testGetterAndSetter() {
        Point point = new Point();
        assertPoint(point);
        point.setX(1);
        assertPoint(point, 1, 0);
        point.setY(2);
        assertPoint(point, 1, 2);
        point.setXY(3);
        assertPoint(point, 3);
    }

    // endregion

    // region override

    @Test
    void testEquals() {
        Point point = new Point(2, 3);
        Assertions.assertEquals(
            point,
            new Point(2, 3)
        );
        Assertions.assertNotEquals(
            point,
            new Point(3, 2)
        );
    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(
            525249,
            new Point(2, 3).hashCode()
        );
    }

    @Test
    void testToString() {
        Point point = new Point(2, 3);
        Assertions.assertEquals("2.0:3.0", point.toString());
    }

    @Test
    void testCompareTo() {
        Point point = new Point(2, 3);
        Assertions.assertEquals(
            0, point.compareTo(new Point(2, 3))
        );
        Assertions.assertEquals(
            -1,
            0, point.compareTo(new Point(2, 1))
        );
        Assertions.assertEquals(
            1,
            0, point.compareTo(new Point(1, 0))
        );
    }

    // endregion


    // region assert

    private static void assertPoint(Point point) {
        assertPoint(point, 0);
    }

    private static void assertPoint(Point point, double xy) {
        assertPoint(point, xy, xy);
    }

    private static void assertPoint(Point point, double x, double y) {
        Assertions.assertEquals(x, point.getX());
        Assertions.assertEquals(y, point.getY());
    }

    // endregion
}
