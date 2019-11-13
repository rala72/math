package io.rala.math.algebra;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DoubleMatrixTest {
    // region constructors, getter and size

    @Test
    void constructorWithSize0() {
        assertMatrix(new DoubleMatrix(0), 0);
    }

    @Test
    void constructorWithSize1() {
        assertMatrix(new DoubleMatrix(1), 1);
    }

    @Test
    void constructorWithRows1Cols1() {
        assertMatrix(new DoubleMatrix(1, 1), 1, 1);
    }

    @Test
    void constructorWithRows1Cols2() {
        assertMatrix(new DoubleMatrix(1, 2), 1, 2);
    }

    @Test
    void constructorWithMatrix() {
        assertMatrix(new DoubleMatrix(new DoubleMatrix(1, 2)), 1, 2);
    }

    @Test
    void createWithSize1AndAssertSizeEquals1() {
        Assertions.assertEquals(1, new DoubleMatrix(1).size());
    }

    @Test
    void createWithSize2AndAssertSizeEquals4() {
        Assertions.assertEquals(4, new DoubleMatrix(2).size());
    }

    @Test
    void createWithRow1Col2AndAssertSizeEquals2() {
        Assertions.assertEquals(2, new DoubleMatrix(1, 2).size());
    }

    @Test
    void createWithRow2Col3AndAssertSizeEquals6() {
        Assertions.assertEquals(6, new DoubleMatrix(2, 3).size());
    }

    // endregion

    // region value

    @Test
    void setValueByIndexMinus1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.setValue(-1, 0d)
        ); // assert exception message?
    }

    @Test
    void setValueByIndex0WhichWasEmpty() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertNull(matrix.setValue(0, 1d));
        Assertions.assertEquals(1, matrix.getValue(0));
        Assertions.assertEquals(1, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByIndex3WhichWasEmpty() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertNull(matrix.setValue(3, 1d));
        Assertions.assertEquals(1, matrix.getValue(3));
        Assertions.assertEquals(1, matrix.getValue(1, 1));
        // assert all other are unset
    }

    @Test
    void setValueByRowMinus1Col0() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.setValue(-1, 0, 0d)
        ); // assert exception message?
    }

    @Test
    void setValueByRow0ColMinus1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.setValue(0, -1, 0d)
        ); // assert exception message?
    }

    @Test
    void setValueByRow0Col0WhichWasEmpty() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertNull(matrix.setValue(0, 0, 1d));
        Assertions.assertEquals(1, matrix.getValue(0));
        Assertions.assertEquals(1, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByRow1Col0WhichWasEmpty() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertNull(matrix.setValue(1, 0, 1d));
        Assertions.assertEquals(1, matrix.getValue(1, 0));
        Assertions.assertEquals(1, matrix.getValue(2));
        // assert all other are unset
    }

    @Test
    void getValueByIndexMinus1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getValue(-1)
        ); // assert exception message?
    }

    @Test
    void getValueByRowMinus1Col0() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getValue(-1, 0)
        ); // assert exception message?
    }

    @Test
    void getValueByRow0ColMinus1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getValue(0, -1)
        ); // assert exception message?
    }

    @Test
    void removeValueByIndexMinus1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.removeValue(-1)
        ); // assert exception message?
    }

    @Test
    void removeValueByIndex0WhichWasEmpty() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertNull(matrix.removeValue(0));
    }

    @Test
    void removeValueByRowMinus1Col0() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.removeValue(-1, 0)
        ); // assert exception message?
    }

    @Test
    void removeValueByRow0ColMinus1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.removeValue(0, -1)
        ); // assert exception message?
    }

    @Test
    void removeValueByRow0Col0WhichWasEmpty() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertNull(matrix.removeValue(0, 0));
    }

    @Test
    void removeValueByIndex2WhichWasNonEmpty() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        matrix.setValue(2, 1d);
        Assertions.assertEquals(1, matrix.removeValue(2));
    }

    // endregion

    // region isDiagonal

    @Test
    void isDiagonalOfMatrixWithRow1Col2() {
        Assertions.assertFalse(new DoubleMatrix(1, 2).isDiagonal());
    }

    @Test
    void isDiagonalOfMatrixOfValidValues() {
        Assertions.assertTrue(
            DoubleMatrix.ofValuesByRows(2, 1, 0, 0, 4).isDiagonal()
        );
    }

    @Test
    void isDiagonalOfMatrixOfInvalidValues() {
        Assertions.assertFalse(
            DoubleMatrix.ofValuesByRows(2, 1, 2, 3, 4).isDiagonal()
        );
    }

    @Test
    void isDiagonalOfDiagonalMatrix() {
        Assertions.assertTrue(DoubleMatrix.diagonal(1, 2, 3).isDiagonal());
    }

    // endregion

    // region abstract

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows2Cols1() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new DoubleMatrix(1)
                .add(new DoubleMatrix(2, 1))
        ); // assert message
    }

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows1Cols2() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new DoubleMatrix(1)
                .add(new DoubleMatrix(1, 2))
        ); // assert message
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
        ); // assert message
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

    // region static: identity and diagonal

    @Test
    void identityOfSize1() {
        DoubleMatrix matrix = DoubleMatrix.identity(1);
        Assertions.assertEquals(1, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            Assertions.assertEquals(1, matrix.getValue(i, i));
    }

    @Test
    void identityOfSize2() {
        DoubleMatrix matrix = DoubleMatrix.identity(2);
        Assertions.assertEquals(2 * 2, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            Assertions.assertEquals(1, matrix.getValue(i, i));
    }

    @Test
    void diagonalOfSize1() {
        DoubleMatrix matrix = DoubleMatrix.diagonal(1);
        Assertions.assertEquals(1, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            Assertions.assertEquals(1, matrix.getValue(i, i));
    }

    @Test
    void diagonalOfSize2() {
        DoubleMatrix matrix = DoubleMatrix.diagonal(2, 2);
        Assertions.assertEquals(2 * 2, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            Assertions.assertEquals(2, matrix.getValue(i, i));
    }

    // endregion

    // region static: of

    @Test
    void ofValuesByRows2WithInvalidParamCount() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> DoubleMatrix.ofValuesByRows(2, 1)
        );
    }

    @Test
    void ofValuesByRows2WithValidParamCount() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2, 1, 2);
        for (int i = 0; i < matrix.size(); i++)
            Assertions.assertEquals(
                i + 1,
                matrix.getValue(i),
                "index: " + i
            );
    }

    @Test
    void ofValuesByCols2WithInvalidParamCount() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> DoubleMatrix.ofValuesByCols(2, 1)
        );
    }

    @Test
    void ofValuesByCols2WithValidParamCount() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByCols(2, 1, 2);
        for (int i = 0; i < matrix.size(); i++)
            Assertions.assertEquals(
                i + 1d,
                matrix.getValue(i),
                "index: " + i
            );
    }

    // endregion

    // region override

    @Test
    void copyOfMatrixWithSize2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertEquals(matrix, matrix.copy());
    }

    @Test
    void iteratorOfEmptyMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        List<DoubleMatrix.Field> values = new ArrayList<>();
        for (DoubleMatrix.Field d : matrix) {
            values.add(d);
            Assertions.assertEquals(0d, d.getValue());
        }
        Assertions.assertEquals(matrix.size(), values.size());
    }

    @Test
    void equalsOfDoubleMatrixWithRow2Col3() {
        DoubleMatrix matrix = new DoubleMatrix(2, 3);
        Assertions.assertEquals(
            matrix,
            new DoubleMatrix(2, 3)
        );
        Assertions.assertNotEquals(
            matrix,
            new DoubleMatrix(3, 2)
        );
    }

    @Test
    void hashCodeOfDoubleMatrixWithRow2Col3() {
        Assertions.assertEquals(
            925536,
            new DoubleMatrix(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfDoubleMatrixWithRow2Col3() {
        DoubleMatrix matrix = new DoubleMatrix(2, 3);
        Assertions.assertEquals("2 3: []", matrix.toString());
    }


    // endregion


    // region assertions

    private static <T extends Number> void assertMatrix(Matrix<T> matrix, int size) {
        assertMatrix(matrix, size, size);
    }

    private static <T extends Number> void assertMatrix(Matrix<T> matrix, int rows, int cols) {
        Assertions.assertEquals(rows, matrix.getRows(), "rows is invalid");
        Assertions.assertEquals(cols, matrix.getCols(), "cols is invalid");
    }

    // endregion
}
