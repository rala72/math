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
    void halvingPointOfLineSegmentWithPXY0dAndPXY1d() {
        GeometryAssertions.assertPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d)).halvingPoint(),
            0.5d, 0.5d
        );
    }

    @Test
    void distributionPointComma25dOfLineSegmentWithPXY0dAndPXY1d() {
        GeometryAssertions.assertPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .distributionPoint(0.25d),
            0.25d, 0.25d
        );
    }

    @Test
    void distributionPointComma5dOfLineSegmentWithPXY0dAndPXY1d() {
        GeometryAssertions.assertPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .distributionPoint(0.5d),
            0.5d, 0.5d
        );
    }

    @Test
    void distributionPointComma75dOfLineSegmentWithPXY0dAndPXY1d() {
        GeometryAssertions.assertPoint(
            new DoubleLineSegment(new DoublePoint(), new DoublePoint(1d))
                .distributionPoint(0.75d),
            0.75d, 0.75d
        );
    }

    // endregion

    // region flip, toLine

    @Test
    void flipWithAXY0dAndBXY1d() {
        GeometryAssertions.assertLineSegment(
            new DoubleLineSegment(new DoublePoint(0d), new DoublePoint(1d)).flip(),
            new DoublePoint(1d), new DoublePoint(0d)
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY0dAndBXY1d() {
        GeometryAssertions.assertLine(
            new DoubleLineSegment(new DoublePoint(0d), new DoublePoint(1d)).toLine(),
            1d, 0d
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1dAndBX1dY0d() {
        GeometryAssertions.assertLine(
            new DoubleLineSegment(new DoublePoint(1d), new DoublePoint(1d, 0d)).toLine(),
            null, 1d
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1dAndBX0dY1d() {
        GeometryAssertions.assertLine(
            new DoubleLineSegment(new DoublePoint(1d), new DoublePoint(0d, 1d)).toLine(),
            -0d, 1d
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1dAndBX2dY3d() {
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
    void rotateOfLineSegmentWithAXY0dAndBX1dY2dWithoutCenterWithPiHalf() {
        GeometryAssertions.assertLineSegment(
            new DoubleLineSegment(new DoublePoint(0d, 0d), new DoublePoint(1d, 2d))
                .rotate(Math.PI / 2d),
            new DoublePoint(),
            new DoublePoint(-2d, 1.0000000000000002d)
        );
    }

    @Test
    void rotateOfLineSegmentWithAXY0dAndBX1dY2dWithCenterXY1dWithPiHalf() {
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
