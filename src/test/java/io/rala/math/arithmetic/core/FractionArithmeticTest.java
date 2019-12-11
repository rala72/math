package io.rala.math.arithmetic.core;

import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.testUtils.algebra.TestFraction;
import io.rala.math.testUtils.arithmetic.TestAbstractResultArithmetic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FractionArithmeticTest {
    private static FractionArithmetic<Number, Number> arithmetic;

    @BeforeAll
    static void beforeAll() {
        arithmetic = new FractionArithmetic<>(new TestAbstractResultArithmetic());
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        Assertions.assertEquals(new TestFraction(1), arithmetic.fromInt(1));
    }

    @Test
    void fromDouble1_1() {
        Assertions.assertEquals(new TestFraction(11d, 10d),
            arithmetic.fromDouble(1.1)
        );
    }

    @Test
    void signum1() {
        Assertions.assertEquals(1, arithmetic.signum(new TestFraction(1)));
    }

    // endregion

    // region absolute and negate

    @Test
    void absoluteM1() {
        Assertions.assertEquals(new TestFraction(1),
            arithmetic.absolute(new TestFraction(-1d))
        );
    }

    @Test
    void negate1() {
        Assertions.assertEquals(new TestFraction(-1),
            arithmetic.negate(new TestFraction(1d))
        );
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        Assertions.assertEquals(new TestFraction(3, 1d),
            arithmetic.sum(new TestFraction(1d), new TestFraction(2d))
        );
    }

    @Test
    void sum123() {
        Assertions.assertEquals(new TestFraction(6, 1d),
            arithmetic.sum(
                new TestFraction(1d),
                new TestFraction(2d),
                new TestFraction(3d)
            )
        );
    }

    @Test
    void difference12() {
        Assertions.assertEquals(new TestFraction(-1, 1d),
            arithmetic.difference(new TestFraction(1d), new TestFraction(2d))
        );
    }

    @Test
    void product12() {
        Assertions.assertEquals(new TestFraction(2),
            arithmetic.product(new TestFraction(1d), new TestFraction(2d))
        );
    }

    @Test
    void product123() {
        Assertions.assertEquals(new TestFraction(6),
            arithmetic.product(
                new TestFraction(1d),
                new TestFraction(2d),
                new TestFraction(3d))
        );
    }

    @Test
    void quotient12() {
        Assertions.assertEquals(new TestFraction(1, 2),
            arithmetic.quotient(new TestFraction(1d), new TestFraction(2d))
        );
    }

    @Test
    void modulo12() {
        Assertions.assertEquals(new TestFraction(0, 2d),
            arithmetic.modulo(new TestFraction(1d), new TestFraction(2d))
        );
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        Assertions.assertEquals(new TestFraction(1),
            arithmetic.power(new TestFraction(1d), 2)
        );
    }

    @Test
    void root21() {
        Assertions.assertEquals(
            new TestFraction(Math.sqrt(1), Math.sqrt(1)),
            arithmetic.root2(new TestFraction(1d))
        );
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        Assertions.assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.gcd(new TestFraction(3d), new TestFraction(4d))
        ); // assert exception message?
    }

    @Test
    void lcm() {
        Assertions.assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.lcm(new TestFraction(3d), new TestFraction(4d))
        ); // assert exception message?
    }

    // endregion
}
