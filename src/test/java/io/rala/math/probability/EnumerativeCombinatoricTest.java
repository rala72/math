package io.rala.math.probability;

import io.rala.math.testUtils.arguments.EnumerativeCombinatoricsArgumentsStreamFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class EnumerativeCombinatoricTest {
    @ParameterizedTest
    @MethodSource("getPermutationsWithoutRepetitionArguments")
    void permutationsWithoutRepetition(int n, long expected) {
        Assertions.assertEquals(expected,
            EnumerativeCombinatoric.permutationsWithoutRepetition(n)
        );
    }

    @ParameterizedTest
    @MethodSource("getPermutationsWithRepetitionArguments")
    void permutationsWithRepetition(long expected, int n, int... k) {
        Assertions.assertEquals(expected,
            EnumerativeCombinatoric.permutationsWithRepetition(n, k)
        );
    }

    @ParameterizedTest
    @MethodSource("getVariationsWithoutRepetitionArguments")
    void variationsWithoutRepetition(int n, int k, long expected) {
        Assertions.assertEquals(expected,
            EnumerativeCombinatoric.variationsWithoutRepetition(n, k)
        );
    }

    @ParameterizedTest
    @MethodSource("getVariationsWithRepetitionArguments")
    void variationsWithRepetition(int n, int k, long expected) {
        Assertions.assertEquals(expected,
            EnumerativeCombinatoric.variationsWithRepetition(n, k)
        );
    }

    @ParameterizedTest
    @MethodSource("getCombinationsWithoutRepetitionArguments")
    void combinationsWithoutRepetition(int n, int k, long expected) {
        Assertions.assertEquals(expected,
            EnumerativeCombinatoric.combinationsWithoutRepetition(n, k)
        );
    }

    @ParameterizedTest
    @MethodSource("getCombinationsWithRepetitionArguments")
    void combinationsWithRepetition(int n, int k, long expected) {
        Assertions.assertEquals(expected,
            EnumerativeCombinatoric.combinationsWithRepetition(n, k)
        );
    }

    // region argument streams

    private static Stream<Arguments> getPermutationsWithoutRepetitionArguments() {
        return EnumerativeCombinatoricsArgumentsStreamFactory.permutationsWithoutRepetition();
    }

    private static Stream<Arguments> getPermutationsWithRepetitionArguments() {
        return EnumerativeCombinatoricsArgumentsStreamFactory.permutationsWithRepetition();
    }

    private static Stream<Arguments> getVariationsWithoutRepetitionArguments() {
        return EnumerativeCombinatoricsArgumentsStreamFactory.variationsWithoutRepetition();
    }

    private static Stream<Arguments> getVariationsWithRepetitionArguments() {
        return EnumerativeCombinatoricsArgumentsStreamFactory.variationsWithRepetition();
    }

    private static Stream<Arguments> getCombinationsWithoutRepetitionArguments() {
        return EnumerativeCombinatoricsArgumentsStreamFactory.combinationsWithoutRepetition();
    }

    private static Stream<Arguments> getCombinationsWithRepetitionArguments() {
        return EnumerativeCombinatoricsArgumentsStreamFactory.combinationsWithRepetition();
    }

    // endregion
}
