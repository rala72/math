package io.rala.math.algebra;

import io.rala.math.utils.Copyable;
import io.rala.math.utils.arithmetic.AbstractArithmetic;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * class which holds a matrix with <code>rows</code> and <code>cols</code>
 *
 * @param <T> number class
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class Matrix<T extends Number>
    implements Copyable<Matrix<T>>, Iterable<Matrix<T>.Field> {
    protected static final String EXCEPTION_SIZE_PREFIX = "size: ";
    protected static final String EXCEPTION_ROW_PREFIX = "row: ";
    protected static final String EXCEPTION_COL_PREFIX = "col: ";
    protected static final String EXCEPTION_ANY_COLS_EQUALS_OTHER_ROWS =
        "any cols have to be equal to the other matrix rows";
    protected static final String EXCEPTION_COLS_EQUALS_PARAM_ROWS =
        "cols have to be equal to parameter rows";
    protected static final String EXCEPTION_ROWS_NOT_CONGRUENT_0 =
        "rows modulo values.length is not congruent 0";
    protected static final String EXCEPTION_COLS_NOT_CONGRUENT_0 =
        "cols modulo values.length is not congruent 0";

    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private final Map<Integer, T> matrix = new TreeMap<>();
    private final int rows;
    private final int cols;
    private final T defaultValue;

    // endregion

    // region constructor and newInstance

    /**
     * calls {@link #Matrix(AbstractArithmetic, int, int, Number)}
     * with size as rows and cols and
     * given default value for non-existing values
     *
     * @param arithmetic   arithmetic for calculations
     * @param size         size of matrix
     * @param defaultValue default value of non-existing values
     */
    public Matrix(AbstractArithmetic<T> arithmetic, int size, T defaultValue) {
        this(arithmetic, size, size, defaultValue);
    }

    /**
     * creates a new matrix with given rows and cols
     * which uses given default value for non-existing values
     *
     * @param arithmetic   arithmetic for calculations
     * @param rows         rows of matrix
     * @param cols         cols of matrix
     * @param defaultValue default value of non-existing values
     */
    public Matrix(AbstractArithmetic<T> arithmetic, int rows, int cols, T defaultValue) {
        this.arithmetic = arithmetic;
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
        this(
            matrix.getArithmetic(),
            matrix.getRows(), matrix.getCols(),
            matrix.getDefaultValue()
        );
        getMatrix().putAll(matrix.getMatrix());
    }

    /**
     * calls {@link #newInstance(int, int)} with size as rows and cols
     *
     * @param size size of new matrix
     * @return new matrix instance
     * @see #newInstance(int, int)
     */
    protected final Matrix<T> newInstance(int size) {
        return newInstance(size, size);
    }

    /**
     * creates a new instance of a matrix of current type
     *
     * @param rows rows of new matrix
     * @param cols cols of new matrix
     * @return new matrix instance
     */
    protected final Matrix<T> newInstance(int rows, int cols) {
        return new Matrix<>(getArithmetic(), rows, cols, getDefaultValue());
    }

    // endregion

    // region getter and size

    /**
     * @return current arithmetic
     */
    protected AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

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
        return value == null && getDefaultValue() == null ||
            value != null && value.equals(getDefaultValue()) ?
            removeValue(index) : matrix.put(index, value);
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
     * @return <code>true</code> if amount of rows and cols is equal
     */
    public final boolean isSquare() {
        return getRows() == getCols();
    }

    /**
     * @return <code>true</code> if only the diagonal has values
     */
    public final boolean isDiagonal() {
        return isSquare() && StreamSupport.stream(spliterator(), true)
            .allMatch(field -> field.getRow() == field.getCol() ||
                field.getValue() == null ||
                field.getValue().doubleValue() == 0d
            );
    }

    // endregion

    // region matrix arithmetic

    /**
     * @param matrix matrix to add
     * @return new matrix with calculated values
     */
    public Matrix<T> add(Matrix<T> matrix) {
        if (getRows() != matrix.getRows())
            throw new IllegalArgumentException("rows have to be equal");
        if (getCols() != matrix.getCols())
            throw new IllegalArgumentException("cols have to be equal");
        Matrix<T> result = copy();
        for (int i = 0; i < size(); i++)
            result.getMatrix().merge(i, matrix.getValue(i), getArithmetic()::sum);
        result.removeDefaultValues();
        return result;
    }

    /**
     * @param matrix matrix to multiply
     * @return new matrix with calculated values
     */
    public Matrix<T> multiply(Matrix<T> matrix) {
        if (getCols() != matrix.getRows())
            throw new IllegalArgumentException(EXCEPTION_COLS_EQUALS_PARAM_ROWS);
        Matrix<T> result = newInstance(getRows(), matrix.getCols());
        for (int r = 0; r < result.getRows(); r++)
            for (int c = 0; c < result.getCols(); c++) {
                T d = getDefaultValue();
                for (int i = 0; i < getRows(); i++)
                    d = getArithmetic().sum(d,
                        getArithmetic().product(getValue(r, i), matrix.getValue(i, c))
                    );
                result.setValue(r, c, d);
            }
        return result;
    }

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
        throw new IllegalArgumentException(EXCEPTION_ANY_COLS_EQUALS_OTHER_ROWS);
    }

    // public Matrix<T> inverse();

    /**
     * @return new transposed matrix
     */
    public Matrix<T> transpose() {
        Matrix<T> result = newInstance(getCols(), getRows());
        for (int r = 0; r < getRows(); r++)
            for (int c = 0; c < getCols(); c++)
                result.setValue(c, r, getValue(getIndexOfRowAndCol(r, c)));
        return result;
    }

    /**
     * @return determinante of matrix
     */
    public T determinante() {
        if (size() == 0 || !isSquare()) return getDefaultValue();
        if (getRows() == 1) return getValue(0);
        if (getRows() == 2) {
            return getArithmetic().difference(
                getArithmetic().product(getValue(0, 0), getValue(1, 1)),
                getArithmetic().product(getValue(0, 1), getValue(1, 0))
            );
        }
        if (getRows() == 3) {
            return getArithmetic().difference(
                getArithmetic().sum(
                    getArithmetic().product(
                        getValue(0, 0), getValue(1, 1), getValue(2, 2)
                    ),
                    getArithmetic().product(
                        getValue(0, 1), getValue(1, 2), getValue(2, 0)
                    ),
                    getArithmetic().product(
                        getValue(0, 2), getValue(1, 0), getValue(2, 1)
                    )
                ),
                getArithmetic().sum(
                    getArithmetic().product(getValue(2, 0),
                        getValue(1, 1), getValue(0, 2)
                    ),
                    getArithmetic().product(getValue(2, 1),
                        getValue(1, 2), getValue(0, 0)
                    ),
                    getArithmetic().product(getValue(2, 2),
                        getValue(1, 0), getValue(0, 1)
                    )
                )
            );
        }
        T t = getDefaultValue();
        boolean isRowMode = true;
        int index = 0;
        List<Field> zeros = StreamSupport.stream(spliterator(), true)
            .filter(field -> field.getValue().equals(0d))
            .collect(Collectors.toList());
        if (!zeros.isEmpty()) {
            Map.Entry<Integer, List<Field>> bestRow = getBestEntry(zeros, true);
            Map.Entry<Integer, List<Field>> bestCol = getBestEntry(zeros, false);
            if (bestRow.getValue().size() < bestCol.getValue().size()) {
                if (getCols() == bestCol.getValue().size()) return getDefaultValue();
                isRowMode = false;
                index = bestCol.getKey();
            } else {
                if (getRows() == bestRow.getValue().size()) return getDefaultValue();
                index = bestRow.getKey();
            }
        }
        for (int i = 0; i < (isRowMode ? getCols() : getRows()); i++) {
            int row = isRowMode ? index : i;
            int col = isRowMode ? i : index;
            T indexValue = isRowMode ? getValue(index, i) : getValue(i, index);
            if (indexValue.equals(getDefaultValue())) continue;
            T signum = getArithmetic().fromInt(index + i % 2 == 0 ? 1 : -1);
            Matrix<T> sub = newInstance(getRows() - 1, getCols() - 1);
            for (int r = 0; r < sub.getRows(); r++) {
                int ar = r < row ? r : r + 1;
                for (int c = 0; c < sub.getCols(); c++) {
                    int ac = c < col ? c : c + 1;
                    sub.setValue(r, c, getValue(ar, ac));
                }
            }
            t = getArithmetic().sum(t,
                getArithmetic().product(
                    getArithmetic().product(indexValue, signum),
                    sub.determinante()
                )
            );
        }
        return t;
    }

    // endregion

    // region copy

    @Override
    public Matrix<T> copy() {
        return new Matrix<>(this);
    }

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
        if (!(o instanceof Matrix<?>)) return false;
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
     * @return new matrix with flipped values
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
     * @return new matrix with flipped values
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

    /**
     * @param row row to multiply
     * @param n   factor to use
     * @return new matrix with multiplied row
     */
    protected final Matrix<T> multiplyRow(int row, int n) {
        if (!isRowValid(row))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row);
        if (n == 0) return newInstance(getRows(), getCols());
        Matrix<T> copy = copy();
        if (n == 1) return copy;
        for (int c = 0; c < getCols(); c++)
            copy.setValue(row, c,
                getArithmetic().product(
                    getValue(row, c),
                    getArithmetic().fromInt(n)
                )
            );
        return copy;
    }

    /**
     * @param col col to multiply
     * @param n   factor to use
     * @return new matrix with multiplied col
     */
    protected final Matrix<T> multiplyCol(int col, int n) {
        if (!isColValid(col))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col);
        if (n == 0) return newInstance(getRows(), getCols());
        Matrix<T> copy = copy();
        if (n == 1) return copy;
        for (int r = 0; r < getRows(); r++)
            copy.setValue(r, col,
                getArithmetic().product(
                    getValue(r, col),
                    getArithmetic().fromInt(n)
                )
            );
        return copy;
    }

    /**
     * @param row1 row to multiply with other multiple times
     * @param row2 row to multiply multiple times with other
     * @param n    factor to use
     * @return new matrix with multiplied rows
     */
    protected final Matrix<T> addRowMultipleTimes(int row1, int row2, int n) {
        if (!isRowValid(row1))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row1);
        if (!isRowValid(row2))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row2);
        if (n == 0) return copy();
        if (row1 == row2) return multiplyRow(row1, n);
        Matrix<T> copy = copy();
        for (int c = 0; c < getCols(); c++)
            copy.setValue(row1, c, getArithmetic().sum(getValue(row1, c),
                getArithmetic().product(getValue(row2, c), getArithmetic().fromInt(n))
            ));
        return copy;
    }

    /**
     * @param col1 col to multiply with other multiple times
     * @param col2 col to multiply multiple times with other
     * @param n    factor to use
     * @return new matrix with multiplied cols
     */
    protected final Matrix<T> addColMultipleTimes(int col1, int col2, int n) {
        if (!isColValid(col1))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col1);
        if (!isColValid(col2))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col2);
        if (n == 0) return copy();
        if (col1 == col2) return multiplyCol(col1, n);
        Matrix<T> copy = copy();
        for (int r = 0; r < getRows(); r++)
            copy.setValue(r, col1, getArithmetic().sum(getValue(r, col1),
                getArithmetic().product(getValue(r, col2), getArithmetic().fromInt(n))
            ));
        return copy;
    }

    // endregion

    // region protected: isDefaultValue, getIndexOfRowAndCol and isValid

    /**
     * @param row row of requested index
     * @param col col of requested index
     * @return index of requested position
     */
    protected final int getIndexOfRowAndCol(int row, int col) {
        return row * getCols() + col;
    }

    /**
     * @param t value to check
     * @return <code>true</code> if t is equal to {@link #getDefaultValue()}
     */
    protected final boolean isDefaultValue(T t) {
        return getDefaultValue() == null && t == null ||
            t != null && t.equals(getDefaultValue());
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

    // region private

    private void removeDefaultValues() {
        getMatrix().entrySet().removeIf(integerTEntry ->
            isDefaultValue(integerTEntry.getValue())
        );
    }

    private static <T extends Number> Map.Entry<Integer, List<Matrix<T>.Field>>
    getBestEntry(List<Matrix<T>.Field> zeros, boolean isRowMode) {
        return zeros.stream()
            .collect(Collectors.groupingBy(field ->
                isRowMode ? field.getRow() : field.getCol()
            ))
            .entrySet().stream()
            .max(Comparator.comparingInt(o -> o.getValue().size()))
            .orElse(Map.entry(0, List.of()));
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
            Matrix<?>.Field field = (Matrix<?>.Field) o;
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
