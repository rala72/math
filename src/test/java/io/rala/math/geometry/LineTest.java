package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.arguments.LineArgumentsStreamFactory;
import io.rala.math.testUtils.geometry.TestLine;
import io.rala.math.testUtils.geometry.TestPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class LineTest {
    // region constructors, getter and setter

    @Test
    void constructorWithX() {
        assertThatLine(new TestLine(1))
            .hasM(null).hasB(1);
    }

    @Test
    void constructorWithMB() {
        assertThatLine(new TestLine(2, 3))
            .hasM(2).hasB(3);
    }

    @Test
    void createAndSetM() {
        Line<Number> line = new TestLine(0, 0);
        line.setM(1);
        assertThatLine(line)
            .hasM(1).hasB(0);
    }

    @Test
    void createAndSetB() {
        Line<Number> line = new TestLine(0, 0);
        line.setB(2);
        assertThatLine(line)
            .hasM(0).hasB(2);
    }

    // endregion

    // region isHorizontal and isVertical

    @Test
    void isHorizontalOfHorizontalLine() {
        assertThatLine(new TestLine(0d, 1d)).isHorizontal();
    }

    @Test
    void isHorizontalOfVerticalLine() {
        assertThatLine(new TestLine(Double.NaN, 1)).isNotHorizontal();
    }

    @Test
    void isHorizontalOfM1B1Line() {
        assertThatLine(new TestLine(1, 1)).isNotHorizontal();
    }

    @Test
    void isVerticalOfHorizontalLine() {
        assertThatLine(new TestLine(0, 1)).isNotVertical();
    }

    @Test
    void isVerticalOfVerticalLine() {
        assertThatLine(new TestLine(null, 1d)).isVertical();
    }

    @Test
    void isVerticalOfM1B1Line() {
        assertThatLine(new TestLine(1, 1)).isNotVertical();
    }

    // endregion

    // region calculateX and calculateY

    @ParameterizedTest
    @MethodSource("getCalculateXArguments")
    void calculateX(double m, double b, double y, Double expected) {
        assertThat(new TestLine(m, b).calculateX(y)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getCalculateYArguments")
    void calculateY(double m, double b, double x, Double expected) {
        assertThat(new TestLine(m, b).calculateY(x)).isEqualTo(expected);
    }

    // endregion

    // region normal

    @Test
    void normalM1B0() {
        assertThatLine(new TestLine(1, 0).normal())
            .hasM(-1d).hasB(0);
    }

    @Test
    void normalOfVerticalLine() {
        assertThatLine(new TestLine(0).normal())
            .hasM(0d).hasB(0);
    }

    @Test
    void normalOfHorizontalLine() {
        assertThatLine(new TestLine(0d, 0).normal())
            .hasM(null).hasB(0);
    }

    @Test
    void normalM1B1AndPointXY1() {
        assertThatLine(new TestLine(1, 1).normal(new TestPoint(1)))
            .hasM(-1d).hasB(2d);
    }

    @Test
    void normalM1B0AndPointXY1() {
        assertThatLine(new TestLine(1, 0).normal(new TestPoint(1, 1)))
            .hasM(-1d).hasB(2d);
    }

    @Test
    void normalOfVerticalLineAndPointX0Y1() {
        assertThatLine(new TestLine(0).normal(new TestPoint(0, 1)))
            .hasM(0d).hasB(1d);
    }

    @Test
    void normalOfHorizontalLineAndPointX1Y0() {
        assertThatLine(new TestLine(0d, 0).normal(new TestPoint(1, 0)))
            .hasM(null).hasB(1);
    }

    // endregion

    // region intersection

    @Test
    void hasIntersectionWithEqualM() {
        assertThatLine(new TestLine(1, 2)).hasNoIntersection(new TestLine(1, 0));
    }

    @Test
    void hasIntersectionWithLineM1B2AndM2B1() {
        assertThatLine(new TestLine(1, 2)).hasIntersection(new TestLine(2, 1));
    }

    @Test
    void hasIntersectionWithLineX1AndX2() {
        assertThatLine(new TestLine(1)).hasNoIntersection(new TestLine(2));
    }

    @Test
    void hasIntersectionWithLineM1B2AndX1() {
        assertThatLine(new TestLine(1, 2)).hasIntersection(new TestLine(1));
    }

    @Test
    void intersectionWithEqualM() {
        assertThatPoint(new TestLine(1, 2).intersection(new TestLine(1, 0))).isNull();
    }

    @Test
    void intersectionWithLineM1B2AndM2B1() {
        assertThatPoint(
            new TestLine(1, 2).intersection(new TestLine(2, 1))
        ).hasX(1).hasY(3);
    }

    @Test
    void intersectionWithLineX1AndX2() {
        assertThatPoint(new TestLine(1).intersection(new TestLine(2))).isNull();
    }

    @Test
    void intersectionWithLineM1B2AndX1() {
        assertThatPoint(
            new TestLine(1, 2).intersection(new TestLine(1))
        ).hasX(1).hasY(3);
    }

    @Test
    void intersectionWithLineX1AndM1B2() {
        assertThatPoint(
            new TestLine(1).intersection(new TestLine(1, 2))
        ).hasX(1).hasY(3);
    }

    @Test
    void intersectionAngleWithLineM1B2AndM2B1() {
        assertThat(new TestLine(1, 2).intersectionAngle(new TestLine(2, 1)))
            .isEqualTo(0.3217505543966422);
    }

    @Test
    void intersectionAngleWithLineX1AndX2() {
        assertThat(new TestLine(1).intersectionAngle(new TestLine(2))).isNull();
    }

    @Test
    void intersectionAngleWithLineM1B2AndX1() {
        assertThat(new TestLine(1, 2).intersectionAngle(new TestLine(1)))
            .isEqualTo(Math.PI / 4);
    }

    @Test
    void intersectionAngleWithLineX1AndM1B2() {
        assertThat(new TestLine(1).intersectionAngle(new TestLine(1, 2)))
            .isEqualTo(Math.PI / 4);
    }

    // endregion

    // region hasPoint

    @Test
    void hasPointWithM1B0AndPointXY1() {
        assertThatLine(new TestLine(1d, 0d)).hasPoint(new TestPoint(1d));
    }

    @Test
    void hasPointWithM1B1AndPointXY1() {
        assertThatLine(new TestLine(1, 1)).hasNoPoint(new TestPoint(1));
    }

    @Test
    void hasPointWithVerticalLine0AndPointX0Y1() {
        assertThatLine(new TestLine(0)).hasPoint(new TestPoint(0, 1));
    }

    @Test
    void hasPointWithVerticalLine0AndPointXY1() {
        assertThatLine(new TestLine(0)).hasNoPoint(new TestPoint(1));
    }

    // endregion

    // region toLineSegment

    @Test
    void toLineSegmentUsingXOfLineWithM0B1() {
        assertThatLineSegment(
            new TestLine(0, 1).toLineSegmentUsingX(0, 1)
        ).hasA(new TestPoint(0, 1d)).hasB(new TestPoint(1, 1d));
    }

    @Test
    void toLineSegmentUsingXOfLineWithM1B1() {
        assertThatLineSegment(
            new TestLine(1, 1).toLineSegmentUsingX(0, 1)
        ).hasA(new TestPoint(0, 1d)).hasB(new TestPoint(1, 2d));
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B0() {
        assertThatLineSegment(
            new TestLine(1, 0).toLineSegmentUsingY(0, 1)
        ).hasA(new TestPoint(0d, 0)).hasB(new TestPoint(1d, 1));
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B1() {
        assertThatLineSegment(
            new TestLine(1, 1).toLineSegmentUsingY(0, 1)
        ).hasA(new TestPoint(-1d, 0)).hasB(new TestPoint(0d, 1));
    }

    // endregion

    // region map, isValid, copy

    @Test
    void mapOfLineWithM0_5B1_5() {
        TestLine line = new TestLine(0.5, 1.5);
        Line<Integer> result = new Line<>(new IntegerArithmetic(), 0, 1);
        assertThatLine(line.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThatLine(new TestLine(0d, 0d)).isValid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatLine(new TestLine(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)).isInvalid();
    }

    @Test
    void copyOfLieWithMB() {
        assertCopyable(new TestLine(2, 3));
    }

    // endregion

    // region override

    @Test
    void equalsOfLineWithMB() {
        assertThatLine(new TestLine(2, 3))
            .isEqualTo(new TestLine(2, 3))
            .isNotEqualTo(new TestLine(3, 2));
    }

    @Test
    void hashCodeOfLineWithMB() {
        assertThat(new TestLine(2, 3).hashCode()).isEqualTo(1026);
    }

    @Test
    void toStringOfLineWithMB() {
        Line<Number> line = new TestLine(2d, 3d);
        assertThatLine(line).hasToString("y=2.0*x+3.0");
    }

    @Test
    void toStringOfVerticalLine() {
        Line<Number> line = new TestLine(1d);
        assertThatLine(line).hasToString("y=1.0");
    }

    @Test
    void compareToOfLineWithMB() {
        Line<Number> line = new TestLine(2, 3);
        assertThatLine(line)
            .isEqualByComparingTo(new TestLine(2, 3))
            .isLessThan(new TestLine(3, 1))
            .isGreaterThan(new TestLine(2, 1))
            .isGreaterThan(new TestLine(null, 1));
        assertThatLine(new TestLine(null, 1)).isLessThan(line);
    }

    @Test
    void serializable() {
        assertSerializable(new TestLine(0), TestLine.class);
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
