package io.rala.math.geometry;

import io.rala.math.testUtils.assertion.GeometryAssertions;
import io.rala.math.testUtils.assertion.SerializableAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VectorTest {
    // region constructors, getter and setter

    @Test
    void constructorWithoutParameter() {
        GeometryAssertions.assertVector(new Vector());
    }

    @Test
    void constructorWithXYParameter() {
        GeometryAssertions.assertVector(new Vector(1), 1);
    }

    @Test
    void constructorWithEqualXYParameter() {
        GeometryAssertions.assertVector(new Vector(2, 2), 2);
    }

    @Test
    void constructorWithDifferentXYParameter() {
        GeometryAssertions.assertVector(new Vector(2, 3), 2, 3);
    }

    @Test
    void createAndSetX() {
        Vector vector = new Vector();
        GeometryAssertions.assertVector(vector);
        vector.setX(1);
        GeometryAssertions.assertVector(vector, 1, 0);
    }

    @Test
    void createAndSetY() {
        Vector vector = new Vector();
        GeometryAssertions.assertVector(vector);
        vector.setY(2);
        GeometryAssertions.assertVector(vector, 0, 2);
    }

    @Test
    void createAndSetXY() {
        Vector vector = new Vector();
        GeometryAssertions.assertVector(vector);
        vector.setXY(3);
        GeometryAssertions.assertVector(vector, 3);
    }

    // endregion

    // region length, add, subtract and multiply

    @Test
    void lengthOfVectorWithoutParameter() {
        Assertions.assertEquals(0, new Vector().length());
    }

    @Test
    void lengthOfVectorXY1() {
        Assertions.assertEquals(Math.sqrt(2), new Vector(1).length());
    }

    @Test
    void lengthOfVectorX1Y0() {
        Assertions.assertEquals(1, new Vector(1, 0).length());
    }

    @Test
    void addWithXY() {
        GeometryAssertions.assertVector(new Vector().add(2), 2, 2);
    }

    @Test
    void addWithXAndY() {
        GeometryAssertions.assertVector(
            new Vector(1).add(1, 1),
            2, 2
        );
    }

    @Test
    void addWithVector() {
        GeometryAssertions.assertVector(
            new Vector(1, 0).add(new Vector(1, 2)),
            2, 2
        );
    }

    @Test
    void subtractWithXY() {
        GeometryAssertions.assertVector(new Vector(2, 2).subtract(2));
    }

    @Test
    void subtractWithXAndY() {
        GeometryAssertions.assertVector(
            new Vector(2, 2).subtract(1, 1),
            1
        );
    }

    @Test
    void subtractWithVector() {
        GeometryAssertions.assertVector(
            new Vector(2, 2).subtract(new Vector(1, 2)),
            1, 0
        );
    }

    @Test
    void multiplyZeroVectorWith1() {
        GeometryAssertions.assertVector(new Vector().multiply(1));
    }

    @Test
    void multiplyVectorWith0() {
        GeometryAssertions.assertVector(new Vector(1).multiply(0));
    }

    @Test
    void multiplyVectorWith1() {
        GeometryAssertions.assertVector(new Vector(1).multiply(1), 1);
    }

    @Test
    void multiplyVectorWithMinus1() {
        GeometryAssertions.assertVector(new Vector(2, 1).multiply(-1), -2, -1);
    }

    // endregion

    // region inverse

    @Test
    void inverseXOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(new Vector(1, 2).inverseX(), -1, 2);
    }

    @Test
    void inverseYOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(new Vector(1, 2).inverseY(), 1, -2);
    }

    @Test
    void inverseXYOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(new Vector(1, 2).inverse(), -1, -2);
    }

    // endregion

    // region normal and normalized

    @Test
    void normalLeftOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(new Vector(1, 2).normalLeft(), -2, 1);
    }

    @Test
    void normalRightOfVectorWithX1Y2() {
        GeometryAssertions.assertVector(new Vector(1, 2).normalRight(), 2, -1);
    }

    @Test
    void normalizedOfVectorWithoutParameter() {
        Vector vector = new Vector().normalized();
        GeometryAssertions.assertVector(vector, Double.NaN, Double.NaN);
        Assertions.assertEquals(Double.NaN, vector.length());
    }

    @Test
    void normalizedOfVectorWithXY1() {
        Vector vector = new Vector(1).normalized();
        GeometryAssertions.assertVector(vector, 0.7071067811865475);
        Assertions.assertEquals(1, Math.round(vector.length()));
    }

    @Test
    void normalizedOfVectorWithX1Y2() {
        Vector vector = new Vector(1, 2).normalized();
        GeometryAssertions.assertVector(vector, 0.4472135954999579, 0.8944271909999159);
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
        Assertions.assertEquals(Math.PI / 4,
            new Vector(0, 1).angle(new Vector(1, 1)),
            GeometryAssertions.DELTA
        );
    }

    // endregion

    // region round

    @Test
    void absoluteOfPositiveXY() {
        GeometryAssertions.assertVector(new Vector(1, 2).absolute(), 1, 2);
    }

    @Test
    void absoluteOfNegativeXAndPositiveY() {
        GeometryAssertions.assertVector(new Vector(-1, 2).absolute(), 1, 2);
    }

    @Test
    void absoluteOfNegativeYAndPositiveX() {
        GeometryAssertions.assertVector(new Vector(-1, -2).absolute(), 1, 2);
    }

    @Test
    void absoluteOfNegativeXAndY() {
        GeometryAssertions.assertVector(new Vector(-1, -2).absolute(), 1, 2);
    }

    @Test
    void roundOfComma0() {
        GeometryAssertions.assertVector(new Vector(1, -2).round(), 1, -2);
    }

    @Test
    void roundOfComma2() {
        GeometryAssertions.assertVector(new Vector(1.2, -2.2).round(), 1, -2);
    }

    @Test
    void roundOfComma49() {
        GeometryAssertions.assertVector(new Vector(1.49, -2.49).round(), 1, -2);
    }

    @Test
    void roundOfComma5() {
        GeometryAssertions.assertVector(new Vector(1.5, -2.5).round(), 2, -2);
    }

    @Test
    void roundOfComma7() {
        GeometryAssertions.assertVector(new Vector(1.7, -2.7).round(), 2, -3);
    }

    @Test
    void floorOfComma0() {
        GeometryAssertions.assertVector(new Vector(1, -2).floor(), 1, -2);
    }

    @Test
    void floorOfComma2() {
        GeometryAssertions.assertVector(new Vector(1.2, -2.2).floor(), 1, -3);
    }

    @Test
    void floorOfComma49() {
        GeometryAssertions.assertVector(new Vector(1.49, -2.49).floor(), 1, -3);
    }

    @Test
    void floorOfComma5() {
        GeometryAssertions.assertVector(new Vector(1.5, -2.5).floor(), 1, -3);
    }

    @Test
    void floorOfComma7() {
        GeometryAssertions.assertVector(new Vector(1.7, -2.7).floor(), 1, -3);
    }

    @Test
    void ceilOfComma0() {
        GeometryAssertions.assertVector(new Vector(1, -2).ceil(), 1, -2);
    }

    @Test
    void ceilOfComma2() {
        GeometryAssertions.assertVector(new Vector(1.2, -2.2).ceil(), 2, -2);
    }

    @Test
    void ceilOfComma49() {
        GeometryAssertions.assertVector(new Vector(1.49, -2.49).ceil(), 2, -2);
    }

    @Test
    void ceilOfComma5() {
        GeometryAssertions.assertVector(new Vector(1.5, -2.5).ceil(), 2, -2);
    }

    @Test
    void ceilOfComma7() {
        GeometryAssertions.assertVector(new Vector(1.7, -2.7).ceil(), 2, -2);
    }

    @Test
    void truncateOfComma0() {
        GeometryAssertions.assertVector(new Vector(1, -2).truncate(), 1, -2);
    }

    @Test
    void truncateOfComma2() {
        GeometryAssertions.assertVector(new Vector(1.2, -2.2).truncate(), 1, -2);
    }

    @Test
    void truncateOfComma49() {
        GeometryAssertions.assertVector(new Vector(1.49, -2.49).truncate(), 1, -2);
    }

    @Test
    void truncateOfComma5() {
        GeometryAssertions.assertVector(new Vector(1.5, -2.5).truncate(), 1, -2);
    }

    @Test
    void truncateOfComma7() {
        GeometryAssertions.assertVector(new Vector(1.7, -2.7).truncate(), 1, -2);
    }

    // endregion

    // region isZeroVector

    @Test
    void isZeroVectorWithVectorWithoutParameter() {
        Assertions.assertTrue(new Vector().isZeroVector());
    }

    @Test
    void isZeroVectorWithVectorWithXY1() {
        Assertions.assertFalse(new Vector(1).isZeroVector());
    }

    // endregion

    // region isValid rotate and copy

    @Test
    void isValidWithZeroValues() {
        Assertions.assertTrue(new Vector().isValid());
    }

    @Test
    void isValidWithInfValues() {
        Assertions.assertFalse(
            new Vector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
                .isValid()
        );
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiHalf() {
        GeometryAssertions.assertVector(new Vector(1, 2).rotate(Math.PI / 2), -2, 1);
    }

    @Test
    void rotateOfVectorWithX1Y2WithPi() {
        GeometryAssertions.assertVector(new Vector(1, 2).rotate(Math.PI), -1, -2);
    }

    @Test
    void rotateOfVectorWithX1Y2WithPiThreeHalf() {
        GeometryAssertions.assertVector(new Vector(1, 2).rotate(Math.PI * 3 / 2), 2, -1);
    }

    @Test
    void rotateOfVectorWithX1Y2WithTwoPi() {
        GeometryAssertions.assertVector(new Vector(1, 2).rotate(Math.PI * 2), 1, 2);
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

    @Test
    void serializable() {
        SerializableAssertions.assertSerializable(new Vector(), Vector.class);
    }

    // endregion
}
