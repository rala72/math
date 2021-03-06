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

import static org.junit.jupiter.api.Assertions.*;

class GaussSolverTest {
    @ParameterizedTest
    @MethodSource("getDoubleLinearEquationSystems")
    void solveLinearEquationSystem(Solution<LinearEquationSystem<Double>, Double> solution) {
        GaussSolver<Double> solver = new GaussSolver<>(solution.getEquationSystem());
        assertEquals(solution, solver.solve());
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
        assertEquals(Solution.unsolvable(equationSystem), solver.solve());
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
        assertEquals(Solution.infinite(equationSystem), solver.solve());
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
        assertEquals(Solution.infinite(equationSystem), solver.solve());
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0.5,
                    0, 0
                )
            ),
            solver.getWorking()
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(3,
                    1, 2,
                    0, -1,
                    0, -2
                )
            ),
            solver.getWorking()
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 1,
                    0, 0
                )
            ),
            solver.getWorking()
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 1,
                    0, 0
                )
            ),
            solver.getWorking()
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 1,
                    0, 1
                )
            ),
            solver.getWorking()
        );
        assertTrue(solver.getSwappedCols().isEmpty());
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0, 0,
                    1, 0, 0
                )
            ),
            solver.getWorking()
        );
        Deque<GaussSolver.ColPair> expected = new LinkedList<>(
            List.of(new GaussSolver.ColPair(0, 1))
        );
        assertEquals(expected, solver.getSwappedCols());
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    0, 0,
                    0, 1
                )
            ),
            solver.getWorking()
        );
        assertTrue(solver.getSwappedCols().isEmpty());
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 1,
                    0, 0
                )
            ),
            solver.getWorking()
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0.5,
                    0, 0
                )
            ),
            solver.getWorking()
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0.3333333333333333,
                    0, 0
                )
            ),
            solver.getWorking()
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, -0.16249999999999998,
                    0, 0
                )
            ),
            solver.getWorking()
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0.5,
                    0, 0
                )
            ),
            solver.getWorking()
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0.5,
                    0, 0
                )
            ),
            solver.getWorking()
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 2,
                    0, 0
                )
            ),
            solver.getWorking()
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 2, 1,
                    0, 0, 1
                )
            ),
            solver.getWorking()
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    1, 0, 1,
                    0, 1, 1
                )
            ),
            solver.getWorking()
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(
                DoubleMatrix.ofValuesByRows(2,
                    0, 1,
                    0, 1
                )
            ),
            solver.getWorking()
        );
        assertTrue(solver.getSwappedCols().isEmpty());
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
        assertEquals(
            LinearEquationSystem.ofMatrixWithSolutionColumn(expected),
            solver.getWorking()
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
        assertFalse(solver.hasNoSolutions());
    }

    @Test
    void hasNoSolutionsWithZeroRowExceptSolution() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 2,
            0, 1
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        assertTrue(solver.hasNoSolutions());
    }

    @Test
    void hasInfiniteSolutionsWithZeroRow() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 2,
            0, 0
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        assertFalse(solver.hasInfiniteSolutions());
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
        assertFalse(solver.hasInfiniteSolutions());
    }

    @Test
    void hasInfiniteSolutionsWithNonZerosBetweenIndexAndSolutions() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            1, 2, 3, 6,
            0, 1, 2, 3
        );
        TestGaussSolver<Double> solver = new TestGaussSolver<>(matrix);
        solver.reset();
        assertTrue(solver.hasInfiniteSolutions());
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
        assertEquals(0, pair.getCol1());
        assertEquals(1, pair.getCol2());
    }

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        GaussSolver.ColPair pair = new GaussSolver.ColPair(0, 1);
        assertEquals(pair, new GaussSolver.ColPair(0, 1));
        assertNotEquals(pair, new GaussSolver.ColPair(1, 0));
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        assertEquals(962, new GaussSolver.ColPair(0, 1).hashCode());
    }

    @Test
    void toStringOfTestAbstractSolver() {
        GaussSolver.ColPair pair = new GaussSolver.ColPair(0, 1);
        assertEquals("0 <> 1", pair.toString());
    }

    // endregion

    // endregion
}
