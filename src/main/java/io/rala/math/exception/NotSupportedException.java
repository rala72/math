package io.rala.math.exception;

/**
 * thrown if operation is not supported
 * <p>
 * may be used in similar context like {@link UnsupportedOperationException}
 */
public class NotSupportedException extends RuntimeException {
    /**
     * @param message message of exception
     */
    public NotSupportedException(String message) {
        super(message);
    }
}
