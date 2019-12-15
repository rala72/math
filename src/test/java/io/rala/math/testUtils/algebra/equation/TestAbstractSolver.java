package io.rala.math.testUtils.algebra.equation;

import io.rala.math.algebra.equation.AbstractSolver;
import io.rala.math.algebra.equation.Solution;

public class TestAbstractSolver extends AbstractSolver<TestAbstractEquationSystem, Number> {
    public TestAbstractSolver(TestAbstractEquationSystem equationSystem) {
        super(equationSystem);
    }

    @Override
    public Solution<Number> solve() {
        return null;
    }

    @Override
    public void reset() {
        setWorking(getEquationSystem());
    }
}
