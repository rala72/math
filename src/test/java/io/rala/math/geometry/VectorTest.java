package io.rala.math.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VectorTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        assertVector(new Vector());
    }

    @Test
    void constructorWithXYParameter() {
        assertVector(new Vector(1), 1);
    }

    @Test
    void constructorWithEqualXYParameter() {
        assertVector(new Vector(2, 2), 2);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        assertVector(new Vector(2, 3), 2, 3);
    }

    @Test
    void createAndSetX() {
        Vector vector = new Vector();
        assertVector(vector);
        vector.setX(1);
        assertVector(vector, 1, 0);
    }

    @Test
    void createAndSetY() {
        Vector vector = new Vector();
        assertVector(vector);
        vector.setY(2);
        assertVector(vector, 0, 2);
    }

    @Test
    void createAndSetXY() {
        Vector vector = new Vector();
        assertVector(vector);
        vector.setXY(3);
        assertVector(vector, 3);
    }

    // endregion

    // region length, add, subtract and multiply

    @Test
    void lengthOfVectorWithoutParameter() {
        Assertions.assertEquals(0, new Vector().length());
    }

    @Test
    void lengthOfVectorXY1() {
        Assertions.assertEquals(1.4142135623730951, new Vector(1).length());
    }

    @Test
    void lengthOfVectorX1Y0() {
        Assertions.assertEquals(1, new Vector(1, 0).length());
    }

    @Test
    void addWithXY() {
        Assertions.assertEquals(new Vector(2, 2), new Vector().add(2));
    }

    @Test
    void addWithXAndY() {
        Assertions.assertEquals(new Vector(2, 2), new Vector(1).add(1, 1));
    }

    @Test
    void addWithVector() {
        Assertions.assertEquals(new Vector(2, 2),
            new Vector(1, 0).add(new Vector(1, 2))
        );
    }

    @Test
    void subtractWithXY() {
        Assertions.assertEquals(new Vector(), new Vector(2, 2).subtract(2));
    }

    @Test
    void subtractWithXAndY() {
        Assertions.assertEquals(new Vector(1), new Vector(2, 2).subtract(1, 1));
    }

    @Test
    void subtractWithVector() {
        Assertions.assertEquals(new Vector(1, 0),
            new Vector(2, 2).subtract(new Vector(1, 2))
        );
    }

    @Test
    void multiplyZeroVectorWith1() {
        Assertions.assertEquals(new Vector(), new Vector().multiply(1));
    }

    @Test
    void multiplyVectorWith0() {
        Assertions.assertEquals(new Vector(), new Vector(1).multiply(0));
    }

    @Test
    void multiplyVectorWith1() {
        Assertions.assertEquals(new Vector(1), new Vector(1).multiply(1));
    }

    @Test
    void multiplyVectorWithMinus1() {
        Assertions.assertEquals(new Vector(-2, -1), new Vector(2, 1).multiply(-1));
    }

    // endregion

    // region inverse

    @Test
    void inverseXOfVectorWithX1Y2() {
        Assertions.assertEquals(new Vector(-1, 2), new Vector(1, 2).inverseX());
    }

    @Test
    void inverseYOfVectorWithX1Y2() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1, 2).inverseY());
    }

    @Test
    void inverseXYOfVectorWithX1Y2() {
        Assertions.assertEquals(new Vector(-1, -2), new Vector(1, 2).inverse());
    }

    // endregion

    // region rotate, normal and normalized

    @Test
    void rotateWithPiHalf() {
        assertVector(new Vector(1, 2).rotate(Math.PI / 2),
            -2, 1.0000000000000002
        );
    }

    @Test
    void rotateWithPi() {
        assertVector(new Vector(1, 2).rotate(Math.PI),
            -1.0000000000000002, -1.9999999999999998
        );
    }

    @Test
    void rotateWithPiThreeHalf() {
        assertVector(new Vector(1, 2).rotate(Math.PI * 3 / 2),
            1.9999999999999998, -1.0000000000000004
        );
    }

    @Test
    void rotateWithTwoPi() {
        assertVector(new Vector(1, 2).rotate(Math.PI * 2),
            1.0000000000000004, 1.9999999999999998
        );
    }

    @Test
    void normalLeftOfVectorWithX1Y2() {
        Assertions.assertEquals(new Vector(-2, 1), new Vector(1, 2).normalLeft());
    }

    @Test
    void normalRightOfVectorWithX1Y2() {
        Assertions.assertEquals(new Vector(2, -1), new Vector(1, 2).normalRight());
    }

    @Test
    void normalizedOfVectorWithoutParameter() {
        Vector vector = new Vector().normalized();
        assertVector(vector);
        Assertions.assertEquals(0, vector.length());
    }

    @Test
    void normalizedOfVectorWithXY1() {
        Vector vector = new Vector(1).normalized();
        assertVector(vector, 0.7071067811865475);
        Assertions.assertEquals(1, Math.round(vector.length()));
    }

    @Test
    void normalizedOfVectorWithX1Y2() {
        Vector vector = new Vector(1, 2).normalized();
        assertVector(vector, 0.4472135954999579, 0.8944271909999159);
        Assertions.assertEquals(1, Math.round(vector.length()));
    }

    // endregion

    // region scalarProduct and angle

    @Test
    void scalarProductOfVectorWithoutParameterAndXY1() {
        Assertions.assertEquals(0, new Vector().scalarProduct(new Vector(1)));
    }

    @Test
    void scalarProductOfVectorWithXY1AndXY1() {
        Assertions.assertEquals(2, new Vector(1).scalarProduct(new Vector(1)));
    }

    @Test
    void scalarProductOfVectorWithXY2AndX1Y2() {
        Assertions.assertEquals(6, new Vector(2).scalarProduct(new Vector(1, 2)));
    }

    @Test
    void angleBetweenX0Y1AndX1Y0() {
        Assertions.assertEquals(Math.PI / 2,
            new Vector(0, 1).angle(new Vector(1, 0))
        );
    }

    @Test
    void angleBetweenX0Y1AndXY1() {
        Assertions.assertEquals(0.7853981633974484, // Math.PI / 4
            new Vector(0, 1).angle(new Vector(1, 1))
        );
    }

    // endregion

    // region round

    @Test
    void absoluteOfPositiveXY() {
        Assertions.assertEquals(new Vector(1, 2), new Vector(1, 2).absolute());
    }

    @Test
    void absoluteOfNegativeXAndPositiveY() {
        Assertions.assertEquals(new Vector(1, 2), new Vector(-1, 2).absolute());
    }

    @Test
    void absoluteOfNegativeYAndPositiveX() {
        Assertions.assertEquals(new Vector(1, 2), new Vector(-1, -2).absolute());
    }

    @Test
    void absoluteOfNegativeXAndY() {
        Assertions.assertEquals(new Vector(1, 2), new Vector(-1, -2).absolute());
    }

    @Test
    void roundOfComma0() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1, -2).round());
    }

    @Test
    void roundOfComma2() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1.2, -2.2).round());
    }

    @Test
    void roundOfComma49() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1.49, -2.49).round());
    }

    @Test
    void roundOfComma5() {
        Assertions.assertEquals(new Vector(2, -2), new Vector(1.5, -2.5).round());
    }

    @Test
    void roundOfComma7() {
        Assertions.assertEquals(new Vector(2, -3), new Vector(1.7, -2.7).round());
    }

    @Test
    void floorOfComma0() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1, -2).floor());
    }

    @Test
    void floorOfComma2() {
        Assertions.assertEquals(new Vector(1, -3), new Vector(1.2, -2.2).floor());
    }

    @Test
    void floorOfComma49() {
        Assertions.assertEquals(new Vector(1, -3), new Vector(1.49, -2.49).floor());
    }

    @Test
    void floorOfComma5() {
        Assertions.assertEquals(new Vector(1, -3), new Vector(1.5, -2.5).floor());
    }

    @Test
    void floorOfComma7() {
        Assertions.assertEquals(new Vector(1, -3), new Vector(1.7, -2.7).floor());
    }

    @Test
    void ceilOfComma0() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1, -2).ceil());
    }

    @Test
    void ceilOfComma2() {
        Assertions.assertEquals(new Vector(2, -2), new Vector(1.2, -2.2).ceil());
    }

    @Test
    void ceilOfComma49() {
        Assertions.assertEquals(new Vector(2, -2), new Vector(1.49, -2.49).ceil());
    }

    @Test
    void ceilOfComma5() {
        Assertions.assertEquals(new Vector(2, -2), new Vector(1.5, -2.5).ceil());
    }

    @Test
    void ceilOfComma7() {
        Assertions.assertEquals(new Vector(2, -2), new Vector(1.7, -2.7).ceil());
    }

    @Test
    void truncateOfComma0() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1, -2).truncate());
    }

    @Test
    void truncateOfComma2() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1.2, -2.2).truncate());
    }

    @Test
    void truncateOfComma49() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1.49, -2.49).truncate());
    }

    @Test
    void truncateOfComma5() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1.5, -2.5).truncate());
    }

    @Test
    void truncateOfComma7() {
        Assertions.assertEquals(new Vector(1, -2), new Vector(1.7, -2.7).truncate());
    }

    // endregion

    // region isZeroVector and copy

    @Test
    void isZeroVectorWithVectorWithoutParameter() {
        Assertions.assertTrue(new Vector().isZeroVector());
    }

    @Test
    void isZeroVectorWithVectorWithXY1() {
        Assertions.assertFalse(new Vector(1).isZeroVector());
    }

    @Test
    void copyOfVectorWithX2Y3() {
        Vector vector = new Vector(2, 3);
        Assertions.assertEquals(vector, vector.copy());
    }

    // endregion

    // region override

    @Test
    void equalsOfVectorWithX2Y3() {
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
    void hashCodeOfVectorWithX2Y3() {
        Assertions.assertEquals(
            525249,
            new Vector(2, 3).hashCode()
        );
    }

    @Test
    void toStringOfVectorWithX2Y3() {
        Vector vector = new Vector(2, 3);
        Assertions.assertEquals("2.0:3.0", vector.toString());
    }

    @Test
    void compareToOfVectorWithX2Y3() {
        Vector vector = new Vector(2, 3);
        Assertions.assertEquals(
            0, vector.compareTo(new Vector(2, 3))
        );
        Assertions.assertEquals(
            -1, vector.compareTo(new Vector(3, 1))
        );
        Assertions.assertEquals(
            1, vector.compareTo(new Vector(1, 0))
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
