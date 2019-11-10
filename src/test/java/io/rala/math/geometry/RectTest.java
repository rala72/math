package io.rala.math.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("SameParameterValue")
class RectTest {
    // region constructors, getter and setter

    @Test
    void testConstructors() {
        assertRect(new Rect(1, 2), 1, 2);
        assertRect(new Rect(new Point(1), 1, 2), new Point(1), 1, 2);
    }

    @Test
    void testGetterAndSetter() {
        Rect rect = new Rect(0, 0);
        assertRect(rect, 0, 0);
        rect.setPoint(new Point(1));
        assertRect(rect, new Point(1), 0, 0);
        rect.setHeight(2);
        assertRect(rect, new Point(1), 2, 0);
        rect.setWidth(3);
        assertRect(rect, new Point(1), 2, 3);
    }

    // endregion

    // region area and circumference

    @Test
    void testArea() {
        Assertions.assertEquals(1, new Rect(1, 1).getArea());
        Assertions.assertEquals(2, new Rect(1, 2).getArea());
        Assertions.assertEquals(6, new Rect(2, 3).getArea());
    }

    @Test
    void testCircumference() {
        Assertions.assertEquals(4, new Rect(1, 1).getCircumference());
        Assertions.assertEquals(6, new Rect(1, 2).getCircumference());
        Assertions.assertEquals(10, new Rect(2, 3).getCircumference());
    }

    // endregion

    // region copy

    @Test
    void testCopy() {
        Rect rect = new Rect(new Point(2), 3, 4);
        Assertions.assertEquals(rect, rect.copy());
    }

    // endregion

    // region override

    @Test
    void testEquals() {
        Rect rect = new Rect(new Point(2), 3, 4);
        Assertions.assertEquals(
            rect,
            new Rect(new Point(2), 3, 4)
        );
        Assertions.assertNotEquals(
            rect,
            new Rect(new Point(2), 4, 3)
        );
    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(
            18254816,
            new Rect(new Point(2), 3, 4).hashCode()
        );
    }

    @Test
    void testToString() {
        Rect rect = new Rect(new Point(2), 3, 4);
        Assertions.assertEquals("2.0:2.0 3.0x4.0", rect.toString());
    }

    // endregion


    // region assert

    private static void assertRect(Rect rect, double height, double width) {
        assertRect(rect, new Point(), height, width);
    }

    private static void assertRect(Rect rect, Point point, double height, double width) {
        Assertions.assertEquals(point, rect.getPoint());
        Assertions.assertEquals(height, rect.getHeight());
        Assertions.assertEquals(width, rect.getWidth());
    }

    // endregion
}
