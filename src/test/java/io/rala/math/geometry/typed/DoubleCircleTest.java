package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertCircle;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class DoubleCircleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertCircle(new DoubleCircle());
    }

    @Test
    void constructorWithCenterButWithoutRadius() {
        assertCircle(
            new DoubleCircle(new DoublePoint(1d)),
            new DoublePoint(1d)
        );
    }

    @Test
    void constructorWithRadiusButWithoutCenter() {
        assertCircle(new DoubleCircle(2d), 2d);
    }

    @Test
    void constructorWithCenterAndPoint() {
        assertCircle(
            new DoubleCircle(new DoublePoint(2d), new DoublePoint(1d)),
            new DoublePoint(2d),
            Math.sqrt(2d)
        );
    }

    @Test
    void constructorWithCenterAndRadius() {
        assertCircle(
            new DoubleCircle(new DoublePoint(2d), 3d),
            new DoublePoint(2d), 3d
        );
    }

    @Test
    void createAndSetCenter() {
        Circle<Double> circle = new DoubleCircle();
        assertCircle(circle);
        circle.setCenter(new DoublePoint(1d));
        assertCircle(circle, new DoublePoint(1d), 1d);
    }

    @Test
    void createAndSetRadius() {
        Circle<Double> circle = new DoubleCircle();
        assertCircle(circle);
        circle.setRadius(2d);
        assertCircle(circle, 2d);
    }

    @Test
    void createAndExpectDiameterToBeDouble() {
        Circle<Double> circle = new DoubleCircle();
        assertThat(circle.getDiameter()).isEqualTo(2d);
        circle.setRadius(2d);
        assertThat(circle.getDiameter()).isEqualTo(4d);
        circle.setDiameter(2d);
        assertThat(circle.getDiameter()).isEqualTo(2d);
    }

    @Test
    void createAndSetRadiusAndExpectDiameterToBeDouble() {
        Circle<Double> circle = new DoubleCircle();
        circle.setRadius(2d);
        assertThat(circle.getDiameter()).isEqualTo(4d);
    }

    @Test
    void createAndSetDiameterAndExpectRadiusToBeHalf() {
        Circle<Double> circle = new DoubleCircle();
        circle.setDiameter(2d);
        assertThat(circle.getDiameter()).isEqualTo(2d);
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfCircleWithoutParameter() {
        assertThat(new DoubleCircle().area()).isCloseTo(Math.PI, offset(GeometryAssertions.DELTA));
    }

    @Test
    void areaOfCircleWithRadius2() {
        assertThat(new DoubleCircle(2d).area()).isEqualTo(12.566370614359172);
    }

    @Test
    void areaOfCircleWithRadius3() {
        assertThat(new DoubleCircle(3d).area()).isEqualTo(28.274333882308138);
    }

    @Test
    void circumferenceOfCircleWithoutParameter() {
        assertThat(new DoubleCircle().circumference()).isEqualTo(6.283185307179586);
    }

    @Test
    void circumferenceOfCircleWithRadius2() {
        assertThat(new DoubleCircle(2d).circumference()).isEqualTo(12.566370614359172);
    }

    @Test
    void circumferenceOfCircleWithRadius3() {
        assertThat(new DoubleCircle(3d).circumference()).isEqualTo(18.84955592153876);
    }

    // endregion

    // region isUnitCircle

    @Test
    void isUnitCircleWithRadius0() {
        assertThat(new DoubleCircle(0d).isUnitCircle()).isFalse();
    }

    @Test
    void isUnitCircleWithRadius1() {
        assertThat(new DoubleCircle(1d).isUnitCircle()).isTrue();
    }

    @Test
    void isUnitCircleWithRadius2() {
        assertThat(new DoubleCircle(2d).isUnitCircle()).isFalse();
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
        assertThat(circle.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new DoubleCircle().isValid()).isTrue();
    }

    @Test
    void isValidWithNegativeRadius() {
        assertThat(new DoubleCircle(-1d).isValid()).isFalse();
    }

    @Test
    void isValidWithInfValues() {
        assertThat(new DoubleCircle(
            new DoublePoint(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY)
            .isValid()).isFalse();
    }

    @Test
    void moveOfCircleWithoutParameterWithXY() {
        assertCircle(
            new DoubleCircle().move(1d),
            new DoublePoint(1d)
        );
    }

    @Test
    void moveOfCircleWithoutParameterWithXAndY() {
        assertCircle(
            new DoubleCircle().move(1d, 1d),
            new DoublePoint(1d)
        );
    }

    @Test
    void moveOfCircleWithoutParameterWithVector() {
        assertCircle(
            new DoubleCircle().move(new DoubleVector(1d)),
            new DoublePoint(1d)
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithoutCenterWithPiHalf() {
        assertCircle(new DoubleCircle(new DoublePoint(1d, 2d))
                .rotate(Math.PI / 2d),
            new DoublePoint(-2d, 1.0000000000000002)
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithCenterXY1WithPiHalf() {
        assertCircle(new DoubleCircle(new DoublePoint(1d, 2d))
                .rotate(new DoublePoint(1d), Math.PI / 2d),
            new DoublePoint(0d, 1d)
        );
    }

    @Test
    void copyOfCircleWithPointAndRadius() {
        Circle<Double> circle = new DoubleCircle(new DoublePoint(2d), 3d);
        assertThat(circle.copy()).isEqualTo(circle);
    }

    // endregion

    // region override

    @Test
    void equalsOfCircleWithPointAndRadius() {
        Circle<Double> circle = new DoubleCircle(new DoublePoint(2d), 3d);
        assertThat(new DoubleCircle(new DoublePoint(2d), 3d)).isEqualTo(circle);
        assertThat(new DoubleCircle(new DoublePoint(3d), 2d)).isNotEqualTo(circle);
    }

    @Test
    void hashCodeOfCircleWithPointAndRadius() {
        assertThat(new DoubleCircle(new DoublePoint(2d), 3d).hashCode()).isEqualTo(1074296864);
    }

    @Test
    void toStringOfCircleWithPointAndRadius() {
        Circle<Double> circle = new DoubleCircle(new DoublePoint(2d), 3d);
        assertThat(circle.toString()).isEqualTo("2.0|2.0 3.0");
    }

    @Test
    void compareToOfCircleWithCenterAndRadius() {
        Circle<Double> circle = new DoubleCircle(new DoublePoint(2d), 3d);
        assertThat(circle.compareTo(new DoubleCircle(new DoublePoint(2d), 3d))).isEqualTo(0);
        assertThat(circle.compareTo(new DoubleCircle(new DoublePoint(3d), 3d))).isEqualTo(-1);
        assertThat(circle.compareTo(new DoubleCircle(new DoublePoint(2d), 1d))).isEqualTo(1);
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleCircle(), DoubleCircle.class);
    }

    // endregion
}
