package io.rala.math.algebra.matrix;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;

import java.util.Arrays;

/**
 * class which holds a matrix with {@code rows} and {@code cols}
 * storing {@link Double}
 */
public class DoubleMatrix extends Matrix<Double> {
    public static final Double DEFAULT_VALUE = 0d;

    // region constructor

    /**
     * default value is {@code 0}
     *
     * @param size size of matrix
     * @see Matrix#Matrix(AbstractArithmetic, int, Number)
     */
    public DoubleMatrix(int size) {
        super(new DoubleArithmetic(), size, DEFAULT_VALUE);
    }

    /**
     * default value is {@code 0}
     *
     * @param rows rows of matrix
     * @param cols cols of matrix
     * @see Matrix#Matrix(AbstractArithmetic, int, int, Number)
     */
    public DoubleMatrix(int rows, int cols) {
        super(new DoubleArithmetic(), rows, cols, DEFAULT_VALUE);
    }

    /**
     * creates a new matrix based on given one<br>
     * default value is {@code 0}
     *
     * @param matrix matrix to copy
     */
    public DoubleMatrix(Matrix<Double> matrix) {
        super(matrix);
    }

    // endregion

    // region static: identity and diagonal

    /**
     * @param size size of matrix
     * @return new created matrix
     * @see Matrix#identity(AbstractArithmetic, int, Number)
     */
    public static DoubleMatrix identity(int size) {
        return new DoubleMatrix(
            Matrix.identity(new DoubleArithmetic(), size, DEFAULT_VALUE)
        );
    }

    /**
     * @param values diagonal values of matrix
     * @return new created matrix
     * @see Matrix#diagonal(AbstractArithmetic, Number, Number[])
     */
    public static DoubleMatrix diagonal(double... values) {
        Double[] boxed = Arrays.stream(values).boxed().toArray(Double[]::new);
        return new DoubleMatrix(
            Matrix.diagonal(new DoubleArithmetic(), DEFAULT_VALUE, boxed)
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
     *                                  is not congruent 0
     * @see Matrix#ofValuesByRows(AbstractArithmetic, Number, int, Number[])
     */
    public static DoubleMatrix ofValuesByRows(int rows, double... values) {
        Double[] boxed = Arrays.stream(values).boxed().toArray(Double[]::new);
        return new DoubleMatrix(
            Matrix.ofValuesByRows(new DoubleArithmetic(),
                DEFAULT_VALUE, rows, boxed
            )
        );
    }

    /**
     * creates a new matrix containing all provided values
     *
     * @param cols   cols of matrix
     * @param values column based values of matrix
     * @return new created matrix
     * @throws IllegalArgumentException if cols modulo {@code values.length}
     *                                  is not congruent 0
     * @see Matrix#ofValuesByCols(AbstractArithmetic, Number, int, Number[])
     */
    public static DoubleMatrix ofValuesByCols(int cols, double... values) {
        Double[] boxed = Arrays.stream(values).boxed().toArray(Double[]::new);
        return new DoubleMatrix(
            Matrix.ofValuesByCols(new DoubleArithmetic(),
                DEFAULT_VALUE, cols, boxed
            )
        );
    }

    // endregion
}
