package io.rala.math.testUtils.arguments;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class UnitsArgumentsStreamFactory {
    private UnitsArgumentsStreamFactory() {
    }

    public static Stream<Arguments> gradToRad() {
        return Stream.of(
            Arguments.of(-90, -Math.PI / 2),
            Arguments.of(-45, -Math.PI / 4),
            Arguments.of(0, 0),
            Arguments.of(1, Math.PI / 180),
            Arguments.of(45, Math.PI / 4),
            Arguments.of(90, Math.PI / 2),
            Arguments.of(135, Math.PI * 3 / 4),
            Arguments.of(180, Math.PI),
            Arguments.of(270, Math.PI * 3 / 2),
            Arguments.of(360, Math.PI * 2)
        );
    }

    public static Stream<Arguments> radToGrad() {
        return Stream.of(
            Arguments.of(-Math.PI / 2, -90),
            Arguments.of(-Math.PI / 4, -45),
            Arguments.of(0, 0),
            Arguments.of(Math.PI / 180, 1),
            Arguments.of(Math.PI / 4, 45),
            Arguments.of(Math.PI / 2, 90),
            Arguments.of(Math.PI * 3 / 4, 135),
            Arguments.of(Math.PI, 180),
            Arguments.of(Math.PI * 3 / 2, 270),
            Arguments.of(Math.PI * 2, 360)
        );
    }
}
