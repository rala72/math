package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertThatCircle;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class DoubleCircleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertThatCircle(new DoubleCircle()).hasCenterInZero().hasRadiusOne();
    }

    @Test
    void constructorWithCenterButWithoutRadius() {
        assertThatCircle(new DoubleCircle(new DoublePoint(1d)))
            .hasCenter(new DoublePoint(1d));
    }

    @Test
    void constructorWithRadiusButWithoutCenter() {
        assertThatCircle(new DoubleCircle(2d)).hasRadius(2d);
    }

    @Test
    void constructorWithCenterAndPoint() {
        assertThatCircle(
            new DoubleCircle(new DoublePoint(2d), new DoublePoint(1d))
        ).hasCenter(new DoublePoint(2d)).hasRadius(Math.sqrt(2d));
    }

    @Test
    void constructorWithCenterAndRadius() {
        assertThatCircle(new DoubleCircle(new DoublePoint(2d), 3d))
            .hasCenter(new DoublePoint(2d)).hasRadius(3d);
    }

    @Test
    void createAndSetCenter() {
        Circle<Double> circle = new DoubleCircle();
        assertThatCircle(circle).hasCenterInZero().hasRadiusOne();
        circle.setCenter(new DoublePoint(1d));
        assertThatCircle(circle).hasCenter(new DoublePoint(1d)).hasRadius(1d);
    }

    @Test
    void createAndSetRadius() {
        Circle<Double> circle = new DoubleCircle();
        assertThatCircle(circle).hasCenterInZero().hasRadiusOne();
        circle.setRadius(2d);
        assertThatCircle(circle).hasRadius(2d);
    }

    @Test
    void createAndExpectDiameterToBeDouble() {
        Circle<Double> circle = new DoubleCircle();
        assertThatCircle(circle).hasDiameter(2d);
        circle.setRadius(2d);
        assertThatCircle(circle).hasDiameter(4d);
        circle.setDiameter(2d);
        assertThatCircle(circle).hasDiameter(2d);
    }

    @Test
    void createAndSetRadiusAndExpectDiameterToBeDouble() {
        Circle<Double> circle = new DoubleCircle();
        circle.setRadius(2d);
        assertThatCircle(circle).hasDiameter(4d);
    }

    @Test
    void createAndSetDiameterAndExpectRadiusToBeHalf() {
        Circle<Double> circle = new DoubleCircle();
        circle.setDiameter(2d);
        assertThatCircle(circle).hasDiameter(2d);
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfCircleWithoutParameter() {
        assertThatCircle(new DoubleCircle()).hasAreaCloseTo(Math.PI);
    }

    @Test
    void areaOfCircleWithRadius2() {
        assertThatCircle(new DoubleCircle(2d)).hasArea(12.566370614359172);
    }

    @Test
    void areaOfCircleWithRadius3() {
        assertThatCircle(new DoubleCircle(3d)).hasArea(28.274333882308138);
    }

    @Test
    void circumferenceOfCircleWithoutParameter() {
        assertThatCircle(new DoubleCircle()).hasCircumference(6.283185307179586);
    }

    @Test
    void circumferenceOfCircleWithRadius2() {
        assertThatCircle(new DoubleCircle(2d)).hasCircumference(12.566370614359172);
    }

    @Test
    void circumferenceOfCircleWithRadius3() {
        assertThatCircle(new DoubleCircle(3d)).hasCircumference(18.84955592153876);
    }

    // endregion

    // region isUnitCircle

    @Test
    void isUnitCircleWithRadius0() {
        assertThatCircle(new DoubleCircle(0d)).isNoUnitCircle();
    }

    @Test
    void isUnitCircleWithRadius1() {
        assertThatCircle(new DoubleCircle(1d)).isUnitCircle();
    }

    @Test
    void isUnitCircleWithRadius2() {
        assertThatCircle(new DoubleCircle(2d)).isNoUnitCircle();
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfCircleWithXY0_5R1_5() {
        DoubleCircle circle = new DoubleCircle(new DoublePoint(0.5), 1.5);
        IntegerArithmetic integerArithmetic = new IntegerArithmetic();
        Circle<Integer> result = new Circle<>(integerArithmetic,
            new Point<>(integerArithmetic, 0), 1
        );
        assertThatCircle(circle.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThatCircle(new DoubleCircle()).isValid();
    }

    @Test
    void isValidWithNegativeRadius() {
        assertThatCircle(new DoubleCircle(-1d)).isInvalid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatCircle(new DoubleCircle(
            new DoublePoint(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY)
        ).isInvalid();
    }

    @Test
    void moveOfCircleWithoutParameterWithXY() {
        assertThatCircle(new DoubleCircle().move(1d))
            .hasCenter(new DoublePoint(1d));
    }

    @Test
    void moveOfCircleWithoutParameterWithXAndY() {
        assertThatCircle(new DoubleCircle().move(1d, 1d))
            .hasCenter(new DoublePoint(1d));
    }

    @Test
    void moveOfCircleWithoutParameterWithVector() {
        assertThatCircle(new DoubleCircle().move(new DoubleVector(1d)))
            .hasCenter(new DoublePoint(1d));
    }

    @Test
    void rotateOfCircleWithX1Y2WithoutCenterWithPiHalf() {
        assertThatCircle(new DoubleCircle(new DoublePoint(1d, 2d))
            .rotate(Math.PI / 2d)
        ).hasCenterCloseTo(new DoublePoint(-2d, 1d));
    }

    @Test
    void rotateOfCircleWithX1Y2WithCenterXY1WithPiHalf() {
        assertThatCircle(new DoubleCircle(new DoublePoint(1d, 2d))
            .rotate(new DoublePoint(1d), Math.PI / 2d)
        ).hasCenter(new DoublePoint(0d, 1d));
    }

    @Test
    void copyOfCircleWithPointAndRadius() {
        assertCopyable(new DoubleCircle(new DoublePoint(2d), 3d));
    }

    // endregion

    // region override

    @Test
    void equalsOfCircleWithPointAndRadius() {
        assertThatCircle(new DoubleCircle(new DoublePoint(2d), 3d))
            .isEqualTo(new DoubleCircle(new DoublePoint(2d), 3d))
            .isNotEqualTo(new DoubleCircle(new DoublePoint(3d), 2d));
    }

    @Test
    void hashCodeOfCircleWithPointAndRadius() {
        assertThat(new DoubleCircle(new DoublePoint(2d), 3d).hashCode()).isEqualTo(1074296864);
    }

    @Test
    void toStringOfCircleWithPointAndRadius() {
        Circle<Double> circle = new DoubleCircle(new DoublePoint(2d), 3d);
        assertThatCircle(circle).hasToString("2.0|2.0 3.0");
    }

    @Test
    void compareToOfCircleWithCenterAndRadius() {
        Circle<Double> circle = new DoubleCircle(new DoublePoint(2d), 3d);
        assertThatCircle(circle)
            .isEqualByComparingTo(new DoubleCircle(new DoublePoint(2d), 3d))
            .isLessThan(new DoubleCircle(new DoublePoint(3d), 3d))
            .isGreaterThan(new DoubleCircle(new DoublePoint(2d), 1d));
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleCircle(), DoubleCircle.class);
    }

    // endregion
}
