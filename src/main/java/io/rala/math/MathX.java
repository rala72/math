package io.rala.math;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * collection of math functions similar to {@link Math}
 *
 * @see Math
 * @since 1.0.0
 */
public class MathX {
    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL64;
    private static final String ILLEGAL_ARGUMENT_NUMBER_HAS_TO_BE_POSITIVE =
        "number has to be positive";

    private MathX() {
    }

    // region gcd

    /**
     * @param a numbers of gcd
     * @return greatest common divisor
     * @throws ArithmeticException may be thrown for example by
     *                             {@link Math#toIntExact(long)}
     * @see #gcd(int, int)
     * @see BigInteger#gcd(BigInteger)
     * @since 1.0.0
     */
    public static int gcd(int... a) {
        return Math.toIntExact(
            gcd(Arrays.stream(a).mapToLong(value -> value).toArray())
        );
    }

    /**
     * @param a numbers of gcd
     * @return greatest common divisor
     * @throws ArithmeticException may be thrown for example by
     *                             {@link BigInteger#longValueExact()}
     * @see #gcd(long, long)
     * @see BigInteger#gcd(BigInteger)
     * @since 1.0.0
     */
    public static long gcd(long... a) {
        return gcd(
            Arrays.stream(a)
                .mapToObj(BigInteger::valueOf)
                .toArray(BigInteger[]::new)
        ).longValueExact();
    }

    /**
     * @param a numbers of gcd
     * @return greatest common divisor
     * @see #gcd(BigInteger, BigInteger)
     * @see BigInteger#gcd(BigInteger)
     * @since 1.0.0
     */
    @NotNull
    public static BigInteger gcd(@NotNull BigInteger... a) {
        if (a.length == 0) return BigInteger.ZERO;
        if (a.length == 1) return a[0];
        BigInteger current = a[0];
        for (int i = 1; i < a.length; i++)
            current = gcd(current, a[i]);
        return current;
    }

    /**
     * @param a number1 of gcd
     * @param b number2 of gcd
     * @return greatest common divisor
     * @throws ArithmeticException may be thrown for example by
     *                             {@link Math#toIntExact(long)}
     * @see #gcd(int...)
     * @see BigInteger#gcd(BigInteger)
     * @since 1.0.0
     */
    public static int gcd(int a, int b) {
        return Math.toIntExact(gcd((long) a, b));
    }

    /**
     * @param a number1 of gcd
     * @param b number2 of gcd
     * @return greatest common divisor
     * @throws ArithmeticException may be thrown for example by
     *                             {@link BigInteger#longValueExact()}
     * @see #gcd(long...)
     * @see BigInteger#gcd(BigInteger)
     * @since 1.0.0
     */
    public static long gcd(long a, long b) {
        return gcd(BigInteger.valueOf(a), BigInteger.valueOf(b)).longValueExact();
    }

    /**
     * @param a number1 of gcd
     * @param b number2 of gcd
     * @return greatest common divisor
     * @see #gcd(BigInteger...)
     * @see BigInteger#gcd(BigInteger)
     * @since 1.0.0
     */
    @NotNull
    public static BigInteger gcd(@NotNull BigInteger a, @NotNull BigInteger b) {
        return a.gcd(b);
    }

    // endregion

    // region factors

    /**
     * @param a number to get factors of
     * @return list of factors
     * @since 1.0.0
     */
    @NotNull
    @Unmodifiable
    public static List<@NotNull Integer> factors(int a) {
        return factors((long) a).stream()
            .mapToInt(Long::intValue).boxed()
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * @param a number to get factors of
     * @return list of factors
     * @since 1.0.0
     */
    @NotNull
    @Unmodifiable
    public static List<@NotNull Long> factors(long a) {
        // https://stackoverflow.com/a/6233030/2715720
        List<Long> factors = new ArrayList<>();
        // factors.add(1);
        for (long x = 2; 1 < a; )
            if (a % x == 0) {
                factors.add(x);
                a /= x;
            } else x++;
        return Collections.unmodifiableList(factors);
    }

    // endregion

    // region factorial

    /**
     * factorial of a number using recursion
     *
     * @param a number
     * @return factorial
     * @since 1.0.0
     */
    public static long factorial(int a) {
        return factorial((long) a);
    }

    /**
     * factorial of a number using recursion
     *
     * @param a number
     * @return factorial
     * @throws ArithmeticException may be thrown for example by
     *                             {@link BigInteger#longValueExact()}
     * @since 1.0.0
     */
    public static long factorial(long a) {
        return factorial(BigInteger.valueOf(a)).longValueExact();
    }

    /**
     * factorial of a number using recursion
     *
     * @param a number
     * @return factorial
     * @since 1.0.0
     */
    @NotNull
    public static BigInteger factorial(@NotNull BigInteger a) {
        if (a.compareTo(BigInteger.ZERO) < 0) return BigInteger.ZERO;
        return a.equals(BigInteger.ZERO) ? BigInteger.ONE :
            a.multiply(factorial(a.subtract(BigInteger.ONE)));
    }

    // endregion

    // region lcm

    /**
     * least common multiple using {@link #gcd(int, int)}
     *
     * @param a numbers of lcm
     * @return least common multiple
     * @throws ArithmeticException may be thrown for example by
     *                             {@link Math#toIntExact(long)}
     * @see #lcm(int, int)
     * @since 1.0.0
     */
    public static int lcm(int... a) {
        return Math.toIntExact(
            lcm(Arrays.stream(a).mapToLong(value -> value).toArray())
        );
    }

    /**
     * least common multiple using {@link #gcd(long, long)}
     *
     * @param a numbers of lcm
     * @return least common multiple
     * @throws ArithmeticException may be thrown for example by
     *                             {@link BigInteger#longValueExact()}
     * @see #lcm(long, long)
     * @since 1.0.0
     */
    public static long lcm(long... a) {
        return lcm(
            Arrays.stream(a)
                .mapToObj(BigInteger::valueOf)
                .toArray(BigInteger[]::new)
        ).longValueExact();
    }

    /**
     * least common multiple using {@link #gcd(BigInteger, BigInteger)}
     *
     * @param a numbers of lcm
     * @return least common multiple
     * @see #lcm(BigInteger, BigInteger)
     * @since 1.0.0
     */
    @NotNull
    public static BigInteger lcm(@NotNull BigInteger... a) {
        if (a.length == 0) return BigInteger.ZERO;
        if (a.length == 1) return a[0];
        BigInteger current = a[0];
        for (int i = 1; i < a.length; i++)
            current = lcm(current, a[i]);
        return current;
    }

    /**
     * least common multiple using {@link #gcd(int, int)}
     *
     * @param a number1 of lcm
     * @param b number2 of lcm
     * @return least common multiple
     * @throws ArithmeticException may be thrown for example by
     *                             {@link Math#toIntExact(long)}
     * @see #lcm(int...)
     * @since 1.0.0
     */
    public static int lcm(int a, int b) {
        return Math.toIntExact(lcm((long) a, b));
    }

    /**
     * least common multiple using {@link #gcd(long, long)}
     *
     * @param a number1 of lcm
     * @param b number2 of lcm
     * @return least common multiple
     * @throws ArithmeticException may be thrown for example by
     *                             {@link BigInteger#longValueExact()}
     * @see #lcm(long...)
     * @since 1.0.0
     */
    public static long lcm(long a, long b) {
        return lcm(BigInteger.valueOf(a), BigInteger.valueOf(b)).longValueExact();
    }

    /**
     * least common multiple using {@link #gcd(BigInteger, BigInteger)}
     *
     * @param a number1 of lcm
     * @param b number2 of lcm
     * @return least common multiple
     * @see #lcm(BigInteger...)
     * @since 1.0.0
     */
    @NotNull
    public static BigInteger lcm(@NotNull BigInteger a, @NotNull BigInteger b) {
        return a.multiply(b).abs().divide(gcd(a, b));
    }

    // endregion

    // region root

    /**
     * calculates nth-root using {@link Math#pow(double, double)}
     * with {@code 1.0/n}
     *
     * @param a number to calc root
     * @param n number of root
     * @return calculated root
     * @throws IllegalArgumentException if {@code a} is not positive
     * @see Math#pow(double, double)
     * @since 1.0.0
     */
    public static double root(double a, int n) {
        if (a < 0)
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_NUMBER_HAS_TO_BE_POSITIVE);
        return n == 2 ? Math.sqrt(a) : Math.pow(a, 1.0 / n);
    }

    /**
     * calculates nth-root using a {@link MathContext}
     * with {@link MathContext#DECIMAL64}
     *
     * @param a number to calc root
     * @param n number of root
     * @return calculated root
     * @see #root(BigDecimal, int)
     * @since 1.0.0
     */
    @NotNull
    public static BigDecimal root(@NotNull BigDecimal a, int n) {
        return root(a, n, MATH_CONTEXT);
    }

    /**
     * calculates nth-root using a custom {@link MathContext}
     *
     * @param a       number to calc root
     * @param n       number of root
     * @param context context to use
     * @return calculated root
     * @throws IllegalArgumentException if {@code a} is not positive
     * @since 1.0.0
     */
    @NotNull
    public static BigDecimal root(@NotNull BigDecimal a, int n, @NotNull MathContext context) {
        // https://stackoverflow.com/a/34074999/2715720
        if (a.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_NUMBER_HAS_TO_BE_POSITIVE);
        if (a.equals(BigDecimal.ZERO)) return BigDecimal.ZERO;
        if (n == 2) return a.sqrt(context);
        BigDecimal current = a.divide(BigDecimal.valueOf(n), context);
        BigDecimal precision = BigDecimal.valueOf(.1)
            .movePointLeft(context.getPrecision());
        BigDecimal prev = a;
        while (current.subtract(prev).abs().compareTo(precision) > 0) {
            prev = current;
            current = BigDecimal.valueOf(n - 1L)
                .multiply(current)
                .add(a.divide(current.pow(n - 1), context))
                .divide(BigDecimal.valueOf(n), context);
        }
        return current;
    }

    // endregion
}
