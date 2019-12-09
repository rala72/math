package io.rala.math.utils.arithmetic;

/**
 * class which handles {@link Float} arithmetic
 */
@SuppressWarnings("unused")
public class FloatArithmetic extends AbstractArithmetic<Float> {
    @Override
    public Float fromInt(int a) {
        return (float) a;
    }

    @Override
    public Float sum(Float a, Float b) {
        return a + b;
    }

    @Override
    public Float difference(Float a, Float b) {
        return a - b;
    }

    @Override
    public Float product(Float a, Float b) {
        return a * b;
    }

    @Override
    public Float quotient(Float a, Float b) {
        return a / b;
    }
}
