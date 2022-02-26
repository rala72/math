package io.rala.math.algebra.equation;

import io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem;
import io.rala.math.testUtils.algebra.equation.TestAbstractSolver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractSolverTest {
    private static TestAbstractEquationSystem equationSystem;

    @BeforeAll
    static void beforeAll() {
        equationSystem = new TestAbstractEquationSystem();
    }

    // region getter and setter

    @Test
    void getEquationSystemOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        assertEquals(equationSystem, solver.getEquationSystem());
    }

    @Test
    void getWorkingOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        assertThrows(IllegalStateException.class, solver::getWorking);
    }

    // endregion

    // region abstract

    @Test
    void solveOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        assertNotNull(solver.solve());
    }

    @Test
    void resetOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        assertThrows(IllegalStateException.class, solver::getWorking);
        solver.reset();
        assertEquals(equationSystem, solver.getWorking());
    }

    // endregion

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        assertEquals(solver, new TestAbstractSolver(equationSystem));
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        // hashCode changing after every start
        assertEquals(
            new TestAbstractSolver(equationSystem).hashCode(),
            new TestAbstractSolver(equationSystem).hashCode()
        );
    }

    @Test
    void toStringOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        solver.reset();
        String toStringPrefix = "io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem@";
        String toStringSuffix = " -> null";
        assertTrue(solver.toString().startsWith(toStringPrefix));
        assertFalse(solver.toString().endsWith(toStringSuffix));
    }

    // endregion
}
