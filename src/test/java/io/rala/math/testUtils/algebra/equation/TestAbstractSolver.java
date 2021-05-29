package io.rala.math.testUtils.algebra.equation;

import io.rala.math.algebra.equation.AbstractSolver;
import io.rala.math.algebra.equation.Solution;
import org.jetbrains.annotations.NotNull;

public class TestAbstractSolver extends AbstractSolver<TestAbstractEquationSystem, Number> {
    public TestAbstractSolver(TestAbstractEquationSystem equationSystem) {
        super(equationSystem);
    }

    @Override
    @NotNull
    public Solution<TestAbstractEquationSystem, Number> solve() {
        return Solution.unsolvable(new TestAbstractEquationSystem());
    }
}
