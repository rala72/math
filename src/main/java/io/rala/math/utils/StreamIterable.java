package io.rala.math.utils;

import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This interface extends {@link Iterable} by the methods
 * {@link #stream()} and {@link #parallelStream()}.
 *
 * @param <T> class used in iteration
 */
public interface StreamIterable<T> extends Iterable<T> {
    /**
     * @return a sequential {@code Stream}
     * @see StreamSupport#stream(Spliterator, boolean)
     */
    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * @return a possibly parallel {@code Stream}
     * @see StreamSupport#stream(Spliterator, boolean)
     */
    default Stream<T> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}
