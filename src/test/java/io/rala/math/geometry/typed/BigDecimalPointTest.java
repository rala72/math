package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Point;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertPoint;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalPointTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertPoint(new BigDecimalPoint());
    }

    @Test
    void constructorWithMathContext5() {
        assertPoint(new BigDecimalPoint(new MathContext(5)));
    }

    @Test
    void constructorWithXYParameter() {
        assertPoint(
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.ONE
        );
    }

    @Test
    void constructorWithXYParameterAndMathContext5() {
        assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, new MathContext(5)),
            BigDecimal.ONE
        );
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertPoint(
            new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithEqualXYParameterAndMathContext5() {
        assertPoint(
            new BigDecimalPoint(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(2d),
                new MathContext(5)
            ),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertPoint(
            new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)),
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
    }

    @Test
    void constructorWithDifferentXYParameterAndMathContext5() {
        assertPoint(
            new BigDecimalPoint(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(3d),
                new MathContext(5)
            ),
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
    }

    @Test
    void createAndSetX() {
        Point<BigDecimal> point = new BigDecimalPoint();
        assertPoint(point);
        point.setX(BigDecimal.ONE);
        assertPoint(point, BigDecimal.ONE, BigDecimal.ZERO);
    }

    @Test
    void createAndSetY() {
        Point<BigDecimal> point = new BigDecimalPoint();
        assertPoint(point);
        point.setY(BigDecimal.valueOf(2d));
        assertPoint(point, BigDecimal.ZERO, BigDecimal.valueOf(2d));
    }

    @Test
    void createAndSetXY() {
        Point<BigDecimal> point = new BigDecimalPoint();
        assertPoint(point);
        point.setXY(BigDecimal.valueOf(3d));
        assertPoint(point,
            BigDecimal.valueOf(3d), BigDecimal.valueOf(3d)
        );
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfPointWithX0_5Y1_5() {
        BigDecimalPoint point = new BigDecimalPoint(
            BigDecimal.valueOf(0.5), BigDecimal.valueOf(1.5)
        );
        Point<Integer> result = new Point<>(new IntegerArithmetic(), 0, 1);
        assertThat(point.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new BigDecimalPoint().isValid()).isTrue();
    }

    @Test
    void moveOfPointWithXYWithXY() {
        assertPoint(
            new BigDecimalPoint().move(BigDecimal.ONE),
            BigDecimal.ONE, BigDecimal.ONE
        );
    }

    @Test
    void moveOfPointWithXYWithXAndY() {
        assertPoint(
            new BigDecimalPoint().move(BigDecimal.ONE, BigDecimal.ONE),
            BigDecimal.ONE, BigDecimal.ONE
        );
    }

    @Test
    void moveOfPointWithXYWithVector() {
        assertPoint(
            new BigDecimalPoint().move(new BigDecimalVector(BigDecimal.ONE)),
            BigDecimal.ONE, BigDecimal.ONE
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiHalf() {
        assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI / 2d)),
            BigDecimal.valueOf(-2d), BigDecimal.ONE
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPi() {
        assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI)),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(-2d)
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiThreeHalf() {
        assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI * 3d / 2d)),
            BigDecimal.valueOf(2d), BigDecimal.ONE.negate()
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithTwoPi() {
        assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI * 2d)),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiHalf() {
        assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI / 2d)
            ),
            BigDecimal.ZERO, BigDecimal.ONE
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPi() {
        assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI)
            ),
            BigDecimal.ONE, BigDecimal.ZERO
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiThreeHalf() {
        assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI * 3d / 2d)
            ),
            BigDecimal.valueOf(2d), BigDecimal.ONE
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithTwoPi() {
        assertPoint(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI * 2d)
            ),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void copyOfPointWithXY() {
        Point<BigDecimal> point = new BigDecimalPoint(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        assertThat(point.copy()).isEqualTo(point);
    }

    // endregion

    // region override

    @Test
    void equalsOfPointWithXY() {
        assertThat(new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)))
            .isEqualTo(new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)))
            .isNotEqualTo(new BigDecimalPoint(BigDecimal.valueOf(3d), BigDecimal.valueOf(2d)));
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
        assertThat(point).hasToString("2.0|3.0");
    }

    @Test
    void compareToOfPointWithXY() {
        Point<BigDecimal> point = new BigDecimalPoint(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        assertThat(point)
            .isEqualByComparingTo(new BigDecimalPoint(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
            ))
            .isLessThan(new BigDecimalPoint(
                BigDecimal.valueOf(3d), BigDecimal.ONE
            ))
            .isGreaterThan(new BigDecimalPoint(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(2d)
            ));
    }

    @Test
    void serializable() {
        assertSerializable(new BigDecimalPoint(), BigDecimalPoint.class);
    }

    // endregion
}
