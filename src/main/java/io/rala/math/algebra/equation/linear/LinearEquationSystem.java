package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.AbstractEquationSystem;
import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.equation.linear.solver.GaussSolver;
import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.arithmetic.AbstractArithmetic;

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

        /**
         * @return {@link Matrix#getArithmetic()}
         * @implSpec overridden so solver methods have access to {@link Matrix#getArithmetic()}
         */
        protected AbstractArithmetic<T> getArithmetic() {
            return super.getArithmetic();
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
}
