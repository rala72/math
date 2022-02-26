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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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
        assertThat(solver.getArithmetic()).isInstanceOf(TestAbstractArithmetic.class);
    }

    @Test
    void getAndSetWorkingEquationSystemOfAbstractLinearSolver() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(solver::getWorkingMatrix);
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(solver::getWorkingVector);
        solver.setWorkingEquationSystem(new LinearEquationSystem<>(
            equationSystem.getMatrix(), equationSystem.getVector())
        );
        assertThat(solver.getWorkingMatrix()).isNotNull();
        assertThat(solver.getWorkingVector()).isNotNull();
    }

    @Test
    void getAndSetWorkingMatrixAndVectorOfAbstractLinearSolver() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(solver::getWorkingMatrix);
        solver.setWorkingEquationSystem(equationSystem.getMatrix(), equationSystem.getVector());
        assertThat(solver.getWorkingMatrix()).isNotNull();
        assertThat(solver.getWorkingVector()).isNotNull();
    }

    @Test
    void toSingleSolutionOfAbstractLinearSolverWithNull() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(solver::toSingleSolution);
    }

    @Test
    void toSingleSolutionOfAbstractLinearSolverWithNonNull() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        solver.reset();

        Solution<LinearEquationSystem<Number>, Number> expectedSolution =
            Solution.single(equationSystem, List.of(2, 4));
        assertThat(solver.toSingleSolution()).isEqualTo(expectedSolution);
    }

    @Test
    void resetOfEquationSystemWithColumnVector() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        solver.reset();
        assertThat(solver.getWorkingMatrix()).isEqualTo(equationSystem.getMatrix());
        assertThat(solver.getWorkingVector()).isEqualTo(equationSystem.getVector());
        assertThat(solver.getWorkingVector().getType()).isEqualTo(Vector.Type.COLUMN);
    }

    @Test
    void resetOfEquationSystemWithRowVector() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(new LinearEquationSystem<>(
                equationSystem.getMatrix().transpose(),
                equationSystem.getVector().transpose()
            ));
        solver.reset();
        assertThat(solver.getWorkingMatrix()).isEqualTo(equationSystem.getMatrix());
        assertThat(solver.getWorkingVector()).isEqualTo(equationSystem.getVector());
        assertThat(solver.getEquationSystem().getVector().getType()).isEqualTo(Vector.Type.ROW);
        assertThat(solver.getWorkingVector().getType()).isEqualTo(Vector.Type.COLUMN);
    }

    // region protected final utils

    @Test
    void isZeroRowWithOnlyZeroValues() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(LinearEquationSystem.ofMatrixWithSolutionColumn(
                TestMatrix.ofValuesByRows(1, 0d, 0d)
            ));
        solver.reset();
        assertThat(solver.isZeroRow(0)).isTrue();
    }

    @Test
    void isZeroRowWithOnlyZeroValuesExceptSolution() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(LinearEquationSystem.ofMatrixWithSolutionColumn(
                TestMatrix.ofValuesByRows(1, 0d, 1d)
            ));
        solver.reset();
        assertThat(solver.isZeroRow(0)).isFalse();
    }

    @Test
    void areAllZeroWithOnlyZeros() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertThat(solver.areAllZero(List.of(0d, 0d))).isTrue();
    }

    @Test
    void areAllZeroWithOnlyZerosExceptSolution() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertThat(solver.areAllZero(List.of(0, 1))).isFalse();
    }

    @Test
    void isZeroWithZero() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertThat(solver.isZero(0d)).isTrue();
    }

    @Test
    void isZeroWithNonZero() {
        TestAbstractLinearSolver solver =
            new TestAbstractLinearSolver(equationSystem);
        assertThat(solver.isZero(1d)).isFalse();
    }

    // endregion
}
