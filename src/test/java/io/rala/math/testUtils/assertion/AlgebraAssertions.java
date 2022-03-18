package io.rala.math.testUtils.assertion;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.algebra.numeric.Complex;
import io.rala.math.algebra.numeric.Fraction;
import io.rala.math.algebra.vector.Vector;
import io.rala.math.testUtils.assertion.algebra.MatrixAssert;
import io.rala.math.testUtils.assertion.algebra.VectorAssert;
import io.rala.math.testUtils.assertion.algebra.numeric.ComplexAssert;
import io.rala.math.testUtils.assertion.algebra.numeric.FractionAssert;

/**
 * assertions for {@link io.rala.math.algebra} package
 */
public class AlgebraAssertions {
    private AlgebraAssertions() {
    }

    public static <T extends Number> ComplexAssert<T> assertThatComplex(Complex<T> complex) {
        return new ComplexAssert<>(complex);
    }

    public static <T extends Number, V extends Number>
    FractionAssert<T, V> assertThatFraction(Fraction<T, V> fraction) {
        return new FractionAssert<>(fraction);
    }

    public static <T extends Number> MatrixAssert<T> assertThatMatrix(Matrix<T> matrix) {
        return new MatrixAssert<>(matrix);
    }

    public static <T extends Number> VectorAssert<T> assertThatVector(Vector<T> vector) {
        return new VectorAssert<>(vector);
    }
}
