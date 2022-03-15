package io.rala.math.testUtils.assertion;

/**
 * {@link Exception} messages
 */
public class ExceptionMessages {
    private ExceptionMessages() {
    }

    public static final String DIVISION_UNDEFINED = "Division undefined";
    public static final String DIVISION_BY_ZERO = "Division by zero";
    public static final String METHOD_NOT_SUPPORTED = "method not supported";

    public static final String COLS_HAVE_TO_BE_EQUAL = "cols have to be equal";
    public static final String COLS_HAVE_TO_BE_EQUAL_ROWS =
        "cols have to be equal to parameter rows";
    public static final String ROWS_COLS_HAVE_TO_BE_GREATER_ZERO =
        "rows and cols have to be greater than 0";
    public static final String ROWS_HAVE_TO_BE_EQUAL = "rows have to be equal";
    public static final String SIZE_HAS_TO_BE_GREATER_ZERO =
        "size has to be greater than 0";
    public static final String SIZE_HAS_TO_BE_ONE = "size has to be exactly one";
    public static final String SIZES_HAVE_TO_BE_EQUAL = "sizes have to be equal";

    public static final String FRACTION_DENOMINATOR_HAS_TO_BE_NON_ZERO =
        "denominator has to be non-zero";

    public static final String MATRIX_COLS_EQUAL_OTHER_ROWS =
        "any cols have to be equal to the other matrix rows";
    public static final String MATRIX_COLS_NOT_CONGRUENT_ZERO =
        "cols modulo values.length is not congruent 0";
    public static final String MATRIX_HAS_TO_BE_SQUARE =
        "matrix has to be a square matrix";
    public static final String MATRIX_ONE_ROW_OR_COLUMN =
        "matrix has to have one row and/or one column";
    public static final String MATRIX_ONLY_ONE_VALUE =
        "matrix has to contain only one value";
    public static final String MATRIX_ROWS_NOT_CONGRUENT_ZERO =
        "rows modulo values.length is not congruent 0";
    public static final String MATRIX_AND_VECTOR_NOT_MATCH =
        "matrix and vector do not match";

    public static final String SOLVER_WORKING_NOT_SET =
        "working not set";

    public static final String VECTOR_ANGLE_NOT_DEFINED_FOR_ZERO_VECTOR =
        "angle is not defined for zero vector";
    public static final String VECTOR_POSITIVE_P_NORM =
        "may only calculate positive p-norm";
    public static final String VECTOR_TYPES_HAVE_TO_BE_EQUAL =
        "types have to be either both row or column";
    public static final String VECTOR_ZERO_VECTOR_CAN_NOT_NORMALIZED =
        "zero vector can not be normalized";
}
