package io.rala.math.algebra.matrix;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;

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
     * @see Matrix#identity(AbstractArithmetic, int, Number)
     */
    public static BigDecimalMatrix identity(int size) {
        return new BigDecimalMatrix(
            Matrix.identity(new BigDecimalArithmetic(), size, DEFAULT_VALUE)
        );
    }

    /**
     * @param values diagonal values of matrix
     * @return new created matrix
     * @see Matrix#diagonal(AbstractArithmetic, Number, Number[])
     */
    public static BigDecimalMatrix diagonal(BigDecimal... values) {
        return new BigDecimalMatrix(
            Matrix.diagonal(new BigDecimalArithmetic(), DEFAULT_VALUE, values)
        );
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
     * @see Matrix#ofValuesByRows(AbstractArithmetic, Number, int, Number[])
     */
    public static BigDecimalMatrix ofValuesByRows(int rows, BigDecimal... values) {
        return new BigDecimalMatrix(
            Matrix.ofValuesByRows(new BigDecimalArithmetic(),
                DEFAULT_VALUE, rows, values
            )
        );
    }

    /**
     * creates a new matrix containing all provided values
     *
     * @param cols   cols of matrix
     * @param values column based values of matrix
     * @return new created matrix
     * @throws IllegalArgumentException if cols modulo <code>values.length</code>
     *                                  is not congruent 0
     * @see Matrix#ofValuesByCols(AbstractArithmetic, Number, int, Number[])
     */
    public static BigDecimalMatrix ofValuesByCols(int cols, BigDecimal... values) {
        return new BigDecimalMatrix(
            Matrix.ofValuesByCols(new BigDecimalArithmetic(),
                DEFAULT_VALUE, cols, values
            )
        );
    }

    // endregion
}
