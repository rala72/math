package io.rala.math.testUtils.algebra.equation;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.equation.linear.AbstractLinearSolver;
import io.rala.math.algebra.equation.linear.LinearEquationSystem;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.TestVector;
import org.jetbrains.annotations.NotNull;

public class TestAbstractLinearSolver extends AbstractLinearSolver<Number> {
    public TestAbstractLinearSolver(LinearEquationSystem<Number> equationSystem) {
        super(equationSystem);
    }

    @Override
    @NotNull
    public Solution<LinearEquationSystem<Number>, Number> solve() {
        return Solution.unsolvable(new LinearEquationSystem<>(
            new TestMatrix(1),
            new TestVector(1)
        ));
    }
}
