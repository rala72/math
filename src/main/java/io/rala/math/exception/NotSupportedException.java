package io.rala.math.exception;

/**
 * thrown if method / operation is not supported
 * <p>
 * may be used in similar context like {@link UnsupportedOperationException}
 *
 * @since 1.0.0
 */
public class NotSupportedException extends RuntimeException {
    /**
     * default message: {@code method not supported}
     *
     * @since 1.0.0
     */
    public NotSupportedException() {
        super("method not supported");
    }

    /**
     * @param message message of exception
     * @since 1.0.0
     */
    public NotSupportedException(String message) {
        super(message);
    }
}
