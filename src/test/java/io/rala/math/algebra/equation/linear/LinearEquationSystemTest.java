package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.algebra.vector.Vector;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.TestVector;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class LinearEquationSystemTest {
    private static Matrix<Number> matrix;
    private static Vector<Number> vector;
    private LinearEquationSystem<Number> equationSystem;

    @BeforeAll
    static void beforeAll() {
        matrix = TestMatrix.ofValuesByRows(2,
            1,
            3
        );
        vector = TestVector.ofValues(2, 4);
    }

    @BeforeEach
    void setUp() {
        equationSystem = new LinearEquationSystem<>(matrix, vector);
    }

    @Test
    void constructorWithNonMatchingMatrixAndVector() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new LinearEquationSystem<>(
                new TestMatrix(1), new TestVector(2)
            ))
            .withMessage(ExceptionMessages.MATRIX_AND_VECTOR_NOT_MATCH);
    }

    @Test
    void getMatrixOfLinearEquationSystem() {
        assertThat(equationSystem.getMatrix()).isEqualTo(matrix);
    }

    @Test
    void solveWithGaussOfLinearEquationSystem() {
        Solution<LinearEquationSystem<Number>, Number> solution =
            equationSystem.solveWithGauss();
        assertThat(solution).isEqualTo(Solution.unsolvable(equationSystem));
    }

    // region modify

    @Test
    void swapRows() {
        assertThat(equationSystem.swapRows(0, 1))
            .isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
                TestMatrix.ofValuesByRows(2,
                    3, 4,
                    1, 2
                )));
    }

    @Test
    void swapCols() {
        equationSystem = equationSystem.transpose();
        assertThat(equationSystem.swapCols(0, 1))
            .isEqualTo(LinearEquationSystem.ofMatrixWithSolutionRow(
                TestMatrix.ofValuesByCols(2,
                    3, 4,
                    1, 2
                )).transpose());
    }

    @Test
    void multiplyRow() {
        assertThat(equationSystem.multiplyRow(0, 2))
            .isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
                TestMatrix.ofValuesByRows(2,
                    2d, 4d,
                    3, 4
                )));
    }

    @Test
    void multiplyCol() {
        assertThat(equationSystem.multiplyCol(0, 2))
            .isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
                TestMatrix.ofValuesByRows(2,
                    2d, 2,
                    6d, 4
                )));
    }

    @Test
    void addRowMultipleTimes() {
        assertThat(equationSystem.addRowMultipleTimes(0, 1, 2))
            .isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
                TestMatrix.ofValuesByRows(2,
                    7d, 10d,
                    3, 4
                )));
    }

    @Test
    void addColMultipleTimes() {
        equationSystem = equationSystem.transpose();
        assertThat(equationSystem.addColMultipleTimes(0, 1, 2))
            .isEqualTo(LinearEquationSystem.ofMatrixWithSolutionRow(
                TestMatrix.ofValuesByCols(2,
                    7d, 2,
                    3, 4
                )).transpose());
    }

    // endregion

    // region static of

    @Test
    void ofMatrixWithSolutionColumn() {
        LinearEquationSystem<Number> equationSystem =
            LinearEquationSystem.ofMatrixWithSolutionColumn(TestMatrix.ofValuesByRows(2,
                1, 2, 3, 4
            ));
        assertThat(equationSystem.getMatrix()).isEqualTo(matrix);
        assertThat(equationSystem.getVector()).isEqualTo(vector);
    }

    @Test
    void ofMatrixWithSolutionRow() {
        LinearEquationSystem<Number> equationSystem =
            LinearEquationSystem.ofMatrixWithSolutionRow(TestMatrix.ofValuesByCols(2,
                1, 2, 3, 4
            ));
        assertThat(equationSystem.getMatrix()).isEqualTo(matrix);
        assertThat(equationSystem.getVector()).isEqualTo(vector);
    }

    // endregion

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        assertThat(equationSystem)
            .isEqualTo(new LinearEquationSystem<>(matrix, vector))
            .isNotEqualTo(new LinearEquationSystem<>(
                new TestMatrix(1), new TestVector(1)));
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        // hashCode changing after every start
        assertThat(equationSystem)
            .hasSameHashCodeAs(new LinearEquationSystem<>(matrix, vector));
    }

    @Test
    void toStringOfTestAbstractSolver() {
        String toString = "2 1: [0={0=1}, 1={0=3}] - 2: [0=2, 1=4]";
        assertThat(equationSystem).hasToString(toString);
    }

    // endregion

    // region LinearEquationMatrix

    @Test
    void createLinearEquationMatrix() {
        LinearEquationSystem.LinearEquationMatrix<Number> equationMatrix =
            new LinearEquationSystem.LinearEquationMatrix<>(matrix);
        assertThat(matrix).isEqualTo(equationMatrix);
        assertThat(equationMatrix.getArithmetic()).isInstanceOf(TestAbstractArithmetic.class);
    }

    @Test
    void linearEquationMatrixModify() {
        LinearEquationSystem.LinearEquationMatrix<Number> equationMatrix =
            new LinearEquationSystem.LinearEquationMatrix<>(matrix);
        // asserting, that implementation has not changed to matrix during overriding
        assertThat(equationMatrix.swapRows(0, 0)).isNotNull();
        assertThat(equationMatrix.swapCols(0, 0)).isNotNull();
        assertThat(equationMatrix.multiplyRow(0, 0)).isNotNull();
        assertThat(equationMatrix.multiplyCol(0, 0)).isNotNull();
        assertThat(equationMatrix.addRowMultipleTimes(0, 0, 0)).isNotNull();
        assertThat(equationMatrix.addColMultipleTimes(0, 0, 0)).isNotNull();
    }

    // endregion

    // region LinearEquationVector

    @Test
    void createLinearEquationVector() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertThat(vector).isEqualTo(equationVector);
        assertThat(equationVector.getArithmetic()).isInstanceOf(TestAbstractArithmetic.class);
    }

    @Test
    void linearEquationVectorSwapValuesUsingInvalidIndex1() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> equationVector.swapValues(-1, 0))
            .withMessage("-1 / 2");
    }

    @Test
    void linearEquationVectorSwapValuesUsingInvalidIndex2() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> equationVector.swapValues(0, -1))
            .withMessage("-1 / 2");
    }

    @Test
    void linearEquationVectorSwapValues() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        LinearEquationSystem.LinearEquationVector<Number> result =
            new LinearEquationSystem.LinearEquationVector<>(TestVector.ofValues(4, 2));
        assertThat(equationVector.swapValues(0, 1)).isEqualTo(result);
    }

    @Test
    void linearEquationVectorMultiplyValueUsingInvalidIndex() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> equationVector.multiplyValue(-1, 0))
            .withMessage("-1 / 2");
    }

    @Test
    void linearEquationVectorMultiplyValueUsingFactor0() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        LinearEquationSystem.LinearEquationVector<Number> result =
            new LinearEquationSystem.LinearEquationVector<>(TestVector.ofValues(0d, 4));
        assertThat(equationVector.multiplyValue(0, 0d)).isEqualTo(result);
    }

    @Test
    void linearEquationVectorMultiplyValueUsingEmptyVector() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(new TestVector(2));
        assertThat(equationVector.multiplyValue(0, 1)).isEqualTo(equationVector);
    }

    @Test
    void linearEquationVectorMultiplyValueUsingFactor1() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        LinearEquationSystem.LinearEquationVector<Number> result =
            new LinearEquationSystem.LinearEquationVector<>(TestVector.ofValues(2d, 4));
        assertThat(equationVector.multiplyValue(0, 1)).isEqualTo(result);
    }

    @Test
    void linearEquationVectorMultiplyValueUsingFactor2() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        LinearEquationSystem.LinearEquationVector<Number> result =
            new LinearEquationSystem.LinearEquationVector<>(TestVector.ofValues(4d, 4));
        assertThat(equationVector.multiplyValue(0, 2)).isEqualTo(result);
    }

    @Test
    void linearEquationVectorAddValueMultipleTimesUsingInvalidIndex1() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> equationVector.addValueMultiplyTimes(-1, 0, 0))
            .withMessage("-1 / 2");
    }

    @Test
    void linearEquationVectorAddValueMultipleTimesUsingInvalidIndex2() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> equationVector.addValueMultiplyTimes(0, -1, 0))
            .withMessage("-1 / 2");
    }

    @Test
    void linearEquationVectorAddValueMultipleTimes() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        LinearEquationSystem.LinearEquationVector<Number> result =
            new LinearEquationSystem.LinearEquationVector<>(TestVector.ofValues(10d, 4));
        assertThat(equationVector.addValueMultiplyTimes(0, 1, 2)).isEqualTo(result);
    }

    // endregion
}
