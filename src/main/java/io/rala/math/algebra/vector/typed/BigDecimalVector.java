package io.rala.math.algebra.vector.typed;

import io.rala.math.algebra.vector.Vector;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

/**
 * class which holds a vector of {@code size}
 * storing {@link BigDecimal}
 *
 * @since 1.0.0
 */
public class BigDecimalVector extends Vector<BigDecimal> {
    // region constructor

    /**
     * @param size size of vector
     * @see Vector#Vector(AbstractArithmetic, int)
     * @since 1.0.0
     */
    public BigDecimalVector(int size) {
        super(BigDecimalArithmetic.getInstance(), size);
    }

    /**
     * @param size    size of vector
     * @param context context of {@link BigDecimalArithmetic}
     * @see Vector#Vector(AbstractArithmetic, int)
     * @since 1.0.0
     */
    public BigDecimalVector(int size, MathContext context) {
        super(new BigDecimalArithmetic(context), size);
    }

    /**
     * @param size size of vector
     * @param type type of vector
     * @see Vector#Vector(AbstractArithmetic, int, Type)
     * @since 1.0.0
     */
    public BigDecimalVector(int size, Type type) {
        super(BigDecimalArithmetic.getInstance(), size, type);
    }

    /**
     * @param size    size of vector
     * @param type    type of vector
     * @param context context of {@link BigDecimalArithmetic}
     * @see Vector#Vector(AbstractArithmetic, int, Type)
     * @since 1.0.0
     */
    public BigDecimalVector(int size, Type type, MathContext context) {
        super(new BigDecimalArithmetic(context), size, type);
    }

    /**
     * creates a new vector based on given one
     *
     * @param vector vector to copy
     * @since 1.0.0
     */
    public BigDecimalVector(Vector<BigDecimal> vector) {
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
    public static BigDecimalVector ofValues(BigDecimal... values) {
        return new BigDecimalVector(Vector.ofValues(BigDecimalArithmetic.getInstance(), values));
    }

    /**
     * creates a new vector containing values from provided list
     *
     * @param values values of vector
     * @return new created vector
     * @throws IllegalArgumentException if size is less than {@code 1}
     * @since 1.0.0
     */
    public static BigDecimalVector ofList(List<BigDecimal> values) {
        return new BigDecimalVector(Vector.ofList(BigDecimalArithmetic.getInstance(), values));
    }

    // endregion
}
