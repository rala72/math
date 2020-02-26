package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertCircle;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(2d, circle.getDiameter());
        circle.setRadius(2d);
        assertEquals(4d, circle.getDiameter());
        circle.setDiameter(2d);
        assertEquals(2d, circle.getDiameter());
    }

    @Test
    void createAndSetRadiusAndExpectDiameterToBeDouble() {
        Circle<Double> circle = new DoubleCircle();
        circle.setRadius(2d);
        assertEquals(4d, circle.getDiameter());
    }

    @Test
    void createAndSetDiameterAndExpectRadiusToBeHalf() {
        Circle<Double> circle = new DoubleCircle();
        circle.setDiameter(2d);
        assertEquals(2d, circle.getDiameter());
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfCircleWithoutParameter() {
        assertEquals(
            Math.PI,
            new DoubleCircle().area(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void areaOfCircleWithRadius2() {
        assertEquals(12.566370614359172d, new DoubleCircle(2d).area());
    }

    @Test
    void areaOfCircleWithRadius3() {
        assertEquals(28.274333882308138d, new DoubleCircle(3d).area());
    }

    @Test
    void circumferenceOfCircleWithoutParameter() {
        assertEquals(6.283185307179586d, new DoubleCircle().circumference());
    }

    @Test
    void circumferenceOfCircleWithRadius2() {
        assertEquals(12.566370614359172d,
            new DoubleCircle(2d).circumference()
        );
    }

    @Test
    void circumferenceOfCircleWithRadius3() {
        assertEquals(18.84955592153876d,
            new DoubleCircle(3d).circumference()
        );
    }

    // endregion

    // region isUnitCircle

    @Test
    void isUnitCircleWithRadius0() {
        assertFalse(new DoubleCircle(0d).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius1() {
        assertTrue(new DoubleCircle(1d).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius2() {
        assertFalse(new DoubleCircle(2d).isUnitCircle());
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
        assertEquals(result,
            circle.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZeroValues() {
        assertTrue(new DoubleCircle().isValid());
    }

    @Test
    void isValidWithNegativeRadius() {
        assertFalse(new DoubleCircle(-1d).isValid());
    }

    @Test
    void isValidWithInfValues() {
        assertFalse(new DoubleCircle(
            new DoublePoint(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY)
            .isValid()
        );
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
            new DoublePoint(-2d, 1.0000000000000002d)
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
        assertEquals(circle, circle.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfCircleWithPointAndRadius() {
        Circle<Double> circle = new DoubleCircle(new DoublePoint(2d), 3d);
        assertEquals(circle,
            new DoubleCircle(new DoublePoint(2d), 3d)
        );
        assertNotEquals(circle,
            new DoubleCircle(new DoublePoint(3d), 2d)
        );
    }

    @Test
    void hashCodeOfCircleWithPointAndRadius() {
        assertEquals(1074296864,
            new DoubleCircle(new DoublePoint(2d), 3d).hashCode()
        );
    }

    @Test
    void toStringOfCircleWithPointAndRadius() {
        Circle<Double> circle = new DoubleCircle(new DoublePoint(2d), 3d);
        assertEquals("2.0|2.0 3.0", circle.toString());
    }

    @Test
    void compareToOfCircleWithCenterAndRadius() {
        Circle<Double> circle = new DoubleCircle(new DoublePoint(2d), 3d);
        assertEquals(0d,
            circle.compareTo(new DoubleCircle(new DoublePoint(2d), 3d))
        );
        assertEquals(-1d,
            circle.compareTo(new DoubleCircle(new DoublePoint(3d), 3d))
        );
        assertEquals(1d,
            circle.compareTo(new DoubleCircle(new DoublePoint(2d), 1d))
        );
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleCircle(), DoubleCircle.class);
    }

    // endregion
}
