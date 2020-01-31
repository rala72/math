package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Line;
import io.rala.math.testUtils.arguments.LineArgumentsStreamFactory;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.stream.Stream;

class BigDecimalLineTest {
    // region constructors, getter and setter

    @Test
    void constructorWithX() {
        GeometryAssertions.assertLine(
            new BigDecimalLine(BigDecimal.ONE),
            null, BigDecimal.ONE
        );
    }

    @Test
    void constructorWithXAndMathContext5() {
        GeometryAssertions.assertLine(
            new BigDecimalLine(
                BigDecimal.ONE,
                new MathContext(5)
            ), null, BigDecimal.ONE
        );
    }

    @Test
    void constructorWithMB() {
        GeometryAssertions.assertLine(new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        ), BigDecimal.valueOf(2d), BigDecimal.valueOf(3d));
    }

    @Test
    void constructorWithMBAndMathContext5() {
        GeometryAssertions.assertLine(new BigDecimalLine(
            BigDecimal.valueOf(2d),
            BigDecimal.valueOf(3d),
            new MathContext(5)
        ), BigDecimal.valueOf(2d), BigDecimal.valueOf(3d));
    }

    @Test
    void createAndSetM() {
        Line<BigDecimal> line = new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO);
        line.setM(BigDecimal.ONE);
        GeometryAssertions.assertLine(line, BigDecimal.ONE, BigDecimal.ZERO);
    }

    @Test
    void createAndSetB() {
        Line<BigDecimal> line = new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO);
        line.setB(BigDecimal.valueOf(2d));
        GeometryAssertions.assertLine(line, BigDecimal.ZERO, BigDecimal.valueOf(2d));
    }

    // endregion

    // region isHorizontal and isVertical

    @Test
    void isHorizontalOfHorizontalLine() {
        Assertions.assertTrue(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ONE).isHorizontal()
        );
    }

    @Test
    void isHorizontalOfVerticalLine() {
        Assertions.assertFalse(new BigDecimalLine(null, BigDecimal.ONE).isHorizontal());
    }

    @Test
    void isHorizontalOfM1B1Line() {
        Assertions.assertFalse(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE).isHorizontal()
        );
    }

    @Test
    void isVerticalOfHorizontalLine() {
        Assertions.assertFalse(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ONE).isVertical()
        );
    }

    @Test
    void isVerticalOfVerticalLine() {
        Assertions.assertTrue(new BigDecimalLine(null, BigDecimal.ONE).isVertical());
    }

    @Test
    void isVerticalOfM1B1Line() {
        Assertions.assertFalse(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE).isVertical()
        );
    }

    // endregion

    // region calculateX and calculateY

    @ParameterizedTest
    @MethodSource("getCalculateXArguments")
    void calculateX(double m, double b, double y, Double expected) {
        Assertions.assertEquals(convertDoubleToBigDecimal(expected),
            new BigDecimalLine(
                convertDoubleToBigDecimal(m),
                convertDoubleToBigDecimal(b)
            ).calculateX(convertDoubleToBigDecimal(y))
        );
    }

    @ParameterizedTest
    @MethodSource("getCalculateYArguments")
    void calculateY(double m, double b, double x, Double expected) {
        Assertions.assertEquals(convertDoubleToBigDecimal(expected),
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
        GeometryAssertions.assertLine(
            new BigDecimalLine(
                BigDecimal.ONE, BigDecimal.ZERO
            ).normal(),
            BigDecimal.ONE.negate(), BigDecimal.ZERO
        );
    }

    @Test
    void normalOfVerticalLine() {
        GeometryAssertions.assertLine(
            new BigDecimalLine(BigDecimal.ZERO).normal(),
            BigDecimal.ZERO, BigDecimal.ZERO
        );
    }

    @Test
    void normalOfHorizontalLine() {
        GeometryAssertions.assertLine(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO).normal(),
            null, BigDecimal.ZERO
        );
    }

    @Test
    void normalM1B1AndPointXY1() {
        GeometryAssertions.assertLine(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE)
                .normal(new BigDecimalPoint(BigDecimal.ONE)),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(2)
        );
    }

    @Test
    void normalM1B0AndPointXY1() {
        GeometryAssertions.assertLine(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO)
                .normal(new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(2)
        );
    }

    @Test
    void normalOfVerticalLineAndPointX0Y1() {
        GeometryAssertions.assertLine(
            new BigDecimalLine(BigDecimal.ZERO)
                .normal(new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE)),
            BigDecimal.ZERO, BigDecimal.ONE
        );
    }

    @Test
    void normalOfHorizontalLineAndPointX1Y0() {
        GeometryAssertions.assertLine(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO)
                .normal(new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO)),
            null, BigDecimal.ONE
        );
    }

    // endregion

    // region intersection

    @Test
    void hasIntersectionWithEqualM() {
        Assertions.assertFalse(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .hasIntersection(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO))
        );
    }

    @Test
    void hasIntersectionWithLineM1B2AndM2B1() {
        Assertions.assertTrue(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .hasIntersection(
                    new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.ONE)
                )
        );
    }

    @Test
    void hasIntersectionWithLineX1AndX2() {
        Assertions.assertFalse(
            new BigDecimalLine(BigDecimal.ONE)
                .hasIntersection(new BigDecimalLine(BigDecimal.valueOf(2d)))
        );
    }

    @Test
    void hasIntersectionWithLineM1B2AndX1() {
        Assertions.assertTrue(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .hasIntersection(new BigDecimalLine(BigDecimal.ONE))
        );
    }

    @Test
    void intersectionWithEqualM() {
        Assertions.assertNull(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .intersection(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO))
        );
    }

    @Test
    void intersectionWithLineM1B2AndM2B1() {
        GeometryAssertions.assertPoint(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .intersection(
                    new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.ONE)
                ),
            BigDecimal.ONE, BigDecimal.valueOf(3d)
        );
    }

    @Test
    void intersectionWithLineX1AndX2() {
        Assertions.assertNull(
            new BigDecimalLine(BigDecimal.ONE)
                .intersection(new BigDecimalLine(BigDecimal.valueOf(2d)))
        );
    }

    @Test
    void intersectionWithLineM1B2AndX1() {
        GeometryAssertions.assertPoint(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .intersection(new BigDecimalLine(BigDecimal.ONE)),
            BigDecimal.ONE, BigDecimal.valueOf(3d)
        );
    }

    @Test
    void intersectionWithLineX1AndM1B2() {
        GeometryAssertions.assertPoint(
            new BigDecimalLine(BigDecimal.ONE)
                .intersection(
                    new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                ),
            BigDecimal.ONE, BigDecimal.valueOf(3d)
        );
    }

    @Test
    void intersectionAngleWithLineM1B2AndM2B1() {
        Assertions.assertEquals(BigDecimal.valueOf(0.3217505544),
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .intersectionAngle(
                    new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.ONE)
                )
        );
    }

    @Test
    void intersectionAngleWithLineX1AndX2() {
        Assertions.assertNull(
            new BigDecimalLine(BigDecimal.ONE)
                .intersectionAngle(new BigDecimalLine(BigDecimal.valueOf(2d)))
        );
    }

    @Test
    void intersectionAngleWithLineM1B2AndX1() {
        Assertions.assertEquals(BigDecimal.valueOf(0.7853981636),
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .intersectionAngle(new BigDecimalLine(BigDecimal.ONE))
        );
    }

    @Test
    void intersectionAngleWithLineX1AndM1B2() {
        Assertions.assertEquals(BigDecimal.valueOf(0.7853981636),
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
        Assertions.assertTrue(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO)
                .hasPoint(new BigDecimalPoint(BigDecimal.ONE))
        );
    }

    @Test
    void hasPointWithM1B1AndPointXY1() {
        Assertions.assertFalse(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE)
                .hasPoint(new BigDecimalPoint(BigDecimal.ONE))
        );
    }

    @Test
    void hasPointWithVerticalLine0AndPointX0Y1() {
        Assertions.assertTrue(
            new BigDecimalLine(BigDecimal.ZERO)
                .hasPoint(new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE))
        );
    }

    @Test
    void hasPointWithVerticalLine0AndPointXY1() {
        Assertions.assertFalse(
            new BigDecimalLine(BigDecimal.ZERO)
                .hasPoint(new BigDecimalPoint(BigDecimal.ONE))
        );
    }

    // endregion

    // region toLineSegment

    @Test
    void toLineSegmentUsingXOfLineWithM0B1() {
        GeometryAssertions.assertLineSegment(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ONE)
                .toLineSegmentUsingX(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
    }

    @Test
    void toLineSegmentUsingXOfLineWithM1B1() {
        GeometryAssertions.assertLineSegment(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE)
                .toLineSegmentUsingX(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B0() {
        GeometryAssertions.assertLineSegment(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO)
                .toLineSegmentUsingY(BigDecimal.ZERO, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE)
        );
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B1() {
        GeometryAssertions.assertLineSegment(
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
        Assertions.assertEquals(result,
            line.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO).isValid()
        );
    }

    @Test
    void copyOfLieWithMB() {
        Line<BigDecimal> line = new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals(line, line.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfLineWithMB() {
        Line<BigDecimal> line = new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals(
            line,
            new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d))
        );
        Assertions.assertNotEquals(
            line,
            new BigDecimalLine(BigDecimal.valueOf(3d), BigDecimal.valueOf(2d))
        );
    }

    @Test
    void hashCodeOfLineWithMB() {
        Assertions.assertEquals(
            21143,
            new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)).hashCode()
        );
    }

    @Test
    void toStringOfLineWithMB() {
        Line<BigDecimal> line = new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals("y=2.0*x+3.0", line.toString());
    }

    @Test
    void toStringOfVerticalLine() {
        Line<BigDecimal> line = new BigDecimalLine(BigDecimal.ONE);
        Assertions.assertEquals("y=1", line.toString());
    }

    @Test
    void compareToOfLineWithMB() {
        Line<BigDecimal> line = new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals(
            0, line.compareTo(new BigDecimalLine(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
            ))
        );
        Assertions.assertEquals(
            -1, line.compareTo(new BigDecimalLine(
                BigDecimal.valueOf(3d), BigDecimal.ONE
            ))
        );
        Assertions.assertEquals(
            1, line.compareTo(new BigDecimalLine(
                BigDecimal.valueOf(2d), BigDecimal.ONE
            ))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
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
