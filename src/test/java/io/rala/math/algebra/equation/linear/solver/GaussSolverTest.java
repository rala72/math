package io.rala.math.algebra.equation.linear.solver;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.equation.linear.LinearEquationSystem;
import io.rala.math.algebra.matrix.DoubleMatrix;
import io.rala.math.testUtils.algebra.equation.TestGaussSolver;
import io.rala.math.testUtils.arguments.LinearEquationArgumentsStreamFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

class GaussSolverTest {
    @ParameterizedTest
    @MethodSource("getDoubleLinearEquationSystems")
    void solveLinearEquationSystem(Solution<LinearEquationSystem<Double>, Double> solution) {
        GaussSolver<Double> solver = new GaussSolver<>(solution.getEquationSystem());
        Assertions.assertEquals(solution, solver.solve());
    }

    // region solve

    @Test
    void solveUnsolvableLinearEquationSystemAfterPrepare() {
        LinearEquationSystem<Double> equationSystem = new LinearEquationSystem<>(
            DoubleMatrix.ofValuesByRows(2,
                1, 0,
                0, 1)
        );
        GaussSolver<Double> solver = new GaussSolver<>(equationSystem);
        Assertions.assertEquals(
            Solution.unsolvable(equationSystem),
            solver.solve()
        );
    }

    @Test
    void solveInfiniteLinearEquationSystemWithLessRowsThanCols() {
        LinearEquationSystem<Double> equationSystem = new LinearEquationSystem<>(
            DoubleMatrix.ofValuesByRows(1,
                1, 0, 0, 1
            )
        );
        GaussSolver<Double> solver = new GaussSolver<>(equationSystem);
        Assertions.assertEquals(
            Solution.infinite(equationSystem),
            solver.solve()
        );
    }

    @Test
    void solveInfiniteLinearEquationSystemAfterSolveBottomUp() {
        LinearEquationSystem<Double> equationSystem = new LinearEquationSystem<>(
            DoubleMatrix.ofValuesByRows(3,
                1, 1, 2, 9,
                0, 1, 3, 1,
                0, 2, 6, 2
            )
        );
        GaussSolver<Double> solver = new GaussSolver<>(equationSystem);
        Assertions.assertEquals(
            Solution.infinite(equationSystem),
            solver.solve()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0.5,
                    0, 0
                )
            ),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(3,
                    1, 2,
                    0, -1,
                    0, -2
                )
            ),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, 1,
                    0, 0
                )
            ),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, 1,
                    0, 0
                )
            ),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, 1,
                    0, 1
                )
            ),
            solver.getWorkingMatrix()
        );
        Assertions.assertTrue(solver.getSwappedCols().isEmpty());
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0, 0,
                    1, 0, 0
                )
            ),
            solver.getWorkingMatrix()
        );
        Deque<GaussSolver.ColPair> expected = new LinkedList<>(
            List.of(new GaussSolver.ColPair(0, 1))
        );
        Assertions.assertEquals(expected, solver.getSwappedCols());
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    0, 0,
                    0, 1
                )
            ),
            solver.getWorkingMatrix()
        );
        Assertions.assertTrue(solver.getSwappedCols().isEmpty());
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, 1,
                    0, 0
                )
            ),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0.5,
                    0, 0
                )
            ),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0.3333333333333333,
                    0, 0
                )
            ),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, -0.16249999999999998,
                    0, 0
                )
            ),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0.5,
                    0, 0
                )
            ),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0.5,
                    0, 0
                )
            ),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, 2,
                    0, 0
                )
            ),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, 2, 1,
                    0, 0, 1
                )
            ),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0, 1,
                    0, 1, 1
                )
            ),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(
                DoubleMatrix.ofValuesByRows(2,
                    0, 1,
                    0, 1
                )
            ),
            solver.getWorkingMatrix()
        );
        Assertions.assertTrue(solver.getSwappedCols().isEmpty());
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
        Assertions.assertEquals(
            new LinearEquationSystem.LinearEquationMatrix<>(expected),
            solver.getWorkingMatrix()
        );
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
        Assertions.assertFalse(solver.hasNoSolutions());
    }

    @Test
    void hasNoSolutionsWithZeroRowExceptSolution() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 2,
            0, 1
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        Assertions.assertTrue(solver.hasNoSolutions());
    }

    @Test
    void hasInfiniteSolutionsWithZeroRow() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 2,
            0, 0
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        Assertions.assertFalse(solver.hasInfiniteSolutions());
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
        Assertions.assertFalse(solver.hasInfiniteSolutions());
    }

    @Test
    void hasInfiniteSolutionsWithNonZerosBetweenIndexAndSolutions() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 2, 3, 6,
            0, 1, 2, 3
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        Assertions.assertTrue(solver.hasInfiniteSolutions());
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
        Assertions.assertEquals(0, pair.getCol1());
        Assertions.assertEquals(1, pair.getCol2());
    }

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        GaussSolver.ColPair pair = new GaussSolver.ColPair(0, 1);
        Assertions.assertEquals(
            pair,
            new GaussSolver.ColPair(0, 1)
        );
        Assertions.assertNotEquals(
            pair,
            new GaussSolver.ColPair(1, 0)
        );
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        // hashCode of State changing after every start
        Assertions.assertEquals(962,
            new GaussSolver.ColPair(0, 1).hashCode()
        );
    }

    @Test
    void toStringOfTestAbstractSolver() {
        GaussSolver.ColPair pair = new GaussSolver.ColPair(0, 1);
        Assertions.assertEquals("0 <> 1", pair.toString());
    }

    // endregion

    // endregion
}
