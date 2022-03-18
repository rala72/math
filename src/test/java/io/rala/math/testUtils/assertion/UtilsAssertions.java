package io.rala.math.testUtils.assertion;

import io.rala.math.testUtils.assertion.utils.SerializableUtils;
import io.rala.math.utils.Copyable;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * assertions for {@link io.rala.math.utils} package and Java interfaces
 */
public class UtilsAssertions {
    private UtilsAssertions() {
    }

    /**
     * verifies if a object is copied correctly
     *
     * @param t   object
     * @param <T> type of object
     */
    public static <T extends Copyable<T>> void assertCopyable(T t) {
        assertThat(t.copy()).isNotSameAs(t).isEqualTo(t);
    }

    /**
     * verifies if a object is {@link Serializable}
     *
     * @param t      object
     * @param tClass class of object
     * @param <T>    type of object
     * @see SerializableUtils#serialize(Serializable)
     * @see SerializableUtils#deserialize(byte[], Class)
     */
    public static <T extends Serializable> void assertSerializable(T t, Class<T> tClass) {
        assertThatCode(() -> {
            byte[] serialize = SerializableUtils.serialize(t);
            T deserialize = SerializableUtils.deserialize(serialize, tClass);
            assertThat(deserialize).isNotSameAs(t).isEqualTo(t);
        }).doesNotThrowAnyException();
    }
}
