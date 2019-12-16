package io.rala.math.algebra.equation;

import java.util.Objects;

/**
 * class which supports solving {@link AbstractEquationSystem}s
 *
 * @param <T> number class of {@link AbstractEquationSystem}
 */
public abstract class AbstractSolver<E extends AbstractEquationSystem, T extends Number> {
    // region attributes

    private final E equationSystem;
    private E working;

    // endregion

    /**
     * creates a {@link AbstractSolver} for given {@link AbstractEquationSystem}
     *
     * @param equationSystem equationSystem to store
     */
    protected AbstractSolver(E equationSystem) {
        this.equationSystem = equationSystem;
    }

    // region getter

    /**
     * @return stored {@link AbstractEquationSystem}
     */
    public final E getEquationSystem() {
        return equationSystem;
    }

    /**
     * @return current working instance
     */
    protected E getWorking() {
        return working;
    }

    /**
     * @param working new working instance
     */
    protected void setWorking(E working) {
        this.working = working;
    }

    // endregion

    // region abstract

    /**
     * @return solution of {@link AbstractEquationSystem}
     */
    public abstract Solution<E, T> solve();

    /**
     * resets solver
     *
     * @implSpec resets {@link #working} to start {@link #getEquationSystem()}
     */
    protected abstract void reset();

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractSolver)) return false;
        AbstractSolver<?, ?> that = (AbstractSolver<?, ?>) o;
        return Objects.equals(getEquationSystem(), that.getEquationSystem()) &&
            Objects.equals(working, that.working);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEquationSystem(), working);
    }

    @Override
    public String toString() {
        return getEquationSystem() + " -> " + getWorking();
    }

    // endregion
}
