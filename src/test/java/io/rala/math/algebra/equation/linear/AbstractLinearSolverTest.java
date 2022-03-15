package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.algebra.vector.Vector;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.TestVector;
import io.rala.math.testUtils.algebra.equation.TestAbstractLinearSolver;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class AbstractLinearSolverTest {
    private static LinearEquationSystem<Number> equationSystem;
    private AbstractLinearSolver<Number> solver;

    @BeforeAll
    static void beforeAll() {
        Matrix<Number> matrix = TestMatrix.ofValuesByRows(2,
            1, 3
        );
        Vector<Number> vector = TestVector.ofValues(2, 4);
        equationSystem = new LinearEquationSystem<>(matrix, vector);
    }

    @BeforeEach
    void setUp() {
        solver = new TestAbstractLinearSolver(equationSystem);
    }

    @Test
    void getArithmeticOfAbstractLinearSolver() {
        assertThat(solver.getArithmetic()).isInstanceOf(TestAbstractArithmetic.class);
    }

    @Test
    void getAndSetWorkingEquationSystemOfAbstractLinearSolver() {
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
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(solver::getWorkingMatrix);
        solver.setWorkingEquationSystem(equationSystem.getMatrix(), equationSystem.getVector());
        assertThat(solver.getWorkingMatrix()).isNotNull();
        assertThat(solver.getWorkingVector()).isNotNull();
    }

    @Test
    void toSingleSolutionOfAbstractLinearSolverWithNull() {
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(solver::toSingleSolution);
    }

    @Test
    void toSingleSolutionOfAbstractLinearSolverWithNonNull() {
        solver.reset();

        Solution<LinearEquationSystem<Number>, Number> expectedSolution =
            Solution.single(equationSystem, List.of(2, 4));
        assertThat(solver.toSingleSolution()).isEqualTo(expectedSolution);
    }

    @Test
    void resetOfEquationSystemWithColumnVector() {
        solver.reset();
        assertThat(solver.getWorkingMatrix()).isEqualTo(equationSystem.getMatrix());
        assertThat(solver.getWorkingVector()).isEqualTo(equationSystem.getVector());
        assertThat(solver.getWorkingVector().getType()).isEqualTo(Vector.Type.COLUMN);
    }

    @Test
    void resetOfEquationSystemWithRowVector() {
        solver = new TestAbstractLinearSolver(
            new LinearEquationSystem<>(
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
        solver = new TestAbstractLinearSolver(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                TestMatrix.ofValuesByRows(1, 0d, 0d)
            ));
        solver.reset();
        assertThat(solver.isZeroRow(0)).isTrue();
    }

    @Test
    void isZeroRowWithOnlyZeroValuesExceptSolution() {
        solver = new TestAbstractLinearSolver(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                TestMatrix.ofValuesByRows(1, 0d, 1d)
            ));
        solver.reset();
        assertThat(solver.isZeroRow(0)).isFalse();
    }

    @Test
    void areAllZeroWithOnlyZeros() {
        assertThat(solver.areAllZero(List.of(0d, 0d))).isTrue();
    }

    @Test
    void areAllZeroWithOnlyZerosExceptSolution() {
        assertThat(solver.areAllZero(List.of(0, 1))).isFalse();
    }

    @Test
    void isZeroWithZero() {
        assertThat(solver.isZero(0d)).isTrue();
    }

    @Test
    void isZeroWithNonZero() {
        assertThat(solver.isZero(1d)).isFalse();
    }

    // endregion
}
