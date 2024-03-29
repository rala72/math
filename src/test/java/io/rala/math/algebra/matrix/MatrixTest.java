package io.rala.math.algebra.matrix;

import io.rala.math.algebra.matrix.typed.DoubleMatrix;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.exception.NotSupportedException;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.TestVector;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static io.rala.math.testUtils.algebra.TestVector.fillVectorWithTestValues;
import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatMatrix;
import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatVector;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MatrixTest {
    // region constructors

    @Test
    void constructorWithNegativeSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestMatrix(-1))
            .withMessage(ExceptionMessages.ROWS_COLS_HAVE_TO_BE_GREATER_ZERO);
    }

    @Test
    void constructorWithIntegerMaxValueSize() {
        TestMatrix matrix = new TestMatrix(Integer.MAX_VALUE);

        long expectedSize = (long) Integer.MAX_VALUE * Integer.MAX_VALUE;
        assertThatMatrix(matrix).hasSize(expectedSize);

        assertThat(matrix.isValidIndex(expectedSize - 1)).isTrue();
        assertThat(matrix.isValidIndex(expectedSize)).isFalse();
    }

    @Test
    void constructorWithSize0() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestMatrix(0))
            .withMessage(ExceptionMessages.ROWS_COLS_HAVE_TO_BE_GREATER_ZERO);
    }

    @Test
    void constructorWithSize1() {
        assertThatMatrix(new TestMatrix(1)).hasSizeOne();
    }

    @Test
    void constructorWithSize2AndDefaultValue() {
        TestMatrix matrix = new TestMatrix(2, 2d);
        assertThatMatrix(matrix).hasSize(4);
        assertThat(matrix.getDefaultValue()).isEqualTo(2d);
    }

    @Test
    void constructorWithRows1Cols1() {
        assertThatMatrix(new TestMatrix(1, 1)).hasRows(1).hasCols(1);
    }

    @Test
    void constructorWithRows1Cols2() {
        assertThatMatrix(new TestMatrix(1, 2)).hasRows(1).hasCols(2);
    }

    @Test
    void constructorWithRows1Cols2AndDefaultValue() {
        TestMatrix matrix = new TestMatrix(1, 2, -2d);
        assertThatMatrix(matrix).hasRows(1).hasCols(2);
        assertThat(matrix.getDefaultValue()).isEqualTo(-2d);
    }

    @Test
    void constructorWithMatrix() {
        assertThatMatrix(new TestMatrix(new TestMatrix(1, 2))).hasRows(1).hasCols(2);
    }

    // endregion

    // region getter and size

    @Test
    void createWithSize1AndAssertSizeEquals1() {
        assertThatMatrix(new TestMatrix(1)).hasSizeOne();
    }

    @Test
    void createWithSize2AndAssertSizeEquals4() {
        assertThatMatrix(new TestMatrix(2)).hasSize(4);
    }

    @Test
    void createWithRow1Col2AndAssertSizeEquals2() {
        assertThatMatrix(new TestMatrix(1, 2)).hasSize(2);
    }

    @Test
    void createWithRow2Col3AndAssertSizeEquals6() {
        assertThatMatrix(new TestMatrix(2, 3)).hasSize(6);
    }

    // endregion

    // region rows and cols

    @Test
    void getRowFieldsM1OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getRowFields(-1))
            .withMessage("row: -1 / 2");
    }

    @Test
    void getRowFields0OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        List<Matrix<Number>.Field> row0 = matrix.getRowFields(0);
        assertThat(row0).hasSize(2);
        for (int i = 0; i < row0.size(); i++) {
            Matrix<Number>.Field field = row0.get(i);
            assertThat(field.getRow()).isZero();
            assertThat(field.getCol()).isEqualTo(i);
            assertThat(field.getValue()).isEqualTo(i);
        }
    }

    @Test
    void getRow0OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        List<Number> row0 = matrix.getRow(0);
        assertThat(row0).hasSize(2);
        for (int i = 0; i < row0.size(); i++)
            assertThat(row0.get(i)).isEqualTo(i);
    }

    @Test
    void getColFieldsM1OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getColFields(-1))
            .withMessage("col: -1 / 2");
    }

    @Test
    void getColFields0OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        List<Matrix<Number>.Field> col0 = matrix.getColFields(0);
        assertThat(col0).hasSize(2);
        for (int i = 0; i < col0.size(); i++) {
            Matrix<Number>.Field field = col0.get(i);
            assertThat(field.getCol()).isZero();
            assertThat(field.getRow()).isEqualTo(i);
            assertThat(field.getValue()).isEqualTo(i * 2);
        }
    }

    @Test
    void getCol0OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        List<Number> col0 = matrix.getCol(0);
        assertThat(col0).hasSize(2);
        for (int i = 0; i < col0.size(); i++)
            assertThat(col0.get(i)).isEqualTo(i * 2);
    }

    // endregion

    // region value

    @Test
    void getValueByIndexMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getValue(-1))
            .withMessage("size: -1 / 4");
    }

    @Test
    void getValueByRowMinus1Col0() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getValue(-1, 0))
            .withMessage("row: -1 / 2");
    }

    @Test
    void getValueByRow0ColMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getValue(0, -1))
            .withMessage("col: -1 / 2");
    }

    @Test
    void setValueByIndexMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.setValue(-1, 0d))
            .withMessage("size: -1 / 4");
    }

    @Test
    void setValueByIndex0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.setValue(0, 1)).isEqualTo(0d);
        assertThat(matrix.getValue(0)).isEqualTo(1);
        assertThat(matrix.getValue(0, 0)).isEqualTo(1);
        // assert all others are unset
    }

    @Test
    void setValueByIndex3WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.setValue(3, 1)).isEqualTo(0d);
        assertThat(matrix.getValue(3)).isEqualTo(1);
        assertThat(matrix.getValue(1, 1)).isEqualTo(1);
        // assert all others are unset
    }

    @Test
    void setValueByRowMinus1Col0() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.setValue(-1, 0, 0d))
            .withMessage("row: -1 / 2");
    }

    @Test
    void setValueByRow0ColMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.setValue(0, -1, 0d))
            .withMessage("col: -1 / 2");
    }

    @Test
    void setValueByRow0Col0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.setValue(0, 0, 1)).isEqualTo(0d);
        assertThat(matrix.getValue(0)).isEqualTo(1);
        assertThat(matrix.getValue(0, 0)).isEqualTo(1);
        // assert all others are unset
    }

    @Test
    void setValueByRow1Col0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.setValue(1, 0, 1)).isEqualTo(0d);
        assertThat(matrix.getValue(1, 0)).isEqualTo(1);
        assertThat(matrix.getValue(2)).isEqualTo(1);
        // assert all others are unset
    }

    @Test
    void setValueByIndex2WichWasSet() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(2, 1);
        assertThat(matrix.getValue(2)).isEqualTo(1);
        assertThat(matrix.setValue(2, 2)).isEqualTo(1);
    }

    @Test
    void removeValueByIndexMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.removeValue(-1))
            .withMessage("size: -1 / 4");
    }

    @Test
    void removeValueByIndex0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.removeValue(0)).isEqualTo(0d);
    }

    @Test
    void removeValueByRowMinus1Col0() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.removeValue(-1, 0))
            .withMessage("row: -1 / 2");
    }

    @Test
    void removeValueByRow0ColMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.removeValue(0, -1))
            .withMessage("col: -1 / 2");
    }

    @Test
    void removeValueByRow0Col0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.removeValue(0, 0)).isEqualTo(0d);
    }

    @Test
    void removeValueByIndex2WhichWasSet() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(2, 1);
        assertThat(matrix.removeValue(2)).isEqualTo(1);
    }

    // endregion

    // region compute

    @Test
    void computeWithUnaryOperatorByIndex0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.compute(0, number -> 1)).isEqualTo(0d);
        assertThat(matrix.getValue(0)).isEqualTo(1);
        assertThat(matrix.getValue(0, 0)).isEqualTo(1);
    }

    @Test
    void computeWithUnaryOperatorByIndex3WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.compute(3, number -> 1)).isEqualTo(0d);
        assertThat(matrix.getValue(3)).isEqualTo(1);
        assertThat(matrix.getValue(1, 1)).isEqualTo(1);
    }

    @Test
    void computeWithUnaryOperatorByRow0Col0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.compute(0, 0, number -> 1)).isEqualTo(0d);
        assertThat(matrix.getValue(0)).isEqualTo(1);
        assertThat(matrix.getValue(0, 0)).isEqualTo(1);
        // assert all others are unset
    }

    @Test
    void computeWithUnaryOperatorByRow1Col1WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.compute(1, 0, number -> 1)).isEqualTo(0d);
        assertThat(matrix.getValue(1, 0)).isEqualTo(1);
        assertThat(matrix.getValue(2)).isEqualTo(1);
        // assert all others are unset
    }

    @Test
    void computeWithBinaryOperatorByIndex0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.compute(0,
            1d, matrix.getArithmetic()::sum
        )).isEqualTo(0d);
        assertThat(matrix.getValue(0)).isEqualTo(1d);
        assertThat(matrix.getValue(0, 0)).isEqualTo(1d);
    }

    @Test
    void computeWithBinaryOperatorByIndex3WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.compute(3,
            1d, matrix.getArithmetic()::sum
        )).isEqualTo(0d);
        assertThat(matrix.getValue(3)).isEqualTo(1d);
        assertThat(matrix.getValue(1, 1)).isEqualTo(1d);
    }

    @Test
    void computeWithBinaryOperatorByRow0Col0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.compute(0, 0,
            1d, matrix.getArithmetic()::sum
        )).isEqualTo(0d);
        assertThat(matrix.getValue(0)).isEqualTo(1d);
        assertThat(matrix.getValue(0, 0)).isEqualTo(1d);
        // assert all others are unset
    }

    @Test
    void computeWithBinaryOperatorByRow1Col1WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.compute(1, 0,
            1d, matrix.getArithmetic()::sum
        )).isEqualTo(0d);
        assertThat(matrix.getValue(1, 0)).isEqualTo(1d);
        assertThat(matrix.getValue(2)).isEqualTo(1d);
        // assert all others are unset
    }

    @Test
    void computeAllWithFunctionOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.computeAll(field ->
            matrix.getArithmetic().sum(field.getValue(), 1)
        );
        assertThat(matrix.stream().allMatch(field ->
            matrix.getArithmetic().isEqual(field.getValue(), 1d)
        )).isTrue();
    }

    @Test
    void computeAllWithFunctionAndBinaryOperatorOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.computeAll(field -> 1, matrix.getArithmetic()::sum);
        assertThat(matrix.stream().allMatch(field ->
            matrix.getArithmetic().isEqual(field.getValue(), 1d)
        )).isTrue();
    }

    // endregion

    // region isSquare, isDiagonal and isInvertible

    @Test
    void isSquareOfMatrixWithRow1Col2() {
        assertThatMatrix(new TestMatrix(1, 2)).isNoSquare();
    }

    @Test
    void isSquareOfMatrixWithSize2() {
        assertThatMatrix(new TestMatrix(2)).isSquare();
    }

    @Test
    void isDiagonalOfMatrixWithRow1Col2() {
        assertThatMatrix(new TestMatrix(1, 2)).isNoDiagonal();
    }

    @Test
    void isDiagonalOfMatrixOfValidValues() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(0, 1);
        matrix.setValue(1, 0d);
        matrix.setValue(2, 0d);
        matrix.setValue(3, 4);
        assertThatMatrix(matrix).isDiagonal();
    }

    @Test
    void isDiagonalOfMatrixOfInvalidValues() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(0, 1);
        matrix.setValue(1, 2);
        matrix.setValue(2, 3);
        matrix.setValue(3, 4);
        assertThatMatrix(matrix).isNoDiagonal();
    }

    @Test
    void isInvertibleOfMatrixWithSize2AndDeterminante0() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(0, 1);
        matrix.setValue(1, 2);
        matrix.setValue(2, 2);
        matrix.setValue(3, 4);
        assertThatMatrix(matrix).isSquare();
        assertThatMatrix(matrix).hasZeroDeterminante();
        assertThatMatrix(matrix).isNoInvertible();
    }

    @Test
    void isInvertibleOfMatrixWithSize2AndDeterminanteNon0() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(0, 1);
        matrix.setValue(1, 0);
        matrix.setValue(2, 0);
        matrix.setValue(3, 4);
        assertThatMatrix(matrix).isSquare();
        assertThatMatrix(matrix).hasNoZeroDeterminante();
        assertThatMatrix(matrix).isInvertible();
    }

    // endregion

    // region add and multiply

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows2Cols1() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestMatrix(1).add(new TestMatrix(2, 1)))
            .withMessage(ExceptionMessages.ROWS_HAVE_TO_BE_EQUAL);
    }

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows1Cols2() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestMatrix(1)
                .add(new TestMatrix(1, 2)))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL);
    }

    @Test
    void addOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        TestMatrix matrix1 = new TestMatrix(2);
        TestMatrix matrix2 = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        assertThatMatrix(matrix1.add(matrix2)).isEqualTo(result);
    }

    @Test
    void addOfEmptyMatrixWithSize2AndDefault0AndEmptyMatrixWithSize2AndDefault1() {
        TestMatrix matrix1 = new TestMatrix(2);
        TestMatrix matrix2 = new TestMatrix(2, 1d);
        TestMatrix result = new TestMatrix(2, 1d);
        assertThatMatrix(matrix1.add(matrix2)).isEqualTo(result);
    }

    @Test
    void addOfMatrixWithSize2AndDefault0ToItself() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, 2 * (i + 1d));
        }
        assertThatMatrix(matrix.add(matrix)).isEqualTo(result);
    }

    @Test
    void addOfMatrixWithSize2AndDefault2ToItself() {
        TestMatrix matrix = new TestMatrix(2, 2d);
        TestMatrix result = new TestMatrix(2, 2d);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, 2 * (i + 1d));
        }
        assertThatMatrix(matrix.add(matrix)).isEqualTo(result);
    }

    @Test
    void multiplyOfEmptyMatrixWithSize2With2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        assertThatMatrix(matrix.multiply(2)).isEqualTo(result);
    }

    @Test
    void multiplyMatrixWithSize2AndDefault0With2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < result.size(); i++) {
            matrix.setValue(i, (i + 1));
            result.setValue(i, (i + 1d) * 2);
        }
        assertThatMatrix(matrix.multiply(2)).isEqualTo(result);
    }

    @Test
    void multiplyMatrixWithSize2AndDefault2With2() {
        TestMatrix matrix = new TestMatrix(2, 2d);
        TestMatrix result = new TestMatrix(2, 2d);
        for (int i = 0; i < result.size(); i++) {
            matrix.setValue(i, (i + 1));
            result.setValue(i, (i + 1d) * 2);
        }
        assertThatMatrix(matrix.multiply(2)).isEqualTo(result);
    }

    @Test
    void multiplyOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        TestMatrix matrix1 = new TestMatrix(2);
        TestMatrix matrix2 = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        assertThatMatrix(matrix1.multiply(matrix2)).isEqualTo(result);
    }

    @Test
    void multiplyOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        TestMatrix matrix1 = new TestMatrix(1, 2);
        TestMatrix matrix2 = new TestMatrix(2, 3);
        TestMatrix result = new TestMatrix(1, 3);
        assertThatMatrix(matrix1.multiply(matrix2)).isEqualTo(result);
    }

    @Test
    void multiplyOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        TestMatrix matrix1 = new TestMatrix(2, 3);
        TestMatrix matrix2 = new TestMatrix(1, 2);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> matrix1.multiply(matrix2))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL_ROWS);
    }

    @Test
    void multiplyOfMatrixWithSize2ToItself() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        assertThatMatrix(matrix.multiply(matrix)).isEqualTo(TestMatrix.ofValuesByRows(2,
            7d, 10d, 15d, 22d
        ));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        TestMatrix matrix1 = new TestMatrix(1, 2);
        TestMatrix matrix2 = new TestMatrix(2, 3);
        TestMatrix result = new TestMatrix(1, 3);
        assertThatMatrix(matrix1.multiplyTolerant(matrix2)).isEqualTo(result);
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        TestMatrix matrix1 = new TestMatrix(2, 3);
        TestMatrix matrix2 = new TestMatrix(1, 2);
        TestMatrix result = new TestMatrix(1, 3);
        assertThatMatrix(matrix1.multiplyTolerant(matrix2)).isEqualTo(result);
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow3Col4() {
        TestMatrix matrix1 = new TestMatrix(1, 2);
        TestMatrix matrix2 = new TestMatrix(3, 4);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> matrix1.multiplyTolerant(matrix2))
            .withMessage(ExceptionMessages.MATRIX_COLS_EQUAL_OTHER_ROWS);
    }

    @Test
    void multiplyTolerantOfMatrixWithSize2ToItself() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        assertThatMatrix(matrix.multiplyTolerant(matrix)).isEqualTo(TestMatrix.ofValuesByRows(2,
            7d, 10d, 15d, 22d
        ));
    }

    // endregion

    // region inverse, transpose and determinante

    @Test
    void inverseOfEmptyMatrixWichIsNoSquare() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new TestMatrix(1, 2).inverse())
            .withMessage(ExceptionMessages.MATRIX_HAS_TO_BE_SQUARE);
    }

    @Test
    void inverseOfEmptyMatrixWithSize2() {
        assertThatMatrix(new TestMatrix(2, 0d).inverse()).isNull();
    }

    @Test
    void inverseOfMatrixWithSize2() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(2,
            2, 5, 1, 3
        );
        TestMatrix result = TestMatrix.ofValuesByRows(2,
            3d, -5d, -1d, 2d
        );
        assertThatMatrix(matrix.inverse()).isEqualTo(result);
    }

    @Test
    void inverseOfMatrixWithSize3() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(3,
            3, 5, 1, 2, 4, 5, 1, 2, 2
        );
        TestMatrix result = TestMatrix.ofValuesByRows(3,
            2d, 8d, -21d, -1d, -5d, 13d, 0d, 1d, -2d
        );
        assertThatMatrix(matrix.inverse()).isEqualTo(result);
    }

    @Test
    void transposeOfEmptyMatrixWithSize2() {
        TestMatrix result = new TestMatrix(2);
        assertThatMatrix(new TestMatrix(2).transpose()).isEqualTo(result);
    }

    @Test
    void transposeOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++) {
                int i = (int) matrix.getIndexOfRowAndCol(r, c);
                matrix.setValue(i, i + 1d);
                result.setValue(result.getIndexOfRowAndCol(c, r), i + 1d);
            }
        assertThatMatrix(matrix.transpose()).isEqualTo(result);
    }

    @Test
    void determinanteOfEmptyMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatMatrix(matrix).hasZeroDeterminante();
    }

    @Test
    void determinanteOfMatrixWithSize2AndValues1234() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        assertThatMatrix(matrix).hasDeterminante(-2d);
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues123456789() {
        TestMatrix matrix = new TestMatrix(3);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        assertThatMatrix(matrix).hasZeroDeterminante();
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues1And2OnDiagonale() {
        TestMatrix matrix = new TestMatrix(3);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? 2d : 1d);
        assertThatMatrix(matrix).hasDeterminante(4d);
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1And2OnDiagonale() {
        TestMatrix matrix = new TestMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? 2d : 1d);
        assertThatMatrix(matrix).hasDeterminante(5d);
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1AndMore0InRow() {
        TestMatrix matrix = new TestMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, c != 0 && (r == c || r == 0) ? 0d : 1d);
        assertThatMatrix(matrix).hasDeterminante(2d);
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1AndMore0InCol() {
        TestMatrix matrix = new TestMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r != 0 && (r == c || c == 0) ? 0d : 1d);
        assertThatMatrix(matrix).hasDeterminante(2d);
    }

    // endregion

    // region rank and rowEchelonForm

    @Test
    void rankOfSquareMatrixWithZeroRowInSolution() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(3,
            2, -1, 0,
            -2, 2, -2,
            2, -1, 0
        );
        assertThat(matrix.rank()).isEqualTo(2);
    }

    @Test
    void rankOfSquareMatrixWithoutZeroRowInSolution() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(3,
            1, -1, 2,
            -2, 1, -6,
            1, 0, -2
        );
        assertThat(matrix.rank()).isEqualTo(3);
    }

    @Test
    void rankOfNonSquareMatrix() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(3,
            0d, -2, 2, 4,
            2, -1, -1, 1,
            2, -2, 0d, 3
        );
        assertThat(matrix.rank()).isEqualTo(2);
    }

    @Test
    void rowEchelonFormOfSquareMatrixWithZeroRowInSolution() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(3,
            2, -1, 0,
            -2, 2, -2,
            2, -1, 0
        );
        assertThat(matrix.rowEchelonForm()).isEqualTo(TestMatrix.ofValuesByRows(3,
            2, -1, 0,
            0d, 1d, -2d,
            0d, 0d, 0d
        ));
    }

    @Test
    void rowEchelonFormOfSquareMatrixWithoutZeroRowInSolution() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(3,
            1, -1, 2,
            -2, 1, -6,
            1, 0, -2
        );
        assertThat(matrix.rowEchelonForm()).isEqualTo(TestMatrix.ofValuesByRows(3,
            1, -1, 2,
            0d, -1d, -2d,
            0d, 0d, -6d
        ));
    }

    @Test
    void rowEchelonFormOfNonSquareMatrix() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(3,
            0d, -2, 2, 4,
            2, -1, -1, 1,
            2, -2, 0d, 3
        );
        assertThat(matrix.rowEchelonForm()).isEqualTo(TestMatrix.ofValuesByRows(3,
            2, -1, -1, 1,
            0d, -2, 2, 4,
            0d, 0d, 0d, 0d
        ));
    }

    // endregion

    // region toVector and toParam

    @Test
    void toVectorOfMatrixWithRow2Col2() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new TestMatrix(2, 2).toVector())
            .withMessage(ExceptionMessages.MATRIX_ONE_ROW_OR_COLUMN);
    }

    @Test
    void toVectorOfEmptyMatrixWithRow2Col1() {
        assertThatVector(new TestMatrix(2, 1).toVector()).isEqualTo(new TestVector(2));
    }

    @Test
    void toVectorOfNonEmptyMatrixWithRow2Col1() {
        TestMatrix matrix = new TestMatrix(2, 1);
        matrix.setValue(0, 0, 1d);
        matrix.setValue(1, 0, -4d);
        assertThatVector(matrix.toVector()).isEqualTo(fillVectorWithTestValues(new TestVector(2)));
    }

    @Test
    void toVectorOfEmptyMatrixWithRow1Col2() {
        assertThatVector(new TestMatrix(1, 2).toVector()).isEqualTo(new TestVector(2).transpose());
    }

    @Test
    void toVectorOfNonEmptyMatrixWithRow1Col2() {
        TestMatrix matrix = new TestMatrix(1, 2);
        matrix.setValue(0, 0, 1d);
        matrix.setValue(0, 1, -4d);
        assertThatVector(matrix.toVector())
            .isEqualTo(fillVectorWithTestValues(new TestVector(2)).transpose());
    }

    @Test
    void toVectorOfEmptyMatrixWithRow1Col1() {
        assertThatVector(new TestMatrix(1, 1).toVector()).isEqualTo(new TestVector(1));
    }

    @Test
    void toVectorOfNonEmptyMatrixWithRow1Col1() {
        TestMatrix matrix = new TestMatrix(1, 1);
        matrix.setValue(0, 0, 1d);
        assertThat(matrix.toVector()).isEqualTo(fillVectorWithTestValues(new TestVector(1)));
    }

    @Test
    void toParamOfMatrixWithRow2Col1() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new TestMatrix(2, 1).toParam())
            .withMessage(ExceptionMessages.MATRIX_ONLY_ONE_VALUE);
    }

    @Test
    void toParamOfMatrixWithRow1Col2() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new TestMatrix(1, 2).toParam())
            .withMessage(ExceptionMessages.MATRIX_ONLY_ONE_VALUE);
    }

    @Test
    void toParamOfEmptyMatrixWithRow1Col1() {
        assertThat(new TestMatrix(1, 1).toParam()).isEqualTo(0d);
    }

    @Test
    void toParamOfNonEmptyMatrixWithRow1Col1() {
        TestMatrix matrix = new TestMatrix(1, 1);
        matrix.setValue(0, 0, 1d);
        assertThat(matrix.toParam()).isEqualTo(1d);
    }

    // endregion

    // region static: identity and diagonal

    @Test
    void identityOfSize1() {
        TestMatrix matrix = TestMatrix.identity(1);
        assertThatMatrix(matrix).hasSizeOne();
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertThat(matrix.getValue(i, i)).as(String.valueOf(i)).isEqualTo(1d);
    }

    @Test
    void identityOfSize2() {
        TestMatrix matrix = TestMatrix.identity(2);
        assertThatMatrix(matrix).hasSize(2 * 2);
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertThat(matrix.getValue(i, i)).as(String.valueOf(i)).isEqualTo(1d);
    }

    @Test
    void diagonalOfSize1() {
        TestMatrix matrix = TestMatrix.diagonal(1);
        assertThatMatrix(matrix).hasSizeOne();
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertThat(matrix.getValue(i, i)).isEqualTo(1);
    }

    @Test
    void diagonalOfSize2() {
        TestMatrix matrix = TestMatrix.diagonal(2, 2);
        assertThatMatrix(matrix).hasSize(2 * 2);
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertThat(matrix.getValue(i, i)).isEqualTo(2);
    }

    // endregion

    // region static: of

    @Test
    void ofValuesByRows2WithInvalidParamCount() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> TestMatrix.ofValuesByRows(2, 1))
            .withMessage(ExceptionMessages.MATRIX_ROWS_NOT_CONGRUENT_ZERO);
    }

    @Test
    void ofValuesByRows2WithValidParamCount() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(2, 1, 2);
        for (int i = 0; i < matrix.size(); i++)
            assertThat(matrix.getValue(i)).as("index: " + i).isEqualTo(i + 1);
    }

    @Test
    void ofValuesByCols2WithInvalidParamCount() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> TestMatrix.ofValuesByCols(2, 1))
            .withMessage(ExceptionMessages.MATRIX_COLS_NOT_CONGRUENT_ZERO);
    }

    @Test
    void ofValuesByCols2WithValidParamCount() {
        TestMatrix matrix = TestMatrix.ofValuesByCols(2, 1, 2);
        for (int i = 0; i < matrix.size(); i++)
            assertThat(matrix.getValue(i)).as("index: " + i).isEqualTo(i + 1);
    }

    // endregion

    // region map and copy

    @Test
    void mapOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        Matrix<Integer> result =
            new Matrix<>(new IntegerArithmetic(), 2);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++) {
                matrix.setValue(r, c, r + c + 0.5);
                result.setValue(r, c, r + c);
            }

        assertThatMatrix(matrix.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void mapDefaultValueOfIdentityMatrixWithSize2() {
        TestMatrix matrix = TestMatrix.identity(2);
        Matrix<Number> mapped = matrix.mapDefaultValue(1d);
        assertThatMatrix(mapped).isEqualTo(matrix);
        matrix.forEach(field -> {
            if (field.getRow() == field.getCol()) {
                assertThat(matrix.getMatrix().get(field.getRow()))
                    .containsKey(field.getCol());
                assertThat(mapped.getMatrix().get(field.getRow()))
                    .doesNotContainKey(field.getCol());
            } else {
                assertThat(matrix.getMatrix().get(field.getRow()))
                    .doesNotContainKey(field.getCol());
                assertThat(mapped.getMatrix().get(field.getRow()))
                    .containsKey(field.getCol());
            }
        });
    }

    @Test
    void mapDefaultValueOfMatrixBy2Rows() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(2, 0d, 0d, 2d, 2d);
        Matrix<Number> mapped = matrix.mapDefaultValue(2d);
        assertThatMatrix(mapped).isEqualTo(matrix);
        matrix.forEach(field -> {
            if (field.getRow() == 0) {
                assertThat(matrix.getMatrix()).doesNotContainKey(field.getRow());
                assertThat(mapped.getMatrix().get(field.getRow())).containsKey(field.getCol());
            } else {
                assertThat(matrix.getMatrix().get(field.getRow())).containsKey(field.getCol());
                assertThat(mapped.getMatrix()).doesNotContainKey(field.getRow());
            }
        });
    }

    @Test
    void copyOfMatrixWithSize2() {
        assertCopyable(new TestMatrix(2));
    }

    // endregion

    // region override

    @Test
    void iteratorOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        List<TestMatrix.Field> values = new ArrayList<>();
        Iterator<Matrix<Number>.Field> iterator = matrix.iterator();
        while (iterator.hasNext()) {
            TestMatrix.Field d = iterator.next();
            values.add(d);
            assertThat(d.getValue()).isEqualTo(0d);
        }
        assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(iterator::next)
            .withMessage("4 / 4");
        assertThat(values).hasSize((int) matrix.size());
    }

    @Test
    void streamOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.stream().count()).isEqualTo(4);
    }

    @Test
    void parallelStreamOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.parallelStream().count()).isEqualTo(4);
    }

    @Test
    void equalsOfMatrixWithDifferentTypes() {
        Matrix<Double> doubleMatrix = new Matrix<>(DoubleArithmetic.getInstance(), 2);
        assertThatMatrix(doubleMatrix)
            .isNotEqualTo(new Matrix<>(IntegerArithmetic.getInstance(), 2))
            .isEqualTo(new DoubleMatrix(2));
    }

    @Test
    void equalsOfTestMatrixWithRow2Col3() {
        assertThatMatrix(new TestMatrix(2, 3))
            .isEqualTo(new TestMatrix(2, 3))
            .isNotEqualTo(new TestMatrix(3, 2));
    }

    @Test
    void equalsOfTestMatrixWithDifferentDefaults() {
        TestMatrix default0 = new TestMatrix(2);
        default0.forEach(field -> default0.setValue(field.getIndex(), 1d));
        assertThatMatrix(default0)
            .isNotEqualTo(new TestMatrix(2))
            .isEqualTo(new TestMatrix(2, 1d));
    }

    @Test
    void hashCodeOfTestMatrixWithRow2Col3() {
        assertThat(new TestMatrix(2, 3).hashCode()).isEqualTo(925536);
    }

    @Test
    void toStringOfTestMatrixWithRow2Col3() {
        TestMatrix matrix = new TestMatrix(2, 3);
        assertThatMatrix(matrix).hasToString("2 3: []");
    }

    @Test
    void serializable() {
        TestMatrix testMatrix = new TestMatrix(1);
        assertSerializable(testMatrix, TestMatrix.class);
        assertSerializable(testMatrix.new Field(0, 1), Matrix.Field.class);
    }

    // endregion

    // region protected: subMatrix, coFactor, coFactorMatrix and signumFactor

    @Test
    void subMatrixR0C0OfMatrixWithR1C2WichIsNoSquare() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new TestMatrix(1, 2).subMatrix(0, 0))
            .withMessage(ExceptionMessages.MATRIX_HAS_TO_BE_SQUARE);
    }

    @Test
    void subMatrixR1C0OfMatrixWithSize1UsingInvalidRow1() {
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> new TestMatrix(1).subMatrix(1, 0))
            .withMessage("row: 1 / 1");
    }

    @Test
    void subMatrixR0C1OfMatrixWithSize1UsingInvalidCol1() {
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> new TestMatrix(1).subMatrix(0, 1))
            .withMessage("col: 1 / 1");
    }

    @Test
    void subMatrixR0C0OfMatrixWithSize1() {
        assertThatMatrix(new TestMatrix(2).subMatrix(0, 0)).hasSizeOne();
    }

    @Test
    void subMatrixR0C0OfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        Matrix<Number> subMatrix = matrix.subMatrix(0, 0);
        assertThatMatrix(subMatrix).hasSizeOne();
        assertThat(subMatrix.getValue(0)).isEqualTo(3);
    }

    @Test
    void subMatrixR0C1OfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        Matrix<Number> subMatrix = matrix.subMatrix(0, 1);
        assertThatMatrix(subMatrix).hasSizeOne();
        assertThat(subMatrix.getValue(0)).isEqualTo(2);
    }

    @Test
    void subMatrixR1C0OfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        Matrix<Number> subMatrix = matrix.subMatrix(1, 0);
        assertThatMatrix(subMatrix).hasSizeOne();
        assertThat(subMatrix.getValue(0)).isEqualTo(1);
    }

    @Test
    void subMatrixR1C1OfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        Matrix<Number> subMatrix = matrix.subMatrix(1, 1);
        assertThatMatrix(subMatrix).hasSizeOne();
        assertThat(subMatrix.getValue(0)).isEqualTo(0);
    }

    @Test
    void coFactorR0C0OfMatrixWithR1C2WichIsNoSquare() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new TestMatrix(1, 2).coFactor(0, 0))
            .withMessage(ExceptionMessages.MATRIX_HAS_TO_BE_SQUARE);
    }

    @Test
    void coFactorR1C0OfMatrixWithSize1UsingInvalidRow1() {
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> new TestMatrix(1).coFactor(1, 0))
            .withMessage("row: 1 / 1");
    }

    @Test
    void coFactorR0C1OfMatrixWithSize1UsingInvalidCol1() {
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> new TestMatrix(1).coFactor(0, 1))
            .withMessage("col: 1 / 1");
    }

    @Test
    void coFactorsOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);

        assertThat(matrix.coFactor(0, 0)).isEqualTo(0d);
        assertThat(matrix.coFactor(0, 1)).isEqualTo(-2d);
        assertThat(matrix.coFactor(1, 0)).isEqualTo(-2d);
        assertThat(matrix.coFactor(1, 1)).isEqualTo(0d);
    }

    @Test
    void coFactorMatrixOfMatrixWhichIsNoSquare() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new TestMatrix(1, 2).coFactorMatrix())
            .withMessage(ExceptionMessages.MATRIX_HAS_TO_BE_SQUARE);
    }

    @Test
    void coFactorMatrixOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);

        Matrix<Number> coFactorMatrix = matrix.coFactorMatrix();
        assertThat(coFactorMatrix.getValue(0, 0)).isEqualTo(0d);
        assertThat(coFactorMatrix.getValue(0, 1)).isEqualTo(-2d);
        assertThat(coFactorMatrix.getValue(1, 0)).isEqualTo(-2d);
        assertThat(coFactorMatrix.getValue(1, 1)).isEqualTo(0d);
    }

    @Test
    void adjunctMatrixOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);

        Matrix<Number> coFactorMatrix = matrix.adjunctMatrix();
        assertThat(coFactorMatrix.getValue(0, 0)).isEqualTo(0d);
        assertThat(coFactorMatrix.getValue(0, 1)).isEqualTo(-2d);
        assertThat(coFactorMatrix.getValue(1, 0)).isEqualTo(-2d);
        assertThat(coFactorMatrix.getValue(1, 1)).isEqualTo(0d);
    }

    @Test
    void signumFactorOfR0C0() {
        assertThat(Matrix.signumFactor(0, 0)).isOne();
    }

    @Test
    void signumFactorOfR0C1() {
        assertThat(Matrix.signumFactor(0, 1)).isEqualTo(-1);
    }

    @Test
    void signumFactorOfR1C0() {
        assertThat(Matrix.signumFactor(1, 0)).isEqualTo(-1);
    }

    @Test
    void signumFactorOfR1C1() {
        assertThat(Matrix.signumFactor(1, 1)).isOne();
    }

    // endregion

    // region protected: modify

    @Test
    void swapRowsOfMatrixWithSize2UsingInvalidRow1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.swapRows(-1, 0))
            .withMessage("row: -1 / 2");
    }

    @Test
    void swapRowsOfMatrixWithSize2UsingInvalidRow2() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.swapRows(0, -1))
            .withMessage("row: -1 / 2");
    }

    @Test
    void swapRowsOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1);
            result.setValue((i + result.getCols()) % (int) result.size(), i + 1);
        }
        assertThatMatrix(matrix.swapRows(0, 1)).isEqualTo(result);
    }

    @Test
    void swapColsOfMatrixWithSize2UsingInvalidCol1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.swapCols(-1, 0))
            .withMessage("col: -1 / 2");
    }

    @Test
    void swapColsOfMatrixWithSize2UsingInvalidCol2() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.swapCols(0, -1))
            .withMessage("col: -1 / 2");
    }

    @Test
    void swapColsOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1);
            result.setValue(i + (i % result.getCols() == 0 ? 1 : -1), i + 1);
        }
        assertThatMatrix(matrix.swapCols(0, 1)).isEqualTo(result);
    }

    @Test
    void multiplyRowOfMatrixWithSize2UsingInvalidRow() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.multiplyRow(-1, 0))
            .withMessage("row: -1 / 2");
    }

    @Test
    void multiplyRowOfMatrixWithSize2Using0() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i / result.getCols() == 0 ? 0 : 1));
        }
        assertThatMatrix(matrix.multiplyRow(0, 0d)).isEqualTo(result);
    }

    @Test
    void multiplyRowOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, i + 1d);
        }
        assertThatMatrix(matrix.multiplyRow(0, 1d)).isEqualTo(result);
    }

    @Test
    void multiplyRowOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i / result.getCols() == 0 ? 2 : 1));
        }
        assertThatMatrix(matrix.multiplyRow(0, 2d)).isEqualTo(result);
    }

    @Test
    void multiplyColOfMatrixWithSize2UsingInvalidCol() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.multiplyCol(-1, 0))
            .withMessage("col: -1 / 2");
    }

    @Test
    void multiplyColOfMatrixWithSize2Using0() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i % result.getCols() == 0 ? 0 : 1));
        }
        assertThatMatrix(matrix.multiplyCol(0, 0d)).isEqualTo(result);
    }

    @Test
    void multiplyColOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, i + 1d);
        }
        assertThatMatrix(matrix.multiplyCol(0, 1d)).isEqualTo(result);
    }

    @Test
    void multiplyColOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i % result.getCols() == 0 ? 2d : 1));
        }
        assertThatMatrix(matrix.multiplyCol(0, 2d)).isEqualTo(result);
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2UsingInvalidRow1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.addRowMultipleTimes(-1, 0, 0))
            .withMessage("row: -1 / 2");
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2UsingInvalidRow2() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.addRowMultipleTimes(0, -1, 0))
            .withMessage("row: -1 / 2");
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2ToSameRow() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i / result.getCols() == 0 ? 2 : 1));
        }
        assertThatMatrix(matrix.addRowMultipleTimes(0, 0, 2)).isEqualTo(result);
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using0() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i, (double) value);
        }
        assertThatMatrix(matrix.addRowMultipleTimes(0, 1, 0d)).isEqualTo(result);
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i,
                (double) value + (i / result.getCols() == 0 ? 2 : 0)
            );
        }
        assertThatMatrix(matrix.addRowMultipleTimes(0, 1, 1d)).isEqualTo(result);
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i,
                (double) value + (i / result.getCols() == 0 ? 4 : 0)
            );
        }
        assertThatMatrix(matrix.addRowMultipleTimes(0, 1, 2d)).isEqualTo(result);
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2UsingInvalidCol1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.addColMultipleTimes(-1, 0, 1))
            .withMessage("col: -1 / 2");
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2UsingInvalidCol2() {
        TestMatrix matrix = new TestMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.addColMultipleTimes(0, -1, 0))
            .withMessage("col: -1 / 2");
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2ToSameRow() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i % result.getCols() == 0 ? 2 : 1));
        }
        assertThatMatrix(matrix.addColMultipleTimes(0, 0, 2)).isEqualTo(result);
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using0() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i, (double) value);
        }
        assertThatMatrix(matrix.addColMultipleTimes(0, 1, 0d)).isEqualTo(result);
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i,
                (double) value + (i % result.getCols() == 0 ? value : 0)
            );
        }
        assertThatMatrix(matrix.addColMultipleTimes(0, 1, 1d)).isEqualTo(result);
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i, (double) value +
                (i % result.getCols() == 0 ? 2 * value : 0)
            );
        }
        assertThatMatrix(matrix.addColMultipleTimes(0, 1, 2d)).isEqualTo(result);
    }

    // endregion

    // region protected: modify - rowEchelonForm

    @Test
    void swapZeroRowsToBottomWithoutZeroRows() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(3,
            2, -1, 0,
            -2, 2, -2,
            2, -1, 0
        );
        assertThatMatrix(matrix.swapZeroRowsToBottom()).isEqualTo(matrix);
    }

    @Test
    void swapZeroRowsToBottomWithZeroRows() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(3,
            0d, 0d, 0d,
            -2, 2, -2,
            2, -1, 0
        );
        assertThatMatrix(matrix.swapZeroRowsToBottom())
            .isNotEqualTo(matrix)
            .isEqualTo(matrix.swapRows(0, 1).swapRows(1, 2));
    }

    @Test
    void ensureDiagonalFieldsAreNonZeroWithoutZeroRows() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(3,
            2, -1, 0,
            -2, 2, -2,
            2, -1, 0
        );
        assertThatMatrix(matrix.ensureDiagonalFieldsAreNonZero(true)).isEqualTo(matrix);
    }

    @Test
    void ensureDiagonalFieldsAreNonZeroWithZeroRows() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(3,
            0d, 0d, -2,
            2, -1, 0d,
            2, 0d, 0d
        );
        assertThatMatrix(matrix.ensureDiagonalFieldsAreNonZero(true))
            .isNotEqualTo(matrix)
            .isEqualTo(matrix.swapRows(0, 1).swapCols(1, 2));
    }

    // endregion

    // region protected: getIndexOfRowAndCol, isDefaultValue and isValid

    @Test
    void getIndexOfRowAndColOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        int index = 0;
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                assertThat(matrix.getIndexOfRowAndCol(r, c)).isEqualTo(index++);
    }

    @Test
    void isValidIndexOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.isValidIndex(-1)).isFalse();
        for (int i = 0; i < matrix.size(); i++)
            assertThat(matrix.isValidIndex(i)).isTrue();
        assertThat(matrix.isValidIndex((int) matrix.size())).isFalse();
    }

    @Test
    void isValidRowOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.isValidRow(-1)).isFalse();
        for (int i = 0; i < matrix.getRows(); i++)
            assertThat(matrix.isValidRow(i)).isTrue();
        assertThat(matrix.isValidRow(matrix.getRows())).isFalse();
    }

    @Test
    void isValidColOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        assertThat(matrix.isValidCol(-1)).isFalse();
        for (int i = 0; i < matrix.getCols(); i++)
            assertThat(matrix.isValidCol(i)).isTrue();
        assertThat(matrix.isValidCol(matrix.getCols())).isFalse();
    }

    @Test
    void isDefaultValueOfMatrixWithDefaultValue1() {
        TestMatrix matrix = new TestMatrix(1, 1d);
        assertThat(matrix.isDefaultValue(0)).isFalse();
        assertThat(matrix.isDefaultValue(1d)).isTrue();
    }

    @Test
    void isZeroRowOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(0, 1);
        assertThat(matrix.isZeroRow(0)).isFalse();
        assertThat(matrix.isZeroRow(1)).isTrue();
    }

    @Test
    void isZeroRowWithInvalidIndexOfMatrixWithSize1() {
        TestMatrix matrix = new TestMatrix(1);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.isZeroRow(1))
            .withMessage("row: 1 / 1");
    }

    // endregion

    // region field

    @Test
    void fieldGetter() {
        TestMatrix matrix = new TestMatrix(2);
        int index = 0;
        for (Matrix<Number>.Field field : matrix) {
            assertThat(field.getIndex()).isEqualTo(index);
            assertThat(field.getRow()).isEqualTo(index / matrix.getCols());
            assertThat(field.getCol()).isEqualTo(index % matrix.getCols());
            assertThat(field.getMatrix()).isEqualTo(matrix);
            index++;
        }
    }

    @Test
    void fieldOverride() {
        TestMatrix matrix = new TestMatrix(2);
        Matrix<Number>.Field previous = null;
        for (Matrix<Number>.Field field : matrix) {
            if (previous != null) assertThat(field).isNotEqualTo(previous);
            else assertThat(field).isEqualTo(matrix.new Field(field.getIndex(), field.getValue()));
            assertThat(field.hashCode()).isPositive();
            assertThat(field).hasToString(field.getIndex() + ": " + field.getValue());
            previous = field;
        }

        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.new Field((int) matrix.size(), 0))
            .withMessage("size: 4 / 4");
    }

    @Test
    void fieldEqualsOfMatrixWithDifferentTypes() {
        Matrix<Double> doubleMatrix = new Matrix<>(DoubleArithmetic.getInstance(), 2);
        assertThat(doubleMatrix.iterator().next())
            .isNotEqualTo(new Matrix<>(IntegerArithmetic.getInstance(), 2).iterator().next())
            .isEqualTo(new DoubleMatrix(2).iterator().next());
    }

    // endregion
}
