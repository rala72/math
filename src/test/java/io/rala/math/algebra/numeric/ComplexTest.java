package io.rala.math.algebra.numeric;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.algebra.TestComplex;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.NumericAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class ComplexTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertComplex(new TestComplex());
    }

    @Test
    void constructorWithReParameter() {
        assertComplex(new TestComplex(2), 2, 0d);
    }

    @Test
    void constructorWithDifferentReImParameter() {
        assertComplex(new TestComplex(2, 3), 2, 3);
    }

    @Test
    void constructorWithComplex() {
        assertComplex(new TestComplex(new TestComplex(2, 3)), 2, 3);
    }

    @Test
    void createAndSetRe() {
        TestComplex complex = new TestComplex();
        assertComplex(complex);
        complex.setRe(1);
        assertComplex(complex, 1, 0d);
    }

    @Test
    void createAndSetIm() {
        TestComplex complex = new TestComplex();
        assertComplex(complex);
        complex.setIm(2);
        assertComplex(complex, 0d, 2);
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
        assertThat(new TestComplex().absoluteValue()).isEqualTo(0d);
    }

    @Test
    void absoluteValueOfComplexX1Y1() {
        assertThat(new TestComplex(1, 1).absoluteValue()).isEqualTo(Math.sqrt(2d));
    }

    @Test
    void absoluteValueOfComplexX1Y0() {
        assertThat(new TestComplex(1, 0).absoluteValue()).isEqualTo(1d);
    }

    @Test
    void argumentOfComplexWithoutParameter() {
        assertThat(new TestComplex().argument()).isEqualTo(Double.NaN);
    }

    @Test
    void argumentOfComplexX1Y1() {
        assertThat(new TestComplex(1, 1).argument().doubleValue()).isCloseTo(Math.PI / 4, offset(GeometryAssertions.DELTA));
    }

    @Test
    void argumentOfComplexX1Y0() {
        assertThat(new TestComplex(1, 0).argument()).isEqualTo(0d);
    }

    @Test
    void signumOfComplexWithoutParameter() {
        assertThat(new TestComplex().signum()).isEqualTo(new TestComplex());
    }

    @Test
    void signumOfComplexX1Y1() {
        double sqrt2half = 0.7071067811865475; // Math.sqrt(2d) / 2
        assertThat(new TestComplex(1, 1).signum()).isEqualTo(new TestComplex(sqrt2half, sqrt2half));
    }

    @Test
    void signumOfComplexX1Y0() {
        assertThat(new TestComplex(1, 0).signum()).isEqualTo(new TestComplex(1d, 0d));
    }

    @Test
    void complexSignumOfComplexWithoutParameter() {
        assertThat(new TestComplex().complexSignum()).isZero();
    }

    @Test
    void complexSignumOfComplexX1Y1() {
        assertThat(new TestComplex(1, 1).complexSignum()).isOne();
    }

    @Test
    void complexSignumOfComplexXMinus1Y0() {
        assertThat(new TestComplex(-1, 0).complexSignum()).isEqualTo(-1);
    }

    @Test
    void complexSignumOfComplexX0Y1() {
        assertThat(new TestComplex(0, 1).complexSignum()).isZero();
    }

    @Test
    void complexSignumOfComplexX0YMinus1() {
        assertThat(new TestComplex(0, -1).complexSignum()).isZero();
    }

    // endregion

    // region conjugation and reciprocal

    @Test
    void conjugationOfComplexWithRe1Im2() {
        assertThat(new TestComplex(1, 2).conjugation()).isEqualTo(new TestComplex(1, -2d));
    }

    @Test
    void reciprocalOfComplexWithoutParameter() {
        assertThat(new TestComplex().reciprocal()).isEqualTo(new TestComplex(Double.NaN, Double.NaN));
    }

    @Test
    void reciprocalOfComplexX1Y1() {
        assertThat(new TestComplex(1, 1).reciprocal()).isEqualTo(new TestComplex(0.5, -0.5));
    }

    @Test
    void reciprocalOfComplexX1Y0() {
        assertThat(new TestComplex(1, 0).reciprocal()).isEqualTo(new TestComplex(1d, -0d));
    }

    // endregion

    // region add and subtract

    @Test
    void addWithXY() {
        assertThat(new TestComplex().add(2, 2)).isEqualTo(new TestComplex(2d, 2d));
    }

    @Test
    void addWithXAndY() {
        assertThat(new TestComplex(1, 1).add(1, 1)).isEqualTo(new TestComplex(2d, 2d));
    }

    @Test
    void addWithComplex() {
        assertThat(new TestComplex(1, 0).add(new TestComplex(1, 2))).isEqualTo(new TestComplex(2d, 2d));
    }

    @Test
    void subtractWithXY() {
        assertThat(new TestComplex(2, 2).subtract(2, 2)).isEqualTo(new TestComplex(0d, 0d));
    }

    @Test
    void subtractWithXAndY() {
        assertThat(new TestComplex(2, 2).subtract(1, 1)).isEqualTo(new TestComplex(1d, 1d));
    }

    @Test
    void subtractWithComplex() {
        assertThat(new TestComplex(2, 2).subtract(new TestComplex(1, 2))).isEqualTo(new TestComplex(1d, 0d));
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyZeroComplexWith1() {
        assertThat(new TestComplex().multiply(1)).isEqualTo(new TestComplex(0d, 0d));
    }

    @Test
    void multiplyComplexWith0() {
        assertThat(new TestComplex(1, 1).multiply(0)).isEqualTo(new TestComplex(0d, 0d));
    }

    @Test
    void multiplyComplexWith1() {
        assertThat(new TestComplex(1, 1).multiply(1)).isEqualTo(new TestComplex(1d, 1d));
    }

    @Test
    void multiplyComplexWithMinus1() {
        assertThat(new TestComplex(2, 1).multiply(-1)).isEqualTo(new TestComplex(-2d, -1d));
    }

    @Test
    void multiplyComplexWithComplex() {
        assertThat(new TestComplex(1, 2).multiply(new TestComplex(2, 1))).isEqualTo(new TestComplex(0d, 5d));
    }

    @Test
    void divideZeroComplexWith1() {
        assertThat(new TestComplex().divide(1)).isEqualTo(new TestComplex(0d, 0d));
    }

    @Test
    void divideComplexWith0() {
        assertThat(new TestComplex(1, 1).divide(0))
            .isEqualTo(new TestComplex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    }

    @Test
    void divideComplexWith1() {
        assertThat(new TestComplex(1, 1).divide(1)).isEqualTo(new TestComplex(1d, 1d));
    }

    @Test
    void divideComplexWithMinus1() {
        assertThat(new TestComplex(2, 1).divide(-1)).isEqualTo(new TestComplex(-2d, -1d));
    }

    @Test
    void divideComplexWithComplex() {
        assertThat(new TestComplex(1, 2).divide(new TestComplex(2, 1))).isEqualTo(new TestComplex(0.8, 0.6));
    }

    // endregion

    // region pow and root

    @Test
    void pow8OfComplexWithRe1Im1() {
        assertThat(new TestComplex(1, 1).pow(8)).isEqualTo(new TestComplex(16.000000000000007, 1.0291984957930479e-14));
    }

    @Test
    void pow5OfComplexWithRe3Im4() {
        assertThat(new TestComplex(3, 4).pow(5)).isEqualTo(new TestComplex(-236.99999999999898, -3116d));
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
        assertThat(new TestComplex(1, 2).inverseRe()).isEqualTo(new TestComplex(-1d, 2));
    }

    @Test
    void inverseImOfComplexWithRe1Im2() {
        assertThat(new TestComplex(1, 2).inverseIm()).isEqualTo(new TestComplex(1, -2d));
    }

    @Test
    void inverseReImOfComplexWithRe1Im2() {
        assertThat(new TestComplex(1, 2).inverse()).isEqualTo(new TestComplex(-1d, -2d));
    }

    // endregion

    // region static of and asVector

    @Test
    void ofAb3AndAr50() {
        assertComplex(
            Complex.of(new TestAbstractArithmetic(), 3, 50),
            2.89489808547634,
            -0.7871245611117863
        );
    }

    @Test
    void ofWithSelfValidation() {
        Complex<Number> complex = Complex.of(new TestAbstractArithmetic(),
            1, 2
        );
        assertThat(complex.absoluteValue()).isEqualTo(1d);
        assertThat(complex.argument()).isEqualTo(2d);
    }

    @Test
    void asVectorOfComplexWithRe1Im2() {
        assertThat(new TestComplex(1, 2).asVector()).isEqualTo(new Vector<>(new TestAbstractArithmetic(), 1, 2));
    }

    @Test
    void asVectorAndAsComplexWithoutChangeAreSuperfluous() {
        // better word than of superfluous?
        Complex<Number> complex = new TestComplex(1d, 2d);
        assertThat(complex.asVector().asComplex()).isEqualTo(complex);
    }

    // endregion

    // region map, isValid and copy

    @Test
    void mapOfComplexWithR0_5Im1_5() {
        TestComplex complex = new TestComplex(0.5, 1.5);
        Complex<Integer> result =
            new Complex<>(new IntegerArithmetic(), 0, 1);
        assertThat(complex.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZero() {
        assertThat(new TestComplex().isValid()).isTrue();
    }

    @Test
    void isValidWithInfValues() {
        assertThat(new TestComplex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
            .isValid()).isFalse();
    }

    @Test
    void copyOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        assertThat(complex.copy()).isEqualTo(complex);
    }

    // endregion

    // region override

    @Test
    void equalsOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        assertThat(new TestComplex(2, 3)).isEqualTo(complex);
        assertThat(new TestComplex(3, 2)).isNotEqualTo(complex);
    }

    @Test
    void hashCodeOfComplexWithReIm() {
        assertThat(new TestComplex(2, 3).hashCode()).isEqualTo(1026);
    }

    @Test
    void toStringOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        assertThat(complex).hasToString("2+3*i");
    }

    @Test
    void compareToOfComplexWithReIm() {
        TestComplex complex = new TestComplex(2, 3);
        assertThat(complex)
            .isEqualByComparingTo(new TestComplex(2, 3))
            .isLessThan(new TestComplex(3, 1))
            .isGreaterThan(new TestComplex(2, 1));
    }

    @Test
    void serializable() {
        assertSerializable(new TestComplex(), TestComplex.class);
    }

    // endregion


    // region assert

    private static void assertComplex(TestComplex complex) {
        assertComplex(complex, 0d, 0d);
    }

    private static void assertComplex(Complex<Number> complex, Number re, Number im) {
        NumericAssertions.assertComplex(complex, re, im);
    }

    // endregion
}
