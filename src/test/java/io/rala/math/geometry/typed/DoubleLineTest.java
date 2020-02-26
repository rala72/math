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
import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(new DoubleLine(0d, 1d).isHorizontal());
    }

    @Test
    void isHorizontalOfVerticalLine() {
        assertFalse(new DoubleLine(Double.NaN, 1d).isHorizontal());
    }

    @Test
    void isHorizontalOfM1B1Line() {
        assertFalse(new DoubleLine(1d, 1d).isHorizontal());
    }

    @Test
    void isVerticalOfHorizontalLine() {
        assertFalse(new DoubleLine(0d, 1d).isVertical());
    }

    @Test
    void isVerticalOfVerticalLine() {
        assertTrue(new DoubleLine(null, 1d).isVertical());
    }

    @Test
    void isVerticalOfM1B1Line() {
        assertFalse(new DoubleLine(1d, 1d).isVertical());
    }

    // endregion

    // region calculateX and calculateY

    @ParameterizedTest
    @MethodSource("getCalculateXArguments")
    void calculateX(double m, double b, double y, Double expected) {
        assertEquals(expected, new DoubleLine(m, b).calculateX(y));
    }

    @ParameterizedTest
    @MethodSource("getCalculateYArguments")
    void calculateY(double m, double b, double x, Double expected) {
        assertEquals(expected, new DoubleLine(m, b).calculateY(x));
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
        assertFalse(
            new DoubleLine(1d, 2d).hasIntersection(new DoubleLine(1d, 0d))
        );
    }

    @Test
    void hasIntersectionWithLineM1B2AndM2B1() {
        assertTrue(
            new DoubleLine(1d, 2d).hasIntersection(new DoubleLine(2d, 1d))
        );
    }

    @Test
    void hasIntersectionWithLineX1AndX2() {
        assertFalse(new DoubleLine(1d).hasIntersection(new DoubleLine(2d)));
    }

    @Test
    void hasIntersectionWithLineM1B2AndX1() {
        assertTrue(
            new DoubleLine(1d, 2d).hasIntersection(new DoubleLine(1d))
        );
    }

    @Test
    void intersectionWithEqualM() {
        assertNull(
            new DoubleLine(1d, 2d).intersection(new DoubleLine(1d, 0d))
        );
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
        assertNull(new DoubleLine(1d).intersection(new DoubleLine(2d)));
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
        assertEquals(0.3217505543966422d,
            new DoubleLine(1d, 2d).intersectionAngle(new DoubleLine(2d, 1d))
        );
    }

    @Test
    void intersectionAngleWithLineX1AndX2() {
        assertNull(
            new DoubleLine(1d).intersectionAngle(new DoubleLine(2d))
        );
    }

    @Test
    void intersectionAngleWithLineM1B2AndX1() {
        assertEquals(0.7853981633974483d,
            new DoubleLine(1d, 2d).intersectionAngle(new DoubleLine(1d))
        );
    }

    @Test
    void intersectionAngleWithLineX1AndM1B2() {
        assertEquals(0.7853981633974483d,
            new DoubleLine(1d).intersectionAngle(new DoubleLine(1d, 2d))
        );
    }

    // endregion

    // region hasPoint

    @Test
    void hasPointWithM1B0AndPointXY1() {
        assertTrue(new DoubleLine(1d, 0d).hasPoint(new DoublePoint(1d)));
    }

    @Test
    void hasPointWithM1B1AndPointXY1() {
        assertFalse(new DoubleLine(1d, 1d).hasPoint(new DoublePoint(1d)));
    }

    @Test
    void hasPointWithVerticalLine0AndPointX0Y1() {
        assertTrue(new DoubleLine(0d).hasPoint(new DoublePoint(0d, 1d)));
    }

    @Test
    void hasPointWithVerticalLine0AndPointXY1() {
        assertFalse(new DoubleLine(0d).hasPoint(new DoublePoint(1d)));
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
        assertEquals(result,
            line.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZeroValues() {
        assertTrue(new DoubleLine(0d, 0d).isValid());
    }

    @Test
    void isValidWithInfValues() {
        assertFalse(
            new DoubleLine(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void copyOfLieWithMB() {
        Line<Double> line = new DoubleLine(2d, 3d);
        assertEquals(line, line.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfLineWithMB() {
        Line<Double> line = new DoubleLine(2d, 3d);
        assertEquals(
            line,
            new DoubleLine(2d, 3d)
        );
        assertNotEquals(
            line,
            new DoubleLine(3d, 2d)
        );
    }

    @Test
    void hashCodeOfLineWithMB() {
        assertEquals(
            525249,
            new DoubleLine(2d, 3d).hashCode()
        );
    }

    @Test
    void toStringOfLineWithMB() {
        Line<Double> line = new DoubleLine(2d, 3d);
        assertEquals("y=2.0*x+3.0", line.toString());
    }

    @Test
    void toStringOfVerticalLine() {
        Line<Double> line = new DoubleLine(1d);
        assertEquals("y=1.0", line.toString());
    }

    @Test
    void compareToOfLineWithMB() {
        Line<Double> line = new DoubleLine(2d, 3d);
        assertEquals(
            0d, line.compareTo(new DoubleLine(2d, 3d))
        );
        assertEquals(
            -1d, line.compareTo(new DoubleLine(3d, 1d))
        );
        assertEquals(
            1d, line.compareTo(new DoubleLine(2d, 1d))
        );
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleLine(0d), Line.class);
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
