package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.geometry.TestCircle;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertCircle;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class CircleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertCircle(new TestCircle());
    }

    @Test
    void constructorWithCenterButWithoutRadius() {
        assertCircle(new TestCircle(new TestPoint(1)), new TestPoint(1));
    }

    @Test
    void constructorWithRadiusButWithoutCenter() {
        assertCircle(new TestCircle(2), 2);
    }

    @Test
    void constructorWithCenterAndPoint() {
        assertCircle(
            new TestCircle(new TestPoint(2), new TestPoint(1)),
            new TestPoint(2),
            Math.sqrt(2)
        );
    }

    @Test
    void constructorWithCenterAndRadius() {
        assertCircle(
            new TestCircle(new TestPoint(2), 3),
            new TestPoint(2), 3
        );
    }

    @Test
    void createAndSetCenter() {
        Circle<Number> circle = new TestCircle();
        assertCircle(circle);
        circle.setCenter(new TestPoint(1));
        assertCircle(circle, new TestPoint(1), 1);
    }

    @Test
    void createAndSetRadius() {
        Circle<Number> circle = new TestCircle();
        assertCircle(circle);
        circle.setRadius(2);
        assertCircle(circle, 2);
    }

    @Test
    void createAndExpectDiameterToBeDouble() {
        Circle<Number> circle = new TestCircle();
        assertThat(circle.getDiameter()).isEqualTo(2d);
        circle.setRadius(2);
        assertThat(circle.getDiameter()).isEqualTo(4d);
        circle.setDiameter(2);
        assertThat(circle.getDiameter()).isEqualTo(2d);
    }

    @Test
    void createAndSetRadiusAndExpectDiameterToBeDouble() {
        Circle<Number> circle = new TestCircle();
        circle.setRadius(2);
        assertThat(circle.getDiameter()).isEqualTo(4d);
    }

    @Test
    void createAndSetDiameterAndExpectRadiusToBeHalf() {
        Circle<Number> circle = new TestCircle();
        circle.setDiameter(2);
        assertThat(circle.getDiameter()).isEqualTo(2d);
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfCircleWithoutParameter() {
        assertThat(new TestCircle().area().doubleValue())
            .isCloseTo(Math.PI, offset(GeometryAssertions.DELTA));
    }

    @Test
    void areaOfCircleWithRadius2() {
        assertThat(new TestCircle(2).area()).isEqualTo(12.566370614359172);
    }

    @Test
    void areaOfCircleWithRadius3() {
        assertThat(new TestCircle(3).area()).isEqualTo(28.274333882308138);
    }

    @Test
    void circumferenceOfCircleWithoutParameter() {
        assertThat(new TestCircle().circumference()).isEqualTo(6.283185307179586);
    }

    @Test
    void circumferenceOfCircleWithRadius2() {
        assertThat(new TestCircle(2).circumference()).isEqualTo(12.566370614359172);
    }

    @Test
    void circumferenceOfCircleWithRadius3() {
        assertThat(new TestCircle(3).circumference()).isEqualTo(18.84955592153876);
    }

    // endregion

    // region isUnitCircle

    @Test
    void isUnitCircleWithRadius0() {
        assertThat(new TestCircle(0).isUnitCircle()).isFalse();
    }

    @Test
    void isUnitCircleWithRadius1() {
        assertThat(new TestCircle(1d).isUnitCircle()).isTrue();
    }

    @Test
    void isUnitCircleWithRadius2() {
        assertThat(new TestCircle(2).isUnitCircle()).isFalse();
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfCircleWithXY0_5R1_5() {
        TestCircle circle = new TestCircle(new TestPoint(0.5), 1.5);
        IntegerArithmetic integerArithmetic = new IntegerArithmetic();
        Circle<Integer> result = new Circle<>(integerArithmetic,
            new Point<>(integerArithmetic, 0), 1
        );
        assertThat(circle.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new TestCircle().isValid()).isTrue();
    }

    @Test
    void isValidWithNegativeRadius() {
        assertThat(new TestCircle(-1).isValid()).isFalse();
    }

    @Test
    void isValidWithInfValues() {
        assertThat(new TestCircle(
            new TestPoint(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY)
            .isValid()).isFalse();
    }

    @Test
    void moveOfCircleWithoutParameterWithXY() {
        assertCircle(new TestCircle().move(1), new TestPoint(1));
    }

    @Test
    void moveOfCircleWithoutParameterWithXAndY() {
        assertCircle(new TestCircle().move(1, 1), new TestPoint(1));
    }

    @Test
    void moveOfCircleWithoutParameterWithVector() {
        assertCircle(
            new TestCircle().move(new TestVector(1)),
            new TestPoint(1)
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithoutCenterWithPiHalf() {
        assertCircle(new TestCircle(new TestPoint(1, 2))
                .rotate(Math.PI / 2),
            new TestPoint(-2, 1.0000000000000002)
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithCenterXY1WithPiHalf() {
        assertCircle(new TestCircle(new TestPoint(1, 2))
                .rotate(new TestPoint(1), Math.PI / 2),
            new TestPoint(0, 1)
        );
    }

    @Test
    void copyOfCircleWithPointAndRadius() {
        Circle<Number> circle = new TestCircle(new TestPoint(2), 3);
        assertThat(circle.copy()).isEqualTo(circle);
    }

    // endregion

    // region override

    @Test
    void equalsOfCircleWithPointAndRadius() {
        assertThat(new TestCircle(new TestPoint(2), 3))
            .isEqualTo(new TestCircle(new TestPoint(2), 3))
            .isNotEqualTo(new TestCircle(new TestPoint(3), 2));
    }

    @Test
    void hashCodeOfCircleWithPointAndRadius() {
        assertThat(new TestCircle(new TestPoint(2), 3).hashCode()).isEqualTo(32739);
    }

    @Test
    void toStringOfCircleWithPointAndRadius() {
        Circle<Number> circle = new TestCircle(new TestPoint(2d), 3d);
        assertThat(circle).hasToString("2.0|2.0 3.0");
    }

    @Test
    void compareToOfCircleWithCenterAndRadius() {
        Circle<Number> circle = new TestCircle(new TestPoint(2), 3);
        assertThat(circle)
            .isEqualByComparingTo(new TestCircle(new TestPoint(2), 3))
            .isLessThan(new TestCircle(new TestPoint(3), 3))
            .isGreaterThan(new TestCircle(new TestPoint(2), 1));
    }

    @Test
    void serializable() {
        assertSerializable(new TestCircle(), TestCircle.class);
    }

    // endregion
}
