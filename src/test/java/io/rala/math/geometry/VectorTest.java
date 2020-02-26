package io.rala.math.geometry;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.algebra.TestComplex;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertVector;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class VectorTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertVector(new TestVector());
    }

    @Test
    void constructorWithXYParameter() {
        assertVector(new TestVector(1), 1);
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertVector(new TestVector(2, 2), 2);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertVector(new TestVector(2, 3), 2, 3);
    }

    @Test
    void createAndSetX() {
        Vector<Number> vector = new TestVector();
        assertVector(vector);
        vector.setX(1);
        assertVector(vector, 1, 0);
    }

    @Test
    void createAndSetY() {
        Vector<Number> vector = new TestVector();
        assertVector(vector);
        vector.setY(2);
        assertVector(vector, 0, 2);
    }

    @Test
    void createAndSetXY() {
        Vector<Number> vector = new TestVector();
        assertVector(vector);
        vector.setXY(3);
        assertVector(vector, 3);
    }

    // endregion

    // region length, add, subtract and multiply

    @Test
    void lengthOfVectorWithoutParameter() {
        assertEquals(0d, new TestVector().length());
    }

    @Test
    void lengthOfVectorXY1() {
        assertEquals(Math.sqrt(2), new TestVector(1).length());
    }

    @Test
    void lengthOfVectorX1Y0() {
        assertEquals(1d, new TestVector(1, 0).length());
    }

    @Test
    void addWithXY() {
        assertVector(new TestVector().add(2), 2, 2);
    }

    @Test
    void addWithXAndY() {
        assertVector(
            new TestVector(1).add(1, 1),
            2, 2
        );
    }

    @Test
    void addWithVector() {
        assertVector(
            new TestVector(1, 0).add(new TestVector(1, 2)),
            2, 2
        );
    }

    @Test
    void subtractWithXY() {
        assertVector(new TestVector(2, 2).subtract(2));
    }

    @Test
    void subtractWithXAndY() {
        assertVector(
            new TestVector(2, 2).subtract(1, 1),
            1
        );
    }

    @Test
    void subtractWithVector() {
        assertVector(
            new TestVector(2, 2).subtract(new TestVector(1, 2)),
            1, 0
        );
    }

    @Test
    void multiplyZeroVectorWith1() {
        assertVector(new TestVector().multiply(1));
    }

    @Test
    void multiplyVectorWith0() {
        assertVector(new TestVector(1).multiply(0));
    }

    @Test
    void multiplyVectorWith1() {
        assertVector(new TestVector(1).multiply(1), 1);
    }

    @Test
    void multiplyVectorWithMinus1() {
        assertVector(new TestVector(2, 1).multiply(-1), -2, -1);
    }

    // endregion

    // region inverse

    @Test
    void inverseXOfVectorWithX1Y2() {
        assertVector(new TestVector(1, 2).inverseX(), -1, 2);
    }

    @Test
    void inverseYOfVectorWithX1Y2() {
        assertVector(new TestVector(1, 2).inverseY(), 1, -2);
    }

    @Test
    void inverseXYOfVectorWithX1Y2() {
        assertVector(new TestVector(1, 2).inverse(), -1, -2);
    }

    // endregion

    // region absolute, normal and normalized

    @Test
    void absoluteOfPositiveXY() {
        assertVector(new TestVector(1, 2).absolute(), 1, 2);
    }

    @Test
    void absoluteOfNegativeXAndPositiveY() {
        assertVector(new TestVector(-1, 2).absolute(), 1, 2);
    }

    @Test
    void absoluteOfNegativeYAndPositiveX() {
        assertVector(new TestVector(-1, -2).absolute(), 1, 2);
    }

    @Test
    void absoluteOfNegativeXAndY() {
        assertVector(new TestVector(-1, -2).absolute(), 1, 2);
    }

    @Test
    void normalLeftOfVectorWithX1Y2() {
        assertVector(new TestVector(1, 2).normalLeft(), -2, 1);
    }

    @Test
    void normalRightOfVectorWithX1Y2() {
        assertVector(new TestVector(1, 2).normalRight(), 2, -1);
    }

    @Test
    void normalizedOfVectorWithoutParameter() {
        Vector<Number> vector = new TestVector().normalized();
        assertVector(vector, Double.NaN, Double.NaN);
        assertEquals(Double.NaN, vector.length());
    }

    @Test
    void normalizedOfVectorWithXY1() {
        Vector<Number> vector = new TestVector(1d).normalized();
        assertVector(vector, 0.7071067811865475);
        assertEquals(
            1,
            vector.length().doubleValue(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void normalizedOfVectorWithX1Y2() {
        Vector<Number> vector = new TestVector(1d, 2d).normalized();
        assertVector(
            vector,
            0.4472135954999579, 0.8944271909999159
        );
        assertEquals(
            1,
            vector.length().doubleValue(),
            GeometryAssertions.DELTA
        );
    }

    // endregion

    // region scalarProduct and angle

    @Test
    void scalarProductOfVectorWithoutParameterAndXY1() {
        assertEquals(0d, new TestVector().scalarProduct(new TestVector(1)));
    }

    @Test
    void scalarProductOfVectorWithXY1AndXY1() {
        assertEquals(2d, new TestVector(1).scalarProduct(new TestVector(1)));
    }

    @Test
    void scalarProductOfVectorWithXY2AndX1Y2() {
        assertEquals(6d,
            new TestVector(2).scalarProduct(new TestVector(1, 2))
        );
    }

    @Test
    void angleBetweenX0Y1AndX1Y0() {
        assertEquals(Math.PI / 2,
            new TestVector(0, 1).angle(new TestVector(1, 0))
        );
    }

    @Test
    void angleBetweenX0Y1AndXY1() {
        assertEquals(Math.PI / 4,
            new TestVector(0, 1)
                .angle(new TestVector(1, 1))
                .doubleValue(),
            GeometryAssertions.DELTA
        );
    }

    // endregion

    // region isZeroVector and asComplex

    @Test
    void isZeroVectorWithVectorWithoutParameter() {
        assertTrue(new TestVector().isZeroVector());
    }

    @Test
    void isZeroVectorWithVectorWithXY1() {
        assertFalse(new TestVector(1).isZeroVector());
    }

    @Test
    void asComplexOfVectorWithX1Y2() {
        Complex<Number> complex = new TestComplex(1, 2);
        assertEquals(complex,
            new TestVector(1, 2).asComplex()
        );
    }

    // endregion

    // region map, isValid rotate and copy

    @Test
    void mapOfVectorWithX0_5Y1_5() {
        TestVector vector = new TestVector(0.5, 1.5);
        Vector<Integer> result = new Vector<>(new IntegerArithmetic(), 0, 1);
        assertEquals(result,
            vector.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZeroValues() {
        assertTrue(new TestVector().isValid());
    }

    @Test
    void isValidWithInfValues() {
        assertFalse(
            new TestVector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiHalf() {
        assertVector(
            new TestVector(1, 2).rotate(Math.PI / 2),
            -2, 1
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithPi() {
        assertVector(new TestVector(1, 2).rotate(Math.PI), -1, -2);
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiThreeHalf() {
        assertVector(
            new TestVector(1, 2).rotate(Math.PI * 3 / 2),
            2, -1
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithTwoPi() {
        assertVector(new TestVector(1, 2).rotate(Math.PI * 2), 1, 2);
    }

    @Test
    void copyOfVectorWithX2Y3() {
        Vector<Number> vector = new TestVector(2, 3);
        assertEquals(vector, vector.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfVectorWithX2Y3() {
        Vector<Number> vector = new TestVector(2, 3);
        assertEquals(
            vector,
            new TestVector(2, 3)
        );
        assertNotEquals(
            vector,
            new TestVector(3, 2)
        );
    }

    @Test
    void hashCodeOfVectorWithX2Y3() {
        assertEquals(
            1026,
            new TestVector(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfVectorWithX2Y3() {
        Vector<Number> vector = new TestVector(2, 3);
        assertEquals("2:3", vector.toString());
    }

    @Test
    void compareToOfVectorWithX2Y3() {
        Vector<Number> vector = new TestVector(2, 3);
        assertEquals(
            0, vector.compareTo(new TestVector(2, 3))
        );
        assertEquals(
            -1, vector.compareTo(new TestVector(3, 1))
        );
        assertEquals(
            1, vector.compareTo(new TestVector(1, 0))
        );
    }

    @Test
    void serializable() {
        assertSerializable(new TestVector(), Vector.class);
    }

    // endregion
}
