package io.rala.math.testUtils.assertion.geometry;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.geometry.Point;
import io.rala.math.geometry.Triangle;
import io.rala.math.testUtils.assertion.core.AbstractComparableAssert;
import org.assertj.core.api.AbstractDoubleAssert;
import org.assertj.core.data.Offset;

import static io.rala.math.testUtils.assertion.utils.OffsetUtils.doubleOffset;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class TriangleAssert<T extends Number>
    extends AbstractComparableAssert<T, TriangleAssert<T>, Triangle<T>> {

    public TriangleAssert(Triangle<T> tTriangle) {
        super(tTriangle, TriangleAssert.class);
    }

    /**
     * @see Triangle#getA()
     */
    public PointAssert<T> getA() {
        isNotNull();
        return new PointAssert<>(actual.getA());
    }

    /**
     * @see Triangle#getB()
     */
    public PointAssert<T> getB() {
        isNotNull();
        return new PointAssert<>(actual.getB());
    }

    /**
     * @see Triangle#getC()
     */
    public PointAssert<T> getC() {
        isNotNull();
        return new PointAssert<>(actual.getC());
    }

    /**
     * @see Triangle#getA()
     */
    public TriangleAssert<T> hasA(Point<T> point) {
        isNotNull();
        getA().as("triangle has a").isEqualTo(point);
        return this;
    }

    /**
     * @see Triangle#getB()
     * @see PointAssert#isCloseTo(Point)
     */
    public TriangleAssert<T> hasACloseTo(Point<T> point) {
        isNotNull();
        getA().as("triangle has a").isCloseTo(point);
        return this;
    }

    /**
     * @see Triangle#getA()
     * @see PointAssert#hasZeroXY()
     */
    public TriangleAssert<T> hasAWithZeroXY() {
        isNotNull();
        getA().as("triangle has a").hasZeroXY();
        return this;
    }

    /**
     * @see Triangle#getB()
     */
    public TriangleAssert<T> hasB(Point<T> point) {
        isNotNull();
        getB().as("triangle has b").isEqualTo(point);
        return this;
    }

    /**
     * @see Triangle#getB()
     * @see PointAssert#isCloseTo(Point)
     */
    public TriangleAssert<T> hasBCloseTo(Point<T> point) {
        isNotNull();
        getB().as("triangle has b").isCloseTo(point);
        return this;
    }

    /**
     * @see Triangle#getB()
     * @see PointAssert#hasZeroXY()
     */
    public TriangleAssert<T> hasBWithZeroXY() {
        isNotNull();
        getB().as("triangle has b").hasZeroXY();
        return this;
    }

    /**
     * @see Triangle#getC()
     */
    public TriangleAssert<T> hasC(Point<T> point) {
        isNotNull();
        getC().as("triangle has c").isEqualTo(point);
        return this;
    }

    /**
     * @see Triangle#getC()
     * @see PointAssert#isCloseTo(Point)
     */
    public TriangleAssert<T> hasCCloseTo(Point<T> point) {
        isNotNull();
        getC().as("triangle has c").isCloseTo(point);
        return this;
    }

    /**
     * @see Triangle#getC()
     * @see PointAssert#hasZeroXY()
     */
    public TriangleAssert<T> hasCWithZeroXY() {
        isNotNull();
        getC().as("triangle has c").hasZeroXY();
        return this;
    }

    /**
     * @see Triangle#angleAlpha()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public TriangleAssert<T> hasAngleAlpha(T angle) {
        isNotNull();
        assertThatNoArgumentIsNull(angle);

        return assertNumberAsDouble(actual.angleAlpha(), doubleAssert ->
            doubleAssert.as("triangle has angle alpha")
                .isCloseTo(angle.doubleValue(), doubleOffset()));
    }

    /**
     * @see Triangle#angleBeta()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public TriangleAssert<T> hasAngleBeta(T angle) {
        isNotNull();
        assertThatNoArgumentIsNull(angle);

        return assertNumberAsDouble(actual.angleBeta(), doubleAssert ->
            doubleAssert.as("triangle has angle beta")
                .isCloseTo(angle.doubleValue(), doubleOffset()));
    }

    /**
     * @see Triangle#angleGamma()
     * @see AbstractDoubleAssert#isCloseTo(Double, Offset)
     */
    public TriangleAssert<T> hasAngleGamma(T angle) {
        isNotNull();
        assertThatNoArgumentIsNull(angle);

        return assertNumberAsDouble(actual.angleGamma(), doubleAssert ->
            doubleAssert.as("triangle has angle gamma")
                .isCloseTo(angle.doubleValue(), doubleOffset()));
    }

    /**
     * @see Triangle#area()
     */
    public TriangleAssert<T> hasArea(T area) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.area()), objectAssert ->
            objectAssert.as("triangle has area").isEqualTo(area));
    }

    /**
     * @see Triangle#area()
     */
    public TriangleAssert<T> hasAreaCloseTo(T area) {
        isNotNull();
        assertThatNoArgumentIsNull(area);

        return assertNumberAsDouble(actual.area(), doubleAssert ->
            doubleAssert.as("triangle has area")
                .isCloseTo(area.doubleValue(), doubleOffset()));
    }

    /**
     * @see Triangle#circumference()
     */
    public TriangleAssert<T> hasCircumference(T circumference) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.circumference()), objectAssert ->
            objectAssert.as("triangle has circumference").isEqualTo(circumference));
    }

    @Override
    protected AbstractArithmetic<T> getArithmetic() {
        isNotNull();
        return actual.getArithmetic();
    }
}
