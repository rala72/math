package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.GeometryAssertions.CONTEXT;
import static io.rala.math.testUtils.assertion.GeometryAssertions.assertCircle;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalCircleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertCircle(new BigDecimalCircle());
    }

    @Test
    void constructorWithMathContext5() {
        assertCircle(new BigDecimalCircle(new MathContext(5)));
    }

    @Test
    void constructorWithCenterButWithoutRadius() {
        assertCircle(
            new BigDecimalCircle(new BigDecimalPoint(BigDecimal.ONE)),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void constructorWithCenterAndMathContext5ButWithoutRadius() {
        assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.ONE),
                new MathContext(5)
            ),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void constructorWithRadiusButWithoutCenter() {
        assertCircle(
            new BigDecimalCircle(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithRadiusAndMathContext5ButWithoutCenter() {
        assertCircle(
            new BigDecimalCircle(
                BigDecimal.valueOf(2d),
                new MathContext(5)
            ),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithCenterAndPoint() {
        assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.ONE)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(Math.sqrt(2d))
        );
    }

    @Test
    void constructorWithCenterAndPointAndMathContext5() {
        assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.ONE),
                new MathContext(5)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(Math.sqrt(2d)).round(new MathContext(5))
        );
    }

    @Test
    void constructorWithCenterAndRadius() {
        assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                BigDecimal.valueOf(3d)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.valueOf(3d)
        );
    }

    @Test
    void constructorWithCenterAndRadiusAndMathContext5() {
        assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                BigDecimal.valueOf(3d),
                new MathContext(5)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.valueOf(3d)
        );
    }

    @Test
    void createAndSetCenter() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        assertCircle(circle);
        circle.setCenter(new BigDecimalPoint(BigDecimal.ONE));
        assertCircle(circle,
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.ONE
        );
    }

    @Test
    void createAndSetRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        assertCircle(circle);
        circle.setRadius(BigDecimal.valueOf(2d));
        assertCircle(circle, BigDecimal.valueOf(2d));
    }

    @Test
    void createAndExpectDiameterToBeBigDecimal() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        assertThat(circle.getDiameter()).isEqualTo(BigDecimal.valueOf(2));
        circle.setRadius(BigDecimal.valueOf(2d));
        assertThat(circle.getDiameter()).isEqualTo(BigDecimal.valueOf(4));
        circle.setDiameter(BigDecimal.valueOf(2d));
        assertThat(circle.getDiameter()).isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void createAndSetRadiusAndExpectDiameterToBeBigDecimal() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        circle.setRadius(BigDecimal.valueOf(2d));
        assertThat(circle.getDiameter()).isEqualTo(BigDecimal.valueOf(4));
    }

    @Test
    void createAndSetDiameterAndExpectRadiusToBeHalf() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        circle.setDiameter(BigDecimal.valueOf(2d));
        assertThat(circle.getDiameter()).isEqualTo(BigDecimal.valueOf(2));
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfCircleWithoutParameter() {
        assertThat(new BigDecimalCircle().area()).isEqualTo(BigDecimal.valueOf(Math.PI).round(CONTEXT));
    }

    @Test
    void areaOfCircleWithRadius2() {
        assertThat(new BigDecimalCircle(BigDecimal.valueOf(2d)).area()).isEqualTo(BigDecimal.valueOf(12.56637061435917));
    }

    @Test
    void areaOfCircleWithRadius3() {
        assertThat(new BigDecimalCircle(BigDecimal.valueOf(3d)).area()).isEqualTo(BigDecimal.valueOf(28.27433388230814));
    }

    @Test
    void circumferenceOfCircleWithoutParameter() {
        assertThat(new BigDecimalCircle().circumference()).isEqualTo(BigDecimal.valueOf(6.283185307179586));
    }

    @Test
    void circumferenceOfCircleWithRadius2() {
        assertThat(new BigDecimalCircle(BigDecimal.valueOf(2d)).circumference())
            .isEqualTo(BigDecimal.valueOf(12.56637061435917));
    }

    @Test
    void circumferenceOfCircleWithRadius3() {
        assertThat(new BigDecimalCircle(BigDecimal.valueOf(3d)).circumference())
            .isEqualTo(BigDecimal.valueOf(18.84955592153876));
    }

    // endregion

    // region isUnitCircle

    @Test
    void isUnitCircleWithRadius0() {
        assertThat(new BigDecimalCircle(BigDecimal.ZERO).isUnitCircle()).isFalse();
    }

    @Test
    void isUnitCircleWithRadius1() {
        assertThat(new BigDecimalCircle(BigDecimal.ONE).isUnitCircle()).isTrue();
    }

    @Test
    void isUnitCircleWithRadius2() {
        assertThat(new BigDecimalCircle(BigDecimal.valueOf(2d)).isUnitCircle()).isFalse();
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
        assertThat(circle.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new BigDecimalCircle().isValid()).isTrue();
    }

    @Test
    void isValidWithNegativeRadius() {
        assertThat(new BigDecimalCircle(BigDecimal.ONE.negate()).isValid()).isFalse();
    }

    @Test
    void moveOfCircleWithoutParameterWithXY() {
        assertCircle(
            new BigDecimalCircle().move(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void moveOfCircleWithoutParameterWithXAndY() {
        assertCircle(
            new BigDecimalCircle().move(BigDecimal.ONE, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void moveOfCircleWithoutParameterWithVector() {
        assertCircle(
            new BigDecimalCircle().move(new BigDecimalVector(BigDecimal.ONE)),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithoutCenterWithPiHalf() {
        assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
            ).rotate(BigDecimal.valueOf(Math.PI / 2d)),
            new BigDecimalPoint(
                BigDecimal.valueOf(-2d),
                BigDecimal.valueOf(1.0000000000000002)
            )
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithCenterXY1WithPiHalf() {
        assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
            ).rotate(
                new BigDecimalPoint(BigDecimal.ONE), BigDecimal.valueOf(Math.PI / 2d)
            ),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE)
        );
    }

    @Test
    void copyOfCircleWithPointAndRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.valueOf(3d)
        );
        assertThat(circle.copy()).isEqualTo(circle);
    }

    // endregion

    // region override

    @Test
    void equalsOfCircleWithPointAndRadius() {
        assertThat(new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(3d)
        )).isEqualTo(new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.valueOf(3d)
        )).isNotEqualTo(new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(3d)), BigDecimal.valueOf(2d)
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
        assertThat(circle).hasToString("2.0|2.0 3.0");
    }

    @Test
    void compareToOfCircleWithCenterAndRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(3d)
        );
        assertThat(circle)
            .isEqualByComparingTo(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.valueOf(3d)
            ))
            .isLessThan(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(3d)), BigDecimal.valueOf(3d)
            ))
            .isGreaterThan(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.ONE
            ));
    }

    @Test
    void serializable() {
        assertSerializable(new BigDecimalCircle(), BigDecimalCircle.class);
    }

    // endregion
}
