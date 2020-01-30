package io.rala.math.geometry.typed;

import io.rala.math.geometry.LineSegment;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class BigDecimalLineSegmentTest {
    // region constructors, getter and setter

    @Test
    void constructorWithBParameter() {
        GeometryAssertions.assertLineSegment(
            new BigDecimalLineSegment(new BigDecimalPoint(BigDecimal.ONE)),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void constructorWithEqualABParameter() {
        GeometryAssertions.assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(2d))
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void constructorWithDifferentABParameter() {
        GeometryAssertions.assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(3d), BigDecimal.valueOf(3d))
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d))
        );
    }

    @Test
    void createAndSetA() {
        LineSegment<BigDecimal> lineSegment =
            new BigDecimalLineSegment(new BigDecimalPoint());
        GeometryAssertions.assertLineSegment(lineSegment, new BigDecimalPoint());
        lineSegment.setA(new BigDecimalPoint(BigDecimal.ONE));
        GeometryAssertions.assertLineSegment(
            lineSegment,
            new BigDecimalPoint(BigDecimal.ONE), new BigDecimalPoint()
        );
    }

    @Test
    void createAndSetB() {
        LineSegment<BigDecimal> lineSegment =
            new BigDecimalLineSegment(new BigDecimalPoint());
        GeometryAssertions.assertLineSegment(lineSegment, new BigDecimalPoint());
        lineSegment.setB(new BigDecimalPoint(BigDecimal.valueOf(2d)));
        GeometryAssertions.assertLineSegment(
            lineSegment,
            new BigDecimalPoint(), new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    // endregion

    // region length, halvingPoint and distributionPoint

    @Test
    void lengthOfLineSegmentWithTwoEqualPoints() {
        Assertions.assertEquals(BigDecimal.ZERO,
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
            ).length()
        );
    }

    @Test
    void lengthOfLineSegmentWithInverseParameters() {
        Assertions.assertEquals(
            BigDecimal.valueOf(Math.sqrt(2d)).round(GeometryAssertions.CONTEXT),
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.ONE)
            ).length()
        );
    }

    @Test
    void lengthOfLineSegmentWithTwoDifferenceEach() {
        Assertions.assertEquals(
            BigDecimal.valueOf(2d * Math.sqrt(2d)).round(GeometryAssertions.CONTEXT),
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(3d), BigDecimal.valueOf(4d)),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
            ).length()
        );
    }

    @Test
    void halvingPointOfLineSegmentWithPXY0AndPXY1() {
        GeometryAssertions.assertPoint(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).halvingPoint(),
            BigDecimal.valueOf(0.5d), BigDecimal.valueOf(0.5d)
        );
    }

    @Test
    void distributionPointComma25OfLineSegmentWithPXY0AndPXY1() {
        GeometryAssertions.assertPoint(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            )
                .distributionPoint(BigDecimal.valueOf(0.25d)),
            BigDecimal.valueOf(0.25d), BigDecimal.valueOf(0.25d)
        );
    }

    @Test
    void distributionPointComma5OfLineSegmentWithPXY0AndPXY1() {
        GeometryAssertions.assertPoint(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).distributionPoint(BigDecimal.valueOf(0.5d)),
            BigDecimal.valueOf(0.5d), BigDecimal.valueOf(0.5d)
        );
    }

    @Test
    void distributionPointComma75OfLineSegmentWithPXY0AndPXY1() {
        GeometryAssertions.assertPoint(
            new BigDecimalLineSegment(
                new BigDecimalPoint(), new BigDecimalPoint(BigDecimal.ONE)
            ).distributionPoint(BigDecimal.valueOf(0.75d)),
            BigDecimal.valueOf(0.75d), BigDecimal.valueOf(0.75d)
        );
    }

    // endregion

    // region flip, toLine

    @Test
    void flipWithAXY0AndBXY1() {
        GeometryAssertions.assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ).flip(),
            new BigDecimalPoint(BigDecimal.ONE), new BigDecimalPoint(BigDecimal.ZERO)
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY0AndBXY1() {
        GeometryAssertions.assertLine(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE)
            ).toLine(),
            BigDecimal.ONE, BigDecimal.ZERO
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX1Y0() {
        GeometryAssertions.assertLine(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.ZERO)
            ).toLine(),
            null, BigDecimal.ONE
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX0Y1() {
        GeometryAssertions.assertLine(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE)
            ).toLine(),
            BigDecimal.ZERO, BigDecimal.ONE
        );
    }

    @Test
    void toLineOfLineSegmentWithAXY1AndBX2Y3() {
        GeometryAssertions.assertLine(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d))
            ).toLine(),
            BigDecimal.valueOf(2), BigDecimal.ONE.negate()
        );
    }

    // endregion

    // region isValid, move, rotate and copy

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(
            new BigDecimalLineSegment(new BigDecimalPoint()).isValid()
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithXY() {
        GeometryAssertions.assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithXAndY() {
        GeometryAssertions.assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(),
                new BigDecimalPoint(BigDecimal.ONE)
            ).move(BigDecimal.ONE, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void moveOfLineSegmentWithAndBWithVector() {
        GeometryAssertions.assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(), new BigDecimalPoint(BigDecimal.ONE)
            ).move(new BigDecimalVector(BigDecimal.ONE)),
            new BigDecimalPoint(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithoutCenterWithPiHalf() {
        GeometryAssertions.assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
            ).rotate(BigDecimal.valueOf(Math.PI / 2d)),
            new BigDecimalPoint(),
            new BigDecimalPoint(
                BigDecimal.valueOf(-2d),
                BigDecimal.valueOf(1.0000000000000002d)
            )
        );
    }

    @Test
    void rotateOfLineSegmentWithAXY0AndBX1Y2WithCenterXY1WithPiHalf() {
        GeometryAssertions.assertLineSegment(
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ZERO),
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
            ).rotate(
                new BigDecimalPoint(BigDecimal.ONE),
                BigDecimal.valueOf(Math.PI / 2d)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d), BigDecimal.ZERO),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE)
        );
    }

    @Test
    void copyOfLineSegmentWithTwoPoints() {
        LineSegment<BigDecimal> lineSegment = new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d), BigDecimal.valueOf(4d))
        );
        Assertions.assertEquals(lineSegment, lineSegment.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfLineSegmentWithTwoPoints() {
        LineSegment<BigDecimal> lineSegment = new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d))
        );
        Assertions.assertEquals(
            lineSegment,
            new BigDecimalLineSegment(new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(3d)))
        );
        Assertions.assertNotEquals(
            lineSegment,
            new BigDecimalLineSegment(new BigDecimalPoint(BigDecimal.valueOf(3d)),
                new BigDecimalPoint(BigDecimal.valueOf(2d)))
        );
    }

    @Test
    void hashCodeOfLineSegmentWithTwoPoints() {
        Assertions.assertEquals(
            677537,
            new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(3d))
            ).hashCode()
        );
    }

    @Test
    void toStringOfLineSegmentWithTwoPoints() {
        LineSegment<BigDecimal> lineSegment = new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d))
        );
        Assertions.assertEquals("2.0|2.0 3.0|3.0", lineSegment.toString());
    }

    @Test
    void compareToOfLineSegmentWithTwoPoints() {
        LineSegment<BigDecimal> lineSegment = new BigDecimalLineSegment(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            new BigDecimalPoint(BigDecimal.valueOf(3d))
        );
        Assertions.assertEquals(0,
            lineSegment.compareTo(new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.valueOf(3d))
            ))
        );
        Assertions.assertEquals(-1,
            lineSegment.compareTo(new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.valueOf(3d)),
                new BigDecimalPoint(BigDecimal.valueOf(4d))
            ))
        );
        Assertions.assertEquals(1,
            lineSegment.compareTo(new BigDecimalLineSegment(
                new BigDecimalPoint(BigDecimal.ONE),
                new BigDecimalPoint(BigDecimal.ONE)
            ))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
            new BigDecimalLineSegment(new BigDecimalPoint()),
            LineSegment.class
        );
    }

    // endregion
}
