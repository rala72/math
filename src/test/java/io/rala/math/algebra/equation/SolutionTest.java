package io.rala.math.algebra.equation;

import io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    private static TestAbstractEquationSystem equationSystem;

    @BeforeAll
    static void beforeAll() {
        equationSystem = new TestAbstractEquationSystem();
    }

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        assertThat(Solution.unsolvable(equationSystem))
            .isEqualTo(Solution.unsolvable(equationSystem))
            .isNotEqualTo(Solution.infinite(equationSystem));
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        // hashCode of State changing after every start
        assertThat(Solution.infinite(equationSystem)).hasSameHashCodeAs(Solution.infinite(equationSystem));
    }

    @Test
    void toStringOfTestAbstractSolver() {
        Solution<TestAbstractEquationSystem, Number> solution =
            Solution.single(equationSystem, List.of(1));
        assertThat(solution).hasToString("SINGLE: [1]");
    }

    // endregion

    // region public static

    @Test
    void createStaticSingle() {
        Solution<TestAbstractEquationSystem, Number> solution = Solution.single(
            equationSystem, List.of(1)
        );
        assertThat(solution.getEquationSystem()).isEqualTo(equationSystem);
        assertThat(solution.getSolution()).isEqualTo(List.of(1));
        assertThat(solution.getState()).isEqualTo(Solution.State.SINGLE);
    }

    @Test
    void createStaticUnsolvable() {
        Solution<TestAbstractEquationSystem, Number> solution =
            Solution.unsolvable(equationSystem);
        assertThat(solution.getEquationSystem()).isEqualTo(equationSystem);
        assertThat(solution.getSolution()).isEmpty();
        assertThat(solution.getState()).isEqualTo(Solution.State.UNSOLVABLE);
    }

    @Test
    void createStaticInfinite() {
        Solution<TestAbstractEquationSystem, Number> solution =
            Solution.infinite(equationSystem);
        assertThat(solution.getEquationSystem()).isEqualTo(equationSystem);
        assertThat(solution.getSolution()).isEmpty();
        assertThat(solution.getState()).isEqualTo(Solution.State.INFINITE);
    }

    // endregion
}
