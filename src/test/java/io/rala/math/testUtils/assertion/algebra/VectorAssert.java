package io.rala.math.testUtils.assertion.algebra;

import io.rala.math.algebra.vector.Vector;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.testUtils.assertion.core.AbstractArithmeticAssert;
import org.assertj.core.api.AbstractDoubleAssert;
import org.assertj.core.data.Offset;

import static io.rala.math.testUtils.assertion.utils.OffsetUtils.doubleOffset;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class VectorAssert<T extends Number> extends AbstractArithmeticAssert<T, VectorAssert<T>, Vector<T>> {
    public VectorAssert(Vector<T> fields) {
        super(fields, VectorAssert.class);
    }

    /**
     * @see Vector#getSize()
     */
    public VectorAssert<T> hasSize(long size) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.getSize()), longAssert ->
            longAssert.as("vector has size").isEqualTo(size));
    }

    /**
     * @see Vector#getSize()
     */
    public VectorAssert<T> hasType(Vector.Type type) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.getType()), objectAssert ->
            objectAssert.as("vector has type").isEqualTo(type));
    }

    /**
     * @see Vector#length()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public VectorAssert<T> hasLength(T length) {
        isNotNull();
        assertThatNoArgumentIsNull(length);

        return assertNumberAsDouble(actual.length(), doubleAssert ->
            doubleAssert.as("vector has length")
                .isCloseTo(length.doubleValue(), doubleOffset()));
    }

    @Override
    protected AbstractArithmetic<T> getArithmetic() {
        isNotNull();
        return actual.getArithmetic();
    }
}
