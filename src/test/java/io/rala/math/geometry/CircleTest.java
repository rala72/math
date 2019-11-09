package io.rala.math.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("SameParameterValue")
class CircleTest {
    // region constructors, getter and setter

    @Test
    void testConstructors() {
        assertCircle(new Circle());
        assertCircle(new Circle(new Point(1)), new Point(1));
        assertCircle(new Circle(2), 2);
        assertCircle(new Circle(new Point(2), 3), new Point(2), 3);
    }

    @Test
    void testGetterAndSetter() {
        Circle circle = new Circle();
        assertCircle(circle);
        circle.setCenter(new Point(1));
        assertCircle(circle, new Point(1), 1);
        circle.setRadius(2);
        assertCircle(circle, new Point(1), 2);
    }

    @Test
    void testDiameter() {
        Circle circle = new Circle();
        Assertions.assertEquals(2, circle.getDiameter());
        circle.setRadius(2);
        Assertions.assertEquals(4, circle.getDiameter());
        circle.setDiameter(2);
        Assertions.assertEquals(2, circle.getDiameter());
    }

    // endregion

    // region copy

    @Test
    void testCopy() {
        Circle circle = new Circle(new Point(2), 3);
        Assertions.assertEquals(circle, circle.copy());
    }

    // endregion

    // region override

    @Test
    void testEquals() {
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
    void testHashCode() {
        Assertions.assertEquals(
            1074296864,
            new Circle(new Point(2), 3).hashCode()
        );
    }

    @Test
    void testToString() {
        Circle circle = new Circle(new Point(2), 3);
        Assertions.assertEquals("2.0:2.0 3.0", circle.toString());
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
        Assertions.assertEquals(center, circle.getCenter());
        Assertions.assertEquals(radius, circle.getRadius());
    }

    // endregion
}
