package io.rala.math.algebra.vector.typed;

import io.rala.math.MathX;
import io.rala.math.algebra.matrix.typed.DoubleMatrix;
import io.rala.math.algebra.vector.Vector;
import io.rala.math.exception.NotSupportedException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.rala.math.testUtils.algebra.TestVector.fillVectorWithTestValues;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static io.rala.math.testUtils.assertion.VectorAssertions.assertVector;
import static org.junit.jupiter.api.Assertions.*;

public class DoubleVectorTest {
    // region constructors

    @Test
    void constructorWithNegativeSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new DoubleVector(-1)
        ); // assert exception message?
    }

    @Test
    void constructorWithZeroSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new DoubleVector(0)
        ); // assert exception message?
    }

    @Test
    void constructorWithPositiveSize() {
        assertVector(new DoubleVector(1), 1);
    }

    @Test
    void constructorWithPositiveSizeAndType() {
        assertVector(new DoubleVector(2, Vector.Type.ROW), 2, Vector.Type.ROW);
    }

    @Test
    void constructorWithPositiveSizeAndTypeNull() {
        assertVector(new DoubleVector(2, null), 2);
    }

    @Test
    void constructorWithVector() {
        DoubleVector vector = new DoubleVector(new DoubleVector(1));
        assertVector(vector, 1);
    }

    // endregion

    // region getter and length

    @Test
    void createWithLength0AndAssertLengthEquals0() {
        assertEquals(0d, new DoubleVector(3).length());
    }

    @Test
    void createWithLength3AndAssertLengthEquals98() {
        assertEquals(
            Math.sqrt(98),
            fillVectorWithTestValues(new DoubleVector(3)).length()
        );
    }

    // endregion

    // region value

    @Test
    void getValueByIndexMinus1() {
        DoubleVector vector = new DoubleVector(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> vector.getValue(-1)
        ); // assert exception message?
    }

    @Test
    void setValueByIndexMinus1() {
        DoubleVector vector = new DoubleVector(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> vector.setValue(-1, 0d)
        ); // assert exception message?
    }

    @Test
    void setValueByIndex0WhichWasUnset() {
        DoubleVector vector = new DoubleVector(2);
        assertEquals(0d, vector.setValue(0, 1d));
        assertEquals(1, vector.getValue(0));
    }

    @Test
    void setValueByIndex0WhichWasSet() {
        DoubleVector vector = new DoubleVector(2);
        vector.setValue(0, 1d);
        assertEquals(1d, vector.setValue(0, 2d));
        assertEquals(2, vector.getValue(0));
    }

    @Test
    void removeValueByIndexMinus1() {
        DoubleVector vector = new DoubleVector(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> vector.removeValue(-1)
        ); // assert exception message?
    }

    @Test
    void removeValueByIndex0WhichWasUnset() {
        DoubleVector vector = new DoubleVector(2);
        assertEquals(0d, vector.removeValue(0));
        assertEquals(0d, vector.getValue(0));
    }

    @Test
    void removeValueByIndex0WhichWasSet() {
        DoubleVector vector = new DoubleVector(2);
        vector.setValue(0, 1d);
        assertEquals(1d, vector.removeValue(0));
        assertEquals(0d, vector.getValue(0));
    }

    // endregion

    // region compute

    @Test
    void computeFirstEntryTimesTwoUnary() {
        DoubleVector vector = fillVectorWithTestValues(new DoubleVector(3));
        assertEquals(
            1d,
            vector.compute(0, x -> vector.getArithmetic().product(2d, x))
        );
        assertEquals(
            DoubleVector.ofValues(vector.getArithmetic(), 2d, -4d, 9d),
            vector
        );
    }

    @Test
    void computeFirstEntryTimesTwoBinary() {
        DoubleVector vector = fillVectorWithTestValues(new DoubleVector(3));
        assertEquals(
            1d,
            vector.compute(0, 2d, vector.getArithmetic()::product)
        );
        assertEquals(
            DoubleVector.ofValues(vector.getArithmetic(), 2d, -4d, 9d),
            vector
        );
    }

    @Test
    void computeTimesTwoUnary() {
        DoubleVector vector = fillVectorWithTestValues(new DoubleVector(3));
        vector.computeAll(
            x -> vector.getArithmetic().product(2d, x.getValue())
        );
        assertEquals(
            DoubleVector.ofValues(vector.getArithmetic(), 2d, -8d, 18d),
            vector
        );
    }

    @Test
    void computeTimesTwoBinary() {
        DoubleVector vector = fillVectorWithTestValues(new DoubleVector(3));
        vector.computeAll(x -> 2d, vector.getArithmetic()::product);
        assertEquals(
            DoubleVector.ofValues(vector.getArithmetic(), 2d, -8d, 18d),
            vector
        );
    }

    // endregion

    // region to Matrix and toParam

    @Test
    void toMatrixOfVectorWithEmptyColumn() {
        assertEquals(
            new DoubleMatrix(3, 1),
            new DoubleVector(3).toMatrix()
        );
    }

    @Test
    void toMatrixOfVectorWithEmptyRow() {
        assertEquals(
            new DoubleMatrix(1, 3),
            new DoubleVector(3, Vector.Type.ROW).toMatrix()
        );
    }

    @Test
    void toMatrixOfNonEmptyVectorWithSize1() {
        assertEquals(
            new DoubleMatrix(1),
            new DoubleVector(1).toMatrix()
        );
    }

    @Test
    void toMatrixOfVectorWithNonEmptyColumn() {
        DoubleMatrix expected = new DoubleMatrix(4, 1);
        expected.setValue(0, 0, 1d);
        expected.setValue(1, 0, -4d);
        expected.setValue(2, 0, 9d);
        expected.setValue(3, 0, -16d);
        assertEquals(
            expected,
            fillVectorWithTestValues(new DoubleVector(4)).toMatrix()
        );
    }

    @Test
    void toMatrixOfVectorWithNonEmptyRow() {
        DoubleMatrix expected = new DoubleMatrix(1, 4);
        expected.setValue(0, 0, 1d);
        expected.setValue(0, 1, -4d);
        expected.setValue(0, 2, 9d);
        expected.setValue(0, 3, -16d);
        assertEquals(
            expected,
            fillVectorWithTestValues(new DoubleVector(4, Vector.Type.ROW)).toMatrix()
        );
    }

    @Test
    void toParamOfEmptyVector() {
        assertEquals(
            0d,
            new DoubleVector(1).toParam()
        );
    }

    @Test
    void toParamOfNonEmptyVector() {
        assertEquals(
            1d,
            fillVectorWithTestValues(new DoubleVector(1)).toParam()
        );
    }

    @Test
    void toParamOFEmptyVectorWithInvalidSize() {
        assertThrows(NotSupportedException.class,
            () -> new DoubleVector(2).toParam()
        ); // assert exception message?
    }

    // endregion

    // region add, subtract, multiply and dotProduct

    @Test
    void addVectorDifferentSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new DoubleVector(2).add(new DoubleVector(3))
        ); // assert exception message?
    }

    @Test
    void addVectorDifferentType() {
        assertThrows(IllegalArgumentException.class,
            () -> new DoubleVector(2).add(new DoubleVector(2, Vector.Type.ROW))
        ); // assert exception message?
    }

    @Test
    void addEmptyVectorToEmptyVector() {
        assertEquals(
            new DoubleVector(3),
            new DoubleVector(3).add(new DoubleVector(3))
        );
    }

    @Test
    void addEmptyVectorToNonEmptyVector() {
        assertEquals(
            fillVectorWithTestValues(new DoubleVector(3)),
            fillVectorWithTestValues(new DoubleVector(3)).add(new DoubleVector(3))
        );
    }

    @Test
    void addNonEmptyVectorToEmptyVector() {
        assertEquals(
            fillVectorWithTestValues(new DoubleVector(3)),
            fillVectorWithTestValues(new DoubleVector(3).add(new DoubleVector(3)))
        );
    }

    @Test
    void addNonEmptyVectorToNonEmptyVector() {
        DoubleVector expected = new DoubleVector(3);
        expected.setValue(0, 2d);
        expected.setValue(1, -8d);
        expected.setValue(2, 18d);
        assertEquals(
            expected,
            fillVectorWithTestValues(new DoubleVector(3))
                .add(fillVectorWithTestValues(new DoubleVector(3)))
        );
    }

    @Test
    void subtractVectorDifferentSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new DoubleVector(2).subtract(new DoubleVector(3))
        ); // assert exception message?
    }

    @Test
    void subtractVectorDifferentType() {
        assertThrows(IllegalArgumentException.class,
            () -> new DoubleVector(2).subtract(new DoubleVector(2, Vector.Type.ROW))
        ); // assert exception message?
    }

    @Test
    void subtractEmptyVectorFromEmptyVector() {
        assertEquals(
            new DoubleVector(3),
            new DoubleVector(3).subtract(new DoubleVector(3))
        );
    }

    @Test
    void subtractEmptyVectorFromNonEmptyVector() {
        assertEquals(
            fillVectorWithTestValues(new DoubleVector(3)),
            fillVectorWithTestValues(new DoubleVector(3)).subtract(new DoubleVector(3))
        );
    }

    @Test
    void subtractNonEmptyVectorFromEmptyVector() {
        assertEquals(
            fillVectorWithTestValues(new DoubleVector(3)).invert(),
            new DoubleVector(3).subtract(fillVectorWithTestValues(new DoubleVector(3)))
        );
    }

    @Test
    void subtractNonEmptyVectorFromNonEmptyVector() {
        assertEquals(
            new DoubleVector(3),
            fillVectorWithTestValues(new DoubleVector(3))
                .subtract(fillVectorWithTestValues(new DoubleVector(3)))
        );
    }

    @Test
    void multiplyNonEmptyVectorByZero() {
        assertEquals(
            new DoubleVector(3),
            fillVectorWithTestValues(new DoubleVector(3)).multiply(0d)
        );
    }

    @Test
    void multiplyNonEmptyVectorByOne() {
        assertEquals(
            fillVectorWithTestValues(new DoubleVector(3)),
            fillVectorWithTestValues(new DoubleVector(3)).multiply(1d)
        );
    }

    @Test
    void multiplyNonEmptyVectorByFive() {
        DoubleVector expected = new DoubleVector(3);
        expected.setValue(0, 5d);
        expected.setValue(1, -20d);
        expected.setValue(2, 45d);
        assertEquals(
            expected,
            fillVectorWithTestValues(new DoubleVector(3)).multiply(5d)
        );
    }

    @Test
    void multiplyVectorsDifferentSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new DoubleVector(3, Vector.Type.ROW).multiply(new DoubleVector(4))
        ); // assert exception message?
    }

    @Test
    void multiplyVectorsSameTypeColumn() {
        assertThrows(IllegalArgumentException.class,
            () -> new DoubleVector(3).multiply(new DoubleVector(3))
        ); // assert exception message?
    }

    @Test
    void multiplyVectorsSameTypeRow() {
        assertThrows(IllegalArgumentException.class,
            () -> new DoubleVector(3, Vector.Type.ROW)
                .multiply(new DoubleVector(3, Vector.Type.ROW))
        ); // assert exception message?
    }

    @Test
    void multiplyEmptyVectorsColumnRow() {
        assertEquals(
            new DoubleMatrix(2),
            new DoubleVector(2).multiply(new DoubleVector(2, Vector.Type.ROW))
        );
    }

    @Test
    void multiplyEmptyVectorsRowColumn() {
        assertEquals(
            new DoubleMatrix(1),
            new DoubleVector(2, Vector.Type.ROW).multiply(new DoubleVector(2))
        );
    }

    @Test
    void multiplyNonEmptyVectorsColumnRow() {
        DoubleMatrix expected = new DoubleMatrix(2);
        expected.setValue(0, 0, 1d);
        expected.setValue(0, 1, -4d);
        expected.setValue(1, 0, -4d);
        expected.setValue(1, 1, 16d);
        assertEquals(
            expected,
            fillVectorWithTestValues(new DoubleVector(2))
                .multiply(fillVectorWithTestValues(new DoubleVector(2, Vector.Type.ROW)))
        );
    }

    @Test
    void multiplyNonEmptyVectorsRowColumn() {
        DoubleMatrix expected = new DoubleMatrix(1);
        expected.setValue(0, 0, 17d);
        assertEquals(
            expected,
            fillVectorWithTestValues(new DoubleVector(2, Vector.Type.ROW))
                .multiply(fillVectorWithTestValues(new DoubleVector(2)))
        );
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorColumnRow() {
        assertEquals(
            new DoubleMatrix(2),
            fillVectorWithTestValues(new DoubleVector(2))
                .multiply(new DoubleVector(2, Vector.Type.ROW))
        );
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorRowColumn() {
        assertEquals(
            new DoubleMatrix(1),
            fillVectorWithTestValues(new DoubleVector(2, Vector.Type.ROW))
                .multiply(new DoubleVector(2))
        );
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorColumnRow() {
        assertEquals(
            new DoubleMatrix(2),
            new DoubleVector(2)
                .multiply(fillVectorWithTestValues(new DoubleVector(2, Vector.Type.ROW)))
        );
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorRowColumn() {
        assertEquals(
            new DoubleMatrix(1),
            new DoubleVector(2, Vector.Type.ROW)
                .multiply(fillVectorWithTestValues(new DoubleVector(2)))
        );
    }

    @Test
    void dotProductEmptyColumnVectors() {
        assertEquals(
            0d,
            new DoubleVector(3)
                .dotProduct(new DoubleVector(3))
        );
    }

    @Test
    void dotProductNonEmptyColumnVectors() {
        assertEquals(
            98d,
            fillVectorWithTestValues(new DoubleVector(3))
                .dotProduct(fillVectorWithTestValues(new DoubleVector(3)))
        );
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyColumnVector() {
        assertEquals(
            0d,
            new DoubleVector(3)
                .dotProduct(fillVectorWithTestValues(new DoubleVector(3)))
        );
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyColumnVector() {
        assertEquals(
            0d,
            fillVectorWithTestValues(new DoubleVector(3))
                .dotProduct(new DoubleVector(3))
        );
    }

    @Test
    void dotProductEmptyColumnVectorEmptyRowVector() {
        assertEquals(
            0d,
            new DoubleVector(3)
                .dotProduct(new DoubleVector(3, Vector.Type.ROW))
        );
    }

    @Test
    void dotProductNonEmptyColumnVectorNonEmptyRowVector() {
        assertEquals(
            98d,
            fillVectorWithTestValues(new DoubleVector(3))
                .dotProduct(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)))
        );
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyRowVector() {
        assertEquals(
            0d,
            new DoubleVector(3)
                .dotProduct(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)))
        );
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyRowVector() {
        assertEquals(
            0d,
            fillVectorWithTestValues(new DoubleVector(3))
                .dotProduct(new DoubleVector(3, Vector.Type.ROW))
        );
    }

    @Test
    void dotProductEmptyRowVectorEmptyColumnVector() {
        assertEquals(
            0d,
            new DoubleVector(3, Vector.Type.ROW)
                .dotProduct(new DoubleVector(3))
        );
    }

    @Test
    void dotProductNonEmptyRowVectorNonEmptyColumnVector() {
        assertEquals(
            98d,
            fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW))
                .dotProduct(fillVectorWithTestValues(new DoubleVector(3)))
        );
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyColumnVector() {
        assertEquals(
            0d,
            new DoubleVector(3, Vector.Type.ROW)
                .dotProduct(fillVectorWithTestValues(new DoubleVector(3)))
        );
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyColumnVector() {
        assertEquals(
            0d,
            fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW))
                .dotProduct(new DoubleVector(3))
        );
    }

    @Test
    void dotProductEmptyRowVectors() {
        assertEquals(
            0d,
            new DoubleVector(3, Vector.Type.ROW)
                .dotProduct(new DoubleVector(3, Vector.Type.ROW))
        );
    }

    @Test
    void dotProductNonEmptyRowVectors() {
        assertEquals(
            98d,
            fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW))
                .dotProduct(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)))
        );
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyRowVector() {
        assertEquals(
            0d,
            new DoubleVector(3, Vector.Type.ROW)
                .dotProduct(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)))
        );
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyRowVector() {
        assertEquals(
            0d,
            fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW))
                .dotProduct(new DoubleVector(3, Vector.Type.ROW))
        );
    }

    // endregion

    // region transpose and invert

    @Test
    void transposeEmptyColumnVector() {
        assertEquals(
            new DoubleVector(3, Vector.Type.ROW),
            new DoubleVector(3).transpose()
        );
    }

    @Test
    void transposeEmptyRowVector() {
        assertEquals(
            new DoubleVector(3),
            new DoubleVector(3, Vector.Type.ROW).transpose()
        );
    }

    @Test
    void transposeNonEmptyColumnVector() {
        assertEquals(
            fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)),
            fillVectorWithTestValues(new DoubleVector(3)).transpose()
        );
    }

    @Test
    void transposeNonEmptyRowVector() {
        assertEquals(
            fillVectorWithTestValues(new DoubleVector(3)),
            fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)).transpose()
        );
    }

    @Test
    void invertEmptyVector() {
        DoubleVector expected = new DoubleVector(3);
        for (int i = 0; i < 3; i++) {
            expected.setValue(i, -0d);
        }
        assertEquals(
            expected,
            new DoubleVector(3).invert()
        );
    }

    @Test
    void invertNonEmptyVector() {
        DoubleVector expected = new DoubleVector(3);
        expected.setValue(0, -1d);
        expected.setValue(1, 4d);
        expected.setValue(2, -9d);
        assertEquals(
            expected,
            fillVectorWithTestValues(new DoubleVector(3)).invert()
        );
    }

    // endregion

    // region norm and unit

    @Test
    void maxNormEmptyVector() {
        assertEquals(
            0d,
            new DoubleVector(3).maxNorm()
        );
    }

    @Test
    void maxNormNonEmptyVector() {
        assertEquals(
            9d,
            fillVectorWithTestValues(new DoubleVector(3)).maxNorm()
        );
    }

    @Test
    void euclideanNormEmptyVector() {
        assertEquals(
            0d,
            new DoubleVector(3).euclideanNorm()
        );
    }

    @Test
    void euclideanNormNonEmptyVector() {
        assertEquals(
            Math.sqrt(98),
            fillVectorWithTestValues(new DoubleVector(3)).euclideanNorm()
        );
    }

    @Test
    void negativePNorm() {
        assertThrows(IllegalArgumentException.class,
            () -> new DoubleVector(3).pNorm(-1)
        ); // assert exception message?
    }

    @Test
    void sevenNormEmptyVector() {
        assertEquals(
            0d,
            new DoubleVector(3).pNorm(7)
        );
    }

    @Test
    void sevenNormNonEmptyVector() {
        assertEquals(
            MathX.root(4766586d, 7),
            fillVectorWithTestValues(new DoubleVector(3)).pNorm(7)
        );
    }

    @Test
    void unitVectorOfEmptyVector() {
        assertThrows(NotSupportedException.class,
            () -> new DoubleVector(3).normalize()
        ); // assert exception message?
    }

    @Test
    void unitVectorOfNonEmptyVector() {
        DoubleVector expected = new DoubleVector(3);
        expected.setValue(0, 1 / Math.sqrt(98));
        expected.setValue(1, -4 / Math.sqrt(98));
        expected.setValue(2, 9 / Math.sqrt(98));
        assertEquals(
            expected,
            fillVectorWithTestValues(new DoubleVector(3)).normalize()
        );
    }

    // endregion

    // region angle

    @Test
    void angleBetweenEmptyVectors() {
        assertThrows(NotSupportedException.class,
            () -> new DoubleVector(3).angle(new DoubleVector(3))
        ); // assert exception message?
    }

    @Test
    void angleBetweenIdenticalVectors() {
        assertEquals(
            0d,
            fillVectorWithTestValues(new DoubleVector(3))
                .angle(fillVectorWithTestValues(new DoubleVector(3)))
        );
    }

    @Test
    void angleBetweenParallelVectors() {
        assertEquals(
            0d,
            fillVectorWithTestValues(new DoubleVector(3)).multiply(2d)
                .angle(fillVectorWithTestValues(new DoubleVector(3)).multiply(3d))
        );
    }

    @Test
    void angleBetweenOppositeVectors() {
        assertEquals(
            Math.PI,
            fillVectorWithTestValues(new DoubleVector(3)).multiply(-2d)
                .angle(fillVectorWithTestValues(new DoubleVector(3)).multiply(3d))
        );
    }

    @Test
    void angleBetweenNonEmptyVectors() {
        assertEquals(
            Math.PI / 2,
            DoubleVector.ofValues(0, 1, 0)
                .angle(DoubleVector.ofValues(0, 0, 1))
        );
    }

    // endregion

    // region static

    @Test
    void ofValuesWithZeroValues() {
        assertEquals(
            new DoubleVector(4),
            DoubleVector.ofValues(0d, 0d, 0d, 0d)
        );
    }

    @Test
    void ofValuesWithNonZeroValues() {
        assertEquals(
            fillVectorWithTestValues(new DoubleVector(4)),
            DoubleVector.ofValues(1d, -4d, 9d, -16d)
        );
    }

    @Test
    void ofListWithZeroValues() {
        List<Double> list = new ArrayList<>();
        list.add(0d);
        list.add(0d);
        assertEquals(
            new DoubleVector(2),
            DoubleVector.ofList(list)
        );
    }

    @Test
    void ofListWithNonZeroValues() {
        List<Double> list = new ArrayList<>();
        list.add(1d);
        list.add(-4d);
        assertEquals(
            fillVectorWithTestValues(new DoubleVector(2)),
            DoubleVector.ofList(list)
        );
    }

    // endregion

    // region override

    @Test
    void copyOfVectorWithSize2() {
        DoubleVector vector = new DoubleVector(2);
        assertEquals(vector, vector.copy());
    }

    @Test
    void iteratorOfEmptyVector() {
        DoubleVector vector = new DoubleVector(2);
        List<DoubleVector.Entry> values = new ArrayList<>();
        for (DoubleVector.Entry d : vector) {
            values.add(d);
            assertEquals(0d, d.getValue());
        }
        assertEquals(vector.getSize(), values.size());
    }

    @Test
    void streamOfEmptyVector() {
        DoubleVector vector = new DoubleVector(2);
        assertEquals(2, vector.stream().count());
    }

    @Test
    void parallelStreamOfEmptyVector() {
        DoubleVector vector = new DoubleVector(2);
        assertEquals(2, vector.parallelStream().count());
    }

    @Test
    void equalsOfDoubleVectorWithSize2() {
        DoubleVector vector = new DoubleVector(2);
        assertEquals(vector, new DoubleVector(2));
        assertNotEquals(vector, new DoubleVector(3));
    }

    @Test
    void hashCodeOfDoubleVectorWithSize2() {
        // hashCode changing after every start
        assertEquals(
            new DoubleVector(2).hashCode(),
            new DoubleVector(2).hashCode()
        );
    }

    @Test
    void toStringOfDoubleVectorWithSize2() {
        DoubleVector vector = new DoubleVector(2);
        assertEquals("2: []", vector.toString());
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleVector(2), DoubleVector.class);
    }

    // endregion
}
