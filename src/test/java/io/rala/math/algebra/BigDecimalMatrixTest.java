package io.rala.math.algebra;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

class BigDecimalMatrixTest {
    // region constructors and newInstance

    @Test
    void constructorWithSize0() {
        assertMatrix(new BigDecimalMatrix(0), 0);
    }

    @Test
    void constructorWithSize1() {
        assertMatrix(new BigDecimalMatrix(1), 1);
    }

    @Test
    void constructorWithSize0AndMathContext5() {
        assertMatrix(new BigDecimalMatrix(0, new MathContext(5)), 0);
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

    @Test
    void newInstanceOfMatrixWithSize3() {
        assertMatrix(new BigDecimalMatrix(1).newInstance(3), 3);
    }

    @Test
    void newInstanceOfMatrixWithRows1Cols2() {
        assertMatrix(new BigDecimalMatrix(2).newInstance(1, 2), 1, 2);
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
        Assertions.assertNull(matrix.setValue(0, BigDecimal.ONE));
        Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(0));
        Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByIndex3WhichWasEmpty() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertNull(matrix.setValue(3, BigDecimal.ONE));
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
        Assertions.assertNull(matrix.setValue(0, 0, BigDecimal.ONE));
        Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(0));
        Assertions.assertEquals(BigDecimal.ONE, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByRow1Col0WhichWasEmpty() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertNull(matrix.setValue(1, 0, BigDecimal.ONE));
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
        Assertions.assertNull(matrix.removeValue(0));
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
        Assertions.assertNull(matrix.removeValue(0, 0));
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

    // region matrix arithmetic

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows2Cols1() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalMatrix(1)
                .add(new BigDecimalMatrix(2, 1))
        ); // assert message
    }

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows1Cols2() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalMatrix(1)
                .add(new BigDecimalMatrix(1, 2))
        ); // assert message
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
            result.setValue(i, BigDecimal.valueOf(2).multiply((BigDecimal.valueOf(i).add(BigDecimal.ONE))));
        }
        Assertions.assertEquals(result, matrix.add(matrix));
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
        ); // assert message
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
                int i = matrix.getIndexOfRowAndCol(r, c);
                matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
                result.setValue(result.getIndexOfRowAndCol(c, r),
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
                matrix.setValue(r, c, c != 0 && (r == c || r == 0) ? BigDecimal.ZERO : BigDecimal.ONE);
        Assertions.assertEquals(BigDecimal.valueOf(2), matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1AndMore0InCol() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r != 0 && (r == c || c == 0) ? BigDecimal.ZERO : BigDecimal.ONE);
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

    // region override

    @Test
    void copyOfMatrixWithSize2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertEquals(matrix, matrix.copy());
    }

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

    // endregion

    // region protected: modify

    @Test
    void flipRowsOfMatrixWithSize2UsingInvalidRow1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.flipRows(-1, 0)
        ); // assert message
    }

    @Test
    void flipRowsOfMatrixWithSize2UsingInvalidRow2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.flipRows(0, -1)
        ); // assert message
    }

    @Test
    void flipRowsOfMatrixWithSize2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, BigDecimal.valueOf(i + 1));
            result.setValue(
                (i + result.getCols()) % result.size(),
                BigDecimal.valueOf(i).add(BigDecimal.ONE)
            );
        }
        Assertions.assertEquals(result, matrix.flipRows(0, 1));
    }

    @Test
    void flipColsOfMatrixWithSize2UsingInvalidCol1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.flipCols(-1, 0)
        ); // assert message
    }

    @Test
    void flipColsOfMatrixWithSize2UsingInvalidCol2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.flipCols(0, -1)
        ); // assert message
    }

    @Test
    void flipColsOfMatrixWithSize2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
            result.setValue(i + (i % result.getCols() == 0 ? 1 : -1),
                BigDecimal.valueOf(i).add(BigDecimal.ONE)
            );
        }
        Assertions.assertEquals(result, matrix.flipCols(0, 1));
    }

    @Test
    void multiplyRowOfMatrixWithSize2UsingInvalidRow() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.multiplyRow(-1, 0)
        ); // assert message
    }

    @Test
    void multiplyRowOfMatrixWithSize2With2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
            result.setValue(i, BigDecimal.valueOf((i + 1) * (i / result.getCols() == 0 ? 2 : 1)));
        }
        Assertions.assertEquals(result, matrix.multiplyRow(0, 2));
    }

    @Test
    void multiplyColOfMatrixWithSize2UsingInvalidCol() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.multiplyCol(-1, 0)
        ); // assert message
    }

    @Test
    void multiplyColOfMatrixWithSize2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
            result.setValue(i, BigDecimal.valueOf((i + 1) * (i % result.getCols() == 0 ? 2 : 1)));
        }
        Assertions.assertEquals(result, matrix.multiplyCol(0, 2));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2UsingInvalidRow1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addRowMultipleTimes(-1, 0, 0)
        ); // assert message
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2UsingInvalidRow2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addRowMultipleTimes(0, -1, 0)
        ); // assert message
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2ToSameRow() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
            result.setValue(i, BigDecimal.valueOf((i + 1) * (i / result.getCols() == 0 ? 2 : 1)));
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 0, 2));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using0() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, BigDecimal.valueOf(value));
            result.setValue(i, BigDecimal.valueOf(value));
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 1, 0));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, BigDecimal.valueOf(value));
            result.setValue(i, BigDecimal.valueOf(value + (i / result.getCols() == 0 ? 2 : 0)));
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 1, 1));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, BigDecimal.valueOf(value));
            result.setValue(i, BigDecimal.valueOf(value + (i / result.getCols() == 0 ? 4 : 0)));
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 1, 2));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2UsingInvalidCol1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addColMultipleTimes(-1, 0, 1)
        ); // assert message
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2UsingInvalidCol2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addColMultipleTimes(0, -1, 0)
        ); // assert message
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2ToSameRow() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, BigDecimal.valueOf(i).add(BigDecimal.ONE));
            result.setValue(i, BigDecimal.valueOf((i + 1) * (i % result.getCols() == 0 ? 2 : 1)));
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 0, 2));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using0() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, BigDecimal.valueOf(value));
            result.setValue(i, BigDecimal.valueOf(value));
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 1, 0));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using1() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, BigDecimal.valueOf(value));
            result.setValue(i, BigDecimal.valueOf(value + (i % result.getCols() == 0 ? value : 0)));
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 1, 1));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using2() {
        BigDecimalMatrix matrix = new BigDecimalMatrix(2);
        BigDecimalMatrix result = new BigDecimalMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, BigDecimal.valueOf(value));
            result.setValue(i, BigDecimal.valueOf(value + (i % result.getCols() == 0 ? 2 * value : 0)));
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 1, 2));
    }

    // endregion


    // region assert

    private static <T extends Number> void assertMatrix(Matrix<T> matrix, int size) {
        assertMatrix(matrix, size, size);
    }

    private static <T extends Number> void assertMatrix(Matrix<T> matrix, int rows, int cols) {
        Assertions.assertEquals(rows, matrix.getRows(), "rows is invalid");
        Assertions.assertEquals(cols, matrix.getCols(), "cols is invalid");
    }

    // endregion
}
