package io.rala.math.algebra.equation;

import io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AbstractEquationSystemTest {
    @Test
    void createOfTestAbstractEquationSystem() {
        TestAbstractEquationSystem equationSystem = new TestAbstractEquationSystem();
        Assertions.assertNotNull(equationSystem);
    }
}
