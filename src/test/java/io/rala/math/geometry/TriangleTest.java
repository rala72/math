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

    @Test
    void testHeight() {
        Triangle triangle = new Triangle(new Point(), new Point(0, 1), new Point(1, 1));
        LineSegment heightA = triangle.getHeightA();
        Assertions.assertEquals(1.0000000000000002, heightA.length());
        Assertions.assertEquals(triangle.getA(), heightA.getA());
        Assertions.assertEquals(new Point(2.5809568279517837e-8, 1), heightA.getB()); // point b
        LineSegment heightB = triangle.getHeightB();
        Assertions.assertEquals(0.7071067811865478, heightB.length());
        Assertions.assertEquals(triangle.getB(), heightB.getA());
        Assertions.assertEquals(new Point(0.5000000000000003, 0.5), heightB.getB());
        LineSegment heightC = triangle.getHeightC();
        Assertions.assertEquals(1.0000000000000002, heightC.length());
        Assertions.assertEquals(triangle.getC(), heightC.getA());
        Assertions.assertEquals(new Point(0, 1.0000000258095683), heightC.getB()); // pointb
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

    // region move and copy

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
        Assertions.assertEquals("2.0:2.0 3.0:3.0 4.0:4.0", triangle.toString());
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
        Assertions.assertEquals(a, triangle.getA());
        Assertions.assertEquals(b, triangle.getB());
        Assertions.assertEquals(c, triangle.getC());
    }

    // endregion
}
