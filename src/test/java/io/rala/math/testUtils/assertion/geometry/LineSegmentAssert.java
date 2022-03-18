package io.rala.math.testUtils.assertion.geometry;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.geometry.LineSegment;
import io.rala.math.geometry.Point;
import io.rala.math.testUtils.assertion.core.AbstractComparableAssert;

import static io.rala.math.testUtils.assertion.utils.OffsetUtils.doubleOffset;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class LineSegmentAssert<T extends Number>
    extends AbstractComparableAssert<T, LineSegmentAssert<T>, LineSegment<T>> {

    public LineSegmentAssert(LineSegment<T> tLineSegment) {
        super(tLineSegment, LineSegmentAssert.class);
    }

    /**
     * @see LineSegment#getA()
     */
    public PointAssert<T> getA() {
        isNotNull();
        return new PointAssert<>(actual.getA());
    }

    /**
     * @see LineSegment#getB()
     */
    public PointAssert<T> getB() {
        isNotNull();
        return new PointAssert<>(actual.getB());
    }

    /**
     * @see LineSegment#getA()
     */
    public LineSegmentAssert<T> hasA(Point<T> point) {
        isNotNull();
        getA().as("lineSegment has a").isEqualTo(point);
        return this;
    }

    /**
     * @see LineSegment#getA()
     * @see PointAssert#isCloseTo(Point)
     */
    public LineSegmentAssert<T> hasACloseTo(Point<T> point) {
        isNotNull();
        getA().as("lineSegment has a").isCloseTo(point);
        return this;
    }

    /**
     * @see LineSegment#getA()
     * @see PointAssert#hasZeroXY()
     */
    public LineSegmentAssert<T> hasAWithZeroXY() {
        isNotNull();
        getA().as("lineSegment has a with zero xy").hasZeroXY();
        return this;
    }

    /**
     * @see LineSegment#getB()
     */
    public LineSegmentAssert<T> hasB(Point<T> point) {
        isNotNull();
        getB().as("lineSegment has b").isEqualTo(point);
        return this;
    }

    /**
     * @see LineSegment#getB()
     * @see PointAssert#isCloseTo(Point)
     */
    public LineSegmentAssert<T> hasBCloseTo(Point<T> point) {
        isNotNull();
        getB().as("lineSegment has b").isCloseTo(point);
        return this;
    }

    /**
     * @see LineSegment#getB()
     * @see PointAssert#isCloseTo(Point)
     */
    public LineSegmentAssert<T> hasBWithZeroXY() {
        isNotNull();
        getB().as("lineSegment has b with zero xy").hasZeroXY();
        return this;
    }

    /**
     * @see LineSegment#length()
     */
    public LineSegmentAssert<T> hasLength(T length) {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.length()), objectAssert ->
            objectAssert.as("lineSegment has length").isEqualTo(length));
    }

    /**
     * @see LineSegment#length()
     */
    public LineSegmentAssert<T> hasLengthCloseTo(T length) {
        isNotNull();
        assertThatNoArgumentIsNull(length);

        return assertNumberAsDouble(actual.length(), doubleAssert ->
            doubleAssert.as("lineSegment has length")
                .isCloseTo(length.doubleValue(), doubleOffset()));
    }

    @Override
    protected AbstractArithmetic<T> getArithmetic() {
        isNotNull();
        return actual.getArithmetic();
    }
}
