package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Triangle;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

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
        assertThat(altitudeA.length()).isEqualTo(1d);
        assertThat(altitudeA.getA()).isEqualTo(triangle.getA());
        assertThat(altitudeA.getB()).isEqualTo(triangle.getB());

        LineSegment<Double> altitudeB = triangle.altitudeB();
        assertThat(altitudeB.length()).isEqualTo(Math.sqrt(2d) / 2d);
        assertThat(altitudeB.getA()).isEqualTo(triangle.getB());
        assertThat(altitudeB.getB()).isEqualTo(triangle.edgeB().halvingPoint());

        LineSegment<Double> altitudeC = triangle.altitudeC();
        assertThat(altitudeC.length()).isEqualTo(1d);
        assertThat(altitudeC.getA()).isEqualTo(triangle.getC());
        assertThat(altitudeC.getB()).isEqualTo(triangle.getB());
    }

    @Test
    void altitudesOfTriangleWithA00B10C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(),
            new DoublePoint(1d, 0d),
            new DoublePoint(1d, 1d)
        );

        LineSegment<Double> altitudeA = triangle.altitudeA();
        assertThat(altitudeA.length()).isEqualTo(1d);
        assertThat(altitudeA.getA()).isEqualTo(triangle.getA());
        assertThat(altitudeA.getB()).isEqualTo(triangle.getB());

        LineSegment<Double> altitudeB = triangle.altitudeB();
        assertThat(altitudeB.length()).isEqualTo(Math.sqrt(2d) / 2d);
        assertThat(altitudeB.getA()).isEqualTo(triangle.getB());
        assertThat(altitudeB.getB()).isEqualTo(triangle.edgeB().halvingPoint());

        LineSegment<Double> altitudeC = triangle.altitudeC();
        assertThat(altitudeC.length()).isEqualTo(1d);
        assertThat(altitudeC.getA()).isEqualTo(triangle.getC());
        assertThat(altitudeC.getB()).isEqualTo(triangle.getB());
    }

    // endregion

    // region angles

    @Test
    void angleAlpha() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d, 0d), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThat(triangle.angleAlpha()).isCloseTo(Math.PI / 4d, offset(GeometryAssertions.DELTA));
    }

    @Test
    void angleBeta() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d, 0d), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThat(triangle.angleBeta()).isCloseTo(Math.PI / 2d, offset(GeometryAssertions.DELTA));
    }

    @Test
    void angleGamma() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d, 0d), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThat(triangle.angleGamma()).isCloseTo(Math.PI / 4d, offset(GeometryAssertions.DELTA));
    }

    @Test
    void angleSum() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d, 0d), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThat(triangle.angleAlpha() +
            triangle.angleBeta() +
            triangle.angleGamma()).isEqualTo(Math.PI);
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThat(triangle.area()).isCloseTo(0.5, offset(GeometryAssertions.DELTA));
    }

    @Test
    void circumferenceOfTriangleWithA00B01C11() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(), new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
        assertThat(triangle.circumference()).isEqualTo(2d + Math.sqrt(2d));
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
        assertThat(triangle.orthoCenter()).isEqualTo(triangle.getB());
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
            new DoublePoint(0.5, -0.5),
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
        assertThat(triangle.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithPositiveValues() {
        assertThat(new DoubleTriangle(
            new DoublePoint(1d), new DoublePoint(1d, 2d), new DoublePoint(2d)
        ).isValid()).isTrue();
    }

    @Test
    void isValidWithLineValues() {
        assertThat(new DoubleTriangle(
            new DoublePoint(0d), new DoublePoint(1d), new DoublePoint(2d)
        ).isValid()).isFalse();
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new DoubleTriangle(
            new DoublePoint(0d), new DoublePoint(0d), new DoublePoint(0d)
        ).isValid()).isFalse();
    }

    @Test
    void isValidWithInfValues() {
        assertThat(new DoubleTriangle(
            new DoublePoint(Double.POSITIVE_INFINITY),
            new DoublePoint(Double.POSITIVE_INFINITY),
            new DoublePoint(Double.POSITIVE_INFINITY)
        ).isValid()).isFalse();
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
            new DoublePoint(-0.9999999999999999, 1d)
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
            new DoublePoint(0.9999999999999999, 0d),
            new DoublePoint(1d, 1d)
        );
    }

    @Test
    void copyOfTriangleWithA2B3C4() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(1d, 2d), new DoublePoint(3d, 4d), new DoublePoint(5d, 6d)
        );
        assertThat(triangle.copy()).isEqualTo(triangle);
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
        assertThat(new DoubleTriangle(
            new DoublePoint(2d),
            new DoublePoint(3d),
            new DoublePoint(4d)
        )).isEqualTo(triangle);
        assertThat(new DoubleTriangle(
            new DoublePoint(3d),
            new DoublePoint(2d),
            new DoublePoint(4d)
        )).isNotEqualTo(triangle);
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
        assertThat(triangle.toString()).isEqualTo("2.0|2.0 3.0|3.0 4.0|4.0");
    }

    @Test
    void compareToOfTriangleWithA2B3C4() {
        Triangle<Double> triangle = new DoubleTriangle(
            new DoublePoint(0d), new DoublePoint(1d, 0d), new DoublePoint(1d)
        );
        assertThat(triangle.compareTo(new DoubleTriangle(
            new DoublePoint(0d), new DoublePoint(1d, 0d), new DoublePoint(1d)
        ))).isEqualTo(0);
        assertThat(triangle.compareTo(new DoubleTriangle(
            new DoublePoint(-1d), new DoublePoint(1d, 0d), new DoublePoint(1d)
        ))).isEqualTo(-1);
        assertThat(triangle.compareTo(new DoubleTriangle(
            new DoublePoint(0.5, 1d), new DoublePoint(1d, 0.5), new DoublePoint(1d)
        ))).isEqualTo(1);
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
