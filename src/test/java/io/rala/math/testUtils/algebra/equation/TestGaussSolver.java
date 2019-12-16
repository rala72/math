package io.rala.math.testUtils.algebra.equation;

import io.rala.math.algebra.equation.linear.LinearEquationSystem;
import io.rala.math.algebra.equation.linear.solver.GaussSolver;
import io.rala.math.algebra.matrix.Matrix;

import java.util.Deque;

public class TestGaussSolver<T extends Number> extends GaussSolver<T> {
    public TestGaussSolver(Matrix<T> matrix) {
        this(new LinearEquationSystem<>(matrix));
    }

    public TestGaussSolver(LinearEquationSystem<T> equationSystem) {
        super(equationSystem);
    }

    @Override
    public LinearEquationSystem.LinearEquationMatrix<T> getWorkingMatrix() {
        return super.getWorkingMatrix();
    }

    @Override
    public Deque<ColPair> getSwappedCols() {
        return super.getSwappedCols();
    }
}
