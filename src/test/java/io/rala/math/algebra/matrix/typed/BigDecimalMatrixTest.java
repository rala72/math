package io.rala.math.algebra.matrix.typed;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.exception.NotSupportedException;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import static io.rala.math.testUtils.assertion.MatrixAssertions.assertMatrix;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BigDecimalMatrixTest {
    // region constructors

    @Test
    void constructorWithNegativeSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalMatrix(-1))
            .withMessage(ExceptionMessages.ROWS_COLS_HAVE_TO_BE_GREATER_ZERO);
    }

    @Test
    void constructorWithIntegerMaxValueSize() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(Integer.MAX_VALUE);

        long expectedSize = (long) Integer.MAX_VALUE * Integer.MAX_VALUE;
        assertThat(matrix.size()).isEqualTo(expectedSize);
    }

    @Test
    void constructorWithSize0() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalMatrix(0))
            .withMessage(ExceptionMessages.ROWS_COLS_HAVE_TO_BE_GREATER_ZERO);
    }

    @Test
    void constructorWithSize1() {
        assertMatrix(new BigDecimalMatrix(1), 1);
    }

    @Test
    void constructorWithSize1AndMathContext5() {
        assertMatrix(new BigDecimalMatrix(1, new MathContext(5)), 1);
    }

    @Test
    void constructorWithRows1Cols1() {
        assertMatrix(new BigDecimalMatrix(1, 1), 1, 1);
    }

    @Test
    void constructorWithRows1Cols1AndMathContext5() {
        assertMatrix(new BigDecimalMatrix(1, 1, new MathContext(5)), 1, 1);
    }

    @Test
    void constructorWithRows1Cols2() {
        assertMatrix(new BigDecimalMatrix(1, 2), 1, 2);
    }

    @Test
    void constructorWithMatrix() {
        assertMatrix(new BigDecimalMatrix(new BigDecimalMatrix(1, 2)), 1, 2);
    }

    // endregion

    // region getter and size

    @Test
    void createWithSize1AndAssertSizeEquals1() {
        assertThat(new BigDecimalMatrix(1).size()).isOne();
    }

    @Test
    void createWithSize2AndAssertSizeEquals4() {
        assertThat(new BigDecimalMatrix(2).size()).isEqualTo(4);
    }

    @Test
    void createWithRow1Col2AndAssertSizeEquals2() {
        assertThat(new BigDecimalMatrix(1, 2).size()).isEqualTo(2);
    }

    @Test
    void createWithRow2Col3AndAssertSizeEquals6() {
        assertThat(new BigDecimalMatrix(2, 3).size()).isEqualTo(6);
    }

    // endregion

    // region rows and cols

    @Test
    void getRowFieldsM1OfMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getRowFields(-1))
            .withMessage("row: -1 / 2");
    }

    @Test
    void getRowFields0OfMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i));
        List<Matrix<BigDecimal>.Field> row0 = matrix.getRowFields(0);
        assertThat(row0).hasSize(2);
        for (int i = 0; i < row0.size(); i++) {
            Matrix<BigDecimal>.Field field = row0.get(i);
            assertThat(field.getRow()).isZero();
            assertThat(field.getCol()).isEqualTo(i);
            assertThat(field.getValue()).isEqualTo(BigDecimal.valueOf(i));
        }
    }

    @Test
    void getRow0OfMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i));
        List<BigDecimal> row0 = matrix.getRow(0);
        assertThat(row0).hasSize(2);
        for (int i = 0; i < row0.size(); i++)
            assertThat(row0.get(i)).isEqualTo(BigDecimal.valueOf(i));
    }

    @Test
    void getColFieldsM1OfMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getColFields(-1))
            .withMessage("col: -1 / 2");
    }

    @Test
    void getColFields0OfMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i));
        List<Matrix<BigDecimal>.Field> col0 = matrix.getColFields(0);
        assertThat(col0).hasSize(2);
        for (int i = 0; i < col0.size(); i++) {
            Matrix<BigDecimal>.Field field = col0.get(i);
            assertThat(field.getCol()).isZero();
            assertThat(field.getRow()).isEqualTo(i);
            assertThat(field.getValue()).isEqualTo(BigDecimal.valueOf(i * 2L));
        }
    }

    @Test
    void getCol0OfMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i));
        List<BigDecimal> col0 = matrix.getCol(0);
        assertThat(col0).hasSize(2);
        for (int i = 0; i < col0.size(); i++)
            assertThat(col0.get(i)).isEqualTo(BigDecimal.valueOf(i * 2L));
    }

    // endregion

    // region value

    @Test
    void getValueByIndexMinus1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getValue(-1))
            .withMessage("size: -1 / 4");
    }

    @Test
    void getValueByRowMinus1Col0() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getValue(-1, 0))
            .withMessage("row: -1 / 2");
    }

    @Test
    void getValueByRow0ColMinus1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.getValue(0, -1))
            .withMessage("col: -1 / 2");
    }

    @Test
    void setValueByIndexMinus1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.setValue(-1, BigDecimal.ZERO))
            .withMessage("size: -1 / 4");
    }

    @Test
    void setValueByIndex0WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.setValue(0, BigDecimal.ONE)).isEqualTo(BigDecimal.ZERO);
        assertThat(matrix.getValue(0)).isEqualTo(BigDecimal.ONE);
        assertThat(matrix.getValue(0, 0)).isEqualTo(BigDecimal.ONE);
        // assert all other are unset
    }

    @Test
    void setValueByIndex3WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.setValue(3, BigDecimal.ONE)).isEqualTo(BigDecimal.ZERO);
        assertThat(matrix.getValue(3)).isEqualTo(BigDecimal.ONE);
        assertThat(matrix.getValue(1, 1)).isEqualTo(BigDecimal.ONE);
        // assert all other are unset
    }

    @Test
    void setValueByRowMinus1Col0() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.setValue(-1, 0, BigDecimal.ZERO))
            .withMessage("row: -1 / 2");
    }

    @Test
    void setValueByRow0ColMinus1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.setValue(0, -1, BigDecimal.ZERO))
            .withMessage("col: -1 / 2");
    }

    @Test
    void setValueByRow0Col0WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.setValue(0, 0, BigDecimal.ONE)).isEqualTo(BigDecimal.ZERO);
        assertThat(matrix.getValue(0)).isEqualTo(BigDecimal.ONE);
        assertThat(matrix.getValue(0, 0)).isEqualTo(BigDecimal.ONE);
        // assert all other are unset
    }

    @Test
    void setValueByRow1Col0WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.setValue(1, 0, BigDecimal.ONE)).isEqualTo(BigDecimal.ZERO);
        assertThat(matrix.getValue(1, 0)).isEqualTo(BigDecimal.ONE);
        assertThat(matrix.getValue(2)).isEqualTo(BigDecimal.ONE);
        // assert all other are unset
    }

    @Test
    void setValueByIndex2WichWasSet() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        matrix.setValue(2, BigDecimal.ONE);
        assertThat(matrix.getValue(2)).isEqualTo(BigDecimal.ONE);
        assertThat(matrix.setValue(2, BigDecimal.valueOf(2))).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void removeValueByIndexMinus1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.removeValue(-1))
            .withMessage("size: -1 / 4");
    }

    @Test
    void removeValueByIndex0WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.removeValue(0)).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void removeValueByRowMinus1Col0() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.removeValue(-1, 0))
            .withMessage("row: -1 / 2");
    }

    @Test
    void removeValueByRow0ColMinus1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> matrix.removeValue(0, -1))
            .withMessage("col: -1 / 2");
    }

    @Test
    void removeValueByRow0Col0WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.removeValue(0, 0)).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void removeValueByIndex2WhichWasSet() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        matrix.setValue(2, BigDecimal.ONE);
        assertThat(matrix.removeValue(2)).isEqualTo(BigDecimal.ONE);
    }

    // endregion

    // region compute

    @Test
    void computeWithUnaryOperatorByIndex0WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.compute(0, number -> BigDecimal.ONE)).isEqualTo(BigDecimal.ZERO);
        assertThat(matrix.getValue(0)).isEqualTo(BigDecimal.ONE);
        assertThat(matrix.getValue(0, 0)).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void computeWithUnaryOperatorByIndex3WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.compute(3, number -> BigDecimal.ONE)).isEqualTo(BigDecimal.ZERO);
        assertThat(matrix.getValue(3)).isEqualTo(BigDecimal.ONE);
        assertThat(matrix.getValue(1, 1)).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void computeWithUnaryOperatorByRow0Col0WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.compute(0, 0, number -> BigDecimal.ONE)).isEqualTo(BigDecimal.ZERO);
        assertThat(matrix.getValue(0)).isEqualTo(BigDecimal.ONE);
        assertThat(matrix.getValue(0, 0)).isEqualTo(BigDecimal.ONE);
        // assert all other are unset
    }

    @Test
    void computeWithUnaryOperatorByRow1Col1WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.compute(1, 0, number -> BigDecimal.ONE)).isEqualTo(BigDecimal.ZERO);
        assertThat(matrix.getValue(1, 0)).isEqualTo(BigDecimal.ONE);
        assertThat(matrix.getValue(2)).isEqualTo(BigDecimal.ONE);
        // assert all other are unset
    }

    @Test
    void computeWithBinaryOperatorByIndex0WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.compute(0,
            BigDecimal.ONE, matrix.getArithmetic()::sum
        )).isEqualTo(BigDecimal.ZERO);
        assertThat(matrix.getValue(0)).isEqualTo(BigDecimal.ONE);
        assertThat(matrix.getValue(0, 0)).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void computeWithBinaryOperatorByIndex3WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.compute(3,
            BigDecimal.ONE, matrix.getArithmetic()::sum
        )).isEqualTo(BigDecimal.ZERO);
        assertThat(matrix.getValue(3)).isEqualTo(BigDecimal.ONE);
        assertThat(matrix.getValue(1, 1)).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void computeWithBinaryOperatorByRow0Col0WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.compute(0, 0,
            BigDecimal.ONE, matrix.getArithmetic()::sum
        )).isEqualTo(BigDecimal.ZERO);
        assertThat(matrix.getValue(0)).isEqualTo(BigDecimal.ONE);
        assertThat(matrix.getValue(0, 0)).isEqualTo(BigDecimal.ONE);
        // assert all other are unset
    }

    @Test
    void computeWithBinaryOperatorByRow1Col1WhichWasUnset() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.compute(1, 0,
            BigDecimal.ONE, matrix.getArithmetic()::sum
        )).isEqualTo(BigDecimal.ZERO);
        assertThat(matrix.getValue(1, 0)).isEqualTo(BigDecimal.ONE);
        assertThat(matrix.getValue(2)).isEqualTo(BigDecimal.ONE);
        // assert all other are unset
    }

    @Test
    void computeAllWithFunctionOfEmptyMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        matrix.computeAll(field ->
            matrix.getArithmetic().sum(field.getValue(), BigDecimal.ONE)
        );
        assertThat(matrix.stream().allMatch(field ->
            matrix.getArithmetic().isEqual(field.getValue(), BigDecimal.ONE)
        )).isTrue();
    }

    @Test
    void computeAllWithFunctionAndBinaryOperatorOfEmptyMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        matrix.computeAll(field -> BigDecimal.ONE, matrix.getArithmetic()::sum);
        assertThat(matrix.stream().allMatch(field ->
            matrix.getArithmetic().isEqual(field.getValue(), BigDecimal.ONE)
        )).isTrue();
    }

    // endregion

    // region isSquare, isDiagonal and isInvertible

    @Test
    void isSquareOfMatrixWithRow1Col2() {
        assertThat(new BigDecimalMatrix(1, 2).isSquare()).isFalse();
    }

    @Test
    void isSquareOfMatrixWithSize2() {
        assertThat(new BigDecimalMatrix(2).isSquare()).isTrue();
    }

    @Test
    void isDiagonalOfMatrixWithRow1Col2() {
        assertThat(new BigDecimalMatrix(1, 2).isDiagonal()).isFalse();
    }

    @Test
    void isDiagonalOfMatrixOfValidValues() {
        assertThat(BigDecimalMatrix.ofValuesByRows(2,
            BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.valueOf(4)
        ).isDiagonal()).isTrue();
    }

    @Test
    void isDiagonalOfMatrixOfInvalidValues() {
        assertThat(BigDecimalMatrix.ofValuesByRows(2,
            BigDecimal.ONE, BigDecimal.valueOf(2),
            BigDecimal.valueOf(3), BigDecimal.valueOf(4)
        ).isDiagonal()).isFalse();
    }

    @Test
    void isDiagonalOfDiagonalMatrix() {
        assertThat(BigDecimalMatrix.diagonal(
            BigDecimal.ONE, BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        ).isDiagonal()).isTrue();
    }

    @Test
    void isInvertibleOfMatrixWithSize2AndDeterminante0() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        matrix.setValue(0, BigDecimal.ONE);
        matrix.setValue(1, BigDecimal.valueOf(2));
        matrix.setValue(2, BigDecimal.valueOf(2));
        matrix.setValue(3, BigDecimal.valueOf(4));
        assertThat(matrix.isSquare()).isTrue();
        assertThat(matrix.getArithmetic().isZero(matrix.determinante())).isTrue();
        assertThat(matrix.isInvertible()).isFalse();
    }

    @Test
    void isInvertibleOfMatrixWithSize2AndDeterminanteNon0() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        matrix.setValue(0, BigDecimal.ONE);
        matrix.setValue(1, BigDecimal.ZERO);
        matrix.setValue(2, BigDecimal.ZERO);
        matrix.setValue(3, BigDecimal.valueOf(4));
        assertThat(matrix.isSquare()).isTrue();
        assertThat(matrix.getArithmetic().isZero(matrix.determinante())).isFalse();
        assertThat(matrix.isInvertible()).isTrue();
    }

    // endregion

    // region add and multiply

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows2Cols1() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalMatrix(1)
                .add(new BigDecimalMatrix(2, 1)))
            .withMessage(ExceptionMessages.ROWS_HAVE_TO_BE_EQUAL);
    }

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows1Cols2() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalMatrix(1)
                .add(new BigDecimalMatrix(1, 2)))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL);
    }

    @Test
    void addOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(2);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, BigDecimal.ZERO);
        assertThat(matrix1.add(matrix2)).isEqualTo(result);
    }

    @Test
    void addOfMatrixWithSize2ToItself() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
            result.setValue(i, BigDecimal.valueOf(2)
                .multiply((BigDecimal.valueOf(i).add(BigDecimal.ONE)))
            );
        }
        assertThat(matrix.add(matrix)).isEqualTo(result);
    }

    @Test
    void multiplyOfEmptyMatrixWithSize2With2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        assertThat(matrix.multiply(BigDecimal.valueOf(2))).isEqualTo(result);
    }

    @Test
    void multiplyMatrixWithSize2With2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < result.size(); i++) {
            matrix.setValue(i, BigDecimal.valueOf(i + 1));
            result.setValue(i, BigDecimal.valueOf((i + 1) * 2L));
        }
        assertThat(matrix.multiply(BigDecimal.valueOf(2))).isEqualTo(result);
    }

    @Test
    void multiplyOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(2);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, BigDecimal.ZERO);
        assertThat(matrix1.multiply(matrix2)).isEqualTo(result);
    }

    @Test
    void multiplyOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(1, 2);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(2, 3);
        BigDecimalMatrix result = new BigDecimalMatrix(1, 3);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, BigDecimal.ZERO);
        assertThat(matrix1.multiply(matrix2)).isEqualTo(result);
    }

    @Test
    void multiplyOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(2, 3);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(1, 2);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> matrix1.multiply(matrix2))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL_ROWS);
    }

    @Test
    void multiplyOfMatrixWithSize2ToItself() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
        assertThat(matrix.multiply(matrix)).isEqualTo(BigDecimalMatrix.ofValuesByRows(2,
            BigDecimal.valueOf(7), BigDecimal.TEN,
            BigDecimal.valueOf(15), BigDecimal.valueOf(22)
        ));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(1, 2);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(2, 3);
        BigDecimalMatrix result = new BigDecimalMatrix(1, 3);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, BigDecimal.ZERO);
        assertThat(matrix1.multiplyTolerant(matrix2)).isEqualTo(result);
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(2, 3);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(1, 2);
        BigDecimalMatrix result = new BigDecimalMatrix(1, 3);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, BigDecimal.ZERO);
        assertThat(matrix1.multiplyTolerant(matrix2)).isEqualTo(result);
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow3Col4() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(1, 2);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(3, 4);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> matrix1.multiplyTolerant(matrix2));
    }

    @Test
    void multiplyTolerantOfMatrixWithSize2ToItself() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
        assertThat(matrix.multiplyTolerant(matrix)).isEqualTo(BigDecimalMatrix.ofValuesByRows(2,
            BigDecimal.valueOf(7), BigDecimal.TEN,
            BigDecimal.valueOf(15), BigDecimal.valueOf(22)
        ));
    }

    // endregion

    // region inverse, transpose and determinante

    @Test
    void inverseOfEmptyMatrixWichIsNoSquare() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new BigDecimalMatrix(1, 2).inverse())
            .withMessage(ExceptionMessages.MATRIX_HAS_TO_BE_SQUARE);
    }

    @Test
    void inverseOfEmptyMatrixWithSize2() {
        assertThat(new BigDecimalMatrix(2).inverse()).isNull();
    }

    @Test
    void inverseOfMatrixWithSize2() {
        BigDecimalMatrix matrix = BigDecimalMatrix.ofValuesByRows(2,
            BigDecimal.valueOf(2), BigDecimal.valueOf(5),
            BigDecimal.ONE, BigDecimal.valueOf(3)
        );
        BigDecimalMatrix result = BigDecimalMatrix.ofValuesByRows(2,
            BigDecimal.valueOf(3), BigDecimal.valueOf(5).negate(),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(2)
        );
        assertThat(matrix.inverse()).isEqualTo(result);
    }

    @Test
    void inverseOfMatrixWithSize3() {
        BigDecimalMatrix matrix = BigDecimalMatrix.ofValuesByRows(3,
            BigDecimal.valueOf(3), BigDecimal.valueOf(5), BigDecimal.ONE,
            BigDecimal.valueOf(2), BigDecimal.valueOf(4), BigDecimal.valueOf(5),
            BigDecimal.ONE, BigDecimal.valueOf(2), BigDecimal.valueOf(2)
        );
        BigDecimalMatrix result = BigDecimalMatrix.ofValuesByRows(3,
            BigDecimal.valueOf(2), BigDecimal.valueOf(8), BigDecimal.valueOf(21).negate(),
            BigDecimal.ONE.negate(), BigDecimal.valueOf(5).negate(), BigDecimal.valueOf(13),
            BigDecimal.ZERO, BigDecimal.valueOf(1), BigDecimal.valueOf(2).negate()
        );
        assertThat(matrix.inverse()).isEqualTo(result);
    }

    @Test
    void transposeOfEmptyMatrixWithSize2() {
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, BigDecimal.ZERO);
        assertThat(new BigDecimalMatrix(2).transpose()).isEqualTo(result);
    }

    @Test
    void transposeOfMatrixWithSize2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++) {
                int i = (int) TestMatrix.getIndexOfRowAndCol(matrix, r, c);
                matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
                result.setValue(TestMatrix.getIndexOfRowAndCol(result, c, r),
                    BigDecimal.valueOf(i).add(BigDecimal.ONE)
                );
            }
        assertThat(matrix.transpose()).isEqualTo(result);
    }

    @Test
    void determinanteOfEmptyMatrixWithSize2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.determinante()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void determinanteOfMatrixWithSize2AndValues1234() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
        assertThat(matrix.determinante()).isEqualTo(BigDecimal.valueOf(-2));
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues123456789() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(3);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
        assertThat(matrix.determinante()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues1And2OnDiagonale() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(3);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? BigDecimal.valueOf(2) : BigDecimal.ONE);
        assertThat(matrix.determinante()).isEqualTo(BigDecimal.valueOf(4));
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1And2OnDiagonale() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? BigDecimal.valueOf(2) : BigDecimal.ONE);
        assertThat(matrix.determinante()).isEqualTo(BigDecimal.valueOf(5));
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1AndMore0InRow() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c,
                    c != 0 && (r == c || r == 0) ?
                        BigDecimal.ZERO : BigDecimal.ONE
                );
        assertThat(matrix.determinante()).isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1AndMore0InCol() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c,
                    r != 0 && (r == c || c == 0) ?
                        BigDecimal.ZERO : BigDecimal.ONE
                );
        assertThat(matrix.determinante()).isEqualTo(BigDecimal.valueOf(2));
    }

    // endregion

    // region static: identity and diagonal

    @Test
    void identityOfSize1() {
        BigDecimalMatrix matrix = BigDecimalMatrix.identity(1);
        assertThat(matrix.size()).isOne();
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertThat(matrix.getValue(i, i)).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void identityOfSize2() {
        BigDecimalMatrix matrix = BigDecimalMatrix.identity(2);
        assertThat(matrix.size()).isEqualTo(2 * 2);
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertThat(matrix.getValue(i, i)).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void diagonalOfSize1() {
        BigDecimalMatrix matrix = BigDecimalMatrix.diagonal(BigDecimal.ONE);
        assertThat(matrix.size()).isOne();
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertThat(matrix.getValue(i, i)).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void diagonalOfSize2() {
        BigDecimalMatrix matrix = BigDecimalMatrix.diagonal(
            BigDecimal.valueOf(2), BigDecimal.valueOf(2)
        );
        assertThat(matrix.size()).isEqualTo(2 * 2);
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertThat(matrix.getValue(i, i)).isEqualTo(BigDecimal.valueOf(2));
    }

    // endregion

    // region static: of

    @Test
    void ofValuesByRows2WithInvalidParamCount() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> BigDecimalMatrix.ofValuesByRows(2, BigDecimal.ONE));
    }

    @Test
    void ofValuesByRows2WithValidParamCount() {
        BigDecimalMatrix matrix = BigDecimalMatrix.ofValuesByRows(2,
            BigDecimal.ONE, BigDecimal.valueOf(2)
        );
        for (int i = 0; i < matrix.size(); i++)
            assertThat(matrix.getValue(i)).as("index: " + i)
                .isEqualTo(BigDecimal.valueOf(i).add(BigDecimal.ONE));
    }

    @Test
    void ofValuesByCols2WithInvalidParamCount() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> BigDecimalMatrix.ofValuesByCols(2, BigDecimal.ONE));
    }

    @Test
    void ofValuesByCols2WithValidParamCount() {
        BigDecimalMatrix matrix = BigDecimalMatrix.ofValuesByCols(2,
            BigDecimal.ONE, BigDecimal.valueOf(2)
        );
        for (int i = 0; i < matrix.size(); i++)
            assertThat(matrix.getValue(i)).as("index: " + i)
                .isEqualTo(BigDecimal.valueOf(i).add(BigDecimal.ONE));
    }

    // endregion

    // region map and copy

    @Test
    void mapOfMatrixWithSize2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Matrix<Integer> result =
            new Matrix<>(new IntegerArithmetic(), 2);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++) {
                matrix.setValue(r, c, BigDecimal.valueOf(r + c + 0.5));
                result.setValue(r, c, r + c);
            }

        assertThat(matrix.map(new IntegerArithmetic(), Number::intValue)).isEqualTo(result);
    }

    @Test
    void copyOfMatrixWithSize2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.copy()).isEqualTo(matrix);
    }

    // endregion

    // region override

    @Test
    void iteratorOfEmptyMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        List<BigDecimalMatrix.Field> values = new ArrayList<>();
        for (BigDecimalMatrix.Field d : matrix) {
            values.add(d);
            assertThat(d.getValue()).isEqualTo(BigDecimal.ZERO);
        }
        assertThat(values).hasSize((int) matrix.size());
    }

    @Test
    void streamOfEmptyMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.stream().count()).isEqualTo(4);
    }

    @Test
    void parallelStreamOfEmptyMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        assertThat(matrix.parallelStream().count()).isEqualTo(4);
    }

    @Test
    void equalsOfBigDecimalMatrixWithRow2Col3() {
        assertThat(new BigDecimalMatrix(2, 3))
            .isEqualTo(new BigDecimalMatrix(2, 3))
            .isNotEqualTo(new BigDecimalMatrix(3, 2));
    }

    @Test
    void hashCodeOfBigDecimalMatrixWithRow2Col3() {
        assertThat(new BigDecimalMatrix(2, 3).hashCode()).isEqualTo(925536);
    }

    @Test
    void toStringOfBigDecimalMatrixWithRow2Col3() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2, 3);
        assertThat(matrix).hasToString("2 3: []");
    }

    @Test
    void serializable() {
        assertSerializable(new BigDecimalMatrix(1), BigDecimalMatrix.class);
    }

    // endregion
}
