package io.rala.math.algebra.vector;

import io.rala.math.arithmetic.AbstractArithmetic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vector<T extends Number> {

    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private final Map<Integer, T> vector = new HashMap<>();
    private final int size;
    private final T defaultValue;
    private Type type;

    // endregion

    // region constructors and newInstance
    // Todo: Constructor using HashMap as values

    /**
     * calls {@link #Vector(AbstractArithmetic, int, Number, Type)}
     * Type.COLUMN as default type
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
     * with Type.COLUMN as default type
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

    // region getter and setter

    /**
     * @return stored arithmetic
     */
    public AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

    /**
     * @return vector map using index as key
     */
    public Map<Integer, T> getVector() {
        return vector;
    }

    /**
     * @return size of vector
     */
    public int getSize() {
        return size;
    }

    /**
     * @return default value for non-existing values
     */
    public T getDefaultValue() {
        return defaultValue;
    }

    /**
     * @return type of vector
     */
    public Type getType() {
        return type;
    }

    /**
     * set type of vector
     *
     * @param type type of vector
     */
    public void setType(Type type) {
        this.type = type;
    }

    // endregion

    private enum Type {ROW, COLUMN}
}
