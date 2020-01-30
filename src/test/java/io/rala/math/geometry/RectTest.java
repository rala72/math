package io.rala.math.geometry;

import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestRect;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RectTest {
    // region constructors, getter and setter

    @Test
    void constructorWithHeightAndWidthButWithoutPoints() {
        GeometryAssertions.assertRect(new TestRect(1, 2), 1, 2);
    }

    @Test
    void constructorWithPoint1AndSize() {
        GeometryAssertions.assertRect(
            new TestRect(new TestPoint(0), new TestPoint(1), 2),
            new TestPoint(), new TestPoint(1), 2
        );
    }

    @Test
    void createAndSetA() {
        Rect<Number> rect = new TestRect(0, 0);
        GeometryAssertions.assertRect(rect, 0, 0);
        rect.setA(new TestPoint(1));
        GeometryAssertions.assertRect(rect, new TestPoint(1), new TestPoint(0), 0);
    }

    @Test
    void createAndSetB() {
        Rect<Number> rect = new TestRect(0, 0);
        GeometryAssertions.assertRect(rect, 0, 0);
        rect.setB(new TestPoint(2));
        GeometryAssertions.assertRect(rect, new TestPoint(0), new TestPoint(2), 0);
    }

    @Test
    void createAndSetSize() {
        Rect<Number> rect = new TestRect(0, 0);
        GeometryAssertions.assertRect(rect, 0, 0);
        rect.setSize(2);
        GeometryAssertions.assertRect(rect, 2, 0);
    }

    // endregion

    // region vertexes

    @Test
    void vertexesOfRectWithHeight1AndWidth2() {
        Rect<Number> rect = new TestRect(1, 2);
        GeometryAssertions.assertPoint(rect.vertexA(), 0, 0);
        GeometryAssertions.assertPoint(rect.vertexB(), 2, 0);
        GeometryAssertions.assertPoint(rect.vertexC(), 2, 1);
        GeometryAssertions.assertPoint(rect.vertexD(), 0, 1);
    }

    @Test
    void vertexesOfRectWithPointsAndSize() {
        Rect<Number> rect = new TestRect(new TestPoint(1, 1), new TestPoint(0, 1), 2);
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
            new TestRect(new TestPoint(), new TestPoint(1), 1).height()
        );
    }

    @Test
    void heightOfRectWithPointsAndNegativeSize() {
        Assertions.assertEquals(1d,
            new TestRect(new TestPoint(), new TestPoint(1), -1).height()
        );
    }

    @Test
    void heightOfRectWithHeight1AndWidth0() {
        Assertions.assertEquals(0d, new TestRect(1, 0).height());
    }

    @Test
    void widthOfRectWithPointsAndPositiveSize() {
        Assertions.assertEquals(Math.sqrt(2),
            new TestRect(new TestPoint(), new TestPoint(1), 1).width()
        );
    }

    @Test
    void widthOfRectWithPointsAndNegativeSize() {
        Assertions.assertEquals(Math.sqrt(2),
            new TestRect(new TestPoint(), new TestPoint(1), -1).width()
        );
    }

    @Test
    void widthOfRectWithHeight1AndWidth0() {
        Assertions.assertEquals(1, new TestRect(1, 0).width());
    }

    @Test
    void diagonaleOfRectWithHeightAndWidth1() {
        Assertions.assertEquals(Math.sqrt(2), new TestRect(1, 1).diagonale());
    }

    @Test
    void diagonaleOfRectWithHeight1AndWidth2() {
        Assertions.assertEquals(Math.sqrt(5), new TestRect(1, 2).diagonale());
    }

    @Test
    void diagonaleOfRectWithHeight2AndWidth3() {
        Assertions.assertEquals(Math.sqrt(13), new TestRect(2, 3).diagonale());
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfRectWithHeightAndWidth1() {
        Assertions.assertEquals(1d, new TestRect(1, 1).area());
    }

    @Test
    void areaOfRectWithHeight1AndWidth2() {
        Assertions.assertEquals(2d, new TestRect(1, 2).area());
    }

    @Test
    void areaOfRectWithHeight2AndWidth3() {
        Assertions.assertEquals(6d, new TestRect(2, 3).area());
    }

    @Test
    void circumferenceOfRectWithHeightAndWidth1() {
        Assertions.assertEquals(4d, new TestRect(1, 1).circumference());
    }

    @Test
    void circumferenceOfRectWithHeight1AndWidth2() {
        Assertions.assertEquals(6d, new TestRect(1, 2).circumference());
    }

    @Test
    void circumferenceOfRectWithHeight2AndWidth3() {
        Assertions.assertEquals(10d, new TestRect(2, 3).circumference());
    }

    // endregion

    // region circumCircle

    @Test
    void circumCircleOfRectWithHeightAndWidth1() {
        GeometryAssertions.assertCircle(
            new TestRect(1, 1).circumCircle(),
            new TestPoint(0.5, 0.5), Math.sqrt(2) / 2
        );
    }

    @Test
    void circumCircleRadiusOfRectWithHeightAndWidth1() {
        Assertions.assertEquals(
            Math.sqrt(2) / 2,
            new TestRect(1, 1).circumCircleRadius()
        );
    }

    @Test
    void circumCirclePointOfRectWithHeightAndWidth1() {
        GeometryAssertions.assertPoint(
            new TestRect(1, 1).circumCirclePoint(),
            0.5, 0.5
        );
    }

    // endregion

    // region isSquare

    @Test
    void isSquareWithEqualHeightAndWidth() {
        Assertions.assertTrue(new TestRect(1, 1).isSquare());
    }

    @Test
    void isSquareWithUnequalHeightAndWidth() {
        Assertions.assertFalse(new TestRect(1, 2).isSquare());
    }

    // endregion

    // region isValid, move, rotate and copy

    @Test
    void isValidWithPositiveValues() {
        Assertions.assertTrue(new TestRect(1, 1).isValid());
    }

    @Test
    void isValidWithZeroValues() {
        Assertions.assertFalse(new TestRect(0d, 0d).isValid());
    }

    @Test
    void isValidWithZeroHeight() {
        Assertions.assertFalse(new TestRect(0d, 1d).isValid());
    }

    @Test
    void isValidWithZeroWidth() {
        Assertions.assertFalse(new TestRect(1d, 0d).isValid());
    }

    @Test
    void isValidWithNegativeValues() {
        Assertions.assertTrue(new TestRect(-1, -1).isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new TestRect(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void moveOfRectWithXYWithXY() {
        GeometryAssertions.assertRect(
            new TestRect(1, 2).move(1),
            new TestPoint(1), new TestPoint(3, 1), 1
        );
    }

    @Test
    void moveOfRectWithXYWithXAndY() {
        GeometryAssertions.assertRect(
            new TestRect(1, 2).move(1, 1),
            new TestPoint(1), new TestPoint(3, 1), 1
        );
    }

    @Test
    void moveOfRectWithXYWithVector() {
        GeometryAssertions.assertRect(
            new TestRect(1, 2).move(new TestVector(1)),
            new TestPoint(1), new TestPoint(3, 1), 1
        );
    }

    @Test
    void rotateOfRectWidthHeight1AndWidth2WithoutCenterWithPiHalf() {
        GeometryAssertions.assertRect(
            new TestRect(new TestPoint(), new TestPoint(0, 1), 2)
                .rotate(Math.PI / 2),
            new TestPoint(), new TestPoint(-1.0, 6.123233995736766E-17), 2
        );
    }

    @Test
    void rotateOfRectWithHeight1AndWidth2WithCenterXY1WithPiHalf() {
        GeometryAssertions.assertRect(
            new TestRect(new TestPoint(), new TestPoint(0, 1), 2)
                .rotate(new TestPoint(1), Math.PI / 2),
            new TestPoint(2, 0), new TestPoint(0.9999999999999999, 0), 2
        );
    }

    @Test
    void copyOfRectWithPointHeightAndWidth() {
        Rect<Number> rect = new TestRect(new TestPoint(2), new TestPoint(3), 4);
        Assertions.assertEquals(rect, rect.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfRectWithPointHeightAndWidth() {
        Rect<Number> rect = new TestRect(new TestPoint(2), new TestPoint(3), 4);
        Assertions.assertEquals(
            rect,
            new TestRect(new TestPoint(2), new TestPoint(3), 4)
        );
        Assertions.assertNotEquals(
            rect,
            new TestRect(new TestPoint(2), new TestPoint(4), 3)
        );
    }

    @Test
    void hashCodeOfRectWithPointHeightAndWidth() {
        Assertions.assertEquals(
            1047587,
            new TestRect(new TestPoint(2), new TestPoint(3), 4).hashCode()
        );
    }

    @Test
    void toStringOfRectWithPointHeightAndWidth() {
        Rect<Number> rect = new TestRect(new TestPoint(2d), new TestPoint(3d), 4d);
        Assertions.assertEquals("2.0|2.0 3.0|3.0 4.0", rect.toString());
    }

    @Test
    void compareToOfRectWithPointHeightAndWidth() {
        Rect<Number> rect = new TestRect(1, 2);
        Assertions.assertEquals(
            0, rect.compareTo(new TestRect(1, 2))
        );
        Assertions.assertEquals(
            -1, rect.compareTo(new TestRect(2, 3))
        );
        Assertions.assertEquals(
            1, rect.compareTo(new TestRect(1, 1))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new TestRect(0, 0), Rect.class);
    }

    // endregion
}
