package io.rala.math.testUtils.assertion;

import io.rala.math.algebra.vector.Vector;
import io.rala.math.arithmetic.AbstractArithmetic;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(size, vector.getSize(), "size is invalid");
        assertEquals(type, vector.getType(), "type is invalid");
        assertEquals(Vector.Type.ROW.equals(type), vector.isRow(), "isRow is invalid");
        assertEquals(Vector.Type.COLUMN.equals(type), vector.isColumn(), "isRow is invalid");
    }
}
