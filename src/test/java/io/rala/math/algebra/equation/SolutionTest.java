package io.rala.math.algebra.equation;

import io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    private static AbstractEquationSystem equationSystem;

    @BeforeAll
    static void beforeAll() {
        equationSystem = new TestAbstractEquationSystem();
    }

    @Test
    void constructorWithNull() {
        assertThrows(IllegalArgumentException.class,
            () -> new Solution<>(null, null, null)
        ); // assert exception message?
    }

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        Solution<AbstractEquationSystem, Number> solution =
            Solution.unsolvable(equationSystem);
        assertEquals(solution, Solution.unsolvable(equationSystem));
        assertNotEquals(solution, Solution.infinite(equationSystem));
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        // hashCode of State changing after every start
        assertEquals(
            Solution.infinite(equationSystem).hashCode(),
            Solution.infinite(equationSystem).hashCode()
        );
    }

    @Test
    void toStringOfTestAbstractSolver() {
        Solution<AbstractEquationSystem, Number> solution =
            Solution.single(equationSystem, List.of(1));
        assertEquals("SINGLE: [1]", solution.toString());
    }

    // endregion

    // region public static

    @Test
    void createStaticSingle() {
        Solution<AbstractEquationSystem, Number> solution = Solution.single(
            equationSystem, List.of(1)
        );
        assertEquals(equationSystem, solution.getEquationSystem());
        assertEquals(List.of(1), solution.getSolution());
        assertEquals(Solution.State.SINGLE, solution.getState());
    }

    @Test
    void createStaticUnsolvable() {
        Solution<AbstractEquationSystem, Number> solution =
            Solution.unsolvable(equationSystem);
        assertEquals(equationSystem, solution.getEquationSystem());
        assertTrue(solution.getSolution().isEmpty());
        assertEquals(Solution.State.UNSOLVABLE, solution.getState());
    }

    @Test
    void createStaticInfinite() {
        Solution<AbstractEquationSystem, Number> solution =
            Solution.infinite(equationSystem);
        assertEquals(equationSystem, solution.getEquationSystem());
        assertTrue(solution.getSolution().isEmpty());
        assertEquals(Solution.State.INFINITE, solution.getState());
    }

    // endregion
}
