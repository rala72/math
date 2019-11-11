package io.rala.math.utils;

import io.rala.math.testUtils.UnitsArgumentsStreamFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class UnitsTest {
    @ParameterizedTest
    @MethodSource("getGradToRadArguments")
    void gradToRad(double grad, double expected) {
        Assertions.assertEquals(expected, Units.gradToRad(grad));
    }

    @ParameterizedTest
    @MethodSource("getRadToGradArguments")
    void radToGrad(double rad, double expected) {
        Assertions.assertEquals(expected, Units.radToGrad(rad));
    }

    // region argument streams

    private static Stream<Arguments> getGradToRadArguments() {
        return UnitsArgumentsStreamFactory.gradToRad();
    }

    private static Stream<Arguments> getRadToGradArguments() {
        return UnitsArgumentsStreamFactory.radToGrad();
    }

    // endregion
}
