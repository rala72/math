package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Line;
import io.rala.math.testUtils.arguments.LineArgumentsStreamFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.stream.Stream;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class BigDecimalLineTest {
    // region constructors, getter and setter

    @Test
    void constructorWithX() {
        assertLine(
            new BigDecimalLine(BigDecimal.ONE),
            null, BigDecimal.ONE
        );
    }

    @Test
    void constructorWithXAndMathContext5() {
        assertLine(
            new BigDecimalLine(
                BigDecimal.ONE,
                new MathContext(5)
            ), null, BigDecimal.ONE
        );
    }

    @Test
    void constructorWithMB() {
        assertLine(new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        ), BigDecimal.valueOf(2d), BigDecimal.valueOf(3d));
    }

    @Test
    void constructorWithMBAndMathContext5() {
        assertLine(new BigDecimalLine(
            BigDecimal.valueOf(2d),
            BigDecimal.valueOf(3d),
            new MathContext(5)
        ), BigDecimal.valueOf(2d), BigDecimal.valueOf(3d));
    }

    @Test
    void createAndSetM() {
        Line<BigDecimal> line = new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO);
        line.setM(BigDecimal.ONE);
        assertLine(line, BigDecimal.ONE, BigDecimal.ZERO);
    }

    @Test
    void createAndSetB() {
        Line<BigDecimal> line = new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO);
        line.setB(BigDecimal.valueOf(2d));
        assertLine(line, BigDecimal.ZERO, BigDecimal.valueOf(2d));
    }

    // endregion

    // region isHorizontal and isVertical

    @Test
    void isHorizontalOfHorizontalLine() {
        assertTrue(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ONE).isHorizontal()
        );
    }

    @Test
    void isHorizontalOfVerticalLine() {
        assertFalse(new BigDecimalLine(null, BigDecimal.ONE).isHorizontal());
    }

    @Test
    void isHorizontalOfM1B1Line() {
        assertFalse(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE).isHorizontal()
        );
    }

    @Test
    void isVerticalOfHorizontalLine() {
        assertFalse(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ONE).isVertical()
        );
    }

    @Test
    void isVerticalOfVerticalLine() {
        assertTrue(new BigDecimalLine(null, BigDecimal.ONE).isVertical());
    }

    @Test
    void isVerticalOfM1B1Line() {
        assertFalse(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE).isVertical()
        );
    }

    // endregion

    // region calculateX and calculateY

    @ParameterizedTest
    @MethodSource("getCalculateXArguments")
    void calculateX(double m, double b, double y, Double expected) {
        assertEquals(convertDoubleToBigDecimal(expected),
            new BigDecimalLine(
                convertDoubleToBigDecimal(m),
                convertDoubleToBigDecimal(b)
            ).calculateX(convertDoubleToBigDecimal(y))
        );
    }

    @ParameterizedTest
    @MethodSource("getCalculateYArguments")
    void calculateY(double m, double b, double x, Double expected) {
        assertEquals(convertDoubleToBigDecimal(expected),
            new BigDecimalLine(
                convertDoubleToBigDecimal(m),
                convertDoubleToBigDecimal(b)
            ).calculateY(convertDoubleToBigDecimal(x))
        );
    }

    // endregion

    // region normal

    @Test
    void normalM1B0() {
        assertLine(
            new BigDecimalLine(
                BigDecimal.ONE, BigDecimal.ZERO
            ).normal(),
            BigDecimal.ONE.negate(), BigDecimal.ZERO
        );
    }

    @Test
    void normalOfVerticalLine() {
        assertLine(
            new BigDecimalLine(BigDecimal.ZERO).normal(),
            BigDecimal.ZERO, BigDecimal.ZERO
        );
    }

    @Test
    void normalOfHorizontalLine() {
        assertLine(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO).normal(),
            null, BigDecimal.ZERO
        );
    }

    @Test
    void normalM1B1AndPointXY1() {
        assertLine(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE)
                .normal(new BigDecimalPoint(BigDecimal.ONE)),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(2)
        );
    }

    @Test
    void normalM1B0AndPointXY1() {
        assertLine(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO)
                .normal(new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(2)
        );
    }

    @Test
    void normalOfVerticalLineAndPointX0Y1() {
        assertLine(
            new BigDecimalLine(BigDecimal.ZERO)
                .normal(new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE)),
            BigDecimal.ZERO, BigDecimal.ONE
        );
    }

    @Test
    void normalOfHorizontalLineAndPointX1Y0() {
        assertLine(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO)
                .normal(new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO)),
            null, BigDecimal.ONE
        );
    }

    // endregion

    // region intersection

    @Test
    void hasIntersectionWithEqualM() {
        assertFalse(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .hasIntersection(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO))
        );
    }

    @Test
    void hasIntersectionWithLineM1B2AndM2B1() {
        assertTrue(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .hasIntersection(
                    new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.ONE)
                )
        );
    }

    @Test
    void hasIntersectionWithLineX1AndX2() {
        assertFalse(
            new BigDecimalLine(BigDecimal.ONE)
                .hasIntersection(new BigDecimalLine(BigDecimal.valueOf(2d)))
        );
    }

    @Test
    void hasIntersectionWithLineM1B2AndX1() {
        assertTrue(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .hasIntersection(new BigDecimalLine(BigDecimal.ONE))
        );
    }

    @Test
    void intersectionWithEqualM() {
        assertNull(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .intersection(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO))
        );
    }

    @Test
    void intersectionWithLineM1B2AndM2B1() {
        assertPoint(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .intersection(
                    new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.ONE)
                ),
            BigDecimal.ONE, BigDecimal.valueOf(3d)
        );
    }

    @Test
    void intersectionWithLineX1AndX2() {
        assertNull(
            new BigDecimalLine(BigDecimal.ONE)
                .intersection(new BigDecimalLine(BigDecimal.valueOf(2d)))
        );
    }

    @Test
    void intersectionWithLineM1B2AndX1() {
        assertPoint(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .intersection(new BigDecimalLine(BigDecimal.ONE)),
            BigDecimal.ONE, BigDecimal.valueOf(3d)
        );
    }

    @Test
    void intersectionWithLineX1AndM1B2() {
        assertPoint(
            new BigDecimalLine(BigDecimal.ONE)
                .intersection(
                    new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                ),
            BigDecimal.ONE, BigDecimal.valueOf(3d)
        );
    }

    @Test
    void intersectionAngleWithLineM1B2AndM2B1() {
        assertEquals(BigDecimal.valueOf(0.3217505544),
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .intersectionAngle(
                    new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.ONE)
                )
        );
    }

    @Test
    void intersectionAngleWithLineX1AndX2() {
        assertNull(
            new BigDecimalLine(BigDecimal.ONE)
                .intersectionAngle(new BigDecimalLine(BigDecimal.valueOf(2d)))
        );
    }

    @Test
    void intersectionAngleWithLineM1B2AndX1() {
        assertEquals(BigDecimal.valueOf(0.7853981636),
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .intersectionAngle(new BigDecimalLine(BigDecimal.ONE))
        );
    }

    @Test
    void intersectionAngleWithLineX1AndM1B2() {
        assertEquals(BigDecimal.valueOf(0.7853981636),
            new BigDecimalLine(BigDecimal.ONE)
                .intersectionAngle(
                    new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                )
        );
    }

    // endregion

    // region hasPoint

    @Test
    void hasPointWithM1B0AndPointXY1() {
        assertTrue(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO)
                .hasPoint(new BigDecimalPoint(BigDecimal.ONE))
        );
    }

    @Test
    void hasPointWithM1B1AndPointXY1() {
        assertFalse(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE)
                .hasPoint(new BigDecimalPoint(BigDecimal.ONE))
        );
    }

    @Test
    void hasPointWithVerticalLine0AndPointX0Y1() {
        assertTrue(
            new BigDecimalLine(BigDecimal.ZERO)
                .hasPoint(new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE))
        );
    }

    @Test
    void hasPointWithVerticalLine0AndPointXY1() {
        assertFalse(
            new BigDecimalLine(BigDecimal.ZERO)
                .hasPoint(new BigDecimalPoint(BigDecimal.ONE))
        );
    }

    // endregion

    // region toLineSegment

    @Test
    void toLineSegmentUsingXOfLineWithM0B1() {
        assertLineSegment(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ONE)
                .toLineSegmentUsingX(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
    }

    @Test
    void toLineSegmentUsingXOfLineWithM1B1() {
        assertLineSegment(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE)
                .toLineSegmentUsingX(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B0() {
        assertLineSegment(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO)
                .toLineSegmentUsingY(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B1() {
        assertLineSegment(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE)
                .toLineSegmentUsingY(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE.negate(), BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE)
        );
    }

    // endregion

    // region map, isValid, copy

    @Test
    void mapOfLineWithM0_5B1_5() {
        BigDecimalLine line = new BigDecimalLine(
            BigDecimal.valueOf(0.5), BigDecimal.valueOf(1.5)
        );
        Line<Integer> result = new Line<>(new IntegerArithmetic(), 0, 1);
        assertEquals(result,
            line.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZeroValues() {
        assertTrue(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO).isValid()
        );
    }

    @Test
    void copyOfLieWithMB() {
        Line<BigDecimal> line = new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        assertEquals(line, line.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfLineWithMB() {
        Line<BigDecimal> line = new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        assertEquals(
            line,
            new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d))
        );
        assertNotEquals(
            line,
            new BigDecimalLine(BigDecimal.valueOf(3d), BigDecimal.valueOf(2d))
        );
    }

    @Test
    void hashCodeOfLineWithMB() {
        assertEquals(
            21143,
            new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)).hashCode()
        );
    }

    @Test
    void toStringOfLineWithMB() {
        Line<BigDecimal> line = new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        assertEquals("y=2.0*x+3.0", line.toString());
    }

    @Test
    void toStringOfVerticalLine() {
        Line<BigDecimal> line = new BigDecimalLine(BigDecimal.ONE);
        assertEquals("y=1", line.toString());
    }

    @Test
    void compareToOfLineWithMB() {
        Line<BigDecimal> line = new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        assertEquals(
            0, line.compareTo(new BigDecimalLine(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
            ))
        );
        assertEquals(
            -1, line.compareTo(new BigDecimalLine(
                BigDecimal.valueOf(3d), BigDecimal.ONE
            ))
        );
        assertEquals(
            1, line.compareTo(new BigDecimalLine(
                BigDecimal.valueOf(2d), BigDecimal.ONE
            ))
        );
    }

    @Test
    void serializable() {
        assertSerializable(
            new BigDecimalLine(BigDecimal.ZERO),
            Line.class
        );
    }

    // endregion


    // region argument streams

    private static Stream<Arguments> getCalculateXArguments() {
        return LineArgumentsStreamFactory.calculateX();
    }

    private static Stream<Arguments> getCalculateYArguments() {
        return LineArgumentsStreamFactory.calculateY();
    }

    private static BigDecimal convertDoubleToBigDecimal(Double d) {
        return d == null ? null : new BigDecimal(
            BigDecimal.valueOf(d).stripTrailingZeros().toPlainString()
        );
    }

    // endregion
}
