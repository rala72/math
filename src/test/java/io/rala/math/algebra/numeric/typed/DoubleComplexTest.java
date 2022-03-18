package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatComplex;
import static io.rala.math.testUtils.assertion.GeometryAssertions.assertThatVector;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class DoubleComplexTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertThatComplex(new DoubleComplex()).hasReWithZero().hasImWithZero();
    }

    @Test
    void constructorWithReParameter() {
        assertThatComplex(new DoubleComplex(2d)).hasRe(2d).hasImWithZero();
    }

    @Test
    void constructorWithDifferentReImParameter() {
        assertThatComplex(new DoubleComplex(2d, 3d))
            .hasRe(2d).hasIm(3d);
    }

    @Test
    void constructorWithComplex() {
        assertThatComplex(new DoubleComplex(new DoubleComplex(2d, 3d)))
            .hasRe(2d).hasIm(3d);
    }

    @Test
    void createAndSetRe() {
        Complex<Double> complex = new DoubleComplex();
        assertThatComplex(complex).hasReWithZero().hasImWithZero();
        complex.setRe(1d);
        assertThatComplex(complex).hasRe(1d).hasImWithZero();
    }

    @Test
    void createAndSetIm() {
        Complex<Double> complex = new DoubleComplex();
        assertThatComplex(complex).hasReWithZero().hasImWithZero();
        complex.setIm(2d);
        assertThatComplex(complex).hasReWithZero().hasIm(2d);
    }

    // endregion

    // region value

    @Test
    void intValueOfComplexWithX1_1Y2_2() {
        assertThat(new DoubleComplex(1.1, 2.2).intValue()).isOne();
    }

    @Test
    void longValueOfComplexWithX1_1Y2_2() {
        assertThat(new DoubleComplex(1.1, 2.2).longValue()).isOne();
    }

    @Test
    void floatValueOfComplexWithX1_1Y2_2() {
        assertThat(new DoubleComplex(1.1, 2.2).floatValue()).isEqualTo((float) 1.1);
    }

    @Test
    void doubleValueOfComplexWithX1_1Y2_2() {
        assertThat(new DoubleComplex(1.1, 2.2).doubleValue()).isEqualTo(1.1);
    }

    // endregion

    // region absoluteValue, argument, signum, conjugation and reciprocal

    @Test
    void absoluteValueOfComplexWithoutParameter() {
        assertThatComplex(new DoubleComplex()).hasAbsoluteValue(0d);
    }

    @Test
    void absoluteValueOfComplexX1Y1() {
        assertThatComplex(new DoubleComplex(1d, 1d)).hasAbsoluteValue(Math.sqrt(2d));
    }

    @Test
    void absoluteValueOfComplexX1Y0() {
        assertThatComplex(new DoubleComplex(1d, 0d)).hasAbsoluteValue(1d);
    }

    @Test
    void argumentOfComplexWithoutParameter() {
        assertThatComplex(new DoubleComplex()).hasArgument(Double.NaN);
    }

    @Test
    void argumentOfComplexX1Y1() {
        assertThatComplex(new DoubleComplex(1d, 1d)).hasArgumentCloseTo(Math.PI / 4);
    }

    @Test
    void argumentOfComplexX1Y0() {
        assertThatComplex(new DoubleComplex(1d, 0d)).hasArgument(0d);
    }

    @Test
    void signumOfComplexWithoutParameter() {
        assertThatComplex(new DoubleComplex().signum()).isEqualTo(new DoubleComplex());
    }

    @Test
    void signumOfComplexX1Y1() {
        double sqrt2half = Math.sqrt(2) / 2;
        assertThatComplex(new DoubleComplex(1d, 1d).signum())
            .hasRe(sqrt2half).hasIm(sqrt2half);
    }

    @Test
    void signumOfComplexX1Y0() {
        assertThatComplex(new DoubleComplex(1d, 0d).signum())
            .isEqualTo(new DoubleComplex(1d, 0d));
    }

    @Test
    void complexSignumOfComplexWithoutParameter() {
        assertThatComplex(new DoubleComplex()).hasComplexSignum(0);
    }

    @Test
    void complexSignumOfComplexX1Y1() {
        assertThatComplex(new DoubleComplex(1d, 1d)).hasComplexSignum(1);
    }

    @Test
    void complexSignumOfComplexXMinus1Y0() {
        assertThatComplex(new DoubleComplex(-1d, 0d)).hasComplexSignum(-1);
    }

    @Test
    void complexSignumOfComplexX0Y1() {
        assertThatComplex(new DoubleComplex(0d, 1d)).hasComplexSignum(1);
    }

    @Test
    void complexSignumOfComplexX0YMinus1() {
        assertThatComplex(new DoubleComplex(0d, -1d)).hasComplexSignum(-1);
    }

    @Test
    void conjugationOfComplexWithRe1Im2() {
        assertThatComplex(new DoubleComplex(1d, 2d).conjugation())
            .isEqualTo(new DoubleComplex(1d, -2d));
    }

    @Test
    void reciprocalOfComplexWithoutParameter() {
        assertThatComplex(new DoubleComplex().reciprocal())
            .isEqualTo(new DoubleComplex(Double.NaN, Double.NaN));
    }

    @Test
    void reciprocalOfComplexX1Y1() {
        assertThatComplex(new DoubleComplex(1d, 1d).reciprocal())
            .isEqualTo(new DoubleComplex(0.5, -0.5));
    }

    @Test
    void reciprocalOfComplexX1Y0() {
        assertThatComplex(new DoubleComplex(1d, 0d).reciprocal())
            .isEqualTo(new DoubleComplex(1d, -0d));
    }

    // endregion

    // region add and subtract

    @Test
    void addWithXY() {
        assertThatComplex(new DoubleComplex().add(2d, 2d))
            .isEqualTo(new DoubleComplex(2d, 2d));
    }

    @Test
    void addWithXAndY() {
        assertThatComplex(new DoubleComplex(1d, 1d).add(1d, 1d))
            .isEqualTo(new DoubleComplex(2d, 2d));
    }

    @Test
    void addWithDoubleComplex() {
        assertThatComplex(new DoubleComplex(1d, 0d).add(new DoubleComplex(1d, 2d)))
            .isEqualTo(new DoubleComplex(2d, 2d));
    }

    @Test
    void subtractWithXY() {
        assertThatComplex(new DoubleComplex(2d, 2d).subtract(2d, 2d))
            .isEqualTo(new DoubleComplex());
    }

    @Test
    void subtractWithXAndY() {
        assertThatComplex(new DoubleComplex(2d, 2d).subtract(1d, 1d))
            .isEqualTo(new DoubleComplex(1d, 1d));
    }

    @Test
    void subtractWithDoubleComplex() {
        assertThatComplex(new DoubleComplex(2d, 2d).subtract(new DoubleComplex(1d, 2d)))
            .isEqualTo(new DoubleComplex(1d, 0d));
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyZeroComplexWith1() {
        assertThatComplex(new DoubleComplex().multiply(1d)).isEqualTo(new DoubleComplex());
    }

    @Test
    void multiplyComplexWith0() {
        assertThatComplex(new DoubleComplex(1d, 1d).multiply(0d))
            .isEqualTo(new DoubleComplex());
    }

    @Test
    void multiplyComplexWith1() {
        assertThatComplex(new DoubleComplex(1d, 1d).multiply(1d))
            .isEqualTo(new DoubleComplex(1d, 1d));
    }

    @Test
    void multiplyComplexWithMinus1() {
        assertThatComplex(new DoubleComplex(2d, 1d).multiply(-1d))
            .isEqualTo(new DoubleComplex(-2d, -1d));
    }

    @Test
    void multiplyComplexWithDoubleComplex() {
        assertThatComplex(new DoubleComplex(1d, 2d).multiply(new DoubleComplex(2d, 1d)))
            .isEqualTo(new DoubleComplex(0d, 5d));
    }

    @Test
    void divideZeroComplexWith1() {
        assertThatComplex(new DoubleComplex().divide(1d)).isEqualTo(new DoubleComplex());
    }

    @Test
    void divideComplexWith0() {
        assertThatComplex(new DoubleComplex(1d, 1d).divide(0d)).isEqualTo(new DoubleComplex(
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY
        ));
    }

    @Test
    void divideComplexWith1() {
        assertThatComplex(new DoubleComplex(1d, 1d).divide(1d))
            .isEqualTo(new DoubleComplex(1d, 1d));
    }

    @Test
    void divideComplexWithMinus1() {
        assertThatComplex(new DoubleComplex(2d, 1d).divide(-1d))
            .isEqualTo(new DoubleComplex(-2d, -1d));
    }

    @Test
    void divideComplexWithDoubleComplex() {
        assertThatComplex(new DoubleComplex(1d, 2d).divide(new DoubleComplex(2d, 1d)))
            .isEqualTo(new DoubleComplex(0.8, 0.6));
    }

    // endregion

    // region pow and root

    @Test
    void pow8OfComplexWithRe1Im1() {
        assertThatComplex(new DoubleComplex(1d, 1d).pow(8))
            .hasRe(16d).hasIm(1.0291984957930479e-14);
    }

    @Test
    void pow5OfComplexWithRe3Im4() {
        assertThatComplex(new DoubleComplex(3d, 4d).pow(5))
            .hasRe(-237d).hasIm(-3116d);
    }

    @Test
    void root3OfComplexWithRe1Im0() {
        assertThat(new DoubleComplex(1d, 0d).root(3)).isEqualTo(List.of(
            new DoubleComplex(1d, 0d),
            new DoubleComplex(-0.4999999999999998, 0.8660254037844387),
            new DoubleComplex(-0.5000000000000004, -0.8660254037844384)
        ));
    }

    @Test
    void root4OfComplexWithRe1Im0() {
        assertThat(new DoubleComplex(1d, 0d).root(4)).isEqualTo(List.of(
            new DoubleComplex(1d, 0d),
            new DoubleComplex(6.123233995736766e-17, 1d),
            new DoubleComplex(-1d, 1.2246467991473532e-16),
            new DoubleComplex(-1.8369701987210297e-16, -1d)
        ));
    }

    @Test
    void root2OfComplexWithReMinus1ImSqrt3() {
        assertThat(new DoubleComplex(-1d, Math.sqrt(3)).root(2)).isEqualTo(List.of(
            new DoubleComplex(0.7071067811865474, 1.224744871391589),
            new DoubleComplex(-0.7071067811865469, -1.2247448713915892)
        ));
    }

    // endregion

    // region inverse

    @Test
    void inverseReOfComplexWithRe1Im2() {
        assertThatComplex(new DoubleComplex(1d, 2d).inverseRe())
            .isEqualTo(new DoubleComplex(-1d, 2d));
    }

    @Test
    void inverseImOfComplexWithRe1Im2() {
        assertThatComplex(new DoubleComplex(1d, 2d).inverseIm())
            .isEqualTo(new DoubleComplex(1d, -2d));
    }

    @Test
    void inverseReImOfComplexWithRe1Im2() {
        assertThatComplex(new DoubleComplex(1d, 2d).inverse())
            .isEqualTo(new DoubleComplex(-1d, -2d));
    }

    // endregion

    // region static of and asVector

    @Test
    void ofAb3AndAr50() {
        assertThatComplex(
            DoubleComplex.of(3d, 50d)
        ).hasRe(2.89489808547634).hasIm(-0.7871245611117863);
    }

    @Test
    void ofWithSelfValidation() {
        Complex<Double> complex = DoubleComplex.of(1d, 2d);
        assertThatComplex(complex).hasAbsoluteValue(1d).hasArgument(2d);
    }

    @Test
    void asVectorOfComplexWithRe1Im2() {
        assertThatVector(new DoubleComplex(1d, 2d).asVector())
            .isEqualTo(new Vector<>(new DoubleArithmetic(), 1d, 2d));
    }

    // endregion

    // region map, isValid and copy

    @Test
    void mapOfComplexWithR0_5Im1_5() {
        DoubleComplex complex = new DoubleComplex(0.5, 1.5);
        Complex<Integer> result =
            new Complex<>(new IntegerArithmetic(), 0, 1);
        assertThatComplex(complex.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZero() {
        assertThatComplex(new DoubleComplex()).isValid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatComplex(new DoubleComplex(
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY
        )).isInvalid();
    }

    @Test
    void copyOfComplexWithReIm() {
        assertCopyable(new DoubleComplex(2d, 3d));
    }

    // endregion

    // region override

    @Test
    void equalsOfComplexWithReIm() {
        assertThatComplex(new DoubleComplex(2d, 3d))
            .isEqualTo(new DoubleComplex(2d, 3d))
            .isNotEqualTo(new DoubleComplex(3d, 2d));
    }

    @Test
    void hashCodeOfComplexWithReIm() {
        assertThat(new DoubleComplex(2d, 3d).hashCode()).isEqualTo(525249);
    }

    @Test
    void toStringOfComplexWithReIm() {
        Complex<Double> complex = new DoubleComplex(2d, 3d);
        assertThatComplex(complex).hasToString("2.0+3.0*i");
    }

    @Test
    void compareToOfComplexWithReIm() {
        Complex<Double> complex = new DoubleComplex(2d, 3d);
        assertThatComplex(complex)
            .isEqualByComparingTo(new DoubleComplex(2d, 3d))
            .isLessThan(new DoubleComplex(3d, 1d))
            .isGreaterThan(new DoubleComplex(2d, 1d));
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleComplex(), DoubleComplex.class);
    }

    // endregion
}
