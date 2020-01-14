package io.rala.math.testUtils;

import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.io.Serializable;

/**
 * {@link Serializable} test utils based on {@link SerializableUtils}
 */
public class SerializableTestUtils {
    private SerializableTestUtils() {
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
    public static <T extends Serializable> void verify(T t, Class<T> tClass) {
        try {
            byte[] serialize = SerializableUtils.serialize(t);
            T deserialize = SerializableUtils.deserialize(serialize, tClass);
            Assertions.assertEquals(t, deserialize);
        } catch (IOException | ClassNotFoundException e) {
            Assertions.fail(e);
        }
    }
}
