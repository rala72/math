package io.rala.math.geometry.typed;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.algebra.numeric.typed.DoubleComplex;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import org.junit.jupiter.api.Test;

import static io.rala.math.testUtils.assertion.GeometryAssertions.assertVector;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class DoubleVectorTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertVector(new DoubleVector());
    }

    @Test
    void constructorWithXYParameter() {
        assertVector(new DoubleVector(1d), 1d);
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertVector(new DoubleVector(2d, 2d), 2d);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertVector(new DoubleVector(2d, 3d), 2d, 3d);
    }

    @Test
    void createAndSetX() {
        Vector<Double> vector = new DoubleVector();
        assertVector(vector);
        vector.setX(1d);
        assertVector(vector, 1d, 0d);
    }

    @Test
    void createAndSetY() {
        Vector<Double> vector = new DoubleVector();
        assertVector(vector);
        vector.setY(2d);
        assertVector(vector, 0d, 2d);
    }

    @Test
    void createAndSetXY() {
        Vector<Double> vector = new DoubleVector();
        assertVector(vector);
        vector.setXY(3d);
        assertVector(vector, 3d);
    }

    // endregion

    // region length, add, subtract and multiply

    @Test
    void lengthOfVectorWithoutParameter() {
        assertThat(new DoubleVector().length()).isEqualTo(0d);
    }

    @Test
    void lengthOfVectorXY1() {
        assertThat(new DoubleVector(1d).length()).isEqualTo(Math.sqrt(2d));
    }

    @Test
    void lengthOfVectorX1Y0() {
        assertThat(new DoubleVector(1d, 0d).length()).isEqualTo(1d);
    }

    @Test
    void addWithXY() {
        assertVector(new DoubleVector().add(2d), 2d, 2d);
    }

    @Test
    void addWithXAndY() {
        assertVector(
            new DoubleVector(1d).add(1d, 1d),
            2d, 2d
        );
    }

    @Test
    void addWithVector() {
        assertVector(
            new DoubleVector(1d, 0d).add(new DoubleVector(1d, 2d)),
            2d, 2d
        );
    }

    @Test
    void subtractWithXY() {
        assertVector(new DoubleVector(2d, 2d).subtract(2d));
    }

    @Test
    void subtractWithXAndY() {
        assertVector(
            new DoubleVector(2d, 2d).subtract(1d, 1d),
            1d
        );
    }

    @Test
    void subtractWithVector() {
        assertVector(
            new DoubleVector(2d, 2d).subtract(new DoubleVector(1d, 2d)),
            1d, 0d
        );
    }

    @Test
    void multiplyZeroVectorWith1() {
        assertVector(new DoubleVector().multiply(1d));
    }

    @Test
    void multiplyVectorWith0() {
        assertVector(new DoubleVector(1d).multiply(0d));
    }

    @Test
    void multiplyVectorWith1() {
        assertVector(new DoubleVector(1d).multiply(1d), 1d);
    }

    @Test
    void multiplyVectorWithMinus1() {
        assertVector(
            new DoubleVector(2d, 1d).multiply(-1d),
            -2d, -1d
        );
    }

    // endregion

    // region inverse

    @Test
    void inverseXOfVectorWithX1Y2() {
        assertVector(new DoubleVector(1d, 2d).inverseX(), -1d, 2d);
    }

    @Test
    void inverseYOfVectorWithX1Y2() {
        assertVector(new DoubleVector(1d, 2d).inverseY(), 1d, -2d);
    }

    @Test
    void inverseXYOfVectorWithX1Y2() {
        assertVector(new DoubleVector(1d, 2d).inverse(), -1d, -2d);
    }

    // endregion

    // region absolute, normal and normalized

    @Test
    void absoluteOfPositiveXY() {
        assertVector(new DoubleVector(1d, 2d).absolute(), 1d, 2d);
    }

    @Test
    void absoluteOfNegativeXAndPositiveY() {
        assertVector(new DoubleVector(-1d, 2d).absolute(), 1d, 2d);
    }

    @Test
    void absoluteOfNegativeYAndPositiveX() {
        assertVector(new DoubleVector(-1d, -2d).absolute(), 1d, 2d);
    }

    @Test
    void absoluteOfNegativeXAndY() {
        assertVector(new DoubleVector(-1d, -2d).absolute(), 1d, 2d);
    }

    @Test
    void normalLeftOfVectorWithX1Y2() {
        assertVector(new DoubleVector(1d, 2d).normalLeft(), -2d, 1d);
    }

    @Test
    void normalRightOfVectorWithX1Y2() {
        assertVector(new DoubleVector(1d, 2d).normalRight(), 2d, -1d);
    }

    @Test
    void normalizedOfVectorWithoutParameter() {
        Vector<Double> vector = new DoubleVector().normalized();
        assertVector(vector, Double.NaN, Double.NaN);
        assertThat(vector.length()).isNaN();
    }

    @Test
    void normalizedOfVectorWithXY1() {
        Vector<Double> vector = new DoubleVector(1d).normalized();
        assertVector(vector, 0.7071067811865475);
        assertThat(vector.length()).isCloseTo(1d, offset(GeometryAssertions.DELTA));
    }

    @Test
    void normalizedOfVectorWithX1Y2() {
        Vector<Double> vector = new DoubleVector(1d, 2d).normalized();
        assertVector(vector,
            0.4472135954999579, 0.8944271909999159
        );
        assertThat(vector.length()).isCloseTo(1d, offset(GeometryAssertions.DELTA));
    }

    // endregion

    // region scalarProduct and angle

    @Test
    void scalarProductOfVectorWithoutParameterAndXY1() {
        assertThat(new DoubleVector().scalarProduct(new DoubleVector(1d))).isEqualTo(0d);
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
            .angle(new DoubleVector(1d, 1d))).isCloseTo(Math.PI / 4d, offset(GeometryAssertions.DELTA));
    }

    // endregion

    // region isZeroVector and asComplex

    @Test
    void isZeroVectorWithVectorWithoutParameter() {
        assertThat(new DoubleVector().isZeroVector()).isTrue();
    }

    @Test
    void isZeroVectorWithVectorWithXY1() {
        assertThat(new DoubleVector(1d).isZeroVector()).isFalse();
    }

    @Test
    void asComplexOfVectorWithX1Y2() {
        Complex<Double> complex = new DoubleComplex(1d, 2d);
        assertThat(new DoubleVector(1d, 2d).asComplex()).isEqualTo(complex);
    }

    // endregion

    // region map, isValid rotate and copy

    @Test
    void mapOfVectorWithX0_5Y1_5() {
        DoubleVector vector = new DoubleVector(0.5, 1.5);
        Vector<Integer> result = new Vector<>(new IntegerArithmetic(), 0, 1);
        assertThat(vector.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new DoubleVector().isValid()).isTrue();
    }

    @Test
    void isValidWithInfValues() {
        assertThat(new DoubleVector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
            .isValid()).isFalse();
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiHalf() {
        assertVector(
            new DoubleVector(1d, 2d).rotate(Math.PI / 2d),
            -2d, 1d
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithPi() {
        assertVector(
            new DoubleVector(1d, 2d).rotate(Math.PI),
            -1d, -2d
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiThreeHalf() {
        assertVector(
            new DoubleVector(1d, 2d).rotate(Math.PI * 3d / 2d),
            2d, -1d
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithTwoPi() {
        assertVector(
            new DoubleVector(1d, 2d).rotate(Math.PI * 2d),
            1d, 2d
        );
    }

    @Test
    void copyOfVectorWithX2Y3() {
        Vector<Double> vector = new DoubleVector(2d, 3d);
        assertThat(vector.copy()).isEqualTo(vector);
    }

    // endregion

    // region override

    @Test
    void equalsOfVectorWithX2Y3() {
        Vector<Double> vector = new DoubleVector(2d, 3d);
        assertThat(new DoubleVector(2d, 3d)).isEqualTo(vector);
        assertThat(new DoubleVector(3d, 2d)).isNotEqualTo(vector);
    }

    @Test
    void hashCodeOfVectorWithX2Y3() {
        assertThat(new DoubleVector(2d, 3d).hashCode()).isEqualTo(525249);
    }

    @Test
    void toStringOfVectorWithX2Y3() {
        Vector<Double> vector = new DoubleVector(2d, 3d);
        assertThat(vector).hasToString("2.0:3.0");
    }

    @Test
    void compareToOfVectorWithX2Y3() {
        Vector<Double> vector = new DoubleVector(2d, 3d);
        assertThat(vector)
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
