package io.rala.math.algebra.vector.typed;

import io.rala.math.algebra.vector.Vector;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * class which holds a vector of {@code size}
 * storing {@link Double}
 *
 * @since 1.0.0
 */
public class DoubleVector extends Vector<Double> {
    // region constructor

    /**
     * @param size size of vector
     * @see Vector#Vector(AbstractArithmetic, int)
     * @since 1.0.0
     */
    public DoubleVector(int size) {
        super(DoubleArithmetic.getInstance(), size);
    }

    /**
     * @param size size of vector
     * @param type type of vector
     * @see Vector#Vector(AbstractArithmetic, int, Type)
     * @since 1.0.0
     */
    public DoubleVector(int size, @Nullable Type type) {
        super(DoubleArithmetic.getInstance(), size, type);
    }

    /**
     * creates a new vector based on given one
     *
     * @param vector vector to copy
     * @since 1.0.0
     */
    public DoubleVector(@NotNull Vector<Double> vector) {
        super(vector);
    }

    // endregion

    // region static

    /**
     * creates a new vector containing all provided values
     *
     * @param values values of vector
     * @return new created vector
     * @throws IllegalArgumentException if size is less than {@code 1}
     * @since 1.0.0
     */
    @NotNull
    public static DoubleVector ofValues(double... values) {
        Double[] boxed = Arrays.stream(values).boxed().toArray(Double[]::new);
        return new DoubleVector(Vector.ofValues(DoubleArithmetic.getInstance(), boxed));
    }

    /**
     * creates a new vector containing values from provided list
     *
     * @param values values of vector
     * @return new created vector
     * @throws IllegalArgumentException if size is less than {@code 1}
     * @since 1.0.0
     */
    @NotNull
    public static DoubleVector ofList(@NotNull List<Double> values) {
        return new DoubleVector(Vector.ofList(DoubleArithmetic.getInstance(), values));
    }

    // endregion
}
