package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Rect;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalRectTest {
    // region constructors, getter and setter

    @Test
    void constructorWithHeightAndWidthButWithoutPoints() {
        assertRect(
            new BigDecimalRect(BigDecimal.ONE, BigDecimal.valueOf(2d)),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithHeightAndWidthAndMathContext5ButWithoutPoints() {
        assertRect(
            new BigDecimalRect(
                BigDecimal.ONE, BigDecimal.valueOf(2d),
                new MathContext(5)
            ),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithPoint1AndSize() {
        assertRect(
            new BigDecimalRect(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(2d)
            ),
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithPoint1AndSizeAndMathContext5() {
        assertRect(
            new BigDecimalRect(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(2d),
                new MathContext(5)
            ),
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void createAndSetA() {
        Rect<BigDecimal> rect = new BigDecimalRect(BigDecimal.ZERO, BigDecimal.ZERO);
        assertRect(rect, BigDecimal.ZERO, BigDecimal.ZERO);
        rect.setA(new BigDecimalPoint(BigDecimal.ONE));
        assertRect(rect,
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ZERO),
            BigDecimal.ZERO
        );
    }

    @Test
    void createAndSetB() {
        Rect<BigDecimal> rect = new BigDecimalRect(BigDecimal.ZERO, BigDecimal.ZERO);
        assertRect(rect, BigDecimal.ZERO, BigDecimal.ZERO);
        rect.setB(new BigDecimalPoint(BigDecimal.valueOf(2d)));
        assertRect(rect,
            new BigDecimalPoint(BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            BigDecimal.ZERO);
    }

    @Test
    void createAndSetSize() {
        Rect<BigDecimal> rect = new BigDecimalRect(BigDecimal.ZERO, BigDecimal.ZERO);
        assertRect(rect, BigDecimal.ZERO, BigDecimal.ZERO);
        rect.setSize(BigDecimal.valueOf(2d));
        assertRect(rect, BigDecimal.valueOf(2d), BigDecimal.ZERO);
    }

    // endregion

    // region vertexes

    @Test
    void vertexesOfRectWithHeight1AndWidth2() {
        Rect<BigDecimal> rect = new BigDecimalRect(
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
        assertPoint(rect.vertexA(),
            BigDecimal.ZERO, BigDecimal.ZERO
        );
        assertPoint(rect.vertexB(),
            BigDecimal.valueOf(2d), BigDecimal.ZERO
        );
        assertPoint(rect.vertexC(),
            BigDecimal.valueOf(2d), BigDecimal.ONE
        );
        assertPoint(rect.vertexD(),
            BigDecimal.ZERO, BigDecimal.ONE
        );
    }

    @Test
    void vertexesOfRectWithPointsAndSize() {
        Rect<BigDecimal> rect = new BigDecimalRect(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
            BigDecimal.valueOf(2d)
        );
        assertPoint(rect.vertexA(),
            BigDecimal.ONE, BigDecimal.ONE
        );
        assertPoint(rect.vertexB(),
            BigDecimal.ZERO, BigDecimal.ONE
        );
        assertPoint(rect.vertexC(),
            BigDecimal.ZERO, BigDecimal.valueOf(3d)
        );
        assertPoint(rect.vertexD(),
            BigDecimal.ONE, BigDecimal.valueOf(3d)
        );
    }

    // endregion

    // region height, width and diagonale

    @Test
    void heightOfRectWithPointsAndPositiveSize() {
        assertThat(new BigDecimalRect(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.ONE
        ).height()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void heightOfRectWithPointsAndNegativeSize() {
        assertThat(new BigDecimalRect(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.ONE.negate()
        ).height()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void heightOfRectWithHeight1AndWidth0() {
        assertThat(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.ZERO
        ).height()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void widthOfRectWithPointsAndPositiveSize() {
        assertThat(new BigDecimalRect(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.ONE
        ).width()).isEqualTo(BigDecimal.valueOf(2d).sqrt(CONTEXT));
    }

    @Test
    void widthOfRectWithPointsAndNegativeSize() {
        assertThat(new BigDecimalRect(
            new BigDecimalPoint(),
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.ONE.negate()
        ).width()).isEqualTo(BigDecimal.valueOf(2d).sqrt(CONTEXT));
    }

    @Test
    void widthOfRectWithHeight1AndWidth0() {
        assertThat(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.ZERO
        ).width()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void diagonaleOfRectWithHeightAndWidth1() {
        assertThat(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.ONE
        ).diagonale()).isEqualTo(BigDecimal.valueOf(2d).sqrt(CONTEXT));
    }

    @Test
    void diagonaleOfRectWithHeight1AndWidth2() {
        assertThat(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.valueOf(2d)
        ).diagonale()).isEqualTo(BigDecimal.valueOf(5d).sqrt(CONTEXT));
    }

    @Test
    void diagonaleOfRectWithHeight2AndWidth3() {
        assertThat(new BigDecimalRect(
            BigDecimal.valueOf(2d),
            BigDecimal.valueOf(3d)
        ).diagonale()).isEqualTo(BigDecimal.valueOf(13d).sqrt(CONTEXT));
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfRectWithHeightAndWidth1() {
        assertThat(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.ONE
        ).area()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void areaOfRectWithHeight1AndWidth2() {
        assertThat(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.valueOf(2d)
        ).area()).isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void areaOfRectWithHeight2AndWidth3() {
        assertThat(new BigDecimalRect(
            BigDecimal.valueOf(2d),
            BigDecimal.valueOf(3d)
        ).area()).isEqualTo(BigDecimal.valueOf(6));
    }

    @Test
    void circumferenceOfRectWithHeightAndWidth1() {
        assertThat(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.ONE
        ).circumference()).isEqualTo(BigDecimal.valueOf(4));
    }

    @Test
    void circumferenceOfRectWithHeight1AndWidth2() {
        assertThat(new BigDecimalRect(
            BigDecimal.ONE,
            BigDecimal.valueOf(2d)
        ).circumference()).isEqualTo(BigDecimal.valueOf(6));
    }

    @Test
    void circumferenceOfRectWithHeight2AndWidth3() {
        assertThat(new BigDecimalRect(
            BigDecimal.valueOf(2d),
            BigDecimal.valueOf(3d)
        ).circumference()).isEqualTo(BigDecimal.valueOf(10));
    }

    // endregion

    // region circumCircle

    @Test
    void circumCircleOfRectWithHeightAndWidth1() {
        assertCircle(
            new BigDecimalRect(BigDecimal.ONE, BigDecimal.ONE).circumCircle(),
            new BigDecimalPoint(BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.5)),
            BigDecimal.valueOf(Math.sqrt(2d) / 2d)
        );
    }

    // endregion

    // region isSquare

    @Test
    void isSquareWithEqualHeightAndWidth() {
        assertThat(new BigDecimalRect(BigDecimal.ONE, BigDecimal.ONE).isSquare()).isTrue();
    }

    @Test
    void isSquareWithUnequalHeightAndWidth() {
        assertThat(new BigDecimalRect(BigDecimal.ONE, BigDecimal.valueOf(2d)).isSquare()).isFalse();
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
        assertThat(rect.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithPositiveValues() {
        assertThat(new BigDecimalRect(BigDecimal.ONE, BigDecimal.ONE).isValid()).isTrue();
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new BigDecimalRect(BigDecimal.ZERO, BigDecimal.ZERO).isValid()).isFalse();
    }

    @Test
    void isValidWithZeroHeight() {
        assertThat(new BigDecimalRect(BigDecimal.ZERO, BigDecimal.ONE).isValid()).isFalse();
    }

    @Test
    void isValidWithZeroWidth() {
        assertThat(new BigDecimalRect(BigDecimal.ONE, BigDecimal.ZERO).isValid()).isFalse();
    }

    @Test
    void isValidWithNegativeValues() {
        assertThat(new BigDecimalRect(
            BigDecimal.ONE.negate(), BigDecimal.ONE.negate()
        ).isValid()).isTrue();
    }

    @Test
    void moveOfRectWithXYWithXY() {
        assertRect(
            new BigDecimalRect(
                BigDecimal.ONE,
                BigDecimal.valueOf(2d)
            ).move(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(3d), BigDecimal.ONE),
            BigDecimal.ONE
        );
    }

    @Test
    void moveOfRectWithXYWithXAndY() {
        assertRect(
            new BigDecimalRect(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .move(BigDecimal.ONE, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(3d), BigDecimal.ONE),
            BigDecimal.ONE
        );
    }

    @Test
    void moveOfRectWithXYWithVector() {
        assertRect(
            new BigDecimalRect(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .move(new BigDecimalVector(BigDecimal.ONE)),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(3d), BigDecimal.ONE),
            BigDecimal.ONE
        );
    }

    @Test
    void rotateOfRectWidthHeight1AndWidth2WithoutCenterWithPiHalf() {
        assertRect(
            new BigDecimalRect(new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
                BigDecimal.valueOf(2d)
            ).rotate(BigDecimal.valueOf(Math.PI / 2d)),
            new BigDecimalPoint(),
            new BigDecimalPoint(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(6.123233995736766e-17d)
            ),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void rotateOfRectWithHeight1AndWidth2WithCenterXY1WithPiHalf() {
        assertRect(
            new BigDecimalRect(new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE),
                BigDecimal.valueOf(2d)
            ).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI / 2d)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.ZERO),
            new BigDecimalPoint(
                BigDecimal.valueOf(0.9999999999999999),
                BigDecimal.ZERO
            ),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void copyOfRectWithPointHeightAndWidth() {
        Rect<BigDecimal> rect = new BigDecimalRect(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            BigDecimal.valueOf(4d)
        );
        assertThat(rect.copy()).isEqualTo(rect);
    }

    // endregion

    // region override

    @Test
    void equalsOfRectWithPointHeightAndWidth() {
        Rect<BigDecimal> rect = new BigDecimalRect(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            BigDecimal.valueOf(4d)
        );
        assertThat(new BigDecimalRect(new BigDecimalPoint(
            BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            BigDecimal.valueOf(4d))).isEqualTo(rect);
        assertThat(new BigDecimalRect(new BigDecimalPoint(
            BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(4d)),
            BigDecimal.valueOf(3d)
        )).isNotEqualTo(rect);
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
        assertThat(rect).hasToString("2.0|2.0 3.0|3.0 4.0");
    }

    @Test
    void compareToOfRectWithPointHeightAndWidth() {
        Rect<BigDecimal> rect = new BigDecimalRect(
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
        assertThat(rect)
            .isEqualByComparingTo(new BigDecimalRect(
                BigDecimal.ONE, BigDecimal.valueOf(2d)
            ))
            .isLessThan(new BigDecimalRect(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
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
