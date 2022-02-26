package io.rala.math.probability;

import io.rala.math.testUtils.arguments.EnumerativeCombinatoricsArgumentsStreamFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class EnumerativeCombinatoricTest {
    @ParameterizedTest
    @MethodSource("getPermutationsWithoutRepetitionArguments")
    void permutationsWithoutRepetition(int n, long expected) {
        assertThat(EnumerativeCombinatoric.permutationsWithoutRepetition(n)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getPermutationsWithRepetitionArguments")
    void permutationsWithRepetition(long expected, int n, int... k) {
        assertThat(EnumerativeCombinatoric.permutationsWithRepetition(n, k)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getVariationsWithoutRepetitionArguments")
    void variationsWithoutRepetition(int n, int k, long expected) {
        assertThat(EnumerativeCombinatoric.variationsWithoutRepetition(n, k)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getVariationsWithRepetitionArguments")
    void variationsWithRepetition(int n, int k, long expected) {
        assertThat(EnumerativeCombinatoric.variationsWithRepetition(n, k)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getCombinationsWithoutRepetitionArguments")
    void combinationsWithoutRepetition(int n, int k, long expected) {
        assertThat(EnumerativeCombinatoric.combinationsWithoutRepetition(n, k)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getCombinationsWithRepetitionArguments")
    void combinationsWithRepetition(int n, int k, long expected) {
        assertThat(EnumerativeCombinatoric.combinationsWithRepetition(n, k)).isEqualTo(expected);
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
