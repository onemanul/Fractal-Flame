package backendacademy.FractalFlame.Render;

import backendacademy.FractalFlame.Structures.FractalImage;
import backendacademy.FractalFlame.Structures.FractalStructure;
import backendacademy.FractalFlame.Structures.Point;
import java.util.Random;

public abstract class Render {
    protected final static double X_MAX = 1.0;
    protected final static double X_MIN = -1.0;
    protected final static double Y_MAX = 1.0;
    protected final static double Y_MIN = -1.0;
    protected final static int SKIP_ITERATIONS = -20;

    protected abstract FractalImage getImage(FractalStructure fractal);

    protected void localRender(FractalStructure fractal, FractalImage image) {
        Random random = new Random();
        Point point  = randomPoint();
        for (int j = SKIP_ITERATIONS; j < fractal.iterations(); j++) {
            int i = random.nextInt(fractal.coefficients().length);
            int iVar = random.nextInt(fractal.variations().length);
            point = fractal.coefficients()[i].transformPoint(point);
            point = fractal.variations()[iVar].apply(point);

            if (j > 0 && point.inRange(X_MIN, X_MAX, Y_MIN, Y_MAX)) {
                int x1 = image.width() - (int) ((X_MAX - point.x()) / (X_MAX - X_MIN) * image.width());
                int y1 = image.height() - (int) ((Y_MAX - point.y()) / (Y_MAX - Y_MIN) * image.height());
                if (image.contains(x1, y1)) {
                    if (image.pixels()[y1][x1].getHits() == 0) {
                        image.pixels()[y1][x1].red = fractal.coefficients()[i].red();
                        image.pixels()[y1][x1].green = fractal.coefficients()[i].green();
                        image.pixels()[y1][x1].blue = fractal.coefficients()[i].blue();
                    } else {
                        image.pixels()[y1][x1].red =
                            (image.pixels()[y1][x1].red + fractal.coefficients()[i].red()) / 2;
                        image.pixels()[y1][x1].green =
                            (image.pixels()[y1][x1].green + fractal.coefficients()[i].green()) / 2;
                        image.pixels()[y1][x1].blue =
                            (image.pixels()[y1][x1].blue + fractal.coefficients()[i].blue()) / 2;
                    }
                    image.pixels()[y1][x1].increaseHit();
                }
            }
        }
    }

    protected static Point randomPoint() {
        Random random = new Random();
        double x = random.nextDouble(X_MIN, X_MAX);
        double y = random.nextDouble(Y_MIN, Y_MAX);
        return new Point(x, y);
    }
}
