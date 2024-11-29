package backendacademy.FractalFlame.Structures;

import java.util.Random;

@SuppressWarnings("checkstyle:RecordComponentNumber")
public record AffineCoefficients(
    double a,
    double b,
    double c,
    double d,
    double e,
    double f,
    int red,
    int green,
    int blue
) {
    public final static int MAX_RGB_VALUE = 255;
    public final static double RANGE_LINEAR = 1.5;
    public final static double RANGE_SHIFT = 3.5;

    public static AffineCoefficients create() {
        double a = randomValue(RANGE_LINEAR);
        double b = randomValue(RANGE_LINEAR);
        double c = randomValue(RANGE_SHIFT);
        double d = randomValue(RANGE_LINEAR);
        double e = randomValue(RANGE_LINEAR);
        double f = randomValue(RANGE_SHIFT);
        while (!coefficientsIsValid(a, b, d, e)) {
            a = randomValue(RANGE_LINEAR);
            b = randomValue(RANGE_LINEAR);
            d = randomValue(RANGE_LINEAR);
            e = randomValue(RANGE_LINEAR);
        }
        Random random = new Random();
        return new AffineCoefficients(a, b, c, d, e, f,
            random.nextInt(0, MAX_RGB_VALUE + 1),
            random.nextInt(0, MAX_RGB_VALUE + 1),
            random.nextInt(0, MAX_RGB_VALUE + 1)
        );
    }

    public Point transformPoint(Point point) {
        double x = this.a * point.x() + this.b * point.y() + this.c;
        double y = this.d * point.x() + this.e * point.y() + this.f;
        return new Point(x, y);
    }

    public Pixel hitPixel(Pixel pixel) {
        if (pixel.hits() == 0) {
            return new Pixel(this.red, this.green, this.blue, 1);
        } else {
            int newRed = (pixel.red() + this.red) / 2;
            int newGreen = (pixel.green() + this.green) / 2;
            int newBlue = (pixel.blue() + this.blue) / 2;
            return new Pixel(newRed, newGreen, newBlue, pixel.hits() + 1);
        }
    }

    private static boolean coefficientsIsValid(double a, double b, double d, double e) {
        return (a * a + d * d < 1)
            && (b * b + e * e < 1)
            && (a * a + b * b + d * d + e * e < 1 + Math.pow(a * e - b * d, 2));
    }

    private static double randomValue(double range) {
        Random random = new Random();
        return (random.nextDouble() * 2 * range) - range;
    }
}
