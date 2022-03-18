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
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static io.rala.math.testUtils.assertion.utils.OffsetUtils.bigDecimalOffset;
import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalLineTest {
    // region constructors, getter and setter

    @Test
    void constructorWithX() {
        assertThatLine(new BigDecimalLine(BigDecimal.ONE))
            .hasM(null).hasB(BigDecimal.ONE);
    }

    @Test
    void constructorWithXAndMathContext5() {
        assertThatLine(new BigDecimalLine(
            BigDecimal.ONE,
            new MathContext(5)
        )).hasM(null).hasB(BigDecimal.ONE);
    }

    @Test
    void constructorWithMB() {
        assertThatLine(new BigDecimalLine(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        )).hasM(BigDecimal.valueOf(2)).hasB(BigDecimal.valueOf(3));
    }

    @Test
    void constructorWithMBAndMathContext5() {
        assertThatLine(new BigDecimalLine(
            BigDecimal.valueOf(2),
            BigDecimal.valueOf(3),
            new MathContext(5)
        )).hasM(BigDecimal.valueOf(2)).hasB(BigDecimal.valueOf(3));
    }

    @Test
    void createAndSetM() {
        Line<BigDecimal> line = new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO);
        line.setM(BigDecimal.ONE);
        assertThatLine(line).hasM(BigDecimal.ONE).hasB(BigDecimal.ZERO);
    }

    @Test
    void createAndSetB() {
        Line<BigDecimal> line = new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO);
        line.setB(BigDecimal.valueOf(2));
        assertThatLine(line).hasM(BigDecimal.ZERO).hasB(BigDecimal.valueOf(2));
    }

    // endregion

    // region isHorizontal and isVertical

    @Test
    void isHorizontalOfHorizontalLine() {
        assertThatLine(new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ONE)).isHorizontal();
    }

    @Test
    void isHorizontalOfVerticalLine() {
        assertThatLine(new BigDecimalLine(null, BigDecimal.ONE)).isNotHorizontal();
    }

    @Test
    void isHorizontalOfM1B1Line() {
        assertThatLine(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE)).isNotHorizontal();
    }

    @Test
    void isVerticalOfHorizontalLine() {
        assertThatLine(new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ONE)).isNotVertical();
    }

    @Test
    void isVerticalOfVerticalLine() {
        assertThatLine(new BigDecimalLine(null, BigDecimal.ONE)).isVertical();
    }

    @Test
    void isVerticalOfM1B1Line() {
        assertThatLine(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE)).isNotVertical();
    }

    // endregion

    // region calculateX and calculateY

    @ParameterizedTest
    @MethodSource("getCalculateXArguments")
    void calculateX(double m, double b, double y, Double expected) {
        assertThat(new BigDecimalLine(
            convertDoubleToBigDecimal(m),
            convertDoubleToBigDecimal(b)
        ).calculateX(convertDoubleToBigDecimal(y)))
            .isEqualTo(convertDoubleToBigDecimal(expected));
    }

    @ParameterizedTest
    @MethodSource("getCalculateYArguments")
    void calculateY(double m, double b, double x, Double expected) {
        assertThat(new BigDecimalLine(
            convertDoubleToBigDecimal(m),
            convertDoubleToBigDecimal(b)
        ).calculateY(convertDoubleToBigDecimal(x)))
            .isEqualTo(convertDoubleToBigDecimal(expected));
    }

    // endregion

    // region normal

    @Test
    void normalM1B0() {
        assertThatLine(
            new BigDecimalLine(
                BigDecimal.ONE, BigDecimal.ZERO
            ).normal()
        ).hasM(BigDecimal.ONE.negate()).hasB(BigDecimal.ZERO);
    }

    @Test
    void normalOfVerticalLine() {
        assertThatLine(
            new BigDecimalLine(BigDecimal.ZERO).normal()
        ).hasM(BigDecimal.ZERO).hasB(BigDecimal.ZERO);
    }

    @Test
    void normalOfHorizontalLine() {
        assertThatLine(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO).normal()
        ).hasM(null).hasB(BigDecimal.ZERO);
    }

    @Test
    void normalM1B1AndPointXY1() {
        assertThatLine(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE)
                .normal(new BigDecimalPoint(BigDecimal.ONE))
        ).hasM(BigDecimal.ONE.negate()).hasB(BigDecimal.valueOf(2));
    }

    @Test
    void normalM1B0AndPointXY1() {
        assertThatLine(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO)
                .normal(new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE))
        ).hasM(BigDecimal.ONE.negate()).hasB(BigDecimal.valueOf(2));
    }

    @Test
    void normalOfVerticalLineAndPointX0Y1() {
        assertThatLine(
            new BigDecimalLine(BigDecimal.ZERO)
                .normal(new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE))
        ).hasM(BigDecimal.ZERO).hasB(BigDecimal.ONE);
    }

    @Test
    void normalOfHorizontalLineAndPointX1Y0() {
        assertThatLine(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO)
                .normal(new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO))
        ).hasM(null).hasB(BigDecimal.ONE);
    }

    // endregion

    // region intersection

    @Test
    void hasIntersectionWithEqualM() {
        assertThatLine(new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2)))
            .hasNoIntersection(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO));
    }

    @Test
    void hasIntersectionWithLineM1B2AndM2B1() {
        assertThatLine(new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2)))
            .hasIntersection(new BigDecimalLine(BigDecimal.valueOf(2), BigDecimal.ONE));
    }

    @Test
    void hasIntersectionWithLineX1AndX2() {
        assertThatLine(new BigDecimalLine(BigDecimal.ONE))
            .hasNoIntersection(new BigDecimalLine(BigDecimal.valueOf(2)));
    }

    @Test
    void hasIntersectionWithLineM1B2AndX1() {
        assertThatLine(new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2)))
            .hasIntersection(new BigDecimalLine(BigDecimal.ONE));
    }

    @Test
    void intersectionWithEqualM() {
        assertThatPoint(new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2))
            .intersection(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO))).isNull();
    }

    @Test
    void intersectionWithLineM1B2AndM2B1() {
        assertThatPoint(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2))
                .intersection(
                    new BigDecimalLine(BigDecimal.valueOf(2), BigDecimal.ONE)
                )
        ).hasX(BigDecimal.ONE)
            .hasY(BigDecimal.valueOf(3));
    }

    @Test
    void intersectionWithLineX1AndX2() {
        assertThatPoint(new BigDecimalLine(BigDecimal.ONE)
            .intersection(new BigDecimalLine(BigDecimal.valueOf(2)))).isNull();
    }

    @Test
    void intersectionWithLineM1B2AndX1() {
        assertThatPoint(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2))
                .intersection(new BigDecimalLine(BigDecimal.ONE))
        ).hasX(BigDecimal.ONE)
            .hasY(BigDecimal.valueOf(3));
    }

    @Test
    void intersectionWithLineX1AndM1B2() {
        assertThatPoint(
            new BigDecimalLine(BigDecimal.ONE)
                .intersection(
                    new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2))
                )
        ).hasX(BigDecimal.ONE)
            .hasY(BigDecimal.valueOf(3));
    }

    @Test
    void intersectionAngleWithLineM1B2AndM2B1() {
        assertThat(new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2))
            .intersectionAngle(
                new BigDecimalLine(BigDecimal.valueOf(2), BigDecimal.ONE)
            )
        ).isEqualTo(BigDecimal.valueOf(0.3217505543966422));
    }

    @Test
    void intersectionAngleWithLineX1AndX2() {
        assertThat(new BigDecimalLine(BigDecimal.ONE)
            .intersectionAngle(new BigDecimalLine(BigDecimal.valueOf(2)))).isNull();
    }

    @Test
    void intersectionAngleWithLineM1B2AndX1() {
        assertThat(new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2))
            .intersectionAngle(new BigDecimalLine(BigDecimal.ONE))
        ).isCloseTo(BigDecimal.valueOf(Math.PI / 4), bigDecimalOffset());
    }

    @Test
    void intersectionAngleWithLineX1AndM1B2() {
        assertThat(new BigDecimalLine(BigDecimal.ONE)
            .intersectionAngle(
                new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2))
            )
        ).isCloseTo(BigDecimal.valueOf(Math.PI / 4), bigDecimalOffset());
    }

    // endregion

    // region hasPoint

    @Test
    void hasPointWithM1B0AndPointXY1() {
        assertThatLine(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO))
            .hasPoint(new BigDecimalPoint(BigDecimal.ONE));
    }

    @Test
    void hasPointWithM1B1AndPointXY1() {
        assertThatLine(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE))
            .hasNoPoint(new BigDecimalPoint(BigDecimal.ONE));
    }

    @Test
    void hasPointWithVerticalLine0AndPointX0Y1() {
        assertThatLine(new BigDecimalLine(BigDecimal.ZERO))
            .hasPoint(new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE));
    }

    @Test
    void hasPointWithVerticalLine0AndPointXY1() {
        assertThatLine(new BigDecimalLine(BigDecimal.ZERO))
            .hasNoPoint(new BigDecimalPoint(BigDecimal.ONE));
    }

    // endregion

    // region toLineSegment

    @Test
    void toLineSegmentUsingXOfLineWithM0B1() {
        assertThatLineSegment(
            new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ONE)
                .toLineSegmentUsingX(BigDecimal.ZERO, BigDecimal.ONE)
        ).hasA(new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE))
            .hasB(new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    void toLineSegmentUsingXOfLineWithM1B1() {
        assertThatLineSegment(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE)
                .toLineSegmentUsingX(BigDecimal.ZERO, BigDecimal.ONE)
        ).hasA(new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE))
            .hasB(new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2)));
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B0() {
        assertThatLineSegment(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO)
                .toLineSegmentUsingY(BigDecimal.ZERO, BigDecimal.ONE)
        ).hasA(new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO))
            .hasB(new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    void toLineSegmentUsingYOfLineWithM1B1() {
        assertThatLineSegment(
            new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE)
                .toLineSegmentUsingY(BigDecimal.ZERO, BigDecimal.ONE)
        ).hasA(new BigDecimalPoint(BigDecimal.ONE.negate(), BigDecimal.ZERO))
            .hasB(new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE));
    }

    // endregion

    // region map, isValid, copy

    @Test
    void mapOfLineWithM0_5B1_5() {
        BigDecimalLine line = new BigDecimalLine(
            BigDecimal.valueOf(0.5), BigDecimal.valueOf(1.5)
        );
        Line<Integer> result = new Line<>(new IntegerArithmetic(), 0, 1);
        assertThatLine(line.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThatLine(new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO)).isValid();
    }

    @Test
    void copyOfLieWithMB() {
        assertCopyable(new BigDecimalLine(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        ));
    }

    // endregion

    // region override

    @Test
    void equalsOfLineWithMB() {
        assertThatLine(new BigDecimalLine(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
            .isEqualTo(new BigDecimalLine(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
            .isNotEqualTo(new BigDecimalLine(BigDecimal.valueOf(3), BigDecimal.valueOf(2)));
    }

    @Test
    void hashCodeOfLineWithMB() {
        assertThat(new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        ).hashCode()).isEqualTo(21143);
    }

    @Test
    void toStringOfLineWithMB() {
        Line<BigDecimal> line = new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        assertThatLine(line).hasToString("y=2.0*x+3.0");
    }

    @Test
    void toStringOfVerticalLine() {
        Line<BigDecimal> line = new BigDecimalLine(BigDecimal.ONE);
        assertThatLine(line).hasToString("y=1");
    }

    @Test
    void compareToOfLineWithMB() {
        Line<BigDecimal> line = new BigDecimalLine(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        );
        assertThatLine(line)
            .isEqualByComparingTo(new BigDecimalLine(
                BigDecimal.valueOf(2), BigDecimal.valueOf(3)
            ))
            .isLessThan(new BigDecimalLine(
                BigDecimal.valueOf(3), BigDecimal.ONE
            ))
            .isGreaterThan(new BigDecimalLine(
                BigDecimal.valueOf(2), BigDecimal.ONE
            ));
    }

    @Test
    void serializable() {
        assertSerializable(
            new BigDecimalLine(BigDecimal.ZERO),
            BigDecimalLine.class
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
