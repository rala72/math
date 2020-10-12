package io.rala.math.testUtils.assertion;

import io.rala.math.testUtils.SerializableUtils;

import java.io.IOException;
import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

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
        try {
            byte[] serialize = SerializableUtils.serialize(t);
            T deserialize = SerializableUtils.deserialize(serialize, tClass);
            assertEquals(t, deserialize);
        } catch (IOException | ClassNotFoundException e) {
            fail(e);
        }
    }
}
