package io.rala.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
     * @param number number to get factors of
     * @return list of factors
     */
    public static List<Integer> factors(long number) {
        // https://stackoverflow.com/a/6233030/2715720
        List<Integer> factors = new ArrayList<>();
        // factors.add(1);
        for (int a = 2; 1 < number; )
            if (number % a == 0) {
                factors.add(a);
                number /= a;
            } else a++;
        return Collections.unmodifiableList(factors);
    }
}
