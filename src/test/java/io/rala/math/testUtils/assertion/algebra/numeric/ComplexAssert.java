package io.rala.math.testUtils.assertion.algebra.numeric;

import io.rala.math.algebra.numeric.Complex;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.testUtils.assertion.core.AbstractComparableAssert;
import org.assertj.core.api.AbstractDoubleAssert;
import org.assertj.core.data.Offset;

import static io.rala.math.testUtils.assertion.utils.OffsetUtils.doubleOffset;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class ComplexAssert<T extends Number>
    extends AbstractComparableAssert<T, ComplexAssert<T>, Complex<T>> {

    public ComplexAssert(Complex<T> tComplex) {
        super(tComplex, ComplexAssert.class);
    }

    /**
     * @see #hasRe(Number)
     */
    public ComplexAssert<T> hasReWithZero() {
        isNotNull().hasArithmetic();
        return hasRe(getArithmetic().zero());
    }

    /**
     * @see Complex#getRe()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public ComplexAssert<T> hasRe(T re) {
        isNotNull();
        assertThatNoArgumentIsNull(re);

        return assertNumberAsDouble(actual.getRe(), doubleAssert ->
            doubleAssert.as("complex has re")
                .isCloseTo(re.doubleValue(), doubleOffset()));
    }

    /**
     * @see #hasIm(Number)
     */
    public ComplexAssert<T> hasImWithZero() {
        isNotNull().hasArithmetic();
        return hasIm(getArithmetic().zero());
    }

    /**
     * @see Complex#getIm()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public ComplexAssert<T> hasIm(T im) {
        isNotNull();
        assertThatNoArgumentIsNull(im);

        return assertNumberAsDouble(actual.getIm(), doubleAssert ->
            doubleAssert.as("complex has im")
                .isCloseTo(im.doubleValue(), doubleOffset()));
    }

    /**
     * @see Complex#absoluteValue()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public ComplexAssert<T> hasAbsoluteValue(T absoluteValue) {
        isNotNull();
        assertThatNoArgumentIsNull(absoluteValue);

        return assertNumberAsDouble(actual.absoluteValue(), doubleAssert ->
            doubleAssert.as("complex has absoluteValue")
                .isCloseTo(absoluteValue.doubleValue(), doubleOffset()));
    }

    /**
     * @see Complex#argument()
     */
    public ComplexAssert<T> hasArgument(T argument) {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.argument()), objectAssert ->
            objectAssert.as("complex has argument").isEqualTo(argument));
    }

    /**
     * @see Complex#argument()
     */
    public ComplexAssert<T> hasArgumentCloseTo(T argument) {
        isNotNull();
        assertThatNoArgumentIsNull(argument);

        return assertNumberAsDouble(actual.argument(), doubleAssert ->
            doubleAssert.as("complex has argument")
                .isCloseTo(argument.doubleValue(), doubleOffset()));
    }

    /**
     * @see Complex#complexSignum()
     */
    public ComplexAssert<T> hasComplexSignum(double complexSignum) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.complexSignum()), doubleAssert ->
            doubleAssert.as("complex has complexSignum").isEqualTo(complexSignum));
    }

    @Override
    protected AbstractArithmetic<T> getArithmetic() {
        isNotNull();
        return actual.getArithmetic();
    }
}
