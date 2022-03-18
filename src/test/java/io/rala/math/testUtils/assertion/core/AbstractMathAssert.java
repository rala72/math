package io.rala.math.testUtils.assertion.core;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractDoubleAssert;
import org.assertj.core.api.AbstractObjectArrayAssert;
import org.assertj.core.api.Assertions;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link AbstractAssert} for mathematical classes
 *
 * @param <T>      number class
 * @param <SELF>   current {@link AbstractAssert} class
 * @param <ACTUAL> mathematical class to test
 */
public abstract class AbstractMathAssert
    <T extends Number, SELF extends AbstractMathAssert<T, SELF, ACTUAL>, ACTUAL>
    extends AbstractAssert<SELF, ACTUAL> {

    protected AbstractMathAssert(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }

    /**
     * @see AbstractObjectArrayAssert#allMatch(Predicate)
     */
    protected SELF assertThatNoArgumentIsNull(Object... args) {
        assertThat(args)
            .as("any argument is null")
            .allMatch(Objects::nonNull);
        return myself;
    }

    /**
     * @see Assertions#assertThat(double)
     */
    protected SELF assertNumberAsDouble(T number, Consumer<AbstractDoubleAssert<?>> consumer) {
        assertThat(number).isNotNull();
        return withConcatenatedMessage(assertThat(number.doubleValue()), consumer::accept);
    }

    /**
     * @see #failWithConcatenatedMessage(String)
     */
    protected <A extends AbstractAssert<?, ?>> SELF withConcatenatedMessage(
        A tAbstractAssert, Consumer<A> consumer
    ) {
        try {
            consumer.accept(tAbstractAssert);
        } catch (AssertionError error) {
            failWithConcatenatedMessage(error.getLocalizedMessage());
        }
        return myself;
    }

    /**
     * @see #failWithMessage(String, Object...)
     */
    protected void failWithConcatenatedMessage(String message) {
        String currentDescriptionText = descriptionText();
        String errorMessage = message;
        if (!currentDescriptionText.isBlank()) {
            describedAs("");
            errorMessage = message
                .replaceFirst("\\[", "\\[" + currentDescriptionText + ": ");
        }
        failWithMessage(errorMessage);
    }
}
