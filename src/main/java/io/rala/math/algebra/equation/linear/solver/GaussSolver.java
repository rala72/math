package io.rala.math.algebra.equation.linear.solver;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.equation.linear.AbstractLinearSolver;
import io.rala.math.algebra.equation.linear.LinearEquationSystem;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * solves a {@link LinearEquationSystem} based on Gauss
 *
 * @param <T> number class
 * @since 1.0.0
 */
public class GaussSolver<T extends Number> extends AbstractLinearSolver<T> {
    private final Deque<ColPair> swappedCols = new LinkedList<>();

    /**
     * creates a new GaussSolver based on a {@link LinearEquationSystem}
     *
     * @param equationSystem equation system to solve
     * @since 1.0.0
     */
    public GaussSolver(@NotNull LinearEquationSystem<T> equationSystem) {
        super(equationSystem);
    }

    // region protected getSwappedCols

    /**
     * @return current swappedCols instance
     * @since 1.0.0
     */
    @NotNull
    protected Deque<ColPair> getSwappedCols() {
        return swappedCols;
    }

    // endregion

    @Override
    @NotNull
    public Solution<LinearEquationSystem<T>, T> solve() {
        reset();
        prepareMatrix();
        if (hasNoSolutions())
            return Solution.unsolvable(getEquationSystem());
        if (getWorkingMatrix().getRows() < getWorkingMatrix().getCols())
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
     *
     * @since 1.0.0
     */
    protected void prepareMatrix() {
        prepareMatrixBySwappingZeroRowsToBottom();
        for (int i = 0; i < getWorkingMatrix().getRows(); i++) {
            if (areAllZero(getWorkingMatrix().getRow(i)))
                break;
            prepareMatrixBySwapping(i);
            prepareMatrixByMakingFieldToOne(i);
            prepareMatrixByMakeColToZero(i);
        }
    }

    /**
     * moves zero rows to bottom
     *
     * @since 1.0.0
     */
    protected void prepareMatrixBySwappingZeroRowsToBottom() {
        for (int i = 0; i < getWorkingMatrix().getRows() - 1; i++) {
            if (!isZeroRow(i)) continue;
            for (int j = i + 1; j < getWorkingMatrix().getRows(); j++)
                if (!isZeroRow(j)) {
                    setWorkingEquationSystem(getWorking().swapRows(i, j));
                    break;
                }
        }
    }

    /**
     * swaps (preferred) rows and (if required) cols to
     * ensure {@code rowIndex} value is not {@code 0}
     * <p>
     * solution column will be untouched if columns are swapped
     *
     * @param rowIndex rowIndex to handle
     * @implSpec pre: non zero row
     * @since 1.0.0
     */
    protected void prepareMatrixBySwapping(int rowIndex) {
        List<T> row = getWorkingMatrix().getRow(rowIndex);
        if (row.size() <= rowIndex || !isZero(row.get(rowIndex)))
            return;
        List<T> col = getWorkingMatrix().getCol(rowIndex);
        for (int i = rowIndex + 1; i < getWorkingMatrix().getRows(); i++)
            if (!isZero(col.get(i))) {
                setWorkingEquationSystem(getWorking().swapRows(rowIndex, i));
                return;
            } else if (isZeroRow(i)) break;
        for (int i = rowIndex + 1; i < getWorkingMatrix().getCols(); i++)
            if (!isZero(row.get(i))) {
                setWorkingEquationSystem(getWorking().swapCols(rowIndex, i));
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
     * @since 1.0.0
     */
    protected void prepareMatrixByMakingFieldToOne(int rowIndex) {
        T rowIndexValue = getWorkingMatrix().getValue(rowIndex, rowIndex);
        if (getArithmetic().one().equals(rowIndexValue))
            return;
        T quotient = getArithmetic().quotient(getArithmetic().one(), rowIndexValue);
        setWorkingEquationSystem(getWorking().multiplyRow(rowIndex, quotient));
        if (!getArithmetic().one().equals(getWorkingMatrix().getValue(rowIndex, rowIndex)))
            getWorkingMatrix().setValue(rowIndex, rowIndex, getArithmetic().one());
    }

    /**
     * ensures that all col values below {@code rowIndex} are {@code 0}
     *
     * @param rowIndex rowIndex to handle
     * @implSpec pre: <i>see {@link #prepareMatrixByMakingFieldToOne(int)}</i>
     * @since 1.0.0
     */
    protected void prepareMatrixByMakeColToZero(int rowIndex) {
        for (int i = rowIndex + 1; i < getWorkingMatrix().getRows(); i++) {
            T rowIndexValue = getWorkingMatrix().getValue(i, rowIndex);
            if (isZero(rowIndexValue))
                continue;
            T negate = getArithmetic().negate(rowIndexValue);
            setWorkingEquationSystem(getWorking().addRowMultipleTimes(i, rowIndex, negate));
        }
    }

    // endregion

    // region solveBottomUp, reSwapCols and sortRows

    /**
     * clears all values above rows so that only the diagonal is left
     *
     * @since 1.0.0
     */
    protected void solveBottomUp() {
        for (int i = getWorkingMatrix().getRows() - 1; 0 < i; i--) {
            if (isZeroRow(i)) continue;
            for (int j = i - 1; 0 <= j; j--) {
                List<T> row = getWorkingMatrix().getRow(j);
                if (!isZero(row.get(i))) {
                    T negate = getArithmetic().negate(row.get(i));
                    setWorkingEquationSystem(getWorking().addRowMultipleTimes(j, i, negate));
                }
            }
        }
    }

    /**
     * reSwaps cols which have been swapped during {@link #prepareMatrixBySwapping(int)}
     *
     * @since 1.0.0
     */
    protected void reSwapCols() {
        while (!getSwappedCols().isEmpty()) {
            ColPair pop = getSwappedCols().pop();
            setWorkingEquationSystem(getWorking().swapCols(pop.getCol1(), pop.getCol2()));
        }
    }

    /**
     * sorts rows after {@link #reSwapCols()}
     *
     * @implSpec suggested only if {@link #getSwappedCols()} was non-empty
     * @since 1.0.0
     */
    protected void sortRows() {
        for (int i = 0; i < getWorkingMatrix().getRows(); i++) {
            if (isZeroRow(i) || !isZero(getWorkingMatrix().getRow(i).get(i)))
                continue;
            for (int j = i + 1; j < getWorkingMatrix().getRows(); j++)
                if (!isZero(getWorkingMatrix().getRow(j).get(i)))
                    setWorkingEquationSystem(getWorking().swapRows(i, j));
        }
    }

    // endregion

    // region solution checks

    /**
     * @return {@code true} if current {@link #getWorkingMatrix()} has no solutions
     * @implSpec checks if a solution value is unequal zero and
     * matrix row values {@link #areAllZero(Collection)}
     * @since 1.0.0
     */
    protected boolean hasNoSolutions() {
        for (int i = 0; i < getWorkingMatrix().getRows(); i++) {
            if (!isZero(getWorkingVector().getValue(i)) &&
                areAllZero(getWorkingMatrix().getRow(i)))
                return true;
        }
        return false;
    }

    /**
     * @return {@code true} if current {@link #getWorkingMatrix()} has infinite solutions
     * @implSpec pre: {@link #prepareMatrix()} and {@link #solveBottomUp()};
     * all possible eliminations have been done -
     * it is checking for non-zero values between main diagonale and solution
     * @since 1.0.0
     */
    protected boolean hasInfiniteSolutions() {
        for (int i = 0; i < getWorkingMatrix().getRows(); i++) {
            List<T> row = getWorkingMatrix().getRow(i);
            if (areAllZero(row) || row.size() <= i)
                continue; // 2nd part: how to check remaining rows?
            List<T> subList = row.subList(i + 1, row.size());
            if (subList.stream().anyMatch(t -> !isZero(t)))
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
     *
     * @since 1.0.0
     */
    protected static class ColPair {
        private final int col1;
        private final int col2;

        /**
         * creates a new pair based on given parameters
         *
         * @param col1 first col
         * @param col2 second col
         * @since 1.0.0
         */
        public ColPair(int col1, int col2) {
            this.col1 = col1;
            this.col2 = col2;
        }

        /**
         * @return col1
         * @since 1.0.0
         */
        public int getCol1() {
            return col1;
        }

        /**
         * @return col2
         * @since 1.0.0
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
        @NotNull
        public String toString() {
            return getCol1() + " <> " + getCol2();
        }

        // endregion
    }
}
