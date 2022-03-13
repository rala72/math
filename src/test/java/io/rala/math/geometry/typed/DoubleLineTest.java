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
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class DoubleLineTest {
    // region constructors, getter and setter

    @Test
    void constructorWithX() {
        assertLine(new DoubleLine(1d), null, 1d);
    }

    @Test
    void constructorWithMB() {
        assertLine(new DoubleLine(2d, 3d), 2d, 3d);
    }

    @Test
    void createAndSetM() {
        Line<Double> line = new DoubleLine(0d, 0d);
        line.setM(1d);
        assertLine(line, 1d, 0d);
    }

    @Test
    void createAndSetB() {
        Line<Double> line = new DoubleLine(0d, 0d);
        line.setB(2d);
        assertLine(line, 0d, 2d);
    }

    // endregion

    // region isHorizontal and isVertical

    @Test
    void isHorizontalOfHorizontalLine() {
        assertThat(new DoubleLine(0d, 1d).isHorizontal()).isTrue();
    }

    @Test
    void isHorizontalOfVerticalLine() {
        assertThat(new DoubleLine(Double.NaN, 1d).isHorizontal()).isFalse();
    }

    @Test
    void isHorizontalOfM1B1Line() {
        assertThat(new DoubleLine(1d, 1d).isHorizontal()).isFalse();
    }

    @Test
    void isVerticalOfHorizontalLine() {
        assertThat(new DoubleLine(0d, 1d).isVertical()).isFalse();
    }

    @Test
    void isVerticalOfVerticalLine() {
        assertThat(new DoubleLine(null, 1d).isVertical()).isTrue();
    }

    @Test
    void isVerticalOfM1B1Line() {
        assertThat(new DoubleLine(1d, 1d).isVertical()).isFalse();
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
        assertLine(new DoubleLine(1d, 0d).normal(), -1d, 0d);
    }

    @Test
    void normalOfVerticalLine() {
        assertLine(new DoubleLine(0d).normal(), 0d, 0d);
    }

    @Test
    void normalOfHorizontalLine() {
        assertLine(new DoubleLine(0d, 0d).normal(), null, 0d);
    }

    @Test
    void normalM1B1AndPointXY1() {
        assertLine(
            new DoubleLine(1d, 1d).normal(new DoublePoint(1d)),
            -1d, 2d
        );
    }

    @Test
    void normalM1B0AndPointXY1() {
        assertLine(
            new DoubleLine(1d, 0d).normal(new DoublePoint(1d, 1d)),
            -1d, 2d
        );
    }

    @Test
    void normalOfVerticalLineAndPointX0Y1() {
        assertLine(
            new DoubleLine(0d).normal(new DoublePoint(0d, 1d)),
            0d, 1d
        );
    }

    @Test
    void normalOfHorizontalLineAndPointX1Y0() {
        assertLine(
            new DoubleLine(0d, 0d).normal(new DoublePoint(1d, 0d)),
            null, 1d
        );
    }

    // endregion

    // region intersection

    @Test
    void hasIntersectionWithEqualM() {
        assertThat(new DoubleLine(1d, 2d).hasIntersection(new DoubleLine(1d, 0d))).isFalse();
    }

    @Test
    void hasIntersectionWithLineM1B2AndM2B1() {
        assertThat(new DoubleLine(1d, 2d).hasIntersection(new DoubleLine(2d, 1d))).isTrue();
    }

    @Test
    void hasIntersectionWithLineX1AndX2() {
        assertThat(new DoubleLine(1d).hasIntersection(new DoubleLine(2d))).isFalse();
    }

    @Test
    void hasIntersectionWithLineM1B2AndX1() {
        assertThat(new DoubleLine(1d, 2d).hasIntersection(new DoubleLine(1d))).isTrue();
    }

    @Test
    void intersectionWithEqualM() {
        assertThat(new DoubleLine(1d, 2d).intersection(new DoubleLine(1d, 0d))).isNull();
    }

    @Test
    void intersectionWithLineM1B2AndM2B1() {
        assertPoint(
            new DoubleLine(1d, 2d).intersection(new DoubleLine(2d, 1d)),
            1d, 3d
        );
    }

    @Test
    void intersectionWithLineX1AndX2() {
        assertThat(new DoubleLine(1d).intersection(new DoubleLine(2d))).isNull();
    }

    @Test
    void intersectionWithLineM1B2AndX1() {
        assertPoint(
            new DoubleLine(1d, 2d).intersection(new DoubleLine(1d)),
            1d, 3d
        );
    }

    @Test
    void intersectionWithLineX1AndM1B2() {
        assertPoint(
            new DoubleLine(1d).intersection(new DoubleLine(1d, 2d)),
            1d, 3d
        );
    }

    @Test
    void intersectionAngleWithLineM1B2AndM2B1() {
        assertThat(new DoubleLine(1d, 2d).intersectionAngle(new DoubleLine(2d, 1d))).isEqualTo(0.3217505543966422);
    }

    @Test
    void intersectionAngleWithLineX1AndX2() {
        assertThat(new DoubleLine(1d).intersectionAngle(new DoubleLine(2d))).isNull();
    }

    @Test
    void intersectionAngleWithLineM1B2AndX1() {
        assertThat(new DoubleLine(1d, 2d).intersectionAngle(new DoubleLine(1d))).isEqualTo(0.7853981633974483);
    }

    @Test
    void intersectionAngleWithLineX1AndM1B2() {
        assertThat(new DoubleLine(1d).intersectionAngle(new DoubleLine(1d, 2d))).isEqualTo(0.7853981633974483);
    }

    // endregion

    // region hasPoint

    @Test
    void hasPointWithM1B0AndPointXY1() {
        assertThat(new DoubleLine(1d, 0d).hasPoint(new DoublePoint(1d))).isTrue();
    }

    @Test
    void hasPointWithM1B1AndPointXY1() {
        assertThat(new DoubleLine(1d, 1d).hasPoint(new DoublePoint(1d))).isFalse();
    }

    @Test
    void hasPointWithVerticalLine0AndPointX0Y1() {
        assertThat(new DoubleLine(0d).hasPoint(new DoublePoint(0d, 1d))).isTrue();
    }

    @Test
    void hasPointWithVerticalLine0AndPointXY1() {
        assertThat(new DoubleLine(0d).hasPoint(new DoublePoint(1d))).isFalse();
    }

    // endregion

    // region toLineSegment

    @Test
    void toLineSegmentUsingXOfLineWithM0B1() {
        assertLineSegment(
            new DoubleLine(0d, 1d).toLineSegmentUsingX(0d, 1d),
            new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
    }

    @Test
    void toLineSegmentUsingXOfLineWithM1B1() {
        assertLineSegment(
            new DoubleLine(1d, 1d).toLineSegmentUsingX(0d, 1d),
            new DoublePoint(0d, 1d), new DoublePoint(1d, 2d)
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B0() {
        assertLineSegment(
            new DoubleLine(1d, 0d).toLineSegmentUsingY(0d, 1d),
            new DoublePoint(0d, 0d), new DoublePoint(1d, 1d)
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B1() {
        assertLineSegment(
            new DoubleLine(1d, 1d).toLineSegmentUsingY(0d, 1d),
            new DoublePoint(-1d, 0d), new DoublePoint(0d, 1d)
        );
    }

    // endregion

    // region map, isValid, copy

    @Test
    void mapOfLineWithM0_5B1_5() {
        DoubleLine line = new DoubleLine(0.5, 1.5);
        Line<Integer> result = new Line<>(new IntegerArithmetic(), 0, 1);
        assertThat(line.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new DoubleLine(0d, 0d).isValid()).isTrue();
    }

    @Test
    void isValidWithInfValues() {
        assertThat(new DoubleLine(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
            .isValid()).isFalse();
    }

    @Test
    void copyOfLieWithMB() {
        Line<Double> line = new DoubleLine(2d, 3d);
        assertThat(line.copy()).isEqualTo(line);
    }

    // endregion

    // region override

    @Test
    void equalsOfLineWithMB() {
        assertThat(new DoubleLine(2d, 3d))
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
        assertThat(line).hasToString("y=2.0*x+3.0");
    }

    @Test
    void toStringOfVerticalLine() {
        Line<Double> line = new DoubleLine(1d);
        assertThat(line).hasToString("y=1.0");
    }

    @Test
    void compareToOfLineWithMB() {
        Line<Double> line = new DoubleLine(2d, 3d);
        assertThat(line)
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
