package io.rala.math.geometry;

import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TriangleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithA2B3C4() {
        GeometryAssertions.assertTriangle(
            new Triangle(new Point(2), new Point(3), new Point(4)),
            new Point(2), new Point(3), new Point(4)
        );
    }

    @Test
    void createAndSetA() {
        Triangle triangle = new Triangle(new Point(), new Point(), new Point());
        GeometryAssertions.assertTriangle(
            triangle,
            new Point(), new Point(), new Point()
        );
        triangle.setA(new Point(1));
        GeometryAssertions.assertTriangle(
            triangle,
            new Point(1), new Point(), new Point()
        );
    }

    @Test
    void createAndSetB() {
        Triangle triangle = new Triangle(new Point(), new Point(), new Point());
        GeometryAssertions.assertTriangle(
            triangle,
            new Point(), new Point(), new Point()
        );
        triangle.setB(new Point(2));
        GeometryAssertions.assertTriangle(
            triangle,
            new Point(), new Point(2), new Point()
        );
    }

    @Test
    void createAndSetC() {
        Triangle triangle = new Triangle(new Point(), new Point(), new Point());
        GeometryAssertions.assertTriangle(
            triangle,
            new Point(), new Point(), new Point()
        );
        triangle.setC(new Point(3));
        GeometryAssertions.assertTriangle(
            triangle,
            new Point(), new Point(), new Point(3)
        );
    }

    // endregion

    // region vertexes

    @Test
    void centerOfGravityOfTriangleWithA00B01C11() {
        GeometryAssertions.assertPoint(
            new Triangle(new Point(), new Point(0, 1), new Point(1, 1))
                .centerOfGravity(),
            1d / 3, 2d / 3
        );
    }

    // endregion

    // region edges and altitudes

    @Test
    void edgesOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(new Point(), new Point(1), new Point(2));
        GeometryAssertions.assertLineSegment(
            triangle.edgeA(),
            new Point(1), new Point(2)
        );
        GeometryAssertions.assertLineSegment(
            triangle.edgeB(),
            new Point(), new Point(2)
        );
        GeometryAssertions.assertLineSegment(
            triangle.edgeC(),
            new Point(), new Point(1)
        );
    }

    @Test
    void altitudesOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(
            new Point(),
            new Point(0, 1),
            new Point(1, 1)
        );

        LineSegment altitudeA = triangle.altitudeA();
        Assertions.assertEquals(1, altitudeA.length());
        Assertions.assertEquals(triangle.getA(), altitudeA.getA());
        Assertions.assertEquals(triangle.getB(), altitudeA.getB());

        LineSegment altitudeB = triangle.altitudeB();
        Assertions.assertEquals(Math.sqrt(2) / 2, altitudeB.length());
        Assertions.assertEquals(triangle.getB(), altitudeB.getA());
        Assertions.assertEquals(triangle.edgeB().halvingPoint(), altitudeB.getB());

        LineSegment altitudeC = triangle.altitudeC();
        Assertions.assertEquals(1, altitudeC.length());
        Assertions.assertEquals(triangle.getC(), altitudeC.getA());
        Assertions.assertEquals(triangle.getB(), altitudeC.getB());
    }

    @Test
    void altitudesOfTriangleWithA00B10C11() {
        Triangle triangle = new Triangle(
            new Point(),
            new Point(1, 0),
            new Point(1, 1)
        );

        LineSegment altitudeA = triangle.altitudeA();
        Assertions.assertEquals(1, altitudeA.length());
        Assertions.assertEquals(triangle.getA(), altitudeA.getA());
        Assertions.assertEquals(triangle.getB(), altitudeA.getB());

        LineSegment altitudeB = triangle.altitudeB();
        Assertions.assertEquals(Math.sqrt(2) / 2, altitudeB.length());
        Assertions.assertEquals(triangle.getB(), altitudeB.getA());
        Assertions.assertEquals(triangle.edgeB().halvingPoint(), altitudeB.getB());

        LineSegment altitudeC = triangle.altitudeC();
        Assertions.assertEquals(1, altitudeC.length());
        Assertions.assertEquals(triangle.getC(), altitudeC.getA());
        Assertions.assertEquals(triangle.getB(), altitudeC.getB());
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
        Assertions.assertEquals(
            Math.PI / 2,
            triangle.angleBeta(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void angleGamma() {
        Triangle triangle = new Triangle(
            new Point(0, 0), new Point(0, 1), new Point(1, 1)
        );
        Assertions.assertEquals(
            Math.PI / 4,
            triangle.angleGamma(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void angleSum() {
        Triangle triangle = new Triangle(
            new Point(0, 0), new Point(0, 1), new Point(1, 1)
        );
        Assertions.assertEquals(
            Math.PI,
            triangle.angleAlpha() + triangle.angleBeta() + triangle.angleGamma()
        );
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(
            new Point(), new Point(0, 1), new Point(1, 1)
        );
        Assertions.assertEquals(0.5, triangle.area(), GeometryAssertions.DELTA);
    }

    @Test
    void circumferenceOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(
            new Point(), new Point(0, 1), new Point(1, 1)
        );
        Assertions.assertEquals(2 + Math.sqrt(2), triangle.circumference());
    }

    // endregion

    // region centroid and orthoCenter

    @Test
    void centroidOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(
            new Point(), new Point(0, 1), new Point(1, 1)
        );
        GeometryAssertions.assertPoint(triangle.centroid(), 1d / 3, 2d / 3);
    }

    @Test
    void orthoCenterOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(
            new Point(), new Point(0, 1), new Point(1, 1)
        );
        Assertions.assertEquals(triangle.getB(), triangle.orthoCenter());
    }

    // endregion

    // region circumCircle and inCircle

    @Test
    void circumCircleOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(
            new Point(), new Point(0, 1), new Point(1, 1)
        );
        GeometryAssertions.assertCircle(
            triangle.circumCircle(),
            new Point(0.5, -0.5),
            Math.sqrt(2) / 2
        );
    }

    @Test
    void inCircleOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(
            new Point(), new Point(0, 1), new Point(1, 1)
        );
        GeometryAssertions.assertCircle(
            triangle.inCircle(),
            new Point(1 - Math.sqrt(2) / 2, Math.sqrt(2) / 2),
            1 - Math.sqrt(2) / 2
        );
    }

    @Test
    void circumCircleRadiusOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(
            new Point(), new Point(0, 1), new Point(1, 1)
        );
        Assertions.assertEquals(
            Math.sqrt(2) / 2,
            triangle.circumCircleRadius(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void circumCirclePointOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(
            new Point(), new Point(0, 1), new Point(1, 1)
        );
        Assertions.assertEquals(new Point(0.5, -0.5), triangle.circumCirclePoint());
    }

    @Test
    void inCircleRadiusOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(
            new Point(), new Point(0, 1), new Point(1, 1)
        );
        Assertions.assertEquals(
            1 - Math.sqrt(2) / 2,
            triangle.inCircleRadius(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void inCirclePointOfTriangleWithA00B01C11() {
        Triangle triangle = new Triangle(
            new Point(), new Point(0, 1), new Point(1, 1)
        );
        GeometryAssertions.assertPoint(
            triangle.inCirclePoint(),
            1 - Math.sqrt(2) / 2,
            Math.sqrt(2) / 2
        );
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
        GeometryAssertions.assertTriangle(
            new Triangle(new Point(0), new Point(1, 0), new Point(1))
                .move(1),
            new Point(1), new Point(2, 1), new Point(2)
        );
    }

    @Test
    void moveOfTriangleWithXYWithXAndY() {
        GeometryAssertions.assertTriangle(
            new Triangle(new Point(0), new Point(1, 0), new Point(1))
                .move(1, 1),
            new Point(1), new Point(2, 1), new Point(2)
        );
    }

    @Test
    void moveOfTriangleWithXYWithVector() {
        GeometryAssertions.assertTriangle(
            new Triangle(new Point(0), new Point(1, 0), new Point(1))
                .move(new Vector(1)),
            new Point(1), new Point(2, 1), new Point(2)
        );
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithoutCenterWithPiHalf() {
        GeometryAssertions.assertTriangle(
            new Triangle(new Point(0, 0), new Point(0, 1), new Point(1, 1))
                .rotate(Math.PI / 2),
            new Point(),
            new Point(-1, 6.123233995736766E-17),
            new Point(-0.9999999999999999, 1.0)
        );
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithCenterXY1WithPiHalf() {
        GeometryAssertions.assertTriangle(
            new Triangle(new Point(0, 0), new Point(0, 1), new Point(1, 1))
                .rotate(new Point(1), Math.PI / 2),
            new Point(2, 0),
            new Point(0.9999999999999999, 0),
            new Point(1, 1)
        );
    }

    @Test
    void copyOfTriangleWithA2B3C4() {
        Triangle triangle = new Triangle(
            new Point(1, 2), new Point(3, 4), new Point(5, 6)
        );
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
        Triangle triangle = new Triangle(
            new Point(0), new Point(1, 0), new Point(1)
        );
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

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
            new Triangle(new Point(), new Point(), new Point()),
            Triangle.class
        );
    }

    // endregion
}
