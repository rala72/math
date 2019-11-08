package io.rala.math;

import java.util.ArrayList;
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

    /**
     * greatest common divisor using Euclid's algorithm
     *
     * @param a number1 of gcd
     * @param b number2 of gcd
     * @return greatest common divisor
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
     */
    public static long gcd(long a, long b) {
        if (a == 0) return b;
        if (b == 0) return a;
        return gcd(b, a % b);
    }

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
}
