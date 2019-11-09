package io.rala.math.geometry;

import java.util.Objects;

/**
 * circle in 2d area
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Circle {
    // region attributes

    private Point center;
    private double radius;

    // endregion

    // region constructors

    /**
     * calls {@link #Circle(Point)} with {@link Point#Point()}
     */
    public Circle() {
        this(new Point());
    }

    /**
     * calls {@link #Circle(Point, double)} with radius <code>1</code>
     *
     * @param center center point of circle
     */
    public Circle(Point center) {
        this(center, 1);
    }

    /**
     * creates a new circle with given center and radius
     *
     * @param center center point of circle
     * @param radius radius of circle
     */
    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    // endregion

    // region getter and setter

    /**
     * @return center of circle
     */
    public Point getCenter() {
        return center;
    }

    /**
     * @param center new center of circle
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * @return radius of circle
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radius new radius of circle
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    // endregion

    // region copy

    /**
     * @return new circle with same values
     */
    public Circle copy() {
        return new Circle(getCenter().copy(), getRadius());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.getRadius(), getRadius()) == 0 &&
            Objects.equals(getCenter(), circle.getCenter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCenter(), getRadius());
    }

    @Override
    public String toString() {
        return getCenter() + " " + getRadius();
    }

    // endregion
}
