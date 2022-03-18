package io.rala.math.testUtils.assertion;

import io.rala.math.geometry.*;
import io.rala.math.testUtils.assertion.geometry.*;

import java.math.MathContext;

/**
 * assertions for {@link io.rala.math.geometry} package
 */
@SuppressWarnings("unused")
public class GeometryAssertions {
    public static final MathContext CONTEXT = MathContext.DECIMAL64;

    private GeometryAssertions() {
    }

    public static <T extends Number> CircleAssert<T> assertThatCircle(Circle<T> circle) {
        return new CircleAssert<>(circle);
    }

    public static <T extends Number> LineSegmentAssert<T> assertThatLineSegment(LineSegment<T> lineSegment) {
        return new LineSegmentAssert<>(lineSegment);
    }

    public static <T extends Number> LineAssert<T> assertThatLine(Line<T> line) {
        return new LineAssert<>(line);
    }

    public static <T extends Number> PointAssert<T> assertThatPoint(Point<T> point) {
        return new PointAssert<>(point);
    }

    public static <T extends Number> RectAssert<T> assertThatRect(Rect<T> rect) {
        return new RectAssert<>(rect);
    }

    public static <T extends Number> TriangleAssert<T> assertThatTriangle(Triangle<T> triangle) {
        return new TriangleAssert<>(triangle);
    }

    public static <T extends Number> VectorAssert<T> assertThatVector(Vector<T> vector) {
        return new VectorAssert<>(vector);
    }
}
