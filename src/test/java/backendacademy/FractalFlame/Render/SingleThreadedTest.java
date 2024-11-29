package backendacademy.FractalFlame.Render;

import backendacademy.FractalFlame.Structures.FractalImage;
import backendacademy.FractalFlame.Structures.FractalStructure;
import backendacademy.FractalFlame.Transformations.Transformation;
import backendacademy.FractalFlame.Transformations.VariationsGetter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SingleThreadedTest {
    @Test
    public void testGetFractalImage() {
        int points = 10;
        int iterations = 10;
        int eqCount = 3;
        int width = 10;
        int height = 10;
        int symmetryIndex = 1;
        Transformation[] variations = VariationsGetter.getRandomVariations();
        FractalStructure
            fractal = FractalStructure.create(points, iterations, eqCount, width, height, symmetryIndex, variations);
        FractalImage image = SingleThreaded.getFractalImage(fractal);

        assertNotNull(image);
        assertEquals(width, image.width());
        assertEquals(height, image.height());
        assertNotNull(image.pixels());

        int modifiedPixelsCount = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (image.pixels()[y][x].hits() > 0) {
                    modifiedPixelsCount++;
                }
            }
        }
        assertTrue(modifiedPixelsCount > 0, "Ни один пиксель не изменился после рендеринга");
    }

    @Test
    public void testGetFractalImage_NoPoints() {
        int points = 0;
        int iterations = 10;
        int eqCount = 3;
        int width = 10;
        int height = 10;
        int symmetryIndex = 1;
        Transformation[] variations = VariationsGetter.getRandomVariations();
        FractalStructure
            fractal = FractalStructure.create(points, iterations, eqCount, width, height, symmetryIndex, variations);
        FractalImage image = SingleThreaded.getFractalImage(fractal);

        assertNotNull(image);
        assertEquals(width, image.width());
        assertEquals(height, image.height());
        assertNotNull(image.pixels());

        int modifiedPixelsCount = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (image.pixels()[y][x].hits() > 0) {
                    modifiedPixelsCount++;
                }
            }
        }
        assertEquals(0, modifiedPixelsCount);
    }
}
