package io.rala.math.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("SameParameterValue")
class CircleTest {
    private static final double DELTA = 0.00001;

    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertCircle(new Circle());
    }

    @Test
    void constructorWithCenterButWithoutRadius() {
        assertCircle(new Circle(new Point(1)), new Point(1));
    }

    @Test
    void constructorWithRadiusButWithoutCenter() {
        assertCircle(new Circle(2), 2);
    }

    @Test
    void constructorWithCenterAndRadius() {
        assertCircle(new Circle(new Point(2), 3), new Point(2), 3);
    }

    @Test
    void createAndSetCenter() {
        Circle circle = new Circle();
        assertCircle(circle);
        circle.setCenter(new Point(1));
        assertCircle(circle, new Point(1), 1);
    }

    @Test
    void createAndSetRadius() {
        Circle circle = new Circle();
        assertCircle(circle);
        circle.setRadius(2);
        assertCircle(circle, 2);
    }

    @Test
    void createAndExpectDiameterToBeDouble() {
        Circle circle = new Circle();
        Assertions.assertEquals(2, circle.getDiameter());
        circle.setRadius(2);
        Assertions.assertEquals(4, circle.getDiameter());
        circle.setDiameter(2);
        Assertions.assertEquals(2, circle.getDiameter());
    }

    @Test
    void createAndSetRadiusAndExpectDiameterToBeDouble() {
        Circle circle = new Circle();
        circle.setRadius(2);
        Assertions.assertEquals(4, circle.getDiameter());
    }

    @Test
    void createAndSetDiameterAndExpectRadiusToBeHalf() {
        Circle circle = new Circle();
        circle.setDiameter(2);
        Assertions.assertEquals(2, circle.getDiameter());
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfCircleWithoutParameter() {
        Assertions.assertEquals(Math.PI, new Circle().area(), DELTA);
    }

    @Test
    void areaOfCircleWithRadius2() {
        Assertions.assertEquals(12.566370614359172, new Circle(2).area());
    }

    @Test
    void areaOfCircleWithRadius3() {
        Assertions.assertEquals(28.274333882308138, new Circle(3).area());
    }

    @Test
    void circumferenceOfCircleWithoutParameter() {
        Assertions.assertEquals(6.283185307179586, new Circle().circumference());
    }

    @Test
    void circumferenceOfCircleWithRadius2() {
        Assertions.assertEquals(12.566370614359172, new Circle(2).circumference());
    }

    @Test
    void circumferenceOfCircleWithRadius3() {
        Assertions.assertEquals(18.84955592153876, new Circle(3).circumference());
    }

    // endregion

    // region isUnitCircle

    @Test
    void isUnitCircleWithRadius0() {
        Assertions.assertFalse(new Circle(0).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius1() {
        Assertions.assertTrue(new Circle(1).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius2() {
        Assertions.assertFalse(new Circle(2).isUnitCircle());
    }

    // endregion

    // region isValid, move, rotate and copy

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new Circle().isValid());
    }

    @Test
    void isValidWithNegativeRadius() {
        Assertions.assertFalse(new Circle(-1).isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new Circle(new Point(Double.POSITIVE_INFINITY), Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void moveOfCircleWithoutParameterWithXY() {
        Assertions.assertEquals(new Circle(new Point(1)), new Circle().move(1));
    }

    @Test
    void moveOfCircleWithoutParameterWithXAndY() {
        Assertions.assertEquals(new Circle(new Point(1)), new Circle().move(1, 1));
    }

    @Test
    void moveOfCircleWithoutParameterWithVector() {
        Assertions.assertEquals(new Circle(new Point(1)), new Circle().move(new Vector(1)));
    }

    @Test
    void rotateOfCircleWithX1Y2WithoutCenterWithPiHalf() {
        assertCircle(new Circle(new Point(1, 2))
                .rotate(Math.PI / 2),
            new Point(-2, 1.0000000000000002)
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithCenterXY1WithPiHalf() {
        assertCircle(new Circle(new Point(1, 2))
                .rotate(new Point(1), Math.PI / 2),
            new Point(0, 1)
        );
    }

    @Test
    void copyOfCircleWithPointAndRadius() {
        Circle circle = new Circle(new Point(2), 3);
        Assertions.assertEquals(circle, circle.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfCircleWithPointAndRadius() {
        Circle circle = new Circle(new Point(2), 3);
        Assertions.assertEquals(
            circle,
            new Circle(new Point(2), 3)
        );
        Assertions.assertNotEquals(
            circle,
            new Circle(new Point(3), 2)
        );
    }

    @Test
    void hashCodeOfCircleWithPointAndRadius() {
        Assertions.assertEquals(
            1074296864,
            new Circle(new Point(2), 3).hashCode()
        );
    }

    @Test
    void toStringOfCircleWithPointAndRadius() {
        Circle circle = new Circle(new Point(2), 3);
        Assertions.assertEquals("2.0|2.0 3.0", circle.toString());
    }

    @Test
    void compareToOfCircleWithCenterAndRadius() {
        Circle circle = new Circle(new Point(2), 3);
        Assertions.assertEquals(
            0, circle.compareTo(new Circle(new Point(2), 3))
        );
        Assertions.assertEquals(
            -1, circle.compareTo(new Circle(new Point(3), 3))
        );
        Assertions.assertEquals(
            1, circle.compareTo(new Circle(new Point(2), 1))
        );
    }

    // endregion


    // region assert

    private static void assertCircle(Circle circle) {
        assertCircle(circle, new Point());
    }

    private static void assertCircle(Circle circle, Point center) {
        assertCircle(circle, center, 1);
    }

    private static void assertCircle(Circle circle, double radius) {
        assertCircle(circle, new Point(), radius);
    }

    private static void assertCircle(Circle circle, Point center, double radius) {
        Assertions.assertEquals(center, circle.getCenter(), "center is invalid");
        Assertions.assertEquals(radius, circle.getRadius(), "radius is invalid");
    }

    // endregion
}
