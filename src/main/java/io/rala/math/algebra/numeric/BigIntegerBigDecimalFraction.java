package io.rala.math.algebra.numeric;

import io.rala.math.arithmetic.result.BigIntegerBigDecimalResultArithmetic;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * class which holds fraction values as {@link BigInteger}
 * and calculates its actual value as {@link BigDecimal}
 */
@SuppressWarnings("unused")
public class BigIntegerBigDecimalFraction extends Fraction<BigInteger, BigDecimal> {
    // region constructors

    /**
     * default denominator is <code>1</code>
     *
     * @param numerator numerator of fraction
     * @see Fraction#Fraction(io.rala.math.arithmetic.AbstractResultArithmetic, Number)
     */
    public BigIntegerBigDecimalFraction(BigInteger numerator) {
        super(new BigIntegerBigDecimalResultArithmetic(), numerator);
    }

    /**
     * creates a new fraction with numerator and denominator
     *
     * @param numerator   numerator of fraction
     * @param denominator denominator of fraction
     * @see Fraction#Fraction(io.rala.math.arithmetic.AbstractResultArithmetic, Number, Number)
     */
    public BigIntegerBigDecimalFraction(BigInteger numerator, BigInteger denominator) {
        super(new BigIntegerBigDecimalResultArithmetic(), numerator, denominator);
    }

    /**
     * creates a new fraction based on given one
     *
     * @param fraction fraction to copy
     */
    public BigIntegerBigDecimalFraction(BigIntegerBigDecimalFraction fraction) {
        super(fraction);
    }

    // endregion
}