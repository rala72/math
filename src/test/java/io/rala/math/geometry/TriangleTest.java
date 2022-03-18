package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestTriangle;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static io.rala.math.testUtils.assertion.utils.OffsetUtils.doubleOffset;
import static org.assertj.core.api.Assertions.assertThat;

class TriangleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithA2B3C4() {
        assertThatTriangle(
            new TestTriangle(new TestPoint(2), new TestPoint(3), new TestPoint(4))
        ).hasA(new TestPoint(2))
            .hasB(new TestPoint(3))
            .hasC(new TestPoint(4));
    }

    @Test
    void createAndSetA() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(), new TestPoint()
        );
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasBWithZeroXY()
            .hasCWithZeroXY();
        triangle.setA(new TestPoint(1));
        assertThatTriangle(triangle)
            .hasA(new TestPoint(1))
            .hasBWithZeroXY()
            .hasCWithZeroXY();
    }

    @Test
    void createAndSetB() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(), new TestPoint()
        );
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasBWithZeroXY()
            .hasCWithZeroXY();
        triangle.setB(new TestPoint(2));
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasB(new TestPoint(2))
            .hasCWithZeroXY();
    }

    @Test
    void createAndSetC() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(), new TestPoint()
        );
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasBWithZeroXY()
            .hasCWithZeroXY();
        triangle.setC(new TestPoint(3));
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasBWithZeroXY()
            .hasC(new TestPoint(3));
    }

    // endregion

    // region edges and altitudes

    @Test
    void edgesOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(1), new TestPoint(2)
        );
        assertThatLineSegment(triangle.edgeA())
            .hasA(new TestPoint(1))
            .hasB(new TestPoint(2));
        assertThatLineSegment(triangle.edgeB())
            .hasAWithZeroXY()
            .hasB(new TestPoint(2));
        assertThatLineSegment(triangle.edgeC())
            .hasAWithZeroXY()
            .hasB(new TestPoint(1));
    }

    @Test
    void altitudesOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(),
            new TestPoint(0d, 1d),
            new TestPoint(1d, 1d)
        );

        LineSegment<Number> altitudeA = triangle.altitudeA();
        assertThatLineSegment(altitudeA).hasLength(1d);
        assertThatPoint(altitudeA.getA()).isEqualTo(triangle.getA());
        assertThatPoint(altitudeA.getB()).isEqualTo(triangle.getB());

        LineSegment<Number> altitudeB = triangle.altitudeB();
        assertThatLineSegment(altitudeB).hasLength(Math.sqrt(2) / 2);
        assertThatPoint(altitudeB.getA()).isEqualTo(triangle.getB());
        assertThatPoint(altitudeB.getB()).isEqualTo(triangle.edgeB().halvingPoint());

        LineSegment<Number> altitudeC = triangle.altitudeC();
        assertThatLineSegment(altitudeC).hasLength(1d);
        assertThatPoint(altitudeC.getA()).isEqualTo(triangle.getC());
        assertThatPoint(altitudeC.getB()).isEqualTo(triangle.getB());
    }

    @Test
    void altitudesOfTriangleWithA00B10C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(),
            new TestPoint(1d, 0d),
            new TestPoint(1d, 1d)
        );

        LineSegment<Number> altitudeA = triangle.altitudeA();
        assertThatLineSegment(altitudeA).hasLength(1d);
        assertThatPoint(altitudeA.getA()).isEqualTo(triangle.getA());
        assertThatPoint(altitudeA.getB()).isEqualTo(triangle.getB());

        LineSegment<Number> altitudeB = triangle.altitudeB();
        assertThatLineSegment(altitudeB).hasLength(Math.sqrt(2) / 2);
        assertThatPoint(altitudeB.getA()).isEqualTo(triangle.getB());
        assertThatPoint(altitudeB.getB()).isEqualTo(triangle.edgeB().halvingPoint());

        LineSegment<Number> altitudeC = triangle.altitudeC();
        assertThatLineSegment(altitudeC).hasLength(1d);
        assertThatPoint(altitudeC.getA()).isEqualTo(triangle.getC());
        assertThatPoint(altitudeC.getB()).isEqualTo(triangle.getB());
    }

    // endregion

    // region angles

    @Test
    void angleAlpha() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(0, 0), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThatTriangle(triangle).hasAngleAlpha(Math.PI / 4);
    }

    @Test
    void angleBeta() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(0, 0), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThatTriangle(triangle).hasAngleBeta(Math.PI / 2);
    }

    @Test
    void angleGamma() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(0, 0), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThatTriangle(triangle).hasAngleGamma(Math.PI / 4);
    }

    @Test
    void angleSum() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(0, 0), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThat(triangle.angleAlpha().doubleValue() +
            triangle.angleBeta().doubleValue() +
            triangle.angleGamma().doubleValue()
        ).isEqualTo(Math.PI);
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThatTriangle(triangle).hasAreaCloseTo(0.5);
    }

    @Test
    void circumferenceOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThatTriangle(triangle).hasCircumference(2 + Math.sqrt(2));
    }

    // endregion

    // region centroid and orthoCenter

    @Test
    void centroidOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThatPoint(triangle.centroid()).hasX(1d / 3).hasY(2d / 3);
    }

    @Test
    void orthoCenterOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0d, 1d), new TestPoint(1d, 1d)
        );
        assertThatPoint(triangle.orthoCenter()).isEqualTo(triangle.getB());
    }

    // endregion

    // region circumCircle and inCircle

    @Test
    void circumCircleOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThatCircle(triangle.circumCircle())
            .hasCenter(new TestPoint(0.5, -0.5))
            .hasRadius(Math.sqrt(2) / 2);
    }

    @Test
    void inCircleOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThatCircle(triangle.inCircle())
            .hasCenterCloseTo(new TestPoint(
                1 - Math.sqrt(2) / 2,
                Math.sqrt(2) / 2
            ))
            .hasRadius(1 - Math.sqrt(2) / 2);
    }

    @Test
    void circumCircleRadiusOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThat(triangle.circumCircleRadius().doubleValue())
            .isCloseTo(Math.sqrt(2) / 2, doubleOffset());
    }

    @Test
    void circumCirclePointOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThatPoint(triangle.circumCirclePoint()).isEqualTo(new TestPoint(0.5, -0.5));
    }

    @Test
    void inCircleRadiusOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThat(triangle.inCircleRadius().doubleValue())
            .isCloseTo(1 - Math.sqrt(2) / 2, doubleOffset());
    }

    @Test
    void inCirclePointOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThatPoint(triangle.inCirclePoint())
            .hasX(1 - Math.sqrt(2) / 2)
            .hasY(Math.sqrt(2) / 2);
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfTriangleWithA0_5B1_5C2_5() {
        TestTriangle triangle = new TestTriangle(
            new TestPoint(0.5), new TestPoint(1.5), new TestPoint(2.5)
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
        assertThatTriangle(new TestTriangle(
            new TestPoint(1), new TestPoint(1, 2), new TestPoint(2)
        )).isValid();
    }

    @Test
    void isValidWithLineValues() {
        assertThatTriangle(new TestTriangle(
            new TestPoint(0), new TestPoint(1), new TestPoint(2)
        )).isInvalid();
    }

    @Test
    void isValidWithZeroValues() {
        assertThatTriangle(new TestTriangle(
            new TestPoint(0), new TestPoint(0), new TestPoint(0)
        )).isInvalid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatTriangle(new TestTriangle(
            new TestPoint(Double.POSITIVE_INFINITY),
            new TestPoint(Double.POSITIVE_INFINITY),
            new TestPoint(Double.POSITIVE_INFINITY)
        )).isInvalid();
    }

    @Test
    void moveOfTriangleWithXYWithXY() {
        assertThatTriangle(
            new TestTriangle(new TestPoint(0), new TestPoint(1, 0), new TestPoint(1))
                .move(1)
        ).hasA(new TestPoint(1d))
            .hasB(new TestPoint(2d, 1d))
            .hasC(new TestPoint(2d));
    }

    @Test
    void moveOfTriangleWithXYWithXAndY() {
        assertThatTriangle(
            new TestTriangle(new TestPoint(0), new TestPoint(1, 0), new TestPoint(1))
                .move(1, 1)
        ).hasA(new TestPoint(1d))
            .hasB(new TestPoint(2d, 1d))
            .hasC(new TestPoint(2d));
    }

    @Test
    void moveOfTriangleWithXYWithVector() {
        assertThatTriangle(
            new TestTriangle(new TestPoint(0), new TestPoint(1, 0), new TestPoint(1))
                .move(new TestVector(1))
        ).hasA(new TestPoint(1d))
            .hasB(new TestPoint(2d, 1d))
            .hasC(new TestPoint(2d));
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithoutCenterWithPiHalf() {
        assertThatTriangle(
            new TestTriangle(
                new TestPoint(0, 0),
                new TestPoint(0, 1),
                new TestPoint(1, 1)
            ).rotate(Math.PI / 2)
        ).hasAWithZeroXY()
            .hasB(new TestPoint(-1d, 6.123233995736766e-17))
            .hasCCloseTo(new TestPoint(-1d, 1d));
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithCenterXY1WithPiHalf() {
        assertThatTriangle(
            new TestTriangle(
                new TestPoint(0, 0),
                new TestPoint(0, 1),
                new TestPoint(1, 1)
            ).rotate(new TestPoint(1), Math.PI / 2)
        ).hasA(new TestPoint(2d, 0d))
            .hasBCloseTo(new TestPoint(1d, 0d))
            .hasC(new TestPoint(1d, 1d));
    }

    @Test
    void copyOfTriangleWithA2B3C4() {
        assertCopyable(new TestTriangle(
            new TestPoint(1, 2), new TestPoint(3, 4), new TestPoint(5, 6)
        ));
    }

    // endregion

    // region override

    @Test
    void equalsOfTriangleWithA2B3C4() {
        assertThatTriangle(new TestTriangle(new TestPoint(2), new TestPoint(3), new TestPoint(4)))
            .isEqualTo(new TestTriangle(new TestPoint(2), new TestPoint(3), new TestPoint(4)))
            .isNotEqualTo(new TestTriangle(new TestPoint(3), new TestPoint(2), new TestPoint(4)));
    }

    @Test
    void hashCodeOfTriangleWithA2B3C4() {
        assertThat(new TestTriangle(
            new TestPoint(2), new TestPoint(3), new TestPoint(4)
        ).hashCode()).isEqualTo(1048672);
    }

    @Test
    void toStringOfTriangleWithA2B3C4() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(2d), new TestPoint(3d), new TestPoint(4d)
        );
        assertThatTriangle(triangle).hasToString("2.0|2.0 3.0|3.0 4.0|4.0");
    }

    @Test
    void compareToOfTriangleWithA2B3C4() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(0), new TestPoint(1, 0), new TestPoint(1)
        );
        assertThatTriangle(triangle)
            .isEqualByComparingTo(new TestTriangle(
                new TestPoint(0), new TestPoint(1, 0), new TestPoint(1)
            ))
            .isLessThan(new TestTriangle(
                new TestPoint(-1), new TestPoint(1, 0), new TestPoint(1)
            ))
            .isGreaterThan(new TestTriangle(
                new TestPoint(0.5, 1), new TestPoint(1, 0.5), new TestPoint(1)
            ));
    }

    @Test
    void serializable() {
        assertSerializable(
            new TestTriangle(new TestPoint(), new TestPoint(), new TestPoint()),
            TestTriangle.class
        );
    }

    // endregion
}
