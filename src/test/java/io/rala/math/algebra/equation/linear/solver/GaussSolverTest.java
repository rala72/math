package io.rala.math.algebra.equation.linear.solver;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.equation.linear.LinearEquationSystem;
import io.rala.math.algebra.matrix.DoubleMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

class GaussSolverTest {
    private static LinearEquationSystem<Double> equationSystem1;
    private static LinearEquationSystem<Double> equationSystem2;

    @BeforeAll
    static void beforeAll() {
        equationSystem1 = new LinearEquationSystem<>(
            DoubleMatrix.ofValuesByRows(3,
                1, 2, 3, 2,
                1, 1, 1, 2,
                3, 3, 1, 0
            )
        );
        equationSystem2 = new LinearEquationSystem<>(
            DoubleMatrix.ofValuesByRows(3,
                1, -1, 2, 0,
                -2, 1, -6, 0,
                1, 0, -2, 3
            )
        );
    }

    @Test
    void solveLinearEquationSystem1() {
        GaussSolver<Double> solver = new GaussSolver<>(equationSystem1);
        Assertions.assertEquals(
            Solution.solved(equationSystem1, List.of(5d, -6d, 3d)),
            solver.solve()
        );
    }

    @Test
    void solveLinearEquationSystem2() {
        GaussSolver<Double> solver = new GaussSolver<>(equationSystem2);
        Assertions.assertEquals(
            Solution.solved(equationSystem2, List.of(2d, 1d, -0.5)),
            solver.solve()
        );
    }
}
