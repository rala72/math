package io.rala.math.arithmetic.result;

import io.rala.math.arithmetic.AbstractResultArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.arithmetic.core.LongArithmetic;

/**
 * class which defines required arithmetic for calculations
 * which calculates {@link Long} to {@link Double}
 */
public class LongDoubleResultArithmetic extends AbstractResultArithmetic<Long, Double> {
    // region singleton

    private static LongDoubleResultArithmetic instance;

    /**
     * @return default instance
     */
    public static LongDoubleResultArithmetic getInstance() {
        if (instance == null) instance = new LongDoubleResultArithmetic();
        return instance;
    }

    // endregion

    /**
     * creates a new {@link AbstractResultArithmetic} with
     * {@link LongArithmetic} and {@link DoubleArithmetic}
     */
    public LongDoubleResultArithmetic() {
        super(LongArithmetic.getInstance(), DoubleArithmetic.getInstance());
    }

    @Override
    public Double fromT(Long a) {
        return (double) a;
    }
}
