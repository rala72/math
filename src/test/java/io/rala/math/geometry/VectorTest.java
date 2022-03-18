package io.rala.math.geometry;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.algebra.TestComplex;
import io.rala.math.testUtils.geometry.TestVector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatComplex;
import static io.rala.math.testUtils.assertion.GeometryAssertions.assertThatVector;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static io.rala.math.testUtils.assertion.utils.OffsetUtils.doubleOffset;
import static org.assertj.core.api.Assertions.assertThat;

class VectorTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertThatVector(new TestVector()).hasZeroXY();
    }

    @Test
    void constructorWithXYParameter() {
        assertThatVector(new TestVector(1)).hasXY(1);
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertThatVector(new TestVector(2, 2)).hasXY(2);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertThatVector(new TestVector(2, 3)).hasX(2).hasY(3);
    }

    @Test
    void createAndSetX() {
        Vector<Number> vector = new TestVector();
        assertThatVector(vector).hasZeroXY();
        vector.setX(1);
        assertThatVector(vector).hasX(1).hasY(0);
    }

    @Test
    void createAndSetY() {
        Vector<Number> vector = new TestVector();
        assertThatVector(vector).hasZeroXY();
        vector.setY(2);
        assertThatVector(vector).hasX(0).hasY(2);
    }

    @Test
    void createAndSetXY() {
        Vector<Number> vector = new TestVector();
        assertThatVector(vector).hasZeroXY();
        vector.setXY(3);
        assertThatVector(vector).hasXY(3);
    }

    // endregion

    // region length, add, subtract and multiply

    @Test
    void lengthOfVectorWithoutParameter() {
        assertThatVector(new TestVector()).hasLength(0d);
    }

    @Test
    void lengthOfVectorXY1() {
        assertThatVector(new TestVector(1)).hasLength(Math.sqrt(2));
    }

    @Test
    void lengthOfVectorX1Y0() {
        assertThatVector(new TestVector(1, 0)).hasLength(1d);
    }

    @Test
    void addWithXY() {
        assertThatVector(new TestVector().add(2)).hasXY(2);
    }

    @Test
    void addWithXAndY() {
        assertThatVector(
            new TestVector(1).add(1, 1)
        ).hasX(2).hasY(2);
    }

    @Test
    void addWithVector() {
        assertThatVector(
            new TestVector(1, 0).add(new TestVector(1, 2))
        ).hasX(2).hasY(2);
    }

    @Test
    void subtractWithXY() {
        assertThatVector(new TestVector(2, 2).subtract(2)).hasZeroXY();
    }

    @Test
    void subtractWithXAndY() {
        assertThatVector(
            new TestVector(2, 2).subtract(1, 1)
        ).hasXY(1);
    }

    @Test
    void subtractWithVector() {
        assertThatVector(
            new TestVector(2, 2).subtract(new TestVector(1, 2))
        ).hasX(1).hasY(0);
    }

    @Test
    void multiplyZeroVectorWith1() {
        assertThatVector(new TestVector().multiply(1)).hasZeroXY();
    }

    @Test
    void multiplyVectorWith0() {
        assertThatVector(new TestVector(1).multiply(0)).hasZeroXY();
    }

    @Test
    void multiplyVectorWith1() {
        assertThatVector(new TestVector(1).multiply(1)).hasXY(1);
    }

    @Test
    void multiplyVectorWithMinus1() {
        assertThatVector(new TestVector(2, 1).multiply(-1)).hasX(-2).hasY(-1);
    }

    // endregion

    // region inverse

    @Test
    void inverseXOfVectorWithX1Y2() {
        assertThatVector(new TestVector(1, 2).inverseX()).hasX(-1).hasY(2);
    }

    @Test
    void inverseYOfVectorWithX1Y2() {
        assertThatVector(new TestVector(1, 2).inverseY()).hasX(1).hasY(-2);
    }

    @Test
    void inverseXYOfVectorWithX1Y2() {
        assertThatVector(new TestVector(1, 2).inverse()).hasX(-1).hasY(-2);
    }

    // endregion

    // region absolute, normal and normalized

    @Test
    void absoluteOfPositiveXY() {
        assertThatVector(new TestVector(1, 2).absolute()).hasX(1).hasY(2);
    }

    @Test
    void absoluteOfNegativeXAndPositiveY() {
        assertThatVector(new TestVector(-1, 2).absolute()).hasX(1).hasY(2);
    }

    @Test
    void absoluteOfNegativeYAndPositiveX() {
        assertThatVector(new TestVector(-1, -2).absolute()).hasX(1).hasY(2);
    }

    @Test
    void absoluteOfNegativeXAndY() {
        assertThatVector(new TestVector(-1, -2).absolute()).hasX(1).hasY(2);
    }

    @Test
    void normalLeftOfVectorWithX1Y2() {
        assertThatVector(new TestVector(1, 2).normalLeft()).hasX(-2).hasY(1);
    }

    @Test
    void normalRightOfVectorWithX1Y2() {
        assertThatVector(new TestVector(1, 2).normalRight()).hasX(2).hasY(-1);
    }

    @Test
    void normalizedOfVectorWithoutParameter() {
        Vector<Number> vector = new TestVector().normalized();
        assertThatVector(vector).hasX(Double.NaN).hasY(Double.NaN);
        assertThatVector(vector).hasLength(Double.NaN);
    }

    @Test
    void normalizedOfVectorWithXY1() {
        Vector<Number> vector = new TestVector(1d).normalized();
        assertThatVector(vector).hasXY(Math.sqrt(2) / 2);
        assertThatVector(vector).hasLengthCloseTo(1);
    }

    @Test
    void normalizedOfVectorWithX1Y2() {
        Vector<Number> vector = new TestVector(1d, 2d).normalized();
        assertThatVector(vector).hasX(0.4472135954999579).hasY(0.8944271909999159);
        assertThatVector(vector).hasLengthCloseTo(1);
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
            .doubleValue()
        ).isCloseTo(Math.PI / 4, doubleOffset());
    }

    // endregion

    // region isZeroVector and asComplex

    @Test
    void isZeroVectorWithVectorWithoutParameter() {
        assertThatVector(new TestVector()).isZeroVector();
    }

    @Test
    void isZeroVectorWithVectorWithXY1() {
        assertThatVector(new TestVector(1)).isNoZeroVector();
    }

    @Test
    void asComplexOfVectorWithX1Y2() {
        Complex<Number> complex = new TestComplex(1, 2);
        assertThatComplex(new TestVector(1, 2).asComplex()).isEqualTo(complex);
    }

    // endregion

    // region map, isValid rotate and copy

    @Test
    void mapOfVectorWithX0_5Y1_5() {
        TestVector vector = new TestVector(0.5, 1.5);
        Vector<Integer> result = new Vector<>(new IntegerArithmetic(), 0, 1);
        assertThatVector(vector.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThatVector(new TestVector()).isValid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatVector(
            new TestVector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        ).isInvalid();
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiHalf() {
        assertThatVector(
            new TestVector(1, 2).rotate(Math.PI / 2)
        ).hasX(-2).hasY(1);
    }

    @Test
    void rotateOfVectorWithX1Y2WithPi() {
        assertThatVector(new TestVector(1, 2).rotate(Math.PI)).hasX(-1).hasY(-2);
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiThreeHalf() {
        assertThatVector(
            new TestVector(1, 2).rotate(Math.PI * 3 / 2)
        ).hasX(2).hasY(-1);
    }

    @Test
    void rotateOfVectorWithX1Y2WithTwoPi() {
        assertThatVector(new TestVector(1, 2).rotate(Math.PI * 2)).hasX(1).hasY(2);
    }

    @Test
    void copyOfVectorWithX2Y3() {
        assertCopyable(new TestVector(2, 3));
    }

    // endregion

    // region override

    @Test
    void equalsOfVectorWithX2Y3() {
        assertThatVector(new TestVector(2, 3))
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
        assertThatVector(vector).hasToString("2:3");
    }

    @Test
    void compareToOfVectorWithX2Y3() {
        Vector<Number> vector = new TestVector(2, 3);
        assertThatVector(vector)
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
