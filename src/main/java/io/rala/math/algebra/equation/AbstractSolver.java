package io.rala.math.algebra.equation;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * class which supports solving {@link AbstractEquationSystem}s
 *
 * @param <E> class of {@link AbstractEquationSystem}
 * @param <T> number class of {@link AbstractEquationSystem}
 * @since 1.0.0
 */
public abstract class AbstractSolver<E extends AbstractEquationSystem<E>, T extends Number> {
    // region attributes

    private final E equationSystem;
    private E working;

    // endregion

    /**
     * creates a {@link AbstractSolver} for given {@link AbstractEquationSystem}
     *
     * @param equationSystem equationSystem to store
     * @since 1.0.0
     */
    protected AbstractSolver(@NotNull E equationSystem) {
        this.equationSystem = equationSystem;
    }

    // region getter and setter

    /**
     * @return stored {@link AbstractEquationSystem}
     * @since 1.0.0
     */
    @NotNull
    public final E getEquationSystem() {
        return equationSystem;
    }

    /**
     * @return current working instance
     * @throws IllegalStateException if {@code working} is {@code null}
     * @since 1.0.0
     */
    @NotNull
    protected E getWorking() {
        if (working == null)
            throw new IllegalStateException("working not set");
        return working;
    }

    /**
     * @param working new working instance
     * @since 1.0.0
     */
    protected void setWorking(@NotNull E working) {
        this.working = working;
    }

    // endregion

    // region abstract

    /**
     * solves {@link AbstractEquationSystem} and returns {@link Solution}
     *
     * @return solution of {@link AbstractEquationSystem}
     * @since 1.0.0
     */
    @NotNull
    public abstract Solution<E, T> solve();

    /**
     * resets solver
     *
     * @implSpec resets {@link #getWorking()} to {@link #getEquationSystem()}
     * @since 1.0.0
     */
    protected void reset() {
        setWorking(getEquationSystem());
    }

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
    @NotNull
    public String toString() {
        return getEquationSystem() + " -> " + getWorking();
    }

    // endregion
}
