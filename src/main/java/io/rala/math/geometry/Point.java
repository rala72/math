package io.rala.math.geometry;

import java.util.Objects;

/**
 * class which holds a point in a 2d area with x &amp; y
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Point implements Comparable<Point> {
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
        setX(x);
        setY(y);
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
        this.x = !Double.isFinite(x) ? 0 : x;
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
        this.y = !Double.isFinite(y) ? 0 : y;
    }

    /**
     * @param xy new x and y value of point
     */
    public void setXY(double xy) {
        setX(xy);
        setY(xy);
    }

    // endregion

    // region move and copy

    /**
     * @param vector vector to use for movement
     * @return new point moved by given vector
     */
    public Point move(Vector vector) {
        return new Point(getX() + vector.getX(), getY() + vector.getY());
    }

    /**
     * @return new point with same values
     */
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
        return getX() + ":" + getY();
    }

    @Override
    public int compareTo(Point o) {
        int compare = Double.compare(getX(), o.getX());
        if (compare != 0) return compare;
        return Double.compare(getY(), o.getY());
    }

    // endregion
}
