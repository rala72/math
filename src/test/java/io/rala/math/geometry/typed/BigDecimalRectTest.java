package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Rect;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalRectTest {
    // region constructors, getter and setter

    @Test
    void constructorWithHeightAndWidthButWithoutPoints() {
        assertThatRect(new BigDecimalRect(BigDecimal.ONE, BigDecimal.valueOf(2)))
            .hasHeight(BigDecimal.ONE).hasWidth(BigDecimal.valueOf(2));
    }

    @Test
    void constructorWithHeightAndWidthAndMathContext5ButWithoutPoints() {
        assertThatRect(
            new BigDecimalRect(
                BigDecimal.ONE, BigDecimal.valueOf(2),
                new MathContext(5)
            )
        ).hasHeight(BigDecimal.ONE).hasWidth(BigDecimal.valueOf(2));
    }

    @Test
    void constructorWithPoint1AndSize() {
        assertThatRect(
            new BigDecimalRect(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(2)
            )
        ).hasAWithZeroXY()
            .hasB(new BigDecimalPoint(BigDecimal.ONE))
            .hasSize(BigDecimal.valueOf(2));
    }

    @Test
    void constructorWithPoint1AndSizeAndMathContext5() {
        assertThatRect(
            new BigDecimalRect(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(2),
                new MathContext(5)
            )
        ).hasAWithZeroXY()
            .hasB(new BigDecimalPoint(BigDecimal.ONE))
            .hasSize(BigDecimal.valueOf(2));
    }

    @Test
    void createAndSetA() {
        Rect<BigDecimal> rect = new BigDecimalRect(BigDecimal.ZERO, BigDecimal.ZERO);
        assertThatRect(rect)
            .hasHeight(BigDecimal.ZERO).hasWidth(BigDecimal.ZERO);
        rect.setA(new BigDecimalPoint(BigDecimal.ONE));
        assertThatRect(rect)
            .hasA(new BigDecimalPoint(BigDecimal.ONE))
            .hasBWithZeroXY()
            .hasSize(BigDecimal.ZERO);
    }

    @Test
    void createAndSetB() {
        Rect<BigDecimal> rect = new BigDecimalRect(BigDecimal.ZERO, BigDecimal.ZERO);
        assertThatRect(rect)
            .hasHeight(BigDecimal.ZERO).hasWidth(BigDecimal.ZERO);
        rect.setB(new BigDecimalPoint(BigDecimal.valueOf(2)));
        assertThatRect(rect)
            .hasAWithZeroXY()
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(2)))
            .hasSize(BigDecimal.ZERO);
    }

    @Test
    void createAndSetSize() {
        Rect<BigDecimal> rect = new BigDecimalRect(BigDecimal.ZERO, BigDecimal.ZERO);
        assertThatRect(rect)
            .hasHeight(BigDecimal.ZERO).hasWidth(BigDecimal.ZERO);
        rect.setSize(BigDecimal.valueOf(2));
        assertThatRect(rect)
            .hasHeight(BigDecimal.ZERO).hasWidth(BigDecimal.valueOf(2));
    }

    // endregion

    // region vertexes

    @Test
    void vertexesOfRectWithHeight1AndWidth2() {
        Rect<BigDecimal> rect = new BigDecimalRect(
            BigDecimal.ONE, BigDecimal.valueOf(2)
        );
        assertThatPoint(rect.vertexA())
            .hasX(BigDecimal.ZERO)
            .hasY(BigDecimal.ZERO);
        assertThatPoint(rect.vertexB())
            .hasX(BigDecimal.valueOf(2))
            .hasY(BigDecimal.ZERO);
        assertThatPoint(rect.vertexC())
            .hasX(BigDecimal.valueOf(2))
            .hasY(BigDecimal.ONE);
        assertThatPoint(rect.vertexD())
            .hasX(BigDecimal.ZERO)
            .hasY(BigDecimal.ONE);
    }

    @Test
    void vertexesOfRectWithPointsAndSize() {
        Rect<BigDecimal> rect = new BigDecimalRect(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            BigDecimal.valueOf(2)
        );
        assertThatPoint(rect.vertexA())
            .hasX(BigDecimal.ONE)
            .hasY(BigDecimal.ONE);
        assertThatPoint(rect.vertexB())
            .hasX(BigDecimal.ZERO)
            .hasY(BigDecimal.ONE);
        assertThatPoint(rect.vertexC())
            .hasX(BigDecimal.ZERO)
            .hasY(BigDecimal.valueOf(3));
        assertThatPoint(rect.vertexD())
            .hasX(BigDecimal.ONE)
            .hasY(BigDecimal.valueOf(3));
    }

    // endregion

    // region height, width and diagonale

    @Test
    void heightOfRectWithPointsAndPositiveSize() {
        assertThatRect(new BigDecimalRect(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.ONE
        )).hasHeight(BigDecimal.ONE);
    }

    @Test
    void heightOfRectWithPointsAndNegativeSize() {
        assertThatRect(new BigDecimalRect(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.ONE.negate()
        )).hasHeight(BigDecimal.ONE);
    }

    @Test
    void heightOfRectWithHeight1AndWidth0() {
        assertThatRect(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.ZERO
        )).hasHeight(BigDecimal.ZERO);
    }

    @Test
    void widthOfRectWithPointsAndPositiveSize() {
        assertThatRect(new BigDecimalRect(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.ONE
        )).hasWidth(BigDecimal.valueOf(2).sqrt(CONTEXT));
    }

    @Test
    void widthOfRectWithPointsAndNegativeSize() {
        assertThatRect(new BigDecimalRect(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.ONE.negate()
        )).hasWidth(BigDecimal.valueOf(2).sqrt(CONTEXT));
    }

    @Test
    void widthOfRectWithHeight1AndWidth0() {
        assertThatRect(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.ZERO
        )).hasWidth(BigDecimal.ONE);
    }

    @Test
    void diagonaleOfRectWithHeightAndWidth1() {
        assertThatRect(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.ONE
        )).hasDiagonale(BigDecimal.valueOf(2).sqrt(CONTEXT));
    }

    @Test
    void diagonaleOfRectWithHeight1AndWidth2() {
        assertThatRect(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.valueOf(2)
        )).hasDiagonale(BigDecimal.valueOf(5).sqrt(CONTEXT));
    }

    @Test
    void diagonaleOfRectWithHeight2AndWidth3() {
        assertThatRect(new BigDecimalRect(
            BigDecimal.valueOf(2),
            BigDecimal.valueOf(3)
        )).hasDiagonale(BigDecimal.valueOf(13).sqrt(CONTEXT));
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfRectWithHeightAndWidth1() {
        assertThatRect(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.ONE
        )).hasArea(BigDecimal.ONE);
    }

    @Test
    void areaOfRectWithHeight1AndWidth2() {
        assertThatRect(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.valueOf(2)
        )).hasArea(BigDecimal.valueOf(2));
    }

    @Test
    void areaOfRectWithHeight2AndWidth3() {
        assertThatRect(new BigDecimalRect(
            BigDecimal.valueOf(2),
            BigDecimal.valueOf(3)
        )).hasArea(BigDecimal.valueOf(6));
    }

    @Test
    void circumferenceOfRectWithHeightAndWidth1() {
        assertThatRect(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.ONE
        )).hasCircumference(BigDecimal.valueOf(4));
    }

    @Test
    void circumferenceOfRectWithHeight1AndWidth2() {
        assertThatRect(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.valueOf(2)
        )).hasCircumference(BigDecimal.valueOf(6));
    }

    @Test
    void circumferenceOfRectWithHeight2AndWidth3() {
        assertThatRect(new BigDecimalRect(
            BigDecimal.valueOf(2),
            BigDecimal.valueOf(3)
        )).hasCircumference(BigDecimal.valueOf(10));
    }

    // endregion

    // region circumCircle

    @Test
    void circumCircleOfRectWithHeightAndWidth1() {
        assertThatCircle(
            new BigDecimalRect(BigDecimal.ONE, BigDecimal.ONE).circumCircle()
        ).hasCenter(new BigDecimalPoint(BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.5)))
            .hasRadius(BigDecimal.valueOf(Math.sqrt(2d) / 2d));
    }

    // endregion

    // region isSquare

    @Test
    void isSquareWithEqualHeightAndWidth() {
        assertThatRect(new BigDecimalRect(BigDecimal.ONE, BigDecimal.ONE)).isSquare();
    }

    @Test
    void isSquareWithUnequalHeightAndWidth() {
        assertThatRect(new BigDecimalRect(BigDecimal.ONE, BigDecimal.valueOf(2))).isNoSquare();
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfRectWithA0_5B1_5S2_5() {
        BigDecimalRect rect = new BigDecimalRect(
            new BigDecimalPoint(BigDecimal.valueOf(0.5)),
            new BigDecimalPoint(BigDecimal.valueOf(1.5)),
            BigDecimal.valueOf(2.5)
        );
        IntegerArithmetic integerArithmetic = new IntegerArithmetic();
        Rect<Integer> result = new Rect<>(integerArithmetic,
            new Point<>(integerArithmetic, 0),
            new Point<>(integerArithmetic, 1),
            2
        );
        assertThatRect(rect.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithPositiveValues() {
        assertThatRect(new BigDecimalRect(BigDecimal.ONE, BigDecimal.ONE)).isValid();
    }

    @Test
    void isValidWithZeroValues() {
        assertThatRect(new BigDecimalRect(BigDecimal.ZERO, BigDecimal.ZERO)).isInvalid();
    }

    @Test
    void isValidWithZeroHeight() {
        assertThatRect(new BigDecimalRect(BigDecimal.ZERO, BigDecimal.ONE)).isInvalid();
    }

    @Test
    void isValidWithZeroWidth() {
        assertThatRect(new BigDecimalRect(BigDecimal.ONE, BigDecimal.ZERO)).isInvalid();
    }

    @Test
    void isValidWithNegativeValues() {
        assertThatRect(new BigDecimalRect(
            BigDecimal.ONE.negate(), BigDecimal.ONE.negate()
        )).isValid();
    }

    @Test
    void moveOfRectWithXYWithXY() {
        assertThatRect(
            new BigDecimalRect(
                BigDecimal.ONE,
                BigDecimal.valueOf(2)
            ).move(BigDecimal.ONE)
        ).hasA(new BigDecimalPoint(BigDecimal.ONE))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(3), BigDecimal.ONE))
            .hasSize(BigDecimal.ONE);
    }

    @Test
    void moveOfRectWithXYWithXAndY() {
        assertThatRect(
            new BigDecimalRect(BigDecimal.ONE, BigDecimal.valueOf(2))
                .move(BigDecimal.ONE, BigDecimal.ONE)
        ).hasA(new BigDecimalPoint(BigDecimal.ONE))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(3), BigDecimal.ONE))
            .hasSize(BigDecimal.ONE);
    }

    @Test
    void moveOfRectWithXYWithVector() {
        assertThatRect(
            new BigDecimalRect(BigDecimal.ONE, BigDecimal.valueOf(2))
                .move(new BigDecimalVector(BigDecimal.ONE))
        ).hasA(new BigDecimalPoint(BigDecimal.ONE))
            .hasB(new BigDecimalPoint(BigDecimal.valueOf(3), BigDecimal.ONE))
            .hasSize(BigDecimal.ONE);
    }

    @Test
    void rotateOfRectWidthHeight1AndWidth2WithoutCenterWithPiHalf() {
        assertThatRect(
            new BigDecimalRect(new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
                BigDecimal.valueOf(2)
            ).rotate(BigDecimal.valueOf(Math.PI / 2d))
        ).hasAWithZeroXY()
            .hasB(new BigDecimalPoint(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(6.123233995736766e-17d)
            ))
            .hasSize(BigDecimal.valueOf(2));
    }

    @Test
    void rotateOfRectWithHeight1AndWidth2WithCenterXY1WithPiHalf() {
        assertThatRect(
            new BigDecimalRect(new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
                BigDecimal.valueOf(2)
            ).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI / 2d)
            )
        ).hasA(new BigDecimalPoint(BigDecimal.valueOf(2), BigDecimal.ZERO))
            .hasBCloseTo(new BigDecimalPoint(
                BigDecimal.valueOf(1),
                BigDecimal.ZERO
            ))
            .hasSize(BigDecimal.valueOf(2));
    }

    @Test
    void copyOfRectWithPointHeightAndWidth() {
        assertCopyable(new BigDecimalRect(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(3)),
            BigDecimal.valueOf(4)
        ));
    }

    // endregion

    // region override

    @Test
    void equalsOfRectWithPointHeightAndWidth() {
        assertThatRect(new BigDecimalRect(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(3)),
            BigDecimal.valueOf(4)
        )).isEqualTo(new BigDecimalRect(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(3)),
            BigDecimal.valueOf(4)
        )).isNotEqualTo(new BigDecimalRect(
            new BigDecimalPoint(BigDecimal.valueOf(2)),
            new BigDecimalPoint(BigDecimal.valueOf(4)),
            BigDecimal.valueOf(3)
        ));
    }

    @Test
    void hashCodeOfRectWithPointHeightAndWidth() {
        assertThat(new BigDecimalRect(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            BigDecimal.valueOf(4d)
        ).hashCode()).isEqualTo(21004888);
    }

    @Test
    void toStringOfRectWithPointHeightAndWidth() {
        Rect<BigDecimal> rect = new BigDecimalRect(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            BigDecimal.valueOf(4d)
        );
        assertThatRect(rect).hasToString("2.0|2.0 3.0|3.0 4.0");
    }

    @Test
    void compareToOfRectWithPointHeightAndWidth() {
        Rect<BigDecimal> rect = new BigDecimalRect(
            BigDecimal.ONE, BigDecimal.valueOf(2)
        );
        assertThatRect(rect)
            .isEqualByComparingTo(new BigDecimalRect(
                BigDecimal.ONE, BigDecimal.valueOf(2)
            ))
            .isLessThan(new BigDecimalRect(
                BigDecimal.valueOf(2), BigDecimal.valueOf(3)
            ))
            .isGreaterThan(new BigDecimalRect(
                BigDecimal.ONE, BigDecimal.ONE
            ));
    }

    @Test
    void serializable() {
        assertSerializable(
            new BigDecimalRect(BigDecimal.ZERO, BigDecimal.ZERO),
            BigDecimalRect.class
        );
    }

    // endregion
}
