package io.rala.math.testUtils.assertion;

import io.rala.math.algebra.vector.Vector;
import io.rala.math.arithmetic.AbstractArithmetic;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * assertions for {@link io.rala.math.algebra.vector} package
 */
public class VectorAssertions {
    private VectorAssertions() {
    }

    /**
     * @see Vector#Vector(AbstractArithmetic, int)
     * @see #assertVector(Vector, int, Vector.Type)
     */
    public static <T extends Number> void assertVector(Vector<T> vector, int size) {
        assertVector(vector, size, Vector.Type.COLUMN);
    }

    /**
     * asserts that vector has expected values
     */
    public static <T extends Number> void assertVector(
        Vector<T> vector, int size, Vector.Type type
    ) {
        assertThat(vector.getSize()).as("size is invalid").isEqualTo(size);
        assertThat(vector.getType()).as("type is invalid").isEqualTo(type);
        assertThat(vector.isRow()).as("isRow is invalid").isEqualTo(Vector.Type.ROW.equals(type));
        assertThat(vector.isColumn()).as("isRow is invalid").isEqualTo(Vector.Type.COLUMN.equals(type));
    }
}
