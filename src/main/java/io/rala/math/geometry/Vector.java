package io.rala.math.geometry;

import io.rala.math.utils.Copyable;
import io.rala.math.utils.Rotatable;
import io.rala.math.utils.Validatable;

import java.util.Objects;

/**
 * class which holds a vector in a 2d area with x &amp; y
 */
public class Vector implements Validatable, Rotatable<Vector>,
    Copyable<Vector>, Comparable<Vector> {
    // region attributes

    private double x;
    private double y;

    // endregion

    // region constructors

    /**
     * calls {@link #Vector(double)} with <code>0</code>
     *
     * @see #Vector(double)
     * @see #Vector(double, double)
     */
    public Vector() {
        this(0);
    }

    /**
     * calls {@link #Vector(double, double)} with the value at x and y
     *
     * @param xy value to be used in {@link #Vector(double, double)} at x and y
     * @see #Vector()
     * @see #Vector(double, double)
     */
    public Vector(double xy) {
        this(xy, xy);
    }

    /**
     * creates a vector with given x and y values
     *
     * @param x x value of vector
     * @param y y value of vector
     * @see #Vector()
     * @see #Vector(double)
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // endregion

    // region getter and setter

    /**
     * @return x value of vector
     */
    public double getX() {
        return x;
    }

    /**
     * @param x new x value of vector
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return y value of vector
     */
    public double getY() {
        return y;
    }

    /**
     * @param y new y value of vector
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @param xy new x and y value of vector
     */
    public void setXY(double xy) {
        setX(xy);
        setY(xy);
    }

    // endregion

    // region length, add, subtract and multiply

    /**
     * @return length of vector based on pythagoras
     */
    public double length() {
        return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));
    }

    /**
     * calls {@link #add(double, double)} with given value
     *
     * @param xy x and y value to add
     * @return new vector with sum of current and given parameters
     * @see #add(double, double)
     * @see #add(Vector)
     */
    public Vector add(double xy) {
        return add(xy, xy);
    }

    /**
     * calls {@link #add(Vector)} with given values
     *
     * @param x x value to add
     * @param y y value to add
     * @return new vector with sum of current and given parameters
     * @see #add(double)
     * @see #add(Vector)
     */
    public Vector add(double x, double y) {
        return add(new Vector(x, y));
    }

    /**
     * @param vector vector value to add
     * @return new vector with sum of current and given vector
     * @see #add(double)
     * @see #add(double, double)
     */
    public Vector add(Vector vector) {
        return new Vector(getX() + vector.getX(), getY() + vector.getY());
    }

    /**
     * calls {@link #subtract(double, double)} with given value
     *
     * @param xy x and y value to subtract
     * @return new vector with difference of current and given parameters
     * @see #subtract(double, double)
     * @see #subtract(Vector)
     */
    public Vector subtract(double xy) {
        return subtract(xy, xy);
    }

    /**
     * calls {@link #subtract(Vector)} with given values
     *
     * @param x x value to subtract
     * @param y y value to subtract
     * @return new vector with difference of current and given parameters
     * @see #subtract(double)
     * @see #subtract(Vector)
     */
    public Vector subtract(double x, double y) {
        return subtract(new Vector(x, y));
    }

    /**
     * @param vector vector value to subtract
     * @return new vector with difference of current and given vector
     * @see #subtract(double)
     * @see #subtract(double, double)
     */
    public Vector subtract(Vector vector) {
        return add(vector.inverse());
    }

    /**
     * @param i value to multiply with x &amp; y
     * @return new vector with multiplied x &amp; y values
     */
    public Vector multiply(double i) {
        return new Vector(getX() * i, getY() * i);
    }

    // endregion

    // region inverse

    /**
     * @return new vector which has inverse x &amp; y values
     * @see #inverseX()
     * @see #inverseY()
     */
    public Vector inverse() {
        return inverseX().inverseY();
    }

    /**
     * @return new vector which has inverse x value
     * @see #inverse()
     * @see #inverseY()
     */
    public Vector inverseX() {
        return new Vector(-getX(), getY());
    }

    /**
     * @return new vector which has inverse y value
     * @see #inverse()
     * @see #inverseX()
     */
    public Vector inverseY() {
        return new Vector(getX(), -getY());
    }

    // endregion

    // region normal and normalized

    /**
     * @return new normal vector rotated left
     */
    public Vector normalLeft() {
        return new Vector(-getY(), getX());
    }

    /**
     * @return new normal vector rotated right
     */
    public Vector normalRight() {
        return new Vector(getY(), -getX());
    }

    /**
     * @return new normalized vector
     */
    public Vector normalized() {
        return new Vector(getX() / length(), getY() / length());
    }

    // endregion

    // region scalarProduct and angle

    /**
     * @param vector to calc scalar product
     * @return scalar product
     */
    public double scalarProduct(Vector vector) {
        return getX() * vector.getX() + getY() * vector.getY();
    }

    /**
     * @param vector vector to calc angle between
     * @return angle in <code>rad</code> between vectors
     */
    public double angle(Vector vector) {
        return Math.acos(scalarProduct(vector) / (length() * vector.length()));
    }

    // endregion

    // region round

    /**
     * @return new vector with absolute values
     * @see Math#abs(double)
     */
    public Vector absolute() {
        return new Vector(Math.abs(getX()), Math.abs(getY()));
    }

    /**
     * @return new vector with {@link Math#round(double)}-values
     * @see #floor()
     * @see #ceil()
     */
    public Vector round() {
        return new Vector(Math.round(getX()), Math.round(getY()));
    }

    /**
     * @return new vector with {@link Math#floor(double)}-values
     * @see #round()
     * @see #ceil()
     */
    public Vector floor() {
        return new Vector(Math.floor(getX()), Math.floor(getY()));
    }

    /**
     * @return new vector with {@link Math#ceil(double)}-values
     * @see #round()
     * @see #floor()
     */
    public Vector ceil() {
        return new Vector(Math.ceil(getX()), Math.ceil(getY()));
    }

    /**
     * @return new vector with truncated-values
     * @see #round()
     * @see #floor()
     * @see #ceil()
     */
    public Vector truncate() {
        return new Vector((long) getX(), (long) getY());
    }

    // endregion

    // region isZeroVector

    /**
     * @return <code>true</code> if both params casted to <code>int</code> are zero
     */
    public boolean isZeroVector() {
        return (int) getX() == 0 && (int) getY() == 0;
    }

    // endregion

    // region isValid, rotate and copy

    @Override
    public boolean isValid() {
        return Double.isFinite(getX()) && Double.isFinite(getY());
    }

    @Override
    public Vector rotate(Point center, double phi) {
        double newX = Math.cos(phi) * getX() - Math.sin(phi) * getY();
        double newY = Math.sin(phi) * getX() + Math.cos(phi) * getY();
        return new Vector(newX, newY);
    }

    @Override
    public Vector copy() {
        return new Vector(getX(), getY());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;
        Vector vector = (Vector) o;
        return getX() == vector.getX() && getY() == vector.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return getX() + ":" + getY();
    }

    @Override
    public int compareTo(Vector o) {
        int diffX = (int) Math.ceil(getX() - o.getX());
        if (diffX != 0) return diffX;
        return (int) Math.ceil(getY() - o.getY());
    }

    // endregion
}
