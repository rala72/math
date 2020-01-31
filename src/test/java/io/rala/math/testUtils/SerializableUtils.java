package io.rala.math.testUtils;

import java.io.*;

/**
 * serialization util methods collection
 *
 * @see Serializable
 * @see <a href="https://stackoverflow.com/a/3840817/2715720">https://stackoverflow.com/a/3840817/2715720</a>
 */
public class SerializableUtils {
    private SerializableUtils() {
    }

    /**
     * converts object to byte array
     *
     * @param t   object to convert
     * @param <T> class of object
     * @return byte array
     * @throws IOException thrown if {@link ObjectOutputStream} throws one
     * @see ByteArrayOutputStream
     * @see ObjectOutputStream#writeObject(Object)
     */
    public static <T extends Serializable> byte[] serialize(T t)
        throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream =
            new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(t);
        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * converts object to byte array
     *
     * @param bytes  bytes to convert
     * @param tClass class of object bytes
     * @param <T>    class of object bytes
     * @return object
     * @throws IOException            thrown if {@link ObjectInputStream} throws one
     * @throws ClassNotFoundException thrown if {@link ObjectInputStream#readObject()} throws one
     * @see ByteArrayInputStream
     * @see ObjectInputStream#readObject()
     */
    public static <T extends Serializable> T deserialize(byte[] bytes, Class<T> tClass)
        throws IOException, ClassNotFoundException {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream =
            new ObjectInputStream(byteArrayInputStream);
        Object object = objectInputStream.readObject();
        return tClass.cast(object);
    }
}
