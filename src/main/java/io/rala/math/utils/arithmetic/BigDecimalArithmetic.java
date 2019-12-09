package io.rala.math.utils.arithmetic;

import io.rala.math.MathX;

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

    /**
     * @return current {@link MathContext}
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

    // region sum, difference, product and quotient

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

    // endregion

    // region exponent and root

    @Override
    public BigDecimal exponent(BigDecimal a, int b) {
        return cleanup(a.pow(b, getMathContext()));
    }

    @Override
    public BigDecimal root(BigDecimal a, int b) {
        return cleanup(MathX.root(a, b));
    }

    // endregion

    private BigDecimal cleanup(BigDecimal a) {
        return new BigDecimal(
            a.stripTrailingZeros().toPlainString(),
            getMathContext()
        );
    }
}
