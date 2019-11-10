package io.rala.math.geometry;

import io.rala.math.utils.Copyable;

import java.util.Objects;

/**
 * class which holds a line in a 2d area with m &amp; b<br>
 * <code>y=m*x+b</code>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Line implements Copyable<Line> {
    // region attributes

    private double m;
    private double b;

    // endregion

    // region constructor

    /**
     * creates a line with given slope/gradient and y-intercept
     *
     * @param m slope/gradient of line
     * @param b y-intercept of line
     */
    public Line(double m, double b) {
        this.m = m;
        this.b = b;
    }

    // endregion

    // region getter and setter

    /**
     * @return m value of line
     */
    public double getM() {
        return m;
    }

    /**
     * @param m new m value of line
     */
    public void setM(double m) {
        this.m = m;
    }

    /**
     * @return b value of line
     */
    public double getB() {
        return b;
    }

    /**
     * @param b new b value of line
     */
    public void setB(double b) {
        this.b = b;
    }

    // endregion

    // region calculateX and calculateY

    /**
     * calculates x value of provided y value
     *
     * @param y y value to get x value
     * @return x=(y-b)/m
     */
    public double calculateX(double y) {
        return (y - getB()) / getM();
    }

    /**
     * calculates y value of provided x value
     *
     * @param x x value to get y value
     * @return y=m*x+b
     */
    public double calculateY(double x) {
        return getM() * x + getB();
    }

    // endregion

    // region copy

    @Override
    public Line copy() {
        return new Line(getM(), getB());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Double.compare(line.getM(), getM()) == 0 &&
            Double.compare(line.getB(), getB()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getM(), getB());
    }

    @Override
    public String toString() {
        return "y=" + getM() + "*x+" + getB();
    }

    // endregion
}
