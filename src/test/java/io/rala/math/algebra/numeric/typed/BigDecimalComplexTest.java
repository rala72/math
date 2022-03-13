package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.assertion.NumericAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.data.Offset.offset;

class BigDecimalComplexTest {
    private static final double DELTA = 0.00001;

    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertComplex(new BigDecimalComplex());
    }

    @Test
    void constructorWithMathContext5() {
        assertComplex(new BigDecimalComplex(new MathContext(5)));
    }

    @Test
    void constructorWithReParameter() {
        assertComplex(
            new BigDecimalComplex(BigDecimal.valueOf(2)),
            BigDecimal.valueOf(2), BigDecimal.ZERO
        );
    }

    @Test
    void constructorWithDifferentReImParameter() {
        assertComplex(new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        ), BigDecimal.valueOf(2), BigDecimal.valueOf(3));
    }

    @Test
    void constructorWithDifferentReImParameterAndMathContext5() {
        assertComplex(new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3), new MathContext(5)
        ), BigDecimal.valueOf(2), BigDecimal.valueOf(3));
    }

    @Test
    void constructorWithComplex() {
        assertComplex(new BigDecimalComplex(
            new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(3))
        ), BigDecimal.valueOf(2), BigDecimal.valueOf(3));
    }

    @Test
    void createAndSetRe() {
        Complex<BigDecimal> complex = new BigDecimalComplex();
        assertComplex(complex);
        complex.setRe(BigDecimal.ONE);
        assertComplex(complex, BigDecimal.ONE, BigDecimal.ZERO);
    }

    @Test
    void createAndSetIm() {
        Complex<BigDecimal> complex = new BigDecimalComplex();
        assertComplex(complex);
        complex.setIm(BigDecimal.valueOf(2));
        assertComplex(complex, BigDecimal.ZERO, BigDecimal.valueOf(2));
    }

    // endregion

    // region value

    @Test
    void intValueOfComplexWithX1_1Y2_2() {
        assertThat(new BigDecimalComplex(
            BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2)).intValue()).isOne();
    }

    @Test
    void longValueOfComplexWithX1_1Y2_2() {
        assertThat(new BigDecimalComplex(
            BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2)).longValue()).isOne();
    }

    @Test
    void floatValueOfComplexWithX1_1Y2_2() {
        assertThat(new BigDecimalComplex(
            BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2)).floatValue()).isEqualTo((float) 1.1);
    }

    @Test
    void doubleValueOfComplexWithX1_1Y2_2() {
        assertThat(new BigDecimalComplex(
            BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2)).doubleValue()).isEqualTo(1.1);
    }

    // endregion

    // region absoluteValue, argument and signum

    @Test
    void absoluteValueOfComplexWithoutParameter() {
        assertThat(new BigDecimalComplex().absoluteValue()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void absoluteValueOfComplexX1Y1() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).absoluteValue())
            .isEqualTo(BigDecimal.valueOf(1.414213562373095));
    }

    @Test
    void absoluteValueOfComplexX1Y0() {
        assertThat(new BigDecimalComplex(
            BigDecimal.ONE, BigDecimal.ZERO).absoluteValue()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void argumentOfComplexWithoutParameter() {
        assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> new BigDecimalComplex().argument()); // assert exception message?
    }

    @Test
    void argumentOfComplexX1Y1() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).argument())
            .isEqualTo(BigDecimal.valueOf(0.7853981633974484d));
    }

    @Test
    void argumentOfComplexX1Y0() {
        assertThat(new BigDecimalComplex(
            BigDecimal.ONE, BigDecimal.ZERO).argument()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void signumOfComplexWithoutParameter() {
        assertThat(new BigDecimalComplex().signum()).isEqualTo(new BigDecimalComplex());
    }

    @Test
    void signumOfComplexX1Y1() {
        BigDecimal sqrt2half = BigDecimal.valueOf(0.7071067811865475);
        // Math.sqrt(2d) / 2d
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).signum())
            .isEqualTo(new BigDecimalComplex(sqrt2half, sqrt2half));
    }

    @Test
    void signumOfComplexX1Y0() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO).signum())
            .isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO));
    }

    @Test
    void complexSignumOfComplexWithoutParameter() {
        assertThat(new BigDecimalComplex().complexSignum()).isZero();
    }

    @Test
    void complexSignumOfComplexX1Y1() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).complexSignum()).isOne();
    }

    @Test
    void complexSignumOfComplexXMinus1Y0() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE.negate(), BigDecimal.ZERO)
            .complexSignum()).isEqualTo(-1);
    }

    @Test
    void complexSignumOfComplexX0Y1() {
        assertThat(new BigDecimalComplex(BigDecimal.ZERO, BigDecimal.ONE).complexSignum()).isOne();
    }

    @Test
    void complexSignumOfComplexX0YMinus1() {
        assertThat(new BigDecimalComplex(BigDecimal.ZERO, BigDecimal.ONE.negate())
            .complexSignum()).isEqualTo(-1);
    }

    // endregion

    // region conjugation and reciprocal

    @Test
    void conjugationOfComplexWithRe1Im2() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)).conjugation())
            .isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2).negate()));
    }

    @Test
    void reciprocalOfComplexWithoutParameter() {
        assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> new BigDecimalComplex().reciprocal()); // assert exception message?
    }

    @Test
    void reciprocalOfComplexX1Y1() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).reciprocal()).isEqualTo(new BigDecimalComplex(
            BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.5).negate()
        ));
    }

    @Test
    void reciprocalOfComplexX1Y0() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO).reciprocal()).isEqualTo(new BigDecimalComplex(
            BigDecimal.ONE, BigDecimal.ZERO
        ));
    }

    // endregion

    // region add and subtract

    @Test
    void addWithXY() {
        assertThat(new BigDecimalComplex().add(BigDecimal.valueOf(2), BigDecimal.valueOf(2)))
            .isEqualTo(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2)));
    }

    @Test
    void addWithXAndY() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE)
            .add(BigDecimal.ONE, BigDecimal.ONE)).isEqualTo(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2)));
    }

    @Test
    void addWithBigDecimalComplex() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO)
            .add(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)))).isEqualTo(new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(2)
        ));
    }

    @Test
    void subtractWithXY() {
        assertThat(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
            .subtract(BigDecimal.valueOf(2), BigDecimal.valueOf(2))).isEqualTo(new BigDecimalComplex());
    }

    @Test
    void subtractWithXAndY() {
        assertThat(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
            .subtract(BigDecimal.ONE, BigDecimal.ONE)).isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    void subtractWithBigDecimalComplex() {
        assertThat(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
            .subtract(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2))))
            .isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO));
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyZeroComplexWith1() {
        assertThat(new BigDecimalComplex().multiply(BigDecimal.ONE)).isEqualTo(new BigDecimalComplex());
    }

    @Test
    void multiplyComplexWith0() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE)
            .multiply(BigDecimal.ZERO)).isEqualTo(new BigDecimalComplex());
    }

    @Test
    void multiplyComplexWith1() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE)
            .multiply(BigDecimal.ONE)).isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    void multiplyComplexWithMinus1() {
        assertThat(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.ONE)
            .multiply(BigDecimal.ONE.negate())).isEqualTo(new BigDecimalComplex(
            BigDecimal.valueOf(2).negate(),
            BigDecimal.ONE.negate()
        ));
    }

    @Test
    void multiplyComplexWithBigDecimalComplex() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2))
            .multiply(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.ONE)))
            .isEqualTo(new BigDecimalComplex(BigDecimal.ZERO, BigDecimal.valueOf(5)));
    }

    @Test
    void divideZeroComplexWith1() {
        assertThat(new BigDecimalComplex().divide(BigDecimal.ONE)).isEqualTo(new BigDecimalComplex());
    }

    @Test
    void divideComplexWith0() {
        assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE)
                .divide(BigDecimal.ZERO)); // assert exception message?
    }

    @Test
    void divideComplexWith1() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).divide(BigDecimal.ONE))
            .isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    void divideComplexWithMinus1() {
        assertThat(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.ONE)
            .divide(BigDecimal.ONE.negate())).isEqualTo(new BigDecimalComplex(
            BigDecimal.valueOf(2).negate(),
            BigDecimal.ONE.negate()
        ));
    }

    @Test
    void divideComplexWithBigDecimalComplex() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2))
            .divide(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.ONE)))
            .isEqualTo(new BigDecimalComplex(BigDecimal.valueOf(0.8), BigDecimal.valueOf(0.6)));
    }

    // endregion

    // region pow and root

    @Test
    void pow8OfComplexWithRe1Im1() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).pow(8)).isEqualTo(new BigDecimalComplex(
            BigDecimal.valueOf(16),
            new BigDecimal("1.029198495793047E-14")
        ));
    }

    @Test
    void pow5OfComplexWithRe3Im4() {
        assertThat(new BigDecimalComplex(BigDecimal.valueOf(3), BigDecimal.valueOf(4)).pow(5)).isEqualTo(new BigDecimalComplex(
            new BigDecimal("-236.9999999999962"),
            BigDecimal.valueOf(3116).negate()
        ));
    }

    @Test
    void root3OfComplexWithRe1Im0() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO).root(3)).isEqualTo(List.of(
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO),
            new BigDecimalComplex(
                new BigDecimal("-0.4999999999999994"),
                new BigDecimal("0.8660254037844389")
            ),
            new BigDecimalComplex(
                new BigDecimal("-0.5000000000000012"),
                new BigDecimal("-0.8660254037844379")
            )
        ));
    }

    @Test
    void root4OfComplexWithRe1Im0() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO).root(4)).isEqualTo(List.of(
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO),
            new BigDecimalComplex(
                new BigDecimal("7.273661547324616E-16"),
                BigDecimal.ONE
            ),
            new BigDecimalComplex(
                BigDecimal.ONE.negate(),
                new BigDecimal("1.454732309464923E-15")
            ),
            new BigDecimalComplex(
                new BigDecimal("-1.83697019872103E-16"),
                BigDecimal.ONE.negate()
            )
        ));
    }

    @Test
    void root2OfComplexWithReMinus1ImSqrt3() {
        assertThat(new BigDecimalComplex(
            BigDecimal.ONE.negate(),
            BigDecimal.valueOf(Math.sqrt(3))
        ).root(2)).isEqualTo(List.of(
            new BigDecimalComplex(
                BigDecimal.valueOf(0.7071067811865471),
                BigDecimal.valueOf(1.224744871391589)
            ),
            new BigDecimalComplex(
                BigDecimal.valueOf(0.7071067811865469).negate(),
                BigDecimal.valueOf(1.224744871391589).negate()
            )
        ));
    }

    // endregion

    // region inverse

    @Test
    void inverseReOfComplexWithRe1Im2() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)).inverseRe())
            .isEqualTo(new BigDecimalComplex(BigDecimal.ONE.negate(), BigDecimal.valueOf(2)));
    }

    @Test
    void inverseImOfComplexWithRe1Im2() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)).inverseIm())
            .isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2).negate()));
    }

    @Test
    void inverseReImOfComplexWithRe1Im2() {
        assertThat(new BigDecimalComplex(
            BigDecimal.ONE,
            BigDecimal.valueOf(2)
        ).inverse()).isEqualTo(new BigDecimalComplex(
            BigDecimal.ONE.negate(),
            BigDecimal.valueOf(2).negate()
        ));
    }

    // endregion

    // region static of and asVector

    @Test
    void ofAb3AndAr50() {
        assertComplex(
            BigDecimalComplex.of(BigDecimal.valueOf(3), BigDecimal.valueOf(50)),
            BigDecimal.valueOf(2.89489808547634),
            BigDecimal.valueOf(0.7871245611117864).negate()
        );
    }

    @Test
    void ofWithSelfValidation() {
        Complex<BigDecimal> complex = BigDecimalComplex.of(
            BigDecimal.ONE, BigDecimal.valueOf(2)
        );
        assertThat(complex.absoluteValue().doubleValue()).isCloseTo(1, offset(DELTA));
        assertThat(complex.argument()).isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void asVectorOfComplexWithRe1Im2() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)).asVector())
            .isEqualTo(new Vector<>(new BigDecimalArithmetic(),
                BigDecimal.ONE, BigDecimal.valueOf(2)
            ));
    }

    // endregion

    // region map, isValid and copy

    @Test
    void mapOfComplexWithR0_5Im1_5() {
        BigDecimalComplex complex = new BigDecimalComplex(
            BigDecimal.valueOf(0.5),
            BigDecimal.valueOf(1.5)
        );
        Complex<Integer> result =
            new Complex<>(new IntegerArithmetic(), 0, 1);
        assertThat(complex.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZero() {
        assertThat(new BigDecimalComplex().isValid()).isTrue();
    }

    @Test
    void copyOfComplexWithReIm() {
        Complex<BigDecimal> complex = new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        );
        assertThat(complex.copy()).isEqualTo(complex);
    }

    // endregion

    // region override

    @Test
    void equalsOfComplexWithReIm() {
        assertThat(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
            .isEqualTo(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
            .isNotEqualTo(new BigDecimalComplex(BigDecimal.valueOf(3), BigDecimal.valueOf(2)));
    }

    @Test
    void hashCodeOfComplexWithReIm() {
        assertThat(new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        ).hashCode()).isEqualTo(2976);
    }

    @Test
    void toStringOfComplexWithReIm() {
        Complex<BigDecimal> complex = new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        );
        assertThat(complex).hasToString("2+3*i");
    }

    @Test
    void compareToOfComplexWithReIm() {
        Complex<BigDecimal> complex = new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        );
        assertThat(complex)
            .isEqualByComparingTo(new BigDecimalComplex(
                BigDecimal.valueOf(2), BigDecimal.valueOf(3)
            ))
            .isLessThan(new BigDecimalComplex(
                BigDecimal.valueOf(3), BigDecimal.ONE
            ))
            .isGreaterThan(new BigDecimalComplex(
                BigDecimal.valueOf(2), BigDecimal.ONE
            ));
    }

    @Test
    void serializable() {
        assertSerializable(new BigDecimalComplex(), BigDecimalComplex.class);
    }

    // endregion


    // region assert

    private static void assertComplex(Complex<BigDecimal> complex) {
        assertComplex(complex, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    private static void assertComplex(
        Complex<BigDecimal> complex, BigDecimal re, BigDecimal im
    ) {
        NumericAssertions.assertComplex(complex, re, im);
    }

    // endregion
}
