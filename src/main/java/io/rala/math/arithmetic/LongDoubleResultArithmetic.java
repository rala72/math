package io.rala.math.arithmetic;

/**
 * class which defines required arithmetic for calculations
 * which calculates {@link Long} to {@link Double}
 */
@SuppressWarnings("unused")
public class LongDoubleResultArithmetic extends AbstractResultArithmetic<Long, Double> {
    /**
     * creates a new {@link AbstractResultArithmetic} with
     * {@link LongArithmetic} and {@link DoubleArithmetic}
     */
    public LongDoubleResultArithmetic() {
        super(new LongArithmetic(), new DoubleArithmetic());
    }

    @Override
    public Double fromT(Long a) {
        return (double) a;
    }
}
