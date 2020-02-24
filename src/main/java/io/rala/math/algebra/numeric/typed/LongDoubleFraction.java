package io.rala.math.algebra.numeric.typed;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.result.LongDoubleResultArithmetic;

/**
 * class which holds fraction values as {@link Long}
 * and calculates its actual value as {@link Double}
 */
public class LongDoubleFraction extends Fraction<Long, Double> {
    // region constructors

    /**
     * default denominator is {@code 1}
     *
     * @param numerator numerator of fraction
     * @see Fraction#Fraction(io.rala.math.arithmetic.AbstractResultArithmetic, Number)
     */
    public LongDoubleFraction(long numerator) {
        super(LongDoubleResultArithmetic.getInstance(), numerator);
    }

    /**
     * creates a new fraction with numerator and denominator
     *
     * @param numerator   numerator of fraction
     * @param denominator denominator of fraction
     * @see Fraction#Fraction(AbstractResultArithmetic, Number, Number)
     */
    public LongDoubleFraction(long numerator, long denominator) {
        super(LongDoubleResultArithmetic.getInstance(), numerator, denominator);
    }

    /**
     * creates a new fraction based on given one
     *
     * @param fraction fraction to copy
     */
    public LongDoubleFraction(LongDoubleFraction fraction) {
        super(fraction);
    }

    // endregion
}
