package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinearEquationSystemTest {
    private static Matrix<Number> matrix;

    @BeforeAll
    static void beforeAll() {
        matrix = TestMatrix.ofValuesByRows(2,
            1, 2, 3, 4
        );
    }

    @Test
    void getMatrixOfLinearEquationSystem() {
        LinearEquationSystem<Number> equationSystem =
            new LinearEquationSystem<>(matrix);
        assertEquals(matrix, equationSystem.getMatrix());
    }

    @Test
    void solveWithGaussOfLinearEquationSystem() {
        LinearEquationSystem<Number> equationSystem =
            new LinearEquationSystem<>(matrix);
        Solution<LinearEquationSystem<Number>, Number> solution =
            equationSystem.solveWithGauss();
        assertEquals(Solution.unsolvable(equationSystem), solution);
    }

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        LinearEquationSystem<Number> equationSystem =
            new LinearEquationSystem<>(matrix);
        assertEquals(
            equationSystem,
            new LinearEquationSystem<>(matrix)
        );
        assertNotEquals(
            equationSystem,
            new LinearEquationSystem<>(null)
        );
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        assertEquals(1312819,
            new LinearEquationSystem<>(matrix).hashCode()
        );
    }

    @Test
    void toStringOfTestAbstractSolver() {
        LinearEquationSystem<Number> equationSystem =
            new LinearEquationSystem<>(matrix);
        String toString = "2 2: [0={0=1, 1=2}, 1={0=3, 1=4}]";
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
}
