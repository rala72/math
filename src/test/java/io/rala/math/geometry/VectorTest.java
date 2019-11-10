package io.rala.math.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VectorTest {
    // region constructors, getter and setter

    @Test
    void testConstructors() {
        assertVector(new Vector());
        assertVector(new Vector(1), 1);
        assertVector(new Vector(2, 2), 2);
        assertVector(new Vector(2, 3), 2, 3);
    }

    @Test
    void testGetterAndSetter() {
        Vector vector = new Vector();
        assertVector(vector);
        vector.setX(1);
        assertVector(vector, 1, 0);
        vector.setY(2);
        assertVector(vector, 1, 2);
        vector.setXY(3);
        assertVector(vector, 3);
    }

    // endregion

    // region length, add, subtract and multiply

    @Test
    void testLength() {
        Assertions.assertEquals(0, new Vector().length());
        Assertions.assertEquals(1.4142135623730951, new Vector(1).length());
        Assertions.assertEquals(1, new Vector(1, 0).length());
    }

    @Test
    void testAdd() {
        Vector vector2 = new Vector(2, 2);
        Assertions.assertEquals(vector2, new Vector().add(2));
        Assertions.assertEquals(vector2, new Vector(1).add(1, 1));
        Assertions.assertEquals(vector2, new Vector(1, 0).add(1, 2));
    }

    @Test
    void testSubtract() {
        Vector vector2 = new Vector(2, 2);
        Assertions.assertEquals(new Vector(), vector2.subtract(2));
        Assertions.assertEquals(new Vector(1), vector2.subtract(1, 1));
        Assertions.assertEquals(new Vector(1, 0), vector2.subtract(1, 2));
    }

    @Test
    void testMultiply() {
        Assertions.assertEquals(new Vector(), new Vector().multiply(1));
        Assertions.assertEquals(new Vector(), new Vector(1).multiply(0));
        Assertions.assertEquals(new Vector(1), new Vector(1).multiply(1));
        Assertions.assertEquals(new Vector(-2, -1), new Vector(2, 1).multiply(-1));
    }

    // endregion

    // region inverse

    @Test
    void testInverse() {
        Assertions.assertEquals(new Vector(-1, 2), new Vector(1, 2).inverseX());
        Assertions.assertEquals(new Vector(1, -2), new Vector(1, 2).inverseY());
        Assertions.assertEquals(new Vector(-1, -2), new Vector(1, 2).inverse());
    }

    // endregion

    // region rotate, normal and normalized

    @Test
    void testRotate() {
        Vector vector = new Vector(1, 2);
        assertVector(vector.rotate(Math.PI / 2), -2, 1.0000000000000002);
        assertVector(vector.rotate(Math.PI), -1.0000000000000002, -1.9999999999999998);
        assertVector(vector.rotate(Math.PI * 3 / 2), 1.9999999999999998, -1.0000000000000004);
        assertVector(vector.rotate(Math.PI * 2), 1.0000000000000004, 1.9999999999999998);
    }

    @Test
    void testNormal() {
        Vector vector = new Vector(1, 2);
        Assertions.assertEquals(new Vector(-2, 1), vector.normalLeft());
        Assertions.assertEquals(new Vector(2, -1), vector.normalRight());
    }

    @Test
    void testNormalized() {
        Vector vector0 = new Vector().normalized();
        assertVector(vector0);
        Assertions.assertEquals(0, vector0.length());

        Vector vector1 = new Vector(1).normalized();
        assertVector(vector1, 0.7071067811865475);
        Assertions.assertEquals(1, Math.round(vector1.length()));

        Vector vector2 = new Vector(1, 2).normalized();
        assertVector(vector2, 0.4472135954999579, 0.8944271909999159);
        Assertions.assertEquals(1, Math.round(vector2.length()));
    }

    // endregion

    // region scalarProduct and angle

    @Test
    void testScalarProduct() {
        Assertions.assertEquals(0, new Vector(0).scalarProduct(new Vector(1)));
        Assertions.assertEquals(2, new Vector(1).scalarProduct(new Vector(1)));
        Assertions.assertEquals(6, new Vector(2).scalarProduct(new Vector(1, 2)));
    }

    @Test
    void testAngle() {
        Assertions.assertEquals(Math.PI / 2,
            new Vector(0, 1).angle(new Vector(1, 0))
        );
        Assertions.assertEquals(0.7853981633974484, // Math.PI / 4
            new Vector(0, 1).angle(new Vector(1, 1))
        );
    }

    // endregion

    // region round

    @Test
    void testAbsolute() {
        Assertions.assertEquals(new Vector(1, 2), new Vector(-1, 2).absolute());
        Assertions.assertEquals(new Vector(1, 2), new Vector(-1, -2).absolute());
        Assertions.assertEquals(new Vector(1, 2), new Vector(1, -2).absolute());
        Assertions.assertEquals(new Vector(1, 2), new Vector(1, 2).absolute());
    }

    @Test
    void testRound() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1, -2).round());
        Assertions.assertEquals(new Vector(1, -2), new Vector(1.2, -2.2).round());
        Assertions.assertEquals(new Vector(1, -2), new Vector(1.49, -2.49).round());
        Assertions.assertEquals(new Vector(2, -2), new Vector(1.5, -2.5).round());
        Assertions.assertEquals(new Vector(2, -3), new Vector(1.7, -2.7).round());
    }

    @Test
    void testFloor() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1, -2).floor());
        Assertions.assertEquals(new Vector(1, -3), new Vector(1.2, -2.2).floor());
        Assertions.assertEquals(new Vector(1, -3), new Vector(1.49, -2.49).floor());
        Assertions.assertEquals(new Vector(1, -3), new Vector(1.5, -2.5).floor());
        Assertions.assertEquals(new Vector(1, -3), new Vector(1.7, -2.7).floor());
    }

    @Test
    void testCeil() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1, -2).ceil());
        Assertions.assertEquals(new Vector(2, -2), new Vector(1.2, -2.2).ceil());
        Assertions.assertEquals(new Vector(2, -2), new Vector(1.49, -2.49).ceil());
        Assertions.assertEquals(new Vector(2, -2), new Vector(1.5, -2.5).ceil());
        Assertions.assertEquals(new Vector(2, -2), new Vector(1.7, -2.7).ceil());
    }

    @Test
    void testTruncate() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1, -2).truncate());
        Assertions.assertEquals(new Vector(1, -2), new Vector(1.2, -2.2).truncate());
        Assertions.assertEquals(new Vector(1, -2), new Vector(1.49, -2.49).truncate());
        Assertions.assertEquals(new Vector(1, -2), new Vector(1.5, -2.5).truncate());
        Assertions.assertEquals(new Vector(1, -2), new Vector(1.7, -2.7).truncate());
    }

    // endregion

    // region isZeroVector and copy

    @Test
    void testIsZeroVector() {
        Assertions.assertTrue(new Vector().isZeroVector());
        Assertions.assertFalse(new Vector(1).isZeroVector());
    }

    @Test
    void testCopy() {
        Vector vector = new Vector(2, 3);
        Assertions.assertEquals(vector, vector.copy());
    }

    // endregion

    // region override

    @Test
    void testEquals() {
        Vector vector = new Vector(2, 3);
        Assertions.assertEquals(
            vector,
            new Vector(2, 3)
        );
        Assertions.assertNotEquals(
            vector,
            new Vector(3, 2)
        );
    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(
            525249,
            new Vector(2, 3).hashCode()
        );
    }

    @Test
    void testToString() {
        Vector vector = new Vector(2, 3);
        Assertions.assertEquals("2.0:3.0", vector.toString());
    }

    @Test
    void testCompareTo() {
        Vector vector = new Vector(2, 3);
        Assertions.assertEquals(
            0, vector.compareTo(new Vector(2, 3))
        );
        Assertions.assertEquals(
            -1,
            0, vector.compareTo(new Vector(2, 1))
        );
        Assertions.assertEquals(
            1,
            0, vector.compareTo(new Vector(1, 0))
        );
    }

    // endregion


    // region assert

    private static void assertVector(Vector vector) {
        assertVector(vector, 0);
    }

    private static void assertVector(Vector vector, double xy) {
        assertVector(vector, xy, xy);
    }

    private static void assertVector(Vector vector, double x, double y) {
        Assertions.assertEquals(x, vector.getX());
        Assertions.assertEquals(y, vector.getY());
    }

    // endregion
}
