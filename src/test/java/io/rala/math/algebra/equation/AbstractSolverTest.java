package io.rala.math.algebra.equation;

import io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem;
import io.rala.math.testUtils.algebra.equation.TestAbstractSolver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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
        assertThat(solver.getEquationSystem()).isEqualTo(equationSystem);
    }

    @Test
    void getWorkingOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(solver::getWorking);
    }

    // endregion

    // region abstract

    @Test
    void solveOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        assertThat(solver.solve()).isNotNull();
    }

    @Test
    void resetOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(solver::getWorking);
        solver.reset();
        assertThat(solver.getWorking()).isEqualTo(equationSystem);
    }

    // endregion

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        assertThat(new TestAbstractSolver(equationSystem)).isEqualTo(solver);
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        // hashCode changing after every start
        assertThat(new TestAbstractSolver(equationSystem).hashCode())
            .isEqualTo(new TestAbstractSolver(equationSystem).hashCode());
    }

    @Test
    void toStringOfTestAbstractSolver() {
        TestAbstractSolver solver = new TestAbstractSolver(equationSystem);
        solver.reset();
        String toStringPrefix = "io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem@";
        String toStringSuffix = " -> null";
        assertThat(solver.toString().startsWith(toStringPrefix)).isTrue();
        assertThat(solver.toString().endsWith(toStringSuffix)).isFalse();
    }

    // endregion
}
