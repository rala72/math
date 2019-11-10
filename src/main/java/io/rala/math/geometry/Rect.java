package io.rala.math.geometry;

import io.rala.math.utils.Copyable;

import java.util.Objects;

/**
 * class which holds a rect in 2d area with point a, b &amp; size
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Rect implements Copyable<Rect> {
    // region attributes

    private Point a;
    private Point b;
    private double size;

    // endregion

    // region constructors

    /**
     * calls {@link #Rect(Point, Point, double)}
     * with {@link Point#Point()}
     * and {@link Point#Point(double, double)}
     * where <code>x=width</code> and <code>y=0</code>
     *
     * @param height height of rect
     * @param width  width of rect
     */
    public Rect(double height, double width) {
        this(new Point(), new Point(width, 0), height);
    }

    /**
     * creates rect with point a, b and size
     *
     * @param a    a of rect
     * @param b    b of rect
     * @param size height of rect
     */
    public Rect(Point a, Point b, double size) {
        this.a = a;
        this.b = b;
        this.size = size;
    }

    // endregion

    // region getter and setter

    /**
     * @return point of rect
     */
    public Point getA() {
        return a;
    }

    /**
     * @param a new point of rect
     */
    public void setA(Point a) {
        this.a = a;
    }

    /**
     * @return b of rect
     */
    public Point getB() {
        return b;
    }

    /**
     * @param b new b of rect
     */
    public void setB(Point b) {
        this.b = b;
    }

    /**
     * @return height of rect
     */
    public double getSize() {
        return size;
    }

    /**
     * @param size new height of rect
     */
    public void setSize(double size) {
        this.size = size;
    }

    // endregion

    // region vertexes

    /**
     * @return {@link #getA()}
     */
    public Point vertexA() {
        return getA();
    }

    /**
     * @return {@link #getB()}
     */
    public Point vertexB() {
        return getB();
    }

    /**
     * @return {@link #vertexB()}<code>+size</code>
     */
    public Point vertexC() {
        return new Point(vertexB().getX(), vertexB().getY() + getSize());
    }

    /**
     * @return {@link #vertexA()}<code>+size</code>
     */
    public Point vertexD() {
        return new Point(vertexA().getX(), vertexA().getY() + getSize());
    }

    // endregion

    // region height, width and diagonale

    /**
     * @return lower length of the rect
     */
    public double height() {
        return Math.min(Math.abs(getSize()), new LineSegment(getA(), getB()).length());
    }

    /**
     * @return larger length of the rect
     */
    public double width() {
        return Math.max(Math.abs(getSize()), new LineSegment(getA(), getB()).length());
    }

    /**
     * @return <code>sqrt(w^2+h^2)</code>
     */
    public double diagonale() {
        return Math.sqrt(Math.pow(height(), 2) + Math.pow(width(), 2));
    }

    // endregion

    // region area and circumference

    /**
     * @return <code>h*w</code>
     */
    public double area() {
        return height() * width();
    }

    /**
     * @return <code>2*(h+w)</code>
     */
    public double circumference() {
        return 2 * (height() + width());
    }

    // endregion

    // region isSquare

    /**
     * @return <code>true</code> if {@link #height()} and {@link #width()} are equal
     */
    public boolean isSquare() {
        return height() == width();
    }

    // endregion

    // region move and copy

    /**
     * @param vector vector to use for movement
     * @return new rect moved by given vector
     */
    public Rect move(Vector vector) {
        return new Rect(getA().move(vector), getB().move(vector), getSize());
    }

    @Override
    public Rect copy() {
        return new Rect(getA().copy(), getB().copy(), getSize());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rect rect = (Rect) o;
        return Objects.equals(getA(), rect.getA()) &&
            Objects.equals(getB(), rect.getB()) &&
            Double.compare(rect.getSize(), getSize()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getA(), getB(), getSize());
    }

    @Override
    public String toString() {
        return getA() + " " + getB() + " " + getSize();
    }

    // endregion
}
