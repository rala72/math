package io.rala.math.algebra.vector;

import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.TestVector;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VectorTest {

    private final TestAbstractArithmetic testAbstractArithmetic = new TestAbstractArithmetic();

    // region constructors

    @Test
    void constructorWithNegativeSize() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TestVector(-1)
        ); // assert exception message?
    }

    @Test
    void constructorWithPositiveSize() {
        int expectedSize = 1;
        TestVector vector = new TestVector(expectedSize);
        assertEquals(expectedSize, vector.getSize());
    }

    @Test
    void constructorWithSizeZero() {
        TestVector vector = new TestVector(0);
        assertEquals(0, vector.getSize());
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> new TestVector(0).getValue(0)
        ); // assert exception message?
    }

    @Test
    void constructorWithSizeTenAndDefaultValue() {
        TestVector vector = new TestVector(10, 2d);
        assertEquals(2d, vector.getDefaultValue());
    }

    @Test
    void constructorWithVector() {
        assertEquals(
            1,
            new TestVector(new TestVector(1, 2d)).getSize()
        );
        assertEquals(
            2d,
            new TestVector(new TestVector(1, 2d)).getDefaultValue()
        );
    }

    // endregion

    // region value

    @Test
    void defaultZeroAllDefault() {
        TestVector vector = new TestVector(3);
        for (int i = 0; i < 3; i++) {
            assertEquals(0d, vector.getValue(i));
        }
    }

    @Test
    void defaultOneAllDefault() {
        TestVector vector = new TestVector(3, 1d);
        for (int i = 0; i < 3; i++) {
            assertEquals(1d, vector.getValue(i));
        }
    }

    @Test
    void defaultZeroSomeDefault() {
        TestVector vector = new TestVector(3);
        vector.setValue(2, 3d);
        for (int i = 0; i < 2; i++) {
            assertEquals(0d, vector.getValue(i));
        }
        assertEquals(3d, vector.getValue(2));
    }

    @Test
    void defaultOneSomeDefault() {
        TestVector vector = new TestVector(3, 1d);
        vector.setValue(2, 3d);
        for (int i = 0; i < 2; i++) {
            assertEquals(1d, vector.getValue(i));
        }
        assertEquals(3d, vector.getValue(2));
    }

    @Test
    void defaultZeroNoneDefault() {
        TestVector vector = new TestVector(3, false);
        assertEquals(1d, vector.getValue(0));
        assertEquals(-4d, vector.getValue(1));
        assertEquals(9d, vector.getValue(2));
    }

    @Test
    void defaultOneNoneDefault() {
        TestVector vector = new TestVector(3, false);
        vector.setValue(0, 7d);
        assertEquals(7d, vector.getValue(0));
        assertEquals(-4d, vector.getValue(1));
        assertEquals(9d, vector.getValue(2));
    }

    // endregion

    // region compute

    @Test
    void computeFirstEntryTimesTwoUnary() {
        TestVector actual = new TestVector(3, false);
        assertEquals(
            1d,
            actual.compute(0, x -> testAbstractArithmetic.product(2d, x))
        );
        assertEquals(
            Vector.ofValues(testAbstractArithmetic, 2d, -4d, 9d),
            actual
        );
    }

    @Test
    void computeFirstEntryTimesTwoBinary() {
        TestVector actual = new TestVector(3, false);
        assertEquals(
            1d,
            actual.compute(0, 2d, testAbstractArithmetic::product)
        );
        assertEquals(
            Vector.ofValues(testAbstractArithmetic, 2d, -4d, 9d),
            actual
        );
    }

    @Test
    void computeTimesTwoUnary() {
        TestVector actual = new TestVector(3, false);
        actual.computeAll(
            x -> testAbstractArithmetic.product(2d, x.getValue())
        );
        assertEquals(
            TestVector.ofValues(testAbstractArithmetic, 2d, -8d, 18d),
            actual
        );
    }

    @Test
    void computeTimesTwoBinary() {
        TestVector actual = new TestVector(3, false);
        actual.computeAll(x -> 2d, testAbstractArithmetic::product);
        assertEquals(
            TestVector.ofValues(testAbstractArithmetic, 2d, -8d, 18d),
            actual
        );
    }

    // endregion

    // region to Matrix and toParam

    @Test
    void emptyColumnVectorToMatrix() {
        assertEquals(
            new TestMatrix(3, 1),
            new TestVector(3).toMatrix()
        );
    }

    @Test
    void emptyRowVectorToMatrix() {
        assertEquals(
            new TestMatrix(1, 3),
            new TestVector(3, Vector.Type.ROW).toMatrix()
        );
    }

    @Test
    void nonEmptyVectorOfSizeOneToMatrix() {
        assertEquals(
            new TestMatrix(1, 1d),
            new TestVector(1, 1d).toMatrix()
        );
    }

    @Test
    void nonEmptyColumnVectorToMatrix() {
        TestMatrix expected = new TestMatrix(4, 1);
        expected.setValue(0, 0, 1d);
        expected.setValue(1, 0, -4d);
        expected.setValue(2, 0, 9d);
        expected.setValue(3, 0, -16d);
        assertEquals(
            expected,
            new TestVector(4, false).toMatrix()
        );
    }

    @Test
    void nonEmptyRowVectorToMatrix() {
        TestMatrix expected = new TestMatrix(1, 4);
        expected.setValue(0, 0, 1d);
        expected.setValue(0, 1, -4d);
        expected.setValue(0, 2, 9d);
        expected.setValue(0, 3, -16d);
        assertEquals(
            expected,
            new TestVector(4, Vector.Type.ROW, false).toMatrix()
        );
    }

    // endregion

    // region add, subtract, multiply and dotProduct

    @Test
    void addVectorDifferentSize() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TestVector(2).add(new TestVector(3))
        );
    }

    @Test
    void addVectorDifferentType() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TestVector(2).add(new TestVector(2).transpose())
        );
    }

    @Test
    void addEmptyVectorToEmptyVector() {
        assertEquals(
            new TestVector(3),
            new TestVector(3).add(new TestVector(3))
        );
    }

    @Test
    void addEmptyVectorToNonEmptyVector() {
        assertEquals(
            new TestVector(3, false),
            new TestVector(3, false).add(new TestVector(3))
        );
    }

    @Test
    void addNonEmptyVectorToEmptyVector() {
        assertEquals(
            new TestVector(3, false),
            new TestVector(3).add(new TestVector(3, false))
        );
    }

    @Test
    void addNonEmptyVectorToNonEmptyVector() {
        TestVector expected = new TestVector(3);
        expected.setValue(0, 2d);
        expected.setValue(1, -8d);
        expected.setValue(2, 18d);
        assertEquals(
            expected,
            new TestVector(3, false).add(new TestVector(3, false))
        );
    }

    @Test
    void subtractVectorDifferentSize() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TestVector(2).subtract(new TestVector(3))
        );
    }

    @Test
    void subtractVectorDifferentType() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TestVector(2).subtract(new TestVector(2).transpose())
        );
    }

    @Test
    void subtractEmptyVectorFromEmptyVector() {
        assertEquals(
            new TestVector(3),
            new TestVector(3).subtract(new TestVector(3))
        );
    }

    @Test
    void subtractEmptyVectorFromNonEmptyVector() {
        assertEquals(
            new TestVector(3, false),
            new TestVector(3, false).subtract(new TestVector(3))
        );
    }

    @Test
    void subtractNonEmptyVectorFromEmptyVector() {
        assertEquals(
            new TestVector(3, false).invert(),
            new TestVector(3).subtract(new TestVector(3, false))
        );
    }

    @Test
    void subtractNonEmptyVectorFromNonEmptyVector() {
        assertEquals(
            new TestVector(3),
            new TestVector(3, false).subtract(new TestVector(3, false))
        );
    }

    @Test
    void multiplyNonEmptyVectorByZero() {
        assertEquals(
            new TestVector(3),
            new TestVector(3, false).multiply(testAbstractArithmetic.zero())
        );
    }

    @Test
    void multiplyNonEmptyVectorByOne() {
        assertEquals(
            new TestVector(3, false),
            new TestVector(3, false).multiply(testAbstractArithmetic.one())
        );
    }

    @Test
    void multiplyNonEmptyVectorByFive() {
        TestVector expected = new TestVector(3);
        expected.setValue(0, 5d);
        expected.setValue(1, -20d);
        expected.setValue(2, 45d);
        assertEquals(
            expected,
            new TestVector(3, false).multiply(testAbstractArithmetic.fromInt(5))
        );
    }

    @Test
    void multiplyVectorsDifferentSize() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TestVector(3).transpose().multiply(new TestVector(4))
        );
    }

    @Test
    void multiplyVectorsSameTypeColumn() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TestVector(3).multiply(new TestVector(3))
        );
    }

    @Test
    void multiplyVectorsSameTypeRow() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TestVector(3).transpose()
                .multiply(new TestVector(3).transpose())
        );
    }

    @Test
    void multiplyEmptyVectorsColumnRow() {
        assertEquals(
            new TestMatrix(2),
            new TestVector(2).multiply(new TestVector(2).transpose())
        );
    }

    @Test
    void multiplyEmptyVectorsRowColumn() {
        assertEquals(
            new TestMatrix(1),
            new TestVector(2).transpose().multiply(new TestVector(2))
        );
    }

    @Test
    void multiplyNonEmptyVectorsColumnRow() {
        TestMatrix expected = new TestMatrix(2);
        expected.setValue(0, 0, 1d);
        expected.setValue(0, 1, -4d);
        expected.setValue(1, 0, -4d);
        expected.setValue(1, 1, 16d);
        assertEquals(
            expected,
            new TestVector(2, false)
                .multiply(new TestVector(2, false).transpose())
        );
    }

    @Test
    void multiplyNonEmptyVectorsRowColumn() {
        TestMatrix expected = new TestMatrix(1);
        expected.setValue(0, 0, 17d);
        assertEquals(
            expected,
            new TestVector(2, false).transpose()
                .multiply(new TestVector(2, false))
        );
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorColumnRow() {
        assertEquals(
            new TestMatrix(2),
            new TestVector(2, false)
                .multiply(new TestVector(2).transpose())
        );
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorRowColumn() {
        assertEquals(
            new TestMatrix(1),
            new TestVector(2, false).transpose()
                .multiply(new TestVector(2))
        );
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorColumnRow() {
        assertEquals(
            new TestMatrix(2),
            new TestVector(2)
                .multiply(new TestVector(2, false).transpose())
        );
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorRowColumn() {
        assertEquals(
            new TestMatrix(1),
            new TestVector(2).transpose()
                .multiply(new TestVector(2, false))
        );
    }

    @Test
    void dotProductEmptyColumnVectors() {
        assertEquals(
            0d,
            new TestVector(3)
                .dotProduct(new TestVector(3))
        );
    }

    @Test
    void dotProductNonEmptyColumnVectors() {
        assertEquals(
            98d,
            new TestVector(3, false)
                .dotProduct(new TestVector(3, false))
        );
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyColumnVector() {
        assertEquals(
            0d,
            new TestVector(3)
                .dotProduct(new TestVector(3, false))
        );
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyColumnVector() {
        assertEquals(
            0d,
            new TestVector(3, false)
                .dotProduct(new TestVector(3))
        );
    }

    @Test
    void dotProductEmptyColumnVectorEmptyRowVector() {
        assertEquals(
            0d,
            new TestVector(3)
                .dotProduct(new TestVector(3).transpose())
        );
    }

    @Test
    void dotProductNonEmptyColumnVectorNonEmptyRowVector() {
        assertEquals(
            98d,
            new TestVector(3, false)
                .dotProduct(new TestVector(3, false).transpose())
        );
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyRowVector() {
        assertEquals(
            0d,
            new TestVector(3)
                .dotProduct(new TestVector(3, false).transpose())
        );
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyRowVector() {
        assertEquals(
            0d,
            new TestVector(3, false)
                .dotProduct(new TestVector(3).transpose())
        );
    }

    @Test
    void dotProductEmptyRowVectorEmptyColumnVector() {
        assertEquals(
            0d,
            new TestVector(3).transpose()
                .dotProduct(new TestVector(3))
        );
    }

    @Test
    void dotProductNonEmptyRowVectorNonEmptyColumnVector() {
        assertEquals(
            98d,
            new TestVector(3, false).transpose()
                .dotProduct(new TestVector(3, false))
        );
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyColumnVector() {
        assertEquals(
            0d,
            new TestVector(3).transpose()
                .dotProduct(new TestVector(3, false))
        );
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyColumnVector() {
        assertEquals(
            0d,
            new TestVector(3, false).transpose()
                .dotProduct(new TestVector(3))
        );
    }

    @Test
    void dotProductEmptyRowVectors() {
        assertEquals(
            0d,
            new TestVector(3).transpose()
                .dotProduct(new TestVector(3).transpose())
        );
    }

    @Test
    void dotProductNonEmptyRowVectors() {
        assertEquals(
            98d,
            new TestVector(3, false).transpose()
                .dotProduct(new TestVector(3, false).transpose())
        );
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyRowVector() {
        assertEquals(
            0d,
            new TestVector(3).transpose()
                .dotProduct(new TestVector(3, false).transpose())
        );
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyRowVector() {
        assertEquals(
            0d,
            new TestVector(3, false).transpose()
                .dotProduct(new TestVector(3).transpose())
        );
    }

    // endregion

    // region transpose and invert

    @Test
    void transposeEmptyColumnVector() {
        assertEquals(
            new TestVector(3, Vector.Type.ROW),
            new TestVector(3).transpose()
        );
    }

    @Test
    void transposeEmptyRowVector() {
        assertEquals(
            new TestVector(3),
            new TestVector(3, Vector.Type.ROW).transpose()
        );
    }

    @Test
    void transposeNonEmptyColumnVector() {
        assertEquals(
            new TestVector(3, Vector.Type.ROW, false),
            new TestVector(3, false).transpose()
        );
    }

    @Test
    void transposeNonEmptyRowVector() {
        assertEquals(
            new TestVector(3, false),
            new TestVector(3, Vector.Type.ROW, false).transpose()
        );
    }

    @Test
    void invertEmptyVector() {
        TestVector expected = new TestVector(3);
        for (int i = 0; i < 3; i++) {
            expected.setValue(i, -0d);
        }
        assertEquals(
            expected,
            new TestVector(3).invert()
        );
    }

    @Test
    void invertNonEmptyVector() {
        TestVector expected = new TestVector(3);
        expected.setValue(0, -1d);
        expected.setValue(1, 4d);
        expected.setValue(2, -9d);
        assertEquals(
            expected,
            new TestVector(3, false).invert()
        );
    }

    // endregion

    // region norm and unit

    @Test
    void maxNormEmptyVector() {
        assertEquals(
            0d,
            new TestVector(3).maxNorm()
        );
    }

    @Test
    void maxNormNonEmptyVector() {
        assertEquals(
            9d,
            new TestVector(3, false).maxNorm()
        );
    }

    @Test
    void euclideanNormEmptyVector() {
        assertEquals(
            0d,
            new TestVector(3).euclideanNorm()
        );
    }

    @Test
    void euclideanNormNonEmptyVector() {
        assertEquals(
            testAbstractArithmetic.root2(98d),
            new TestVector(3, false).euclideanNorm()
        );
    }

    @Test
    void negativePNorm() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TestVector(3).pNorm(-1)
        );
    }

    @Test
    void sevenNormEmptyVector() {
        assertEquals(
            0d,
            new TestVector(3).pNorm(7)
        );
    }

    @Test
    void sevenNormNonEmptyVector() {
        assertEquals(
            testAbstractArithmetic.root(4766586d, 7),
            new TestVector(3, false).pNorm(7)
        );
    }

    @Test
    void unitVectorOfEmptyVector() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TestVector(3).normalize()
        );
    }

    @Test
    void unitVectorOfNonEmptyVector() {
        TestVector expected = new TestVector(3);
        expected.setValue(0, testAbstractArithmetic.quotient(
            1,
            testAbstractArithmetic.root2(98)
        ));
        expected.setValue(1, testAbstractArithmetic.quotient(
            -4,
            testAbstractArithmetic.root2(98)
        ));
        expected.setValue(2, testAbstractArithmetic.quotient(
            9, testAbstractArithmetic.root2(98)
        ));
        assertEquals(
            expected,
            new TestVector(3, false).normalize()
        );
    }

    // endregion

    // region angle

    @Test
    void angleBetweenEmptyVectors() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TestVector(3).angle(new TestVector(3))
        );
    }

    @Test
    void angleBetweenIdenticalVectors() {
        assertEquals(
            testAbstractArithmetic.zero(),
            new TestVector(3, false).angle(new TestVector(3, false))
        );
    }

    @Test
    void angleBetweenParallelVectors() {
        assertEquals(
            testAbstractArithmetic.zero(),
            new TestVector(3, false).multiply(
                testAbstractArithmetic.fromInt(2)
            ).angle(new TestVector(3, false).multiply(
                testAbstractArithmetic.fromInt(3)
                )
            )
        );
    }

    @Test
    void angleBetweenOppositeVectors() {
        assertEquals(
            testAbstractArithmetic.fromDouble(Math.PI),
            new TestVector(3, false).multiply(
                testAbstractArithmetic.fromInt(-2)
            ).angle(new TestVector(3, false).multiply(
                testAbstractArithmetic.fromInt(3)
                )
            )
        );
    }

    @Test
    void angleBetweenNonEmptyVectors() {
        assertEquals(
            testAbstractArithmetic.fromDouble(Math.PI / 2),
            Vector.ofValues(testAbstractArithmetic, 0, 1, 0)
                .angle(Vector.ofValues(testAbstractArithmetic, 0, 0, 1))
        );
    }

    // endregion

    // region static

    @Test
    void emptyVectorOfValues() {
        assertEquals(
            new TestVector(4),
            Vector.ofValues(testAbstractArithmetic, 0d, 0d, 0d, 0d)
        );
    }

    @Test
    void nonEmptyVectorOfValues() {
        assertEquals(
            new TestVector(4, false),
            Vector.ofValues(testAbstractArithmetic, 1d, -4d, 9d, -16d)
        );
    }

    @Test
    void emptyVectorOfList() {
        List<Number> list = new ArrayList<>();
        list.add(0d);
        list.add(0d);
        assertEquals(
            new TestVector(2),
            Vector.ofList(
                testAbstractArithmetic,
                list)
        );
    }

    @Test
    void nonEmptyVectorOfList() {
        List<Number> list = new ArrayList<>();
        list.add(1d);
        list.add(-4d);
        assertEquals(
            new TestVector(2, false),
            Vector.ofList(
                testAbstractArithmetic,
                list
            )
        );
    }

    // endregion
}
