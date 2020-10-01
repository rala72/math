package io.rala.math.testUtils.algebra;

import io.rala.math.algebra.vector.Vector;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;

public class TestVector extends Vector<Number> {

    // region constructors

    public TestVector(int size) {
        super(new TestAbstractArithmetic(), size);
    }

    public TestVector(int size, Number defaultValue) {
        super(new TestAbstractArithmetic(), size, defaultValue);
    }

    public TestVector(int size, Type type) {
        super(new TestAbstractArithmetic(), size, type);
    }

    public TestVector(int size, Type type, Number defaultValue) {
        super(new TestAbstractArithmetic(), size, type, defaultValue);
    }

    public TestVector(TestVector vector) {
        super(vector);
    }

    // endregion

    public TestVector fillWithTestValues() {
        for (int i = 0; i < getSize(); i++) {
            setValue(i, getArithmetic().product(
                getArithmetic().power(i + 1, 2),
                getArithmetic().power(getArithmetic().fromInt(-1), i)));
        }
        return this;
    }
}
