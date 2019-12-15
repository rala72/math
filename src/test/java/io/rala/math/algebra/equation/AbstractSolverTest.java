package io.rala.math.algebra.equation;

import io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem;
import io.rala.math.testUtils.algebra.equation.TestAbstractSolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AbstractSolverTest {
    private static TestAbstractEquationSystem equationSystem;

    @BeforeAll
    static void beforeAll() {
        equationSystem = new TestAbstractEquationSystem();
    }

    // region getter

    @Test
    void getEquationSystemOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        Assertions.assertEquals(equationSystem, solver.getEquationSystem());
    }

    @Test
    void getWorkingOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        Assertions.assertNull(solver.getWorking());
    }

    // endregion

    // region abstract

    @Test
    void solveOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        Assertions.assertNull(solver.solve());
    }

    @Test
    void resetOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        Assertions.assertNull(solver.getWorking());
        solver.reset();
        Assertions.assertEquals(equationSystem, solver.getWorking());
    }

    // endregion

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        Assertions.assertEquals(
            solver,
            new TestAbstractSolver(equationSystem)
        );
        Assertions.assertNotEquals(
            solver,
            new TestAbstractSolver(null)
        );
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        // hashCode changing after every start
        Assertions.assertEquals(
            new TestAbstractSolver(equationSystem).hashCode(),
            new TestAbstractSolver(equationSystem).hashCode()
        );
    }

    @Test
    void toStringOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        String toStringPrefix = "io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem@";
        String toStringSuffix = " -> null";
        Assertions.assertTrue(solver.toString().startsWith(toStringPrefix));
        Assertions.assertTrue(solver.toString().endsWith(toStringSuffix));
    }

    // endregion
}
