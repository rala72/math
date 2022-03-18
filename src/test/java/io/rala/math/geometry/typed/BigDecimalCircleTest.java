package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.GeometryAssertions.CONTEXT;
import static io.rala.math.testUtils.assertion.GeometryAssertions.assertThatCircle;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalCircleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertThatCircle(new BigDecimalCircle()).hasCenterInZero().hasRadiusOne();
    }

    @Test
    void constructorWithMathContext5() {
        assertThatCircle(new BigDecimalCircle(new MathContext(5)))
            .hasCenterInZero().hasRadiusOne();
    }

    @Test
    void constructorWithCenterButWithoutRadius() {
        assertThatCircle(
            new BigDecimalCircle(new BigDecimalPoint(BigDecimal.ONE))
        ).hasCenter(new BigDecimalPoint(BigDecimal.ONE));
    }

    @Test
    void constructorWithCenterAndMathContext5ButWithoutRadius() {
        assertThatCircle(new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.ONE),
            new MathContext(5)
        )).hasCenter(new BigDecimalPoint(BigDecimal.ONE));
    }

    @Test
    void constructorWithRadiusButWithoutCenter() {
        assertThatCircle(new BigDecimalCircle(BigDecimal.valueOf(2)))
            .hasRadius(BigDecimal.valueOf(2));
    }

    @Test
    void constructorWithRadiusAndMathContext5ButWithoutCenter() {
        assertThatCircle(new BigDecimalCircle(
            BigDecimal.valueOf(2),
            new MathContext(5)
        )).hasRadius(BigDecimal.valueOf(2));
    }

    @Test
    void constructorWithCenterAndPoint() {
        assertThatCircle(new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.ONE)
        )).hasCenter(new BigDecimalPoint(BigDecimal.valueOf(2)))
            .hasRadius(BigDecimal.valueOf(Math.sqrt(2d)));
    }

    @Test
    void constructorWithCenterAndPointAndMathContext5() {
        assertThatCircle(new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.ONE),
            new MathContext(5)
        )).hasCenter(new BigDecimalPoint(BigDecimal.valueOf(2)))
            .hasRadius(BigDecimal.valueOf(Math.sqrt(2d))
                .round(new MathContext(5)));
    }

    @Test
    void constructorWithCenterAndRadius() {
        assertThatCircle(new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            BigDecimal.valueOf(3)
        )).hasCenter(new BigDecimalPoint(BigDecimal.valueOf(2)))
            .hasRadius(BigDecimal.valueOf(3));
    }

    @Test
    void constructorWithCenterAndRadiusAndMathContext5() {
        assertThatCircle(new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            BigDecimal.valueOf(3),
            new MathContext(5)
        )).hasCenter(new BigDecimalPoint(BigDecimal.valueOf(2)))
            .hasRadius(BigDecimal.valueOf(3));
    }

    @Test
    void createAndSetCenter() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        assertThatCircle(circle).hasCenterInZero().hasRadiusOne();
        circle.setCenter(new BigDecimalPoint(BigDecimal.ONE));
        assertThatCircle(circle)
            .hasCenter(new BigDecimalPoint(BigDecimal.ONE))
            .hasRadius(BigDecimal.ONE);
    }

    @Test
    void createAndSetRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        assertThatCircle(circle).hasCenterInZero().hasRadiusOne();
        circle.setRadius(BigDecimal.valueOf(2));
        assertThatCircle(circle).hasRadius(BigDecimal.valueOf(2));
    }

    @Test
    void createAndExpectDiameterToBeBigDecimal() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        assertThatCircle(circle).hasDiameter(BigDecimal.valueOf(2));
        circle.setRadius(BigDecimal.valueOf(2));
        assertThatCircle(circle).hasDiameter(BigDecimal.valueOf(4));
        circle.setDiameter(BigDecimal.valueOf(2));
        assertThatCircle(circle).hasDiameter(BigDecimal.valueOf(2));
    }

    @Test
    void createAndSetRadiusAndExpectDiameterToBeBigDecimal() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        circle.setRadius(BigDecimal.valueOf(2));
        assertThatCircle(circle).hasDiameter(BigDecimal.valueOf(4));
    }

    @Test
    void createAndSetDiameterAndExpectRadiusToBeHalf() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        circle.setDiameter(BigDecimal.valueOf(2));
        assertThatCircle(circle).hasDiameter(BigDecimal.valueOf(2));
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfCircleWithoutParameter() {
        assertThatCircle(new BigDecimalCircle())
            .hasArea(BigDecimal.valueOf(Math.PI).round(CONTEXT));
    }

    @Test
    void areaOfCircleWithRadius2() {
        assertThatCircle(new BigDecimalCircle(BigDecimal.valueOf(2)))
            .hasArea(BigDecimal.valueOf(12.56637061435917));
    }

    @Test
    void areaOfCircleWithRadius3() {
        assertThatCircle(new BigDecimalCircle(BigDecimal.valueOf(3)))
            .hasArea(BigDecimal.valueOf(28.27433388230814));
    }

    @Test
    void circumferenceOfCircleWithoutParameter() {
        assertThatCircle(new BigDecimalCircle())
            .hasCircumference(BigDecimal.valueOf(6.283185307179586));
    }

    @Test
    void circumferenceOfCircleWithRadius2() {
        assertThatCircle(new BigDecimalCircle(BigDecimal.valueOf(2)))
            .hasCircumference(BigDecimal.valueOf(12.56637061435917));
    }

    @Test
    void circumferenceOfCircleWithRadius3() {
        assertThatCircle(new BigDecimalCircle(BigDecimal.valueOf(3)))
            .hasCircumference(BigDecimal.valueOf(18.84955592153876));
    }

    // endregion

    // region isUnitCircle

    @Test
    void isUnitCircleWithRadius0() {
        assertThatCircle(new BigDecimalCircle(BigDecimal.ZERO)).isNoUnitCircle();
    }

    @Test
    void isUnitCircleWithRadius1() {
        assertThatCircle(new BigDecimalCircle(BigDecimal.ONE)).isUnitCircle();
    }

    @Test
    void isUnitCircleWithRadius2() {
        assertThatCircle(new BigDecimalCircle(BigDecimal.valueOf(2))).isNoUnitCircle();
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfCircleWithXY0_5R1_5() {
        BigDecimalCircle circle = new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(0.5)), BigDecimal.valueOf(1.5)
        );
        IntegerArithmetic integerArithmetic = new IntegerArithmetic();
        Circle<Integer> result = new Circle<>(integerArithmetic,
            new Point<>(integerArithmetic, 0), 1
        );
        assertThatCircle(circle.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThatCircle(new BigDecimalCircle()).isValid();
    }

    @Test
    void isValidWithNegativeRadius() {
        assertThatCircle(new BigDecimalCircle(BigDecimal.ONE.negate())).isInvalid();
    }

    @Test
    void moveOfCircleWithoutParameterWithXY() {
        assertThatCircle(new BigDecimalCircle().move(BigDecimal.ONE))
            .hasCenter(new BigDecimalPoint(BigDecimal.ONE));
    }

    @Test
    void moveOfCircleWithoutParameterWithXAndY() {
        assertThatCircle(
            new BigDecimalCircle().move(BigDecimal.ONE, BigDecimal.ONE)
        ).hasCenter(new BigDecimalPoint(BigDecimal.ONE));
    }

    @Test
    void moveOfCircleWithoutParameterWithVector() {
        assertThatCircle(
            new BigDecimalCircle().move(new BigDecimalVector(BigDecimal.ONE))
        ).hasCenter(new BigDecimalPoint(BigDecimal.ONE));
    }

    @Test
    void rotateOfCircleWithX1Y2WithoutCenterWithPiHalf() {
        assertThatCircle(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2))
            ).rotate(BigDecimal.valueOf(Math.PI / 2d))
        ).hasCenter(new BigDecimalPoint(
            BigDecimal.valueOf(-2),
            BigDecimal.valueOf(1)
        ));
    }

    @Test
    void rotateOfCircleWithX1Y2WithCenterXY1WithPiHalf() {
        assertThatCircle(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2))
            ).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI / 2d)
            )
        ).hasCenter(new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE));
    }

    @Test
    void copyOfCircleWithPointAndRadius() {
        assertCopyable(new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2)), BigDecimal.valueOf(3)
        ));
    }

    // endregion

    // region override

    @Test
    void equalsOfCircleWithPointAndRadius() {
        assertThatCircle(new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            BigDecimal.valueOf(3)
        )).isEqualTo(new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2)), BigDecimal.valueOf(3)
        )).isNotEqualTo(new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(3)), BigDecimal.valueOf(2)
        ));
    }

    @Test
    void hashCodeOfCircleWithPointAndRadius() {
        assertThat(new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(3d)
        ).hashCode()).isEqualTo(647715);
    }

    @Test
    void toStringOfCircleWithPointAndRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(3d)
        );
        assertThatCircle(circle).hasToString("2.0|2.0 3.0");
    }

    @Test
    void compareToOfCircleWithCenterAndRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            BigDecimal.valueOf(3)
        );
        assertThatCircle(circle)
            .isEqualByComparingTo(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2)), BigDecimal.valueOf(3)
            ))
            .isLessThan(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(3)), BigDecimal.valueOf(3)
            ))
            .isGreaterThan(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2)), BigDecimal.ONE
            ));
    }

    @Test
    void serializable() {
        assertSerializable(new BigDecimalCircle(), BigDecimalCircle.class);
    }

    // endregion
}
