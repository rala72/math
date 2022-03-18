package io.rala.math.testUtils.assertion.geometry;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Rect;
import io.rala.math.testUtils.assertion.core.AbstractComparableAssert;

import static io.rala.math.testUtils.assertion.utils.OffsetUtils.doubleOffset;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class RectAssert<T extends Number> extends AbstractComparableAssert<T, RectAssert<T>, Rect<T>> {
    public RectAssert(Rect<T> tRect) {
        super(tRect, RectAssert.class);
    }

    /**
     * @see Rect#getA()
     */
    public PointAssert<T> getA() {
        isNotNull();
        return new PointAssert<>(actual.getA());
    }

    /**
     * @see Rect#getB()
     */
    public PointAssert<T> getB() {
        isNotNull();
        return new PointAssert<>(actual.getB());
    }

    /**
     * @see Rect#getA()
     */
    public RectAssert<T> hasA(Point<T> point) {
        isNotNull();
        getA().as("rect has a").isEqualTo(point);
        return this;
    }

    /**
     * @see Rect#getA()
     * @see PointAssert#isCloseTo(Point)
     */
    public RectAssert<T> hasACloseTo(Point<T> point) {
        isNotNull();
        getA().as("rect has a").isCloseTo(point);
        return this;
    }

    /**
     * @see Rect#getA()
     * @see PointAssert#hasZeroXY()
     */
    public RectAssert<T> hasAWithZeroXY() {
        isNotNull();
        getA().as("rect has a").hasZeroXY();
        return this;
    }

    /**
     * @see Rect#getB()
     */
    public RectAssert<T> hasB(Point<T> point) {
        isNotNull();
        getB().as("rect has b").isEqualTo(point);
        return this;
    }

    /**
     * @see Rect#getB()
     * @see PointAssert#isCloseTo(Point)
     */
    public RectAssert<T> hasBCloseTo(Point<T> point) {
        isNotNull();
        getB().as("rect has b").isCloseTo(point);
        return this;
    }

    /**
     * @see Rect#getB()
     * @see PointAssert#hasZeroXY()
     */
    public RectAssert<T> hasBWithZeroXY() {
        isNotNull();
        getB().as("rect has a").hasZeroXY();
        return this;
    }

    /**
     * @see Rect#getSize()
     */
    public RectAssert<T> hasSize(T size) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.getSize()), objectAssert ->
            objectAssert.as("rect has size").isEqualTo(size));
    }

    /**
     * @see Rect#height()
     */
    public RectAssert<T> hasHeight(T height) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.height()), objectAssert ->
            objectAssert.as("rect has height").isEqualTo(height));
    }

    /**
     * @see Rect#width()
     */
    public RectAssert<T> hasWidth(T width) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.width()), objectAssert ->
            objectAssert.as("rect has width").isEqualTo(width));
    }

    /**
     * @see Rect#diagonale()
     */
    public RectAssert<T> hasDiagonale(T diagonale) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.diagonale()), objectAssert ->
            objectAssert.as("rect has diagonale").isEqualTo(diagonale));
    }

    /**
     * @see Rect#area()
     */
    public RectAssert<T> hasArea(T area) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.area()), objectAssert ->
            objectAssert.as("rect has area").isEqualTo(area));
    }

    /**
     * @see Rect#area()
     */
    public RectAssert<T> hasAreaCloseTo(T area) {
        isNotNull();
        assertThatNoArgumentIsNull(area);

        return assertNumberAsDouble(actual.area(), doubleAssert ->
            doubleAssert.as("rect has area")
                .isCloseTo(area.doubleValue(), doubleOffset()));
    }

    /**
     * @see Rect#circumference()
     */
    public RectAssert<T> hasCircumference(T circumference) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.circumference()), objectAssert ->
            objectAssert.as("rect has circumference").isEqualTo(circumference));
    }

    /**
     * @see Rect#isSquare()
     */
    public RectAssert<T> isSquare() {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.isSquare()), booleanAssert ->
            booleanAssert.as("rect is square").isTrue());
    }

    /**
     * @see Rect#isSquare()
     */
    public RectAssert<T> isNoSquare() {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.isSquare()), booleanAssert ->
            booleanAssert.as("rect is no square").isFalse());
    }

    @Override
    protected AbstractArithmetic<T> getArithmetic() {
        isNotNull();
        return actual.getArithmetic();
    }
}
