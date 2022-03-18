package io.rala.math.testUtils.assertion.core;

import io.rala.math.utils.Validatable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link AbstractArithmeticAssert} for mathematical {@link Validatable} classes
 *
 * @param <T>      number class
 * @param <SELF>   current {@link AbstractArithmeticAssert} class
 * @param <ACTUAL> mathematical class to test
 */
public abstract class AbstractValidatableAssert
    <T extends Number,
        SELF extends AbstractValidatableAssert<T, SELF, ACTUAL>,
        ACTUAL extends Validatable>
    extends AbstractArithmeticAssert<T, SELF, ACTUAL> {

    protected AbstractValidatableAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }

    /**
     * @see Validatable#isValid()
     */
    public SELF isValid() {
        isNotNull();
        assertThat(actual.isValid()).isTrue();
        return myself;
    }

    /**
     * @see Validatable#isValid()
     */
    public SELF isInvalid() {
        isNotNull();
        assertThat(actual.isValid()).isFalse();
        return myself;
    }
}
