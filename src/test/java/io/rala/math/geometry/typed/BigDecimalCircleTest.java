package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertCircle;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class BigDecimalCircleTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertCircle(new BigDecimalCircle());
    }

    @Test
    void constructorWithMathContext5() {
        assertCircle(new BigDecimalCircle(new MathContext(5)));
    }

    @Test
    void constructorWithCenterButWithoutRadius() {
        assertCircle(
            new BigDecimalCircle(new BigDecimalPoint(BigDecimal.ONE)),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void constructorWithCenterAndMathContext5ButWithoutRadius() {
        assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.ONE),
                new MathContext(5)
            ),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void constructorWithRadiusButWithoutCenter() {
        assertCircle(
            new BigDecimalCircle(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithRadiusAndMathContext5ButWithoutCenter() {
        assertCircle(
            new BigDecimalCircle(
                BigDecimal.valueOf(2d),
                new MathContext(5)
            ),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithCenterAndPoint() {
        assertCircle(
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
        assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                new BigDecimalPoint(BigDecimal.ONE),
                new MathContext(5)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(Math.sqrt(2d)).round(new MathContext(5))
        );
    }

    @Test
    void constructorWithCenterAndRadius() {
        assertCircle(
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                BigDecimal.valueOf(3d)
            ),
            new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.valueOf(3d)
        );
    }

    @Test
    void constructorWithCenterAndRadiusAndMathContext5() {
        assertCircle(
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
        assertCircle(circle);
        circle.setCenter(new BigDecimalPoint(BigDecimal.ONE));
        assertCircle(circle,
            new BigDecimalPoint(BigDecimal.ONE),
            BigDecimal.ONE
        );
    }

    @Test
    void createAndSetRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        assertCircle(circle);
        circle.setRadius(BigDecimal.valueOf(2d));
        assertCircle(circle, BigDecimal.valueOf(2d));
    }

    @Test
    void createAndExpectDiameterToBeBigDecimal() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        assertEquals(BigDecimal.valueOf(2), circle.getDiameter());
        circle.setRadius(BigDecimal.valueOf(2d));
        assertEquals(BigDecimal.valueOf(4), circle.getDiameter());
        circle.setDiameter(BigDecimal.valueOf(2d));
        assertEquals(BigDecimal.valueOf(2), circle.getDiameter());
    }

    @Test
    void createAndSetRadiusAndExpectDiameterToBeBigDecimal() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        circle.setRadius(BigDecimal.valueOf(2d));
        assertEquals(BigDecimal.valueOf(4), circle.getDiameter());
    }

    @Test
    void createAndSetDiameterAndExpectRadiusToBeHalf() {
        Circle<BigDecimal> circle = new BigDecimalCircle();
        circle.setDiameter(BigDecimal.valueOf(2d));
        assertEquals(BigDecimal.valueOf(2), circle.getDiameter());
    }

    // endregion

    // region area and circumference

    @Test
    void areaOfCircleWithoutParameter() {
        assertEquals(
            BigDecimal.valueOf(Math.PI).round(GeometryAssertions.CONTEXT),
            new BigDecimalCircle().area()
        );
    }

    @Test
    void areaOfCircleWithRadius2() {
        assertEquals(
            BigDecimal.valueOf(12.56637062d),
            new BigDecimalCircle(BigDecimal.valueOf(2d)).area()
        );
    }

    @Test
    void areaOfCircleWithRadius3() {
        assertEquals(
            BigDecimal.valueOf(28.27433389d),
            new BigDecimalCircle(BigDecimal.valueOf(3d)).area()
        );
    }

    @Test
    void circumferenceOfCircleWithoutParameter() {
        assertEquals(
            BigDecimal.valueOf(6.283185308d),
            new BigDecimalCircle().circumference()
        );
    }

    @Test
    void circumferenceOfCircleWithRadius2() {
        assertEquals(
            BigDecimal.valueOf(12.56637062d),
            new BigDecimalCircle(BigDecimal.valueOf(2d)).circumference()
        );
    }

    @Test
    void circumferenceOfCircleWithRadius3() {
        assertEquals(
            BigDecimal.valueOf(18.84955592d),
            new BigDecimalCircle(BigDecimal.valueOf(3d)).circumference()
        );
    }

    // endregion

    // region isUnitCircle

    @Test
    void isUnitCircleWithRadius0() {
        assertFalse(new BigDecimalCircle(BigDecimal.ZERO).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius1() {
        assertTrue(new BigDecimalCircle(BigDecimal.ONE).isUnitCircle());
    }

    @Test
    void isUnitCircleWithRadius2() {
        assertFalse(
            new BigDecimalCircle(BigDecimal.valueOf(2d)).isUnitCircle()
        );
    }

    // endregion

    // region map, isValid, move, rotate and copy

    @Test
    void mapOfCircleWithXY0_5R1_5() {
        BigDecimalCircle circle = new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(0.5)), BigDecimal.valueOf(1.5)
        );
        IntegerArithmetic integerArithmetic = new IntegerArithmetic();
        Circle<Integer> result = new Circle<>(integerArithmetic,
            new Point<>(integerArithmetic, 0), 1
        );
        assertEquals(result,
            circle.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZeroValues() {
        assertTrue(new BigDecimalCircle().isValid());
    }

    @Test
    void isValidWithNegativeRadius() {
        assertFalse(new BigDecimalCircle(BigDecimal.ONE.negate()).isValid());
    }

    @Test
    void moveOfCircleWithoutParameterWithXY() {
        assertCircle(
            new BigDecimalCircle().move(BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void moveOfCircleWithoutParameterWithXAndY() {
        assertCircle(
            new BigDecimalCircle().move(BigDecimal.ONE, BigDecimal.ONE),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void moveOfCircleWithoutParameterWithVector() {
        assertCircle(
            new BigDecimalCircle().move(new BigDecimalVector(BigDecimal.ONE)),
            new BigDecimalPoint(BigDecimal.ONE)
        );
    }

    @Test
    void rotateOfCircleWithX1Y2WithoutCenterWithPiHalf() {
        assertCircle(
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
        assertCircle(
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
        assertEquals(circle, circle.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfCircleWithPointAndRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.valueOf(3d)
        );
        assertEquals(circle,
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)),
                BigDecimal.valueOf(3d)
            )
        );
        assertNotEquals(circle,
            new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(3d)),
                BigDecimal.valueOf(2d)
            )
        );
    }

    @Test
    void hashCodeOfCircleWithPointAndRadius() {
        assertEquals(647715,
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
        assertEquals("2.0|2.0 3.0", circle.toString());
    }

    @Test
    void compareToOfCircleWithCenterAndRadius() {
        Circle<BigDecimal> circle = new BigDecimalCircle(
            new BigDecimalPoint(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(3d)
        );
        assertEquals(0,
            circle.compareTo(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.valueOf(3d)
            ))
        );
        assertEquals(-1,
            circle.compareTo(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(3d)), BigDecimal.valueOf(3d)
            ))
        );
        assertEquals(1,
            circle.compareTo(new BigDecimalCircle(
                new BigDecimalPoint(BigDecimal.valueOf(2d)), BigDecimal.ONE
            ))
        );
    }

    @Test
    void serializable() {
        assertSerializable(new BigDecimalCircle(), BigDecimalCircle.class);
    }

    // endregion
}
