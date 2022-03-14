package io.rala.math.geometry;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.algebra.TestComplex;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertVector;
import static io.rala.math.testUtils.assertion.OffsetUtils.doubleOffset;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(new TestVector().length()).isEqualTo(0d);
    }

    @Test
    void lengthOfVectorXY1() {
        assertThat(new TestVector(1).length()).isEqualTo(Math.sqrt(2));
    }

    @Test
    void lengthOfVectorX1Y0() {
        assertThat(new TestVector(1, 0).length()).isEqualTo(1d);
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
        assertThat(vector.length()).isEqualTo(Double.NaN);
    }

    @Test
    void normalizedOfVectorWithXY1() {
        Vector<Number> vector = new TestVector(1d).normalized();
        assertVector(vector, 0.7071067811865475);
        assertThat(vector.length().doubleValue()).isCloseTo(1, doubleOffset());
    }

    @Test
    void normalizedOfVectorWithX1Y2() {
        Vector<Number> vector = new TestVector(1d, 2d).normalized();
        assertVector(vector,
            0.4472135954999579, 0.8944271909999159
        );
        assertThat(vector.length().doubleValue()).isCloseTo(1, doubleOffset());
    }

    // endregion

    // region scalarProduct and angle

    @Test
    void scalarProductOfVectorWithoutParameterAndXY1() {
        assertThat(new TestVector().scalarProduct(new TestVector(1))).isEqualTo(0d);
    }

    @Test
    void scalarProductOfVectorWithXY1AndXY1() {
        assertThat(new TestVector(1).scalarProduct(new TestVector(1))).isEqualTo(2d);
    }

    @Test
    void scalarProductOfVectorWithXY2AndX1Y2() {
        assertThat(new TestVector(2).scalarProduct(new TestVector(1, 2))).isEqualTo(6d);
    }

    @Test
    void angleBetweenX0Y1AndX1Y0() {
        assertThat(new TestVector(0, 1).angle(new TestVector(1, 0))).isEqualTo(Math.PI / 2);
    }

    @Test
    void angleBetweenX0Y1AndXY1() {
        assertThat(new TestVector(0, 1)
            .angle(new TestVector(1, 1))
            .doubleValue()).isCloseTo(Math.PI / 4, doubleOffset());
    }

    // endregion

    // region isZeroVector and asComplex

    @Test
    void isZeroVectorWithVectorWithoutParameter() {
        assertThat(new TestVector().isZeroVector()).isTrue();
    }

    @Test
    void isZeroVectorWithVectorWithXY1() {
        assertThat(new TestVector(1).isZeroVector()).isFalse();
    }

    @Test
    void asComplexOfVectorWithX1Y2() {
        Complex<Number> complex = new TestComplex(1, 2);
        assertThat(new TestVector(1, 2).asComplex()).isEqualTo(complex);
    }

    // endregion

    // region map, isValid rotate and copy

    @Test
    void mapOfVectorWithX0_5Y1_5() {
        TestVector vector = new TestVector(0.5, 1.5);
        Vector<Integer> result = new Vector<>(new IntegerArithmetic(), 0, 1);
        assertThat(vector.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new TestVector().isValid()).isTrue();
    }

    @Test
    void isValidWithInfValues() {
        assertThat(new TestVector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
            .isValid()).isFalse();
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
        assertThat(vector.copy()).isEqualTo(vector);
    }

    // endregion

    // region override

    @Test
    void equalsOfVectorWithX2Y3() {
        assertThat(new TestVector(2, 3))
            .isEqualTo(new TestVector(2, 3))
            .isNotEqualTo(new TestVector(3, 2));
    }

    @Test
    void hashCodeOfVectorWithX2Y3() {
        assertThat(new TestVector(2, 3).hashCode()).isEqualTo(1026);
    }

    @Test
    void toStringOfVectorWithX2Y3() {
        Vector<Number> vector = new TestVector(2, 3);
        assertThat(vector).hasToString("2:3");
    }

    @Test
    void compareToOfVectorWithX2Y3() {
        Vector<Number> vector = new TestVector(2, 3);
        assertThat(vector)
            .isEqualByComparingTo(new TestVector(2, 3))
            .isLessThan(new TestVector(3, 1))
            .isGreaterThan(new TestVector(1, 0));
    }

    @Test
    void serializable() {
        assertSerializable(new TestVector(), TestVector.class);
    }

    // endregion
}
