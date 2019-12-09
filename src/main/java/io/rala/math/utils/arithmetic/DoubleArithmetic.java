package io.rala.math.utils.arithmetic;

/**
 * class which handles {@link Double} arithmetic
 */
@SuppressWarnings("unused")
public class DoubleArithmetic extends AbstractArithmetic<Double> {
    @Override
    public Double fromInt(int a) {
        return (double) a;
    }

    @Override
    public Double sum(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double difference(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double product(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double quotient(Double a, Double b) {
        return a / b;
    }
}
