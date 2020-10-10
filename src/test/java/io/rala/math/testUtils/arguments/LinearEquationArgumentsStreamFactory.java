package io.rala.math.testUtils.arguments;

import io.rala.math.algebra.equation.Solution;
import io.rala.math.algebra.equation.linear.LinearEquationSystem;
import io.rala.math.algebra.matrix.typed.DoubleMatrix;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

public class LinearEquationArgumentsStreamFactory {
    private LinearEquationArgumentsStreamFactory() {
    }

    public static Stream<Arguments> doubleLinearEquationSystems() {
        return Stream.of(
            Arguments.of(
                Solution.single(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            1, 2, 3, 2,
                            1, 1, 1, 2,
                            3, 3, 1, 0
                        )
                    ),
                    List.of(5d, -6d, 3d)
                )
            ),
            Arguments.of(
                Solution.single(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            1, -1, 2, 0,
                            -2, 1, -6, 0,
                            1, 0, -2, 3
                        )
                    ),
                    List.of(2d, 1d, -0.5)
                )
            ),
            Arguments.of(
                Solution.single(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(2,
                            3, 4, -1,
                            2, 5, -3
                        )
                    ),
                    List.of(1d, -1d)
                )
            ),
            Arguments.of(
                Solution.single(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(2,
                            3, -4, -26,
                            2, 3, 28
                        )
                    ),
                    List.of(2d, 8d)
                )
            ),
            Arguments.of(
                Solution.unsolvable(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            3, 2, -1,
                            4, 1, -2,
                            6, 4, 3
                        )
                    )
                )
            ),
            Arguments.of(
                Solution.unsolvable(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            1, -3, 4,
                            2, 1, 1,
                            4, 5, 9
                        )
                    )
                )
            ),
            Arguments.of(
                Solution.infinite(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            1, -2, 3,
                            -2, 4, -6,
                            -1, 2, -3
                        )
                    )
                )
            ),
            Arguments.of(
                Solution.single(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            6, -1, 2, 1,
                            5, -3, 3, 4,
                            3, -2, 1, 14
                        )
                    ),
                    List.of(2.750000000000001d, -9d, -12.250000000000002d)
                )
            ),
            Arguments.of(
                Solution.single(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            1, -2, 0, 4,
                            0, -1, -1, -1,
                            -1, 1, 3, -1
                        )
                    ),
                    List.of(4d, 0d, 1d)
                )
            ),
            Arguments.of(
                Solution.single(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            1, 2, -1, 2,
                            1, 1, 2, 9,
                            2, 3, -3, -1
                        )
                    ),
                    List.of(1d, 2d, 3d)
                )
            ),
            Arguments.of(
                Solution.single(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            2, 3, -1, 3,
                            1, 0, 2, 9,
                            1, -1, 0, 2
                        )
                    ),
                    // List.of(27d/11, 5d/11, 36d/11)
                    List.of(
                        2.454545454545454,
                        0.45454545454545503,
                        3.2727272727272734
                    )
                )
            ),
            Arguments.of(
                Solution.single(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            5, 2, -2, -1,
                            3, 1, -3, -4,
                            2, 0, 1, 4
                        )
                    ),
                    List.of(
                        1.0000000000000004,
                        -1d,
                        2.0000000000000004
                    )
                )
            ),
            Arguments.of(
                Solution.single(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            4, 3, 1, 13,
                            2, -5, 3, 1,
                            7, -1, -2, -1
                        )
                    ),
                    List.of(
                        1.0000000000000002d,
                        1.9999999999999998d,
                        2.999999999999999d
                    )
                )
            ),
            Arguments.of(
                Solution.single(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            2, 9, -14, 39,
                            3, 6, 2, 36,
                            1d / 2, 1d / 3, 7, 2
                        )
                    ),
                    // List.of(465d/52, 87d/52, -45d/104)
                    List.of(
                        8.942307692307692d,
                        1.6730769230769234d,
                        -0.43269230769230765d
                    )
                )
            ),
            Arguments.of(
                Solution.unsolvable(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            1, 1, -1, 4,
                            4, -2, -2, 3,
                            -5, 4, 2, 0
                        )
                    )
                )
            ),
            Arguments.of(
                Solution.infinite(
                    LinearEquationSystem.ofMatrixWithSolutionColumn(
                        DoubleMatrix.ofValuesByRows(3,
                            1, 0, 0, 2,
                            0, 0, 1, 0,
                            0, 0, 1, 0)
                    )
                )
            )
        );
    }
}
