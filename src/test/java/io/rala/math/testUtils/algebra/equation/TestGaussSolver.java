package io.rala.math.testUtils.algebra.equation;

import io.rala.math.algebra.equation.linear.LinearEquationSystem;
import io.rala.math.algebra.equation.linear.solver.GaussSolver;
import io.rala.math.algebra.matrix.Matrix;
import org.jetbrains.annotations.NotNull;

import java.util.Deque;

public class TestGaussSolver<T extends Number> extends GaussSolver<T> {
    public TestGaussSolver(Matrix<T> matrix) {
        this(LinearEquationSystem.ofMatrixWithSolutionColumn(matrix));
    }

    public TestGaussSolver(LinearEquationSystem<T> equationSystem) {
        super(equationSystem);
    }

    @Override
    @NotNull
    public LinearEquationSystem<T> getWorking() {
        return super.getWorking();
    }

    @Override
    @NotNull
    public Deque<ColPair> getSwappedCols() {
        return super.getSwappedCols();
    }
}
