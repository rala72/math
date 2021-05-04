package io.rala.math.algebra.matrix;

import io.rala.math.algebra.vector.Vector;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.exception.NotSupportedException;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.StreamIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * class which holds a matrix with {@code rows} and {@code cols}
 *
 * @param <T> number class
 * @since 1.0.0
 */
public class Matrix<T extends Number>
    implements Copyable<Matrix<T>>, StreamIterable<Matrix<T>.Field>, Serializable {

    // region protected exception messages
    protected static final String EXCEPTION_SIZE_PREFIX = "size: ";
    protected static final String EXCEPTION_ROW_PREFIX = "row: ";
    protected static final String EXCEPTION_COL_PREFIX = "col: ";
    protected static final String EXCEPTION_ROWS_UNEQUAL =
        "rows have to be equal";
    protected static final String EXCEPTION_COLS_UNEQUAL =
        "cols have to be equal";
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
    protected static final String EXCEPTION_ROW_OR_COLUMN_AMOUNT_NOT_1 =
        "matrix has to have one row and/or one column";
    protected static final String EXCEPTION_NOT_1X1 =
        "matrix has to contain only one value";
    // endregion

    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private final Map<Integer, Map<Integer, T>> matrix = new HashMap<>();
    private final int rows;
    private final int cols;
    private final T defaultValue;

    // endregion

    // region constructor

    /**
     * calls {@link #Matrix(AbstractArithmetic, int, int)}
     *
     * @param arithmetic arithmetic for calculations
     * @param size       size of matrix
     * @throws IllegalArgumentException if size is less than {@code 1}
     * @see #Matrix(AbstractArithmetic, int, int)
     * @since 1.0.0
     */
    public Matrix(@NotNull AbstractArithmetic<T> arithmetic, int size) {
        this(arithmetic, size, size);
    }

    /**
     * calls {@link #Matrix(AbstractArithmetic, int, int, Number)}
     * with size as rows and cols and
     * given default value for non-existing values
     *
     * @param arithmetic   arithmetic for calculations
     * @param size         size of matrix
     * @param defaultValue default value of non-existing values
     * @throws IllegalArgumentException if size is less than {@code 1}
     * @implSpec default value should be {@code 0}
     * - other values may not be handled correctly
     * <i>(in {@link #equals(Object)}, ...)</i>
     * @see #Matrix(AbstractArithmetic, int, Number)
     * @since 1.0.0
     */
    protected Matrix(@NotNull AbstractArithmetic<T> arithmetic, int size, @Nullable T defaultValue) {
        this(arithmetic, size, size, defaultValue);
    }

    /**
     * calls {@link #Matrix(AbstractArithmetic, int, int, Number)}
     * with given rows and cols using
     * {@link AbstractArithmetic#zero()} as {@code defaultValue}
     *
     * @param arithmetic arithmetic for calculations
     * @param rows       rows of matrix
     * @param cols       cols of matrix
     * @throws IllegalArgumentException if rows or cols is less than {@code 1}
     * @see #Matrix(AbstractArithmetic, int, int)
     * @since 1.0.0
     */
    public Matrix(@NotNull AbstractArithmetic<T> arithmetic, int rows, int cols) {
        this(arithmetic, rows, cols, arithmetic.zero());
    }

    /**
     * creates a new matrix with given rows and cols
     * using given default value for non-existing values
     *
     * @param arithmetic   arithmetic for calculations
     * @param rows         rows of matrix
     * @param cols         cols of matrix
     * @param defaultValue default value of non-existing values
     * @throws IllegalArgumentException if rows or cols is less than {@code 1}
     * @implSpec default value should be {@code 0}
     * - other values may not be handled correctly
     * <i>(in {@link #equals(Object)}, ...)</i>
     * @since 1.0.0
     */
    protected Matrix(@NotNull AbstractArithmetic<T> arithmetic, int rows, int cols, @Nullable T defaultValue) {
        if (rows <= 0 || cols <= 0)
            throw new IllegalArgumentException("rows and cols have to be greater than 0");
        this.arithmetic = arithmetic;
        this.rows = rows;
        this.cols = cols;
        this.defaultValue = defaultValue;
    }

    /**
     * creates a new matrix based on given one
     *
     * @param matrix matrix to copy
     * @since 1.0.0
     */
    protected Matrix(@NotNull Matrix<T> matrix) {
        this(
            matrix.getArithmetic(),
            matrix.getRows(), matrix.getCols(),
            matrix.getDefaultValue()
        );
        matrix.getMatrix().forEach(
            (key, value) -> getMatrix().put(key, new HashMap<>(value))
        );
    }

    // endregion

    // region getter and size

    /**
     * @return stored arithmetic
     * @since 1.0.0
     */
    @NotNull
    public AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

    /**
     * @return matrix map which uses index as key
     * @since 1.0.0
     */
    @NotNull
    protected final Map<Integer, Map<Integer, T>> getMatrix() {
        return matrix;
    }

    /**
     * @return rows of matrix
     * @since 1.0.0
     */
    public final int getRows() {
        return rows;
    }

    /**
     * @return cols of matrix
     * @since 1.0.0
     */
    public final int getCols() {
        return cols;
    }

    /**
     * @return default value for non-existing values
     * @since 1.0.0
     */
    @NotNull
    protected final T getDefaultValue() {
        return defaultValue;
    }

    /**
     * @return {@link #getRows()} * {@link #getCols()}
     * @since 1.0.0
     */
    public final long size() {
        return (long) getRows() * getCols();
    }

    // endregion

    // region rows and cols

    /**
     * @param row row of matrix
     * @return list of fields in row
     * @throws IndexOutOfBoundsException if row is invalid
     * @since 1.0.0
     */
    @NotNull
    @Unmodifiable
    public List<Field> getRowFields(int row) {
        if (!isValidRow(row))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row + " / " + getRows());
        return IntStream.range(0, getCols())
            .mapToObj(col -> new Field(row, col, getValue(row, col)))
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * @param row row of matrix
     * @return list of values in row
     * @throws IndexOutOfBoundsException if row is invalid
     * @see #getRowFields(int)
     * @since 1.0.0
     */
    @NotNull
    @Unmodifiable
    public List<T> getRow(int row) {
        return getRowFields(row).stream()
            .map(Field::getValue)
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * @param col col of matrix
     * @return list of fields in col
     * @throws IndexOutOfBoundsException if col is invalid
     * @since 1.0.0
     */
    @NotNull
    @Unmodifiable
    public List<Field> getColFields(int col) {
        if (!isValidCol(col))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col + " / " + getCols());
        return IntStream.range(0, getRows())
            .mapToObj(row -> new Field(row, col, getValue(row, col)))
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * @param col col of matrix
     * @return list of values in col
     * @throws IndexOutOfBoundsException if col is invalid
     * @see #getColFields(int)
     * @since 1.0.0
     */
    @NotNull
    @Unmodifiable
    public List<T> getCol(int col) {
        return getColFields(col).stream()
            .map(Field::getValue)
            .collect(Collectors.toUnmodifiableList());
    }

    // endregion

    // region value

    /**
     * @param row row of requested value
     * @param col col of requested value
     * @return current value on given position
     * @throws IndexOutOfBoundsException if row or col is invalid
     * @see #getValue(long)
     * @since 1.0.0
     */
    @NotNull
    public T getValue(int row, int col) {
        return getValue(getIndexOfRowAndCol(row, col));
    }

    /**
     * @param index index of requested value
     * @return current value on given position
     * @throws IndexOutOfBoundsException if index is invalid
     * @since 1.0.0
     */
    @NotNull
    public T getValue(long index) {
        if (!isValidIndex(index))
            throw new IndexOutOfBoundsException(EXCEPTION_SIZE_PREFIX + index + " / " + size());
        return getMatrix()
            .getOrDefault((int) (index / getCols()), Collections.emptyMap())
            .getOrDefault((int) (index % getCols()), getDefaultValue());
    }

    /**
     * @param row   row where value should be stored
     * @param col   col where value should be stored
     * @param value new value to store
     * @return old value if existed or {@link #getDefaultValue()}
     * @throws IndexOutOfBoundsException if row or col is invalid
     * @see #setValue(long, Number)
     * @since 1.0.0
     */
    @NotNull
    public T setValue(int row, int col, @NotNull T value) {
        return setValue(getIndexOfRowAndCol(row, col), value);
    }

    /**
     * @param index index where value should be stored
     * @param value new value to store
     * @return old value if existed or {@link #getDefaultValue()}
     * @throws IndexOutOfBoundsException if index is invalid
     * @since 1.0.0
     */
    @NotNull
    public T setValue(long index, @NotNull T value) {
        if (!isValidIndex(index))
            throw new IndexOutOfBoundsException(EXCEPTION_SIZE_PREFIX + index + " / " + size());
        if (isDefaultValue(value))
            return removeValue(index);
        else {
            AtomicReference<T> previous = new AtomicReference<>(getDefaultValue());
            getMatrix().compute((int) (index / getCols()),
                (integer, integerTMap) -> {
                    Map<Integer, T> map = integerTMap == null ?
                        new HashMap<>() : integerTMap;
                    T prev = map.put((int) (index % getCols()), value);
                    if (prev != null) previous.set(prev);
                    return map;
                }
            );
            return previous.get();
        }
    }

    /**
     * @param row row of value to remove
     * @param col col of value to remove
     * @return old value if existed or {@link #getDefaultValue()}
     * @throws IndexOutOfBoundsException if row or col is invalid
     * @see #removeValue(long)
     * @since 1.0.0
     */
    @NotNull
    public T removeValue(int row, int col) {
        return removeValue(getIndexOfRowAndCol(row, col));
    }

    /**
     * @param index index of value to remove
     * @return old value if existed or {@link #getDefaultValue()}
     * @throws IndexOutOfBoundsException if index is invalid
     * @since 1.0.0
     */
    @NotNull
    public T removeValue(long index) {
        if (!isValidIndex(index))
            throw new IndexOutOfBoundsException(EXCEPTION_SIZE_PREFIX + index + " / " + size());
        AtomicReference<T> previous = new AtomicReference<>(getDefaultValue());
        getMatrix().compute((int) (index / getCols()), (integer, integerTMap) -> {
            if (integerTMap == null) return null;
            T prev = integerTMap.remove((int) (index % getCols()));
            if (prev != null) previous.set(prev);
            return integerTMap.isEmpty() ? null : integerTMap;
        });
        return previous.get();
    }

    // endregion

    // region compute

    /**
     * computes a new value based on given operator
     *
     * @param row      row where new value is computed
     * @param col      col where new value is computed
     * @param operator operator to apply current value
     * @return old value if existed or {@link #getDefaultValue()}
     * @see #compute(long, UnaryOperator)
     * @see #setValue(int, int, Number)
     * @see #getValue(int, int)
     * @since 1.0.0
     */
    @NotNull
    public T compute(int row, int col, @NotNull UnaryOperator<T> operator) {
        return compute(getIndexOfRowAndCol(row, col), operator);
    }

    /**
     * computes a new value based on given operator
     *
     * @param index    index where new value is computed
     * @param operator operator to apply current value
     * @return old value if existed or {@link #getDefaultValue()}
     * @see #compute(long, UnaryOperator)
     * @see #setValue(long, Number)
     * @see #getValue(long)
     * @since 1.0.0
     */
    @NotNull
    public T compute(long index, @NotNull UnaryOperator<T> operator) {
        return setValue(index, operator.apply(getValue(index)));
    }

    /**
     * computes a new value based on given operator
     *
     * @param row      row where new value is computed
     * @param col      col where new value is computed
     * @param value    new value to use in computation
     * @param operator operator to apply on old and new value
     * @return old value if existed or {@link #getDefaultValue()}
     * @see #compute(long, Number, BinaryOperator)
     * @see #setValue(int, int, Number)
     * @see #getValue(int, int)
     * @since 1.0.0
     */
    @NotNull
    public T compute(int row, int col, @NotNull T value, @NotNull BinaryOperator<T> operator) {
        return compute(getIndexOfRowAndCol(row, col), value, operator);
    }

    /**
     * computes a new value based on given operator
     *
     * @param index    index where new value is computed
     * @param value    new value to use in computation
     * @param operator operator to apply on old and new value
     * @return old value if existed or {@link #getDefaultValue()}
     * @see #setValue(long, Number)
     * @see #getValue(long)
     * @since 1.0.0
     */
    @NotNull
    public T compute(long index, T value, @NotNull BinaryOperator<T> operator) {
        return setValue(index, operator.apply(getValue(index), value));
    }

    /**
     * computes new values based on given operator
     *
     * @param operator operator to apply on all fields
     * @see #setValue(long, Number)
     * @since 1.0.0
     */
    public void computeAll(@NotNull Function<Field, T> operator) {
        forEach(field -> setValue(field.getIndex(), operator.apply(field)));
    }

    /**
     * computes new values based on given operator
     *
     * @param value    function returning new value to use in computation
     * @param operator operator to apply on old and new value
     * @see #computeAll(Function)
     * @since 1.0.0
     */
    public void computeAll(@NotNull Function<Field, T> value, @NotNull BinaryOperator<T> operator) {
        computeAll(field -> operator.apply(field.getValue(), value.apply(field)));
    }

    // endregion

    // region isSquare, isDiagonal and isInvertible

    /**
     * @return {@code true} if amount of rows and cols is equal
     * @since 1.0.0
     */
    public final boolean isSquare() {
        return getRows() == getCols();
    }

    /**
     * @return {@code true} if only the diagonal has values
     * @since 1.0.0
     */
    public final boolean isDiagonal() {
        return isSquare() && stream()
            .allMatch(field -> field.getRow() == field.getCol() ||
                getArithmetic().isZero(field.getValue())
            );
    }

    /**
     * @return {@code true} if {@link #isSquare()}
     * and {@link #determinante()}!={@code 0}
     * @since 1.0.0
     */
    public final boolean isInvertible() {
        return isSquare() && !isZero(determinante());
    }

    // endregion

    // region add and multiply

    /**
     * @param matrix matrix to add
     * @return new matrix with calculated values
     * @throws IllegalArgumentException if rows or cols are not equal
     * @since 1.0.0
     */
    @NotNull
    public Matrix<T> add(@NotNull Matrix<T> matrix) {
        if (getRows() != matrix.getRows())
            throw new IllegalArgumentException(EXCEPTION_ROWS_UNEQUAL);
        if (getCols() != matrix.getCols())
            throw new IllegalArgumentException(EXCEPTION_COLS_UNEQUAL);
        Matrix<T> result = copy();
        result.computeAll(
            field -> matrix.getValue(field.getIndex()),
            getArithmetic()::sum
        );
        result.removeDefaultValues();
        return result;
    }

    /**
     * @param t value to multiply
     * @return new matrix with calculated values
     * @since 1.0.0
     */
    @NotNull
    public Matrix<T> multiply(@NotNull T t) {
        Matrix<T> result = copy();
        result.computeAll(field -> t, getArithmetic()::product);
        result.removeDefaultValues();
        return result;
    }

    /**
     * @param matrix matrix to multiply
     * @return new matrix with calculated values
     * @throws IllegalArgumentException if cols are not equal param rows
     * @since 1.0.0
     */
    @NotNull
    public Matrix<T> multiply(@NotNull Matrix<T> matrix) {
        if (getCols() != matrix.getRows())
            throw new IllegalArgumentException(EXCEPTION_COLS_EQUALS_PARAM_ROWS);
        Matrix<T> result = new Matrix<>(getArithmetic(),
            getRows(), matrix.getCols(), getDefaultValue()
        );
        result.computeAll(field -> {
            T d = getArithmetic().zero();
            for (int i = 0; i < getCols(); i++)
                d = getArithmetic().sum(d,
                    getArithmetic().product(
                        getValue(field.getRow(), i),
                        matrix.getValue(i, field.getCol())
                    )
                );
            return d;
        });
        return result;
    }

    /**
     * calls {@link #multiply(Matrix)} but flips matrices
     * if arithmetic does not work in provided order but in flipped one
     *
     * @param matrix matrix to multiply
     * @return new matrix with calculated values
     * @throws IllegalArgumentException if no cols and rows match
     * @see #multiply(Matrix)
     * @since 1.0.0
     */
    @NotNull
    public Matrix<T> multiplyTolerant(@NotNull Matrix<T> matrix) {
        if (getCols() == matrix.getRows())
            return this.multiply(matrix);
        else if (getRows() == matrix.getCols())
            return matrix.multiplyTolerant(this);
        throw new IllegalArgumentException(EXCEPTION_ANY_COLS_EQUALS_OTHER_ROWS);
    }

    // endregion

    // region inverse, transpose and determinante

    /**
     * @return new inverse matrix or {@code null} if there is none
     * @throws NotSupportedException if {@link #isSquare()} is {@code false}
     * @since 1.0.0
     */
    @Nullable
    public Matrix<T> inverse() {
        if (!isSquare())
            throw new NotSupportedException(EXCEPTION_NO_SQUARE);
        T determinante = determinante();
        if (isZero(determinante)) return null;
        T k = getArithmetic().quotient(getArithmetic().fromInt(1), determinante);
        Matrix<T> minorMatrix = new Matrix<>(getArithmetic(),
            getRows(), getCols(), getDefaultValue()
        );
        minorMatrix.computeAll(field ->
            getArithmetic().product(
                getArithmetic().fromInt(
                    signumFactor(field.getRow(), field.getCol())
                ),
                subMatrix(field.getRow(), field.getCol()).determinante()
            )
        );
        return minorMatrix.transpose().multiply(k);
    }

    /**
     * @return new transposed matrix
     * @since 1.0.0
     */
    @NotNull
    public Matrix<T> transpose() {
        Matrix<T> result = new Matrix<>(getArithmetic(),
            getCols(), getRows(), getDefaultValue()
        );
        forEach(field -> result.setValue(
            field.getCol(), field.getRow(),
            getValue(getIndexOfRowAndCol(field.getRow(), field.getCol()))
        ));
        return result;
    }

    /**
     * @return determinante of matrix or {@code 0}
     * @since 1.0.0
     */
    @NotNull
    public T determinante() {
        if (size() == 0 || !isSquare()) return getArithmetic().zero();
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
     * @return determinante of matrix or {@code 0}
     * @see #determinante()
     * @since 1.0.0
     */
    @NotNull
    protected T determinanteRecursive() {
        if (size() == 0 || !isSquare()) return getArithmetic().zero();
        boolean isRowMode = true;
        int index = 0;
        List<Field> zeros = parallelStream()
            .filter(field -> isZero(field.getValue()))
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

    // region rank and rowEchelonForm

    /**
     * @return rank of matrix
     * @see #rowEchelonForm()
     * @since 1.0.0
     */
    public int rank() {
        Matrix<T> matrix = rowEchelonForm();
        return getRows() - (int) IntStream.range(0, getRows()).filter(matrix::isZeroRow).count();
    }

    /**
     * @return new matrix in row echelon form
     * (not necessary reduced)
     * @since 1.0.0
     */
    @NotNull
    public Matrix<T> rowEchelonForm() {
        // see GaussSolver
        Matrix<T> result = copy();
        result = result.swapZeroRowsToBottom();
        result = result.ensureDiagonalFieldsAreNonZero();
        final int minSquareSize = Math.min(result.getRows(), result.getCols());
        for (int i = 0; i < minSquareSize; i++) {
            T value = result.getValue(i, i);
            if (isZero(value)) break;
            for (int r = i + 1; r < minSquareSize; r++) {
                T rValue = result.getValue(r, i);
                if (isZero(rValue)) continue;
                T quotient = getArithmetic().quotient(value, rValue);
                T inverse = getArithmetic().quotient(getArithmetic().one(), quotient);
                T negated = getArithmetic().negate(inverse);
                result = result.addRowMultipleTimes(r, i, negated);
            }
        }
        return result;
    }

    // endregion

    // region toVector and toParam

    /**
     * @return new vector from matrix row/column
     * prefers column vector if rows and columns are equal to one
     * @throws NotSupportedException if columns and rows are more than one
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> toVector() {
        if (getCols() == 1)
            return Vector.ofList(getArithmetic(), getCol(0));
        if (getRows() == 1)
            return Vector.ofList(getArithmetic(), getRow(0)).transpose();
        throw new NotSupportedException(EXCEPTION_ROW_OR_COLUMN_AMOUNT_NOT_1);
    }

    /**
     * @return only entry of 1x1 matrix
     * @throws NotSupportedException if matrix is not 1x1
     * @since 1.0.0
     */
    @NotNull
    public T toParam() {
        if (getRows() == 1 && getCols() == 1)
            return getValue(0, 0);
        throw new NotSupportedException(EXCEPTION_NOT_1X1);
    }

    // endregion

    // region static: identity and diagonal

    /**
     * calls {@link #identity(AbstractArithmetic, int, Number)}
     * using {@link AbstractArithmetic#zero()} as {@code defaultValue}
     *
     * @param arithmetic arithmetic for calculations
     * @param size       size of matrix
     * @param <T>        number class
     * @return new created matrix
     * @throws IllegalArgumentException if rows or cols is less than {@code 1}
     * @since 1.0.0
     */
    @NotNull
    public static <T extends Number> Matrix<T> identity(
        @NotNull AbstractArithmetic<T> arithmetic, int size
    ) {
        return identity(arithmetic, size, arithmetic.zero());
    }

    /**
     * @param arithmetic   arithmetic for calculations
     * @param size         size of matrix
     * @param defaultValue default value of non-existing values
     * @param <T>          number class
     * @return new created matrix
     * @throws IllegalArgumentException if rows or cols is less than {@code 1}
     * @since 1.0.0
     */
    @NotNull
    protected static <T extends Number> Matrix<T> identity(
        @NotNull AbstractArithmetic<T> arithmetic,
        int size, @NotNull T defaultValue
    ) {
        Matrix<T> matrix = new Matrix<>(arithmetic, size, defaultValue);
        for (int i = 0; i < size; i++)
            matrix.setValue(i, i, arithmetic.one());
        return matrix;
    }

    /**
     * calls {@link #diagonal(AbstractArithmetic, Number, Number[])}
     * using {@link AbstractArithmetic#zero()} as {@code defaultValue}
     *
     * @param arithmetic arithmetic for calculations
     * @param values     diagonal values of matrix
     * @param <T>        number class
     * @return new created matrix
     * @throws IllegalArgumentException if rows or cols is less than {@code 1}
     * @since 1.0.0
     */
    @NotNull
    @SafeVarargs
    public static <T extends Number> Matrix<T> diagonal(
        @NotNull AbstractArithmetic<T> arithmetic, @NotNull T... values
    ) {
        return diagonal(arithmetic, arithmetic.zero(), values);
    }

    /**
     * @param arithmetic   arithmetic for calculations
     * @param defaultValue default value of non-existing values
     * @param values       diagonal values of matrix
     * @param <T>          number class
     * @return new created matrix
     * @throws IllegalArgumentException if rows or cols is less than {@code 1}
     * @since 1.0.0
     */
    @NotNull
    @SafeVarargs
    protected static <T extends Number> Matrix<T> diagonal(
        @NotNull AbstractArithmetic<T> arithmetic,
        @NotNull T defaultValue, @NotNull T... values
    ) {
        Matrix<T> matrix = new Matrix<>(arithmetic, values.length, defaultValue);
        for (int i = 0; i < values.length; i++)
            matrix.setValue(i, i, values[i]);
        return matrix;
    }

    // endregion

    // region static: of

    /**
     * calls {@link #ofValuesByRows(AbstractArithmetic, Number, int, Number[])}
     * using {@link AbstractArithmetic#zero()} as {@code defaultValue}
     *
     * @param arithmetic arithmetic for calculations
     * @param rows       rows of matrix
     * @param values     row based values of matrix
     * @param <T>        number class
     * @return new created matrix
     * @throws IllegalArgumentException if rows modulo {@code values.length}
     *                                  is not congruent {@code 0}
     * @throws IllegalArgumentException if rows or cols is less than {@code 1}
     * @since 1.0.0
     */
    @NotNull
    @SafeVarargs
    public static <T extends Number> Matrix<T> ofValuesByRows(
        @NotNull AbstractArithmetic<T> arithmetic, int rows, @NotNull T... values
    ) {
        return ofValuesByRows(arithmetic, arithmetic.zero(), rows, values);
    }

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
     *                                  is not congruent {@code 0}
     * @throws IllegalArgumentException if rows or cols is less than {@code 1}
     * @since 1.0.0
     */
    @NotNull
    @SafeVarargs
    protected static <T extends Number> Matrix<T> ofValuesByRows(
        @NotNull AbstractArithmetic<T> arithmetic,
        @NotNull T defaultValue, int rows, @NotNull T... values
    ) {
        if (values.length % rows != 0)
            throw new IllegalArgumentException(EXCEPTION_ROWS_NOT_CONGRUENT_0);
        Matrix<T> matrix = new Matrix<>(arithmetic,
            rows, values.length / rows, defaultValue
        );
        for (int i = 0; i < values.length; i++)
            matrix.setValue(i, values[i]);
        return matrix;
    }

    /**
     * calls {@link #ofValuesByCols(AbstractArithmetic, Number, int, Number[])}
     * using {@link AbstractArithmetic#zero()} as {@code defaultValue}
     *
     * @param arithmetic arithmetic for calculations
     * @param cols       cols of matrix
     * @param values     column based values of matrix
     * @param <T>        number class
     * @return new created matrix
     * @throws IllegalArgumentException if cols modulo {@code values.length}
     *                                  is not congruent {@code 0}
     * @throws IllegalArgumentException if rows or cols is less than {@code 1}
     * @since 1.0.0
     */
    @NotNull
    @SafeVarargs
    public static <T extends Number> Matrix<T> ofValuesByCols(
        @NotNull AbstractArithmetic<T> arithmetic, int cols, @NotNull T... values
    ) {
        return ofValuesByCols(arithmetic, arithmetic.zero(), cols, values);
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
     *                                  is not congruent {@code 0}
     * @throws IllegalArgumentException if rows or cols is less than {@code 1}
     * @since 1.0.0
     */
    @NotNull
    @SafeVarargs
    protected static <T extends Number> Matrix<T> ofValuesByCols(
        @NotNull AbstractArithmetic<T> arithmetic,
        @NotNull T defaultValue, int cols, @NotNull T... values
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
     * @since 1.0.0
     */
    @NotNull
    public <NT extends Number> Matrix<NT> map(
        @NotNull AbstractArithmetic<NT> arithmetic, @NotNull Function<T, NT> map
    ) {
        Matrix<NT> matrix = new Matrix<>(
            arithmetic, getRows(), getCols(), map.apply(getDefaultValue())
        );
        forEach(field -> matrix.setValue(
            field.getIndex(), map.apply(field.getValue())
        ));
        return matrix;
    }

    /**
     * @param newDefaultValue new default value for matrix
     * @return mapped matrix
     * @since 1.0.0
     */
    @NotNull
    protected Matrix<T> mapDefaultValue(@NotNull T newDefaultValue) {
        if (isDefaultValue(newDefaultValue)) return copy();
        Matrix<T> mapped = new Matrix<>(
            getArithmetic(), getRows(), getCols(), newDefaultValue
        );
        LongStream.range(0, size())
            .forEach(i -> mapped.setValue(i, getValue(i)));
        return mapped;
    }

    @Override
    @NotNull
    public Matrix<T> copy() {
        return new Matrix<>(this);
    }

    // endregion

    // region override

    @Override
    @NotNull
    public Iterator<Field> iterator() {
        return new Iterator<>() {
            private long index = 0;

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
    @SuppressWarnings("unchecked") // can only fail in isEqual
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix<?>)) return false;
        Matrix<?> matrix = (Matrix<?>) o;
        return getRows() == matrix.getRows() &&
            getCols() == matrix.getCols() &&
            LongStream.range(0, size()).allMatch(i ->
                getArithmetic().isEqual(getValue(i), (T) matrix.getValue(i))
            );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMatrix(), getRows(), getCols(), getDefaultValue());
    }

    @Override
    @NotNull
    public String toString() {
        return getRows() + " " + getCols() + ": " + getMatrix().entrySet();
    }

    // endregion

    // region protected: subMatrix, coFactor and signumFactor

    /**
     * @param row row to exclude
     * @param col col to exclude
     * @return new sub matrix excluding specified row and col
     * @throws NotSupportedException     if {@link #isSquare()} is {@code false}
     * @throws IndexOutOfBoundsException if row or col is invalid
     * @since 1.0.0
     */
    @NotNull
    protected final Matrix<T> subMatrix(int row, int col) {
        if (!isSquare())
            throw new NotSupportedException(EXCEPTION_NO_SQUARE);
        if (!isValidRow(row))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row + " / " + getRows());
        if (!isValidCol(col))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col + " / " + getCols());
        Matrix<T> subMatrix = new Matrix<>(getArithmetic(),
            getRows() - 1, getCols() - 1, getDefaultValue()
        );
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
     * @throws NotSupportedException     if {@link #isSquare()} is {@code false}
     * @throws IndexOutOfBoundsException if row or col is invalid
     * @see #subMatrix(int, int)
     * @see #signumFactor(int, int)
     * @since 1.0.0
     */
    @NotNull
    protected final T coFactor(int row, int col) {
        if (!isSquare())
            throw new NotSupportedException(EXCEPTION_NO_SQUARE);
        if (!isValidRow(row))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row + " / " + getRows());
        if (!isValidCol(col))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col + " / " + getCols());
        return getArithmetic().product(
            getArithmetic().product(
                getValue(row, col),
                getArithmetic().fromInt(signumFactor(row, col))
            ),
            subMatrix(row, col).determinante()
        );
    }

    /**
     * @return coFactor matrix
     * @throws NotSupportedException if {@link #isSquare()} is {@code false}
     * @see #coFactor(int, int)
     * @since 1.0.0
     */
    @NotNull
    protected final Matrix<T> coFactorMatrix() {
        if (!isSquare())
            throw new NotSupportedException(EXCEPTION_NO_SQUARE);
        Matrix<T> result = copy();
        result.computeAll(field -> coFactor(field.getRow(), field.getCol()));
        return result;
    }

    /**
     * @return transposed coFactorMatrix
     * @see #coFactorMatrix()
     * @since 1.0.0
     */
    @NotNull
    protected final Matrix<T> adjunctMatrix() {
        return coFactorMatrix().transpose();
    }

    /**
     * @param row row of matrix
     * @param col col of matrix
     * @return {@code (-1)^(row+col)}
     * @since 1.0.0
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
     * @since 1.0.0
     */
    @NotNull
    protected Matrix<T> swapRows(int row1, int row2) {
        if (!isValidRow(row1))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row1 + " / " + getRows());
        if (!isValidRow(row2))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row2 + " / " + getRows());
        Matrix<T> result = copy();
        if (row1 == row2) return result;
        for (int c = 0; c < getCols(); c++) {
            result.setValue(row1, c, getValue(row2, c));
            result.setValue(row2, c, getValue(row1, c));
        }
        return result;
    }

    /**
     * @param col1 col1 to swap with col2
     * @param col2 col2 to swap with col1
     * @return new matrix with swapped values
     * @throws IndexOutOfBoundsException if col1 or col2 is invalid
     * @since 1.0.0
     */
    @NotNull
    protected Matrix<T> swapCols(int col1, int col2) {
        if (!isValidCol(col1))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col1 + " / " + getCols());
        if (!isValidCol(col2))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col2 + " / " + getCols());
        Matrix<T> result = copy();
        if (col1 == col2) return result;
        for (int r = 0; r < getRows(); r++) {
            result.setValue(r, col1, getValue(r, col2));
            result.setValue(r, col2, getValue(r, col1));
        }
        return result;
    }

    /**
     * @param row row to multiply
     * @param n   factor to use
     * @return new matrix with multiplied row
     * @throws IndexOutOfBoundsException if row is invalid
     * @since 1.0.0
     */
    @NotNull
    protected Matrix<T> multiplyRow(int row, @NotNull T n) {
        if (!isValidRow(row))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row + " / " + getRows());
        Matrix<T> result = copy();
        if (isZero(n)) {
            for (int i = 0; i < getCols(); i++)
                result.setValue(row, i, getArithmetic().zero());
            return result;
        }
        if (getArithmetic().one().equals(n))
            return result;
        for (int c = 0; c < getCols(); c++)
            result.compute(row, c, n, getArithmetic()::product);
        return result;
    }

    /**
     * @param col col to multiply
     * @param n   factor to use
     * @return new matrix with multiplied col
     * @throws IndexOutOfBoundsException if col is invalid
     * @since 1.0.0
     */
    @NotNull
    protected Matrix<T> multiplyCol(int col, @NotNull T n) {
        if (!isValidCol(col))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col + " / " + getCols());
        Matrix<T> result = copy();
        if (isZero(n)) {
            for (int i = 0; i < getCols(); i++)
                result.setValue(i, col, getArithmetic().zero());
            return result;
        }
        if (getArithmetic().one().equals(n))
            return result;
        for (int r = 0; r < getRows(); r++)
            result.compute(r, col, n, getArithmetic()::product);
        return result;
    }

    /**
     * @param row1 row to multiply with other multiple times
     * @param row2 row to multiply multiple times with other
     * @param n    factor to use
     * @return new matrix with multiplied row
     * @throws IndexOutOfBoundsException if row1 or row2 is invalid
     * @since 1.0.0
     */
    @NotNull
    protected Matrix<T> addRowMultipleTimes(int row1, int row2, @NotNull T n) {
        if (!isValidRow(row1))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row1 + " / " + getRows());
        if (!isValidRow(row2))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row2 + " / " + getRows());
        if (isZero(n))
            return copy();
        if (row1 == row2) return multiplyRow(row1, n);
        Matrix<T> result = copy();
        for (int c = 0; c < getCols(); c++)
            result.compute(row1, c,
                getArithmetic().product(getValue(row2, c), n),
                getArithmetic()::sum
            );
        return result;
    }

    /**
     * @param col1 col to multiply with other multiple times
     * @param col2 col to multiply multiple times with other
     * @param n    factor to use
     * @return new matrix with multiplied col
     * @throws IndexOutOfBoundsException if col1 or col2 is invalid
     * @since 1.0.0
     */
    @NotNull
    protected Matrix<T> addColMultipleTimes(int col1, int col2, @NotNull T n) {
        if (!isValidCol(col1))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col1 + " / " + getCols());
        if (!isValidCol(col2))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col2 + " / " + getCols());
        if (isZero(n))
            return copy();
        if (col1 == col2) return multiplyCol(col1, n);
        Matrix<T> result = copy();
        for (int r = 0; r < getRows(); r++)
            result.compute(r, col1,
                getArithmetic().product(getValue(r, col2), n),
                getArithmetic()::sum
            );
        return result;
    }

    // endregion

    // region protected: modify - rowEchelonForm

    /**
     * swaps zero rows to bottom
     *
     * @return new matrix with swapped rows
     * @since 1.0.0
     */
    @NotNull
    protected Matrix<T> swapZeroRowsToBottom() {
        // see GaussSolver#prepareMatrixBySwappingZeroRowsToBottom
        Matrix<T> result = copy();
        for (int i = 0; i < result.getRows() - 1; i++) {
            if (!result.isZeroRow(i)) continue;
            for (int j = i + 1; j < result.getRows(); j++)
                if (!result.isZeroRow(j)) {
                    result = result.swapRows(i, j);
                    break;
                }
        }
        return result;
    }

    /**
     * swaps rows so diagonal fields are non zero
     *
     * @return new matrix with swapped rows
     * @see #ensureDiagonalFieldsAreNonZero(boolean)
     * @since 1.0.0
     */
    @NotNull
    protected Matrix<T> ensureDiagonalFieldsAreNonZero() {
        return ensureDiagonalFieldsAreNonZero(false);
    }

    /**
     * swaps rows (or if necessary columns) so diagonal fields are non zero
     *
     * @param includeCols if {@code true} columns may be swapped if necessary
     * @return new matrix with swapped rows
     * @since 1.0.0
     */
    @NotNull
    protected Matrix<T> ensureDiagonalFieldsAreNonZero(boolean includeCols) {
        // see GaussSolver#prepareMatrixBySwapping
        Matrix<T> result = copy();
        rowIndex:
        for (int rowIndex = 0; rowIndex < result.getRows(); rowIndex++) {
            if (result.isZeroRow(rowIndex)) break;
            List<T> row = result.getRow(rowIndex);
            if (row.size() <= rowIndex || !isZero(row.get(rowIndex)))
                continue;
            List<T> col = result.getCol(rowIndex);
            for (int i = rowIndex + 1; i < result.getRows(); i++)
                if (!isZero(col.get(i))) {
                    result = result.swapRows(rowIndex, i);
                    continue rowIndex;
                } else if (result.isZeroRow(i)) break;
            for (int i = rowIndex + 1; includeCols && i < result.getCols(); i++)
                if (!isZero(row.get(i))) {
                    result = result.swapCols(rowIndex, i);
                    continue rowIndex;
                }
        }
        return result;
    }

    // endregion

    // region protected: getIndexOfRowAndCol, isValid and isDefaultValue

    /**
     * @param row row of requested index
     * @param col col of requested index
     * @return index of requested position
     * @throws IndexOutOfBoundsException if row or col is invalid
     * @since 1.0.0
     */
    protected final long getIndexOfRowAndCol(int row, int col) {
        if (!isValidRow(row))
            throw new IndexOutOfBoundsException(EXCEPTION_ROW_PREFIX + row + " / " + getRows());
        if (!isValidCol(col))
            throw new IndexOutOfBoundsException(EXCEPTION_COL_PREFIX + col + " / " + getCols());
        return (long) row * getCols() + col;
    }

    /**
     * @param index index to check
     * @return {@code true} if value is valid
     * @since 1.0.0
     */
    protected final boolean isValidIndex(long index) {
        return 0 <= index && index < size();
    }

    /**
     * @param row row to check
     * @return {@code true} if value is valid
     * @since 1.0.0
     */
    protected final boolean isValidRow(int row) {
        return 0 <= row && row < getRows();
    }

    /**
     * @param col col to check
     * @return {@code true} if col is valid
     * @since 1.0.0
     */
    protected final boolean isValidCol(int col) {
        return 0 <= col && col < getCols();
    }

    /**
     * @param t value to check
     * @return {@code true} if {@code t} is equal to {@link #getDefaultValue()}
     * @see AbstractArithmetic#isEqual(Number, Number)
     * @since 1.0.0
     */
    protected final boolean isDefaultValue(@NotNull T t) {
        return getArithmetic().isEqual(getDefaultValue(), t);
    }

    /**
     * @param t value to check
     * @return {@code true} if {@link AbstractArithmetic#isZero(Number)} is
     * @see AbstractArithmetic#isZero(Number)
     * @since 1.0.0
     */
    protected final boolean isZero(@NotNull T t) {
        return getArithmetic().isZero(t);
    }

    /**
     * @param row row to check
     * @return {@code true} if value is valid
     * @throws IndexOutOfBoundsException if row is invalid
     * @see #getRow(int)
     * @since 1.0.0
     */
    protected final boolean isZeroRow(int row) {
        return getRow(row).stream().allMatch(this::isZero);
    }

    // endregion

    // region private

    private void removeDefaultValues() {
        getMatrix().entrySet().removeIf(integerMapEntry -> {
            if (integerMapEntry.getValue() == null) return true;
            integerMapEntry.getValue().entrySet().removeIf(integerTEntry ->
                isDefaultValue(integerTEntry.getValue())
            );
            return integerMapEntry.getValue().isEmpty();
        });
    }

    @NotNull
    private static <T extends Number> Map.Entry<Integer, List<Matrix<T>.Field>>
    getBestEntry(@NotNull List<Matrix<T>.Field> zeros, boolean isRowMode) {
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
     * class which holds a field of a matrix with immutable attributes
     *
     * @since 1.0.0
     */
    public class Field {
        private final long index;
        private final T value;

        /**
         * @param row   row of field
         * @param col   col of field
         * @param value value of field
         * @throws IndexOutOfBoundsException if row or col is invalid
         * @see #Field(long, Number)
         * @see #getIndexOfRowAndCol(int, int)
         * @since 1.0.0
         */
        protected Field(int row, int col, T value) {
            this(getIndexOfRowAndCol(row, col), value);
        }

        /**
         * @param index index of field
         * @param value value of field
         * @throws IndexOutOfBoundsException if index is invalid
         * @since 1.0.0
         */
        protected Field(long index, T value) {
            if (!isValidIndex(index))
                throw new IndexOutOfBoundsException(EXCEPTION_SIZE_PREFIX + index + " / " + size());
            this.index = index;
            this.value = value;
        }

        /**
         * @return row of field
         * @since 1.0.0
         */
        public final int getRow() {
            return (int) (getIndex() / getCols());
        }

        /**
         * @return col of field
         * @since 1.0.0
         */
        public final int getCol() {
            return (int) (getIndex() % getCols());
        }

        /**
         * @return index of field
         * @since 1.0.0
         */
        public final long getIndex() {
            return index;
        }

        /**
         * @return value of field
         * @since 1.0.0
         */
        @NotNull
        public T getValue() {
            return value;
        }

        /**
         * @return Matrix instance of field
         * @since 1.0.0
         */
        @NotNull
        protected Matrix<T> getMatrix() {
            return Matrix.this;
        }

        @Override
        @SuppressWarnings("unchecked") // can only fail in isEqual
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Matrix<?>.Field field = (Matrix<?>.Field) o;
            return Objects.equals(getMatrix(), field.getMatrix()) &&
                getIndex() == field.getIndex() &&
                getArithmetic().isEqual(getValue(), (T) field.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getIndex(), getValue());
        }

        @Override
        @NotNull
        public String toString() {
            return getIndex() + ": " + getValue();
        }
    }
}
