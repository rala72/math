package io.rala.math.algebra.vector;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.arithmetic.core.FractionArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.arithmetic.result.BigIntegerBigDecimalResultArithmetic;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.TestVector;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VectorTest {

    // region constructors

    @Test
    void constructorWithNegativeSize() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new TestVector(-1)
        ); // assert exception message?
    }

    @Test
    void constructorWithPositiveSize() {
        int expectedSize = 1;
        TestVector vector = new TestVector(expectedSize);
        Assertions.assertEquals(expectedSize, vector.size());
    }

    @Test
    void constructorWithSizeZero() {
        TestVector vector = new TestVector(0);
        Assertions.assertEquals(0, vector.size());
        Assertions.assertThrows(IndexOutOfBoundsException.class,
            () -> new TestVector(0).getValue(0)
        ); // assert exception message?
    }

    @Test
    void constructorWithSizeTenAndDefaultValue() {
        TestVector vector = new TestVector(10, 2d);
        Assertions.assertEquals(2d, vector.getDefaultValue());
    }

    @Test
    void constructorWithVector() {
        Assertions.assertEquals(
            1,
            new TestVector(new TestVector(1, 2d)).size()
        );
        Assertions.assertEquals(
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
            Assertions.assertEquals(0d, vector.getValue(i));
        }
    }

    @Test
    void defaultOneAllDefault() {
        TestVector vector = new TestVector(3, 1d);
        for (int i = 0; i < 3; i++) {
            Assertions.assertEquals(1d, vector.getValue(i));
        }
    }

    @Test
    void defaultZeroSomeDefault() {
        TestVector vector = new TestVector(3);
        vector.setValue(2, 3d);
        for (int i = 0; i < 2; i++) {
            Assertions.assertEquals(0d, vector.getValue(i));
        }
        Assertions.assertEquals(3d, vector.getValue(2));
    }

    @Test
    void defaultOneSomeDefault() {
        TestVector vector = new TestVector(3, 1d);
        vector.setValue(2, 3d);
        for (int i = 0; i < 2; i++) {
            Assertions.assertEquals(1d, vector.getValue(i));
        }
        Assertions.assertEquals(3d, vector.getValue(2));
    }

    @Test
    void defaultZeroNoneDefault() {
        TestVector vector = new TestVector(3, false);
        Assertions.assertEquals(1d, vector.getValue(0));
        Assertions.assertEquals(-4d, vector.getValue(1));
        Assertions.assertEquals(9d, vector.getValue(2));
    }

    @Test
    void defaultOneNoneDefault() {
        TestVector vector = new TestVector(3, false);
        vector.setValue(0, 7d);
        Assertions.assertEquals(7d, vector.getValue(0));
        Assertions.assertEquals(-4d, vector.getValue(1));
        Assertions.assertEquals(9d, vector.getValue(2));
    }

    // endregion

    // region Matrix

    @Test
    void emptyColumnVectorToMatrix() {
        Assertions.assertEquals(
            new TestMatrix(3, 1),
            new TestVector(3).toMatrix()
        );
    }

    @Test
    void emptyRowVectorToMatrix() {
        Assertions.assertEquals(
            new TestMatrix(1, 3),
            new TestVector(3, Vector.Type.ROW).toMatrix()
        );
    }

    @Test
    void nonEmptyVectorOfSizeOneToMatrix() {
        Assertions.assertEquals(
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
        Assertions.assertEquals(
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
        Assertions.assertEquals(
            expected,
            new TestVector(4, Vector.Type.ROW, false).toMatrix()
        );
    }

    // endregion

    // region temp (to be deleted, when proper tests exist)
    @Test
    void times2() {
        TestVector vector = new TestVector(5, false);
        Vector result = vector.multiply(vector.getArithmetic().fromInt(2));
        Assertions.assertEquals(new Vector<>(new TestAbstractArithmetic(), Arrays.asList(2d, -8d, 18d, -32d, 50d), 0d), result);
    }

    @Test
    void add() {
        AbstractArithmetic arithmetic = new FractionArithmetic(new BigIntegerBigDecimalResultArithmetic());
        Vector v1 = new Vector(arithmetic, 3, 1);
        Vector v2 = new Vector(arithmetic, 3, 2);
        Vector res = new Vector(arithmetic, 3, 3);
        Assertions.assertEquals(res, v1.add(v2));
    }

    @Test
    void dotProduct() {
        AbstractArithmetic arithmetic = new IntegerArithmetic();
        Vector v1 = new Vector(arithmetic, 3, 1);
        Vector v2 = new Vector(arithmetic, 3, 2);
        Integer res = 6;
        Assertions.assertEquals(res, v1.dotProduct(v2));
    }

    @Test
    void euclideanNorm() {
        AbstractArithmetic<BigDecimal> arithmetic = new BigDecimalArithmetic();
        List<BigDecimal> values = Stream.of(1, 0, 1, 0).map(arithmetic::fromInt).collect(Collectors.toList());
        Vector<BigDecimal> vector = new Vector<BigDecimal>(arithmetic, values, arithmetic.fromInt(2));
        BigDecimal result = arithmetic.root2(arithmetic.fromInt(2));
        Assertions.assertEquals(result, vector.euclideanNorm());
    }
    // endregion

}
