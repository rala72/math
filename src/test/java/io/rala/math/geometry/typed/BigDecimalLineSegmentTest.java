package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalLineSegmentTest {
    // region constructors, getter and setter

    @Test
    void constructorWithBParameter() {
        assertThatLineSegment(
            new BigDecimalLineSegment(new BigDecimalPoint(BigDecimal.ONE))
        ).hasAWithZeroXY().hasB(new BigDecimalPoint(BigDecimal.ONE));
    }

    @Test
    void constructorWithBParameterAndMathContext5() {
        assertThatLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE),
                new MathContext(5)
            )
        ).hasAWithZeroXY().hasB(new BigDecimalPoint(BigDecimal.ONE));
    }

    @Test
    void constructorWithEqualABParameter() {
        assertThatLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2)),
                new BigDecimalPoint(BigDecimal.valueOf(2))
            )
        ).hasA(new BigDecimalPoint(BigDecimal.valueOf(2)))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(2)));
    }

    @Test
    void constructorWithEqualABParameterAndMathContext5() {
        assertThatLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2)),
                new BigDecimalPoint(BigDecimal.valueOf(2)),
                new MathContext(5)
            )
        ).hasA(new BigDecimalPoint(BigDecimal.valueOf(2)))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(2)));
    }

    @Test
    void constructorWithDifferentABParameter() {
        assertThatLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.valueOf(2)),
                new BigDecimalPoint(BigDecimal.valueOf(3), BigDecimal.valueOf(3))
            )
        ).hasA(new BigDecimalPoint(BigDecimal.valueOf(2)))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(3)));
    }

    @Test
    void constructorWithDifferentABParameterAndMathContext5() {
        assertThatLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.valueOf(2)),
                new BigDecimalPoint(BigDecimal.valueOf(3), BigDecimal.valueOf(3)),
                new MathContext(5)
            )
        ).hasA(new BigDecimalPoint(BigDecimal.valueOf(2)))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(3)));
    }

    @Test
    void createAndSetA() {
        LineSegment<BigDecimal> lineSegment =
            new BigDecimalLineSegment(new BigDecimalPoint());
        assertThatLineSegment(lineSegment).hasAWithZeroXY().hasB(new BigDecimalPoint());
        lineSegment.setA(new BigDecimalPoint(BigDecimal.ONE));
        assertThatLineSegment(lineSegment)
            .hasA(new BigDecimalPoint(BigDecimal.ONE))
            .hasBWithZeroXY();
    }

    @Test
    void createAndSetB() {
        LineSegment<BigDecimal> lineSegment =
            new BigDecimalLineSegment(new BigDecimalPoint());
        assertThatLineSegment(lineSegment).hasAWithZeroXY().hasBWithZeroXY();
        lineSegment.setB(new BigDecimalPoint(BigDecimal.valueOf(2)));
        assertThatLineSegment(lineSegment)
            .hasAWithZeroXY()
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(2)));
    }

    // endregion

    // region length, halvingPoint and distributionPoint

    @Test
    void lengthOfLineSegmentWithTwoEqualPoints() {
        assertThatLineSegment(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2))
        )).hasLength(BigDecimal.ZERO);
    }

    @Test
    void lengthOfLineSegmentWithInverseParameters() {
        assertThatLineSegment(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.ONE)
        )).hasLength(BigDecimal.valueOf(Math.sqrt(2d)).round(CONTEXT));
    }

    @Test
    void lengthOfLineSegmentWithTwoDifferenceEach() {
        assertThatLineSegment(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(3), BigDecimal.valueOf(4)),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2))
        )).hasLength(BigDecimal.valueOf(2d * Math.sqrt(2d)).round(CONTEXT).stripTrailingZeros());
    }

    @Test
    void halvingPointOfLineSegmentWithPXY0AndPXY1() {
        assertThatPoint(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).halvingPoint()
        ).hasX(BigDecimal.valueOf(0.5))
            .hasY(BigDecimal.valueOf(0.5));
    }

    @Test
    void distributionPointComma25OfLineSegmentWithPXY0AndPXY1() {
        assertThatPoint(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).distributionPoint(BigDecimal.valueOf(0.25))
        ).hasX(BigDecimal.valueOf(0.25))
            .hasY(BigDecimal.valueOf(0.25));
    }

    @Test
    void distributionPointComma5OfLineSegmentWithPXY0AndPXY1() {
        assertThatPoint(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).distributionPoint(BigDecimal.valueOf(0.5))
        ).hasX(BigDecimal.valueOf(0.5))
            .hasY(BigDecimal.valueOf(0.5));
    }

    @Test
    void distributionPointComma75OfLineSegmentWithPXY0AndPXY1() {
        assertThatPoint(
            new BigDecimalLineSegment(
                new BigDecimalPoint(), new BigDecimalPoint(BigDecimal.ONE)
            ).distributionPoint(BigDecimal.valueOf(0.75))
        ).hasX(BigDecimal.valueOf(0.75))
            .hasY(BigDecimal.valueOf(0.75));
    }

    // endregion

    // region flip, toLine

    @Test
    void flipWithAXY0AndBXY1() {
        assertThatLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ).flip()
        ).hasA(new BigDecimalPoint(BigDecimal.ONE)).hasBWithZeroXY();
    }

    @Test
    void toLineOfLineSegmentWithAXY0AndBXY1() {
        assertThatLine(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ).toLine()
        ).hasM(BigDecimal.ONE).hasB(BigDecimal.ZERO);
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX1Y0() {
        assertThatLine(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO)
            ).toLine()
        ).hasM(null).hasB(BigDecimal.ONE);
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX0Y1() {
        assertThatLine(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE)
            ).toLine()
        ).hasM(BigDecimal.ZERO).hasB(BigDecimal.ONE);
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX2Y3() {
        assertThatLine(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.valueOf(3))
            ).toLine()
        ).hasM(BigDecimal.valueOf(2)).hasB(BigDecimal.ONE.negate());
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfLineSegmentWithA0_5B1_5() {
        BigDecimalLineSegment lineSegment = new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(0.5)),
            new BigDecimalPoint(BigDecimal.valueOf(1.5))
        );
        IntegerArithmetic integerArithmetic = new IntegerArithmetic();
        LineSegment<Integer> result = new LineSegment<>(integerArithmetic,
            new Point<>(integerArithmetic, 0),
            new Point<>(integerArithmetic, 1)
        );
        assertThatLineSegment(lineSegment.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThatLineSegment(new BigDecimalLineSegment(new BigDecimalPoint())).isValid();
    }

    @Test
    void moveOfLineSegmentWithAndBWithXY() {
        assertThatLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(BigDecimal.ONE)
        ).hasA(new BigDecimalPoint(BigDecimal.ONE))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(2)));
    }

    @Test
    void moveOfLineSegmentWithAndBWithXAndY() {
        assertThatLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(BigDecimal.ONE, BigDecimal.ONE)
        ).hasA(new BigDecimalPoint(BigDecimal.ONE))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(2)));
    }

    @Test
    void moveOfLineSegmentWithAndBWithVector() {
        assertThatLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(new BigDecimalVector(BigDecimal.ONE))
        ).hasA(new BigDecimalPoint(BigDecimal.ONE))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(2)));
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithoutCenterWithPiHalf() {
        assertThatLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2))
            ).rotate(BigDecimal.valueOf(Math.PI / 2d))
        ).hasAWithZeroXY()
            .hasB(new BigDecimalPoint(
                BigDecimal.valueOf(-2),
                BigDecimal.valueOf(1)
            ));
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithCenterXY1WithPiHalf() {
        assertThatLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2))
            ).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI / 2d)
            )
        ).hasA(new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.ZERO))
            .hasB(new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE));
    }

    @Test
    void copyOfLineSegmentWithTwoPoints() {
        assertCopyable(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(3), BigDecimal.valueOf(4))
        ));
    }

    // endregion

    // region override

    @Test
    void equalsOfLineSegmentWithTwoPoints() {
        assertThatLineSegment(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(3))
        )).isEqualTo(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(3))
        )).isNotEqualTo(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(3)),
            new BigDecimalPoint(BigDecimal.valueOf(2))
        ));
    }

    @Test
    void hashCodeOfLineSegmentWithTwoPoints() {
        assertThat(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d))
        ).hashCode()).isEqualTo(677537);
    }

    @Test
    void toStringOfLineSegmentWithTwoPoints() {
        LineSegment<BigDecimal> lineSegment = new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d))
        );
        assertThatLineSegment(lineSegment).hasToString("2.0|2.0 3.0|3.0");
    }

    @Test
    void compareToOfLineSegmentWithTwoPoints() {
        LineSegment<BigDecimal> lineSegment = new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(3))
        );
        assertThatLineSegment(lineSegment)
            .isEqualByComparingTo(new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2)),
                new BigDecimalPoint(BigDecimal.valueOf(3))
            ))
            .isLessThan(new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(3)),
                new BigDecimalPoint(BigDecimal.valueOf(4))
            ))
            .isGreaterThan(new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ONE)
            ));
    }

    @Test
    void serializable() {
        assertSerializable(
            new BigDecimalLineSegment(new BigDecimalPoint()),
            BigDecimalLineSegment.class
        );
    }

    // endregion
}
