package io.rala.math;

import io.rala.math.testUtils.MathXArgumentsStreamFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class MathXTest {
    @ParameterizedTest
    @MethodSource("getGcdArguments")
    void gcd(int expected, int... a) {
        Assertions.assertEquals(expected, a.length == 2 ?
            MathX.gcd(a[0], a[1]) : MathX.gcd(a)
        );
    }

    @ParameterizedTest
    @MethodSource("getFactorsArguments")
    void factors(int number, List<Integer> expected) {
        Assertions.assertEquals(expected, MathX.factors(number));
    }

    @ParameterizedTest
    @MethodSource("getFactorialArguments")
    void factorial(int number, long expected) {
        Assertions.assertEquals(expected, MathX.factorial(number));
    }

    @ParameterizedTest
    @MethodSource("getLcmArguments")
    void lcm(int expected, int... a) {
        Assertions.assertEquals(expected, a.length == 2 ?
            MathX.lcm(a[0], a[1]) : MathX.lcm(a)
        );
    }

    // region argument streams

    private static Stream<Arguments> getGcdArguments() {
        return MathXArgumentsStreamFactory.gcd();
    }

    private static Stream<Arguments> getFactorsArguments() {
        return MathXArgumentsStreamFactory.factors();
    }

    private static Stream<Arguments> getFactorialArguments() {
        return MathXArgumentsStreamFactory.factorial();
    }

    private static Stream<Arguments> getLcmArguments() {
        return MathXArgumentsStreamFactory.lcm();
    }

    // endregion
}
