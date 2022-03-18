package io.rala.math.testUtils.assertion.geometry;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.geometry.Vector;
import io.rala.math.testUtils.assertion.core.AbstractComparableAssert;
import org.assertj.core.api.AbstractDoubleAssert;
import org.assertj.core.data.Offset;

import static io.rala.math.testUtils.assertion.utils.OffsetUtils.doubleOffset;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class VectorAssert<T extends Number> extends AbstractComparableAssert<T, VectorAssert<T>, Vector<T>> {
    public VectorAssert(Vector<T> vector) {
        super(vector, VectorAssert.class);
    }

    /**
     * @see #hasXY(Number)
     */
    public VectorAssert<T> hasZeroXY() {
        isNotNull().hasArithmetic();
        return hasXY(actual.getArithmetic().zero());
    }

    /**
     * @see #hasX(Number)
     * @see #hasY(Number)
     */
    public VectorAssert<T> hasXY(T xy) {
        isNotNull();
        return hasX(xy).hasY(xy);
    }

    /**
     * @see Vector#getX()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public VectorAssert<T> hasX(T x) {
        isNotNull();
        assertThatNoArgumentIsNull(x);

        return assertNumberAsDouble(actual.getX(), doubleAssert ->
            doubleAssert.as("vector has x")
                .isCloseTo(x.doubleValue(), doubleOffset()));
    }

    /**
     * @see Vector#getY()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public VectorAssert<T> hasY(T y) {
        isNotNull();
        assertThatNoArgumentIsNull(y);

        return assertNumberAsDouble(actual.getY(), doubleAssert ->
            doubleAssert.as("vector has y")
                .isCloseTo(y.doubleValue(), doubleOffset()));
    }

    /**
     * @see Vector#length()
     */
    public VectorAssert<T> hasLength(T length) {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.length()), objectAssert ->
            objectAssert.as("vector has length")
                .isEqualTo(length));
    }

    /**
     * @see Vector#length()
     */
    public VectorAssert<T> hasLengthCloseTo(T length) {
        isNotNull();
        assertThatNoArgumentIsNull(length);

        return assertNumberAsDouble(actual.length(), doubleAssert ->
            doubleAssert.as("vector has length")
                .isCloseTo(length.doubleValue(), doubleOffset()));
    }

    /**
     * @see Vector#isZeroVector()
     */
    public VectorAssert<T> isZeroVector() {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.isZeroVector()), booleanAssert ->
            booleanAssert.as("vector is zeroVector").isTrue());
    }

    /**
     * @see Vector#isZeroVector()
     */
    public VectorAssert<T> isNoZeroVector() {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.isZeroVector()), booleanAssert ->
            booleanAssert.as("vector is no zeroVector").isFalse());
    }

    @Override
    protected AbstractArithmetic<T> getArithmetic() {
        return actual.getArithmetic();
    }
}
