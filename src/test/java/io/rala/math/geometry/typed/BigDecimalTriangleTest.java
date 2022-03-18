package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Triangle;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static io.rala.math.testUtils.assertion.utils.OffsetUtils.bigDecimalOffset;
import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalTriangleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithA2B3C4() {
        assertThatTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.valueOf(2)),
                new BigDecimalPoint(BigDecimal.valueOf(3)),
                new BigDecimalPoint(BigDecimal.valueOf(4))
            )
        ).hasA(new BigDecimalPoint(BigDecimal.valueOf(2)))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(3)))
            .hasC(new BigDecimalPoint(BigDecimal.valueOf(4)));
    }

    @Test
    void constructorWithA2B3C4AndMathContext5() {
        assertThatTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.valueOf(2)),
                new BigDecimalPoint(BigDecimal.valueOf(3)),
                new BigDecimalPoint(BigDecimal.valueOf(4)),
                new MathContext(5)
            )
        ).hasA(new BigDecimalPoint(BigDecimal.valueOf(2)))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(3)))
            .hasC(new BigDecimalPoint(BigDecimal.valueOf(4)));
    }

    @Test
    void createAndSetA() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(), new BigDecimalPoint(), new BigDecimalPoint()
        );
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasBWithZeroXY()
            .hasCWithZeroXY();
        triangle.setA(new BigDecimalPoint(BigDecimal.ONE));
        assertThatTriangle(triangle)
            .hasA(new BigDecimalPoint(BigDecimal.ONE))
            .hasBWithZeroXY()
            .hasCWithZeroXY();
    }

    @Test
    void createAndSetB() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(), new BigDecimalPoint(), new BigDecimalPoint()
        );
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasBWithZeroXY()
            .hasCWithZeroXY();
        triangle.setB(new BigDecimalPoint(BigDecimal.valueOf(2)));
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(2)))
            .hasCWithZeroXY();
    }

    @Test
    void createAndSetC() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(), new BigDecimalPoint(), new BigDecimalPoint()
        );
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasBWithZeroXY()
            .hasCWithZeroXY();
        triangle.setC(new BigDecimalPoint(BigDecimal.valueOf(3)));
        assertThatTriangle(triangle)
            .hasAWithZeroXY()
            .hasBWithZeroXY()
            .hasC(new BigDecimalPoint(BigDecimal.valueOf(3)));
    }

    // endregion

    // region edges and altitudes

    @Test
    void edgesOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2))
        );
        assertThatLineSegment(triangle.edgeA())
            .hasA(new BigDecimalPoint(BigDecimal.ONE))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(2)));
        assertThatLineSegment(triangle.edgeB())
            .hasAWithZeroXY()
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(2)));
        assertThatLineSegment(triangle.edgeC())
            .hasAWithZeroXY()
            .hasB(new BigDecimalPoint(BigDecimal.ONE));
    }

    @Test
    void altitudesOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );

        LineSegment<BigDecimal> altitudeA = triangle.altitudeA();
        assertThatLineSegment(altitudeA).hasLength(BigDecimal.ONE);
        assertThatPoint(altitudeA.getA()).isEqualTo(triangle.getA());
        assertThatPoint(altitudeA.getB()).isEqualTo(triangle.getB());

        LineSegment<BigDecimal> altitudeB = triangle.altitudeB();
        assertThatLineSegment(altitudeB).hasLength(BigDecimal.valueOf(2).sqrt(CONTEXT)
            .divide(BigDecimal.valueOf(2), CONTEXT));
        assertThatPoint(altitudeB.getA()).isEqualTo(triangle.getB());
        assertThatPoint(altitudeB.getB()).isEqualTo(triangle.edgeB().halvingPoint());

        LineSegment<BigDecimal> altitudeC = triangle.altitudeC();
        assertThatLineSegment(altitudeC).hasLength(BigDecimal.ONE);
        assertThatPoint(altitudeC.getA()).isEqualTo(triangle.getC());
        assertThatPoint(altitudeC.getB()).isEqualTo(triangle.getB());
    }

    @Test
    void altitudesOfTriangleWithA00B10C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );

        LineSegment<BigDecimal> altitudeA = triangle.altitudeA();
        assertThatLineSegment(altitudeA).hasLength(BigDecimal.ONE);
        assertThatPoint(altitudeA.getA()).isEqualTo(triangle.getA());
        assertThatPoint(altitudeA.getB()).isEqualTo(triangle.getB());

        LineSegment<BigDecimal> altitudeB = triangle.altitudeB();
        assertThatLineSegment(altitudeB).hasLengthCloseTo(BigDecimal.valueOf(Math.sqrt(2) / 2));
        assertThatPoint(altitudeB.getA()).isEqualTo(triangle.getB());
        assertThatPoint(altitudeB.getB()).isEqualTo(triangle.edgeB().halvingPoint());

        LineSegment<BigDecimal> altitudeC = triangle.altitudeC();
        assertThatLineSegment(altitudeC).hasLength(BigDecimal.ONE);
        assertThatPoint(altitudeC.getA()).isEqualTo(triangle.getC());
        assertThatPoint(altitudeC.getB()).isEqualTo(triangle.getB());
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
        assertThatTriangle(triangle).hasAngleAlpha(BigDecimal.valueOf(Math.PI / 4d));
    }

    @Test
    void angleBeta() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        assertThatTriangle(triangle).hasAngleBeta(BigDecimal.valueOf(Math.PI / 2d));
    }

    @Test
    void angleGamma() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        assertThatTriangle(triangle).hasAngleGamma(BigDecimal.valueOf(Math.PI / 4d));
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
            .add(triangle.angleGamma())
        ).isCloseTo(new BigDecimal(Math.PI), bigDecimalOffset());
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
        assertThatTriangle(triangle).hasAreaCloseTo(BigDecimal.valueOf(0.5));
    }

    @Test
    void circumferenceOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        assertThatTriangle(triangle).hasCircumference(BigDecimal.valueOf(2d + Math.sqrt(2d))
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
        assertThatPoint(triangle.centroid())
            .hasX(BigDecimal.valueOf(1 / 3d))
            .hasY(BigDecimal.valueOf(2d / 3d));
    }

    @Test
    void orthoCenterOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        assertThatPoint(triangle.orthoCenter()).isEqualTo(triangle.getB());
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
        assertThatCircle(triangle.circumCircle())
            .hasCenter(new BigDecimalPoint(BigDecimal.valueOf(0.5), BigDecimal.valueOf(-0.5)))
            .hasRadius(BigDecimal.valueOf(Math.sqrt(2d) / 2d));
    }

    @Test
    void inCircleOfTriangleWithA00B01C11() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
        assertThatCircle(triangle.inCircle())
            .hasCenterCloseTo(new BigDecimalPoint(
                BigDecimal.valueOf(1 - Math.sqrt(2d) / 2d),
                BigDecimal.valueOf(Math.sqrt(2d) / 2d)
            ))
            .hasRadius(BigDecimal.valueOf(1d - Math.sqrt(2d) / 2d));
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
        assertThatTriangle(triangle.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithPositiveValues() {
        assertThatTriangle(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(2))
        )).isValid();
    }

    @Test
    void isValidWithLineValues() {
        assertThatTriangle(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2))
        )).isInvalid();
    }

    @Test
    void isValidWithZeroValues() {
        assertThatTriangle(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO)
        )).isInvalid();
    }

    @Test
    void moveOfTriangleWithXYWithXY() {
        assertThatTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(BigDecimal.ONE)
        ).hasA(new BigDecimalPoint(BigDecimal.ONE))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.ONE))
            .hasC(new BigDecimalPoint(BigDecimal.valueOf(2)));
    }

    @Test
    void moveOfTriangleWithXYWithXAndY() {
        assertThatTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(BigDecimal.ONE, BigDecimal.ONE)
        ).hasA(new BigDecimalPoint(BigDecimal.ONE))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.ONE))
            .hasC(new BigDecimalPoint(BigDecimal.valueOf(2)));
    }

    @Test
    void moveOfTriangleWithXYWithVector() {
        assertThatTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(new BigDecimalVector(BigDecimal.ONE))
        ).hasA(new BigDecimalPoint(BigDecimal.ONE))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.ONE))
            .hasC(new BigDecimalPoint(BigDecimal.valueOf(2)));
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithoutCenterWithPiHalf() {
        assertThatTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
            ).rotate(BigDecimal.valueOf(Math.PI / 2d))
        ).hasAWithZeroXY()
            .hasB(new BigDecimalPoint(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(6.123233995736766e-17d)
            ))
            .hasCCloseTo(new BigDecimalPoint(
                BigDecimal.ONE.negate(),
                BigDecimal.ONE
            ));
    }

    @Test
    void rotateOfTriangleWithA00B01C11WithCenterXY1WithPiHalf() {
        assertThatTriangle(
            new BigDecimalTriangle(
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
            ).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI / 2d)
            )
        ).hasA(new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.ZERO))
            .hasBCloseTo(new BigDecimalPoint(BigDecimal.valueOf(1), BigDecimal.ZERO))
            .hasC(new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    void copyOfTriangleWithA2B3C4() {
        assertCopyable(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(3), BigDecimal.valueOf(4)),
            new BigDecimalPoint(BigDecimal.valueOf(5), BigDecimal.valueOf(6))
        ));
    }

    // endregion

    // region override

    @Test
    void equalsOfTriangleWithA2B3C4() {
        assertThatTriangle(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(3)),
            new BigDecimalPoint(BigDecimal.valueOf(4))
        )).isEqualTo(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(3)),
            new BigDecimalPoint(BigDecimal.valueOf(4))
        )).isNotEqualTo(new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.valueOf(3)),
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(4))
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
        assertThatTriangle(triangle).hasToString("2.0|2.0 3.0|3.0 4.0|4.0");
    }

    @Test
    void compareToOfTriangleWithA2B3C4() {
        Triangle<BigDecimal> triangle = new BigDecimalTriangle(
            new BigDecimalPoint(BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ONE)
        );
        assertThatTriangle(triangle)
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
