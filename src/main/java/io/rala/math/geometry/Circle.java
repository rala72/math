package io.rala.math.geometry;

import io.rala.math.utils.Copyable;
import io.rala.math.utils.Movable;
import io.rala.math.utils.Rotatable;
import io.rala.math.utils.Validatable;

import java.io.Serializable;
import java.util.Objects;

/**
 * class which holds a circle a in 2d area with center &amp; radius
 */
public class Circle implements Validatable, Movable<Circle>, Rotatable<Circle>,
    Copyable<Circle>, Comparable<Circle>, Serializable {
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
     * calls {@link #Circle(Point, double)} with radius {@code 1}
     *
     * @param center center point of circle
     */
    public Circle(Point center) {
        this(center, 1);
    }

    /**
     * calls {@link #Circle(Point, double)} with {@link Point#Point()}
     *
     * @param radius radius of circle
     */
    public Circle(double radius) {
        this(new Point(), radius);
    }

    /**
     * creates a new circle with given center and radius
     *
     * @param center center point of circle
     * @param radius radius of circle
     */
    public Circle(Point center, double radius) {
        setCenter(center);
        setRadius(radius);
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

    /**
     * @return {@link #getRadius()}{@code *2}
     */
    public double getDiameter() {
        return getRadius() * 2;
    }

    /**
     * calls {@link #setRadius(double)} with {@code diameter/2}
     *
     * @param diameter new diameter of circle
     * @see #setRadius(double)
     */
    public void setDiameter(double diameter) {
        setRadius(diameter / 2);
    }

    // endregion

    // region area and circumference

    /**
     * @return {@code &pi;*r^2}
     */
    public double area() {
        return Math.PI * Math.pow(getRadius(), 2);
    }

    /**
     * @return {@code 2*&pi;*r}
     */
    public double circumference() {
        return 2 * Math.PI * getRadius();
    }

    // endregion

    // region isUnitCircle

    /**
     * @return {@code true} if {@link #getRadius()} is 1
     */
    public boolean isUnitCircle() {
        return getRadius() == 1;
    }

    // endregion

    // region isValid, move, rotate and copy

    @Override
    public boolean isValid() {
        return getCenter().isValid() && Double.isFinite(getRadius()) &&
            0 <= getRadius();
    }

    @Override
    public Circle move(Vector vector) {
        return new Circle(getCenter().move(vector), getRadius());
    }

    @Override
    public Circle rotate(Point center, double phi) {
        return new Circle(getCenter().rotate(center, phi), getRadius());
    }

    @Override
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

    @Override
    public int compareTo(Circle o) {
        int compare = Double.compare(getRadius(), o.getRadius());
        if (compare != 0) return compare;
        return getCenter().compareTo(o.getCenter());
    }

    // endregion
}
