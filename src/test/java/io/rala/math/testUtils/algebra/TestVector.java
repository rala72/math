package io.rala.math.testUtils.algebra;

import io.rala.math.algebra.vector.Vector;
import io.rala.math.arithmetic.AbstractArithmetic;
import io.rala.math.testUtils.arithmetic.TestAbstractArithmetic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("unused")
public class TestVector extends Vector<Number> {

    // region constructors

    public TestVector(int size) {
        super(new TestAbstractArithmetic(), size);
    }

    public TestVector(int size, @NotNull Number defaultValue) {
        super(new TestAbstractArithmetic(), size, defaultValue);
    }

    public TestVector(int size, @Nullable Type type) {
        super(new TestAbstractArithmetic(), size, type);
    }

    public TestVector(int size, @Nullable Type type, @NotNull Number defaultValue) {
        super(new TestAbstractArithmetic(), size, type, defaultValue);
    }

    public TestVector(@NotNull Vector<Number> vector) {
        super(vector);
    }

    // endregion

    // region static: of

    @NotNull
    public static TestVector ofValues(Number... values) {
        return new TestVector(
            Vector.ofValues(new TestAbstractArithmetic(), values)
        );
    }

    @NotNull
    public static TestVector ofList(List<Number> values) {
        return new TestVector(
            Vector.ofList(new TestAbstractArithmetic(), values)
        );
    }

    // endregion

    @NotNull
    public static <V extends Vector<N>, N extends Number> V fillVectorWithTestValues(V vector) {
        final AbstractArithmetic<N> arithmetic = vector.getArithmetic();
        for (int i = 0; i < vector.getSize(); i++) {
            vector.setValue(i, arithmetic.product(
                arithmetic.power(arithmetic.fromInt(i + 1), 2),
                arithmetic.power(arithmetic.fromInt(-1), i)));
        }
        return vector;
    }
}
