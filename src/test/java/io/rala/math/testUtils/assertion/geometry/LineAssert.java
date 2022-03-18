package io.rala.math.testUtils.assertion.geometry;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.geometry.Line;
import io.rala.math.geometry.Point;
import io.rala.math.testUtils.assertion.core.AbstractComparableAssert;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class LineAssert<T extends Number> extends AbstractComparableAssert<T, LineAssert<T>, Line<T>> {
    public LineAssert(Line<T> tLine) {
        super(tLine, LineAssert.class);
    }

    /**
     * @see Line#getM()
     */
    public LineAssert<T> hasM(T m) {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.getM()), objectAssert ->
            objectAssert.as("line has m").isEqualTo(m));
    }

    /**
     * @see Line#getB()
     */
    public LineAssert<T> hasB(T b) {
        isNotNull();
        assertThatNoArgumentIsNull(b);

        return withConcatenatedMessage(assertThat(actual.getB()), objectAssert ->
            objectAssert.as("line has b").isEqualTo(b));
    }

    /**
     * @see Line#hasIntersection(Line)
     */
    public LineAssert<T> hasIntersection(Line<T> line) {
        isNotNull();
        assertThatNoArgumentIsNull(line);

        return withConcatenatedMessage(assertThat(actual.hasIntersection(line)), booleanAssert ->
            booleanAssert.as("line is has intersection").isTrue());
    }

    /**
     * @see Line#hasIntersection(Line)
     */
    public LineAssert<T> hasNoIntersection(Line<T> line) {
        isNotNull();
        assertThatNoArgumentIsNull(line);

        return withConcatenatedMessage(assertThat(actual.hasIntersection(line)), booleanAssert ->
            booleanAssert.as("line is has no intersection").isFalse());
    }

    /**
     * @see Line#hasPoint(Point)
     */
    public LineAssert<T> hasPoint(Point<T> point) {
        isNotNull();
        assertThatNoArgumentIsNull(point);

        return withConcatenatedMessage(assertThat(actual.hasPoint(point)), booleanAssert ->
            booleanAssert.as("line is has point").isTrue());
    }

    /**
     * @see Line#hasPoint(Point)
     */
    public LineAssert<T> hasNoPoint(Point<T> point) {
        isNotNull();
        assertThatNoArgumentIsNull(point);

        return withConcatenatedMessage(assertThat(actual.hasPoint(point)), booleanAssert ->
            booleanAssert.as("line is has not point").isFalse());
    }

    /**
     * @see Line#isHorizontal()
     */
    public LineAssert<T> isHorizontal() {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.isHorizontal()), booleanAssert ->
            booleanAssert.as("line is horizontal").isTrue());
    }

    /**
     * @see Line#isHorizontal()
     */
    public LineAssert<T> isNotHorizontal() {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.isHorizontal()), booleanAssert ->
            booleanAssert.as("line is not horizontal").isFalse());
    }

    /**
     * @see Line#isVertical()
     */
    public LineAssert<T> isVertical() {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.isVertical()), booleanAssert ->
            booleanAssert.as("line is vertical").isTrue());
    }

    /**
     * @see Line#isVertical()
     */
    public LineAssert<T> isNotVertical() {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.isVertical()), booleanAssert ->
            booleanAssert.as("line is not vertical").isFalse());
    }

    @Override
    protected AbstractArithmetic<T> getArithmetic() {
        isNotNull();
        return actual.getArithmetic();
    }
}
