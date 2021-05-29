package io.rala.math.arithmetic.result;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.arithmetic.core.BigIntegerArithmetic;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/**
 * class which defines required arithmetic for calculations
 * which calculates {@link BigInteger} to {@link BigDecimal}
 *
 * @since 1.0.0
 */
public class BigIntegerBigDecimalResultArithmetic extends AbstractResultArithmetic<BigInteger, BigDecimal> {
    // region singleton

    private static BigIntegerBigDecimalResultArithmetic instance;

    /**
     * @return default instance
     * @since 1.0.0
     */
    @NotNull
    public static BigIntegerBigDecimalResultArithmetic getInstance() {
        if (instance == null) instance = new BigIntegerBigDecimalResultArithmetic();
        return instance;
    }

    // endregion

    /**
     * creates a new {@link AbstractResultArithmetic} with
     * {@link BigIntegerArithmetic} and {@link BigDecimalArithmetic}
     *
     * @see BigDecimalArithmetic#BigDecimalArithmetic()
     * @since 1.0.0
     */
    public BigIntegerBigDecimalResultArithmetic() {
        super(BigIntegerArithmetic.getInstance(), BigDecimalArithmetic.getInstance());
    }

    /**
     * creates a new {@link AbstractResultArithmetic} with
     * {@link BigIntegerArithmetic} and {@link BigDecimalArithmetic}
     *
     * @param context context of {@link BigDecimalArithmetic}
     * @see BigDecimalArithmetic#BigDecimalArithmetic(MathContext)
     * @since 1.0.0
     */
    public BigIntegerBigDecimalResultArithmetic(@NotNull MathContext context) {
        super(BigIntegerArithmetic.getInstance(), new BigDecimalArithmetic(context));
    }

    @Override
    @NotNull
    public BigDecimal fromT(@NotNull BigInteger a) {
        return new BigDecimal(a);
    }
}
