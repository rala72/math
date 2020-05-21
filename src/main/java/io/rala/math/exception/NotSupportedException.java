package io.rala.math.exception;

/**
 * thrown if operation is not supported
 * <p>
 * may be used in similar context like {@link UnsupportedOperationException}
 */
public class NotSupportedException extends RuntimeException {
    /**
     * exception default message: {@code method not supported}
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
