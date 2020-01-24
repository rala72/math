package io.rala.math.algebra.numeric;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.assertion.NumericAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

class BigDecimalComplexTest {
    private static final MathContext CONTEXT = new MathContext(10);

    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertComplex(new BigDecimalComplex());
    }

    @Test
    void constructorWithContext() {
        assertComplex(new BigDecimalComplex(new MathContext(5)));
    }

    @Test
    void constructorWithDifferentReImParameter() {
        assertComplex(new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        ), BigDecimal.valueOf(2), BigDecimal.valueOf(3));
    }

    @Test
    void constructorWithDifferentReImParameterAndContext() {
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
        Assertions.assertEquals(1, new BigDecimalComplex(
            BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2)).intValue()
        );
    }

    @Test
    void longValueOfComplexWithX1_1Y2_2() {
        Assertions.assertEquals(1, new BigDecimalComplex(
            BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2)).longValue()
        );
    }

    @Test
    void floatValueOfComplexWithX1_1Y2_2() {
        Assertions.assertEquals((float) 1.1, new BigDecimalComplex(
            BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2)).floatValue()
        );
    }

    @Test
    void doubleValueOfComplexWithX1_1Y2_2() {
        Assertions.assertEquals(1.1, new BigDecimalComplex(
            BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2)).doubleValue()
        );
    }

    // endregion

    // region absoluteValue, argument and signum

    @Test
    void absoluteValueOfComplexWithoutParameter() {
        Assertions.assertEquals(
            BigDecimal.ZERO,
            new BigDecimalComplex().absoluteValue()
        );
    }

    @Test
    void absoluteValueOfComplexX1Y1() {
        Assertions.assertEquals(BigDecimal.valueOf(2).sqrt(CONTEXT),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).absoluteValue()
        );
    }

    @Test
    void absoluteValueOfComplexX1Y0() {
        Assertions.assertEquals(BigDecimal.ONE, new BigDecimalComplex(
            BigDecimal.ONE, BigDecimal.ZERO).absoluteValue()
        );
    }

    @Test
    void argumentOfComplexWithoutParameter() {
        Assertions.assertThrows(ArithmeticException.class,
            () -> new BigDecimalComplex().argument()
        ); // assert exception message?
    }

    @Test
    void argumentOfComplexX1Y1() {
        Assertions.assertEquals(
            BigDecimal.valueOf(0.7853981631), // PI/4
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).argument()
        );
    }

    @Test
    void argumentOfComplexX1Y0() {
        Assertions.assertEquals(BigDecimal.ZERO, new BigDecimalComplex(
            BigDecimal.ONE, BigDecimal.ZERO).argument()
        );
    }

    @Test
    void signumOfComplexWithoutParameter() {
        Assertions.assertEquals(new BigDecimalComplex(),
            new BigDecimalComplex().signum()
        );
    }

    @Test
    void signumOfComplexX1Y1() {
        BigDecimal sqrt2half = BigDecimal.valueOf(0.7071067814);
        // Math.sqrt(2d) / 2d
        Assertions.assertEquals(new BigDecimalComplex(sqrt2half, sqrt2half),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).signum()
        );
    }

    @Test
    void signumOfComplexX1Y0() {
        Assertions.assertEquals(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO).signum()
        );
    }

    @Test
    void complexSignumOfComplexWithoutParameter() {
        Assertions.assertEquals(0,
            new BigDecimalComplex().complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX1Y1() {
        Assertions.assertEquals(1,
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexXMinus1Y0() {
        Assertions.assertEquals(-1,
            new BigDecimalComplex(BigDecimal.ONE.negate(), BigDecimal.ZERO).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX0Y1() {
        Assertions.assertEquals(1,
            new BigDecimalComplex(BigDecimal.ZERO, BigDecimal.ONE).complexSignum()
        );
    }

    @Test
    void complexSignumOfComplexX0YMinus1() {
        Assertions.assertEquals(-1,
            new BigDecimalComplex(BigDecimal.ZERO, BigDecimal.ONE.negate()).complexSignum()
        );
    }

    // endregion

    // region conjugation and reciprocal

    @Test
    void conjugationOfComplexWithRe1Im2() {
        Assertions.assertEquals(
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2).negate()),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)).conjugation()
        );
    }

    @Test
    void reciprocalOfComplexWithoutParameter() {
        Assertions.assertThrows(ArithmeticException.class,
            () -> new BigDecimalComplex().reciprocal()
        ); // assert exception message?
    }

    @Test
    void reciprocalOfComplexX1Y1() {
        Assertions.assertEquals(new BigDecimalComplex(
                BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.5).negate()
            ),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).reciprocal()
        );
    }

    @Test
    void reciprocalOfComplexX1Y0() {
        Assertions.assertEquals(new BigDecimalComplex(
                BigDecimal.ONE, BigDecimal.ZERO
            ),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO).reciprocal()
        );
    }

    // endregion

    // region add and subtract

    @Test
    void addWithXY() {
        Assertions.assertEquals(
            new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2)),
            new BigDecimalComplex().add(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
        );
    }

    @Test
    void addWithXAndY() {
        Assertions.assertEquals(
            new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2)),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE)
                .add(BigDecimal.ONE, BigDecimal.ONE)
        );
    }

    @Test
    void addWithBigDecimalComplex() {
        Assertions.assertEquals(new BigDecimalComplex(
                BigDecimal.valueOf(2), BigDecimal.valueOf(2)
            ),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO)
                .add(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)))
        );
    }

    @Test
    void subtractWithXY() {
        Assertions.assertEquals(
            new BigDecimalComplex(),
            new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
                .subtract(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
        );
    }

    @Test
    void subtractWithXAndY() {
        Assertions.assertEquals(
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE),
            new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
                .subtract(BigDecimal.ONE, BigDecimal.ONE)
        );
    }

    @Test
    void subtractWithBigDecimalComplex() {
        Assertions.assertEquals(
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO),
            new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(2))
                .subtract(new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)))
        );
    }

    // endregion

    // region multiply and divide

    @Test
    void multiplyZeroComplexWith1() {
        Assertions.assertEquals(
            new BigDecimalComplex(),
            new BigDecimalComplex().multiply(BigDecimal.ONE)
        );
    }

    @Test
    void multiplyComplexWith0() {
        Assertions.assertEquals(
            new BigDecimalComplex(),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE)
                .multiply(BigDecimal.ZERO)
        );
    }

    @Test
    void multiplyComplexWith1() {
        Assertions.assertEquals(
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE)
                .multiply(BigDecimal.ONE)
        );
    }

    @Test
    void multiplyComplexWithMinus1() {
        Assertions.assertEquals(
            new BigDecimalComplex(
                BigDecimal.valueOf(2).negate(),
                BigDecimal.ONE.negate()
            ),
            new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.ONE)
                .multiply(BigDecimal.ONE.negate())
        );
    }

    @Test
    void multiplyComplexWithBigDecimalComplex() {
        Assertions.assertEquals(
            new BigDecimalComplex(BigDecimal.ZERO, BigDecimal.valueOf(5)),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2))
                .multiply(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.ONE))
        );
    }

    @Test
    void divideZeroComplexWith1() {
        Assertions.assertEquals(
            new BigDecimalComplex(),
            new BigDecimalComplex().divide(BigDecimal.ONE)
        );
    }

    @Test
    void divideComplexWith0() {
        Assertions.assertThrows(ArithmeticException.class,
            () -> new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE)
                .divide(BigDecimal.ZERO)
        ); // assert exception message?
    }

    @Test
    void divideComplexWith1() {
        Assertions.assertEquals(
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).divide(BigDecimal.ONE)
        );
    }

    @Test
    void divideComplexWithMinus1() {
        Assertions.assertEquals(new BigDecimalComplex(
                BigDecimal.valueOf(2).negate(),
                BigDecimal.ONE.negate()
            ),
            new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.ONE)
                .divide(BigDecimal.ONE.negate())
        );
    }

    @Test
    void divideComplexWithBigDecimalComplex() {
        Assertions.assertEquals(
            new BigDecimalComplex(BigDecimal.valueOf(0.8), BigDecimal.valueOf(0.6)),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2))
                .divide(new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.ONE))
        );
    }

    // endregion

    // region pow and root

    @Test
    void pow8OfComplexWithRe1Im1() {
        Assertions.assertEquals(
            new BigDecimalComplex(
                BigDecimal.valueOf(15.99999997),
                BigDecimal.valueOf(-3.487338448E-8)
            ),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ONE).pow(8)
        );
    }

    @Test
    void pow5OfComplexWithRe3Im4() {
        Assertions.assertEquals(
            new BigDecimalComplex(
                BigDecimal.valueOf(237).negate(),
                BigDecimal.valueOf(3116).negate()
            ),
            new BigDecimalComplex(BigDecimal.valueOf(3), BigDecimal.valueOf(4)).pow(5)
        );
    }

    @Test
    void root3OfComplexWithRe1Im0() {
        Assertions.assertEquals(
            List.of(
                new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO),
                new BigDecimalComplex(
                    BigDecimal.valueOf(0.5000000005).negate(),
                    BigDecimal.valueOf(0.8660254035)
                ),
                new BigDecimalComplex(
                    BigDecimal.valueOf(0.4999999981).negate(),
                    BigDecimal.valueOf(0.8660254049).negate()
                )
            ),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO).root(3)
        );
    }

    @Test
    void root4OfComplexWithRe1Im0() {
        Assertions.assertEquals(
            List.of(
                new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO),
                new BigDecimalComplex(
                    BigDecimal.valueOf(2.051034285E-10).negate(),
                    BigDecimal.ONE
                ),
                new BigDecimalComplex(
                    BigDecimal.ONE.negate(),
                    BigDecimal.valueOf(1.41020694E-9).negate()
                ),
                new BigDecimalComplex(
                    BigDecimal.valueOf(3.846897972E-10).negate(),
                    BigDecimal.ONE.negate()
                )
            ),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.ZERO).root(4)
        );
    }

    @Test
    void root2OfComplexWithReMinus1ImSqrt3() {
        Assertions.assertEquals(
            List.of(
                new BigDecimalComplex(
                    BigDecimal.valueOf(0.7071067813),
                    BigDecimal.valueOf(1.224744871)
                ),
                new BigDecimalComplex(
                    BigDecimal.valueOf(0.7071067807).negate(),
                    BigDecimal.valueOf(1.224744871).negate()
                )
            ),
            new BigDecimalComplex(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(Math.sqrt(3))
            ).root(2)
        );
    }

    // endregion

    // region inverse

    @Test
    void inverseReOfComplexWithRe1Im2() {
        Assertions.assertEquals(
            new BigDecimalComplex(BigDecimal.ONE.negate(), BigDecimal.valueOf(2)),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)).inverseRe()
        );
    }

    @Test
    void inverseImOfComplexWithRe1Im2() {
        Assertions.assertEquals(
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2).negate()),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)).inverseIm()
        );
    }

    @Test
    void inverseReImOfComplexWithRe1Im2() {
        Assertions.assertEquals(
            new BigDecimalComplex(
                BigDecimal.ONE.negate(),
                BigDecimal.valueOf(2).negate()
            ),
            new BigDecimalComplex(
                BigDecimal.ONE,
                BigDecimal.valueOf(2)
            ).inverse()
        );
    }

    // endregion

    // region static of, asVector, static ofVector

    @Test
    void ofAb3AndAr50() {
        assertComplex(
            BigDecimalComplex.of(BigDecimal.valueOf(3), BigDecimal.valueOf(50)),
            BigDecimal.valueOf(2.894898086),
            BigDecimal.valueOf(0.7871245611).negate()
        );
    }

    @Test
    void ofWithSelfValidation() {
        Complex<BigDecimal> complex = BigDecimalComplex.of(
            BigDecimal.ONE, BigDecimal.valueOf(2)
        );
        Assertions.assertEquals(BigDecimal.ONE, complex.absoluteValue());
        Assertions.assertEquals(BigDecimal.valueOf(2), complex.argument());
    }

    @Test
    void asVectorOfComplexWithRe1Im2() {
        Assertions.assertEquals(new Vector(1, 2),
            new BigDecimalComplex(BigDecimal.ONE, BigDecimal.valueOf(2)).asVector()
        );
    }

    // endregion

    // region map

    @Test
    void mapOfComplexWithR0_5Im1_5() {
        BigDecimalComplex complex = new BigDecimalComplex(
            BigDecimal.valueOf(0.5),
            BigDecimal.valueOf(1.5)
        );
        Complex<Integer> result =
            new Complex<>(new IntegerArithmetic(), 0, 1);
        Assertions.assertEquals(result,
            complex.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    // endregion

    // region isValid and copy

    @Test
    void isValidWithZero() {
        Assertions.assertTrue(new BigDecimalComplex().isValid());
    }

    @Test
    void copyOfComplexWithReIm() {
        Complex<BigDecimal> complex = new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        );
        Assertions.assertEquals(complex, complex.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfComplexWithReIm() {
        Complex<BigDecimal> complex = new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        );
        Assertions.assertEquals(
            complex,
            new BigDecimalComplex(BigDecimal.valueOf(2), BigDecimal.valueOf(3))
        );
        Assertions.assertNotEquals(
            complex,
            new BigDecimalComplex(BigDecimal.valueOf(3), BigDecimal.valueOf(2))
        );
    }

    @Test
    void hashCodeOfComplexWithReIm() {
        Assertions.assertEquals(
            2976,
            new BigDecimalComplex(
                BigDecimal.valueOf(2), BigDecimal.valueOf(3)
            ).hashCode()
        );
    }

    @Test
    void toStringOfComplexWithReIm() {
        Complex<BigDecimal> complex = new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        );
        Assertions.assertEquals(
            "2+3*i",
            complex.toString()
        );
    }

    @Test
    void compareToOfComplexWithReIm() {
        Complex<BigDecimal> complex = new BigDecimalComplex(
            BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        );
        Assertions.assertEquals(
            0, complex.compareTo(new BigDecimalComplex(
                BigDecimal.valueOf(2), BigDecimal.valueOf(3)
            ))
        );
        Assertions.assertEquals(
            -1, complex.compareTo(new BigDecimalComplex(
                BigDecimal.valueOf(3), BigDecimal.ONE
            ))
        );
        Assertions.assertEquals(
            1, complex.compareTo(new BigDecimalComplex(
                BigDecimal.valueOf(2), BigDecimal.ONE
            ))
        );
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
            new BigDecimalComplex(),
            BigDecimalComplex.class
        );
    }

    // endregion


    // region assert

    private static void assertComplex(Complex<BigDecimal> complex) {
        assertComplex(complex, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    private static void assertComplex(Complex<BigDecimal> complex, BigDecimal re, BigDecimal im) {
        NumericAssertions.assertComplex(complex, re, im);
    }

    // endregion
}
