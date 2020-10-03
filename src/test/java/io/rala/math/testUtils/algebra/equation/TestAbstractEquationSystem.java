package io.rala.math.testUtils.algebra.equation;

import io.rala.math.algebra.equation.AbstractEquationSystem;

public class TestAbstractEquationSystem extends AbstractEquationSystem<TestAbstractEquationSystem> {
    /**
     * {@inheritDoc}
     *
     * @implSpec returns {@code this} for test purpose
     */
    @Override
    public TestAbstractEquationSystem transpose() {
        return this;
    }
}
