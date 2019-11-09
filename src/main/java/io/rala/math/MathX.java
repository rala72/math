package io.rala.math;

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
     * greatest common divisor using Euclid's algorithm
     *
     * @param a numbers of gcd
     * @return greatest common divisor
     * @see #gcd(int, int)
     */
    public static int gcd(int... a) {
        return (int) gcd(Arrays.stream(a).mapToLong(value -> value).toArray());
    }

    /**
     * greatest common divisor using Euclid's algorithm
     *
     * @param a numbers of gcd
     * @return greatest common divisor
     * @see #gcd(long, long)
     */
    public static long gcd(long... a) {
        if (a.length == 0) return 0;
        if (a.length == 1) return a[0];
        long current = a[0];
        for (int i = 1; i < a.length; i++)
            current = gcd(current, a[i]);
        return current;
    }

    /**
     * greatest common divisor using Euclid's algorithm
     *
     * @param a number1 of gcd
     * @param b number2 of gcd
     * @return greatest common divisor
     * @see #gcd(int...)
     */
    public static int gcd(int a, int b) {
        return (int) gcd((long) a, b);
    }

    /**
     * greatest common divisor using Euclid's algorithm
     *
     * @param a number1 of gcd
     * @param b number2 of gcd
     * @return greatest common divisor
     * @see #gcd(long...)
     */
    public static long gcd(long a, long b) {
        if (a == 0) return b;
        if (b == 0) return a;
        return gcd(b, a % b);
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
     */
    public static long factorial(long a) {
        return a < 0 ? 0 : a == 0 ? 1 : a * factorial(a - 1);
    }

    // region lcm

    /**
     * least common multiple using {@link #gcd(long, long)}
     *
     * @param a numbers of lcm
     * @return least common multiple
     * @see #lcm(int, int)
     */
    public static int lcm(int... a) {
        return (int) lcm(Arrays.stream(a).mapToLong(value -> value).toArray());
    }

    /**
     * least common multiple using {@link #gcd(long, long)}
     *
     * @param a numbers of lcm
     * @return least common multiple
     * @see #lcm(long, long)
     */
    public static long lcm(long... a) {
        if (a.length == 0) return 0;
        if (a.length == 1) return a[0];
        long current = a[0];
        for (int i = 1; i < a.length; i++)
            current = lcm(current, a[i]);
        return current;
    }

    /**
     * least common multiple using {@link #gcd(long, long)}
     *
     * @param a number1 of lcm
     * @param b number2 of lcm
     * @return least common multiple
     */
    public static int lcm(int a, int b) {
        return (int) lcm((long) a, b);
    }

    /**
     * least common multiple using {@link #gcd(long, long)}
     *
     * @param a number1 of lcm
     * @param b number2 of lcm
     * @return least common multiple
     */
    public static long lcm(long a, long b) {
        return Math.abs(a * b) / gcd(a, b);
    }

    // endregion
}
