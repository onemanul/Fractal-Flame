package backendacademy.FractalFlame.Structures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FractalImageTest {
    @Test
    public void testCreateFractalImage() {
        FractalImage image = FractalImage.create(5, 3);
        assertNotNull(image);
        assertEquals(5, image.width());
        assertEquals(3, image.height());
        assertNotNull(image.pixels());
        assertEquals(3, image.pixels().length);
        assertEquals(5, image.pixels()[0].length);

        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                assertEquals(new Pixel(0, 0, 0, 0), image.pixels()[y][x]);
            }
        }
    }

    @Test
    public void testContains_WithinBounds() {
        FractalImage image = FractalImage.create(5, 5);
        assertTrue(image.contains(2, 2));
    }

    @Test
    public void testContains_OnLowerBound() {
        FractalImage image = FractalImage.create(5, 5);
        assertTrue(image.contains(0, 0));
    }

    @Test
    public void testContains_OnUpperBound() {
        FractalImage image = FractalImage.create(5, 5);
        assertTrue(image.contains(4, 4));
    }

    @Test
    public void testContains_OutsideXBounds() {
        FractalImage image = FractalImage.create(5, 5);
        assertFalse(image.contains(-1, 2));
        assertFalse(image.contains(5, 2));
    }

    @Test
    public void testContains_OutsideYBounds() {
        FractalImage image = FractalImage.create(5, 5);
        assertFalse(image.contains(2, -1));
        assertFalse(image.contains(2, 5));
    }

    @Test
    public void testContains_OutsideBounds() {
        FractalImage image = FractalImage.create(5, 5);
        assertFalse(image.contains(5, -1));
        assertFalse(image.contains(-1, 5));
    }
}
