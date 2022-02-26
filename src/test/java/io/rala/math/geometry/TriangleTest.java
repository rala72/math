package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestTriangle;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class TriangleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithA2B3C4() {
        assertTriangle(
            new TestTriangle(new TestPoint(2), new TestPoint(3), new TestPoint(4)),
            new TestPoint(2), new TestPoint(3), new TestPoint(4)
        );
    }

    @Test
    void createAndSetA() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(), new TestPoint()
        );
        assertTriangle(triangle,
            new TestPoint(), new TestPoint(), new TestPoint()
        );
        triangle.setA(new TestPoint(1));
        assertTriangle(triangle,
            new TestPoint(1), new TestPoint(), new TestPoint()
        );
    }

    @Test
    void createAndSetB() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(), new TestPoint()
        );
        assertTriangle(triangle,
            new TestPoint(), new TestPoint(), new TestPoint()
        );
        triangle.setB(new TestPoint(2));
        assertTriangle(triangle,
            new TestPoint(), new TestPoint(2), new TestPoint()
        );
    }

    @Test
    void createAndSetC() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(), new TestPoint()
        );
        assertTriangle(triangle,
            new TestPoint(), new TestPoint(), new TestPoint()
        );
        triangle.setC(new TestPoint(3));
        assertTriangle(triangle,
            new TestPoint(), new TestPoint(), new TestPoint(3)
        );
    }

    // endregion

    // region edges and altitudes

    @Test
    void edgesOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(1), new TestPoint(2)
        );
        assertLineSegment(
            triangle.edgeA(),
            new TestPoint(1), new TestPoint(2)
        );
        assertLineSegment(
            triangle.edgeB(),
            new TestPoint(), new TestPoint(2)
        );
        assertLineSegment(
            triangle.edgeC(),
            new TestPoint(), new TestPoint(1)
        );
    }

    @Test
    void altitudesOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(),
            new TestPoint(0d, 1d),
            new TestPoint(1d, 1d)
        );

        LineSegment<Number> altitudeA = triangle.altitudeA();
        assertThat(altitudeA.length()).isEqualTo(1d);
        assertThat(altitudeA.getA()).isEqualTo(triangle.getA());
        assertThat(altitudeA.getB()).isEqualTo(triangle.getB());

        LineSegment<Number> altitudeB = triangle.altitudeB();
        assertThat(altitudeB.length()).isEqualTo(Math.sqrt(2) / 2);
        assertThat(altitudeB.getA()).isEqualTo(triangle.getB());
        assertThat(altitudeB.getB()).isEqualTo(triangle.edgeB().halvingPoint());

        LineSegment<Number> altitudeC = triangle.altitudeC();
        assertThat(altitudeC.length()).isEqualTo(1d);
        assertThat(altitudeC.getA()).isEqualTo(triangle.getC());
        assertThat(altitudeC.getB()).isEqualTo(triangle.getB());
    }

    @Test
    void altitudesOfTriangleWithA00B10C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(),
            new TestPoint(1d, 0d),
            new TestPoint(1d, 1d)
        );

        LineSegment<Number> altitudeA = triangle.altitudeA();
        assertThat(altitudeA.length()).isEqualTo(1d);
        assertThat(altitudeA.getA()).isEqualTo(triangle.getA());
        assertThat(altitudeA.getB()).isEqualTo(triangle.getB());

        LineSegment<Number> altitudeB = triangle.altitudeB();
        assertThat(altitudeB.length()).isEqualTo(Math.sqrt(2) / 2);
        assertThat(altitudeB.getA()).isEqualTo(triangle.getB());
        assertThat(altitudeB.getB()).isEqualTo(triangle.edgeB().halvingPoint());

        LineSegment<Number> altitudeC = triangle.altitudeC();
        assertThat(altitudeC.length()).isEqualTo(1d);
        assertThat(altitudeC.getA()).isEqualTo(triangle.getC());
        assertThat(altitudeC.getB()).isEqualTo(triangle.getB());
    }

    // endregion

    // region angles

    @Test
    void angleAlpha() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(0, 0), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThat(triangle.angleAlpha().doubleValue()).isCloseTo(Math.PI / 4, offset(GeometryAssertions.DELTA));
    }

    @Test
    void angleBeta() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(0, 0), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThat(triangle.angleBeta().doubleValue()).isCloseTo(Math.PI / 2, offset(GeometryAssertions.DELTA));
    }

    @Test
    void angleGamma() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(0, 0), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThat(triangle.angleGamma().doubleValue()).isCloseTo(Math.PI / 4, offset(GeometryAssertions.DELTA));
    }

    @Test
    void angleSum() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(0, 0), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThat(triangle.angleAlpha().doubleValue() +
            triangle.angleBeta().doubleValue() +
            triangle.angleGamma().doubleValue()).isEqualTo(Math.PI);
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThat(triangle.area().doubleValue()).isCloseTo(0.5, offset(GeometryAssertions.DELTA));
    }

    @Test
    void circumferenceOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThat(triangle.circumference()).isEqualTo(2 + Math.sqrt(2));
    }

    // endregion

    // region centroid and orthoCenter

    @Test
    void centroidOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertPoint(triangle.centroid(), 1d / 3, 2d / 3);
    }

    @Test
    void orthoCenterOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0d, 1d), new TestPoint(1d, 1d)
        );
        assertThat(triangle.orthoCenter()).isEqualTo(triangle.getB());
    }

    // endregion

    // region circumCircle and inCircle

    @Test
    void circumCircleOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertCircle(
            triangle.circumCircle(),
            new TestPoint(0.5, -0.5),
            Math.sqrt(2) / 2
        );
    }

    @Test
    void inCircleOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertCircle(
            triangle.inCircle(),
            new TestPoint(1 - Math.sqrt(2) / 2, Math.sqrt(2) / 2),
            1 - Math.sqrt(2) / 2
        );
    }

    @Test
    void circumCircleRadiusOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThat(triangle.circumCircleRadius().doubleValue()).isCloseTo(Math.sqrt(2) / 2, offset(GeometryAssertions.DELTA));
    }

    @Test
    void circumCirclePointOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThat(triangle.circumCirclePoint()).isEqualTo(new TestPoint(0.5, -0.5));
    }

    @Test
    void inCircleRadiusOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertThat(triangle.inCircleRadius().doubleValue()).isCloseTo(1 - Math.sqrt(2) / 2, offset(GeometryAssertions.DELTA));
    }

    @Test
    void inCirclePointOfTriangleWithA00B01C11() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(), new TestPoint(0, 1), new TestPoint(1, 1)
        );
        assertPoint(
            triangle.inCirclePoint(),
            1 - Math.sqrt(2) / 2,
            Math.sqrt(2) / 2
        );
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
        assertThat(triangle.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithPositiveValues() {
        assertThat(new TestTriangle(
            new TestPoint(1), new TestPoint(1, 2), new TestPoint(2)
        ).isValid()).isTrue();
    }

    @Test
    void isValidWithLineValues() {
        assertThat(new TestTriangle(
            new TestPoint(0), new TestPoint(1), new TestPoint(2)
        ).isValid()).isFalse();
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new TestTriangle(
            new TestPoint(0), new TestPoint(0), new TestPoint(0)
        ).isValid()).isFalse();
    }

    @Test
    void isValidWithInfValues() {
        assertThat(new TestTriangle(
            new TestPoint(Double.POSITIVE_INFINITY),
            new TestPoint(Double.POSITIVE_INFINITY),
            new TestPoint(Double.POSITIVE_INFINITY)
        ).isValid()).isFalse();
    }

    @Test
    void moveOfTriangleWithXYWithXY() {
        assertTriangle(
            new TestTriangle(new TestPoint(0), new TestPoint(1, 0), new TestPoint(1))
                .move(1),
            new TestPoint(1), new TestPoint(2, 1), new TestPoint(2)
        );
    }

    @Test
    void moveOfTriangleWithXYWithXAndY() {
        assertTriangle(
            new TestTriangle(new TestPoint(0), new TestPoint(1, 0), new TestPoint(1))
                .move(1, 1),
            new TestPoint(1), new TestPoint(2, 1), new TestPoint(2)
        );
    }

    @Test
    void moveOfTriangleWithXYWithVector() {
        assertTriangle(
            new TestTriangle(new TestPoint(0), new TestPoint(1, 0), new TestPoint(1))
                .move(new TestVector(1)),
            new TestPoint(1), new TestPoint(2, 1), new TestPoint(2)
        );
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithoutCenterWithPiHalf() {
        assertTriangle(
            new TestTriangle(
                new TestPoint(0, 0),
                new TestPoint(0, 1),
                new TestPoint(1, 1)
            ).rotate(Math.PI / 2),
            new TestPoint(),
            new TestPoint(-1, 6.123233995736766E-17),
            new TestPoint(-0.9999999999999999, 1.0)
        );
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithCenterXY1WithPiHalf() {
        assertTriangle(
            new TestTriangle(
                new TestPoint(0, 0),
                new TestPoint(0, 1),
                new TestPoint(1, 1)
            ).rotate(new TestPoint(1), Math.PI / 2),
            new TestPoint(2, 0),
            new TestPoint(0.9999999999999999, 0),
            new TestPoint(1, 1)
        );
    }

    @Test
    void copyOfTriangleWithA2B3C4() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(1, 2), new TestPoint(3, 4), new TestPoint(5, 6)
        );
        assertThat(triangle.copy()).isEqualTo(triangle);
    }

    // endregion

    // region override

    @Test
    void equalsOfTriangleWithA2B3C4() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(2), new TestPoint(3), new TestPoint(4)
        );
        assertThat(new TestTriangle(new TestPoint(2), new TestPoint(3), new TestPoint(4))).isEqualTo(triangle);
        assertThat(new TestTriangle(new TestPoint(3), new TestPoint(2), new TestPoint(4))).isNotEqualTo(triangle);
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
        assertThat(triangle).hasToString("2.0|2.0 3.0|3.0 4.0|4.0");
    }

    @Test
    void compareToOfTriangleWithA2B3C4() {
        Triangle<Number> triangle = new TestTriangle(
            new TestPoint(0), new TestPoint(1, 0), new TestPoint(1)
        );
        assertThat(triangle)
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
