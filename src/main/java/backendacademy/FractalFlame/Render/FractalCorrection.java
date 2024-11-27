package backendacademy.FractalFlame.Render;

import backendacademy.FractalFlame.Structures.FractalImage;

public class FractalCorrection {
    private FractalCorrection() {}

    private final static int MAX_RGB_VALUE = 255;
    private final static double GAMMA = 2.2;

    public static void correct(FractalImage image) {
        double max = 0.0;
        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                if (image.pixels()[row][col].getHits() != 0) {
                    max = Math.max(max, image.pixels()[row][col].getHits());
                }
            }
        }
        double maxLog = Math.log10(max);
        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                double normal = Math.log10(image.pixels()[row][col].getHits()) / maxLog;
                double gammaFactor = Math.pow(normal, 1.0 / GAMMA);
                image.pixels()[row][col].red =
                    Math.min((int) (image.pixels()[row][col].red * gammaFactor), MAX_RGB_VALUE);
                image.pixels()[row][col].green =
                    Math.min((int) (image.pixels()[row][col].green * gammaFactor), MAX_RGB_VALUE);
                image.pixels()[row][col].blue =
                    Math.min((int) (image.pixels()[row][col].blue * gammaFactor), MAX_RGB_VALUE);
            }
        }
    }
}
