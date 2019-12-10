package io.rala.math.arithmetic.result;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.arithmetic.core.BigIntegerArithmetic;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/**
 * class which defines required arithmetic for calculations
 * which calculates {@link BigInteger} to {@link BigDecimal}
 */
@SuppressWarnings("unused")
public class BigIntegerBigDecimalResultArithmetic extends AbstractResultArithmetic<BigInteger, BigDecimal> {
    /**
     * creates a new {@link AbstractResultArithmetic} with
     * {@link BigIntegerArithmetic} and {@link BigDecimalArithmetic}
     *
     * @see BigDecimalArithmetic#BigDecimalArithmetic()
     */
    public BigIntegerBigDecimalResultArithmetic() {
        super(new BigIntegerArithmetic(), new BigDecimalArithmetic());
    }

    /**
     * creates a new {@link AbstractResultArithmetic} with
     * {@link BigIntegerArithmetic} and {@link BigDecimalArithmetic}
     *
     * @param context context of {@link BigDecimalArithmetic}
     * @see BigDecimalArithmetic#BigDecimalArithmetic(MathContext)
     */
    public BigIntegerBigDecimalResultArithmetic(MathContext context) {
        super(new BigIntegerArithmetic(), new BigDecimalArithmetic(context));
    }

    @Override
    public BigDecimal fromT(BigInteger a) {
        return new BigDecimal(a);
    }
}
