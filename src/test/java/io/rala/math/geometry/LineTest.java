package io.rala.math.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

    @Test
    void testCalculateX() {
        Line line = new Line(2, 3);
        for (int i = -2; i <= 2; i++)
            Assertions.assertEquals((i - 3d) / 2, line.calculateX(i));
    }

    @Test
    void testCalculateY() {
        Line line = new Line(2, 3);
        for (int i = -2; i <= 2; i++)
            Assertions.assertEquals(i * 2 + 3, line.calculateY(i));
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
}
