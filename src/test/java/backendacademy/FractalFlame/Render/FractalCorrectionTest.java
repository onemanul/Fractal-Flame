package backendacademy.FractalFlame.Render;

import backendacademy.FractalFlame.Structures.FractalImage;
import backendacademy.FractalFlame.Structures.Pixel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FractalCorrectionTest {
    @Test
    public void testCorrect() {
        int width = 2;
        int height = 2;
        FractalImage image = FractalImage.create(width, height);

        image.pixels()[0][0] = new Pixel(0,  0, 0, 0);
        image.pixels()[0][1] = new Pixel(50, 75, 100, 1);
        image.pixels()[1][0] = new Pixel(100, 150, 200, 2);
        image.pixels()[1][1] = new Pixel(150, 225, 255, 3);

        FractalCorrection.correct(image);
        double maxLog = Math.log10(3);
        int[] blueResults = {0, 0,
            Math.min( (int) (200 * Math.pow(Math.log10(2) / maxLog, 1.0 / FractalCorrection.GAMMA)), FractalCorrection.MAX_RGB_VALUE),
            Math.min( (int) (255 * Math.pow(Math.log10(3) / maxLog, 1.0 / FractalCorrection.GAMMA)), FractalCorrection.MAX_RGB_VALUE)};

        assertEquals(blueResults[0], image.pixels()[0][0].blue());
        assertEquals(blueResults[1], image.pixels()[0][1].blue());
        assertEquals(blueResults[2], image.pixels()[1][0].blue());
        assertEquals(blueResults[3], image.pixels()[1][1].blue());
    }

    @Test
    public void testChangeColor_Normal() {
        Pixel pixel = new Pixel(100, 150, 200, 5);
        Pixel newPixel = FractalCorrection.changeColor(pixel, 1.5);
        assertEquals(Math.min(150, FractalCorrection.MAX_RGB_VALUE), newPixel.red());
        assertEquals(Math.min(225, FractalCorrection.MAX_RGB_VALUE), newPixel.green());
        assertEquals(Math.min(400, FractalCorrection.MAX_RGB_VALUE), newPixel.blue());
        assertEquals(5, newPixel.hits());
    }

    @Test
    public void testChangeColor_NoChange() {
        Pixel pixel = new Pixel(0, 0, 0, 0);
        Pixel newPixel = FractalCorrection.changeColor(pixel, 1.0);
        assertEquals(0, newPixel.red());
        assertEquals(0, newPixel.green());
        assertEquals(0, newPixel.blue());
        assertEquals(0, newPixel.hits());
    }
}
