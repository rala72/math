package io.rala.math.algebra.vector.typed;

import io.rala.math.MathX;
import io.rala.math.algebra.matrix.typed.BigDecimalMatrix;
import io.rala.math.algebra.vector.Vector;
import io.rala.math.exception.NotSupportedException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import static io.rala.math.testUtils.algebra.TestVector.fillVectorWithTestValues;
import static io.rala.math.testUtils.assertion.GeometryAssertions.CONTEXT;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static io.rala.math.testUtils.assertion.VectorAssertions.assertVector;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BigDecimalVectorTest {
    // region constructors

    @Test
    void constructorWithNegativeSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(-1)); // assert exception message?
    }

    @Test
    void constructorWithZeroSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(0)); // assert exception message?
    }

    @Test
    void constructorWithPositiveSize() {
        assertVector(new BigDecimalVector(1), 1);
    }

    @Test
    void constructorWithPositiveSizeAndMathContext5() {
        assertVector(new BigDecimalVector(1, new MathContext(5)), 1);
    }

    @Test
    void constructorWithPositiveSizeAndType() {
        assertVector(new BigDecimalVector(2, Vector.Type.ROW), 2, Vector.Type.ROW);
    }

    @Test
    void constructorWithPositiveSizeAndTypeNull() {
        assertVector(new BigDecimalVector(2, (Vector.Type) null), 2);
    }

    @Test
    void constructorWithPositiveSizeAndTypeAndMathContext5() {
        assertVector(new BigDecimalVector(2, Vector.Type.ROW, new MathContext(5)),
            2, Vector.Type.ROW
        );
    }

    @Test
    void constructorWithVector() {
        BigDecimalVector vector = new BigDecimalVector(new BigDecimalVector(1));
        assertVector(vector, 1);
    }

    // endregion

    // region getter and length

    @Test
    void createWithLength0AndAssertLengthEquals0() {
        assertThat(new BigDecimalVector(3).length()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void createWithLength3AndAssertLengthEquals98() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3)).length())
            .isEqualTo(BigDecimal.valueOf(9.899494936611665));
    }

    // endregion

    // region value

    @Test
    void getValueByIndexMinus1() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> vector.getValue(-1)); // assert exception message?
    }

    @Test
    void setValueByIndexMinus1() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> vector.setValue(-1, BigDecimal.ZERO)); // assert exception message?
    }

    @Test
    void setValueByIndex0WhichWasUnset() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThat(vector.setValue(0, BigDecimal.ONE)).isEqualTo(BigDecimal.ZERO);
        assertThat(vector.getValue(0)).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void setValueByIndex0WhichWasSet() {
        BigDecimalVector vector = new BigDecimalVector(2);
        vector.setValue(0, BigDecimal.ONE);
        assertThat(vector.setValue(0, BigDecimal.valueOf(2))).isEqualTo(BigDecimal.ONE);
        assertThat(vector.getValue(0)).isEqualTo(BigDecimal.valueOf(2));
    }

    @Test
    void removeValueByIndexMinus1() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
            .isThrownBy(() -> vector.removeValue(-1)); // assert exception message?
    }

    @Test
    void removeValueByIndex0WhichWasUnset() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThat(vector.removeValue(0)).isEqualTo(BigDecimal.ZERO);
        assertThat(vector.getValue(0)).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void removeValueByIndex0WhichWasSet() {
        BigDecimalVector vector = new BigDecimalVector(2);
        vector.setValue(0, BigDecimal.ONE);
        assertThat(vector.removeValue(0)).isEqualTo(BigDecimal.ONE);
        assertThat(vector.getValue(0)).isEqualTo(BigDecimal.ZERO);
    }

    // endregion

    // region compute

    @Test
    void computeFirstEntryTimesTwoUnary() {
        BigDecimalVector vector = fillVectorWithTestValues(new BigDecimalVector(3));
        assertThat(vector.compute(0, x -> vector.getArithmetic().product(BigDecimal.valueOf(2), x)))
            .isEqualTo(BigDecimal.ONE);
        assertThat(vector).isEqualTo(Vector.ofValues(vector.getArithmetic(),
            BigDecimal.valueOf(2), BigDecimal.valueOf(-4), BigDecimal.valueOf(9)
        ));
    }

    @Test
    void computeFirstEntryTimesTwoBinary() {
        BigDecimalVector vector = fillVectorWithTestValues(new BigDecimalVector(3));
        assertThat(vector.compute(0, BigDecimal.valueOf(2), vector.getArithmetic()::product))
            .isEqualTo(BigDecimal.ONE);
        assertThat(vector).isEqualTo(Vector.ofValues(vector.getArithmetic(),
            BigDecimal.valueOf(2), BigDecimal.valueOf(-4), BigDecimal.valueOf(9)
        ));
    }

    @Test
    void computeTimesTwoUnary() {
        BigDecimalVector vector = fillVectorWithTestValues(new BigDecimalVector(3));
        vector.computeAll(
            x -> vector.getArithmetic().product(BigDecimal.valueOf(2), x.getValue())
        );
        assertThat(vector).isEqualTo(Vector.ofValues(vector.getArithmetic(),
            BigDecimal.valueOf(2), BigDecimal.valueOf(-8), BigDecimal.valueOf(18)
        ));
    }

    @Test
    void computeTimesTwoBinary() {
        BigDecimalVector vector = fillVectorWithTestValues(new BigDecimalVector(3));
        vector.computeAll(x -> BigDecimal.valueOf(2), vector.getArithmetic()::product);
        assertThat(vector).isEqualTo(BigDecimalVector.ofValues(vector.getArithmetic(),
            BigDecimal.valueOf(2), BigDecimal.valueOf(-8), BigDecimal.valueOf(18)
        ));
    }

    // endregion

    // region to Matrix and toParam

    @Test
    void toMatrixOfVectorWithEmptyColumn() {
        assertThat(new BigDecimalVector(3).toMatrix()).isEqualTo(new BigDecimalMatrix(3, 1));
    }

    @Test
    void toMatrixOfVectorWithEmptyRow() {
        assertThat(new BigDecimalVector(3, Vector.Type.ROW).toMatrix())
            .isEqualTo(new BigDecimalMatrix(1, 3));
    }

    @Test
    void toMatrixOfNonEmptyVectorWithSize1() {
        assertThat(new BigDecimalVector(1).toMatrix()).isEqualTo(new BigDecimalMatrix(1));
    }

    @Test
    void toMatrixOfVectorWithNonEmptyColumn() {
        BigDecimalMatrix expected = new BigDecimalMatrix(4, 1);
        expected.setValue(0, 0, BigDecimal.ONE);
        expected.setValue(1, 0, BigDecimal.valueOf(-4));
        expected.setValue(2, 0, BigDecimal.valueOf(9));
        expected.setValue(3, 0, BigDecimal.valueOf(-16));
        assertThat(fillVectorWithTestValues(new BigDecimalVector(4)).toMatrix()).isEqualTo(expected);
    }

    @Test
    void toMatrixOfVectorWithNonEmptyRow() {
        BigDecimalMatrix expected = new BigDecimalMatrix(1, 4);
        expected.setValue(0, 0, BigDecimal.ONE);
        expected.setValue(0, 1, BigDecimal.valueOf(-4));
        expected.setValue(0, 2, BigDecimal.valueOf(9));
        expected.setValue(0, 3, BigDecimal.valueOf(-16));
        assertThat(fillVectorWithTestValues(new BigDecimalVector(4, Vector.Type.ROW)).toMatrix())
            .isEqualTo(expected);
    }

    @Test
    void toParamOfEmptyVector() {
        assertThat(new BigDecimalVector(1).toParam()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void toParamOfNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(1)).toParam()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void toParamOFEmptyVectorWithInvalidSize() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new BigDecimalVector(2).toParam()); // assert exception message?
    }

    // endregion

    // region add, subtract, multiply and dotProduct

    @Test
    void addVectorDifferentSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(2).add(new BigDecimalVector(3)));
        // assert exception message?
    }

    @Test
    void addVectorDifferentType() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(2)
                .add(new BigDecimalVector(2, Vector.Type.ROW))); // assert exception message?
    }

    @Test
    void addEmptyVectorToEmptyVector() {
        assertThat(new BigDecimalVector(3).add(new BigDecimalVector(3))).isEqualTo(new BigDecimalVector(3));
    }

    @Test
    void addEmptyVectorToNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .add(new BigDecimalVector(3)))
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3)));
    }

    @Test
    void addNonEmptyVectorToEmptyVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3)
            .add(new BigDecimalVector(3))))
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3)));
    }

    @Test
    void addNonEmptyVectorToNonEmptyVector() {
        BigDecimalVector expected = new BigDecimalVector(3);
        expected.setValue(0, BigDecimal.valueOf(2));
        expected.setValue(1, BigDecimal.valueOf(-8));
        expected.setValue(2, BigDecimal.valueOf(18));
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .add(fillVectorWithTestValues(new BigDecimalVector(3)))).isEqualTo(expected);
    }

    @Test
    void subtractVectorDifferentSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(2).subtract(new BigDecimalVector(3)));
        // assert exception message?
    }

    @Test
    void subtractVectorDifferentType() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(2)
                .subtract(new BigDecimalVector(2, Vector.Type.ROW))); // assert exception message?
    }

    @Test
    void subtractEmptyVectorFromEmptyVector() {
        assertThat(new BigDecimalVector(3).subtract(new BigDecimalVector(3)))
            .isEqualTo(new BigDecimalVector(3));
    }

    @Test
    void subtractEmptyVectorFromNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .subtract(new BigDecimalVector(3)))
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3)));
    }

    @Test
    void subtractNonEmptyVectorFromEmptyVector() {
        assertThat(new BigDecimalVector(3)
            .subtract(fillVectorWithTestValues(new BigDecimalVector(3))))
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3)).invert());
    }

    @Test
    void subtractNonEmptyVectorFromNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .subtract(fillVectorWithTestValues(new BigDecimalVector(3)))).isEqualTo(new BigDecimalVector(3));
    }

    @Test
    void multiplyNonEmptyVectorByZero() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3)).multiply(BigDecimal.ZERO))
            .isEqualTo(new BigDecimalVector(3));
    }

    @Test
    void multiplyNonEmptyVectorByOne() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .multiply(BigDecimal.ONE))
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3)));
    }

    @Test
    void multiplyNonEmptyVectorByFive() {
        BigDecimalVector expected = new BigDecimalVector(3);
        expected.setValue(0, BigDecimal.valueOf(5));
        expected.setValue(1, BigDecimal.valueOf(-20));
        expected.setValue(2, BigDecimal.valueOf(45));
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3)).multiply(BigDecimal.valueOf(5)))
            .isEqualTo(expected);
    }

    @Test
    void multiplyVectorsDifferentSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(3, Vector.Type.ROW)
                .multiply(new BigDecimalVector(4))); // assert exception message?
    }

    @Test
    void multiplyVectorsSameTypeColumn() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(3).multiply(new BigDecimalVector(3)));
        // assert exception message?
    }

    @Test
    void multiplyVectorsSameTypeRow() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(3, Vector.Type.ROW)
                .multiply(new BigDecimalVector(3, Vector.Type.ROW))); // assert exception message?
    }

    @Test
    void multiplyEmptyVectorsColumnRow() {
        assertThat(new BigDecimalVector(2).multiply(new BigDecimalVector(2, Vector.Type.ROW)))
            .isEqualTo(new BigDecimalMatrix(2));
    }

    @Test
    void multiplyEmptyVectorsRowColumn() {
        assertThat(new BigDecimalVector(2, Vector.Type.ROW).multiply(new BigDecimalVector(2)))
            .isEqualTo(new BigDecimalMatrix(1));
    }

    @Test
    void multiplyNonEmptyVectorsColumnRow() {
        BigDecimalMatrix expected = new BigDecimalMatrix(2);
        expected.setValue(0, 0, BigDecimal.ONE);
        expected.setValue(0, 1, BigDecimal.valueOf(-4));
        expected.setValue(1, 0, BigDecimal.valueOf(-4));
        expected.setValue(1, 1, BigDecimal.valueOf(16));
        assertThat(fillVectorWithTestValues(new BigDecimalVector(2))
            .multiply(fillVectorWithTestValues(new BigDecimalVector(2, Vector.Type.ROW)))
        ).isEqualTo(expected);
    }

    @Test
    void multiplyNonEmptyVectorsRowColumn() {
        BigDecimalMatrix expected = new BigDecimalMatrix(1);
        expected.setValue(0, 0, BigDecimal.valueOf(17));
        assertThat(fillVectorWithTestValues(new BigDecimalVector(2, Vector.Type.ROW))
            .multiply(fillVectorWithTestValues(new BigDecimalVector(2)))).isEqualTo(expected);
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorColumnRow() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(2))
            .multiply(new BigDecimalVector(2, Vector.Type.ROW))).isEqualTo(new BigDecimalMatrix(2));
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorRowColumn() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(2, Vector.Type.ROW))
            .multiply(new BigDecimalVector(2))).isEqualTo(new BigDecimalMatrix(1));
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorColumnRow() {
        assertThat(new BigDecimalVector(2)
            .multiply(fillVectorWithTestValues(new BigDecimalVector(2, Vector.Type.ROW)))
        ).isEqualTo(new BigDecimalMatrix(2));
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorRowColumn() {
        assertThat(new BigDecimalVector(2, Vector.Type.ROW)
            .multiply(fillVectorWithTestValues(new BigDecimalVector(2)))).isEqualTo(new BigDecimalMatrix(1));
    }

    @Test
    void dotProductEmptyColumnVectors() {
        assertThat(new BigDecimalVector(3)
            .dotProduct(new BigDecimalVector(3))).isEqualTo(BigDecimal.ZERO);
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
            .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3)))).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .dotProduct(new BigDecimalVector(3))).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void dotProductEmptyColumnVectorEmptyRowVector() {
        assertThat(new BigDecimalVector(3)
            .dotProduct(new BigDecimalVector(3, Vector.Type.ROW))).isEqualTo(BigDecimal.ZERO);
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
        ).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .dotProduct(new BigDecimalVector(3, Vector.Type.ROW))).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void dotProductEmptyRowVectorEmptyColumnVector() {
        assertThat(new BigDecimalVector(3, Vector.Type.ROW)
            .dotProduct(new BigDecimalVector(3))).isEqualTo(BigDecimal.ZERO);
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
            .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3)))).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW))
            .dotProduct(new BigDecimalVector(3))).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void dotProductEmptyRowVectors() {
        assertThat(new BigDecimalVector(3, Vector.Type.ROW)
            .dotProduct(new BigDecimalVector(3, Vector.Type.ROW))).isEqualTo(BigDecimal.ZERO);
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
        ).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW))
            .dotProduct(new BigDecimalVector(3, Vector.Type.ROW))).isEqualTo(BigDecimal.ZERO);
    }

    // endregion

    // region transpose and invert

    @Test
    void transposeEmptyColumnVector() {
        assertThat(new BigDecimalVector(3).transpose()).isEqualTo(new BigDecimalVector(3, Vector.Type.ROW));
    }

    @Test
    void transposeEmptyRowVector() {
        assertThat(new BigDecimalVector(3, Vector.Type.ROW).transpose()).isEqualTo(new BigDecimalVector(3));
    }

    @Test
    void transposeNonEmptyColumnVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3)).transpose())
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)));
    }

    @Test
    void transposeNonEmptyRowVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)).transpose())
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(3)));
    }

    @Test
    void invertEmptyVector() {
        BigDecimalVector expected = new BigDecimalVector(3);
        for (int i = 0; i < 3; i++) {
            expected.setValue(i, BigDecimal.ZERO.negate());
        }
        assertThat(new BigDecimalVector(3).invert()).isEqualTo(expected);
    }

    @Test
    void invertNonEmptyVector() {
        BigDecimalVector expected = new BigDecimalVector(3);
        expected.setValue(0, BigDecimal.ONE.negate());
        expected.setValue(1, BigDecimal.valueOf(4));
        expected.setValue(2, BigDecimal.valueOf(-9));
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3)).invert()).isEqualTo(expected);
    }

    // endregion

    // region norm and unit

    @Test
    void maxNormEmptyVector() {
        assertThat(new BigDecimalVector(3).maxNorm()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void maxNormNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3)).maxNorm())
            .isEqualTo(BigDecimal.valueOf(9));
    }

    @Test
    void euclideanNormEmptyVector() {
        assertThat(new BigDecimalVector(3).euclideanNorm()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void euclideanNormNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3)).euclideanNorm())
            .isEqualTo(BigDecimal.valueOf(98).sqrt(CONTEXT));
    }

    @Test
    void negativePNorm() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BigDecimalVector(3).pNorm(-1)); // assert exception message?
    }

    @Test
    void sevenNormEmptyVector() {
        assertThat(new BigDecimalVector(3).pNorm(7)).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void sevenNormNonEmptyVector() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3)).pNorm(7))
            .isEqualTo(MathX.root(BigDecimal.valueOf(4766586d), 7));
    }

    @Test
    void unitVectorOfEmptyVector() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new BigDecimalVector(3).normalize()); // assert exception message?
    }

    @Test
    void unitVectorOfNonEmptyVector() {
        BigDecimalVector expected = new BigDecimalVector(3);
        expected.setValue(0, BigDecimal.valueOf(0.1010152544552211));
        expected.setValue(1, BigDecimal.valueOf(-0.4040610178208843));
        expected.setValue(2, BigDecimal.valueOf(0.9091372900969897));
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3)).normalize()).isEqualTo(expected);
    }

    // endregion

    // region angle

    @Test
    void angleBetweenEmptyVectors() {
        assertThatExceptionOfType(NotSupportedException.class)
            .isThrownBy(() -> new BigDecimalVector(3).angle(new BigDecimalVector(3)));
        // assert exception message?
    }

    @Test
    void angleBetweenIdenticalVectors() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .angle(fillVectorWithTestValues(new BigDecimalVector(3)))).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void angleBetweenParallelVectors() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .multiply(BigDecimal.valueOf(2))
            .angle(fillVectorWithTestValues(new BigDecimalVector(3))
                .multiply(BigDecimal.valueOf(3)))).isEqualTo(new BigDecimal("2.107342425544702E-8"));
    }

    @Test
    void angleBetweenOppositeVectors() {
        assertThat(fillVectorWithTestValues(new BigDecimalVector(3))
            .multiply(BigDecimal.valueOf(-2))
            .angle(fillVectorWithTestValues(new BigDecimalVector(3))
                .multiply(BigDecimal.valueOf(3)))).isEqualTo(BigDecimal.valueOf(3.141592632516369));
    }

    @Test
    void angleBetweenNonEmptyVectors() {
        assertThat(BigDecimalVector.ofValues(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO)
            .angle(BigDecimalVector.ofValues(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE))
        ).isEqualTo(BigDecimal.valueOf(1.570796326794897));
    }

    // endregion

    // region static

    @Test
    void ofValuesWithZeroValues() {
        assertThat(BigDecimalVector.ofValues(
            BigDecimal.ZERO, BigDecimal.ZERO,
            BigDecimal.ZERO, BigDecimal.ZERO
        )).isEqualTo(new BigDecimalVector(4));
    }

    @Test
    void ofValuesWithNonZeroValues() {
        assertThat(BigDecimalVector.ofValues(
            BigDecimal.ONE, BigDecimal.valueOf(-4),
            BigDecimal.valueOf(9), BigDecimal.valueOf(-16)
        )).isEqualTo(fillVectorWithTestValues(new BigDecimalVector(4)));
    }

    @Test
    void ofListWithZeroValues() {
        List<BigDecimal> list = new ArrayList<>();
        list.add(BigDecimal.ZERO);
        list.add(BigDecimal.ZERO);
        assertThat(BigDecimalVector.ofList(list)).isEqualTo(new BigDecimalVector(2));
    }

    @Test
    void ofListWithNonZeroValues() {
        List<BigDecimal> list = new ArrayList<>();
        list.add(BigDecimal.ONE);
        list.add(BigDecimal.valueOf(-4));
        assertThat(BigDecimalVector.ofList(list))
            .isEqualTo(fillVectorWithTestValues(new BigDecimalVector(2)));
    }

    // endregion

    // region override

    @Test
    void copyOfVectorWithSize2() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThat(vector.copy()).isEqualTo(vector);
    }

    @Test
    void iteratorOfEmptyVector() {
        BigDecimalVector vector = new BigDecimalVector(2);
        List<BigDecimalVector.Entry> values = new ArrayList<>();
        for (BigDecimalVector.Entry d : vector) {
            values.add(d);
            assertThat(d.getValue()).isEqualTo(BigDecimal.ZERO);
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
        assertThat(new BigDecimalVector(2))
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
        assertThat(vector).hasToString("2: []");
    }

    @Test
    void serializable() {
        assertSerializable(new BigDecimalVector(2), BigDecimalVector.class);
    }

    // endregion
}
