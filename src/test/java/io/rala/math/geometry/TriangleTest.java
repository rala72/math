package io.rala.math.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TriangleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithA2B3C4() {
        assertTriangle(
            new Triangle(new Point(2), new Point(3), new Point(4)),
            new Point(2), new Point(3), new Point(4)
        );
    }

    @Test
    void createAndSetA() {
        Triangle triangle = new Triangle(new Point(), new Point(), new Point());
        assertTriangle(triangle, new Point(), new Point(), new Point());
        triangle.setA(new Point(1));
        assertTriangle(triangle, new Point(1), new Point(), new Point());
    }

    @Test
    void createAndSetB() {
        Triangle triangle = new Triangle(new Point(), new Point(), new Point());
        assertTriangle(triangle, new Point(), new Point(), new Point());
        triangle.setB(new Point(2));
        assertTriangle(triangle, new Point(), new Point(2), new Point());
    }

    @Test
    void createAndSetC() {
        Triangle triangle = new Triangle(new Point(), new Point(), new Point());
        assertTriangle(triangle, new Point(), new Point(), new Point());
        triangle.setC(new Point(3));
        assertTriangle(triangle, new Point(), new Point(), new Point(3));
    }

    // endregion

    // region vertexes

    @Test
    void centerOfGravityOfTriangleWithA00B01C11() {
        Assertions.assertEquals(new Point(0.3333333333333333, 0.6666666666666666),
            new Triangle(new Point(), new Point(0, 1), new Point(1, 1)).centerOfGravity()
        );
    }

    // endregion

    // region edges

    @Test
    void edgesOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(new Point(), new Point(1), new Point(2));
        Assertions.assertEquals(
            new LineSegment(new Point(1), new Point(2)),
            triangle.edgeA()
        );
        Assertions.assertEquals(
            new LineSegment(new Point(), new Point(2)),
            triangle.edgeB()
        );
        Assertions.assertEquals(
            new LineSegment(new Point(), new Point(1)),
            triangle.edgeC()
        );
    }

    // endregion

    // region angles

    @Test
    void angleAlpha() {
        Triangle triangle = new Triangle(
            new Point(0, 0), new Point(0, 1), new Point(1, 1)
        );
        Assertions.assertEquals(Math.PI / 4, triangle.angleAlpha(), 0.00001);
    }

    @Test
    void angleBeta() {
        Triangle triangle = new Triangle(
            new Point(0, 0), new Point(0, 1), new Point(1, 1)
        );
        Assertions.assertEquals(Math.PI / 2, triangle.angleBeta(), 0.00001);
    }

    @Test
    void angleGamma() {
        Triangle triangle = new Triangle(
            new Point(0, 0), new Point(0, 1), new Point(1, 1)
        );
        Assertions.assertEquals(Math.PI / 4, triangle.angleGamma(), 0.00001);
    }

    // endregion

    // region area, circumference, circumRadius, inRadius

    @Test
    void areaOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(new Point(), new Point(0, 1), new Point(1, 1));
        Assertions.assertEquals(0.49999999999999983, triangle.area());
    }

    @Test
    void circumferenceOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(new Point(), new Point(0, 1), new Point(1, 1));
        Assertions.assertEquals(3.414213562373095, triangle.circumference());
    }

    @Test
    void circumRadiusOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(new Point(), new Point(0, 1), new Point(1, 1));
        Assertions.assertEquals(0.7071067811865478, triangle.circumRadius());
    }

    @Test
    void inRadiusOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(new Point(), new Point(0, 1), new Point(1, 1));
        Assertions.assertEquals(0.29289321881345237, triangle.inRadius());
    }

    // endregion

    // region isValid, move, rotate and copy

    @Test
    void isValidWithPositiveValues() {
        Assertions.assertTrue(
            new Triangle(
                new Point(1), new Point(1, 2), new Point(2)
            ).isValid()
        );
    }

    @Test
    void isValidWithLineValues() {
        Assertions.assertFalse(
            new Triangle(
                new Point(0), new Point(1), new Point(2)
            ).isValid()
        );
    }

    @Test
    void isValidWithZeroValues() {
        Assertions.assertFalse(
            new Triangle(
                new Point(0), new Point(0), new Point(0)
            ).isValid()
        );
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new Triangle(
                new Point(Double.POSITIVE_INFINITY),
                new Point(Double.POSITIVE_INFINITY),
                new Point(Double.POSITIVE_INFINITY)
            ).isValid()
        );
    }

    @Test
    void moveOfTriangleWithXYWithXY() {
        Assertions.assertEquals(
            new Triangle(new Point(1), new Point(2, 1), new Point(2)),
            new Triangle(new Point(0), new Point(1, 0), new Point(1))
                .move(1)
        );
    }

    @Test
    void moveOfTriangleWithXYWithXAndY() {
        Assertions.assertEquals(
            new Triangle(new Point(1), new Point(2, 1), new Point(2)),
            new Triangle(new Point(0), new Point(1, 0), new Point(1))
                .move(1, 1)
        );
    }

    @Test
    void moveOfTriangleWithXYWithVector() {
        Assertions.assertEquals(
            new Triangle(new Point(1), new Point(2, 1), new Point(2)),
            new Triangle(new Point(0), new Point(1, 0), new Point(1))
                .move(new Vector(1))
        );
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithoutCenterWithPiHalf() {
        assertTriangle(
            new Triangle(new Point(0, 0), new Point(0, 1), new Point(1, 1))
                .rotate(Math.PI / 2),
            new Point(),
            new Point(-1, 6.123233995736766E-17),
            new Point(-0.9999999999999999, 1.0)
        );
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithCenterXY1WithPiHalf() {
        assertTriangle(
            new Triangle(new Point(0, 0), new Point(0, 1), new Point(1, 1))
                .rotate(new Point(1), Math.PI / 2),
            new Point(2, 0),
            new Point(0.9999999999999999, 0),
            new Point(1, 1)
        );
    }

    @Test
    void copyOfTriangleWithA2B3C4() {
        Triangle triangle = new Triangle(new Point(1, 2), new Point(3, 4), new Point(5, 6));
        Assertions.assertEquals(triangle, triangle.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfTriangleWithA2B3C4() {
        Triangle triangle = new Triangle(new Point(2), new Point(3), new Point(4));
        Assertions.assertEquals(
            triangle,
            new Triangle(new Point(2), new Point(3), new Point(4))
        );
        Assertions.assertNotEquals(
            triangle,
            new Triangle(new Point(3), new Point(2), new Point(4))
        );
    }

    @Test
    void hashCodeOfTriangleWithA2B3C4() {
        Assertions.assertEquals(
            554632192,
            new Triangle(new Point(2), new Point(3), new Point(4)).hashCode()
        );
    }

    @Test
    void toStringOfTriangleWithA2B3C4() {
        Triangle triangle = new Triangle(new Point(2), new Point(3), new Point(4));
        Assertions.assertEquals("2.0|2.0 3.0|3.0 4.0|4.0", triangle.toString());
    }

    @Test
    void compareToOfTriangleWithA2B3C4() {
        Triangle triangle = new Triangle(new Point(0), new Point(1, 0), new Point(1));
        Assertions.assertEquals(
            0, triangle.compareTo(new Triangle(
                new Point(0), new Point(1, 0), new Point(1)
            ))
        );
        Assertions.assertEquals(
            -1, triangle.compareTo(new Triangle(
                new Point(-1), new Point(1, 0), new Point(1)
            ))
        );
        Assertions.assertEquals(
            1, triangle.compareTo(new Triangle(
                new Point(0.5, 1), new Point(1, 0.5), new Point(1)
            ))
        );
    }

    // endregion


    // region assert

    private static void assertTriangle(Triangle triangle, Point a, Point b, Point c) {
        Assertions.assertEquals(a, triangle.getA(), "a is invalid");
        Assertions.assertEquals(b, triangle.getB(), "b is invalid");
        Assertions.assertEquals(c, triangle.getC(), "c is invalid");
    }

    // endregion
}
