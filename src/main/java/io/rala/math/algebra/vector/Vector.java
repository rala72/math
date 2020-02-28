package io.rala.math.algebra.vector;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.StreamIterable;

import java.util.*;
import java.util.stream.IntStream;

public class Vector<T extends Number>
    implements Copyable<Vector<T>>, StreamIterable<Vector<T>.Entry> {

    public enum Type {ROW, COLUMN}

    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private final Map<Integer, T> vector = new HashMap<>();
    private final int size;
    private final T defaultValue;
    private final Type type;

    // endregion

    // region constructors

    /**
     * calls {@link #Vector(AbstractArithmetic, int, Number, Type)}
     * {@link Type#COLUMN} as default type
     *
     * @param arithmetic   arithmetic for calculations
     * @param size         size of vector
     * @param defaultValue default value of non-existing values
     * @throws IllegalArgumentException if size is negative or zero
     */
    public Vector(AbstractArithmetic<T> arithmetic, int size, T defaultValue) {
        this(arithmetic, size, defaultValue, Type.COLUMN);
    }

    /**
     * creates a new vector with given size and given type
     * which uses given default value for non-existing values
     *
     * @param arithmetic   arithmetic for calculations
     * @param size         size of vector
     * @param defaultValue default value of non-existing values
     * @param type         type of vector
     * @throws IllegalArgumentException if size is negative or zero
     */
    public Vector(
        AbstractArithmetic<T> arithmetic, int size, T defaultValue, Type type
    ) {
        if (size < 0)
            throw new IllegalArgumentException("Size has to be greater than 0");
        this.arithmetic = arithmetic;
        this.size = size;
        this.defaultValue = defaultValue;
        this.type = type;
    }

    /**
     * calls {@link #Vector(AbstractArithmetic, List, Number, Type)}
     * with {@link Type#COLUMN} as default type
     *
     * @param arithmetic   arithmetic for calculations
     * @param values       values of vector entries
     * @param defaultValue default value for non-existing values
     * @throws IllegalArgumentException if values is null or its size is zero
     */
    public Vector(
        AbstractArithmetic<T> arithmetic, List<T> values, T defaultValue
    ) {
        this(arithmetic, values, defaultValue, Type.COLUMN);
    }

    /**
     * calls {@link #Vector(AbstractArithmetic, int, Number, Type)}
     *
     * @param arithmetic   arithmetic for calculations
     * @param values       values of vector entries
     * @param defaultValue default value for non-existing values
     * @param type         type of vector
     * @throws IllegalArgumentException if values is null or its size is zero
     */
    public Vector(
        AbstractArithmetic<T> arithmetic, List<T> values,
        T defaultValue, Type type
    ) {
        this(arithmetic, values == null
            ? -1 : values.size(), defaultValue, type
        );
        assert values != null;
        for (int i = 0; i < size; i++) {
            vector.put(i, values.get(i));
        }
    }

    /**
     * calls {@link #Vector(AbstractArithmetic, Map, Number, Type)}
     * with {@link Type#COLUMN} as default type
     *
     * @param arithmetic   arithmetic for calculations
     * @param values       values of vector entries
     * @param defaultValue default value for non-existing values
     * @throws IllegalArgumentException if values is null or its size is zero
     */
    public Vector(
        AbstractArithmetic<T> arithmetic, Map<Integer,
        T> values, T defaultValue
    ) {
        this(arithmetic, values, defaultValue, Type.COLUMN);
    }

    /**
     * calls {@link #Vector(AbstractArithmetic, int, Number, Type)}
     *
     * @param arithmetic   arithmetic for calculation
     * @param values       values of vector entries
     * @param defaultValue default value for non-existing values
     * @param type         type of vector
     * @throws IllegalArgumentException if values is null or its size is zero
     */
    public Vector(
        AbstractArithmetic<T> arithmetic, Map<Integer, T> values,
        T defaultValue, Type type
    ) {
        this(arithmetic, values == null
            ? -1 : values.size(), defaultValue, type
        );
        assert values != null;
        vector.putAll(values);
    }

    /**
     * creates a new vector based on given one
     *
     * @param vector vector to copy
     */
    public Vector(Vector<T> vector) {
        this(vector.getArithmetic(), vector.getSize(),
            vector.getDefaultValue(), vector.getType());
        vector.getVector().forEach((key, value) -> getVector().put(key, value));
    }

    // endregion

    // region getter, setter

    /**
     * @return stored arithmetic
     */
    public AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

    /**
     * @return vector map using index as key
     */
    protected Map<Integer, T> getVector() {
        return vector;
    }

    /**
     * @return size of vector
     */
    protected int getSize() {
        return size;
    }

    /**
     * @return default value for non-existing values
     */
    protected T getDefaultValue() {
        return defaultValue;
    }

    /**
     * @return type of vector
     */
    public Type getType() {
        return type;
    }

    // endregion

    // region properties

    /**
     * calls {@link #getSize()}
     */
    public int size() {
        return getSize();
    }

    /**
     * calls {@link #euclideanNorm()}
     */
    public T length() {
        return euclideanNorm();
    }

    // endregion

    // region value

    /**
     * @param index where value should be stored
     * @param value to be stored
     * @return old value if existed or {@link #getDefaultValue()}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public T setValue(int index, T value) {
        if (!isValidIndex(index)) throw new IndexOutOfBoundsException();
        if (getArithmetic().isEqual(value, getDefaultValue()))
            return removeValue(index);
        T old = getVector().put(index, value);
        return old == null ? getDefaultValue() : old;
    }

    /**
     * @param index of requested value
     * @return value at index if exists or {@link #getDefaultValue()}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public T getValue(int index) {
        if (!isValidIndex(index)) throw new IndexOutOfBoundsException();
        return getVector().getOrDefault(index, getDefaultValue());
    }

    /**
     * @param index of value to be deleted
     * @return old value if existed or {@link #getDefaultValue()}
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public T removeValue(int index) {
        if (!isValidIndex(index)) throw new IndexOutOfBoundsException();
        T old = getVector().remove(index);
        return old == null ? getDefaultValue() : old;
    }

    // endregion

    // region Matrix

    /**
     * @return matrix equivalent to vector
     */
    public Matrix<T> toMatrix() {
        Matrix<T> matrix =
            new Matrix<>(getArithmetic(), getSize(), 1, getDefaultValue());
        getVector().forEach(matrix::setValue);
        return getType().equals(Type.COLUMN) ? matrix : matrix.transpose();
    }

    // endregion

    // region transpose and invert

    /**
     * @return new vector with opposite type
     */
    public Vector<T> transpose() {
        Vector<T> flipped =
            new Vector<>(getArithmetic(), getSize(), getDefaultValue(),
                getType().equals(Type.COLUMN) ? Type.ROW : Type.COLUMN
            );
        getVector().forEach(flipped::setValue);
        return flipped;
    }

    /**
     * @return new vector with inverted sign
     */
    public Vector<T> invert() {
        return multiply(getArithmetic().negate(getArithmetic().one()));
    }

    // endregion

    // region arithmetic

    /**
     * @param vector vector to add
     * @return new vector with calculated values
     */
    public Vector<T> add(Vector<T> vector) {
        if (vector == null || getSize() != vector.getSize())
            throw new IllegalArgumentException("size has to be equal");
        if (getType() != vector.getType())
            throw new IllegalArgumentException("vectors have to be either both row or both column");
        Vector<T> result =
            new Vector<>(getArithmetic(), getSize(), getDefaultValue());
        IntStream.range(0, getSize())
            .forEach(index -> result.setValue(
                index,
                getArithmetic().sum(getValue(index), vector.getValue(index))
            ));
        result.removeDefaultValues();
        return result;
    }

    /**
     * @param vector vector to subtract
     * @return new vector with calculated values
     */
    public Vector<T> subtract(Vector<T> vector) {
        if (vector == null)
            throw new IllegalArgumentException("vector may not be null");
        return add(vector.invert());
    }

    /**
     * @param scalar to multiply entries with
     * @return new vector with calculated values
     */
    public Vector<T> multiply(T scalar) {
        Vector<T> result = new Vector<>(this);
        for (int i = 0; i < size(); i++) {
            result.setValue(i,
                result.getArithmetic().product(result.getValue(i), scalar)
            );
        }
        return result;
    }

    /**
     * @param vector to multiply with
     * @return product of matrix multiplication
     * @throws IllegalArgumentException if dimensions do not match
     */
    public Matrix<T> multiply(Vector<T> vector) {
        return toMatrix().multiply(vector.toMatrix());
    }

    /**
     * @param vector to compute dot product
     * @return dot product
     * @throws IllegalArgumentException if sizes do not match
     */
    public T dotProduct(Vector<T> vector) {
        Vector<T> v1 =
            new Vector<>(getType() == Type.ROW ? this : transpose());
        Vector<T> v2 =
            new Vector<>(vector.getType() == Type.COLUMN
                ? vector : vector.transpose()
            );
        return v1.toMatrix().multiply(v2.toMatrix()).toParam();
    }

    // endregion

    // region norm and normalize

    /**
     * @return max-Norm of the vector, equal to the entry with highest absolute value
     */
    public T maxNorm() {
        return getVector().values().stream().max(
            (a, b) -> getArithmetic().difference(
                getArithmetic().absolute(a),
                getArithmetic().absolute(b)
            ).intValue()
        ).orElse(getArithmetic().zero());
    }

    /**
     * @return euclidean norm of the vector
     */
    public T euclideanNorm() {
        return pNorm(2);
    }

    /**
     * @param p degree of the norm
     * @return p-norm of the vector
     * @throws IllegalArgumentException if p is less than 1
     */
    public T pNorm(int p) {
        if (p <= 0) throw new IllegalArgumentException("May only calculate positive p-norm");
        Map<Integer, T> powers = new HashMap<>();
        getVector().forEach(
            (key, value) -> powers.put(key, getArithmetic().power(value, p))
        );
        return getArithmetic().root(
            getArithmetic().absolute(
                powers.values().stream().reduce(
                    (a, b) -> getArithmetic().sum(a, b)
                ).orElse(getArithmetic().zero())
            ), p
        );
    }

    /**
     * Result is only guaranteed to be correct for float, double, Fraction
     *
     * @return vector of length one
     * @throws IllegalArgumentException if al vector entries are zero
     */
    public Vector<T> normalize() {
        if (isZero())
            throw new IllegalArgumentException("Zero vector may not be normalized.");
        Vector<T> unit =
            new Vector<>(
                getArithmetic(), getSize(), getDefaultValue(), getType()
            );
        T norm = euclideanNorm();
        stream().forEach(
            entry -> unit.setValue(
                entry.getIndex(),
                getArithmetic().quotient(entry.getValue(), norm)
            )
        );
        return unit;
    }

    // endregion

    // region static

    /**
     * creates a new vector containing all provided values
     *
     * @param arithmetic   arithmetic for calculations
     * @param defaultValue default value of non-existing values
     * @param values       values of vector
     * @param <T>          number class
     * @return new created vector
     * @throws IllegalArgumentException if size is less than {@code 1}
     */
    @SafeVarargs
    public static <T extends Number> Vector<T> ofValues(
        AbstractArithmetic<T> arithmetic, T defaultValue, T... values
    ) {
        Vector<T> vector =
            new Vector<>(arithmetic, values.length, defaultValue);
        for (int i = 0; i < values.length; i++)
            vector.setValue(i, values[i]);
        return vector;
    }

    // endregion

    // region private

    private void removeDefaultValues() {
        getVector().forEach((key, value) -> {
            if (value.equals(getDefaultValue())) removeValue(key);
        });
    }

    // endregion

    // region validation

    /**
     * @param index to be validated
     * @return {@code true} if value is valid
     */
    protected boolean isValidIndex(int index) {
        return 0 <= index && index < size();
    }

    protected boolean isZero() {
        return stream().allMatch(
            (entry) -> getArithmetic().isZero(entry.getValue())
        );
    }

    // endregion

    // region override

    @Override
    public Vector<T> copy() {
        return new Vector<>(this);
    }

    @Override
    public Iterator<Entry> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size();
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
        Vector<?> vector1 = (Vector<?>) o;
        return getSize() == vector1.getSize() &&
            getVector().equals(vector1.getVector()) &&
            getDefaultValue().equals(vector1.getDefaultValue()) &&
            getType() == vector1.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVector(), getSize(), getDefaultValue(), getType());
    }

    @Override
    public String toString() {
        return getSize() + ": " + getVector().entrySet().toString();
    }

    // endregion

    /**
     * Class which holds an entry of a vector with immutable attributes
     */
    public class Entry {
        private final int index;
        private final T value;

        /**
         * @param index index of entry
         * @param value value of entry
         * @throws IndexOutOfBoundsException if index is invalid
         */
        protected Entry(int index, T value) {
            if (!isValidIndex(index))
                throw new IndexOutOfBoundsException("Invalid index: " + index);
            this.index = index;
            this.value = value;
        }

        /**
         * @return index of entry
         */
        public int getIndex() {
            return index;
        }

        /**
         * @return value of entry
         */
        public T getValue() {
            return value;
        }

        /**
         * @return Vector instance of entry
         */
        protected Vector<T> getVector() {
            return Vector.this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vector<?>.Entry entry = (Vector<?>.Entry) o;
            return getIndex() == entry.getIndex() &&
                getValue().equals(entry.getValue());
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
