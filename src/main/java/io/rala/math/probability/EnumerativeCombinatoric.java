package io.rala.math.probability;

import io.rala.math.MathX;

import java.util.Arrays;

@SuppressWarnings({"unused", "WeakerAccess"})
public class EnumerativeCombinatoric {
    private EnumerativeCombinatoric() {
    }

    /**
     * @param n number of elements
     * @return <code>n!</code>
     * @see MathX#factorial(int)
     */
    public static long permutationsWithoutRepetition(int n) {
        return permutationsWithoutRepetition((long) n);
    }

    /**
     * @param n number of elements
     * @return <code>n!</code>
     * @see MathX#factorial(long)
     */
    public static long permutationsWithoutRepetition(long n) {
        return MathX.factorial(n);
    }

    /**
     * <i>multinomial theorem</i>
     *
     * @param n number of elements
     * @param k sub number of elements
     * @return <code>n! / product(k)</code>
     * @see MathX#factorial(int)
     */
    public static long permutationsWithRepetition(int n, int... k) {
        return permutationsWithRepetition(n, Arrays.stream(k).mapToLong(MathX::factorial).toArray());
    }

    /**
     * <i>multinomial theorem</i>
     *
     * @param n number of elements
     * @param k sub number of elements
     * @return <code>n! / product(k)</code>
     * @see MathX#factorial(long)
     */
    public static long permutationsWithRepetition(long n, long... k) {
        return MathX.factorial(n) / Arrays.stream(k).map(MathX::factorial)
            .reduce((left, right) -> left * right).orElse(1);
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return <code>nPr(n,k)</code>
     * @see MathX#factorial(int)
     */
    public static long variationsWithoutRepetition(int n, int k) {
        return variationsWithoutRepetition((long) n, k);
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return <code>nPr(n,k)</code>
     * @see MathX#factorial(long)
     */
    public static long variationsWithoutRepetition(long n, long k) {
        return MathX.factorial(n) / MathX.factorial(n - k);
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return n^k
     * @see Math#pow(double, double)
     */
    public static long variationsWithRepetition(int n, int k) {
        return variationsWithRepetition((long) n, k);
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return n^k
     * @see Math#pow(double, double)
     */
    public static long variationsWithRepetition(long n, long k) {
        return (long) Math.pow(n, k);
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return <code>nCr(n,k)</code>
     * @see MathX#factorial(int)
     */
    public static long combinationsWithoutRepetition(int n, int k) {
        return combinationsWithoutRepetition((long) n, k);
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return <code>nCr(n,k)</code>
     * @see MathX#factorial(long)
     */
    public static long combinationsWithoutRepetition(long n, long k) {
        return MathX.factorial(n) / (MathX.factorial(n - k) * MathX.factorial(k));
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return <code>nCr(n+k-1,k)</code>
     * @see #combinationsWithoutRepetition(int, int)
     */
    public static long combinationsWithRepetition(int n, int k) {
        return combinationsWithRepetition((long) n, k);
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return <code>nCr(n+k-1,k)</code>
     * @see #combinationsWithoutRepetition(long, long)
     */
    public static long combinationsWithRepetition(long n, long k) {
        return combinationsWithoutRepetition(n + k - 1, k);
    }
}
