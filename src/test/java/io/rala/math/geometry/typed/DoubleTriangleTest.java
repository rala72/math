package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Triangle;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class DoubleTriangleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithA2B3C4() {
        assertTriangle(
            new DoubleTriangle(
                new DoublePoint(2d),
                new DoublePoint(3d),
                new DoublePoint(4d)
            ),
            new DoublePoint(2d), new DoublePoint(3d), new DoublePoint(4d)
        );
    }

    @Test
    void createAndSetA() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        assertTriangle(triangle,
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        triangle.setA(new DoublePoint(1d));
        assertTriangle(triangle,
            new DoublePoint(1d), new DoublePoint(), new DoublePoint()
        );
    }

    @Test
    void createAndSetB() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        assertTriangle(triangle,
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        triangle.setB(new DoublePoint(2d));
        assertTriangle(triangle,
            new DoublePoint(), new DoublePoint(2d), new DoublePoint()
        );
    }

    @Test
    void createAndSetC() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        assertTriangle(triangle,
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        triangle.setC(new DoublePoint(3d));
        assertTriangle(triangle,
            new DoublePoint(), new DoublePoint(), new DoublePoint(3d)
        );
    }

    // endregion

    // region edges and altitudes

    @Test
    void edgesOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(1d), new DoublePoint(2d)
        );
        assertLineSegment(
            triangle.edgeA(),
            new DoublePoint(1d), new DoublePoint(2d)
        );
        assertLineSegment(
            triangle.edgeB(),
            new DoublePoint(), new DoublePoint(2d)
        );
        assertLineSegment(
            triangle.edgeC(),
            new DoublePoint(), new DoublePoint(1d)
        );
    }

    @Test
    void altitudesOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(),
            new DoublePoint(0d, 1d),
            new DoublePoint(1d, 1d)
        );

        LineSegment<Double> altitudeA = triangle.altitudeA();
        assertEquals(1d, altitudeA.length());
        assertEquals(triangle.getA(), altitudeA.getA());
        assertEquals(triangle.getB(), altitudeA.getB());

        LineSegment<Double> altitudeB = triangle.altitudeB();
        assertEquals(Math.sqrt(2d) / 2d, altitudeB.length());
        assertEquals(triangle.getB(), altitudeB.getA());
        assertEquals(triangle.edgeB().halvingPoint(), altitudeB.getB());

        LineSegment<Double> altitudeC = triangle.altitudeC();
        assertEquals(1d, altitudeC.length());
        assertEquals(triangle.getC(), altitudeC.getA());
        assertEquals(triangle.getB(), altitudeC.getB());
    }

    @Test
    void altitudesOfTriangleWithA00B10C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(),
            new DoublePoint(1d, 0d),
            new DoublePoint(1d, 1d)
        );

        LineSegment<Double> altitudeA = triangle.altitudeA();
        assertEquals(1d, altitudeA.length());
        assertEquals(triangle.getA(), altitudeA.getA());
        assertEquals(triangle.getB(), altitudeA.getB());

        LineSegment<Double> altitudeB = triangle.altitudeB();
        assertEquals(Math.sqrt(2d) / 2d, altitudeB.length());
        assertEquals(triangle.getB(), altitudeB.getA());
        assertEquals(triangle.edgeB().halvingPoint(), altitudeB.getB());

        LineSegment<Double> altitudeC = triangle.altitudeC();
        assertEquals(1d, altitudeC.length());
        assertEquals(triangle.getC(), altitudeC.getA());
        assertEquals(triangle.getB(), altitudeC.getB());
    }

    // endregion

    // region angles

    @Test
    void angleAlpha() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d, 0d), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertEquals(
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
        assertEquals(
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
        assertEquals(
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
        assertEquals(
            Math.PI,
            triangle.angleAlpha() +
                triangle.angleBeta() +
                triangle.angleGamma()
        );
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertEquals(
            0.5d,
            triangle.area(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void circumferenceOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertEquals(2d + Math.sqrt(2d), triangle.circumference());
    }

    // endregion

    // region centroid and orthoCenter

    @Test
    void centroidOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertPoint(triangle.centroid(), 1d / 3d, 2d / 3d);
    }

    @Test
    void orthoCenterOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertEquals(triangle.getB(), triangle.orthoCenter());
    }

    // endregion

    // region circumCircle and inCircle

    @Test
    void circumCircleOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertCircle(
            triangle.circumCircle(),
            new DoublePoint(0.5d, -0.5d),
            Math.sqrt(2d) / 2d
        );
    }

    @Test
    void inCircleOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertCircle(
            triangle.inCircle(),
            new DoublePoint(1d - Math.sqrt(2d) / 2d, Math.sqrt(2d) / 2d),
            1d - Math.sqrt(2d) / 2d
        );
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfTriangleWithA0_5B1_5C2_5() {
        DoubleTriangle triangle = new DoubleTriangle(
            new DoublePoint(0.5), new DoublePoint(1.5), new DoublePoint(2.5)
        );
        IntegerArithmetic integerArithmetic = new IntegerArithmetic();
        Triangle<Integer> result = new Triangle<>(integerArithmetic,
            new Point<>(integerArithmetic, 0),
            new Point<>(integerArithmetic, 1),
            new Point<>(integerArithmetic, 2)
        );
        assertEquals(result,
            triangle.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithPositiveValues() {
        assertTrue(
            new DoubleTriangle(
                new DoublePoint(1d), new DoublePoint(1d, 2d), new DoublePoint(2d)
            ).isValid()
        );
    }

    @Test
    void isValidWithLineValues() {
        assertFalse(
            new DoubleTriangle(
                new DoublePoint(0d), new DoublePoint(1d), new DoublePoint(2d)
            ).isValid()
        );
    }

    @Test
    void isValidWithZeroValues() {
        assertFalse(
            new DoubleTriangle(
                new DoublePoint(0d), new DoublePoint(0d), new DoublePoint(0d)
            ).isValid()
        );
    }

    @Test
    void isValidWithInfValues() {
        assertFalse(
            new DoubleTriangle(
                new DoublePoint(Double.POSITIVE_INFINITY),
                new DoublePoint(Double.POSITIVE_INFINITY),
                new DoublePoint(Double.POSITIVE_INFINITY)
            ).isValid()
        );
    }

    @Test
    void moveOfTriangleWithXYWithXY() {
        assertTriangle(
            new DoubleTriangle(
                new DoublePoint(0d),
                new DoublePoint(1d, 0d),
                new DoublePoint(1d)
            ).move(1d),
            new DoublePoint(1d), new DoublePoint(2d, 1d), new DoublePoint(2d)
        );
    }

    @Test
    void moveOfTriangleWithXYWithXAndY() {
        assertTriangle(
            new DoubleTriangle(
                new DoublePoint(0d),
                new DoublePoint(1d, 0d),
                new DoublePoint(1d)
            ).move(1d, 1d),
            new DoublePoint(1d), new DoublePoint(2d, 1d), new DoublePoint(2d)
        );
    }

    @Test
    void moveOfTriangleWithXYWithVector() {
        assertTriangle(
            new DoubleTriangle(
                new DoublePoint(0d),
                new DoublePoint(1d, 0d),
                new DoublePoint(1d)
            ).move(new DoubleVector(1d)),
            new DoublePoint(1d), new DoublePoint(2d, 1d), new DoublePoint(2d)
        );
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithoutCenterWithPiHalf() {
        assertTriangle(
            new DoubleTriangle(
                new DoublePoint(0d, 0d),
                new DoublePoint(0d, 1d),
                new DoublePoint(1d, 1d)
            ).rotate(Math.PI / 2d),
            new DoublePoint(),
            new DoublePoint(-1d, 6.123233995736766e-17d),
            new DoublePoint(-0.9999999999999999d, 1d)
        );
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithCenterXY1WithPiHalf() {
        assertTriangle(
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
    void copyOfTriangleWithA2B3C4() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(1d, 2d), new DoublePoint(3d, 4d), new DoublePoint(5d, 6d)
        );
        assertEquals(triangle, triangle.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfTriangleWithA2B3C4() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(2d),
            new DoublePoint(3d),
            new DoublePoint(4d)
        );
        assertEquals(triangle,
            new DoubleTriangle(
                new DoublePoint(2d),
                new DoublePoint(3d),
                new DoublePoint(4d)
            )
        );
        assertNotEquals(triangle,
            new DoubleTriangle(
                new DoublePoint(3d),
                new DoublePoint(2d),
                new DoublePoint(4d)
            )
        );
    }

    @Test
    void hashCodeOfTriangleWithA2B3C4() {
        assertEquals(554632192,
            new DoubleTriangle(
                new DoublePoint(2d), new DoublePoint(3d), new DoublePoint(4d)
            ).hashCode()
        );
    }

    @Test
    void toStringOfTriangleWithA2B3C4() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(2d), new DoublePoint(3d), new DoublePoint(4d)
        );
        assertEquals("2.0|2.0 3.0|3.0 4.0|4.0", triangle.toString());
    }

    @Test
    void compareToOfTriangleWithA2B3C4() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d), new DoublePoint(1d, 0d), new DoublePoint(1d)
        );
        assertEquals(0d,
            triangle.compareTo(new DoubleTriangle(
                new DoublePoint(0d), new DoublePoint(1d, 0d), new DoublePoint(1d)
            ))
        );
        assertEquals(-1d,
            triangle.compareTo(new DoubleTriangle(
                new DoublePoint(-1d), new DoublePoint(1d, 0d), new DoublePoint(1d)
            ))
        );
        assertEquals(1d,
            triangle.compareTo(new DoubleTriangle(
                new DoublePoint(0.5d, 1d), new DoublePoint(1d, 0.5d), new DoublePoint(1d)
            ))
        );
    }

    @Test
    void serializable() {
        assertSerializable(
            new DoubleTriangle(new DoublePoint(), new DoublePoint(), new DoublePoint()),
            DoubleTriangle.class
        );
    }

    // endregion
}
