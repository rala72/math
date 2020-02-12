package io.rala.math.algebra.vector;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.arithmetic.AbstractArithmetic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vector<T extends Number> {

    public enum Type {ROW, COLUMN}

    // region attributes
    private final AbstractArithmetic<T> arithmetic;
    private final Map<Integer, T> vector = new HashMap<>();
    private final int size;
    private final T defaultValue;
    private Type type;

    // endregion

    // region constructors and newInstance

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
     * creates a new vector with given values as entries
     *
     * @param arithmetic   arithmetic for calculations
     * @param values       values of vector entries
     * @param defaultValue default value for non-existing values
     * @param type         type of vector
     * @throws IllegalArgumentException if values is null or its size is zero
     */
    public Vector(AbstractArithmetic<T> arithmetic, List<T> values, T defaultValue, Type type) {
        if (values == null || values.size() == 0) throw new IllegalArgumentException();
        this.arithmetic = arithmetic;
        this.size = values.size();
        this.defaultValue = defaultValue;
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
     * creates a new vector with given values as entries
     *
     * @param arithmetic   arithmetic for calculation
     * @param values       values of vector entries
     * @param defaultValue default value for non-existing values
     * @param type         type of vector
     * @throws IllegalArgumentException if values is null or its size is zero
     */
    public Vector(AbstractArithmetic<T> arithmetic, Map<Integer, T> values, T defaultValue, Type type) {
        if (values == null || values.size() == 0) throw new IllegalArgumentException();
        this.arithmetic = arithmetic;
        this.size = values.size();
        this.defaultValue = defaultValue;
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

    /**
     * creates a new instance of a vector of current type
     *
     * @param size size of new vector
     * @return new vector instance
     * @throws IllegalArgumentException if size is negative or zero
     */
    public Vector<T> newInstance(int size) {
        return new Vector<>(getArithmetic(), size, getDefaultValue());
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
     */
    protected void setType(Type type) {
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
        T old = getVector().put(index, null);
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

    // region validation

    /**
     * @param index to be validated
     * @return {@code true} if value is valid
     */
    protected boolean isValidIndex(int index) {
        return 0 <= index && index <= size();
    }

    // endregion
}
