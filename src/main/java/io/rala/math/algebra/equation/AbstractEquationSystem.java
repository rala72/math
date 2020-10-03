package io.rala.math.algebra.equation;

/**
 * class which stores and allows to solve equation systems
 *
 * @implSpec this class has only {@link #transpose()} at the moment
 */
public abstract class AbstractEquationSystem<T extends AbstractEquationSystem<T>> {
    /**
     * @return new transposed equation system
     */
    public abstract T transpose();
}
