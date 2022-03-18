package io.rala.math.testUtils.assertion.algebra.numeric;

import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.testUtils.assertion.core.AbstractComparableAssert;
import org.assertj.core.api.AbstractDoubleAssert;
import org.assertj.core.data.Offset;

import static io.rala.math.testUtils.assertion.utils.OffsetUtils.doubleOffset;

@SuppressWarnings("UnusedReturnValue")
public class FractionAssert<T extends Number, V extends Number>
    extends AbstractComparableAssert<T, FractionAssert<T, V>, Fraction<T, V>> {

    public FractionAssert(Fraction<T, V> tFraction) {
        super(tFraction, FractionAssert.class);
    }

    /**
     * @see #hasNumerator(Number)
     */
    public FractionAssert<T, V> hasNumeratorWithOne() {
        isNotNull().hasArithmetic();
        return hasNumerator(getArithmetic().one());
    }

    /**
     * @see Fraction#getNumerator()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public FractionAssert<T, V> hasNumerator(T numerator) {
        isNotNull();
        assertThatNoArgumentIsNull(numerator);

        return assertNumberAsDouble(actual.getNumerator(), doubleAssert ->
            doubleAssert.as("fraction has numerator")
                .isCloseTo(numerator.doubleValue(), doubleOffset()));
    }

    /**
     * @see #hasDenominator(Number)
     */
    public FractionAssert<T, V> hasDenominatorWithOne() {
        isNotNull().hasArithmetic();
        return hasDenominator(getArithmetic().one());
    }

    /**
     * @see Fraction#getDenominator()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public FractionAssert<T, V> hasDenominator(T denominator) {
        isNotNull();
        assertThatNoArgumentIsNull(denominator);

        return assertNumberAsDouble(actual.getDenominator(), doubleAssert ->
            doubleAssert.as("fraction has denominator")
                .isCloseTo(denominator.doubleValue(), doubleOffset()));
    }

    @Override
    protected AbstractArithmetic<T> getArithmetic() {
        isNotNull();
        return actual.getArithmetic().getTArithmetic();
    }
}
