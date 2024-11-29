package backendacademy.FractalFlame.Render;

import backendacademy.FractalFlame.Structures.FractalImage;
import backendacademy.FractalFlame.Structures.FractalStructure;
import backendacademy.FractalFlame.Structures.Point;
import backendacademy.FractalFlame.Transformations.VariationsGetter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RenderTest {
    private static class TestRender extends Render {
        @Override
        protected FractalImage getImage(FractalStructure fractal) {
            return FractalImage.create(fractal.width(), fractal.height());
        }
    }

    private int modifiedPixels(FractalStructure fractal) {
        Render render = new TestRender();
        FractalImage image = render.getImage(fractal);

        assertNotNull(image);
        assertEquals(fractal.width(), image.width());
        assertEquals(fractal.height(), image.height());
        assertNotNull(image.pixels());

        for (int p = 0; p < fractal.points(); ++p) {
            render.localRender(fractal, image);
        }
        int modifiedPixelsCount = 0;
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                if (image.pixels()[y][x].hits() > 0) {
                    modifiedPixelsCount++;
                }
            }
        }
        return modifiedPixelsCount;
    }

    @Test
    public void testLocalRender_DifferentParameters() {
        int modifiedPixels = modifiedPixels(FractalStructure.create
            (10, 10, 10, 10, 10, 1, VariationsGetter.getRandomVariations()));
        assertTrue(modifiedPixels > 0);

        modifiedPixels = modifiedPixels(FractalStructure.create
            (100, 100, 100, 100, 100, 0, VariationsGetter.getRandomVariations()));
        assertTrue(modifiedPixels > 0);

        modifiedPixels = modifiedPixels(FractalStructure.create
            (123, 4567, 89, 123, 456, 1, VariationsGetter.getRandomVariations()));
        assertTrue(modifiedPixels > 0);

        modifiedPixels = modifiedPixels(FractalStructure.create
            (10, 123456, 789, 2000, 3000, 4, VariationsGetter.getRandomVariations()));
        assertTrue(modifiedPixels > 0);

        modifiedPixels = modifiedPixels(FractalStructure.create
            (1000, 10, 10, 1987, 1876, 2, VariationsGetter.getRandomVariations()));
        assertTrue(modifiedPixels > 0);
    }

    @Test
    public void testLocalRender_WithZeroInParameters() {
        int modifiedPixelsNoPoints = modifiedPixels(FractalStructure.create
            (0, 10, 3, 10, 10, 1, VariationsGetter.getRandomVariations()));
        assertEquals(0, modifiedPixelsNoPoints);

        int modifiedPixelsNoIterations = modifiedPixels(FractalStructure.create
            (10, 0, 10, 10, 10, 1, VariationsGetter.getRandomVariations()));
        assertEquals(0, modifiedPixelsNoIterations);
    }

    @Test
    public void testRandomPoint() {
        Point point = Render.randomPoint();
        assertTrue(point.x() >= Render.X_MIN && point.x() <= Render.X_MAX);
        assertTrue(point.y() >= Render.Y_MIN && point.y() <= Render.Y_MAX);
    }

    @Test
    public void testRotate() {
        Point originalPoint = new Point(1, 0);
        double theta = Math.PI / 2;     // 90
        Point rotatedPoint = Render.rotate(originalPoint, theta);
        assertEquals(0, rotatedPoint.x(), 1e-10);
        assertEquals(1, rotatedPoint.y(), 1e-10);

        rotatedPoint = Render.rotate(rotatedPoint, theta);
        assertEquals(-1, rotatedPoint.x(), 1e-10);
        assertEquals(0, rotatedPoint.y(), 1e-10);

        rotatedPoint = Render.rotate(rotatedPoint, theta);
        assertEquals(0, rotatedPoint.x(), 1e-10);
        assertEquals(-1, rotatedPoint.y(), 1e-10);

        rotatedPoint = Render.rotate(rotatedPoint, theta);
        assertEquals(originalPoint.x(), rotatedPoint.x(), 1e-10);
        assertEquals(originalPoint.y(), rotatedPoint.y(), 1e-10);
    }
}
