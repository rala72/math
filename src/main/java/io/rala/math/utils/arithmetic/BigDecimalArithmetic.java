package io.rala.math.utils.arithmetic;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * class which handles {@link BigDecimal} arithmetic
 */
@SuppressWarnings("unused")
public class BigDecimalArithmetic extends AbstractArithmetic<BigDecimal> {
    private final MathContext mathContext;

    /**
     * calls {@link #BigDecimalArithmetic(MathContext)}
     * with a new {@link MathContext} which precision is <code>10</code>
     *
     * @see MathContext#MathContext(int)
     */
    public BigDecimalArithmetic() {
        this(new MathContext(10));
    }

    /**
     * creates a new instance
     *
     * @param mathContext mathContext of arithmetic
     */
    public BigDecimalArithmetic(MathContext mathContext) {
        this.mathContext = mathContext;
    }

    public MathContext getMathContext() {
        return mathContext;
    }

    @Override
    public BigDecimal fromInt(int a) {
        return BigDecimal.valueOf(a).stripTrailingZeros();
    }

    @Override
    public double signum(BigDecimal a) {
        return a.signum();
    }

    @Override
    public BigDecimal sum(BigDecimal a, BigDecimal b) {
        return a.add(b).stripTrailingZeros();
    }

    @Override
    public BigDecimal difference(BigDecimal a, BigDecimal b) {
        return a.subtract(b).stripTrailingZeros();
    }

    @Override
    public BigDecimal product(BigDecimal a, BigDecimal b) {
        return a.multiply(b).stripTrailingZeros();
    }

    @Override
    public BigDecimal quotient(BigDecimal a, BigDecimal b) {
        return a.divide(b, getMathContext()).stripTrailingZeros();
    }

    @Override
    public BigDecimal exponent(BigDecimal a, int b) {
        return a.pow(b);
    }

    @Override
    public BigDecimal root2(BigDecimal a) {
        return a.sqrt(getMathContext());
    }
}
