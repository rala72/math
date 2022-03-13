package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Triangle;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.GeometryAssertions.CONTEXT;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

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
        GeometryAssertions.assertTriangle(triangle,
            new BigDecimalPoint(), new BigDecimalPoint(), new BigDecimalPoint()
        );
        triangle.setA(new BigDecimalPoint(BigDecimal.ONE));
        GeometryAssertions.assertTriangle(triangle,
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
        GeometryAssertions.assertTriangle(triangle,
            new BigDecimalPoint(), new BigDecimalPoint(), new BigDecimalPoint()
        );
        triangle.setB(new BigDecimalPoint(BigDecimal.valueOf(2d)));
        GeometryAssertions.assertTriangle(triangle,
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
        GeometryAssertions.assertTriangle(triangle,
            new BigDecimalPoint(), new BigDecimalPoint(), new BigDecimalPoint()
        );
        triangle.setC(new BigDecimalPoint(BigDecimal.valueOf(3d)));
        GeometryAssertions.assertTriangle(triangle,
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
        assertThat(altitudeA.length()).isEqualTo(BigDecimal.ONE);
        assertThat(altitudeA.getA()).isEqualTo(triangle.getA());
        assertThat(altitudeA.getB()).isEqualTo(triangle.getB());

        LineSegment<BigDecimal> altitudeB = triangle.altitudeB();
        assertThat(altitudeB.length()).isEqualTo(BigDecimal.valueOf(2d).sqrt(CONTEXT)
            .divide(BigDecimal.valueOf(2d), CONTEXT));
        assertThat(altitudeB.getA()).isEqualTo(triangle.getB());
        assertThat(altitudeB.getB()).isEqualTo(triangle.edgeB().halvingPoint());

        LineSegment<BigDecimal> altitudeC = triangle.altitudeC();
        assertThat(altitudeC.length()).isEqualTo(BigDecimal.ONE);
        assertThat(altitudeC.getA()).isEqualTo(triangle.getC());
        assertThat(altitudeC.getB()).isEqualTo(triangle.getB());
    }

    @Test
    void altitudesOfTriangleWithA00B10C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );

        LineSegment<BigDecimal> altitudeA = triangle.altitudeA();
        assertThat(altitudeA.length()).isEqualTo(BigDecimal.ONE);
        assertThat(altitudeA.getA()).isEqualTo(triangle.getA());
        assertThat(altitudeA.getB()).isEqualTo(triangle.getB());

        LineSegment<BigDecimal> altitudeB = triangle.altitudeB();
        assertThat(altitudeB.length()).isEqualTo(BigDecimal.valueOf(0.7071067811865475));
        assertThat(altitudeB.getA()).isEqualTo(triangle.getB());
        assertThat(altitudeB.getB()).isEqualTo(triangle.edgeB().halvingPoint());

        LineSegment<BigDecimal> altitudeC = triangle.altitudeC();
        assertThat(altitudeC.length()).isEqualTo(BigDecimal.ONE);
        assertThat(altitudeC.getA()).isEqualTo(triangle.getC());
        assertThat(altitudeC.getB()).isEqualTo(triangle.getB());
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
        assertThat(triangle.angleAlpha()).isEqualTo(BigDecimal.valueOf(0.7853981633974484));
    }

    @Test
    void angleBeta() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        assertThat(triangle.angleBeta()).isEqualTo(BigDecimal.valueOf(1.570796326794897));
    }

    @Test
    void angleGamma() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        assertThat(triangle.angleGamma()).isEqualTo(BigDecimal.valueOf(0.7853981633974484));
    }

    @Test
    void angleSum() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        assertThat(triangle.angleAlpha()
            .add(triangle.angleBeta())
            .add(triangle.angleGamma())).isEqualTo(new BigDecimal("3.1415926535897938"));
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
        assertThat(triangle.area()).isEqualTo(BigDecimal.valueOf(0.5000000000000009));
    }

    @Test
    void circumferenceOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        assertThat(triangle.circumference()).isEqualTo(BigDecimal.valueOf(2d + Math.sqrt(2d))
            .round(CONTEXT));
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
        assertThat(triangle.orthoCenter()).isEqualTo(triangle.getB());
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
            new BigDecimalPoint(BigDecimal.valueOf(0.5), BigDecimal.valueOf(-0.5)),
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
        assertThat(triangle.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithPositiveValues() {
        assertThat(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        ).isValid()).isTrue();
    }

    @Test
    void isValidWithLineValues() {
        assertThat(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        ).isValid()).isFalse();
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO)
        ).isValid()).isFalse();
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
            new BigDecimalPoint(BigDecimal.valueOf(-0.9999999999999999), BigDecimal.ONE)
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
                BigDecimal.valueOf(0.9999999999999999),
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
        assertThat(triangle.copy()).isEqualTo(triangle);
    }

    // endregion

    // region override

    @Test
    void equalsOfTriangleWithA2B3C4() {
        assertThat(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            new BigDecimalPoint(BigDecimal.valueOf(4d))
        )).isEqualTo(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            new BigDecimalPoint(BigDecimal.valueOf(4d))
        )).isNotEqualTo(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(4d))
        ));
    }

    @Test
    void hashCodeOfTriangleWithA2B3C4() {
        assertThat(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            new BigDecimalPoint(BigDecimal.valueOf(4d))
        ).hashCode()).isEqualTo(21044320);
    }

    @Test
    void toStringOfTriangleWithA2B3C4() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            new BigDecimalPoint(BigDecimal.valueOf(4d))
        );
        assertThat(triangle).hasToString("2.0|2.0 3.0|3.0 4.0|4.0");
    }

    @Test
    void compareToOfTriangleWithA2B3C4() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ONE)
        );
        assertThat(triangle)
            .isEqualByComparingTo(new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ))
            .isLessThan(new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ONE.negate()),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ))
            .isGreaterThan(new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.valueOf(0.5), BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(0.5)),
                new BigDecimalPoint(BigDecimal.ONE)
            ));
    }

    @Test
    void serializable() {
        assertSerializable(
            new BigDecimalTriangle(
                new BigDecimalPoint(),
                new BigDecimalPoint(),
                new BigDecimalPoint()
            ),
            BigDecimalTriangle.class
        );
    }

    // endregion
}
