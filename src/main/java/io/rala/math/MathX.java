package io.rala.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * collection of math functions similar to {@link Math}
 *
 * @see Math
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class MathX {
    private MathX() {
    }

    // region gcd

    /**
     * @param a numbers of gcd
     * @return greatest common divisor
     * @see #gcd(int, int)
     * @see BigInteger#gcd(BigInteger)
     */
    public static int gcd(int... a) {
        return Math.toIntExact(gcd(Arrays.stream(a).mapToLong(value -> value).toArray()));
    }

    /**
     * @param a numbers of gcd
     * @return greatest common divisor
     * @throws ArithmeticException may be thrown for example by {@link BigInteger#longValueExact()}
     * @see #gcd(long, long)
     * @see BigInteger#gcd(BigInteger)
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
     */
    public static BigInteger gcd(BigInteger... a) {
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
     * @see #gcd(int...)
     * @see BigInteger#gcd(BigInteger)
     */
    public static int gcd(int a, int b) {
        return Math.toIntExact(gcd((long) a, b));
    }

    /**
     * @param a number1 of gcd
     * @param b number2 of gcd
     * @return greatest common divisor
     * @throws ArithmeticException may be thrown for example by {@link BigInteger#longValueExact()}
     * @see #gcd(long...)
     * @see BigInteger#gcd(BigInteger)
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
     */
    public static BigInteger gcd(BigInteger a, BigInteger b) {
        return a.gcd(b);
    }

    // endregion

    // region factors

    /**
     * @param a number to get factors of
     * @return list of factors
     */
    public static List<Integer> factors(int a) {
        return factors((long) a).stream()
            .mapToInt(Long::intValue).boxed()
            .collect(Collectors.toList());
    }

    /**
     * @param a number to get factors of
     * @return list of factors
     */
    public static List<Long> factors(long a) {
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
     */
    public static long factorial(int a) {
        return factorial((long) a);
    }

    /**
     * factorial of a number using recursion
     *
     * @param a number
     * @return factorial
     * @throws ArithmeticException may be thrown for example by {@link BigInteger#longValueExact()}
     */
    public static long factorial(long a) {
        return factorial(BigInteger.valueOf(a)).longValueExact();
    }

    /**
     * factorial of a number using recursion
     *
     * @param a number
     * @return factorial
     */
    public static BigInteger factorial(BigInteger a) {
        return a.compareTo(BigInteger.ZERO) < 0 ? BigInteger.ZERO :
            a.equals(BigInteger.ZERO) ? BigInteger.ONE :
                a.multiply(factorial(a.subtract(BigInteger.ONE)));
    }

    // endregion

    // region lcm

    /**
     * least common multiple using {@link #gcd(int, int)}
     *
     * @param a numbers of lcm
     * @return least common multiple
     * @see #lcm(int, int)
     */
    public static int lcm(int... a) {
        return Math.toIntExact(lcm(Arrays.stream(a).mapToLong(value -> value).toArray()));
    }

    /**
     * least common multiple using {@link #gcd(long, long)}
     *
     * @param a numbers of lcm
     * @return least common multiple
     * @throws ArithmeticException may be thrown for example by {@link BigInteger#longValueExact()}
     * @see #lcm(long, long)
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
     */
    public static BigInteger lcm(BigInteger... a) {
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
     * @see #lcm(int...)
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
     * @throws ArithmeticException may be thrown for example by {@link BigInteger#longValueExact()}
     * @see #lcm(long...)
     */
    public static long lcm(long a, long b) {
        return lcm(BigInteger.valueOf(a), BigInteger.valueOf(b)).longValueExact();
    }

    /**
     * least common multiple using {@link #gcd(long, long)}
     *
     * @param a number1 of lcm
     * @param b number2 of lcm
     * @return least common multiple
     * @see #lcm(BigInteger...)
     */
    public static BigInteger lcm(BigInteger a, BigInteger b) {
        return a.multiply(b).abs().divide(gcd(a, b));
    }

    // endregion
}
