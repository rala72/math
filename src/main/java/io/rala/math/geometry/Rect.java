package io.rala.math.geometry;

import io.rala.math.utils.Copyable;
import io.rala.math.utils.Movable;
import io.rala.math.utils.Rotatable;
import io.rala.math.utils.Validatable;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * class which holds a rect in 2d area with point a, b &amp; size
 */
public class Rect implements Validatable, Movable<Rect>, Rotatable<Rect>,
    Copyable<Rect>, Comparable<Rect>, Serializable {
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
     * where {@code x=width} and {@code y=0}
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
        setA(a);
        setB(b);
        setSize(size);
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
     * @return {@link #vertexB()}{@code +size}
     */
    public Point vertexC() {
        return new Point(vertexB().getX(), vertexB().getY() + getSize());
    }

    /**
     * @return {@link #vertexA()}{@code +size}
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
     * @return {@code sqrt(w^2+h^2)}
     */
    public double diagonale() {
        return Math.sqrt(Math.pow(height(), 2) + Math.pow(width(), 2));
    }

    // endregion

    // region area and circumference

    /**
     * @return {@code h*w}
     */
    public double area() {
        return height() * width();
    }

    /**
     * @return {@code 2*(h+w)}
     */
    public double circumference() {
        return 2 * (height() + width());
    }

    // endregion

    // region circumCircle

    /**
     * @return circum circle of rect
     */
    public Circle circumCircle() {
        return new Circle(circumCirclePoint(), circumCircleRadius());
    }

    /**
     * @return {@link #diagonale()}{@code /2}
     */
    protected double circumCircleRadius() {
        return diagonale() / 2;
    }

    /**
     * @return intersection of AC and BD diagonals
     */
    protected Point circumCirclePoint() {
        LineSegment ac = new LineSegment(vertexA(), vertexC());
        LineSegment bd = new LineSegment(vertexB(), vertexD());
        return ac.toLine().intersection(bd.toLine());
    }

    // endregion

    // region isSquare

    /**
     * @return {@code true} if {@link #height()} and {@link #width()} are equal
     */
    public boolean isSquare() {
        return height() == width();
    }

    // endregion

    // region isValid, move, rotate and copy

    @Override
    public boolean isValid() {
        return getA().isValid() && getB().isValid() && !getA().equals(getB()) &&
            Double.isFinite(getSize()) && getSize() != 0;
    }

    @Override
    public Rect move(Vector vector) {
        return new Rect(getA().move(vector), getB().move(vector), getSize());
    }

    @Override
    public Rect rotate(Point center, double phi) {
        return new Rect(
            getA().rotate(center, phi),
            getB().rotate(center, phi),
            getSize()
        );
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

    @Override
    public int compareTo(Rect o) {
        int compare = Double.compare(getSize(), o.getSize());
        if (compare != 0) return compare;
        Point min = List.of(getA(), getB()).stream().min(Point::compareTo).orElse(getA());
        Point minO = List.of(o.getA(), o.getB()).stream().min(Point::compareTo).orElse(o.getA());
        int a = min.compareTo(minO);
        if (a != 0) return a;
        Point max = List.of(getA(), getB()).stream().max(Point::compareTo).orElse(getB());
        Point maxO = List.of(o.getA(), o.getB()).stream().max(Point::compareTo).orElse(o.getB());
        return max.compareTo(maxO);
    }

    // endregion
}
