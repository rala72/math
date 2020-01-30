package io.rala.math.geometry.typed;

import io.rala.math.geometry.Point;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DoublePointTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        GeometryAssertions.assertPoint(new DoublePoint());
    }

    @Test
    void constructorWithXYParameter() {
        GeometryAssertions.assertPoint(new DoublePoint(1d), 1d);
    }

    @Test
    void constructorWithEqualXYParameter() {
        GeometryAssertions.assertPoint(new DoublePoint(2d, 2d), 2d);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        GeometryAssertions.assertPoint(new DoublePoint(2d, 3d), 2d, 3d);
    }

    @Test
    void createAndSetX() {
        Point<Double> point = new DoublePoint();
        GeometryAssertions.assertPoint(point);
        point.setX(1d);
        GeometryAssertions.assertPoint(point, 1d, 0d);
    }

    @Test
    void createAndSetY() {
        Point<Double> point = new DoublePoint();
        GeometryAssertions.assertPoint(point);
        point.setY(2d);
        GeometryAssertions.assertPoint(point, 0d, 2d);
    }

    @Test
    void createAndSetXY() {
        Point<Double> point = new DoublePoint();
        GeometryAssertions.assertPoint(point);
        point.setXY(3d);
        GeometryAssertions.assertPoint(point, 3d, 3d);
    }

    // endregion

    // region isValid, move, rotate and copy

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new DoublePoint().isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new DoublePoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void moveOfPointWithXYWithXY() {
        GeometryAssertions.assertPoint(new DoublePoint().move(1d), 1d, 1d);
    }

    @Test
    void moveOfPointWithXYWithXAndY() {
        GeometryAssertions.assertPoint(new DoublePoint().move(1d, 1d), 1d, 1d);
    }

    @Test
    void moveOfPointWithXYWithVector() {
        GeometryAssertions.assertPoint(new DoublePoint().move(new DoubleVector(1d)), 1d, 1d);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiHalf() {
        GeometryAssertions.assertPoint(new DoublePoint(1d, 2d).rotate(Math.PI / 2d), -2d, 1d);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPi() {
        GeometryAssertions.assertPoint(new DoublePoint(1d, 2d).rotate(Math.PI), -1d, -2d);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiThreeHalf() {
        GeometryAssertions.assertPoint(
            new DoublePoint(1d, 2d).rotate(Math.PI * 3d / 2d),
            2d, -1d
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithTwoPi() {
        GeometryAssertions.assertPoint(new DoublePoint(1d, 2d).rotate(Math.PI * 2d), 1d, 2d);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiHalf() {
        GeometryAssertions.assertPoint(
            new DoublePoint(1d, 2d).rotate(new DoublePoint(1d), Math.PI / 2d),
            0d, 1d
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPi() {
        GeometryAssertions.assertPoint(
            new DoublePoint(1d, 2d).rotate(new DoublePoint(1d), Math.PI),
            1d, 0d
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiThreeHalf() {
        GeometryAssertions.assertPoint(
            new DoublePoint(1d, 2d).rotate(new DoublePoint(1d), Math.PI * 3d / 2d),
            2d, 1d
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithTwoPi() {
        GeometryAssertions.assertPoint(
            new DoublePoint(1d, 2d).rotate(new DoublePoint(1d), Math.PI * 2d),
            1d, 2d
        );
    }

    @Test
    void copyOfPointWithXY() {
        Point<Double> point = new DoublePoint(2d, 3d);
        Assertions.assertEquals(point, point.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfPointWithXY() {
        Point<Double> point = new DoublePoint(2d, 3d);
        Assertions.assertEquals(
            point,
            new DoublePoint(2d, 3d)
        );
        Assertions.assertNotEquals(
            point,
            new DoublePoint(3d, 2d)
        );
    }

    @Test
    void hashCodeOfPointWithXY() {
        Assertions.assertEquals(
            525249,
            new DoublePoint(2d, 3d).hashCode()
        );
    }

    @Test
    void toStringOfPointWithXY() {
        Point<Double> point = new DoublePoint(2d, 3d);
        Assertions.assertEquals("2.0|3.0", point.toString());
    }

    @Test
    void compareToOfPointWithXY() {
        Point<Double> point = new DoublePoint(2d, 3d);
        Assertions.assertEquals(
            0d, point.compareTo(new DoublePoint(2d, 3d))
        );
        Assertions.assertEquals(
            -1d, point.compareTo(new DoublePoint(3d, 1d))
        );
        Assertions.assertEquals(
            1d, point.compareTo(new DoublePoint(2d, 2d))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new DoublePoint(), Point.class);
    }

    // endregion
}
