package io.rala.math.algebra;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * class which holds a matrix with <code>rows</code> and <code>cols</code>
 *
 * @param <T> number class
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public abstract class Matrix<T extends Number> implements Iterable<T> {
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
     * @return old value if existed
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
     * @return old value if existed
     * @throws IndexOutOfBoundsException if index is invalid
     * @see Map#put(Object, Object)
     */
    public T setValue(int index, T value) {
        if (index < 0 || size() <= index)
            throw new IndexOutOfBoundsException("size: " + size());
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
        if (index < 0 || size() <= index)
            throw new IndexOutOfBoundsException("size: " + size());
        return matrix.getOrDefault(index, getDefaultValue());
    }

    /**
     * @param row row of value to remove
     * @param col col of value to remove
     * @return old value
     * @throws IndexOutOfBoundsException if index is invalid
     * @see #removeValue(int)
     * @see Map#remove(Object)
     */
    public T removeValue(int row, int col) {
        return removeValue(getIndexOfRowAndCol(row, col));
    }

    /**
     * @param index index of value to remove
     * @return old value
     * @throws IndexOutOfBoundsException if index is invalid
     * @see Map#remove(Object)
     */
    public T removeValue(int index) {
        if (index < 0 || size() <= index)
            throw new IndexOutOfBoundsException("size: " + size());
        return getMatrix().remove(index);
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
     * @see #multiply(Matrix)
     */
    public Matrix<T> multiplyTolerant(Matrix<T> matrix) {
        if (getRows() == matrix.getCols())
            return this.multiply(matrix);
        else if (getCols() == matrix.getRows())
            return matrix.multiplyTolerant(this);
        throw new IllegalArgumentException("any rows have to be equal to the other cols");
    }

    // public abstract Matrix<T> inverse();

    /**
     * @return new transposed matrix
     */
    public abstract Matrix<T> transpose();

    // endregion

    // region override

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
        return getRows() + " " + getCols();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public T next() {
                return getValue(index++);
            }
        };
    }

    // endregion

    // region protected

    /**
     * @param row row of requested index
     * @param col col of requested index
     * @return index of requested position
     */
    protected final int getIndexOfRowAndCol(int row, int col) {
        return row * getCols() + col;
    }

    // endregion
}
