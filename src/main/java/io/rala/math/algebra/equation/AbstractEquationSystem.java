package io.rala.math.algebra.equation;

import org.jetbrains.annotations.NotNull;

/**
 * class which stores and allows to solve equation systems
 *
 * @param <T> class of equation system
 * @implSpec this class has only {@link #transpose()} at the moment
 * @since 1.0.0
 */
public abstract class AbstractEquationSystem<T extends AbstractEquationSystem<T>> {
    /**
     * @return new transposed equation system
     * @since 1.0.0
     */
    @NotNull
    protected abstract T transpose();
}
