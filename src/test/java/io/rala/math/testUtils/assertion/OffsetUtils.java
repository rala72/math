package io.rala.math.testUtils.assertion;

import org.assertj.core.data.Offset;

import java.math.BigDecimal;

import static org.assertj.core.data.Offset.offset;

/**
 * utils for {@link Offset}
 */
public class OffsetUtils {
    public static final double DEFAULT_OFFSET = 0.00001;

    private OffsetUtils() {
    }

    /**
     * @return {@link #DEFAULT_OFFSET}
     * @see #doubleOffset(double)
     */
    public static Offset<Double> doubleOffset() {
        return doubleOffset(DEFAULT_OFFSET);
    }

    /**
     * @param d value of offset
     * @return created offset
     * @see Offset#offset(Number)
     */
    public static Offset<Double> doubleOffset(double d) {
        return offset(d);
    }

    /**
     * @return {@link #DEFAULT_OFFSET}
     * @see #bigDecimalOffset(BigDecimal)
     */
    public static Offset<BigDecimal> bigDecimalOffset() {
        return bigDecimalOffset(BigDecimal.valueOf(DEFAULT_OFFSET));
    }

    /**
     * @param bigDecimal value of offset
     * @return created offset
     * @see Offset#offset(Number)
     */
    public static Offset<BigDecimal> bigDecimalOffset(BigDecimal bigDecimal) {
        return offset(bigDecimal);
    }
}
