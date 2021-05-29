package io.rala.math.testUtils.algebra;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.jetbrains.annotations.NotNull;

public class TestMatrix extends Matrix<Number> {
    // region constructors

    public TestMatrix(int size) {
        super(new TestAbstractArithmetic(), size, 0d);
    }

    public TestMatrix(int size, @NotNull Number defaultValue) {
        super(new TestAbstractArithmetic(), size, defaultValue);
    }

    public TestMatrix(int rows, int cols) {
        super(new TestAbstractArithmetic(), rows, cols, 0d);
    }

    public TestMatrix(int rows, int cols, @NotNull Number defaultValue) {
        super(new TestAbstractArithmetic(), rows, cols, defaultValue);
    }

    public TestMatrix(@NotNull Matrix<Number> matrix) {
        super(matrix);
    }

    // endregion

    // region static: identity and diagonal

    @NotNull
    public static TestMatrix identity(int size) {
        return new TestMatrix(
            Matrix.identity(new TestAbstractArithmetic(), size)
        );
    }

    @NotNull
    public static TestMatrix diagonal(@NotNull Number... values) {
        return new TestMatrix(
            Matrix.diagonal(new TestAbstractArithmetic(), values)
        );
    }

    // endregion

    // region static: of

    @NotNull
    public static TestMatrix ofValuesByRows(int rows, @NotNull Number... values) {
        return new TestMatrix(
            Matrix.ofValuesByRows(new TestAbstractArithmetic(),
                0d, rows, values
            )
        );
    }

    @NotNull
    public static TestMatrix ofValuesByCols(int cols, @NotNull Number... values) {
        return new TestMatrix(
            Matrix.ofValuesByCols(new TestAbstractArithmetic(),
                0d, cols, values
            )
        );
    }

    // endregion

    /**
     * implements a shortcut of {@link #getIndexOfRowAndCol(int, int)}
     * but without exception handling for public test access
     *
     * @see Matrix#getIndexOfRowAndCol(int, int)
     */
    public static long getIndexOfRowAndCol(@NotNull Matrix<?> matrix, int row, int col) {
        return (long) row * matrix.getCols() + col;
    }
}
