package io.rala.math.testUtils;

import io.rala.math.algebra.Matrix;

@SuppressWarnings("unused")
public class TestMatrix extends Matrix<Number> {
    public TestMatrix(int size) {
        super(size);
    }

    public TestMatrix(int rows, int cols) {
        super(rows, cols);
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

    @Override
    public Matrix<Number> add(Matrix<Number> matrix) {
        return matrix;
    }

    @Override
    public Matrix<Number> multiply(Matrix<Number> matrix) {
        return matrix;
    }

    @Override
    public Matrix<Number> transpose() {
        return null;
    }

    @Override
    public double determinante() {
        return 0;
    }

    @Override
    public Matrix<Number> copy() {
        return new TestMatrix(this);
    }
}
