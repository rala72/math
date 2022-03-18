package io.rala.math.testUtils.assertion.algebra;

import io.rala.math.algebra.matrix.Matrix;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.testUtils.assertion.core.AbstractArithmeticAssert;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnusedReturnValue")
public class MatrixAssert<T extends Number> extends AbstractArithmeticAssert<T, MatrixAssert<T>, Matrix<T>> {
    public MatrixAssert(Matrix<T> fields) {
        super(fields, MatrixAssert.class);
    }

    /**
     * @see #hasSize(long)
     */
    public MatrixAssert<T> hasSizeOne() {
        return hasSize(1);
    }

    /**
     * @see Matrix#size()
     */
    public MatrixAssert<T> hasSize(long size) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.size()), longAssert ->
            longAssert.as("matrix has size").isEqualTo(size));
    }

    /**
     * @see Matrix#getRows()
     */
    public MatrixAssert<T> hasRows(int rows) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.getRows()), integerAssert ->
            integerAssert.as("matrix has rows").isEqualTo(rows));
    }

    /**
     * @see Matrix#getCols()
     */
    public MatrixAssert<T> hasCols(int cols) {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.getCols()), integerAssert ->
            integerAssert.as("matrix has cols").isEqualTo(cols));
    }

    /**
     * @see #hasDeterminante(Number)
     */
    public MatrixAssert<T> hasZeroDeterminante() {
        isNotNull();
        return hasDeterminante(getArithmetic().zero());
    }

    /**
     * @see Matrix#determinante()
     */
    public MatrixAssert<T> hasDeterminante(T determinante) {
        isNotNull();
        assertThatNoArgumentIsNull(determinante);

        return withConcatenatedMessage(assertThat(actual.determinante()), objectAssert ->
            objectAssert.as("matrix has determinante").isEqualTo(determinante));
    }

    /**
     * @see Matrix#determinante()
     */
    public MatrixAssert<T> hasNoZeroDeterminante() {
        isNotNull();

        return withConcatenatedMessage(assertThat(actual.determinante()), objectAssert ->
            objectAssert.as("matrix has no zero determinante")
                .isNotEqualTo(getArithmetic().zero()));
    }

    /**
     * @see Matrix#isSquare()
     */
    public MatrixAssert<T> isSquare() {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.isSquare()), booleanAssert ->
            booleanAssert.as("matrix is square").isTrue());
    }

    /**
     * @see Matrix#isSquare()
     */
    public MatrixAssert<T> isNoSquare() {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.isSquare()), booleanAssert ->
            booleanAssert.as("matrix is no square").isFalse());
    }

    /**
     * @see Matrix#isDiagonal()
     */
    public MatrixAssert<T> isDiagonal() {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.isDiagonal()), booleanAssert ->
            booleanAssert.as("matrix is diagonal").isTrue());
    }

    /**
     * @see Matrix#isDiagonal()
     */
    public MatrixAssert<T> isNoDiagonal() {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.isDiagonal()), booleanAssert ->
            booleanAssert.as("matrix is no diagonal").isFalse());
    }

    /**
     * @see Matrix#isInvertible()
     */
    public MatrixAssert<T> isInvertible() {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.isInvertible()), booleanAssert ->
            booleanAssert.as("matrix is invertible").isTrue());
    }

    /**
     * @see Matrix#isInvertible()
     */
    public MatrixAssert<T> isNoInvertible() {
        isNotNull();
        return withConcatenatedMessage(assertThat(actual.isInvertible()), booleanAssert ->
            booleanAssert.as("matrix is no invertible").isFalse());
    }

    @Override
    protected AbstractArithmetic<T> getArithmetic() {
        isNotNull();
        return actual.getArithmetic();
    }
}
