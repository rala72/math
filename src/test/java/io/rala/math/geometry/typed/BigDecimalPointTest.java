package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Point;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertThatPoint;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalPointTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertThatPoint(new BigDecimalPoint()).hasZeroXY();
    }

    @Test
    void constructorWithMathContext5() {
        assertThatPoint(new BigDecimalPoint(new MathContext(5)))
            .hasZeroXY();
    }

    @Test
    void constructorWithXYParameter() {
        assertThatPoint(new BigDecimalPoint(BigDecimal.ONE))
            .hasXY(BigDecimal.ONE);
    }

    @Test
    void constructorWithXYParameterAndMathContext5() {
        assertThatPoint(
            new BigDecimalPoint(BigDecimal.ONE, new MathContext(5))
        ).hasXY(BigDecimal.ONE);
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertThatPoint(
            new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
        ).hasXY(BigDecimal.valueOf(2));
    }

    @Test
    void constructorWithEqualXYParameterAndMathContext5() {
        assertThatPoint(new BigDecimalPoint(
            BigDecimal.valueOf(2), BigDecimal.valueOf(2),
            new MathContext(5)
        )).hasXY(BigDecimal.valueOf(2));
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertThatPoint(
            new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.valueOf(3))
        ).hasX(BigDecimal.valueOf(2)).hasY(BigDecimal.valueOf(3));
    }

    @Test
    void constructorWithDifferentXYParameterAndMathContext5() {
        assertThatPoint(new BigDecimalPoint(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            new MathContext(5)
        )).hasX(BigDecimal.valueOf(2)).hasY(BigDecimal.valueOf(3));
    }

    @Test
    void createAndSetX() {
        Point<BigDecimal> point = new BigDecimalPoint();
        assertThatPoint(point).hasZeroXY();
        point.setX(BigDecimal.ONE);
        assertThatPoint(point).hasX(BigDecimal.ONE).hasY(BigDecimal.ZERO);
    }

    @Test
    void createAndSetY() {
        Point<BigDecimal> point = new BigDecimalPoint();
        assertThatPoint(point);
        point.setY(BigDecimal.valueOf(2));
        assertThatPoint(point).hasX(BigDecimal.ZERO).hasY(BigDecimal.valueOf(2));
    }

    @Test
    void createAndSetXY() {
        Point<BigDecimal> point = new BigDecimalPoint();
        assertThatPoint(point);
        point.setXY(BigDecimal.valueOf(3));
        assertThatPoint(point).hasXY(BigDecimal.valueOf(3));
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfPointWithX0_5Y1_5() {
        BigDecimalPoint point = new BigDecimalPoint(
            BigDecimal.valueOf(0.5), BigDecimal.valueOf(1.5)
        );
        Point<Integer> result = new Point<>(new IntegerArithmetic(), 0, 1);
        assertThatPoint(point.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThatPoint(new BigDecimalPoint()).isValid();
    }

    @Test
    void moveOfPointWithXYWithXY() {
        assertThatPoint(
            new BigDecimalPoint().move(BigDecimal.ONE)
        ).hasX(BigDecimal.ONE).hasY(BigDecimal.ONE);
    }

    @Test
    void moveOfPointWithXYWithXAndY() {
        assertThatPoint(
            new BigDecimalPoint().move(BigDecimal.ONE, BigDecimal.ONE)
        ).hasX(BigDecimal.ONE).hasY(BigDecimal.ONE);
    }

    @Test
    void moveOfPointWithXYWithVector() {
        assertThatPoint(
            new BigDecimalPoint().move(new BigDecimalVector(BigDecimal.ONE))
        ).hasX(BigDecimal.ONE).hasY(BigDecimal.ONE);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiHalf() {
        assertThatPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2))
                .rotate(BigDecimal.valueOf(Math.PI / 2d))
        ).hasX(BigDecimal.valueOf(-2d)).hasY(BigDecimal.ONE);
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPi() {
        assertThatPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2))
                .rotate(BigDecimal.valueOf(Math.PI))
        ).hasX(BigDecimal.ONE.negate()).hasY(BigDecimal.valueOf(-2d));
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiThreeHalf() {
        assertThatPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2))
                .rotate(BigDecimal.valueOf(Math.PI * 3d / 2d))
        ).hasX(BigDecimal.valueOf(2)).hasY(BigDecimal.ONE.negate());
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithTwoPi() {
        assertThatPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2))
                .rotate(BigDecimal.valueOf(Math.PI * 2d))
        ).hasX(BigDecimal.ONE).hasY(BigDecimal.valueOf(2));
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiHalf() {
        assertThatPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2)).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI / 2d)
            )
        ).hasX(BigDecimal.ZERO).hasY(BigDecimal.ONE);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPi() {
        assertThatPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2)).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI)
            )
        ).hasX(BigDecimal.ONE).hasY(BigDecimal.ZERO);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiThreeHalf() {
        assertThatPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2)).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI * 3d / 2d)
            )
        ).hasX(BigDecimal.valueOf(2)).hasY(BigDecimal.ONE);
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithTwoPi() {
        assertThatPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2)).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI * 2d)
            )
        ).hasX(BigDecimal.ONE).hasY(BigDecimal.valueOf(2));
    }

    @Test
    void copyOfPointWithXY() {
        assertThatPoint(new BigDecimalPoint(
            BigDecimal.valueOf(2),
            BigDecimal.valueOf(3)
        ));
    }

    // endregion

    // region override

    @Test
    void equalsOfPointWithXY() {
        assertThatPoint(new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
            .isEqualTo(new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
            .isNotEqualTo(new BigDecimalPoint(BigDecimal.valueOf(3), BigDecimal.valueOf(2)));
    }

    @Test
    void hashCodeOfPointWithXY() {
        assertThat(new BigDecimalPoint(
            BigDecimal.valueOf(2d),
            BigDecimal.valueOf(3d)
        ).hashCode()).isEqualTo(21143);
    }

    @Test
    void toStringOfPointWithXY() {
        Point<BigDecimal> point = new BigDecimalPoint(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        assertThatPoint(point).hasToString("2.0|3.0");
    }

    @Test
    void compareToOfPointWithXY() {
        Point<BigDecimal> point = new BigDecimalPoint(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        );
        assertThatPoint(point)
            .isEqualByComparingTo(new BigDecimalPoint(
                BigDecimal.valueOf(2), BigDecimal.valueOf(3)
            ))
            .isLessThan(new BigDecimalPoint(
                BigDecimal.valueOf(3), BigDecimal.ONE
            ))
            .isGreaterThan(new BigDecimalPoint(
                BigDecimal.valueOf(2), BigDecimal.valueOf(2)
            ));
    }

    @Test
    void serializable() {
        assertSerializable(new BigDecimalPoint(), BigDecimalPoint.class);
    }

    // endregion
}
