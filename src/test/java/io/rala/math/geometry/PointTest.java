package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertThatPoint;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class PointTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertThatPoint(new TestPoint()).hasZeroXY();
    }

    @Test
    void constructorWithXYParameter() {
        assertThatPoint(new TestPoint(1)).hasXY(1);
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertThatPoint(new TestPoint(2, 2)).hasXY(2);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertThatPoint(new TestPoint(2, 3)).hasX(2).hasY(3);
    }

    @Test
    void createAndSetX() {
        Point<Number> point = new TestPoint();
        assertThatPoint(point).hasZeroXY();
        point.setX(1);
        assertThatPoint(point).hasX(1).hasY(0);
    }

    @Test
    void createAndSetY() {
        Point<Number> point = new TestPoint();
        assertThatPoint(point).hasZeroXY();
        point.setY(2);
        assertThatPoint(point).hasX(0).hasY(2);
    }

    @Test
    void createAndSetXY() {
        Point<Number> point = new TestPoint();
        assertThatPoint(point).hasZeroXY();
        point.setXY(3);
        assertThatPoint(point).hasXY(3);
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfPointWithX0_5Y1_5() {
        TestPoint point = new TestPoint(0.5, 1.5);
        Point<Integer> result = new Point<>(new IntegerArithmetic(), 0, 1);
        assertThatPoint(point.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThatPoint(new TestPoint()).isValid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatPoint(new TestPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY))
            .isInvalid();
    }

    @Test
    void moveOfPointWithXYWithXY() {
        assertThatPoint(new TestPoint().move(1))
            .hasX(1).hasY(1);
    }

    @Test
    void moveOfPointWithXYWithXAndY() {
        assertThatPoint(new TestPoint().move(1, 1))
            .hasX(1).hasY(1);
    }

    @Test
    void moveOfPointWithXYWithVector() {
        assertThatPoint(new TestPoint().move(new TestVector(1)))
            .hasX(1).hasY(1);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiHalf() {
        assertThatPoint(new TestPoint(1, 2).rotate(Math.PI / 2))
            .hasX(-2).hasY(1);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPi() {
        assertThatPoint(new TestPoint(1, 2).rotate(Math.PI))
            .hasX(-1).hasY(-2);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiThreeHalf() {
        assertThatPoint(new TestPoint(1, 2).rotate(Math.PI * 3 / 2))
            .hasX(2).hasY(-1);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithTwoPi() {
        assertThatPoint(new TestPoint(1, 2).rotate(Math.PI * 2))
            .hasX(1).hasY(2);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiHalf() {
        assertThatPoint(new TestPoint(1, 2)
            .rotate(new TestPoint(1), Math.PI / 2)
        ).hasX(0).hasY(1);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPi() {
        assertThatPoint(new TestPoint(1, 2)
            .rotate(new TestPoint(1), Math.PI)
        ).hasX(1).hasY(0);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiThreeHalf() {
        assertThatPoint(new TestPoint(1, 2)
            .rotate(new TestPoint(1), Math.PI * 3 / 2)
        ).hasX(2).hasY(1);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithTwoPi() {
        assertThatPoint(new TestPoint(1, 2)
            .rotate(new TestPoint(1), Math.PI * 2)
        ).hasX(1).hasY(2);
    }

    @Test
    void copyOfPointWithXY() {
        assertCopyable(new TestPoint(2, 3));
    }

    // endregion

    // region override

    @Test
    void equalsOfPointWithXY() {
        assertThatPoint(new TestPoint(2, 3))
            .isEqualTo(new TestPoint(2, 3))
            .isNotEqualTo(new TestPoint(3, 2));
    }

    @Test
    void hashCodeOfPointWithXY() {
        assertThat(new TestPoint(2, 3).hashCode()).isEqualTo(1026);
    }

    @Test
    void toStringOfPointWithXY() {
        Point<Number> point = new TestPoint(2, 3);
        assertThatPoint(point).hasToString("2|3");
    }

    @Test
    void compareToOfPointWithXY() {
        Point<Number> point = new TestPoint(2, 3);
        assertThatPoint(point)
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
