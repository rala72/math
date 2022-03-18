package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Rect;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class DoubleRectTest {
    // region constructors, getter and setter

    @Test
    void constructorWithHeightAndWidthButWithoutPoints() {
        assertThatRect(new DoubleRect(1d, 2d)).hasHeight(1d).hasWidth(2d);
    }

    @Test
    void constructorWithPoint1AndSize() {
        assertThatRect(
            new DoubleRect(new DoublePoint(0d), new DoublePoint(1d), 2d)
        ).hasAWithZeroXY()
            .hasB(new DoublePoint(1d))
            .hasSize(2d);
    }

    @Test
    void createAndSetA() {
        Rect<Double> rect = new DoubleRect(0d, 0d);
        assertThatRect(rect).hasHeight(0d).hasWidth(0d);
        rect.setA(new DoublePoint(1d));
        assertThatRect(rect)
            .hasA(new DoublePoint(1d))
            .hasBWithZeroXY()
            .hasSize(0d);
    }

    @Test
    void createAndSetB() {
        Rect<Double> rect = new DoubleRect(0d, 0d);
        assertThatRect(rect).hasHeight(0d).hasWidth(0d);
        rect.setB(new DoublePoint(2d));
        assertThatRect(rect)
            .hasAWithZeroXY()
            .hasB(new DoublePoint(2d))
            .hasSize(0d);
    }

    @Test
    void createAndSetSize() {
        Rect<Double> rect = new DoubleRect(0d, 0d);
        assertThatRect(rect).hasHeight(0d).hasWidth(0d);
        rect.setSize(2d);
        assertThatRect(rect).hasHeight(0d).hasWidth(2d);
    }

    // endregion

    // region vertexes

    @Test
    void vertexesOfRectWithHeight1AndWidth2() {
        Rect<Double> rect = new DoubleRect(1d, 2d);
        assertThatPoint(rect.vertexA()).hasX(0d).hasY(0d);
        assertThatPoint(rect.vertexB()).hasX(2d).hasY(0d);
        assertThatPoint(rect.vertexC()).hasX(2d).hasY(1d);
        assertThatPoint(rect.vertexD()).hasX(0d).hasY(1d);
    }

    @Test
    void vertexesOfRectWithPointsAndSize() {
        Rect<Double> rect = new DoubleRect(
            new DoublePoint(1d, 1d),
            new DoublePoint(0d, 1d),
            2d
        );
        assertThatPoint(rect.vertexA()).hasX(1d).hasY(1d);
        assertThatPoint(rect.vertexB()).hasX(0d).hasY(1d);
        assertThatPoint(rect.vertexC()).hasX(0d).hasY(3d);
        assertThatPoint(rect.vertexD()).hasX(1d).hasY(3d);
    }

    // endregion

    // region height, width and diagonale

    @Test
    void heightOfRectWithPointsAndPositiveSize() {
        assertThatRect(new DoubleRect(new DoublePoint(), new DoublePoint(1d), 1d)).hasHeight(1d);
    }

    @Test
    void heightOfRectWithPointsAndNegativeSize() {
        assertThatRect(new DoubleRect(new DoublePoint(), new DoublePoint(1d), -1d)).hasHeight(1d);
    }

    @Test
    void heightOfRectWithHeight1AndWidth0() {
        assertThatRect(new DoubleRect(1d, 0d)).hasHeight(0d);
    }

    @Test
    void widthOfRectWithPointsAndPositiveSize() {
        assertThatRect(new DoubleRect(new DoublePoint(), new DoublePoint(1d), 1d))
            .hasWidth(Math.sqrt(2d));
    }

    @Test
    void widthOfRectWithPointsAndNegativeSize() {
        assertThatRect(new DoubleRect(new DoublePoint(), new DoublePoint(1d), -1d))
            .hasWidth(Math.sqrt(2d));
    }

    @Test
    void widthOfRectWithHeight1AndWidth0() {
        assertThatRect(new DoubleRect(1d, 0d)).hasWidth(1d);
    }

    @Test
    void diagonaleOfRectWithHeightAndWidth1() {
        assertThatRect(new DoubleRect(1d, 1d)).hasDiagonale(Math.sqrt(2d));
    }

    @Test
    void diagonaleOfRectWithHeight1AndWidth2() {
        assertThatRect(new DoubleRect(1d, 2d)).hasDiagonale(Math.sqrt(5d));
    }

    @Test
    void diagonaleOfRectWithHeight2AndWidth3() {
        assertThatRect(new DoubleRect(2d, 3d)).hasDiagonale(Math.sqrt(13d));
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfRectWithHeightAndWidth1() {
        assertThatRect(new DoubleRect(1d, 1d)).hasArea(1d);
    }

    @Test
    void areaOfRectWithHeight1AndWidth2() {
        assertThatRect(new DoubleRect(1d, 2d)).hasArea(2d);
    }

    @Test
    void areaOfRectWithHeight2AndWidth3() {
        assertThatRect(new DoubleRect(2d, 3d)).hasArea(6d);
    }

    @Test
    void circumferenceOfRectWithHeightAndWidth1() {
        assertThatRect(new DoubleRect(1d, 1d)).hasCircumference(4d);
    }

    @Test
    void circumferenceOfRectWithHeight1AndWidth2() {
        assertThatRect(new DoubleRect(1d, 2d)).hasCircumference(6d);
    }

    @Test
    void circumferenceOfRectWithHeight2AndWidth3() {
        assertThatRect(new DoubleRect(2d, 3d)).hasCircumference(10d);
    }

    // endregion

    // region circumCircle

    @Test
    void circumCircleOfRectWithHeightAndWidth1() {
        assertThatCircle(new DoubleRect(1d, 1d).circumCircle())
            .hasCenter(new DoublePoint(0.5, 0.5))
            .hasRadius(Math.sqrt(2d) / 2d);
    }

    // endregion

    // region isSquare

    @Test
    void isSquareWithEqualHeightAndWidth() {
        assertThatRect(new DoubleRect(1d, 1d)).isSquare();
    }

    @Test
    void isSquareWithUnequalHeightAndWidth() {
        assertThatRect(new DoubleRect(1d, 2d)).isNoSquare();
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfRectWithA0_5B1_5S2_5() {
        DoubleRect rect = new DoubleRect(
            new DoublePoint(0.5), new DoublePoint(1.5), 2.5
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
        assertThatRect(new DoubleRect(1d, 1d)).isValid();
    }

    @Test
    void isValidWithZeroValues() {
        assertThatRect(new DoubleRect(0d, 0d)).isInvalid();
    }

    @Test
    void isValidWithZeroHeight() {
        assertThatRect(new DoubleRect(0d, 1d)).isInvalid();
    }

    @Test
    void isValidWithZeroWidth() {
        assertThatRect(new DoubleRect(1d, 0d)).isInvalid();
    }

    @Test
    void isValidWithNegativeValues() {
        assertThatRect(new DoubleRect(-1d, -1d)).isValid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatRect(new DoubleRect(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY))
            .isInvalid();
    }

    @Test
    void moveOfRectWithXYWithXY() {
        assertThatRect(
            new DoubleRect(1d, 2d).move(1d)
        ).hasA(new DoublePoint(1d))
            .hasB(new DoublePoint(3d, 1d))
            .hasSize(1d);
    }

    @Test
    void moveOfRectWithXYWithXAndY() {
        assertThatRect(
            new DoubleRect(1d, 2d).move(1d, 1d)
        ).hasA(new DoublePoint(1d))
            .hasB(new DoublePoint(3d, 1d))
            .hasSize(1d);
    }

    @Test
    void moveOfRectWithXYWithVector() {
        assertThatRect(
            new DoubleRect(1d, 2d).move(new DoubleVector(1d))
        ).hasA(new DoublePoint(1d))
            .hasB(new DoublePoint(3d, 1d))
            .hasSize(1d);
    }

    @Test
    void rotateOfRectWidthHeight1AndWidth2WithoutCenterWithPiHalf() {
        assertThatRect(
            new DoubleRect(new DoublePoint(), new DoublePoint(0d, 1d), 2d)
                .rotate(Math.PI / 2d)
        ).hasAWithZeroXY()
            .hasB(new DoublePoint(-1d, 6.123233995736766e-17d))
            .hasSize(2d);
    }

    @Test
    void rotateOfRectWithHeight1AndWidth2WithCenterXY1WithPiHalf() {
        assertThatRect(
            new DoubleRect(new DoublePoint(), new DoublePoint(0d, 1d), 2d)
                .rotate(new DoublePoint(1d), Math.PI / 2d)
        ).hasA(new DoublePoint(2d, 0d))
            .hasBCloseTo(new DoublePoint(1d, 0d))
            .hasSize(2d);
    }

    @Test
    void copyOfRectWithPointHeightAndWidth() {
        assertCopyable(new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d));
    }

    // endregion

    // region override

    @Test
    void equalsOfRectWithPointHeightAndWidth() {
        assertThatRect(new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d))
            .isEqualTo(new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d))
            .isNotEqualTo(new DoubleRect(new DoublePoint(2d), new DoublePoint(4d), 3d));
    }

    @Test
    void hashCodeOfRectWithPointHeightAndWidth() {
        assertThat(new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d).hashCode())
            .isEqualTo(1595867199);
    }

    @Test
    void toStringOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d);
        assertThatRect(rect).hasToString("2.0|2.0 3.0|3.0 4.0");
    }

    @Test
    void compareToOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(1d, 2d);
        assertThatRect(rect)
            .isEqualByComparingTo(new DoubleRect(1d, 2d))
            .isLessThan(new DoubleRect(2d, 3d))
            .isGreaterThan(new DoubleRect(1d, 1d));
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleRect(0d, 0d), DoubleRect.class);
    }

    // endregion
}
