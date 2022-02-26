package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Point;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertPoint;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class DoublePointTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertPoint(new DoublePoint());
    }

    @Test
    void constructorWithXYParameter() {
        assertPoint(new DoublePoint(1d), 1d);
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertPoint(new DoublePoint(2d, 2d), 2d);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertPoint(new DoublePoint(2d, 3d), 2d, 3d);
    }

    @Test
    void createAndSetX() {
        Point<Double> point = new DoublePoint();
        assertPoint(point);
        point.setX(1d);
        assertPoint(point, 1d, 0d);
    }

    @Test
    void createAndSetY() {
        Point<Double> point = new DoublePoint();
        assertPoint(point);
        point.setY(2d);
        assertPoint(point, 0d, 2d);
    }

    @Test
    void createAndSetXY() {
        Point<Double> point = new DoublePoint();
        assertPoint(point);
        point.setXY(3d);
        assertPoint(point, 3d, 3d);
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfPointWithX0_5Y1_5() {
        DoublePoint point = new DoublePoint(0.5, 1.5);
        Point<Integer> result = new Point<>(new IntegerArithmetic(), 0, 1);
        assertThat(point.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new DoublePoint().isValid()).isTrue();
    }

    @Test
    void isValidWithInfValues() {
        assertThat(new DoublePoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
            .isValid()).isFalse();
    }

    @Test
    void moveOfPointWithXYWithXY() {
        assertPoint(new DoublePoint().move(1d), 1d, 1d);
    }

    @Test
    void moveOfPointWithXYWithXAndY() {
        assertPoint(new DoublePoint().move(1d, 1d), 1d, 1d);
    }

    @Test
    void moveOfPointWithXYWithVector() {
        assertPoint(
            new DoublePoint().move(new DoubleVector(1d)),
            1d, 1d
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiHalf() {
        assertPoint(
            new DoublePoint(1d, 2d).rotate(Math.PI / 2d),
            -2d, 1d
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPi() {
        assertPoint(
            new DoublePoint(1d, 2d).rotate(Math.PI),
            -1d, -2d
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiThreeHalf() {
        assertPoint(
            new DoublePoint(1d, 2d).rotate(Math.PI * 3d / 2d),
            2d, -1d
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithTwoPi() {
        assertPoint(
            new DoublePoint(1d, 2d).rotate(Math.PI * 2d),
            1d, 2d
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiHalf() {
        assertPoint(
            new DoublePoint(1d, 2d).rotate(new DoublePoint(1d), Math.PI / 2d),
            0d, 1d
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPi() {
        assertPoint(
            new DoublePoint(1d, 2d).rotate(new DoublePoint(1d), Math.PI),
            1d, 0d
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiThreeHalf() {
        assertPoint(
            new DoublePoint(1d, 2d).rotate(new DoublePoint(1d), Math.PI * 3d / 2d),
            2d, 1d
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithTwoPi() {
        assertPoint(
            new DoublePoint(1d, 2d).rotate(new DoublePoint(1d), Math.PI * 2d),
            1d, 2d
        );
    }

    @Test
    void copyOfPointWithXY() {
        Point<Double> point = new DoublePoint(2d, 3d);
        assertThat(point.copy()).isEqualTo(point);
    }

    // endregion

    // region override

    @Test
    void equalsOfPointWithXY() {
        Point<Double> point = new DoublePoint(2d, 3d);
        assertThat(new DoublePoint(2d, 3d)).isEqualTo(point);
        assertThat(new DoublePoint(3d, 2d)).isNotEqualTo(point);
    }

    @Test
    void hashCodeOfPointWithXY() {
        assertThat(new DoublePoint(2d, 3d).hashCode()).isEqualTo(525249);
    }

    @Test
    void toStringOfPointWithXY() {
        Point<Double> point = new DoublePoint(2d, 3d);
        assertThat(point).hasToString("2.0|3.0");
    }

    @Test
    void compareToOfPointWithXY() {
        Point<Double> point = new DoublePoint(2d, 3d);
        assertThat(point)
            .isEqualByComparingTo(new DoublePoint(2d, 3d))
            .isLessThan(new DoublePoint(3d, 1d))
            .isGreaterThan(new DoublePoint(2d, 2d));
    }

    @Test
    void serializable() {
        assertSerializable(new DoublePoint(), DoublePoint.class);
    }

    // endregion
}
