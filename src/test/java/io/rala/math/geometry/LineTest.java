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
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class LineTest {
    // region constructors, getter and setter

    @Test
    void constructorWithX() {
        assertLine(new TestLine(1), null, 1);
    }

    @Test
    void constructorWithMB() {
        assertLine(new TestLine(2, 3), 2, 3);
    }

    @Test
    void createAndSetM() {
        Line<Number> line = new TestLine(0, 0);
        line.setM(1);
        assertLine(line, 1, 0);
    }

    @Test
    void createAndSetB() {
        Line<Number> line = new TestLine(0, 0);
        line.setB(2);
        assertLine(line, 0, 2);
    }

    // endregion

    // region isHorizontal and isVertical

    @Test
    void isHorizontalOfHorizontalLine() {
        assertTrue(new TestLine(0d, 1d).isHorizontal());
    }

    @Test
    void isHorizontalOfVerticalLine() {
        assertFalse(new TestLine(Double.NaN, 1).isHorizontal());
    }

    @Test
    void isHorizontalOfM1B1Line() {
        assertFalse(new TestLine(1, 1).isHorizontal());
    }

    @Test
    void isVerticalOfHorizontalLine() {
        assertFalse(new TestLine(0, 1).isVertical());
    }

    @Test
    void isVerticalOfVerticalLine() {
        assertTrue(new TestLine(null, 1d).isVertical());
    }

    @Test
    void isVerticalOfM1B1Line() {
        assertFalse(new TestLine(1, 1).isVertical());
    }

    // endregion

    // region calculateX and calculateY

    @ParameterizedTest
    @MethodSource("getCalculateXArguments")
    void calculateX(double m, double b, double y, Double expected) {
        assertEquals(expected, new TestLine(m, b).calculateX(y));
    }

    @ParameterizedTest
    @MethodSource("getCalculateYArguments")
    void calculateY(double m, double b, double x, Double expected) {
        assertEquals(expected, new TestLine(m, b).calculateY(x));
    }

    // endregion

    // region normal

    @Test
    void normalM1B0() {
        assertLine(new TestLine(1, 0).normal(), -1d, 0);
    }

    @Test
    void normalOfVerticalLine() {
        assertLine(new TestLine(0).normal(), 0d, 0);
    }

    @Test
    void normalOfHorizontalLine() {
        assertLine(new TestLine(0d, 0).normal(), null, 0);
    }

    @Test
    void normalM1B1AndPointXY1() {
        assertLine(
            new TestLine(1, 1).normal(new TestPoint(1)),
            -1d, 2d
        );
    }

    @Test
    void normalM1B0AndPointXY1() {
        assertLine(
            new TestLine(1, 0).normal(new TestPoint(1, 1)),
            -1d, 2d
        );
    }

    @Test
    void normalOfVerticalLineAndPointX0Y1() {
        assertLine(
            new TestLine(0).normal(new TestPoint(0, 1)),
            0d, 1d
        );
    }

    @Test
    void normalOfHorizontalLineAndPointX1Y0() {
        assertLine(
            new TestLine(0d, 0).normal(new TestPoint(1, 0)),
            null, 1
        );
    }

    // endregion

    // region intersection

    @Test
    void hasIntersectionWithEqualM() {
        assertFalse(new TestLine(1, 2).hasIntersection(new TestLine(1, 0)));
    }

    @Test
    void hasIntersectionWithLineM1B2AndM2B1() {
        assertTrue(new TestLine(1, 2).hasIntersection(new TestLine(2, 1)));
    }

    @Test
    void hasIntersectionWithLineX1AndX2() {
        assertFalse(new TestLine(1).hasIntersection(new TestLine(2)));
    }

    @Test
    void hasIntersectionWithLineM1B2AndX1() {
        assertTrue(new TestLine(1, 2).hasIntersection(new TestLine(1)));
    }

    @Test
    void intersectionWithEqualM() {
        assertNull(new TestLine(1, 2).intersection(new TestLine(1, 0)));
    }

    @Test
    void intersectionWithLineM1B2AndM2B1() {
        assertPoint(
            new TestLine(1, 2).intersection(new TestLine(2, 1)),
            1, 3
        );
    }

    @Test
    void intersectionWithLineX1AndX2() {
        assertNull(new TestLine(1).intersection(new TestLine(2)));
    }

    @Test
    void intersectionWithLineM1B2AndX1() {
        assertPoint(
            new TestLine(1, 2).intersection(new TestLine(1)),
            1, 3
        );
    }

    @Test
    void intersectionWithLineX1AndM1B2() {
        assertPoint(
            new TestLine(1).intersection(new TestLine(1, 2)),
            1, 3
        );
    }

    @Test
    void intersectionAngleWithLineM1B2AndM2B1() {
        assertEquals(0.3217505543966422,
            new TestLine(1, 2).intersectionAngle(new TestLine(2, 1))
        );
    }

    @Test
    void intersectionAngleWithLineX1AndX2() {
        assertNull(
            new TestLine(1).intersectionAngle(new TestLine(2))
        );
    }

    @Test
    void intersectionAngleWithLineM1B2AndX1() {
        assertEquals(0.7853981633974483,
            new TestLine(1, 2).intersectionAngle(new TestLine(1))
        );
    }

    @Test
    void intersectionAngleWithLineX1AndM1B2() {
        assertEquals(0.7853981633974483,
            new TestLine(1).intersectionAngle(new TestLine(1, 2))
        );
    }

    // endregion

    // region hasPoint

    @Test
    void hasPointWithM1B0AndPointXY1() {
        assertTrue(new TestLine(1d, 0d).hasPoint(new TestPoint(1d)));
    }

    @Test
    void hasPointWithM1B1AndPointXY1() {
        assertFalse(new TestLine(1, 1).hasPoint(new TestPoint(1)));
    }

    @Test
    void hasPointWithVerticalLine0AndPointX0Y1() {
        assertTrue(new TestLine(0).hasPoint(new TestPoint(0, 1)));
    }

    @Test
    void hasPointWithVerticalLine0AndPointXY1() {
        assertFalse(new TestLine(0).hasPoint(new TestPoint(1)));
    }

    // endregion

    // region toLineSegment

    @Test
    void toLineSegmentUsingXOfLineWithM0B1() {
        assertLineSegment(
            new TestLine(0, 1).toLineSegmentUsingX(0, 1),
            new TestPoint(0, 1), new TestPoint(1, 1)
        );
    }

    @Test
    void toLineSegmentUsingXOfLineWithM1B1() {
        assertLineSegment(
            new TestLine(1, 1).toLineSegmentUsingX(0, 1),
            new TestPoint(0, 1), new TestPoint(1, 2)
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B0() {
        assertLineSegment(
            new TestLine(1, 0).toLineSegmentUsingY(0, 1),
            new TestPoint(0, 0), new TestPoint(1, 1)
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B1() {
        assertLineSegment(
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
        assertEquals(result,
            line.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZeroValues() {
        assertTrue(new TestLine(0d, 0d).isValid());
    }

    @Test
    void isValidWithInfValues() {
        assertFalse(
            new TestLine(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void copyOfLieWithMB() {
        Line<Number> line = new TestLine(2, 3);
        assertEquals(line, line.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfLineWithMB() {
        Line<Number> line = new TestLine(2, 3);
        assertEquals(line, new TestLine(2, 3));
        assertNotEquals(line, new TestLine(3, 2));
    }

    @Test
    void hashCodeOfLineWithMB() {
        assertEquals(1026, new TestLine(2, 3).hashCode());
    }

    @Test
    void toStringOfLineWithMB() {
        Line<Number> line = new TestLine(2d, 3d);
        assertEquals("y=2.0*x+3.0", line.toString());
    }

    @Test
    void toStringOfVerticalLine() {
        Line<Number> line = new TestLine(1d);
        assertEquals("y=1.0", line.toString());
    }

    @Test
    void compareToOfLineWithMB() {
        Line<Number> line = new TestLine(2, 3);
        assertEquals(0, line.compareTo(new TestLine(2, 3)));
        assertEquals(-1, line.compareTo(new TestLine(3, 1)));
        assertEquals(1, line.compareTo(new TestLine(2, 1)));

        assertEquals(1, line.compareTo(new TestLine(null, 1)));
        assertEquals(-1, new TestLine(null, 1).compareTo(line));
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
