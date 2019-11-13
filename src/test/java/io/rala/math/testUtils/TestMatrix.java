package io.rala.math.testUtils;

import io.rala.math.algebra.Matrix;

@SuppressWarnings("unused")
public class TestMatrix extends Matrix<Number> {
    // region constructors

    public TestMatrix(int size) {
        super(size, 0);
    }

    public TestMatrix(int rows, int cols) {
        super(rows, cols, 0);
    }

    public TestMatrix(int size, Number defaultValue) {
        super(size, defaultValue);
    }

    public TestMatrix(int rows, int cols, Number defaultValue) {
        super(rows, cols, defaultValue);
    }

    public TestMatrix(Matrix<Number> matrix) {
        super(matrix);
    }

    // endregion

    /**
     * <b>only public for testing purpose - in general <code>protected</code></b>
     */
    @Override
    public Matrix<Number> newInstance(int rows, int cols) {
        return new TestMatrix(rows, cols);
    }

    @Override
    protected Number fromInt(int a) {
        return a;
    }

    @Override
    protected Number sum(Number a, Number b) {
        return a.doubleValue() + b.doubleValue();
    }

    @Override
    protected Number difference(Number a, Number b) {
        return a.doubleValue() - b.doubleValue();
    }

    @Override
    protected Number product(Number a, Number b) {
        return a.doubleValue() * b.doubleValue();
    }

    @Override
    public Matrix<Number> copy() {
        return new TestMatrix(this);
    }
}
