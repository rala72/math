package io.rala.math.algebra.matrix.typed;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import static io.rala.math.testUtils.assertion.MatrixAssertions.assertMatrix;

class BigDecimalMatrixTest {
    // region constructors

    @Test
    void constructorWithNegativeSize() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalMatrix(-1)
        ); // assert exception message?
    }

    @Test
    void constructorWithIntegerMaxValueSize() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(Integer.MAX_VALUE);

        long expectedSize = (long) Integer.MAX_VALUE * Integer.MAX_VALUE;
        Assertions.assertEquals(expectedSize, matrix.size());
    }

    @Test
    void constructorWithSize0() {
        assertMatrix(new BigDecimalMatrix(1), 1);
    }

    @Test
    void constructorWithSize1() {
        assertMatrix(new BigDecimalMatrix(1), 1);
    }

    @Test
    void constructorWithSize0AndMathContext5() {
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
        Assertions.assertEquals(1, new BigDecimalMatrix(1).size());
    }

    @Test
    void createWithSize2AndAssertSizeEquals4() {
        Assertions.assertEquals(4, new BigDecimalMatrix(2).size());
    }

    @Test
    void createWithRow1Col2AndAssertSizeEquals2() {
        Assertions.assertEquals(2, new BigDecimalMatrix(1, 2).size());
    }

    @Test
    void createWithRow2Col3AndAssertSizeEquals6() {
        Assertions.assertEquals(6, new BigDecimalMatrix(2, 3).size());
    }

    // endregion

    // region rows and cols

    @Test
    void getRowFieldsM1OfMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getRowFields(-1)
        ); // assert exception message?
    }

    @Test
    void getRowFields0OfMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i));
        List<Matrix<BigDecimal>.Field> row0 = matrix.getRowFields(0);
        Assertions.assertEquals(2, row0.size());
        for (int i = 0; i < row0.size(); i++) {
            Matrix<BigDecimal>.Field field = row0.get(i);
            Assertions.assertEquals(0, field.getRow());
            Assertions.assertEquals(i, field.getCol());
            Assertions.assertEquals(BigDecimal.valueOf(i), field.getValue());
        }
    }

    @Test
    void getRow0OfMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i));
        List<BigDecimal> row0 = matrix.getRow(0);
        Assertions.assertEquals(2, row0.size());
        for (int i = 0; i < row0.size(); i++)
            Assertions.assertEquals(BigDecimal.valueOf(i), row0.get(i));
    }

    @Test
    void getColFieldsM1OfMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getColFields(-1)
        ); // assert exception message?
    }

    @Test
    void getColFields0OfMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i));
        List<Matrix<BigDecimal>.Field> col0 = matrix.getColFields(0);
        Assertions.assertEquals(2, col0.size());
        for (int i = 0; i < col0.size(); i++) {
            Matrix<BigDecimal>.Field field = col0.get(i);
            Assertions.assertEquals(0, field.getCol());
            Assertions.assertEquals(i, field.getRow());
            Assertions.assertEquals(BigDecimal.valueOf(i * 2), field.getValue());
        }
    }

    @Test
    void getCol0OfMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i));
        List<BigDecimal> col0 = matrix.getCol(0);
        Assertions.assertEquals(2, col0.size());
        for (int i = 0; i < col0.size(); i++)
            Assertions.assertEquals(BigDecimal.valueOf(i * 2), col0.get(i));
    }

    // endregion

    // region value

    @Test
    void setValueByIndexMinus1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.setValue(-1, BigDecimal.ZERO)
        ); // assert exception message?
    }

    @Test
    void setValueByIndex0WhichWasEmpty() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertEquals(BigDecimal.ZERO, matrix.setValue(0, BigDecimal.ONE));
        Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(0));
        Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByIndex3WhichWasEmpty() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertEquals(BigDecimal.ZERO, matrix.setValue(3, BigDecimal.ONE));
        Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(3));
        Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(1, 1));
        // assert all other are unset
    }

    @Test
    void setValueByRowMinus1Col0() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.setValue(-1, 0, BigDecimal.ZERO)
        ); // assert exception message?
    }

    @Test
    void setValueByRow0ColMinus1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.setValue(0, -1, BigDecimal.ZERO)
        ); // assert exception message?
    }

    @Test
    void setValueByRow0Col0WhichWasEmpty() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertEquals(BigDecimal.ZERO, matrix.setValue(0, 0, BigDecimal.ONE));
        Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(0));
        Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByRow1Col0WhichWasEmpty() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertEquals(BigDecimal.ZERO, matrix.setValue(1, 0, BigDecimal.ONE));
        Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(1, 0));
        Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(2));
        // assert all other are unset
    }

    @Test
    void getValueByIndexMinus1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getValue(-1)
        ); // assert exception message?
    }

    @Test
    void getValueByRowMinus1Col0() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getValue(-1, 0)
        ); // assert exception message?
    }

    @Test
    void getValueByRow0ColMinus1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getValue(0, -1)
        ); // assert exception message?
    }

    @Test
    void removeValueByIndexMinus1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.removeValue(-1)
        ); // assert exception message?
    }

    @Test
    void removeValueByIndex0WhichWasEmpty() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertEquals(BigDecimal.ZERO, matrix.removeValue(0));
    }

    @Test
    void removeValueByRowMinus1Col0() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.removeValue(-1, 0)
        ); // assert exception message?
    }

    @Test
    void removeValueByRow0ColMinus1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.removeValue(0, -1)
        ); // assert exception message?
    }

    @Test
    void removeValueByRow0Col0WhichWasEmpty() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertEquals(BigDecimal.ZERO, matrix.removeValue(0, 0));
    }

    @Test
    void removeValueByIndex2WhichWasNonEmpty() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        matrix.setValue(2, BigDecimal.ONE);
        Assertions.assertEquals(BigDecimal.ONE, matrix.removeValue(2));
    }

    // endregion

    // region isSquare and isDiagonal

    @Test
    void isSquareOfMatrixWithRow1Col2() {
        Assertions.assertFalse(new BigDecimalMatrix(1, 2).isDiagonal());
    }

    @Test
    void isSquareOfMatrixWithSize2() {
        Assertions.assertTrue(new BigDecimalMatrix(2).isDiagonal());
    }

    @Test
    void isDiagonalOfMatrixWithRow1Col2() {
        Assertions.assertFalse(new BigDecimalMatrix(1, 2).isDiagonal());
    }

    @Test
    void isDiagonalOfMatrixOfValidValues() {
        Assertions.assertTrue(BigDecimalMatrix.ofValuesByRows(2,
            BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.valueOf(4)
        ).isDiagonal());
    }

    @Test
    void isDiagonalOfMatrixOfInvalidValues() {
        Assertions.assertFalse(BigDecimalMatrix.ofValuesByRows(2,
            BigDecimal.ONE, BigDecimal.valueOf(2),
            BigDecimal.valueOf(3), BigDecimal.valueOf(4)
        ).isDiagonal());
    }

    @Test
    void isDiagonalOfDiagonalMatrix() {
        Assertions.assertTrue(BigDecimalMatrix.diagonal(
            BigDecimal.ONE, BigDecimal.valueOf(2), BigDecimal.valueOf(3)
        ).isDiagonal());
    }

    // endregion

    // region matrix arithmetic: add and multiply

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows2Cols1() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalMatrix(1)
                .add(new BigDecimalMatrix(2, 1))
        ); // assert exception message?
    }

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows1Cols2() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalMatrix(1)
                .add(new BigDecimalMatrix(1, 2))
        ); // assert exception message?
    }

    @Test
    void addOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(2);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, BigDecimal.ZERO);
        Assertions.assertEquals(result, matrix1.add(matrix2));
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
        Assertions.assertEquals(result, matrix.add(matrix));
    }

    @Test
    void multiplyOfEmptyMatrixWithSize2With2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        Assertions.assertEquals(result, matrix.multiply(BigDecimal.valueOf(2)));
    }

    @Test
    void multiplyMatrixWithSize2With2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < result.size(); i++) {
            matrix.setValue(i, BigDecimal.valueOf(i + 1));
            result.setValue(i, BigDecimal.valueOf((i + 1) * 2));
        }
        Assertions.assertEquals(result, matrix.multiply(BigDecimal.valueOf(2)));
    }

    @Test
    void multiplyOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(2);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, BigDecimal.ZERO);
        Assertions.assertEquals(result, matrix1.multiply(matrix2));
    }

    @Test
    void multiplyOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(1, 2);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(2, 3);
        BigDecimalMatrix result = new BigDecimalMatrix(1, 3);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, BigDecimal.ZERO);
        Assertions.assertEquals(result, matrix1.multiply(matrix2));
    }

    @Test
    void multiplyOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(2, 3);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(1, 2);
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> matrix1.multiply(matrix2)
        ); // assert exception message?
    }

    @Test
    void multiplyOfMatrixWithSize2ToItself() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
        Assertions.assertEquals(BigDecimalMatrix.ofValuesByRows(2,
            BigDecimal.valueOf(7), BigDecimal.TEN,
            BigDecimal.valueOf(15), BigDecimal.valueOf(22)
        ), matrix.multiply(matrix));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(1, 2);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(2, 3);
        BigDecimalMatrix result = new BigDecimalMatrix(1, 3);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, BigDecimal.ZERO);
        Assertions.assertEquals(result, matrix1.multiplyTolerant(matrix2));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(2, 3);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(1, 2);
        BigDecimalMatrix result = new BigDecimalMatrix(1, 3);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, BigDecimal.ZERO);
        Assertions.assertEquals(result, matrix1.multiplyTolerant(matrix2));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow3Col4() {
        BigDecimalMatrix matrix1 = new BigDecimalMatrix(1, 2);
        BigDecimalMatrix matrix2 = new BigDecimalMatrix(3, 4);
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> matrix1.multiplyTolerant(matrix2)
        );
    }

    @Test
    void multiplyTolerantOfMatrixWithSize2ToItself() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
        Assertions.assertEquals(
            BigDecimalMatrix.ofValuesByRows(2,
                BigDecimal.valueOf(7), BigDecimal.TEN,
                BigDecimal.valueOf(15), BigDecimal.valueOf(22)
            ),
            matrix.multiplyTolerant(matrix)
        );
    }

    // endregion

    // region matrix arithmetic: inverse, transpose and determinante

    @Test
    void inverseOfEmptyMatrixWichIsNoSquare() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalMatrix(1, 2).inverse()
        ); // assert exception message?
    }

    @Test
    void inverseOfEmptyMatrixWithSize2() {
        Assertions.assertNull(new BigDecimalMatrix(2).inverse());
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
        Assertions.assertEquals(result, matrix.inverse());
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
        Assertions.assertEquals(result, matrix.inverse());
    }

    @Test
    void transposeOfEmptyMatrixWithSize2() {
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, BigDecimal.ZERO);
        Assertions.assertEquals(result, new BigDecimalMatrix(2).transpose());
    }

    @Test
    void transposeOfMatrixWithSize2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int r = 0; r < matrix.getRows(); r++) {
            for (int c = 0; c < matrix.getCols(); c++) {
                int i = (int) TestMatrix.getIndexOfRowAndCol(matrix, r, c);
                matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
                result.setValue(TestMatrix.getIndexOfRowAndCol(result, c, r),
                    BigDecimal.valueOf(i).add(BigDecimal.ONE)
                );
            }
        }
        Assertions.assertEquals(result, matrix.transpose());
    }

    @Test
    void determinanteOfEmptyMatrixWithSize2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertEquals(BigDecimal.ZERO, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize2AndValues1234() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
        Assertions.assertEquals(BigDecimal.valueOf(-2), matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues123456789() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(3);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
        Assertions.assertEquals(BigDecimal.ZERO, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues1And2OnDiagonale() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(3);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? BigDecimal.valueOf(2) : BigDecimal.ONE);
        Assertions.assertEquals(BigDecimal.valueOf(4), matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1And2OnDiagonale() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? BigDecimal.valueOf(2) : BigDecimal.ONE);
        Assertions.assertEquals(BigDecimal.valueOf(5), matrix.determinante());
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
        Assertions.assertEquals(BigDecimal.valueOf(2), matrix.determinante());
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
        Assertions.assertEquals(BigDecimal.valueOf(2), matrix.determinante());
    }

    // endregion

    // region static: identity and diagonal

    @Test
    void identityOfSize1() {
        BigDecimalMatrix matrix = BigDecimalMatrix.identity(1);
        Assertions.assertEquals(1, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(i, i));
    }

    @Test
    void identityOfSize2() {
        BigDecimalMatrix matrix = BigDecimalMatrix.identity(2);
        Assertions.assertEquals(2 * 2, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(i, i));
    }

    @Test
    void diagonalOfSize1() {
        BigDecimalMatrix matrix = BigDecimalMatrix.diagonal(BigDecimal.ONE);
        Assertions.assertEquals(1, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(i, i));
    }

    @Test
    void diagonalOfSize2() {
        BigDecimalMatrix matrix = BigDecimalMatrix.diagonal(
            BigDecimal.valueOf(2), BigDecimal.valueOf(2)
        );
        Assertions.assertEquals(2 * 2, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            Assertions.assertEquals(BigDecimal.valueOf(2), matrix.getValue(i, i));
    }

    // endregion

    // region static: of

    @Test
    void ofValuesByRows2WithInvalidParamCount() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> BigDecimalMatrix.ofValuesByRows(2, BigDecimal.ONE)
        );
    }

    @Test
    void ofValuesByRows2WithValidParamCount() {
        BigDecimalMatrix matrix = BigDecimalMatrix.ofValuesByRows(2,
            BigDecimal.ONE, BigDecimal.valueOf(2)
        );
        for (int i = 0; i < matrix.size(); i++)
            Assertions.assertEquals(
                BigDecimal.valueOf(i).add(BigDecimal.ONE),
                matrix.getValue(i),
                "index: " + i
            );
    }

    @Test
    void ofValuesByCols2WithInvalidParamCount() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> BigDecimalMatrix.ofValuesByCols(2, BigDecimal.ONE)
        );
    }

    @Test
    void ofValuesByCols2WithValidParamCount() {
        BigDecimalMatrix matrix = BigDecimalMatrix.ofValuesByCols(2,
            BigDecimal.ONE, BigDecimal.valueOf(2)
        );
        for (int i = 0; i < matrix.size(); i++)
            Assertions.assertEquals(
                BigDecimal.valueOf(i).add(BigDecimal.ONE),
                matrix.getValue(i),
                "index: " + i
            );
    }

    // endregion

    // region map and copy

    @Test
    void mapOfMatrixWithSize2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Matrix<Integer> result =
            new Matrix<>(new IntegerArithmetic(), 2, 0);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++) {
                matrix.setValue(r, c, BigDecimal.valueOf(r + c + 0.5));
                result.setValue(r, c, r + c);
            }

        Assertions.assertEquals(result,
            matrix.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void copyOfMatrixWithSize2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertEquals(matrix, matrix.copy());
    }

    // endregion

    // region override

    @Test
    void iteratorOfEmptyMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        List<BigDecimalMatrix.Field> values = new ArrayList<>();
        for (BigDecimalMatrix.Field d : matrix) {
            values.add(d);
            Assertions.assertEquals(BigDecimal.ZERO, d.getValue());
        }
        Assertions.assertEquals(matrix.size(), values.size());
    }

    @Test
    void streamOfEmptyMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertEquals(4, matrix.stream().count());
    }

    @Test
    void parallelStreamOfEmptyMatrix() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertEquals(4, matrix.parallelStream().count());
    }

    @Test
    void equalsOfBigDecimalMatrixWithRow2Col3() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2, 3);
        Assertions.assertEquals(
            matrix,
            new BigDecimalMatrix(2, 3)
        );
        Assertions.assertNotEquals(
            matrix,
            new BigDecimalMatrix(3, 2)
        );
    }

    @Test
    void hashCodeOfBigDecimalMatrixWithRow2Col3() {
        Assertions.assertEquals(
            925536,
            new BigDecimalMatrix(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfBigDecimalMatrixWithRow2Col3() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2, 3);
        Assertions.assertEquals("2 3: []", matrix.toString());
    }

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
            new BigDecimalMatrix(1),
            BigDecimalMatrix.class
        );
    }

    // endregion
}