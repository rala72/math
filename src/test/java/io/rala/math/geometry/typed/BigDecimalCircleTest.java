package io.rala.math.geometry.typed;

import io.rala.math.geometry.Circle;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

class BigDecimalCircleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        GeometryAssertions.assertCircle(new BigDecimalCircle());
    }

    @Test
    void constructorWithMathContext5() {
        GeometryAssertions.assertCircle(new BigDecimalCircle(new MathContext(5)));
    }

    @Test
    void constructorWithCenterButWithoutRadius() {
        GeometryAssertions.assertCircle(
            new BigDecimalCircle(new BigDecimalPoint(BigDecimal.ONE)),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void constructorWithCenterAndMathContext5ButWithoutRadius() {
        GeometryAssertions.assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.ONE),
                new MathContext(5)
            ),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void constructorWithRadiusButWithoutCenter() {
        GeometryAssertions.assertCircle(
            new BigDecimalCircle(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithRadiusAndMathContext5ButWithoutCenter() {
        GeometryAssertions.assertCircle(
            new BigDecimalCircle(
                BigDecimal.valueOf(2d),
                new MathContext(5)
            ),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithCenterAndPoint() {
        GeometryAssertions.assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.ONE)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(Math.sqrt(2d))
        );
    }

    @Test
    void constructorWithCenterAndPointAndMathContext5() {
        GeometryAssertions.assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.ONE),
                new MathContext(5)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(Math.sqrt(2d))
        );
    }

    @Test
    void constructorWithCenterAndRadius() {
        GeometryAssertions.assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                BigDecimal.valueOf(3d)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.valueOf(3d)
        );
    }

    @Test
    void constructorWithCenterAndRadiusAndMathContext5() {
        GeometryAssertions.assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                BigDecimal.valueOf(3d),
                new MathContext(5)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.valueOf(3d)
        );
    }

    @Test
    void createAndSetCenter() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        GeometryAssertions.assertCircle(circle);
        circle.setCenter(new BigDecimalPoint(BigDecimal.ONE));
        GeometryAssertions.assertCircle(circle,
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.ONE
        );
    }

    @Test
    void createAndSetRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        GeometryAssertions.assertCircle(circle);
        circle.setRadius(BigDecimal.valueOf(2d));
        GeometryAssertions.assertCircle(circle, BigDecimal.valueOf(2d));
    }

    @Test
    void createAndExpectDiameterToBeBigDecimal() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        Assertions.assertEquals(BigDecimal.valueOf(2), circle.getDiameter());
        circle.setRadius(BigDecimal.valueOf(2d));
        Assertions.assertEquals(BigDecimal.valueOf(4), circle.getDiameter());
        circle.setDiameter(BigDecimal.valueOf(2d));
        Assertions.assertEquals(BigDecimal.valueOf(2), circle.getDiameter());
    }

    @Test
    void createAndSetRadiusAndExpectDiameterToBeBigDecimal() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        circle.setRadius(BigDecimal.valueOf(2d));
        Assertions.assertEquals(BigDecimal.valueOf(4), circle.getDiameter());
    }

    @Test
    void createAndSetDiameterAndExpectRadiusToBeHalf() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        circle.setDiameter(BigDecimal.valueOf(2d));
        Assertions.assertEquals(BigDecimal.valueOf(2), circle.getDiameter());
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfCircleWithoutParameter() {
        Assertions.assertEquals(
            BigDecimal.valueOf(Math.PI).round(GeometryAssertions.CONTEXT),
            new BigDecimalCircle().area()
        );
    }

    @Test
    void areaOfCircleWithRadius2() {
        Assertions.assertEquals(
            BigDecimal.valueOf(12.56637062d),
            new BigDecimalCircle(BigDecimal.valueOf(2d)).area()
        );
    }

    @Test
    void areaOfCircleWithRadius3() {
        Assertions.assertEquals(
            BigDecimal.valueOf(28.27433389d),
            new BigDecimalCircle(BigDecimal.valueOf(3d)).area()
        );
    }

    @Test
    void circumferenceOfCircleWithoutParameter() {
        Assertions.assertEquals(
            BigDecimal.valueOf(6.283185308d),
            new BigDecimalCircle().circumference()
        );
    }

    @Test
    void circumferenceOfCircleWithRadius2() {
        Assertions.assertEquals(
            BigDecimal.valueOf(12.56637062d),
            new BigDecimalCircle(BigDecimal.valueOf(2d)).circumference()
        );
    }

    @Test
    void circumferenceOfCircleWithRadius3() {
        Assertions.assertEquals(
            BigDecimal.valueOf(18.84955592d),
            new BigDecimalCircle(BigDecimal.valueOf(3d)).circumference()
        );
    }

    // endregion

    // region isUnitCircle

    @Test
    void isUnitCircleWithRadius0() {
        Assertions.assertFalse(new BigDecimalCircle(BigDecimal.ZERO).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius1() {
        Assertions.assertTrue(new BigDecimalCircle(BigDecimal.ONE).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius2() {
        Assertions.assertFalse(
            new BigDecimalCircle(BigDecimal.valueOf(2d)).isUnitCircle()
        );
    }

    // endregion

    // region isValid, move, rotate and copy

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new BigDecimalCircle().isValid());
    }

    @Test
    void isValidWithNegativeRadius() {
        Assertions.assertFalse(new BigDecimalCircle(BigDecimal.ONE.negate()).isValid());
    }

    @Test
    void moveOfCircleWithoutParameterWithXY() {
        GeometryAssertions.assertCircle(
            new BigDecimalCircle().move(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void moveOfCircleWithoutParameterWithXAndY() {
        GeometryAssertions.assertCircle(
            new BigDecimalCircle().move(BigDecimal.ONE, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void moveOfCircleWithoutParameterWithVector() {
        GeometryAssertions.assertCircle(
            new BigDecimalCircle().move(new BigDecimalVector(BigDecimal.ONE)),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithoutCenterWithPiHalf() {
        GeometryAssertions.assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
            ).rotate(BigDecimal.valueOf(Math.PI / 2d)),
            new BigDecimalPoint(
                BigDecimal.valueOf(-2d),
                BigDecimal.valueOf(1.0000000000000002d)
            )
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithCenterXY1WithPiHalf() {
        GeometryAssertions.assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.ONE, BigDecimal.valueOf(2d))
            ).rotate(
                new BigDecimalPoint(BigDecimal.ONE), BigDecimal.valueOf(Math.PI / 2d)
            ),
            new BigDecimalPoint(BigDecimal.ZERO, BigDecimal.ONE)
        );
    }

    @Test
    void copyOfCircleWithPointAndRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals(circle, circle.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfCircleWithPointAndRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals(
            circle,
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                BigDecimal.valueOf(3d)
            )
        );
        Assertions.assertNotEquals(
            circle,
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(3d)),
                BigDecimal.valueOf(2d)
            )
        );
    }

    @Test
    void hashCodeOfCircleWithPointAndRadius() {
        Assertions.assertEquals(
            647715,
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                BigDecimal.valueOf(3d)
            ).hashCode()
        );
    }

    @Test
    void toStringOfCircleWithPointAndRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals("2.0|2.0 3.0", circle.toString());
    }

    @Test
    void compareToOfCircleWithCenterAndRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals(
            0, circle.compareTo(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.valueOf(3d)
            ))
        );
        Assertions.assertEquals(
            -1, circle.compareTo(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(3d)), BigDecimal.valueOf(3d)
            ))
        );
        Assertions.assertEquals(
            1, circle.compareTo(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.ONE
            ))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new BigDecimalCircle(), Circle.class);
    }

    // endregion
}
