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
 * @param <T> number class of matrix
 */
public class LinearEquationSystem<T extends Number> extends AbstractEquationSystem {
    // region attributes

    private final LinearEquationMatrix<T> matrix;

    // endregion

    /**
     * creates a new {@link LinearEquationSystem} for given matrix
     * by calling {@link LinearEquationMatrix#LinearEquationMatrix(Matrix)}
     *
     * @param matrix matrix of linear equation matrix
     * @see LinearEquationMatrix#LinearEquationMatrix(Matrix)
     * @see LinearEquationSystem#LinearEquationSystem(LinearEquationMatrix)
     */
    public LinearEquationSystem(Matrix<T> matrix) {
        this(new LinearEquationMatrix<>(matrix));
    }

    /**
     * creates a new {@link LinearEquationSystem} for given matrix
     *
     * @param matrix matrix of linear equation system
     */
    public LinearEquationSystem(LinearEquationMatrix<T> matrix) {
        this.matrix = matrix;
    }

    /**
     * @return stored matrix
     */
    public LinearEquationMatrix<T> getMatrix() {
        return matrix;
    }

    /**
     * @return {@link Solution} of {@link GaussSolver#solve()}
     */
    public Solution<LinearEquationSystem<T>, T> solveWithGauss() {
        return new GaussSolver<>(this).solve();
    }

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinearEquationSystem)) return false;
        LinearEquationSystem<?> that = (LinearEquationSystem<?>) o;
        return Objects.equals(getMatrix(), that.getMatrix());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMatrix());
    }

    @Override
    public String toString() {
        return getMatrix().toString();
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
