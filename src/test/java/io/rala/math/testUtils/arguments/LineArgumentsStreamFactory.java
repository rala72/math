package io.rala.math.testUtils.arguments;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class LineArgumentsStreamFactory {
    private LineArgumentsStreamFactory() {
    }

    public static Stream<Arguments> calculateX() {
        return Stream.of(
            createCalculateXArguments(0, 0, -1, 2, 1),
            createCalculateXArguments(0, 3, -2, 3, 1),
            createCalculateXArguments(2, 0, -2, 3, 1),
            createCalculateXArguments(2, 3, -10, 11, 2)
        ).flatMap(argumentsStream -> argumentsStream);
    }

    public static Stream<Arguments> calculateY() {
        return Stream.of(
            createCalculateYArguments(0, 0, -1, 2, 1),
            createCalculateYArguments(0, 3, -2, 3, 1),
            createCalculateYArguments(2, 0, -2, 3, 1),
            createCalculateYArguments(2, 3, -10, 11, 2)
        ).flatMap(argumentsStream -> argumentsStream);
    }

    private static Stream<Arguments> createCalculateXArguments(
        double m, double b, double minY, double maxY, double steps
    ) {
        return createCalculateArguments(m, b, minY, maxY, steps, y -> (y - b) / m);
    }

    private static Stream<Arguments> createCalculateYArguments(
        double m, double b, double minX, double maxX, double steps
    ) {
        return createCalculateArguments(m, b, minX, maxX, steps, x -> x * m + b);
    }

    private static Stream<Arguments> createCalculateArguments(
        double m, double b, double minValue, double maxValue, double steps, Function<Double, Double> function
    ) {
        List<Arguments> list = new ArrayList<>();
        for (double y = minValue; y < maxValue; y += steps)
            list.add(Arguments.of(m, b, y, function.apply(y)));
        return list.stream();
    }
}
