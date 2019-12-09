package io.rala.math.utils.arithmetic;

import io.rala.math.MathX;

/**
 * class which handles {@link Integer} arithmetic
 */
@SuppressWarnings("unused")
public class IntegerArithmetic extends AbstractArithmetic<Integer> {
    // region fromInt, fromDouble and signum

    @Override
    public Integer fromInt(int a) {
        return a;
    }

    @Override
    public Integer fromDouble(double a) {
        return (int) a;
    }

    @Override
    public double signum(Integer a) {
        return Math.signum(a);
    }

    // endregion

    // region sum, difference, product and quotient

    @Override
    public Integer sum(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public Integer difference(Integer a, Integer b) {
        return a - b;
    }

    @Override
    public Integer product(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer quotient(Integer a, Integer b) {
        return a / b;
    }

    // endregion

    // region exponent and root

    @Override
    public Integer exponent(Integer a, int b) {
        return Math.toIntExact((long) Math.pow(a, b));
    }

    @Override
    public Integer root(Integer a, int b) {
        return (int) MathX.root(a, b);
    }

    // endregion
}
