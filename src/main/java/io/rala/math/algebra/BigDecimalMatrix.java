package io.rala.math.algebra;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.BigDecimalArithmetic;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a matrix with <code>rows</code> and <code>cols</code>
 * storing {@link BigDecimal}
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class BigDecimalMatrix extends Matrix<BigDecimal> {
    public static final BigDecimal DEFAULT_VALUE = BigDecimal.ZERO;

    // region constructor

    /**
     * default value is <code>0</code>
     *
     * @param size size of matrix
     * @see Matrix#Matrix(AbstractArithmetic, int, Number)
     */
    public BigDecimalMatrix(int size) {
        super(new BigDecimalArithmetic(), size, DEFAULT_VALUE);
    }

    /**
     * default value is <code>0</code>
     *
     * @param size    size of matrix
     * @param context context of {@link BigDecimalArithmetic}
     * @see Matrix#Matrix(AbstractArithmetic, int, Number)
     */
    public BigDecimalMatrix(int size, MathContext context) {
        super(new BigDecimalArithmetic(context), size, DEFAULT_VALUE);
    }

    /**
     * default value is <code>0</code>
     *
     * @param rows rows of matrix
     * @param cols cols of matrix
     * @see Matrix#Matrix(AbstractArithmetic, int, int, Number)
     */
    public BigDecimalMatrix(int rows, int cols) {
        super(new BigDecimalArithmetic(), rows, cols, DEFAULT_VALUE);
    }

    /**
     * default value is <code>0</code>
     *
     * @param rows    rows of matrix
     * @param cols    cols of matrix
     * @param context context of {@link BigDecimalArithmetic}
     * @see Matrix#Matrix(AbstractArithmetic, int, int, Number)
     */
    public BigDecimalMatrix(int rows, int cols, MathContext context) {
        super(new BigDecimalArithmetic(context), rows, cols, DEFAULT_VALUE);
    }

    /**
     * creates a new matrix based on given one<br>
     * default value is <code>0</code>
     *
     * @param matrix matrix to copy
     */
    public BigDecimalMatrix(Matrix<BigDecimal> matrix) {
        super(matrix);
    }

    // endregion

    // region static: identity and diagonal

    /**
     * @param size size of matrix
     * @return new created matrix
     */
    public static BigDecimalMatrix identity(int size) {
        BigDecimalMatrix matrix = new BigDecimalMatrix(size);
        for (int i = 0; i < size; i++)
            matrix.setValue(i, i, BigDecimal.ONE);
        return matrix;
    }

    /**
     * @param values diagonal values of matrix
     * @return new created matrix
     */
    public static BigDecimalMatrix diagonal(BigDecimal... values) {
        BigDecimalMatrix matrix = new BigDecimalMatrix(values.length);
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
    public static BigDecimalMatrix ofValuesByRows(int rows, BigDecimal... values) {
        if (values.length % rows != 0)
            throw new IllegalArgumentException(EXCEPTION_ROWS_NOT_CONGRUENT_0);
        BigDecimalMatrix matrix = new BigDecimalMatrix(rows, values.length / rows);
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
    public static BigDecimalMatrix ofValuesByCols(int cols, BigDecimal... values) {
        if (values.length % cols != 0)
            throw new IllegalArgumentException(EXCEPTION_COLS_NOT_CONGRUENT_0);
        return new BigDecimalMatrix(
            ofValuesByRows(values.length / cols, values)
                .transpose()
        );
    }

    // endregion
}
