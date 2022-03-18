package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestRect;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class RectTest {
    // region constructors, getter and setter

    @Test
    void constructorWithHeightAndWidthButWithoutPoints() {
        assertThatRect(new TestRect(1, 2)).hasHeight(1).hasWidth(2d);
    }

    @Test
    void constructorWithPoint1AndSize() {
        assertThatRect(
            new TestRect(new TestPoint(0), new TestPoint(1), 2)
        ).hasA(new TestPoint(0)).hasB(new TestPoint(1)).hasSize(2);
    }

    @Test
    void createAndSetA() {
        Rect<Number> rect = new TestRect(0, 0);
        assertThatRect(rect).hasHeight(0).hasWidth(0);
        rect.setA(new TestPoint(1));
        assertThatRect(rect).hasA(new TestPoint(1)).hasB(new TestPoint(0, 0d)).hasSize(0);
    }

    @Test
    void createAndSetB() {
        Rect<Number> rect = new TestRect(0, 0);
        assertThatRect(rect).hasHeight(0).hasWidth(0);
        rect.setB(new TestPoint(2));
        assertThatRect(rect).hasAWithZeroXY().hasB(new TestPoint(2)).hasSize(0);
    }

    @Test
    void createAndSetSize() {
        Rect<Number> rect = new TestRect(0, 0);
        assertThatRect(rect).hasHeight(0).hasWidth(0);
        rect.setSize(2);
        assertThatRect(rect).hasHeight(0d).hasWidth(2);
    }

    // endregion

    // region vertexes

    @Test
    void vertexesOfRectWithHeight1AndWidth2() {
        Rect<Number> rect = new TestRect(1, 2);
        assertThatPoint(rect.vertexA()).hasX(0).hasY(0);
        assertThatPoint(rect.vertexB()).hasX(2).hasY(0);
        assertThatPoint(rect.vertexC()).hasX(2).hasY(1);
        assertThatPoint(rect.vertexD()).hasX(0).hasY(1);
    }

    @Test
    void vertexesOfRectWithPointsAndSize() {
        Rect<Number> rect = new TestRect(new TestPoint(1, 1), new TestPoint(0, 1), 2);
        assertThatPoint(rect.vertexA()).hasX(1).hasY(1);
        assertThatPoint(rect.vertexB()).hasX(0).hasY(1);
        assertThatPoint(rect.vertexC()).hasX(0).hasY(3);
        assertThatPoint(rect.vertexD()).hasX(1).hasY(3);
    }

    // endregion

    // region height, width and diagonale

    @Test
    void heightOfRectWithPointsAndPositiveSize() {
        assertThatRect(new TestRect(new TestPoint(), new TestPoint(1), 1)).hasHeight(1);
    }

    @Test
    void heightOfRectWithPointsAndNegativeSize() {
        assertThatRect(new TestRect(new TestPoint(), new TestPoint(1), -1)).hasHeight(1d);
    }

    @Test
    void heightOfRectWithHeight1AndWidth0() {
        assertThatRect(new TestRect(1, 0)).hasHeight(0d);
    }

    @Test
    void widthOfRectWithPointsAndPositiveSize() {
        assertThatRect(new TestRect(new TestPoint(), new TestPoint(1), 1)).hasWidth(Math.sqrt(2));
    }

    @Test
    void widthOfRectWithPointsAndNegativeSize() {
        assertThatRect(new TestRect(new TestPoint(), new TestPoint(1), -1)).hasWidth(Math.sqrt(2));
    }

    @Test
    void widthOfRectWithHeight1AndWidth0() {
        assertThatRect(new TestRect(1, 0)).hasWidth(1);
    }

    @Test
    void diagonaleOfRectWithHeightAndWidth1() {
        assertThatRect(new TestRect(1, 1)).hasDiagonale(Math.sqrt(2));
    }

    @Test
    void diagonaleOfRectWithHeight1AndWidth2() {
        assertThatRect(new TestRect(1, 2)).hasDiagonale(Math.sqrt(5));
    }

    @Test
    void diagonaleOfRectWithHeight2AndWidth3() {
        assertThatRect(new TestRect(2, 3)).hasDiagonale(Math.sqrt(13));
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfRectWithHeightAndWidth1() {
        assertThatRect(new TestRect(1, 1)).hasArea(1d);
    }

    @Test
    void areaOfRectWithHeight1AndWidth2() {
        assertThatRect(new TestRect(1, 2)).hasArea(2d);
    }

    @Test
    void areaOfRectWithHeight2AndWidth3() {
        assertThatRect(new TestRect(2, 3)).hasArea(6d);
    }

    @Test
    void circumferenceOfRectWithHeightAndWidth1() {
        assertThatRect(new TestRect(1, 1)).hasCircumference(4d);
    }

    @Test
    void circumferenceOfRectWithHeight1AndWidth2() {
        assertThatRect(new TestRect(1, 2)).hasCircumference(6d);
    }

    @Test
    void circumferenceOfRectWithHeight2AndWidth3() {
        assertThatRect(new TestRect(2, 3)).hasCircumference(10d);
    }

    // endregion

    // region circumCircle

    @Test
    void circumCircleOfRectWithHeightAndWidth1() {
        assertThatCircle(new TestRect(1, 1).circumCircle())
            .hasCenter(new TestPoint(0.5, 0.5))
            .hasRadius(Math.sqrt(2) / 2);
    }

    @Test
    void circumCircleRadiusOfRectWithHeightAndWidth1() {
        assertThat(new TestRect(1, 1).circumCircleRadius()).isEqualTo(Math.sqrt(2) / 2);
    }

    @Test
    void circumCirclePointOfRectWithHeightAndWidth1() {
        assertThatPoint(new TestRect(1, 1).circumCirclePoint())
            .hasX(0.5).hasY(0.5);
    }

    // endregion

    // region isSquare

    @Test
    void isSquareWithEqualHeightAndWidth() {
        assertThatRect(new TestRect(1, 1)).isSquare();
    }

    @Test
    void isSquareWithUnequalHeightAndWidth() {
        assertThatRect(new TestRect(1, 2)).isNoSquare();
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfRectWithA0_5B1_5S2_5() {
        TestRect rect = new TestRect(
            new TestPoint(0.5), new TestPoint(1.5), 2.5
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
        assertThatRect(new TestRect(1, 1)).isValid();
    }

    @Test
    void isValidWithZeroValues() {
        assertThatRect(new TestRect(0d, 0d)).isInvalid();
    }

    @Test
    void isValidWithZeroHeight() {
        assertThatRect(new TestRect(0d, 1d)).isInvalid();
    }

    @Test
    void isValidWithZeroWidth() {
        assertThatRect(new TestRect(1d, 0d)).isInvalid();
    }

    @Test
    void isValidWithNegativeValues() {
        assertThatRect(new TestRect(-1, -1)).isValid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatRect(new TestRect(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)).isInvalid();
    }

    @Test
    void moveOfRectWithXYWithXY() {
        assertThatRect(new TestRect(1, 2).move(1))
            .hasA(new TestPoint(1d))
            .hasB(new TestPoint(3d, 1d))
            .hasSize(1);
    }

    @Test
    void moveOfRectWithXYWithXAndY() {
        assertThatRect(new TestRect(1, 2).move(1, 1))
            .hasA(new TestPoint(1d))
            .hasB(new TestPoint(3d, 1d))
            .hasSize(1);
    }

    @Test
    void moveOfRectWithXYWithVector() {
        assertThatRect(new TestRect(1, 2).move(new TestVector(1)))
            .hasA(new TestPoint(1d))
            .hasB(new TestPoint(3d, 1d))
            .hasSize(1);
    }

    @Test
    void rotateOfRectWidthHeight1AndWidth2WithoutCenterWithPiHalf() {
        assertThatRect(
            new TestRect(new TestPoint(), new TestPoint(0, 1), 2)
                .rotate(Math.PI / 2)
        ).hasAWithZeroXY()
            .hasB(new TestPoint(-1d, 6.123233995736766e-17))
            .hasSize(2);
    }

    @Test
    void rotateOfRectWithHeight1AndWidth2WithCenterXY1WithPiHalf() {
        assertThatRect(
            new TestRect(new TestPoint(), new TestPoint(0, 1), 2)
                .rotate(new TestPoint(1), Math.PI / 2)
        ).hasA(new TestPoint(2d, 0d))
            .hasBCloseTo(new TestPoint(1d, 0d))
            .hasSize(2);
    }

    @Test
    void copyOfRectWithPointHeightAndWidth() {
        assertCopyable(new TestRect(new TestPoint(2), new TestPoint(3), 4));
    }

    // endregion

    // region override

    @Test
    void equalsOfRectWithPointHeightAndWidth() {
        assertThatRect(new TestRect(new TestPoint(2), new TestPoint(3), 4))
            .isEqualTo(new TestRect(new TestPoint(2), new TestPoint(3), 4))
            .isNotEqualTo(new TestRect(new TestPoint(2), new TestPoint(4), 3));
    }

    @Test
    void hashCodeOfRectWithPointHeightAndWidth() {
        assertThat(new TestRect(new TestPoint(2), new TestPoint(3), 4).hashCode())
            .isEqualTo(1047587);
    }

    @Test
    void toStringOfRectWithPointHeightAndWidth() {
        Rect<Number> rect = new TestRect(new TestPoint(2d), new TestPoint(3d), 4d);
        assertThatRect(rect).hasToString("2.0|2.0 3.0|3.0 4.0");
    }

    @Test
    void compareToOfRectWithPointHeightAndWidth() {
        Rect<Number> rect = new TestRect(1, 2);
        assertThatRect(rect)
            .isEqualByComparingTo(new TestRect(1, 2))
            .isLessThan(new TestRect(2, 3))
            .isGreaterThan(new TestRect(1, 1));
    }

    @Test
    void serializable() {
        assertSerializable(new TestRect(0, 0), TestRect.class);
    }

    // endregion
}
