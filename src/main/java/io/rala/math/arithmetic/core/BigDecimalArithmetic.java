package io.rala.math.arithmetic.core;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.exception.NotSupportedException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

/**
 * class which handles {@link BigDecimal} arithmetic
 *
 * @since 1.0.0
 */
public class BigDecimalArithmetic extends AbstractArithmetic<BigDecimal> {
    // region singleton

    private static BigDecimalArithmetic instance;

    /**
     * @return default instance
     * @since 1.0.0
     */
    public static BigDecimalArithmetic getInstance() {
        if (instance == null) instance = new BigDecimalArithmetic();
        return instance;
    }

    // endregion

    private final MathContext mathContext;

    /**
     * calls {@link #BigDecimalArithmetic(MathContext)}
     * with a new {@link MathContext} which precision is {@code 10}
     *
     * @see MathContext#MathContext(int)
     * @since 1.0.0
     */
    public BigDecimalArithmetic() {
        this(new MathContext(10));
    }

    /**
     * creates a new instance
     *
     * @param mathContext mathContext of arithmetic
     * @since 1.0.0
     */
    public BigDecimalArithmetic(MathContext mathContext) {
        this.mathContext = mathContext;
    }

    /**
     * @return stored {@link MathContext}
     * @since 1.0.0
     */
    public MathContext getMathContext() {
        return mathContext;
    }

    // region fromInt, fromDouble and signum

    @Override
    public BigDecimal fromInt(int a) {
        return BigDecimal.valueOf(a);
    }

    @Override
    public BigDecimal fromDouble(double a) {
        return cleanup(BigDecimal.valueOf(a));
    }

    @Override
    public double signum(BigDecimal a) {
        return a.signum();
    }

    // endregion

    // region absolute, negate and compare

    @Override
    public BigDecimal absolute(BigDecimal a) {
        return a.abs();
    }

    @Override
    public BigDecimal negate(BigDecimal a) {
        return a.negate();
    }

    @Override
    public int compare(BigDecimal a, BigDecimal b) {
        return a.compareTo(b);
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Override
    public BigDecimal sum(BigDecimal a, BigDecimal b) {
        return cleanup(a.add(b, getMathContext()));
    }

    @Override
    public BigDecimal difference(BigDecimal a, BigDecimal b) {
        return cleanup(a.subtract(b, getMathContext()));
    }

    @Override
    public BigDecimal product(BigDecimal a, BigDecimal b) {
        return cleanup(a.multiply(b, getMathContext()));
    }

    @Override
    public BigDecimal quotient(BigDecimal a, BigDecimal b) {
        return cleanup(a.divide(b, getMathContext()));
    }

    @Override
    public BigDecimal modulo(BigDecimal a, BigDecimal b) {
        return a.remainder(b, getMathContext());
    }

    // endregion

    // region power and root

    @Override
    public BigDecimal power(BigDecimal a, int b) {
        return cleanup(a.pow(b, getMathContext()));
    }

    @Override
    public BigDecimal root(BigDecimal a, int b) {
        return cleanup(MathX.root(a, b, getMathContext()));
    }

    // endregion

    // region gcd

    @Override
    public BigDecimal gcd(BigDecimal a, BigDecimal b) {
        throw new NotSupportedException();
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BigDecimalArithmetic)) return false;
        if (!super.equals(o)) return false;
        BigDecimalArithmetic that = (BigDecimalArithmetic) o;
        return Objects.equals(getMathContext(), that.getMathContext());
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(getMathContext());
    }

    // endregion

    private BigDecimal cleanup(BigDecimal a) {
        return new BigDecimal(
            a.stripTrailingZeros().toPlainString(),
            getMathContext()
        );
    }
}
