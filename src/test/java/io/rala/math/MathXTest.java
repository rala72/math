package io.rala.math;

import io.rala.math.testUtils.ArgumentStreamFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class MathXTest {
    @ParameterizedTest
    @MethodSource("getFactorsArguments")
    void testFactors(int number, List<Integer> expected) {
        Assertions.assertEquals(expected, MathX.factors(number));
    }

    private static Stream<Arguments> getFactorsArguments() {
        return ArgumentStreamFactory.getMathXFactorsArguments();
    }
}
