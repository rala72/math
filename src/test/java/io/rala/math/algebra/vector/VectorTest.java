package io.rala.math.algebra.vector;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.arithmetic.core.BigDecimalArithmetic;
import io.rala.math.arithmetic.core.IntegerArithmetic;
import io.rala.math.testUtils.algebra.TestMatrix;
import io.rala.math.testUtils.algebra.TestVector;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        assertEquals(expectedSize, vector.size());
    }

    @Test
    void constructorWithSizeZero() {
        TestVector vector = new TestVector(0);
        assertEquals(0, vector.size());
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
            new TestVector(new TestVector(1, 2d)).size()
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

    // region Matrix

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

    // region arithmetic

    @Test
    void addNullVector() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TestVector(2).add(null)
        );
    }

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
    void subtractNullVector() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new TestVector(2).subtract(null)
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

    // endregion

    // region temp (to be deleted, when proper tests exist)
    @Test
    void dotProduct() {
        AbstractArithmetic arithmetic = new IntegerArithmetic();
        Vector v1 = new Vector(arithmetic, 3, 1);
        Vector v2 = new Vector(arithmetic, 3, 2);
        Integer res = 6;
        assertEquals(res, v1.dotProduct(v2));
    }

    @Test
    void euclideanNorm() {
        AbstractArithmetic<BigDecimal> arithmetic = new BigDecimalArithmetic();
        List<BigDecimal> values = Stream.of(1, 0, 1, 0).map(arithmetic::fromInt).collect(Collectors.toList());
        Vector<BigDecimal> vector = new Vector<BigDecimal>(arithmetic, values, arithmetic.fromInt(2));
        BigDecimal result = arithmetic.root2(arithmetic.fromInt(2));
        assertEquals(result, vector.euclideanNorm());
    }
    // endregion

}
