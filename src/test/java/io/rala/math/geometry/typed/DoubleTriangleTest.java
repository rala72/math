package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Triangle;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class DoubleTriangleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithA2B3C4() {
        assertThatTriangle(
            new DoubleTriangle(
                new DoublePoint(2d),
                new DoublePoint(3d),
                new DoublePoint(4d)
            )
        ).hasA(new DoublePoint(2d))
            .hasB(new DoublePoint(3d))
            .hasC(new DoublePoint(4d));
    }

    @Test
    void createAndSetA() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasBWithZeroXY()
            .hasCWithZeroXY();
        triangle.setA(new DoublePoint(1d));
        assertThatTriangle(triangle)
            .hasA(new DoublePoint(1d))
            .hasBWithZeroXY()
            .hasCWithZeroXY();
    }

    @Test
    void createAndSetB() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasBWithZeroXY()
            .hasCWithZeroXY();
        triangle.setB(new DoublePoint(2d));
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasB(new DoublePoint(2d))
            .hasCWithZeroXY();
    }

    @Test
    void createAndSetC() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(), new DoublePoint()
        );
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasBWithZeroXY()
            .hasCWithZeroXY();
        triangle.setC(new DoublePoint(3d));
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasBWithZeroXY()
            .hasC(new DoublePoint(3d));
    }

    // endregion

    // region edges and altitudes

    @Test
    void edgesOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(1d), new DoublePoint(2d)
        );
        assertThatLineSegment(triangle.edgeA())
            .hasA(new DoublePoint(1d)).hasB(new DoublePoint(2d));
        assertThatLineSegment(triangle.edgeB())
            .hasAWithZeroXY().hasB(new DoublePoint(2d));
        assertThatLineSegment(triangle.edgeC())
            .hasAWithZeroXY().hasB(new DoublePoint(1d));
    }

    @Test
    void altitudesOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(),
            new DoublePoint(0d, 1d),
            new DoublePoint(1d, 1d)
        );

        LineSegment<Double> altitudeA = triangle.altitudeA();
        assertThatLineSegment(altitudeA).hasLength(1d);
        assertThatPoint(altitudeA.getA()).isEqualTo(triangle.getA());
        assertThatPoint(altitudeA.getB()).isEqualTo(triangle.getB());

        LineSegment<Double> altitudeB = triangle.altitudeB();
        assertThatLineSegment(altitudeB).hasLength(Math.sqrt(2d) / 2d);
        assertThatPoint(altitudeB.getA()).isEqualTo(triangle.getB());
        assertThatPoint(altitudeB.getB()).isEqualTo(triangle.edgeB().halvingPoint());

        LineSegment<Double> altitudeC = triangle.altitudeC();
        assertThatLineSegment(altitudeC).hasLength(1d);
        assertThatPoint(altitudeC.getA()).isEqualTo(triangle.getC());
        assertThatPoint(altitudeC.getB()).isEqualTo(triangle.getB());
    }

    @Test
    void altitudesOfTriangleWithA00B10C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(),
            new DoublePoint(1d, 0d),
            new DoublePoint(1d, 1d)
        );

        LineSegment<Double> altitudeA = triangle.altitudeA();
        assertThatLineSegment(altitudeA).hasLength(1d);
        assertThatPoint(altitudeA.getA()).isEqualTo(triangle.getA());
        assertThatPoint(altitudeA.getB()).isEqualTo(triangle.getB());

        LineSegment<Double> altitudeB = triangle.altitudeB();
        assertThatLineSegment(altitudeB).hasLength(Math.sqrt(2d) / 2d);
        assertThatPoint(altitudeB.getA()).isEqualTo(triangle.getB());
        assertThatPoint(altitudeB.getB()).isEqualTo(triangle.edgeB().halvingPoint());

        LineSegment<Double> altitudeC = triangle.altitudeC();
        assertThatLineSegment(altitudeC).hasLength(1d);
        assertThatPoint(altitudeC.getA()).isEqualTo(triangle.getC());
        assertThatPoint(altitudeC.getB()).isEqualTo(triangle.getB());
    }

    // endregion

    // region angles

    @Test
    void angleAlpha() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d, 0d), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThatTriangle(triangle).hasAngleAlpha(Math.PI / 4d);
    }

    @Test
    void angleBeta() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d, 0d), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThatTriangle(triangle).hasAngleBeta(Math.PI / 2d);
    }

    @Test
    void angleGamma() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d, 0d), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThatTriangle(triangle).hasAngleGamma(Math.PI / 4d);
    }

    @Test
    void angleSum() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d, 0d), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThat(triangle.angleAlpha() +
            triangle.angleBeta() +
            triangle.angleGamma()
        ).isEqualTo(Math.PI);
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThatTriangle(triangle).hasAreaCloseTo(0.5);
    }

    @Test
    void circumferenceOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThatTriangle(triangle).hasCircumference(2d + Math.sqrt(2d));
    }

    // endregion

    // region centroid and orthoCenter

    @Test
    void centroidOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThatPoint(triangle.centroid()).hasX(1d / 3d).hasY(2d / 3d);
    }

    @Test
    void orthoCenterOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThatPoint(triangle.orthoCenter()).isEqualTo(triangle.getB());
    }

    // endregion

    // region circumCircle and inCircle

    @Test
    void circumCircleOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThatCircle(triangle.circumCircle())
            .hasCenter(new DoublePoint(0.5, -0.5))
            .hasRadius(Math.sqrt(2d) / 2d);
    }

    @Test
    void inCircleOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThatCircle(triangle.inCircle())
            .hasCenterCloseTo(new DoublePoint(
                1d - Math.sqrt(2d) / 2d,
                Math.sqrt(2d) / 2d
            ))
            .hasRadius(1d - Math.sqrt(2d) / 2d);
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
        assertThatTriangle(triangle.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithPositiveValues() {
        assertThatTriangle(new DoubleTriangle(
            new DoublePoint(1d), new DoublePoint(1d, 2d), new DoublePoint(2d)
        )).isValid();
    }

    @Test
    void isValidWithLineValues() {
        assertThatTriangle(new DoubleTriangle(
            new DoublePoint(0d), new DoublePoint(1d), new DoublePoint(2d)
        )).isInvalid();
    }

    @Test
    void isValidWithZeroValues() {
        assertThatTriangle(new DoubleTriangle(
            new DoublePoint(0d), new DoublePoint(0d), new DoublePoint(0d)
        )).isInvalid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatTriangle(new DoubleTriangle(
            new DoublePoint(Double.POSITIVE_INFINITY),
            new DoublePoint(Double.POSITIVE_INFINITY),
            new DoublePoint(Double.POSITIVE_INFINITY)
        )).isInvalid();
    }

    @Test
    void moveOfTriangleWithXYWithXY() {
        assertThatTriangle(
            new DoubleTriangle(
                new DoublePoint(0d),
                new DoublePoint(1d, 0d),
                new DoublePoint(1d)
            ).move(1d)
        ).hasA(new DoublePoint(1d))
            .hasB(new DoublePoint(2d, 1d))
            .hasC(new DoublePoint(2d));
    }

    @Test
    void moveOfTriangleWithXYWithXAndY() {
        assertThatTriangle(
            new DoubleTriangle(
                new DoublePoint(0d),
                new DoublePoint(1d, 0d),
                new DoublePoint(1d)
            ).move(1d, 1d)
        ).hasA(new DoublePoint(1d))
            .hasB(new DoublePoint(2d, 1d))
            .hasC(new DoublePoint(2d));
    }

    @Test
    void moveOfTriangleWithXYWithVector() {
        assertThatTriangle(
            new DoubleTriangle(
                new DoublePoint(0d),
                new DoublePoint(1d, 0d),
                new DoublePoint(1d)
            ).move(new DoubleVector(1d))
        ).hasA(new DoublePoint(1d))
            .hasB(new DoublePoint(2d, 1d))
            .hasC(new DoublePoint(2d));
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithoutCenterWithPiHalf() {
        assertThatTriangle(
            new DoubleTriangle(
                new DoublePoint(0d, 0d),
                new DoublePoint(0d, 1d),
                new DoublePoint(1d, 1d)
            ).rotate(Math.PI / 2d)
        ).hasAWithZeroXY()
            .hasB(new DoublePoint(-1d, 6.123233995736766e-17d))
            .hasCCloseTo(new DoublePoint(-1d, 1d));
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithCenterXY1WithPiHalf() {
        assertThatTriangle(
            new DoubleTriangle(
                new DoublePoint(0d, 0d),
                new DoublePoint(0d, 1d),
                new DoublePoint(1d, 1d)
            ).rotate(new DoublePoint(1d), Math.PI / 2d)
        ).hasA(new DoublePoint(2d, 0d))
            .hasBCloseTo(new DoublePoint(1d, 0d))
            .hasC(new DoublePoint(1d, 1d));
    }

    @Test
    void copyOfTriangleWithA2B3C4() {
        assertCopyable(new DoubleTriangle(
            new DoublePoint(1d, 2d),
            new DoublePoint(3d, 4d),
            new DoublePoint(5d, 6d)
        ));
    }

    // endregion

    // region override

    @Test
    void equalsOfTriangleWithA2B3C4() {
        assertThatTriangle(new DoubleTriangle(
            new DoublePoint(2d),
            new DoublePoint(3d),
            new DoublePoint(4d)
        )).isEqualTo(new DoubleTriangle(
            new DoublePoint(2d),
            new DoublePoint(3d),
            new DoublePoint(4d)
        )).isNotEqualTo(new DoubleTriangle(
            new DoublePoint(3d),
            new DoublePoint(2d),
            new DoublePoint(4d)
        ));
    }

    @Test
    void hashCodeOfTriangleWithA2B3C4() {
        assertThat(new DoubleTriangle(
            new DoublePoint(2d), new DoublePoint(3d), new DoublePoint(4d)
        ).hashCode()).isEqualTo(554632192);
    }

    @Test
    void toStringOfTriangleWithA2B3C4() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(2d), new DoublePoint(3d), new DoublePoint(4d)
        );
        assertThatTriangle(triangle).hasToString("2.0|2.0 3.0|3.0 4.0|4.0");
    }

    @Test
    void compareToOfTriangleWithA2B3C4() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d), new DoublePoint(1d, 0d), new DoublePoint(1d)
        );
        assertThatTriangle(triangle)
            .isEqualByComparingTo(new DoubleTriangle(
                new DoublePoint(0d), new DoublePoint(1d, 0d), new DoublePoint(1d)
            ))
            .isLessThan(new DoubleTriangle(
                new DoublePoint(-1d), new DoublePoint(1d, 0d), new DoublePoint(1d)
            ))
            .isGreaterThan(new DoubleTriangle(
                new DoublePoint(0.5, 1d), new DoublePoint(1d, 0.5), new DoublePoint(1d)
            ));
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
