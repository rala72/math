package io.rala.math.testUtils.algebra.equation;

import io.rala.math.algebra.equation.AbstractEquationSystem;
import org.jetbrains.annotations.NotNull;

public class TestAbstractEquationSystem extends AbstractEquationSystem<TestAbstractEquationSystem> {
    /**
     * {@inheritDoc}
     *
     * @implSpec returns {@code this} for test purpose
     */
    @Override
    @NotNull
    public TestAbstractEquationSystem transpose() {
        return this;
    }
}
