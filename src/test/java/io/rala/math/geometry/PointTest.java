package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertPoint;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class PointTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertPoint(new TestPoint());
    }

    @Test
    void constructorWithXYParameter() {
        assertPoint(new TestPoint(1), 1);
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertPoint(new TestPoint(2, 2), 2);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertPoint(new TestPoint(2, 3), 2, 3);
    }

    @Test
    void createAndSetX() {
        Point<Number> point = new TestPoint();
        assertPoint(point);
        point.setX(1);
        assertPoint(point, 1, 0);
    }

    @Test
    void createAndSetY() {
        Point<Number> point = new TestPoint();
        assertPoint(point);
        point.setY(2);
        assertPoint(point, 0, 2);
    }

    @Test
    void createAndSetXY() {
        Point<Number> point = new TestPoint();
        assertPoint(point);
        point.setXY(3);
        assertPoint(point, 3, 3);
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfPointWithX0_5Y1_5() {
        TestPoint point = new TestPoint(0.5, 1.5);
        Point<Integer> result = new Point<>(new IntegerArithmetic(), 0, 1);
        assertThat(point.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new TestPoint().isValid()).isTrue();
    }

    @Test
    void isValidWithInfValues() {
        assertThat(new TestPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
            .isValid()).isFalse();
    }

    @Test
    void moveOfPointWithXYWithXY() {
        assertPoint(new TestPoint().move(1), 1, 1);
    }

    @Test
    void moveOfPointWithXYWithXAndY() {
        assertPoint(new TestPoint().move(1, 1), 1, 1);
    }

    @Test
    void moveOfPointWithXYWithVector() {
        assertPoint(new TestPoint().move(new TestVector(1)), 1, 1);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiHalf() {
        assertPoint(new TestPoint(1, 2).rotate(Math.PI / 2), -2, 1);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPi() {
        assertPoint(new TestPoint(1, 2).rotate(Math.PI), -1, -2);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiThreeHalf() {
        assertPoint(
            new TestPoint(1, 2).rotate(Math.PI * 3 / 2),
            2, -1
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithTwoPi() {
        assertPoint(new TestPoint(1, 2).rotate(Math.PI * 2), 1, 2);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiHalf() {
        assertPoint(
            new TestPoint(1, 2).rotate(new TestPoint(1), Math.PI / 2),
            0, 1
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPi() {
        assertPoint(
            new TestPoint(1, 2).rotate(new TestPoint(1), Math.PI),
            1, 0
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiThreeHalf() {
        assertPoint(
            new TestPoint(1, 2).rotate(new TestPoint(1), Math.PI * 3 / 2),
            2, 1
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithTwoPi() {
        assertPoint(
            new TestPoint(1, 2).rotate(new TestPoint(1), Math.PI * 2),
            1, 2
        );
    }

    @Test
    void copyOfPointWithXY() {
        Point<Number> point = new TestPoint(2, 3);
        assertThat(point.copy()).isEqualTo(point);
    }

    // endregion

    // region override

    @Test
    void equalsOfPointWithXY() {
        Point<Number> point = new TestPoint(2, 3);
        assertThat(new TestPoint(2, 3)).isEqualTo(point);
        assertThat(new TestPoint(3, 2)).isNotEqualTo(point);
    }

    @Test
    void hashCodeOfPointWithXY() {
        assertThat(new TestPoint(2, 3).hashCode()).isEqualTo(1026);
    }

    @Test
    void toStringOfPointWithXY() {
        Point<Number> point = new TestPoint(2, 3);
        assertThat(point).hasToString("2|3");
    }

    @Test
    void compareToOfPointWithXY() {
        Point<Number> point = new TestPoint(2, 3);
        assertThat(point)
            .isEqualByComparingTo(new TestPoint(2, 3))
            .isLessThan(new TestPoint(3, 1))
            .isGreaterThan(new TestPoint(2, 2));
    }

    @Test
    void serializable() {
        assertSerializable(new TestPoint(), TestPoint.class);
    }

    // endregion
}
