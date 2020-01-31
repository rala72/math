package io.rala.math.geometry.typed;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.algebra.numeric.typed.DoubleComplex;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DoubleVectorTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        GeometryAssertions.assertVector(new DoubleVector());
    }

    @Test
    void constructorWithXYParameter() {
        GeometryAssertions.assertVector(new DoubleVector(1d), 1d);
    }

    @Test
    void constructorWithEqualXYParameter() {
        GeometryAssertions.assertVector(new DoubleVector(2d, 2d), 2d);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        GeometryAssertions.assertVector(new DoubleVector(2d, 3d), 2d, 3d);
    }

    @Test
    void createAndSetX() {
        Vector<Double> vector = new DoubleVector();
        GeometryAssertions.assertVector(vector);
        vector.setX(1d);
        GeometryAssertions.assertVector(vector, 1d, 0d);
    }

    @Test
    void createAndSetY() {
        Vector<Double> vector = new DoubleVector();
        GeometryAssertions.assertVector(vector);
        vector.setY(2d);
        GeometryAssertions.assertVector(vector, 0d, 2d);
    }

    @Test
    void createAndSetXY() {
        Vector<Double> vector = new DoubleVector();
        GeometryAssertions.assertVector(vector);
        vector.setXY(3d);
        GeometryAssertions.assertVector(vector, 3d);
    }

    // endregion

    // region length, add, subtract and multiply

    @Test
    void lengthOfVectorWithoutParameter() {
        Assertions.assertEquals(0d, new DoubleVector().length());
    }

    @Test
    void lengthOfVectorXY1() {
        Assertions.assertEquals(Math.sqrt(2d), new DoubleVector(1d).length());
    }

    @Test
    void lengthOfVectorX1Y0() {
        Assertions.assertEquals(1d, new DoubleVector(1d, 0d).length());
    }

    @Test
    void addWithXY() {
        GeometryAssertions.assertVector(new DoubleVector().add(2d), 2d, 2d);
    }

    @Test
    void addWithXAndY() {
        GeometryAssertions.assertVector(
            new DoubleVector(1d).add(1d, 1d),
            2d, 2d
        );
    }

    @Test
    void addWithVector() {
        GeometryAssertions.assertVector(
            new DoubleVector(1d, 0d).add(new DoubleVector(1d, 2d)),
            2d, 2d
        );
    }

    @Test
    void subtractWithXY() {
        GeometryAssertions.assertVector(new DoubleVector(2d, 2d).subtract(2d));
    }

    @Test
    void subtractWithXAndY() {
        GeometryAssertions.assertVector(
            new DoubleVector(2d, 2d).subtract(1d, 1d),
            1d
        );
    }

    @Test
    void subtractWithVector() {
        GeometryAssertions.assertVector(
            new DoubleVector(2d, 2d).subtract(new DoubleVector(1d, 2d)),
            1d, 0d
        );
    }

    @Test
    void multiplyZeroVectorWith1() {
        GeometryAssertions.assertVector(new DoubleVector().multiply(1d));
    }

    @Test
    void multiplyVectorWith0() {
        GeometryAssertions.assertVector(new DoubleVector(1d).multiply(0d));
    }

    @Test
    void multiplyVectorWith1() {
        GeometryAssertions.assertVector(new DoubleVector(1d).multiply(1d), 1d);
    }

    @Test
    void multiplyVectorWithMinus1() {
        GeometryAssertions.assertVector(
            new DoubleVector(2d, 1d).multiply(-1d),
            -2d, -1d
        );
    }

    // endregion

    // region inverse

    @Test
    void inverseXOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(new DoubleVector(1d, 2d).inverseX(), -1d, 2d);
    }

    @Test
    void inverseYOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(new DoubleVector(1d, 2d).inverseY(), 1d, -2d);
    }

    @Test
    void inverseXYOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(new DoubleVector(1d, 2d).inverse(), -1d, -2d);
    }

    // endregion

    // region absolute, normal and normalized

    @Test
    void absoluteOfPositiveXY() {
        GeometryAssertions.assertVector(new DoubleVector(1d, 2d).absolute(), 1d, 2d);
    }

    @Test
    void absoluteOfNegativeXAndPositiveY() {
        GeometryAssertions.assertVector(new DoubleVector(-1d, 2d).absolute(), 1d, 2d);
    }

    @Test
    void absoluteOfNegativeYAndPositiveX() {
        GeometryAssertions.assertVector(new DoubleVector(-1d, -2d).absolute(), 1d, 2d);
    }

    @Test
    void absoluteOfNegativeXAndY() {
        GeometryAssertions.assertVector(new DoubleVector(-1d, -2d).absolute(), 1d, 2d);
    }

    @Test
    void normalLeftOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(new DoubleVector(1d, 2d).normalLeft(), -2d, 1d);
    }

    @Test
    void normalRightOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(new DoubleVector(1d, 2d).normalRight(), 2d, -1d);
    }

    @Test
    void normalizedOfVectorWithoutParameter() {
        Vector<Double> vector = new DoubleVector().normalized();
        GeometryAssertions.assertVector(vector, Double.NaN, Double.NaN);
        Assertions.assertEquals(Double.NaN, vector.length());
    }

    @Test
    void normalizedOfVectorWithXY1() {
        Vector<Double> vector = new DoubleVector(1d).normalized();
        GeometryAssertions.assertVector(vector, 0.7071067811865475d);
        Assertions.assertEquals(
            1d,
            vector.length(),
            GeometryAssertions.DELTA
        );
    }

    @Test
    void normalizedOfVectorWithX1Y2() {
        Vector<Double> vector = new DoubleVector(1d, 2d).normalized();
        GeometryAssertions.assertVector(
            vector,
            0.4472135954999579d, 0.8944271909999159d
        );
        Assertions.assertEquals(
            1d,
            vector.length(),
            GeometryAssertions.DELTA
        );
    }

    // endregion

    // region scalarProduct and angle

    @Test
    void scalarProductOfVectorWithoutParameterAndXY1() {
        Assertions.assertEquals(0d,
            new DoubleVector().scalarProduct(new DoubleVector(1d))
        );
    }

    @Test
    void scalarProductOfVectorWithXY1AndXY1() {
        Assertions.assertEquals(2d,
            new DoubleVector(1d).scalarProduct(new DoubleVector(1d))
        );
    }

    @Test
    void scalarProductOfVectorWithXY2AndX1Y2() {
        Assertions.assertEquals(6d,
            new DoubleVector(2d).scalarProduct(new DoubleVector(1d, 2d))
        );
    }

    @Test
    void angleBetweenX0Y1AndX1Y0() {
        Assertions.assertEquals(Math.PI / 2d,
            new DoubleVector(0d, 1d).angle(new DoubleVector(1d, 0d))
        );
    }

    @Test
    void angleBetweenX0Y1AndXY1() {
        Assertions.assertEquals(Math.PI / 4d,
            new DoubleVector(0d, 1d)
                .angle(new DoubleVector(1d, 1d)),
            GeometryAssertions.DELTA
        );
    }

    // endregion

    // region isZeroVector and asComplex

    @Test
    void isZeroVectorWithVectorWithoutParameter() {
        Assertions.assertTrue(new DoubleVector().isZeroVector());
    }

    @Test
    void isZeroVectorWithVectorWithXY1() {
        Assertions.assertFalse(new DoubleVector(1d).isZeroVector());
    }

    @Test
    void asComplexOfVectorWithX1Y2() {
        Complex<Double> complex = new DoubleComplex(1d, 2d);
        Assertions.assertEquals(complex,
            new DoubleVector(1d, 2d).asComplex()
        );
    }

    // endregion

    // region map, isValid rotate and copy

    @Test
    void mapOfVectorWithX0_5Y1_5() {
        DoubleVector vector = new DoubleVector(0.5, 1.5);
        Vector<Integer> result = new Vector<>(new IntegerArithmetic(), 0, 1);
        Assertions.assertEquals(result,
            vector.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new DoubleVector().isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new DoubleVector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiHalf() {
        GeometryAssertions.assertVector(
            new DoubleVector(1d, 2d).rotate(Math.PI / 2d),
            -2d, 1d
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithPi() {
        GeometryAssertions.assertVector(
            new DoubleVector(1d, 2d).rotate(Math.PI),
            -1d, -2d
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiThreeHalf() {
        GeometryAssertions.assertVector(
            new DoubleVector(1d, 2d).rotate(Math.PI * 3d / 2d),
            2d, -1d
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithTwoPi() {
        GeometryAssertions.assertVector(
            new DoubleVector(1d, 2d).rotate(Math.PI * 2d),
            1d, 2d
        );
    }

    @Test
    void copyOfVectorWithX2Y3() {
        Vector<Double> vector = new DoubleVector(2d, 3d);
        Assertions.assertEquals(vector, vector.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfVectorWithX2Y3() {
        Vector<Double> vector = new DoubleVector(2d, 3d);
        Assertions.assertEquals(
            vector,
            new DoubleVector(2d, 3d)
        );
        Assertions.assertNotEquals(
            vector,
            new DoubleVector(3d, 2d)
        );
    }

    @Test
    void hashCodeOfVectorWithX2Y3() {
        Assertions.assertEquals(
            525249,
            new DoubleVector(2d, 3d).hashCode()
        );
    }

    @Test
    void toStringOfVectorWithX2Y3() {
        Vector<Double> vector = new DoubleVector(2d, 3d);
        Assertions.assertEquals("2.0:3.0", vector.toString());
    }

    @Test
    void compareToOfVectorWithX2Y3() {
        Vector<Double> vector = new DoubleVector(2d, 3d);
        Assertions.assertEquals(
            0d, vector.compareTo(new DoubleVector(2d, 3d))
        );
        Assertions.assertEquals(
            -1d, vector.compareTo(new DoubleVector(3d, 1d))
        );
        Assertions.assertEquals(
            1d, vector.compareTo(new DoubleVector(1d, 0d))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new DoubleVector(), Vector.class);
    }

    // endregion
}
