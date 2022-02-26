package io.rala.math.algebra.equation.linear;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.algebra.vector.Vector;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.TestVector;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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
    void constructorWithNonMatchingMatrixAndVector() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new LinearEquationSystem<>(
                new TestMatrix(1), new TestVector(2)
            )); // assert exception message?
    }

    @Test
    void getMatrixOfLinearEquationSystem() {
        LinearEquationSystem<Number> equationSystem =
            new LinearEquationSystem<>(matrix, vector);
        assertThat(equationSystem.getMatrix()).isEqualTo(matrix);
    }

    @Test
    void solveWithGaussOfLinearEquationSystem() {
        LinearEquationSystem<Number> equationSystem =
            new LinearEquationSystem<>(matrix, vector);
        Solution<LinearEquationSystem<Number>, Number> solution =
            equationSystem.solveWithGauss();
        assertThat(solution).isEqualTo(Solution.unsolvable(equationSystem));
    }

    // region modify

    @Test
    void swapRows() {
        LinearEquationSystem<Number> equation = new LinearEquationSystem<>(matrix, vector);
        assertThat(equation.swapRows(0, 1)).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            TestMatrix.ofValuesByRows(2,
                3, 4,
                1, 2
            )));
    }

    @Test
    void swapCols() {
        LinearEquationSystem<Number> equation =
            new LinearEquationSystem<>(matrix, vector)
                .transpose();
        assertThat(equation.swapCols(0, 1)).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionRow(
            TestMatrix.ofValuesByCols(2,
                3, 4,
                1, 2
            )).transpose());
    }

    @Test
    void multiplyRow() {
        LinearEquationSystem<Number> equation = new LinearEquationSystem<>(matrix, vector);
        assertThat(equation.multiplyRow(0, 2)).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            TestMatrix.ofValuesByRows(2,
                2d, 4d,
                3, 4
            )));
    }

    @Test
    void multiplyCol() {
        LinearEquationSystem<Number> equation = new LinearEquationSystem<>(matrix, vector);
        assertThat(equation.multiplyCol(0, 2)).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            TestMatrix.ofValuesByRows(2,
                2d, 2,
                6d, 4
            )));
    }

    @Test
    void addRowMultipleTimes() {
        LinearEquationSystem<Number> equation = new LinearEquationSystem<>(matrix, vector);
        assertThat(equation.addRowMultipleTimes(0, 1, 2)).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionColumn(
            TestMatrix.ofValuesByRows(2,
                7d, 10d,
                3, 4
            )));
    }

    @Test
    void addColMultipleTimes() {
        LinearEquationSystem<Number> equation =
            new LinearEquationSystem<>(matrix, vector)
                .transpose();
        assertThat(equation.addColMultipleTimes(0, 1, 2)).isEqualTo(LinearEquationSystem.ofMatrixWithSolutionRow(
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
                )
            );
        assertThat(equationSystem.getMatrix()).isEqualTo(matrix);
        assertThat(equationSystem.getVector()).isEqualTo(vector);
    }

    @Test
    void ofMatrixWithSolutionRow() {
        LinearEquationSystem<Number> equationSystem =
            LinearEquationSystem.ofMatrixWithSolutionRow(TestMatrix.ofValuesByCols(2,
                    1, 2, 3, 4
                )
            );
        assertThat(equationSystem.getMatrix()).isEqualTo(matrix);
        assertThat(equationSystem.getVector()).isEqualTo(vector);
    }

    // endregion

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        LinearEquationSystem<Number> equationSystem =
            new LinearEquationSystem<>(matrix, vector);
        assertThat(new LinearEquationSystem<>(matrix, vector)).isEqualTo(equationSystem);
        assertThat(new LinearEquationSystem<>(
            new TestMatrix(1), new TestVector(1))).isNotEqualTo(equationSystem);
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        // hashCode changing after every start
        assertThat(new LinearEquationSystem<>(matrix, vector))
            .hasSameHashCodeAs(new LinearEquationSystem<>(matrix, vector));
    }

    @Test
    void toStringOfTestAbstractSolver() {
        LinearEquationSystem<Number> equationSystem =
            new LinearEquationSystem<>(matrix, vector);
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
            .isThrownBy(() -> equationVector.swapValues(-1, 0)); // assert exception message?
    }

    @Test
    void linearEquationVectorSwapValuesUsingInvalidIndex2() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> equationVector.swapValues(0, -1)); // assert exception message?
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
            .isThrownBy(() -> equationVector.multiplyValue(-1, 0)); // assert exception message?
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
            .isThrownBy(() -> equationVector.addValueMultiplyTimes(-1, 0, 0)); // assert exception message?
    }

    @Test
    void linearEquationVectorAddValueMultipleTimesUsingInvalidIndex2() {
        LinearEquationSystem.LinearEquationVector<Number> equationVector =
            new LinearEquationSystem.LinearEquationVector<>(vector);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> equationVector.addValueMultiplyTimes(0, -1, 0)); // assert exception message?
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
