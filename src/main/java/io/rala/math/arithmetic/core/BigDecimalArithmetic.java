package io.rala.math.arithmetic.core;

import io.rala.math.MathX;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.exception.NotSupportedException;
import org.jetbrains.annotations.NotNull;

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
    @NotNull
    public static BigDecimalArithmetic getInstance() {
        if (instance == null) instance = new BigDecimalArithmetic();
        return instance;
    }

    // endregion

    private final MathContext mathContext;

    /**
     * calls {@link #BigDecimalArithmetic(MathContext)}
     * with {@link MathContext#DECIMAL64}
     *
     * @see MathContext#MathContext(int)
     * @since 1.0.0
     */
    public BigDecimalArithmetic() {
        this(MathContext.DECIMAL64);
    }

    /**
     * creates a new instance
     *
     * @param mathContext mathContext of arithmetic
     * @since 1.0.0
     */
    public BigDecimalArithmetic(@NotNull MathContext mathContext) {
        this.mathContext = mathContext;
    }

    /**
     * @return stored {@link MathContext}
     * @since 1.0.0
     */
    @NotNull
    public MathContext getMathContext() {
        return mathContext;
    }

    // region fromInt, fromDouble and signum

    @Override
    @NotNull
    public BigDecimal fromInt(int a) {
        return BigDecimal.valueOf(a);
    }

    @Override
    @NotNull
    public BigDecimal fromDouble(double a) {
        return cleanup(BigDecimal.valueOf(a));
    }

    @Override
    public double signum(@NotNull BigDecimal a) {
        return a.signum();
    }

    // endregion

    // region absolute, negate and compare

    @Override
    @NotNull
    public BigDecimal absolute(@NotNull BigDecimal a) {
        return a.abs();
    }

    @Override
    @NotNull
    public BigDecimal negate(@NotNull BigDecimal a) {
        return a.negate();
    }

    @Override
    public int compare(@NotNull BigDecimal a, @NotNull BigDecimal b) {
        return a.compareTo(b);
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Override
    @NotNull
    public BigDecimal sum(@NotNull BigDecimal a, @NotNull BigDecimal b) {
        return cleanup(a.add(b, getMathContext()));
    }

    @Override
    @NotNull
    public BigDecimal difference(@NotNull BigDecimal a, @NotNull BigDecimal b) {
        return cleanup(a.subtract(b, getMathContext()));
    }

    @Override
    @NotNull
    public BigDecimal product(@NotNull BigDecimal a, @NotNull BigDecimal b) {
        return cleanup(a.multiply(b, getMathContext()));
    }

    @Override
    @NotNull
    public BigDecimal quotient(@NotNull BigDecimal a, @NotNull BigDecimal b) {
        return cleanup(a.divide(b, getMathContext()));
    }

    @Override
    @NotNull
    public BigDecimal modulo(@NotNull BigDecimal a, @NotNull BigDecimal b) {
        return a.remainder(b, getMathContext());
    }

    // endregion

    // region power and root

    @Override
    @NotNull
    public BigDecimal power(@NotNull BigDecimal a, int b) {
        return cleanup(a.pow(b, getMathContext()));
    }

    @Override
    @NotNull
    public BigDecimal root(@NotNull BigDecimal a, int b) {
        return cleanup(MathX.root(a, b, getMathContext()));
    }

    // endregion

    // region gcd

    @Override
    @NotNull
    public BigDecimal gcd(@NotNull BigDecimal a, @NotNull BigDecimal b) {
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

    @NotNull
    private BigDecimal cleanup(@NotNull BigDecimal a) {
        return new BigDecimal(
            a.stripTrailingZeros().toPlainString(),
            getMathContext()
        );
    }
}
