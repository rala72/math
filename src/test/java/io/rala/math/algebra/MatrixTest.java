package io.rala.math.algebra;

import io.rala.math.testUtils.TestMatrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MatrixTest {
    // region constructors, getter and size

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

    // region abstract

    @Test
    void addOfMatrixWithSize2AndMatrixWithSize2() {
        TestMatrix matrix1 = new TestMatrix(2);
        TestMatrix matrix2 = new TestMatrix(2);
        Assertions.assertEquals(matrix2, matrix1.add(matrix2));
    }

    @Test
    void multiplyOfMatrixWithSize2AndMatrixWithSize2() {
        TestMatrix matrix1 = new TestMatrix(2);
        TestMatrix matrix2 = new TestMatrix(2);
        Assertions.assertEquals(matrix2, matrix1.multiply(matrix2));
    }

    @Test
    void multiplyOfMatrixWithRow1Col2AndMatrixWithRow2Col3() {
        TestMatrix matrix1 = new TestMatrix(1, 2);
        TestMatrix matrix2 = new TestMatrix(2, 3);
        Assertions.assertEquals(matrix2, matrix1.multiply(matrix2));
    }

    @Test
    void multiplyOfMatrixWithRow2Col3AndMatrixWithRow1Col2() {
        TestMatrix matrix1 = new TestMatrix(2, 3);
        TestMatrix matrix2 = new TestMatrix(1, 2);
        Assertions.assertEquals(matrix2, matrix1.multiply(matrix2));
    }

    @Test
    void multiplyTolerantOfMatrixWithRow1Col2AndMatrixWithRow2Col3() {
        TestMatrix matrix1 = new TestMatrix(1, 2);
        TestMatrix matrix2 = new TestMatrix(2, 3);
        Assertions.assertEquals(matrix2, matrix1.multiplyTolerant(matrix2));
    }

    @Test
    void multiplyTolerantOfMatrixWithRow2Col3AndMatrixWithRow1Col2() {
        TestMatrix matrix1 = new TestMatrix(2, 3);
        TestMatrix matrix2 = new TestMatrix(1, 2);
        Assertions.assertEquals(matrix1, matrix1.multiplyTolerant(matrix2));
    }

    @Test
    void multiplyTolerantOfMatrixWithRow1Col2AndMatrixWithRow3Col4() {
        TestMatrix matrix1 = new TestMatrix(1, 2);
        TestMatrix matrix2 = new TestMatrix(3, 4);
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> matrix1.multiplyTolerant(matrix2)
        );
    }

    @Test
    void transposeOfMatrixWithSize2() {
        Assertions.assertNull(new TestMatrix(2).transpose());
    }

    // endregion

    // region override

    @Test
    void iteratorOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        List<TestMatrix.Field> values = new ArrayList<>();
        for (TestMatrix.Field d : matrix) {
            values.add(d);
            Assertions.assertNull(d.getValue());
        }
        Assertions.assertEquals(matrix.size(), values.size());
    }

    @Test
    void equalsOfTestMatrixWithReIm() {
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
    void hashCodeOfTestMatrixWithReIm() {
        Assertions.assertEquals(
            925536,
            new TestMatrix(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfTestMatrixWithReIm() {
        TestMatrix matrix = new TestMatrix(2, 3);
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
