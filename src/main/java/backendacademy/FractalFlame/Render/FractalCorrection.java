package backendacademy.FractalFlame.Render;

import backendacademy.FractalFlame.Structures.FractalImage;
import backendacademy.FractalFlame.Structures.Pixel;

public class FractalCorrection {
    private FractalCorrection() {}

    public final static int MAX_RGB_VALUE = 255;
    public final static double GAMMA = 2.2;

    public static void correct(FractalImage image) {
        double max = 0.0;
        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                if (image.pixels()[row][col].hits() != 0) {
                    max = Math.max(max, image.pixels()[row][col].hits());
                }
            }
        }
        double maxLog = Math.log10(max);
        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                double normal = Math.log10(image.pixels()[row][col].hits()) / maxLog;
                double gammaFactor = Math.pow(normal, 1.0 / GAMMA);
                image.pixels()[row][col] = changeColor(image.pixels()[row][col], gammaFactor);
            }
        }
    }

    public static Pixel changeColor(Pixel pixel, double gammaFactor) {
        int newRed = Math.min((int) (pixel.red() * gammaFactor), MAX_RGB_VALUE);
        int newGreen = Math.min((int) (pixel.green() * gammaFactor), MAX_RGB_VALUE);
        int newBlue = Math.min((int) (pixel.blue() * gammaFactor), MAX_RGB_VALUE);
        return new Pixel(newRed, newGreen, newBlue, pixel.hits());
    }
}
