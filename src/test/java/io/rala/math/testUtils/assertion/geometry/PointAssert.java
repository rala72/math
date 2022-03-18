package io.rala.math.testUtils.assertion.geometry;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.testUtils.assertion.core.AbstractComparableAssert;
import org.assertj.core.api.AbstractDoubleAssert;
import org.assertj.core.data.Offset;

import static io.rala.math.testUtils.assertion.utils.OffsetUtils.doubleOffset;

@SuppressWarnings("UnusedReturnValue")
public class PointAssert<T extends Number> extends AbstractComparableAssert<T, PointAssert<T>, Point<T>> {
    public PointAssert(Point<T> point) {
        super(point, PointAssert.class);
    }

    /**
     * @see #hasXY(Number)
     */
    public PointAssert<T> hasZeroXY() {
        isNotNull().hasArithmetic();
        return hasXY(actual.getArithmetic().zero());
    }

    /**
     * @see #hasX(Number)
     * @see #hasY(Number)
     */
    public PointAssert<T> hasXY(T xy) {
        isNotNull();
        return hasX(xy).hasY(xy);
    }

    /**
     * @see Point#getX()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public PointAssert<T> hasX(T x) {
        isNotNull();
        assertThatNoArgumentIsNull(x);

        return assertNumberAsDouble(actual.getX(), doubleAssert ->
            doubleAssert.as("point has x")
                .isCloseTo(x.doubleValue(), doubleOffset()));
    }

    /**
     * @see Point#getY()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public PointAssert<T> hasY(T y) {
        isNotNull();
        assertThatNoArgumentIsNull(y);

        return assertNumberAsDouble(actual.getY(), doubleAssert ->
            doubleAssert.as("point has y")
                .isCloseTo(y.doubleValue(), doubleOffset()));
    }

    /**
     * @see #hasX(Number)
     * @see #hasY(Number)
     */
    public PointAssert<T> isCloseTo(Point<T> point) {
        isNotNull();
        assertThatNoArgumentIsNull(point);

        return hasX(point.getX()).hasY(point.getY());
    }

    @Override
    protected AbstractArithmetic<T> getArithmetic() {
        return actual.getArithmetic();
    }
}
