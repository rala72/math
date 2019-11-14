package io.rala.math.geometry;

import io.rala.math.utils.Copyable;
import io.rala.math.utils.Movable;
import io.rala.math.utils.Rotatable;
import io.rala.math.utils.Validatable;

import java.util.Objects;

/**
 * class which holds a point in a 2d area with x &amp; y
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Point implements Validatable, Movable<Point>, Rotatable<Point>,
    Copyable<Point>, Comparable<Point> {
    // region attributes

    private double x;
    private double y;

    // endregion

    // region constructors

    /**
     * calls {@link #Point(double)} with <code>0</code>
     *
     * @see #Point(double)
     * @see #Point(double, double)
     */
    public Point() {
        this(0);
    }

    /**
     * calls {@link #Point(double, double)} with the value at x and y
     *
     * @param xy value to be used in {@link #Point(double, double)} at x and y
     * @see #Point()
     * @see #Point(double, double)
     */
    public Point(double xy) {
        this(xy, xy);
    }

    /**
     * creates a point with given x and y values
     *
     * @param x x value of point
     * @param y y value of point
     * @see #Point()
     * @see #Point(double)
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // endregion

    // region getter and setter

    /**
     * @return x value of point
     */
    public double getX() {
        return x;
    }

    /**
     * @param x new x value of point
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return y value of point
     */
    public double getY() {
        return y;
    }

    /**
     * @param y new y value of point
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @param xy new x and y value of point
     */
    public void setXY(double xy) {
        setX(xy);
        setY(xy);
    }

    // endregion

    // region isValid, move, rotate and copy

    @Override
    public boolean isValid() {
        return Double.isFinite(getX()) && Double.isFinite(getY());
    }

    @Override
    public Point move(Vector vector) {
        return new Point(getX() + vector.getX(), getY() + vector.getY());
    }

    @Override
    public Point rotate(Point center, double phi) {
        Vector vector = new Vector(center.getX(), center.getY());
        Point moved = move(vector.inverse());
        double newX = Math.cos(phi) * moved.getX() - Math.sin(phi) * moved.getY();
        double newY = Math.sin(phi) * moved.getX() + Math.cos(phi) * moved.getY();
        return new Point(newX, newY).move(vector);
    }

    @Override
    public Point copy() {
        return new Point(getX(), getY());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.getX(), getX()) == 0 &&
            Double.compare(point.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    @Override
    public String toString() {
        return getX() + "|" + getY();
    }

    @Override
    public int compareTo(Point o) {
        int diffX = (int) Math.ceil(getX() - o.getX());
        if (diffX != 0) return diffX;
        return (int) Math.ceil(getY() - o.getY());
    }

    // endregion
}
