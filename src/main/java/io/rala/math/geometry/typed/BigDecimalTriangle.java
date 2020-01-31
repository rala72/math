package io.rala.math.geometry.typed;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Triangle;

import java.math.BigDecimal;

/**
 * class which holds a triangle in a 2d area with points a, b &amp; c
 * storing {@link BigDecimal}
 */
public class BigDecimalTriangle extends Triangle<BigDecimal> {
    // region constructors

    /**
     * @param a a of triangle
     * @param b b of triangle
     * @param c c of triangle
     * @see Triangle#Triangle(AbstractArithmetic, Point, Point, Point)
     */
    public BigDecimalTriangle(
        Point<BigDecimal> a, Point<BigDecimal> b, Point<BigDecimal> c
    ) {
        super(new BigDecimalArithmetic(), a, b, c);
    }

    // endregion
}
