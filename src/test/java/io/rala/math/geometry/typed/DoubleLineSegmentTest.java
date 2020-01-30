package io.rala.math.geometry.typed;

import io.rala.math.geometry.LineSegment;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DoubleLineSegmentTest {
    // region constructors, getter and setter

    @Test
    void constructorWithBParameter() {
        GeometryAssertions.assertLineSegment(
            new DoubleLineSegment(new DoublePoint(1d)),
            new DoublePoint(1d)
        );
    }

    @Test
    void constructorWithEqualABParameter() {
        GeometryAssertions.assertLineSegment(
            new DoubleLineSegment(new DoublePoint(2d), new DoublePoint(2d)),
            new DoublePoint(2d), new DoublePoint(2d)
        );
    }

    @Test
    void constructorWithDifferentABParameter() {
        GeometryAssertions.assertLineSegment(
            new DoubleLineSegment(
                new DoublePoint(2d, 2d), new DoublePoint(3d, 3d)
            ),
            new DoublePoint(2d), new DoublePoint(3d)
        );
    }

    @Test
    void createAndSetA() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(new DoublePoint());
        GeometryAssertions.assertLineSegment(lineSegment, new DoublePoint());
        lineSegment.setA(new DoublePoint(1d));
        GeometryAssertions.assertLineSegment(
            lineSegment,
            new DoublePoint(1d), new DoublePoint()
        );
    }

    @Test
    void createAndSetB() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(new DoublePoint());
        GeometryAssertions.assertLineSegment(lineSegment, new DoublePoint());
        lineSegment.setB(new DoublePoint(2d));
        GeometryAssertions.assertLineSegment(
            lineSegment,
            new DoublePoint(), new DoublePoint(2d)
        );
    }

    // endregion

    // region length, halvingPoint and distributionPoint

    @Test
    void lengthOfLineSegmentWithTwoEqualPoints() {
        Assertions.assertEquals(0d,
            new DoubleLineSegment(new DoublePoint(1d, 2d), new DoublePoint(1d, 2d)).length()
        );
    }

    @Test
    void lengthOfLineSegmentWithInverseParameters() {
        Assertions.assertEquals(
            Math.sqrt(2d),
            new DoubleLineSegment(
                new DoublePoint(1d, 2d),
                new DoublePoint(2d, 1d)
            ).length()
        );
    }

    @Test
    void lengthOfLineSegmentWithTwoDifferenceEach() {
        Assertions.assertEquals(
            2d * Math.sqrt(2d),
            new DoubleLineSegment(
                new DoublePoint(3d, 4d),
                new DoublePoint(1d, 2d)
            ).length()
        );
    }

    @Test
    void halvingPointOfLineSegmentWithPXY0AndPXY1() {
        GeometryAssertions.assertPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d)).halvingPoint(),
            0.5d, 0.5d
        );
    }

    @Test
    void distributionPointComma25OfLineSegmentWithPXY0AndPXY1() {
        GeometryAssertions.assertPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .distributionPoint(0.25d),
            0.25d, 0.25d
        );
    }

    @Test
    void distributionPointComma5OfLineSegmentWithPXY0AndPXY1() {
        GeometryAssertions.assertPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .distributionPoint(0.5d),
            0.5d, 0.5d
        );
    }

    @Test
    void distributionPointComma75OfLineSegmentWithPXY0AndPXY1() {
        GeometryAssertions.assertPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .distributionPoint(0.75d),
            0.75d, 0.75d
        );
    }

    // endregion

    // region flip, toLine

    @Test
    void flipWithAXY0AndBXY1() {
        GeometryAssertions.assertLineSegment(
            new DoubleLineSegment(new DoublePoint(0d), new DoublePoint(1d)).flip(),
            new DoublePoint(1d), new DoublePoint(0d)
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY0AndBXY1() {
        GeometryAssertions.assertLine(
            new DoubleLineSegment(new DoublePoint(0d), new DoublePoint(1d)).toLine(),
            1d, 0d
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX1Y0() {
        GeometryAssertions.assertLine(
            new DoubleLineSegment(new DoublePoint(1d), new DoublePoint(1d, 0d)).toLine(),
            null, 1d
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX0Y1() {
        GeometryAssertions.assertLine(
            new DoubleLineSegment(new DoublePoint(1d), new DoublePoint(0d, 1d)).toLine(),
            -0d, 1d
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX2Y3() {
        GeometryAssertions.assertLine(
            new DoubleLineSegment(new DoublePoint(1d), new DoublePoint(2d, 3d)).toLine(),
            2d, -1d
        );
    }

    // endregion

    // region isValid, move, rotate and copy

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new DoubleLineSegment(new DoublePoint()).isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new DoubleLineSegment(
                new DoublePoint(Double.POSITIVE_INFINITY),
                new DoublePoint(Double.POSITIVE_INFINITY)
            ).isValid()
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithXY() {
        GeometryAssertions.assertLineSegment(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d)).move(1d),
            new DoublePoint(1d), new DoublePoint(2d)
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithXAndY() {
        GeometryAssertions.assertLineSegment(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d)).move(1d, 1d),
            new DoublePoint(1d), new DoublePoint(2d)
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithVector() {
        GeometryAssertions.assertLineSegment(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .move(new DoubleVector(1d)),
            new DoublePoint(1d), new DoublePoint(2d)
        );
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithoutCenterWithPiHalf() {
        GeometryAssertions.assertLineSegment(
            new DoubleLineSegment(new DoublePoint(0d, 0d), new DoublePoint(1d, 2d))
                .rotate(Math.PI / 2d),
            new DoublePoint(),
            new DoublePoint(-2d, 1.0000000000000002d)
        );
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithCenterXY1WithPiHalf() {
        GeometryAssertions.assertLineSegment(
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
        Assertions.assertEquals(lineSegment, lineSegment.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfLineSegmentWithTwoPoints() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(
            new DoublePoint(2d), new DoublePoint(3d)
        );
        Assertions.assertEquals(
            lineSegment,
            new DoubleLineSegment(new DoublePoint(2d), new DoublePoint(3d))
        );
        Assertions.assertNotEquals(
            lineSegment,
            new DoubleLineSegment(new DoublePoint(3d), new DoublePoint(2d))
        );
    }

    @Test
    void hashCodeOfLineSegmentWithTwoPoints() {
        Assertions.assertEquals(
            16808929,
            new DoubleLineSegment(new DoublePoint(2d), new DoublePoint(3d)).hashCode()
        );
    }

    @Test
    void toStringOfLineSegmentWithTwoPoints() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(
            new DoublePoint(2d), new DoublePoint(3d)
        );
        Assertions.assertEquals("2.0|2.0 3.0|3.0", lineSegment.toString());
    }

    @Test
    void compareToOfLineSegmentWithTwoPoints() {
        LineSegment<Double> lineSegment = new DoubleLineSegment(
            new DoublePoint(2d), new DoublePoint(3d)
        );
        Assertions.assertEquals(0d,
            lineSegment.compareTo(new DoubleLineSegment(
                new DoublePoint(2d), new DoublePoint(3d)
            ))
        );
        Assertions.assertEquals(-1d,
            lineSegment.compareTo(new DoubleLineSegment(
                new DoublePoint(3d), new DoublePoint(4d)
            ))
        );
        Assertions.assertEquals(1d,
            lineSegment.compareTo(new DoubleLineSegment(
                new DoublePoint(1d), new DoublePoint(1d)
            ))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
            new DoubleLineSegment(new DoublePoint()),
            LineSegment.class
        );
    }

    // endregion
}
