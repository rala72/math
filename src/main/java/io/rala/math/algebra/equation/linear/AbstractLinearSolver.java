package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.AbstractSolver;
import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.vector.Vector;
import io.rala.math.arithmetic.AbstractArithmetic;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * class which allows solving {@link LinearEquationSystem}s
 *
 * @param <T> number class
 * @since 1.0.0
 */
public abstract class AbstractLinearSolver<T extends Number> extends AbstractSolver<LinearEquationSystem<T>, T> {
    /**
     * creates a {@link AbstractLinearSolver} for given {@link LinearEquationSystem}
     *
     * @param equationSystem equationSystem to store
     * @since 1.0.0
     */
    protected AbstractLinearSolver(@NotNull LinearEquationSystem<T> equationSystem) {
        super(equationSystem);
    }

    /**
     * @return {@link AbstractArithmetic} of {@link #getEquationSystem()}
     * @since 1.0.0
     */
    @NotNull
    protected final AbstractArithmetic<T> getArithmetic() {
        return getEquationSystem().getMatrix().getArithmetic();
    }

    /**
     * @return current working matrix
     * @see #getWorking()
     * @since 1.0.0
     */
    @NotNull
    protected LinearEquationSystem.LinearEquationMatrix<T> getWorkingMatrix() {
        return getWorking().getMatrix();
    }

    /**
     * @return current working vector
     * @see #getWorking()
     * @since 1.0.0
     */
    @NotNull
    protected LinearEquationSystem.LinearEquationVector<T> getWorkingVector() {
        return getWorking().getVector();
    }

    /**
     * @param working new working instance
     * @since 1.0.0
     */
    protected void setWorkingEquationSystem(@NotNull LinearEquationSystem<T> working) {
        setWorkingEquationSystem(working.getMatrix(), working.getVector());
    }

    /**
     * @param workingMatrix new workingMatrix
     * @param workingVector new workingVector
     * @since 1.0.0
     */
    protected void setWorkingEquationSystem(
        @NotNull LinearEquationSystem.LinearEquationMatrix<T> workingMatrix,
        @NotNull LinearEquationSystem.LinearEquationVector<T> workingVector
    ) {
        setWorking(new LinearEquationSystem<>(workingMatrix, workingVector));
    }

    /**
     * converts {@link #getWorkingVector()} to a
     * {@link Solution} with state {@link Solution.State#SINGLE}
     *
     * @return {@link Solution} based on {@link #getWorkingVector()}
     * @since 1.0.0
     */
    @NotNull
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
     * @since 1.0.0
     */
    @MustBeInvokedByOverriders
    protected void reset() {
        if (getEquationSystem().getVector().getType().equals(Vector.Type.ROW))
            setWorking(getEquationSystem().transpose());
        else super.reset();
    }

    // region protected final utils

    /**
     * @param index index of row
     * @return {@code true} if all values in {@link #getWorking()}
     * matrix row and vector index are {@code 0}
     * @see #areAllZero(Collection)
     * @see #isZero(Number)
     * @since 1.0.0
     */
    protected final boolean isZeroRow(int index) {
        return areAllZero(getWorkingMatrix().getRow(index)) &&
            isZero(getWorkingVector().getValue(index));
    }

    /**
     * @param collection collection to check
     * @return {@code true} if all elements are {@code 0}
     * @since 1.0.0
     */
    protected final boolean areAllZero(@NotNull Collection<T> collection) {
        return collection.stream().allMatch(this::isZero);
    }

    /**
     * @param t t to check
     * @return {@code} if {@link AbstractArithmetic#isZero(Number)}
     * @since 1.0.0
     */
    protected final boolean isZero(@NotNull T t) {
        return getArithmetic().isZero(t);
    }

    // endregion
}
