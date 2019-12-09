package io.rala.math.testUtils;

import io.rala.math.algebra.Matrix;

@SuppressWarnings("unused")
public class TestMatrix extends Matrix<Number> {
    // region constructors and newInstance

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

    /**
     * <b>only public for testing purpose - in general <code>protected</code></b>
     */
    @Override
    public Matrix<Number> newInstance(int rows, int cols) {
        return new TestMatrix(rows, cols);
    }

    // endregion

    // region override

    @Override
    public Matrix<Number> copy() {
        return new TestMatrix(this);
    }

    // endregion
}
