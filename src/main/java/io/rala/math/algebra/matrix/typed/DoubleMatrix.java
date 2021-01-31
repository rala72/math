package io.rala.math.algebra.matrix.typed;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;

import java.util.Arrays;

/**
 * class which holds a matrix with {@code rows} and {@code cols}
 * storing {@link Double}
 *
 * @since 1.0.0
 */
public class DoubleMatrix extends Matrix<Double> {
    // region constructor

    /**
     * @param size size of matrix
     * @see Matrix#Matrix(AbstractArithmetic, int)
     * @since 1.0.0
     */
    public DoubleMatrix(int size) {
        super(DoubleArithmetic.getInstance(), size);
    }

    /**
     * @param rows rows of matrix
     * @param cols cols of matrix
     * @see Matrix#Matrix(AbstractArithmetic, int, int)
     * @since 1.0.0
     */
    public DoubleMatrix(int rows, int cols) {
        super(DoubleArithmetic.getInstance(), rows, cols);
    }

    /**
     * creates a new matrix based on given one
     *
     * @param matrix matrix to copy
     * @since 1.0.0
     */
    public DoubleMatrix(Matrix<Double> matrix) {
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
    public static DoubleMatrix identity(int size) {
        return new DoubleMatrix(
            Matrix.identity(DoubleArithmetic.getInstance(), size)
        );
    }

    /**
     * @param values diagonal values of matrix
     * @return new created matrix
     * @see Matrix#diagonal(AbstractArithmetic, Number[])
     * @since 1.0.0
     */
    public static DoubleMatrix diagonal(double... values) {
        Double[] boxed = Arrays.stream(values).boxed().toArray(Double[]::new);
        return new DoubleMatrix(
            Matrix.diagonal(DoubleArithmetic.getInstance(), boxed)
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
    public static DoubleMatrix ofValuesByRows(int rows, double... values) {
        Double[] boxed = Arrays.stream(values).boxed().toArray(Double[]::new);
        return new DoubleMatrix(Matrix.ofValuesByRows(
            DoubleArithmetic.getInstance(), rows, boxed
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
    public static DoubleMatrix ofValuesByCols(int cols, double... values) {
        Double[] boxed = Arrays.stream(values).boxed().toArray(Double[]::new);
        return new DoubleMatrix(Matrix.ofValuesByCols(
            DoubleArithmetic.getInstance(), cols, boxed
        ));
    }

    // endregion
}
