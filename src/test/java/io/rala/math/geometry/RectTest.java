package io.rala.math.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("SameParameterValue")
class RectTest {
    // region constructors, getter and setter

    @Test
    void constructorWithHeightAndWidthButWithoutPoint() {
        assertRect(new Rect(1, 2), 1, 2);
    }

    @Test
    void constructorWithPointAndHeightAndWidth() {
        assertRect(new Rect(new Point(1), 1, 2), new Point(1), 1, 2);
    }

    @Test
    void createAndSetPoint() {
        Rect rect = new Rect(0, 0);
        assertRect(rect, 0, 0);
        rect.setPoint(new Point(1));
        assertRect(rect, new Point(1), 0, 0);
    }

    @Test
    void createAndSetHeight() {
        Rect rect = new Rect(0, 0);
        assertRect(rect, 0, 0);
        rect.setHeight(2);
        assertRect(rect, 2, 0);
    }

    @Test
    void createAndSetWidth() {
        Rect rect = new Rect(0, 0);
        assertRect(rect, 0, 0);
        rect.setWidth(3);
        assertRect(rect, 0, 3);
    }

    // endregion

    // region area, circumference and diagonale

    @Test
    void areaOfRectWithHeightAndWidth1() {
        Assertions.assertEquals(1, new Rect(1, 1).area());
    }

    @Test
    void areaOfRectWithHeight1AndWidth2() {
        Assertions.assertEquals(2, new Rect(1, 2).area());
    }

    @Test
    void areaOfRectWithHeight2AndWidth3() {
        Assertions.assertEquals(6, new Rect(2, 3).area());
    }


    @Test
    void circumferenceOfRectWithHeightAndWidth1() {
        Assertions.assertEquals(4, new Rect(1, 1).circumference());
    }

    @Test
    void circumferenceOfRectWithHeight1AndWidth2() {
        Assertions.assertEquals(6, new Rect(1, 2).circumference());
    }

    @Test
    void circumferenceOfRectWithHeight2AndWidth3() {
        Assertions.assertEquals(10, new Rect(2, 3).circumference());
    }

    @Test
    void diagonaleOfRectWithHeightAndWidth1() {
        Assertions.assertEquals(1.4142135623730951, new Rect(1, 1).diagonale());
    }

    @Test
    void diagonaleOfRectWithHeight1AndWidth2() {
        Assertions.assertEquals(2.23606797749979, new Rect(1, 2).diagonale());
    }

    @Test
    void diagonaleOfRectWithHeight2AndWidth3() {
        Assertions.assertEquals(3.605551275463989, new Rect(2, 3).diagonale());
    }

    // endregion

    // region copy

    @Test
    void copyOfRectWithPointHeightAndWidth() {
        Rect rect = new Rect(new Point(2), 3, 4);
        Assertions.assertEquals(rect, rect.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfRectWithPointHeightAndWidth() {
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
    void hashCodeOfRectWithPointHeightAndWidth() {
        Assertions.assertEquals(
            18254816,
            new Rect(new Point(2), 3, 4).hashCode()
        );
    }

    @Test
    void toStringOfRectWithPointHeightAndWidth() {
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
