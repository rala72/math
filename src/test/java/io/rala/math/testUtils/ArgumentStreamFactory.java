package io.rala.math.testUtils;

import org.junit.jupiter.params.provider.Arguments;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class ArgumentStreamFactory {
    private ArgumentStreamFactory() {
    }

    public static Stream<Arguments> getMathXFactorsArguments() {
        return Stream.of(
            Arguments.of(0, Collections.emptyList()),
            Arguments.of(1, Collections.emptyList()),
            Arguments.of(2, List.of(2)),
            Arguments.of(3, List.of(3)),
            Arguments.of(4, List.of(2, 2)),
            Arguments.of(5, List.of(5)),
            Arguments.of(6, List.of(2, 3)),
            Arguments.of(7, List.of(7)),
            Arguments.of(8, List.of(2, 2, 2)),
            Arguments.of(9, List.of(3, 3)),
            Arguments.of(10, List.of(2, 5)),
            Arguments.of(11, List.of(11)),
            Arguments.of(12, List.of(2, 2, 3)),
            Arguments.of(13, List.of(13)),
            Arguments.of(14, List.of(2, 7)),
            Arguments.of(15, List.of(3, 5)),
            Arguments.of(16, List.of(2, 2, 2, 2)),
            Arguments.of(100, List.of(2, 2, 5, 5)),
            Arguments.of(200, List.of(2, 2, 2, 5, 5)),
            Arguments.of(1000, List.of(2, 2, 2, 5, 5, 5)),
            Arguments.of(10000, List.of(2, 2, 2, 2, 5, 5, 5, 5))
        );
    }
}
