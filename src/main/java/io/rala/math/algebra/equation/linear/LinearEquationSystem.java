package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.AbstractEquationSystem;
import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.equation.linear.solver.GaussSolver;
import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.algebra.vector.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * class which holds a linear equation system
 *
 * @param <T> number class of linear equation system
 * @since 1.0.0
 */
public class LinearEquationSystem<T extends Number> extends AbstractEquationSystem<LinearEquationSystem<T>> {
    // region protected exception messages
    protected static final String EXCEPTION_MATRIX_VECTOR_NO_MATCH =
        "matrix and vector do not match";
    // endregion

    // region attributes

    private final LinearEquationMatrix<T> matrix;
    private final LinearEquationVector<T> vector;

    // endregion

    /**
     * creates a new {@link LinearEquationSystem} for given matrix
     * by calling {@link LinearEquationMatrix#LinearEquationMatrix(Matrix)}
     *
     * @param matrix matrix of linear equation matrix
     * @param vector vector of linear equation solution
     * @throws IllegalArgumentException if matrix and vector do not match
     * @see LinearEquationMatrix#LinearEquationMatrix(Matrix)
     * @see LinearEquationVector#LinearEquationVector(Vector)
     * @see LinearEquationSystem#LinearEquationSystem(LinearEquationMatrix, LinearEquationVector)
     * @since 1.0.0
     */
    public LinearEquationSystem(@NotNull Matrix<T> matrix, @NotNull Vector<T> vector) {
        this(new LinearEquationMatrix<>(matrix), new LinearEquationVector<>(vector));
    }

    /**
     * creates a new {@link LinearEquationSystem} for given matrix
     *
     * @param matrix matrix of linear equation system
     * @param vector vector of linear equation solution
     * @throws IllegalArgumentException if matrix and vector do not match
     * @since 1.0.0
     */
    public LinearEquationSystem(
        @NotNull LinearEquationMatrix<T> matrix, @NotNull LinearEquationVector<T> vector
    ) {
        if (vector.isRow() && vector.getSize() != matrix.getCols() ||
            vector.isColumn() && vector.getSize() != matrix.getRows()) {
            throw new IllegalArgumentException(EXCEPTION_MATRIX_VECTOR_NO_MATCH);
        }
        this.matrix = matrix;
        this.vector = vector;
    }

    /**
     * @return stored matrix
     * @since 1.0.0
     */
    @NotNull
    public LinearEquationMatrix<T> getMatrix() {
        return matrix;
    }

    /**
     * @return stored vector
     * @since 1.0.0
     */
    @NotNull
    public LinearEquationVector<T> getVector() {
        return vector;
    }

    /**
     * solves an {@link AbstractEquationSystem} with {@link GaussSolver}
     *
     * @return {@link Solution} of {@link GaussSolver#solve()}
     * @since 1.0.0
     */
    @NotNull
    public Solution<LinearEquationSystem<T>, T> solveWithGauss() {
        return new GaussSolver<>(this).solve();
    }

    // region modify

    /**
     * @param row1 row1 to swap with row2
     * @param row2 row2 to swap with row1
     * @return new equation system with changed values
     * @see LinearEquationMatrix#swapRows(int, int)
     * @see LinearEquationVector#swapValues(int, int)
     * @since 1.0.0
     */
    @NotNull
    public LinearEquationSystem<T> swapRows(int row1, int row2) {
        return new LinearEquationSystem<>(
            getMatrix().swapRows(row1, row2),
            getVector().isColumn() ? getVector().swapValues(row1, row2) : getVector()
        );
    }

    /**
     * @param col1 col1 to swap with col2
     * @param col2 col2 to swap with col1
     * @return new equation system with changed values
     * @see LinearEquationMatrix#swapCols(int, int)
     * @see LinearEquationVector#swapValues(int, int)
     * @since 1.0.0
     */
    @NotNull
    public LinearEquationSystem<T> swapCols(int col1, int col2) {
        return new LinearEquationSystem<>(
            getMatrix().swapCols(col1, col2),
            getVector().isRow() ? getVector().swapValues(col1, col2) : getVector()
        );
    }

    /**
     * @param row row to multiply
     * @param n   factor to use
     * @return new equation system with changed values
     * @see LinearEquationMatrix#multiplyRow(int, Number)
     * @see LinearEquationVector#multiplyValue(int, Number)
     * @since 1.0.0
     */
    @NotNull
    public LinearEquationSystem<T> multiplyRow(int row, @NotNull T n) {
        return new LinearEquationSystem<>(
            getMatrix().multiplyRow(row, n),
            getVector().isColumn() ? getVector().multiplyValue(row, n) : getVector()
        );
    }

    /**
     * @param col col to multiply
     * @param n   factor to use
     * @return new equation system with changed values
     * @see LinearEquationMatrix#multiplyCol(int, Number)
     * @see LinearEquationVector#multiplyValue(int, Number)
     * @since 1.0.0
     */
    @NotNull
    public LinearEquationSystem<T> multiplyCol(int col, @NotNull T n) {
        return new LinearEquationSystem<>(
            getMatrix().multiplyCol(col, n),
            getVector().isRow() ? getVector().multiplyValue(col, n) : getVector()
        );
    }

    /**
     * @param row1 row to multiply with other multiple times
     * @param row2 row to multiply multiple times with other
     * @param n    factor to use
     * @return new equation system with changed values
     * @see LinearEquationMatrix#addRowMultipleTimes(int, int, Number)
     * @see LinearEquationVector#addValueMultiplyTimes(int, int, Number)
     * @since 1.0.0
     */
    @NotNull
    public LinearEquationSystem<T> addRowMultipleTimes(int row1, int row2, @NotNull T n) {
        return new LinearEquationSystem<>(
            getMatrix().addRowMultipleTimes(row1, row2, n),
            getVector().isColumn() ? getVector().addValueMultiplyTimes(row1, row2, n) : getVector()
        );
    }

    /**
     * @param col1 col to multiply with other multiple times
     * @param col2 col to multiply multiple times with other
     * @param n    factor to use
     * @return new equation system with changed values
     * @see LinearEquationMatrix#addColMultipleTimes(int, int, Number)
     * @see LinearEquationVector#addValueMultiplyTimes(int, int, Number)
     * @since 1.0.0
     */
    @NotNull
    public LinearEquationSystem<T> addColMultipleTimes(int col1, int col2, @NotNull T n) {
        return new LinearEquationSystem<>(
            getMatrix().addColMultipleTimes(col1, col2, n),
            getVector().isColumn() ? getVector().addValueMultiplyTimes(col1, col2, n) : getVector()
        );
    }

    // endregion

    // region static of

    /**
     * converts {@link Matrix} to {@link LinearEquationMatrix}
     * where {@link Solution} is last column
     *
     * @param matrix matrix of linear equation system where solution is last column
     * @param <T>    number class of linear equation system
     * @return new linear equation system with
     * {@link LinearEquationMatrix} and {@link LinearEquationVector}
     * @since 1.0.0
     */
    @NotNull
    public static <T extends Number> LinearEquationSystem<T> ofMatrixWithSolutionColumn(
        @NotNull Matrix<T> matrix
    ) {
        Matrix<T> eMatrix = new Matrix<>(matrix.getArithmetic(),
            matrix.getRows(), matrix.getCols() - 1
        );
        eMatrix.forEach(field -> eMatrix.setValue(
            field.getIndex(),
            matrix.getValue(field.getRow(), field.getCol())
        ));
        Vector<T> eVector = Vector.ofList(matrix.getArithmetic(),
            matrix.getCol(matrix.getCols() - 1)
        );
        return new LinearEquationSystem<>(eMatrix, eVector);
    }

    /**
     * converts {@link Matrix} to {@link LinearEquationMatrix}
     * where {@link Solution} is last row
     *
     * @param matrix matrix of linear equation system where solution is last row
     * @param <T>    number class of linear equation system
     * @return new linear equation system with
     * {@link LinearEquationMatrix} and {@link LinearEquationVector}
     * @since 1.0.0
     */
    @NotNull
    public static <T extends Number> LinearEquationSystem<T> ofMatrixWithSolutionRow(
        @NotNull Matrix<T> matrix
    ) {
        return ofMatrixWithSolutionColumn(matrix.transpose());
    }

    // endregion

    // region override

    @Override
    @NotNull
    protected LinearEquationSystem<T> transpose() {
        return new LinearEquationSystem<>(getMatrix().transpose(), getVector().transpose());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinearEquationSystem)) return false;
        LinearEquationSystem<?> that = (LinearEquationSystem<?>) o;
        return Objects.equals(getMatrix(), that.getMatrix()) &&
            Objects.equals(getVector(), that.getVector());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMatrix(), getVector());
    }

    @Override
    @NotNull
    public String toString() {
        return getMatrix() + " - " + getVector();
    }

    // endregion

    /**
     * special {@link Matrix} for linear equations
     *
     * @param <T> number class of {@link Matrix}
     * @since 1.0.0
     */
    public static class LinearEquationMatrix<T extends Number> extends Matrix<T> {
        /**
         * creates a new equation system from given matrix
         *
         * @param matrix matrix to store
         * @since 1.0.0
         */
        public LinearEquationMatrix(@NotNull Matrix<T> matrix) {
            super(matrix);
        }

        // region override matrix modify

        @Override
        @NotNull
        public LinearEquationMatrix<T> swapRows(int row1, int row2) {
            return new LinearEquationMatrix<>(super.swapRows(row1, row2));
        }

        @Override
        @NotNull
        public LinearEquationMatrix<T> swapCols(int col1, int col2) {
            return new LinearEquationMatrix<>(super.swapCols(col1, col2));
        }

        @Override
        @NotNull
        public LinearEquationMatrix<T> multiplyRow(int row, @NotNull T n) {
            return new LinearEquationMatrix<>(super.multiplyRow(row, n));
        }

        @Override
        @NotNull
        public LinearEquationMatrix<T> multiplyCol(int col, @NotNull T n) {
            return new LinearEquationMatrix<>(super.multiplyCol(col, n));
        }

        @Override
        @NotNull
        public LinearEquationMatrix<T> addRowMultipleTimes(int row1, int row2, @NotNull T n) {
            return new LinearEquationMatrix<>(super.addRowMultipleTimes(row1, row2, n));
        }

        @Override
        @NotNull
        public LinearEquationMatrix<T> addColMultipleTimes(int col1, int col2, @NotNull T n) {
            return new LinearEquationMatrix<>(super.addColMultipleTimes(col1, col2, n));
        }

        // endregion
    }

    /**
     * special {@link Vector} for linear equations
     *
     * @param <T> number class of {@link Vector}
     * @since 1.0.0
     */
    public static class LinearEquationVector<T extends Number> extends Vector<T> {
        /**
         * creates a new equation vector from given vector
         *
         * @param vector vector to store
         * @since 1.0.0
         */
        public LinearEquationVector(@NotNull Vector<T> vector) {
            super(vector);
        }

        /**
         * @param index1 index1 to swap with index2
         * @param index2 index2 to swap with index1
         * @return new vector with swapped values
         * @throws IndexOutOfBoundsException if index1 or index2 is invalid
         * @since 1.0.0
         */
        @NotNull
        public LinearEquationVector<T> swapValues(int index1, int index2) {
            if (!isValidIndex(index1))
                throw new IndexOutOfBoundsException(index1 + " / " + getSize());
            if (!isValidIndex(index2))
                throw new IndexOutOfBoundsException(index2 + " / " + getSize());
            LinearEquationVector<T> copy = new LinearEquationVector<>(copy());
            if (index1 == index2) return copy;
            T value1 = copy.getValue(index1);
            copy.setValue(index1, copy.getValue(index2));
            copy.setValue(index2, value1);
            return copy;
        }

        /**
         * @param index index to multiply
         * @param n     factor to use
         * @return new vector with multiplied index
         * @throws IndexOutOfBoundsException if index is invalid
         * @since 1.0.0
         */
        @NotNull
        public LinearEquationVector<T> multiplyValue(int index, @NotNull T n) {
            if (!isValidIndex(index))
                throw new IndexOutOfBoundsException(index + " / " + getSize());
            LinearEquationVector<T> copy = new LinearEquationVector<>(copy());
            if (isZero() || getArithmetic().one().equals(n))
                return copy;
            if (getArithmetic().isZero(n)) {
                copy.setValue(index, getArithmetic().zero());
                return copy;
            }
            copy.compute(index, t -> getArithmetic().product(t, n));
            return copy;
        }

        /**
         * @param index1 index to multiply with other multiple times
         * @param index2 index to multiply multiple times with other
         * @param n      factor to use
         * @return new vector with multiplied value
         * @throws IndexOutOfBoundsException if index1 or index2 is invalid
         * @since 1.0.0
         */
        @NotNull
        public LinearEquationVector<T> addValueMultiplyTimes(int index1, int index2, @NotNull T n) {
            if (!isValidIndex(index1))
                throw new IndexOutOfBoundsException(index1 + " / " + getSize());
            if (!isValidIndex(index2))
                throw new IndexOutOfBoundsException(index2 + " / " + getSize());
            LinearEquationVector<T> copy = new LinearEquationVector<>(copy());
            if (isZero()) return copy;
            if (index1 == index2) return multiplyValue(index1, n);
            copy.compute(index1,
                getArithmetic().product(getValue(index2), n),
                getArithmetic()::sum
            );
            return copy;
        }
    }
}
