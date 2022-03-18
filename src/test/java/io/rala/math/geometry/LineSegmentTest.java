package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.geometry.TestLineSegment;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class LineSegmentTest {
    // region constructors, getter and setter

    @Test
    void constructorWithBParameter() {
        assertThatLineSegment(new TestLineSegment(new TestPoint(1)))
            .hasAWithZeroXY().hasB(new TestPoint(1));
    }

    @Test
    void constructorWithEqualABParameter() {
        assertThatLineSegment(
            new TestLineSegment(new TestPoint(2), new TestPoint(2))
        ).hasA(new TestPoint(2)).hasB(new TestPoint(2));
    }

    @Test
    void constructorWithDifferentABParameter() {
        assertThatLineSegment(
            new TestLineSegment(
                new TestPoint(2, 2), new TestPoint(3, 3)
            )
        ).hasA(new TestPoint(2)).hasB(new TestPoint(3));
    }

    @Test
    void createAndSetA() {
        LineSegment<Number> lineSegment = new TestLineSegment(new TestPoint());
        assertThatLineSegment(lineSegment).hasAWithZeroXY().hasB(new TestPoint());
        lineSegment.setA(new TestPoint(1));
        assertThatLineSegment(lineSegment)
            .hasA(new TestPoint(1)).hasBWithZeroXY();
    }

    @Test
    void createAndSetB() {
        LineSegment<Number> lineSegment = new TestLineSegment(new TestPoint());
        assertThatLineSegment(lineSegment).hasAWithZeroXY().hasB(new TestPoint());
        lineSegment.setB(new TestPoint(2));
        assertThatLineSegment(lineSegment).hasAWithZeroXY().hasB(new TestPoint(2));
    }

    // endregion

    // region length, halvingPoint and distributionPoint

    @Test
    void lengthOfLineSegmentWithTwoEqualPoints() {
        assertThatLineSegment(new TestLineSegment(new TestPoint(1, 2), new TestPoint(1, 2)))
            .hasLength(0d);
    }

    @Test
    void lengthOfLineSegmentWithInverseParameters() {
        assertThatLineSegment(new TestLineSegment(
            new TestPoint(1, 2),
            new TestPoint(2, 1)
        )).hasLength(Math.sqrt(2));
    }

    @Test
    void lengthOfLineSegmentWithTwoDifferenceEach() {
        assertThatLineSegment(new TestLineSegment(
            new TestPoint(3, 4),
            new TestPoint(1, 2)
        )).hasLength(2 * Math.sqrt(2));
    }

    @Test
    void halvingPointOfLineSegmentWithPXY0AndPXY1() {
        assertThatPoint(
            new TestLineSegment(new TestPoint(), new TestPoint(1)).halvingPoint()
        ).hasX(0.5).hasY(0.5);
    }

    @Test
    void distributionPointComma25OfLineSegmentWithPXY0AndPXY1() {
        assertThatPoint(
            new TestLineSegment(new TestPoint(), new TestPoint(1))
                .distributionPoint(0.25)
        ).hasX(0.25).hasY(0.25);
    }

    @Test
    void distributionPointComma5OfLineSegmentWithPXY0AndPXY1() {
        assertThatPoint(
            new TestLineSegment(new TestPoint(), new TestPoint(1))
                .distributionPoint(0.5)
        ).hasX(0.5).hasY(0.5);
    }

    @Test
    void distributionPointComma75OfLineSegmentWithPXY0AndPXY1() {
        assertThatPoint(
            new TestLineSegment(new TestPoint(), new TestPoint(1))
                .distributionPoint(0.75)
        ).hasX(0.75).hasY(0.75);
    }

    // endregion

    // region flip, toLine

    @Test
    void flipWithAXY0AndBXY1() {
        assertThatLineSegment(
            new TestLineSegment(new TestPoint(0), new TestPoint(1)).flip()
        ).hasA(new TestPoint(1)).hasB(new TestPoint(0));
    }

    @Test
    void toLineOfLineSegmentWithAXY0AndBXY1() {
        assertThatLine(
            new TestLineSegment(new TestPoint(0), new TestPoint(1)).toLine()
        ).hasM(1d).hasB(0d);
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX1Y0() {
        assertThatLine(
            new TestLineSegment(new TestPoint(1), new TestPoint(1, 0)).toLine()
        ).hasM(null).hasB(1);
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX0Y1() {
        assertThatLine(
            new TestLineSegment(new TestPoint(1), new TestPoint(0, 1)).toLine()
        ).hasM(-0d).hasB(1d);
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX2Y3() {
        assertThatLine(
            new TestLineSegment(new TestPoint(1), new TestPoint(2, 3)).toLine()
        ).hasM(2d).hasB(-1d);
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfLineSegmentWithA0_5B1_5() {
        TestLineSegment lineSegment = new TestLineSegment(
            new TestPoint(0.5), new TestPoint(1.5)
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
        assertThatLineSegment(new TestLineSegment(new TestPoint())).isValid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatLineSegment(new TestLineSegment(
            new TestPoint(Double.POSITIVE_INFINITY),
            new TestPoint(Double.POSITIVE_INFINITY)
        )).isInvalid();
    }

    @Test
    void moveOfLineSegmentWithAndBWithXY() {
        assertThatLineSegment(
            new TestLineSegment(new TestPoint(), new TestPoint(1)).move(1)
        ).hasA(new TestPoint(1d)).hasB(new TestPoint(2d));
    }

    @Test
    void moveOfLineSegmentWithAndBWithXAndY() {
        assertThatLineSegment(
            new TestLineSegment(new TestPoint(), new TestPoint(1)).move(1, 1)
        ).hasA(new TestPoint(1d)).hasB(new TestPoint(2d));
    }

    @Test
    void moveOfLineSegmentWithAndBWithVector() {
        assertThatLineSegment(
            new TestLineSegment(new TestPoint(), new TestPoint(1))
                .move(new TestVector(1))
        ).hasA(new TestPoint(1d)).hasB(new TestPoint(2d));
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithoutCenterWithPiHalf() {
        assertThatLineSegment(
            new TestLineSegment(new TestPoint(0, 0), new TestPoint(1, 2))
                .rotate(Math.PI / 2)
        ).hasAWithZeroXY()
            .hasBCloseTo(new TestPoint(-2d, 1d));
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithCenterXY1WithPiHalf() {
        assertThatLineSegment(
            new TestLineSegment(new TestPoint(0, 0), new TestPoint(1, 2))
                .rotate(new TestPoint(1), Math.PI / 2)
        ).hasA(new TestPoint(2d, 0d))
            .hasB(new TestPoint(0d, 1d));
    }

    @Test
    void copyOfLineSegmentWithTwoPoints() {
        assertCopyable(new TestLineSegment(
            new TestPoint(1, 2), new TestPoint(3, 4)
        ));
    }

    // endregion

    // region override

    @Test
    void equalsOfLineSegmentWithTwoPoints() {
        assertThatLineSegment(new TestLineSegment(new TestPoint(2), new TestPoint(3)))
            .isEqualTo(new TestLineSegment(new TestPoint(2), new TestPoint(3)))
            .isNotEqualTo(new TestLineSegment(new TestPoint(3), new TestPoint(2)));
    }

    @Test
    void hashCodeOfLineSegmentWithTwoPoints() {
        assertThat(new TestLineSegment(new TestPoint(2), new TestPoint(3)).hashCode()).isEqualTo(33793);
    }

    @Test
    void toStringOfLineSegmentWithTwoPoints() {
        LineSegment<Number> lineSegment = new TestLineSegment(
            new TestPoint(2d), new TestPoint(3d)
        );
        assertThatLineSegment(lineSegment).hasToString("2.0|2.0 3.0|3.0");
    }

    @Test
    void compareToOfLineSegmentWithTwoPoints() {
        LineSegment<Number> lineSegment = new TestLineSegment(
            new TestPoint(2), new TestPoint(3)
        );
        assertThatLineSegment(lineSegment)
            .isEqualByComparingTo(new TestLineSegment(
                new TestPoint(2), new TestPoint(3)
            ))
            .isLessThan(new TestLineSegment(
                new TestPoint(3), new TestPoint(4)
            ))
            .isGreaterThan(new TestLineSegment(
                new TestPoint(1), new TestPoint(1)
            ));
    }

    @Test
    void serializable() {
        assertSerializable(
            new TestLineSegment(new TestPoint()),
            TestLineSegment.class
        );
    }

    // endregion
}
