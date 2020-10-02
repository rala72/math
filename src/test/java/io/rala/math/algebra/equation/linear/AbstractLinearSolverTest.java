package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.algebra.vector.Vector;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.TestVector;
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
            1, 3
        );
        Vector<Number> vector = TestVector.ofValues(2, 4);
        equationSystem = new LinearEquationSystem<>(matrix, vector);
    }

    @Test
    void getArithmeticOfAbstractLinearSolver() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertTrue(solver.getArithmetic() instanceof TestAbstractArithmetic);
    }

    @Test
    void getAndSetWorkingEquationSystemOfAbstractLinearSolver() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertThrows(NullPointerException.class,
            solver::getWorkingMatrix
        );
        assertThrows(NullPointerException.class,
            solver::getWorkingVector
        );
        solver.setWorkingEquationSystem(new LinearEquationSystem<>(
            equationSystem.getMatrix(), equationSystem.getVector())
        );
        assertNotNull(solver.getWorkingMatrix());
        assertNotNull(solver.getWorkingVector());
    }

    @Test
    void getAndSetWorkingMatrixAndVectorOfAbstractLinearSolver() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertThrows(NullPointerException.class,
            solver::getWorkingMatrix
        );
        solver.setWorkingEquationSystem(equationSystem.getMatrix(), equationSystem.getVector());
        assertNotNull(solver.getWorkingMatrix());
        assertNotNull(solver.getWorkingVector());
    }

    @Test
    void toSingleSolutionOfAbstractLinearSolverWithNull() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertThrows(NullPointerException.class, solver::toSingleSolution);
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
    void isZeroWithZero() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertTrue(solver.isZero(0d));
    }

    @Test
    void isZeroWithNonZero() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertFalse(solver.isZero(1d));
    }

    // endregion
}
