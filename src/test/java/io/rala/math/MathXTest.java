package io.rala.math;

import io.rala.math.testUtils.arguments.MathXArgumentsStreamFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathXTest {
    @ParameterizedTest
    @MethodSource("getGcdArguments")
    void gcd(int expected, int... a) {
        assertEquals(expected, a.length == 2 ?
            MathX.gcd(a[0], a[1]) : MathX.gcd(a)
        );
    }

    @ParameterizedTest
    @MethodSource("getFactorsArguments")
    void factors(int number, List<Integer> expected) {
        assertEquals(expected, MathX.factors(number));
    }

    @ParameterizedTest
    @MethodSource("getFactorialArguments")
    void factorial(int number, long expected) {
        assertEquals(expected, MathX.factorial(number));
    }

    @ParameterizedTest
    @MethodSource("getLcmArguments")
    void lcm(int expected, int... a) {
        assertEquals(expected, a.length == 2 ?
            MathX.lcm(a[0], a[1]) : MathX.lcm(a)
        );
    }

    @ParameterizedTest
    @MethodSource("getRootUsingDoubleArguments")
    void rootUsingDouble(double a, int n, Double expected) {
        if (expected == null) {
            try {
                MathX.root(a, n);
            } catch (IllegalArgumentException e) {
                assertEquals("number has to be positive", e.getMessage());
                return;
            }
            Assertions.fail();
        }
        assertEquals(expected, Math.round(MathX.root(a, n)));
    }

    @ParameterizedTest
    @MethodSource("getRootUsingBigDecimalArguments")
    void rootUsingBigDecimal(BigDecimal a, int n, BigDecimal expected) {
        if (expected == null) {
            try {
                MathX.root(a, n);
            } catch (IllegalArgumentException e) {
                assertEquals("number has to be positive", e.getMessage());
                return;
            }
            Assertions.fail();
        }
        assertEquals(expected, MathX.root(a, n).stripTrailingZeros());
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

    private static Stream<Arguments> getRootUsingDoubleArguments() {
        return MathXArgumentsStreamFactory.rootUsingDouble();
    }

    private static Stream<Arguments> getRootUsingBigDecimalArguments() {
        return MathXArgumentsStreamFactory.rootUsingBigDecimal();
    }

    // endregion
}
