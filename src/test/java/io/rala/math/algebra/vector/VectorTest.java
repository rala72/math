package io.rala.math.algebra.vector;

import io.rala.math.exception.NotSupportedException;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.TestVector;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

public class VectorTest {

    private final TestAbstractArithmetic testAbstractArithmetic = new TestAbstractArithmetic();

    // region constructors

    @Test
    void constructorWithNegativeSize() {
        assertThrows(IllegalArgumentException.class,
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
        assertThrows(IndexOutOfBoundsException.class,
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

    // region getter and length

    @Test
    void createWithLength0AndAssertLengthEquals0() {
        assertEquals(
            0d,
            new TestVector(3).length()
        );
    }

    @Test
    void createWithLength3AndAssertLengthEquals98() {
        assertEquals(
            testAbstractArithmetic.root2(98d),
            new TestVector(3).fillWithTestValues().length()
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
        TestVector vector = new TestVector(3).fillWithTestValues();
        assertEquals(1d, vector.getValue(0));
        assertEquals(-4d, vector.getValue(1));
        assertEquals(9d, vector.getValue(2));
    }

    @Test
    void defaultOneNoneDefault() {
        TestVector vector = new TestVector(3).fillWithTestValues();
        vector.setValue(0, 7d);
        assertEquals(7d, vector.getValue(0));
        assertEquals(-4d, vector.getValue(1));
        assertEquals(9d, vector.getValue(2));
    }

    // endregion

    // region compute

    @Test
    void computeFirstEntryTimesTwoUnary() {
        TestVector actual = new TestVector(3).fillWithTestValues();
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
        TestVector actual = new TestVector(3).fillWithTestValues();
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
        TestVector actual = new TestVector(3).fillWithTestValues();
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
        TestVector actual = new TestVector(3).fillWithTestValues();
        actual.computeAll(x -> 2d, testAbstractArithmetic::product);
        assertEquals(
            TestVector.ofValues(testAbstractArithmetic, 2d, -8d, 18d),
            actual
        );
    }

    // endregion

    // region to Matrix and toParam

    @Test
    void toMatrixOfVectorWithEmptyColumn() {
        assertEquals(
            new TestMatrix(3, 1),
            new TestVector(3).toMatrix()
        );
    }

    @Test
    void toMatrixOfVectorWithEmptyRow() {
        assertEquals(
            new TestMatrix(1, 3),
            new TestVector(3, Vector.Type.ROW).toMatrix()
        );
    }

    @Test
    void toMatrixOfNonEmptyVectorWithSize1() {
        assertEquals(
            new TestMatrix(1, 1d),
            new TestVector(1, 1d).toMatrix()
        );
    }

    @Test
    void toMatrixOfVectorWithNonEmptyColumn() {
        TestMatrix expected = new TestMatrix(4, 1);
        expected.setValue(0, 0, 1d);
        expected.setValue(1, 0, -4d);
        expected.setValue(2, 0, 9d);
        expected.setValue(3, 0, -16d);
        assertEquals(
            expected,
            new TestVector(4).fillWithTestValues().toMatrix()
        );
    }

    @Test
    void toMatrixOfVectorWithNonEmptyRow() {
        TestMatrix expected = new TestMatrix(1, 4);
        expected.setValue(0, 0, 1d);
        expected.setValue(0, 1, -4d);
        expected.setValue(0, 2, 9d);
        expected.setValue(0, 3, -16d);
        assertEquals(
            expected,
            new TestVector(4, Vector.Type.ROW).fillWithTestValues().toMatrix()
        );
    }

    @Test
    void toParamOfEmptyVector() {
        assertEquals(
            0d,
            new TestVector(1).toParam()
        );
    }

    @Test
    void toParamOfNonEmptyVector() {
        assertEquals(
            1d,
            new TestVector(1).fillWithTestValues().toParam()
        );
    }

    @Test
    void toParamOFEmptyVectorWithInvalidSize() {
        assertThrows(NotSupportedException.class,
            () -> new TestVector(2).toParam()
        ); // assert exception message?
    }

    // endregion

    // region add, subtract, multiply and dotProduct

    @Test
    void addVectorDifferentSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestVector(2).add(new TestVector(3))
        ); // assert exception message?
    }

    @Test
    void addVectorDifferentType() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestVector(2).add(new TestVector(2).transpose())
        ); // assert exception message?
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
            new TestVector(3).fillWithTestValues(),
            new TestVector(3).fillWithTestValues().add(new TestVector(3))
        );
    }

    @Test
    void addNonEmptyVectorToEmptyVector() {
        assertEquals(
            new TestVector(3).fillWithTestValues(),
            new TestVector(3).add(new TestVector(3).fillWithTestValues())
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
            new TestVector(3).fillWithTestValues().add(new TestVector(3).fillWithTestValues())
        );
    }

    @Test
    void subtractVectorDifferentSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestVector(2).subtract(new TestVector(3))
        ); // assert exception message?
    }

    @Test
    void subtractVectorDifferentType() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestVector(2).subtract(new TestVector(2).transpose())
        ); // assert exception message?
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
            new TestVector(3).fillWithTestValues(),
            new TestVector(3).fillWithTestValues().subtract(new TestVector(3))
        );
    }

    @Test
    void subtractNonEmptyVectorFromEmptyVector() {
        assertEquals(
            new TestVector(3).fillWithTestValues().invert(),
            new TestVector(3).subtract(new TestVector(3).fillWithTestValues())
        );
    }

    @Test
    void subtractNonEmptyVectorFromNonEmptyVector() {
        assertEquals(
            new TestVector(3),
            new TestVector(3).fillWithTestValues().subtract(new TestVector(3).fillWithTestValues())
        );
    }

    @Test
    void multiplyNonEmptyVectorByZero() {
        assertEquals(
            new TestVector(3),
            new TestVector(3).fillWithTestValues().multiply(testAbstractArithmetic.zero())
        );
    }

    @Test
    void multiplyNonEmptyVectorByOne() {
        assertEquals(
            new TestVector(3).fillWithTestValues(),
            new TestVector(3).fillWithTestValues().multiply(testAbstractArithmetic.one())
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
            new TestVector(3).fillWithTestValues().multiply(testAbstractArithmetic.fromInt(5))
        );
    }

    @Test
    void multiplyVectorsDifferentSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestVector(3).transpose().multiply(new TestVector(4))
        ); // assert exception message?
    }

    @Test
    void multiplyVectorsSameTypeColumn() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestVector(3).multiply(new TestVector(3))
        ); // assert exception message?
    }

    @Test
    void multiplyVectorsSameTypeRow() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestVector(3).transpose()
                .multiply(new TestVector(3).transpose())
        ); // assert exception message?
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
            new TestVector(2).fillWithTestValues()
                .multiply(new TestVector(2).fillWithTestValues().transpose())
        );
    }

    @Test
    void multiplyNonEmptyVectorsRowColumn() {
        TestMatrix expected = new TestMatrix(1);
        expected.setValue(0, 0, 17d);
        assertEquals(
            expected,
            new TestVector(2).fillWithTestValues().transpose()
                .multiply(new TestVector(2).fillWithTestValues())
        );
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorColumnRow() {
        assertEquals(
            new TestMatrix(2),
            new TestVector(2).fillWithTestValues()
                .multiply(new TestVector(2).transpose())
        );
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorRowColumn() {
        assertEquals(
            new TestMatrix(1),
            new TestVector(2).fillWithTestValues().transpose()
                .multiply(new TestVector(2))
        );
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorColumnRow() {
        assertEquals(
            new TestMatrix(2),
            new TestVector(2)
                .multiply(new TestVector(2).fillWithTestValues().transpose())
        );
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorRowColumn() {
        assertEquals(
            new TestMatrix(1),
            new TestVector(2).transpose()
                .multiply(new TestVector(2).fillWithTestValues())
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
            new TestVector(3).fillWithTestValues()
                .dotProduct(new TestVector(3).fillWithTestValues())
        );
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyColumnVector() {
        assertEquals(
            0d,
            new TestVector(3)
                .dotProduct(new TestVector(3).fillWithTestValues())
        );
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyColumnVector() {
        assertEquals(
            0d,
            new TestVector(3).fillWithTestValues()
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
            new TestVector(3).fillWithTestValues()
                .dotProduct(new TestVector(3).fillWithTestValues().transpose())
        );
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyRowVector() {
        assertEquals(
            0d,
            new TestVector(3)
                .dotProduct(new TestVector(3).fillWithTestValues().transpose())
        );
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyRowVector() {
        assertEquals(
            0d,
            new TestVector(3).fillWithTestValues()
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
            new TestVector(3).fillWithTestValues().transpose()
                .dotProduct(new TestVector(3).fillWithTestValues())
        );
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyColumnVector() {
        assertEquals(
            0d,
            new TestVector(3).transpose()
                .dotProduct(new TestVector(3).fillWithTestValues())
        );
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyColumnVector() {
        assertEquals(
            0d,
            new TestVector(3).fillWithTestValues().transpose()
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
            new TestVector(3).fillWithTestValues().transpose()
                .dotProduct(new TestVector(3).fillWithTestValues().transpose())
        );
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyRowVector() {
        assertEquals(
            0d,
            new TestVector(3).transpose()
                .dotProduct(new TestVector(3).fillWithTestValues().transpose())
        );
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyRowVector() {
        assertEquals(
            0d,
            new TestVector(3).fillWithTestValues().transpose()
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
            new TestVector(3, Vector.Type.ROW).fillWithTestValues(),
            new TestVector(3).fillWithTestValues().transpose()
        );
    }

    @Test
    void transposeNonEmptyRowVector() {
        assertEquals(
            new TestVector(3).fillWithTestValues(),
            new TestVector(3, Vector.Type.ROW).fillWithTestValues().transpose()
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
            new TestVector(3).fillWithTestValues().invert()
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
            new TestVector(3).fillWithTestValues().maxNorm()
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
            new TestVector(3).fillWithTestValues().euclideanNorm()
        );
    }

    @Test
    void negativePNorm() {
        assertThrows(IllegalArgumentException.class,
            () -> new TestVector(3).pNorm(-1)
        ); // assert exception message?
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
            new TestVector(3).fillWithTestValues().pNorm(7)
        );
    }

    @Test
    void unitVectorOfEmptyVector() {
        assertThrows(NotSupportedException.class,
            () -> new TestVector(3).normalize()
        ); // assert exception message?
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
            new TestVector(3).fillWithTestValues().normalize()
        );
    }

    // endregion

    // region angle

    @Test
    void angleBetweenEmptyVectors() {
        assertThrows(NotSupportedException.class,
            () -> new TestVector(3).angle(new TestVector(3))
        ); // assert exception message?
    }

    @Test
    void angleBetweenIdenticalVectors() {
        assertEquals(
            testAbstractArithmetic.zero(),
            new TestVector(3).fillWithTestValues().angle(new TestVector(3).fillWithTestValues())
        );
    }

    @Test
    void angleBetweenParallelVectors() {
        assertEquals(
            testAbstractArithmetic.zero(),
            new TestVector(3).fillWithTestValues().multiply(
                testAbstractArithmetic.fromInt(2)
            ).angle(new TestVector(3).fillWithTestValues().multiply(
                testAbstractArithmetic.fromInt(3)
                )
            )
        );
    }

    @Test
    void angleBetweenOppositeVectors() {
        assertEquals(
            testAbstractArithmetic.fromDouble(Math.PI),
            new TestVector(3).fillWithTestValues().multiply(
                testAbstractArithmetic.fromInt(-2)
            ).angle(new TestVector(3).fillWithTestValues().multiply(
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
            new TestVector(4).fillWithTestValues(),
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
            new TestVector(2).fillWithTestValues(),
            Vector.ofList(
                testAbstractArithmetic,
                list
            )
        );
    }

    // endregion

    // region override

    @Test
    void copyOfVectorWithSize2() {
        TestVector vector = new TestVector(2);
        assertEquals(vector, vector.copy());
    }

    @Test
    void iteratorOfEmptyVector() {
        TestVector vector = new TestVector(2);
        List<TestVector.Entry> values = new ArrayList<>();
        for (TestVector.Entry d : vector) {
            values.add(d);
            assertEquals(0d, d.getValue());
        }
        assertEquals(vector.getSize(), values.size());
    }

    @Test
    void streamOfEmptyVector() {
        TestVector vector = new TestVector(2);
        assertEquals(2, vector.stream().count());
    }

    @Test
    void parallelStreamOfEmptyVector() {
        TestVector vector = new TestVector(2);
        assertEquals(2, vector.parallelStream().count());
    }

    @Test
    void equalsOfTestVectorWithSize2() {
        TestVector vector = new TestVector(2);
        assertEquals(vector, new TestVector(2));
        assertNotEquals(vector, new TestVector(3));
    }

    @Test
    void equalsOfTestVectorWithDifferentDefaults() {
        TestVector default0 = new TestVector(2);
        default0.forEach(field -> default0.setValue(field.getIndex(), 1d));
        assertNotEquals(default0, new TestVector(2));
        assertEquals(default0, new TestVector(2, 1d));
    }

    @Test
    void hashCodeOfTestVectorWithSize2() {
        // hashCode changing after every start
        assertEquals(
            new TestVector(2).hashCode(),
            new TestVector(2).hashCode()
        );
    }

    @Test
    void toStringOfTestVectorWithSize2() {
        TestVector vector = new TestVector(2);
        assertEquals("2: []", vector.toString());
    }

    @Test
    void serializable() {
        assertSerializable(new TestVector(2), TestVector.class);
    }

    // endregion

    // region protected: validation

    @Test
    void isValidIndexOfVectorWithSize2() {
        TestVector vector = new TestVector(2);
        assertFalse(vector.isValidIndex(-1));
        for (int i = 0; i < vector.getSize(); i++)
            assertTrue(vector.isValidIndex(i));
        assertFalse(vector.isValidIndex(vector.getSize()));
    }

    @Test
    void isZeroOfEmptyVectorWithSize2() {
        assertTrue(new TestVector(2).isZero());
    }

    @Test
    void isZeroOfNonEmptyVectorWithSize2() {
        assertFalse(new TestVector(2).fillWithTestValues().isZero());
    }

    // endregion

    // region entry

    @Test
    void entryGetter() {
        TestVector vector = new TestVector(2);
        int index = 0;
        for (Vector<Number>.Entry Entry : vector) {
            assertEquals(index, Entry.getIndex());
            assertEquals(vector, Entry.getVector());
            index++;
        }
    }

    @Test
    void entryOverride() {
        TestVector vector = new TestVector(2);
        Vector<Number>.Entry previous = null;
        for (Vector<Number>.Entry entry : vector) {
            if (previous != null) assertNotEquals(previous, entry);
            else assertEquals(
                vector.new Entry(entry.getIndex(), entry.getValue()),
                entry
            );
            assertTrue(0 < entry.hashCode());
            assertEquals(
                entry.getIndex() + ": " + entry.getValue(),
                entry.toString()
            );
            previous = entry;
        }

        assertThrows(IndexOutOfBoundsException.class,
            () -> vector.new Entry(vector.getSize(), 0)
        ); // assert exception message?
    }

    // endregion
}
