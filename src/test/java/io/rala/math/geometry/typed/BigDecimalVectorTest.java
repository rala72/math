package io.rala.math.geometry.typed;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.algebra.numeric.typed.BigDecimalComplex;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class BigDecimalVectorTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        GeometryAssertions.assertVector(new BigDecimalVector());
    }

    @Test
    void constructorWithXYParameter() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE), BigDecimal.ONE
        );
    }

    @Test
    void constructorWithEqualXYParameter() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithDifferentXYParameter() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
            ),
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
    }

    @Test
    void createAndSetX() {
        Vector<BigDecimal> vector = new BigDecimalVector();
        GeometryAssertions.assertVector(vector);
        vector.setX(BigDecimal.ONE);
        GeometryAssertions.assertVector(vector, BigDecimal.ONE, BigDecimal.ZERO);
    }

    @Test
    void createAndSetY() {
        Vector<BigDecimal> vector = new BigDecimalVector();
        GeometryAssertions.assertVector(vector);
        vector.setY(BigDecimal.valueOf(2d));
        GeometryAssertions.assertVector(vector, BigDecimal.ZERO, BigDecimal.valueOf(2d));
    }

    @Test
    void createAndSetXY() {
        Vector<BigDecimal> vector = new BigDecimalVector();
        GeometryAssertions.assertVector(vector);
        vector.setXY(BigDecimal.valueOf(3d));
        GeometryAssertions.assertVector(vector, BigDecimal.valueOf(3d));
    }

    // endregion

    // region length, add, subtract and multiply

    @Test
    void lengthOfVectorWithoutParameter() {
        Assertions.assertEquals(BigDecimal.ZERO, new BigDecimalVector().length());
    }

    @Test
    void lengthOfVectorXY1() {
        Assertions.assertEquals(
            BigDecimal.valueOf(Math.sqrt(2d)).round(GeometryAssertions.CONTEXT),
            new BigDecimalVector(BigDecimal.ONE).length()
        );
    }

    @Test
    void lengthOfVectorX1Y0() {
        Assertions.assertEquals(BigDecimal.ONE,
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.ZERO).length()
        );
    }

    @Test
    void addWithXY() {
        GeometryAssertions.assertVector(
            new BigDecimalVector().add(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(2d),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void addWithXAndY() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE).add(BigDecimal.ONE, BigDecimal.ONE),
            BigDecimal.valueOf(2d), BigDecimal.valueOf(2d)
        );
    }

    @Test
    void addWithVector() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.ZERO)
                .add(new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d))),
            BigDecimal.valueOf(2d), BigDecimal.valueOf(2d)
        );
    }

    @Test
    void subtractWithXY() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.valueOf(2d))
                .subtract(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void subtractWithXAndY() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.valueOf(2d))
                .subtract(BigDecimal.ONE, BigDecimal.ONE),
            BigDecimal.ONE
        );
    }

    @Test
    void subtractWithVector() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.valueOf(2d))
                .subtract(new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d))),
            BigDecimal.ONE, BigDecimal.ZERO
        );
    }

    @Test
    void multiplyZeroVectorWith1() {
        GeometryAssertions.assertVector(new BigDecimalVector().multiply(BigDecimal.ONE));
    }

    @Test
    void multiplyVectorWith0() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE)
                .multiply(BigDecimal.ZERO)
        );
    }

    @Test
    void multiplyVectorWith1() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE)
                .multiply(BigDecimal.ONE),
            BigDecimal.ONE
        );
    }

    @Test
    void multiplyVectorWithMinus1() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.ONE)
                .multiply(BigDecimal.ONE.negate()),
            BigDecimal.valueOf(-2d), BigDecimal.ONE.negate()
        );
    }

    // endregion

    // region inverse

    @Test
    void inverseXOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).inverseX(),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(2d)
        );
    }

    @Test
    void inverseYOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).inverseY(),
            BigDecimal.ONE, BigDecimal.valueOf(-2d)
        );
    }

    @Test
    void inverseXYOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).inverse(),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(-2d)
        );
    }

    // endregion

    // region absolute, normal and normalized

    @Test
    void absoluteOfPositiveXY() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).absolute(),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void absoluteOfNegativeXAndPositiveY() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(2d)
            ).absolute(),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void absoluteOfNegativeYAndPositiveX() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(-2d)
            ).absolute(),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void absoluteOfNegativeXAndY() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(-2d)
            ).absolute(),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void normalLeftOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).normalLeft(),
            BigDecimal.valueOf(-2d), BigDecimal.ONE
        );
    }

    @Test
    void normalRightOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).normalRight(),
            BigDecimal.valueOf(2d), BigDecimal.ONE.negate()
        );
    }

    @Test
    void normalizedOfVectorWithoutParameter() {
        Assertions.assertThrows(ArithmeticException.class,
            () -> new BigDecimalVector().normalized()
        ); // assert exception message?
    }

    @Test
    void normalizedOfVectorWithXY1() {
        Vector<BigDecimal> vector = new BigDecimalVector(BigDecimal.ONE).normalized();
        GeometryAssertions.assertVector(vector, BigDecimal.valueOf(0.7071067811865475d));
        Assertions.assertEquals(BigDecimal.valueOf(1.000000001), vector.length());
    }

    @Test
    void normalizedOfVectorWithX1Y2() {
        Vector<BigDecimal> vector = new BigDecimalVector(
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        ).normalized();
        GeometryAssertions.assertVector(
            vector,
            BigDecimal.valueOf(0.4472135954999579d),
            BigDecimal.valueOf(0.8944271909999159d)
        );
        Assertions.assertEquals(BigDecimal.valueOf(1.000000001), vector.length());
    }

    // endregion

    // region scalarProduct and angle

    @Test
    void scalarProductOfVectorWithoutParameterAndXY1() {
        Assertions.assertEquals(BigDecimal.ZERO,
            new BigDecimalVector()
                .scalarProduct(new BigDecimalVector(BigDecimal.ONE))
        );
    }

    @Test
    void scalarProductOfVectorWithXY1AndXY1() {
        Assertions.assertEquals(BigDecimal.valueOf(2),
            new BigDecimalVector(BigDecimal.ONE)
                .scalarProduct(new BigDecimalVector(BigDecimal.ONE))
        );
    }

    @Test
    void scalarProductOfVectorWithXY2AndX1Y2() {
        Assertions.assertEquals(BigDecimal.valueOf(6),
            new BigDecimalVector(BigDecimal.valueOf(2d))
                .scalarProduct(new BigDecimalVector(
                    BigDecimal.ONE,
                    BigDecimal.valueOf(2d)
                ))
        );
    }

    @Test
    void angleBetweenX0Y1AndX1Y0() {
        Assertions.assertEquals(
            BigDecimal.valueOf(Math.PI / 2d).round(GeometryAssertions.CONTEXT),
            new BigDecimalVector(BigDecimal.ZERO, BigDecimal.ONE)
                .angle(new BigDecimalVector(BigDecimal.ONE, BigDecimal.ZERO))
        );
    }

    @Test
    void angleBetweenX0Y1AndXY1() {
        Assertions.assertEquals(
            BigDecimal.valueOf(0.7853981631), // PI/4
            new BigDecimalVector(BigDecimal.ZERO, BigDecimal.ONE)
                .angle(new BigDecimalVector(BigDecimal.ONE, BigDecimal.ONE))
        );
    }

    // endregion

    // region isZeroVector and asComplex

    @Test
    void isZeroVectorWithVectorWithoutParameter() {
        Assertions.assertTrue(new BigDecimalVector().isZeroVector());
    }

    @Test
    void isZeroVectorWithVectorWithXY1() {
        Assertions.assertFalse(new BigDecimalVector(BigDecimal.ONE).isZeroVector());
    }

    @Test
    void asComplexOfVectorWithX1Y2() {
        Complex<BigDecimal> complex = new BigDecimalComplex(
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
        Assertions.assertEquals(complex,
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).asComplex()
        );
    }

    // endregion

    // region isValid rotate and copy

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new BigDecimalVector().isValid());
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiHalf() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI / 2d)),
            BigDecimal.valueOf(-2d), BigDecimal.ONE
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithPi() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI)),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(-2d)
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiThreeHalf() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI * 3d / 2d)),
            BigDecimal.valueOf(2d), BigDecimal.ONE.negate()
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithTwoPi() {
        GeometryAssertions.assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI * 2d)),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void copyOfVectorWithX2Y3() {
        Vector<BigDecimal> vector = new BigDecimalVector(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals(vector, vector.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfVectorWithX2Y3() {
        Vector<BigDecimal> vector = new BigDecimalVector(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals(
            vector,
            new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d))
        );
        Assertions.assertNotEquals(
            vector,
            new BigDecimalVector(BigDecimal.valueOf(3d), BigDecimal.valueOf(2d))
        );
    }

    @Test
    void hashCodeOfVectorWithX2Y3() {
        Assertions.assertEquals(
            21143,
            new BigDecimalVector(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
            ).hashCode()
        );
    }

    @Test
    void toStringOfVectorWithX2Y3() {
        Vector<BigDecimal> vector = new BigDecimalVector(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals("2.0:3.0", vector.toString());
    }

    @Test
    void compareToOfVectorWithX2Y3() {
        Vector<BigDecimal> vector = new BigDecimalVector(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        Assertions.assertEquals(
            0, vector.compareTo(
                new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d))
            )
        );
        Assertions.assertEquals(
            -1, vector.compareTo(
                new BigDecimalVector(BigDecimal.valueOf(3d), BigDecimal.ONE)
            )
        );
        Assertions.assertEquals(
            1, vector.compareTo(
                new BigDecimalVector(BigDecimal.ONE, BigDecimal.ZERO)
            )
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new BigDecimalVector(), Vector.class);
    }

    // endregion
}
