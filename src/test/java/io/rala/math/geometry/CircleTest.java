package io.rala.math.geometry;

import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import io.rala.math.testUtils.geometry.TestCircle;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CircleTest {

    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        GeometryAssertions.assertCircle(new TestCircle());
    }

    @Test
    void constructorWithCenterButWithoutRadius() {
        GeometryAssertions.assertCircle(
            new TestCircle(new TestPoint(1)),
            new TestPoint(1)
        );
    }

    @Test
    void constructorWithRadiusButWithoutCenter() {
        GeometryAssertions.assertCircle(new TestCircle(2), 2);
    }

    @Test
    void constructorWithCenterAndPoint() {
        GeometryAssertions.assertCircle(
            new TestCircle(new TestPoint(2), new TestPoint(1)),
            new TestPoint(2),
            Math.sqrt(2)
        );
    }

    @Test
    void constructorWithCenterAndRadius() {
        GeometryAssertions.assertCircle(
            new TestCircle(new TestPoint(2), 3),
            new TestPoint(2), 3
        );
    }

    @Test
    void createAndSetCenter() {
        Circle<Number> circle = new TestCircle();
        GeometryAssertions.assertCircle(circle);
        circle.setCenter(new TestPoint(1));
        GeometryAssertions.assertCircle(circle, new TestPoint(1), 1);
    }

    @Test
    void createAndSetRadius() {
        Circle<Number> circle = new TestCircle();
        GeometryAssertions.assertCircle(circle);
        circle.setRadius(2);
        GeometryAssertions.assertCircle(circle, 2);
    }

    @Test
    void createAndExpectDiameterToBeDouble() {
        Circle<Number> circle = new TestCircle();
        Assertions.assertEquals(2d, circle.getDiameter());
        circle.setRadius(2);
        Assertions.assertEquals(4d, circle.getDiameter());
        circle.setDiameter(2);
        Assertions.assertEquals(2d, circle.getDiameter());
    }

    @Test
    void createAndSetRadiusAndExpectDiameterToBeDouble() {
        Circle<Number> circle = new TestCircle();
        circle.setRadius(2);
        Assertions.assertEquals(4d, circle.getDiameter());
    }

    @Test
    void createAndSetDiameterAndExpectRadiusToBeHalf() {
        Circle<Number> circle = new TestCircle();
        circle.setDiameter(2);
        Assertions.assertEquals(2d, circle.getDiameter());
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfCircleWithoutParameter() {
        Assertions.assertEquals(
            Math.PI,
            new TestCircle().area().doubleValue(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void areaOfCircleWithRadius2() {
        Assertions.assertEquals(12.566370614359172, new TestCircle(2).area());
    }

    @Test
    void areaOfCircleWithRadius3() {
        Assertions.assertEquals(28.274333882308138, new TestCircle(3).area());
    }

    @Test
    void circumferenceOfCircleWithoutParameter() {
        Assertions.assertEquals(6.283185307179586, new TestCircle().circumference());
    }

    @Test
    void circumferenceOfCircleWithRadius2() {
        Assertions.assertEquals(12.566370614359172, new TestCircle(2).circumference());
    }

    @Test
    void circumferenceOfCircleWithRadius3() {
        Assertions.assertEquals(18.84955592153876, new TestCircle(3).circumference());
    }

    // endregion

    // region isUnitCircle

    @Test
    void isUnitCircleWithRadius0() {
        Assertions.assertFalse(new TestCircle(0).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius1() {
        Assertions.assertTrue(new TestCircle(1d).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius2() {
        Assertions.assertFalse(new TestCircle(2).isUnitCircle());
    }

    // endregion

    // region isValid, move, rotate and copy

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new TestCircle().isValid());
    }

    @Test
    void isValidWithNegativeRadius() {
        Assertions.assertFalse(new TestCircle(-1).isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(new TestCircle(
            new TestPoint(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY)
            .isValid()
        );
    }

    @Test
    void moveOfCircleWithoutParameterWithXY() {
        GeometryAssertions.assertCircle(new TestCircle().move(1), new TestPoint(1));
    }

    @Test
    void moveOfCircleWithoutParameterWithXAndY() {
        GeometryAssertions.assertCircle(new TestCircle().move(1, 1), new TestPoint(1));
    }

    @Test
    void moveOfCircleWithoutParameterWithVector() {
        GeometryAssertions.assertCircle(
            new TestCircle().move(new TestVector(1)),
            new TestPoint(1)
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithoutCenterWithPiHalf() {
        GeometryAssertions.assertCircle(new TestCircle(new TestPoint(1, 2))
                .rotate(Math.PI / 2),
            new TestPoint(-2, 1.0000000000000002)
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithCenterXY1WithPiHalf() {
        GeometryAssertions.assertCircle(new TestCircle(new TestPoint(1, 2))
                .rotate(new TestPoint(1), Math.PI / 2),
            new TestPoint(0, 1)
        );
    }

    @Test
    void copyOfCircleWithPointAndRadius() {
        Circle<Number> circle = new TestCircle(new TestPoint(2), 3);
        Assertions.assertEquals(circle, circle.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfCircleWithPointAndRadius() {
        Circle<Number> circle = new TestCircle(new TestPoint(2), 3);
        Assertions.assertEquals(
            circle,
            new TestCircle(new TestPoint(2), 3)
        );
        Assertions.assertNotEquals(
            circle,
            new TestCircle(new TestPoint(3), 2)
        );
    }

    @Test
    void hashCodeOfCircleWithPointAndRadius() {
        Assertions.assertEquals(
            32739,
            new TestCircle(new TestPoint(2), 3).hashCode()
        );
    }

    @Test
    void toStringOfCircleWithPointAndRadius() {
        Circle<Number> circle = new TestCircle(new TestPoint(2d), 3d);
        Assertions.assertEquals("2.0|2.0 3.0", circle.toString());
    }

    @Test
    void compareToOfCircleWithCenterAndRadius() {
        Circle<Number> circle = new TestCircle(new TestPoint(2), 3);
        Assertions.assertEquals(
            0, circle.compareTo(new TestCircle(new TestPoint(2), 3))
        );
        Assertions.assertEquals(
            -1, circle.compareTo(new TestCircle(new TestPoint(3), 3))
        );
        Assertions.assertEquals(
            1, circle.compareTo(new TestCircle(new TestPoint(2), 1))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new TestCircle(), Circle.class);
    }

    // endregion
}
