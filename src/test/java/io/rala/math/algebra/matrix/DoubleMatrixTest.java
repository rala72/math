package io.rala.math.algebra.matrix;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.rala.math.testUtils.assertion.MatrixAssertions.assertMatrix;

class DoubleMatrixTest {
    // region constructors

    @Test
    void constructorWithNegativeSize() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new DoubleMatrix(-1)
        ); // assert exception message?
    }

    @Test
    void constructorWithIntegerMaxValueSize() {
        DoubleMatrix matrix = new DoubleMatrix(Integer.MAX_VALUE);

        long expectedSize = (long) Integer.MAX_VALUE * Integer.MAX_VALUE;
        Assertions.assertEquals(expectedSize, matrix.size());

        Assertions.assertTrue(matrix.isIndexValid(expectedSize - 1));
        Assertions.assertFalse(matrix.isIndexValid(expectedSize));
    }

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
    void newInstanceOfMatrixWithSize3() {
        assertMatrix(new DoubleMatrix(1).newInstance(3), 3);
    }

    @Test
    void newInstanceOfMatrixWithRows1Cols2() {
        assertMatrix(new DoubleMatrix(2).newInstance(1, 2), 1, 2);
    }

    // endregion

    // region getter and size

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

    // region rows and cols

    @Test
    void getRowFieldsM1OfMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getRowFields(-1)
        ); // assert exception message?
    }

    @Test
    void getRowFields0OfMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);
        List<Matrix<Double>.Field> row0 = matrix.getRowFields(0);
        Assertions.assertEquals(2, row0.size());
        for (int i = 0; i < row0.size(); i++) {
            Matrix<Double>.Field field = row0.get(i);
            Assertions.assertEquals(0, field.getRow());
            Assertions.assertEquals(i, field.getCol());
            Assertions.assertEquals(i, field.getValue());
        }
    }

    @Test
    void getRow0OfMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);
        List<Double> row0 = matrix.getRow(0);
        Assertions.assertEquals(2, row0.size());
        for (int i = 0; i < row0.size(); i++)
            Assertions.assertEquals(i, row0.get(i));
    }

    @Test
    void getColFieldsM1OfMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getColFields(-1)
        ); // assert exception message?
    }

    @Test
    void getColFields0OfMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);
        List<Matrix<Double>.Field> col0 = matrix.getColFields(0);
        Assertions.assertEquals(2, col0.size());
        for (int i = 0; i < col0.size(); i++) {
            Matrix<Double>.Field field = col0.get(i);
            Assertions.assertEquals(0, field.getCol());
            Assertions.assertEquals(i, field.getRow());
            Assertions.assertEquals(i * 2, field.getValue());
        }
    }

    @Test
    void getCol0OfMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);
        List<Double> col0 = matrix.getCol(0);
        Assertions.assertEquals(2, col0.size());
        for (int i = 0; i < col0.size(); i++)
            Assertions.assertEquals(i * 2, col0.get(i));
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
        Assertions.assertEquals(0d, matrix.setValue(0, 1d));
        Assertions.assertEquals(1, matrix.getValue(0));
        Assertions.assertEquals(1, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByIndex3WhichWasEmpty() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertEquals(0d, matrix.setValue(3, 1d));
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
        Assertions.assertEquals(0d, matrix.setValue(0, 0, 1d));
        Assertions.assertEquals(1, matrix.getValue(0));
        Assertions.assertEquals(1, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByRow1Col0WhichWasEmpty() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertEquals(0d, matrix.setValue(1, 0, 1d));
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
        Assertions.assertEquals(0d, matrix.removeValue(0));
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
        Assertions.assertEquals(0d, matrix.removeValue(0, 0));
    }

    @Test
    void removeValueByIndex2WhichWasNonEmpty() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        matrix.setValue(2, 1d);
        Assertions.assertEquals(1, matrix.removeValue(2));
    }

    // endregion

    // region isSquare and isDiagonal

    @Test
    void isSquareOfMatrixWithRow1Col2() {
        Assertions.assertFalse(new DoubleMatrix(1, 2).isDiagonal());
    }

    @Test
    void isSquareOfMatrixWithSize2() {
        Assertions.assertTrue(new DoubleMatrix(2).isDiagonal());
    }

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
    void multiplyOfEmptyMatrixWithSize2With2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        Assertions.assertEquals(result, matrix.multiply(2d));
    }

    @Test
    void multiplyMatrixWithSize2With2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < result.size(); i++) {
            matrix.setValue(i, (i + 1d));
            result.setValue(i, (i + 1d) * 2);
        }
        Assertions.assertEquals(result, matrix.multiply(2d));
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

    // region matrix arithmetic: inverse, transpose and determinante

    @Test
    void inverseOfEmptyMatrixWichIsNoSquare() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new DoubleMatrix(1, 2).inverse()
        ); // assert exception message?
    }

    @Test
    void inverseOfEmptyMatrixWithSize2() {
        Assertions.assertNull(new DoubleMatrix(2).inverse());
    }

    @Test
    void inverseOfMatrixWithSize2() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(2,
            2, 5, 1, 3
        );
        DoubleMatrix result = DoubleMatrix.ofValuesByRows(2,
            3, -5, -1, 2
        );
        Assertions.assertEquals(result, matrix.inverse());
    }

    @Test
    void inverseOfMatrixWithSize3() {
        DoubleMatrix matrix = DoubleMatrix.ofValuesByRows(3,
            3, 5, 1, 2, 4, 5, 1, 2, 2
        );
        DoubleMatrix result = DoubleMatrix.ofValuesByRows(3,
            2, 8, -21, -1, -5, 13, 0, 1, -2
        );
        Assertions.assertEquals(result, matrix.inverse());
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
                int i = (int) matrix.getIndexOfRowAndCol(r, c);
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

    // region map and copy

    @Test
    void mapOfMatrixWithSize2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Matrix<Integer> result =
            new Matrix<>(new IntegerArithmetic(), 2, 0);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++) {
                matrix.setValue(r, c, r + c + 0.5);
                result.setValue(r, c, r + c);
            }

        Assertions.assertEquals(result,
            matrix.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void copyOfMatrixWithSize2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertEquals(matrix, matrix.copy());
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
    void streamOfEmptyMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertEquals(4, matrix.stream().count());
    }

    @Test
    void parallelStreamOfEmptyMatrix() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertEquals(4, matrix.parallelStream().count());
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

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
            new DoubleMatrix(0),
            DoubleMatrix.class
        );
    }

    // endregion

    // region protected: modify

    @Test
    void swapRowsOfMatrixWithSize2UsingInvalidRow1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.swapRows(-1, 0)
        ); // assert exception message?
    }

    @Test
    void swapRowsOfMatrixWithSize2UsingInvalidRow2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.swapRows(0, -1)
        ); // assert exception message?
    }

    @Test
    void swapRowsOfMatrixWithSize2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue((i + result.getCols()) % (int) result.size(), i + 1d);
        }
        Assertions.assertEquals(result, matrix.swapRows(0, 1));
    }

    @Test
    void swapColsOfMatrixWithSize2UsingInvalidCol1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.swapCols(-1, 0)
        ); // assert exception message?
    }

    @Test
    void swapColsOfMatrixWithSize2UsingInvalidCol2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.swapCols(0, -1)
        ); // assert exception message?
    }

    @Test
    void swapColsOfMatrixWithSize2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i + (i % result.getCols() == 0 ? 1 : -1), i + 1d);
        }
        Assertions.assertEquals(result, matrix.swapCols(0, 1));
    }

    @Test
    void multiplyRowOfMatrixWithSize2UsingInvalidRow() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.multiplyRow(-1, 0d)
        ); // assert exception message?
    }

    @Test
    void multiplyRowOfMatrixWithSize2Using0() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i / result.getCols() == 0 ? 0 : 1));
        }
        Assertions.assertEquals(result, matrix.multiplyRow(0, 0d));
    }

    @Test
    void multiplyRowOfMatrixWithSize2Using1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i / result.getCols() == 0 ? 1 : 1));
        }
        Assertions.assertEquals(result, matrix.multiplyRow(0, 1d));
    }

    @Test
    void multiplyRowOfMatrixWithSize2Using2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i / result.getCols() == 0 ? 2 : 1));
        }
        Assertions.assertEquals(result, matrix.multiplyRow(0, 2d));
    }

    @Test
    void multiplyColOfMatrixWithSize2UsingInvalidCol() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.multiplyCol(-1, 0d)
        ); // assert exception message?
    }

    @Test
    void multiplyColOfMatrixWithSize2Using0() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i % result.getCols() == 0 ? 0 : 1));
        }
        Assertions.assertEquals(result, matrix.multiplyCol(0, 0d));
    }

    @Test
    void multiplyColOfMatrixWithSize2Using1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i % result.getCols() == 0 ? 1 : 1));
        }
        Assertions.assertEquals(result, matrix.multiplyCol(0, 1d));
    }

    @Test
    void multiplyColOfMatrixWithSize2Using2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i % result.getCols() == 0 ? 2 : 1));
        }
        Assertions.assertEquals(result, matrix.multiplyCol(0, 2d));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2UsingInvalidRow1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addRowMultipleTimes(-1, 0, 0d)
        ); // assert exception message?
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2UsingInvalidRow2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addRowMultipleTimes(0, -1, 0d)
        ); // assert exception message?
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2ToSameRow() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i / result.getCols() == 0 ? 2 : 1));
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 0, 2d));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using0() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i, (double) value);
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 1, 0d));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i, value + (i / result.getCols() == 0 ? 2d : 0d));
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 1, 1d));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i, value + (i / result.getCols() == 0 ? 4d : 0d));
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 1, 2d));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2UsingInvalidCol1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addColMultipleTimes(-1, 0, 1d)
        ); // assert exception message?
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2UsingInvalidCol2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addColMultipleTimes(0, -1, 0d)
        ); // assert exception message?
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2ToSameRow() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i % result.getCols() == 0 ? 2 : 1));
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 0, 2d));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using0() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i, (double) value);
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 1, 0d));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using1() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i, (double) value + (i % result.getCols() == 0 ? value : 0));
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 1, 1d));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using2() {
        DoubleMatrix matrix = new DoubleMatrix(2);
        DoubleMatrix result = new DoubleMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i,
                (double) value + (i % result.getCols() == 0 ?
                    2 * value : 0));
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 1, 2d));
    }

    // endregion
}
