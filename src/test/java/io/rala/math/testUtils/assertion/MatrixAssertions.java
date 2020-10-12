package io.rala.math.testUtils.assertion;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.arithmetic.AbstractArithmetic;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * assertions for {@link io.rala.math.algebra.matrix} package
 */
public class MatrixAssertions {
    private MatrixAssertions() {
    }

    /**
     * @see Matrix#Matrix(AbstractArithmetic, int)
     * @see #assertMatrix(Matrix, int, int)
     */
    public static <T extends Number> void assertMatrix(Matrix<T> matrix, int size) {
        assertMatrix(matrix, size, size);
    }

    /**
     * asserts that matrix has expected values
     */
    public static <T extends Number> void assertMatrix(
        Matrix<T> matrix, int rows, int cols
    ) {
        assertEquals(rows, matrix.getRows(), "rows is invalid");
        assertEquals(cols, matrix.getCols(), "cols is invalid");
    }
}
