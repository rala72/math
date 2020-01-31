package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Triangle;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

class BigDecimalTriangleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithA2B3C4() {
        GeometryAssertions.assertTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(3d)),
                new BigDecimalPoint(BigDecimal.valueOf(4d))
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            new BigDecimalPoint(BigDecimal.valueOf(4d))
        );
    }

    @Test
    void constructorWithA2B3C4AndMathContext5() {
        GeometryAssertions.assertTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(3d)),
                new BigDecimalPoint(BigDecimal.valueOf(4d)),
                new MathContext(5)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            new BigDecimalPoint(BigDecimal.valueOf(4d))
        );
    }

    @Test
    void createAndSetA() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(), new BigDecimalPoint(), new BigDecimalPoint()
        );
        GeometryAssertions.assertTriangle(
            triangle,
            new BigDecimalPoint(), new BigDecimalPoint(), new BigDecimalPoint()
        );
        triangle.setA(new BigDecimalPoint(BigDecimal.ONE));
        GeometryAssertions.assertTriangle(
            triangle,
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(),
            new BigDecimalPoint()
        );
    }

    @Test
    void createAndSetB() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(), new BigDecimalPoint(), new BigDecimalPoint()
        );
        GeometryAssertions.assertTriangle(
            triangle,
            new BigDecimalPoint(), new BigDecimalPoint(), new BigDecimalPoint()
        );
        triangle.setB(new BigDecimalPoint(BigDecimal.valueOf(2d)));
        GeometryAssertions.assertTriangle(
            triangle,
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint()
        );
    }

    @Test
    void createAndSetC() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(), new BigDecimalPoint(), new BigDecimalPoint()
        );
        GeometryAssertions.assertTriangle(
            triangle,
            new BigDecimalPoint(), new BigDecimalPoint(), new BigDecimalPoint()
        );
        triangle.setC(new BigDecimalPoint(BigDecimal.valueOf(3d)));
        GeometryAssertions.assertTriangle(
            triangle,
            new BigDecimalPoint(),
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.valueOf(3d))
        );
    }

    // endregion

    // region edges and altitudes

    @Test
    void edgesOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
        GeometryAssertions.assertLineSegment(
            triangle.edgeA(),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
        GeometryAssertions.assertLineSegment(
            triangle.edgeB(),
            new BigDecimalPoint(), new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
        GeometryAssertions.assertLineSegment(
            triangle.edgeC(),
            new BigDecimalPoint(), new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void altitudesOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );

        LineSegment<BigDecimal> altitudeA = triangle.altitudeA();
        Assertions.assertEquals(BigDecimal.ONE, altitudeA.length());
        Assertions.assertEquals(triangle.getA(), altitudeA.getA());
        Assertions.assertEquals(triangle.getB(), altitudeA.getB());

        LineSegment<BigDecimal> altitudeB = triangle.altitudeB();
        Assertions.assertEquals(
            BigDecimal.valueOf(Math.sqrt(2d) / 2d)
                .round(GeometryAssertions.CONTEXT),
            altitudeB.length()
        );
        Assertions.assertEquals(triangle.getB(), altitudeB.getA());
        Assertions.assertEquals(triangle.edgeB().halvingPoint(), altitudeB.getB());

        LineSegment<BigDecimal> altitudeC = triangle.altitudeC();
        Assertions.assertEquals(BigDecimal.ONE, altitudeC.length());
        Assertions.assertEquals(triangle.getC(), altitudeC.getA());
        Assertions.assertEquals(triangle.getB(), altitudeC.getB());
    }

    @Test
    void altitudesOfTriangleWithA00B10C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );

        LineSegment<BigDecimal> altitudeA = triangle.altitudeA();
        Assertions.assertEquals(BigDecimal.ONE, altitudeA.length());
        Assertions.assertEquals(triangle.getA(), altitudeA.getA());
        Assertions.assertEquals(triangle.getB(), altitudeA.getB());

        LineSegment<BigDecimal> altitudeB = triangle.altitudeB();
        Assertions.assertEquals(
            BigDecimal.valueOf(Math.sqrt(2d) / 2d)
                .round(GeometryAssertions.CONTEXT),
            altitudeB.length()
        );
        Assertions.assertEquals(triangle.getB(), altitudeB.getA());
        Assertions.assertEquals(triangle.edgeB().halvingPoint(), altitudeB.getB());

        LineSegment<BigDecimal> altitudeC = triangle.altitudeC();
        Assertions.assertEquals(BigDecimal.ONE, altitudeC.length());
        Assertions.assertEquals(triangle.getC(), altitudeC.getA());
        Assertions.assertEquals(triangle.getB(), altitudeC.getB());
    }

    // endregion

    // region angles

    @Test
    void angleAlpha() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        Assertions.assertEquals(
            BigDecimal.valueOf(0.7853981637), // PI/4
            triangle.angleAlpha()
        );
    }

    @Test
    void angleBeta() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        Assertions.assertEquals(
            BigDecimal.valueOf(1.570796326), // PI/2
            triangle.angleBeta()
        );
    }

    @Test
    void angleGamma() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        Assertions.assertEquals(
            BigDecimal.valueOf(0.7853981637), // PI/4
            triangle.angleGamma()
        );
    }

    @Test
    void angleSum() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        Assertions.assertEquals(
            BigDecimal.valueOf(3.1415926534),
            triangle.angleAlpha()
                .add(triangle.angleBeta())
                .add(triangle.angleGamma())
        );
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        Assertions.assertEquals(
            BigDecimal.valueOf(0.5d),
            triangle.area()
        );
    }

    @Test
    void circumferenceOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        Assertions.assertEquals(
            BigDecimal.valueOf(2d + Math.sqrt(2d))
                .round(GeometryAssertions.CONTEXT),
            triangle.circumference()
        );
    }

    // endregion

    // region centroid and orthoCenter

    @Test
    void centroidOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        GeometryAssertions.assertPoint(triangle.centroid(),
            BigDecimal.valueOf(1 / 3d), BigDecimal.valueOf(2d / 3d)
        );
    }

    @Test
    void orthoCenterOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        Assertions.assertEquals(triangle.getB(), triangle.orthoCenter());
    }

    // endregion

    // region circumCircle and inCircle

    @Test
    void circumCircleOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        GeometryAssertions.assertCircle(
            triangle.circumCircle(),
            new BigDecimalPoint(BigDecimal.valueOf(0.5d), BigDecimal.valueOf(-0.5d)),
            BigDecimal.valueOf(Math.sqrt(2d) / 2d)
        );
    }

    @Test
    void inCircleOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        GeometryAssertions.assertCircle(
            triangle.inCircle(),
            new BigDecimalPoint(
                BigDecimal.valueOf(1 - Math.sqrt(2d) / 2d),
                BigDecimal.valueOf(Math.sqrt(2d) / 2d)
            ),
            BigDecimal.valueOf(1d - Math.sqrt(2d) / 2d)
        );
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfTriangleWithA0_5B1_5C2_5() {
        BigDecimalTriangle triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.valueOf(0.5)),
            new BigDecimalPoint(BigDecimal.valueOf(1.5)),
            new BigDecimalPoint(BigDecimal.valueOf(2.5))
        );
        IntegerArithmetic integerArithmetic = new IntegerArithmetic();
        Triangle<Integer> result = new Triangle<>(integerArithmetic,
            new Point<>(integerArithmetic, 0),
            new Point<>(integerArithmetic, 1),
            new Point<>(integerArithmetic, 2)
        );
        Assertions.assertEquals(result,
            triangle.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithPositiveValues() {
        Assertions.assertTrue(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(2d))
            ).isValid()
        );
    }

    @Test
    void isValidWithLineValues() {
        Assertions.assertFalse(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.valueOf(2d))
            ).isValid()
        );
    }

    @Test
    void isValidWithZeroValues() {
        Assertions.assertFalse(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ZERO)
            ).isValid()
        );
    }

    @Test
    void moveOfTriangleWithXYWithXY() {
        GeometryAssertions.assertTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void moveOfTriangleWithXYWithXAndY() {
        GeometryAssertions.assertTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(BigDecimal.ONE, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void moveOfTriangleWithXYWithVector() {
        GeometryAssertions.assertTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(new BigDecimalVector(BigDecimal.ONE)),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithoutCenterWithPiHalf() {
        GeometryAssertions.assertTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
            ).rotate(BigDecimal.valueOf(Math.PI / 2d)),
            new BigDecimalPoint(),
            new BigDecimalPoint(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(6.123233995736766e-17d)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(-0.9999999999999999d), BigDecimal.ONE)
        );
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithCenterXY1WithPiHalf() {
        GeometryAssertions.assertTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
            ).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI / 2d)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.ZERO),
            new BigDecimalPoint(
                BigDecimal.valueOf(0.9999999999999999d),
                BigDecimal.ZERO
            ),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
    }

    @Test
    void copyOfTriangleWithA2B3C4() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d), BigDecimal.valueOf(4d)),
            new BigDecimalPoint(BigDecimal.valueOf(5d), BigDecimal.valueOf(6d))
        );
        Assertions.assertEquals(triangle, triangle.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfTriangleWithA2B3C4() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            new BigDecimalPoint(BigDecimal.valueOf(4d))
        );
        Assertions.assertEquals(
            triangle,
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(3d)),
                new BigDecimalPoint(BigDecimal.valueOf(4d))
            )
        );
        Assertions.assertNotEquals(
            triangle,
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.valueOf(3d)),
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(4d))
            )
        );
    }

    @Test
    void hashCodeOfTriangleWithA2B3C4() {
        Assertions.assertEquals(
            21044320,
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(3d)),
                new BigDecimalPoint(BigDecimal.valueOf(4d))
            ).hashCode()
        );
    }

    @Test
    void toStringOfTriangleWithA2B3C4() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            new BigDecimalPoint(BigDecimal.valueOf(4d))
        );
        Assertions.assertEquals("2.0|2.0 3.0|3.0 4.0|4.0", triangle.toString());
    }

    @Test
    void compareToOfTriangleWithA2B3C4() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ONE)
        );
        Assertions.assertEquals(
            0, triangle.compareTo(new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ))
        );
        Assertions.assertEquals(
            -1, triangle.compareTo(new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ONE.negate()),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ))
        );
        Assertions.assertEquals(
            1, triangle.compareTo(new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.valueOf(0.5d), BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(0.5d)),
                new BigDecimalPoint(BigDecimal.ONE)
            ))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
            new BigDecimalTriangle(
                new BigDecimalPoint(),
                new BigDecimalPoint(),
                new BigDecimalPoint()
            ),
            Triangle.class
        );
    }

    // endregion
}
