package io.rala.math.algebra;

/**
 * class which holds a matrix with <code>rows</code> and <code>cols</code>
 * storing {@link Double}
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class DoubleMatrix extends Matrix<Double> {
    // region constructor

    /**
     * default value is <code>0</code>
     *
     * @param size size of matrix
     * @see Matrix#Matrix(int, Number)
     */
    public DoubleMatrix(int size) {
        super(size, 0d);
    }

    /**
     * default value is <code>0</code>
     *
     * @param rows rows of matrix
     * @param cols cols of matrix
     * @see Matrix#Matrix(int, int, Number)
     */
    public DoubleMatrix(int rows, int cols) {
        super(rows, cols, 0d);
    }

    /**
     * creates a new matrix based on given one<br>
     * default value is <code>0</code>
     *
     * @param matrix matrix to copy
     */
    public DoubleMatrix(Matrix<Double> matrix) {
        super(matrix);
    }

    // endregion

    // region abstract

    @Override
    public Matrix<Double> add(Matrix<Double> matrix) {
        // move assertions to matrix?
        if (getRows() != matrix.getRows())
            throw new IllegalArgumentException("rows have to be equal");
        if (getCols() != matrix.getCols())
            throw new IllegalArgumentException("cols have to be equal");
        DoubleMatrix result = new DoubleMatrix(getRows(), getCols());
        result.getMatrix().putAll(getMatrix());
        for (int i = 0; i < size(); i++)
            result.getMatrix().merge(i, result.getDefaultValue(), Double::sum);
        return result;
    }

    @Override
    public Matrix<Double> multiply(Matrix<Double> matrix) {
        if (getCols() != matrix.getRows())
            throw new IllegalArgumentException("cols have to be equal to parameter rows");
        DoubleMatrix result = new DoubleMatrix(getRows(), matrix.getCols());
        for (int r = 0; r < result.getRows(); r++)
            for (int c = 0; c < result.getCols(); c++) {
                double d = 0;
                for (int i = 0; i < getRows(); i++)
                    d += getValue(r, i) * matrix.getValue(i, c);
                result.setValue(r, c, d);
            }
        return result;
    }

    @Override
    public Matrix<Double> transpose() {
        DoubleMatrix result = new DoubleMatrix(getCols(), getRows());
        for (int i = size() - 1; 0 <= i; i--)
            result.setValue(size() - 1 - i, getValue(i));
        return result;
    }

    // endregion
}
