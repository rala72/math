package io.rala.math.algebra.equation.linear.solver;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.equation.linear.AbstractLinearSolver;
import io.rala.math.algebra.equation.linear.LinearEquationSystem;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * solves a {@link LinearEquationSystem} based on Gauss
 *
 * @param <T> number class
 */
public class GaussSolver<T extends Number> extends AbstractLinearSolver<T> {
    private final Deque<ColPair> swappedCols = new LinkedList<>();

    /**
     * creates a new GaussSolver based on a {@link LinearEquationSystem}
     *
     * @param equationSystem equation system to solve
     */
    public GaussSolver(LinearEquationSystem<T> equationSystem) {
        super(equationSystem);
    }

    // region protected getSwappedCols

    /**
     * @return current swappedCols instance
     */
    protected Deque<ColPair> getSwappedCols() {
        return swappedCols;
    }

    // endregion

    @Override
    public Solution<T> solve() {
        reset();
        prepareMatrix();
        if (hasNoSolutions())
            return Solution.unsolvable(getEquationSystem());
        if (getWorkingMatrix().getRows() < getWorkingMatrix().getCols() - 1) {
            List<T> lastRow = getWorkingMatrix().getRow(getWorkingMatrix().getRows() - 1);
            return getArithmetic().zero().equals(lastRow.get(lastRow.size() - 1)) ?
                Solution.unsolvable(getEquationSystem()) :
                Solution.infinite(getEquationSystem());
        }
        solveBottomUp();
        if (hasInfiniteSolutions())
            return Solution.infinite(getEquationSystem());
        if (!getSwappedCols().isEmpty()) {
            reSwapCols();
            sortRows();
        }
        if (hasNoSolutions())
            return Solution.unsolvable(getEquationSystem());
        if (hasInfiniteSolutions())
            return Solution.infinite(getEquationSystem());
        return toSolvedSolution();
    }

    // region prepare

    /**
     * prepares matrix by calling
     * {@link #prepareMatrixBySwapping(int)},
     * {@link #prepareMatrixByMakingFieldToOne(int)} and
     * {@link #prepareMatrixByMakeColToZero(int)}
     */
    protected void prepareMatrix() {
        // TODO: cleanup zero rows
        for (int i = 0; i < getWorkingMatrix().getRows(); i++) {
            if (areAllZero(getWorkingMatrix().getRow(i)))
                continue;
            prepareMatrixBySwapping(i);
            prepareMatrixByMakingFieldToOne(i);
            prepareMatrixByMakeColToZero(i);
        }
    }

    /**
     * swaps (preferred) rows and (if required) cols to
     * ensure {@code rowIndex} value is not {@code 0}
     *
     * @param rowIndex rowIndex to handle
     */
    protected void prepareMatrixBySwapping(int rowIndex) {
        List<T> row = getWorkingMatrix().getRow(rowIndex);
        if (!getArithmetic().zero().equals(row.get(rowIndex)))
            return;
        for (int i = rowIndex + 1; i < getWorkingMatrix().getRows(); i++)
            if (!getArithmetic().zero().equals(row.get(i))) {
                setWorkingMatrix(getWorkingMatrix().swapRows(rowIndex, i));
                return;
            }
        for (int i = rowIndex + 1; i < getWorkingMatrix().getCols() - 1; i++)
            if (!getArithmetic().zero().equals(row.get(i))) {
                setWorkingMatrix(getWorkingMatrix().swapCols(rowIndex, i));
                getSwappedCols().add(new ColPair(rowIndex, i));
                return;
            }
    }

    /**
     * ensures that {@code rowIndex} value is {@code 1}
     *
     * @param rowIndex rowIndex to handle
     */
    protected void prepareMatrixByMakingFieldToOne(int rowIndex) {
        T rowIndexValue = getWorkingMatrix().getValue(rowIndex, rowIndex);
        if (getArithmetic().one().equals(rowIndexValue))
            return;
        T quotient = getArithmetic().quotient(getArithmetic().one(), rowIndexValue);
        setWorkingMatrix(getWorkingMatrix().multiplyRow(rowIndex, quotient));
        if (!getArithmetic().one().equals(getWorkingMatrix().getRow(rowIndex).get(rowIndex)))
            getWorkingMatrix().setValue(rowIndex, rowIndex, getArithmetic().one());
    }

    /**
     * ensures that all col values below {@code rowIndex} are {@code 0}
     *
     * @param rowIndex rowIndex to handle
     */
    protected void prepareMatrixByMakeColToZero(int rowIndex) {
        for (int i = rowIndex + 1; i < getWorkingMatrix().getRows(); i++) {
            T rowIndexValue = getWorkingMatrix().getValue(i, rowIndex);
            if (getArithmetic().zero().equals(rowIndexValue))
                continue;
            T negate = getArithmetic().negate(rowIndexValue);
            setWorkingMatrix(getWorkingMatrix().addRowMultipleTimes(i, rowIndex, negate));
        }
    }

    // endregion

    // region solveBottomUp and reSwapCols

    /**
     * clears all values above rows so that only the diagonal is left
     */
    protected void solveBottomUp() {
        for (int i = getWorkingMatrix().getRows() - 1; 0 < i; i--) {
            if (areAllZero(getWorkingMatrix().getRow(i)))
                continue;
            for (int j = i - 1; 0 <= j; j--) {
                List<T> row = getWorkingMatrix().getRow(j);
                if (!getArithmetic().zero().equals(row.get(i))) {
                    T negate = getArithmetic().negate(row.get(i));
                    setWorkingMatrix(getWorkingMatrix().addRowMultipleTimes(j, i, negate));
                }
            }
        }
    }

    /**
     * reSwaps cols which have been swapped during {@link #prepareMatrixBySwapping(int)}
     */
    protected void reSwapCols() {
        while (!getSwappedCols().isEmpty()) {
            ColPair pop = getSwappedCols().pop();
            setWorkingMatrix(getWorkingMatrix().swapCols(pop.getCol1(), pop.getCol2()));
        }
    }

    /**
     * sorts rows after {@link #reSwapCols()}
     */
    protected void sortRows() {
        for (int i = 0; i < getWorkingMatrix().getRows(); i++) {
            List<T> row = getWorkingMatrix().getRow(i);
            if (areAllZero(row) || !getArithmetic().zero().equals(row.get(i)))
                continue;
            for (int j = i + 1; j < getWorkingMatrix().getRows(); j++)
                if (!getArithmetic().zero().equals(getWorkingMatrix().getRow(j).get(i)))
                    setWorkingMatrix(getWorkingMatrix().swapRows(i, j));
        }
    }

    // endregion

    // region solution checks

    /**
     * @return {@code true} if current {@link #getWorkingMatrix()} has no solutions
     */
    protected boolean hasNoSolutions() {
        for (int i = 0; i < getWorkingMatrix().getRows(); i++) {
            List<T> row = getWorkingMatrix().getRow(i);
            if (getArithmetic().zero().equals(row.get(row.size() - 1)))
                continue;
            if (areAllZeroExceptSolution(row))
                return true;
        }
        return false;
    }

    /**
     * @return {@code true} if current {@link #getWorkingMatrix()} has infinite solutions
     */
    protected boolean hasInfiniteSolutions() {
        for (int i = 0; i < getWorkingMatrix().getRows(); i++) {
            List<T> row = getWorkingMatrix().getRow(i);
            if (areAllZero(row))
                continue;
            if (getArithmetic().zero().equals(row.get(row.size() - 1)))
                return true;
            List<T> subList = row.subList(i + 1, row.size() - 1);
            if (subList.stream().anyMatch(t -> !getArithmetic().zero().equals(t)))
                return true;
        }
        return false;
    }

    // endregion

    @Override
    protected void reset() {
        super.reset();
        getSwappedCols().clear();
    }

    /**
     * class which stores two col indizes
     */
    protected static class ColPair {
        private final int col1;
        private final int col2;

        /**
         * creates a new pair based on given parameters
         *
         * @param col1 first col
         * @param col2 second col
         */
        public ColPair(int col1, int col2) {
            this.col1 = col1;
            this.col2 = col2;
        }

        /**
         * @return col1
         */
        public int getCol1() {
            return col1;
        }

        /**
         * @return col2
         */
        public int getCol2() {
            return col2;
        }
    }
}
