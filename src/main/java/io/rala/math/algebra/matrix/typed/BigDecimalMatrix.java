package io.rala.math.algebra.matrix.typed;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which holds a matrix with {@code rows} and {@code cols}
 * storing {@link BigDecimal}
 *
 * @since 1.0.0
 */
public class BigDecimalMatrix extends Matrix<BigDecimal> {
    // region constructor

    /**
     * @param size size of matrix
     * @see Matrix#Matrix(AbstractArithmetic, int)
     * @since 1.0.0
     */
    public BigDecimalMatrix(int size) {
        super(BigDecimalArithmetic.getInstance(), size);
    }

    /**
     * @param size    size of matrix
     * @param context context of {@link BigDecimalArithmetic}
     * @see Matrix#Matrix(AbstractArithmetic, int)
     * @since 1.0.0
     */
    public BigDecimalMatrix(int size, MathContext context) {
        super(new BigDecimalArithmetic(context), size);
    }

    /**
     * @param rows rows of matrix
     * @param cols cols of matrix
     * @see Matrix#Matrix(AbstractArithmetic, int, int)
     * @since 1.0.0
     */
    public BigDecimalMatrix(int rows, int cols) {
        super(BigDecimalArithmetic.getInstance(), rows, cols);
    }

    /**
     * @param rows    rows of matrix
     * @param cols    cols of matrix
     * @param context context of {@link BigDecimalArithmetic}
     * @see Matrix#Matrix(AbstractArithmetic, int, int)
     * @since 1.0.0
     */
    public BigDecimalMatrix(int rows, int cols, MathContext context) {
        super(new BigDecimalArithmetic(context), rows, cols);
    }

    /**
     * creates a new matrix based on given one
     *
     * @param matrix matrix to copy
     * @since 1.0.0
     */
    public BigDecimalMatrix(Matrix<BigDecimal> matrix) {
        super(matrix);
    }

    // endregion

    // region static: identity and diagonal

    /**
     * @param size size of matrix
     * @return new created matrix
     * @see Matrix#identity(AbstractArithmetic, int)
     * @since 1.0.0
     */
    public static BigDecimalMatrix identity(int size) {
        return new BigDecimalMatrix(
            Matrix.identity(BigDecimalArithmetic.getInstance(), size)
        );
    }

    /**
     * @param values diagonal values of matrix
     * @return new created matrix
     * @see Matrix#diagonal(AbstractArithmetic, Number[])
     * @since 1.0.0
     */
    public static BigDecimalMatrix diagonal(BigDecimal... values) {
        return new BigDecimalMatrix(
            Matrix.diagonal(BigDecimalArithmetic.getInstance(), values)
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
     * @throws IllegalArgumentException if rows modulo {@code values.length}
     *                                  is not congruent {@code 0}
     * @see Matrix#ofValuesByRows(AbstractArithmetic, int, Number[])
     * @since 1.0.0
     */
    public static BigDecimalMatrix ofValuesByRows(int rows, BigDecimal... values) {
        return new BigDecimalMatrix(Matrix.ofValuesByRows(
            BigDecimalArithmetic.getInstance(), rows, values
        ));
    }

    /**
     * creates a new matrix containing all provided values
     *
     * @param cols   cols of matrix
     * @param values column based values of matrix
     * @return new created matrix
     * @throws IllegalArgumentException if cols modulo {@code values.length}
     *                                  is not congruent {@code 0}
     * @see Matrix#ofValuesByCols(AbstractArithmetic, int, Number[])
     * @since 1.0.0
     */
    public static BigDecimalMatrix ofValuesByCols(int cols, BigDecimal... values) {
        return new BigDecimalMatrix(Matrix.ofValuesByCols(
            BigDecimalArithmetic.getInstance(), cols, values
        ));
    }

    // endregion
}
