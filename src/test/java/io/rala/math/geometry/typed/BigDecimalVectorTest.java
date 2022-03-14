package io.rala.math.geometry.typed;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.algebra.numeric.typed.BigDecimalComplex;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.GeometryAssertions.CONTEXT;
import static io.rala.math.testUtils.assertion.GeometryAssertions.assertVector;
import static io.rala.math.testUtils.assertion.OffsetUtils.doubleOffset;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BigDecimalVectorTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertVector(new BigDecimalVector());
    }

    @Test
    void constructorWithMathContext5() {
        assertVector(new BigDecimalVector(new MathContext(5)));
    }

    @Test
    void constructorWithXYParameter() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE), BigDecimal.ONE
        );
    }

    @Test
    void constructorWithXYParameterAndMathContext5() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE, new MathContext(5)),
            BigDecimal.ONE
        );
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertVector(
            new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithEqualXYParameterAndMathContext5() {
        assertVector(
            new BigDecimalVector(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(2d),
                new MathContext(5)
            ),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertVector(
            new BigDecimalVector(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
            ),
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
    }

    @Test
    void constructorWithDifferentXYParameterAndMathContext5() {
        assertVector(
            new BigDecimalVector(
                BigDecimal.valueOf(2d), BigDecimal.valueOf(3d),
                new MathContext(5)
            ),
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
    }

    @Test
    void createAndSetX() {
        Vector<BigDecimal> vector = new BigDecimalVector();
        assertVector(vector);
        vector.setX(BigDecimal.ONE);
        assertVector(vector, BigDecimal.ONE, BigDecimal.ZERO);
    }

    @Test
    void createAndSetY() {
        Vector<BigDecimal> vector = new BigDecimalVector();
        assertVector(vector);
        vector.setY(BigDecimal.valueOf(2d));
        assertVector(vector, BigDecimal.ZERO, BigDecimal.valueOf(2d));
    }

    @Test
    void createAndSetXY() {
        Vector<BigDecimal> vector = new BigDecimalVector();
        assertVector(vector);
        vector.setXY(BigDecimal.valueOf(3d));
        assertVector(vector, BigDecimal.valueOf(3d));
    }

    // endregion

    // region length, add, subtract and multiply

    @Test
    void lengthOfVectorWithoutParameter() {
        assertThat(new BigDecimalVector().length()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void lengthOfVectorXY1() {
        assertThat(new BigDecimalVector(BigDecimal.ONE).length())
            .isEqualTo(BigDecimal.valueOf(Math.sqrt(2d)).round(CONTEXT));
    }

    @Test
    void lengthOfVectorX1Y0() {
        assertThat(new BigDecimalVector(BigDecimal.ONE, BigDecimal.ZERO).length()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void addWithXY() {
        assertVector(
            new BigDecimalVector().add(BigDecimal.valueOf(2d)),
            BigDecimal.valueOf(2d),
            BigDecimal.valueOf(2d)
        );
    }

    @Test
    void addWithXAndY() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE).add(BigDecimal.ONE, BigDecimal.ONE),
            BigDecimal.valueOf(2d), BigDecimal.valueOf(2d)
        );
    }

    @Test
    void addWithVector() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.ZERO)
                .add(new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d))),
            BigDecimal.valueOf(2d), BigDecimal.valueOf(2d)
        );
    }

    @Test
    void subtractWithXY() {
        assertVector(
            new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.valueOf(2d))
                .subtract(BigDecimal.valueOf(2d))
        );
    }

    @Test
    void subtractWithXAndY() {
        assertVector(
            new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.valueOf(2d))
                .subtract(BigDecimal.ONE, BigDecimal.ONE),
            BigDecimal.ONE
        );
    }

    @Test
    void subtractWithVector() {
        assertVector(
            new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.valueOf(2d))
                .subtract(new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d))),
            BigDecimal.ONE, BigDecimal.ZERO
        );
    }

    @Test
    void multiplyZeroVectorWith1() {
        assertVector(new BigDecimalVector().multiply(BigDecimal.ONE));
    }

    @Test
    void multiplyVectorWith0() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE)
                .multiply(BigDecimal.ZERO)
        );
    }

    @Test
    void multiplyVectorWith1() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE)
                .multiply(BigDecimal.ONE),
            BigDecimal.ONE
        );
    }

    @Test
    void multiplyVectorWithMinus1() {
        assertVector(
            new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.ONE)
                .multiply(BigDecimal.ONE.negate()),
            BigDecimal.valueOf(-2d), BigDecimal.ONE.negate()
        );
    }

    // endregion

    // region inverse

    @Test
    void inverseXOfVectorWithX1Y2() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).inverseX(),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(2d)
        );
    }

    @Test
    void inverseYOfVectorWithX1Y2() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).inverseY(),
            BigDecimal.ONE, BigDecimal.valueOf(-2d)
        );
    }

    @Test
    void inverseXYOfVectorWithX1Y2() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).inverse(),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(-2d)
        );
    }

    // endregion

    // region absolute, normal and normalized

    @Test
    void absoluteOfPositiveXY() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).absolute(),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void absoluteOfNegativeXAndPositiveY() {
        assertVector(
            new BigDecimalVector(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(2d)
            ).absolute(),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void absoluteOfNegativeYAndPositiveX() {
        assertVector(
            new BigDecimalVector(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(-2d)
            ).absolute(),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void absoluteOfNegativeXAndY() {
        assertVector(
            new BigDecimalVector(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(-2d)
            ).absolute(),
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
    }

    @Test
    void normalLeftOfVectorWithX1Y2() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).normalLeft(),
            BigDecimal.valueOf(-2d), BigDecimal.ONE
        );
    }

    @Test
    void normalRightOfVectorWithX1Y2() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).normalRight(),
            BigDecimal.valueOf(2d), BigDecimal.ONE.negate()
        );
    }

    @Test
    void normalizedOfVectorWithoutParameter() {
        assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> new BigDecimalVector().normalized()); // assert exception message?
    }

    @Test
    void normalizedOfVectorWithXY1() {
        Vector<BigDecimal> vector = new BigDecimalVector(BigDecimal.ONE).normalized();
        assertVector(vector, BigDecimal.valueOf(0.7071067811865475));
        assertThat(vector.length().doubleValue())
            .isCloseTo(1.000000001, doubleOffset());
    }

    @Test
    void normalizedOfVectorWithX1Y2() {
        Vector<BigDecimal> vector = new BigDecimalVector(
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        ).normalized();
        assertVector(vector,
            BigDecimal.valueOf(0.4472135954999579),
            BigDecimal.valueOf(0.8944271909999159)
        );
        assertThat(vector.length().doubleValue()).isCloseTo(1.000000001, doubleOffset());
    }

    // endregion

    // region scalarProduct and angle

    @Test
    void scalarProductOfVectorWithoutParameterAndXY1() {
        assertThat(new BigDecimalVector()
            .scalarProduct(new BigDecimalVector(BigDecimal.ONE))).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void scalarProductOfVectorWithXY1AndXY1() {
        assertThat(new BigDecimalVector(BigDecimal.ONE)
            .scalarProduct(new BigDecimalVector(BigDecimal.ONE))).isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void scalarProductOfVectorWithXY2AndX1Y2() {
        assertThat(new BigDecimalVector(BigDecimal.valueOf(2d))
            .scalarProduct(new BigDecimalVector(
                BigDecimal.ONE,
                BigDecimal.valueOf(2d)
            ))).isEqualTo(BigDecimal.valueOf(6));
    }

    @Test
    void angleBetweenX0Y1AndX1Y0() {
        assertThat(new BigDecimalVector(BigDecimal.ZERO, BigDecimal.ONE)
            .angle(new BigDecimalVector(BigDecimal.ONE, BigDecimal.ZERO)))
            .isEqualTo(BigDecimal.valueOf(Math.PI / 2d).round(CONTEXT));
    }

    @Test
    void angleBetweenX0Y1AndXY1() {
        assertThat(new BigDecimalVector(BigDecimal.ZERO, BigDecimal.ONE)
            .angle(new BigDecimalVector(BigDecimal.ONE, BigDecimal.ONE))
        ).isEqualTo(BigDecimal.valueOf(0.7853981633974484));
    }

    // endregion

    // region isZeroVector and asComplex

    @Test
    void isZeroVectorWithVectorWithoutParameter() {
        assertThat(new BigDecimalVector().isZeroVector()).isTrue();
    }

    @Test
    void isZeroVectorWithVectorWithXY1() {
        assertThat(new BigDecimalVector(BigDecimal.ONE).isZeroVector()).isFalse();
    }

    @Test
    void asComplexOfVectorWithX1Y2() {
        Complex<BigDecimal> complex = new BigDecimalComplex(
            BigDecimal.ONE, BigDecimal.valueOf(2d)
        );
        assertThat(new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d)).asComplex())
            .isEqualTo(complex);
    }

    // endregion

    // region map, isValid rotate and copy

    @Test
    void mapOfVectorWithX0_5Y1_5() {
        BigDecimalVector vector = new BigDecimalVector(
            BigDecimal.valueOf(0.5), BigDecimal.valueOf(1.5)
        );
        Vector<Integer> result = new Vector<>(new IntegerArithmetic(), 0, 1);
        assertThat(vector.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThat(new BigDecimalVector().isValid()).isTrue();
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiHalf() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI / 2d)),
            BigDecimal.valueOf(-2d), BigDecimal.ONE
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithPi() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI)),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(-2d)
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiThreeHalf() {
        assertVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2d))
                .rotate(BigDecimal.valueOf(Math.PI * 3d / 2d)),
            BigDecimal.valueOf(2d), BigDecimal.ONE.negate()
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithTwoPi() {
        assertVector(
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
        assertThat(vector.copy()).isEqualTo(vector);
    }

    // endregion

    // region override

    @Test
    void equalsOfVectorWithX2Y3() {
        assertThat(new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)))
            .isEqualTo(new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)))
            .isNotEqualTo(new BigDecimalVector(BigDecimal.valueOf(3d), BigDecimal.valueOf(2d)));
    }

    @Test
    void hashCodeOfVectorWithX2Y3() {
        assertThat(new BigDecimalVector(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        ).hashCode()).isEqualTo(21143);
    }

    @Test
    void toStringOfVectorWithX2Y3() {
        Vector<BigDecimal> vector = new BigDecimalVector(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        assertThat(vector).hasToString("2.0:3.0");
    }

    @Test
    void compareToOfVectorWithX2Y3() {
        Vector<BigDecimal> vector = new BigDecimalVector(
            BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)
        );
        assertThat(vector)
            .isEqualByComparingTo(new BigDecimalVector(BigDecimal.valueOf(2d), BigDecimal.valueOf(3d)))
            .isLessThan(new BigDecimalVector(BigDecimal.valueOf(3d), BigDecimal.ONE))
            .isGreaterThan(new BigDecimalVector(BigDecimal.ONE, BigDecimal.ZERO));
    }

    @Test
    void serializable() {
        assertSerializable(new BigDecimalVector(), BigDecimalVector.class);
    }

    // endregion
}
