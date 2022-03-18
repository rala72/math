package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.testUtils.assertion.UtilsAssertions;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertThatPoint;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static org.assertj.core.api.Assertions.assertThat;

class DoublePointTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertThatPoint(new DoublePoint()).hasZeroXY();
    }

    @Test
    void constructorWithXYParameter() {
        assertThatPoint(new DoublePoint(1d)).hasXY(1d);
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertThatPoint(new DoublePoint(2d, 2d)).hasXY(2d);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertThatPoint(new DoublePoint(2d, 3d)).hasX(2d).hasY(3d);
    }

    @Test
    void createAndSetX() {
        Point<Double> point = new DoublePoint();
        assertThatPoint(point).hasZeroXY();
        point.setX(1d);
        assertThatPoint(point).hasX(1d).hasY(0d);
    }

    @Test
    void createAndSetY() {
        Point<Double> point = new DoublePoint();
        assertThatPoint(point).hasZeroXY();
        point.setY(2d);
        assertThatPoint(point).hasX(0d).hasY(2d);
    }

    @Test
    void createAndSetXY() {
        Point<Double> point = new DoublePoint();
        assertThatPoint(point).hasZeroXY();
        point.setXY(3d);
        assertThatPoint(point).hasXY(3d);
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfPointWithX0_5Y1_5() {
        DoublePoint point = new DoublePoint(0.5, 1.5);
        Point<Integer> result = new Point<>(new IntegerArithmetic(), 0, 1);
        assertThatPoint(point.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThatPoint(new DoublePoint()).isValid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatPoint(new DoublePoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY))
            .isInvalid();
    }

    @Test
    void moveOfPointWithXYWithXY() {
        assertThatPoint(new DoublePoint().move(1d)).hasXY(1d);
    }

    @Test
    void moveOfPointWithXYWithXAndY() {
        assertThatPoint(new DoublePoint().move(1d, 1d)).hasXY(1d);
    }

    @Test
    void moveOfPointWithXYWithVector() {
        assertThatPoint(
            new DoublePoint().move(new DoubleVector(1d))
        ).hasX(1d).hasY(1d);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiHalf() {
        assertThatPoint(
            new DoublePoint(1d, 2d).rotate(Math.PI / 2d)
        ).hasX(-2d).hasY(1d);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPi() {
        assertThatPoint(
            new DoublePoint(1d, 2d).rotate(Math.PI)
        ).hasX(-1d).hasY(-2d);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiThreeHalf() {
        assertThatPoint(
            new DoublePoint(1d, 2d).rotate(Math.PI * 3d / 2d)
        ).hasX(2d).hasY(-1d);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithTwoPi() {
        assertThatPoint(
            new DoublePoint(1d, 2d).rotate(Math.PI * 2d)
        ).hasX(1d).hasY(2d);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiHalf() {
        assertThatPoint(
            new DoublePoint(1d, 2d).rotate(new DoublePoint(1d), Math.PI / 2d)
        ).hasX(0d).hasY(1d);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPi() {
        assertThatPoint(
            new DoublePoint(1d, 2d).rotate(new DoublePoint(1d), Math.PI)
        ).hasX(1d).hasY(0d);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiThreeHalf() {
        assertThatPoint(
            new DoublePoint(1d, 2d).rotate(new DoublePoint(1d), Math.PI * 3d / 2d)
        ).hasX(2d).hasY(1d);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithTwoPi() {
        assertThatPoint(
            new DoublePoint(1d, 2d).rotate(new DoublePoint(1d), Math.PI * 2d)
        ).hasX(1d).hasY(2d);
    }

    @Test
    void copyOfPointWithXY() {
        assertCopyable(new DoublePoint(2d, 3d));
    }

    // endregion

    // region override

    @Test
    void equalsOfPointWithXY() {
        assertThatPoint(new DoublePoint(2d, 3d))
            .isEqualTo(new DoublePoint(2d, 3d))
            .isNotEqualTo(new DoublePoint(3d, 2d));
    }

    @Test
    void hashCodeOfPointWithXY() {
        assertThat(new DoublePoint(2d, 3d).hashCode()).isEqualTo(525249);
    }

    @Test
    void toStringOfPointWithXY() {
        Point<Double> point = new DoublePoint(2d, 3d);
        assertThatPoint(point).hasToString("2.0|3.0");
    }

    @Test
    void compareToOfPointWithXY() {
        Point<Double> point = new DoublePoint(2d, 3d);
        assertThatPoint(point)
            .isEqualByComparingTo(new DoublePoint(2d, 3d))
            .isLessThan(new DoublePoint(3d, 1d))
            .isGreaterThan(new DoublePoint(2d, 2d));
    }

    @Test
    void serializable() {
        UtilsAssertions.assertSerializable(new DoublePoint(), DoublePoint.class);
    }

    // endregion
}
