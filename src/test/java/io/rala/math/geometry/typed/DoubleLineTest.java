package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Line;
import io.rala.math.testUtils.arguments.LineArgumentsStreamFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class DoubleLineTest {
    // region constructors, getter and setter

    @Test
    void constructorWithX() {
        assertThatLine(new DoubleLine(1d))
            .hasM(null).hasB(1d);
    }

    @Test
    void constructorWithMB() {
        assertThatLine(new DoubleLine(2d, 3d))
            .hasM(2d).hasB(3d);
    }

    @Test
    void createAndSetM() {
        Line<Double> line = new DoubleLine(0d, 0d);
        line.setM(1d);
        assertThatLine(line)
            .hasM(1d).hasB(0d);
    }

    @Test
    void createAndSetB() {
        Line<Double> line = new DoubleLine(0d, 0d);
        line.setB(2d);
        assertThatLine(line)
            .hasM(0d).hasB(2d);
    }

    // endregion

    // region isHorizontal and isVertical

    @Test
    void isHorizontalOfHorizontalLine() {
        assertThatLine(new DoubleLine(0d, 1d)).isHorizontal();
    }

    @Test
    void isHorizontalOfVerticalLine() {
        assertThatLine(new DoubleLine(Double.NaN, 1d)).isNotHorizontal();
    }

    @Test
    void isHorizontalOfM1B1Line() {
        assertThatLine(new DoubleLine(1d, 1d)).isNotHorizontal();
    }

    @Test
    void isVerticalOfHorizontalLine() {
        assertThatLine(new DoubleLine(0d, 1d)).isNotVertical();
    }

    @Test
    void isVerticalOfVerticalLine() {
        assertThatLine(new DoubleLine(null, 1d)).isVertical();
    }

    @Test
    void isVerticalOfM1B1Line() {
        assertThatLine(new DoubleLine(1d, 1d)).isNotVertical();
    }

    // endregion

    // region calculateX and calculateY

    @ParameterizedTest
    @MethodSource("getCalculateXArguments")
    void calculateX(double m, double b, double y, Double expected) {
        assertThat(new DoubleLine(m, b).calculateX(y)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getCalculateYArguments")
    void calculateY(double m, double b, double x, Double expected) {
        assertThat(new DoubleLine(m, b).calculateY(x)).isEqualTo(expected);
    }

    // endregion

    // region normal

    @Test
    void normalM1B0() {
        assertThatLine(new DoubleLine(1d, 0d).normal())
            .hasM(-1d).hasB(0d);
    }

    @Test
    void normalOfVerticalLine() {
        assertThatLine(new DoubleLine(0d).normal())
            .hasM(0d).hasB(0d);
    }

    @Test
    void normalOfHorizontalLine() {
        assertThatLine(new DoubleLine(0d, 0d).normal())
            .hasM(null).hasB(0d);
    }

    @Test
    void normalM1B1AndPointXY1() {
        assertThatLine(new DoubleLine(1d, 1d).normal(new DoublePoint(1d)))
            .hasM(-1d).hasB(2d);
    }

    @Test
    void normalM1B0AndPointXY1() {
        assertThatLine(new DoubleLine(1d, 0d).normal(new DoublePoint(1d, 1d)))
            .hasM(-1d).hasB(2d);
    }

    @Test
    void normalOfVerticalLineAndPointX0Y1() {
        assertThatLine(new DoubleLine(0d).normal(new DoublePoint(0d, 1d)))
            .hasM(0d).hasB(1d);
    }

    @Test
    void normalOfHorizontalLineAndPointX1Y0() {
        assertThatLine(new DoubleLine(0d, 0d).normal(new DoublePoint(1d, 0d)))
            .hasM(null).hasB(1d);
    }

    // endregion

    // region intersection

    @Test
    void hasIntersectionWithEqualM() {
        assertThatLine(new DoubleLine(1d, 2d)).hasNoIntersection(new DoubleLine(1d, 0d));
    }

    @Test
    void hasIntersectionWithLineM1B2AndM2B1() {
        assertThatLine(new DoubleLine(1d, 2d)).hasIntersection(new DoubleLine(2d, 1d));
    }

    @Test
    void hasIntersectionWithLineX1AndX2() {
        assertThatLine(new DoubleLine(1d)).hasNoIntersection(new DoubleLine(2d));
    }

    @Test
    void hasIntersectionWithLineM1B2AndX1() {
        assertThatLine(new DoubleLine(1d, 2d)).hasIntersection(new DoubleLine(1d));
    }

    @Test
    void intersectionWithEqualM() {
        assertThatPoint(new DoubleLine(1d, 2d).intersection(new DoubleLine(1d, 0d))).isNull();
    }

    @Test
    void intersectionWithLineM1B2AndM2B1() {
        assertThatPoint(
            new DoubleLine(1d, 2d).intersection(new DoubleLine(2d, 1d))
        ).hasX(1d).hasY(3d);
    }

    @Test
    void intersectionWithLineX1AndX2() {
        assertThatPoint(new DoubleLine(1d).intersection(new DoubleLine(2d))).isNull();
    }

    @Test
    void intersectionWithLineM1B2AndX1() {
        assertThatPoint(
            new DoubleLine(1d, 2d).intersection(new DoubleLine(1d))
        ).hasX(1d).hasY(3d);
    }

    @Test
    void intersectionWithLineX1AndM1B2() {
        assertThatPoint(
            new DoubleLine(1d).intersection(new DoubleLine(1d, 2d))
        ).hasX(1d).hasY(3d);
    }

    @Test
    void intersectionAngleWithLineM1B2AndM2B1() {
        assertThat(new DoubleLine(1d, 2d).intersectionAngle(new DoubleLine(2d, 1d)))
            .isEqualTo(0.3217505543966422);
    }

    @Test
    void intersectionAngleWithLineX1AndX2() {
        assertThat(new DoubleLine(1d).intersectionAngle(new DoubleLine(2d))).isNull();
    }

    @Test
    void intersectionAngleWithLineM1B2AndX1() {
        assertThat(new DoubleLine(1d, 2d).intersectionAngle(new DoubleLine(1d)))
            .isEqualTo(Math.PI / 4);
    }

    @Test
    void intersectionAngleWithLineX1AndM1B2() {
        assertThat(new DoubleLine(1d).intersectionAngle(new DoubleLine(1d, 2d)))
            .isEqualTo(Math.PI / 4);
    }

    // endregion

    // region hasPoint

    @Test
    void hasPointWithM1B0AndPointXY1() {
        assertThatLine(new DoubleLine(1d, 0d)).hasPoint(new DoublePoint(1d));
    }

    @Test
    void hasPointWithM1B1AndPointXY1() {
        assertThatLine(new DoubleLine(1d, 1d)).hasNoPoint(new DoublePoint(1d));
    }

    @Test
    void hasPointWithVerticalLine0AndPointX0Y1() {
        assertThatLine(new DoubleLine(0d)).hasPoint(new DoublePoint(0d, 1d));
    }

    @Test
    void hasPointWithVerticalLine0AndPointXY1() {
        assertThatLine(new DoubleLine(0d)).hasNoPoint(new DoublePoint(1d));
    }

    // endregion

    // region toLineSegment

    @Test
    void toLineSegmentUsingXOfLineWithM0B1() {
        assertThatLineSegment(
            new DoubleLine(0d, 1d).toLineSegmentUsingX(0d, 1d)
        ).hasA(new DoublePoint(0d, 1d)).hasB(new DoublePoint(1d, 1d));
    }

    @Test
    void toLineSegmentUsingXOfLineWithM1B1() {
        assertThatLineSegment(
            new DoubleLine(1d, 1d).toLineSegmentUsingX(0d, 1d)
        ).hasA(new DoublePoint(0d, 1d)).hasB(new DoublePoint(1d, 2d));
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B0() {
        assertThatLineSegment(
            new DoubleLine(1d, 0d).toLineSegmentUsingY(0d, 1d)
        ).hasAWithZeroXY().hasB(new DoublePoint(1d, 1d));
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B1() {
        assertThatLineSegment(
            new DoubleLine(1d, 1d).toLineSegmentUsingY(0d, 1d)
        ).hasA(new DoublePoint(-1d, 0d)).hasB(new DoublePoint(0d, 1d));
    }

    // endregion

    // region map, isValid, copy

    @Test
    void mapOfLineWithM0_5B1_5() {
        DoubleLine line = new DoubleLine(0.5, 1.5);
        Line<Integer> result = new Line<>(new IntegerArithmetic(), 0, 1);
        assertThatLine(line.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThatLine(new DoubleLine(0d, 0d)).isValid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatLine(
            new DoubleLine(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        ).isInvalid();
    }

    @Test
    void copyOfLieWithMB() {
        assertCopyable(new DoubleLine(2d, 3d));
    }

    // endregion

    // region override

    @Test
    void equalsOfLineWithMB() {
        assertThatLine(new DoubleLine(2d, 3d))
            .isEqualTo(new DoubleLine(2d, 3d))
            .isNotEqualTo(new DoubleLine(3d, 2d));
    }

    @Test
    void hashCodeOfLineWithMB() {
        assertThat(new DoubleLine(2d, 3d).hashCode()).isEqualTo(525249);
    }

    @Test
    void toStringOfLineWithMB() {
        Line<Double> line = new DoubleLine(2d, 3d);
        assertThatLine(line).hasToString("y=2.0*x+3.0");
    }

    @Test
    void toStringOfVerticalLine() {
        Line<Double> line = new DoubleLine(1d);
        assertThatLine(line).hasToString("y=1.0");
    }

    @Test
    void compareToOfLineWithMB() {
        Line<Double> line = new DoubleLine(2d, 3d);
        assertThatLine(line)
            .isEqualByComparingTo(new DoubleLine(2d, 3d))
            .isLessThan(new DoubleLine(3d, 1d))
            .isGreaterThan(new DoubleLine(2d, 1d));
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleLine(0d), DoubleLine.class);
    }

    // endregion


    // region argument streams

    private static Stream<Arguments> getCalculateXArguments() {
        return LineArgumentsStreamFactory.calculateX();
    }

    private static Stream<Arguments> getCalculateYArguments() {
        return LineArgumentsStreamFactory.calculateY();
    }

    // endregion
}
