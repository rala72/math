package io.rala.math.geometry.typed;

import io.rala.math.geometry.Rect;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DoubleRectTest {
    // region constructors, getter and setter

    @Test
    void constructorWithHeightAndWidthButWithoutPoints() {
        GeometryAssertions.assertRect(new DoubleRect(1d, 2d), 1d, 2d);
    }


    @Test
    void constructorWithPoint1dAndSize() {
        GeometryAssertions.assertRect(
            new DoubleRect(new DoublePoint(0d), new DoublePoint(1d), 2d),
            new DoublePoint(), new DoublePoint(1d), 2d
        );
    }

    @Test
    void createAndSetA() {
        Rect<Double> rect = new DoubleRect(0d, 0d);
        GeometryAssertions.assertRect(rect, 0d, 0d);
        rect.setA(new DoublePoint(1d));
        GeometryAssertions.assertRect(rect, new DoublePoint(1d), new DoublePoint(0d), 0d);
    }

    @Test
    void createAndSetB() {
        Rect<Double> rect = new DoubleRect(0d, 0d);
        GeometryAssertions.assertRect(rect, 0d, 0d);
        rect.setB(new DoublePoint(2d));
        GeometryAssertions.assertRect(rect, new DoublePoint(0d), new DoublePoint(2d), 0d);
    }

    @Test
    void createAndSetSize() {
        Rect<Double> rect = new DoubleRect(0d, 0d);
        GeometryAssertions.assertRect(rect, 0d, 0d);
        rect.setSize(2d);
        GeometryAssertions.assertRect(rect, 2d, 0d);
    }

    // endregion

    // region vertexes

    @Test
    void vertexesOfRectWithHeight1dAndWidth2d() {
        Rect<Double> rect = new DoubleRect(1d, 2d);
        GeometryAssertions.assertPoint(rect.vertexA(), 0d, 0d);
        GeometryAssertions.assertPoint(rect.vertexB(), 2d, 0d);
        GeometryAssertions.assertPoint(rect.vertexC(), 2d, 1d);
        GeometryAssertions.assertPoint(rect.vertexD(), 0d, 1d);
    }

    @Test
    void vertexesOfRectWithPointsAndSize() {
        Rect<Double> rect = new DoubleRect(new DoublePoint(1d, 1d), new DoublePoint(0d, 1d), 2d);
        GeometryAssertions.assertPoint(rect.vertexA(), 1d, 1d);
        GeometryAssertions.assertPoint(rect.vertexB(), 0d, 1d);
        GeometryAssertions.assertPoint(rect.vertexC(), 0d, 3d);
        GeometryAssertions.assertPoint(rect.vertexD(), 1d, 3d);
    }

    // endregion

    // region height, width and diagonale

    @Test
    void heightOfRectWithPointsAndPositiveSize() {
        Assertions.assertEquals(1d,
            new DoubleRect(new DoublePoint(), new DoublePoint(1d), 1d).height()
        );
    }

    @Test
    void heightOfRectWithPointsAndNegativeSize() {
        Assertions.assertEquals(1d,
            new DoubleRect(new DoublePoint(), new DoublePoint(1d), -1d).height()
        );
    }

    @Test
    void heightOfRectWithHeight1dAndWidth0d() {
        Assertions.assertEquals(0d, new DoubleRect(1d, 0d).height());
    }

    @Test
    void widthOfRectWithPointsAndPositiveSize() {
        Assertions.assertEquals(Math.sqrt(2d),
            new DoubleRect(new DoublePoint(), new DoublePoint(1d), 1d).width()
        );
    }

    @Test
    void widthOfRectWithPointsAndNegativeSize() {
        Assertions.assertEquals(Math.sqrt(2d),
            new DoubleRect(new DoublePoint(), new DoublePoint(1d), -1d).width()
        );
    }

    @Test
    void widthOfRectWithHeight1dAndWidth0d() {
        Assertions.assertEquals(1d, new DoubleRect(1d, 0d).width());
    }

    @Test
    void diagonaleOfRectWithHeightAndWidth1d() {
        Assertions.assertEquals(Math.sqrt(2d), new DoubleRect(1d, 1d).diagonale());
    }

    @Test
    void diagonaleOfRectWithHeight1dAndWidth2d() {
        Assertions.assertEquals(Math.sqrt(5d), new DoubleRect(1d, 2d).diagonale());
    }

    @Test
    void diagonaleOfRectWithHeight2dAndWidth3d() {
        Assertions.assertEquals(Math.sqrt(13d), new DoubleRect(2d, 3d).diagonale());
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfRectWithHeightAndWidth1d() {
        Assertions.assertEquals(1d, new DoubleRect(1d, 1d).area());
    }

    @Test
    void areaOfRectWithHeight1dAndWidth2d() {
        Assertions.assertEquals(2d, new DoubleRect(1d, 2d).area());
    }

    @Test
    void areaOfRectWithHeight2dAndWidth3d() {
        Assertions.assertEquals(6d, new DoubleRect(2d, 3d).area());
    }


    @Test
    void circumferenceOfRectWithHeightAndWidth1d() {
        Assertions.assertEquals(4d, new DoubleRect(1d, 1d).circumference());
    }

    @Test
    void circumferenceOfRectWithHeight1dAndWidth2d() {
        Assertions.assertEquals(6d, new DoubleRect(1d, 2d).circumference());
    }

    @Test
    void circumferenceOfRectWithHeight2dAndWidth3d() {
        Assertions.assertEquals(10d, new DoubleRect(2d, 3d).circumference());
    }

    // endregion

    // region circumCircle

    @Test
    void circumCircleOfRectWithHeightAndWidth1d() {
        GeometryAssertions.assertCircle(
            new DoubleRect(1d, 1d).circumCircle(),
            new DoublePoint(0.5d, 0.5d), Math.sqrt(2d) / 2d
        );
    }

    // endregion

    // region isSquare

    @Test
    void isSquareWithEqualHeightAndWidth() {
        Assertions.assertTrue(new DoubleRect(1d, 1d).isSquare());
    }

    @Test
    void isSquareWithUnequalHeightAndWidth() {
        Assertions.assertFalse(new DoubleRect(1d, 2d).isSquare());
    }

    // endregion

    // region isValid, move, rotate and copy

    @Test
    void isValidWithPositiveValues() {
        Assertions.assertTrue(new DoubleRect(1d, 1d).isValid());
    }

    @Test
    void isValidWithZeroValues() {
        Assertions.assertFalse(new DoubleRect(0d, 0d).isValid());
    }

    @Test
    void isValidWithZeroHeight() {
        Assertions.assertFalse(new DoubleRect(0d, 1d).isValid());
    }

    @Test
    void isValidWithZeroWidth() {
        Assertions.assertFalse(new DoubleRect(1d, 0d).isValid());
    }

    @Test
    void isValidWithNegativeValues() {
        Assertions.assertTrue(new DoubleRect(-1d, -1d).isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new DoubleRect(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void moveOfRectWithXYWithXY() {
        GeometryAssertions.assertRect(
            new DoubleRect(1d, 2d).move(1d),
            new DoublePoint(1d), new DoublePoint(3d, 1d), 1d
        );
    }

    @Test
    void moveOfRectWithXYWithXAndY() {
        GeometryAssertions.assertRect(
            new DoubleRect(1d, 2d).move(1d, 1d),
            new DoublePoint(1d), new DoublePoint(3d, 1d), 1d
        );
    }

    @Test
    void moveOfRectWithXYWithVector() {
        GeometryAssertions.assertRect(
            new DoubleRect(1d, 2d).move(new DoubleVector(1d)),
            new DoublePoint(1d), new DoublePoint(3d, 1d), 1d
        );
    }

    @Test
    void rotateOfRectWidthHeight1dAndWidth2dWithoutCenterWithPiHalf() {
        GeometryAssertions.assertRect(
            new DoubleRect(new DoublePoint(), new DoublePoint(0d, 1d), 2d)
                .rotate(Math.PI / 2d),
            new DoublePoint(), new DoublePoint(-1.0d, 6.123233995736766e-17d), 2d
        );
    }

    @Test
    void rotateOfRectWithHeight1dAndWidth2dWithCenterXY1dWithPiHalf() {
        GeometryAssertions.assertRect(
            new DoubleRect(new DoublePoint(), new DoublePoint(0d, 1d), 2d)
                .rotate(new DoublePoint(1d), Math.PI / 2d),
            new DoublePoint(2d, 0d), new DoublePoint(0.9999999999999999d, 0d), 2d
        );
    }

    @Test
    void copyOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d);
        Assertions.assertEquals(rect, rect.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d);
        Assertions.assertEquals(
            rect,
            new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d)
        );
        Assertions.assertNotEquals(
            rect,
            new DoubleRect(new DoublePoint(2d), new DoublePoint(4d), 3d)
        );
    }

    @Test
    void hashCodeOfRectWithPointHeightAndWidth() {
        Assertions.assertEquals(
            1595867199,
            new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d).hashCode()
        );
    }

    @Test
    void toStringOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d);
        Assertions.assertEquals("2.0|2.0 3.0|3.0 4.0", rect.toString());
    }

    @Test
    void compareToOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(1d, 2d);
        Assertions.assertEquals(
            0d, rect.compareTo(new DoubleRect(1d, 2d))
        );
        Assertions.assertEquals(
            -1d, rect.compareTo(new DoubleRect(2d, 3d))
        );
        Assertions.assertEquals(
            1d, rect.compareTo(new DoubleRect(1d, 1d))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new DoubleRect(0d, 0d), Rect.class);
    }

    // endregion
}
