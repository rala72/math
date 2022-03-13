package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalLineSegmentTest {
    // region constructors, getter and setter

    @Test
    void constructorWithBParameter() {
        assertLineSegment(
            new BigDecimalLineSegment(new BigDecimalPoint(BigDecimal.ONE)),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void constructorWithBParameterAndMathContext5() {
        assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE),
                new MathContext(5)
            ),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void constructorWithEqualABParameter() {
        assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(2d))
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void constructorWithEqualABParameterAndMathContext5() {
        assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new MathContext(5)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void constructorWithDifferentABParameter() {
        assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(3d), BigDecimal.valueOf(3d))
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d))
        );
    }

    @Test
    void constructorWithDifferentABParameterAndMathContext5() {
        assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(3d), BigDecimal.valueOf(3d)),
                new MathContext(5)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d))
        );
    }

    @Test
    void createAndSetA() {
        LineSegment<BigDecimal> lineSegment =
            new BigDecimalLineSegment(new BigDecimalPoint());
        assertLineSegment(lineSegment, new BigDecimalPoint());
        lineSegment.setA(new BigDecimalPoint(BigDecimal.ONE));
        assertLineSegment(lineSegment,
            new BigDecimalPoint(BigDecimal.ONE), new BigDecimalPoint()
        );
    }

    @Test
    void createAndSetB() {
        LineSegment<BigDecimal> lineSegment =
            new BigDecimalLineSegment(new BigDecimalPoint());
        assertLineSegment(lineSegment, new BigDecimalPoint());
        lineSegment.setB(new BigDecimalPoint(BigDecimal.valueOf(2d)));
        assertLineSegment(lineSegment,
            new BigDecimalPoint(), new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    // endregion

    // region length, halvingPoint and distributionPoint

    @Test
    void lengthOfLineSegmentWithTwoEqualPoints() {
        assertThat(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
        ).length()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void lengthOfLineSegmentWithInverseParameters() {
        assertThat(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.ONE)
        ).length()).isEqualTo(BigDecimal.valueOf(Math.sqrt(2d)).round(CONTEXT));
    }

    @Test
    void lengthOfLineSegmentWithTwoDifferenceEach() {
        assertThat(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(3d), BigDecimal.valueOf(4d)),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
        ).length()).isEqualTo(BigDecimal.valueOf(2d * Math.sqrt(2d)).round(CONTEXT).stripTrailingZeros());
    }

    @Test
    void halvingPointOfLineSegmentWithPXY0AndPXY1() {
        assertPoint(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).halvingPoint(),
            BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.5)
        );
    }

    @Test
    void distributionPointComma25OfLineSegmentWithPXY0AndPXY1() {
        assertPoint(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).distributionPoint(BigDecimal.valueOf(0.25)),
            BigDecimal.valueOf(0.25), BigDecimal.valueOf(0.25)
        );
    }

    @Test
    void distributionPointComma5OfLineSegmentWithPXY0AndPXY1() {
        assertPoint(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).distributionPoint(BigDecimal.valueOf(0.5)),
            BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.5)
        );
    }

    @Test
    void distributionPointComma75OfLineSegmentWithPXY0AndPXY1() {
        assertPoint(
            new BigDecimalLineSegment(
                new BigDecimalPoint(), new BigDecimalPoint(BigDecimal.ONE)
            ).distributionPoint(BigDecimal.valueOf(0.75)),
            BigDecimal.valueOf(0.75), BigDecimal.valueOf(0.75)
        );
    }

    // endregion

    // region flip, toLine

    @Test
    void flipWithAXY0AndBXY1() {
        assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ).flip(),
            new BigDecimalPoint(BigDecimal.ONE), new BigDecimalPoint(BigDecimal.ZERO)
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY0AndBXY1() {
        assertLine(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ).toLine(),
            BigDecimal.ONE, BigDecimal.ZERO
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX1Y0() {
        assertLine(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO)
            ).toLine(),
            null, BigDecimal.ONE
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX0Y1() {
        assertLine(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE)
            ).toLine(),
            BigDecimal.ZERO, BigDecimal.ONE
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX2Y3() {
        assertLine(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d))
            ).toLine(),
            BigDecimal.valueOf(2), BigDecimal.ONE.negate()
        );
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
        assertThat(lineSegment.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new BigDecimalLineSegment(new BigDecimalPoint()).isValid()).isTrue();
    }

    @Test
    void moveOfLineSegmentWithAndBWithXY() {
        assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithXAndY() {
        assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(BigDecimal.ONE, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithVector() {
        assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(new BigDecimalVector(BigDecimal.ONE)),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithoutCenterWithPiHalf() {
        assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
            ).rotate(BigDecimal.valueOf(Math.PI / 2d)),
            new BigDecimalPoint(),
            new BigDecimalPoint(
                BigDecimal.valueOf(-2d),
                BigDecimal.valueOf(1.0000000000000002)
            )
        );
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithCenterXY1WithPiHalf() {
        assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
            ).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI / 2d)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE)
        );
    }

    @Test
    void copyOfLineSegmentWithTwoPoints() {
        LineSegment<BigDecimal> lineSegment = new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d), BigDecimal.valueOf(4d))
        );
        assertThat(lineSegment.copy()).isEqualTo(lineSegment);
    }

    // endregion

    // region override

    @Test
    void equalsOfLineSegmentWithTwoPoints() {
        assertThat(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d))
        )).isEqualTo(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d))
        )).isNotEqualTo(new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
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
        assertThat(lineSegment).hasToString("2.0|2.0 3.0|3.0");
    }

    @Test
    void compareToOfLineSegmentWithTwoPoints() {
        LineSegment<BigDecimal> lineSegment = new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d))
        );
        assertThat(lineSegment)
            .isEqualByComparingTo(new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(3d))
            ))
            .isLessThan(new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(3d)),
                new BigDecimalPoint(BigDecimal.valueOf(4d))
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
