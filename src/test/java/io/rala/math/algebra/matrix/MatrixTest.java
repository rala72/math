package io.rala.math.algebra.matrix;

import io.rala.math.testUtils.algebra.TestMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MatrixTest {
    // region constructors and newInstance

    @Test
    void constructorWithSize0() {
        assertMatrix(new TestMatrix(0), 0);
    }

    @Test
    void constructorWithSize1() {
        assertMatrix(new TestMatrix(1), 1);
    }

    @Test
    void constructorWithSize2AndDefaultValue() {
        TestMatrix matrix = new TestMatrix(2, 2d);
        assertMatrix(matrix, 2);
        Assertions.assertEquals(2d, matrix.getDefaultValue());
    }

    @Test
    void constructorWithRows1Cols1() {
        assertMatrix(new TestMatrix(1, 1), 1, 1);
    }

    @Test
    void constructorWithRows1Cols2() {
        assertMatrix(new TestMatrix(1, 2), 1, 2);
    }

    @Test
    void constructorWithRows1Cols2AndDefaultValue() {
        TestMatrix matrix = new TestMatrix(1, 2, -2d);
        assertMatrix(matrix, 1, 2);
        Assertions.assertEquals(-2d, matrix.getDefaultValue());
    }

    @Test
    void constructorWithMatrix() {
        assertMatrix(new TestMatrix(new TestMatrix(1, 2)), 1, 2);
    }

    @Test
    void newInstanceOfMatrixWithSize2() {
        assertMatrix(new TestMatrix(1).newInstance(2), 2);
    }

    @Test
    void newInstanceOfMatrixWithRows1Cols2() {
        assertMatrix(new TestMatrix(2).newInstance(1, 2), 1, 2);
    }

    // endregion

    // region getter and size

    @Test
    void createWithSize1AndAssertSizeEquals1() {
        Assertions.assertEquals(1, new TestMatrix(1).size());
    }

    @Test
    void createWithSize2AndAssertSizeEquals4() {
        Assertions.assertEquals(4, new TestMatrix(2).size());
    }

    @Test
    void createWithRow1Col2AndAssertSizeEquals2() {
        Assertions.assertEquals(2, new TestMatrix(1, 2).size());
    }

    @Test
    void createWithRow2Col3AndAssertSizeEquals6() {
        Assertions.assertEquals(6, new TestMatrix(2, 3).size());
    }

    // endregion

    // region value

    @Test
    void setValueByIndexMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.setValue(-1, 0d)
        ); // assert exception message?
    }

    @Test
    void setValueByIndex0WhichWasEmpty() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertNull(matrix.setValue(0, 1));
        Assertions.assertEquals(1, matrix.getValue(0));
        Assertions.assertEquals(1, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByIndex3WhichWasEmpty() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertNull(matrix.setValue(3, 1));
        Assertions.assertEquals(1, matrix.getValue(3));
        Assertions.assertEquals(1, matrix.getValue(1, 1));
        // assert all other are unset
    }

    @Test
    void setValueByRowMinus1Col0() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.setValue(-1, 0, 0d)
        ); // assert exception message?
    }

    @Test
    void setValueByRow0ColMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.setValue(0, -1, 0d)
        ); // assert exception message?
    }

    @Test
    void setValueByRow0Col0WhichWasEmpty() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertNull(matrix.setValue(0, 0, 1));
        Assertions.assertEquals(1, matrix.getValue(0));
        Assertions.assertEquals(1, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByRow1Col0WhichWasEmpty() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertNull(matrix.setValue(1, 0, 1));
        Assertions.assertEquals(1, matrix.getValue(1, 0));
        Assertions.assertEquals(1, matrix.getValue(2));
        // assert all other are unset
    }

    @Test
    void getValueByIndexMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getValue(-1)
        ); // assert exception message?
    }

    @Test
    void getValueByRowMinus1Col0() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getValue(-1, 0)
        ); // assert exception message?
    }

    @Test
    void getValueByRow0ColMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getValue(0, -1)
        ); // assert exception message?
    }

    @Test
    void removeValueByIndexMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.removeValue(-1)
        ); // assert exception message?
    }

    @Test
    void removeValueByIndex0WhichWasEmpty() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertNull(matrix.removeValue(0));
    }

    @Test
    void removeValueByRowMinus1Col0() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.removeValue(-1, 0)
        ); // assert exception message?
    }

    @Test
    void removeValueByRow0ColMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.removeValue(0, -1)
        ); // assert exception message?
    }

    @Test
    void removeValueByRow0Col0WhichWasEmpty() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertNull(matrix.removeValue(0, 0));
    }

    @Test
    void removeValueByIndex2WhichWasNonEmpty() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(2, 1);
        Assertions.assertEquals(1, matrix.removeValue(2));
    }

    // endregion

    // region isSquare and isDiagonal

    @Test
    void isSquareOfMatrixWithRow1Col2() {
        Assertions.assertFalse(new TestMatrix(1, 2).isDiagonal());
    }

    @Test
    void isSquareOfMatrixWithSize2() {
        Assertions.assertTrue(new TestMatrix(2).isDiagonal());
    }

    @Test
    void isDiagonalOfMatrixWithRow1Col2() {
        Assertions.assertFalse(new TestMatrix(1, 2).isDiagonal());
    }

    @Test
    void isDiagonalOfMatrixOfValidValues() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(0, 1);
        matrix.setValue(1, 0);
        matrix.setValue(2, 0);
        matrix.setValue(3, 4);
        Assertions.assertTrue(matrix.isDiagonal());
    }

    @Test
    void isDiagonalOfMatrixOfInvalidValues() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(0, 1);
        matrix.setValue(1, 2);
        matrix.setValue(2, 3);
        matrix.setValue(3, 4);
        Assertions.assertFalse(matrix.isDiagonal());
    }

    // endregion

    // region matrix arithmetic: add and multiply

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows2Cols1() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new DoubleMatrix(1)
                .add(new DoubleMatrix(2, 1))
        ); // assert exception message?
    }

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows1Cols2() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new DoubleMatrix(1)
                .add(new DoubleMatrix(1, 2))
        ); // assert exception message?
    }

    @Test
    void addOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        DoubleMatrix matrix1 = new DoubleMatrix(2);
        DoubleMatrix matrix2 = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, 0d);
        Assertions.assertEquals(result, matrix1.add(matrix2));
    }

    @Test
    void addOfMatrixWithSize2ToItself() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, 2 * (i + 1d));
        }
        Assertions.assertEquals(result, matrix.add(matrix));
    }

    @Test
    void multiplyOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        DoubleMatrix matrix1 = new DoubleMatrix(2);
        DoubleMatrix matrix2 = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, 0d);
        Assertions.assertEquals(result, matrix1.multiply(matrix2));
    }

    @Test
    void multiplyOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        DoubleMatrix matrix1 = new DoubleMatrix(1, 2);
        DoubleMatrix matrix2 = new DoubleMatrix(2, 3);
        DoubleMatrix result = new DoubleMatrix(1, 3);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, 0d);
        Assertions.assertEquals(result, matrix1.multiply(matrix2));
    }

    @Test
    void multiplyOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        DoubleMatrix matrix1 = new DoubleMatrix(2, 3);
        DoubleMatrix matrix2 = new DoubleMatrix(1, 2);
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> matrix1.multiply(matrix2)
        ); // assert exception message?
    }

    @Test
    void multiplyOfMatrixWithSize2ToItself() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        Assertions.assertEquals(DoubleMatrix.ofValuesByRows(2,
            7, 10, 15, 22
        ), matrix.multiply(matrix));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        DoubleMatrix matrix1 = new DoubleMatrix(1, 2);
        DoubleMatrix matrix2 = new DoubleMatrix(2, 3);
        DoubleMatrix result = new DoubleMatrix(1, 3);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, 0d);
        Assertions.assertEquals(result, matrix1.multiplyTolerant(matrix2));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        DoubleMatrix matrix1 = new DoubleMatrix(2, 3);
        DoubleMatrix matrix2 = new DoubleMatrix(1, 2);
        DoubleMatrix result = new DoubleMatrix(1, 3);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, 0d);
        Assertions.assertEquals(result, matrix1.multiplyTolerant(matrix2));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow3Col4() {
        DoubleMatrix matrix1 = new DoubleMatrix(1, 2);
        DoubleMatrix matrix2 = new DoubleMatrix(3, 4);
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> matrix1.multiplyTolerant(matrix2)
        );
    }

    @Test
    void multiplyTolerantOfMatrixWithSize2ToItself() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        Assertions.assertEquals(
            DoubleMatrix.ofValuesByRows(2,
                7, 10, 15, 22
            ),
            matrix.multiplyTolerant(matrix)
        );
    }

    // endregion

    // region matrix arithmetic: transpose and determinante

    @Test
    void transposeOfEmptyMatrixWithSize2() {
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, 0d);
        Assertions.assertEquals(result, new DoubleMatrix(2).transpose());
    }

    @Test
    void transposeOfMatrixWithSize2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int r = 0; r < matrix.getRows(); r++) {
            for (int c = 0; c < matrix.getCols(); c++) {
                int i = matrix.getIndexOfRowAndCol(r, c);
                matrix.setValue(i, i + 1d);
                result.setValue(result.getIndexOfRowAndCol(c, r), i + 1d);
            }
        }
        Assertions.assertEquals(result, matrix.transpose());
    }

    @Test
    void determinanteOfEmptyMatrixWithSize2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertEquals(0, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize2AndValues1234() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        Assertions.assertEquals(-2, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues123456789() {
        DoubleMatrix matrix = new DoubleMatrix(3);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        Assertions.assertEquals(0, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues1And2OnDiagonale() {
        DoubleMatrix matrix = new DoubleMatrix(3);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? 2d : 1d);
        Assertions.assertEquals(4, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1And2OnDiagonale() {
        DoubleMatrix matrix = new DoubleMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? 2d : 1d);
        Assertions.assertEquals(5, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1AndMore0InRow() {
        DoubleMatrix matrix = new DoubleMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, c != 0 && (r == c || r == 0) ? 0d : 1d);
        Assertions.assertEquals(2, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1AndMore0InCol() {
        DoubleMatrix matrix = new DoubleMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r != 0 && (r == c || c == 0) ? 0d : 1d);
        Assertions.assertEquals(2, matrix.determinante());
    }

    // endregion

    // region override

    @Test
    void copyOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertEquals(matrix, matrix.copy());
    }

    @Test
    void iteratorOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        List<TestMatrix.Field> values = new ArrayList<>();
        for (TestMatrix.Field d : matrix) {
            values.add(d);
            Assertions.assertEquals(0d, d.getValue());
        }
        Assertions.assertEquals(matrix.size(), values.size());
    }

    @Test
    void equalsOfTestMatrixWithRow2Col3() {
        TestMatrix matrix = new TestMatrix(2, 3);
        Assertions.assertEquals(
            matrix,
            new TestMatrix(2, 3)
        );
        Assertions.assertNotEquals(
            matrix,
            new TestMatrix(3, 2)
        );
    }

    @Test
    void hashCodeOfTestMatrixWithRow2Col3() {
        Assertions.assertEquals(
            925536,
            new TestMatrix(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfTestMatrixWithRow2Col3() {
        TestMatrix matrix = new TestMatrix(2, 3);
        Assertions.assertEquals("2 3: []", matrix.toString());
    }

    // endregion

    // region protected: subMatrix

    @Test
    void subMatrixR0C0OfMatrixWithR1C2WitchIsNoSquare() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new TestMatrix(1, 2).subMatrix(0, 0)
        ); // assert exception message?
    }

    @Test
    void subMatrixR1C0OfMatrixWithSize1UsingInvalidRow1() {
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> new TestMatrix(1).subMatrix(1, 0)
        ); // assert exception message?
    }

    @Test
    void subMatrixR0C1OfMatrixWithSize1UsingInvalidCol1() {
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> new TestMatrix(1).subMatrix(0, 1)
        ); // assert exception message?
    }

    @Test
    void subMatrixR0C0OfMatrixWithSize1() {
        Assertions.assertEquals(0,
            new TestMatrix(1).subMatrix(0, 0).size()
        );
    }

    @Test
    void subMatrixR0C0OfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        Matrix<Number> subMatrix = matrix.subMatrix(0, 0);
        Assertions.assertEquals(1, subMatrix.size());
        Assertions.assertEquals(3, subMatrix.getValue(0));
    }

    @Test
    void subMatrixR0C1OfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        Matrix<Number> subMatrix = matrix.subMatrix(0, 1);
        Assertions.assertEquals(1, subMatrix.size());
        Assertions.assertEquals(2, subMatrix.getValue(0));
    }

    @Test
    void subMatrixR1C0OfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        Matrix<Number> subMatrix = matrix.subMatrix(1, 0);
        Assertions.assertEquals(1, subMatrix.size());
        Assertions.assertEquals(1, subMatrix.getValue(0));
    }

    @Test
    void subMatrixR1C1OfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        Matrix<Number> subMatrix = matrix.subMatrix(1, 1);
        Assertions.assertEquals(1, subMatrix.size());
        Assertions.assertEquals(0, subMatrix.getValue(0));
    }

    // endregion

    // region protected: modify

    @Test
    void flipRowsOfMatrixWithSize2UsingInvalidRow1() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.flipRows(-1, 0)
        ); // assert exception message?
    }

    @Test
    void flipRowsOfMatrixWithSize2UsingInvalidRow2() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.flipRows(0, -1)
        ); // assert exception message?
    }

    @Test
    void flipRowsOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1);
            result.setValue((i + result.getCols()) % result.size(), i + 1);
        }
        Assertions.assertEquals(result, matrix.flipRows(0, 1));
    }

    @Test
    void flipColsOfMatrixWithSize2UsingInvalidCol1() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.flipCols(-1, 0)
        ); // assert exception message?
    }

    @Test
    void flipColsOfMatrixWithSize2UsingInvalidCol2() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.flipCols(0, -1)
        ); // assert exception message?
    }

    @Test
    void flipColsOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1);
            result.setValue(i + (i % result.getCols() == 0 ? 1 : -1), i + 1);
        }
        Assertions.assertEquals(result, matrix.flipCols(0, 1));
    }

    @Test
    void multiplyRowOfMatrixWithSize2UsingInvalidRow() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.multiplyRow(-1, 0)
        ); // assert exception message?
    }

    @Test
    void multiplyRowOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1);
            result.setValue(i, (i + 1) * (i / result.getCols() == 0 ? 2 : 1));
        }
        Assertions.assertEquals(result, matrix.multiplyRow(0, 2));
    }

    @Test
    void multiplyColOfMatrixWithSize2UsingInvalidCol() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.multiplyCol(-1, 0)
        ); // assert exception message?
    }

    @Test
    void multiplyColOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1);
            result.setValue(i, (i + 1) * (i % result.getCols() == 0 ? 2 : 1));
        }
        Assertions.assertEquals(result, matrix.multiplyCol(0, 2));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2UsingInvalidRow1() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addRowMultipleTimes(-1, 0, 0)
        ); // assert exception message?
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2UsingInvalidRow2() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addRowMultipleTimes(0, -1, 0)
        ); // assert exception message?
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2ToSameRow() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1);
            result.setValue(i, (i + 1) * (i / result.getCols() == 0 ? 2 : 1));
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 0, 2));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using0() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, value);
            result.setValue(i, value);
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 1, 0));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, value);
            result.setValue(i, value + (i / result.getCols() == 0 ? 2 : 0));
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 1, 1));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, value);
            result.setValue(i, value + (i / result.getCols() == 0 ? 4 : 0));
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 1, 2));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2UsingInvalidCol1() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addColMultipleTimes(-1, 0, 1)
        ); // assert exception message?
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2UsingInvalidCol2() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addColMultipleTimes(0, -1, 0)
        ); // assert exception message?
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2ToSameRow() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1);
            result.setValue(i, (i + 1) * (i % result.getCols() == 0 ? 2 : 1));
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 0, 2));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using0() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, value);
            result.setValue(i, value);
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 1, 0));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, value);
            result.setValue(i, value + (i % result.getCols() == 0 ? value : 0));
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 1, 1));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, value);
            result.setValue(i, value + (i % result.getCols() == 0 ? 2 * value : 0));
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 1, 2));
    }

    // endregion

    // region protected: getIndexOfRowAndCol, isDefaultValue and isValid

    @Test
    void getIndexOfRowAndColOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        int index = 0;
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                Assertions.assertEquals(index++, matrix.getIndexOfRowAndCol(r, c));
    }

    @Test
    void isDefaultValueOfMatrixWithDefaultValueNull() {
        TestMatrix matrix = new TestMatrix(1, null);
        Assertions.assertTrue(matrix.isDefaultValue(null));
        Assertions.assertFalse(matrix.isDefaultValue(0));
        Assertions.assertFalse(matrix.isDefaultValue(1));
    }

    @Test
    void isDefaultValueOfMatrixWithDefaultValue1() {
        TestMatrix matrix = new TestMatrix(1, 1d);
        Assertions.assertFalse(matrix.isDefaultValue(null));
        Assertions.assertFalse(matrix.isDefaultValue(0));
        Assertions.assertTrue(matrix.isDefaultValue(1d));
    }

    @Test
    void isIndexValidOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertFalse(matrix.isIndexValid(-1));
        for (int i = 0; i < matrix.size(); i++)
            Assertions.assertTrue(matrix.isIndexValid(i));
        Assertions.assertFalse(matrix.isIndexValid(matrix.size()));
    }

    @Test
    void isRowValidOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertFalse(matrix.isRowValid(-1));
        for (int i = 0; i < matrix.getRows(); i++)
            Assertions.assertTrue(matrix.isRowValid(i));
        Assertions.assertFalse(matrix.isRowValid(matrix.getRows()));
    }

    @Test
    void isColValidOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertFalse(matrix.isColValid(-1));
        for (int i = 0; i < matrix.getCols(); i++)
            Assertions.assertTrue(matrix.isColValid(i));
        Assertions.assertFalse(matrix.isColValid(matrix.getCols()));
    }

    // endregion

    // region field

    @Test
    void fieldGetter() {
        TestMatrix matrix = new TestMatrix(2);
        int index = 0;
        for (Matrix<Number>.Field field : matrix) {
            Assertions.assertEquals(index, field.getIndex());
            Assertions.assertEquals(index / matrix.getCols(), field.getRow());
            Assertions.assertEquals(index % matrix.getCols(), field.getCol());
            index++;
        }
    }

    @Test
    void fieldOverride() {
        TestMatrix matrix = new TestMatrix(2);
        Matrix<Number>.Field previous = null;
        for (Matrix<Number>.Field field : matrix) {
            if (previous != null) Assertions.assertNotEquals(previous, field);
            else Assertions.assertEquals(
                matrix.new Field(field.getIndex(), field.getValue()),
                field
            );
            Assertions.assertTrue(0 < field.hashCode());
            Assertions.assertEquals(
                field.getIndex() + ": " + field.getValue(),
                field.toString()
            );
            previous = field;
        }
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
