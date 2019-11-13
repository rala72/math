package io.rala.math.algebra;

import io.rala.math.utils.Copyable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.StreamSupport;

/**
 * class which holds a matrix with <code>rows</code> and <code>cols</code>
 *
 * @param <T> number class
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public abstract class Matrix<T extends Number> implements Copyable<Matrix<T>>, Iterable<Matrix<T>.Field> {
    protected static final String EXCEPTION_SIZE_PREFIX = "size: ";
    protected static final String EXCEPTION_ROW_PREFIX = "row: ";
    protected static final String EXCEPTION_COL_PREFIX = "col: ";

    // region attributes

    private final Map<Integer, T> matrix = new TreeMap<>();
    private final int rows;
    private final int cols;
    private final T defaultValue;

    // endregion

    // region constructor

    /**
     * calls {@link #Matrix(int, Number)} with given size and
     * default value <code>null</code>
     *
     * @param size size of square matrix
     */
    public Matrix(int size) {
        this(size, null);
    }

    /**
     * calls {@link #Matrix(int, int, Number)} with given values and
     * default value <code>null</code>
     *
     * @param rows rows of matrix
     * @param cols cols of matrix
     */
    public Matrix(int rows, int cols) {
        this(rows, cols, null);
    }

    /**
     * calls {@link #Matrix(int, int, Number)} with size as rows and cols and
     * given default value for non-existing values
     *
     * @param size         size of matrix
     * @param defaultValue default value of non-existing values
     */
    public Matrix(int size, T defaultValue) {
        this(size, size, defaultValue);
    }

    /**
     * creates a new matrix with given rows and cols
     * which uses given default value for non-existing values
     *
     * @param rows         rows of matrix
     * @param cols         cols of matrix
     * @param defaultValue default value of non-existing values
     */
    public Matrix(int rows, int cols, T defaultValue) {
        this.rows = rows;
        this.cols = cols;
        this.defaultValue = defaultValue;
    }

    /**
     * creates a new matrix based on given one
     *
     * @param matrix matrix to copy
     */
    protected Matrix(Matrix<T> matrix) {
        this(matrix.getRows(), matrix.getCols(), matrix.getDefaultValue());
        getMatrix().putAll(matrix.getMatrix());
    }

    // endregion

    // region getter and size

    /**
     * @return matrix map which uses index as key
     */
    protected final Map<Integer, T> getMatrix() {
        return matrix;
    }

    /**
     * @return rows of matrix
     */
    public final int getRows() {
        return rows;
    }

    /**
     * @return cols of matrix
     */
    public final int getCols() {
        return cols;
    }

    /**
     * @return default value for non-existing values
     */
    protected final T getDefaultValue() {
        return defaultValue;
    }

    /**
     * @return {@link #getRows()} * {@link #getCols()}
     */
    public final int size() {
        return getRows() * getCols();
    }

    // endregion

    // region value

    /**
     * @param row   row where value should be stored
     * @param col   col where value should be stored
     * @param value new value to store
     * @return old value if existed - may return <code>null</code> if it was empty
     * @throws IndexOutOfBoundsException if index is invalid
     * @see #setValue(int, Number)
     * @see Map#put(Object, Object)
     */
    public T setValue(int row, int col, T value) {
        return setValue(getIndexOfRowAndCol(row, col), value);
    }

    /**
     * @param index index where value should be stored
     * @param value new value to store
     * @return old value if existed - may return <code>null</code> if it was empty
     * @throws IndexOutOfBoundsException if index is invalid
     * @see Map#put(Object, Object)
     */
    public T setValue(int index, T value) {
        if (!isIndexValid(index))
            throw new IndexOutOfBoundsException(EXCEPTION_SIZE_PREFIX + size());
        return matrix.put(index, value);
    }

    /**
     * @param row row of requested value
     * @param col col of requested value
     * @return current value on given position
     * @throws IndexOutOfBoundsException if index is invalid
     * @see #getValue(int)
     * @see Map#getOrDefault(Object, Object)
     */
    public T getValue(int row, int col) {
        return getValue(getIndexOfRowAndCol(row, col));
    }

    /**
     * @param index index of requested value
     * @return current value on given position
     * @throws IndexOutOfBoundsException if index is invalid
     * @see Map#getOrDefault(Object, Object)
     */
    public T getValue(int index) {
        if (!isIndexValid(index))
            throw new IndexOutOfBoundsException(EXCEPTION_SIZE_PREFIX + size());
        return matrix.getOrDefault(index, getDefaultValue());
    }

    /**
     * @param row row of value to remove
     * @param col col of value to remove
     * @return old value - may return <code>null</code> if it was empty
     * @throws IndexOutOfBoundsException if index is invalid
     * @see #removeValue(int)
     * @see Map#remove(Object)
     */
    public T removeValue(int row, int col) {
        return removeValue(getIndexOfRowAndCol(row, col));
    }

    /**
     * @param index index of value to remove
     * @return old value - may return <code>null</code> if it was empty
     * @throws IndexOutOfBoundsException if index is invalid
     * @see Map#remove(Object)
     */
    public T removeValue(int index) {
        if (!isIndexValid(index))
            throw new IndexOutOfBoundsException(EXCEPTION_SIZE_PREFIX + size());
        return getMatrix().remove(index);
    }

    // endregion

    // region isDiagonal

    /**
     * @return <code>true</code> if only the diagonal has values
     */
    public boolean isDiagonal() {
        return getRows() == getCols() &&
            StreamSupport.stream(spliterator(), true)
                .allMatch(field -> field.getRow() == field.getCol() ||
                    field.getValue() == null ||
                    field.getValue().doubleValue() == 0d
                );
    }

    // endregion

    // region abstract

    /**
     * @param matrix matrix to add
     * @return new matrix with calculated values
     */
    public abstract Matrix<T> add(Matrix<T> matrix);

    /**
     * @param matrix matrix to multiply
     * @return new matrix with calculated values
     */
    public abstract Matrix<T> multiply(Matrix<T> matrix);

    /**
     * calls {@link #multiply(Matrix)} but flips matrizen
     * if they do not flip in provided order but in flipped one
     *
     * @param matrix matrix to multiply
     * @return new matrix with calculated values
     * @throws IllegalArgumentException if no cols and rows match
     * @see #multiply(Matrix)
     */
    public Matrix<T> multiplyTolerant(Matrix<T> matrix) {
        if (getCols() == matrix.getRows())
            return this.multiply(matrix);
        else if (getRows() == matrix.getCols())
            return matrix.multiplyTolerant(this);
        throw new IllegalArgumentException("any cols have to be equal to the other matrix rows");
    }

    // public abstract Matrix<T> inverse();

    /**
     * @return new transposed matrix
     */
    public abstract Matrix<T> transpose();

    /**
     * @return determinante of matrix
     */
    public abstract double determinante();

    // endregion

    // region override

    @Override
    public Iterator<Field> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public Field next() {
                T value = getValue(index);
                return new Field(index++, value);
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix<?> matrix1 = (Matrix<?>) o;
        return getRows() == matrix1.getRows() &&
            getCols() == matrix1.getCols() &&
            Objects.equals(getMatrix(), matrix1.getMatrix()) &&
            Objects.equals(getDefaultValue(), matrix1.getDefaultValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMatrix(), getRows(), getCols(), getDefaultValue());
    }

    @Override
    public String toString() {
        return getRows() + " " + getCols() + ": " + getMatrix().entrySet().toString();
    }

    // endregion

    // region protected: modify

    /**
     * @param row1 row1 to flip with row2
     * @param row2 row2 to flip with row1
     * @return matrix with flipped values
     * @throws IndexOutOfBoundsException if row1 or row2 is invalid
     */
    protected final Matrix<T> flipRows(int row1, int row2) {
        if (!isRowValid(row1))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row1);
        if (!isRowValid(row2))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row2);
        Matrix<T> copy = copy();
        if (row1 == row2) return copy;
        for (int c = 0; c < getCols(); c++) {
            copy.setValue(row1, c, getValue(row2, c));
            copy.setValue(row2, c, getValue(row1, c));
        }
        return copy;
    }

    /**
     * @param col1 col1 to flip with col2
     * @param col2 col2 to flip with col1
     * @return matrix with flipped values
     * @throws IndexOutOfBoundsException if col1 or col2 is invalid
     */
    protected final Matrix<T> flipCols(int col1, int col2) {
        if (!isColValid(col1))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col1);
        if (!isColValid(col2))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col2);
        Matrix<T> copy = copy();
        if (col1 == col2) return copy;
        for (int r = 0; r < getRows(); r++) {
            copy.setValue(r, col1, getValue(r, col2));
            copy.setValue(r, col2, getValue(r, col1));
        }
        return copy;
    }

    // endregion

    // region protected: getIndexOfRowAndCol and isValid

    /**
     * @param row row of requested index
     * @param col col of requested index
     * @return index of requested position
     */
    protected final int getIndexOfRowAndCol(int row, int col) {
        return row * getCols() + col;
    }

    /**
     * @param index index to check
     * @return <code>true</code> if value is valid
     */
    protected final boolean isIndexValid(int index) {
        return 0 <= index && index < size();
    }

    /**
     * @param row row to check
     * @return <code>true</code> if value is valid
     */
    protected final boolean isRowValid(int row) {
        return 0 <= row && row < getRows();
    }

    /**
     * @param col col to check
     * @return <code>true</code> if col is valid
     */
    protected final boolean isColValid(int col) {
        return 0 <= col && col < getCols();
    }

    // endregion

    /**
     * class which holds a field of a matrix
     */
    public class Field {
        private final int index;
        private final T value;

        /**
         * @param index index of field
         * @param value value of field
         */
        protected Field(int index, T value) {
            this.index = index;
            this.value = value;
        }

        /**
         * @return row of field
         */
        public final int getRow() {
            return getIndex() / getCols();
        }

        /**
         * @return col of field
         */
        public final int getCol() {
            return getIndex() % getCols();
        }

        /**
         * @return index of field
         */
        public final int getIndex() {
            return index;
        }

        /**
         * @return value of field
         */
        public T getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Matrix.Field field = (Matrix.Field) o;
            return getIndex() == field.getIndex() &&
                Objects.equals(getValue(), field.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getIndex(), getValue());
        }

        @Override
        public String toString() {
            return getIndex() + ": " + getValue();
        }
    }
}
