package io.rala.math.algebra.equation.linear.solver;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.equation.linear.LinearEquationSystem;
import io.rala.math.algebra.matrix.typed.DoubleMatrix;
import io.rala.math.testUtils.algebra.equation.TestGaussSolver;
import io.rala.math.testUtils.arguments.LinearEquationArgumentsStreamFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GaussSolverTest {
    @ParameterizedTest
    @MethodSource("getDoubleLinearEquationSystems")
    void solveLinearEquationSystem(Solution<LinearEquationSystem<Double>, Double> solution) {
        GaussSolver<Double> solver = new GaussSolver<>(solution.getEquationSystem());
        assertThat(solver.solve()).isEqualTo(solution);
    }

    // region solve

    @Test
    void solveUnsolvableLinearEquationSystemAfterPrepare() {
        LinearEquationSystem<Double> equationSystem =
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0,
                    0, 1)
            );
        GaussSolver<Double> solver = new GaussSolver<>(equationSystem);
        assertThat(solver.solve()).isEqualTo(Solution.unsolvable(equationSystem));
    }

    @Test
    void solveInfiniteLinearEquationSystemWithLessRowsThanCols() {
        LinearEquationSystem<Double> equationSystem =
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(1,
                    1, 0, 0, 1
                )
            );
        GaussSolver<Double> solver = new GaussSolver<>(equationSystem);
        assertThat(solver.solve()).isEqualTo(Solution.infinite(equationSystem));
    }

    @Test
    void solveInfiniteLinearEquationSystemAfterSolveBottomUp() {
        LinearEquationSystem<Double> equationSystem =
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(3,
                    1, 1, 2, 9,
                    0, 1, 3, 1,
                    0, 2, 6, 2
                )
            );
        GaussSolver<Double> solver = new GaussSolver<>(equationSystem);
        assertThat(solver.solve()).isEqualTo(Solution.infinite(equationSystem));
    }

    // endregion

    // region prepare

    @Test
    void prepareMatrixWithZeroRow() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            2, 1,
            0, 0
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrix();
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, 0.5,
                0, 0
            )
        ));
    }

    @Test
    void prepareMatrixWithMoreRowsThanCols() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(3,
            1, 2,
            2, 3,
            3, 4
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrix();
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(3,
                1, 2,
                0, -1,
                0, -2
            )
        ));
    }

    @Test
    void prepareMatrixBySwappingZeroRowsToBottom() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            0, 0,
            1, 1
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrixBySwappingZeroRowsToBottom();
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, 1,
                0, 0
            )
        ));
    }

    @Test
    void prepareMatrixBySwappingWithNonZeroOnIndex() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 1,
            0, 0
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrixBySwapping(0);
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, 1,
                0, 0
            )
        ));
    }

    @Test
    void prepareMatrixBySwappingWithZeroOnIndexForRowSwap() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            0, 1,
            1, 1
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrixBySwapping(0);
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, 1,
                0, 1
            )
        ));
        assertThat(solver.getSwappedCols()).isEmpty();
    }

    @Test
    void prepareMatrixBySwappingWithZeroOnIndexForColSwap() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            0, 1, 0,
            0, 1, 0
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrixBySwapping(0);
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, 0, 0,
                1, 0, 0
            )
        ));
        Deque<GaussSolver.ColPair> expected = new LinkedList<>(
            List.of(new GaussSolver.ColPair(0, 1))
        );
        assertThat(solver.getSwappedCols()).isEqualTo(expected);
    }

    @Test
    void prepareMatrixBySwappingWithZeroOnIndexForNoneSwap() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            0, 0,
            0, 1
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrixBySwapping(0);
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                0, 0,
                0, 1
            )
        ));
        assertThat(solver.getSwappedCols()).isEmpty();
    }

    @Test
    void prepareMatrixByMakingFieldToOneWith1OnIndex() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 1,
            0, 0
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrixByMakingFieldToOne(0);
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, 1,
                0, 0
            )
        ));
    }

    @Test
    void prepareMatrixByMakingFieldToOneWith2OnIndex() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            2, 1,
            0, 0
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrixByMakingFieldToOne(0);
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, 0.5,
                0, 0
            )
        ));
    }

    @Test
    void prepareMatrixByMakingFieldToOneWith3OnIndex() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            3, 1,
            0, 0
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrixByMakingFieldToOne(0);
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, 0.3333333333333333,
                0, 0
            )
        ));
    }

    @Test
    void prepareMatrixByMakingFieldToOneWithDecimalOnIndex() {
        // 'easier' number possible?
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            -6.153846153846154, 1,
            0, 0
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrixByMakingFieldToOne(0);
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, -0.16249999999999998,
                0, 0
            )
        ));
    }

    @Test
    void prepareMatrixByMakeColToZeroWithZeroRow() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 0.5,
            0, 0
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrixByMakeColToZero(0);
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, 0.5,
                0, 0
            )
        ));
    }

    @Test
    void prepareMatrixByMakeColToZeroWithNonZeroRow() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 0.5,
            1, 0.5
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrixByMakeColToZero(0);
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, 0.5,
                0, 0
            )
        ));
    }

    // endregion

    // region solveBottomUp, reSwapCols and sortRows

    @Test
    void solveBottomUpWithZeroRow() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 2,
            0, 0
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.solveBottomUp();
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, 2,
                0, 0
            )
        ));
    }

    @Test
    void solveBottomUpWithZeroOnIndex() { // expected?
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 2, 3,
            0, 0, 1
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.solveBottomUp();
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, 2, 1,
                0, 0, 1
            )
        ));
    }

    @Test
    void solveBottomUpWithNonZeroOnIndex() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 2, 3,
            0, 1, 1
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.solveBottomUp();
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                1, 0, 1,
                0, 1, 1
            )
        ));
    }

    @Test
    void reSwapCols() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            0, 1,
            0, 1
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.prepareMatrixBySwapping(0);

        solver.reSwapCols();
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            DoubleMatrix.ofValuesByRows(2,
                0, 1,
                0, 1
            )
        ));
        assertThat(solver.getSwappedCols()).isEmpty();
    }

    @Test
    void sortRows() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(3,
            0, 0, 1, 1,
            0, 1, 0, 1,
            1, 0, 0, 1
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        solver.sortRows();
        DoubleMatrix expected = DoubleMatrix.ofValuesByRows(3,
            1, 0, 0, 1,
            0, 1, 0, 1,
            0, 0, 1, 1
        );
        assertThat(solver.getWorking()).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(expected));
    }

    // endregion

    // region solution checks

    @Test
    void hasNoSolutionsWithZeroRow() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 2,
            0, 0
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        assertThat(solver.hasNoSolutions()).isFalse();
    }

    @Test
    void hasNoSolutionsWithZeroRowExceptSolution() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 2,
            0, 1
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        assertThat(solver.hasNoSolutions()).isTrue();
    }

    @Test
    void hasInfiniteSolutionsWithZeroRow() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 2,
            0, 0
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        assertThat(solver.hasInfiniteSolutions()).isFalse();
    }

    @Test
    void hasInfiniteSolutionsWithManyRows() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(4,
            1, 2,
            2, 4,
            0, 1,
            0, 1
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        assertThat(solver.hasInfiniteSolutions()).isFalse();
    }

    @Test
    void hasInfiniteSolutionsWithNonZerosBetweenIndexAndSolutions() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 2, 3, 6,
            0, 1, 2, 3
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        assertThat(solver.hasInfiniteSolutions()).isTrue();
    }

    // endregion

    // region argument streams

    private static Stream<Arguments> getDoubleLinearEquationSystems() {
        return LinearEquationArgumentsStreamFactory.doubleLinearEquationSystems();
    }

    // endregion


    // region ColPair

    @Test
    void createAndGetterOfColPair() {
        GaussSolver.ColPair pair = new GaussSolver.ColPair(0, 1);
        assertThat(pair.getCol1()).isZero();
        assertThat(pair.getCol2()).isEqualTo(1);
    }

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        GaussSolver.ColPair pair = new GaussSolver.ColPair(0, 1);
        assertThat(new GaussSolver.ColPair(0, 1)).isEqualTo(pair);
        assertThat(new GaussSolver.ColPair(1, 0)).isNotEqualTo(pair);
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        assertThat(new GaussSolver.ColPair(0, 1).hashCode()).isEqualTo(962);
    }

    @Test
    void toStringOfTestAbstractSolver() {
        GaussSolver.ColPair pair = new GaussSolver.ColPair(0, 1);
        assertThat(pair).hasToString("0 <> 1");
    }

    // endregion

    // endregion
}
