package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.equation.TestAbstractLinearSolver;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

class AbstractLinearSolverTest {
    private static LinearEquationSystem<Number> equationSystem;

    @BeforeAll
    static void beforeAll() {
        Matrix<Number> matrix = TestMatrix.ofValuesByRows(2,
            1, 2, 3, 4
        );
        equationSystem = new LinearEquationSystem<>(matrix);
    }

    @Test
    void getArithmeticOfAbstractLinearSolver() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        Assertions.assertTrue(solver.getArithmetic() instanceof TestAbstractArithmetic);
    }

    @Test
    void getAndSetWorkingMatrixOfAbstractLinearSolver() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        Assertions.assertThrows(NullPointerException.class,
            solver::getWorkingMatrix
        );
        solver.setWorkingMatrix(equationSystem.getMatrix());
        Assertions.assertNotNull(solver.getWorkingMatrix());
    }

    @Test
    void toSolvedSolutionOfAbstractLinearSolverWithNull() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        Assertions.assertThrows(NullPointerException.class,
            solver::toSolvedSolution
        );
    }

    @Test
    void toSolvedSolutionOfAbstractLinearSolverWithNonNull() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        solver.reset();

        Solution<LinearEquationSystem<Number>, Number> expectedSolution =
            Solution.solved(equationSystem, List.of(2, 4));
        Assertions.assertEquals(expectedSolution, solver.toSolvedSolution());
    }

    // region protected final utils

    @Test
    void areAllZeroWithOnlyZeros() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        Assertions.assertTrue(solver.areAllZero(List.of(0, 0)));
    }

    @Test
    void areAllZeroWithOnlyZerosExceptSolution() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        Assertions.assertFalse(solver.areAllZero(List.of(0, 1)));
    }

    @Test
    void areAllZeroIgnoringSolutionWithOnlyZeros() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        Assertions.assertTrue(solver.areAllZero(List.of(0, 0)));
    }

    @Test
    void areAllZeroIgnoringSolutionWithOnlyZerosExceptSolution() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        Assertions.assertTrue(solver.areAllZeroIgnoringSolution(List.of(0, 1)));
    }

    @Test
    void areAllZeroIgnoringSolutionWithNotOnlyZerosExceptSolution() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        Assertions.assertFalse(solver.areAllZeroIgnoringSolution(List.of(1, 0)));
    }

    // endregion
}
