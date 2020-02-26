package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.geometry.TestCircle;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertCircle;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class CircleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertCircle(new TestCircle());
    }

    @Test
    void constructorWithCenterButWithoutRadius() {
        assertCircle(
            new TestCircle(new TestPoint(1)),
            new TestPoint(1)
        );
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
        assertEquals(2d, circle.getDiameter());
        circle.setRadius(2);
        assertEquals(4d, circle.getDiameter());
        circle.setDiameter(2);
        assertEquals(2d, circle.getDiameter());
    }

    @Test
    void createAndSetRadiusAndExpectDiameterToBeDouble() {
        Circle<Number> circle = new TestCircle();
        circle.setRadius(2);
        assertEquals(4d, circle.getDiameter());
    }

    @Test
    void createAndSetDiameterAndExpectRadiusToBeHalf() {
        Circle<Number> circle = new TestCircle();
        circle.setDiameter(2);
        assertEquals(2d, circle.getDiameter());
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfCircleWithoutParameter() {
        assertEquals(
            Math.PI,
            new TestCircle().area().doubleValue(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void areaOfCircleWithRadius2() {
        assertEquals(12.566370614359172, new TestCircle(2).area());
    }

    @Test
    void areaOfCircleWithRadius3() {
        assertEquals(28.274333882308138, new TestCircle(3).area());
    }

    @Test
    void circumferenceOfCircleWithoutParameter() {
        assertEquals(6.283185307179586, new TestCircle().circumference());
    }

    @Test
    void circumferenceOfCircleWithRadius2() {
        assertEquals(12.566370614359172, new TestCircle(2).circumference());
    }

    @Test
    void circumferenceOfCircleWithRadius3() {
        assertEquals(18.84955592153876, new TestCircle(3).circumference());
    }

    // endregion

    // region isUnitCircle

    @Test
    void isUnitCircleWithRadius0() {
        assertFalse(new TestCircle(0).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius1() {
        assertTrue(new TestCircle(1d).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius2() {
        assertFalse(new TestCircle(2).isUnitCircle());
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
        assertEquals(result,
            circle.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZeroValues() {
        assertTrue(new TestCircle().isValid());
    }

    @Test
    void isValidWithNegativeRadius() {
        assertFalse(new TestCircle(-1).isValid());
    }

    @Test
    void isValidWithInfValues() {
        assertFalse(new TestCircle(
            new TestPoint(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY)
            .isValid()
        );
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
        assertEquals(circle, circle.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfCircleWithPointAndRadius() {
        Circle<Number> circle = new TestCircle(new TestPoint(2), 3);
        assertEquals(
            circle,
            new TestCircle(new TestPoint(2), 3)
        );
        assertNotEquals(
            circle,
            new TestCircle(new TestPoint(3), 2)
        );
    }

    @Test
    void hashCodeOfCircleWithPointAndRadius() {
        assertEquals(
            32739,
            new TestCircle(new TestPoint(2), 3).hashCode()
        );
    }

    @Test
    void toStringOfCircleWithPointAndRadius() {
        Circle<Number> circle = new TestCircle(new TestPoint(2d), 3d);
        assertEquals("2.0|2.0 3.0", circle.toString());
    }

    @Test
    void compareToOfCircleWithCenterAndRadius() {
        Circle<Number> circle = new TestCircle(new TestPoint(2), 3);
        assertEquals(
            0, circle.compareTo(new TestCircle(new TestPoint(2), 3))
        );
        assertEquals(
            -1, circle.compareTo(new TestCircle(new TestPoint(3), 3))
        );
        assertEquals(
            1, circle.compareTo(new TestCircle(new TestPoint(2), 1))
        );
    }

    @Test
    void serializable() {
        assertSerializable(new TestCircle(), Circle.class);
    }

    // endregion
}
