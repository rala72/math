package io.rala.math.algebra.vector;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.exception.NotSupportedException;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.StreamIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * class which holds a vector of {@code size}
 *
 * @param <T> number class
 * @since 1.0.0
 */
public class Vector<T extends Number>
    implements Copyable<Vector<T>>, StreamIterable<Vector<T>.Entry>, Serializable {

    /**
     * describes whether a vector is {@link #COLUMN} or {@link #ROW}
     *
     * @since 1.0.0
     */
    public enum Type {ROW, COLUMN}

    // region protected exception messages
    protected static final String EXCEPTION_SIZE_UNEQUAL_1 =
        "size has to be exactly one";
    protected static final String EXCEPTION_SIZES_UNEQUAL =
        "sizes have to be equal";
    protected static final String EXCEPTION_TYPES_UNEQUAL =
        "types have to be either both row or column";
    protected static final String EXCEPTION_NOT_POSITIV_P_NORM =
        "may only calculate positive p-norm";
    protected static final String EXCEPTION_ZERO_VECTOR_NO_NORMALIZE =
        "zero vector can not be normalized";
    protected static final String EXCEPTION_ZERO_VECTOR_NO_ANGLE =
        "angle is not defined for zero vector";
    // endregion

    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private final Map<Integer, T> vector = new HashMap<>();
    private final int size;
    private final Type type;
    private final T defaultValue;

    // endregion

    // region constructors

    /**
     * calls {@link #Vector(AbstractArithmetic, int, Number)}
     * using {@code 0} as {@code defaultValue}
     *
     * @param arithmetic arithmetic for calculations
     * @param size       size of vector
     * @throws IllegalArgumentException if size is less than {@code 1}
     * @since 1.0.0
     */
    public Vector(@NotNull AbstractArithmetic<T> arithmetic, int size) {
        this(arithmetic, size, arithmetic.zero());
    }

    /**
     * calls {@link #Vector(AbstractArithmetic, int, Type, Number)}
     * using {@link Type#COLUMN} as default {@link Type}
     *
     * @param arithmetic   arithmetic for calculations
     * @param size         size of vector
     * @param defaultValue default value of non-existing values
     * @throws IllegalArgumentException if size is less than {@code 1}
     * @since 1.0.0
     */
    protected Vector(
        @NotNull AbstractArithmetic<T> arithmetic, int size,
        @NotNull T defaultValue
    ) {
        this(arithmetic, size, Type.COLUMN, defaultValue);
    }

    /**
     * calls {@link #Vector(AbstractArithmetic, int, Type, Number)}
     * using {@code 0} as {@code defaultValue}
     *
     * @param arithmetic arithmetic for calculations
     * @param size       size of vector
     * @param type       type of vector
     * @throws IllegalArgumentException if size is less than {@code 1}
     * @since 1.0.0
     */
    public Vector(
        @NotNull AbstractArithmetic<T> arithmetic, int size,
        @Nullable Type type
    ) {
        this(arithmetic, size, type, arithmetic.zero());
    }

    /**
     * creates a new vector with given size and given {@link Type}
     * which uses given default value for non-existing values
     *
     * @param arithmetic   arithmetic for calculations
     * @param size         size of vector
     * @param defaultValue default value of non-existing values
     * @param type         type of vector
     * @throws IllegalArgumentException if size is less than {@code 1}
     * @since 1.0.0
     */
    protected Vector(
        @NotNull AbstractArithmetic<T> arithmetic, int size,
        @Nullable Type type, @NotNull T defaultValue
    ) {
        if (size <= 0)
            throw new IllegalArgumentException("size has to be greater than 0");
        this.arithmetic = arithmetic;
        this.size = size;
        this.defaultValue = defaultValue;
        this.type = type != null ? type : Type.COLUMN;
    }

    /**
     * creates a new vector based on given one
     *
     * @param vector vector to copy
     * @since 1.0.0
     */
    protected Vector(@NotNull Vector<T> vector) {
        this(vector.getArithmetic(), vector.getSize(),
            vector.getType(), vector.getDefaultValue()
        );
        vector.getVector().forEach((key, value) -> getVector().put(key, value));
    }

    // endregion

    // region getter and length

    /**
     * @return stored arithmetic
     * @since 1.0.0
     */
    @NotNull
    public AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

    /**
     * @return vector map using index as key
     * @since 1.0.0
     */
    @NotNull
    protected final Map<Integer, T> getVector() {
        return vector;
    }

    /**
     * @return size of vector
     * @since 1.0.0
     */
    public final int getSize() {
        return size;
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
     * @return type of vector
     * @since 1.0.0
     */
    @NotNull
    public final Type getType() {
        return type;
    }

    /**
     * @return {@code true} if {@link #getType()} returns {@link Type#ROW}
     * @see #isColumn()
     * @since 1.0.0
     */
    public final boolean isRow() {
        return Type.ROW.equals(getType());
    }

    /**
     * @return {@code true} if {@link #getType()} returns {@link Type#COLUMN}
     * @see #isRow()
     * @since 1.0.0
     */
    public final boolean isColumn() {
        return Type.COLUMN.equals(getType());
    }

    /**
     * @return {@link #euclideanNorm()}
     * @since 1.0.0
     */
    @NotNull
    public T length() {
        return euclideanNorm();
    }

    // endregion

    // region value

    /**
     * @param index of requested value
     * @return value at index if exists or {@link #getDefaultValue()}
     * @throws IndexOutOfBoundsException if index is invalid
     * @since 1.0.0
     */
    @NotNull
    public T getValue(int index) {
        if (!isValidIndex(index)) throw new IndexOutOfBoundsException(index + " / " + getSize());
        return getVector().getOrDefault(index, getDefaultValue());
    }

    /**
     * @param index index where value should be stored
     * @param value new value to store
     * @return old value if existed or {@link #getDefaultValue()}
     * @throws IndexOutOfBoundsException if index is invalid
     * @since 1.0.0
     */
    @NotNull
    public T setValue(int index, @NotNull T value) {
        if (!isValidIndex(index)) throw new IndexOutOfBoundsException(index + " / " + getSize());
        if (getArithmetic().isEqual(value, getDefaultValue()))
            return removeValue(index);
        T old = getVector().put(index, value);
        return old == null ? getDefaultValue() : old;
    }

    /**
     * @param index index of value to remove
     * @return old value if existed or {@link #getDefaultValue()}
     * @throws IndexOutOfBoundsException if index is invalid
     * @since 1.0.0
     */
    @NotNull
    public T removeValue(int index) {
        if (!isValidIndex(index)) throw new IndexOutOfBoundsException(index + " / " + getSize());
        T old = getVector().remove(index);
        return old == null ? getDefaultValue() : old;
    }

    // endregion

    // region compute

    /**
     * @param index    index where new value is computed
     * @param operator operator to apply to current value
     * @return old value if existed or {@link #getDefaultValue()}
     * @see #setValue(int, Number)
     * @see #getValue(int)
     * @since 1.0.0
     */
    @NotNull
    public T compute(int index, @NotNull UnaryOperator<T> operator) {
        return setValue(index, operator.apply(getValue(index)));
    }

    /**
     * @param index    index where new value is computed
     * @param value    new value to use in computation
     * @param operator operator to apply to old and new value
     * @return old value if existed or {@link #getDefaultValue()}
     * @see #setValue(int, Number)
     * @see #getValue(int)
     * @since 1.0.0
     */
    @NotNull
    public T compute(int index, @NotNull T value, @NotNull BinaryOperator<T> operator) {
        return setValue(index, operator.apply(getValue(index), value));
    }

    /**
     * @param operator operator to apply to all entries
     * @see #setValue(int, Number)
     * @since 1.0.0
     */
    public void computeAll(@NotNull Function<Entry, T> operator) {
        forEach(entry -> setValue(entry.getIndex(), operator.apply(entry)));
    }

    /**
     * @param value    function returning new value to use in computation
     * @param operator operator to apply to all entries
     * @see #computeAll(Function)
     * @since 1.0.0
     */
    public void computeAll(@NotNull Function<Entry, T> value, @NotNull BinaryOperator<T> operator) {
        computeAll(entry -> operator.apply(entry.getValue(), value.apply(entry)));
    }

    // endregion

    // region toMatrix and toParam

    /**
     * @return matrix equivalent to vector
     * @since 1.0.0
     */
    @NotNull
    public Matrix<T> toMatrix() {
        Matrix<T> matrix = new Matrix<>(
            getArithmetic(),
            Type.COLUMN.equals(getType()) ? getSize() : 1,
            Type.COLUMN.equals(getType()) ? 1 : getSize()
        );
        forEach(entry -> matrix.setValue(entry.getIndex(), entry.getValue()));
        return matrix;
    }

    /**
     * @return only entry of a size {@code 1} vector
     * @throws NotSupportedException if size is unequal to {@code 1}
     * @since 1.0.0
     */
    @NotNull
    public T toParam() {
        if (getSize() == 1) return getValue(0);
        throw new NotSupportedException(EXCEPTION_SIZE_UNEQUAL_1);
    }

    // endregion

    // region add, subtract, multiply and dotProduct

    /**
     * @param vector vector to add
     * @return new vector with calculated values
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> add(@NotNull Vector<T> vector) {
        if (getSize() != vector.getSize())
            throw new IllegalArgumentException(EXCEPTION_SIZES_UNEQUAL);
        if (getType() != vector.getType())
            throw new IllegalArgumentException(EXCEPTION_TYPES_UNEQUAL);
        Vector<T> result = copy();
        result.computeAll(entry -> getArithmetic().sum(entry.getValue(), vector.getValue(entry.getIndex())));
        result.removeDefaultValues();
        return result;
    }

    /**
     * @param vector vector to subtract
     * @return new vector with calculated values
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> subtract(@NotNull Vector<T> vector) {
        return add(vector.invert());
    }

    /**
     * @param scalar scalar to multiply entries with
     * @return new vector with calculated values
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> multiply(@NotNull T scalar) {
        Vector<T> result = copy();
        result.computeAll(entry -> getArithmetic().product(entry.getValue(), scalar));
        result.removeDefaultValues();
        return result;
    }

    /**
     * @param vector vector to multiply with
     * @return product of matrix multiplication
     * @throws IllegalArgumentException if sizes do not match
     * @see Matrix#multiply(Matrix)
     * @since 1.0.0
     */
    @NotNull
    public Matrix<T> multiply(@NotNull Vector<T> vector) {
        return toMatrix().multiply(vector.toMatrix());
    }

    /**
     * @param vector vector to compute dot product
     * @return dot product
     * @throws IllegalArgumentException if sizes do not match
     * @see Matrix#multiply(Matrix)
     * @see #toParam()
     * @since 1.0.0
     */
    @NotNull
    public T dotProduct(@NotNull Vector<T> vector) {
        Vector<T> v1 =
            new Vector<>(getType() == Type.ROW ? this : transpose());
        Vector<T> v2 =
            new Vector<>(vector.isColumn() ?
                vector : vector.transpose()
            );
        return v1.toMatrix().multiply(v2.toMatrix()).toParam();
    }

    // endregion

    // region transpose and invert

    /**
     * @return new vector with opposite {@link Type}
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> transpose() {
        Vector<T> flipped =
            new Vector<>(getArithmetic(), getSize(),
                Type.COLUMN.equals(getType()) ? Type.ROW : Type.COLUMN,
                getDefaultValue()
            );
        getVector().forEach(flipped::setValue);
        return flipped;
    }

    /**
     * @return new vector with inverted sign
     * @see #multiply(Number)
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> invert() {
        return multiply(getArithmetic().negate(getArithmetic().one()));
    }

    // endregion

    // region norm and normalize

    /**
     * @return max-Norm of the vector, equal to the entry with highest absolute value
     * @since 1.0.0
     */
    @NotNull
    public T maxNorm() {
        return getVector().values().stream().max(
            (a, b) -> getArithmetic().difference(
                getArithmetic().absolute(a),
                getArithmetic().absolute(b)
            ).intValue()
        ).orElse(getArithmetic().zero());
    }

    /**
     * calls {@link #pNorm(int)} with degree {@code 2}
     *
     * @return euclidean norm of the vector
     * @since 1.0.0
     */
    @NotNull
    public T euclideanNorm() {
        return pNorm(2);
    }

    /**
     * @param p degree of the norm
     * @return p-norm of the vector
     * @throws IllegalArgumentException if p is less than {@code 1}
     * @since 1.0.0
     */
    @NotNull
    public T pNorm(int p) {
        if (p <= 0) throw new IllegalArgumentException(EXCEPTION_NOT_POSITIV_P_NORM);
        Map<Integer, T> powers = getVector().entrySet().stream()
            .map(integerTEntry -> Map.entry(
                integerTEntry.getKey(),
                getArithmetic().power(integerTEntry.getValue(), p)
            )).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return getArithmetic().root(
            getArithmetic().absolute(
                powers.values().stream().reduce(getArithmetic()::sum)
                    .orElse(getArithmetic().zero())
            ), p
        );
    }

    /**
     * result is only guaranteed to be correct for
     * floating point numbers and {@link Fraction}
     *
     * @return vector of {@link #length()} one
     * @throws NotSupportedException if all values are {@code 0}
     * @since 1.0.0
     */
    @NotNull
    public Vector<T> normalize() {
        if (isZero())
            throw new NotSupportedException(EXCEPTION_ZERO_VECTOR_NO_NORMALIZE);
        Vector<T> unit =
            new Vector<>(
                getArithmetic(), getSize(), getType(), getDefaultValue()
            );
        T norm = euclideanNorm();
        forEach(entry -> unit.setValue(
            entry.getIndex(),
            getArithmetic().quotient(entry.getValue(), norm)
        ));
        return unit;
    }

    // endregion

    // region angle

    /**
     * @param vector second vector of angle to calculate
     * @return angle in radian
     * @throws NotSupportedException if any vector has only values equal {@code 0}
     * @since 1.0.0
     */
    @NotNull
    public T angle(@NotNull Vector<T> vector) {
        if (isZero() || vector.isZero())
            throw new NotSupportedException(EXCEPTION_ZERO_VECTOR_NO_ANGLE);
        return getArithmetic().acos(
            getArithmetic().quotient(
                dotProduct(vector),
                getArithmetic().product(
                    euclideanNorm(),
                    vector.euclideanNorm()
                )
            )
        );
    }

    // endregion

    // region static

    /**
     * creates a new vector containing all provided values
     *
     * @param arithmetic arithmetic for calculations
     * @param values     values of vector
     * @param <T>        number class
     * @return new created vector
     * @throws IllegalArgumentException if size is less than {@code 1}
     * @since 1.0.0
     */
    @SafeVarargs
    @NotNull
    public static <T extends Number> Vector<T> ofValues(
        @NotNull AbstractArithmetic<T> arithmetic, @NotNull T... values
    ) {
        return ofValues(arithmetic, arithmetic.zero(), values);
    }

    /**
     * creates a new vector containing all provided values
     *
     * @param arithmetic   arithmetic for calculations
     * @param defaultValue default value of non-existing values
     * @param values       values of vector
     * @param <T>          number class
     * @return new created vector
     * @throws IllegalArgumentException if size is less than {@code 1}
     * @since 1.0.0
     */
    @SafeVarargs
    @NotNull
    private static <T extends Number> Vector<T> ofValues(
        @NotNull AbstractArithmetic<T> arithmetic,
        @NotNull T defaultValue, @NotNull T... values
    ) {
        Vector<T> vector =
            new Vector<>(arithmetic, values.length, defaultValue);
        for (int i = 0; i < values.length; i++)
            vector.setValue(i, values[i]);
        return vector;
    }

    /**
     * creates a new vector containing values from provided list
     *
     * @param arithmetic arithmetic for calculations
     * @param values     values of vector
     * @param <T>        number class
     * @return new created vector
     * @throws IllegalArgumentException if size is less than {@code 1}
     * @since 1.0.0
     */
    @NotNull
    public static <T extends Number> Vector<T> ofList(
        @NotNull AbstractArithmetic<T> arithmetic, @NotNull List<T> values
    ) {
        return ofList(arithmetic, values, arithmetic.zero());
    }

    /**
     * creates a new vector containing values from provided list
     *
     * @param arithmetic   arithmetic for calculations
     * @param values       values of vector
     * @param defaultValue default value for non-existing values
     * @param <T>          number class
     * @return new created vector
     * @throws IllegalArgumentException if size is less than {@code 1}
     * @since 1.0.0
     */
    @NotNull
    protected static <T extends Number> Vector<T> ofList(
        @NotNull AbstractArithmetic<T> arithmetic,
        @NotNull List<T> values, @NotNull T defaultValue
    ) {
        Vector<T> vector = new Vector<>(arithmetic, values.size(), defaultValue);
        for (int i = 0; i < values.size(); i++)
            vector.setValue(i, values.get(i));
        return vector;
    }

    // endregion

    // region override

    @Override
    @NotNull
    public Vector<T> copy() {
        return new Vector<>(this);
    }

    @Override
    @NotNull
    public Iterator<Entry> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < getSize();
            }

            @Override
            public Entry next() {
                T value = getValue(index);
                return new Entry(index++, value);
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector<?>)) return false;
        Vector<?> vector = (Vector<?>) o;
        //noinspection unchecked
        return getSize() == vector.getSize() &&
            getType() == vector.getType() &&
            IntStream.range(0, getSize()).allMatch(index ->
                getArithmetic().isEqual(
                    getValue(index),
                    (T) vector.getValue(index)
                )
            );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVector(), getSize(), getType(), getDefaultValue());
    }

    @Override
    @NotNull
    public String toString() {
        return getSize() + ": " + getVector().entrySet();
    }

    // endregion

    // region protected: validation

    /**
     * @param index to be validated
     * @return {@code true} if value is valid
     * @since 1.0.0
     */
    protected final boolean isValidIndex(int index) {
        return 0 <= index && index < getSize();
    }

    /**
     * @return {@code true} if all values are zero
     * @since 1.0.0
     */
    protected final boolean isZero() {
        return stream().allMatch(
            (entry) -> getArithmetic().isZero(entry.getValue())
        );
    }

    // endregion

    // region private

    private void removeDefaultValues() {
        getVector().entrySet().removeIf(
            entry -> getArithmetic().isEqual(getDefaultValue(), entry.getValue()));
    }

    // endregion

    /**
     * class which holds an entry of a vector with immutable attributes
     *
     * @since 1.0.0
     */
    public class Entry {
        private final int index;
        private final T value;

        /**
         * @param index index of entry
         * @param value value of entry
         * @throws IndexOutOfBoundsException if index is invalid
         * @since 1.0.0
         */
        protected Entry(int index, T value) {
            if (!isValidIndex(index))
                throw new IndexOutOfBoundsException(index + " / " + getSize());
            this.index = index;
            this.value = value;
        }

        /**
         * @return index of entry
         * @since 1.0.0
         */
        public final int getIndex() {
            return index;
        }

        /**
         * @return value of entry
         * @since 1.0.0
         */
        @NotNull
        public T getValue() {
            return value;
        }

        /**
         * @return Vector instance of entry
         * @since 1.0.0
         */
        @NotNull
        protected Vector<T> getVector() {
            return Vector.this;
        }

        @Override
        @SuppressWarnings("unchecked") // can only fail in isEqual
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vector<?>.Entry entry = (Vector<?>.Entry) o;
            return Objects.equals(getVector(), entry.getVector()) &&
                getIndex() == entry.getIndex() &&
                getArithmetic().isEqual(getValue(), (T) entry.getValue());
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
