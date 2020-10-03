package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.AbstractSolver;
import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.vector.Vector;
import io.rala.math.arithmetic.AbstractArithmetic;

import java.util.Collection;
import java.util.stream.Collectors;

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
     * @return current working vector
     * @see #getWorking()
     */
    protected LinearEquationSystem.LinearEquationVector<T> getWorkingVector() {
        return getWorking().getVector();
    }

    /**
     * @param working new working instance
     */
    protected void setWorkingEquationSystem(LinearEquationSystem<T> working) {
        setWorkingEquationSystem(working.getMatrix(), working.getVector());
    }

    /**
     * @param workingMatrix new workingMatrix
     * @param workingVector new workingVector
     */
    protected void setWorkingEquationSystem(
        LinearEquationSystem.LinearEquationMatrix<T> workingMatrix,
        LinearEquationSystem.LinearEquationVector<T> workingVector
    ) {
        setWorking(new LinearEquationSystem<>(workingMatrix, workingVector));
    }

    /**
     * @return {@link Solution} based on {@link #getWorkingMatrix()}
     */
    protected Solution<LinearEquationSystem<T>, T> toSingleSolution() {
        return Solution.single(getEquationSystem(),
            getWorkingVector().stream().map(Vector.Entry::getValue).collect(Collectors.toList())
        );
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec transposes {@link #getWorking()} equation system
     * if {@link #getEquationSystem()} has solution {@link Vector.Type#ROW}
     */
    protected void reset() {
        if (getEquationSystem().getVector().getType().equals(Vector.Type.ROW))
            setWorking(getEquationSystem().transpose());
        else super.reset();
    }

    // region protected final utils

    /**
     * @param collection collection to check
     * @return {@code true} if all elements are {@code 0}
     */
    protected final boolean areAllZero(Collection<T> collection) {
        return collection.stream().allMatch(this::isZero);
    }

    /**
     * @param t t to check
     * @return {@code} if {@link AbstractArithmetic#isZero(Number)}
     */
    protected final boolean isZero(T t) {
        return getArithmetic().isZero(t);
    }

    // endregion
}
