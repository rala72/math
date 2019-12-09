package io.rala.math.algebra;

import io.rala.math.utils.arithmetic.AbstractArithmetic;
import io.rala.math.utils.arithmetic.DoubleArithmetic;

/**
 * class which holds a matrix with <code>rows</code> and <code>cols</code>
 * storing {@link Double}
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class DoubleMatrix extends Matrix<Double> {
    // region constructor & newInstance

    /**
     * default value is <code>0</code>
     *
     * @param size size of matrix
     * @see Matrix#Matrix(AbstractArithmetic, int, Number)
     */
    public DoubleMatrix(int size) {
        super(new DoubleArithmetic(), size, 0d);
    }

    /**
     * default value is <code>0</code>
     *
     * @param rows rows of matrix
     * @param cols cols of matrix
     * @see Matrix#Matrix(AbstractArithmetic, int, int, Number)
     */
    public DoubleMatrix(int rows, int cols) {
        super(new DoubleArithmetic(), rows, cols, 0d);
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

    @Override
    protected Matrix<Double> newInstance(int rows, int cols) {
        return new DoubleMatrix(rows, cols);
    }

    // endregion

    // region override

    @Override
    public Matrix<Double> copy() {
        return new DoubleMatrix(this);
    }

    // endregion

    // region static: identity and diagonal

    /**
     * @param size size of matrix
     * @return new created matrix
     */
    public static DoubleMatrix identity(int size) {
        DoubleMatrix matrix = new DoubleMatrix(size);
        for (int i = 0; i < size; i++)
            matrix.setValue(i, i, 1d);
        return matrix;
    }

    /**
     * @param values diagonal values of matrix
     * @return new created matrix
     */
    public static DoubleMatrix diagonal(double... values) {
        DoubleMatrix matrix = new DoubleMatrix(values.length);
        for (int i = 0; i < values.length; i++)
            matrix.setValue(i, i, values[i]);
        return matrix;
    }

    // endregion

    // region static: of

    /**
     * creates a new matrix containing all provided values
     *
     * @param rows   rows of matrix
     * @param values row based values of matrix
     * @return new created matrix
     * @throws IllegalArgumentException if rows modulo <code>values.length</code>
     *                                  is not congruent 0
     */
    public static DoubleMatrix ofValuesByRows(int rows, double... values) {
        if (values.length % rows != 0)
            throw new IllegalArgumentException("rows modulo values.length is not congruent 0");
        DoubleMatrix matrix = new DoubleMatrix(rows, values.length / rows);
        for (int i = 0; i < values.length; i++)
            matrix.setValue(i, values[i]);
        return matrix;
    }

    /**
     * creates a new matrix containing all provided values
     *
     * @param cols   cols of matrix
     * @param values column based values of matrix
     * @return new created matrix
     * @throws IllegalArgumentException if cols modulo <code>values.length</code>
     *                                  is not congruent 0
     */
    public static DoubleMatrix ofValuesByCols(int cols, double... values) {
        if (values.length % cols != 0)
            throw new IllegalArgumentException("cols modulo values.length is not congruent 0");
        return new DoubleMatrix(
            ofValuesByRows(values.length / cols, values)
                .transpose()
        );
    }

    // endregion
}
