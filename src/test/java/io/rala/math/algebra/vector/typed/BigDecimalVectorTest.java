package io.rala.math.algebra.vector.typed;

import io.rala.math.MathX;
import io.rala.math.algebra.matrix.typed.BigDecimalMatrix;
import io.rala.math.algebra.vector.Vector;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.exception.NotSupportedException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import static io.rala.math.testUtils.algebra.TestVector.fillVectorWithTestValues;
import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static io.rala.math.testUtils.assertion.VectorAssertions.assertVector;
import static org.junit.jupiter.api.Assertions.*;

public class BigDecimalVectorTest {
    // region constructors

    @Test
    void constructorWithNegativeSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalVector(-1)
        ); // assert exception message?
    }

    @Test
    void constructorWithZeroSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalVector(0)
        ); // assert exception message?
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
        assertEquals(BigDecimal.ZERO, new BigDecimalVector(3).length());
    }

    @Test
    void createWithLength3AndAssertLengthEquals98() {
        assertEquals(
            BigDecimal.valueOf(9.899494937),
            fillVectorWithTestValues(new BigDecimalVector(3)).length()
        );
    }

    // endregion

    // region value

    @Test
    void getValueByIndexMinus1() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> vector.getValue(-1)
        ); // assert exception message?
    }

    @Test
    void setValueByIndexMinus1() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> vector.setValue(-1, BigDecimal.ZERO)
        ); // assert exception message?
    }

    @Test
    void setValueByIndex0WhichWasUnset() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertEquals(BigDecimal.ZERO, vector.setValue(0, BigDecimal.ONE));
        assertEquals(BigDecimal.ONE, vector.getValue(0));
    }

    @Test
    void setValueByIndex0WhichWasSet() {
        BigDecimalVector vector = new BigDecimalVector(2);
        vector.setValue(0, BigDecimal.ONE);
        assertEquals(BigDecimal.ONE, vector.setValue(0, BigDecimal.valueOf(2)));
        assertEquals(BigDecimal.valueOf(2), vector.getValue(0));
    }

    @Test
    void removeValueByIndexMinus1() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertThrows(IndexOutOfBoundsException.class,
            () -> vector.removeValue(-1)
        ); // assert exception message?
    }

    @Test
    void removeValueByIndex0WhichWasUnset() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertEquals(BigDecimal.ZERO, vector.removeValue(0));
        assertEquals(BigDecimal.ZERO, vector.getValue(0));
    }

    @Test
    void removeValueByIndex0WhichWasSet() {
        BigDecimalVector vector = new BigDecimalVector(2);
        vector.setValue(0, BigDecimal.ONE);
        assertEquals(BigDecimal.ONE, vector.removeValue(0));
        assertEquals(BigDecimal.ZERO, vector.getValue(0));
    }

    // endregion

    // region compute

    @Test
    void computeFirstEntryTimesTwoUnary() {
        BigDecimalVector vector = fillVectorWithTestValues(new BigDecimalVector(3));
        assertEquals(
            BigDecimal.ONE,
            vector.compute(0, x -> vector.getArithmetic().product(BigDecimal.valueOf(2), x))
        );
        assertEquals(
            Vector.ofValues(vector.getArithmetic(),
                BigDecimal.valueOf(2), BigDecimal.valueOf(-4), BigDecimal.valueOf(9)
            ),
            vector
        );
    }

    @Test
    void computeFirstEntryTimesTwoBinary() {
        BigDecimalVector vector = fillVectorWithTestValues(new BigDecimalVector(3));
        assertEquals(
            BigDecimal.ONE,
            vector.compute(0, BigDecimal.valueOf(2), vector.getArithmetic()::product)
        );
        assertEquals(
            Vector.ofValues(vector.getArithmetic(),
                BigDecimal.valueOf(2), BigDecimal.valueOf(-4), BigDecimal.valueOf(9)
            ),
            vector
        );
    }

    @Test
    void computeTimesTwoUnary() {
        BigDecimalVector vector = fillVectorWithTestValues(new BigDecimalVector(3));
        vector.computeAll(
            x -> vector.getArithmetic().product(BigDecimal.valueOf(2), x.getValue())
        );
        assertEquals(
            Vector.ofValues(vector.getArithmetic(),
                BigDecimal.valueOf(2), BigDecimal.valueOf(-8), BigDecimal.valueOf(18)
            ),
            vector
        );
    }

    @Test
    void computeTimesTwoBinary() {
        BigDecimalVector vector = fillVectorWithTestValues(new BigDecimalVector(3));
        vector.computeAll(x -> BigDecimal.valueOf(2), vector.getArithmetic()::product);
        assertEquals(
            BigDecimalVector.ofValues(vector.getArithmetic(),
                BigDecimal.valueOf(2), BigDecimal.valueOf(-8), BigDecimal.valueOf(18)
            ),
            vector
        );
    }

    // endregion

    // region to Matrix and toParam

    @Test
    void toMatrixOfVectorWithEmptyColumn() {
        assertEquals(
            new BigDecimalMatrix(3, 1),
            new BigDecimalVector(3).toMatrix()
        );
    }

    @Test
    void toMatrixOfVectorWithEmptyRow() {
        assertEquals(
            new BigDecimalMatrix(1, 3),
            new BigDecimalVector(3, Vector.Type.ROW).toMatrix()
        );
    }

    @Test
    void toMatrixOfNonEmptyVectorWithSize1() {
        assertEquals(
            new BigDecimalMatrix(1),
            new BigDecimalVector(1).toMatrix()
        );
    }

    @Test
    void toMatrixOfVectorWithNonEmptyColumn() {
        BigDecimalMatrix expected = new BigDecimalMatrix(4, 1);
        expected.setValue(0, 0, BigDecimal.ONE);
        expected.setValue(1, 0, BigDecimal.valueOf(-4));
        expected.setValue(2, 0, BigDecimal.valueOf(9));
        expected.setValue(3, 0, BigDecimal.valueOf(-16));
        assertEquals(
            expected,
            fillVectorWithTestValues(new BigDecimalVector(4)).toMatrix()
        );
    }

    @Test
    void toMatrixOfVectorWithNonEmptyRow() {
        BigDecimalMatrix expected = new BigDecimalMatrix(1, 4);
        expected.setValue(0, 0, BigDecimal.ONE);
        expected.setValue(0, 1, BigDecimal.valueOf(-4));
        expected.setValue(0, 2, BigDecimal.valueOf(9));
        expected.setValue(0, 3, BigDecimal.valueOf(-16));
        assertEquals(
            expected,
            fillVectorWithTestValues(new BigDecimalVector(4, Vector.Type.ROW)).toMatrix()
        );
    }

    @Test
    void toParamOfEmptyVector() {
        assertEquals(
            BigDecimal.ZERO,
            new BigDecimalVector(1).toParam()
        );
    }

    @Test
    void toParamOfNonEmptyVector() {
        assertEquals(
            BigDecimal.ONE,
            fillVectorWithTestValues(new BigDecimalVector(1)).toParam()
        );
    }

    @Test
    void toParamOFEmptyVectorWithInvalidSize() {
        assertThrows(NotSupportedException.class,
            () -> new BigDecimalVector(2).toParam()
        ); // assert exception message?
    }

    // endregion

    // region add, subtract, multiply and dotProduct

    @Test
    void addVectorDifferentSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalVector(2).add(new BigDecimalVector(3))
        ); // assert exception message?
    }

    @Test
    void addVectorDifferentType() {
        assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalVector(2).add(new BigDecimalVector(2, Vector.Type.ROW))
        ); // assert exception message?
    }

    @Test
    void addEmptyVectorToEmptyVector() {
        assertEquals(
            new BigDecimalVector(3),
            new BigDecimalVector(3).add(new BigDecimalVector(3))
        );
    }

    @Test
    void addEmptyVectorToNonEmptyVector() {
        assertEquals(
            fillVectorWithTestValues(new BigDecimalVector(3)),
            fillVectorWithTestValues(new BigDecimalVector(3)).add(new BigDecimalVector(3))
        );
    }

    @Test
    void addNonEmptyVectorToEmptyVector() {
        assertEquals(
            fillVectorWithTestValues(new BigDecimalVector(3)),
            fillVectorWithTestValues(new BigDecimalVector(3).add(new BigDecimalVector(3)))
        );
    }

    @Test
    void addNonEmptyVectorToNonEmptyVector() {
        BigDecimalVector expected = new BigDecimalVector(3);
        expected.setValue(0, BigDecimal.valueOf(2));
        expected.setValue(1, BigDecimal.valueOf(-8));
        expected.setValue(2, BigDecimal.valueOf(18));
        assertEquals(
            expected,
            fillVectorWithTestValues(new BigDecimalVector(3))
                .add(fillVectorWithTestValues(new BigDecimalVector(3)))
        );
    }

    @Test
    void subtractVectorDifferentSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalVector(2).subtract(new BigDecimalVector(3))
        ); // assert exception message?
    }

    @Test
    void subtractVectorDifferentType() {
        assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalVector(2).subtract(new BigDecimalVector(2, Vector.Type.ROW))
        ); // assert exception message?
    }

    @Test
    void subtractEmptyVectorFromEmptyVector() {
        assertEquals(
            new BigDecimalVector(3),
            new BigDecimalVector(3).subtract(new BigDecimalVector(3))
        );
    }

    @Test
    void subtractEmptyVectorFromNonEmptyVector() {
        assertEquals(
            fillVectorWithTestValues(new BigDecimalVector(3)),
            fillVectorWithTestValues(new BigDecimalVector(3)).subtract(new BigDecimalVector(3))
        );
    }

    @Test
    void subtractNonEmptyVectorFromEmptyVector() {
        assertEquals(
            fillVectorWithTestValues(new BigDecimalVector(3)).invert(),
            new BigDecimalVector(3).subtract(fillVectorWithTestValues(new BigDecimalVector(3)))
        );
    }

    @Test
    void subtractNonEmptyVectorFromNonEmptyVector() {
        assertEquals(
            new BigDecimalVector(3),
            fillVectorWithTestValues(new BigDecimalVector(3))
                .subtract(fillVectorWithTestValues(new BigDecimalVector(3)))
        );
    }

    @Test
    void multiplyNonEmptyVectorByZero() {
        assertEquals(
            new BigDecimalVector(3),
            fillVectorWithTestValues(new BigDecimalVector(3)).multiply(BigDecimal.ZERO)
        );
    }

    @Test
    void multiplyNonEmptyVectorByOne() {
        assertEquals(
            fillVectorWithTestValues(new BigDecimalVector(3)),
            fillVectorWithTestValues(new BigDecimalVector(3)).multiply(BigDecimal.ONE)
        );
    }

    @Test
    void multiplyNonEmptyVectorByFive() {
        BigDecimalVector expected = new BigDecimalVector(3);
        expected.setValue(0, BigDecimal.valueOf(5));
        expected.setValue(1, BigDecimal.valueOf(-20));
        expected.setValue(2, BigDecimal.valueOf(45));
        assertEquals(
            expected,
            fillVectorWithTestValues(new BigDecimalVector(3)).multiply(BigDecimal.valueOf(5))
        );
    }

    @Test
    void multiplyVectorsDifferentSize() {
        assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalVector(3, Vector.Type.ROW).multiply(new BigDecimalVector(4))
        ); // assert exception message?
    }

    @Test
    void multiplyVectorsSameTypeColumn() {
        assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalVector(3).multiply(new BigDecimalVector(3))
        ); // assert exception message?
    }

    @Test
    void multiplyVectorsSameTypeRow() {
        assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalVector(3, Vector.Type.ROW)
                .multiply(new BigDecimalVector(3, Vector.Type.ROW))
        ); // assert exception message?
    }

    @Test
    void multiplyEmptyVectorsColumnRow() {
        assertEquals(
            new BigDecimalMatrix(2),
            new BigDecimalVector(2).multiply(new BigDecimalVector(2, Vector.Type.ROW))
        );
    }

    @Test
    void multiplyEmptyVectorsRowColumn() {
        assertEquals(
            new BigDecimalMatrix(1),
            new BigDecimalVector(2, Vector.Type.ROW).multiply(new BigDecimalVector(2))
        );
    }

    @Test
    void multiplyNonEmptyVectorsColumnRow() {
        BigDecimalMatrix expected = new BigDecimalMatrix(2);
        expected.setValue(0, 0, BigDecimal.ONE);
        expected.setValue(0, 1, BigDecimal.valueOf(-4));
        expected.setValue(1, 0, BigDecimal.valueOf(-4));
        expected.setValue(1, 1, BigDecimal.valueOf(16));
        assertEquals(
            expected,
            fillVectorWithTestValues(new BigDecimalVector(2))
                .multiply(fillVectorWithTestValues(new BigDecimalVector(2, Vector.Type.ROW)))
        );
    }

    @Test
    void multiplyNonEmptyVectorsRowColumn() {
        BigDecimalMatrix expected = new BigDecimalMatrix(1);
        expected.setValue(0, 0, BigDecimal.valueOf(17));
        assertEquals(
            expected,
            fillVectorWithTestValues(new BigDecimalVector(2, Vector.Type.ROW))
                .multiply(fillVectorWithTestValues(new BigDecimalVector(2)))
        );
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorColumnRow() {
        assertEquals(
            new BigDecimalMatrix(2),
            fillVectorWithTestValues(new BigDecimalVector(2))
                .multiply(new BigDecimalVector(2, Vector.Type.ROW))
        );
    }

    @Test
    void multiplyEmptyVectorToNonEmptyVectorRowColumn() {
        assertEquals(
            new BigDecimalMatrix(1),
            fillVectorWithTestValues(new BigDecimalVector(2, Vector.Type.ROW))
                .multiply(new BigDecimalVector(2))
        );
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorColumnRow() {
        assertEquals(
            new BigDecimalMatrix(2),
            new BigDecimalVector(2)
                .multiply(fillVectorWithTestValues(new BigDecimalVector(2, Vector.Type.ROW)))
        );
    }

    @Test
    void multiplyNonEmptyVectorToEmptyVectorRowColumn() {
        assertEquals(
            new BigDecimalMatrix(1),
            new BigDecimalVector(2, Vector.Type.ROW)
                .multiply(fillVectorWithTestValues(new BigDecimalVector(2)))
        );
    }

    @Test
    void dotProductEmptyColumnVectors() {
        assertEquals(
            BigDecimal.ZERO,
            new BigDecimalVector(3)
                .dotProduct(new BigDecimalVector(3))
        );
    }

    @Test
    void dotProductNonEmptyColumnVectors() {
        assertEquals(
            BigDecimal.valueOf(98),
            fillVectorWithTestValues(new BigDecimalVector(3))
                .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3)))
        );
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyColumnVector() {
        assertEquals(
            BigDecimal.ZERO,
            new BigDecimalVector(3)
                .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3)))
        );
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyColumnVector() {
        assertEquals(
            BigDecimal.ZERO,
            fillVectorWithTestValues(new BigDecimalVector(3))
                .dotProduct(new BigDecimalVector(3))
        );
    }

    @Test
    void dotProductEmptyColumnVectorEmptyRowVector() {
        assertEquals(
            BigDecimal.ZERO,
            new BigDecimalVector(3)
                .dotProduct(new BigDecimalVector(3, Vector.Type.ROW))
        );
    }

    @Test
    void dotProductNonEmptyColumnVectorNonEmptyRowVector() {
        assertEquals(
            BigDecimal.valueOf(98),
            fillVectorWithTestValues(new BigDecimalVector(3))
                .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)))
        );
    }

    @Test
    void dotProductEmptyColumnVectorNonEmptyRowVector() {
        assertEquals(
            BigDecimal.ZERO,
            new BigDecimalVector(3)
                .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)))
        );
    }

    @Test
    void dotProductNonEmptyColumnVectorEmptyRowVector() {
        assertEquals(
            BigDecimal.ZERO,
            fillVectorWithTestValues(new BigDecimalVector(3))
                .dotProduct(new BigDecimalVector(3, Vector.Type.ROW))
        );
    }

    @Test
    void dotProductEmptyRowVectorEmptyColumnVector() {
        assertEquals(
            BigDecimal.ZERO,
            new BigDecimalVector(3, Vector.Type.ROW)
                .dotProduct(new BigDecimalVector(3))
        );
    }

    @Test
    void dotProductNonEmptyRowVectorNonEmptyColumnVector() {
        assertEquals(
            BigDecimal.valueOf(98),
            fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW))
                .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3)))
        );
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyColumnVector() {
        assertEquals(
            BigDecimal.ZERO,
            new BigDecimalVector(3, Vector.Type.ROW)
                .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3)))
        );
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyColumnVector() {
        assertEquals(
            BigDecimal.ZERO,
            fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW))
                .dotProduct(new BigDecimalVector(3))
        );
    }

    @Test
    void dotProductEmptyRowVectors() {
        assertEquals(
            BigDecimal.ZERO,
            new BigDecimalVector(3, Vector.Type.ROW)
                .dotProduct(new BigDecimalVector(3, Vector.Type.ROW))
        );
    }

    @Test
    void dotProductNonEmptyRowVectors() {
        assertEquals(
            BigDecimal.valueOf(98),
            fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW))
                .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)))
        );
    }

    @Test
    void dotProductEmptyRowVectorNonEmptyRowVector() {
        assertEquals(
            BigDecimal.ZERO,
            new BigDecimalVector(3, Vector.Type.ROW)
                .dotProduct(fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)))
        );
    }

    @Test
    void dotProductNonEmptyRowVectorEmptyRowVector() {
        assertEquals(
            BigDecimal.ZERO,
            fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW))
                .dotProduct(new BigDecimalVector(3, Vector.Type.ROW))
        );
    }

    // endregion

    // region transpose and invert

    @Test
    void transposeEmptyColumnVector() {
        assertEquals(
            new BigDecimalVector(3, Vector.Type.ROW),
            new BigDecimalVector(3).transpose()
        );
    }

    @Test
    void transposeEmptyRowVector() {
        assertEquals(
            new BigDecimalVector(3),
            new BigDecimalVector(3, Vector.Type.ROW).transpose()
        );
    }

    @Test
    void transposeNonEmptyColumnVector() {
        assertEquals(
            fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)),
            fillVectorWithTestValues(new BigDecimalVector(3)).transpose()
        );
    }

    @Test
    void transposeNonEmptyRowVector() {
        assertEquals(
            fillVectorWithTestValues(new BigDecimalVector(3)),
            fillVectorWithTestValues(new BigDecimalVector(3, Vector.Type.ROW)).transpose()
        );
    }

    @Test
    void invertEmptyVector() {
        BigDecimalVector expected = new BigDecimalVector(3);
        for (int i = 0; i < 3; i++) {
            expected.setValue(i, BigDecimal.ZERO.negate());
        }
        assertEquals(
            expected,
            new BigDecimalVector(3).invert()
        );
    }

    @Test
    void invertNonEmptyVector() {
        BigDecimalVector expected = new BigDecimalVector(3);
        expected.setValue(0, BigDecimal.ONE.negate());
        expected.setValue(1, BigDecimal.valueOf(4));
        expected.setValue(2, BigDecimal.valueOf(-9));
        assertEquals(
            expected,
            fillVectorWithTestValues(new BigDecimalVector(3)).invert()
        );
    }

    // endregion

    // region norm and unit

    @Test
    void maxNormEmptyVector() {
        assertEquals(
            BigDecimal.ZERO,
            new BigDecimalVector(3).maxNorm()
        );
    }

    @Test
    void maxNormNonEmptyVector() {
        assertEquals(
            BigDecimal.valueOf(9),
            fillVectorWithTestValues(new BigDecimalVector(3)).maxNorm()
        );
    }

    @Test
    void euclideanNormEmptyVector() {
        assertEquals(
            BigDecimal.ZERO,
            new BigDecimalVector(3).euclideanNorm()
        );
    }

    @Test
    void euclideanNormNonEmptyVector() {
        assertEquals(
            BigDecimal.valueOf(98).sqrt(BigDecimalArithmetic.getInstance().getMathContext()),
            fillVectorWithTestValues(new BigDecimalVector(3)).euclideanNorm()
        );
    }

    @Test
    void negativePNorm() {
        assertThrows(IllegalArgumentException.class,
            () -> new BigDecimalVector(3).pNorm(-1)
        ); // assert exception message?
    }

    @Test
    void sevenNormEmptyVector() {
        assertEquals(
            BigDecimal.ZERO,
            new BigDecimalVector(3).pNorm(7)
        );
    }

    @Test
    void sevenNormNonEmptyVector() {
        assertEquals(
            MathX.root(BigDecimal.valueOf(4766586d), 7),
            fillVectorWithTestValues(new BigDecimalVector(3)).pNorm(7)
        );
    }

    @Test
    void unitVectorOfEmptyVector() {
        assertThrows(NotSupportedException.class,
            () -> new BigDecimalVector(3).normalize()
        ); // assert exception message?
    }

    @Test
    void unitVectorOfNonEmptyVector() {
        BigDecimalVector expected = new BigDecimalVector(3);
        expected.setValue(0, BigDecimal.valueOf(0.1010152545));
        expected.setValue(1, BigDecimal.valueOf(-0.4040610178));
        expected.setValue(2, BigDecimal.valueOf(0.9091372901));
        assertEquals(
            expected,
            fillVectorWithTestValues(new BigDecimalVector(3)).normalize()
        );
    }

    // endregion

    // region angle

    @Test
    void angleBetweenEmptyVectors() {
        assertThrows(NotSupportedException.class,
            () -> new BigDecimalVector(3).angle(new BigDecimalVector(3))
        ); // assert exception message?
    }

    @Test
    void angleBetweenIdenticalVectors() {
        assertEquals(
            BigDecimal.valueOf(0.00001414213621),
            fillVectorWithTestValues(new BigDecimalVector(3))
                .angle(fillVectorWithTestValues(new BigDecimalVector(3)))
        );
    }

    @Test
    void angleBetweenParallelVectors() {
        assertEquals(
            BigDecimal.ZERO,
            fillVectorWithTestValues(new BigDecimalVector(3))
                .multiply(BigDecimal.valueOf(2))
                .angle(fillVectorWithTestValues(new BigDecimalVector(3))
                    .multiply(BigDecimal.valueOf(3)))
        );
    }

    @Test
    void angleBetweenOppositeVectors() {
        assertEquals(
            BigDecimal.valueOf(3.141592654),
            fillVectorWithTestValues(new BigDecimalVector(3))
                .multiply(BigDecimal.valueOf(-2))
                .angle(fillVectorWithTestValues(new BigDecimalVector(3))
                    .multiply(BigDecimal.valueOf(3)))
        );
    }

    @Test
    void angleBetweenNonEmptyVectors() {
        assertEquals(
            BigDecimal.valueOf(Math.PI).divide(BigDecimal.valueOf(2),
                BigDecimalArithmetic.getInstance().getMathContext()
            ),
            BigDecimalVector.ofValues(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO)
                .angle(BigDecimalVector.ofValues(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE))
        );
    }

    // endregion

    // region static

    @Test
    void ofValuesWithZeroValues() {
        assertEquals(
            new BigDecimalVector(4),
            BigDecimalVector.ofValues(
                BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO
            )
        );
    }

    @Test
    void ofValuesWithNonZeroValues() {
        assertEquals(
            fillVectorWithTestValues(new BigDecimalVector(4)),
            BigDecimalVector.ofValues(
                BigDecimal.ONE, BigDecimal.valueOf(-4),
                BigDecimal.valueOf(9), BigDecimal.valueOf(-16)
            )
        );
    }

    @Test
    void ofListWithZeroValues() {
        List<BigDecimal> list = new ArrayList<>();
        list.add(BigDecimal.ZERO);
        list.add(BigDecimal.ZERO);
        assertEquals(
            new BigDecimalVector(2),
            BigDecimalVector.ofList(list)
        );
    }

    @Test
    void ofListWithNonZeroValues() {
        List<BigDecimal> list = new ArrayList<>();
        list.add(BigDecimal.ONE);
        list.add(BigDecimal.valueOf(-4));
        assertEquals(
            fillVectorWithTestValues(new BigDecimalVector(2)),
            BigDecimalVector.ofList(list)
        );
    }

    // endregion

    // region override

    @Test
    void copyOfVectorWithSize2() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertEquals(vector, vector.copy());
    }

    @Test
    void iteratorOfEmptyVector() {
        BigDecimalVector vector = new BigDecimalVector(2);
        List<BigDecimalVector.Entry> values = new ArrayList<>();
        for (BigDecimalVector.Entry d : vector) {
            values.add(d);
            assertEquals(BigDecimal.ZERO, d.getValue());
        }
        assertEquals(vector.getSize(), values.size());
    }

    @Test
    void streamOfEmptyVector() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertEquals(2, vector.stream().count());
    }

    @Test
    void parallelStreamOfEmptyVector() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertEquals(2, vector.parallelStream().count());
    }

    @Test
    void equalsOfBigDecimalVectorWithSize2() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertEquals(vector, new BigDecimalVector(2));
        assertNotEquals(vector, new BigDecimalVector(3));
    }

    @Test
    void hashCodeOfBigDecimalVectorWithSize2() {
        // hashCode changing after every start
        assertEquals(
            new BigDecimalVector(2).hashCode(),
            new BigDecimalVector(2).hashCode()
        );
    }

    @Test
    void toStringOfBigDecimalVectorWithSize2() {
        BigDecimalVector vector = new BigDecimalVector(2);
        assertEquals("2: []", vector.toString());
    }

    @Test
    void serializable() {
        assertSerializable(new BigDecimalVector(2), BigDecimalVector.class);
    }

    // endregion
}
