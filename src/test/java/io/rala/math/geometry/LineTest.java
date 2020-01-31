package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.arguments.LineArgumentsStreamFactory;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import io.rala.math.testUtils.geometry.TestLine;
import io.rala.math.testUtils.geometry.TestPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class LineTest {
    // region constructors, getter and setter

    @Test
    void constructorWithX() {
        GeometryAssertions.assertLine(new TestLine(1), null, 1);
    }

    @Test
    void constructorWithMB() {
        GeometryAssertions.assertLine(new TestLine(2, 3), 2, 3);
    }

    @Test
    void createAndSetM() {
        Line<Number> line = new TestLine(0, 0);
        line.setM(1);
        GeometryAssertions.assertLine(line, 1, 0);
    }

    @Test
    void createAndSetB() {
        Line<Number> line = new TestLine(0, 0);
        line.setB(2);
        GeometryAssertions.assertLine(line, 0, 2);
    }

    // endregion

    // region isHorizontal and isVertical

    @Test
    void isHorizontalOfHorizontalLine() {
        Assertions.assertTrue(new TestLine(0d, 1d).isHorizontal());
    }

    @Test
    void isHorizontalOfVerticalLine() {
        Assertions.assertFalse(new TestLine(Double.NaN, 1).isHorizontal());
    }

    @Test
    void isHorizontalOfM1B1Line() {
        Assertions.assertFalse(new TestLine(1, 1).isHorizontal());
    }

    @Test
    void isVerticalOfHorizontalLine() {
        Assertions.assertFalse(new TestLine(0, 1).isVertical());
    }

    @Test
    void isVerticalOfVerticalLine() {
        Assertions.assertTrue(new TestLine(null, 1d).isVertical());
    }

    @Test
    void isVerticalOfM1B1Line() {
        Assertions.assertFalse(new TestLine(1, 1).isVertical());
    }

    // endregion

    // region calculateX and calculateY

    @ParameterizedTest
    @MethodSource("getCalculateXArguments")
    void calculateX(double m, double b, double y, Double expected) {
        Assertions.assertEquals(expected, new TestLine(m, b).calculateX(y));
    }

    @ParameterizedTest
    @MethodSource("getCalculateYArguments")
    void calculateY(double m, double b, double x, Double expected) {
        Assertions.assertEquals(expected, new TestLine(m, b).calculateY(x));
    }

    // endregion

    // region normal

    @Test
    void normalM1B0() {
        GeometryAssertions.assertLine(new TestLine(1, 0).normal(), -1d, 0);
    }

    @Test
    void normalOfVerticalLine() {
        GeometryAssertions.assertLine(new TestLine(0).normal(), 0d, 0);
    }

    @Test
    void normalOfHorizontalLine() {
        GeometryAssertions.assertLine(new TestLine(0d, 0).normal(), null, 0);
    }

    @Test
    void normalM1B1AndPointXY1() {
        GeometryAssertions.assertLine(
            new TestLine(1, 1).normal(new TestPoint(1)),
            -1d, 2d
        );
    }

    @Test
    void normalM1B0AndPointXY1() {
        GeometryAssertions.assertLine(
            new TestLine(1, 0).normal(new TestPoint(1, 1)),
            -1d, 2d
        );
    }

    @Test
    void normalOfVerticalLineAndPointX0Y1() {
        GeometryAssertions.assertLine(
            new TestLine(0).normal(new TestPoint(0, 1)),
            0d, 1d
        );
    }

    @Test
    void normalOfHorizontalLineAndPointX1Y0() {
        GeometryAssertions.assertLine(
            new TestLine(0d, 0).normal(new TestPoint(1, 0)),
            null, 1
        );
    }

    // endregion

    // region intersection

    @Test
    void hasIntersectionWithEqualM() {
        Assertions.assertFalse(new TestLine(1, 2).hasIntersection(new TestLine(1, 0)));
    }

    @Test
    void hasIntersectionWithLineM1B2AndM2B1() {
        Assertions.assertTrue(new TestLine(1, 2).hasIntersection(new TestLine(2, 1)));
    }

    @Test
    void hasIntersectionWithLineX1AndX2() {
        Assertions.assertFalse(new TestLine(1).hasIntersection(new TestLine(2)));
    }

    @Test
    void hasIntersectionWithLineM1B2AndX1() {
        Assertions.assertTrue(new TestLine(1, 2).hasIntersection(new TestLine(1)));
    }

    @Test
    void intersectionWithEqualM() {
        Assertions.assertNull(new TestLine(1, 2).intersection(new TestLine(1, 0)));
    }

    @Test
    void intersectionWithLineM1B2AndM2B1() {
        GeometryAssertions.assertPoint(
            new TestLine(1, 2).intersection(new TestLine(2, 1)),
            1, 3
        );
    }

    @Test
    void intersectionWithLineX1AndX2() {
        Assertions.assertNull(new TestLine(1).intersection(new TestLine(2)));
    }

    @Test
    void intersectionWithLineM1B2AndX1() {
        GeometryAssertions.assertPoint(
            new TestLine(1, 2).intersection(new TestLine(1)),
            1, 3
        );
    }

    @Test
    void intersectionWithLineX1AndM1B2() {
        GeometryAssertions.assertPoint(
            new TestLine(1).intersection(new TestLine(1, 2)),
            1, 3
        );
    }

    @Test
    void intersectionAngleWithLineM1B2AndM2B1() {
        Assertions.assertEquals(0.3217505543966422,
            new TestLine(1, 2).intersectionAngle(new TestLine(2, 1))
        );
    }

    @Test
    void intersectionAngleWithLineX1AndX2() {
        Assertions.assertNull(
            new TestLine(1).intersectionAngle(new TestLine(2))
        );
    }

    @Test
    void intersectionAngleWithLineM1B2AndX1() {
        Assertions.assertEquals(0.7853981633974483,
            new TestLine(1, 2).intersectionAngle(new TestLine(1))
        );
    }

    @Test
    void intersectionAngleWithLineX1AndM1B2() {
        Assertions.assertEquals(0.7853981633974483,
            new TestLine(1).intersectionAngle(new TestLine(1, 2))
        );
    }

    // endregion

    // region hasPoint

    @Test
    void hasPointWithM1B0AndPointXY1() {
        Assertions.assertTrue(new TestLine(1d, 0d).hasPoint(new TestPoint(1d)));
    }

    @Test
    void hasPointWithM1B1AndPointXY1() {
        Assertions.assertFalse(new TestLine(1, 1).hasPoint(new TestPoint(1)));
    }

    @Test
    void hasPointWithVerticalLine0AndPointX0Y1() {
        Assertions.assertTrue(new TestLine(0).hasPoint(new TestPoint(0, 1)));
    }

    @Test
    void hasPointWithVerticalLine0AndPointXY1() {
        Assertions.assertFalse(new TestLine(0).hasPoint(new TestPoint(1)));
    }

    // endregion

    // region toLineSegment

    @Test
    void toLineSegmentUsingXOfLineWithM0B1() {
        GeometryAssertions.assertLineSegment(
            new TestLine(0, 1).toLineSegmentUsingX(0, 1),
            new TestPoint(0, 1), new TestPoint(1, 1)
        );
    }

    @Test
    void toLineSegmentUsingXOfLineWithM1B1() {
        GeometryAssertions.assertLineSegment(
            new TestLine(1, 1).toLineSegmentUsingX(0, 1),
            new TestPoint(0, 1), new TestPoint(1, 2)
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B0() {
        GeometryAssertions.assertLineSegment(
            new TestLine(1, 0).toLineSegmentUsingY(0, 1),
            new TestPoint(0, 0), new TestPoint(1, 1)
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B1() {
        GeometryAssertions.assertLineSegment(
            new TestLine(1, 1).toLineSegmentUsingY(0, 1),
            new TestPoint(-1, 0), new TestPoint(0, 1)
        );
    }

    // endregion

    // region map, isValid, copy

    @Test
    void mapOfLineWithM0_5B1_5() {
        TestLine line = new TestLine(0.5, 1.5);
        Line<Integer> result = new Line<>(new IntegerArithmetic(), 0, 1);
        Assertions.assertEquals(result,
            line.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new TestLine(0d, 0d).isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new TestLine(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void copyOfLieWithMB() {
        Line<Number> line = new TestLine(2, 3);
        Assertions.assertEquals(line, line.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfLineWithMB() {
        Line<Number> line = new TestLine(2, 3);
        Assertions.assertEquals(
            line,
            new TestLine(2, 3)
        );
        Assertions.assertNotEquals(
            line,
            new TestLine(3, 2)
        );
    }

    @Test
    void hashCodeOfLineWithMB() {
        Assertions.assertEquals(
            1026,
            new TestLine(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfLineWithMB() {
        Line<Number> line = new TestLine(2d, 3d);
        Assertions.assertEquals("y=2.0*x+3.0", line.toString());
    }

    @Test
    void toStringOfVerticalLine() {
        Line<Number> line = new TestLine(1d);
        Assertions.assertEquals("y=1.0", line.toString());
    }

    @Test
    void compareToOfLineWithMB() {
        Line<Number> line = new TestLine(2, 3);
        Assertions.assertEquals(
            0, line.compareTo(new TestLine(2, 3))
        );
        Assertions.assertEquals(
            -1, line.compareTo(new TestLine(3, 1))
        );
        Assertions.assertEquals(
            1, line.compareTo(new TestLine(2, 1))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new TestLine(0), Line.class);
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
