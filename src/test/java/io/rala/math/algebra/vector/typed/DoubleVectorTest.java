package io.rala.math.algebra.vector.typed;

import io.rala.math.MathX;
import io.rala.math.algebra.matrix.typed.DoubleMatrix;
import io.rala.math.algebra.vector.Vector;
import io.rala.math.exception.NotSupportedException;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.rala.math.testUtils.algebra.TestVector.fillVectorWithTestValues;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static io.rala.math.testUtils.assertion.VectorAssertions.assertVector;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DoubleVectorTest {
    // region constructors

    @Test
    void constructorWithNegativeSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleVector(-1))
            .withMessage(ExceptionMessages.SIZE_HAS_TO_BE_GREATER_ZERO);
    }

    @Test
    void constructorWithZeroSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleVector(0))
            .withMessage(ExceptionMessages.SIZE_HAS_TO_BE_GREATER_ZERO);
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
        assertThat(new DoubleVector(3).length()).isEqualTo(0d);
    }

    @Test
    void createWithLength3AndAssertLengthEquals98() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).length()).isEqualTo(Math.sqrt(98));
    }

    // endregion

    // region value

    @Test
    void getValueByIndexMinus1() {
        DoubleVector vector = new DoubleVector(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> vector.getValue(-1))
            .withMessage("-1 / 2");
    }

    @Test
    void setValueByIndexMinus1() {
        DoubleVector vector = new DoubleVector(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> vector.setValue(-1, 0d))
            .withMessage("-1 / 2");
    }

    @Test
    void setValueByIndex0WhichWasUnset() {
        DoubleVector vector = new DoubleVector(2);
        assertThat(vector.setValue(0, 1d)).isEqualTo(0d);
        assertThat(vector.getValue(0)).isOne();
    }

    @Test
    void setValueByIndex0WhichWasSet() {
        DoubleVector vector = new DoubleVector(2);
        vector.setValue(0, 1d);
        assertThat(vector.setValue(0, 2d)).isEqualTo(1d);
        assertThat(vector.getValue(0)).isEqualTo(2);
    }

    @Test
    void removeValueByIndexMinus1() {
        DoubleVector vector = new DoubleVector(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> vector.removeValue(-1))
            .withMessage("-1 / 2");
    }

    @Test
    void removeValueByIndex0WhichWasUnset() {
        DoubleVector vector = new DoubleVector(2);
        assertThat(vector.removeValue(0)).isEqualTo(0d);
        assertThat(vector.getValue(0)).isEqualTo(0d);
    }

    @Test
    void removeValueByIndex0WhichWasSet() {
        DoubleVector vector = new DoubleVector(2);
        vector.setValue(0, 1d);
        assertThat(vector.removeValue(0)).isEqualTo(1d);
        assertThat(vector.getValue(0)).isEqualTo(0d);
    }

    // endregion

    // region compute

    @Test
    void computeFirstEntryTimesTwoUnary() {
        DoubleVector vector = fillVectorWithTestValues(new DoubleVector(3));
        assertThat(vector.compute(0, x -> vector.getArithmetic().product(2d, x))).isEqualTo(1d);
        assertThat(vector).isEqualTo(DoubleVector.ofValues(vector.getArithmetic(), 2d, -4d, 9d));
    }

    @Test
    void computeFirstEntryTimesTwoBinary() {
        DoubleVector vector = fillVectorWithTestValues(new DoubleVector(3));
        assertThat(vector.compute(0, 2d, vector.getArithmetic()::product)).isEqualTo(1d);
        assertThat(vector).isEqualTo(DoubleVector.ofValues(vector.getArithmetic(), 2d, -4d, 9d));
    }

    @Test
    void computeTimesTwoUnary() {
        DoubleVector vector = fillVectorWithTestValues(new DoubleVector(3));
        vector.computeAll(
            x -> vector.getArithmetic().product(2d, x.getValue())
        );
        assertThat(vector).isEqualTo(DoubleVector.ofValues(vector.getArithmetic(), 2d, -8d, 18d));
    }

    @Test
    void computeTimesTwoBinary() {
        DoubleVector vector = fillVectorWithTestValues(new DoubleVector(3));
        vector.computeAll(x -> 2d, vector.getArithmetic()::product);
        assertThat(vector).isEqualTo(DoubleVector.ofValues(vector.getArithmetic(), 2d, -8d, 18d));
    }

    // endregion

    // region to Matrix and toParam

    @Test
    void toMatrixOfVectorWithEmptyColumn() {
        assertThat(new DoubleVector(3).toMatrix()).isEqualTo(new DoubleMatrix(3, 1));
    }

    @Test
    void toMatrixOfVectorWithEmptyRow() {
        assertThat(new DoubleVector(3, Vector.Type.ROW).toMatrix()).isEqualTo(new DoubleMatrix(1, 3));
    }

    @Test
    void toMatrixOfNonEmptyVectorWithSize1() {
        assertThat(new DoubleVector(1).toMatrix()).isEqualTo(new DoubleMatrix(1));
    }

    @Test
    void toMatrixOfVectorWithNonEmptyColumn() {
        DoubleMatrix expected = new DoubleMatrix(4, 1);
        expected.setValue(0, 0, 1d);
        expected.setValue(1, 0, -4d);
        expected.setValue(2, 0, 9d);
        expected.setValue(3, 0, -16d);
        assertThat(fillVectorWithTestValues(new DoubleVector(4)).toMatrix()).isEqualTo(expected);
    }

    @Test
    void toMatrixOfVectorWithNonEmptyRow() {
        DoubleMatrix expected = new DoubleMatrix(1, 4);
        expected.setValue(0, 0, 1d);
        expected.setValue(0, 1, -4d);
        expected.setValue(0, 2, 9d);
        expected.setValue(0, 3, -16d);
        assertThat(fillVectorWithTestValues(new DoubleVector(4, Vector.Type.ROW)).toMatrix())
            .isEqualTo(expected);
    }

    @Test
    void toParamOfEmptyVector() {
        assertThat(new DoubleVector(1).toParam()).isEqualTo(0d);
    }

    @Test
    void toParamOfNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(1)).toParam()).isEqualTo(1d);
    }

    @Test
    void toParamOFEmptyVectorWithInvalidSize() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new DoubleVector(2).toParam())
            .withMessage(ExceptionMessages.SIZE_HAS_TO_BE_ONE);
    }

    // endregion

    // region add, subtract, multiply and dotProduct

    @Test
    void addVectorDifferentSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleVector(2).add(new DoubleVector(3)))
            .withMessage(ExceptionMessages.SIZES_HAVE_TO_BE_EQUAL);
    }

    @Test
    void addVectorDifferentType() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleVector(2).add(new DoubleVector(2, Vector.Type.ROW)))
            .withMessage(ExceptionMessages.VECTOR_TYPES_HAVE_TO_BE_EQUAL);
    }

    @Test
    void addEmptyVectorToEmptyVector() {
        assertThat(new DoubleVector(3).add(new DoubleVector(3))).isEqualTo(new DoubleVector(3));
    }

    @Test
    void addEmptyVectorToNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).add(new DoubleVector(3)))
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3)));
    }

    @Test
    void addNonEmptyVectorToEmptyVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3).add(new DoubleVector(3))))
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3)));
    }

    @Test
    void addNonEmptyVectorToNonEmptyVector() {
        DoubleVector expected = new DoubleVector(3);
        expected.setValue(0, 2d);
        expected.setValue(1, -8d);
        expected.setValue(2, 18d);
        assertThat(fillVectorWithTestValues(new DoubleVector(3))
            .add(fillVectorWithTestValues(new DoubleVector(3)))).isEqualTo(expected);
    }

    @Test
    void subtractVectorDifferentSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleVector(2).subtract(new DoubleVector(3)))
            .withMessage(ExceptionMessages.SIZES_HAVE_TO_BE_EQUAL);
    }

    @Test
    void subtractVectorDifferentType() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleVector(2).subtract(new DoubleVector(2, Vector.Type.ROW)))
            .withMessage(ExceptionMessages.VECTOR_TYPES_HAVE_TO_BE_EQUAL);
    }

    @Test
    void subtractEmptyVectorFromEmptyVector() {
        assertThat(new DoubleVector(3).subtract(new DoubleVector(3))).isEqualTo(new DoubleVector(3));
    }

    @Test
    void subtractEmptyVectorFromNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).subtract(new DoubleVector(3)))
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3)));
    }

    @Test
    void subtractNonEmptyVectorFromEmptyVector() {
        assertThat(new DoubleVector(3).subtract(fillVectorWithTestValues(new DoubleVector(3))))
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3)).invert());
    }

    @Test
    void subtractNonEmptyVectorFromNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3))
            .subtract(fillVectorWithTestValues(new DoubleVector(3)))).isEqualTo(new DoubleVector(3));
    }

    @Test
    void multiplyNonEmptyVectorByZero() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).multiply(0d))
            .isEqualTo(new DoubleVector(3));
    }

    @Test
    void multiplyNonEmptyVectorByOne() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).multiply(1d))
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3)));
    }

    @Test
    void multiplyNonEmptyVectorByFive() {
        DoubleVector expected = new DoubleVector(3);
        expected.setValue(0, 5d);
        expected.setValue(1, -20d);
        expected.setValue(2, 45d);
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).multiply(5d)).isEqualTo(expected);
    }

    @Test
    void multiplyVectorsDifferentSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleVector(3, Vector.Type.ROW).multiply(new DoubleVector(4)))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL_ROWS);
    }

    @Test
    void multiplyVectorsSameTypeColumn() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleVector(3).multiply(new DoubleVector(3)))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL_ROWS);
    }

    @Test
    void multiplyVectorsSameTypeRow() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleVector(3, Vector.Type.ROW)
                .multiply(new DoubleVector(3, Vector.Type.ROW)))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL_ROWS);
    }

    @Test
    void multiplyEmptyVectorsColumnRow() {
        assertThat(new DoubleVector(2).multiply(new DoubleVector(2, Vector.Type.ROW)))
            .isEqualTo(new DoubleMatrix(2));
    }

    @Test
    void multiplyEmptyVectorsRowColumn() {
        assertThat(new DoubleVector(2, Vector.Type.ROW).multiply(new DoubleVector(2)))
            .isEqualTo(new DoubleMatrix(1));
    }

    @Test
    void multiplyNonEmptyVectorsColumnRow() {
        DoubleMatrix expected = new DoubleMatrix(2);
        expected.setValue(0, 0, 1d);
        expected.setValue(0, 1, -4d);
        expected.setValue(1, 0, -4d);
        expected.setValue(1, 1, 16d);
        assertThat(fillVectorWithTestValues(new DoubleVector(2))
            .multiply(fillVectorWithTestValues(new DoubleVector(2, Vector.Type.ROW)))).isEqualTo(expected);
    }

    @Test
    void multiplyNonEmptyVectorsRowColumn() {
        DoubleMatrix expected = new DoubleMatrix(1);
        expected.setValue(0, 0, 17d);
        assertThat(fillVectorWithTestValues(new DoubleVector(2, Vector.Type.ROW))
            .multiply(fillVectorWithTestValues(new DoubleVector(2)))).isEqualTo(expected);
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorColumnRow() {
        assertThat(fillVectorWithTestValues(new DoubleVector(2))
            .multiply(new DoubleVector(2, Vector.Type.ROW))).isEqualTo(new DoubleMatrix(2));
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorRowColumn() {
        assertThat(fillVectorWithTestValues(new DoubleVector(2, Vector.Type.ROW))
            .multiply(new DoubleVector(2))).isEqualTo(new DoubleMatrix(1));
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorColumnRow() {
        assertThat(new DoubleVector(2)
            .multiply(fillVectorWithTestValues(new DoubleVector(2, Vector.Type.ROW)))
        ).isEqualTo(new DoubleMatrix(2));
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorRowColumn() {
        assertThat(new DoubleVector(2, Vector.Type.ROW)
            .multiply(fillVectorWithTestValues(new DoubleVector(2)))).isEqualTo(new DoubleMatrix(1));
    }

    @Test
    void dotProductEmptyColumnVectors() {
        assertThat(new DoubleVector(3)
            .dotProduct(new DoubleVector(3))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyColumnVectors() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3))
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3)))).isEqualTo(98d);
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyColumnVector() {
        assertThat(new DoubleVector(3)
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3)))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3))
            .dotProduct(new DoubleVector(3))).isEqualTo(0d);
    }

    @Test
    void dotProductEmptyColumnVectorEmptyRowVector() {
        assertThat(new DoubleVector(3)
            .dotProduct(new DoubleVector(3, Vector.Type.ROW))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyColumnVectorNonEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3))
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)))).isEqualTo(98d);
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyRowVector() {
        assertThat(new DoubleVector(3)
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3))
            .dotProduct(new DoubleVector(3, Vector.Type.ROW))).isEqualTo(0d);
    }

    @Test
    void dotProductEmptyRowVectorEmptyColumnVector() {
        assertThat(new DoubleVector(3, Vector.Type.ROW)
            .dotProduct(new DoubleVector(3))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyRowVectorNonEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW))
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3)))).isEqualTo(98d);
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyColumnVector() {
        assertThat(new DoubleVector(3, Vector.Type.ROW)
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3)))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW))
            .dotProduct(new DoubleVector(3))).isEqualTo(0d);
    }

    @Test
    void dotProductEmptyRowVectors() {
        assertThat(new DoubleVector(3, Vector.Type.ROW)
            .dotProduct(new DoubleVector(3, Vector.Type.ROW))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyRowVectors() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW))
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)))).isEqualTo(98d);
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyRowVector() {
        assertThat(new DoubleVector(3, Vector.Type.ROW)
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW))
            .dotProduct(new DoubleVector(3, Vector.Type.ROW))).isEqualTo(0d);
    }

    // endregion

    // region transpose and invert

    @Test
    void transposeEmptyColumnVector() {
        assertThat(new DoubleVector(3).transpose()).isEqualTo(new DoubleVector(3, Vector.Type.ROW));
    }

    @Test
    void transposeEmptyRowVector() {
        assertThat(new DoubleVector(3, Vector.Type.ROW).transpose()).isEqualTo(new DoubleVector(3));
    }

    @Test
    void transposeNonEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).transpose())
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)));
    }

    @Test
    void transposeNonEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)).transpose())
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3)));
    }

    @Test
    void invertEmptyVector() {
        DoubleVector expected = new DoubleVector(3);
        for (int i = 0; i < 3; i++) {
            expected.setValue(i, -0d);
        }
        assertThat(new DoubleVector(3).invert()).isEqualTo(expected);
    }

    @Test
    void invertNonEmptyVector() {
        DoubleVector expected = new DoubleVector(3);
        expected.setValue(0, -1d);
        expected.setValue(1, 4d);
        expected.setValue(2, -9d);
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).invert()).isEqualTo(expected);
    }

    // endregion

    // region norm and unit

    @Test
    void maxNormEmptyVector() {
        assertThat(new DoubleVector(3).maxNorm()).isEqualTo(0d);
    }

    @Test
    void maxNormNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).maxNorm()).isEqualTo(9d);
    }

    @Test
    void euclideanNormEmptyVector() {
        assertThat(new DoubleVector(3).euclideanNorm()).isEqualTo(0d);
    }

    @Test
    void euclideanNormNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).euclideanNorm()).isEqualTo(Math.sqrt(98));
    }

    @Test
    void negativePNorm() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DoubleVector(3).pNorm(-1))
            .withMessage(ExceptionMessages.VECTOR_POSITIVE_P_NORM);
    }

    @Test
    void sevenNormEmptyVector() {
        assertThat(new DoubleVector(3).pNorm(7)).isEqualTo(0d);
    }

    @Test
    void sevenNormNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).pNorm(7))
            .isEqualTo(MathX.root(4766586d, 7));
    }

    @Test
    void unitVectorOfEmptyVector() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new DoubleVector(3).normalize())
            .withMessage(ExceptionMessages.VECTOR_ZERO_VECTOR_CAN_NOT_NORMALIZED);
    }

    @Test
    void unitVectorOfNonEmptyVector() {
        DoubleVector expected = new DoubleVector(3);
        expected.setValue(0, 1 / Math.sqrt(98));
        expected.setValue(1, -4 / Math.sqrt(98));
        expected.setValue(2, 9 / Math.sqrt(98));
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).normalize()).isEqualTo(expected);
    }

    // endregion

    // region angle

    @Test
    void angleBetweenEmptyVectors() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new DoubleVector(3).angle(new DoubleVector(3)))
            .withMessage(ExceptionMessages.VECTOR_ANGLE_NOT_DEFINED_FOR_ZERO_VECTOR);
    }

    @Test
    void angleBetweenIdenticalVectors() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3))
            .angle(fillVectorWithTestValues(new DoubleVector(3)))).isEqualTo(0d);
    }

    @Test
    void angleBetweenParallelVectors() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).multiply(2d)
            .angle(fillVectorWithTestValues(new DoubleVector(3)).multiply(3d))).isEqualTo(0d);
    }

    @Test
    void angleBetweenOppositeVectors() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).multiply(-2d)
            .angle(fillVectorWithTestValues(new DoubleVector(3)).multiply(3d))).isEqualTo(Math.PI);
    }

    @Test
    void angleBetweenNonEmptyVectors() {
        assertThat(DoubleVector.ofValues(0, 1, 0)
            .angle(DoubleVector.ofValues(0, 0, 1))).isEqualTo(Math.PI / 2);
    }

    // endregion

    // region static

    @Test
    void ofValuesWithZeroValues() {
        assertThat(DoubleVector.ofValues(0d, 0d, 0d, 0d)).isEqualTo(new DoubleVector(4));
    }

    @Test
    void ofValuesWithNonZeroValues() {
        assertThat(DoubleVector.ofValues(1d, -4d, 9d, -16d))
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(4)));
    }

    @Test
    void ofListWithZeroValues() {
        List<Double> list = new ArrayList<>();
        list.add(0d);
        list.add(0d);
        assertThat(DoubleVector.ofList(list)).isEqualTo(new DoubleVector(2));
    }

    @Test
    void ofListWithNonZeroValues() {
        List<Double> list = new ArrayList<>();
        list.add(1d);
        list.add(-4d);
        assertThat(DoubleVector.ofList(list)).isEqualTo(fillVectorWithTestValues(new DoubleVector(2)));
    }

    // endregion

    // region override

    @Test
    void copyOfVectorWithSize2() {
        DoubleVector vector = new DoubleVector(2);
        assertThat(vector.copy()).isEqualTo(vector);
    }

    @Test
    void iteratorOfEmptyVector() {
        DoubleVector vector = new DoubleVector(2);
        List<DoubleVector.Entry> values = new ArrayList<>();
        for (DoubleVector.Entry d : vector) {
            values.add(d);
            assertThat(d.getValue()).isEqualTo(0d);
        }
        assertThat(values).hasSize(vector.getSize());
    }

    @Test
    void streamOfEmptyVector() {
        DoubleVector vector = new DoubleVector(2);
        assertThat(vector.stream().count()).isEqualTo(2);
    }

    @Test
    void parallelStreamOfEmptyVector() {
        DoubleVector vector = new DoubleVector(2);
        assertThat(vector.parallelStream().count()).isEqualTo(2);
    }

    @Test
    void equalsOfDoubleVectorWithSize2() {
        assertThat(new DoubleVector(2))
            .isEqualTo(new DoubleVector(2))
            .isNotEqualTo(new DoubleVector(3));
    }

    @Test
    void hashCodeOfDoubleVectorWithSize2() {
        // hashCode changing after every start
        assertThat(new DoubleVector(2)).hasSameHashCodeAs(new DoubleVector(2));
    }

    @Test
    void toStringOfDoubleVectorWithSize2() {
        DoubleVector vector = new DoubleVector(2);
        assertThat(vector).hasToString("2: []");
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleVector(2), DoubleVector.class);
    }

    // endregion
}
