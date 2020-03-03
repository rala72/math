package io.rala.math.testUtils.algebra;

import io.rala.math.algebra.vector.Vector;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;

public class TestVector extends Vector<Number> {

    // region constructors

    public TestVector(int size) {
        this(size, true);
    }

    public TestVector(int size, boolean empty) {
        super(new TestAbstractArithmetic(), size, 0d);
        if (!empty) {
            for (int i = 0; i < getSize(); i++) {
                setValue(i, getArithmetic().product(
                    getArithmetic().power(i + 1, 2),
                    getArithmetic().power(getArithmetic().fromInt(-1), i)));
            }
        }
    }

    public TestVector(int size, Type type) {
        this(size, type, true);
    }

    public TestVector(int size, Type type, boolean empty) {
        super(new TestAbstractArithmetic(), size, type, 0d);
        if (!empty) {
            for (int i = 0; i < getSize(); i++) {
                setValue(i, getArithmetic().product(
                    getArithmetic().power(i + 1, 2),
                    getArithmetic().power(getArithmetic().fromInt(-1), i)));
            }
        }
    }

    public TestVector(int size, double defaultValue) {
        super(new TestAbstractArithmetic(), size, defaultValue);
    }

    public TestVector(TestVector vector) {
        super(vector);
    }

    // endregion

}
