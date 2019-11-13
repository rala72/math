package io.rala.math.algebra;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * class which holds a matrix with <code>rows</code> and <code>cols</code>
 * storing {@link Double}
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class DoubleMatrix extends Matrix<Double> {
    // region constructor

    /**
     * default value is <code>0</code>
     *
     * @param size size of matrix
     * @see Matrix#Matrix(int, Number)
     */
    public DoubleMatrix(int size) {
        super(size, 0d);
    }

    /**
     * default value is <code>0</code>
     *
     * @param rows rows of matrix
     * @param cols cols of matrix
     * @see Matrix#Matrix(int, int, Number)
     */
    public DoubleMatrix(int rows, int cols) {
        super(rows, cols, 0d);
    }

    /**
     * creates a new matrix based on given one<br>
     * default value is <code>0</code>
     *
     * @param matrix matrix to copy
     */
    public DoubleMatrix(Matrix<Double> matrix) {
        super(matrix);
    }

    // endregion

    // region abstract

    @Override
    public Matrix<Double> add(Matrix<Double> matrix) {
        // move assertions to matrix?
        if (getRows() != matrix.getRows())
            throw new IllegalArgumentException("rows have to be equal");
        if (getCols() != matrix.getCols())
            throw new IllegalArgumentException("cols have to be equal");
        DoubleMatrix result = new DoubleMatrix(getRows(), getCols());
        result.getMatrix().putAll(getMatrix());
        for (int i = 0; i < size(); i++)
            result.getMatrix().merge(i, matrix.getValue(i), Double::sum);
        return result;
    }

    @Override
    public Matrix<Double> multiply(Matrix<Double> matrix) {
        if (getCols() != matrix.getRows())
            throw new IllegalArgumentException("cols have to be equal to parameter rows");
        DoubleMatrix result = new DoubleMatrix(getRows(), matrix.getCols());
        for (int r = 0; r < result.getRows(); r++)
            for (int c = 0; c < result.getCols(); c++) {
                double d = 0;
                for (int i = 0; i < getRows(); i++)
                    d += getValue(r, i) * matrix.getValue(i, c);
                result.setValue(r, c, d);
            }
        return result;
    }

    @Override
    public Matrix<Double> transpose() {
        DoubleMatrix result = new DoubleMatrix(getCols(), getRows());
        for (int r = 0; r < getRows(); r++)
            for (int c = 0; c < getCols(); c++)
                result.setValue(c, r, getValue(getIndexOfRowAndCol(r, c)));
        return result;
    }

    @Override
    public double determinante() {
        if (size() == 0 || getRows() != getCols()) return 0;
        if (getRows() == 1) return getValue(0);
        if (getRows() == 2) {
            return getValue(0, 0) * getValue(1, 1) -
                getValue(0, 1) * getValue(1, 0);
        }
        if (getRows() == 3) {
            return getValue(0, 0) * getValue(1, 1) * getValue(2, 2) +
                getValue(0, 1) * getValue(1, 2) * getValue(2, 0) +
                getValue(0, 2) * getValue(1, 0) * getValue(2, 1) -
                getValue(2, 0) * getValue(1, 1) * getValue(0, 2) -
                getValue(2, 1) * getValue(1, 2) * getValue(0, 0) -
                getValue(2, 2) * getValue(1, 0) * getValue(0, 1);
        }
        double d = 0;
        boolean isRowMode = true;
        int index = 0;
        List<Field> zeros = StreamSupport.stream(spliterator(), true)
            .filter(field -> field.getValue().equals(0d))
            .collect(Collectors.toList());
        if (!zeros.isEmpty()) {
            Map.Entry<Integer, List<Field>> bestRow = getBestEntry(zeros, true);
            Map.Entry<Integer, List<Field>> bestCol = getBestEntry(zeros, false);
            if (bestRow.getValue().size() < bestCol.getValue().size()) {
                if (getCols() == bestCol.getValue().size()) return 0;
                isRowMode = false;
                index = bestCol.getKey();
            } else {
                if (getRows() == bestRow.getValue().size()) return 0;
                index = bestRow.getKey();
            }
        }
        for (int i = 0; i < (isRowMode ? getCols() : getRows()); i++) {
            int row = isRowMode ? index : i;
            int col = isRowMode ? i : index;
            double indexValue = isRowMode ? getValue(index, i) : getValue(i, index);
            if (indexValue == 0) continue;
            double signum = (index + i) % 2 == 0 ? 1 : -1;
            DoubleMatrix sub = new DoubleMatrix(getRows() - 1, getCols() - 1);
            for (int r = 0; r < sub.getRows(); r++) {
                int ar = r < row ? r : r + 1;
                for (int c = 0; c < sub.getCols(); c++) {
                    int ac = c < col ? c : c + 1;
                    sub.setValue(r, c, getValue(ar, ac));
                }
            }
            d += indexValue * signum * sub.determinante();
        }
        return d;
    }

    // endregion

    // region static: identity and diagonal

    /**
     * @param size size of matrix
     * @return new created matrix
     */
    public static DoubleMatrix identity(int size) {
        DoubleMatrix matrix = new DoubleMatrix(size);
        for (int i = 0; i < size; i++)
            matrix.setValue(i, i, 1d);
        return matrix;
    }

    /**
     * @param values diagonal values of matrix
     * @return new created matrix
     */
    public static DoubleMatrix diagonal(double... values) {
        DoubleMatrix matrix = new DoubleMatrix(values.length);
        for (int i = 0; i < values.length; i++)
            matrix.setValue(i, i, values[i]);
        return matrix;
    }

    // endregion

    // region static: of

    /**
     * creates a new matrix containing all provided values
     *
     * @param rows   rows of matrix
     * @param values row based values of matrix
     * @return new created matrix
     * @throws IllegalArgumentException if rows modulo <code>values.length</code>
     *                                  is not congruent 0
     */
    public static DoubleMatrix ofValuesByRows(int rows, double... values) {
        if (values.length % rows != 0)
            throw new IllegalArgumentException("rows modulo values.length is not congruent 0");
        DoubleMatrix matrix = new DoubleMatrix(rows, values.length / rows);
        for (int i = 0; i < values.length; i++)
            matrix.setValue(i, values[i]);
        return matrix;
    }

    /**
     * creates a new matrix containing all provided values
     *
     * @param cols   cols of matrix
     * @param values column based values of matrix
     * @return new created matrix
     * @throws IllegalArgumentException if cols modulo <code>values.length</code>
     *                                  is not congruent 0
     */
    public static DoubleMatrix ofValuesByCols(int cols, double... values) {
        if (values.length % cols != 0)
            throw new IllegalArgumentException("cols modulo values.length is not congruent 0");
        return new DoubleMatrix(
            ofValuesByRows(values.length / cols, values)
                .transpose()
        );
    }

    // endregion

    // region private

    private static Map.Entry<Integer, List<Field>> getBestEntry(
        List<Field> zeros, boolean isRowMode
    ) {
        return zeros.stream()
            .collect(Collectors.groupingBy(field ->
                isRowMode ? field.getRow() : field.getCol()
            ))
            .entrySet().stream()
            .max(Comparator.comparingInt(o -> o.getValue().size()))
            .orElse(Map.entry(0, List.of()));
    }

    // endregion
}
