package io.rala.math.geometry;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.utils.Copyable;
import io.rala.math.utils.Movable;
import io.rala.math.utils.Rotatable;
import io.rala.math.utils.Validatable;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * class which holds a triangle in a 2d area with points a, b &amp; c
 *
 * @param <T> number class
 */
public class Triangle<T extends Number> implements Validatable,
    Movable<T, Triangle<T>>, Rotatable<T, Triangle<T>>,
    Copyable<Triangle<T>>, Comparable<Triangle<T>>, Serializable {
    // region attributes

    private final AbstractArithmetic<T> arithmetic;
    private Point<T> a;
    private Point<T> b;
    private Point<T> c;

    // endregion

    // region constructors

    /**
     * creates a new triangle with given a, b and c
     *
     * @param arithmetic arithmetic for calculations
     * @param a          a of triangle
     * @param b          b of triangle
     * @param c          c of triangle
     */
    public Triangle(AbstractArithmetic<T> arithmetic, Point<T> a, Point<T> b, Point<T> c) {
        this.arithmetic = arithmetic;
        setA(a);
        setB(b);
        setC(c);
    }

    // endregion

    // region getter and setter

    /**
     * @return stored arithmetic
     */
    protected AbstractArithmetic<T> getArithmetic() {
        return arithmetic;
    }

    /**
     * @return a of triangle
     */
    public Point<T> getA() {
        return a;
    }

    /**
     * @param a new a of triangle
     */
    public void setA(Point<T> a) {
        this.a = a;
    }

    /**
     * @return b of triangle
     */
    public Point<T> getB() {
        return b;
    }

    /**
     * @param b new b of triangle
     */
    public void setB(Point<T> b) {
        this.b = b;
    }

    /**
     * @return c of triangle
     */
    public Point<T> getC() {
        return c;
    }

    /**
     * @param c new c of triangle
     */
    public void setC(Point<T> c) {
        this.c = c;
    }

    // endregion

    // region edges and altitudes

    /**
     * @return line segment from b to c
     */
    public LineSegment<T> edgeA() {
        return new LineSegment<>(getArithmetic(), getB(), getC());
    }

    /**
     * @return line segment from a to c
     */
    public LineSegment<T> edgeB() {
        return new LineSegment<>(getArithmetic(), getA(), getC());
    }

    /**
     * @return line segment from a to b
     */
    public LineSegment<T> edgeC() {
        return new LineSegment<>(getArithmetic(), getA(), getB());
    }

    /**
     * @return line segment of altitude {@code a} starting at {@link #getA()}
     */
    public LineSegment<T> altitudeA() {
        return getAltitude(edgeA().toLine(), getA());
    }

    /**
     * @return line segment of altitude {@code b} starting at {@link #getB()}
     */
    public LineSegment<T> altitudeB() {
        return getAltitude(edgeB().toLine(), getB());
    }

    /**
     * @return line segment of altitude {@code c} starting at {@link #getC()}
     */
    public LineSegment<T> altitudeC() {
        return getAltitude(edgeC().toLine(), getC());
    }

    /**
     * @param edge  edge to get altitude from
     * @param point point to get altitude from
     * @return altitude starting at {@code point} and ending at intersection with {@code edge}
     */
    protected LineSegment<T> getAltitude(Line<T> edge, Point<T> point) {
        Line<T> altitudeLine = edge.normal(point);
        return new LineSegment<>(getArithmetic(), point, altitudeLine.intersection(edge));
    }

    // endregion

    // region angles

    /**
     * calculates angle using law of cosines
     *
     * @return angle in {@code rad} at point {@code A}
     */
    public T angleAlpha() {
        T dividend = getArithmetic().difference(
            getArithmetic().power(edgeA().length(), 2),
            getArithmetic().sum(
                getArithmetic().power(edgeB().length(), 2),
                getArithmetic().power(edgeC().length(), 2)
            )
        );
        T divisor = getArithmetic().product(
            getArithmetic().fromInt(-2),
            edgeB().length(), edgeC().length()
        );
        return getArithmetic().acos(
            getArithmetic().quotient(dividend, divisor)
        );
    }

    /**
     * calculates angle using law of cosines
     *
     * @return angle in {@code rad} at point {@code B}
     */
    public T angleBeta() {
        T dividend = getArithmetic().difference(
            getArithmetic().power(edgeB().length(), 2),
            getArithmetic().sum(
                getArithmetic().power(edgeA().length(), 2),
                getArithmetic().power(edgeC().length(), 2)
            )
        );
        T divisor = getArithmetic().product(
            getArithmetic().fromInt(-2),
            edgeA().length(), edgeC().length()
        );
        return getArithmetic().acos(
            getArithmetic().quotient(dividend, divisor)
        );
    }

    /**
     * calculates angle using law of cosines
     *
     * @return angle in {@code rad} at point {@code C}
     */
    public T angleGamma() {
        T dividend = getArithmetic().difference(
            getArithmetic().power(edgeC().length(), 2),
            getArithmetic().sum(
                getArithmetic().power(edgeA().length(), 2),
                getArithmetic().power(edgeB().length(), 2)
            )
        );
        T divisor = getArithmetic().product(
            getArithmetic().fromInt(-2),
            edgeA().length(), edgeB().length()
        );
        return getArithmetic().acos(
            getArithmetic().quotient(dividend, divisor)
        );
    }

    // endregion

    // region area and circumference

    /**
     * @return {@code sqrt(s*(s-a)*(s-b)*(s-c))}
     */
    public T area() {
        T s = getArithmetic().quotient(
            circumference(),
            getArithmetic().fromInt(2)
        );
        return getArithmetic().root2(
            getArithmetic().product(s,
                getArithmetic().product(
                    getArithmetic().difference(s, edgeA().length()),
                    getArithmetic().difference(s, edgeB().length()),
                    getArithmetic().difference(s, edgeC().length())
                )
            )
        );
    }

    /**
     * @return {@code a+b+c}
     */
    public T circumference() {
        return getArithmetic().sum(
            edgeA().length(), edgeB().length(), edgeC().length()
        );
    }

    // endregion

    // region centroid and orthoCenter

    /**
     * @return {@code (A+B+C)/3}
     */
    public Point<T> centroid() {
        return new Point<>(getArithmetic(),
            getArithmetic().quotient(
                getArithmetic().sum(
                    getA().getX(), getB().getX(), getC().getX()
                ),
                getArithmetic().fromInt(3)
            ),
            getArithmetic().quotient(
                getArithmetic().sum(
                    getA().getY(), getB().getY(), getC().getY()
                ),
                getArithmetic().fromInt(3)
            )
        );
    }

    /**
     * @return intersection from {@link #altitudeA()} and {@link #altitudeB()}
     * @see Line#intersection(Line)
     */
    public Point<T> orthoCenter() {
        return altitudeA().toLine().intersection(altitudeB().toLine());
    }

    // endregion

    // region circumCircle and inCircle

    /**
     * @return circum circle of triangle
     */
    public Circle<T> circumCircle() {
        return new Circle<>(getArithmetic(), circumCirclePoint(), circumCircleRadius());
    }

    /**
     * @return in circle of triangle
     */
    public Circle<T> inCircle() {
        return new Circle<>(getArithmetic(), inCirclePoint(), inCircleRadius());
    }

    /**
     * @return {@code (a*b*c)/A}
     */
    protected T circumCircleRadius() {
        return getArithmetic().quotient(
            getArithmetic().product(
                edgeA().length(),
                edgeB().length(),
                edgeC().length()
            ), getArithmetic().product(
                getArithmetic().fromInt(4), area()
            )
        );
    }

    /**
     * @return {@code ( (a2*(By-Cy)+b2*(Cy-Ay)+c2*(Ay-By))/d,
     * (a2*(Bx-Cx)+b2*(Cx-Ax)+c2*(Ax-Bx))/d )}
     * where {@code N2=Nx^2+Ny^2} with {@code N in [ABC]}
     * and {@code d=Ax*(By-Cy)+Bx*(Cy-Ay)+Cx*(Ay-By)}
     */
    protected Point<T> circumCirclePoint() {
        T d = getArithmetic().product(getArithmetic().fromInt(2),
            getArithmetic().sum(
                getArithmetic().product(
                    getA().getX(),
                    getArithmetic().difference(getB().getY(), getC().getY())
                ),
                getArithmetic().product(
                    getB().getX(),
                    getArithmetic().difference(getC().getY(), getA().getY())
                ),
                getArithmetic().product(
                    getC().getX(),
                    getArithmetic().difference(getA().getY(), getB().getY())
                )
            )
        );
        T a2 = getArithmetic().sum(
            getArithmetic().power(getA().getX(), 2),
            getArithmetic().power(getA().getY(), 2)
        );
        T b2 = getArithmetic().sum(
            getArithmetic().power(getB().getX(), 2),
            getArithmetic().power(getB().getY(), 2)
        );
        T c2 = getArithmetic().sum(
            getArithmetic().power(getC().getX(), 2),
            getArithmetic().power(getC().getY(), 2)
        );
        return new Point<>(getArithmetic(),
            getArithmetic().quotient(
                getArithmetic().sum(
                    getArithmetic().product(a2,
                        getArithmetic().difference(getB().getY(), getC().getY())
                    ),
                    getArithmetic().product(b2,
                        getArithmetic().difference(getC().getY(), getA().getY())
                    ),
                    getArithmetic().product(c2,
                        getArithmetic().difference(getA().getY(), getB().getY())
                    )
                ), d
            ),
            getArithmetic().quotient(
                getArithmetic().sum(
                    getArithmetic().product(a2,
                        getArithmetic().difference(getB().getX(), getC().getX())
                    ),
                    getArithmetic().product(b2,
                        getArithmetic().difference(getC().getX(), getA().getX())
                    ),
                    getArithmetic().product(c2,
                        getArithmetic().difference(getA().getX(), getB().getX())
                    )
                ), d
            )
        );
    }

    /**
     * @return {@code A/(r/2)}
     */
    protected T inCircleRadius() {
        return getArithmetic().quotient(area(),
            getArithmetic().quotient(
                circumference(),
                getArithmetic().fromInt(2)
            )
        );
    }

    /**
     * @return {@code ( (a*xA+b*xB+c*xC)/p, (a*yA+b*yB+c*yC)/p )} where {@code p=a+b+c}
     */
    protected Point<T> inCirclePoint() {
        T p = circumference();
        return new Point<>(getArithmetic(),
            getArithmetic().quotient(
                getArithmetic().sum(
                    getArithmetic().product(edgeA().length(), getA().getX()),
                    getArithmetic().product(edgeB().length(), getB().getX()),
                    getArithmetic().product(edgeC().length(), getC().getX())
                ), p
            ),
            getArithmetic().quotient(
                getArithmetic().sum(
                    getArithmetic().product(edgeA().length(), getA().getY()),
                    getArithmetic().product(edgeB().length(), getB().getY()),
                    getArithmetic().product(edgeC().length(), getC().getY())
                ), p
            )
        );
    }

    // endregion

    // region isValid, move, rotate and copy

    @Override
    public boolean isValid() {
        return getA().isValid() && getB().isValid() && getC().isValid() &&
            getArithmetic().compare(edgeA().length(),
                getArithmetic().sum(edgeB().length(), edgeC().length())
            ) < 0 &&
            getArithmetic().compare(edgeB().length(),
                getArithmetic().sum(edgeA().length(), edgeC().length())
            ) < 0 &&
            getArithmetic().compare(edgeB().length(),
                getArithmetic().sum(edgeA().length(), edgeB().length())
            ) < 0;
    }

    @Override
    public Triangle<T> move(T x, T y) {
        return move(new Vector<>(getArithmetic(), x, y));
    }

    @Override
    public Triangle<T> move(Vector<T> vector) {
        return new Triangle<>(getArithmetic(), getA().move(vector), getB().move(vector), getC().move(vector));
    }

    @Override
    public Triangle<T> rotate(T phi) {
        return rotate(new Point<>(getArithmetic()), phi);
    }

    @Override
    public Triangle<T> rotate(Point<T> center, T phi) {
        return new Triangle<>(
            getArithmetic(), getA().rotate(center, phi),
            getB().rotate(center, phi),
            getC().rotate(center, phi)
        );
    }

    @Override
    public Triangle<T> copy() {
        return new Triangle<>(getArithmetic(), getA().copy(), getB().copy(), getC().copy());
    }

    // endregion

    // region override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Triangle<?>)) return false;
        Triangle<?> triangle = (Triangle<?>) o;
        return Objects.equals(getA(), triangle.getA()) &&
            Objects.equals(getB(), triangle.getB()) &&
            Objects.equals(getC(), triangle.getC());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getA(), getB(), getC());
    }

    @Override
    public String toString() {
        return getA() + " " + getB() + " " + getC();
    }

    @Override
    public int compareTo(Triangle<T> o) {
        int compare = getArithmetic().compare(area(), o.area());
        if (compare != 0) return compare;
        List<Point<T>> s = List.of(getA(), getB(), getC())
            .stream().sorted().collect(Collectors.toList());
        List<Point<T>> sO = List.of(o.getA(), o.getB(), o.getC())
            .stream().sorted().collect(Collectors.toList());
        for (int i = 0; i < s.size(); i++) {
            Point<T> p = s.get(i);
            Point<T> pO = sO.get(i);
            int c = p.compareTo(pO);
            if (c != 0) return c;
        }
        return 0;
    }

    // endregion
}
