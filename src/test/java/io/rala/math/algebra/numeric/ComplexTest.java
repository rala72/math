package io.rala.math.algebra.numeric;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.algebra.TestComplex;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatComplex;
import static io.rala.math.testUtils.assertion.GeometryAssertions.assertThatVector;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;

class ComplexTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertThatComplex(new TestComplex()).hasReWithZero().hasImWithZero();
    }

    @Test
    void constructorWithReParameter() {
        assertThatComplex(new TestComplex(2)).hasRe(2).hasImWithZero();
    }

    @Test
    void constructorWithDifferentReImParameter() {
        assertThatComplex(new TestComplex(2, 3)).hasRe(2).hasIm(3);
    }

    @Test
    void constructorWithComplex() {
        assertThatComplex(new TestComplex(new TestComplex(2, 3)))
            .hasRe(2).hasIm(3);
    }

    @Test
    void createAndSetRe() {
        TestComplex complex = new TestComplex();
        assertThatComplex(complex).hasReWithZero().hasImWithZero();
        complex.setRe(1);
        assertThatComplex(complex).hasRe(1).hasImWithZero();
    }

    @Test
    void createAndSetIm() {
        TestComplex complex = new TestComplex();
        assertThatComplex(complex).hasReWithZero().hasImWithZero();
        complex.setIm(2);
        assertThatComplex(complex).hasReWithZero().hasIm(2);
    }

    // endregion

    // region value

    @Test
    void intValueOfComplexWithX1_1Y2_2() {
        assertThat(new TestComplex(1.1, 2.2).intValue()).isOne();
    }

    @Test
    void longValueOfComplexWithX1_1Y2_2() {
        assertThat(new TestComplex(1.1, 2.2).longValue()).isOne();
    }

    @Test
    void floatValueOfComplexWithX1_1Y2_2() {
        assertThat(new TestComplex(1.1, 2.2).floatValue()).isEqualTo((float) 1.1);
    }

    @Test
    void doubleValueOfComplexWithX1_1Y2_2() {
        assertThat(new TestComplex(1.1, 2.2).doubleValue()).isEqualTo(1.1);
    }

    // endregion

    // region absoluteValue, argument and signum

    @Test
    void absoluteValueOfComplexWithoutParameter() {
        assertThatComplex(new TestComplex()).hasAbsoluteValue(0d);
    }

    @Test
    void absoluteValueOfComplexX1Y1() {
        assertThatComplex(new TestComplex(1, 1)).hasAbsoluteValue(Math.sqrt(2d));
    }

    @Test
    void absoluteValueOfComplexX1Y0() {
        assertThatComplex(new TestComplex(1, 0)).hasAbsoluteValue(1d);
    }

    @Test
    void argumentOfComplexWithoutParameter() {
        assertThatComplex(new TestComplex()).hasArgument(Double.NaN);
    }

    @Test
    void argumentOfComplexX1Y1() {
        assertThatComplex(new TestComplex(1, 1))
            .hasArgumentCloseTo(Math.PI / 4);
    }

    @Test
    void argumentOfComplexX1Y0() {
        assertThatComplex(new TestComplex(1, 0)).hasArgument(0d);
    }

    @Test
    void signumOfComplexWithoutParameter() {
        assertThatComplex(new TestComplex().signum()).isEqualTo(new TestComplex());
    }

    @Test
    void signumOfComplexX1Y1() {
        double sqrt2half = Math.sqrt(2) / 2;
        assertThatComplex(new TestComplex(1, 1).signum())
            .hasRe(sqrt2half).hasIm(sqrt2half);
    }

    @Test
    void signumOfComplexX1Y0() {
        assertThatComplex(new TestComplex(1, 0).signum()).isEqualTo(new TestComplex(1d, 0d));
    }

    @Test
    void complexSignumOfComplexWithoutParameter() {
        assertThatComplex(new TestComplex()).hasComplexSignum(0);
    }

    @Test
    void complexSignumOfComplexX1Y1() {
        assertThatComplex(new TestComplex(1, 1)).hasComplexSignum(1);
    }

    @Test
    void complexSignumOfComplexXMinus1Y0() {
        assertThatComplex(new TestComplex(-1, 0)).hasComplexSignum(-1);
    }

    @Test
    void complexSignumOfComplexX0Y1() {
        assertThatComplex(new TestComplex(0, 1)).hasComplexSignum(0);
    }

    @Test
    void complexSignumOfComplexX0YMinus1() {
        assertThatComplex(new TestComplex(0, -1)).hasComplexSignum(0);
    }

    // endregion

    // region conjugation and reciprocal

    @Test
    void conjugationOfComplexWithRe1Im2() {
        assertThatComplex(new TestComplex(1, 2).conjugation())
            .isEqualTo(new TestComplex(1, -2d));
    }

    @Test
    void reciprocalOfComplexWithoutParameter() {
        assertThatComplex(new TestComplex().reciprocal())
            .isEqualTo(new TestComplex(Double.NaN, Double.NaN));
    }

    @Test
    void reciprocalOfComplexX1Y1() {
        assertThatComplex(new TestComplex(1, 1).reciprocal())
            .isEqualTo(new TestComplex(0.5, -0.5));
    }

    @Test
    void reciprocalOfComplexX1Y0() {
        assertThatComplex(new TestComplex(1, 0).reciprocal())
            .isEqualTo(new TestComplex(1d, -0d));
    }

    // endregion

    // region add and subtract

    @Test
    void addWithXY() {
        assertThatComplex(new TestComplex().add(2, 2))
            .isEqualTo(new TestComplex(2d, 2d));
    }

    @Test
    void addWithXAndY() {
        assertThatComplex(new TestComplex(1, 1).add(1, 1))
            .isEqualTo(new TestComplex(2d, 2d));
    }

    @Test
    void addWithComplex() {
        assertThatComplex(new TestComplex(1, 0).add(new TestComplex(1, 2)))
            .isEqualTo(new TestComplex(2d, 2d));
    }

    @Test
    void subtractWithXY() {
        assertThatComplex(new TestComplex(2, 2).subtract(2, 2))
            .isEqualTo(new TestComplex(0d, 0d));
    }

    @Test
    void subtractWithXAndY() {
        assertThatComplex(new TestComplex(2, 2).subtract(1, 1))
            .isEqualTo(new TestComplex(1d, 1d));
    }

    @Test
    void subtractWithComplex() {
        assertThatComplex(new TestComplex(2, 2).subtract(new TestComplex(1, 2)))
            .isEqualTo(new TestComplex(1d, 0d));
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyZeroComplexWith1() {
        assertThatComplex(new TestComplex().multiply(1))
            .isEqualTo(new TestComplex(0d, 0d));
    }

    @Test
    void multiplyComplexWith0() {
        assertThatComplex(new TestComplex(1, 1).multiply(0))
            .isEqualTo(new TestComplex(0d, 0d));
    }

    @Test
    void multiplyComplexWith1() {
        assertThatComplex(new TestComplex(1, 1).multiply(1))
            .isEqualTo(new TestComplex(1d, 1d));
    }

    @Test
    void multiplyComplexWithMinus1() {
        assertThatComplex(new TestComplex(2, 1).multiply(-1))
            .isEqualTo(new TestComplex(-2d, -1d));
    }

    @Test
    void multiplyComplexWithComplex() {
        assertThatComplex(new TestComplex(1, 2).multiply(new TestComplex(2, 1)))
            .isEqualTo(new TestComplex(0d, 5d));
    }

    @Test
    void divideZeroComplexWith1() {
        assertThatComplex(new TestComplex().divide(1)).isEqualTo(new TestComplex(0d, 0d));
    }

    @Test
    void divideComplexWith0() {
        assertThatComplex(new TestComplex(1, 1).divide(0))
            .isEqualTo(new TestComplex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    }

    @Test
    void divideComplexWith1() {
        assertThatComplex(new TestComplex(1, 1).divide(1))
            .isEqualTo(new TestComplex(1d, 1d));
    }

    @Test
    void divideComplexWithMinus1() {
        assertThatComplex(new TestComplex(2, 1).divide(-1))
            .isEqualTo(new TestComplex(-2d, -1d));
    }

    @Test
    void divideComplexWithComplex() {
        assertThatComplex(new TestComplex(1, 2).divide(new TestComplex(2, 1)))
            .isEqualTo(new TestComplex(0.8, 0.6));
    }

    // endregion

    // region pow and root

    @Test
    void pow8OfComplexWithRe1Im1() {
        assertThatComplex(new TestComplex(1, 1).pow(8))
            .hasRe(16).hasIm(1.0291984957930479e-14);
    }

    @Test
    void pow5OfComplexWithRe3Im4() {
        assertThatComplex(new TestComplex(3, 4).pow(5))
            .hasRe(-237).hasIm(-3116);
    }

    @Test
    void root3OfComplexWithRe1Im0() {
        assertThat(new TestComplex(1, 0).root(3)).isEqualTo(List.of(
            new TestComplex(1d, 0d),
            new TestComplex(-0.4999999999999998, 0.8660254037844387),
            new TestComplex(-0.5000000000000004, -0.8660254037844384)
        ));
    }

    @Test
    void root4OfComplexWithRe1Im0() {
        assertThat(new TestComplex(1, 0).root(4)).isEqualTo(List.of(
            new TestComplex(1d, 0d),
            new TestComplex(6.123233995736766e-17, 1d),
            new TestComplex(-1d, 1.2246467991473532e-16),
            new TestComplex(-1.8369701987210297e-16, -1d)
        ));
    }

    @Test
    void root2OfComplexWithReMinus1ImSqrt3() {
        assertThat(new TestComplex(-1, Math.sqrt(3)).root(2)).isEqualTo(List.of(
            new TestComplex(0.7071067811865474, 1.224744871391589),
            new TestComplex(-0.7071067811865469, -1.2247448713915892)
        ));
    }

    // endregion

    // region inverse

    @Test
    void inverseReOfComplexWithRe1Im2() {
        assertThatComplex(new TestComplex(1, 2).inverseRe())
            .isEqualTo(new TestComplex(-1d, 2));
    }

    @Test
    void inverseImOfComplexWithRe1Im2() {
        assertThatComplex(new TestComplex(1, 2).inverseIm())
            .isEqualTo(new TestComplex(1, -2d));
    }

    @Test
    void inverseReImOfComplexWithRe1Im2() {
        assertThatComplex(new TestComplex(1, 2).inverse())
            .isEqualTo(new TestComplex(-1d, -2d));
    }

    // endregion

    // region static of and asVector

    @Test
    void ofAb3AndAr50() {
        assertThatComplex(
            Complex.of(new TestAbstractArithmetic(), 3, 50)
        ).hasRe(2.89489808547634).hasIm(-0.7871245611117863);
    }

    @Test
    void ofWithSelfValidation() {
        Complex<Number> complex = Complex.of(new TestAbstractArithmetic(),
            1, 2
        );
        assertThatComplex(complex).hasAbsoluteValue(1d).hasArgument(2d);
    }

    @Test
    void asVectorOfComplexWithRe1Im2() {
        assertThatVector(new TestComplex(1, 2).asVector())
            .isEqualTo(new Vector<>(new TestAbstractArithmetic(), 1, 2));
    }

    @Test
    void asVectorAndAsComplexWithoutChangeAreSuperfluous() {
        // better word than of superfluous?
        Complex<Number> complex = new TestComplex(1d, 2d);
        assertThatComplex(complex.asVector().asComplex()).isEqualTo(complex);
    }

    // endregion

    // region map, isValid and copy

    @Test
    void mapOfComplexWithR0_5Im1_5() {
        TestComplex complex = new TestComplex(0.5, 1.5);
        Complex<Integer> result =
            new Complex<>(new IntegerArithmetic(), 0, 1);
        assertThatComplex(complex.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZero() {
        assertThatComplex(new TestComplex()).isValid();
    }

    @Test
    void isValidWithInfValues() {
        assertThatComplex(
            new TestComplex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        ).isInvalid();
    }

    @Test
    void copyOfComplexWithReIm() {
        assertCopyable(new TestComplex(2, 3));
    }

    // endregion

    // region override

    @Test
    void equalsOfComplexWithReIm() {
        assertThatComplex(new TestComplex(2, 3))
            .isEqualTo(new TestComplex(2, 3))
            .isNotEqualTo(new TestComplex(3, 2));
    }

    @Test
    void hashCodeOfComplexWithReIm() {
        assertThat(new TestComplex(2, 3).hashCode()).isEqualTo(1026);
    }

    @Test
    void toStringOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        assertThatComplex(complex).hasToString("2+3*i");
    }

    @Test
    void compareToOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        assertThatComplex(complex)
            .isEqualByComparingTo(new TestComplex(2, 3))
            .isLessThan(new TestComplex(3, 1))
            .isGreaterThan(new TestComplex(2, 1));
    }

    @Test
    void serializable() {
        assertSerializable(new TestComplex(), TestComplex.class);
    }

    // endregion
}
