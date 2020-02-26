package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Rect;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(BigDecimal.ONE,
            new BigDecimalRect(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.ONE
            ).height()
        );
    }

    @Test
    void heightOfRectWithPointsAndNegativeSize() {
        assertEquals(BigDecimal.ONE,
            new BigDecimalRect(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.ONE.negate()
            ).height()
        );
    }

    @Test
    void heightOfRectWithHeight1AndWidth0() {
        assertEquals(BigDecimal.ZERO,
            new BigDecimalRect(
                BigDecimal.ONE,
                BigDecimal.ZERO
            ).height()
        );
    }

    @Test
    void widthOfRectWithPointsAndPositiveSize() {
        assertEquals(
            BigDecimal.valueOf(Math.sqrt(2d)).round(GeometryAssertions.CONTEXT),
            new BigDecimalRect(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.ONE
            ).width()
        );
    }

    @Test
    void widthOfRectWithPointsAndNegativeSize() {
        assertEquals(
            BigDecimal.valueOf(Math.sqrt(2d)).round(GeometryAssertions.CONTEXT),
            new BigDecimalRect(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.ONE.negate()
            ).width()
        );
    }

    @Test
    void widthOfRectWithHeight1AndWidth0() {
        assertEquals(BigDecimal.ONE,
            new BigDecimalRect(
                BigDecimal.ONE,
                BigDecimal.ZERO
            ).width()
        );
    }

    @Test
    void diagonaleOfRectWithHeightAndWidth1() {
        assertEquals(
            BigDecimal.valueOf(Math.sqrt(2d)).round(GeometryAssertions.CONTEXT),
            new BigDecimalRect(
                BigDecimal.ONE,
                BigDecimal.ONE
            ).diagonale()
        );
    }

    @Test
    void diagonaleOfRectWithHeight1AndWidth2() {
        assertEquals(
            BigDecimal.valueOf(Math.sqrt(5d)).round(GeometryAssertions.CONTEXT),
            new BigDecimalRect(
                BigDecimal.ONE,
                BigDecimal.valueOf(2d)
            ).diagonale()
        );
    }

    @Test
    void diagonaleOfRectWithHeight2AndWidth3() {
        assertEquals(
            BigDecimal.valueOf(Math.sqrt(13d)).round(GeometryAssertions.CONTEXT),
            new BigDecimalRect(
                BigDecimal.valueOf(2d),
                BigDecimal.valueOf(3d)
            ).diagonale()
        );
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfRectWithHeightAndWidth1() {
        assertEquals(BigDecimal.ONE,
            new BigDecimalRect(
                BigDecimal.ONE,
                BigDecimal.ONE
            ).area()
        );
    }

    @Test
    void areaOfRectWithHeight1AndWidth2() {
        assertEquals(BigDecimal.valueOf(2),
            new BigDecimalRect(
                BigDecimal.ONE,
                BigDecimal.valueOf(2d)
            ).area()
        );
    }

    @Test
    void areaOfRectWithHeight2AndWidth3() {
        assertEquals(BigDecimal.valueOf(6),
            new BigDecimalRect(
                BigDecimal.valueOf(2d),
                BigDecimal.valueOf(3d)
            ).area()
        );
    }

    @Test
    void circumferenceOfRectWithHeightAndWidth1() {
        assertEquals(BigDecimal.valueOf(4),
            new BigDecimalRect(
                BigDecimal.ONE,
                BigDecimal.ONE
            ).circumference()
        );
    }

    @Test
    void circumferenceOfRectWithHeight1AndWidth2() {
        assertEquals(BigDecimal.valueOf(6),
            new BigDecimalRect(
                BigDecimal.ONE,
                BigDecimal.valueOf(2d)
            ).circumference()
        );
    }

    @Test
    void circumferenceOfRectWithHeight2AndWidth3() {
        assertEquals(BigDecimal.valueOf(10),
            new BigDecimalRect(
                BigDecimal.valueOf(2d),
                BigDecimal.valueOf(3d)
            ).circumference()
        );
    }

    // endregion

    // region circumCircle

    @Test
    void circumCircleOfRectWithHeightAndWidth1() {
        assertCircle(
            new BigDecimalRect(BigDecimal.ONE, BigDecimal.ONE).circumCircle(),
            new BigDecimalPoint(BigDecimal.valueOf(0.5d), BigDecimal.valueOf(0.5d)),
            BigDecimal.valueOf(Math.sqrt(2d) / 2d)
        );
    }

    // endregion

    // region isSquare

    @Test
    void isSquareWithEqualHeightAndWidth() {
        assertTrue(
            new BigDecimalRect(BigDecimal.ONE, BigDecimal.ONE).isSquare()
        );
    }

    @Test
    void isSquareWithUnequalHeightAndWidth() {
        assertFalse(
            new BigDecimalRect(BigDecimal.ONE, BigDecimal.valueOf(2d)).isSquare()
        );
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
        assertEquals(result,
            rect.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithPositiveValues() {
        assertTrue(
            new BigDecimalRect(BigDecimal.ONE, BigDecimal.ONE).isValid()
        );
    }

    @Test
    void isValidWithZeroValues() {
        assertFalse(
            new BigDecimalRect(BigDecimal.ZERO, BigDecimal.ZERO).isValid()
        );
    }

    @Test
    void isValidWithZeroHeight() {
        assertFalse(
            new BigDecimalRect(BigDecimal.ZERO, BigDecimal.ONE).isValid()
        );
    }

    @Test
    void isValidWithZeroWidth() {
        assertFalse(
            new BigDecimalRect(BigDecimal.ONE, BigDecimal.ZERO).isValid()
        );
    }

    @Test
    void isValidWithNegativeValues() {
        assertTrue(
            new BigDecimalRect(
                BigDecimal.ONE.negate(), BigDecimal.ONE.negate()
            ).isValid()
        );
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
                BigDecimal.valueOf(0.9999999999999999d),
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
        assertEquals(rect, rect.copy());
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
        assertEquals(rect,
            new BigDecimalRect(new BigDecimalPoint(
                BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(3d)),
                BigDecimal.valueOf(4d))
        );
        assertNotEquals(rect,
            new BigDecimalRect(new BigDecimalPoint(
                BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(4d)),
                BigDecimal.valueOf(3d)
            )
        );
    }

    @Test
    void hashCodeOfRectWithPointHeightAndWidth() {
        assertEquals(21004888,
            new BigDecimalRect(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(3d)),
                BigDecimal.valueOf(4d)
            ).hashCode()
        );
    }

    @Test
    void toStringOfRectWithPointHeightAndWidth() {
        Rect<BigDecimal> rect = new BigDecimalRect(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d)),
            BigDecimal.valueOf(4d)
        );
        assertEquals("2.0|2.0 3.0|3.0 4.0", rect.toString());
    }

    @Test
    void compareToOfRectWithPointHeightAndWidth() {
        Rect<BigDecimal> rect = new BigDecimalRect(
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
        assertEquals(0,
            rect.compareTo(new BigDecimalRect(
                BigDecimal.ONE, BigDecimal.valueOf(2d)
            ))
        );
        assertEquals(-1,
            rect.compareTo(new BigDecimalRect(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
            ))
        );
        assertEquals(1,
            rect.compareTo(new BigDecimalRect(
                BigDecimal.ONE, BigDecimal.ONE
            ))
        );
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
