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
import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ONE).isHorizontal()).isTrue();
    }

    @Test
    void isHorizontalOfVerticalLine() {
        assertThat(new BigDecimalLine(null, BigDecimal.ONE).isHorizontal()).isFalse();
    }

    @Test
    void isHorizontalOfM1B1Line() {
        assertThat(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE).isHorizontal()).isFalse();
    }

    @Test
    void isVerticalOfHorizontalLine() {
        assertThat(new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ONE).isVertical()).isFalse();
    }

    @Test
    void isVerticalOfVerticalLine() {
        assertThat(new BigDecimalLine(null, BigDecimal.ONE).isVertical()).isTrue();
    }

    @Test
    void isVerticalOfM1B1Line() {
        assertThat(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE).isVertical()).isFalse();
    }

    // endregion

    // region calculateX and calculateY

    @ParameterizedTest
    @MethodSource("getCalculateXArguments")
    void calculateX(double m, double b, double y, Double expected) {
        assertThat(new BigDecimalLine(
            convertDoubleToBigDecimal(m),
            convertDoubleToBigDecimal(b)
        ).calculateX(convertDoubleToBigDecimal(y))).isEqualTo(convertDoubleToBigDecimal(expected));
    }

    @ParameterizedTest
    @MethodSource("getCalculateYArguments")
    void calculateY(double m, double b, double x, Double expected) {
        assertThat(new BigDecimalLine(
            convertDoubleToBigDecimal(m),
            convertDoubleToBigDecimal(b)
        ).calculateY(convertDoubleToBigDecimal(x))).isEqualTo(convertDoubleToBigDecimal(expected));
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
        assertThat(new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
            .hasIntersection(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO))).isFalse();
    }

    @Test
    void hasIntersectionWithLineM1B2AndM2B1() {
        assertThat(new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
            .hasIntersection(
                new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.ONE)
            )).isTrue();
    }

    @Test
    void hasIntersectionWithLineX1AndX2() {
        assertThat(new BigDecimalLine(BigDecimal.ONE)
            .hasIntersection(new BigDecimalLine(BigDecimal.valueOf(2d)))).isFalse();
    }

    @Test
    void hasIntersectionWithLineM1B2AndX1() {
        assertThat(new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
            .hasIntersection(new BigDecimalLine(BigDecimal.ONE))).isTrue();
    }

    @Test
    void intersectionWithEqualM() {
        assertThat(new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
            .intersection(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO))).isNull();
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
        assertThat(new BigDecimalLine(BigDecimal.ONE)
            .intersection(new BigDecimalLine(BigDecimal.valueOf(2d)))).isNull();
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
        assertThat(new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
            .intersectionAngle(
                new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.ONE)
            )).isEqualTo(BigDecimal.valueOf(0.3217505543966422));
    }

    @Test
    void intersectionAngleWithLineX1AndX2() {
        assertThat(new BigDecimalLine(BigDecimal.ONE)
            .intersectionAngle(new BigDecimalLine(BigDecimal.valueOf(2d)))).isNull();
    }

    @Test
    void intersectionAngleWithLineM1B2AndX1() {
        assertThat(new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
            .intersectionAngle(new BigDecimalLine(BigDecimal.ONE))).isEqualTo(BigDecimal.valueOf(0.7853981633974477));
    }

    @Test
    void intersectionAngleWithLineX1AndM1B2() {
        assertThat(new BigDecimalLine(BigDecimal.ONE)
            .intersectionAngle(
                new BigDecimalLine(BigDecimal.ONE, BigDecimal.valueOf(2d))
            )).isEqualTo(BigDecimal.valueOf(0.7853981633974477));
    }

    // endregion

    // region hasPoint

    @Test
    void hasPointWithM1B0AndPointXY1() {
        assertThat(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ZERO)
            .hasPoint(new BigDecimalPoint(BigDecimal.ONE))).isTrue();
    }

    @Test
    void hasPointWithM1B1AndPointXY1() {
        assertThat(new BigDecimalLine(BigDecimal.ONE, BigDecimal.ONE)
            .hasPoint(new BigDecimalPoint(BigDecimal.ONE))).isFalse();
    }

    @Test
    void hasPointWithVerticalLine0AndPointX0Y1() {
        assertThat(new BigDecimalLine(BigDecimal.ZERO)
            .hasPoint(new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE))).isTrue();
    }

    @Test
    void hasPointWithVerticalLine0AndPointXY1() {
        assertThat(new BigDecimalLine(BigDecimal.ZERO)
            .hasPoint(new BigDecimalPoint(BigDecimal.ONE))).isFalse();
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
        assertThat(line.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new BigDecimalLine(BigDecimal.ZERO, BigDecimal.ZERO).isValid()).isTrue();
    }

    @Test
    void copyOfLieWithMB() {
        Line<BigDecimal> line = new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        assertThat(line.copy()).isEqualTo(line);
    }

    // endregion

    // region override

    @Test
    void equalsOfLineWithMB() {
        assertThat(new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)))
            .isEqualTo(new BigDecimalLine(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)))
            .isNotEqualTo(new BigDecimalLine(BigDecimal.valueOf(3d), BigDecimal.valueOf(2d)));
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
        assertThat(line).hasToString("y=2.0*x+3.0");
    }

    @Test
    void toStringOfVerticalLine() {
        Line<BigDecimal> line = new BigDecimalLine(BigDecimal.ONE);
        assertThat(line).hasToString("y=1");
    }

    @Test
    void compareToOfLineWithMB() {
        Line<BigDecimal> line = new BigDecimalLine(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        assertThat(line)
            .isEqualByComparingTo(new BigDecimalLine(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
            ))
            .isLessThan(new BigDecimalLine(
                BigDecimal.valueOf(3d), BigDecimal.ONE
            ))
            .isGreaterThan(new BigDecimalLine(
                BigDecimal.valueOf(2d), BigDecimal.ONE
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
