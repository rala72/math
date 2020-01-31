package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DoubleCircleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        GeometryAssertions.assertCircle(new DoubleCircle());
    }

    @Test
    void constructorWithCenterButWithoutRadius() {
        GeometryAssertions.assertCircle(
            new DoubleCircle(new DoublePoint(1d)),
            new DoublePoint(1d)
        );
    }

    @Test
    void constructorWithRadiusButWithoutCenter() {
        GeometryAssertions.assertCircle(new DoubleCircle(2d), 2d);
    }

    @Test
    void constructorWithCenterAndPoint() {
        GeometryAssertions.assertCircle(
            new DoubleCircle(new DoublePoint(2d), new DoublePoint(1d)),
            new DoublePoint(2d),
            Math.sqrt(2d)
        );
    }

    @Test
    void constructorWithCenterAndRadius() {
        GeometryAssertions.assertCircle(
            new DoubleCircle(new DoublePoint(2d), 3d),
            new DoublePoint(2d), 3d
        );
    }

    @Test
    void createAndSetCenter() {
        Circle<Double> circle = new DoubleCircle();
        GeometryAssertions.assertCircle(circle);
        circle.setCenter(new DoublePoint(1d));
        GeometryAssertions.assertCircle(circle, new DoublePoint(1d), 1d);
    }

    @Test
    void createAndSetRadius() {
        Circle<Double> circle = new DoubleCircle();
        GeometryAssertions.assertCircle(circle);
        circle.setRadius(2d);
        GeometryAssertions.assertCircle(circle, 2d);
    }

    @Test
    void createAndExpectDiameterToBeDouble() {
        Circle<Double> circle = new DoubleCircle();
        Assertions.assertEquals(2d, circle.getDiameter());
        circle.setRadius(2d);
        Assertions.assertEquals(4d, circle.getDiameter());
        circle.setDiameter(2d);
        Assertions.assertEquals(2d, circle.getDiameter());
    }

    @Test
    void createAndSetRadiusAndExpectDiameterToBeDouble() {
        Circle<Double> circle = new DoubleCircle();
        circle.setRadius(2d);
        Assertions.assertEquals(4d, circle.getDiameter());
    }

    @Test
    void createAndSetDiameterAndExpectRadiusToBeHalf() {
        Circle<Double> circle = new DoubleCircle();
        circle.setDiameter(2d);
        Assertions.assertEquals(2d, circle.getDiameter());
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfCircleWithoutParameter() {
        Assertions.assertEquals(
            Math.PI,
            new DoubleCircle().area(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void areaOfCircleWithRadius2() {
        Assertions.assertEquals(12.566370614359172d, new DoubleCircle(2d).area());
    }

    @Test
    void areaOfCircleWithRadius3() {
        Assertions.assertEquals(28.274333882308138d, new DoubleCircle(3d).area());
    }

    @Test
    void circumferenceOfCircleWithoutParameter() {
        Assertions.assertEquals(6.283185307179586d, new DoubleCircle().circumference());
    }

    @Test
    void circumferenceOfCircleWithRadius2() {
        Assertions.assertEquals(12.566370614359172d,
            new DoubleCircle(2d).circumference()
        );
    }

    @Test
    void circumferenceOfCircleWithRadius3() {
        Assertions.assertEquals(18.84955592153876d,
            new DoubleCircle(3d).circumference()
        );
    }

    // endregion

    // region isUnitCircle

    @Test
    void isUnitCircleWithRadius0() {
        Assertions.assertFalse(new DoubleCircle(0d).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius1() {
        Assertions.assertTrue(new DoubleCircle(1d).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius2() {
        Assertions.assertFalse(new DoubleCircle(2d).isUnitCircle());
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
        Assertions.assertEquals(result,
            circle.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new DoubleCircle().isValid());
    }

    @Test
    void isValidWithNegativeRadius() {
        Assertions.assertFalse(new DoubleCircle(-1d).isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(new DoubleCircle(
            new DoublePoint(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY)
            .isValid()
        );
    }

    @Test
    void moveOfCircleWithoutParameterWithXY() {
        GeometryAssertions.assertCircle(
            new DoubleCircle().move(1d),
            new DoublePoint(1d)
        );
    }

    @Test
    void moveOfCircleWithoutParameterWithXAndY() {
        GeometryAssertions.assertCircle(
            new DoubleCircle().move(1d, 1d),
            new DoublePoint(1d)
        );
    }

    @Test
    void moveOfCircleWithoutParameterWithVector() {
        GeometryAssertions.assertCircle(
            new DoubleCircle().move(new DoubleVector(1d)),
            new DoublePoint(1d)
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithoutCenterWithPiHalf() {
        GeometryAssertions.assertCircle(new DoubleCircle(new DoublePoint(1d, 2d))
                .rotate(Math.PI / 2d),
            new DoublePoint(-2d, 1.0000000000000002d)
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithCenterXY1WithPiHalf() {
        GeometryAssertions.assertCircle(new DoubleCircle(new DoublePoint(1d, 2d))
                .rotate(new DoublePoint(1d), Math.PI / 2d),
            new DoublePoint(0d, 1d)
        );
    }

    @Test
    void copyOfCircleWithPointAndRadius() {
        Circle<Double> circle = new DoubleCircle(new DoublePoint(2d), 3d);
        Assertions.assertEquals(circle, circle.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfCircleWithPointAndRadius() {
        Circle<Double> circle = new DoubleCircle(new DoublePoint(2d), 3d);
        Assertions.assertEquals(
            circle,
            new DoubleCircle(new DoublePoint(2d), 3d)
        );
        Assertions.assertNotEquals(
            circle,
            new DoubleCircle(new DoublePoint(3d), 2d)
        );
    }

    @Test
    void hashCodeOfCircleWithPointAndRadius() {
        Assertions.assertEquals(
            1074296864,
            new DoubleCircle(new DoublePoint(2d), 3d).hashCode()
        );
    }

    @Test
    void toStringOfCircleWithPointAndRadius() {
        Circle<Double> circle = new DoubleCircle(new DoublePoint(2d), 3d);
        Assertions.assertEquals("2.0|2.0 3.0", circle.toString());
    }

    @Test
    void compareToOfCircleWithCenterAndRadius() {
        Circle<Double> circle = new DoubleCircle(new DoublePoint(2d), 3d);
        Assertions.assertEquals(
            0d, circle.compareTo(new DoubleCircle(new DoublePoint(2d), 3d))
        );
        Assertions.assertEquals(
            -1d, circle.compareTo(new DoubleCircle(new DoublePoint(3d), 3d))
        );
        Assertions.assertEquals(
            1d, circle.compareTo(new DoubleCircle(new DoublePoint(2d), 1d))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new DoubleCircle(), Circle.class);
    }

    // endregion
}
