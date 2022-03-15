package io.rala.math.algebra.equation;

import io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem;
import io.rala.math.testUtils.algebra.equation.TestAbstractSolver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class AbstractSolverTest {
    private static TestAbstractEquationSystem equationSystem;
    private AbstractSolver<TestAbstractEquationSystem, Number> solver;

    @BeforeAll
    static void beforeAll() {
        equationSystem = new TestAbstractEquationSystem();
    }

    @BeforeEach
    void setUp() {
        solver = new TestAbstractSolver(equationSystem);
    }

    // region getter and setter

    @Test
    void getEquationSystemOfTestAbstractSolver() {
        assertThat(solver.getEquationSystem()).isEqualTo(equationSystem);
    }

    @Test
    void getWorkingOfTestAbstractSolver() {
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(solver::getWorking);
    }

    // endregion

    // region abstract

    @Test
    void solveOfTestAbstractSolver() {
        assertThat(solver.solve()).isNotNull();
    }

    @Test
    void resetOfTestAbstractSolver() {
        assertThatExceptionOfType(IllegalStateException.class)
            .isThrownBy(solver::getWorking);
        solver.reset();
        assertThat(solver.getWorking()).isEqualTo(equationSystem);
    }

    // endregion

    // region override

    @Test
    void equalsOfTestAbstractSolver() {
        assertThat(solver).isEqualTo(new TestAbstractSolver(equationSystem));
    }

    @Test
    void hashCodeOfTestAbstractSolver() {
        // hashCode changing after every start
        assertThat(solver).hasSameHashCodeAs(new TestAbstractSolver(equationSystem));
    }

    @Test
    void toStringOfTestAbstractSolver() {
        solver.reset();
        String toStringPrefix = "io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem@";
        String toStringSuffix = " -> null";
        assertThat(solver.toString())
            .startsWith(toStringPrefix)
            .doesNotEndWith(toStringSuffix);
    }

    // endregion
}
