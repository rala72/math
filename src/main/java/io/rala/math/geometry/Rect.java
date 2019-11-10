package io.rala.math.geometry;

import java.util.Objects;

/**
 * rect in 2d area
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Rect {
    // region attributes

    private Point point;
    private double height;
    private double width;
    // TODO: rotated rects

    // endregion

    // region constructors

    /**
     * calls {@link #Rect(Point, double, double)} with {@link Point#Point()}
     *
     * @param height height of rect
     * @param width  width of rect
     */
    public Rect(double height, double width) {
        this(new Point(), height, width);
    }

    /**
     * creates rect with point, height and width
     *
     * @param point  point of rect
     * @param height height of rect
     * @param width  width of rect
     */
    public Rect(Point point, double height, double width) {
        this.point = point;
        this.height = height;
        this.width = width;
    }

    // endregion

    // region getter and setter

    /**
     * @return point of rect
     */
    public Point getPoint() {
        return point;
    }

    /**
     * @param point new point of rect
     */
    public void setPoint(Point point) {
        this.point = point;
    }

    /**
     * @return height of rect
     */
    public double getHeight() {
        return height;
    }

    /**
     * @param height new height of rect
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * @return width of rect
     */
    public double getWidth() {
        return width;
    }

    /**
     * @param width new width of rect
     */
    public void setWidth(double width) {
        this.width = width;
    }

    // endregion

    // region area, circumference and diagonale

    /**
     * @return <code>h*w</code>
     */
    public double area() {
        return getHeight() * getWidth();
    }

    /**
     * @return <code>2*(h+w)</code>
     */
    public double circumference() {
        return 2 * (getHeight() + getWidth());
    }

    /**
     * @return <code>sqrt(w^2+h^2)</code>
     */
    public double diagonale() {
        return Math.sqrt(Math.pow(getHeight(), 2) + Math.pow(getWidth(), 2));
    }

    // endregion

    // region copy

    /**
     * @return new rect with same values
     */
    public Rect copy() {
        return new Rect(getPoint().copy(), getHeight(), getWidth());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rect rect = (Rect) o;
        return Double.compare(rect.getHeight(), getHeight()) == 0 &&
            Double.compare(rect.getWidth(), getWidth()) == 0 &&
            Objects.equals(getPoint(), rect.getPoint());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPoint(), getHeight(), getWidth());
    }

    @Override
    public String toString() {
        return getPoint() + " " + getHeight() + "x" + getWidth();
    }

    // endregion
}
