package io.rala.math.utils.arithmetic;

/**
 * class which handles {@link Float} arithmetic
 */
@SuppressWarnings("unused")
public class FloatArithmetic extends AbstractArithmetic<Float> {
    // region fromInt and signum

    @Override
    public Float fromInt(int a) {
        return (float) a;
    }

    @Override
    public double signum(Float a) {
        return Math.signum(a);
    }

    // endregion

    // region sum, difference, product and quotient

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

    // endregion

    // region exponent and root

    @Override
    public Float exponent(Float a, int b) {
        return (float) Math.pow(a, b);
    }

    @Override
    public Float root2(Float a) {
        return (float) Math.sqrt(a);
    }

    // endregion
}
