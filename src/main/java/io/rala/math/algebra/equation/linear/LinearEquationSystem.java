package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.AbstractEquationSystem;
import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.equation.linear.solver.GaussSolver;
import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.algebra.vector.Vector;

import java.util.Objects;

/**
 * class which holds a linear equation system
 *
 * @param <T> number class of linear equation system
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
     */
    public LinearEquationSystem(Matrix<T> matrix, Vector<T> vector) {
        this(new LinearEquationMatrix<>(matrix), new LinearEquationVector<>(vector));
    }

    /**
     * creates a new {@link LinearEquationSystem} for given matrix
     *
     * @param matrix matrix of linear equation system
     * @param vector vector of linear equation solution
     * @throws IllegalArgumentException if matrix and vector do not match
     */
    public LinearEquationSystem(
        LinearEquationMatrix<T> matrix, LinearEquationVector<T> vector
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
     */
    public LinearEquationMatrix<T> getMatrix() {
        return matrix;
    }

    /**
     * @return stored vector
     */
    public LinearEquationVector<T> getVector() {
        return vector;
    }

    /**
     * solves an {@link AbstractEquationSystem} with {@link GaussSolver}
     *
     * @return {@link Solution} of {@link GaussSolver#solve()}
     */
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
     */
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
     */
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
     */
    public LinearEquationSystem<T> multiplyRow(int row, T n) {
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
     */
    public LinearEquationSystem<T> multiplyCol(int col, T n) {
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
     */
    public LinearEquationSystem<T> addRowMultipleTimes(int row1, int row2, T n) {
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
     */
    public LinearEquationSystem<T> addColMultipleTimes(int col1, int col2, T n) {
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
     */
    public static <T extends Number> LinearEquationSystem<T> ofMatrixWithSolutionColumn(Matrix<T> matrix) {
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
     */
    public static <T extends Number> LinearEquationSystem<T> ofMatrixWithSolutionRow(Matrix<T> matrix) {
        return ofMatrixWithSolutionColumn(matrix.transpose());
    }

    // endregion

    // region override

    @Override
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
    public String toString() {
        return getMatrix().toString() + " - " + getVector();
    }

    // endregion

    /**
     * special {@link Matrix} for linear equations
     *
     * @param <T> number class of {@link Matrix}
     */
    public static class LinearEquationMatrix<T extends Number> extends Matrix<T> {
        /**
         * creates a new equation system from given matrix
         *
         * @param matrix matrix to store
         */
        public LinearEquationMatrix(Matrix<T> matrix) {
            super(matrix);
        }

        // region override matrix modify

        @Override
        public LinearEquationMatrix<T> swapRows(int row1, int row2) {
            return new LinearEquationMatrix<>(super.swapRows(row1, row2));
        }

        @Override
        public LinearEquationMatrix<T> swapCols(int col1, int col2) {
            return new LinearEquationMatrix<>(super.swapCols(col1, col2));
        }

        @Override
        public LinearEquationMatrix<T> multiplyRow(int row, T n) {
            return new LinearEquationMatrix<>(super.multiplyRow(row, n));
        }

        @Override
        public LinearEquationMatrix<T> multiplyCol(int col, T n) {
            return new LinearEquationMatrix<>(super.multiplyCol(col, n));
        }

        @Override
        public LinearEquationMatrix<T> addRowMultipleTimes(int row1, int row2, T n) {
            return new LinearEquationMatrix<>(super.addRowMultipleTimes(row1, row2, n));
        }

        @Override
        public LinearEquationMatrix<T> addColMultipleTimes(int col1, int col2, T n) {
            return new LinearEquationMatrix<>(super.addColMultipleTimes(col1, col2, n));
        }

        // endregion
    }

    /**
     * special {@link Vector} for linear equations
     *
     * @param <T> number class of {@link Vector}
     */
    public static class LinearEquationVector<T extends Number> extends Vector<T> {
        /**
         * creates a new equation vector from given vector
         *
         * @param vector vector to store
         */
        public LinearEquationVector(Vector<T> vector) {
            super(vector);
        }

        /**
         * @param index1 index1 to swap with index2
         * @param index2 index2 to swap with index1
         * @return new vector with swapped values
         * @throws IndexOutOfBoundsException if index1 or index2 is invalid
         */
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
         */
        public LinearEquationVector<T> multiplyValue(int index, T n) {
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
         */
        public LinearEquationVector<T> addValueMultiplyTimes(int index1, int index2, T n) {
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
