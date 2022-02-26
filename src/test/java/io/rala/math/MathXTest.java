package io.rala.math;

import io.rala.math.testUtils.arguments.MathXArgumentsStreamFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MathXTest {
    @ParameterizedTest
    @MethodSource("getGcdArguments")
    void gcd(int expected, int... a) {
        assertThat(a.length == 2 ?
            MathX.gcd(a[0], a[1]) : MathX.gcd(a)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getFactorsArguments")
    void factors(int number, List<Integer> expected) {
        assertThat(MathX.factors(number)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getFactorialArguments")
    void factorial(int number, long expected) {
        assertThat(MathX.factorial(number)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getLcmArguments")
    void lcm(int expected, int... a) {
        assertThat(a.length == 2 ?
            MathX.lcm(a[0], a[1]) : MathX.lcm(a)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getRootUsingDoubleArguments")
    void rootUsingDouble(double a, int n, Double expected) {
        if (expected == null) {
            assertThatThrownBy(() -> MathX.root(a, n))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("number has to be positive");
        } else {
            assertThat(Math.round(MathX.root(a, n))).isEqualTo(expected.longValue());
        }
    }

    @ParameterizedTest
    @MethodSource("getRootUsingBigDecimalArguments")
    void rootUsingBigDecimal(BigDecimal a, int n, BigDecimal expected) {
        if (expected == null) {
            assertThatThrownBy(() -> MathX.root(a, n))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("number has to be positive");
        } else {
            BigDecimal root = MathX.root(a, n);
            assertThat(root).isNotNull();
            assertThat(root.stripTrailingZeros()).isEqualTo(expected);
        }
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
