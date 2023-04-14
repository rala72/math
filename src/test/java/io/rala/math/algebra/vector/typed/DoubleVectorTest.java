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
import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatMatrix;
import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatVector;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
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
        assertThatVector(new DoubleVector(1))
            .hasSize(1).hasType(Vector.Type.COLUMN);
    }

    @Test
    void constructorWithPositiveSizeAndType() {
        assertThatVector(new DoubleVector(2, Vector.Type.ROW))
            .hasSize(2).hasType(Vector.Type.ROW);
    }

    @Test
    void constructorWithPositiveSizeAndTypeNull() {
        assertThatVector(new DoubleVector(2, null))
            .hasSize(2).hasType(Vector.Type.COLUMN);
    }

    @Test
    void constructorWithVector() {
        DoubleVector vector = new DoubleVector(new DoubleVector(1));
        assertThatVector(vector).hasSize(1).hasType(Vector.Type.COLUMN);
    }

    // endregion

    // region getter and length

    @Test
    void createWithLength0AndAssertLengthEquals0() {
        assertThatVector(new DoubleVector(3)).hasLength(0d);
    }

    @Test
    void createWithLength3AndAssertLengthEquals98() {
        assertThatVector(fillVectorWithTestValues(new DoubleVector(3))).hasLength(Math.sqrt(98));
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
        assertThat(vector.setValue(0, 1d)).isZero();
        assertThat(vector.getValue(0)).isOne();
    }

    @Test
    void setValueByIndex0WhichWasSet() {
        DoubleVector vector = new DoubleVector(2);
        vector.setValue(0, 1d);
        assertThat(vector.setValue(0, 2d)).isOne();
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
        assertThat(vector.removeValue(0)).isZero();
        assertThat(vector.getValue(0)).isZero();
    }

    @Test
    void removeValueByIndex0WhichWasSet() {
        DoubleVector vector = new DoubleVector(2);
        vector.setValue(0, 1d);
        assertThat(vector.removeValue(0)).isOne();
        assertThat(vector.getValue(0)).isZero();
    }

    // endregion

    // region compute

    @Test
    void computeFirstEntryTimesTwoUnary() {
        DoubleVector vector = fillVectorWithTestValues(new DoubleVector(3));
        assertThat(vector.compute(0, x -> vector.getArithmetic().product(2d, x))).isOne();
        assertThatVector(vector).isEqualTo(DoubleVector.ofValues(vector.getArithmetic(), 2d, -4d, 9d));
    }

    @Test
    void computeFirstEntryTimesTwoBinary() {
        DoubleVector vector = fillVectorWithTestValues(new DoubleVector(3));
        assertThat(vector.compute(0, 2d, vector.getArithmetic()::product)).isOne();
        assertThatVector(vector).isEqualTo(DoubleVector.ofValues(vector.getArithmetic(), 2d, -4d, 9d));
    }

    @Test
    void computeTimesTwoUnary() {
        DoubleVector vector = fillVectorWithTestValues(new DoubleVector(3));
        vector.computeAll(
            x -> vector.getArithmetic().product(2d, x.getValue())
        );
        assertThatVector(vector).isEqualTo(DoubleVector.ofValues(vector.getArithmetic(), 2d, -8d, 18d));
    }

    @Test
    void computeTimesTwoBinary() {
        DoubleVector vector = fillVectorWithTestValues(new DoubleVector(3));
        vector.computeAll(x -> 2d, vector.getArithmetic()::product);
        assertThatVector(vector).isEqualTo(DoubleVector.ofValues(vector.getArithmetic(), 2d, -8d, 18d));
    }

    // endregion

    // region to Matrix and toParam

    @Test
    void toMatrixOfVectorWithEmptyColumn() {
        assertThatMatrix(new DoubleVector(3).toMatrix()).isEqualTo(new DoubleMatrix(3, 1));
    }

    @Test
    void toMatrixOfVectorWithEmptyRow() {
        assertThatMatrix(new DoubleVector(3, Vector.Type.ROW).toMatrix())
            .isEqualTo(new DoubleMatrix(1, 3));
    }

    @Test
    void toMatrixOfNonEmptyVectorWithSize1() {
        assertThatMatrix(new DoubleVector(1).toMatrix()).isEqualTo(new DoubleMatrix(1));
    }

    @Test
    void toMatrixOfVectorWithNonEmptyColumn() {
        DoubleMatrix expected = new DoubleMatrix(4, 1);
        expected.setValue(0, 0, 1d);
        expected.setValue(1, 0, -4d);
        expected.setValue(2, 0, 9d);
        expected.setValue(3, 0, -16d);
        assertThatMatrix(fillVectorWithTestValues(new DoubleVector(4)).toMatrix()).isEqualTo(expected);
    }

    @Test
    void toMatrixOfVectorWithNonEmptyRow() {
        DoubleMatrix expected = new DoubleMatrix(1, 4);
        expected.setValue(0, 0, 1d);
        expected.setValue(0, 1, -4d);
        expected.setValue(0, 2, 9d);
        expected.setValue(0, 3, -16d);
        assertThatMatrix(fillVectorWithTestValues(new DoubleVector(4, Vector.Type.ROW)).toMatrix())
            .isEqualTo(expected);
    }

    @Test
    void toParamOfEmptyVector() {
        assertThat(new DoubleVector(1).toParam()).isZero();
    }

    @Test
    void toParamOfNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(1)).toParam()).isOne();
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
        assertThatVector(new DoubleVector(3).add(new DoubleVector(3)))
            .isEqualTo(new DoubleVector(3));
    }

    @Test
    void addEmptyVectorToNonEmptyVector() {
        assertThatVector(fillVectorWithTestValues(new DoubleVector(3)).add(new DoubleVector(3)))
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3)));
    }

    @Test
    void addNonEmptyVectorToEmptyVector() {
        assertThatVector(fillVectorWithTestValues(new DoubleVector(3).add(new DoubleVector(3))))
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3)));
    }

    @Test
    void addNonEmptyVectorToNonEmptyVector() {
        DoubleVector expected = new DoubleVector(3);
        expected.setValue(0, 2d);
        expected.setValue(1, -8d);
        expected.setValue(2, 18d);
        assertThatVector(fillVectorWithTestValues(new DoubleVector(3))
            .add(fillVectorWithTestValues(new DoubleVector(3)))
        ).isEqualTo(expected);
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
        assertThatVector(new DoubleVector(3).subtract(new DoubleVector(3)))
            .isEqualTo(new DoubleVector(3));
    }

    @Test
    void subtractEmptyVectorFromNonEmptyVector() {
        assertThatVector(fillVectorWithTestValues(new DoubleVector(3)).subtract(new DoubleVector(3)))
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3)));
    }

    @Test
    void subtractNonEmptyVectorFromEmptyVector() {
        assertThatVector(new DoubleVector(3).subtract(fillVectorWithTestValues(new DoubleVector(3))))
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3)).invert());
    }

    @Test
    void subtractNonEmptyVectorFromNonEmptyVector() {
        assertThatVector(fillVectorWithTestValues(new DoubleVector(3))
            .subtract(fillVectorWithTestValues(new DoubleVector(3)))
        ).isEqualTo(new DoubleVector(3));
    }

    @Test
    void multiplyNonEmptyVectorByZero() {
        assertThatVector(fillVectorWithTestValues(new DoubleVector(3)).multiply(0d))
            .isEqualTo(new DoubleVector(3));
    }

    @Test
    void multiplyNonEmptyVectorByOne() {
        assertThatVector(fillVectorWithTestValues(new DoubleVector(3)).multiply(1d))
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3)));
    }

    @Test
    void multiplyNonEmptyVectorByFive() {
        DoubleVector expected = new DoubleVector(3);
        expected.setValue(0, 5d);
        expected.setValue(1, -20d);
        expected.setValue(2, 45d);
        assertThatVector(fillVectorWithTestValues(new DoubleVector(3)).multiply(5d)).isEqualTo(expected);
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
        assertThatMatrix(new DoubleVector(2).multiply(new DoubleVector(2, Vector.Type.ROW)))
            .isEqualTo(new DoubleMatrix(2));
    }

    @Test
    void multiplyEmptyVectorsRowColumn() {
        assertThatMatrix(new DoubleVector(2, Vector.Type.ROW).multiply(new DoubleVector(2)))
            .isEqualTo(new DoubleMatrix(1));
    }

    @Test
    void multiplyNonEmptyVectorsColumnRow() {
        DoubleMatrix expected = new DoubleMatrix(2);
        expected.setValue(0, 0, 1d);
        expected.setValue(0, 1, -4d);
        expected.setValue(1, 0, -4d);
        expected.setValue(1, 1, 16d);
        assertThatMatrix(fillVectorWithTestValues(new DoubleVector(2))
            .multiply(fillVectorWithTestValues(new DoubleVector(2, Vector.Type.ROW)))
        ).isEqualTo(expected);
    }

    @Test
    void multiplyNonEmptyVectorsRowColumn() {
        DoubleMatrix expected = new DoubleMatrix(1);
        expected.setValue(0, 0, 17d);
        assertThatMatrix(fillVectorWithTestValues(new DoubleVector(2, Vector.Type.ROW))
            .multiply(fillVectorWithTestValues(new DoubleVector(2)))
        ).isEqualTo(expected);
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorColumnRow() {
        assertThatMatrix(fillVectorWithTestValues(new DoubleVector(2))
            .multiply(new DoubleVector(2, Vector.Type.ROW))
        ).isEqualTo(new DoubleMatrix(2));
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorRowColumn() {
        assertThatMatrix(fillVectorWithTestValues(new DoubleVector(2, Vector.Type.ROW))
            .multiply(new DoubleVector(2))
        ).isEqualTo(new DoubleMatrix(1));
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorColumnRow() {
        assertThatMatrix(new DoubleVector(2)
            .multiply(fillVectorWithTestValues(new DoubleVector(2, Vector.Type.ROW)))
        )
            .isEqualTo(new DoubleMatrix(2));
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorRowColumn() {
        assertThatMatrix(new DoubleVector(2, Vector.Type.ROW)
            .multiply(fillVectorWithTestValues(new DoubleVector(2)))
        ).isEqualTo(new DoubleMatrix(1));
    }

    @Test
    void dotProductEmptyColumnVectors() {
        assertThat(new DoubleVector(3)
            .dotProduct(new DoubleVector(3))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyColumnVectors() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3))
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3)))
        ).isEqualTo(98d);
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyColumnVector() {
        assertThat(new DoubleVector(3)
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3)))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3))
            .dotProduct(new DoubleVector(3))
        ).isZero();
    }

    @Test
    void dotProductEmptyColumnVectorEmptyRowVector() {
        assertThat(new DoubleVector(3)
            .dotProduct(new DoubleVector(3, Vector.Type.ROW))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyColumnVectorNonEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3))
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)))
        ).isEqualTo(98d);
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyRowVector() {
        assertThat(new DoubleVector(3)
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3))
            .dotProduct(new DoubleVector(3, Vector.Type.ROW))
        ).isZero();
    }

    @Test
    void dotProductEmptyRowVectorEmptyColumnVector() {
        assertThat(new DoubleVector(3, Vector.Type.ROW)
            .dotProduct(new DoubleVector(3))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyRowVectorNonEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW))
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3)))
        ).isEqualTo(98d);
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyColumnVector() {
        assertThat(new DoubleVector(3, Vector.Type.ROW)
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3)))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW))
            .dotProduct(new DoubleVector(3))
        ).isZero();
    }

    @Test
    void dotProductEmptyRowVectors() {
        assertThat(new DoubleVector(3, Vector.Type.ROW)
            .dotProduct(new DoubleVector(3, Vector.Type.ROW))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyRowVectors() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW))
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)))
        ).isEqualTo(98d);
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyRowVector() {
        assertThat(new DoubleVector(3, Vector.Type.ROW)
            .dotProduct(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW))
            .dotProduct(new DoubleVector(3, Vector.Type.ROW))
        ).isZero();
    }

    // endregion

    // region transpose and invert

    @Test
    void transposeEmptyColumnVector() {
        assertThatVector(new DoubleVector(3).transpose())
            .isEqualTo(new DoubleVector(3, Vector.Type.ROW));
    }

    @Test
    void transposeEmptyRowVector() {
        assertThatVector(new DoubleVector(3, Vector.Type.ROW).transpose())
            .isEqualTo(new DoubleVector(3));
    }

    @Test
    void transposeNonEmptyColumnVector() {
        assertThatVector(fillVectorWithTestValues(new DoubleVector(3)).transpose())
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)));
    }

    @Test
    void transposeNonEmptyRowVector() {
        assertThatVector(fillVectorWithTestValues(new DoubleVector(3, Vector.Type.ROW)).transpose())
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(3)));
    }

    @Test
    void invertEmptyVector() {
        DoubleVector expected = new DoubleVector(3);
        for (int i = 0; i < 3; i++) {
            expected.setValue(i, -0d);
        }
        assertThatVector(new DoubleVector(3).invert()).isEqualTo(expected);
    }

    @Test
    void invertNonEmptyVector() {
        DoubleVector expected = new DoubleVector(3);
        expected.setValue(0, -1d);
        expected.setValue(1, 4d);
        expected.setValue(2, -9d);
        assertThatVector(fillVectorWithTestValues(new DoubleVector(3)).invert()).isEqualTo(expected);
    }

    // endregion

    // region norm and unit

    @Test
    void maxNormEmptyVector() {
        assertThat(new DoubleVector(3).maxNorm()).isZero();
    }

    @Test
    void maxNormNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).maxNorm()).isEqualTo(9d);
    }

    @Test
    void euclideanNormEmptyVector() {
        assertThat(new DoubleVector(3).euclideanNorm()).isZero();
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
        assertThat(new DoubleVector(3).pNorm(7)).isZero();
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
        assertThatVector(fillVectorWithTestValues(new DoubleVector(3)).normalize()).isEqualTo(expected);
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
            .angle(fillVectorWithTestValues(new DoubleVector(3)))
        ).isZero();
    }

    @Test
    void angleBetweenParallelVectors() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).multiply(2d)
            .angle(fillVectorWithTestValues(new DoubleVector(3)).multiply(3d))
        ).isZero();
    }

    @Test
    void angleBetweenOppositeVectors() {
        assertThat(fillVectorWithTestValues(new DoubleVector(3)).multiply(-2d)
            .angle(fillVectorWithTestValues(new DoubleVector(3)).multiply(3d))
        ).isEqualTo(Math.PI);
    }

    @Test
    void angleBetweenNonEmptyVectors() {
        assertThat(DoubleVector.ofValues(0, 1, 0)
            .angle(DoubleVector.ofValues(0, 0, 1))
        ).isEqualTo(Math.PI / 2);
    }

    // endregion

    // region static

    @Test
    void ofValuesWithZeroValues() {
        assertThatVector(DoubleVector.ofValues(0d, 0d, 0d, 0d)).isEqualTo(new DoubleVector(4));
    }

    @Test
    void ofValuesWithNonZeroValues() {
        assertThatVector(DoubleVector.ofValues(1d, -4d, 9d, -16d))
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(4)));
    }

    @Test
    void ofListWithZeroValues() {
        List<Double> list = new ArrayList<>();
        list.add(0d);
        list.add(0d);
        assertThatVector(DoubleVector.ofList(list)).isEqualTo(new DoubleVector(2));
    }

    @Test
    void ofListWithNonZeroValues() {
        List<Double> list = new ArrayList<>();
        list.add(1d);
        list.add(-4d);
        assertThatVector(DoubleVector.ofList(list))
            .isEqualTo(fillVectorWithTestValues(new DoubleVector(2)));
    }

    // endregion

    // region override

    @Test
    void copyOfVectorWithSize2() {
        assertCopyable(new DoubleVector(2));
    }

    @Test
    void iteratorOfEmptyVector() {
        DoubleVector vector = new DoubleVector(2);
        List<DoubleVector.Entry> values = new ArrayList<>();
        for (DoubleVector.Entry d : vector) {
            values.add(d);
            assertThat(d.getValue()).isZero();
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
        assertThatVector(new DoubleVector(2))
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
        assertThatVector(vector).hasToString("2: []");
    }

    @Test
    void serializable() {
        assertSerializable(new DoubleVector(2), DoubleVector.class);
    }

    // endregion
}
