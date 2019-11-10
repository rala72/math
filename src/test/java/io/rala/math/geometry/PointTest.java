package io.rala.math.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PointTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertPoint(new Point());
    }

    @Test
    void constructorWithXYParameter() {
        assertPoint(new Point(1), 1);
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertPoint(new Point(2, 2), 2);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertPoint(new Point(2, 3), 2, 3);
    }

    @Test
    void createAndSetX() {
        Point point = new Point();
        assertPoint(point);
        point.setX(1);
        assertPoint(point, 1, 0);
    }

    @Test
    void createAndSetY() {
        Point point = new Point();
        assertPoint(point);
        point.setY(2);
        assertPoint(point, 0, 2);
    }

    @Test
    void createAndSetXY() {
        Point point = new Point();
        assertPoint(point);
        point.setXY(3);
        assertPoint(point, 3, 3);
    }

    // endregion

    // region move, rotate and copy

    @Test
    void moveOfPointWithXYWithXY() {
        Assertions.assertEquals(new Point(1, 1), new Point().move(1));
    }

    @Test
    void moveOfPointWithXYWithXAndY() {
        Assertions.assertEquals(new Point(1, 1), new Point().move(1, 1));
    }

    @Test
    void moveOfPointWithXYWithVector() {
        Assertions.assertEquals(new Point(1, 1), new Point().move(new Vector(1)));
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiHalf() {
        assertPoint(new Point(1, 2).rotate(Math.PI / 2),
            -2, 1.0000000000000002
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPi() {
        assertPoint(new Point(1, 2).rotate(Math.PI),
            -1.0000000000000002, -1.9999999999999998
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithPiThreeHalf() {
        assertPoint(new Point(1, 2).rotate(Math.PI * 3 / 2),
            1.9999999999999998, -1.0000000000000004
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithoutCenterWithTwoPi() {
        assertPoint(new Point(1, 2).rotate(Math.PI * 2),
            1.0000000000000004, 1.9999999999999998
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiHalf() {
        assertPoint(new Point(1, 2).rotate(new Point(1), Math.PI / 2),
            0, 1
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPi() {
        assertPoint(new Point(1, 2).rotate(new Point(1), Math.PI),
            0.9999999999999999, 0
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithPiThreeHalf() {
        assertPoint(new Point(1, 2).rotate(new Point(1), Math.PI * 3 / 2),
            2, 0.9999999999999998
        );
    }

    @Test
    void rotateOfPointWithX1Y2WithCenterXY1WithTwoPi() {
        assertPoint(new Point(1, 2).rotate(new Point(1), Math.PI * 2),
            1.0000000000000002, 2
        );
    }

    @Test
    void copyOfPointWithXY() {
        Point point = new Point(2, 3);
        Assertions.assertEquals(point, point.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfPointWithXY() {
        Point point = new Point(2, 3);
        Assertions.assertEquals(
            point,
            new Point(2, 3)
        );
        Assertions.assertNotEquals(
            point,
            new Point(3, 2)
        );
    }

    @Test
    void hashCodeOfPointWithXY() {
        Assertions.assertEquals(
            525249,
            new Point(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfPointWithXY() {
        Point point = new Point(2, 3);
        Assertions.assertEquals("2.0|3.0", point.toString());
    }

    @Test
    void compareToOfPointWithXY() {
        Point point = new Point(2, 3);
        Assertions.assertEquals(
            0, point.compareTo(new Point(2, 3))
        );
        Assertions.assertEquals(
            -1, point.compareTo(new Point(3, 1))
        );
        Assertions.assertEquals(
            1, point.compareTo(new Point(2, 1))
        );
    }

    // endregion


    // region assert

    private static void assertPoint(Point point) {
        assertPoint(point, 0);
    }

    private static void assertPoint(Point point, double xy) {
        assertPoint(point, xy, xy);
    }

    private static void assertPoint(Point point, double x, double y) {
        Assertions.assertEquals(x, point.getX(), "x is invalid");
        Assertions.assertEquals(y, point.getY(), "y is invalid");
    }

    // endregion
}
