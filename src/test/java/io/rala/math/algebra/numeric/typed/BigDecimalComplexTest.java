package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatComplex;
import static io.rala.math.testUtils.assertion.GeometryAssertions.assertThatVector;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BigDecimalComplexTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertThatComplex(new BigDecimalComplex()).hasReWithZero().hasImWithZero();
    }

    @Test
    void constructorWithMathContext5() {
        assertThatComplex(new BigDecimalComplex(new MathContext(5)))
            .hasReWithZero().hasImWithZero();
    }

    @Test
    void constructorWithReParameter() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.valueOf(2)))
            .hasRe(BigDecimal.valueOf(2)).hasImWithZero();
    }

    @Test
    void constructorWithDifferentReImParameter() {
        assertThatComplex(new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        )).hasRe(BigDecimal.valueOf(2)).hasIm(BigDecimal.valueOf(3));
    }

    @Test
    void constructorWithDifferentReImParameterAndMathContext5() {
        assertThatComplex(new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3), new MathContext(5)
        )).hasRe(BigDecimal.valueOf(2)).hasIm(BigDecimal.valueOf(3));
    }

    @Test
    void constructorWithComplex() {
        assertThatComplex(new BigDecimalComplex(
            new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(3))
        )).hasRe(BigDecimal.valueOf(2)).hasIm(BigDecimal.valueOf(3));
    }

    @Test
    void createAndSetRe() {
        Complex<BigDecimal> complex = new BigDecimalComplex();
        assertThatComplex(complex).hasReWithZero().hasImWithZero();
        complex.setRe(BigDecimal.ONE);
        assertThatComplex(complex).hasRe(BigDecimal.ONE).hasImWithZero();
    }

    @Test
    void createAndSetIm() {
        Complex<BigDecimal> complex = new BigDecimalComplex();
        assertThatComplex(complex).hasReWithZero().hasImWithZero();
        complex.setIm(BigDecimal.valueOf(2));
        assertThatComplex(complex).hasReWithZero().hasIm(BigDecimal.valueOf(2));
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
        assertThatComplex(new BigDecimalComplex()).hasAbsoluteValue(BigDecimal.ZERO);
    }

    @Test
    void absoluteValueOfComplexX1Y1() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE))
            .hasAbsoluteValue(BigDecimal.valueOf(Math.sqrt(2)));
    }

    @Test
    void absoluteValueOfComplexX1Y0() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO))
            .hasAbsoluteValue(BigDecimal.ONE);
    }

    @Test
    void argumentOfComplexWithoutParameter() {
        assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> new BigDecimalComplex().argument())
            .withMessage(ExceptionMessages.DIVISION_UNDEFINED);
    }

    @Test
    void argumentOfComplexX1Y1() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE))
            .hasArgumentCloseTo(BigDecimal.valueOf(Math.PI / 4));
    }

    @Test
    void argumentOfComplexX1Y0() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO))
            .hasArgument(BigDecimal.ZERO);
    }

    @Test
    void signumOfComplexWithoutParameter() {
        assertThatComplex(new BigDecimalComplex().signum()).isEqualTo(new BigDecimalComplex());
    }

    @Test
    void signumOfComplexX1Y1() {
        BigDecimal sqrt2half = BigDecimal.valueOf(Math.sqrt(2) / 2);
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).signum())
            .hasRe(sqrt2half).hasIm(sqrt2half);
    }

    @Test
    void signumOfComplexX1Y0() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO).signum())
            .isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO));
    }

    @Test
    void complexSignumOfComplexWithoutParameter() {
        assertThatComplex(new BigDecimalComplex()).hasComplexSignum(0);
    }

    @Test
    void complexSignumOfComplexX1Y1() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE))
            .hasComplexSignum(1);
    }

    @Test
    void complexSignumOfComplexXMinus1Y0() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE.negate(), BigDecimal.ZERO))
            .hasComplexSignum(-1);
    }

    @Test
    void complexSignumOfComplexX0Y1() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ZERO, BigDecimal.ONE))
            .hasComplexSignum(1);
    }

    @Test
    void complexSignumOfComplexX0YMinus1() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ZERO, BigDecimal.ONE.negate()))
            .hasComplexSignum(-1);
    }

    // endregion

    // region conjugation and reciprocal

    @Test
    void conjugationOfComplexWithRe1Im2() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)).conjugation())
            .isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2).negate()));
    }

    @Test
    void reciprocalOfComplexWithoutParameter() {
        assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> new BigDecimalComplex().reciprocal())
            .withMessage(ExceptionMessages.DIVISION_UNDEFINED);
    }

    @Test
    void reciprocalOfComplexX1Y1() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).reciprocal())
            .isEqualTo(new BigDecimalComplex(BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.5).negate()));
    }

    @Test
    void reciprocalOfComplexX1Y0() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO).reciprocal())
            .isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO));
    }

    // endregion

    // region add and subtract

    @Test
    void addWithXY() {
        assertThatComplex(new BigDecimalComplex().add(BigDecimal.valueOf(2), BigDecimal.valueOf(2)))
            .isEqualTo(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2)));
    }

    @Test
    void addWithXAndY() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE)
            .add(BigDecimal.ONE, BigDecimal.ONE)
        ).isEqualTo(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2)));
    }

    @Test
    void addWithBigDecimalComplex() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO)
            .add(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)))
        ).isEqualTo(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2)));
    }

    @Test
    void subtractWithXY() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
            .subtract(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
        ).isEqualTo(new BigDecimalComplex());
    }

    @Test
    void subtractWithXAndY() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
            .subtract(BigDecimal.ONE, BigDecimal.ONE)
        ).isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    void subtractWithBigDecimalComplex() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
            .subtract(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2))))
            .isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO));
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyZeroComplexWith1() {
        assertThatComplex(new BigDecimalComplex().multiply(BigDecimal.ONE))
            .isEqualTo(new BigDecimalComplex());
    }

    @Test
    void multiplyComplexWith0() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE)
            .multiply(BigDecimal.ZERO)).isEqualTo(new BigDecimalComplex());
    }

    @Test
    void multiplyComplexWith1() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE)
            .multiply(BigDecimal.ONE)).isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    void multiplyComplexWithMinus1() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.ONE)
            .multiply(BigDecimal.ONE.negate())
        ).isEqualTo(new BigDecimalComplex(
            BigDecimal.valueOf(2).negate(),
            BigDecimal.ONE.negate()
        ));
    }

    @Test
    void multiplyComplexWithBigDecimalComplex() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2))
            .multiply(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.ONE)))
            .isEqualTo(new BigDecimalComplex(BigDecimal.ZERO, BigDecimal.valueOf(5)));
    }

    @Test
    void divideZeroComplexWith1() {
        assertThatComplex(new BigDecimalComplex().divide(BigDecimal.ONE)).isEqualTo(new BigDecimalComplex());
    }

    @Test
    void divideComplexWith0() {
        assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE)
                .divide(BigDecimal.ZERO))
            .withMessage(ExceptionMessages.DIVISION_BY_ZERO);
    }

    @Test
    void divideComplexWith1() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).divide(BigDecimal.ONE))
            .isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE));
    }

    @Test
    void divideComplexWithMinus1() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.ONE)
            .divide(BigDecimal.ONE.negate())
        ).isEqualTo(new BigDecimalComplex(
            BigDecimal.valueOf(2).negate(),
            BigDecimal.ONE.negate()
        ));
    }

    @Test
    void divideComplexWithBigDecimalComplex() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2))
            .divide(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.ONE)))
            .isEqualTo(new BigDecimalComplex(BigDecimal.valueOf(0.8), BigDecimal.valueOf(0.6)));
    }

    // endregion

    // region pow and root

    @Test
    void pow8OfComplexWithRe1Im1() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).pow(8))
            .isEqualTo(new BigDecimalComplex(
                BigDecimal.valueOf(16),
                BigDecimal.valueOf(1.029198495793047e-14)
            ));
    }

    @Test
    void pow5OfComplexWithRe3Im4() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.valueOf(3), BigDecimal.valueOf(4)).pow(5))
            .isEqualTo(new BigDecimalComplex(
                BigDecimal.valueOf(-236.9999999999962),
                BigDecimal.valueOf(3116).negate()
            ));
    }

    @Test
    void root3OfComplexWithRe1Im0() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO).root(3)).isEqualTo(List.of(
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO),
            new BigDecimalComplex(
                BigDecimal.valueOf(-0.4999999999999994),
                BigDecimal.valueOf(0.8660254037844389)
            ),
            new BigDecimalComplex(
                BigDecimal.valueOf(-0.5000000000000012),
                BigDecimal.valueOf(-0.8660254037844379)
            )
        ));
    }

    @Test
    void root4OfComplexWithRe1Im0() {
        assertThat(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO).root(4)).isEqualTo(List.of(
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO),
            new BigDecimalComplex(
                BigDecimal.valueOf(7.273661547324616e-16),
                BigDecimal.ONE
            ),
            new BigDecimalComplex(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(1.454732309464923e-15)
            ),
            new BigDecimalComplex(
                BigDecimal.valueOf(-1.83697019872103e-16),
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
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)).inverseRe())
            .isEqualTo(new BigDecimalComplex(BigDecimal.ONE.negate(), BigDecimal.valueOf(2)));
    }

    @Test
    void inverseImOfComplexWithRe1Im2() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)).inverseIm())
            .isEqualTo(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2).negate()));
    }

    @Test
    void inverseReImOfComplexWithRe1Im2() {
        assertThatComplex(new BigDecimalComplex(
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
        assertThatComplex(
            BigDecimalComplex.of(BigDecimal.valueOf(3), BigDecimal.valueOf(50))
        ).hasRe(BigDecimal.valueOf(2.89489808547634))
            .hasIm(BigDecimal.valueOf(-0.7871245611117864));
    }

    @Test
    void ofWithSelfValidation() {
        Complex<BigDecimal> complex = BigDecimalComplex.of(
            BigDecimal.ONE, BigDecimal.valueOf(2)
        );
        assertThatComplex(complex)
            .hasAbsoluteValue(BigDecimal.ONE)
            .hasArgument(BigDecimal.valueOf(2));
    }

    @Test
    void asVectorOfComplexWithRe1Im2() {
        assertThatVector(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)).asVector())
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
        assertThatComplex(complex.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void isValidWithZero() {
        assertThatComplex(new BigDecimalComplex()).isValid();
    }

    @Test
    void copyOfComplexWithReIm() {
        assertCopyable(new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        ));
    }

    // endregion

    // region override

    @Test
    void equalsOfComplexWithReIm() {
        assertThatComplex(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
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
        assertThatComplex(complex).hasToString("2+3*i");
    }

    @Test
    void compareToOfComplexWithReIm() {
        Complex<BigDecimal> complex = new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        );
        assertThatComplex(complex)
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
}
