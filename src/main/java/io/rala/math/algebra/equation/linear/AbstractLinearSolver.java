package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.AbstractSolver;
import io.rala.math.algebra.equation.Solution;
import io.rala.math.arithmetic.AbstractArithmetic;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * class which allows solving {@link LinearEquationSystem}s
 *
 * @param <T> number class
 */
public abstract class AbstractLinearSolver<T extends Number> extends AbstractSolver<LinearEquationSystem<T>, T> {
    /**
     * creates a {@link AbstractLinearSolver} for given {@link LinearEquationSystem}
     *
     * @param equationSystem equationSystem to store
     */
    protected AbstractLinearSolver(LinearEquationSystem<T> equationSystem) {
        super(equationSystem);
    }

    /**
     * @return {@link AbstractArithmetic} of {@link #getEquationSystem()}
     */
    protected final AbstractArithmetic<T> getArithmetic() {
        return getEquationSystem().getMatrix().getArithmetic();
    }

    /**
     * @return current working matrix
     * @see #getWorking()
     */
    protected LinearEquationSystem.LinearEquationMatrix<T> getWorkingMatrix() {
        return getWorking().getMatrix();
    }

    /**
     * @param workingMatrix new workingMatrix
     */
    protected void setWorkingMatrix(LinearEquationSystem.LinearEquationMatrix<T> workingMatrix) {
        setWorking(new LinearEquationSystem<>(workingMatrix));
    }

    /**
     * @return {@link Solution} based on {@link #getWorkingMatrix()}
     */
    protected Solution<LinearEquationSystem<T>, T> toSolvedSolution() {
        return Solution.solved(getEquationSystem(),
            getWorkingMatrix().getCol(getWorkingMatrix().getCols() - 1)
        );
    }

    protected void reset() {
        setWorkingMatrix(getEquationSystem().getMatrix());
    }

    // region protected final utils

    /**
     * @param collection collection to check
     * @return {@code true} if all elements are {@code 0}
     */
    protected final boolean areAllZero(Collection<T> collection) {
        return collection.stream().allMatch(t -> getArithmetic().isZero(t));
    }

    /**
     * @param collection collection to check
     * @return {@code true} if all elements ignoring last one are {@code 0}
     * @see #areAllZero(Collection)
     * @see List#subList(int, int)
     */
    protected final boolean areAllZeroIgnoringSolution(Collection<T> collection) {
        return areAllZero(
            new LinkedList<>(collection).subList(0, collection.size() - 1)
        );
    }

    // endregion
}
