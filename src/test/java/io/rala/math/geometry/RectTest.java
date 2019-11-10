package io.rala.math.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("SameParameterValue")
class RectTest {
    // region constructors, getter and setter

    @Test
    void constructorWithHeightAndWidthButWithoutPoints() {
        assertRect(new Rect(1, 2), 1, 2);
    }


    @Test
    void constructorWithPoint1AndSize() {
        assertRect(new Rect(new Point(0), new Point(1), 2),
            new Point(), new Point(1), 2
        );
    }

    @Test
    void createAndSetA() {
        Rect rect = new Rect(0, 0);
        assertRect(rect, 0, 0);
        rect.setA(new Point(1));
        assertRect(rect, new Point(1), new Point(0), 0);
    }

    @Test
    void createAndSetB() {
        Rect rect = new Rect(0, 0);
        assertRect(rect, 0, 0);
        rect.setB(new Point(2));
        assertRect(rect, new Point(0), new Point(2), 0);
    }

    @Test
    void createAndSetSize() {
        Rect rect = new Rect(0, 0);
        assertRect(rect, 0, 0);
        rect.setSize(2);
        assertRect(rect, 2, 0);
    }

    // endregion

    // region vertexes

    @Test
    void vertexesOfRectWithHeight1AndWidth2() {
        Rect rect = new Rect(1, 2);
        Assertions.assertEquals(new Point(0, 0), rect.vertexA());
        Assertions.assertEquals(new Point(2, 0), rect.vertexB());
        Assertions.assertEquals(new Point(2, 1), rect.vertexC());
        Assertions.assertEquals(new Point(0, 1), rect.vertexD());
    }

    @Test
    void vertexesOfRectWithPointsAndSize() {
        Rect rect = new Rect(new Point(1, 1), new Point(0, 1), 2);
        Assertions.assertEquals(new Point(1, 1), rect.vertexA());
        Assertions.assertEquals(new Point(0, 1), rect.vertexB());
        Assertions.assertEquals(new Point(0, 3), rect.vertexC());
        Assertions.assertEquals(new Point(1, 3), rect.vertexD());
    }

    // endregion

    // region height, width and diagonale

    @Test
    void heightOfRectWithPointsAndPositiveSize() {
        Assertions.assertEquals(1,
            new Rect(new Point(), new Point(1), 1).height()
        );
    }

    @Test
    void heightOfRectWithPointsAndNegativeSize() {
        Assertions.assertEquals(1,
            new Rect(new Point(), new Point(1), -1).height()
        );
    }

    @Test
    void heightOfRectWithHeight1AndWidth0() {
        Assertions.assertEquals(0, new Rect(1, 0).height());
    }

    @Test
    void widthOfRectWithPointsAndPositiveSize() {
        Assertions.assertEquals(1.4142135623730951,
            new Rect(new Point(), new Point(1), 1).width()
        );
    }

    @Test
    void widthOfRectWithPointsAndNegativeSize() {
        Assertions.assertEquals(1.4142135623730951,
            new Rect(new Point(), new Point(1), -1).width()
        );
    }

    @Test
    void widthOfRectWithHeight1AndWidth0() {
        Assertions.assertEquals(1, new Rect(1, 0).width());
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

    // region area and circumference

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

    // endregion

    // region isSquare

    @Test
    void isSquareWithEqualHeightAndWidth() {
        Assertions.assertTrue(new Rect(1, 1).isSquare());
    }

    @Test
    void isSquareWithUnequalHeightAndWidth() {
        Assertions.assertFalse(new Rect(1, 2).isSquare());
    }

    // endregion

    // region copy

    @Test
    void copyOfRectWithPointHeightAndWidth() {
        Rect rect = new Rect(new Point(2), new Point(3), 4);
        Assertions.assertEquals(rect, rect.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfRectWithPointHeightAndWidth() {
        Rect rect = new Rect(new Point(2), new Point(3), 4);
        Assertions.assertEquals(
            rect,
            new Rect(new Point(2), new Point(3), 4)
        );
        Assertions.assertNotEquals(
            rect,
            new Rect(new Point(2), new Point(4), 3)
        );
    }

    @Test
    void hashCodeOfRectWithPointHeightAndWidth() {
        Assertions.assertEquals(
            1595867199,
            new Rect(new Point(2), new Point(3), 4).hashCode()
        );
    }

    @Test
    void toStringOfRectWithPointHeightAndWidth() {
        Rect rect = new Rect(new Point(2), new Point(3), 4);
        Assertions.assertEquals("2.0:2.0 3.0:3.0 4.0", rect.toString());
    }

    // endregion


    // region assert

    private static void assertRect(Rect rect, double height, double width) {
        assertRect(rect, new Point(), new Point(width, 0), height);
    }

    private static void assertRect(Rect rect, Point a, Point b, double size) {
        Assertions.assertEquals(a, rect.getA());
        Assertions.assertEquals(b, rect.getB());
        Assertions.assertEquals(size, rect.getSize());
    }

    // endregion
}
