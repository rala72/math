package io.rala.math.algebra.vector;

import io.rala.math.MathX;
import io.rala.math.exception.NotSupportedException;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.TestVector;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.rala.math.testUtils.algebra.TestVector.fillVectorWithTestValues;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static io.rala.math.testUtils.assertion.VectorAssertions.assertVector;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class VectorTest {
    // region constructors

    @Test
    void constructorWithNegativeSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestVector(-1))
            .withMessage(ExceptionMessages.SIZE_HAS_TO_BE_GREATER_ZERO);
    }

    @Test
    void constructorWithZeroSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestVector(0))
            .withMessage(ExceptionMessages.SIZE_HAS_TO_BE_GREATER_ZERO);
    }

    @Test
    void constructorWithPositiveSize() {
        assertVector(new TestVector(1), 1);
    }

    @Test
    void constructorWithPositiveSizeAndType() {
        assertVector(new TestVector(2, Vector.Type.ROW), 2, Vector.Type.ROW);
    }

    @Test
    void constructorWithPositiveSizeAndTypeNull() {
        assertVector(new TestVector(2, (Vector.Type) null), 2);
    }

    @Test
    void constructorWithSizeTenAndDefaultValue() {
        TestVector vector = new TestVector(10, 2d);
        assertThat(vector.getDefaultValue()).isEqualTo(2d);
    }

    @Test
    void constructorWithVector() {
        TestVector vector = new TestVector(new TestVector(1, 2d));
        assertVector(vector, 1);
        assertThat(vector.getDefaultValue()).isEqualTo(2d);
    }

    // endregion

    // region getter and length

    @Test
    void createWithLength0AndAssertLengthEquals0() {
        assertThat(new TestVector(3).length()).isEqualTo(0d);
    }

    @Test
    void createWithLength3AndAssertLengthEquals98() {
        assertThat(fillVectorWithTestValues(new TestVector(3)).length()).isEqualTo(Math.sqrt(98));
    }

    // endregion

    // region value

    @Test
    void getValueByIndexMinus1() {
        TestVector vector = new TestVector(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> vector.getValue(-1))
            .withMessage("-1 / 2");
    }

    @Test
    void setValueByIndexMinus1() {
        TestVector vector = new TestVector(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> vector.setValue(-1, 0d))
            .withMessage("-1 / 2");
    }

    @Test
    void setValueByIndex0WhichWasUnset() {
        TestVector vector = new TestVector(2);
        assertThat(vector.setValue(0, 1d)).isEqualTo(0d);
        assertThat(vector.getValue(0)).isEqualTo(1d);
    }

    @Test
    void setValueByIndex0WhichWasSet() {
        TestVector vector = new TestVector(2);
        vector.setValue(0, 1d);
        assertThat(vector.setValue(0, 2d)).isEqualTo(1d);
        assertThat(vector.getValue(0)).isEqualTo(2d);
    }

    @Test
    void removeValueByIndexMinus1() {
        TestVector vector = new TestVector(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> vector.removeValue(-1))
            .withMessage("-1 / 2");
    }

    @Test
    void removeValueByIndex0WhichWasUnset() {
        TestVector vector = new TestVector(2);
        assertThat(vector.removeValue(0)).isEqualTo(0d);
        assertThat(vector.getValue(0)).isEqualTo(0d);
    }

    @Test
    void removeValueByIndex0WhichWasSet() {
        TestVector vector = new TestVector(2);
        vector.setValue(0, 1d);
        assertThat(vector.removeValue(0)).isEqualTo(1d);
        assertThat(vector.getValue(0)).isEqualTo(0d);
    }

    // endregion

    // region compute

    @Test
    void computeFirstEntryTimesTwoUnary() {
        TestVector vector = fillVectorWithTestValues(new TestVector(3));
        assertThat(vector.compute(0, x -> vector.getArithmetic().product(2d, x))).isEqualTo(1d);
        assertThat(vector).isEqualTo(TestVector.ofValues(vector.getArithmetic(), 2d, -4d, 9d));
    }

    @Test
    void computeFirstEntryTimesTwoBinary() {
        TestVector vector = fillVectorWithTestValues(new TestVector(3));
        assertThat(vector.compute(0, 2d, vector.getArithmetic()::product)).isEqualTo(1d);
        assertThat(vector).isEqualTo(TestVector.ofValues(vector.getArithmetic(), 2d, -4d, 9d));
    }

    @Test
    void computeTimesTwoUnary() {
        TestVector vector = fillVectorWithTestValues(new TestVector(3));
        vector.computeAll(
            x -> vector.getArithmetic().product(2d, x.getValue())
        );
        assertThat(vector).isEqualTo(TestVector.ofValues(vector.getArithmetic(), 2d, -8d, 18d));
    }

    @Test
    void computeTimesTwoBinary() {
        TestVector vector = fillVectorWithTestValues(new TestVector(3));
        vector.computeAll(x -> 2d, vector.getArithmetic()::product);
        assertThat(vector).isEqualTo(TestVector.ofValues(vector.getArithmetic(), 2d, -8d, 18d));
    }

    // endregion

    // region toMatrix and toParam

    @Test
    void toMatrixOfVectorWithEmptyColumn() {
        assertThat(new TestVector(3).toMatrix()).isEqualTo(new TestMatrix(3, 1));
    }

    @Test
    void toMatrixOfVectorWithEmptyRow() {
        assertThat(new TestVector(3, Vector.Type.ROW).toMatrix()).isEqualTo(new TestMatrix(1, 3));
    }

    @Test
    void toMatrixOfNonEmptyVectorWithSize1() {
        assertThat(new TestVector(1, 1d).toMatrix()).isEqualTo(new TestMatrix(1, 1d));
    }

    @Test
    void toMatrixOfVectorWithNonEmptyColumn() {
        TestMatrix expected = new TestMatrix(4, 1);
        expected.setValue(0, 0, 1d);
        expected.setValue(1, 0, -4d);
        expected.setValue(2, 0, 9d);
        expected.setValue(3, 0, -16d);
        assertThat(fillVectorWithTestValues(new TestVector(4)).toMatrix()).isEqualTo(expected);
    }

    @Test
    void toMatrixOfVectorWithNonEmptyRow() {
        TestMatrix expected = new TestMatrix(1, 4);
        expected.setValue(0, 0, 1d);
        expected.setValue(0, 1, -4d);
        expected.setValue(0, 2, 9d);
        expected.setValue(0, 3, -16d);
        assertThat(fillVectorWithTestValues(new TestVector(4, Vector.Type.ROW)).toMatrix())
            .isEqualTo(expected);
    }

    @Test
    void toParamOfEmptyVector() {
        assertThat(new TestVector(1).toParam()).isEqualTo(0d);
    }

    @Test
    void toParamOfNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new TestVector(1)).toParam()).isEqualTo(1d);
    }

    @Test
    void toParamOFEmptyVectorWithInvalidSize() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new TestVector(2).toParam())
            .withMessage(ExceptionMessages.SIZE_HAS_TO_BE_ONE);
    }

    // endregion

    // region add, subtract, multiply and dotProduct

    @Test
    void addVectorDifferentSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestVector(2).add(new TestVector(3)))
            .withMessage(ExceptionMessages.SIZES_HAVE_TO_BE_EQUAL);
    }

    @Test
    void addVectorDifferentType() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestVector(2).add(new TestVector(2, Vector.Type.ROW)))
            .withMessage(ExceptionMessages.VECTOR_TYPES_HAVE_TO_BE_EQUAL);
    }

    @Test
    void addEmptyVectorToEmptyVector() {
        assertThat(new TestVector(3).add(new TestVector(3))).isEqualTo(new TestVector(3));
    }

    @Test
    void addEmptyVectorToNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3)).add(new TestVector(3)))
            .isEqualTo(fillVectorWithTestValues(new TestVector(3)));
    }

    @Test
    void addNonEmptyVectorToEmptyVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3).add(new TestVector(3))))
            .isEqualTo(fillVectorWithTestValues(new TestVector(3)));
    }

    @Test
    void addNonEmptyVectorToNonEmptyVector() {
        TestVector expected = new TestVector(3);
        expected.setValue(0, 2d);
        expected.setValue(1, -8d);
        expected.setValue(2, 18d);
        assertThat(fillVectorWithTestValues(new TestVector(3))
            .add(fillVectorWithTestValues(new TestVector(3)))).isEqualTo(expected);
    }

    @Test
    void subtractVectorDifferentSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestVector(2).subtract(new TestVector(3)))
            .withMessage(ExceptionMessages.SIZES_HAVE_TO_BE_EQUAL);
    }

    @Test
    void subtractVectorDifferentType() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestVector(2).subtract(new TestVector(2, Vector.Type.ROW)))
            .withMessage(ExceptionMessages.VECTOR_TYPES_HAVE_TO_BE_EQUAL);
    }

    @Test
    void subtractEmptyVectorFromEmptyVector() {
        assertThat(new TestVector(3).subtract(new TestVector(3))).isEqualTo(new TestVector(3));
    }

    @Test
    void subtractEmptyVectorFromNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3)).subtract(new TestVector(3)))
            .isEqualTo(fillVectorWithTestValues(new TestVector(3)));
    }

    @Test
    void subtractNonEmptyVectorFromEmptyVector() {
        assertThat(new TestVector(3).subtract(fillVectorWithTestValues(new TestVector(3))))
            .isEqualTo(fillVectorWithTestValues(new TestVector(3)).invert());
    }

    @Test
    void subtractNonEmptyVectorFromNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3))
            .subtract(fillVectorWithTestValues(new TestVector(3)))).isEqualTo(new TestVector(3));
    }

    @Test
    void multiplyNonEmptyVectorByZero() {
        assertThat(fillVectorWithTestValues(new TestVector(3)).multiply(0)).isEqualTo(new TestVector(3));
    }

    @Test
    void multiplyNonEmptyVectorByOne() {
        assertThat(fillVectorWithTestValues(new TestVector(3)).multiply(1))
            .isEqualTo(fillVectorWithTestValues(new TestVector(3)));
    }

    @Test
    void multiplyNonEmptyVectorByFive() {
        TestVector expected = new TestVector(3);
        expected.setValue(0, 5d);
        expected.setValue(1, -20d);
        expected.setValue(2, 45d);
        assertThat(fillVectorWithTestValues(new TestVector(3)).multiply(5)).isEqualTo(expected);
    }

    @Test
    void multiplyVectorsDifferentSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestVector(3, Vector.Type.ROW).multiply(new TestVector(4)))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL_ROWS);
    }

    @Test
    void multiplyVectorsSameTypeColumn() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestVector(3).multiply(new TestVector(3)))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL_ROWS);
    }

    @Test
    void multiplyVectorsSameTypeRow() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestVector(3, Vector.Type.ROW)
                .multiply(new TestVector(3, Vector.Type.ROW)))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL_ROWS);
    }

    @Test
    void multiplyEmptyVectorsColumnRow() {
        assertThat(new TestVector(2).multiply(new TestVector(2, Vector.Type.ROW)))
            .isEqualTo(new TestMatrix(2));
    }

    @Test
    void multiplyEmptyVectorsRowColumn() {
        assertThat(new TestVector(2, Vector.Type.ROW).multiply(new TestVector(2)))
            .isEqualTo(new TestMatrix(1));
    }

    @Test
    void multiplyNonEmptyVectorsColumnRow() {
        TestMatrix expected = new TestMatrix(2);
        expected.setValue(0, 0, 1d);
        expected.setValue(0, 1, -4d);
        expected.setValue(1, 0, -4d);
        expected.setValue(1, 1, 16d);
        assertThat(fillVectorWithTestValues(new TestVector(2))
            .multiply(fillVectorWithTestValues(new TestVector(2, Vector.Type.ROW)))).isEqualTo(expected);
    }

    @Test
    void multiplyNonEmptyVectorsRowColumn() {
        TestMatrix expected = new TestMatrix(1);
        expected.setValue(0, 0, 17d);
        assertThat(fillVectorWithTestValues(new TestVector(2, Vector.Type.ROW))
            .multiply(fillVectorWithTestValues(new TestVector(2)))).isEqualTo(expected);
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorColumnRow() {
        assertThat(fillVectorWithTestValues(new TestVector(2))
            .multiply(new TestVector(2, Vector.Type.ROW))).isEqualTo(new TestMatrix(2));
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorRowColumn() {
        assertThat(fillVectorWithTestValues(new TestVector(2, Vector.Type.ROW))
            .multiply(new TestVector(2))).isEqualTo(new TestMatrix(1));
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorColumnRow() {
        assertThat(new TestVector(2)
            .multiply(fillVectorWithTestValues(new TestVector(2, Vector.Type.ROW)))
        ).isEqualTo(new TestMatrix(2));
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorRowColumn() {
        assertThat(new TestVector(2, Vector.Type.ROW)
            .multiply(fillVectorWithTestValues(new TestVector(2)))).isEqualTo(new TestMatrix(1));
    }

    @Test
    void dotProductEmptyColumnVectors() {
        assertThat(new TestVector(3)
            .dotProduct(new TestVector(3))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyColumnVectors() {
        assertThat(fillVectorWithTestValues(new TestVector(3))
            .dotProduct(fillVectorWithTestValues(new TestVector(3)))).isEqualTo(98d);
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyColumnVector() {
        assertThat(new TestVector(3)
            .dotProduct(fillVectorWithTestValues(new TestVector(3)))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3))
            .dotProduct(new TestVector(3))).isEqualTo(0d);
    }

    @Test
    void dotProductEmptyColumnVectorEmptyRowVector() {
        assertThat(new TestVector(3)
            .dotProduct(new TestVector(3, Vector.Type.ROW))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyColumnVectorNonEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3))
            .dotProduct(fillVectorWithTestValues(new TestVector(3, Vector.Type.ROW)))).isEqualTo(98d);
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyRowVector() {
        assertThat(new TestVector(3)
            .dotProduct(fillVectorWithTestValues(new TestVector(3, Vector.Type.ROW)))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3))
            .dotProduct(new TestVector(3, Vector.Type.ROW))).isEqualTo(0d);
    }

    @Test
    void dotProductEmptyRowVectorEmptyColumnVector() {
        assertThat(new TestVector(3, Vector.Type.ROW)
            .dotProduct(new TestVector(3))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyRowVectorNonEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3, Vector.Type.ROW))
            .dotProduct(fillVectorWithTestValues(new TestVector(3)))).isEqualTo(98d);
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyColumnVector() {
        assertThat(new TestVector(3, Vector.Type.ROW)
            .dotProduct(fillVectorWithTestValues(new TestVector(3)))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3, Vector.Type.ROW))
            .dotProduct(new TestVector(3))).isEqualTo(0d);
    }

    @Test
    void dotProductEmptyRowVectors() {
        assertThat(new TestVector(3, Vector.Type.ROW)
            .dotProduct(new TestVector(3, Vector.Type.ROW))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyRowVectors() {
        assertThat(fillVectorWithTestValues(new TestVector(3, Vector.Type.ROW))
            .dotProduct(fillVectorWithTestValues(new TestVector(3, Vector.Type.ROW)))).isEqualTo(98d);
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyRowVector() {
        assertThat(new TestVector(3, Vector.Type.ROW)
            .dotProduct(fillVectorWithTestValues(new TestVector(3, Vector.Type.ROW)))).isEqualTo(0d);
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3, Vector.Type.ROW))
            .dotProduct(new TestVector(3, Vector.Type.ROW))).isEqualTo(0d);
    }

    // endregion

    // region transpose and invert

    @Test
    void transposeEmptyColumnVector() {
        assertThat(new TestVector(3).transpose()).isEqualTo(new TestVector(3, Vector.Type.ROW));
    }

    @Test
    void transposeEmptyRowVector() {
        assertThat(new TestVector(3, Vector.Type.ROW).transpose()).isEqualTo(new TestVector(3));
    }

    @Test
    void transposeNonEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3)).transpose())
            .isEqualTo(fillVectorWithTestValues(new TestVector(3, Vector.Type.ROW)));
    }

    @Test
    void transposeNonEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3, Vector.Type.ROW)).transpose())
            .isEqualTo(fillVectorWithTestValues(new TestVector(3)));
    }

    @Test
    void invertEmptyVector() {
        TestVector expected = new TestVector(3);
        for (int i = 0; i < 3; i++) {
            expected.setValue(i, -0d);
        }
        assertThat(new TestVector(3).invert()).isEqualTo(expected);
    }

    @Test
    void invertNonEmptyVector() {
        TestVector expected = new TestVector(3);
        expected.setValue(0, -1d);
        expected.setValue(1, 4d);
        expected.setValue(2, -9d);
        assertThat(fillVectorWithTestValues(new TestVector(3)).invert()).isEqualTo(expected);
    }

    // endregion

    // region norm and unit

    @Test
    void maxNormEmptyVector() {
        assertThat(new TestVector(3).maxNorm()).isEqualTo(0d);
    }

    @Test
    void maxNormNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3)).maxNorm()).isEqualTo(9d);
    }

    @Test
    void euclideanNormEmptyVector() {
        assertThat(new TestVector(3).euclideanNorm()).isEqualTo(0d);
    }

    @Test
    void euclideanNormNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3)).euclideanNorm()).isEqualTo(Math.sqrt(98));
    }

    @Test
    void negativePNorm() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new TestVector(3).pNorm(-1))
            .withMessage(ExceptionMessages.VECTOR_POSITIVE_P_NORM);
    }

    @Test
    void sevenNormEmptyVector() {
        assertThat(new TestVector(3).pNorm(7)).isEqualTo(0d);
    }

    @Test
    void sevenNormNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new TestVector(3)).pNorm(7)).isEqualTo(MathX.root(4766586d, 7));
    }

    @Test
    void unitVectorOfEmptyVector() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new TestVector(3).normalize())
            .withMessage(ExceptionMessages.VECTOR_ZERO_VECTOR_CAN_NOT_NORMALIZED);
    }

    @Test
    void unitVectorOfNonEmptyVector() {
        TestVector expected = new TestVector(3);
        expected.setValue(0, 1 / Math.sqrt(98));
        expected.setValue(1, -4 / Math.sqrt(98));
        expected.setValue(2, 9 / Math.sqrt(98));
        assertThat(fillVectorWithTestValues(new TestVector(3)).normalize()).isEqualTo(expected);
    }

    // endregion

    // region angle

    @Test
    void angleBetweenEmptyVectors() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new TestVector(3).angle(new TestVector(3)))
            .withMessage(ExceptionMessages.VECTOR_ANGLE_NOT_DEFINED_FOR_ZERO_VECTOR);
    }

    @Test
    void angleBetweenIdenticalVectors() {
        assertThat(fillVectorWithTestValues(new TestVector(3))
            .angle(fillVectorWithTestValues(new TestVector(3)))).isEqualTo(0d);
    }

    @Test
    void angleBetweenParallelVectors() {
        assertThat(fillVectorWithTestValues(new TestVector(3)).multiply(2d)
            .angle(fillVectorWithTestValues(new TestVector(3)).multiply(3d))).isEqualTo(0d);
    }

    @Test
    void angleBetweenOppositeVectors() {
        assertThat(fillVectorWithTestValues(new TestVector(3)).multiply(-2d)
            .angle(fillVectorWithTestValues(new TestVector(3)).multiply(3d))).isEqualTo(Math.PI);
    }

    @Test
    void angleBetweenNonEmptyVectors() {
        assertThat(TestVector.ofValues(new TestAbstractArithmetic(), 0, 1, 0)
            .angle(TestVector.ofValues(new TestAbstractArithmetic(), 0, 0, 1))).isEqualTo(Math.PI / 2);
    }

    // endregion

    // region static

    @Test
    void ofValuesWithZeroValues() {
        assertThat(TestVector.ofValues(new TestAbstractArithmetic(), 0d, 0d, 0d, 0d))
            .isEqualTo(new TestVector(4));
    }

    @Test
    void ofValuesWithNonZeroValues() {
        assertThat(TestVector.ofValues(new TestAbstractArithmetic(), 1d, -4d, 9d, -16d))
            .isEqualTo(fillVectorWithTestValues(new TestVector(4)));
    }

    @Test
    void ofListWithZeroValues() {
        List<Number> list = new ArrayList<>();
        list.add(0d);
        list.add(0d);
        assertThat(TestVector.ofList(new TestAbstractArithmetic(), list)).isEqualTo(new TestVector(2));
    }

    @Test
    void ofListWithNonZeroValues() {
        List<Number> list = new ArrayList<>();
        list.add(1d);
        list.add(-4d);
        assertThat(TestVector.ofList(new TestAbstractArithmetic(), list))
            .isEqualTo(fillVectorWithTestValues(new TestVector(2)));
    }

    // endregion

    // region override

    @Test
    void copyOfVectorWithSize2() {
        TestVector vector = new TestVector(2);
        assertThat(vector.copy()).isEqualTo(vector);
    }

    @Test
    void iteratorOfEmptyVector() {
        TestVector vector = new TestVector(2);
        List<TestVector.Entry> values = new ArrayList<>();
        for (TestVector.Entry d : vector) {
            values.add(d);
            assertThat(d.getValue()).isEqualTo(0d);
        }
        assertThat(values).hasSize(vector.getSize());
    }

    @Test
    void streamOfEmptyVector() {
        TestVector vector = new TestVector(2);
        assertThat(vector.stream().count()).isEqualTo(2);
    }

    @Test
    void parallelStreamOfEmptyVector() {
        TestVector vector = new TestVector(2);
        assertThat(vector.parallelStream().count()).isEqualTo(2);
    }

    @Test
    void equalsOfTestVectorWithSize2() {
        assertThat(new TestVector(2))
            .isEqualTo(new TestVector(2))
            .isNotEqualTo(new TestVector(3));
    }

    @Test
    void equalsOfTestVectorWithDifferentDefaults() {
        TestVector default0 = new TestVector(2);
        default0.forEach(field -> default0.setValue(field.getIndex(), 1d));
        assertThat(default0)
            .isNotEqualTo(new TestVector(2))
            .isEqualTo(new TestVector(2, 1d));
    }

    @Test
    void hashCodeOfTestVectorWithSize2() {
        // hashCode changing after every start
        assertThat(new TestVector(2)).hasSameHashCodeAs(new TestVector(2));
    }

    @Test
    void toStringOfTestVectorWithSize2() {
        TestVector vector = new TestVector(2);
        assertThat(vector).hasToString("2: []");
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
        assertThat(vector.isValidIndex(-1)).isFalse();
        for (int i = 0; i < vector.getSize(); i++)
            assertThat(vector.isValidIndex(i)).isTrue();
        assertThat(vector.isValidIndex(vector.getSize())).isFalse();
    }

    @Test
    void isZeroOfEmptyVectorWithSize2() {
        assertThat(new TestVector(2).isZero()).isTrue();
    }

    @Test
    void isZeroOfNonEmptyVectorWithSize2() {
        assertThat(fillVectorWithTestValues(new TestVector(2)).isZero()).isFalse();
    }

    // endregion

    // region entry

    @Test
    void entryGetter() {
        TestVector vector = new TestVector(2);
        int index = 0;
        for (Vector<Number>.Entry Entry : vector) {
            assertThat(Entry.getIndex()).isEqualTo(index);
            assertThat(Entry.getVector()).isEqualTo(vector);
            index++;
        }
    }

    @Test
    void entryOverride() {
        TestVector vector = new TestVector(2);
        Vector<Number>.Entry previous = null;
        for (Vector<Number>.Entry entry : vector) {
            if (previous != null) assertThat(entry).isNotEqualTo(previous);
            else assertThat(entry).isEqualTo(vector.new Entry(entry.getIndex(), entry.getValue()));
            assertThat(entry.hashCode()).isPositive();
            assertThat(entry).hasToString(entry.getIndex() + ": " + entry.getValue());
            previous = entry;
        }

        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> vector.new Entry(vector.getSize(), 0))
            .withMessage("2 / 2");
    }

    // endregion
}
