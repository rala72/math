package io.rala.math.testUtils.algebra;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;

public class TestMatrix extends Matrix<Number> {
    // region constructors

    public TestMatrix(int size) {
        super(new TestAbstractArithmetic(), size, 0d);
    }

    public TestMatrix(int rows, int cols) {
        super(new TestAbstractArithmetic(), rows, cols, 0d);
    }

    public TestMatrix(int size, Number defaultValue) {
        super(new TestAbstractArithmetic(), size, defaultValue);
    }

    public TestMatrix(int rows, int cols, Number defaultValue) {
        super(new TestAbstractArithmetic(), rows, cols, defaultValue);
    }

    public TestMatrix(Matrix<Number> matrix) {
        super(matrix);
    }

    // endregion

    // region static: identity and diagonal

    public static TestMatrix identity(int size) {
        return new TestMatrix(
            Matrix.identity(new TestAbstractArithmetic(), size, 0d)
        );
    }

    public static TestMatrix diagonal(Number... values) {
        return new TestMatrix(
            Matrix.diagonal(new TestAbstractArithmetic(), 0d, values)
        );
    }

    // endregion

    // region static: of

    public static TestMatrix ofValuesByRows(int rows, Number... values) {
        return new TestMatrix(
            Matrix.ofValuesByRows(new TestAbstractArithmetic(),
                0d, rows, values
            )
        );
    }

    public static TestMatrix ofValuesByCols(int cols, Number... values) {
        return new TestMatrix(
            Matrix.ofValuesByCols(new TestAbstractArithmetic(),
                0d, cols, values
            )
        );
    }

    // endregion
}
