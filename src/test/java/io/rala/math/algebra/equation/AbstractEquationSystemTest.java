package io.rala.math.algebra.equation;

import io.rala.math.testUtils.algebra.equation.TestAbstractEquationSystem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AbstractEquationSystemTest {
    @Test
    void createOfTestAbstractEquationSystem() {
        TestAbstractEquationSystem equationSystem = new TestAbstractEquationSystem();
        assertNotNull(equationSystem);
    }

    @Test
    void transposeOfTestAbstractEquationSystem() {
        TestAbstractEquationSystem equationSystem = new TestAbstractEquationSystem();
        assertEquals(equationSystem, equationSystem.transpose());
    }
}
