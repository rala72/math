package io.rala.math.geometry;

import io.rala.math.testUtils.arguments.LineArgumentsStreamFactory;
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
        assertLine(new Line(1), Double.NaN, 1);
    }

    @Test
    void constructorWithMB() {
        assertLine(new Line(2, 3), 2, 3);
    }

    @Test
    void createAndSetM() {
        Line line = new Line(0, 0);
        line.setM(1);
        assertLine(line, 1, 0);
    }

    @Test
    void createAndSetB() {
        Line line = new Line(0, 0);
        line.setB(2);
        assertLine(line, 0, 2);
    }

    // endregion

    // region isHorizontal and isVertical

    @Test
    void isHorizontalOfHorizontalLine() {
        Assertions.assertTrue(new Line(0, 1).isHorizontal());
    }

    @Test
    void isHorizontalOfVerticalLine() {
        Assertions.assertFalse(new Line(Double.NaN, 1).isHorizontal());
    }

    @Test
    void isHorizontalOfM1B1Line() {
        Assertions.assertFalse(new Line(1, 1).isHorizontal());
    }

    @Test
    void isVerticalOfHorizontalLine() {
        Assertions.assertFalse(new Line(0, 1).isVertical());
    }

    @Test
    void isVerticalOfVerticalLine() {
        Assertions.assertTrue(new Line(Double.NaN, 1).isVertical());
    }

    @Test
    void isVerticalOfM1B1Line() {
        Assertions.assertFalse(new Line(1, 1).isVertical());
    }

    // endregion

    // region calculateX and calculateY

    @ParameterizedTest
    @MethodSource("getCalculateXArguments")
    void calculateX(double m, double b, double y, double expected) {
        Assertions.assertEquals(expected, new Line(m, b).calculateX(y));
    }

    @ParameterizedTest
    @MethodSource("getCalculateYArguments")
    void calculateY(double m, double b, double x, double expected) {
        Assertions.assertEquals(expected, new Line(m, b).calculateY(x));
    }

    // endregion

    // region intersection

    @Test
    void hasIntersectionWithEqualM() {
        Assertions.assertFalse(new Line(1, 2).hasIntersection(new Line(1, 0)));
    }

    @Test
    void hasIntersectionWithLineM1B2AndM2B1() {
        Assertions.assertTrue(new Line(1, 2).hasIntersection(new Line(2, 1)));
    }

    @Test
    void hasIntersectionWithLineX1AndX2() {
        Assertions.assertFalse(new Line(1).hasIntersection(new Line(2)));
    }

    @Test
    void hasIntersectionWithLineM1B2AndX1() {
        Assertions.assertTrue(new Line(1, 2).hasIntersection(new Line(1)));
    }

    @Test
    void intersectionWithEqualM() {
        Assertions.assertNull(new Line(1, 2).intersection(new Line(1, 0)));
    }

    @Test
    void intersectionWithLineM1B2AndM2B1() {
        Assertions.assertEquals(new Point(1, 3),
            new Line(1, 2).intersection(new Line(2, 1))
        );
    }

    @Test
    void intersectionWithLineX1AndX2() {
        Assertions.assertNull(new Line(1).intersection(new Line(2)));
    }

    @Test
    void intersectionWithLineM1B2AndX1() {
        Assertions.assertEquals(new Point(1, 3),
            new Line(1, 2).intersection(new Line(1))
        );
    }

    @Test
    void intersectionWithLineX1AndM1B2() {
        Assertions.assertEquals(new Point(1, 3),
            new Line(1).intersection(new Line(1, 2))
        );
    }

    @Test
    void intersectionAngleWithLineM1B2AndM2B1() {
        Assertions.assertEquals(0.3217505543966422,
            new Line(1, 2).intersectionAngle(new Line(2, 1))
        );
    }

    @Test
    void intersectionAngleWithLineX1AndX2() {
        Assertions.assertTrue(Double.isNaN(new Line(1).intersectionAngle(new Line(2))));
    }

    @Test
    void intersectionAngleWithLineM1B2AndX1() {
        Assertions.assertEquals(0.7853981633974483,
            new Line(1, 2).intersectionAngle(new Line(1))
        );
    }

    @Test
    void intersectionAngleWithLineX1AndM1B2() {
        Assertions.assertEquals(0.7853981633974483,
            new Line(1).intersectionAngle(new Line(1, 2))
        );
    }

    // endregion

    // region toLineSegment

    @Test
    void toLineSegmentUsingXOfLineWithM0B1() {
        Assertions.assertEquals(
            new LineSegment(new Point(0, 1), new Point(1, 1)),
            new Line(0, 1).toLineSegmentUsingX(0, 1)
        );
    }

    @Test
    void toLineSegmentUsingXOfLineWithM1B1() {
        Assertions.assertEquals(
            new LineSegment(new Point(0, 1), new Point(1, 2)),
            new Line(1, 1).toLineSegmentUsingX(0, 1)
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B0() {
        Assertions.assertEquals(
            new LineSegment(new Point(0, 0), new Point(1, 1)),
            new Line(1, 0).toLineSegmentUsingY(0, 1)
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B1() {
        Assertions.assertEquals(
            new LineSegment(new Point(-1, 0), new Point(0, 1)),
            new Line(1, 1).toLineSegmentUsingY(0, 1)
        );
    }

    // endregion

    // region isValid, copy

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new Line(0, 0).isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new Line(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void copyOfLieWithMB() {
        Line line = new Line(2, 3);
        Assertions.assertEquals(line, line.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfLineWithMB() {
        Line line = new Line(2, 3);
        Assertions.assertEquals(
            line,
            new Line(2, 3)
        );
        Assertions.assertNotEquals(
            line,
            new Line(3, 2)
        );
    }

    @Test
    void hashCodeOfLineWithMB() {
        Assertions.assertEquals(
            525249,
            new Line(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfLineWithMB() {
        Line line = new Line(2, 3);
        Assertions.assertEquals("y=2.0*x+3.0", line.toString());
    }

    @Test
    void toStringOfVerticalLine() {
        Line line = new Line(1);
        Assertions.assertEquals("y=1.0", line.toString());
    }

    @Test
    void compareToOfLineWithMB() {
        Line line = new Line(2, 3);
        Assertions.assertEquals(
            0, line.compareTo(new Line(2, 3))
        );
        Assertions.assertEquals(
            -1, line.compareTo(new Line(3, 1))
        );
        Assertions.assertEquals(
            1, line.compareTo(new Line(2, 1))
        );
    }

    // endregion


    // region assert

    private static void assertLine(Line line, double m, double b) {
        Assertions.assertEquals(m, line.getM(), "m is invalid");
        Assertions.assertEquals(b, line.getB(), "b is invalid");
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
