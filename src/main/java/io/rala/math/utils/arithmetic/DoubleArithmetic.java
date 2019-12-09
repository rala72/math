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
    public double signum(Double a) {
        return Math.signum(a);
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

    @Override
    public Double exponent(Double a, int b) {
        return Math.pow(a, b);
    }

    @Override
    public Double root2(Double a) {
        return Math.sqrt(a);
    }
}
