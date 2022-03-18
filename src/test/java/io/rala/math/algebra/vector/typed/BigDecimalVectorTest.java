package io.rala.math.algebra.vector.typed;

import io.rala.math.MathX;
import io.rala.math.algebra.matrix.typed.BigDecimalMatrix;
import io.rala.math.algebra.vector.Vector;
import io.rala.math.exception.NotSupportedException;
import io.rala.math.testUtils.assertion.ExceptionMessages;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import static io.rala.math.testUtils.algebra.TestVector.fillVectorWithTestValues;
import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatMatrix;
import static io.rala.math.testUtils.assertion.AlgebraAssertions.assertThatVector;
import static io.rala.math.testUtils.assertion.GeometryAssertions.CONTEXT;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertCopyable;
import static io.rala.math.testUtils.assertion.UtilsAssertions.assertSerializable;
import static io.rala.math.testUtils.assertion.utils.OffsetUtils.bigDecimalOffset;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BigDecimalVectorTest {
    // region constructors

    @Test
    void constructorWithNegativeSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(-1))
            .withMessage(ExceptionMessages.SIZE_HAS_TO_BE_GREATER_ZERO);
    }

    @Test
    void constructorWithZeroSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(0))
            .withMessage(ExceptionMessages.SIZE_HAS_TO_BE_GREATER_ZERO);
    }

    @Test
    void constructorWithPositiveSize() {
        assertThatVector(new BigDecimalVector(1))
            .hasSize(1).hasType(Vector.Type.COLUMN);
    }

    @Test
    void constructorWithPositiveSizeAndMathContext5() {
        assertThatVector(new BigDecimalVector(1, new MathContext(5)))
            .hasSize(1).hasType(Vector.Type.COLUMN);
    }

    @Test
    void constructorWithPositiveSizeAndType() {
        assertThatVector(new BigDecimalVector(2, Vector.Type.ROW))
            .hasSize(2).hasType(Vector.Type.ROW);
    }

    @Test
    void constructorWithPositiveSizeAndTypeNull() {
        assertThatVector(new BigDecimalVector(2, (Vector.Type) null))
            .hasSize(2).hasType(Vector.Type.COLUMN);
    }

    @Test
    void constructorWithPositiveSizeAndTypeAndMathContext5() {
        assertThatVector(new BigDecimalVector(2, Vector.Type.ROW, new MathContext(5)))
            .hasSize(2).hasType(Vector.Type.ROW);
    }

    @Test
    void constructorWithVector() {
        BigDecimalVector vector = new BigDecimalVector(new BigDecimalVector(1));
        assertThatVector(vector).hasSize(1).hasType(Vector.Type.COLUMN);
    }

    // endregion

    // region getter and length

    @Test
    void createWithLength0AndAssertLengthEquals0() {
        assertThatVector(new BigDecimalVector(3)).hasLength(BigDecimal.ZERO);
    }

    @Test
    void createWithLength3AndAssertLengthEquals98() {
        assertThatVector(fillVectorWithTestValues(new BigDecimalVector(3)))
            .hasLength(BigDecimal.valueOf(9.899494936611665));
    }

    // endregion

    // region value

    @Test
    void getValueByIndexMinus1() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> vector.getValue(-1))
            .withMessage("-1 / 2");
    }

    @Test
    void setValueByIndexMinus1() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> vector.setValue(-1, BigDecimal.ZERO))
            .withMessage("-1 / 2");
    }

    @Test
    void setValueByIndex0WhichWasUnset() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThat(vector.setValue(0, BigDecimal.ONE)).isZero();
        assertThat(vector.getValue(0)).isOne();
    }

    @Test
    void setValueByIndex0WhichWasSet() {
        BigDecimalVector vector = new BigDecimalVector(2);
        vector.setValue(0, BigDecimal.ONE);
        assertThat(vector.setValue(0, BigDecimal.valueOf(2))).isOne();
        assertThat(vector.getValue(0)).isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void removeValueByIndexMinus1() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> vector.removeValue(-1))
            .withMessage("-1 / 2");
    }

    @Test
    void removeValueByIndex0WhichWasUnset() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThat(vector.removeValue(0)).isZero();
        assertThat(vector.getValue(0)).isZero();
    }

    @Test
    void removeValueByIndex0WhichWasSet() {
        BigDecimalVector vector = new BigDecimalVector(2);
        vector.setValue(0, BigDecimal.ONE);
        assertThat(vector.removeValue(0)).isOne();
        assertThat(vector.getValue(0)).isZero();
    }

    // endregion

    // region compute

    @Test
    void computeFirstEntryTimesTwoUnary() {
        BigDecimalVector vector = fillVectorWithTestValues(new BigDecimalVector(3));
        assertThat(vector.compute(0, x -> vector.getArithmetic().product(BigDecimal.valueOf(2), x)))
            .isOne();
        assertThatVector(vector).isEqualTo(Vector.ofValues(vector.getArithmetic(),
            BigDecimal.valueOf(2), BigDecimal.valueOf(-4), BigDecimal.valueOf(9)
        ));
    }

    @Test
    void computeFirstEntryTimesTwoBinary() {
        BigDecimalVector vector = fillVectorWithTestValues(new BigDecimalVector(3));
        assertThat(vector.compute(0, BigDecimal.valueOf(2), vector.getArithmetic()::product))
            .isOne();
        assertThatVector(vector).isEqualTo(Vector.ofValues(vector.getArithmetic(),
            BigDecimal.valueOf(2), BigDecimal.valueOf(-4), BigDecimal.valueOf(9)
        ));
    }

    @Test
    void computeTimesTwoUnary() {
        BigDecimalVector vector = fillVectorWithTestValues(new BigDecimalVector(3));
        vector.computeAll(
            x -> vector.getArithmetic().product(BigDecimal.valueOf(2), x.getValue())
        );
        assertThatVector(vector).isEqualTo(Vector.ofValues(vector.getArithmetic(),
            BigDecimal.valueOf(2), BigDecimal.valueOf(-8), BigDecimal.valueOf(18)
        ));
    }

    @Test
    void computeTimesTwoBinary() {
        BigDecimalVector vector = fillVectorWithTestValues(new BigDecimalVector(3));
        vector.computeAll(x -> BigDecimal.valueOf(2), vector.getArithmetic()::product);
        assertThatVector(vector).isEqualTo(BigDecimalVector.ofValues(vector.getArithmetic(),
            BigDecimal.valueOf(2), BigDecimal.valueOf(-8), BigDecimal.valueOf(18)
        ));
    }

    // endregion

    // region to Matrix and toParam

    @Test
    void toMatrixOfVectorWithEmptyColumn() {
        assertThatMatrix(new BigDecimalVector(3).toMatrix()).isEqualTo(new BigDecimalMatrix(3, 1));
    }

    @Test
    void toMatrixOfVectorWithEmptyRow() {
        assertThatMatrix(new BigDecimalVector(3, Vector.Type.ROW).toMatrix())
            .isEqualTo(new BigDecimalMatrix(1, 3));
    }

    @Test
    void toMatrixOfNonEmptyVectorWithSize1() {
        assertThatMatrix(new BigDecimalVector(1).toMatrix()).isEqualTo(new BigDecimalMatrix(1));
    }

    @Test
    void toMatrixOfVectorWithNonEmptyColumn() {
        BigDecimalMatrix expected = new BigDecimalMatrix(4, 1);
        expected.setValue(0, 0, BigDecimal.ONE);
        expected.setValue(1, 0, BigDecimal.valueOf(-4));
        expected.setValue(2, 0, BigDecimal.valueOf(9));
        expected.setValue(3, 0, BigDecimal.valueOf(-16));
        assertThatMatrix(fillVectorWithTestValues(new BigDecimalVector(4)).toMatrix()).isEqualTo(expected);
    }

    @Test
    void toMatrixOfVectorWithNonEmptyRow() {
        BigDecimalMatrix expected = new BigDecimalMatrix(1, 4);
        expected.setValue(0, 0, BigDecimal.ONE);
        expected.setValue(0, 1, BigDecimal.valueOf(-4));
        expected.setValue(0, 2, BigDecimal.valueOf(9));
        expected.setValue(0, 3, BigDecimal.valueOf(-16));
        assertThatMatrix(fillVectorWithTestValues(new BigDecimalVector(4, Vector.Type.ROW)).toMatrix())
            .isEqualTo(expected);
    }

    @Test
    void toParamOfEmptyVector() {
        assertThat(new BigDecimalVector(1).toParam()).isZero();
    }

    @Test
    void toParamOfNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(1)).toParam()).isOne();
    }

    @Test
    void toParamOFEmptyVectorWithInvalidSize() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new BigDecimalVector(2).toParam())
            .withMessage(ExceptionMessages.SIZE_HAS_TO_BE_ONE);
    }

    // endregion

    // region add, subtract, multiply and dotProduct

    @Test
    void addVectorDifferentSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(2).add(new BigDecimalVector(3)))
            .withMessage(ExceptionMessages.SIZES_HAVE_TO_BE_EQUAL);
    }

    @Test
    void addVectorDifferentType() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(2)
                .add(new BigDecimalVector(2, Vector.Type.ROW)))
            .withMessage(ExceptionMessages.VECTOR_TYPES_HAVE_TO_BE_EQUAL);
    }

    @Test
    void addEmptyVectorToEmptyVector() {
        assertThatVector(new BigDecimalVector(3).add(new BigDecimalVector(3)))
            .isEqualTo(new BigDecimalVector(3));
    }

    @Test
    void addEmptyVectorToNonEmptyVector() {
        assertThatVector(fillVectorWithTestValues(new BigDecimalVector(3))
            .add(new BigDecimalVector(3)))
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3)));
    }

    @Test
    void addNonEmptyVectorToEmptyVector() {
        assertThatVector(fillVectorWithTestValues(new BigDecimalVector(3)
            .add(new BigDecimalVector(3))))
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3)));
    }

    @Test
    void addNonEmptyVectorToNonEmptyVector() {
        BigDecimalVector expected = new BigDecimalVector(3);
        expected.setValue(0, BigDecimal.valueOf(2));
        expected.setValue(1, BigDecimal.valueOf(-8));
        expected.setValue(2, BigDecimal.valueOf(18));
        assertThatVector(fillVectorWithTestValues(new BigDecimalVector(3))
            .add(fillVectorWithTestValues(new BigDecimalVector(3)))
        ).isEqualTo(expected);
    }

    @Test
    void subtractVectorDifferentSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(2).subtract(new BigDecimalVector(3)))
            .withMessage(ExceptionMessages.SIZES_HAVE_TO_BE_EQUAL);
    }

    @Test
    void subtractVectorDifferentType() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(2)
                .subtract(new BigDecimalVector(2, Vector.Type.ROW)))
            .withMessage(ExceptionMessages.VECTOR_TYPES_HAVE_TO_BE_EQUAL);
    }

    @Test
    void subtractEmptyVectorFromEmptyVector() {
        assertThatVector(new BigDecimalVector(3).subtract(new BigDecimalVector(3)))
            .isEqualTo(new BigDecimalVector(3));
    }

    @Test
    void subtractEmptyVectorFromNonEmptyVector() {
        assertThatVector(fillVectorWithTestValues(new BigDecimalVector(3))
            .subtract(new BigDecimalVector(3)))
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3)));
    }

    @Test
    void subtractNonEmptyVectorFromEmptyVector() {
        assertThatVector(new BigDecimalVector(3)
            .subtract(fillVectorWithTestValues(new BigDecimalVector(3))))
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3)).invert());
    }

    @Test
    void subtractNonEmptyVectorFromNonEmptyVector() {
        assertThatVector(fillVectorWithTestValues(new BigDecimalVector(3))
            .subtract(fillVectorWithTestValues(new BigDecimalVector(3)))
        ).isEqualTo(new BigDecimalVector(3));
    }

    @Test
    void multiplyNonEmptyVectorByZero() {
        assertThatVector(fillVectorWithTestValues(new BigDecimalVector(3)).multiply(BigDecimal.ZERO))
            .isEqualTo(new BigDecimalVector(3));
    }

    @Test
    void multiplyNonEmptyVectorByOne() {
        assertThatVector(fillVectorWithTestValues(new BigDecimalVector(3))
            .multiply(BigDecimal.ONE))
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3)));
    }

    @Test
    void multiplyNonEmptyVectorByFive() {
        BigDecimalVector expected = new BigDecimalVector(3);
        expected.setValue(0, BigDecimal.valueOf(5));
        expected.setValue(1, BigDecimal.valueOf(-20));
        expected.setValue(2, BigDecimal.valueOf(45));
        assertThatVector(fillVectorWithTestValues(new BigDecimalVector(3))
            .multiply(BigDecimal.valueOf(5))
        )
            .isEqualTo(expected);
    }

    @Test
    void multiplyVectorsDifferentSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(3, Vector.Type.ROW)
                .multiply(new BigDecimalVector(4)))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL_ROWS);
    }

    @Test
    void multiplyVectorsSameTypeColumn() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(3).multiply(new BigDecimalVector(3)))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL_ROWS);
    }

    @Test
    void multiplyVectorsSameTypeRow() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(3, Vector.Type.ROW)
                .multiply(new BigDecimalVector(3, Vector.Type.ROW)))
            .withMessage(ExceptionMessages.COLS_HAVE_TO_BE_EQUAL_ROWS);
    }

    @Test
    void multiplyEmptyVectorsColumnRow() {
        assertThatMatrix(new BigDecimalVector(2).multiply(new BigDecimalVector(2, Vector.Type.ROW)))
            .isEqualTo(new BigDecimalMatrix(2));
    }

    @Test
    void multiplyEmptyVectorsRowColumn() {
        assertThatMatrix(new BigDecimalVector(2, Vector.Type.ROW).multiply(new BigDecimalVector(2)))
            .isEqualTo(new BigDecimalMatrix(1));
    }

    @Test
    void multiplyNonEmptyVectorsColumnRow() {
        BigDecimalMatrix expected = new BigDecimalMatrix(2);
        expected.setValue(0, 0, BigDecimal.ONE);
        expected.setValue(0, 1, BigDecimal.valueOf(-4));
        expected.setValue(1, 0, BigDecimal.valueOf(-4));
        expected.setValue(1, 1, BigDecimal.valueOf(16));
        assertThatMatrix(fillVectorWithTestValues(new BigDecimalVector(2))
            .multiply(fillVectorWithTestValues(new BigDecimalVector(2, Vector.Type.ROW)))
        ).isEqualTo(expected);
    }

    @Test
    void multiplyNonEmptyVectorsRowColumn() {
        BigDecimalMatrix expected = new BigDecimalMatrix(1);
        expected.setValue(0, 0, BigDecimal.valueOf(17));
        assertThatMatrix(fillVectorWithTestValues(new BigDecimalVector(2, Vector.Type.ROW))
            .multiply(fillVectorWithTestValues(new BigDecimalVector(2)))
        ).isEqualTo(expected);
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorColumnRow() {
        assertThatMatrix(fillVectorWithTestValues(new BigDecimalVector(2))
            .multiply(new BigDecimalVector(2, Vector.Type.ROW))
        ).isEqualTo(new BigDecimalMatrix(2));
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorRowColumn() {
        assertThatMatrix(fillVectorWithTestValues(new BigDecimalVector(2, Vector.Type.ROW))
            .multiply(new BigDecimalVector(2))
        ).isEqualTo(new BigDecimalMatrix(1));
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorColumnRow() {
        assertThatMatrix(new BigDecimalVector(2)
            .multiply(fillVectorWithTestValues(new BigDecimalVector(2, Vector.Type.ROW)))
        ).isEqualTo(new BigDecimalMatrix(2));
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorRowColumn() {
        assertThatMatrix(new BigDecimalVector(2, Vector.Type.ROW)
            .multiply(fillVectorWithTestValues(new BigDecimalVector(2)))
        )
            .isEqualTo(new BigDecimalMatrix(1));
    }

    @Test
    void dotProductEmptyColumnVectors() {
        assertThat(new BigDecimalVector(3)
            .dotProduct(new BigDecimalVector(3))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyColumnVectors() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3)))
        ).isEqualTo(BigDecimal.valueOf(98));
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyColumnVector() {
        assertThat(new BigDecimalVector(3)
            .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3)))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .dotProduct(new BigDecimalVector(3))
        ).isZero();
    }

    @Test
    void dotProductEmptyColumnVectorEmptyRowVector() {
        assertThat(new BigDecimalVector(3)
            .dotProduct(new BigDecimalVector(3, Vector.Type.ROW))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyColumnVectorNonEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)))
        ).isEqualTo(BigDecimal.valueOf(98));
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyRowVector() {
        assertThat(new BigDecimalVector(3)
            .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .dotProduct(new BigDecimalVector(3, Vector.Type.ROW))
        ).isZero();
    }

    @Test
    void dotProductEmptyRowVectorEmptyColumnVector() {
        assertThat(new BigDecimalVector(3, Vector.Type.ROW)
            .dotProduct(new BigDecimalVector(3))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyRowVectorNonEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW))
            .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3)))
        ).isEqualTo(BigDecimal.valueOf(98));
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyColumnVector() {
        assertThat(new BigDecimalVector(3, Vector.Type.ROW)
            .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3)))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW))
            .dotProduct(new BigDecimalVector(3))
        ).isZero();
    }

    @Test
    void dotProductEmptyRowVectors() {
        assertThat(new BigDecimalVector(3, Vector.Type.ROW)
            .dotProduct(new BigDecimalVector(3, Vector.Type.ROW))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyRowVectors() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW))
            .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)))
        ).isEqualTo(BigDecimal.valueOf(98));
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyRowVector() {
        assertThat(new BigDecimalVector(3, Vector.Type.ROW)
            .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)))
        ).isZero();
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW))
            .dotProduct(new BigDecimalVector(3, Vector.Type.ROW))
        ).isZero();
    }

    // endregion

    // region transpose and invert

    @Test
    void transposeEmptyColumnVector() {
        assertThatVector(new BigDecimalVector(3).transpose())
            .isEqualTo(new BigDecimalVector(3, Vector.Type.ROW));
    }

    @Test
    void transposeEmptyRowVector() {
        assertThatVector(new BigDecimalVector(3, Vector.Type.ROW).transpose())
            .isEqualTo(new BigDecimalVector(3));
    }

    @Test
    void transposeNonEmptyColumnVector() {
        assertThatVector(fillVectorWithTestValues(new BigDecimalVector(3)).transpose())
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)));
    }

    @Test
    void transposeNonEmptyRowVector() {
        assertThatVector(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)).transpose())
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3)));
    }

    @Test
    void invertEmptyVector() {
        BigDecimalVector expected = new BigDecimalVector(3);
        for (int i = 0; i < 3; i++) {
            expected.setValue(i, BigDecimal.ZERO.negate());
        }
        assertThatVector(new BigDecimalVector(3).invert()).isEqualTo(expected);
    }

    @Test
    void invertNonEmptyVector() {
        BigDecimalVector expected = new BigDecimalVector(3);
        expected.setValue(0, BigDecimal.ONE.negate());
        expected.setValue(1, BigDecimal.valueOf(4));
        expected.setValue(2, BigDecimal.valueOf(-9));
        assertThatVector(fillVectorWithTestValues(new BigDecimalVector(3)).invert()).isEqualTo(expected);
    }

    // endregion

    // region norm and unit

    @Test
    void maxNormEmptyVector() {
        assertThat(new BigDecimalVector(3).maxNorm()).isZero();
    }

    @Test
    void maxNormNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3)).maxNorm())
            .isEqualTo(BigDecimal.valueOf(9));
    }

    @Test
    void euclideanNormEmptyVector() {
        assertThat(new BigDecimalVector(3).euclideanNorm()).isZero();
    }

    @Test
    void euclideanNormNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3)).euclideanNorm())
            .isEqualTo(BigDecimal.valueOf(98).sqrt(CONTEXT));
    }

    @Test
    void negativePNorm() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(3).pNorm(-1))
            .withMessage(ExceptionMessages.VECTOR_POSITIVE_P_NORM);
    }

    @Test
    void sevenNormEmptyVector() {
        assertThat(new BigDecimalVector(3).pNorm(7)).isZero();
    }

    @Test
    void sevenNormNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3)).pNorm(7))
            .isEqualTo(MathX.root(BigDecimal.valueOf(4766586), 7));
    }

    @Test
    void unitVectorOfEmptyVector() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new BigDecimalVector(3).normalize())
            .withMessage(ExceptionMessages.VECTOR_ZERO_VECTOR_CAN_NOT_NORMALIZED);
    }

    @Test
    void unitVectorOfNonEmptyVector() {
        BigDecimalVector expected = new BigDecimalVector(3);
        expected.setValue(0, BigDecimal.valueOf(0.1010152544552211));
        expected.setValue(1, BigDecimal.valueOf(-0.4040610178208843));
        expected.setValue(2, BigDecimal.valueOf(0.9091372900969897));
        assertThatVector(fillVectorWithTestValues(new BigDecimalVector(3)).normalize()).isEqualTo(expected);
    }

    // endregion

    // region angle

    @Test
    void angleBetweenEmptyVectors() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new BigDecimalVector(3).angle(new BigDecimalVector(3)))
            .withMessage(ExceptionMessages.VECTOR_ANGLE_NOT_DEFINED_FOR_ZERO_VECTOR);
    }

    @Test
    void angleBetweenIdenticalVectors() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .angle(fillVectorWithTestValues(new BigDecimalVector(3)))
        ).isZero();
    }

    @Test
    void angleBetweenParallelVectors() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .multiply(BigDecimal.valueOf(2))
            .angle(fillVectorWithTestValues(new BigDecimalVector(3))
                .multiply(BigDecimal.valueOf(3)))
        ).isEqualTo(BigDecimal.valueOf(2.107342425544702e-8));
    }

    @Test
    void angleBetweenOppositeVectors() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .multiply(BigDecimal.valueOf(-2))
            .angle(fillVectorWithTestValues(new BigDecimalVector(3))
                .multiply(BigDecimal.valueOf(3)))
        ).isCloseTo(BigDecimal.valueOf(Math.PI), bigDecimalOffset());
    }

    @Test
    void angleBetweenNonEmptyVectors() {
        assertThat(BigDecimalVector.ofValues(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO)
            .angle(BigDecimalVector.ofValues(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE))
        ).isCloseTo(BigDecimal.valueOf(Math.PI / 2), bigDecimalOffset());
    }

    // endregion

    // region static

    @Test
    void ofValuesWithZeroValues() {
        assertThatVector(BigDecimalVector.ofValues(
            BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO
        )).isEqualTo(new BigDecimalVector(4));
    }

    @Test
    void ofValuesWithNonZeroValues() {
        assertThatVector(BigDecimalVector.ofValues(
            BigDecimal.ONE, BigDecimal.valueOf(-4),
            BigDecimal.valueOf(9), BigDecimal.valueOf(-16)
        )).isEqualTo(fillVectorWithTestValues(new BigDecimalVector(4)));
    }

    @Test
    void ofListWithZeroValues() {
        List<BigDecimal> list = new ArrayList<>();
        list.add(BigDecimal.ZERO);
        list.add(BigDecimal.ZERO);
        assertThatVector(BigDecimalVector.ofList(list)).isEqualTo(new BigDecimalVector(2));
    }

    @Test
    void ofListWithNonZeroValues() {
        List<BigDecimal> list = new ArrayList<>();
        list.add(BigDecimal.ONE);
        list.add(BigDecimal.valueOf(-4));
        assertThatVector(BigDecimalVector.ofList(list))
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(2)));
    }

    // endregion

    // region override

    @Test
    void copyOfVectorWithSize2() {
        assertCopyable(new BigDecimalVector(2));
    }

    @Test
    void iteratorOfEmptyVector() {
        BigDecimalVector vector = new BigDecimalVector(2);
        List<BigDecimalVector.Entry> values = new ArrayList<>();
        for (BigDecimalVector.Entry d : vector) {
            values.add(d);
            assertThat(d.getValue()).isZero();
        }
        assertThat(values).hasSize(vector.getSize());
    }

    @Test
    void streamOfEmptyVector() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThat(vector.stream().count()).isEqualTo(2);
    }

    @Test
    void parallelStreamOfEmptyVector() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThat(vector.parallelStream().count()).isEqualTo(2);
    }

    @Test
    void equalsOfBigDecimalVectorWithSize2() {
        assertThatVector(new BigDecimalVector(2))
            .isEqualTo(new BigDecimalVector(2))
            .isNotEqualTo(new BigDecimalVector(3));
    }

    @Test
    void hashCodeOfBigDecimalVectorWithSize2() {
        // hashCode changing after every start
        assertThat(new BigDecimalVector(2)).hasSameHashCodeAs(new BigDecimalVector(2));
    }

    @Test
    void toStringOfBigDecimalVectorWithSize2() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThatVector(vector).hasToString("2: []");
    }

    @Test
    void serializable() {
        assertSerializable(new BigDecimalVector(2), BigDecimalVector.class);
    }

    // endregion
}
