package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.result.BigIntegerBigDecimalResultArithmetic;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * class which holds fraction values as {@link BigInteger}
 * and calculates its actual value as {@link BigDecimal}
 *
 * @since 1.0.0
 */
public class BigIntegerBigDecimalFraction extends Fraction<BigInteger, BigDecimal> {
    // region constructors

    /**
     * default denominator is {@code 1}
     *
     * @param numerator numerator of fraction
     * @see Fraction#Fraction(io.rala.math.arithmetic.AbstractResultArithmetic, Number)
     * @since 1.0.0
     */
    public BigIntegerBigDecimalFraction(BigInteger numerator) {
        super(BigIntegerBigDecimalResultArithmetic.getInstance(), numerator);
    }

    /**
     * creates a new fraction with numerator and denominator
     *
     * @param numerator   numerator of fraction
     * @param denominator denominator of fraction
     * @see Fraction#Fraction(AbstractResultArithmetic, Number, Number)
     * @since 1.0.0
     */
    public BigIntegerBigDecimalFraction(BigInteger numerator, BigInteger denominator) {
        super(BigIntegerBigDecimalResultArithmetic.getInstance(), numerator, denominator);
    }

    /**
     * creates a new fraction based on given one
     *
     * @param fraction fraction to copy
     * @since 1.0.0
     */
    public BigIntegerBigDecimalFraction(Fraction<BigInteger, BigDecimal> fraction) {
        super(fraction);
    }

    // endregion
}
