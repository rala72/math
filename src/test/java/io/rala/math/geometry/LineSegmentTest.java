package io.rala.math.geometry;

import io.rala.math.testUtils.SerializableTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LineSegmentTest {
    // region constructors, getter and setter

    @Test
    void constructorWithBParameter() {
        assertLineSegment(new LineSegment(new Point(1)), new Point(1));
    }

    @Test
    void constructorWithEqualABParameter() {
        assertLineSegment(
            new LineSegment(new Point(2), new Point(2)),
            new Point(2), new Point(2)
        );
    }

    @Test
    void constructorWithDifferentABParameter() {
        assertLineSegment(
            new LineSegment(
                new Point(2, 2), new Point(3, 3)
            ),
            new Point(2), new Point(3)
        );
    }

    @Test
    void createAndSetA() {
        LineSegment lineSegment = new LineSegment(new Point());
        assertLineSegment(lineSegment, new Point());
        lineSegment.setA(new Point(1));
        assertLineSegment(lineSegment, new Point(1), new Point());
    }

    @Test
    void createAndSetB() {
        LineSegment lineSegment = new LineSegment(new Point());
        assertLineSegment(lineSegment, new Point());
        lineSegment.setB(new Point(2));
        assertLineSegment(lineSegment, new Point(), new Point(2));
    }

    // endregion

    // region length, halvingPoint and distributionPoint

    @Test
    void lengthOfLineSegmentWithTwoEqualPoints() {
        Assertions.assertEquals(0,
            new LineSegment(new Point(1, 2), new Point(1, 2)).length()
        );
    }

    @Test
    void lengthOfLineSegmentWithInverseParameters() {
        Assertions.assertEquals(Math.sqrt(2), new LineSegment(new Point(1, 2), new Point(2, 1)).length());
    }

    @Test
    void lengthOfLineSegmentWithTwoDifferenceEach() {
        Assertions.assertEquals(2 * Math.sqrt(2), new LineSegment(new Point(3, 4), new Point(1, 2)).length());
    }

    @Test
    void halvingPointOfLineSegmentWithPXY0AndPXY1() {
        Assertions.assertEquals(new Point(0.5, 0.5),
            new LineSegment(new Point(), new Point(1)).halvingPoint()
        );
    }

    @Test
    void distributionPointComma25OfLineSegmentWithPXY0AndPXY1() {
        Assertions.assertEquals(new Point(0.25, 0.25),
            new LineSegment(new Point(), new Point(1)).distributionPoint(0.25)
        );
    }

    @Test
    void distributionPointComma5OfLineSegmentWithPXY0AndPXY1() {
        Assertions.assertEquals(new Point(0.5, 0.5),
            new LineSegment(new Point(), new Point(1)).distributionPoint(0.5)
        );
    }

    @Test
    void distributionPointComma75OfLineSegmentWithPXY0AndPXY1() {
        Assertions.assertEquals(new Point(0.75, 0.75),
            new LineSegment(new Point(), new Point(1)).distributionPoint(0.75)
        );
    }

    // endregion

    // region flip, toLine

    @Test
    void flipWithAXY0AndBXY1() {
        assertLineSegment(
            new LineSegment(new Point(0), new Point(1)).flip(),
            new Point(1), new Point(0)
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY0AndBXY1() {
        Assertions.assertEquals(new Line(1, 0),
            new LineSegment(new Point(0), new Point(1)).toLine()
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX1Y0() {
        Assertions.assertEquals(new Line(Double.NaN, 1),
            new LineSegment(new Point(1), new Point(1, 0)).toLine()
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX0Y1() {
        Assertions.assertEquals(new Line(1, 0),
            new LineSegment(new Point(1), new Point(0, 1)).toLine()
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX2Y3() {
        Assertions.assertEquals(new Line(2.23606797749979, -1.2360679774997898),
            new LineSegment(new Point(1), new Point(2, 3)).toLine()
        );
    }

    // endregion

    // region isValid, move, rotate and copy

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new LineSegment(new Point()).isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new LineSegment(
                new Point(Double.POSITIVE_INFINITY),
                new Point(Double.POSITIVE_INFINITY)
            ).isValid()
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithXY() {
        Assertions.assertEquals(
            new LineSegment(new Point(1), new Point(2)),
            new LineSegment(new Point(), new Point(1)).move(1)
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithXAndY() {
        Assertions.assertEquals(
            new LineSegment(new Point(1), new Point(2)),
            new LineSegment(new Point(), new Point(1)).move(1, 1)
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithVector() {
        Assertions.assertEquals(
            new LineSegment(new Point(1), new Point(2)),
            new LineSegment(new Point(), new Point(1)).move(new Vector(1))
        );
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithoutCenterWithPiHalf() {
        assertLineSegment(
            new LineSegment(new Point(0, 0), new Point(1, 2))
                .rotate(Math.PI / 2),
            new Point(),
            new Point(-2, 1.0000000000000002)
        );
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithCenterXY1WithPiHalf() {
        assertLineSegment(
            new LineSegment(new Point(0, 0), new Point(1, 2))
                .rotate(new Point(1), Math.PI / 2),
            new Point(2, 0),
            new Point(0, 1)
        );
    }

    @Test
    void copyOfLineSegmentWithTwoPoints() {
        LineSegment lineSegment = new LineSegment(new Point(1, 2), new Point(3, 4));
        Assertions.assertEquals(lineSegment, lineSegment.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfLineSegmentWithTwoPoints() {
        LineSegment lineSegment = new LineSegment(new Point(2), new Point(3));
        Assertions.assertEquals(
            lineSegment,
            new LineSegment(new Point(2), new Point(3))
        );
        Assertions.assertNotEquals(
            lineSegment,
            new LineSegment(new Point(3), new Point(2))
        );
    }

    @Test
    void hashCodeOfLineSegmentWithTwoPoints() {
        Assertions.assertEquals(
            16808929,
            new LineSegment(new Point(2), new Point(3)).hashCode()
        );
    }

    @Test
    void toStringOfLineSegmentWithTwoPoints() {
        LineSegment lineSegment = new LineSegment(new Point(2), new Point(3));
        Assertions.assertEquals("2.0|2.0 3.0|3.0", lineSegment.toString());
    }

    @Test
    void compareToOfLineSegmentWithTwoPoints() {
        LineSegment lineSegment = new LineSegment(new Point(2), new Point(3));
        Assertions.assertEquals(
            0, lineSegment.compareTo(new LineSegment(new Point(2), new Point(3)))
        );
        Assertions.assertEquals(
            -1, lineSegment.compareTo(new LineSegment(new Point(3), new Point(4)))
        );
        Assertions.assertEquals(
            1, lineSegment.compareTo(new LineSegment(new Point(1), new Point(1)))
        );
    }

    @Test
    void serializable() {
        SerializableTestUtils.verify(new LineSegment(new Point()), LineSegment.class);
    }

    // endregion


    // region assert

    private static void assertLineSegment(LineSegment lineSegment, Point b) {
        assertLineSegment(lineSegment, new Point(), b);
    }

    private static void assertLineSegment(LineSegment lineSegment, Point a, Point b) {
        Assertions.assertEquals(a, lineSegment.getA(), "a is invalid");
        Assertions.assertEquals(b, lineSegment.getB(), "b is invalid");
    }

    // endregion
}
