package io.rala.math.algebra.vector;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.utils.Copyable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vector<T extends Number> implements Copyable<Vector<T>> {

    public enum Type {ROW, COLUMN}

    // region attributes
    private final AbstractArithmetic<T> arithmetic;
    private final Map<Integer, T> vector = new HashMap<>();
    private final int size;
    private final T defaultValue;
    private Type type;

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
    public Vector(AbstractArithmetic<T> arithmetic, int size, T defaultValue, Type type) {
        if (size <= 0) throw new IllegalArgumentException("Size has to be grater than 0");
        this.arithmetic = arithmetic;
        this.size = size;
        this.defaultValue = defaultValue;
        this.setType(type);
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
    public Vector(AbstractArithmetic<T> arithmetic, List<T> values, T defaultValue) {
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
    public Vector(AbstractArithmetic<T> arithmetic, List<T> values, T defaultValue, Type type) {
        this(arithmetic, values == null ? -1 : values.size(), defaultValue, type);
        if (getSize() == 0) throw new IllegalArgumentException();
        for (int i = 0; i < size; i++) {
            vector.put(i, values.get(i));
        }
        this.setType(type);
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
    public Vector(AbstractArithmetic<T> arithmetic, Map<Integer, T> values, T defaultValue) {
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
    public Vector(AbstractArithmetic<T> arithmetic, Map<Integer, T> values, T defaultValue, Type type) {
        this(arithmetic, values == null ? -1 : values.size(), defaultValue, type);
        if (getSize() == 0) throw new IllegalArgumentException();
        values.forEach(vector::put);
        this.setType(type);
    }

    /**
     * creates a new vector based on given one
     *
     * @param vector vector to copy
     */
    public Vector(Vector<T> vector) {
        this(vector.getArithmetic(), vector.getSize(), vector.getDefaultValue(), vector.getType());
        vector.getVector().forEach((key, value) -> getVector().put(key, value));
    }

    // endregion

    // region getter, setter and size

    /**
     * @return stored arithmetic
     */
    protected AbstractArithmetic<T> getArithmetic() {
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
     * @return size of vector
     */
    public int size() {
        return getSize();
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
    protected Type getType() {
        return type;
    }

    /**
     * set type of vector
     *
     * @param type type of vector
     * @throws IllegalArgumentException if type is null
     */
    protected void setType(Type type) {
        if (type == null) throw new IllegalArgumentException("Type of vector may not be null");
        this.type = type;
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
        if (value == null && getDefaultValue() == null
            || value != null && value.equals(getDefaultValue()))
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
        Matrix<T> matrix = new Matrix<>(getArithmetic(), getSize(), 1, getDefaultValue());
        getVector().forEach(matrix::setValue);
        return getType().equals(Type.COLUMN) ? matrix : matrix.transpose();
    }

    // endregion

    // region transpose and invert

    /**
     * @return new vector with opposite type
     */
    public Vector<T> transpose() {
        Vector<T> temp = new Vector<>(this);
        if (temp.getType().equals(Type.COLUMN)) {
            temp.setType(Type.ROW);
        } else {
            temp.setType(Type.COLUMN);
        }
        return temp;
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
        Vector<T> result = new Vector<>(getArithmetic(), getSize(), getDefaultValue());
        vector.getVector().forEach((key, value) ->
            result.setValue(key, getArithmetic().sum(getValue(key), vector.getValue(key))));
        result.removeDefaultValues();
        return result;
    }

    /**
     * @param scalar to multiply entries with
     * @return new vector with calculated values
     */
    public Vector<T> multiply(T scalar) {
        Vector<T> result = new Vector<>(this);
        for (int i = 0; i < size(); i++) {
            result.setValue(i, result.getArithmetic().product(result.getValue(i), scalar));
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
        Vector<T> v1 = new Vector<>(getType() == Type.ROW ? this : transpose());
        Vector<T> v2 = new Vector<>(vector.getType() == Type.COLUMN ? vector : vector.transpose());
        return v1.toMatrix().multiply(v2.toMatrix()).toParam();
    }

    // endregion

    // region norm

    /**
     * @return euclidean norm of the vector
     */
    public T euclideanNorm() {
        return pNorm(2);
    }

    /**
     * @return max-Norm of the vector, equal to the entry with highest absolute value
     */
    public T maxNorm() {
        return getVector().values().stream().max(
            (a, b) -> getArithmetic().difference(getArithmetic().absolute(a), getArithmetic().absolute(b)).intValue())
            .orElse(getArithmetic().zero());
    }

    /**
     * @param p degree of the norm
     * @return p-norm of the vector
     * @throws IllegalArgumentException if p is less than 1
     */
    public T pNorm(int p) {
        if (p <= 0) throw new IllegalArgumentException("May only calculate positive p-norm");
        Map<Integer, T> squares = new HashMap<>();
        getVector().forEach((key, value) -> squares.put(key, getArithmetic().power(value, p)));
        return getArithmetic().root(squares.values().stream().reduce(
            (a, b) -> getArithmetic().sum(getArithmetic().absolute(a), getArithmetic().absolute(b)))
            .orElse(getArithmetic().zero()), p);
    }

    // endregion

    // region copy

    @Override
    public Vector<T> copy() {
        return new Vector<>(this);
    }

    /**
     * remove
     */
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
        return 0 <= index && index <= size();
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector<?> vector1 = (Vector<?>) o;

        if (getSize() != vector1.getSize()) return false;
        if (!getVector().equals(vector1.getVector())) return false;
        return getType() == vector1.getType();
    }

    @Override
    public int hashCode() {
        int result = getVector().hashCode();
        result = 31 * result + getSize();
        result = 31 * result + getType().hashCode();
        return result;
    }

    // endregion
}
