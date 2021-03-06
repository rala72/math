package io.rala.math.algebra.equation;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * class which holds a solution of a {@link AbstractEquationSystem}
 *
 * @param <E> class of {@link AbstractEquationSystem}
 * @param <T> number class
 * @since 1.0.0
 */
public class Solution<E extends AbstractEquationSystem<E>, T extends Number> {
    /**
     * describes the state of the solution of a {@link AbstractEquationSystem}
     * which can either have a {@link #SINGLE} or {@link #INFINITE} solutions or
     * is {@link #UNSOLVABLE}
     *
     * @since 1.0.0
     */
    public enum State {SINGLE, UNSOLVABLE, INFINITE}

    // region attributes

    private final E equationSystem;
    private final List<T> solution;
    private final State state;

    // endregion

    /**
     * creates a new solution with given values
     *
     * @param equationSystem equation system to store
     * @param solution       solution values
     * @param state          state of solution
     * @throws IllegalArgumentException if any argument is {@code null}
     * @since 1.0.0
     */
    public Solution(E equationSystem, List<T> solution, State state) {
        if (equationSystem == null || solution == null || state == null)
            throw new IllegalArgumentException("arguments must not be null");
        this.equationSystem = equationSystem;
        this.solution = solution;
        this.state = state;
    }

    // region getter

    /**
     * @return equation system of solution
     * @since 1.0.0
     */
    public E getEquationSystem() {
        return equationSystem;
    }

    /**
     * @return solution values
     * @since 1.0.0
     */
    public List<T> getSolution() {
        return Collections.unmodifiableList(solution);
    }

    /**
     * @return {@link State} of solution
     * @since 1.0.0
     */
    public State getState() {
        return state;
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Solution)) return false;
        Solution<?, ?> solution = (Solution<?, ?>) o;
        return Objects.equals(getEquationSystem(), solution.getEquationSystem()) &&
            Objects.equals(getSolution(), solution.getSolution()) &&
            getState() == solution.getState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEquationSystem(), getSolution(), getState());
    }

    @Override
    public String toString() {
        return getState() + ": " + getSolution();
    }

    // endregion

    // region public static

    /**
     * creates a solved solution for given equationSystem
     *
     * @param equationSystem equation system to store
     * @param solution       solution values
     * @param <E>            evaluation class
     * @param <T>            number class
     * @return new {@link Solution} instance
     * @since 1.0.0
     */
    public static <E extends AbstractEquationSystem<E>, T extends Number>
    Solution<E, T> single(E equationSystem, List<T> solution) {
        return new Solution<>(equationSystem, solution, State.SINGLE);
    }

    /**
     * creates a unsolvable solution for given equationSystem
     *
     * @param equationSystem equation system to store
     * @param <E>            evaluation class
     * @param <T>            number class
     * @return new {@link Solution} instance
     * @since 1.0.0
     */
    public static <E extends AbstractEquationSystem<E>, T extends Number>
    Solution<E, T> unsolvable(E equationSystem) {
        return new Solution<>(equationSystem, Collections.emptyList(), State.UNSOLVABLE);
    }

    /**
     * creates a infinite solution for given equationSystem
     *
     * @param equationSystem equation system to store
     * @param <E>            evaluation class
     * @param <T>            number class
     * @return new {@link Solution} instance
     * @since 1.0.0
     */
    public static <E extends AbstractEquationSystem<E>, T extends Number>
    Solution<E, T> infinite(E equationSystem) {
        return new Solution<>(equationSystem, Collections.emptyList(), State.INFINITE);
    }

    // endregion
}
