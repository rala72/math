package io.rala.math.algebra.equation.linear.solver;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.equation.linear.AbstractLinearSolver;
import io.rala.math.algebra.equation.linear.LinearEquationSystem;

import java.util.*;

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
    public Solution<LinearEquationSystem<T>, T> solve() {
        reset();
        prepareMatrix();
        if (hasNoSolutions())
            return Solution.unsolvable(getEquationSystem());
        if (getWorkingMatrix().getRows() < getWorkingMatrix().getCols() - 1)
            return Solution.infinite(getEquationSystem());
        solveBottomUp();
        if (hasInfiniteSolutions())
            return Solution.infinite(getEquationSystem());
        if (!getSwappedCols().isEmpty()) {
            reSwapCols();
            sortRows();
        }
        if (hasInfiniteSolutions())
            return Solution.infinite(getEquationSystem());
        return toSingleSolution();
    }

    // region prepare

    /**
     * prepares matrix by calling
     * {@link #prepareMatrixBySwappingZeroRowsToBottom()},
     * {@link #prepareMatrixBySwapping(int)},
     * {@link #prepareMatrixByMakingFieldToOne(int)} and
     * {@link #prepareMatrixByMakeColToZero(int)}
     */
    protected void prepareMatrix() {
        prepareMatrixBySwappingZeroRowsToBottom();
        for (int i = 0; i < getWorkingMatrix().getRows(); i++) {
            if (areAllZeroIgnoringSolution(getWorkingMatrix().getRow(i)))
                break;
            prepareMatrixBySwapping(i);
            prepareMatrixByMakingFieldToOne(i);
            prepareMatrixByMakeColToZero(i);
        }
    }

    /**
     * moves zero rows to bottom
     */
    protected void prepareMatrixBySwappingZeroRowsToBottom() {
        for (int i = 0; i < getWorkingMatrix().getRows() - 1; i++) {
            List<T> row = getWorkingMatrix().getRow(i);
            if (!areAllZero(row)) continue;
            for (int j = i + 1; j < getWorkingMatrix().getRows(); j++)
                if (!areAllZero(getWorkingMatrix().getRow(j))) {
                    setWorkingMatrix(getWorkingMatrix().swapRows(i, j));
                    break;
                }
        }
    }

    /**
     * swaps (preferred) rows and (if required) cols to
     * ensure {@code rowIndex} value is not {@code 0}
     * <p>
     * solution column method will be untouched if columns are swapped
     *
     * @param rowIndex rowIndex to handle
     * @implSpec pre: non zero row
     */
    protected void prepareMatrixBySwapping(int rowIndex) {
        List<T> row = getWorkingMatrix().getRow(rowIndex);
        if (row.size() <= rowIndex ||
            !getArithmetic().isZero(row.get(rowIndex)))
            return;
        List<T> col = getWorkingMatrix().getCol(rowIndex);
        for (int i = rowIndex + 1; i < getWorkingMatrix().getRows(); i++)
            if (!getArithmetic().isZero(col.get(i))) {
                setWorkingMatrix(getWorkingMatrix().swapRows(rowIndex, i));
                return;
            } else if (areAllZero(getWorkingMatrix().getRow(i))) break;
        for (int i = rowIndex + 1; i < getWorkingMatrix().getCols() - 1; i++)
            if (!getArithmetic().isZero(row.get(i))) {
                setWorkingMatrix(getWorkingMatrix().swapCols(rowIndex, i));
                getSwappedCols().add(new ColPair(rowIndex, i));
                return;
            }
    }

    /**
     * ensures that {@code rowIndex} value is {@code 1}
     *
     * @param rowIndex rowIndex to handle
     * @implSpec pre: <i>see {@link #prepareMatrixBySwapping(int)}</i>
     * and {@code rowIndex} is also a valid column index
     */
    protected void prepareMatrixByMakingFieldToOne(int rowIndex) {
        T rowIndexValue = getWorkingMatrix().getValue(rowIndex, rowIndex);
        if (getArithmetic().one().equals(rowIndexValue))
            return;
        T quotient = getArithmetic().quotient(getArithmetic().one(), rowIndexValue);
        setWorkingMatrix(getWorkingMatrix().multiplyRow(rowIndex, quotient));
        if (!getArithmetic().one().equals(getWorkingMatrix().getValue(rowIndex, rowIndex)))
            getWorkingMatrix().setValue(rowIndex, rowIndex, getArithmetic().one());
    }

    /**
     * ensures that all col values below {@code rowIndex} are {@code 0}
     *
     * @param rowIndex rowIndex to handle
     * @implSpec pre: <i>see {@link #prepareMatrixByMakingFieldToOne(int)}</i>
     */
    protected void prepareMatrixByMakeColToZero(int rowIndex) {
        for (int i = rowIndex + 1; i < getWorkingMatrix().getRows(); i++) {
            T rowIndexValue = getWorkingMatrix().getValue(i, rowIndex);
            if (getArithmetic().isZero(rowIndexValue))
                continue;
            T negate = getArithmetic().negate(rowIndexValue);
            setWorkingMatrix(getWorkingMatrix().addRowMultipleTimes(i, rowIndex, negate));
        }
    }

    // endregion

    // region solveBottomUp, reSwapCols and sortRows

    /**
     * clears all values above rows so that only the diagonal is left
     */
    protected void solveBottomUp() {
        for (int i = getWorkingMatrix().getRows() - 1; 0 < i; i--) {
            if (areAllZero(getWorkingMatrix().getRow(i)))
                continue;
            for (int j = i - 1; 0 <= j; j--) {
                List<T> row = getWorkingMatrix().getRow(j);
                if (!getArithmetic().isZero(row.get(i))) {
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
     *
     * @implSpec suggested only if {@link #getSwappedCols()} was non-empty
     */
    protected void sortRows() {
        for (int i = 0; i < getWorkingMatrix().getRows(); i++) {
            List<T> row = getWorkingMatrix().getRow(i);
            if (areAllZero(row) || !getArithmetic().isZero(row.get(i)))
                continue;
            for (int j = i + 1; j < getWorkingMatrix().getRows(); j++)
                if (!getArithmetic().isZero(getWorkingMatrix().getRow(j).get(i)))
                    setWorkingMatrix(getWorkingMatrix().swapRows(i, j));
        }
    }

    // endregion

    // region solution checks

    /**
     * @return {@code true} if current {@link #getWorkingMatrix()} has no solutions
     * @implSpec checks if a row values {@link #areAllZeroIgnoringSolution(Collection)}
     */
    protected boolean hasNoSolutions() {
        for (int i = 0; i < getWorkingMatrix().getRows(); i++) {
            List<T> row = getWorkingMatrix().getRow(i);
            if (!getArithmetic().isZero(row.get(row.size() - 1)) &&
                areAllZeroIgnoringSolution(row))
                return true;
        }
        return false;
    }

    /**
     * @return {@code true} if current {@link #getWorkingMatrix()} has infinite solutions
     * @implSpec pre: {@link #prepareMatrix()} and {@link #solveBottomUp()};
     * all possible eliminations have been done -
     * it is checking for non-zero values between main diagonale and solution
     */
    protected boolean hasInfiniteSolutions() {
        for (int i = 0; i < getWorkingMatrix().getRows(); i++) {
            List<T> row = getWorkingMatrix().getRow(i);
            if (areAllZero(row) || row.size() <= i + 1)
                continue; // 2nd part: how to check remaining rows?
            List<T> subList = row.subList(i + 1, row.size() - 1);
            if (subList.stream().anyMatch(t -> !getArithmetic().isZero(t)))
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
     * class which stores two col indices
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

        // region override

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ColPair)) return false;
            ColPair colPair = (ColPair) o;
            return getCol1() == colPair.getCol1() &&
                getCol2() == colPair.getCol2();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getCol1(), getCol2());
        }

        @Override
        public String toString() {
            return getCol1() + " <> " + getCol2();
        }

        // endregion
    }
}
