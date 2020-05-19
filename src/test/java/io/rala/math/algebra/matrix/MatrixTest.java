package io.rala.math.algebra.matrix;

import io.rala.math.algebra.matrix.typed.BigDecimalMatrix;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.algebra.TestMatrix;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.rala.math.testUtils.assertion.MatrixAssertions.assertMatrix;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    // region constructors

    @Test
    void constructorWithNegativeSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestMatrix(-1)
        ); // assert exception message?
    }

    @Test
    void constructorWithIntegerMaxValueSize() {
        TestMatrix matrix = new TestMatrix(Integer.MAX_VALUE);

        long expectedSize = (long) Integer.MAX_VALUE * Integer.MAX_VALUE;
        assertEquals(expectedSize, matrix.size());

        assertTrue(matrix.isIndexValid(expectedSize - 1));
        assertFalse(matrix.isIndexValid(expectedSize));
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
        assertEquals(2d, matrix.getDefaultValue());
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
        assertEquals(-2d, matrix.getDefaultValue());
    }

    @Test
    void constructorWithMatrix() {
        assertMatrix(new TestMatrix(new TestMatrix(1, 2)), 1, 2);
    }

    // endregion

    // region getter and size

    @Test
    void createWithSize1AndAssertSizeEquals1() {
        assertEquals(1, new TestMatrix(1).size());
    }

    @Test
    void createWithSize2AndAssertSizeEquals4() {
        assertEquals(4, new TestMatrix(2).size());
    }

    @Test
    void createWithRow1Col2AndAssertSizeEquals2() {
        assertEquals(2, new TestMatrix(1, 2).size());
    }

    @Test
    void createWithRow2Col3AndAssertSizeEquals6() {
        assertEquals(6, new TestMatrix(2, 3).size());
    }

    // endregion

    // region rows and cols

    @Test
    void getRowFieldsM1OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getRowFields(-1)
        ); // assert exception message?
    }

    @Test
    void getRowFields0OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        List<Matrix<Number>.Field> row0 = matrix.getRowFields(0);
        assertEquals(2, row0.size());
        for (int i = 0; i < row0.size(); i++) {
            Matrix<Number>.Field field = row0.get(i);
            assertEquals(0, field.getRow());
            assertEquals(i, field.getCol());
            assertEquals(i, field.getValue());
        }
    }

    @Test
    void getRow0OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        List<Number> row0 = matrix.getRow(0);
        assertEquals(2, row0.size());
        for (int i = 0; i < row0.size(); i++)
            assertEquals(i, row0.get(i));
    }

    @Test
    void getColFieldsM1OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getColFields(-1)
        ); // assert exception message?
    }

    @Test
    void getColFields0OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        List<Matrix<Number>.Field> col0 = matrix.getColFields(0);
        assertEquals(2, col0.size());
        for (int i = 0; i < col0.size(); i++) {
            Matrix<Number>.Field field = col0.get(i);
            assertEquals(0, field.getCol());
            assertEquals(i, field.getRow());
            assertEquals(i * 2, field.getValue());
        }
    }

    @Test
    void getCol0OfMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        List<Number> col0 = matrix.getCol(0);
        assertEquals(2, col0.size());
        for (int i = 0; i < col0.size(); i++)
            assertEquals(i * 2, col0.get(i));
    }

    // endregion

    // region value

    @Test
    void setValueByIndexMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.setValue(-1, 0d)
        ); // assert exception message?
    }

    @Test
    void setValueByIndex0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.setValue(0, 1));
        assertEquals(1, matrix.getValue(0));
        assertEquals(1, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByIndex3WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.setValue(3, 1));
        assertEquals(1, matrix.getValue(3));
        assertEquals(1, matrix.getValue(1, 1));
        // assert all other are unset
    }

    @Test
    void setValueByRowMinus1Col0() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.setValue(-1, 0, 0d)
        ); // assert exception message?
    }

    @Test
    void setValueByRow0ColMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.setValue(0, -1, 0d)
        ); // assert exception message?
    }

    @Test
    void setValueByRow0Col0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.setValue(0, 0, 1));
        assertEquals(1, matrix.getValue(0));
        assertEquals(1, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void setValueByRow1Col0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.setValue(1, 0, 1));
        assertEquals(1, matrix.getValue(1, 0));
        assertEquals(1, matrix.getValue(2));
        // assert all other are unset
    }

    @Test
    void setValueByIndex2WichWasSet() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(2, 1);
        assertEquals(1, matrix.getValue(2));
        assertEquals(1, matrix.setValue(2, 2));
    }

    @Test
    void getValueByIndexMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getValue(-1)
        ); // assert exception message?
    }

    @Test
    void getValueByRowMinus1Col0() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getValue(-1, 0)
        ); // assert exception message?
    }

    @Test
    void getValueByRow0ColMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.getValue(0, -1)
        ); // assert exception message?
    }

    @Test
    void removeValueByIndexMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.removeValue(-1)
        ); // assert exception message?
    }

    @Test
    void removeValueByIndex0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.removeValue(0));
    }

    @Test
    void removeValueByRowMinus1Col0() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.removeValue(-1, 0)
        ); // assert exception message?
    }

    @Test
    void removeValueByRow0ColMinus1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.removeValue(0, -1)
        ); // assert exception message?
    }

    @Test
    void removeValueByRow0Col0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.removeValue(0, 0));
    }

    @Test
    void removeValueByIndex2WhichWasSet() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(2, 1);
        assertEquals(1, matrix.removeValue(2));
    }

    // endregion

    // region compute

    @Test
    void computeWithUnaryOperatorByIndex0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.compute(0, number -> 1));
        assertEquals(1, matrix.getValue(0));
        assertEquals(1, matrix.getValue(0, 0));
    }

    @Test
    void computeWithUnaryOperatorByIndex3WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.compute(3, number -> 1));
        assertEquals(1, matrix.getValue(3));
        assertEquals(1, matrix.getValue(1, 1));
    }

    @Test
    void computeWithUnaryOperatorByRow0Col0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.compute(0, 0, number -> 1));
        assertEquals(1, matrix.getValue(0));
        assertEquals(1, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void computeWithUnaryOperatorByRow1Col1WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.compute(1, 0, number -> 1));
        assertEquals(1, matrix.getValue(1, 0));
        assertEquals(1, matrix.getValue(2));
        // assert all other are unset
    }

    @Test
    void computeWithBinaryOperatorByIndex0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.compute(0,
            1d, matrix.getArithmetic()::sum
        ));
        assertEquals(1d, matrix.getValue(0));
        assertEquals(1d, matrix.getValue(0, 0));
    }

    @Test
    void computeWithBinaryOperatorByIndex3WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.compute(3,
            1d, matrix.getArithmetic()::sum
        ));
        assertEquals(1d, matrix.getValue(3));
        assertEquals(1d, matrix.getValue(1, 1));
    }

    @Test
    void computeWithBinaryOperatorByRow0Col0WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.compute(0, 0,
            1d, matrix.getArithmetic()::sum
        ));
        assertEquals(1d, matrix.getValue(0));
        assertEquals(1d, matrix.getValue(0, 0));
        // assert all other are unset
    }

    @Test
    void computeWithBinaryOperatorByRow1Col1WhichWasUnset() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.compute(1, 0,
            1d, matrix.getArithmetic()::sum
        ));
        assertEquals(1d, matrix.getValue(1, 0));
        assertEquals(1d, matrix.getValue(2));
        // assert all other are unset
    }

    @Test
    void computeAllWithFunctionOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.computeAll(field ->
            matrix.getArithmetic().sum(field.getValue(), 1)
        );
        assertTrue(matrix.stream().allMatch(field ->
            matrix.getArithmetic().isEqual(field.getValue(), 1d)
        ));
    }

    @Test
    void computeAllWithFunctionAndBinaryOperatorOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.computeAll(field -> 1, matrix.getArithmetic()::sum);
        assertTrue(matrix.stream().allMatch(field ->
            matrix.getArithmetic().isEqual(field.getValue(), 1d)
        ));
    }

    // endregion

    // region isSquare, isDiagonal and isInvertible

    @Test
    void isSquareOfMatrixWithRow1Col2() {
        assertFalse(new TestMatrix(1, 2).isDiagonal());
    }

    @Test
    void isSquareOfMatrixWithSize2() {
        assertTrue(new TestMatrix(2).isDiagonal());
    }

    @Test
    void isDiagonalOfMatrixWithRow1Col2() {
        assertFalse(new TestMatrix(1, 2).isDiagonal());
    }

    @Test
    void isDiagonalOfMatrixOfValidValues() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(0, 1);
        matrix.setValue(1, 0);
        matrix.setValue(2, 0);
        matrix.setValue(3, 4);
        assertTrue(matrix.isDiagonal());
    }

    @Test
    void isDiagonalOfMatrixOfInvalidValues() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(0, 1);
        matrix.setValue(1, 2);
        matrix.setValue(2, 3);
        matrix.setValue(3, 4);
        assertFalse(matrix.isDiagonal());
    }

    @Test
    void isInvertibleOfMatrixWithSize2AndDeterminante0() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(0, 1);
        matrix.setValue(1, 2);
        matrix.setValue(2, 2);
        matrix.setValue(3, 4);
        assertTrue(matrix.isSquare());
        assertTrue(matrix.getArithmetic().isZero(matrix.determinante()));
        assertFalse(matrix.isInvertible());
    }

    @Test
    void isInvertibleOfMatrixWithSize2AndDeterminanteNon0() {
        TestMatrix matrix = new TestMatrix(2);
        matrix.setValue(0, 1);
        matrix.setValue(1, 0);
        matrix.setValue(2, 0);
        matrix.setValue(3, 4);
        assertTrue(matrix.isSquare());
        assertFalse(matrix.getArithmetic().isZero(matrix.determinante()));
        assertTrue(matrix.isInvertible());
    }

    // endregion

    // region matrix arithmetic: add and multiply

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows2Cols1() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestMatrix(1).add(new TestMatrix(2, 1))
        ); // assert exception message?
    }

    @Test
    void addOfEmptyMatrixWithSize1AndEmptyMatrixWithRows1Cols2() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestMatrix(1)
                .add(new TestMatrix(1, 2))
        ); // assert exception message?
    }

    @Test
    void addOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        TestMatrix matrix1 = new TestMatrix(2);
        TestMatrix matrix2 = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        assertEquals(result, matrix1.add(matrix2));
    }

    @Test
    void addOfEmptyMatrixWithSize2AndDefault0AndEmptyMatrixWithSize2AndDefault1() {
        TestMatrix matrix1 = new TestMatrix(2);
        TestMatrix matrix2 = new TestMatrix(2, 1d);
        TestMatrix result = new TestMatrix(2, 1d);
        assertEquals(result, matrix1.add(matrix2));
    }

    @Test
    void addOfMatrixWithSize2AndDefault0ToItself() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, 2 * (i + 1d));
        }
        assertEquals(result, matrix.add(matrix));
    }

    @Test
    void addOfMatrixWithSize2AndDefault2ToItself() {
        TestMatrix matrix = new TestMatrix(2, 2d);
        TestMatrix result = new TestMatrix(2, 2d);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, 2 * (i + 1d));
        }
        assertEquals(result, matrix.add(matrix));
    }

    @Test
    void multiplyOfEmptyMatrixWithSize2With2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        assertEquals(result, matrix.multiply(2));
    }

    @Test
    void multiplyMatrixWithSize2AndDefault0With2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < result.size(); i++) {
            matrix.setValue(i, (i + 1));
            result.setValue(i, (i + 1d) * 2);
        }
        assertEquals(result, matrix.multiply(2));
    }

    @Test
    void multiplyMatrixWithSize2AndDefault2With2() {
        TestMatrix matrix = new TestMatrix(2, 2d);
        TestMatrix result = new TestMatrix(2, 2d);
        for (int i = 0; i < result.size(); i++) {
            matrix.setValue(i, (i + 1));
            result.setValue(i, (i + 1d) * 2);
        }
        assertEquals(result, matrix.multiply(2));
    }

    @Test
    void multiplyOfEmptyMatrixWithSize2AndEmptyMatrixWithSize2() {
        TestMatrix matrix1 = new TestMatrix(2);
        TestMatrix matrix2 = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        assertEquals(result, matrix1.multiply(matrix2));
    }

    @Test
    void multiplyOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        TestMatrix matrix1 = new TestMatrix(1, 2);
        TestMatrix matrix2 = new TestMatrix(2, 3);
        TestMatrix result = new TestMatrix(1, 3);
        assertEquals(result, matrix1.multiply(matrix2));
    }

    @Test
    void multiplyOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        TestMatrix matrix1 = new TestMatrix(2, 3);
        TestMatrix matrix2 = new TestMatrix(1, 2);
        assertThrows(IllegalArgumentException.class,
            () -> matrix1.multiply(matrix2)
        ); // assert exception message?
    }

    @Test
    void multiplyOfMatrixWithSize2ToItself() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        assertEquals(TestMatrix.ofValuesByRows(2,
            7d, 10d, 15d, 22d
        ), matrix.multiply(matrix));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow2Col3() {
        TestMatrix matrix1 = new TestMatrix(1, 2);
        TestMatrix matrix2 = new TestMatrix(2, 3);
        TestMatrix result = new TestMatrix(1, 3);
        assertEquals(result, matrix1.multiplyTolerant(matrix2));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow2Col3AndEmptyMatrixWithRow1Col2() {
        TestMatrix matrix1 = new TestMatrix(2, 3);
        TestMatrix matrix2 = new TestMatrix(1, 2);
        TestMatrix result = new TestMatrix(1, 3);
        assertEquals(result, matrix1.multiplyTolerant(matrix2));
    }

    @Test
    void multiplyTolerantOfEmptyMatrixWithRow1Col2AndEmptyMatrixWithRow3Col4() {
        TestMatrix matrix1 = new TestMatrix(1, 2);
        TestMatrix matrix2 = new TestMatrix(3, 4);
        assertThrows(IllegalArgumentException.class,
            () -> matrix1.multiplyTolerant(matrix2)
        );
    }

    @Test
    void multiplyTolerantOfMatrixWithSize2ToItself() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        assertEquals(
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
        assertThrows(IllegalArgumentException.class,
            () -> new TestMatrix(1, 2).inverse()
        ); // assert exception message?
    }

    @Test
    void inverseOfEmptyMatrixWithSize2() {
        assertNull(new TestMatrix(2, 0d).inverse());
    }

    @Test
    void inverseOfMatrixWithSize2() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(2,
            2, 5, 1, 3
        );
        TestMatrix result = TestMatrix.ofValuesByRows(2,
            3d, -5d, -1d, 2d
        );
        assertEquals(result, matrix.inverse());
    }

    @Test
    void inverseOfMatrixWithSize3() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(3,
            3, 5, 1, 2, 4, 5, 1, 2, 2
        );
        TestMatrix result = TestMatrix.ofValuesByRows(3,
            2d, 8d, -21d, -1d, -5d, 13d, 0d, 1d, -2d
        );
        assertEquals(result, matrix.inverse());
    }

    @Test
    void transposeOfEmptyMatrixWithSize2() {
        TestMatrix result = new TestMatrix(2);
        assertEquals(result, new TestMatrix(2).transpose());
    }

    @Test
    void transposeOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++) {
                int i = (int) matrix.getIndexOfRowAndCol(r, c);
                matrix.setValue(i, i + 1d);
                result.setValue(result.getIndexOfRowAndCol(c, r), i + 1d);
            }
        assertEquals(result, matrix.transpose());
    }

    @Test
    void determinanteOfEmptyMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(0d, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize2AndValues1234() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        assertEquals(-2d, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues123456789() {
        TestMatrix matrix = new TestMatrix(3);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i + 1d);
        assertEquals(0d, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize3AndValues1And2OnDiagonale() {
        TestMatrix matrix = new TestMatrix(3);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? 2d : 1d);
        assertEquals(4d, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1And2OnDiagonale() {
        TestMatrix matrix = new TestMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r == c ? 2d : 1d);
        assertEquals(5d, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1AndMore0InRow() {
        TestMatrix matrix = new TestMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, c != 0 && (r == c || r == 0) ? 0d : 1d);
        assertEquals(2d, matrix.determinante());
    }

    @Test
    void determinanteOfMatrixWithSize4AndValues1AndMore0InCol() {
        TestMatrix matrix = new TestMatrix(4);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                matrix.setValue(r, c, r != 0 && (r == c || c == 0) ? 0d : 1d);
        assertEquals(2d, matrix.determinante());
    }

    // endregion

    // region static: identity and diagonal

    @Test
    void identityOfSize1() {
        TestMatrix matrix = TestMatrix.identity(1);
        assertEquals(1, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertEquals(1d, matrix.getValue(i, i), String.valueOf(i));
    }

    @Test
    void identityOfSize2() {
        TestMatrix matrix = TestMatrix.identity(2);
        assertEquals(2 * 2, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertEquals(1d, matrix.getValue(i, i), String.valueOf(i));
    }

    @Test
    void diagonalOfSize1() {
        TestMatrix matrix = TestMatrix.diagonal(1);
        assertEquals(1, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertEquals(1, matrix.getValue(i, i));
    }

    @Test
    void diagonalOfSize2() {
        TestMatrix matrix = TestMatrix.diagonal(2, 2);
        assertEquals(2 * 2, matrix.size());
        for (int i = 0; i < Math.sqrt(matrix.size()); i++)
            assertEquals(2, matrix.getValue(i, i));
    }

    // endregion

    // region static: of

    @Test
    void ofValuesByRows2WithInvalidParamCount() {
        assertThrows(IllegalArgumentException.class,
            () -> TestMatrix.ofValuesByRows(2, 1)
        );
    }

    @Test
    void ofValuesByRows2WithValidParamCount() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(2, 1, 2);
        for (int i = 0; i < matrix.size(); i++)
            assertEquals(i + 1, matrix.getValue(i), "index: " + i);
    }

    @Test
    void ofValuesByCols2WithInvalidParamCount() {
        assertThrows(IllegalArgumentException.class,
            () -> TestMatrix.ofValuesByCols(2, 1)
        );
    }

    @Test
    void ofValuesByCols2WithValidParamCount() {
        TestMatrix matrix = TestMatrix.ofValuesByCols(2, 1, 2);
        for (int i = 0; i < matrix.size(); i++)
            assertEquals(i + 1, matrix.getValue(i), "index: " + i);
    }

    // endregion

    // region map and copy

    @Test
    void mapOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        Matrix<Integer> result =
            new Matrix<>(new IntegerArithmetic(), 2);
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++) {
                matrix.setValue(r, c, r + c + 0.5);
                result.setValue(r, c, r + c);
            }

        assertEquals(result,
            matrix.map(new IntegerArithmetic(), Number::intValue)
        );
    }

    @Test
    void mapDefaultValueOfIdentityMatrixWithSize2() {
        TestMatrix matrix = TestMatrix.identity(2);
        Matrix<Number> mapped = matrix.mapDefaultValue(1d);
        assertEquals(matrix, mapped);
        matrix.forEach(field -> {
            if (field.getRow() == field.getCol()) {
                assertTrue(
                    matrix.getMatrix().get(field.getRow())
                        .containsKey(field.getCol())
                );
                assertFalse(
                    mapped.getMatrix().get(field.getRow())
                        .containsKey(field.getCol())
                );
            } else {
                assertFalse(
                    matrix.getMatrix().get(field.getRow())
                        .containsKey(field.getCol())
                );
                assertTrue(
                    mapped.getMatrix().get(field.getRow())
                        .containsKey(field.getCol())
                );
            }
        });
    }

    @Test
    void mapDefaultValueOfMatrixBy2Rows() {
        TestMatrix matrix = TestMatrix.ofValuesByRows(2, 0d, 0d, 2d, 2d);
        Matrix<Number> mapped = matrix.mapDefaultValue(2d);
        assertEquals(matrix, mapped);
        matrix.forEach(field -> {
            if (field.getRow() == 0) {
                assertFalse(
                    matrix.getMatrix().containsKey(field.getRow())
                );
                assertTrue(
                    mapped.getMatrix().get(field.getRow())
                        .containsKey(field.getCol())
                );
            } else {
                assertTrue(
                    matrix.getMatrix().get(field.getRow())
                        .containsKey(field.getCol())
                );
                assertFalse(
                    mapped.getMatrix().containsKey(field.getRow())
                );
            }
        });
    }

    @Test
    void copyOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(matrix, matrix.copy());
    }

    // endregion

    // region override

    @Test
    void iteratorOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        List<TestMatrix.Field> values = new ArrayList<>();
        for (TestMatrix.Field d : matrix) {
            values.add(d);
            assertEquals(0d, d.getValue());
        }
        assertEquals(matrix.size(), values.size());
    }

    @Test
    void streamOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(4, matrix.stream().count());
    }

    @Test
    void parallelStreamOfEmptyMatrix() {
        TestMatrix matrix = new TestMatrix(2);
        assertEquals(4, matrix.parallelStream().count());
    }

    @Test
    void equalsOfTestMatrixWithRow2Col3() {
        TestMatrix matrix = new TestMatrix(2, 3);
        assertEquals(matrix, new TestMatrix(2, 3));
        assertNotEquals(matrix, new TestMatrix(3, 2));
    }

    @Test
    void equalsOfTestMatrixWithDifferentDefaults() {
        TestMatrix default0 = new TestMatrix(2);
        default0.forEach(field -> default0.setValue(field.getIndex(), 1d));
        assertNotEquals(default0, new TestMatrix(2));
        assertEquals(default0, new TestMatrix(2, 1d));
    }

    @Test
    void equalsOfTestMatrixAndBigDecimalMatrix() {
        assertNotEquals(new TestMatrix(2), new BigDecimalMatrix(2));
    }

    @Test
    void hashCodeOfTestMatrixWithRow2Col3() {
        assertEquals(925536, new TestMatrix(2, 3).hashCode());
    }

    @Test
    void toStringOfTestMatrixWithRow2Col3() {
        TestMatrix matrix = new TestMatrix(2, 3);
        assertEquals("2 3: []", matrix.toString());
    }

    @Test
    void serializable() {
        assertSerializable(new TestMatrix(1), TestMatrix.class);
    }

    // endregion

    // region protected: subMatrix, coFactor, coFactorMatrix and signumFactor

    @Test
    void subMatrixR0C0OfMatrixWithR1C2WichIsNoSquare() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestMatrix(1, 2).subMatrix(0, 0)
        ); // assert exception message?
    }

    @Test
    void subMatrixR1C0OfMatrixWithSize1UsingInvalidRow1() {
        assertThrows(IndexOutOfBoundsException.class,
            () -> new TestMatrix(1).subMatrix(1, 0)
        ); // assert exception message?
    }

    @Test
    void subMatrixR0C1OfMatrixWithSize1UsingInvalidCol1() {
        assertThrows(IndexOutOfBoundsException.class,
            () -> new TestMatrix(1).subMatrix(0, 1)
        ); // assert exception message?
    }

    @Test
    void subMatrixR0C0OfMatrixWithSize1() {
        assertEquals(1, new TestMatrix(2).subMatrix(0, 0).size());
    }

    @Test
    void subMatrixR0C0OfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        Matrix<Number> subMatrix = matrix.subMatrix(0, 0);
        assertEquals(1, subMatrix.size());
        assertEquals(3, subMatrix.getValue(0));
    }

    @Test
    void subMatrixR0C1OfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        Matrix<Number> subMatrix = matrix.subMatrix(0, 1);
        assertEquals(1, subMatrix.size());
        assertEquals(2, subMatrix.getValue(0));
    }

    @Test
    void subMatrixR1C0OfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        Matrix<Number> subMatrix = matrix.subMatrix(1, 0);
        assertEquals(1, subMatrix.size());
        assertEquals(1, subMatrix.getValue(0));
    }

    @Test
    void subMatrixR1C1OfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, i);
        Matrix<Number> subMatrix = matrix.subMatrix(1, 1);
        assertEquals(1, subMatrix.size());
        assertEquals(0, subMatrix.getValue(0));
    }

    @Test
    void coFactorR0C0OfMatrixWithR1C2WichIsNoSquare() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestMatrix(1, 2).coFactor(0, 0)
        ); // assert exception message?
    }

    @Test
    void coFactorR1C0OfMatrixWithSize1UsingInvalidRow1() {
        assertThrows(IndexOutOfBoundsException.class,
            () -> new TestMatrix(1).coFactor(1, 0)
        ); // assert exception message?
    }

    @Test
    void coFactorR0C1OfMatrixWithSize1UsingInvalidCol1() {
        assertThrows(IndexOutOfBoundsException.class,
            () -> new TestMatrix(1).coFactor(0, 1)
        ); // assert exception message?
    }

    @Test
    void coFactorsOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);

        assertEquals(0d, matrix.coFactor(0, 0));
        assertEquals(-2d, matrix.coFactor(0, 1));
        assertEquals(-2d, matrix.coFactor(1, 0));
        assertEquals(0d, matrix.coFactor(1, 1));
    }

    @Test
    void coFactorMatrixOfMatrixWhichIsNoSquare() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestMatrix(1, 2).coFactorMatrix()
        ); // assert exception message?
    }

    @Test
    void coFactorMatrixOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);

        Matrix<Number> coFactorMatrix = matrix.coFactorMatrix();
        assertEquals(0d, coFactorMatrix.getValue(0, 0));
        assertEquals(-2d, coFactorMatrix.getValue(0, 1));
        assertEquals(-2d, coFactorMatrix.getValue(1, 0));
        assertEquals(0d, coFactorMatrix.getValue(1, 1));
    }

    @Test
    void adjunctMatrixOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++)
            matrix.setValue(i, (double) i);

        Matrix<Number> coFactorMatrix = matrix.adjunctMatrix();
        assertEquals(0d, coFactorMatrix.getValue(0, 0));
        assertEquals(-2d, coFactorMatrix.getValue(0, 1));
        assertEquals(-2d, coFactorMatrix.getValue(1, 0));
        assertEquals(0d, coFactorMatrix.getValue(1, 1));
    }

    @Test
    void signumFactorOfR0C0() {
        assertEquals(1, Matrix.signumFactor(0, 0));
    }

    @Test
    void signumFactorOfR0C1() {
        assertEquals(-1, Matrix.signumFactor(0, 1));
    }

    @Test
    void signumFactorOfR1C0() {
        assertEquals(-1, Matrix.signumFactor(1, 0));
    }

    @Test
    void signumFactorOfR1C1() {
        assertEquals(1, Matrix.signumFactor(1, 1));
    }

    // endregion

    // region protected: modify

    @Test
    void swapRowsOfMatrixWithSize2UsingInvalidRow1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.swapRows(-1, 0)
        ); // assert exception message?
    }

    @Test
    void swapRowsOfMatrixWithSize2UsingInvalidRow2() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
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
        assertEquals(result, matrix.swapRows(0, 1));
    }

    @Test
    void swapColsOfMatrixWithSize2UsingInvalidCol1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.swapCols(-1, 0)
        ); // assert exception message?
    }

    @Test
    void swapColsOfMatrixWithSize2UsingInvalidCol2() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
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
        assertEquals(result, matrix.swapCols(0, 1));
    }

    @Test
    void multiplyRowOfMatrixWithSize2UsingInvalidRow() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
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
        assertEquals(result, matrix.multiplyRow(0, 0d));
    }

    @Test
    void multiplyRowOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i / result.getCols() == 0 ? 1 : 1));
        }
        assertEquals(result, matrix.multiplyRow(0, 1d));
    }

    @Test
    void multiplyRowOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i / result.getCols() == 0 ? 2 : 1));
        }
        assertEquals(result, matrix.multiplyRow(0, 2d));
    }

    @Test
    void multiplyColOfMatrixWithSize2UsingInvalidCol() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
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
        assertEquals(result, matrix.multiplyCol(0, 0d));
    }

    @Test
    void multiplyColOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i % result.getCols() == 0 ? 1 : 1));
        }
        assertEquals(result, matrix.multiplyCol(0, 1d));
    }

    @Test
    void multiplyColOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.setValue(i, i + 1d);
            result.setValue(i, (i + 1d) * (i % result.getCols() == 0 ? 2d : 1));
        }
        assertEquals(result, matrix.multiplyCol(0, 2d));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2UsingInvalidRow1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addRowMultipleTimes(-1, 0, 0)
        ); // assert exception message?
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2UsingInvalidRow2() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
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
        assertEquals(result, matrix.addRowMultipleTimes(0, 0, 2));
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
        assertEquals(result, matrix.addRowMultipleTimes(0, 1, 0d));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i,
                (double) value + (i / result.getCols() == 0 ? 2 : 0)
            );
        }
        assertEquals(result, matrix.addRowMultipleTimes(0, 1, 1d));
    }

    @Test
    void addRowMultipleTimesOfMatrixWithSize2Using2() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i,
                (double) value + (i / result.getCols() == 0 ? 4 : 0)
            );
        }
        assertEquals(result, matrix.addRowMultipleTimes(0, 1, 2d));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2UsingInvalidCol1() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.addColMultipleTimes(-1, 0, 1)
        ); // assert exception message?
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2UsingInvalidCol2() {
        TestMatrix matrix = new TestMatrix(2);
        assertThrows(IndexOutOfBoundsException.class,
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
        assertEquals(result, matrix.addColMultipleTimes(0, 0, 2));
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
        assertEquals(result, matrix.addColMultipleTimes(0, 1, 0d));
    }

    @Test
    void addColMultipleTimesOfMatrixWithSize2Using1() {
        TestMatrix matrix = new TestMatrix(2);
        TestMatrix result = new TestMatrix(2);
        for (int i = 0; i < matrix.size(); i++) {
            int value = i / result.getCols() + 1;
            matrix.setValue(i, (double) value);
            result.setValue(i,
                (double) value + (i % result.getCols() == 0 ? value : 0)
            );
        }
        assertEquals(result, matrix.addColMultipleTimes(0, 1, 1d));
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
        assertEquals(result, matrix.addColMultipleTimes(0, 1, 2d));
    }

    // endregion

    // region protected: getIndexOfRowAndCol, isDefaultValue and isValid

    @Test
    void getIndexOfRowAndColOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        int index = 0;
        for (int r = 0; r < matrix.getRows(); r++)
            for (int c = 0; c < matrix.getCols(); c++)
                assertEquals(index++, matrix.getIndexOfRowAndCol(r, c));
    }

    @Test
    void isDefaultValueOfMatrixWithDefaultValueNull() {
        TestMatrix matrix = new TestMatrix(1, null);
        assertTrue(matrix.isDefaultValue(null));
        assertFalse(matrix.isDefaultValue(0));
        assertFalse(matrix.isDefaultValue(1));
    }

    @Test
    void isDefaultValueOfMatrixWithDefaultValue1() {
        TestMatrix matrix = new TestMatrix(1, 1d);
        assertFalse(matrix.isDefaultValue(null));
        assertFalse(matrix.isDefaultValue(0));
        assertTrue(matrix.isDefaultValue(1d));
    }

    @Test
    void isIndexValidOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        assertFalse(matrix.isIndexValid(-1));
        for (int i = 0; i < matrix.size(); i++)
            assertTrue(matrix.isIndexValid(i));
        assertFalse(matrix.isIndexValid((int) matrix.size()));
    }

    @Test
    void isRowValidOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        assertFalse(matrix.isRowValid(-1));
        for (int i = 0; i < matrix.getRows(); i++)
            assertTrue(matrix.isRowValid(i));
        assertFalse(matrix.isRowValid(matrix.getRows()));
    }

    @Test
    void isColValidOfMatrixWithSize2() {
        TestMatrix matrix = new TestMatrix(2);
        assertFalse(matrix.isColValid(-1));
        for (int i = 0; i < matrix.getCols(); i++)
            assertTrue(matrix.isColValid(i));
        assertFalse(matrix.isColValid(matrix.getCols()));
    }

    // endregion

    // region field

    @Test
    void fieldGetter() {
        TestMatrix matrix = new TestMatrix(2);
        int index = 0;
        for (Matrix<Number>.Field field : matrix) {
            assertEquals(index, field.getIndex());
            assertEquals(index / matrix.getCols(), field.getRow());
            assertEquals(index % matrix.getCols(), field.getCol());
            assertEquals(matrix, field.getMatrix());
            index++;
        }
    }

    @Test
    void fieldOverride() {
        TestMatrix matrix = new TestMatrix(2);
        Matrix<Number>.Field previous = null;
        for (Matrix<Number>.Field field : matrix) {
            if (previous != null) assertNotEquals(previous, field);
            else assertEquals(
                matrix.new Field(field.getIndex(), field.getValue()),
                field
            );
            assertTrue(0 < field.hashCode());
            assertEquals(
                field.getIndex() + ": " + field.getValue(),
                field.toString()
            );
            previous = field;
        }

        assertThrows(IndexOutOfBoundsException.class,
            () -> matrix.new Field((int) matrix.size(), 0)
        ); // assert exception message?
    }

    // endregion
}
