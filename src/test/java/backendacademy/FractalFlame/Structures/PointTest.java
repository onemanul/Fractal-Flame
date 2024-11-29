package backendacademy.FractalFlame.Structures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    @Test
    public void testInRange_WithinBounds() {
        Point point = new Point(5, 5);
        assertTrue(point.inRange(0, 10, 0, 10));
    }

    @Test
    public void testInRange_OnLowerBound() {
        Point point = new Point(0, 0);
        assertTrue(point.inRange(0, 10, 0, 10));
    }

    @Test
    public void testInRange_OnUpperBound() {
        Point point = new Point(10, 10);
        assertTrue(point.inRange(0, 10, 0, 10));
    }

    @Test
    public void testInRange_OutsideBounds() {
        Point point = new Point(-1, -1);
        assertFalse(point.inRange(0, 10, 0, 10));
    }

    @Test
    public void testInRange_OutsideXBounds() {
        Point point = new Point(11, 5);
        assertFalse(point.inRange(0, 10, 0, 10));
    }

    @Test
    public void testInRange_OutsideYBounds() {
        Point point = new Point(5, 11);
        assertFalse(point.inRange(0, 10, 0, 10));
    }
}
