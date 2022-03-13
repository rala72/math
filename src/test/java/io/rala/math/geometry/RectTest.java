package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestRect;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class RectTest {
    // region constructors, getter and setter

    @Test
    void constructorWithHeightAndWidthButWithoutPoints() {
        assertRect(new TestRect(1, 2), 1, 2);
    }

    @Test
    void constructorWithPoint1AndSize() {
        assertRect(
            new TestRect(new TestPoint(0), new TestPoint(1), 2),
            new TestPoint(), new TestPoint(1), 2
        );
    }

    @Test
    void createAndSetA() {
        Rect<Number> rect = new TestRect(0, 0);
        assertRect(rect, 0, 0);
        rect.setA(new TestPoint(1));
        assertRect(rect, new TestPoint(1), new TestPoint(0), 0);
    }

    @Test
    void createAndSetB() {
        Rect<Number> rect = new TestRect(0, 0);
        assertRect(rect, 0, 0);
        rect.setB(new TestPoint(2));
        assertRect(rect, new TestPoint(0), new TestPoint(2), 0);
    }

    @Test
    void createAndSetSize() {
        Rect<Number> rect = new TestRect(0, 0);
        assertRect(rect, 0, 0);
        rect.setSize(2);
        assertRect(rect, 2, 0);
    }

    // endregion

    // region vertexes

    @Test
    void vertexesOfRectWithHeight1AndWidth2() {
        Rect<Number> rect = new TestRect(1, 2);
        assertPoint(rect.vertexA(), 0, 0);
        assertPoint(rect.vertexB(), 2, 0);
        assertPoint(rect.vertexC(), 2, 1);
        assertPoint(rect.vertexD(), 0, 1);
    }

    @Test
    void vertexesOfRectWithPointsAndSize() {
        Rect<Number> rect = new TestRect(new TestPoint(1, 1), new TestPoint(0, 1), 2);
        assertPoint(rect.vertexA(), 1, 1);
        assertPoint(rect.vertexB(), 0, 1);
        assertPoint(rect.vertexC(), 0, 3);
        assertPoint(rect.vertexD(), 1, 3);
    }

    // endregion

    // region height, width and diagonale

    @Test
    void heightOfRectWithPointsAndPositiveSize() {
        assertThat(new TestRect(new TestPoint(), new TestPoint(1), 1).height()).isEqualTo(1);
    }

    @Test
    void heightOfRectWithPointsAndNegativeSize() {
        assertThat(new TestRect(new TestPoint(), new TestPoint(1), -1).height()).isEqualTo(1d);
    }

    @Test
    void heightOfRectWithHeight1AndWidth0() {
        assertThat(new TestRect(1, 0).height()).isEqualTo(0d);
    }

    @Test
    void widthOfRectWithPointsAndPositiveSize() {
        assertThat(new TestRect(new TestPoint(), new TestPoint(1), 1).width()).isEqualTo(Math.sqrt(2));
    }

    @Test
    void widthOfRectWithPointsAndNegativeSize() {
        assertThat(new TestRect(new TestPoint(), new TestPoint(1), -1).width()).isEqualTo(Math.sqrt(2));
    }

    @Test
    void widthOfRectWithHeight1AndWidth0() {
        assertThat(new TestRect(1, 0).width()).isEqualTo(1);
    }

    @Test
    void diagonaleOfRectWithHeightAndWidth1() {
        assertThat(new TestRect(1, 1).diagonale()).isEqualTo(Math.sqrt(2));
    }

    @Test
    void diagonaleOfRectWithHeight1AndWidth2() {
        assertThat(new TestRect(1, 2).diagonale()).isEqualTo(Math.sqrt(5));
    }

    @Test
    void diagonaleOfRectWithHeight2AndWidth3() {
        assertThat(new TestRect(2, 3).diagonale()).isEqualTo(Math.sqrt(13));
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfRectWithHeightAndWidth1() {
        assertThat(new TestRect(1, 1).area()).isEqualTo(1d);
    }

    @Test
    void areaOfRectWithHeight1AndWidth2() {
        assertThat(new TestRect(1, 2).area()).isEqualTo(2d);
    }

    @Test
    void areaOfRectWithHeight2AndWidth3() {
        assertThat(new TestRect(2, 3).area()).isEqualTo(6d);
    }

    @Test
    void circumferenceOfRectWithHeightAndWidth1() {
        assertThat(new TestRect(1, 1).circumference()).isEqualTo(4d);
    }

    @Test
    void circumferenceOfRectWithHeight1AndWidth2() {
        assertThat(new TestRect(1, 2).circumference()).isEqualTo(6d);
    }

    @Test
    void circumferenceOfRectWithHeight2AndWidth3() {
        assertThat(new TestRect(2, 3).circumference()).isEqualTo(10d);
    }

    // endregion

    // region circumCircle

    @Test
    void circumCircleOfRectWithHeightAndWidth1() {
        assertCircle(
            new TestRect(1, 1).circumCircle(),
            new TestPoint(0.5, 0.5), Math.sqrt(2) / 2
        );
    }

    @Test
    void circumCircleRadiusOfRectWithHeightAndWidth1() {
        assertThat(new TestRect(1, 1).circumCircleRadius()).isEqualTo(Math.sqrt(2) / 2);
    }

    @Test
    void circumCirclePointOfRectWithHeightAndWidth1() {
        assertPoint(
            new TestRect(1, 1).circumCirclePoint(),
            0.5, 0.5
        );
    }

    // endregion

    // region isSquare

    @Test
    void isSquareWithEqualHeightAndWidth() {
        assertThat(new TestRect(1, 1).isSquare()).isTrue();
    }

    @Test
    void isSquareWithUnequalHeightAndWidth() {
        assertThat(new TestRect(1, 2).isSquare()).isFalse();
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
        assertThat(rect.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithPositiveValues() {
        assertThat(new TestRect(1, 1).isValid()).isTrue();
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new TestRect(0d, 0d).isValid()).isFalse();
    }

    @Test
    void isValidWithZeroHeight() {
        assertThat(new TestRect(0d, 1d).isValid()).isFalse();
    }

    @Test
    void isValidWithZeroWidth() {
        assertThat(new TestRect(1d, 0d).isValid()).isFalse();
    }

    @Test
    void isValidWithNegativeValues() {
        assertThat(new TestRect(-1, -1).isValid()).isTrue();
    }

    @Test
    void isValidWithInfValues() {
        assertThat(new TestRect(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
            .isValid()).isFalse();
    }

    @Test
    void moveOfRectWithXYWithXY() {
        assertRect(
            new TestRect(1, 2).move(1),
            new TestPoint(1), new TestPoint(3, 1), 1
        );
    }

    @Test
    void moveOfRectWithXYWithXAndY() {
        assertRect(
            new TestRect(1, 2).move(1, 1),
            new TestPoint(1), new TestPoint(3, 1), 1
        );
    }

    @Test
    void moveOfRectWithXYWithVector() {
        assertRect(
            new TestRect(1, 2).move(new TestVector(1)),
            new TestPoint(1), new TestPoint(3, 1), 1
        );
    }

    @Test
    void rotateOfRectWidthHeight1AndWidth2WithoutCenterWithPiHalf() {
        assertRect(
            new TestRect(new TestPoint(), new TestPoint(0, 1), 2)
                .rotate(Math.PI / 2),
            new TestPoint(), new TestPoint(-1.0, 6.123233995736766E-17), 2
        );
    }

    @Test
    void rotateOfRectWithHeight1AndWidth2WithCenterXY1WithPiHalf() {
        assertRect(
            new TestRect(new TestPoint(), new TestPoint(0, 1), 2)
                .rotate(new TestPoint(1), Math.PI / 2),
            new TestPoint(2, 0), new TestPoint(0.9999999999999999, 0), 2
        );
    }

    @Test
    void copyOfRectWithPointHeightAndWidth() {
        Rect<Number> rect = new TestRect(new TestPoint(2), new TestPoint(3), 4);
        assertThat(rect.copy()).isEqualTo(rect);
    }

    // endregion

    // region override

    @Test
    void equalsOfRectWithPointHeightAndWidth() {
        assertThat(new TestRect(new TestPoint(2), new TestPoint(3), 4))
            .isEqualTo(new TestRect(new TestPoint(2), new TestPoint(3), 4))
            .isNotEqualTo(new TestRect(new TestPoint(2), new TestPoint(4), 3));
    }

    @Test
    void hashCodeOfRectWithPointHeightAndWidth() {
        assertThat(new TestRect(new TestPoint(2), new TestPoint(3), 4).hashCode()).isEqualTo(1047587);
    }

    @Test
    void toStringOfRectWithPointHeightAndWidth() {
        Rect<Number> rect = new TestRect(new TestPoint(2d), new TestPoint(3d), 4d);
        assertThat(rect).hasToString("2.0|2.0 3.0|3.0 4.0");
    }

    @Test
    void compareToOfRectWithPointHeightAndWidth() {
        Rect<Number> rect = new TestRect(1, 2);
        assertThat(rect)
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
