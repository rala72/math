package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class DoubleLineSegmentTest {
    // region constructors, getter and setter

    @Test
    void constructorWithBParameter() {
        assertThatLineSegment(new DoubleLineSegment(new DoublePoint(1d)))
            .hasAWithZeroXY().hasB(new DoublePoint(1d));
    }

    @Test
    void constructorWithEqualABParameter() {
        assertThatLineSegment(
            new DoubleLineSegment(new DoublePoint(2d), new DoublePoint(2d))
        ).hasA(new DoublePoint(2d)).hasB(new DoublePoint(2d));
    }

    @Test
    void constructorWithDifferentABParameter() {
        assertThatLineSegment(
            new DoubleLineSegment(
                new DoublePoint(2d, 2d), new DoublePoint(3d, 3d)
            )
        ).hasA(new DoublePoint(2d)).hasB(new DoublePoint(3d));
    }

    @Test
    void createAndSetA() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(new DoublePoint());
        assertThatLineSegment(lineSegment).hasAWithZeroXY().hasB(new DoublePoint());
        lineSegment.setA(new DoublePoint(1d));
        assertThatLineSegment(lineSegment).hasA(new DoublePoint(1d)).hasBWithZeroXY();
    }

    @Test
    void createAndSetB() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(new DoublePoint());
        assertThatLineSegment(lineSegment).hasAWithZeroXY().hasBWithZeroXY();
        lineSegment.setB(new DoublePoint(2d));
        assertThatLineSegment(lineSegment)
            .hasAWithZeroXY().hasB(new DoublePoint(2d));
    }

    // endregion

    // region length, halvingPoint and distributionPoint

    @Test
    void lengthOfLineSegmentWithTwoEqualPoints() {
        assertThatLineSegment(new DoubleLineSegment(
            new DoublePoint(1d, 2d),
            new DoublePoint(1d, 2d)
        )).hasLength(0d);
    }

    @Test
    void lengthOfLineSegmentWithInverseParameters() {
        assertThatLineSegment(new DoubleLineSegment(
            new DoublePoint(1d, 2d),
            new DoublePoint(2d, 1d)
        )).hasLength(Math.sqrt(2d));
    }

    @Test
    void lengthOfLineSegmentWithTwoDifferenceEach() {
        assertThatLineSegment(new DoubleLineSegment(
            new DoublePoint(3d, 4d),
            new DoublePoint(1d, 2d)
        )).hasLength(2d * Math.sqrt(2d));
    }

    @Test
    void halvingPointOfLineSegmentWithPXY0AndPXY1() {
        assertThatPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d)).halvingPoint()
        ).hasX(0.5).hasY(0.5);
    }

    @Test
    void distributionPointComma25OfLineSegmentWithPXY0AndPXY1() {
        assertThatPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .distributionPoint(0.25)
        ).hasX(0.25).hasY(0.25);
    }

    @Test
    void distributionPointComma5OfLineSegmentWithPXY0AndPXY1() {
        assertThatPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .distributionPoint(0.5)
        ).hasX(0.5).hasY(0.5);
    }

    @Test
    void distributionPointComma75OfLineSegmentWithPXY0AndPXY1() {
        assertThatPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .distributionPoint(0.75)
        ).hasX(0.75).hasY(0.75);
    }

    // endregion

    // region flip, toLine

    @Test
    void flipWithAXY0AndBXY1() {
        assertThatLineSegment(
            new DoubleLineSegment(new DoublePoint(0d), new DoublePoint(1d)).flip()
        ).hasA(new DoublePoint(1d)).hasB(new DoublePoint(0d));
    }

    @Test
    void toLineOfLineSegmentWithAXY0AndBXY1() {
        assertThatLine(
            new DoubleLineSegment(new DoublePoint(0d), new DoublePoint(1d)).toLine()
        ).hasM(1d).hasB(0d);
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX1Y0() {
        assertThatLine(
            new DoubleLineSegment(new DoublePoint(1d), new DoublePoint(1d, 0d)).toLine()
        ).hasM(null).hasB(1d);
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX0Y1() {
        assertThatLine(
            new DoubleLineSegment(new DoublePoint(1d), new DoublePoint(0d, 1d)).toLine()
        ).hasM(-0d).hasB(1d);
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX2Y3() {
        assertThatLine(
            new DoubleLineSegment(new DoublePoint(1d), new DoublePoint(2d, 3d)).toLine()
        ).hasM(2d).hasB(-1d);
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfLineSegmentWithA0_5B1_5() {
        DoubleLineSegment lineSegment = new DoubleLineSegment(
            new DoublePoint(0.5), new DoublePoint(1.5)
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
        assertThatLineSegment(new DoubleLineSegment(new DoublePoint())).isValid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatLineSegment(new DoubleLineSegment(
            new DoublePoint(Double.POSITIVE_INFINITY),
            new DoublePoint(Double.POSITIVE_INFINITY)
        )).isInvalid();
    }

    @Test
    void moveOfLineSegmentWithAndBWithXY() {
        assertThatLineSegment(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d)).move(1d)
        ).hasA(new DoublePoint(1d)).hasB(new DoublePoint(2d));
    }

    @Test
    void moveOfLineSegmentWithAndBWithXAndY() {
        assertThatLineSegment(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d)).move(1d, 1d)
        ).hasA(new DoublePoint(1d)).hasB(new DoublePoint(2d));
    }

    @Test
    void moveOfLineSegmentWithAndBWithVector() {
        assertThatLineSegment(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .move(new DoubleVector(1d))
        ).hasA(new DoublePoint(1d)).hasB(new DoublePoint(2d));
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithoutCenterWithPiHalf() {
        assertThatLineSegment(
            new DoubleLineSegment(new DoublePoint(0d, 0d), new DoublePoint(1d, 2d))
                .rotate(Math.PI / 2d)
        ).hasA(new DoublePoint())
            .hasBCloseTo(new DoublePoint(-2d, 1d));
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithCenterXY1WithPiHalf() {
        assertThatLineSegment(
            new DoubleLineSegment(new DoublePoint(0d, 0d), new DoublePoint(1d, 2d))
                .rotate(new DoublePoint(1d), Math.PI / 2d)
        ).hasA(new DoublePoint(2d, 0d))
            .hasB(new DoublePoint(0d, 1d));
    }

    @Test
    void copyOfLineSegmentWithTwoPoints() {
        assertCopyable(new DoubleLineSegment(
            new DoublePoint(1d, 2d), new DoublePoint(3d, 4d)
        ));
    }

    // endregion

    // region override

    @Test
    void equalsOfLineSegmentWithTwoPoints() {
        assertThatLineSegment(new DoubleLineSegment(new DoublePoint(2d), new DoublePoint(3d)))
            .isEqualTo(new DoubleLineSegment(new DoublePoint(2d), new DoublePoint(3d)))
            .isNotEqualTo(new DoubleLineSegment(new DoublePoint(3d), new DoublePoint(2d)));
    }

    @Test
    void hashCodeOfLineSegmentWithTwoPoints() {
        assertThat(new DoubleLineSegment(new DoublePoint(2d), new DoublePoint(3d)).hashCode())
            .isEqualTo(16808929);
    }

    @Test
    void toStringOfLineSegmentWithTwoPoints() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(
            new DoublePoint(2d), new DoublePoint(3d)
        );
        assertThatLineSegment(lineSegment).hasToString("2.0|2.0 3.0|3.0");
    }

    @Test
    void compareToOfLineSegmentWithTwoPoints() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(
            new DoublePoint(2d), new DoublePoint(3d)
        );
        assertThatLineSegment(lineSegment)
            .isEqualByComparingTo(new DoubleLineSegment(
                new DoublePoint(2d), new DoublePoint(3d)
            ))
            .isLessThan(new DoubleLineSegment(
                new DoublePoint(3d), new DoublePoint(4d)
            ))
            .isGreaterThan(new DoubleLineSegment(
                new DoublePoint(1d), new DoublePoint(1d)
            ));
    }

    @Test
    void serializable() {
        assertSerializable(
            new DoubleLineSegment(new DoublePoint()),
            DoubleLineSegment.class
        );
    }

    // endregion
}
