package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.equation.TestAbstractLinearSolver;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(solver.getArithmetic() instanceof TestAbstractArithmetic);
    }

    @Test
    void getAndSetWorkingMatrixOfAbstractLinearSolver() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertThrows(NullPointerException.class,
            solver::getWorkingMatrix
        );
        solver.setWorkingMatrix(equationSystem.getMatrix());
        assertNotNull(solver.getWorkingMatrix());
    }

    @Test
    void toSingleSolutionOfAbstractLinearSolverWithNull() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertThrows(NullPointerException.class,
            solver::toSingleSolution
        );
    }

    @Test
    void toSingleSolutionOfAbstractLinearSolverWithNonNull() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        solver.reset();

        Solution<LinearEquationSystem<Number>, Number> expectedSolution =
            Solution.single(equationSystem, List.of(2, 4));
        assertEquals(expectedSolution, solver.toSingleSolution());
    }

    // region protected final utils

    @Test
    void areAllZeroWithOnlyZeros() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertTrue(solver.areAllZero(List.of(0d, 0d)));
    }

    @Test
    void areAllZeroWithOnlyZerosExceptSolution() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertFalse(solver.areAllZero(List.of(0, 1)));
    }

    @Test
    void areAllZeroIgnoringSolutionWithOnlyZeros() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertTrue(solver.areAllZero(List.of(0d, 0d)));
    }

    @Test
    void areAllZeroIgnoringSolutionWithOnlyZerosExceptSolution() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertTrue(solver.areAllZeroIgnoringSolution(List.of(0d, 1d)));
    }

    @Test
    void areAllZeroIgnoringSolutionWithNotOnlyZerosExceptSolution() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertFalse(solver.areAllZeroIgnoringSolution(List.of(1, 0)));
    }

    // endregion
}
