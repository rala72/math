package io.rala.math.geometry.typed;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.algebra.numeric.typed.BigDecimalComplex;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatComplex;
import static io.rala.math.testUtils.assertion.GeometryAssertions.CONTEXT;
import static io.rala.math.testUtils.assertion.GeometryAssertions.assertThatVector;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static io.rala.math.testUtils.assertion.utils.OffsetUtils.bigDecimalOffset;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BigDecimalVectorTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertThatVector(new BigDecimalVector()).hasZeroXY();
    }

    @Test
    void constructorWithMathContext5() {
        assertThatVector(new BigDecimalVector(new MathContext(5))).hasZeroXY();
    }

    @Test
    void constructorWithXYParameter() {
        assertThatVector(new BigDecimalVector(BigDecimal.ONE))
            .hasXY(BigDecimal.ONE);
    }

    @Test
    void constructorWithXYParameterAndMathContext5() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE, new MathContext(5))
        ).hasXY(BigDecimal.ONE);
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
        ).hasXY(BigDecimal.valueOf(2));
    }

    @Test
    void constructorWithEqualXYParameterAndMathContext5() {
        assertThatVector(
            new BigDecimalVector(
                BigDecimal.valueOf(2), BigDecimal.valueOf(2),
                new MathContext(5)
            )
        ).hasXY(BigDecimal.valueOf(2));
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertThatVector(
            new BigDecimalVector(
                BigDecimal.valueOf(2), BigDecimal.valueOf(3)
            )
        ).hasX(BigDecimal.valueOf(2)).hasY(BigDecimal.valueOf(3));
    }

    @Test
    void constructorWithDifferentXYParameterAndMathContext5() {
        assertThatVector(
            new BigDecimalVector(
                BigDecimal.valueOf(2), BigDecimal.valueOf(3),
                new MathContext(5)
            )
        ).hasX(BigDecimal.valueOf(2)).hasY(BigDecimal.valueOf(3));
    }

    @Test
    void createAndSetX() {
        Vector<BigDecimal> vector = new BigDecimalVector();
        assertThatVector(vector).hasZeroXY();
        vector.setX(BigDecimal.ONE);
        assertThatVector(vector).hasX(BigDecimal.ONE).hasY(BigDecimal.ZERO);
    }

    @Test
    void createAndSetY() {
        Vector<BigDecimal> vector = new BigDecimalVector();
        assertThatVector(vector).hasZeroXY();
        vector.setY(BigDecimal.valueOf(2));
        assertThatVector(vector).hasX(BigDecimal.ZERO).hasY(BigDecimal.valueOf(2));
    }

    @Test
    void createAndSetXY() {
        Vector<BigDecimal> vector = new BigDecimalVector();
        assertThatVector(vector).hasZeroXY();
        vector.setXY(BigDecimal.valueOf(3));
        assertThatVector(vector).hasXY(BigDecimal.valueOf(3));
    }

    // endregion

    // region length, add, subtract and multiply

    @Test
    void lengthOfVectorWithoutParameter() {
        assertThatVector(new BigDecimalVector()).hasLength(BigDecimal.ZERO);
    }

    @Test
    void lengthOfVectorXY1() {
        assertThatVector(new BigDecimalVector(BigDecimal.ONE))
            .hasLength(BigDecimal.valueOf(Math.sqrt(2d)).round(CONTEXT));
    }

    @Test
    void lengthOfVectorX1Y0() {
        assertThatVector(new BigDecimalVector(BigDecimal.ONE, BigDecimal.ZERO))
            .hasLength(BigDecimal.ONE);
    }

    @Test
    void addWithXY() {
        assertThatVector(
            new BigDecimalVector().add(BigDecimal.valueOf(2))
        ).hasXY(BigDecimal.valueOf(2));
    }

    @Test
    void addWithXAndY() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE).add(BigDecimal.ONE, BigDecimal.ONE)
        ).hasX(BigDecimal.valueOf(2)).hasY(BigDecimal.valueOf(2));
    }

    @Test
    void addWithVector() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.ZERO)
                .add(new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2)))
        ).hasX(BigDecimal.valueOf(2)).hasY(BigDecimal.valueOf(2));
    }

    @Test
    void subtractWithXY() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
                .subtract(BigDecimal.valueOf(2))
        ).hasZeroXY();
    }

    @Test
    void subtractWithXAndY() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
                .subtract(BigDecimal.ONE, BigDecimal.ONE)
        ).hasXY(BigDecimal.ONE);
    }

    @Test
    void subtractWithVector() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
                .subtract(new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2)))
        ).hasX(BigDecimal.ONE).hasY(BigDecimal.ZERO);
    }

    @Test
    void multiplyZeroVectorWith1() {
        assertThatVector(new BigDecimalVector().multiply(BigDecimal.ONE)).hasZeroXY();
    }

    @Test
    void multiplyVectorWith0() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE)
                .multiply(BigDecimal.ZERO)
        ).hasZeroXY();
    }

    @Test
    void multiplyVectorWith1() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE)
                .multiply(BigDecimal.ONE)
        ).hasXY(BigDecimal.ONE);
    }

    @Test
    void multiplyVectorWithMinus1() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.valueOf(2), BigDecimal.ONE)
                .multiply(BigDecimal.ONE.negate())
        ).hasX(BigDecimal.valueOf(-2d)).hasY(BigDecimal.ONE.negate());
    }

    // endregion

    // region inverse

    @Test
    void inverseXOfVectorWithX1Y2() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2)).inverseX()
        ).hasX(BigDecimal.ONE.negate()).hasY(BigDecimal.valueOf(2));
    }

    @Test
    void inverseYOfVectorWithX1Y2() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2)).inverseY()
        ).hasX(BigDecimal.ONE).hasY(BigDecimal.valueOf(-2d));
    }

    @Test
    void inverseXYOfVectorWithX1Y2() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2)).inverse()
        ).hasX(BigDecimal.ONE.negate()).hasY(BigDecimal.valueOf(-2d));
    }

    // endregion

    // region absolute, normal and normalized

    @Test
    void absoluteOfPositiveXY() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2)).absolute()
        ).hasX(BigDecimal.ONE).hasY(BigDecimal.valueOf(2));
    }

    @Test
    void absoluteOfNegativeXAndPositiveY() {
        assertThatVector(
            new BigDecimalVector(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(2)
            ).absolute()
        ).hasX(BigDecimal.ONE).hasY(BigDecimal.valueOf(2));
    }

    @Test
    void absoluteOfNegativeYAndPositiveX() {
        assertThatVector(
            new BigDecimalVector(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(-2d)
            ).absolute()
        ).hasX(BigDecimal.ONE).hasY(BigDecimal.valueOf(2));
    }

    @Test
    void absoluteOfNegativeXAndY() {
        assertThatVector(
            new BigDecimalVector(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(-2d)
            ).absolute()
        ).hasX(BigDecimal.ONE).hasY(BigDecimal.valueOf(2));
    }

    @Test
    void normalLeftOfVectorWithX1Y2() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2)).normalLeft()
        ).hasX(BigDecimal.valueOf(-2d)).hasY(BigDecimal.ONE);
    }

    @Test
    void normalRightOfVectorWithX1Y2() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2)).normalRight()
        ).hasX(BigDecimal.valueOf(2)).hasY(BigDecimal.ONE.negate());
    }

    @Test
    void normalizedOfVectorWithoutParameter() {
        assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> new BigDecimalVector().normalized())
            .withMessage(ExceptionMessages.DIVISION_UNDEFINED);
    }

    @Test
    void normalizedOfVectorWithXY1() {
        Vector<BigDecimal> vector = new BigDecimalVector(BigDecimal.ONE).normalized();
        assertThatVector(vector).hasXY(BigDecimal.valueOf(Math.sqrt(2) / 2));
        assertThatVector(vector).hasLength(BigDecimal.ONE);
    }

    @Test
    void normalizedOfVectorWithX1Y2() {
        Vector<BigDecimal> vector = new BigDecimalVector(
            BigDecimal.ONE, BigDecimal.valueOf(2)
        ).normalized();
        assertThatVector(vector)
            .hasX(BigDecimal.valueOf(0.4472135954999579))
            .hasY(BigDecimal.valueOf(0.8944271909999159));
        assertThatVector(vector).hasLengthCloseTo(BigDecimal.ONE);
    }

    // endregion

    // region scalarProduct and angle

    @Test
    void scalarProductOfVectorWithoutParameterAndXY1() {
        assertThat(new BigDecimalVector()
            .scalarProduct(new BigDecimalVector(BigDecimal.ONE))
        ).isZero();
    }

    @Test
    void scalarProductOfVectorWithXY1AndXY1() {
        assertThat(new BigDecimalVector(BigDecimal.ONE)
            .scalarProduct(new BigDecimalVector(BigDecimal.ONE))
        ).isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void scalarProductOfVectorWithXY2AndX1Y2() {
        assertThat(new BigDecimalVector(BigDecimal.valueOf(2))
            .scalarProduct(new BigDecimalVector(
                BigDecimal.ONE,
                BigDecimal.valueOf(2)
            ))
        ).isEqualTo(BigDecimal.valueOf(6));
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
        ).isCloseTo(BigDecimal.valueOf(Math.PI / 4), bigDecimalOffset());
    }

    // endregion

    // region isZeroVector and asComplex

    @Test
    void isZeroVectorWithVectorWithoutParameter() {
        assertThatVector(new BigDecimalVector()).isZeroVector();
    }

    @Test
    void isZeroVectorWithVectorWithXY1() {
        assertThatVector(new BigDecimalVector(BigDecimal.ONE)).isNoZeroVector();
    }

    @Test
    void asComplexOfVectorWithX1Y2() {
        Complex<BigDecimal> complex = new BigDecimalComplex(
            BigDecimal.ONE, BigDecimal.valueOf(2)
        );
        assertThatComplex(new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2)).asComplex())
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
        assertThatVector(vector.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZeroValues() {
        assertThatVector(new BigDecimalVector()).isValid();
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiHalf() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2))
                .rotate(BigDecimal.valueOf(Math.PI / 2d))
        ).hasX(BigDecimal.valueOf(-2d)).hasY(BigDecimal.ONE);
    }

    @Test
    void rotateOfVectorWithX1Y2WithPi() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2))
                .rotate(BigDecimal.valueOf(Math.PI))
        ).hasX(BigDecimal.ONE.negate()).hasY(BigDecimal.valueOf(-2d));
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiThreeHalf() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2))
                .rotate(BigDecimal.valueOf(Math.PI * 3d / 2d))
        ).hasX(BigDecimal.valueOf(2)).hasY(BigDecimal.ONE.negate());
    }

    @Test
    void rotateOfVectorWithX1Y2WithTwoPi() {
        assertThatVector(
            new BigDecimalVector(BigDecimal.ONE, BigDecimal.valueOf(2))
                .rotate(BigDecimal.valueOf(Math.PI * 2d))
        ).hasX(BigDecimal.ONE).hasY(BigDecimal.valueOf(2));
    }

    @Test
    void copyOfVectorWithX2Y3() {
        assertCopyable(new BigDecimalVector(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        ));
    }

    // endregion

    // region override

    @Test
    void equalsOfVectorWithX2Y3() {
        assertThatVector(new BigDecimalVector(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
            .isEqualTo(new BigDecimalVector(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
            .isNotEqualTo(new BigDecimalVector(BigDecimal.valueOf(3), BigDecimal.valueOf(2)));
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
        assertThatVector(vector).hasToString("2.0:3.0");
    }

    @Test
    void compareToOfVectorWithX2Y3() {
        Vector<BigDecimal> vector = new BigDecimalVector(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        );
        assertThatVector(vector)
            .isEqualByComparingTo(new BigDecimalVector(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
            .isLessThan(new BigDecimalVector(BigDecimal.valueOf(3), BigDecimal.ONE))
            .isGreaterThan(new BigDecimalVector(BigDecimal.ONE, BigDecimal.ZERO));
    }

    @Test
    void serializable() {
        assertSerializable(new BigDecimalVector(), BigDecimalVector.class);
    }

    // endregion
}
