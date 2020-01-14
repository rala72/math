package io.rala.math.algebra.matrix;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.StreamIterable;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * class which holds a matrix with {@code rows} and {@code cols}
 *
 * @param <T> number class
 */
public class Matrix<T extends Number>
    implements Copyable<Matrix<T>>, StreamIterable<Matrix<T>.Field>, Serializable {
    // region protected exception messages
    protected static final String EXCEPTION_SIZE_PREFIX = "size: ";
    protected static final String EXCEPTION_ROW_PREFIX = "row: ";
    protected static final String EXCEPTION_COL_PREFIX = "col: ";
    protected static final String EXCEPTION_NO_SQUARE =
        "matrix has to be a square matrix";
    protected static final String EXCEPTION_ANY_COLS_EQUALS_OTHER_ROWS =
        "any cols have to be equal to the other matrix rows";
    protected static final String EXCEPTION_COLS_EQUALS_PARAM_ROWS =
        "cols have to be equal to parameter rows";
    protected static final String EXCEPTION_ROWS_NOT_CONGRUENT_0 =
        "rows modulo values.length is not congruent 0";
    protected static final String EXCEPTION_COLS_NOT_CONGRUENT_0 =
        "cols modulo values.length is not congruent 0";
    // endregion

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
     * @throws IllegalArgumentException if rows or cols is negative or size is to large
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
     * @throws IllegalArgumentException if rows or cols is negative or size is to large
     */
    public Matrix(AbstractArithmetic<T> arithmetic, int rows, int cols, T defaultValue) {
        if (rows < 0 || cols < 0)
            throw new IllegalArgumentException("rows and cols have to be greater or equals to 0");
        if (0 < rows && 0 < cols && (rows * cols < rows || rows * cols < cols))
            throw new IllegalArgumentException("size is to large");
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
     * @throws IllegalArgumentException if rows or cols is negative or size is to large
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
     * @throws IllegalArgumentException if rows or cols is negative or size is to large
     */
    protected final Matrix<T> newInstance(int rows, int cols) {
        return new Matrix<>(getArithmetic(), rows, cols, getDefaultValue());
    }

    // endregion

    // region getter and size

    /**
     * @return stored arithmetic
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

    // region rows and cols

    /**
     * @param row row of matrix
     * @return list of fields in row
     * @throws IndexOutOfBoundsException if row is invalid
     */
    public List<Field> getRowFields(int row) {
        if (!isRowValid(row))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row);
        return IntStream.range(0, getCols())
            .mapToObj(col -> new Field(row, col, getValue(row, col)))
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * @param row row of matrix
     * @return list of values in row
     * @throws IndexOutOfBoundsException if row is invalid
     * @see #getRowFields(int)
     */
    public List<T> getRow(int row) {
        return getRowFields(row).stream()
            .map(Field::getValue)
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * @param col col of matrix
     * @return list of fields in col
     * @throws IndexOutOfBoundsException if col is invalid
     */
    public List<Field> getColFields(int col) {
        if (!isColValid(col))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col);
        return IntStream.range(0, getRows())
            .mapToObj(row -> new Field(row, col, getValue(row, col)))
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * @param col col of matrix
     * @return list of values in col
     * @throws IndexOutOfBoundsException if col is invalid
     * @see #getColFields(int)
     */
    public List<T> getCol(int col) {
        return getColFields(col).stream()
            .map(Field::getValue)
            .collect(Collectors.toUnmodifiableList());
    }

    // endregion

    // region value

    /**
     * @param row   row where value should be stored
     * @param col   col where value should be stored
     * @param value new value to store
     * @return old value if existed - may return {@code null} if it was empty
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
     * @return old value if existed - may return {@code null} if it was empty
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
     * @return old value - may return {@code null} if it was empty
     * @throws IndexOutOfBoundsException if index is invalid
     * @see #removeValue(int)
     * @see Map#remove(Object)
     */
    public T removeValue(int row, int col) {
        return removeValue(getIndexOfRowAndCol(row, col));
    }

    /**
     * @param index index of value to remove
     * @return old value - may return {@code null} if it was empty
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
     * @return {@code true} if amount of rows and cols is equal
     */
    public final boolean isSquare() {
        return getRows() == getCols();
    }

    /**
     * @return {@code true} if only the diagonal has values
     */
    public final boolean isDiagonal() {
        return isSquare() && StreamSupport.stream(spliterator(), true)
            .allMatch(field -> field.getRow() == field.getCol() ||
                field.getValue() == null ||
                field.getValue().doubleValue() == 0d
            );
    }

    // endregion

    // region matrix arithmetic: add and multiply

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
     * @param t value to multiply
     * @return new matrix with calculated values
     */
    public Matrix<T> multiply(T t) {
        Matrix<T> result = copy();
        for (int i = 0; i < size(); i++)
            result.getMatrix().computeIfPresent(i,
                (integer, value) -> getArithmetic().product(value, t)
            );
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
     * if arithmetic does not work in provided order but in flipped one
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

    // endregion

    // region matrix arithmetic: inverse, transpose and determinante

    /**
     * @return new inverse matrix or {@code null} if there is none
     */
    public Matrix<T> inverse() {
        if (!isSquare())
            throw new IllegalArgumentException(EXCEPTION_NO_SQUARE);
        T determinante = determinante();
        if (determinante == null || isDefaultValue(determinante))
            return null;
        T k = getArithmetic().quotient(getArithmetic().fromInt(1), determinante);
        Matrix<T> minorMatrix = newInstance(getRows(), getCols());
        for (int r = 0; r < minorMatrix.getRows(); r++) {
            for (int c = 0; c < minorMatrix.getCols(); c++) {
                T value = getArithmetic().product(
                    getArithmetic().fromInt(signumFactor(r, c)),
                    subMatrix(r, c).determinante()
                );
                minorMatrix.setValue(r, c, value);
            }
        }
        return minorMatrix.transpose().multiply(k);
    }

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
        return determinanteRecursive();
    }

    /**
     * recursive implementation especially for matrices
     * with sizes greater than {@code 3}<br>
     *
     * @return determinante of matrix
     * @see #determinante()
     */
    protected T determinanteRecursive() {
        if (size() == 0 || !isSquare()) return getDefaultValue();
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
        T t = getDefaultValue();
        for (int i = 0; i < (isRowMode ? getCols() : getRows()); i++) {
            int row = isRowMode ? index : i;
            int col = isRowMode ? i : index;
            t = getArithmetic().sum(t, coFactor(row, col));
        }
        return t;
    }

    // endregion

    // region static: identity and diagonal

    /**
     * @param arithmetic   arithmetic for calculations
     * @param size         size of matrix
     * @param defaultValue default value of non-existing values
     * @param <T>          number class
     * @return new created matrix
     */
    public static <T extends Number> Matrix<T> identity(
        AbstractArithmetic<T> arithmetic, int size, T defaultValue
    ) {
        Matrix<T> matrix = new Matrix<>(arithmetic, size, defaultValue);
        for (int i = 0; i < size; i++)
            matrix.setValue(i, i, arithmetic.one());
        return matrix;
    }

    /**
     * @param arithmetic   arithmetic for calculations
     * @param defaultValue default value of non-existing values
     * @param values       diagonal values of matrix
     * @param <T>          number class
     * @return new created matrix
     */
    @SafeVarargs
    public static <T extends Number> Matrix<T> diagonal(
        AbstractArithmetic<T> arithmetic, T defaultValue, T... values
    ) {
        Matrix<T> matrix = new Matrix<>(arithmetic, values.length, defaultValue);
        for (int i = 0; i < values.length; i++)
            matrix.setValue(i, i, values[i]);
        return matrix;
    }

    // endregion

    // region static: of

    /**
     * creates a new matrix containing all provided values
     *
     * @param arithmetic   arithmetic for calculations
     * @param defaultValue default value of non-existing values
     * @param rows         rows of matrix
     * @param values       row based values of matrix
     * @param <T>          number class
     * @return new created matrix
     * @throws IllegalArgumentException if rows modulo {@code values.length}
     *                                  is not congruent 0
     */
    @SafeVarargs
    public static <T extends Number> Matrix<T> ofValuesByRows(
        AbstractArithmetic<T> arithmetic, T defaultValue, int rows, T... values
    ) {
        if (values.length % rows != 0)
            throw new IllegalArgumentException(EXCEPTION_ROWS_NOT_CONGRUENT_0);
        Matrix<T> matrix = new Matrix<>(arithmetic, rows, values.length / rows, defaultValue);
        for (int i = 0; i < values.length; i++)
            matrix.setValue(i, values[i]);
        return matrix;
    }

    /**
     * creates a new matrix containing all provided values
     *
     * @param arithmetic   arithmetic for calculations
     * @param defaultValue default value of non-existing values
     * @param cols         cols of matrix
     * @param values       column based values of matrix
     * @param <T>          number class
     * @return new created matrix
     * @throws IllegalArgumentException if cols modulo {@code values.length}
     *                                  is not congruent 0
     */
    @SafeVarargs
    public static <T extends Number> Matrix<T> ofValuesByCols(
        AbstractArithmetic<T> arithmetic, T defaultValue, int cols, T... values
    ) {
        if (values.length % cols != 0)
            throw new IllegalArgumentException(EXCEPTION_COLS_NOT_CONGRUENT_0);
        return Matrix.ofValuesByRows(
            arithmetic, defaultValue, values.length / cols, values
        ).transpose();
    }

    // endregion

    // region map and copy

    /**
     * @param arithmetic arithmetic for calculations
     * @param map        mapping function to convert current values to new one
     * @param <NT>       new number class
     * @return mapped matrix
     */
    public <NT extends Number> Matrix<NT> map(
        AbstractArithmetic<NT> arithmetic, Function<T, NT> map
    ) {
        Matrix<NT> matrix = new Matrix<>(
            arithmetic, getRows(), getCols(), map.apply(getDefaultValue())
        );
        getMatrix().forEach((integer, t) -> matrix.setValue(integer, map.apply(t)));
        return matrix;
    }

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

    // region protected: subMatrix, coFactor and signumFactor

    /**
     * @param row row to exclude
     * @param col col to exclude
     * @return new sub matrix excluding specified row and col
     * @throws IndexOutOfBoundsException if row or col is invalid
     */
    protected final Matrix<T> subMatrix(int row, int col) {
        if (!isSquare())
            throw new IllegalArgumentException(EXCEPTION_NO_SQUARE);
        if (!isRowValid(row))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row);
        if (!isColValid(col))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col);
        Matrix<T> subMatrix = newInstance(getRows() - 1, getCols() - 1);
        for (int r = 0; r < subMatrix.getRows(); r++) {
            int ar = r < row ? r : r + 1;
            for (int c = 0; c < subMatrix.getCols(); c++) {
                int ac = c < col ? c : c + 1;
                subMatrix.setValue(r, c, getValue(ar, ac));
            }
        }
        return subMatrix;
    }

    /**
     * @param row row of coFactor
     * @param col col of coFactor
     * @return coFactor of matrix
     * @throws IndexOutOfBoundsException if row or col is invalid
     * @see #subMatrix(int, int)
     * @see #signumFactor(int, int)
     */
    protected final T coFactor(int row, int col) {
        if (!isSquare())
            throw new IllegalArgumentException(EXCEPTION_NO_SQUARE);
        if (!isRowValid(row))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row);
        if (!isColValid(col))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col);
        return getArithmetic().product(
            getArithmetic().product(
                getValue(row, col),
                getArithmetic().fromInt(signumFactor(row, col))
            ),
            subMatrix(row, col).determinante()
        );
    }

    /**
     * @param row row of matrix
     * @param col col of matrix
     * @return {@code (-1)^(row+col)}
     */
    protected static int signumFactor(int row, int col) {
        return (row + col) % 2 == 0 ? 1 : -1;
    }

    // endregion

    // region protected: modify

    /**
     * @param row1 row1 to swap with row2
     * @param row2 row2 to swap with row1
     * @return new matrix with swapped values
     * @throws IndexOutOfBoundsException if row1 or row2 is invalid
     */
    protected final Matrix<T> swapRows(int row1, int row2) {
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
     * @param col1 col1 to swap with col2
     * @param col2 col2 to swap with col1
     * @return new matrix with swapped values
     * @throws IndexOutOfBoundsException if col1 or col2 is invalid
     */
    protected final Matrix<T> swapCols(int col1, int col2) {
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
    protected final Matrix<T> multiplyRow(int row, T n) {
        if (!isRowValid(row))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row);
        Matrix<T> copy = copy();
        if (getArithmetic().zero().equals(n)) {
            for (int i = 0; i < getCols(); i++)
                copy.setValue(row, i, getArithmetic().zero());
            return copy;
        }
        if (getArithmetic().one().equals(n))
            return copy;
        for (int c = 0; c < getCols(); c++)
            copy.setValue(row, c,
                getArithmetic().product(getValue(row, c), n)
            );
        return copy;
    }

    /**
     * @param col col to multiply
     * @param n   factor to use
     * @return new matrix with multiplied col
     */
    protected final Matrix<T> multiplyCol(int col, T n) {
        if (!isColValid(col))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col);
        Matrix<T> copy = copy();
        if (getArithmetic().zero().equals(n)) {
            for (int i = 0; i < getCols(); i++)
                copy.setValue(i, col, getArithmetic().zero());
            return copy;
        }
        if (getArithmetic().one().equals(n))
            return copy;
        for (int r = 0; r < getRows(); r++)
            copy.setValue(r, col,
                getArithmetic().product(getValue(r, col), n)
            );
        return copy;
    }

    /**
     * @param row1 row to multiply with other multiple times
     * @param row2 row to multiply multiple times with other
     * @param n    factor to use
     * @return new matrix with multiplied rows
     */
    protected final Matrix<T> addRowMultipleTimes(int row1, int row2, T n) {
        if (!isRowValid(row1))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row1);
        if (!isRowValid(row2))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row2);
        if (getArithmetic().zero().equals(n))
            return copy();
        if (row1 == row2) return multiplyRow(row1, n);
        Matrix<T> copy = copy();
        for (int c = 0; c < getCols(); c++)
            copy.setValue(row1, c, getArithmetic().sum(getValue(row1, c),
                getArithmetic().product(getValue(row2, c), n)
            ));
        return copy;
    }

    /**
     * @param col1 col to multiply with other multiple times
     * @param col2 col to multiply multiple times with other
     * @param n    factor to use
     * @return new matrix with multiplied cols
     */
    protected final Matrix<T> addColMultipleTimes(int col1, int col2, T n) {
        if (!isColValid(col1))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col1);
        if (!isColValid(col2))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col2);
        if (getArithmetic().zero().equals(n))
            return copy();
        if (col1 == col2) return multiplyCol(col1, n);
        Matrix<T> copy = copy();
        for (int r = 0; r < getRows(); r++)
            copy.setValue(r, col1, getArithmetic().sum(getValue(r, col1),
                getArithmetic().product(getValue(r, col2), n)
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
     * @return {@code true} if t is equal to {@link #getDefaultValue()}
     */
    protected final boolean isDefaultValue(T t) {
        return getDefaultValue() == null && t == null ||
            t != null && t.equals(getDefaultValue());
    }

    /**
     * @param index index to check
     * @return {@code true} if value is valid
     */
    protected final boolean isIndexValid(int index) {
        return 0 <= index && index < size();
    }

    /**
     * @param row row to check
     * @return {@code true} if value is valid
     */
    protected final boolean isRowValid(int row) {
        return 0 <= row && row < getRows();
    }

    /**
     * @param col col to check
     * @return {@code true} if col is valid
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
         * @param row   row of field
         * @param col   col of field
         * @param value value of field
         * @see #Field(int, Number)
         * @see #getIndexOfRowAndCol(int, int)
         */
        protected Field(int row, int col, T value) {
            this(getIndexOfRowAndCol(row, col), value);
        }

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
