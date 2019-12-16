package io.rala.math.testUtils.algebra.equation;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.equation.linear.AbstractLinearSolver;
import io.rala.math.algebra.equation.linear.LinearEquationSystem;

public class TestAbstractLinearSolver extends AbstractLinearSolver<Number> {
    public TestAbstractLinearSolver(LinearEquationSystem<Number> equationSystem) {
        super(equationSystem);
    }

    @Override
    public Solution<LinearEquationSystem<Number>, Number> solve() {
        return null;
    }
}
