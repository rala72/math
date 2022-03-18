package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.geometry.TestCircle;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertThatCircle;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class CircleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertThatCircle(new TestCircle()).hasCenterInZero().hasRadiusOne();
    }

    @Test
    void constructorWithCenterButWithoutRadius() {
        assertThatCircle(new TestCircle(new TestPoint(1))).hasCenter(new TestPoint(1));
    }

    @Test
    void constructorWithRadiusButWithoutCenter() {
        assertThatCircle(new TestCircle(2)).hasRadius(2);
    }

    @Test
    void constructorWithCenterAndPoint() {
        assertThatCircle(
            new TestCircle(new TestPoint(2), new TestPoint(1))
        ).hasCenter(new TestPoint(2)).hasRadius(Math.sqrt(2));
    }

    @Test
    void constructorWithCenterAndRadius() {
        assertThatCircle(new TestCircle(new TestPoint(2), 3))
            .hasCenter(new TestPoint(2)).hasRadius(3);
    }

    @Test
    void createAndSetCenter() {
        Circle<Number> circle = new TestCircle();
        assertThatCircle(circle).hasCenterInZero().hasRadiusOne();
        circle.setCenter(new TestPoint(1));
        assertThatCircle(circle).hasCenter(new TestPoint(1)).hasRadius(1);
    }

    @Test
    void createAndSetRadius() {
        Circle<Number> circle = new TestCircle();
        assertThatCircle(circle).hasCenterInZero().hasRadiusOne();
        circle.setRadius(2);
        assertThatCircle(circle).hasRadius(2);
    }

    @Test
    void createAndExpectDiameterToBeDouble() {
        Circle<Number> circle = new TestCircle();
        assertThatCircle(circle).hasDiameter(2d);
        circle.setRadius(2);
        assertThatCircle(circle).hasDiameter(4d);
        circle.setDiameter(2);
        assertThatCircle(circle).hasDiameter(2d);
    }

    @Test
    void createAndSetRadiusAndExpectDiameterToBeDouble() {
        Circle<Number> circle = new TestCircle();
        circle.setRadius(2);
        assertThatCircle(circle).hasDiameter(4d);
    }

    @Test
    void createAndSetDiameterAndExpectRadiusToBeHalf() {
        Circle<Number> circle = new TestCircle();
        circle.setDiameter(2);
        assertThatCircle(circle).hasDiameter(2d);
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfCircleWithoutParameter() {
        assertThatCircle(new TestCircle()).hasAreaCloseTo(Math.PI);
    }

    @Test
    void areaOfCircleWithRadius2() {
        assertThatCircle(new TestCircle(2)).hasArea(12.566370614359172);
    }

    @Test
    void areaOfCircleWithRadius3() {
        assertThatCircle(new TestCircle(3)).hasArea(28.274333882308138);
    }

    @Test
    void circumferenceOfCircleWithoutParameter() {
        assertThatCircle(new TestCircle()).hasCircumference(6.283185307179586);
    }

    @Test
    void circumferenceOfCircleWithRadius2() {
        assertThatCircle(new TestCircle(2)).hasCircumference(12.566370614359172);
    }

    @Test
    void circumferenceOfCircleWithRadius3() {
        assertThatCircle(new TestCircle(3)).hasCircumference(18.84955592153876);
    }

    // endregion

    // region isUnitCircle

    @Test
    void isUnitCircleWithRadius0() {
        assertThatCircle(new TestCircle(0)).isNoUnitCircle();
    }

    @Test
    void isUnitCircleWithRadius1() {
        assertThatCircle(new TestCircle(1d)).isUnitCircle();
    }

    @Test
    void isUnitCircleWithRadius2() {
        assertThatCircle(new TestCircle(2)).isNoUnitCircle();
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
        assertThatCircle(circle.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThatCircle(new TestCircle()).isValid();
    }

    @Test
    void isValidWithNegativeRadius() {
        assertThatCircle(new TestCircle(-1)).isInvalid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatCircle(new TestCircle(
            new TestPoint(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY)
        ).isInvalid();
    }

    @Test
    void moveOfCircleWithoutParameterWithXY() {
        assertThatCircle(new TestCircle().move(1))
            .hasCenter(new TestPoint(1d));
    }

    @Test
    void moveOfCircleWithoutParameterWithXAndY() {
        assertThatCircle(new TestCircle().move(1, 1))
            .hasCenter(new TestPoint(1d));
    }

    @Test
    void moveOfCircleWithoutParameterWithVector() {
        assertThatCircle(new TestCircle().move(new TestVector(1)))
            .hasCenter(new TestPoint(1d));
    }

    @Test
    void rotateOfCircleWithX1Y2WithoutCenterWithPiHalf() {
        assertThatCircle(new TestCircle(new TestPoint(1, 2))
            .rotate(Math.PI / 2)
        ).hasCenterCloseTo(new TestPoint(-2d, 1d));
    }

    @Test
    void rotateOfCircleWithX1Y2WithCenterXY1WithPiHalf() {
        assertThatCircle(new TestCircle(new TestPoint(1, 2))
            .rotate(new TestPoint(1), Math.PI / 2)
        ).hasCenter(new TestPoint(0d, 1d));
    }

    @Test
    void copyOfCircleWithPointAndRadius() {
        assertCopyable(new TestCircle(new TestPoint(2), 3));
    }

    // endregion

    // region override

    @Test
    void equalsOfCircleWithPointAndRadius() {
        assertThatCircle(new TestCircle(new TestPoint(2), 3))
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
        assertThatCircle(circle).hasToString("2.0|2.0 3.0");
    }

    @Test
    void compareToOfCircleWithCenterAndRadius() {
        Circle<Number> circle = new TestCircle(new TestPoint(2), 3);
        assertThatCircle(circle)
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
