package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Rect;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(1d,
            new DoubleRect(new DoublePoint(), new DoublePoint(1d), 1d).height()
        );
    }

    @Test
    void heightOfRectWithPointsAndNegativeSize() {
        assertEquals(1d,
            new DoubleRect(new DoublePoint(), new DoublePoint(1d), -1d).height()
        );
    }

    @Test
    void heightOfRectWithHeight1AndWidth0() {
        assertEquals(0d, new DoubleRect(1d, 0d).height());
    }

    @Test
    void widthOfRectWithPointsAndPositiveSize() {
        assertEquals(Math.sqrt(2d),
            new DoubleRect(new DoublePoint(), new DoublePoint(1d), 1d).width()
        );
    }

    @Test
    void widthOfRectWithPointsAndNegativeSize() {
        assertEquals(Math.sqrt(2d),
            new DoubleRect(new DoublePoint(), new DoublePoint(1d), -1d).width()
        );
    }

    @Test
    void widthOfRectWithHeight1AndWidth0() {
        assertEquals(1d, new DoubleRect(1d, 0d).width());
    }

    @Test
    void diagonaleOfRectWithHeightAndWidth1() {
        assertEquals(Math.sqrt(2d), new DoubleRect(1d, 1d).diagonale());
    }

    @Test
    void diagonaleOfRectWithHeight1AndWidth2() {
        assertEquals(Math.sqrt(5d), new DoubleRect(1d, 2d).diagonale());
    }

    @Test
    void diagonaleOfRectWithHeight2AndWidth3() {
        assertEquals(Math.sqrt(13d), new DoubleRect(2d, 3d).diagonale());
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfRectWithHeightAndWidth1() {
        assertEquals(1d, new DoubleRect(1d, 1d).area());
    }

    @Test
    void areaOfRectWithHeight1AndWidth2() {
        assertEquals(2d, new DoubleRect(1d, 2d).area());
    }

    @Test
    void areaOfRectWithHeight2AndWidth3() {
        assertEquals(6d, new DoubleRect(2d, 3d).area());
    }

    @Test
    void circumferenceOfRectWithHeightAndWidth1() {
        assertEquals(4d, new DoubleRect(1d, 1d).circumference());
    }

    @Test
    void circumferenceOfRectWithHeight1AndWidth2() {
        assertEquals(6d, new DoubleRect(1d, 2d).circumference());
    }

    @Test
    void circumferenceOfRectWithHeight2AndWidth3() {
        assertEquals(10d, new DoubleRect(2d, 3d).circumference());
    }

    // endregion

    // region circumCircle

    @Test
    void circumCircleOfRectWithHeightAndWidth1() {
        assertCircle(
            new DoubleRect(1d, 1d).circumCircle(),
            new DoublePoint(0.5d, 0.5d), Math.sqrt(2d) / 2d
        );
    }

    // endregion

    // region isSquare

    @Test
    void isSquareWithEqualHeightAndWidth() {
        assertTrue(new DoubleRect(1d, 1d).isSquare());
    }

    @Test
    void isSquareWithUnequalHeightAndWidth() {
        assertFalse(new DoubleRect(1d, 2d).isSquare());
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
        assertEquals(result,
            rect.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithPositiveValues() {
        assertTrue(new DoubleRect(1d, 1d).isValid());
    }

    @Test
    void isValidWithZeroValues() {
        assertFalse(new DoubleRect(0d, 0d).isValid());
    }

    @Test
    void isValidWithZeroHeight() {
        assertFalse(new DoubleRect(0d, 1d).isValid());
    }

    @Test
    void isValidWithZeroWidth() {
        assertFalse(new DoubleRect(1d, 0d).isValid());
    }

    @Test
    void isValidWithNegativeValues() {
        assertTrue(new DoubleRect(-1d, -1d).isValid());
    }

    @Test
    void isValidWithInfValues() {
        assertFalse(
            new DoubleRect(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
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
            new DoublePoint(2d, 0d), new DoublePoint(0.9999999999999999d, 0d), 2d
        );
    }

    @Test
    void copyOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d);
        assertEquals(rect, rect.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d);
        assertEquals(rect,
            new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d)
        );
        assertNotEquals(rect,
            new DoubleRect(new DoublePoint(2d), new DoublePoint(4d), 3d)
        );
    }

    @Test
    void hashCodeOfRectWithPointHeightAndWidth() {
        assertEquals(1595867199,
            new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d).hashCode()
        );
    }

    @Test
    void toStringOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(new DoublePoint(2d), new DoublePoint(3d), 4d);
        assertEquals("2.0|2.0 3.0|3.0 4.0", rect.toString());
    }

    @Test
    void compareToOfRectWithPointHeightAndWidth() {
        Rect<Double> rect = new DoubleRect(1d, 2d);
        assertEquals(0d, rect.compareTo(new DoubleRect(1d, 2d)));
        assertEquals(-1d, rect.compareTo(new DoubleRect(2d, 3d)));
        assertEquals(1d, rect.compareTo(new DoubleRect(1d, 1d)));
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleRect(0d, 0d), DoubleRect.class);
    }

    // endregion
}
