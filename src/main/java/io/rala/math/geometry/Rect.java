package io.rala.math.geometry;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.Movable;
import io.rala.math.utils.Rotatable;
import io.rala.math.utils.Validatable;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * class which holds a rect in 2d area with point a, b &amp; size
 *
 * @param <T> number class
 * @since 1.0.0
 */
public class Rect<T extends Number> implements Validatable,
    Movable<T, Rect<T>>, Rotatable<T, Rect<T>>,
    Copyable<Rect<T>>, Comparable<Rect<T>>, Serializable {

    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private Point<T> a;
    private Point<T> b;
    private T size;

    // endregion

    // region constructors

    /**
     * calls {@link #Rect(AbstractArithmetic, Point, Point, Number)}
     * with {@link Point#Point(AbstractArithmetic)}
     * and {@link Point#Point(AbstractArithmetic, Number, Number)}
     * where {@code x=width} and {@code y=0}
     *
     * @param arithmetic arithmetic for calculations
     * @param height     height of rect
     * @param width      width of rect
     * @since 1.0.0
     */
    public Rect(AbstractArithmetic<T> arithmetic, T height, T width) {
        this(arithmetic,
            new Point<>(arithmetic),
            new Point<>(arithmetic, width, arithmetic.zero()),
            height
        );
    }

    /**
     * creates rect with point a, b and size
     *
     * @param arithmetic arithmetic for calculations
     * @param a          a of rect
     * @param b          b of rect
     * @param size       height of rect
     * @since 1.0.0
     */
    public Rect(AbstractArithmetic<T> arithmetic, Point<T> a, Point<T> b, T size) {
        this.arithmetic = arithmetic;
        setA(a);
        setB(b);
        setSize(size);
    }

    // endregion

    // region getter and setter

    /**
     * @return stored arithmetic
     * @since 1.0.0
     */
    public AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

    /**
     * @return point of rect
     * @since 1.0.0
     */
    public Point<T> getA() {
        return a;
    }

    /**
     * @param a new point of rect
     * @since 1.0.0
     */
    public void setA(Point<T> a) {
        this.a = a;
    }

    /**
     * @return b of rect
     * @since 1.0.0
     */
    public Point<T> getB() {
        return b;
    }

    /**
     * @param b new b of rect
     * @since 1.0.0
     */
    public void setB(Point<T> b) {
        this.b = b;
    }

    /**
     * @return height of rect
     * @since 1.0.0
     */
    public T getSize() {
        return size;
    }

    /**
     * @param size new height of rect
     * @since 1.0.0
     */
    public void setSize(T size) {
        this.size = size;
    }

    // endregion

    // region vertexes

    /**
     * @return {@link #getA()}
     * @since 1.0.0
     */
    public Point<T> vertexA() {
        return getA();
    }

    /**
     * @return {@link #getB()}
     * @since 1.0.0
     */
    public Point<T> vertexB() {
        return getB();
    }

    /**
     * @return {@link #vertexB()}{@code +size}
     * @since 1.0.0
     */
    public Point<T> vertexC() {
        return new Point<>(getArithmetic(), vertexB().getX(),
            getArithmetic().sum(vertexB().getY(), getSize())
        );
    }

    /**
     * @return {@link #vertexA()}{@code +size}
     * @since 1.0.0
     */
    public Point<T> vertexD() {
        return new Point<>(getArithmetic(), vertexA().getX(),
            getArithmetic().sum(vertexA().getY(), getSize())
        );
    }

    // endregion

    // region height, width and diagonale

    /**
     * @return lower length of the rect
     * @since 1.0.0
     */
    public T height() {
        return getArithmetic().min(getArithmetic().absolute(getSize()),
            new LineSegment<>(getArithmetic(), getA(), getB()).length());
    }

    /**
     * @return larger length of the rect
     * @since 1.0.0
     */
    public T width() {
        return getArithmetic().max(getArithmetic().absolute(getSize()),
            new LineSegment<>(getArithmetic(), getA(), getB()).length());
    }

    /**
     * @return {@code sqrt(w^2+h^2)}
     * @since 1.0.0
     */
    public T diagonale() {
        return getArithmetic().root2(
            getArithmetic().sum(
                getArithmetic().power(height(), 2),
                getArithmetic().power(width(), 2)
            )
        );
    }

    // endregion

    // region area and circumference

    /**
     * @return {@code h*w}
     * @since 1.0.0
     */
    public T area() {
        return getArithmetic().product(height(), width());
    }

    /**
     * @return {@code 2*(h+w)}
     * @since 1.0.0
     */
    public T circumference() {
        return getArithmetic().product(
            getArithmetic().fromInt(2),
            getArithmetic().sum(height(), width())
        );
    }

    // endregion

    // region circumCircle

    /**
     * @return circum circle of rect
     * @since 1.0.0
     */
    public Circle<T> circumCircle() {
        return new Circle<>(getArithmetic(), circumCirclePoint(), circumCircleRadius());
    }

    /**
     * @return {@link #diagonale()}{@code /2}
     * @since 1.0.0
     */
    protected T circumCircleRadius() {
        return getArithmetic().quotient(diagonale(), getArithmetic().fromInt(2));
    }

    /**
     * @return intersection of AC and BD diagonals
     * @since 1.0.0
     */
    protected Point<T> circumCirclePoint() {
        LineSegment<T> ac = new LineSegment<>(getArithmetic(), vertexA(), vertexC());
        LineSegment<T> bd = new LineSegment<>(getArithmetic(), vertexB(), vertexD());
        return ac.toLine().intersection(bd.toLine());
    }

    // endregion

    // region isSquare

    /**
     * @return {@code true} if {@link #height()} and {@link #width()} are equal
     * @since 1.0.0
     */
    public boolean isSquare() {
        return getArithmetic().isEqual(height(), width());
    }

    // endregion

    // region map, isValid, move, rotate and copy

    /**
     * @param arithmetic arithmetic for calculations
     * @param map        mapping function to convert current values to new one
     * @param <NT>       new number class
     * @return mapped rect
     * @since 1.0.0
     */
    public <NT extends Number> Rect<NT> map(
        AbstractArithmetic<NT> arithmetic, Function<T, NT> map
    ) {
        return new Rect<>(
            arithmetic,
            getA().map(arithmetic, map),
            getB().map(arithmetic, map),
            map.apply(getSize())
        );
    }

    @Override
    public boolean isValid() {
        return getA().isValid() && getB().isValid() && !getA().equals(getB()) &&
            getArithmetic().isFinite(getSize()) && !getArithmetic().isZero(getSize());
    }

    @Override
    public Rect<T> move(T x, T y) {
        return new Rect<>(getArithmetic(),
            getA().move(x, y), getB().move(x, y), getSize()
        );
    }

    @Override
    public Rect<T> rotate(T phi) {
        return rotate(new Point<>(getArithmetic()), phi);
    }

    @Override
    public Rect<T> rotate(Point<T> center, T phi) {
        return new Rect<>(
            getArithmetic(), getA().rotate(center, phi),
            getB().rotate(center, phi),
            getSize()
        );
    }

    @Override
    public Rect<T> copy() {
        return new Rect<>(getArithmetic(), getA().copy(), getB().copy(), getSize());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rect<?>)) return false;
        Rect<?> rect = (Rect<?>) o;
        return Objects.equals(getA(), rect.getA()) &&
            Objects.equals(getB(), rect.getB()) &&
            Objects.equals(getSize(), rect.getSize());
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
    public int compareTo(Rect<T> o) {
        int compare = getArithmetic().compare(getSize(), o.getSize());
        if (compare != 0) return compare;
        Point<T> min = List.of(getA(), getB()).stream()
            .min(Point::compareTo).orElse(getA());
        Point<T> minO = List.of(o.getA(), o.getB()).stream()
            .min(Point::compareTo).orElse(o.getA());
        int a = min.compareTo(minO);
        if (a != 0) return a;
        Point<T> max = List.of(getA(), getB()).stream()
            .max(Point::compareTo).orElse(getB());
        Point<T> maxO = List.of(o.getA(), o.getB()).stream()
            .max(Point::compareTo).orElse(o.getB());
        return max.compareTo(maxO);
    }

    // endregion
}
