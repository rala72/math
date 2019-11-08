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
    @MethodSource("getGcdArguments")
    void testGcd(int expected, int... a) {
        Assertions.assertEquals(expected, a.length == 2 ?
            MathX.gcd(a[0], a[1]) : MathX.gcd(a)
        );
    }

    @ParameterizedTest
    @MethodSource("getFactorsArguments")
    void testFactors(int number, List<Integer> expected) {
        Assertions.assertEquals(expected, MathX.factors(number));
    }

    @ParameterizedTest
    @MethodSource("getLcmArguments")
    void testLcm(int expected, int... a) {
        Assertions.assertEquals(expected, a.length == 2 ?
            MathX.lcm(a[0], a[1]) : MathX.lcm(a)
        );
    }

    private static Stream<Arguments> getGcdArguments() {
        return ArgumentStreamFactory.getMathXGcdArguments();
    }

    private static Stream<Arguments> getFactorsArguments() {
        return ArgumentStreamFactory.getMathXFactorsArguments();
    }

    private static Stream<Arguments> getLcmArguments() {
        return ArgumentStreamFactory.getMathXLcmArguments();
    }
}
