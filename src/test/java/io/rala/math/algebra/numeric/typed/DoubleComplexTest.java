package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.assertion.NumericAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class DoubleComplexTest {
    private static final double DELTA = 0.00001;

    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertComplex(new DoubleComplex());
    }

    @Test
    void constructorWithReParameter() {
        assertComplex(new DoubleComplex(2d), 2, 0);
    }

    @Test
    void constructorWithDifferentReImParameter() {
        assertComplex(new DoubleComplex(2d, 3d), 2, 3);
    }

    @Test
    void constructorWithComplex() {
        assertComplex(new DoubleComplex(new DoubleComplex(2d, 3d)), 2, 3);
    }

    @Test
    void createAndSetRe() {
        Complex<Double> complex = new DoubleComplex();
        assertComplex(complex);
        complex.setRe(1d);
        assertComplex(complex, 1, 0);
    }

    @Test
    void createAndSetIm() {
        Complex<Double> complex = new DoubleComplex();
        assertComplex(complex);
        complex.setIm(2d);
        assertComplex(complex, 0, 2);
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
        assertThat(new DoubleComplex().absoluteValue()).isZero();
    }

    @Test
    void absoluteValueOfComplexX1Y1() {
        assertThat(new DoubleComplex(1d, 1d).absoluteValue()).isEqualTo(Math.sqrt(2d));
    }

    @Test
    void absoluteValueOfComplexX1Y0() {
        assertThat(new DoubleComplex(1d, 0d).absoluteValue()).isOne();
    }

    @Test
    void argumentOfComplexWithoutParameter() {
        assertThat(new DoubleComplex().argument()).isNaN();
    }

    @Test
    void argumentOfComplexX1Y1() {
        assertThat(new DoubleComplex(1d, 1d).argument()).isCloseTo(Math.PI / 4, offset(DELTA));
    }

    @Test
    void argumentOfComplexX1Y0() {
        assertThat(new DoubleComplex(1d, 0d).argument()).isZero();
    }

    @Test
    void signumOfComplexWithoutParameter() {
        assertThat(new DoubleComplex().signum()).isEqualTo(new DoubleComplex());
    }

    @Test
    void signumOfComplexX1Y1() {
        double sqrt2half = 0.7071067811865475; // Math.sqrt(2d) / 2
        assertThat(new DoubleComplex(1d, 1d).signum()).isEqualTo(new DoubleComplex(sqrt2half, sqrt2half));
    }

    @Test
    void signumOfComplexX1Y0() {
        assertThat(new DoubleComplex(1d, 0d).signum()).isEqualTo(new DoubleComplex(1d, 0d));
    }

    @Test
    void complexSignumOfComplexWithoutParameter() {
        assertThat(new DoubleComplex().complexSignum()).isZero();
    }

    @Test
    void complexSignumOfComplexX1Y1() {
        assertThat(new DoubleComplex(1d, 1d).complexSignum()).isOne();
    }

    @Test
    void complexSignumOfComplexXMinus1Y0() {
        assertThat(new DoubleComplex(-1d, 0d).complexSignum()).isEqualTo(-1);
    }

    @Test
    void complexSignumOfComplexX0Y1() {
        assertThat(new DoubleComplex(0d, 1d).complexSignum()).isOne();
    }

    @Test
    void complexSignumOfComplexX0YMinus1() {
        assertThat(new DoubleComplex(0d, -1d).complexSignum()).isEqualTo(-1);
    }

    @Test
    void conjugationOfComplexWithRe1Im2() {
        assertThat(new DoubleComplex(1d, 2d).conjugation()).isEqualTo(new DoubleComplex(1d, -2d));
    }

    @Test
    void reciprocalOfComplexWithoutParameter() {
        assertThat(new DoubleComplex().reciprocal()).isEqualTo(new DoubleComplex(Double.NaN, Double.NaN));
    }

    @Test
    void reciprocalOfComplexX1Y1() {
        assertThat(new DoubleComplex(1d, 1d).reciprocal()).isEqualTo(new DoubleComplex(0.5, -0.5));
    }

    @Test
    void reciprocalOfComplexX1Y0() {
        assertThat(new DoubleComplex(1d, 0d).reciprocal()).isEqualTo(new DoubleComplex(1d, -0d));
    }

    // endregion

    // region add and subtract

    @Test
    void addWithXY() {
        assertThat(new DoubleComplex().add(2d, 2d)).isEqualTo(new DoubleComplex(2d, 2d));
    }

    @Test
    void addWithXAndY() {
        assertThat(new DoubleComplex(1d, 1d).add(1d, 1d)).isEqualTo(new DoubleComplex(2d, 2d));
    }

    @Test
    void addWithDoubleComplex() {
        assertThat(new DoubleComplex(1d, 0d).add(new DoubleComplex(1d, 2d)))
            .isEqualTo(new DoubleComplex(2d, 2d));
    }

    @Test
    void subtractWithXY() {
        assertThat(new DoubleComplex(2d, 2d).subtract(2d, 2d)).isEqualTo(new DoubleComplex());
    }

    @Test
    void subtractWithXAndY() {
        assertThat(new DoubleComplex(2d, 2d).subtract(1d, 1d)).isEqualTo(new DoubleComplex(1d, 1d));
    }

    @Test
    void subtractWithDoubleComplex() {
        assertThat(new DoubleComplex(2d, 2d).subtract(new DoubleComplex(1d, 2d)))
            .isEqualTo(new DoubleComplex(1d, 0d));
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyZeroComplexWith1() {
        assertThat(new DoubleComplex().multiply(1d)).isEqualTo(new DoubleComplex());
    }

    @Test
    void multiplyComplexWith0() {
        assertThat(new DoubleComplex(1d, 1d).multiply(0d)).isEqualTo(new DoubleComplex());
    }

    @Test
    void multiplyComplexWith1() {
        assertThat(new DoubleComplex(1d, 1d).multiply(1d)).isEqualTo(new DoubleComplex(1d, 1d));
    }

    @Test
    void multiplyComplexWithMinus1() {
        assertThat(new DoubleComplex(2d, 1d).multiply(-1d)).isEqualTo(new DoubleComplex(-2d, -1d));
    }

    @Test
    void multiplyComplexWithDoubleComplex() {
        assertThat(new DoubleComplex(1d, 2d).multiply(new DoubleComplex(2d, 1d)))
            .isEqualTo(new DoubleComplex(0d, 5d));
    }

    @Test
    void divideZeroComplexWith1() {
        assertThat(new DoubleComplex().divide(1d)).isEqualTo(new DoubleComplex());
    }

    @Test
    void divideComplexWith0() {
        assertThat(new DoubleComplex(1d, 1d).divide(0d)).isEqualTo(new DoubleComplex(
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY
        ));
    }

    @Test
    void divideComplexWith1() {
        assertThat(new DoubleComplex(1d, 1d).divide(1d)).isEqualTo(new DoubleComplex(1d, 1d));
    }

    @Test
    void divideComplexWithMinus1() {
        assertThat(new DoubleComplex(2d, 1d).divide(-1d)).isEqualTo(new DoubleComplex(-2d, -1d));
    }

    @Test
    void divideComplexWithDoubleComplex() {
        assertThat(new DoubleComplex(1d, 2d).divide(new DoubleComplex(2d, 1d)))
            .isEqualTo(new DoubleComplex(0.8, 0.6));
    }

    // endregion

    // region pow and root

    @Test
    void pow8OfComplexWithRe1Im1() {
        assertThat(new DoubleComplex(1d, 1d).pow(8))
            .isEqualTo(new DoubleComplex(16.000000000000007, 1.0291984957930479E-14));
    }

    @Test
    void pow5OfComplexWithRe3Im4() {
        assertThat(new DoubleComplex(3d, 4d).pow(5))
            .isEqualTo(new DoubleComplex(-236.99999999999898, -3116d));
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
            new DoubleComplex(6.123233995736766E-17, 1d),
            new DoubleComplex(-1d, 1.2246467991473532E-16),
            new DoubleComplex(-1.8369701987210297E-16, -1d)
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
        assertThat(new DoubleComplex(1d, 2d).inverseRe()).isEqualTo(new DoubleComplex(-1d, 2d));
    }

    @Test
    void inverseImOfComplexWithRe1Im2() {
        assertThat(new DoubleComplex(1d, 2d).inverseIm()).isEqualTo(new DoubleComplex(1d, -2d));
    }

    @Test
    void inverseReImOfComplexWithRe1Im2() {
        assertThat(new DoubleComplex(1d, 2d).inverse()).isEqualTo(new DoubleComplex(-1d, -2d));
    }

    // endregion

    // region static of and asVector

    @Test
    void ofAb3AndAr50() {
        assertComplex(
            DoubleComplex.of(3d, 50d),
            2.89489808547634,
            -0.7871245611117863
        );
    }

    @Test
    void ofWithSelfValidation() {
        Complex<Double> complex = DoubleComplex.of(1d, 2d);
        assertThat(complex.absoluteValue()).isOne();
        assertThat(complex.argument()).isEqualTo(2);
    }

    @Test
    void asVectorOfComplexWithRe1Im2() {
        assertThat(new DoubleComplex(1d, 2d).asVector())
            .isEqualTo(new Vector<>(new DoubleArithmetic(), 1d, 2d));
    }

    // endregion

    // region map, isValid and copy

    @Test
    void mapOfComplexWithR0_5Im1_5() {
        DoubleComplex complex = new DoubleComplex(0.5, 1.5);
        Complex<Integer> result =
            new Complex<>(new IntegerArithmetic(), 0, 1);
        assertThat(complex.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZero() {
        assertThat(new DoubleComplex().isValid()).isTrue();
    }

    @Test
    void isValidWithInfValues() {
        assertThat(new DoubleComplex(
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY
        ).isValid()).isFalse();
    }

    @Test
    void copyOfComplexWithReIm() {
        Complex<Double> complex = new DoubleComplex(2d, 3d);
        assertThat(complex.copy()).isEqualTo(complex);
    }

    // endregion

    // region override

    @Test
    void equalsOfComplexWithReIm() {
        assertThat(new DoubleComplex(2d, 3d))
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
        assertThat(complex).hasToString("2.0+3.0*i");
    }

    @Test
    void compareToOfComplexWithReIm() {
        Complex<Double> complex = new DoubleComplex(2d, 3d);
        assertThat(complex)
            .isEqualByComparingTo(new DoubleComplex(2d, 3d))
            .isLessThan(new DoubleComplex(3d, 1d))
            .isGreaterThan(new DoubleComplex(2d, 1d));
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleComplex(), DoubleComplex.class);
    }

    // endregion


    // region assert

    private static void assertComplex(Complex<Double> complex) {
        assertComplex(complex, 0, 0);
    }

    private static void assertComplex(Complex<Double> complex, double re, double im) {
        NumericAssertions.assertComplex(complex, re, im);
    }

    // endregion
}
