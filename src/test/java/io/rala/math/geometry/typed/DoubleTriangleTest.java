package io.rala.math.geometry.typed;

import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Triangle;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DoubleTriangleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithA2dB3dC4d() {
        GeometryAssertions.assertTriangle(
            new DoubleTriangle(new DoublePoint(2d), new DoublePoint(3d), new DoublePoint(4d)),
            new DoublePoint(2d), new DoublePoint(3d), new DoublePoint(4d)
        );
    }

    @Test
    void createAndSetA() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        GeometryAssertions.assertTriangle(
            triangle,
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        triangle.setA(new DoublePoint(1d));
        GeometryAssertions.assertTriangle(
            triangle,
            new DoublePoint(1d), new DoublePoint(), new DoublePoint()
        );
    }

    @Test
    void createAndSetB() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        GeometryAssertions.assertTriangle(
            triangle,
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        triangle.setB(new DoublePoint(2d));
        GeometryAssertions.assertTriangle(
            triangle,
            new DoublePoint(), new DoublePoint(2d), new DoublePoint()
        );
    }

    @Test
    void createAndSetC() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        GeometryAssertions.assertTriangle(
            triangle,
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        triangle.setC(new DoublePoint(3d));
        GeometryAssertions.assertTriangle(
            triangle,
            new DoublePoint(), new DoublePoint(), new DoublePoint(3d)
        );
    }

    // endregion

    // region edges and altitudes

    @Test
    void edgesOfTriangleWithA00dB01dC11d() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(1d), new DoublePoint(2d)
        );
        GeometryAssertions.assertLineSegment(
            triangle.edgeA(),
            new DoublePoint(1d), new DoublePoint(2d)
        );
        GeometryAssertions.assertLineSegment(
            triangle.edgeB(),
            new DoublePoint(), new DoublePoint(2d)
        );
        GeometryAssertions.assertLineSegment(
            triangle.edgeC(),
            new DoublePoint(), new DoublePoint(1d)
        );
    }

    @Test
    void altitudesOfTriangleWithA00dB01dC11d() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(),
            new DoublePoint(0d, 1d),
            new DoublePoint(1d, 1d)
        );

        LineSegment<Double> altitudeA = triangle.altitudeA();
        Assertions.assertEquals(1d, altitudeA.length());
        Assertions.assertEquals(triangle.getA(), altitudeA.getA());
        Assertions.assertEquals(triangle.getB(), altitudeA.getB());

        LineSegment<Double> altitudeB = triangle.altitudeB();
        Assertions.assertEquals(Math.sqrt(2d) / 2d, altitudeB.length());
        Assertions.assertEquals(triangle.getB(), altitudeB.getA());
        Assertions.assertEquals(triangle.edgeB().halvingPoint(), altitudeB.getB());

        LineSegment<Double> altitudeC = triangle.altitudeC();
        Assertions.assertEquals(1d, altitudeC.length());
        Assertions.assertEquals(triangle.getC(), altitudeC.getA());
        Assertions.assertEquals(triangle.getB(), altitudeC.getB());
    }

    @Test
    void altitudesOfTriangleWithA00dB10dC11d() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(),
            new DoublePoint(1d, 0d),
            new DoublePoint(1d, 1d)
        );

        LineSegment<Double> altitudeA = triangle.altitudeA();
        Assertions.assertEquals(1d, altitudeA.length());
        Assertions.assertEquals(triangle.getA(), altitudeA.getA());
        Assertions.assertEquals(triangle.getB(), altitudeA.getB());

        LineSegment<Double> altitudeB = triangle.altitudeB();
        Assertions.assertEquals(Math.sqrt(2d) / 2d, altitudeB.length());
        Assertions.assertEquals(triangle.getB(), altitudeB.getA());
        Assertions.assertEquals(triangle.edgeB().halvingPoint(), altitudeB.getB());

        LineSegment<Double> altitudeC = triangle.altitudeC();
        Assertions.assertEquals(1d, altitudeC.length());
        Assertions.assertEquals(triangle.getC(), altitudeC.getA());
        Assertions.assertEquals(triangle.getB(), altitudeC.getB());
    }

    // endregion

    // region angles

    @Test
    void angleAlpha() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d, 0d), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        Assertions.assertEquals(
            Math.PI / 4d,
            triangle.angleAlpha(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void angleBeta() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d, 0d), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        Assertions.assertEquals(
            Math.PI / 2d,
            triangle.angleBeta(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void angleGamma() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d, 0d), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        Assertions.assertEquals(
            Math.PI / 4d,
            triangle.angleGamma(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void angleSum() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d, 0d), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        Assertions.assertEquals(
            Math.PI,
            triangle.angleAlpha() +
                triangle.angleBeta() +
                triangle.angleGamma()
        );
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfTriangleWithA00dB01dC11d() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        Assertions.assertEquals(
            0.5d,
            triangle.area(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void circumferenceOfTriangleWithA00dB01dC11d() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        Assertions.assertEquals(2d + Math.sqrt(2d), triangle.circumference());
    }

    // endregion

    // region centroid and orthoCenter

    @Test
    void centroidOfTriangleWithA00dB01dC11d() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        GeometryAssertions.assertPoint(triangle.centroid(), 1d / 3d, 2d / 3d);
    }

    @Test
    void orthoCenterOfTriangleWithA00dB01dC11d() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        Assertions.assertEquals(triangle.getB(), triangle.orthoCenter());
    }

    // endregion

    // region circumCircle and inCircle

    @Test
    void circumCircleOfTriangleWithA00dB01dC11d() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        GeometryAssertions.assertCircle(
            triangle.circumCircle(),
            new DoublePoint(0.5d, -0.5d),
            Math.sqrt(2d) / 2d
        );
    }

    @Test
    void inCircleOfTriangleWithA00dB01dC11d() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        GeometryAssertions.assertCircle(
            triangle.inCircle(),
            new DoublePoint(1d - Math.sqrt(2d) / 2d, Math.sqrt(2d) / 2d),
            1d - Math.sqrt(2d) / 2d
        );
    }

    // endregion

    // region isValid, move, rotate and copy

    @Test
    void isValidWithPositiveValues() {
        Assertions.assertTrue(
            new DoubleTriangle(
                new DoublePoint(1d), new DoublePoint(1d, 2d), new DoublePoint(2d)
            ).isValid()
        );
    }

    @Test
    void isValidWithLineValues() {
        Assertions.assertFalse(
            new DoubleTriangle(
                new DoublePoint(0d), new DoublePoint(1d), new DoublePoint(2d)
            ).isValid()
        );
    }

    @Test
    void isValidWithZeroValues() {
        Assertions.assertFalse(
            new DoubleTriangle(
                new DoublePoint(0d), new DoublePoint(0d), new DoublePoint(0d)
            ).isValid()
        );
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new DoubleTriangle(
                new DoublePoint(Double.POSITIVE_INFINITY),
                new DoublePoint(Double.POSITIVE_INFINITY),
                new DoublePoint(Double.POSITIVE_INFINITY)
            ).isValid()
        );
    }

    @Test
    void moveOfTriangleWithXYWithXY() {
        GeometryAssertions.assertTriangle(
            new DoubleTriangle(new DoublePoint(0d), new DoublePoint(1d, 0d), new DoublePoint(1d))
                .move(1d),
            new DoublePoint(1d), new DoublePoint(2d, 1d), new DoublePoint(2d)
        );
    }

    @Test
    void moveOfTriangleWithXYWithXAndY() {
        GeometryAssertions.assertTriangle(
            new DoubleTriangle(new DoublePoint(0d), new DoublePoint(1d, 0d), new DoublePoint(1d))
                .move(1d, 1d),
            new DoublePoint(1d), new DoublePoint(2d, 1d), new DoublePoint(2d)
        );
    }

    @Test
    void moveOfTriangleWithXYWithVector() {
        GeometryAssertions.assertTriangle(
            new DoubleTriangle(new DoublePoint(0d), new DoublePoint(1d, 0d), new DoublePoint(1d))
                .move(new DoubleVector(1d)),
            new DoublePoint(1d), new DoublePoint(2d, 1d), new DoublePoint(2d)
        );
    }

    @Test
    void rotateOfTriangleWithA00dB01dC11dWithoutCenterWithPiHalf() {
        GeometryAssertions.assertTriangle(
            new DoubleTriangle(
                new DoublePoint(0d, 0d),
                new DoublePoint(0d, 1d),
                new DoublePoint(1d, 1d)
            ).rotate(Math.PI / 2d),
            new DoublePoint(),
            new DoublePoint(-1d, 6.123233995736766e-17d),
            new DoublePoint(-0.9999999999999999d, 1.0d)
        );
    }

    @Test
    void rotateOfTriangleWithA00dB01dC11dWithCenterXY1dWithPiHalf() {
        GeometryAssertions.assertTriangle(
            new DoubleTriangle(
                new DoublePoint(0d, 0d),
                new DoublePoint(0d, 1d),
                new DoublePoint(1d, 1d)
            ).rotate(new DoublePoint(1d), Math.PI / 2d),
            new DoublePoint(2d, 0d),
            new DoublePoint(0.9999999999999999d, 0d),
            new DoublePoint(1d, 1d)
        );
    }

    @Test
    void copyOfTriangleWithA2dB3dC4d() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(1d, 2d), new DoublePoint(3d, 4d), new DoublePoint(5d, 6d)
        );
        Assertions.assertEquals(triangle, triangle.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfTriangleWithA2dB3dC4d() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(2d), new DoublePoint(3d), new DoublePoint(4d)
        );
        Assertions.assertEquals(
            triangle,
            new DoubleTriangle(new DoublePoint(2d), new DoublePoint(3d), new DoublePoint(4d))
        );
        Assertions.assertNotEquals(
            triangle,
            new DoubleTriangle(new DoublePoint(3d), new DoublePoint(2d), new DoublePoint(4d))
        );
    }

    @Test
    void hashCodeOfTriangleWithA2dB3dC4d() {
        Assertions.assertEquals(
            554632192,
            new DoubleTriangle(
                new DoublePoint(2d), new DoublePoint(3d), new DoublePoint(4d)
            ).hashCode()
        );
    }

    @Test
    void toStringOfTriangleWithA2dB3dC4d() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(2d), new DoublePoint(3d), new DoublePoint(4d)
        );
        Assertions.assertEquals("2.0|2.0 3.0|3.0 4.0|4.0", triangle.toString());
    }

    @Test
    void compareToOfTriangleWithA2dB3dC4d() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d), new DoublePoint(1d, 0d), new DoublePoint(1d)
        );
        Assertions.assertEquals(
            0d, triangle.compareTo(new DoubleTriangle(
                new DoublePoint(0d), new DoublePoint(1d, 0d), new DoublePoint(1d)
            ))
        );
        Assertions.assertEquals(
            -1d, triangle.compareTo(new DoubleTriangle(
                new DoublePoint(-1d), new DoublePoint(1d, 0d), new DoublePoint(1d)
            ))
        );
        Assertions.assertEquals(
            1d, triangle.compareTo(new DoubleTriangle(
                new DoublePoint(0.5d, 1d), new DoublePoint(1d, 0.5d), new DoublePoint(1d)
            ))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
            new DoubleTriangle(new DoublePoint(), new DoublePoint(), new DoublePoint()),
            Triangle.class
        );
    }

    // endregion
}
