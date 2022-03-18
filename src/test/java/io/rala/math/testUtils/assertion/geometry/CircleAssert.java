package io.rala.math.testUtils.assertion.geometry;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.geometry.Circle;
import io.rala.math.geometry.Point;
import io.rala.math.testUtils.assertion.core.AbstractComparableAssert;
import org.assertj.core.api.AbstractDoubleAssert;
import org.assertj.core.data.Offset;

import static io.rala.math.testUtils.assertion.utils.OffsetUtils.doubleOffset;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class CircleAssert<T extends Number> extends AbstractComparableAssert<T, CircleAssert<T>, Circle<T>> {
    public CircleAssert(Circle<T> tCircle) {
        super(tCircle, CircleAssert.class);
    }

    /**
     * @see Circle#getCenter()
     */
    public PointAssert<T> getCenter() {
        isNotNull();
        return new PointAssert<>(actual.getCenter());
    }

    /**
     * @see Circle#getCenter()
     * @see PointAssert#hasZeroXY()
     */
    public CircleAssert<T> hasCenterInZero() {
        isNotNull();
        getCenter().as("circle has center").hasZeroXY();
        return this;
    }

    /**
     * @see Circle#getCenter()
     */
    public CircleAssert<T> hasCenter(Point<T> center) {
        isNotNull();
        getCenter().as("circle has center").isEqualTo(center);
        return this;
    }

    /**
     * @see Circle#getCenter()
     * @see PointAssert#isCloseTo(Point)
     */
    public CircleAssert<T> hasCenterCloseTo(Point<T> center) {
        isNotNull();
        getCenter().as("circle has center").isCloseTo(center);
        return this;
    }

    /**
     * @see #hasRadius(Number)
     */
    public CircleAssert<T> hasRadiusOne() {
        isNotNull().hasArithmetic();
        return hasRadius(getArithmetic().one());
    }

    /**
     * @see Circle#getRadius()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public CircleAssert<T> hasRadius(T radius) {
        isNotNull();
        assertThatNoArgumentIsNull(radius);

        return assertNumberAsDouble(actual.getRadius(), doubleAssert ->
            doubleAssert.as("circle has radius")
                .isCloseTo(radius.doubleValue(), doubleOffset()));
    }

    /**
     * @see Circle#getDiameter()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public CircleAssert<T> hasDiameter(T diameter) {
        isNotNull();
        assertThatNoArgumentIsNull(diameter);

        return assertNumberAsDouble(actual.getDiameter(), doubleAssert ->
            doubleAssert.as("circle has diameter")
                .isCloseTo(diameter.doubleValue(), doubleOffset()));
    }

    /**
     * @see Circle#area()
     */
    public CircleAssert<T> hasArea(T area) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.area()), objectAssert ->
            objectAssert.as("circle has area").isEqualTo(area));
    }

    /**
     * @see Circle#area()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public CircleAssert<T> hasAreaCloseTo(T area) {
        isNotNull();
        assertThatNoArgumentIsNull(area);

        return assertNumberAsDouble(actual.area(), doubleAssert ->
            doubleAssert.as("circle has area")
                .isCloseTo(area.doubleValue(), doubleOffset()));
    }

    /**
     * @see Circle#circumference()
     */
    public CircleAssert<T> hasCircumference(T circumference) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.circumference()), objectAssert ->
            objectAssert.as("circle has circumference").isEqualTo(circumference));
    }

    /**
     * @see Circle#isUnitCircle()
     */
    public CircleAssert<T> isUnitCircle() {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.isUnitCircle()), booleanAssert ->
            booleanAssert.as("circle is unitCircle").isTrue());
    }

    /**
     * @see Circle#isUnitCircle()
     */
    public CircleAssert<T> isNoUnitCircle() {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.isUnitCircle()), booleanAssert ->
            booleanAssert.as("circle is no unitCircle").isFalse());
    }

    @Override
    protected AbstractArithmetic<T> getArithmetic() {
        isNotNull();
        return actual.getArithmetic();
    }
}
