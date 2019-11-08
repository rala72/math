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
     * @param a number to get factors of
     * @return list of factors
     */
    public static List<Integer> factors(long a) {
        // https://stackoverflow.com/a/6233030/2715720
        List<Integer> factors = new ArrayList<>();
        // factors.add(1);
        for (int x = 2; 1 < a; )
            if (a % x == 0) {
                factors.add(x);
                a /= x;
            } else x++;
        return Collections.unmodifiableList(factors);
    }
}
