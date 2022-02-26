package io.rala.math.algebra.equation;

import io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractEquationSystemTest {
    @Test
    void createOfTestAbstractEquationSystem() {
        TestAbstractEquationSystem equationSystem = new TestAbstractEquationSystem();
        assertThat(equationSystem).isNotNull();
    }

    @Test
    void transposeOfTestAbstractEquationSystem() {
        TestAbstractEquationSystem equationSystem = new TestAbstractEquationSystem();
        assertThat(equationSystem.transpose()).isEqualTo(equationSystem);
    }
}
