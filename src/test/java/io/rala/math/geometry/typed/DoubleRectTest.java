package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Rect;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class DoubleRectTest {
    // region constructors, getter and setter

    @Test
    void constructorWithHeightAndWidthButWithoutPoints() {
        assertRect(new DoubleRect(1d, 2d), 1d, 2d);
    }

    @Test
    void constructorWithPoint1AndSize() {
        assertRect(
            new DoubleRect(new DoublePoint(0d), new DoublePoint(1d), 2d),
            new DoublePoint(), new DoublePoint(1d), 2d
        );
    }

    @Test
    void createAndSetA() {
        Rect<Double> rect = new DoubleRect(0d, 0d);
        assertRect(rect, 0d, 0d);
        rect.setA(new DoublePoint(1d));
        assertRect(rect,
            new DoublePoint(1d),
            new DoublePoint(0d),
            0d
        );
    }

    @Test
    void createAndSetB() {
        Rect<Double> rect = new DoubleRect(0d, 0d);
        assertRect(rect, 0d, 0d);
        rect.setB(new DoublePoint(2d));
        assertRect(rect,
            new DoublePoint(0d),
            new DoublePoint(2d),
            0d
        );
    }

    @Test
    void createAndSetSize() {
        Rect<Double> rect = new DoubleRect(0d, 0d);
        assertRect(rect, 0d, 0d);
        rect.setSize(2d);
        assertRect(rect, 2d, 0d);
    }

    // endregion

    // region vertexes

    @Test
    void vertexesOfRectWithHeight1AndWidth2() {
        Rect<Double> rect = new DoubleRect(1d, 2d);
        assertPoint(rect.vertexA(), 0d, 0d);
        assertPoint(rect.vertexB(), 2d, 0d);
        assertPoint(rect.vertexC(), 2d, 1d);
        assertPoint(rect.vertexD(), 0d, 1d);
    }

    @Test
    void vertexesOfRectWithPointsAndSize() {
        Rect<Double> rect = new DoubleRect(
            new DoublePoint(1d, 1d),
            new DoublePoint(0d, 1d),
            2d
        );
        assertPoint(rect.vertexA(), 1d, 1d);
        assertPoint(rect.vertexB(), 0d, 1d);
        assertPoint(rect.vertexC(), 0d, 3d);
        assertPoint(rect.vertexD(), 1d, 3d);
    }

    // endregion

    // region height, width and diagonale

    @Test
    void heightOfRectWithPointsAndPositiveSize() {
        assertThat(new DoubleRect(new DoublePoint(), new DoublePoint(1d), 1d).height()).isEqualTo(1d);
    }

    @Test
    void heightOfRectWithPointsAndNegativeSize() {
        assertThat(new DoubleRect(new DoublePoint(), new DoublePoint(1d), -1d).height()).isEqualTo(1d);
    }

    @Test
    void heightOfRectWithHeight1AndWidth0() {
        assertThat(new DoubleRect(1d, 0d).height()).isEqualTo(0d);
    }

    @Test
    void widthOfRectWithPointsAndPositiveSize() {
        assertThat(new DoubleRect(new DoublePoint(), new DoublePoint(1d), 1d).width()).isEqualTo(Math.sqrt(2d));
    }

    @Test
    void widthOfRectWithPointsAndNegativeSize() {
        assertThat(new DoubleRect(new DoublePoint(), new DoublePoint(1d), -1d).width()).isEqualTo(Math.sqrt(2d));
    }

    @Test
    void widthOfRectWithHeight1AndWidth0() {
        assertThat(new DoubleRect(1d, 0d).width()).isEqualTo(1d);
    }

    @Test
    void diagonaleOfRectWithHeightAndWidth1() {
        assertThat(new DoubleRect(1d, 1d).diagonale()).isEqualTo(Math.sqrt(2d));
    }

    @Test
    void diagonaleOfRectWithHeight1AndWidth2() {
        assertThat(new DoubleRect(1d, 2d).diagonale()).isEqualTo(Math.sqrt(5d));
    }

    @Test
    void diagonaleOfRectWithHeight2AndWidth3() {
        assertThat(new DoubleRect(2d, 3d).diagonale()).isEqualTo(Math.sqrt(13d));
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfRectWithHeightAndWidth1() {
        assertThat(new DoubleRect(1d, 1d).area()).isEqualTo(1d);
    }

    @Test
    void areaOfRectWithHeight1AndWidth2() {
        assertThat(new DoubleRect(1d, 2d).area()).isEqualTo(2d);
    }

    @Test
    void areaOfRectWithHeight2AndWidth3() {
        assertThat(new DoubleRect(2d, 3d).area()).isEqualTo(6d);
    }

    @Test
    void circumferenceOfRectWithHeightAndWidth1() {
        assertThat(new DoubleRect(1d, 1d).circumference()).isEqualTo(4d);
    }

    @Test
    void circumferenceOfRectWithHeight1AndWidth2() {
        assertThat(new DoubleRect(1d, 2d).circumference()).isEqualTo(6d);
    }

    @Test
    void circumferenceOfRectWithHeight2AndWidth3() {
        assertThat(new DoubleRect(2d, 3d).circumference()).isEqualTo(10d);
    }

    // endregion

    // region circumCircle

    @Test
    void circumCircleOfRectWithHeightAndWidth1() {
        assertCircle(
            new DoubleRect(1d, 1d).circumCircle(),
            new DoublePoint(0.5, 0.5), Math.sqrt(2d) / 2d
        );
    }

    // endregion

    // region isSquare

    @Test
    void isSquareWithEqualHeightAndWidth() {
        assertThat(new DoubleRect(1d, 1d).isSquare()).isTrue();
    }

    @Test
    void isSquareWithUnequalHeightAndWidth() {
        assertThat(new DoubleRect(1d, 2d).isSquare()).isFalse();
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
        assertThat(rect.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithPositiveValues() {
        assertThat(new DoubleRect(1d, 1d).isValid()).isTrue();
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new DoubleRect(0d, 0d).isValid()).isFalse();
    }

    @Test
    void isValidWithZeroHeight() {
        assertThat(new DoubleRect(0d, 1d).isValid()).isFalse();
    }

    @Test
    void isValidWithZeroWidth() {
        assertThat(new DoubleRect(1d, 0d).isValid()).isFalse();
    }

    @Test
    void isValidWithNegativeValues() {
        assertThat(new DoubleRect(-1d, -1d).isValid()).isTrue();
    }

    @Test
    void isValidWithInfValues() {
        assertThat(new DoubleRect(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
            .isValid()).isFalse();
    }

    @Test
    void moveOfRectWithXYWithXY() {
        assertRect(
            new DoubleRect(1d, 2d).move(1d),
            new DoublePoint(1d), new DoublePoint(3d, 1d), 1d
        );
    }

    @Test
    void moveOfRectWithXYWithXAndY() {
        assertRect(
            new DoubleRect(1d, 2d).move(1d, 1d),
            new DoublePoint(1d), new DoublePoint(3d, 1d), 1d
        );
    }

    @Test
    void moveOfRectWithXYWithVector() {
        assertRect(
            new DoubleRect(1d, 2d).move(new DoubleVector(1d)),
            new DoublePoint(1d), new DoublePoint(3d, 1d), 1d
        );
    }

    @Test
    void rotateOfRectWidthHeight1AndWidth2WithoutCenterWithPiHalf() {
        assertRect(
            new DoubleRect(new DoublePoint(), new DoublePoint(0d, 1d), 2d)
                .rotate(Math.PI / 2d),
            new DoublePoint(), new DoublePoint(-1d, 6.123233995736766e-17d), 2d
        );
    }

    @Test
    void rotateOfRectWithHeight1AndWidth2WithCenterXY1WithPiHalf() {
        assertRect(
            new DoubleRect(new DoublePoint(), new DoublePoint(0d, 1d), 2d)
                .rotate(new DoublePoint(1d), Math.PI / 2d),
            new DoublePoint(2d, 0d), new DoublePoint(0.9999999999999999, 0d), 2d
        );
    }

    @Test
    void copyOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d);
        assertThat(rect.copy()).isEqualTo(rect);
    }

    // endregion

    // region override

    @Test
    void equalsOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d);
        assertThat(new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d)).isEqualTo(rect);
        assertThat(new DoubleRect(new DoublePoint(2d), new DoublePoint(4d), 3d)).isNotEqualTo(rect);
    }

    @Test
    void hashCodeOfRectWithPointHeightAndWidth() {
        assertThat(new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d).hashCode()).isEqualTo(1595867199);
    }

    @Test
    void toStringOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d);
        assertThat(rect).hasToString("2.0|2.0 3.0|3.0 4.0");
    }

    @Test
    void compareToOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(1d, 2d);
        assertThat(rect)
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
