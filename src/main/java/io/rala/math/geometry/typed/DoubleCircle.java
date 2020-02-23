package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.DoubleArithmetic;
import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;

/**
 * class which holds a circle a in 2d area with center &amp; radius
 * storing {@link Double}
 */
public class DoubleCircle extends Circle<Double> {
    // region constructors

    /**
     * @see Circle#Circle(AbstractArithmetic)
     */
    public DoubleCircle() {
        super(DoubleArithmetic.getInstance());
    }

    /**
     * @param center center point of circle
     * @see Circle#Circle(AbstractArithmetic, Point)
     */
    public DoubleCircle(Point<Double> center) {
        super(DoubleArithmetic.getInstance(), center);
    }

    /**
     * @param radius radius of circle
     * @see Circle#Circle(AbstractArithmetic, Number)
     */
    public DoubleCircle(double radius) {
        super(DoubleArithmetic.getInstance(), radius);
    }

    /**
     * @param center center point of circle
     * @param point  point on circle
     * @see Circle#Circle(AbstractArithmetic, Point, Point)
     */
    public DoubleCircle(Point<Double> center, Point<Double> point) {
        super(DoubleArithmetic.getInstance(), center, point);
    }

    /**
     * @param center center point of circle
     * @param radius radius of circle
     * @see Circle#Circle(AbstractArithmetic, Point, Number)
     */
    public DoubleCircle(Point<Double> center, double radius) {
        super(DoubleArithmetic.getInstance(), center, radius);
    }

    // endregion
}
