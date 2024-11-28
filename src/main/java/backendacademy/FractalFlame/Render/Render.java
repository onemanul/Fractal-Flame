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
            if (j > 0) {
                double theta = 0;
                for (int s = 0; s < fractal.symmetryParts(); s++) {
                    if (point.inRange(X_MIN, X_MAX, Y_MIN, Y_MAX)) {
                        int x1 = image.width() - (int) ((X_MAX - point.x()) / (X_MAX - X_MIN) * image.width());
                        int y1 = image.height() - (int) ((Y_MAX - point.y()) / (Y_MAX - Y_MIN) * image.height());
                        if (image.contains(x1, y1)) {
                            image.pixels()[y1][x1] = fractal.coefficients()[i].hitPixel(image.pixels()[y1][x1]);
                        }
                    }
                    theta += 2 * Math.PI / fractal.symmetryParts();
                    point = rotate(point, theta);
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

    protected static Point rotate(Point point, double theta) {
        double xRotated = point.x() * Math.cos(theta) - point.y() * Math.sin(theta);
        double yRotated = point.x() * Math.sin(theta) + point.y() * Math.cos(theta);
        return new Point(xRotated, yRotated);
    }
}
