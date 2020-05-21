package io.rala.math.exception;

/**
 * thrown if method / operation is not supported
 * <p>
 * may be used in similar context like {@link UnsupportedOperationException}
 */
public class NotSupportedException extends RuntimeException {
    /**
     * default message: {@code method not supported}
     */
    public NotSupportedException() {
        super("method not supported");
    }

    /**
     * @param message message of exception
     */
    public NotSupportedException(String message) {
        super(message);
    }
}
