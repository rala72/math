package io.rala.math.testUtils.assertion;

import io.rala.math.testUtils.SerializableUtils;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * {@link Serializable} assertions based on {@link SerializableUtils}
 */
public class SerializableAssertions {
    private SerializableAssertions() {
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
