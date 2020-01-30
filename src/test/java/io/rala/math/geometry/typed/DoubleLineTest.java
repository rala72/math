package io.rala.math.geometry.typed;

import io.rala.math.geometry.Line;
import io.rala.math.testUtils.arguments.LineArgumentsStreamFactory;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class DoubleLineTest {
    // region constructors, getter and setter

    @Test
    void constructorWithX() {
        GeometryAssertions.assertLine(new DoubleLine(1d), null, 1d);
    }

    @Test
    void constructorWithMB() {
        GeometryAssertions.assertLine(new DoubleLine(2d, 3d), 2d, 3d);
    }

    @Test
    void createAndSetM() {
        Line<Double> line = new DoubleLine(0d, 0d);
        line.setM(1d);
        GeometryAssertions.assertLine(line, 1d, 0d);
    }

    @Test
    void createAndSetB() {
        Line<Double> line = new DoubleLine(0d, 0d);
        line.setB(2d);
        GeometryAssertions.assertLine(line, 0d, 2d);
    }

    // endregion

    // region isHorizontal and isVertical

    @Test
    void isHorizontalOfHorizontalLine() {
        Assertions.assertTrue(new DoubleLine(0d, 1d).isHorizontal());
    }

    @Test
    void isHorizontalOfVerticalLine() {
        Assertions.assertFalse(new DoubleLine(Double.NaN, 1d).isHorizontal());
    }

    @Test
    void isHorizontalOfM1B1Line() {
        Assertions.assertFalse(new DoubleLine(1d, 1d).isHorizontal());
    }

    @Test
    void isVerticalOfHorizontalLine() {
        Assertions.assertFalse(new DoubleLine(0d, 1d).isVertical());
    }

    @Test
    void isVerticalOfVerticalLine() {
        Assertions.assertTrue(new DoubleLine(null, 1d).isVertical());
    }

    @Test
    void isVerticalOfM1B1Line() {
        Assertions.assertFalse(new DoubleLine(1d, 1d).isVertical());
    }

    // endregion

    // region calculateX and calculateY

    @ParameterizedTest
    @MethodSource("getCalculateXArguments")
    void calculateX(double m, double b, double y, double expected) {
        Assertions.assertEquals(expected, new DoubleLine(m, b).calculateX(y));
    }

    @ParameterizedTest
    @MethodSource("getCalculateYArguments")
    void calculateY(double m, double b, double x, double expected) {
        Assertions.assertEquals(expected, new DoubleLine(m, b).calculateY(x));
    }

    // endregion

    // region normal

    @Test
    void normalM1B0() {
        GeometryAssertions.assertLine(new DoubleLine(1d, 0d).normal(), -1d, 0d);
    }

    @Test
    void normalOfVerticalLine() {
        GeometryAssertions.assertLine(new DoubleLine(0d).normal(), 0d, 0d);
    }

    @Test
    void normalOfHorizontalLine() {
        GeometryAssertions.assertLine(new DoubleLine(0d, 0d).normal(), null, 0d);
    }

    @Test
    void normalM1B1AndPointXY1() {
        GeometryAssertions.assertLine(
            new DoubleLine(1d, 1d).normal(new DoublePoint(1d)),
            -1d, 2d
        );
    }

    @Test
    void normalM1B0AndPointXY1() {
        GeometryAssertions.assertLine(
            new DoubleLine(1d, 0d).normal(new DoublePoint(1d, 1d)),
            -1d, 2d
        );
    }

    @Test
    void normalOfVerticalLineAndPointX0Y1() {
        GeometryAssertions.assertLine(new DoubleLine(0d).normal(new DoublePoint(0d, 1d)), 0d, 1d);
    }

    @Test
    void normalOfHorizontalLineAndPointX1Y0() {
        GeometryAssertions.assertLine(
            new DoubleLine(0d, 0d).normal(new DoublePoint(1d, 0d)),
            null, 1d
        );
    }

    // endregion

    // region intersection

    @Test
    void hasIntersectionWithEqualM() {
        Assertions.assertFalse(new DoubleLine(1d, 2d).hasIntersection(new DoubleLine(1d, 0d)));
    }

    @Test
    void hasIntersectionWithLineM1B2AndM2B1() {
        Assertions.assertTrue(new DoubleLine(1d, 2d).hasIntersection(new DoubleLine(2d, 1d)));
    }

    @Test
    void hasIntersectionWithLineX1AndX2() {
        Assertions.assertFalse(new DoubleLine(1d).hasIntersection(new DoubleLine(2d)));
    }

    @Test
    void hasIntersectionWithLineM1B2AndX1() {
        Assertions.assertTrue(new DoubleLine(1d, 2d).hasIntersection(new DoubleLine(1d)));
    }

    @Test
    void intersectionWithEqualM() {
        Assertions.assertNull(new DoubleLine(1d, 2d).intersection(new DoubleLine(1d, 0d)));
    }

    @Test
    void intersectionWithLineM1B2AndM2B1() {
        GeometryAssertions.assertPoint(
            new DoubleLine(1d, 2d).intersection(new DoubleLine(2d, 1d)),
            1d, 3d
        );
    }

    @Test
    void intersectionWithLineX1AndX2() {
        Assertions.assertNull(new DoubleLine(1d).intersection(new DoubleLine(2d)));
    }

    @Test
    void intersectionWithLineM1B2AndX1() {
        GeometryAssertions.assertPoint(
            new DoubleLine(1d, 2d).intersection(new DoubleLine(1d)),
            1d, 3d
        );
    }

    @Test
    void intersectionWithLineX1AndM1B2() {
        GeometryAssertions.assertPoint(
            new DoubleLine(1d).intersection(new DoubleLine(1d, 2d)),
            1d, 3d
        );
    }

    @Test
    void intersectionAngleWithLineM1B2AndM2B1() {
        Assertions.assertEquals(0.3217505543966422d,
            new DoubleLine(1d, 2d).intersectionAngle(new DoubleLine(2d, 1d))
        );
    }

    @Test
    void intersectionAngleWithLineX1AndX2() {
        Assertions.assertNull(
            new DoubleLine(1d).intersectionAngle(new DoubleLine(2d))
        );
    }

    @Test
    void intersectionAngleWithLineM1B2AndX1() {
        Assertions.assertEquals(0.7853981633974483d,
            new DoubleLine(1d, 2d).intersectionAngle(new DoubleLine(1d))
        );
    }

    @Test
    void intersectionAngleWithLineX1AndM1B2() {
        Assertions.assertEquals(0.7853981633974483d,
            new DoubleLine(1d).intersectionAngle(new DoubleLine(1d, 2d))
        );
    }

    // endregion

    // region hasPoint

    @Test
    void hasPointWithM1B0AndPointXY1() {
        Assertions.assertTrue(new DoubleLine(1d, 0d).hasPoint(new DoublePoint(1d)));
    }

    @Test
    void hasPointWithM1B1AndPointXY1() {
        Assertions.assertFalse(new DoubleLine(1d, 1d).hasPoint(new DoublePoint(1d)));
    }

    @Test
    void hasPointWithVerticalLine0AndPointX0Y1() {
        Assertions.assertTrue(new DoubleLine(0d).hasPoint(new DoublePoint(0d, 1d)));
    }

    @Test
    void hasPointWithVerticalLine0AndPointXY1() {
        Assertions.assertFalse(new DoubleLine(0d).hasPoint(new DoublePoint(1d)));
    }

    // endregion

    // region toLineSegment

    @Test
    void toLineSegmentUsingXOfLineWithM0B1() {
        GeometryAssertions.assertLineSegment(
            new DoubleLine(0d, 1d).toLineSegmentUsingX(0d, 1d),
            new DoublePoint(0d, 1d), new DoublePoint(1d, 1d)
        );
    }

    @Test
    void toLineSegmentUsingXOfLineWithM1B1() {
        GeometryAssertions.assertLineSegment(
            new DoubleLine(1d, 1d).toLineSegmentUsingX(0d, 1d),
            new DoublePoint(0d, 1d), new DoublePoint(1d, 2d)
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B0() {
        GeometryAssertions.assertLineSegment(
            new DoubleLine(1d, 0d).toLineSegmentUsingY(0d, 1d),
            new DoublePoint(0d, 0d), new DoublePoint(1d, 1d)
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B1() {
        GeometryAssertions.assertLineSegment(
            new DoubleLine(1d, 1d).toLineSegmentUsingY(0d, 1d),
            new DoublePoint(-1d, 0d), new DoublePoint(0d, 1d)
        );
    }

    // endregion

    // region isValid, copy

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new DoubleLine(0d, 0d).isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new DoubleLine(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void copyOfLieWithMB() {
        Line<Double> line = new DoubleLine(2d, 3d);
        Assertions.assertEquals(line, line.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfLineWithMB() {
        Line<Double> line = new DoubleLine(2d, 3d);
        Assertions.assertEquals(
            line,
            new DoubleLine(2d, 3d)
        );
        Assertions.assertNotEquals(
            line,
            new DoubleLine(3d, 2d)
        );
    }

    @Test
    void hashCodeOfLineWithMB() {
        Assertions.assertEquals(
            525249,
            new DoubleLine(2d, 3d).hashCode()
        );
    }

    @Test
    void toStringOfLineWithMB() {
        Line<Double> line = new DoubleLine(2d, 3d);
        Assertions.assertEquals("y=2.0*x+3.0", line.toString());
    }

    @Test
    void toStringOfVerticalLine() {
        Line<Double> line = new DoubleLine(1d);
        Assertions.assertEquals("y=1.0", line.toString());
    }

    @Test
    void compareToOfLineWithMB() {
        Line<Double> line = new DoubleLine(2d, 3d);
        Assertions.assertEquals(
            0d, line.compareTo(new DoubleLine(2d, 3d))
        );
        Assertions.assertEquals(
            -1d, line.compareTo(new DoubleLine(3d, 1d))
        );
        Assertions.assertEquals(
            1d, line.compareTo(new DoubleLine(2d, 1d))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new DoubleLine(0d), Line.class);
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
