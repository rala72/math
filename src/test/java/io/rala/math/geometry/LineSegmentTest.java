package io.rala.math.geometry;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.geometry.TestLineSegment;
import io.rala.math.testUtils.geometry.TestPoint;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.*;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class LineSegmentTest {
    // region constructors, getter and setter

    @Test
    void constructorWithBParameter() {
        assertLineSegment(
            new TestLineSegment(new TestPoint(1)),
            new TestPoint(1)
        );
    }

    @Test
    void constructorWithEqualABParameter() {
        assertLineSegment(
            new TestLineSegment(new TestPoint(2), new TestPoint(2)),
            new TestPoint(2), new TestPoint(2)
        );
    }

    @Test
    void constructorWithDifferentABParameter() {
        assertLineSegment(
            new TestLineSegment(
                new TestPoint(2, 2), new TestPoint(3, 3)
            ),
            new TestPoint(2), new TestPoint(3)
        );
    }

    @Test
    void createAndSetA() {
        LineSegment<Number> lineSegment = new TestLineSegment(new TestPoint());
        assertLineSegment(lineSegment, new TestPoint());
        lineSegment.setA(new TestPoint(1));
        assertLineSegment(
            lineSegment,
            new TestPoint(1), new TestPoint()
        );
    }

    @Test
    void createAndSetB() {
        LineSegment<Number> lineSegment = new TestLineSegment(new TestPoint());
        assertLineSegment(lineSegment, new TestPoint());
        lineSegment.setB(new TestPoint(2));
        assertLineSegment(
            lineSegment,
            new TestPoint(), new TestPoint(2)
        );
    }

    // endregion

    // region length, halvingPoint and distributionPoint

    @Test
    void lengthOfLineSegmentWithTwoEqualPoints() {
        assertEquals(0d,
            new TestLineSegment(new TestPoint(1, 2), new TestPoint(1, 2)).length()
        );
    }

    @Test
    void lengthOfLineSegmentWithInverseParameters() {
        assertEquals(
            Math.sqrt(2),
            new TestLineSegment(
                new TestPoint(1, 2),
                new TestPoint(2, 1)
            ).length()
        );
    }

    @Test
    void lengthOfLineSegmentWithTwoDifferenceEach() {
        assertEquals(
            2 * Math.sqrt(2),
            new TestLineSegment(
                new TestPoint(3, 4),
                new TestPoint(1, 2)
            ).length()
        );
    }

    @Test
    void halvingPointOfLineSegmentWithPXY0AndPXY1() {
        assertPoint(
            new TestLineSegment(new TestPoint(), new TestPoint(1)).halvingPoint(),
            0.5, 0.5
        );
    }

    @Test
    void distributionPointComma25OfLineSegmentWithPXY0AndPXY1() {
        assertPoint(
            new TestLineSegment(new TestPoint(), new TestPoint(1))
                .distributionPoint(0.25),
            0.25, 0.25
        );
    }

    @Test
    void distributionPointComma5OfLineSegmentWithPXY0AndPXY1() {
        assertPoint(
            new TestLineSegment(new TestPoint(), new TestPoint(1))
                .distributionPoint(0.5),
            0.5, 0.5
        );
    }

    @Test
    void distributionPointComma75OfLineSegmentWithPXY0AndPXY1() {
        assertPoint(
            new TestLineSegment(new TestPoint(), new TestPoint(1))
                .distributionPoint(0.75),
            0.75, 0.75
        );
    }

    // endregion

    // region flip, toLine

    @Test
    void flipWithAXY0AndBXY1() {
        assertLineSegment(
            new TestLineSegment(new TestPoint(0), new TestPoint(1)).flip(),
            new TestPoint(1), new TestPoint(0)
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY0AndBXY1() {
        assertLine(
            new TestLineSegment(new TestPoint(0), new TestPoint(1)).toLine(),
            1d, 0d
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX1Y0() {
        assertLine(
            new TestLineSegment(new TestPoint(1), new TestPoint(1, 0)).toLine(),
            null, 1
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX0Y1() {
        assertLine(
            new TestLineSegment(new TestPoint(1), new TestPoint(0, 1)).toLine(),
            -0d, 1d
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX2Y3() {
        assertLine(
            new TestLineSegment(new TestPoint(1), new TestPoint(2, 3)).toLine(),
            2d, -1d
        );
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfLineSegmentWithA0_5B1_5() {
        TestLineSegment lineSegment = new TestLineSegment(
            new TestPoint(0.5), new TestPoint(1.5)
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
        assertTrue(new TestLineSegment(new TestPoint()).isValid());
    }

    @Test
    void isValidWithInfValues() {
        assertFalse(
            new TestLineSegment(
                new TestPoint(Double.POSITIVE_INFINITY),
                new TestPoint(Double.POSITIVE_INFINITY)
            ).isValid()
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithXY() {
        assertLineSegment(
            new TestLineSegment(new TestPoint(), new TestPoint(1)).move(1),
            new TestPoint(1), new TestPoint(2)
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithXAndY() {
        assertLineSegment(
            new TestLineSegment(new TestPoint(), new TestPoint(1)).move(1, 1),
            new TestPoint(1), new TestPoint(2)
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithVector() {
        assertLineSegment(
            new TestLineSegment(new TestPoint(), new TestPoint(1))
                .move(new TestVector(1)),
            new TestPoint(1), new TestPoint(2)
        );
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithoutCenterWithPiHalf() {
        assertLineSegment(
            new TestLineSegment(new TestPoint(0, 0), new TestPoint(1, 2))
                .rotate(Math.PI / 2),
            new TestPoint(),
            new TestPoint(-2, 1.0000000000000002)
        );
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithCenterXY1WithPiHalf() {
        assertLineSegment(
            new TestLineSegment(new TestPoint(0, 0), new TestPoint(1, 2))
                .rotate(new TestPoint(1), Math.PI / 2),
            new TestPoint(2, 0),
            new TestPoint(0, 1)
        );
    }

    @Test
    void copyOfLineSegmentWithTwoPoints() {
        LineSegment<Number> lineSegment = new TestLineSegment(
            new TestPoint(1, 2), new TestPoint(3, 4)
        );
        assertEquals(lineSegment, lineSegment.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfLineSegmentWithTwoPoints() {
        LineSegment<Number> lineSegment = new TestLineSegment(
            new TestPoint(2), new TestPoint(3)
        );
        assertEquals(
            lineSegment,
            new TestLineSegment(new TestPoint(2), new TestPoint(3))
        );
        assertNotEquals(
            lineSegment,
            new TestLineSegment(new TestPoint(3), new TestPoint(2))
        );
    }

    @Test
    void hashCodeOfLineSegmentWithTwoPoints() {
        assertEquals(
            33793,
            new TestLineSegment(new TestPoint(2), new TestPoint(3)).hashCode()
        );
    }

    @Test
    void toStringOfLineSegmentWithTwoPoints() {
        LineSegment<Number> lineSegment = new TestLineSegment(
            new TestPoint(2d), new TestPoint(3d)
        );
        assertEquals("2.0|2.0 3.0|3.0", lineSegment.toString());
    }

    @Test
    void compareToOfLineSegmentWithTwoPoints() {
        LineSegment<Number> lineSegment = new TestLineSegment(
            new TestPoint(2), new TestPoint(3)
        );
        assertEquals(0,
            lineSegment.compareTo(new TestLineSegment(
                new TestPoint(2), new TestPoint(3)
            ))
        );
        assertEquals(-1,
            lineSegment.compareTo(new TestLineSegment(
                new TestPoint(3), new TestPoint(4)
            ))
        );
        assertEquals(1,
            lineSegment.compareTo(new TestLineSegment(
                new TestPoint(1), new TestPoint(1)
            ))
        );
    }

    @Test
    void serializable() {
        assertSerializable(
            new TestLineSegment(new TestPoint()),
            LineSegment.class
        );
    }

    // endregion
}
