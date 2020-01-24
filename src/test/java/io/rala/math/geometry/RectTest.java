package io.rala.math.geometry;

import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RectTest {
    // region constructors, getter and setter

    @Test
    void constructorWithHeightAndWidthButWithoutPoints() {
        GeometryAssertions.assertRect(new Rect(1, 2), 1, 2);
    }


    @Test
    void constructorWithPoint1AndSize() {
        GeometryAssertions.assertRect(new Rect(new Point(0), new Point(1), 2),
            new Point(), new Point(1), 2
        );
    }

    @Test
    void createAndSetA() {
        Rect rect = new Rect(0, 0);
        GeometryAssertions.assertRect(rect, 0, 0);
        rect.setA(new Point(1));
        GeometryAssertions.assertRect(rect, new Point(1), new Point(0), 0);
    }

    @Test
    void createAndSetB() {
        Rect rect = new Rect(0, 0);
        GeometryAssertions.assertRect(rect, 0, 0);
        rect.setB(new Point(2));
        GeometryAssertions.assertRect(rect, new Point(0), new Point(2), 0);
    }

    @Test
    void createAndSetSize() {
        Rect rect = new Rect(0, 0);
        GeometryAssertions.assertRect(rect, 0, 0);
        rect.setSize(2);
        GeometryAssertions.assertRect(rect, 2, 0);
    }

    // endregion

    // region vertexes

    @Test
    void vertexesOfRectWithHeight1AndWidth2() {
        Rect rect = new Rect(1, 2);
        GeometryAssertions.assertPoint(rect.vertexA(), 0, 0);
        GeometryAssertions.assertPoint(rect.vertexB(), 2, 0);
        GeometryAssertions.assertPoint(rect.vertexC(), 2, 1);
        GeometryAssertions.assertPoint(rect.vertexD(), 0, 1);
    }

    @Test
    void vertexesOfRectWithPointsAndSize() {
        Rect rect = new Rect(new Point(1, 1), new Point(0, 1), 2);
        GeometryAssertions.assertPoint(rect.vertexA(), 1, 1);
        GeometryAssertions.assertPoint(rect.vertexB(), 0, 1);
        GeometryAssertions.assertPoint(rect.vertexC(), 0, 3);
        GeometryAssertions.assertPoint(rect.vertexD(), 1, 3);
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
        Assertions.assertEquals(Math.sqrt(2),
            new Rect(new Point(), new Point(1), 1).width()
        );
    }

    @Test
    void widthOfRectWithPointsAndNegativeSize() {
        Assertions.assertEquals(Math.sqrt(2),
            new Rect(new Point(), new Point(1), -1).width()
        );
    }

    @Test
    void widthOfRectWithHeight1AndWidth0() {
        Assertions.assertEquals(1, new Rect(1, 0).width());
    }

    @Test
    void diagonaleOfRectWithHeightAndWidth1() {
        Assertions.assertEquals(Math.sqrt(2), new Rect(1, 1).diagonale());
    }

    @Test
    void diagonaleOfRectWithHeight1AndWidth2() {
        Assertions.assertEquals(Math.sqrt(5), new Rect(1, 2).diagonale());
    }

    @Test
    void diagonaleOfRectWithHeight2AndWidth3() {
        Assertions.assertEquals(Math.sqrt(13), new Rect(2, 3).diagonale());
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

    // region isValid, move, rotate and copy

    @Test
    void isValidWithPositiveValues() {
        Assertions.assertTrue(new Rect(1, 1).isValid());
    }

    @Test
    void isValidWithZeroValues() {
        Assertions.assertFalse(new Rect(0, 0).isValid());
    }

    @Test
    void isValidWithZeroHeight() {
        Assertions.assertFalse(new Rect(0, 1).isValid());
    }

    @Test
    void isValidWithZeroWidth() {
        Assertions.assertFalse(new Rect(1, 0).isValid());
    }

    @Test
    void isValidWithNegativeValues() {
        Assertions.assertTrue(new Rect(-1, -1).isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new Rect(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void moveOfRectWithXYWithXY() {
        GeometryAssertions.assertRect(
            new Rect(1, 2).move(1),
            new Point(1), new Point(3, 1), 1
        );
    }

    @Test
    void moveOfRectWithXYWithXAndY() {
        GeometryAssertions.assertRect(
            new Rect(1, 2).move(1, 1),
            new Point(1), new Point(3, 1), 1
        );
    }

    @Test
    void moveOfRectWithXYWithVector() {
        GeometryAssertions.assertRect(
            new Rect(1, 2).move(new Vector(1)),
            new Point(1), new Point(3, 1), 1
        );
    }

    @Test
    void rotateOfRectWidthHeight1AndWidth2WithoutCenterWithPiHalf() {
        GeometryAssertions.assertRect(
            new Rect(new Point(), new Point(0, 1), 2)
                .rotate(Math.PI / 2),
            new Point(), new Point(-1.0, 6.123233995736766E-17), 2
        );
    }

    @Test
    void rotateOfRectWithHeight1AndWidth2WithCenterXY1WithPiHalf() {
        GeometryAssertions.assertRect(
            new Rect(new Point(), new Point(0, 1), 2)
                .rotate(new Point(1), Math.PI / 2),
            new Point(2, 0), new Point(0.9999999999999999, 0), 2
        );
    }

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
        Assertions.assertEquals("2.0|2.0 3.0|3.0 4.0", rect.toString());
    }

    @Test
    void compareToOfRectWithPointHeightAndWidth() {
        Rect rect = new Rect(1, 2);
        Assertions.assertEquals(
            0, rect.compareTo(new Rect(1, 2))
        );
        Assertions.assertEquals(
            -1, rect.compareTo(new Rect(2, 3))
        );
        Assertions.assertEquals(
            1, rect.compareTo(new Rect(1, 1))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new Rect(0, 0), Rect.class);
    }

    // endregion
}
