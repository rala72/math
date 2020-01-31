package io.rala.math.algebra.matrix;

import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.rala.math.testUtils.assertion.MatrixAssertions.assertMatrix;

class MatrixTest {
    // region constructors and newInstance

    @Test
    void constructorWithNegativeSize() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new TestMatrix(-1)
        ); // assert exception message?
    }

    @Test
    void constructorWithIntegerMaxValueSize() {
        TestMatrix matrix = new TestMatrix(Integer.MAX_VALUE);

        long expectedSize = (long) Integer.MAX_VALUE * Integer.MAX_VALUE;
        Assertions.assertEquals(expectedSize, matrix.size());

        Assertions.assertTrue(matrix.isIndexValid(expectedSize - 1));
        Assertions.assertFalse(matrix.isIndexValid(expectedSize));
    }

    @Test
    void constructorWithSize0() {
        assertMatrix(new TestMatrix(1), 1);
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

    // region rows and cols

    @Test
    void getRowFieldsM1OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getRowFields(-1)
        ); // assert exception message?
    }

    @Test
    void getRowFields0OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        List<Matrix<Number>.Field> row0 = matrix.getRowFields(0);
        Assertions.assertEquals(2, row0.size());
        for (int i = 0; i < row0.size(); i++) {
            Matrix<Number>.Field field = row0.get(i);
            Assertions.assertEquals(0, field.getRow());
            Assertions.assertEquals(i, field.getCol());
            Assertions.assertEquals(i, field.getValue());
        }
    }

    @Test
    void getRow0OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        List<Number> row0 = matrix.getRow(0);
        Assertions.assertEquals(2, row0.size());
        for (int i = 0; i < row0.size(); i++)
            Assertions.assertEquals(i, row0.get(i));
    }

    @Test
    void getColFieldsM1OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getColFields(-1)
        ); // assert exception message?
    }

    @Test
    void getColFields0OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        List<Matrix<Number>.Field> col0 = matrix.getColFields(0);
        Assertions.assertEquals(2, col0.size());
        for (int i = 0; i < col0.size(); i++) {
            Matrix<Number>.Field field = col0.get(i);
            Assertions.assertEquals(0, field.getCol());
            Assertions.assertEquals(i, field.getRow());
            Assertions.assertEquals(i * 2, field.getValue());
        }
    }

    @Test
    void getCol0OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        List<Number> col0 = matrix.getCol(0);
        Assertions.assertEquals(2, col0.size());
        for (int i = 0; i < col0.size(); i++)
            Assertions.assertEquals(i * 2, col0.get(i));
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
        Assertions.assertEquals(0d, matrix.setValue(0, 1));
        Assertions.assertEquals(1, matrix.getValue(0));
        Assertions.assertEquals(1, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByIndex3WhichWasEmpty() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertEquals(0d, matrix.setValue(3, 1));
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
        Assertions.assertEquals(0d, matrix.setValue(0, 0, 1));
        Assertions.assertEquals(1, matrix.getValue(0));
        Assertions.assertEquals(1, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByRow1Col0WhichWasEmpty() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertEquals(0d, matrix.setValue(1, 0, 1));
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
        Assertions.assertEquals(0d, matrix.removeValue(0));
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
        Assertions.assertEquals(0d, matrix.removeValue(0, 0));
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
            () -> new TestMatrix(1)
                .add(new TestMatrix(2, 1))
        ); // assert exception message?
    }

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows1Cols2() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new TestMatrix(1)
                .add(new TestMatrix(1, 2))
        ); // assert exception message?
    }

    @Test
    void addOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        TestMatrix matrix1 = new TestMatrix(2);
        TestMatrix matrix2 = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, 0d);
        Assertions.assertEquals(result, matrix1.add(matrix2));
    }

    @Test
    void addOfMatrixWithSize2ToItself() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (double) 2 * (i + 1));
        }
        Assertions.assertEquals(result, matrix.add(matrix));
    }

    @Test
    void multiplyOfEmptyMatrixWithSize2With2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        Assertions.assertEquals(result, matrix.multiply(2));
    }

    @Test
    void multiplyMatrixWithSize2With2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < result.size(); i++) {
            matrix.setValue(i, (i + 1));
            result.setValue(i, (double) (i + 1) * 2);
        }
        Assertions.assertEquals(result, matrix.multiply(2));
    }

    @Test
    void multiplyOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        TestMatrix matrix1 = new TestMatrix(2);
        TestMatrix matrix2 = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, 0d);
        Assertions.assertEquals(result, matrix1.multiply(matrix2));
    }

    @Test
    void multiplyOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        TestMatrix matrix1 = new TestMatrix(1, 2);
        TestMatrix matrix2 = new TestMatrix(2, 3);
        TestMatrix result = new TestMatrix(1, 3);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, 0d);
        Assertions.assertEquals(result, matrix1.multiply(matrix2));
    }

    @Test
    void multiplyOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        TestMatrix matrix1 = new TestMatrix(2, 3);
        TestMatrix matrix2 = new TestMatrix(1, 2);
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> matrix1.multiply(matrix2)
        ); // assert exception message?
    }

    @Test
    void multiplyOfMatrixWithSize2ToItself() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        Assertions.assertEquals(TestMatrix.ofValuesByRows(2,
            7d, 10d, 15d, 22d
        ), matrix.multiply(matrix));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        TestMatrix matrix1 = new TestMatrix(1, 2);
        TestMatrix matrix2 = new TestMatrix(2, 3);
        TestMatrix result = new TestMatrix(1, 3);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, 0d);
        Assertions.assertEquals(result, matrix1.multiplyTolerant(matrix2));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        TestMatrix matrix1 = new TestMatrix(2, 3);
        TestMatrix matrix2 = new TestMatrix(1, 2);
        TestMatrix result = new TestMatrix(1, 3);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, 0d);
        Assertions.assertEquals(result, matrix1.multiplyTolerant(matrix2));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow3Col4() {
        TestMatrix matrix1 = new TestMatrix(1, 2);
        TestMatrix matrix2 = new TestMatrix(3, 4);
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> matrix1.multiplyTolerant(matrix2)
        );
    }

    @Test
    void multiplyTolerantOfMatrixWithSize2ToItself() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        Assertions.assertEquals(
            TestMatrix.ofValuesByRows(2,
                7d, 10d, 15d, 22d
            ),
            matrix.multiplyTolerant(matrix)
        );
    }

    // endregion

    // region matrix arithmetic: inverse, transpose and determinante

    @Test
    void inverseOfEmptyMatrixWichIsNoSquare() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new TestMatrix(1, 2).inverse()
        ); // assert exception message?
    }

    @Test
    void inverseOfEmptyMatrixWithSize2() {
        Assertions.assertNull(new TestMatrix(2, 0d).inverse());
    }

    @Test
    void inverseOfMatrixWithSize2() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(2,
            2, 5, 1, 3
        );
        TestMatrix result = TestMatrix.ofValuesByRows(2,
            3d, -5d, -1d, 2d
        );
        Assertions.assertEquals(result, matrix.inverse());
    }

    @Test
    void inverseOfMatrixWithSize3() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(3,
            3, 5, 1, 2, 4, 5, 1, 2, 2
        );
        TestMatrix result = TestMatrix.ofValuesByRows(3,
            2d, 8d, -21d, -1d, -5d, 13d, 0d, 1d, -2d
        );
        Assertions.assertEquals(result, matrix.inverse());
    }

    @Test
    void transposeOfEmptyMatrixWithSize2() {
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < result.size(); i++)
            result.setValue(i, 0d);
        Assertions.assertEquals(result, new TestMatrix(2).transpose());
    }

    @Test
    void transposeOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
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
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertEquals(0d, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize2AndValues1234() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        Assertions.assertEquals(-2d, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues123456789() {
        TestMatrix matrix = new TestMatrix(3);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        Assertions.assertEquals(0d, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues1And2OnDiagonale() {
        TestMatrix matrix = new TestMatrix(3);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? 2d : 1d);
        Assertions.assertEquals(4d, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1And2OnDiagonale() {
        TestMatrix matrix = new TestMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? 2d : 1d);
        Assertions.assertEquals(5d, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1AndMore0InRow() {
        TestMatrix matrix = new TestMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, c != 0 && (r == c || r == 0) ? 0d : 1d);
        Assertions.assertEquals(2d, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1AndMore0InCol() {
        TestMatrix matrix = new TestMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r != 0 && (r == c || c == 0) ? 0d : 1d);
        Assertions.assertEquals(2d, matrix.determinante());
    }

    // endregion

    // region static: identity and diagonal

    @Test
    void identityOfSize1() {
        TestMatrix matrix = TestMatrix.identity(1);
        Assertions.assertEquals(1, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            Assertions.assertEquals(1d, matrix.getValue(i, i), String.valueOf(i));
    }

    @Test
    void identityOfSize2() {
        TestMatrix matrix = TestMatrix.identity(2);
        Assertions.assertEquals(2 * 2, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            Assertions.assertEquals(1d, matrix.getValue(i, i), String.valueOf(i));
    }

    @Test
    void diagonalOfSize1() {
        TestMatrix matrix = TestMatrix.diagonal(1);
        Assertions.assertEquals(1, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            Assertions.assertEquals(1, matrix.getValue(i, i));
    }

    @Test
    void diagonalOfSize2() {
        TestMatrix matrix = TestMatrix.diagonal(2, 2);
        Assertions.assertEquals(2 * 2, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            Assertions.assertEquals(2, matrix.getValue(i, i));
    }

    // endregion

    // region static: of

    @Test
    void ofValuesByRows2WithInvalidParamCount() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> TestMatrix.ofValuesByRows(2, 1)
        );
    }

    @Test
    void ofValuesByRows2WithValidParamCount() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(2, 1, 2);
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
            () -> TestMatrix.ofValuesByCols(2, 1)
        );
    }

    @Test
    void ofValuesByCols2WithValidParamCount() {
        TestMatrix matrix = TestMatrix.ofValuesByCols(2, 1, 2);
        for (int i = 0; i < matrix.size(); i++)
            Assertions.assertEquals(
                i + 1,
                matrix.getValue(i),
                "index: " + i
            );
    }

    // endregion

    // region map and copy

    @Test
    void mapOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
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
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertEquals(matrix, matrix.copy());
    }

    // endregion

    // region override

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
    void streamOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertEquals(4, matrix.stream().count());
    }

    @Test
    void parallelStreamOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertEquals(4, matrix.parallelStream().count());
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

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(
            new TestMatrix(1),
            TestMatrix.class
        );
    }

    // endregion

    // region protected: subMatrix, coFactor and signumFactor

    @Test
    void subMatrixR0C0OfMatrixWithR1C2WichIsNoSquare() {
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
        Assertions.assertEquals(1,
            new TestMatrix(2).subMatrix(0, 0).size()
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

    @Test
    void coFactorR0C0OfMatrixWithR1C2WichIsNoSquare() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new TestMatrix(1, 2).coFactor(0, 0)
        ); // assert exception message?
    }

    @Test
    void coFactorR1C0OfMatrixWithSize1UsingInvalidRow1() {
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> new TestMatrix(1).coFactor(1, 0)
        ); // assert exception message?
    }

    @Test
    void coFactorR0C1OfMatrixWithSize1UsingInvalidCol1() {
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> new TestMatrix(1).coFactor(0, 1)
        ); // assert exception message?
    }

    @Test
    void coFactorsOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);

        Assertions.assertEquals(0d, matrix.coFactor(0, 0));
        Assertions.assertEquals(-2d, matrix.coFactor(0, 1));
        Assertions.assertEquals(-2d, matrix.coFactor(1, 0));
        Assertions.assertEquals(0d, matrix.coFactor(1, 1));
    }

    @Test
    void signumFactorOfR0C0() {
        Assertions.assertEquals(1, Matrix.signumFactor(0, 0));
    }

    @Test
    void signumFactorOfR0C1() {
        Assertions.assertEquals(-1, Matrix.signumFactor(0, 1));
    }

    @Test
    void signumFactorOfR1C0() {
        Assertions.assertEquals(-1, Matrix.signumFactor(1, 0));
    }

    @Test
    void signumFactorOfR1C1() {
        Assertions.assertEquals(1, Matrix.signumFactor(1, 1));
    }

    // endregion

    // region protected: modify

    @Test
    void swapRowsOfMatrixWithSize2UsingInvalidRow1() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.swapRows(-1, 0)
        ); // assert exception message?
    }

    @Test
    void swapRowsOfMatrixWithSize2UsingInvalidRow2() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.swapRows(0, -1)
        ); // assert exception message?
    }

    @Test
    void swapRowsOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1);
            result.setValue((i + result.getCols()) % (int) result.size(), i + 1);
        }
        Assertions.assertEquals(result, matrix.swapRows(0, 1));
    }

    @Test
    void swapColsOfMatrixWithSize2UsingInvalidCol1() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.swapCols(-1, 0)
        ); // assert exception message?
    }

    @Test
    void swapColsOfMatrixWithSize2UsingInvalidCol2() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.swapCols(0, -1)
        ); // assert exception message?
    }

    @Test
    void swapColsOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1);
            result.setValue(i + (i % result.getCols() == 0 ? 1 : -1), i + 1);
        }
        Assertions.assertEquals(result, matrix.swapCols(0, 1));
    }

    @Test
    void multiplyRowOfMatrixWithSize2UsingInvalidRow() {
        TestMatrix matrix = new TestMatrix(2);
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.multiplyRow(-1, 0)
        ); // assert exception message?
    }

    @Test
    void multiplyRowOfMatrixWithSize2Using0() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i / result.getCols() == 0 ? 0 : 1));
        }
        Assertions.assertEquals(result, matrix.multiplyRow(0, 0));
    }

    @Test
    void multiplyRowOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i / result.getCols() == 0 ? 1 : 1));
        }
        Assertions.assertEquals(result, matrix.multiplyRow(0, 1));
    }

    @Test
    void multiplyRowOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i / result.getCols() == 0 ? 2 : 1));
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
    void multiplyColOfMatrixWithSize2Using0() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i % result.getCols() == 0 ? 0 : 1));
        }
        Assertions.assertEquals(result, matrix.multiplyCol(0, 0));
    }

    @Test
    void multiplyColOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i % result.getCols() == 0 ? 1 : 1));
        }
        Assertions.assertEquals(result, matrix.multiplyCol(0, 1));
    }

    @Test
    void multiplyColOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i % result.getCols() == 0 ? 2d : 1));
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
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i / result.getCols() == 0 ? 2 : 1));
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 0, 2));
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
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 1, 0));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i, (double) value + (i / result.getCols() == 0 ? 2 : 0));
        }
        Assertions.assertEquals(result, matrix.addRowMultipleTimes(0, 1, 1));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i, (double) value + (i / result.getCols() == 0 ? 4 : 0));
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
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i % result.getCols() == 0 ? 2 : 1));
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 0, 2));
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
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 1, 0));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i, (double) value + (i % result.getCols() == 0 ? value : 0));
        }
        Assertions.assertEquals(result, matrix.addColMultipleTimes(0, 1, 1));
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
        Assertions.assertFalse(matrix.isIndexValid((int) matrix.size()));
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

        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.new Field((int) matrix.size(), 0)
        ); // assert exception message?
    }

    // endregion
}
