package io.rala.math.algebra.matrix.typed;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.exception.NotSupportedException;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatMatrix;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DoubleMatrixTest {
    // region constructors

    @Test
    void constructorWithNegativeSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleMatrix(-1))
            .withMessage(ExceptionMessages.ROWS_COLS_HAVE_TO_BE_GREATER_ZERO);
    }

    @Test
    void constructorWithIntegerMaxValueSize() {
        DoubleMatrix matrix = new DoubleMatrix(Integer.MAX_VALUE);

        long expectedSize = (long) Integer.MAX_VALUE * Integer.MAX_VALUE;
        assertThatMatrix(matrix).hasSize(expectedSize);
    }

    @Test
    void constructorWithSize0() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleMatrix(0))
            .withMessage(ExceptionMessages.ROWS_COLS_HAVE_TO_BE_GREATER_ZERO);
    }

    @Test
    void constructorWithSize1() {
        assertThatMatrix(new DoubleMatrix(1)).hasSize(1);
    }

    @Test
    void constructorWithRows1Cols1() {
        assertThatMatrix(new DoubleMatrix(1, 1)).hasRows(1).hasCols(1);
    }

    @Test
    void constructorWithRows1Cols2() {
        assertThatMatrix(new DoubleMatrix(1, 2)).hasRows(1).hasCols(2);
    }

    @Test
    void constructorWithMatrix() {
        assertThatMatrix(new DoubleMatrix(new DoubleMatrix(1, 2))).hasRows(1).hasCols(2);
    }

    // endregion

    // region getter and size

    @Test
    void createWithSize1AndAssertSizeEquals1() {
        assertThatMatrix(new DoubleMatrix(1)).hasSizeOne();
    }

    @Test
    void createWithSize2AndAssertSizeEquals4() {
        assertThatMatrix(new DoubleMatrix(2)).hasSize(4);
    }

    @Test
    void createWithRow1Col2AndAssertSizeEquals2() {
        assertThatMatrix(new DoubleMatrix(1, 2)).hasSize(2);
    }

    @Test
    void createWithRow2Col3AndAssertSizeEquals6() {
        assertThatMatrix(new DoubleMatrix(2, 3)).hasSize(6);
    }

    // endregion

    // region rows and cols

    @Test
    void getRowFieldsM1OfMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getRowFields(-1))
            .withMessage("row: -1 / 2");
    }

    @Test
    void getRowFields0OfMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);
        List<Matrix<Double>.Field> row0 = matrix.getRowFields(0);
        assertThat(row0).hasSize(2);
        for (int i = 0; i < row0.size(); i++) {
            Matrix<Double>.Field field = row0.get(i);
            assertThat(field.getRow()).isZero();
            assertThat(field.getCol()).isEqualTo(i);
            assertThat(field.getValue()).isEqualTo(i);
        }
    }

    @Test
    void getRow0OfMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);
        List<Double> row0 = matrix.getRow(0);
        assertThat(row0).hasSize(2);
        for (int i = 0; i < row0.size(); i++)
            assertThat(row0.get(i)).isEqualTo(i);
    }

    @Test
    void getColFieldsM1OfMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getColFields(-1))
            .withMessage("col: -1 / 2");
    }

    @Test
    void getColFields0OfMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);
        List<Matrix<Double>.Field> col0 = matrix.getColFields(0);
        assertThat(col0).hasSize(2);
        for (int i = 0; i < col0.size(); i++) {
            Matrix<Double>.Field field = col0.get(i);
            assertThat(field.getCol()).isZero();
            assertThat(field.getRow()).isEqualTo(i);
            assertThat(field.getValue()).isEqualTo(i * 2);
        }
    }

    @Test
    void getCol0OfMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);
        List<Double> col0 = matrix.getCol(0);
        assertThat(col0).hasSize(2);
        for (int i = 0; i < col0.size(); i++)
            assertThat(col0.get(i)).isEqualTo(i * 2);
    }

    // endregion

    // region value

    @Test
    void getValueByIndexMinus1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getValue(-1))
            .withMessage("size: -1 / 4");
    }

    @Test
    void getValueByRowMinus1Col0() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getValue(-1, 0))
            .withMessage("row: -1 / 2");
    }

    @Test
    void getValueByRow0ColMinus1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getValue(0, -1))
            .withMessage("col: -1 / 2");
    }

    @Test
    void setValueByIndexMinus1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.setValue(-1, 0d))
            .withMessage("size: -1 / 4");
    }

    @Test
    void setValueByIndex0WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.setValue(0, 1d)).isZero();
        assertThat(matrix.getValue(0)).isOne();
        assertThat(matrix.getValue(0, 0)).isOne();
        // assert all other are unset
    }

    @Test
    void setValueByIndex3WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.setValue(3, 1d)).isZero();
        assertThat(matrix.getValue(3)).isOne();
        assertThat(matrix.getValue(1, 1)).isOne();
        // assert all other are unset
    }

    @Test
    void setValueByRowMinus1Col0() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.setValue(-1, 0, 0d))
            .withMessage("row: -1 / 2");
    }

    @Test
    void setValueByRow0ColMinus1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.setValue(0, -1, 0d))
            .withMessage("col: -1 / 2");
    }

    @Test
    void setValueByRow0Col0WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.setValue(0, 0, 1d)).isZero();
        assertThat(matrix.getValue(0)).isOne();
        assertThat(matrix.getValue(0, 0)).isOne();
        // assert all other are unset
    }

    @Test
    void setValueByRow1Col0WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.setValue(1, 0, 1d)).isZero();
        assertThat(matrix.getValue(1, 0)).isOne();
        assertThat(matrix.getValue(2)).isOne();
        // assert all other are unset
    }

    @Test
    void setValueByIndex2WichWasSet() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        matrix.setValue(2, 1d);
        assertThat(matrix.getValue(2)).isOne();
        assertThat(matrix.setValue(2, 2d)).isOne();
    }

    @Test
    void removeValueByIndexMinus1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.removeValue(-1))
            .withMessage("size: -1 / 4");
    }

    @Test
    void removeValueByIndex0WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.removeValue(0)).isZero();
    }

    @Test
    void removeValueByRowMinus1Col0() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.removeValue(-1, 0))
            .withMessage("row: -1 / 2");
    }

    @Test
    void removeValueByRow0ColMinus1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.removeValue(0, -1))
            .withMessage("col: -1 / 2");
    }

    @Test
    void removeValueByRow0Col0WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.removeValue(0, 0)).isZero();
    }

    @Test
    void removeValueByIndex2WhichWasSet() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        matrix.setValue(2, 1d);
        assertThat(matrix.removeValue(2)).isOne();
    }

    // endregion

    // region compute

    @Test
    void computeWithUnaryOperatorByIndex0WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.compute(0, number -> 1d)).isZero();
        assertThat(matrix.getValue(0)).isOne();
        assertThat(matrix.getValue(0, 0)).isOne();
    }

    @Test
    void computeWithUnaryOperatorByIndex3WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.compute(3, number -> 1d)).isZero();
        assertThat(matrix.getValue(3)).isOne();
        assertThat(matrix.getValue(1, 1)).isOne();
    }

    @Test
    void computeWithUnaryOperatorByRow0Col0WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.compute(0, 0, number -> 1d)).isZero();
        assertThat(matrix.getValue(0)).isOne();
        assertThat(matrix.getValue(0, 0)).isOne();
        // assert all other are unset
    }

    @Test
    void computeWithUnaryOperatorByRow1Col1WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.compute(1, 0, number -> 1d)).isZero();
        assertThat(matrix.getValue(1, 0)).isOne();
        assertThat(matrix.getValue(2)).isOne();
        // assert all other are unset
    }

    @Test
    void computeWithBinaryOperatorByIndex0WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.compute(0,
            1d, matrix.getArithmetic()::sum
        )).isZero();
        assertThat(matrix.getValue(0)).isEqualTo(1d);
        assertThat(matrix.getValue(0, 0)).isEqualTo(1d);
    }

    @Test
    void computeWithBinaryOperatorByIndex3WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.compute(3,
            1d, matrix.getArithmetic()::sum
        )).isZero();
        assertThat(matrix.getValue(3)).isEqualTo(1d);
        assertThat(matrix.getValue(1, 1)).isEqualTo(1d);
    }

    @Test
    void computeWithBinaryOperatorByRow0Col0WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.compute(0, 0,
            1d, matrix.getArithmetic()::sum
        )).isZero();
        assertThat(matrix.getValue(0)).isEqualTo(1d);
        assertThat(matrix.getValue(0, 0)).isEqualTo(1d);
        // assert all other are unset
    }

    @Test
    void computeWithBinaryOperatorByRow1Col1WhichWasUnset() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.compute(1, 0,
            1d, matrix.getArithmetic()::sum
        )).isZero();
        assertThat(matrix.getValue(1, 0)).isEqualTo(1d);
        assertThat(matrix.getValue(2)).isEqualTo(1d);
        // assert all other are unset
    }

    @Test
    void computeAllWithFunctionOfEmptyMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        matrix.computeAll(field ->
            matrix.getArithmetic().sum(field.getValue(), 1d)
        );
        assertThat(matrix.stream().allMatch(field ->
            matrix.getArithmetic().isEqual(field.getValue(), 1d)
        )).isTrue();
    }

    @Test
    void computeAllWithFunctionAndBinaryOperatorOfEmptyMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        matrix.computeAll(field -> 1d, matrix.getArithmetic()::sum);
        assertThat(matrix.stream().allMatch(field ->
            matrix.getArithmetic().isEqual(field.getValue(), 1d)
        )).isTrue();
    }

    // endregion

    // region isSquare, isDiagonal and isInvertible

    @Test
    void isSquareOfMatrixWithRow1Col2() {
        assertThatMatrix(new DoubleMatrix(1, 2)).isNoSquare();
    }

    @Test
    void isSquareOfMatrixWithSize2() {
        assertThatMatrix(new DoubleMatrix(2)).isSquare();
    }

    @Test
    void isDiagonalOfMatrixWithRow1Col2() {
        assertThatMatrix(new DoubleMatrix(1, 2)).isNoDiagonal();
    }

    @Test
    void isDiagonalOfMatrixOfValidValues() {
        assertThatMatrix(DoubleMatrix.ofValuesByRows(2, 1, 0, 0, 4)).isDiagonal();
    }

    @Test
    void isDiagonalOfMatrixOfInvalidValues() {
        assertThatMatrix(DoubleMatrix.ofValuesByRows(2, 1, 2, 3, 4)).isNoDiagonal();
    }

    @Test
    void isDiagonalOfDiagonalMatrix() {
        assertThatMatrix(DoubleMatrix.diagonal(1, 2, 3)).isDiagonal();
    }

    @Test
    void isInvertibleOfMatrixWithSize2AndDeterminante0() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        matrix.setValue(0, 1d);
        matrix.setValue(1, 2d);
        matrix.setValue(2, 2d);
        matrix.setValue(3, 4d);
        assertThatMatrix(matrix).isSquare();
        assertThatMatrix(matrix).hasZeroDeterminante();
        assertThatMatrix(matrix).isNoInvertible();
    }

    @Test
    void isInvertibleOfMatrixWithSize2AndDeterminanteNon0() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        matrix.setValue(0, 1d);
        matrix.setValue(1, 0d);
        matrix.setValue(2, 0d);
        matrix.setValue(3, 4d);
        assertThatMatrix(matrix).isSquare();
        assertThatMatrix(matrix).hasNoZeroDeterminante();
        assertThatMatrix(matrix).isInvertible();
    }

    // endregion

    // region add and multiply

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows2Cols1() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleMatrix(1)
                .add(new DoubleMatrix(2, 1)))
            .withMessage(ExceptionMessages.ROWS_HAVE_TO_BE_EQUAL);
    }

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows1Cols2() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleMatrix(1)
                .add(new DoubleMatrix(1, 2)))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL);
    }

    @Test
    void addOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        DoubleMatrix matrix1 = new DoubleMatrix(2);
        DoubleMatrix matrix2 = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        assertThatMatrix(matrix1.add(matrix2)).isEqualTo(result);
    }

    @Test
    void addOfMatrixWithSize2ToItself() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, 2 * (i + 1d));
        }
        assertThatMatrix(matrix.add(matrix)).isEqualTo(result);
    }

    @Test
    void multiplyOfEmptyMatrixWithSize2With2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        assertThatMatrix(matrix.multiply(2d)).isEqualTo(result);
    }

    @Test
    void multiplyMatrixWithSize2With2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < result.size(); i++) {
            matrix.setValue(i, (i + 1d));
            result.setValue(i, (i + 1d) * 2);
        }
        assertThatMatrix(matrix.multiply(2d)).isEqualTo(result);
    }

    @Test
    void multiplyOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        DoubleMatrix matrix1 = new DoubleMatrix(2);
        DoubleMatrix matrix2 = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        assertThatMatrix(matrix1.multiply(matrix2)).isEqualTo(result);
    }

    @Test
    void multiplyOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        DoubleMatrix matrix1 = new DoubleMatrix(1, 2);
        DoubleMatrix matrix2 = new DoubleMatrix(2, 3);
        DoubleMatrix result = new DoubleMatrix(1, 3);
        assertThatMatrix(matrix1.multiply(matrix2)).isEqualTo(result);
    }

    @Test
    void multiplyOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        DoubleMatrix matrix1 = new DoubleMatrix(2, 3);
        DoubleMatrix matrix2 = new DoubleMatrix(1, 2);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> matrix1.multiply(matrix2))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL_ROWS);
    }

    @Test
    void multiplyOfMatrixWithSize2ToItself() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        assertThatMatrix(matrix.multiply(matrix)).isEqualTo(DoubleMatrix.ofValuesByRows(2,
            7, 10, 15, 22
        ));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        DoubleMatrix matrix1 = new DoubleMatrix(1, 2);
        DoubleMatrix matrix2 = new DoubleMatrix(2, 3);
        DoubleMatrix result = new DoubleMatrix(1, 3);
        assertThatMatrix(matrix1.multiplyTolerant(matrix2)).isEqualTo(result);
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        DoubleMatrix matrix1 = new DoubleMatrix(2, 3);
        DoubleMatrix matrix2 = new DoubleMatrix(1, 2);
        DoubleMatrix result = new DoubleMatrix(1, 3);
        assertThatMatrix(matrix1.multiplyTolerant(matrix2)).isEqualTo(result);
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow3Col4() {
        DoubleMatrix matrix1 = new DoubleMatrix(1, 2);
        DoubleMatrix matrix2 = new DoubleMatrix(3, 4);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> matrix1.multiplyTolerant(matrix2))
            .withMessage(ExceptionMessages.MATRIX_COLS_EQUAL_OTHER_ROWS);
    }

    @Test
    void multiplyTolerantOfMatrixWithSize2ToItself() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        assertThatMatrix(matrix.multiplyTolerant(matrix)).isEqualTo(DoubleMatrix.ofValuesByRows(2,
            7, 10, 15, 22
        ));
    }

    // endregion

    // region inverse, transpose and determinante

    @Test
    void inverseOfEmptyMatrixWichIsNoSquare() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new DoubleMatrix(1, 2).inverse())
            .withMessage(ExceptionMessages.MATRIX_HAS_TO_BE_SQUARE);
    }

    @Test
    void inverseOfEmptyMatrixWithSize2() {
        assertThatMatrix(new DoubleMatrix(2).inverse()).isNull();
    }

    @Test
    void inverseOfMatrixWithSize2() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            2, 5, 1, 3
        );
        DoubleMatrix result = DoubleMatrix.ofValuesByRows(2,
            3, -5, -1, 2
        );
        assertThatMatrix(matrix.inverse()).isEqualTo(result);
    }

    @Test
    void inverseOfMatrixWithSize3() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(3,
            3, 5, 1, 2, 4, 5, 1, 2, 2
        );
        DoubleMatrix result = DoubleMatrix.ofValuesByRows(3,
            2, 8, -21, -1, -5, 13, 0, 1, -2
        );
        assertThatMatrix(matrix.inverse()).isEqualTo(result);
    }

    @Test
    void transposeOfEmptyMatrixWithSize2() {
        DoubleMatrix result = new DoubleMatrix(2);
        assertThatMatrix(new DoubleMatrix(2).transpose()).isEqualTo(result);
    }

    @Test
    void transposeOfMatrixWithSize2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++) {
                int i = (int) TestMatrix.getIndexOfRowAndCol(matrix, r, c);
                matrix.setValue(i, i + 1d);
                result.setValue(TestMatrix.getIndexOfRowAndCol(result, c, r), i + 1d);
            }
        assertThatMatrix(matrix.transpose()).isEqualTo(result);
    }

    @Test
    void determinanteOfEmptyMatrixWithSize2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThatMatrix(matrix).hasZeroDeterminante();
    }

    @Test
    void determinanteOfMatrixWithSize2AndValues1234() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        assertThatMatrix(matrix).hasDeterminante(-2d);
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues123456789() {
        DoubleMatrix matrix = new DoubleMatrix(3);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        assertThatMatrix(matrix).hasZeroDeterminante();
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues1And2OnDiagonale() {
        DoubleMatrix matrix = new DoubleMatrix(3);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? 2d : 1d);
        assertThatMatrix(matrix).hasDeterminante(4d);
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1And2OnDiagonale() {
        DoubleMatrix matrix = new DoubleMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? 2d : 1d);
        assertThatMatrix(matrix).hasDeterminante(5d);
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1AndMore0InRow() {
        DoubleMatrix matrix = new DoubleMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, c != 0 && (r == c || r == 0) ? 0d : 1d);
        assertThatMatrix(matrix).hasDeterminante(2d);
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1AndMore0InCol() {
        DoubleMatrix matrix = new DoubleMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r != 0 && (r == c || c == 0) ? 0d : 1d);
        assertThatMatrix(matrix).hasDeterminante(2d);
    }

    // endregion

    // region static: identity and diagonal

    @Test
    void identityOfSize1() {
        DoubleMatrix matrix = DoubleMatrix.identity(1);
        assertThatMatrix(matrix).hasSizeOne();
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertThat(matrix.getValue(i, i)).isOne();
    }

    @Test
    void identityOfSize2() {
        DoubleMatrix matrix = DoubleMatrix.identity(2);
        assertThatMatrix(matrix).hasSize(2 * 2);
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertThat(matrix.getValue(i, i)).isOne();
    }

    @Test
    void diagonalOfSize1() {
        DoubleMatrix matrix = DoubleMatrix.diagonal(1);
        assertThatMatrix(matrix).hasSizeOne();
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertThat(matrix.getValue(i, i)).isOne();
    }

    @Test
    void diagonalOfSize2() {
        DoubleMatrix matrix = DoubleMatrix.diagonal(2, 2);
        assertThatMatrix(matrix).hasSize(2 * 2);
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertThat(matrix.getValue(i, i)).isEqualTo(2);
    }

    // endregion

    // region static: of

    @Test
    void ofValuesByRows2WithInvalidParamCount() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> DoubleMatrix.ofValuesByRows(2, 1))
            .withMessage(ExceptionMessages.MATRIX_ROWS_NOT_CONGRUENT_ZERO);
    }

    @Test
    void ofValuesByRows2WithValidParamCount() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2, 1, 2);
        for (int i = 0; i < matrix.size(); i++)
            assertThat(matrix.getValue(i)).as("index: " + i).isEqualTo(i + 1);
    }

    @Test
    void ofValuesByCols2WithInvalidParamCount() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> DoubleMatrix.ofValuesByCols(2, 1))
            .withMessage(ExceptionMessages.MATRIX_COLS_NOT_CONGRUENT_ZERO);
    }

    @Test
    void ofValuesByCols2WithValidParamCount() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByCols(2, 1, 2);
        for (int i = 0; i < matrix.size(); i++)
            assertThat(matrix.getValue(i)).as("index: " + i).isEqualTo(i + 1d);
    }

    // endregion

    // region map and copy

    @Test
    void mapOfMatrixWithSize2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
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
    void copyOfMatrixWithSize2() {
        assertCopyable(new DoubleMatrix(2));
    }

    // endregion

    // region override

    @Test
    void iteratorOfEmptyMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        List<DoubleMatrix.Field> values = new ArrayList<>();
        for (DoubleMatrix.Field d : matrix) {
            values.add(d);
            assertThat(d.getValue()).isZero();
        }
        assertThat(values).hasSize((int) matrix.size());
    }

    @Test
    void streamOfEmptyMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.stream().count()).isEqualTo(4);
    }

    @Test
    void parallelStreamOfEmptyMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        assertThat(matrix.parallelStream().count()).isEqualTo(4);
    }

    @Test
    void equalsOfDoubleMatrixWithRow2Col3() {
        assertThatMatrix(new DoubleMatrix(2, 3))
            .isEqualTo(new DoubleMatrix(2, 3))
            .isNotEqualTo(new DoubleMatrix(3, 2));
    }

    @Test
    void hashCodeOfDoubleMatrixWithRow2Col3() {
        assertThat(new DoubleMatrix(2, 3).hashCode()).isEqualTo(925536);
    }

    @Test
    void toStringOfDoubleMatrixWithRow2Col3() {
        DoubleMatrix matrix = new DoubleMatrix(2, 3);
        assertThatMatrix(matrix).hasToString("2 3: []");
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleMatrix(1), DoubleMatrix.class);
    }

    // endregion
}
