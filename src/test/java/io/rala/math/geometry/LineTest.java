package io.rala.math.geometry;

import io.rala.math.testUtils.LineArgumentsStreamFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class LineTest {
    // region constructors, getter and setter

    @Test
    void testConstructors() {
        assertLine(new Line(2, 2), 2, 2);
        assertLine(new Line(2, 3), 2, 3);
    }

    @Test
    void testGetterAndSetter() {
        Line line = new Line(0, 0);
        line.setM(1);
        assertLine(line, 1, 0);
        line.setB(2);
        assertLine(line, 1, 2);
    }

    // endregion

    // region calculateX and calculateY

    @ParameterizedTest
    @MethodSource("getCalculateXArguments")
    void testCalculateX(double m, double b, double y, double expected) {
        Assertions.assertEquals(expected, new Line(m, b).calculateX(y));
    }

    @ParameterizedTest
    @MethodSource("getCalculateYArguments")
    void testCalculateY(double m, double b, double x, double expected) {
        Assertions.assertEquals(expected, new Line(m, b).calculateY(x));
    }

    // endregion

    // region copy

    @Test
    void testCopy() {
        Line line = new Line(2, 3);
        Assertions.assertEquals(line, line.copy());
    }

    // endregion

    // region override

    @Test
    void testEquals() {
        Line line = new Line(2, 3);
        Assertions.assertEquals(
            line,
            new Line(2, 3)
        );
        Assertions.assertNotEquals(
            line,
            new Line(3, 2)
        );
    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(
            525249,
            new Line(2, 3).hashCode()
        );
    }

    @Test
    void testToString() {
        Line line = new Line(2, 3);
        Assertions.assertEquals("y=2.0*x+3.0", line.toString());
    }

    // endregion


    // region assert

    private static void assertLine(Line line, double m, double b) {
        Assertions.assertEquals(m, line.getM());
        Assertions.assertEquals(b, line.getB());
    }

    // endregion

    // region argument streams

    private static Stream<Arguments> getCalculateXArguments() {
        return LineArgumentsStreamFactory.calculateX();
    }

    private static Stream<Arguments> getCalculateYArguments() {
        return LineArgumentsStreamFactory.calculateY();
    }

    // endregion
}
