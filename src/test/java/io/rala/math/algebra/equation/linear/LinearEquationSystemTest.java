package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.algebra.vector.Vector;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.TestVector;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinearEquationSystemTest {
    private static Matrix<Number> matrix;
    private static Vector<Number> vector;

    @BeforeAll
    static void beforeAll() {
        matrix = TestMatrix.ofValuesByRows(2,
            1,
            3
        );
        vector = TestVector.ofValues(2, 4);
    }

    @Test
    void getMatrixOfLinearEquationSystem() {
        LinearEquationSystem<Number> equationSystem =
            new LinearEquationSystem<>(matrix, vector);
        assertEquals(matrix, equationSystem.getMatrix());
    }

    @Test
    void solveWithGaussOfLinearEquationSystem() {
        LinearEquationSystem<Number> equationSystem =
            new LinearEquationSystem<>(matrix, vector);
        Solution<LinearEquationSystem<Number>, Number> solution =
            equationSystem.solveWithGauss();
        assertEquals(Solution.unsolvable(equationSystem), solution);
    }

    @Test
    void ofMatrixWithSolutionColumn() {
        LinearEquationSystem<Number> equationSystem =
            LinearEquationSystem.ofMatrixWithSolutionColumn(TestMatrix.ofValuesByRows(2,
                1, 2, 3, 4
                )
            );
        assertEquals(matrix, equationSystem.getMatrix());
        assertEquals(vector, equationSystem.getVector());
    }

    @Test
    void ofMatrixWithSolutionRow() {
        LinearEquationSystem<Number> equationSystem =
            LinearEquationSystem.ofMatrixWithSolutionRow(TestMatrix.ofValuesByCols(2,
                1, 2, 3, 4
                )
            );
        assertEquals(matrix, equationSystem.getMatrix());
        assertEquals(vector, equationSystem.getVector());
    }

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        LinearEquationSystem<Number> equationSystem =
            new LinearEquationSystem<>(matrix, vector);
        assertEquals(equationSystem, new LinearEquationSystem<>(matrix, vector));
        assertNotEquals(equationSystem, new LinearEquationSystem<>(null, null));
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        // hashCode changing after every start
        assertEquals(
            new LinearEquationSystem<>(matrix, vector).hashCode(),
            new LinearEquationSystem<>(matrix, vector).hashCode()
        );
    }

    @Test
    void toStringOfTestAbstractSolver() {
        LinearEquationSystem<Number> equationSystem =
            new LinearEquationSystem<>(matrix, vector);
        String toString = "2 1: [0={0=1}, 1={0=3}] - 2: [0=2, 1=4]";
        assertEquals(toString, equationSystem.toString());
    }

    // endregion

    // region LinearEquationMatrix

    @Test
    void createLinearEquationMatrix() {
        LinearEquationSystem.LinearEquationMatrix<Number> equationMatrix =
            new LinearEquationSystem.LinearEquationMatrix<>(matrix);
        assertEquals(equationMatrix, matrix);
        assertTrue(
            equationMatrix.getArithmetic() instanceof TestAbstractArithmetic
        );
    }

    @Test
    void linearEquationMatrixModify() {
        LinearEquationSystem.LinearEquationMatrix<Number> equationMatrix =
            new LinearEquationSystem.LinearEquationMatrix<>(matrix);
        // asserting, that implementation has not changed to matrix during overriding
        assertNotNull(equationMatrix.swapRows(0, 0));
        assertNotNull(equationMatrix.swapCols(0, 0));
        assertNotNull(equationMatrix.multiplyRow(0, 0));
        assertNotNull(equationMatrix.multiplyCol(0, 0));
        assertNotNull(equationMatrix.addRowMultipleTimes(0, 0, 0));
        assertNotNull(equationMatrix.addColMultipleTimes(0, 0, 0));
    }

    // endregion

    // region LinearEquationVector

    @Test
    void createLinearEquationVector() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertEquals(equationVector, vector);
        assertTrue(
            equationVector.getArithmetic() instanceof TestAbstractArithmetic
        );
    }

    @Test
    void linearEquationVectorSwapValuesUsingInvalidIndex1() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertThrows(IndexOutOfBoundsException.class,
            () -> equationVector.swapValues(-1, 0)
        ); // assert exception message?
    }

    @Test
    void linearEquationVectorSwapValuesUsingInvalidIndex2() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertThrows(IndexOutOfBoundsException.class,
            () -> equationVector.swapValues(0, -1)
        ); // assert exception message?
    }

    @Test
    void linearEquationVectorSwapValues() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        LinearEquationSystem.LinearEquationVector<Number> result =
            new LinearEquationSystem.LinearEquationVector<>(TestVector.ofValues(4, 2));
        assertEquals(result, equationVector.swapValues(0, 1));
    }

    @Test
    void linearEquationVectorMultiplyValueUsingInvalidIndex() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertThrows(IndexOutOfBoundsException.class,
            () -> equationVector.multiplyValue(-1, 0)
        ); // assert exception message?
    }

    @Test
    void linearEquationVectorMultiplyValueUsingFactor0() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        LinearEquationSystem.LinearEquationVector<Number> result =
            new LinearEquationSystem.LinearEquationVector<>(TestVector.ofValues(0d, 4));
        assertEquals(result, equationVector.multiplyValue(0, 0d));
    }

    @Test
    void linearEquationVectorMultiplyValueUsingEmptyVector() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(new TestVector(2));
        assertEquals(equationVector, equationVector.multiplyValue(0, 1));
    }

    @Test
    void linearEquationVectorMultiplyValueUsingFactor1() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        LinearEquationSystem.LinearEquationVector<Number> result =
            new LinearEquationSystem.LinearEquationVector<>(TestVector.ofValues(2d, 4));
        assertEquals(result, equationVector.multiplyValue(0, 1));
    }

    @Test
    void linearEquationVectorMultiplyValueUsingFactor2() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        LinearEquationSystem.LinearEquationVector<Number> result =
            new LinearEquationSystem.LinearEquationVector<>(TestVector.ofValues(4d, 4));
        assertEquals(result, equationVector.multiplyValue(0, 2));
    }

    @Test
    void linearEquationVectorAddValueMultipleTimesUsingInvalidIndex1() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertThrows(IndexOutOfBoundsException.class,
            () -> equationVector.addValueMultiplyTimes(-1, 0, 0)
        ); // assert exception message?
    }

    @Test
    void linearEquationVectorAddValueMultipleTimesUsingInvalidIndex2() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertThrows(IndexOutOfBoundsException.class,
            () -> equationVector.addValueMultiplyTimes(0, -1, 0)
        ); // assert exception message?
    }

    @Test
    void linearEquationVectorAddValueMultipleTimes() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        LinearEquationSystem.LinearEquationVector<Number> result =
            new LinearEquationSystem.LinearEquationVector<>(TestVector.ofValues(10d, 4));
        assertEquals(result, equationVector.addValueMultiplyTimes(0, 1, 2));
    }

    // endregion
}
