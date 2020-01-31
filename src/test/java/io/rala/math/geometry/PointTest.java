package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PointTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        GeometryAssertions.assertPoint(new TestPoint());
    }

    @Test
    void constructorWithXYParameter() {
        GeometryAssertions.assertPoint(new TestPoint(1), 1);
    }

    @Test
    void constructorWithEqualXYParameter() {
        GeometryAssertions.assertPoint(new TestPoint(2, 2), 2);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        GeometryAssertions.assertPoint(new TestPoint(2, 3), 2, 3);
    }

    @Test
    void createAndSetX() {
        Point<Number> point = new TestPoint();
        GeometryAssertions.assertPoint(point);
        point.setX(1);
        GeometryAssertions.assertPoint(point, 1, 0);
    }

    @Test
    void createAndSetY() {
        Point<Number> point = new TestPoint();
        GeometryAssertions.assertPoint(point);
        point.setY(2);
        GeometryAssertions.assertPoint(point, 0, 2);
    }

    @Test
    void createAndSetXY() {
        Point<Number> point = new TestPoint();
        GeometryAssertions.assertPoint(point);
        point.setXY(3);
        GeometryAssertions.assertPoint(point, 3, 3);
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfPointWithX0_5Y1_5() {
        TestPoint point = new TestPoint(0.5, 1.5);
        Point<Integer> result = new Point<>(new IntegerArithmetic(), 0, 1);
        Assertions.assertEquals(result,
            point.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new TestPoint().isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new TestPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void moveOfPointWithXYWithXY() {
        GeometryAssertions.assertPoint(new TestPoint().move(1), 1, 1);
    }

    @Test
    void moveOfPointWithXYWithXAndY() {
        GeometryAssertions.assertPoint(new TestPoint().move(1, 1), 1, 1);
    }

    @Test
    void moveOfPointWithXYWithVector() {
        GeometryAssertions.assertPoint(new TestPoint().move(new TestVector(1)), 1, 1);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiHalf() {
        GeometryAssertions.assertPoint(new TestPoint(1, 2).rotate(Math.PI / 2), -2, 1);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPi() {
        GeometryAssertions.assertPoint(new TestPoint(1, 2).rotate(Math.PI), -1, -2);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiThreeHalf() {
        GeometryAssertions.assertPoint(
            new TestPoint(1, 2).rotate(Math.PI * 3 / 2),
            2, -1
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithTwoPi() {
        GeometryAssertions.assertPoint(new TestPoint(1, 2).rotate(Math.PI * 2), 1, 2);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiHalf() {
        GeometryAssertions.assertPoint(
            new TestPoint(1, 2).rotate(new TestPoint(1), Math.PI / 2),
            0, 1
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPi() {
        GeometryAssertions.assertPoint(
            new TestPoint(1, 2).rotate(new TestPoint(1), Math.PI),
            1, 0
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiThreeHalf() {
        GeometryAssertions.assertPoint(
            new TestPoint(1, 2).rotate(new TestPoint(1), Math.PI * 3 / 2),
            2, 1
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithTwoPi() {
        GeometryAssertions.assertPoint(
            new TestPoint(1, 2).rotate(new TestPoint(1), Math.PI * 2),
            1, 2
        );
    }

    @Test
    void copyOfPointWithXY() {
        Point<Number> point = new TestPoint(2, 3);
        Assertions.assertEquals(point, point.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfPointWithXY() {
        Point<Number> point = new TestPoint(2, 3);
        Assertions.assertEquals(
            point,
            new TestPoint(2, 3)
        );
        Assertions.assertNotEquals(
            point,
            new TestPoint(3, 2)
        );
    }

    @Test
    void hashCodeOfPointWithXY() {
        Assertions.assertEquals(
            1026,
            new TestPoint(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfPointWithXY() {
        Point<Number> point = new TestPoint(2, 3);
        Assertions.assertEquals("2|3", point.toString());
    }

    @Test
    void compareToOfPointWithXY() {
        Point<Number> point = new TestPoint(2, 3);
        Assertions.assertEquals(
            0, point.compareTo(new TestPoint(2, 3))
        );
        Assertions.assertEquals(
            -1, point.compareTo(new TestPoint(3, 1))
        );
        Assertions.assertEquals(
            1, point.compareTo(new TestPoint(2, 2))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new TestPoint(), Point.class);
    }

    // endregion
}
