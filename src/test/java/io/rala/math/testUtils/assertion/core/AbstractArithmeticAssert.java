package io.rala.math.testUtils.assertion.core;

import io.rala.math.arithmetic.AbstractArithmetic;
import org.assertj.core.api.ObjectAssert;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link AbstractMathAssert} for mathematical classes with {@link AbstractArithmetic}
 *
 * @param <T>      number class
 * @param <SELF>   current {@link AbstractMathAssert} class
 * @param <ACTUAL> mathematical class to test
 */
@SuppressWarnings("UnusedReturnValue")
public abstract class AbstractArithmeticAssert
    <T extends Number, SELF extends AbstractArithmeticAssert<T, SELF, ACTUAL>, ACTUAL>
    extends AbstractMathAssert<T, SELF, ACTUAL> {

    protected AbstractArithmeticAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }

    /**
     * @see ObjectAssert#isNotNull()
     */
    public SELF hasArithmetic() {
        isNotNull();
        assertThat(getArithmetic()).isNotNull();
        return myself;
    }

    /**
     * @return {@link AbstractArithmetic} of {@code actual}
     */
    protected abstract AbstractArithmetic<T> getArithmetic();
}
