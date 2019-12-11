package io.rala.math.testUtils.arguments;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EnumerativeCombinatoricsArgumentsStreamFactory {
    private EnumerativeCombinatoricsArgumentsStreamFactory() {
    }

    public static Stream<Arguments> permutationsWithoutRepetition() {
        return MathXArgumentsStreamFactory.factorial();
    }

    public static Stream<Arguments> permutationsWithRepetition() {
        return Stream.of(
            Arguments.of(1, 1, IntStream.of().toArray()),
            Arguments.of(2, 2, IntStream.of().toArray()),
            Arguments.of(6, 3, IntStream.of(1).toArray()),
            Arguments.of(6, 3, IntStream.of(1, 1).toArray()),
            Arguments.of(3, 3, IntStream.of(1, 2).toArray()),
            Arguments.of(3, 3, IntStream.of(2).toArray()),
            Arguments.of(360_360, 14, IntStream.of(4, 7, 1, 2).toArray()),
            Arguments.of(3960, 11, IntStream.of(1, 7, 1, 2).toArray())
        );
    }

    public static Stream<Arguments> variationsWithoutRepetition() {
        return Stream.of(
            Arguments.of(5, 3, 60),
            Arguments.of(10, 7, 604_800)
        );
    }

    public static Stream<Arguments> variationsWithRepetition() {
        return Stream.of(
            Arguments.of(5, 3, 125)
        );
    }

    public static Stream<Arguments> combinationsWithoutRepetition() {
        return Stream.of(
            Arguments.of(5, 3, 10),
            Arguments.of(10, 6, 210),
            Arguments.of(8, 2, 28),
            Arguments.of(23, 5, 33_649)
        );
    }

    public static Stream<Arguments> combinationsWithRepetition() {
        return Stream.of(
            Arguments.of(5, 3, 35)
        );
    }
}
