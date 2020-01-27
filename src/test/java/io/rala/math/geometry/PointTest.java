package io.rala.math.geometry;

import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PointTest {

    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        GeometryAssertions.assertPoint(new Point());
    }

    @Test
    void constructorWithXYParameter() {
        GeometryAssertions.assertPoint(new Point(1), 1);
    }

    @Test
    void constructorWithEqualXYParameter() {
        GeometryAssertions.assertPoint(new Point(2, 2), 2);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        GeometryAssertions.assertPoint(new Point(2, 3), 2, 3);
    }

    @Test
    void createAndSetX() {
        Point point = new Point();
        GeometryAssertions.assertPoint(point);
        point.setX(1);
        GeometryAssertions.assertPoint(point, 1, 0);
    }

    @Test
    void createAndSetY() {
        Point point = new Point();
        GeometryAssertions.assertPoint(point);
        point.setY(2);
        GeometryAssertions.assertPoint(point, 0, 2);
    }

    @Test
    void createAndSetXY() {
        Point point = new Point();
        GeometryAssertions.assertPoint(point);
        point.setXY(3);
        GeometryAssertions.assertPoint(point, 3, 3);
    }

    // endregion

    // region isValid, move, rotate and copy

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new Point().isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void moveOfPointWithXYWithXY() {
        GeometryAssertions.assertPoint(new Point().move(1), 1, 1);
    }

    @Test
    void moveOfPointWithXYWithXAndY() {
        GeometryAssertions.assertPoint(new Point().move(1, 1), 1, 1);
    }

    @Test
    void moveOfPointWithXYWithVector() {
        GeometryAssertions.assertPoint(new Point().move(new Vector(1)), 1, 1);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiHalf() {
        GeometryAssertions.assertPoint(new Point(1, 2).rotate(Math.PI / 2), -2, 1);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPi() {
        GeometryAssertions.assertPoint(new Point(1, 2).rotate(Math.PI), -1, -2);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiThreeHalf() {
        GeometryAssertions.assertPoint(
            new Point(1, 2).rotate(Math.PI * 3 / 2),
            2, -1
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithTwoPi() {
        GeometryAssertions.assertPoint(new Point(1, 2).rotate(Math.PI * 2), 1, 2);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiHalf() {
        GeometryAssertions.assertPoint(
            new Point(1, 2).rotate(new Point(1), Math.PI / 2),
            0, 1
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPi() {
        GeometryAssertions.assertPoint(
            new Point(1, 2).rotate(new Point(1), Math.PI),
            1, 0
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiThreeHalf() {
        GeometryAssertions.assertPoint(
            new Point(1, 2).rotate(new Point(1), Math.PI * 3 / 2),
            2, 1
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithTwoPi() {
        GeometryAssertions.assertPoint(
            new Point(1, 2).rotate(new Point(1), Math.PI * 2),
            1, 2
        );
    }

    @Test
    void copyOfPointWithXY() {
        Point point = new Point(2, 3);
        Assertions.assertEquals(point, point.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfPointWithXY() {
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
    void hashCodeOfPointWithXY() {
        Assertions.assertEquals(
            525249,
            new Point(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfPointWithXY() {
        Point point = new Point(2, 3);
        Assertions.assertEquals("2.0|3.0", point.toString());
    }

    @Test
    void compareToOfPointWithXY() {
        Point point = new Point(2, 3);
        Assertions.assertEquals(
            0, point.compareTo(new Point(2, 3))
        );
        Assertions.assertEquals(
            -1, point.compareTo(new Point(3, 1))
        );
        Assertions.assertEquals(
            1, point.compareTo(new Point(2, 2))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new Point(), Point.class);
    }

    // endregion
}
