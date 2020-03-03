package io.rala.math.arithmetic.core;

import io.rala.math.arithmetic.AbstractArithmetic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static io.rala.math.testUtils.assertion.SerializableAssertions.assertSerializable;
import static org.junit.jupiter.api.Assertions.*;

class BigDecimalArithmeticTest {
    private BigDecimalArithmetic arithmetic;

    @BeforeEach
    void setUp() {
        arithmetic = new BigDecimalArithmetic();
    }

    @Test
    void getInstance() {
        assertEquals(arithmetic, BigDecimalArithmetic.getInstance());
    }

    // region fromInt, fromDouble and signum

    @Test
    void fromInt1() {
        assertEquals(BigDecimal.ONE, arithmetic.fromInt(1));
    }

    @Test
    void fromDouble1_1() {
        assertEquals(BigDecimal.valueOf(1.1), arithmetic.fromDouble(1.1));
    }

    @Test
    void signum1() {
        assertEquals(1, arithmetic.signum(BigDecimal.ONE));
    }

    // endregion

    // region absolute, negate and compare

    @Test
    void absoluteM1() {
        assertEquals(BigDecimal.ONE,
            arithmetic.absolute(BigDecimal.ONE.negate())
        );
    }

    @Test
    void negate1() {
        assertEquals(BigDecimal.ONE.negate(),
            arithmetic.negate(BigDecimal.ONE)
        );
    }

    @Test
    void compare() {
        assertEquals(0,
            arithmetic.compare(BigDecimal.ONE, BigDecimal.ONE)
        );
        assertEquals(-1,
            arithmetic.compare(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
        assertEquals(1,
            arithmetic.compare(BigDecimal.valueOf(2), BigDecimal.ONE)
        );
    }

    @Test
    void min() {
        assertEquals(BigDecimal.ONE,
            arithmetic.min(BigDecimal.ONE, BigDecimal.ONE)
        );
        assertEquals(BigDecimal.ONE,
            arithmetic.min(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
        assertEquals(BigDecimal.ONE,
            arithmetic.min(BigDecimal.valueOf(2), BigDecimal.ONE)
        );
    }

    @Test
    void max() {
        assertEquals(BigDecimal.ONE,
            arithmetic.max(BigDecimal.ONE, BigDecimal.ONE)
        );
        assertEquals(BigDecimal.valueOf(2),
            arithmetic.max(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
        assertEquals(BigDecimal.valueOf(2),
            arithmetic.max(BigDecimal.valueOf(2), BigDecimal.ONE)
        );
    }

    @Test
    void isZero() {
        assertTrue(arithmetic.isZero(BigDecimal.ZERO));
        assertTrue(arithmetic.isZero(BigDecimal.ZERO.negate()));
        assertFalse(arithmetic.isZero(BigDecimal.ONE));
    }

    @Test
    void isEqual() {
        assertTrue(
            arithmetic.isEqual(BigDecimal.ZERO, BigDecimal.ZERO)
        );
        assertTrue(
            arithmetic.isEqual(BigDecimal.ZERO.negate(), BigDecimal.ZERO)
        );
        assertTrue(
            arithmetic.isEqual(BigDecimal.ZERO.negate(), BigDecimal.ZERO.negate())
        );
        assertFalse(
            arithmetic.isEqual(BigDecimal.ONE, BigDecimal.ZERO)
        );
    }

    // endregion

    // region sum, difference, product, quotient and modulo

    @Test
    void sum12() {
        assertEquals(BigDecimal.valueOf(3),
            arithmetic.sum(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
    }

    @Test
    void sum123() {
        assertEquals(BigDecimal.valueOf(6),
            arithmetic.sum(BigDecimal.ONE, BigDecimal.valueOf(2), BigDecimal.valueOf(3))
        );
    }

    @Test
    void difference12() {
        assertEquals(BigDecimal.valueOf(-1),
            arithmetic.difference(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
    }

    @Test
    void product12() {
        assertEquals(BigDecimal.valueOf(2),
            arithmetic.product(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
    }

    @Test
    void product123() {
        assertEquals(BigDecimal.valueOf(6),
            arithmetic.product(
                BigDecimal.ONE,
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(3)
            )
        );
    }

    @Test
    void quotient12() {
        assertEquals(BigDecimal.valueOf(0.5),
            arithmetic.quotient(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
    }

    @Test
    void modulo12() {
        assertEquals(BigDecimal.ONE,
            arithmetic.modulo(BigDecimal.ONE, BigDecimal.valueOf(2))
        );
    }

    // endregion

    // region power and root

    @Test
    void power12() {
        assertEquals(BigDecimal.ONE, arithmetic.power(BigDecimal.ONE, 2));
    }

    @Test
    void root21() {
        assertEquals(
            BigDecimal.ONE.sqrt(new MathContext(10)),
            arithmetic.root2(BigDecimal.ONE)
        );
    }

    // endregion

    // region gcd and lcm

    @Test
    void gcd() {
        assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.gcd(BigDecimal.valueOf(3), BigDecimal.valueOf(4))
        ); // assert exception message?
    }

    @Test
    void lcm() {
        assertThrows(AbstractArithmetic.NotImplementedException.class,
            () -> arithmetic.gcd(BigDecimal.valueOf(3), BigDecimal.valueOf(4))
        ); // assert exception message?
    }

    // endregion

    // region override

    @Test
    void equalsOfArithmetic() {
        assertEquals(new BigDecimalArithmetic(), new BigDecimalArithmetic());
        assertNotEquals(new BigDecimalArithmetic(),
            new BigDecimalArithmetic(new MathContext(5))
        );
    }

    @Test
    void hashCodeOfArithmetic() {
        // hashCode of RoundingMode enum changing after every start
        assertEquals(
            new BigDecimalArithmetic().hashCode(),
            new BigDecimalArithmetic().hashCode()
        );
    }

    @Test
    void toStringOfArithmetic() {
        assertEquals("BigDecimalArithmetic",
            new BigDecimalArithmetic().toString()
        );
    }

    @Test
    void serializable() {
        assertSerializable(
            new BigDecimalArithmetic(),
            BigDecimalArithmetic.class
        );
    }

    // endregion
}
