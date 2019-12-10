package io.rala.math.testUtils.algebra;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;

@SuppressWarnings("unused")
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
}
