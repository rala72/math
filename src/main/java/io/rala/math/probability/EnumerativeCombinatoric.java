package io.rala.math.probability;

import io.rala.math.MathX;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * collection of enumerative combinatoric math functions
 *
 * @since 1.0.0
 */
public class EnumerativeCombinatoric {
    private EnumerativeCombinatoric() {
    }

    // region permutations

    /**
     * @param n number of elements
     * @return {@code n!}
     * @see MathX#factorial(int)
     * @since 1.0.0
     */
    public static long permutationsWithoutRepetition(int n) {
        return permutationsWithoutRepetition((long) n);
    }

    /**
     * @param n number of elements
     * @return {@code n!}
     * @throws ArithmeticException may be thrown for example by
     *                             {@link BigInteger#longValueExact()}
     * @see MathX#factorial(long)
     * @since 1.0.0
     */
    public static long permutationsWithoutRepetition(long n) {
        return permutationsWithoutRepetition(BigInteger.valueOf(n)).longValueExact();
    }

    /**
     * @param n number of elements
     * @return {@code n!}
     * @see MathX#factorial(BigInteger)
     * @since 1.0.0
     */
    @NotNull
    public static BigInteger permutationsWithoutRepetition(@NotNull BigInteger n) {
        return MathX.factorial(n);
    }

    /**
     * <i>multinomial theorem</i>
     *
     * @param n number of elements
     * @param k sub number of elements
     * @return {@code n! / product(k)}
     * @see MathX#factorial(int)
     * @since 1.0.0
     */
    public static long permutationsWithRepetition(int n, int... k) {
        return permutationsWithRepetition(n,
            Arrays.stream(k).mapToLong(Long::valueOf).toArray()
        );
    }

    /**
     * <i>multinomial theorem</i>
     *
     * @param n number of elements
     * @param k sub number of elements
     * @return {@code n! / product(k)}
     * @throws ArithmeticException may be thrown for example by
     *                             {@link BigInteger#longValueExact()}
     * @see MathX#factorial(long)
     * @since 1.0.0
     */
    public static long permutationsWithRepetition(long n, long... k) {
        return permutationsWithRepetition(BigInteger.valueOf(n),
            Arrays.stream(k).mapToObj(BigInteger::valueOf).toArray(BigInteger[]::new)
        ).longValueExact();
    }

    /**
     * <i>multinomial theorem</i>
     *
     * @param n number of elements
     * @param k sub number of elements
     * @return {@code n! / product(k)}
     * @see MathX#factorial(BigInteger)
     * @since 1.0.0
     */
    @NotNull
    public static BigInteger permutationsWithRepetition(@NotNull BigInteger n, @NotNull BigInteger... k) {
        return MathX.factorial(n).divide(
            Arrays.stream(k).map(MathX::factorial)
                .reduce(BigInteger::multiply).orElse(BigInteger.ONE)
        );
    }

    // endregion

    // region variations

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return {@code nPr(n,k)}
     * @see MathX#factorial(int)
     * @since 1.0.0
     */
    public static long variationsWithoutRepetition(int n, int k) {
        return variationsWithoutRepetition((long) n, k);
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return {@code nPr(n,k)}
     * @throws ArithmeticException may be thrown for example by
     *                             {@link BigInteger#longValueExact()}
     * @see MathX#factorial(long)
     * @since 1.0.0
     */
    public static long variationsWithoutRepetition(long n, long k) {
        return variationsWithoutRepetition(
            BigInteger.valueOf(n), BigInteger.valueOf(k)
        ).longValueExact();
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return {@code nPr(n,k)}
     * @see MathX#factorial(BigInteger)
     * @since 1.0.0
     */
    @NotNull
    public static BigInteger variationsWithoutRepetition(
        @NotNull BigInteger n, @NotNull BigInteger k
    ) {
        return MathX.factorial(n).divide(MathX.factorial(n.subtract(k)));
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return n^k
     * @see BigInteger#pow(int)
     * @since 1.0.0
     */
    public static long variationsWithRepetition(int n, int k) {
        return variationsWithRepetition((long) n, k);
    }

    /**
     * @param n number of elements
     * @param k sub number of elements - has to be in {@link Integer} range
     * @return n^k
     * @throws ArithmeticException may be thrown for example by
     *                             {@link BigInteger#longValueExact()}
     * @see BigInteger#pow(int)
     * @since 1.0.0
     */
    public static long variationsWithRepetition(long n, long k) {
        return variationsWithRepetition(
            BigInteger.valueOf(n), BigInteger.valueOf(k)
        ).longValueExact();
    }

    /**
     * @param n number of elements
     * @param k sub number of elements - has to be in {@link Integer} range
     * @return n^k
     * @throws ArithmeticException may be thrown for example by
     *                             {@link BigInteger#longValueExact()}
     * @see BigInteger#pow(int)
     * @since 1.0.0
     */
    @NotNull
    public static BigInteger variationsWithRepetition(
        @NotNull BigInteger n, @NotNull BigInteger k
    ) {
        return n.pow(k.intValueExact());
    }

    // endregion

    // region combinations

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return {@code nCr(n,k)}
     * @see MathX#factorial(int)
     * @since 1.0.0
     */
    public static long combinationsWithoutRepetition(int n, int k) {
        return combinationsWithoutRepetition((long) n, k);
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return {@code nCr(n,k)}
     * @throws ArithmeticException may be thrown for example by
     *                             {@link BigInteger#longValueExact()}
     * @see MathX#factorial(long)
     * @since 1.0.0
     */
    public static long combinationsWithoutRepetition(long n, long k) {
        return combinationsWithoutRepetition(
            BigInteger.valueOf(n), BigInteger.valueOf(k)
        ).longValueExact();
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return {@code nCr(n,k)}
     * @see MathX#factorial(BigInteger)
     * @since 1.0.0
     */
    @NotNull
    public static BigInteger combinationsWithoutRepetition(
        @NotNull BigInteger n, @NotNull BigInteger k
    ) {
        if (k.equals(BigInteger.ZERO) || n.equals(k)) return BigInteger.ONE;
        return MathX.factorial(n).divide(
            MathX.factorial(n.subtract(k))
                .multiply(MathX.factorial(k))
        );
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return {@code nCr(n+k-1,k)}
     * @see #combinationsWithoutRepetition(int, int)
     * @since 1.0.0
     */
    public static long combinationsWithRepetition(int n, int k) {
        return combinationsWithRepetition((long) n, k);
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return {@code nCr(n+k-1,k)}
     * @throws ArithmeticException may be thrown for example by
     *                             {@link BigInteger#longValueExact()}
     * @see #combinationsWithoutRepetition(long, long)
     * @since 1.0.0
     */
    public static long combinationsWithRepetition(long n, long k) {
        return combinationsWithRepetition(BigInteger.valueOf(n), BigInteger.valueOf(k))
            .longValueExact();
    }

    /**
     * @param n number of elements
     * @param k sub number of elements
     * @return {@code nCr(n+k-1,k)}
     * @see #combinationsWithoutRepetition(BigInteger, BigInteger)
     * @since 1.0.0
     */
    @NotNull
    public static BigInteger combinationsWithRepetition(
        @NotNull BigInteger n, @NotNull BigInteger k
    ) {
        return combinationsWithoutRepetition(n.add(k).subtract(BigInteger.ONE), k);
    }

    // endregion
}
