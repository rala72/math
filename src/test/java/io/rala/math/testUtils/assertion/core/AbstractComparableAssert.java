package io.rala.math.testUtils.assertion.core;

import io.rala.math.utils.Validatable;
import org.assertj.core.api.ComparableAssert;
import org.assertj.core.api.GenericComparableAssert;

/**
 * {@link AbstractValidatableAssert} for mathematical {@link Comparable} classes
 *
 * @param <T>      number class
 * @param <SELF>   current {@link AbstractValidatableAssert} class
 * @param <ACTUAL> mathematical class to test
 */
public abstract class AbstractComparableAssert
    <T extends Number,
        SELF extends AbstractComparableAssert<T, SELF, ACTUAL>,
        ACTUAL extends Validatable & Comparable<ACTUAL>>
    extends AbstractValidatableAssert<T, SELF, ACTUAL> implements ComparableAssert<SELF, ACTUAL> {

    protected AbstractComparableAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }

    // region Comparable

    @Override
    public SELF isEqualByComparingTo(ACTUAL other) {
        getComparableAssert().isEqualByComparingTo(other);
        return myself;
    }

    @Override
    public SELF isNotEqualByComparingTo(ACTUAL other) {
        getComparableAssert().isNotEqualByComparingTo(other);
        return myself;
    }

    @Override
    public SELF isLessThan(ACTUAL other) {
        getComparableAssert().isLessThan(other);
        return myself;
    }

    @Override
    public SELF isLessThanOrEqualTo(ACTUAL other) {
        getComparableAssert().isLessThanOrEqualTo(other);
        return myself;
    }

    @Override
    public SELF isGreaterThan(ACTUAL other) {
        getComparableAssert().isGreaterThan(other);
        return myself;
    }

    @Override
    public SELF isGreaterThanOrEqualTo(ACTUAL other) {
        getComparableAssert().isGreaterThanOrEqualTo(other);
        return myself;
    }

    @Override
    public SELF isBetween(ACTUAL startInclusive, ACTUAL endInclusive) {
        getComparableAssert().isBetween(startInclusive, endInclusive);
        return myself;
    }

    @Override
    public SELF isStrictlyBetween(ACTUAL startExclusive, ACTUAL endExclusive) {
        getComparableAssert().isStrictlyBetween(startExclusive, endExclusive);
        return myself;
    }

    protected GenericComparableAssert<ACTUAL> getComparableAssert() {
        return new GenericComparableAssert<>(actual);
    }

    // endregion
}
