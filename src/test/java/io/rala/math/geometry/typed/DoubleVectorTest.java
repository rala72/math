package io.rala.math.geometry.typed;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.algebra.numeric.typed.DoubleComplex;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatComplex;
import static io.rala.math.testUtils.assertion.GeometryAssertions.assertThatVector;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static io.rala.math.testUtils.assertion.utils.OffsetUtils.doubleOffset;
import static org.assertj.core.api.Assertions.assertThat;

class DoubleVectorTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertThatVector(new DoubleVector()).hasZeroXY();
    }

    @Test
    void constructorWithXYParameter() {
        assertThatVector(new DoubleVector(1d)).hasXY(1d);
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertThatVector(new DoubleVector(2d, 2d)).hasXY(2d);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertThatVector(new DoubleVector(2d, 3d)).hasX(2d).hasY(3d);
    }

    @Test
    void createAndSetX() {
        Vector<Double> vector = new DoubleVector();
        assertThatVector(vector).hasZeroXY();
        vector.setX(1d);
        assertThatVector(vector).hasX(1d).hasY(0d);
    }

    @Test
    void createAndSetY() {
        Vector<Double> vector = new DoubleVector();
        assertThatVector(vector).hasZeroXY();
        vector.setY(2d);
        assertThatVector(vector).hasX(0d).hasY(2d);
    }

    @Test
    void createAndSetXY() {
        Vector<Double> vector = new DoubleVector();
        assertThatVector(vector).hasZeroXY();
        vector.setXY(3d);
        assertThatVector(vector).hasXY(3d);
    }

    // endregion

    // region length, add, subtract and multiply

    @Test
    void lengthOfVectorWithoutParameter() {
        assertThatVector(new DoubleVector()).hasLength(0d);
    }

    @Test
    void lengthOfVectorXY1() {
        assertThatVector(new DoubleVector(1d)).hasLength(Math.sqrt(2d));
    }

    @Test
    void lengthOfVectorX1Y0() {
        assertThatVector(new DoubleVector(1d, 0d)).hasLength(1d);
    }

    @Test
    void addWithXY() {
        assertThatVector(new DoubleVector().add(2d)).hasXY(2d);
    }

    @Test
    void addWithXAndY() {
        assertThatVector(new DoubleVector(1d).add(1d, 1d))
            .hasX(2d).hasY(2d);
    }

    @Test
    void addWithVector() {
        assertThatVector(
            new DoubleVector(1d, 0d).add(new DoubleVector(1d, 2d))
        ).hasX(2d).hasY(2d);
    }

    @Test
    void subtractWithXY() {
        assertThatVector(new DoubleVector(2d, 2d).subtract(2d)).hasZeroXY();
    }

    @Test
    void subtractWithXAndY() {
        assertThatVector(
            new DoubleVector(2d, 2d).subtract(1d, 1d)
        ).hasXY(1d);
    }

    @Test
    void subtractWithVector() {
        assertThatVector(
            new DoubleVector(2d, 2d).subtract(new DoubleVector(1d, 2d))
        ).hasX(1d).hasY(0d);
    }

    @Test
    void multiplyZeroVectorWith1() {
        assertThatVector(new DoubleVector().multiply(1d)).hasZeroXY();
    }

    @Test
    void multiplyVectorWith0() {
        assertThatVector(new DoubleVector(1d).multiply(0d)).hasZeroXY();
    }

    @Test
    void multiplyVectorWith1() {
        assertThatVector(new DoubleVector(1d).multiply(1d)).hasXY(1d);
    }

    @Test
    void multiplyVectorWithMinus1() {
        assertThatVector(
            new DoubleVector(2d, 1d).multiply(-1d)
        ).hasX(-2d).hasY(-1d);
    }

    // endregion

    // region inverse

    @Test
    void inverseXOfVectorWithX1Y2() {
        assertThatVector(new DoubleVector(1d, 2d).inverseX()).hasX(-1d).hasY(2d);
    }

    @Test
    void inverseYOfVectorWithX1Y2() {
        assertThatVector(new DoubleVector(1d, 2d).inverseY()).hasX(1d).hasY(-2d);
    }

    @Test
    void inverseXYOfVectorWithX1Y2() {
        assertThatVector(new DoubleVector(1d, 2d).inverse()).hasX(-1d).hasY(-2d);
    }

    // endregion

    // region absolute, normal and normalized

    @Test
    void absoluteOfPositiveXY() {
        assertThatVector(new DoubleVector(1d, 2d).absolute()).hasX(1d).hasY(2d);
    }

    @Test
    void absoluteOfNegativeXAndPositiveY() {
        assertThatVector(new DoubleVector(-1d, 2d).absolute()).hasX(1d).hasY(2d);
    }

    @Test
    void absoluteOfNegativeYAndPositiveX() {
        assertThatVector(new DoubleVector(-1d, -2d).absolute()).hasX(1d).hasY(2d);
    }

    @Test
    void absoluteOfNegativeXAndY() {
        assertThatVector(new DoubleVector(-1d, -2d).absolute()).hasX(1d).hasY(2d);
    }

    @Test
    void normalLeftOfVectorWithX1Y2() {
        assertThatVector(new DoubleVector(1d, 2d).normalLeft()).hasX(-2d).hasY(1d);
    }

    @Test
    void normalRightOfVectorWithX1Y2() {
        assertThatVector(new DoubleVector(1d, 2d).normalRight()).hasX(2d).hasY(-1d);
    }

    @Test
    void normalizedOfVectorWithoutParameter() {
        Vector<Double> vector = new DoubleVector().normalized();
        assertThatVector(vector).hasX(Double.NaN).hasY(Double.NaN);
        assertThatVector(vector).hasLength(Double.NaN);
    }

    @Test
    void normalizedOfVectorWithXY1() {
        Vector<Double> vector = new DoubleVector(1d).normalized();
        assertThatVector(vector).hasXY(Math.sqrt(2) / 2);
        assertThatVector(vector).hasLengthCloseTo(1d);
    }

    @Test
    void normalizedOfVectorWithX1Y2() {
        Vector<Double> vector = new DoubleVector(1d, 2d).normalized();
        assertThatVector(vector)
            .hasX(0.4472135954999579).hasY(0.8944271909999159);
        assertThatVector(vector).hasLengthCloseTo(1d);
    }

    // endregion

    // region scalarProduct and angle

    @Test
    void scalarProductOfVectorWithoutParameterAndXY1() {
        assertThat(new DoubleVector().scalarProduct(new DoubleVector(1d))).isZero();
    }

    @Test
    void scalarProductOfVectorWithXY1AndXY1() {
        assertThat(new DoubleVector(1d).scalarProduct(new DoubleVector(1d))).isEqualTo(2d);
    }

    @Test
    void scalarProductOfVectorWithXY2AndX1Y2() {
        assertThat(new DoubleVector(2d).scalarProduct(new DoubleVector(1d, 2d))).isEqualTo(6d);
    }

    @Test
    void angleBetweenX0Y1AndX1Y0() {
        assertThat(new DoubleVector(0d, 1d).angle(new DoubleVector(1d, 0d))).isEqualTo(Math.PI / 2d);
    }

    @Test
    void angleBetweenX0Y1AndXY1() {
        assertThat(new DoubleVector(0d, 1d)
            .angle(new DoubleVector(1d, 1d))
        ).isCloseTo(Math.PI / 4d, doubleOffset());
    }

    // endregion

    // region isZeroVector and asComplex

    @Test
    void isZeroVectorWithVectorWithoutParameter() {
        assertThatVector(new DoubleVector()).isZeroVector();
    }

    @Test
    void isZeroVectorWithVectorWithXY1() {
        assertThatVector(new DoubleVector(1d)).isNoZeroVector();
    }

    @Test
    void asComplexOfVectorWithX1Y2() {
        Complex<Double> complex = new DoubleComplex(1d, 2d);
        assertThatComplex(new DoubleVector(1d, 2d).asComplex()).isEqualTo(complex);
    }

    // endregion

    // region map, isValid rotate and copy

    @Test
    void mapOfVectorWithX0_5Y1_5() {
        DoubleVector vector = new DoubleVector(0.5, 1.5);
        Vector<Integer> result = new Vector<>(new IntegerArithmetic(), 0, 1);
        assertThatVector(vector.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThatVector(new DoubleVector()).isValid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatVector(
            new DoubleVector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        ).isInvalid();
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiHalf() {
        assertThatVector(new DoubleVector(1d, 2d).rotate(Math.PI / 2d))
            .hasX(-2d).hasY(1d);
    }

    @Test
    void rotateOfVectorWithX1Y2WithPi() {
        assertThatVector(new DoubleVector(1d, 2d).rotate(Math.PI))
            .hasX(-1d).hasY(-2d);
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiThreeHalf() {
        assertThatVector(new DoubleVector(1d, 2d).rotate(Math.PI * 3d / 2d))
            .hasX(2d).hasY(-1d);
    }

    @Test
    void rotateOfVectorWithX1Y2WithTwoPi() {
        assertThatVector(new DoubleVector(1d, 2d).rotate(Math.PI * 2d))
            .hasX(1d).hasY(2d);
    }

    @Test
    void copyOfVectorWithX2Y3() {
        assertCopyable(new DoubleVector(2d, 3d));
    }

    // endregion

    // region override

    @Test
    void equalsOfVectorWithX2Y3() {
        assertThatVector(new DoubleVector(2d, 3d))
            .isEqualTo(new DoubleVector(2d, 3d))
            .isNotEqualTo(new DoubleVector(3d, 2d));
    }

    @Test
    void hashCodeOfVectorWithX2Y3() {
        assertThat(new DoubleVector(2d, 3d).hashCode()).isEqualTo(525249);
    }

    @Test
    void toStringOfVectorWithX2Y3() {
        Vector<Double> vector = new DoubleVector(2d, 3d);
        assertThatVector(vector).hasToString("2.0:3.0");
    }

    @Test
    void compareToOfVectorWithX2Y3() {
        Vector<Double> vector = new DoubleVector(2d, 3d);
        assertThatVector(vector)
            .isEqualByComparingTo(new DoubleVector(2d, 3d))
            .isLessThan(new DoubleVector(3d, 1d))
            .isGreaterThan(new DoubleVector(1d, 0d));
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleVector(), DoubleVector.class);
    }

    // endregion
}
