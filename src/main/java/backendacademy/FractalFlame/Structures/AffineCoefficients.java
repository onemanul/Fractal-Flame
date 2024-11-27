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
    private final static int MAX_RGB_VALUE = 255;
    private final static double RANGE = 1.5;

    public static AffineCoefficients create() {
        double a = randomValue(RANGE);
        double b = randomValue(RANGE);
        double c = randomValue(RANGE * 2);
        double d = randomValue(RANGE);
        double e = randomValue(RANGE);
        double f = randomValue(RANGE * 2);
        while (!coefficientsIsValid(a, b, d, e)) {
            a = randomValue(RANGE);
            b = randomValue(RANGE);
            d = randomValue(RANGE);
            e = randomValue(RANGE);
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
