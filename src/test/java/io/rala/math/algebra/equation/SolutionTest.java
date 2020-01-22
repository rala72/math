package io.rala.math.algebra.equation;

import io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

class SolutionTest {
    private static AbstractEquationSystem equationSystem;

    @BeforeAll
    static void beforeAll() {
        equationSystem = new TestAbstractEquationSystem();
    }

    @Test
    void constructorWithNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new Solution<>(null, null, null)
        ); // assert exception message?
    }

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        Solution<AbstractEquationSystem, Number> solution =
            Solution.unsolvable(equationSystem);
        Assertions.assertEquals(
            solution,
            Solution.unsolvable(equationSystem)
        );
        Assertions.assertNotEquals(
            solution,
            Solution.infinite(equationSystem)
        );
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        // hashCode of State changing after every start
        Assertions.assertEquals(
            Solution.infinite(equationSystem).hashCode(),
            Solution.infinite(equationSystem).hashCode()
        );
    }

    @Test
    void toStringOfTestAbstractSolver() {
        Solution<AbstractEquationSystem, Number> solution =
            Solution.solved(equationSystem, List.of(1));
        Assertions.assertEquals("SOLVED: [1]", solution.toString());
    }

    // endregion

    // region public static

    @Test
    void createStaticSolved() {
        Solution<AbstractEquationSystem, Number> solution = Solution.solved(
            equationSystem, List.of(1)
        );
        Assertions.assertEquals(equationSystem, solution.getEquationSystem());
        Assertions.assertEquals(List.of(1), solution.getSolution());
        Assertions.assertEquals(Solution.State.SOLVED, solution.getState());
    }

    @Test
    void createStaticUnsolvable() {
        Solution<AbstractEquationSystem, Number> solution = Solution.unsolvable(equationSystem);
        Assertions.assertEquals(equationSystem, solution.getEquationSystem());
        Assertions.assertTrue(solution.getSolution().isEmpty());
        Assertions.assertEquals(Solution.State.UNSOLVABLE, solution.getState());
    }

    @Test
    void createStaticInfinite() {
        Solution<AbstractEquationSystem, Number> solution = Solution.infinite(equationSystem);
        Assertions.assertEquals(equationSystem, solution.getEquationSystem());
        Assertions.assertTrue(solution.getSolution().isEmpty());
        Assertions.assertEquals(Solution.State.INFINITE, solution.getState());
    }

    // endregion
}
