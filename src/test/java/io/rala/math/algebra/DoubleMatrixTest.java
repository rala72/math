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
    void transposeOfEmptyMatrixWithSize2() {
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, 0d);
        Assertions.assertEquals(result, new DoubleMatrix(2).transpose());
    }

    // endregion

    // region static of

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
                matrix.size() - i,
                matrix.getValue(i),
                "index: " + i
            );
    }

    // endregion

    // region override

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
    void equalsOfDoubleMatrixWithReIm() {
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
    void hashCodeOfDoubleMatrixWithReIm() {
        Assertions.assertEquals(
            925536,
            new DoubleMatrix(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfDoubleMatrixWithReIm() {
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
