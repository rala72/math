package io.rala.math.testUtils;

import org.junit.jupiter.params.provider.Arguments;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class MathXArgumentsStreamFactory {
    private MathXArgumentsStreamFactory() {
    }

    public static Stream<Arguments> gcd() {
        return Stream.of(
            Arguments.of(0, IntStream.of().toArray()),
            Arguments.of(10, IntStream.of(10).toArray()),
            Arguments.of(6, IntStream.of(12, 18).toArray()),
            Arguments.of(8, IntStream.of(24, 32).toArray()),
            Arguments.of(6, IntStream.of(54, 24).toArray()),
            Arguments.of(5, IntStream.of(105, 25).toArray()),
            Arguments.of(3, IntStream.of(123, 456, 789).toArray()),
            Arguments.of(13, IntStream.of(13, 169, 2197).toArray()),
            Arguments.of(2, IntStream.of(984, 1002, 382).toArray())
        );
    }

    public static Stream<Arguments> factors() {
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

    public static Stream<Arguments> factorial() {
        return Stream.of(
            Arguments.of(-1, 0),
            Arguments.of(0, 1),
            Arguments.of(1, 1),
            Arguments.of(2, 2),
            Arguments.of(3, 6),
            Arguments.of(4, 24),
            Arguments.of(5, 120),
            Arguments.of(6, 720),
            Arguments.of(7, 5040),
            Arguments.of(8, 40_320),
            Arguments.of(9, 362_880),
            Arguments.of(10, 3_628_800),
            Arguments.of(11, 39_916_800),
            Arguments.of(12, 479_001_600),
            Arguments.of(13, 6_227_020_800L),
            Arguments.of(14, 87_178_291_200L)
        );
    }

    public static Stream<Arguments> lcm() {
        return Stream.of(
            Arguments.of(0, IntStream.of().toArray()),
            Arguments.of(10, IntStream.of(10).toArray()),
            Arguments.of(24, IntStream.of(3, 8).toArray()),
            Arguments.of(12, IntStream.of(4, 6).toArray()),
            Arguments.of(25, IntStream.of(5, 25).toArray()),
            Arguments.of(36, IntStream.of(12, 18).toArray()),
            Arguments.of(3630, IntStream.of(15, 22, 121).toArray()),
            Arguments.of(7801080, IntStream.of(444, 753, 280).toArray()),
            Arguments.of(672, IntStream.of(21, 32, 16, 4, 7).toArray())
        );
    }
}
