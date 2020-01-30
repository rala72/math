package io.rala.math.geometry.typed;

import io.rala.math.geometry.Point;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class BigDecimalPointTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        GeometryAssertions.assertPoint(new BigDecimalPoint());
    }

    @Test
    void constructorWithXYParameter() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.ONE
        );
    }

    @Test
    void constructorWithEqualXYParameter() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithDifferentXYParameter() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)),
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
    }

    @Test
    void createAndSetX() {
        Point<BigDecimal> point = new BigDecimalPoint();
        GeometryAssertions.assertPoint(point);
        point.setX(BigDecimal.ONE);
        GeometryAssertions.assertPoint(point, BigDecimal.ONE, BigDecimal.ZERO);
    }

    @Test
    void createAndSetY() {
        Point<BigDecimal> point = new BigDecimalPoint();
        GeometryAssertions.assertPoint(point);
        point.setY(BigDecimal.valueOf(2d));
        GeometryAssertions.assertPoint(point, BigDecimal.ZERO, BigDecimal.valueOf(2d));
    }

    @Test
    void createAndSetXY() {
        Point<BigDecimal> point = new BigDecimalPoint();
        GeometryAssertions.assertPoint(point);
        point.setXY(BigDecimal.valueOf(3d));
        GeometryAssertions.assertPoint(point,
            BigDecimal.valueOf(3d), BigDecimal.valueOf(3d)
        );
    }

    // endregion

    // region isValid, move, rotate and copy

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new BigDecimalPoint().isValid());
    }

    @Test
    void moveOfPointWithXYWithXY() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint().move(BigDecimal.ONE),
            BigDecimal.ONE, BigDecimal.ONE
        );
    }

    @Test
    void moveOfPointWithXYWithXAndY() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint().move(BigDecimal.ONE, BigDecimal.ONE),
            BigDecimal.ONE, BigDecimal.ONE
        );
    }

    @Test
    void moveOfPointWithXYWithVector() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint().move(new BigDecimalVector(BigDecimal.ONE)),
            BigDecimal.ONE, BigDecimal.ONE
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiHalf() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI / 2d)),
            BigDecimal.valueOf(-2d), BigDecimal.ONE
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPi() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI)),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(-2d)
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiThreeHalf() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI * 3d / 2d)),
            BigDecimal.valueOf(2d), BigDecimal.ONE.negate()
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithTwoPi() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI * 2d)),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiHalf() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI / 2d)
            ),
            BigDecimal.ZERO, BigDecimal.ONE
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPi() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI)
            ),
            BigDecimal.ONE, BigDecimal.ZERO
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiThreeHalf() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI * 3d / 2d)
            ),
            BigDecimal.valueOf(2d), BigDecimal.ONE
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithTwoPi() {
        GeometryAssertions.assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI * 2d)
            ),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void copyOfPointWithXY() {
        Point<BigDecimal> point = new BigDecimalPoint(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals(point, point.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfPointWithXY() {
        Point<BigDecimal> point = new BigDecimalPoint(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals(
            point,
            new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d))
        );
        Assertions.assertNotEquals(
            point,
            new BigDecimalPoint(BigDecimal.valueOf(3d), BigDecimal.valueOf(2d))
        );
    }

    @Test
    void hashCodeOfPointWithXY() {
        Assertions.assertEquals(
            21143,
            new BigDecimalPoint(
                BigDecimal.valueOf(2d),
                BigDecimal.valueOf(3d)
            ).hashCode()
        );
    }

    @Test
    void toStringOfPointWithXY() {
        Point<BigDecimal> point = new BigDecimalPoint(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals("2.0|3.0", point.toString());
    }

    @Test
    void compareToOfPointWithXY() {
        Point<BigDecimal> point = new BigDecimalPoint(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals(
            0, point.compareTo(new BigDecimalPoint(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
            ))
        );
        Assertions.assertEquals(
            -1, point.compareTo(new BigDecimalPoint(
                BigDecimal.valueOf(3d), BigDecimal.ONE
            ))
        );
        Assertions.assertEquals(
            1, point.compareTo(new BigDecimalPoint(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(2d)
            ))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new BigDecimalPoint(), Point.class);
    }

    // endregion
}
