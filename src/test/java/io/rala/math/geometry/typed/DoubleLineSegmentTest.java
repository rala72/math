package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class DoubleLineSegmentTest {
    // region constructors, getter and setter

    @Test
    void constructorWithBParameter() {
        assertLineSegment(
            new DoubleLineSegment(new DoublePoint(1d)),
            new DoublePoint(1d)
        );
    }

    @Test
    void constructorWithEqualABParameter() {
        assertLineSegment(
            new DoubleLineSegment(new DoublePoint(2d), new DoublePoint(2d)),
            new DoublePoint(2d), new DoublePoint(2d)
        );
    }

    @Test
    void constructorWithDifferentABParameter() {
        assertLineSegment(
            new DoubleLineSegment(
                new DoublePoint(2d, 2d), new DoublePoint(3d, 3d)
            ),
            new DoublePoint(2d), new DoublePoint(3d)
        );
    }

    @Test
    void createAndSetA() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(new DoublePoint());
        assertLineSegment(lineSegment, new DoublePoint());
        lineSegment.setA(new DoublePoint(1d));
        assertLineSegment(
            lineSegment,
            new DoublePoint(1d), new DoublePoint()
        );
    }

    @Test
    void createAndSetB() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(new DoublePoint());
        assertLineSegment(lineSegment, new DoublePoint());
        lineSegment.setB(new DoublePoint(2d));
        assertLineSegment(
            lineSegment,
            new DoublePoint(), new DoublePoint(2d)
        );
    }

    // endregion

    // region length, halvingPoint and distributionPoint

    @Test
    void lengthOfLineSegmentWithTwoEqualPoints() {
        assertEquals(0d,
            new DoubleLineSegment(
                new DoublePoint(1d, 2d),
                new DoublePoint(1d, 2d)
            ).length()
        );
    }

    @Test
    void lengthOfLineSegmentWithInverseParameters() {
        assertEquals(
            Math.sqrt(2d),
            new DoubleLineSegment(
                new DoublePoint(1d, 2d),
                new DoublePoint(2d, 1d)
            ).length()
        );
    }

    @Test
    void lengthOfLineSegmentWithTwoDifferenceEach() {
        assertEquals(
            2d * Math.sqrt(2d),
            new DoubleLineSegment(
                new DoublePoint(3d, 4d),
                new DoublePoint(1d, 2d)
            ).length()
        );
    }

    @Test
    void halvingPointOfLineSegmentWithPXY0AndPXY1() {
        assertPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d)).halvingPoint(),
            0.5d, 0.5d
        );
    }

    @Test
    void distributionPointComma25OfLineSegmentWithPXY0AndPXY1() {
        assertPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .distributionPoint(0.25d),
            0.25d, 0.25d
        );
    }

    @Test
    void distributionPointComma5OfLineSegmentWithPXY0AndPXY1() {
        assertPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .distributionPoint(0.5d),
            0.5d, 0.5d
        );
    }

    @Test
    void distributionPointComma75OfLineSegmentWithPXY0AndPXY1() {
        assertPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .distributionPoint(0.75d),
            0.75d, 0.75d
        );
    }

    // endregion

    // region flip, toLine

    @Test
    void flipWithAXY0AndBXY1() {
        assertLineSegment(
            new DoubleLineSegment(new DoublePoint(0d), new DoublePoint(1d)).flip(),
            new DoublePoint(1d), new DoublePoint(0d)
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY0AndBXY1() {
        assertLine(
            new DoubleLineSegment(new DoublePoint(0d), new DoublePoint(1d)).toLine(),
            1d, 0d
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX1Y0() {
        assertLine(
            new DoubleLineSegment(new DoublePoint(1d), new DoublePoint(1d, 0d)).toLine(),
            null, 1d
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX0Y1() {
        assertLine(
            new DoubleLineSegment(new DoublePoint(1d), new DoublePoint(0d, 1d)).toLine(),
            -0d, 1d
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX2Y3() {
        assertLine(
            new DoubleLineSegment(new DoublePoint(1d), new DoublePoint(2d, 3d)).toLine(),
            2d, -1d
        );
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfLineSegmentWithA0_5B1_5() {
        DoubleLineSegment lineSegment = new DoubleLineSegment(
            new DoublePoint(0.5), new DoublePoint(1.5)
        );
        IntegerArithmetic integerArithmetic = new IntegerArithmetic();
        LineSegment<Integer> result = new LineSegment<>(integerArithmetic,
            new Point<>(integerArithmetic, 0),
            new Point<>(integerArithmetic, 1)
        );
        assertEquals(result,
            lineSegment.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZeroValues() {
        assertTrue(new DoubleLineSegment(new DoublePoint()).isValid());
    }

    @Test
    void isValidWithInfValues() {
        assertFalse(
            new DoubleLineSegment(
                new DoublePoint(Double.POSITIVE_INFINITY),
                new DoublePoint(Double.POSITIVE_INFINITY)
            ).isValid()
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithXY() {
        assertLineSegment(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d)).move(1d),
            new DoublePoint(1d), new DoublePoint(2d)
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithXAndY() {
        assertLineSegment(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d)).move(1d, 1d),
            new DoublePoint(1d), new DoublePoint(2d)
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithVector() {
        assertLineSegment(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .move(new DoubleVector(1d)),
            new DoublePoint(1d), new DoublePoint(2d)
        );
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithoutCenterWithPiHalf() {
        assertLineSegment(
            new DoubleLineSegment(new DoublePoint(0d, 0d), new DoublePoint(1d, 2d))
                .rotate(Math.PI / 2d),
            new DoublePoint(),
            new DoublePoint(-2d, 1.0000000000000002d)
        );
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithCenterXY1WithPiHalf() {
        assertLineSegment(
            new DoubleLineSegment(new DoublePoint(0d, 0d), new DoublePoint(1d, 2d))
                .rotate(new DoublePoint(1d), Math.PI / 2d),
            new DoublePoint(2d, 0d),
            new DoublePoint(0d, 1d)
        );
    }

    @Test
    void copyOfLineSegmentWithTwoPoints() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(
            new DoublePoint(1d, 2d), new DoublePoint(3d, 4d)
        );
        assertEquals(lineSegment, lineSegment.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfLineSegmentWithTwoPoints() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(
            new DoublePoint(2d), new DoublePoint(3d)
        );
        assertEquals(
            lineSegment,
            new DoubleLineSegment(new DoublePoint(2d), new DoublePoint(3d))
        );
        assertNotEquals(
            lineSegment,
            new DoubleLineSegment(new DoublePoint(3d), new DoublePoint(2d))
        );
    }

    @Test
    void hashCodeOfLineSegmentWithTwoPoints() {
        assertEquals(
            16808929,
            new DoubleLineSegment(new DoublePoint(2d), new DoublePoint(3d)).hashCode()
        );
    }

    @Test
    void toStringOfLineSegmentWithTwoPoints() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(
            new DoublePoint(2d), new DoublePoint(3d)
        );
        assertEquals("2.0|2.0 3.0|3.0", lineSegment.toString());
    }

    @Test
    void compareToOfLineSegmentWithTwoPoints() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(
            new DoublePoint(2d), new DoublePoint(3d)
        );
        assertEquals(0d,
            lineSegment.compareTo(new DoubleLineSegment(
                new DoublePoint(2d), new DoublePoint(3d)
            ))
        );
        assertEquals(-1d,
            lineSegment.compareTo(new DoubleLineSegment(
                new DoublePoint(3d), new DoublePoint(4d)
            ))
        );
        assertEquals(1d,
            lineSegment.compareTo(new DoubleLineSegment(
                new DoublePoint(1d), new DoublePoint(1d)
            ))
        );
    }

    @Test
    void serializable() {
        assertSerializable(
            new DoubleLineSegment(new DoublePoint()),
            LineSegment.class
        );
    }

    // endregion
}
