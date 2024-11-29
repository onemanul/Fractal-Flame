package backendacademy.FractalFlame.Structures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AffineCoefficientsTest {
    @Test
    public void testCreate() {
        AffineCoefficients coeffs = AffineCoefficients.create();
        assertNotNull(coeffs);
        assertTrue(coeffs.a() >= -AffineCoefficients.RANGE_LINEAR && coeffs.a() <= AffineCoefficients.RANGE_LINEAR);
        assertTrue(coeffs.b() >= -AffineCoefficients.RANGE_LINEAR && coeffs.b() <= AffineCoefficients.RANGE_LINEAR);
        assertTrue(coeffs.c() >= -AffineCoefficients.RANGE_SHIFT && coeffs.c() <= AffineCoefficients.RANGE_SHIFT);
        assertTrue(coeffs.d() >= -AffineCoefficients.RANGE_LINEAR && coeffs.d() <= AffineCoefficients.RANGE_LINEAR);
        assertTrue(coeffs.e() >= -AffineCoefficients.RANGE_LINEAR && coeffs.e() <= AffineCoefficients.RANGE_LINEAR);
        assertTrue(coeffs.f() >= -AffineCoefficients.RANGE_SHIFT && coeffs.f() <= AffineCoefficients.RANGE_SHIFT);
        assertTrue(coeffs.red() >= 0 && coeffs.red() <= AffineCoefficients.MAX_RGB_VALUE);
        assertTrue(coeffs.green() >= 0 && coeffs.green() <= AffineCoefficients.MAX_RGB_VALUE);
        assertTrue(coeffs.blue() >= 0 && coeffs.blue() <= AffineCoefficients.MAX_RGB_VALUE);

        assertTrue(
            Math.pow(coeffs.a(), 2) + Math.pow(coeffs.d(), 2) < 1 &&
                Math.pow(coeffs.b(), 2) + Math.pow(coeffs.e(), 2) < 1 &&
                Math.pow(coeffs.a(), 2) + Math.pow(coeffs.d(), 2) + Math.pow(coeffs.b(), 2) + Math.pow(coeffs.e(), 2)
                    < 1 + Math.pow(coeffs.a() * coeffs.e() - coeffs.b() * coeffs.d(), 2)
        );
    }

    @Test
    public void testTransformPoint() {
        AffineCoefficients coeffs = AffineCoefficients.create();
        Point point = new Point(1, 2);
        Point transformed = coeffs.transformPoint(point);
        double expectedX = coeffs.a() * point.x() + coeffs.b() * point.y() + coeffs.c();
        double expectedY = coeffs.d() * point.x() + coeffs.e() * point.y() + coeffs.f();
        assertEquals(expectedX, transformed.x());
        assertEquals(expectedY, transformed.y());
    }

    @Test
    public void testHitPixel_NewPixel() {
        AffineCoefficients coeffs = AffineCoefficients.create();
        Pixel pixel = new Pixel(0, 0, 0, 0);
        Pixel newPixel = coeffs.hitPixel(pixel);

        assertEquals(coeffs.red(), newPixel.red());
        assertEquals(coeffs.green(), newPixel.green());
        assertEquals(coeffs.blue(), newPixel.blue());
        assertEquals(1, newPixel.hits());
    }

    @Test
    public void testHitPixel_ExistingPixel() {
        AffineCoefficients coeffs = AffineCoefficients.create();
        Pixel pixel = new Pixel(100, 150, 200, 1);
        Pixel newPixel = coeffs.hitPixel(pixel);

        int expectedRed = (pixel.red() + coeffs.red()) / 2;
        int expectedGreen = (pixel.green() + coeffs.green()) / 2;
        int expectedBlue = (pixel.blue() + coeffs.blue()) / 2;

        assertEquals(expectedRed, newPixel.red());
        assertEquals(expectedGreen, newPixel.green());
        assertEquals(expectedBlue, newPixel.blue());
        assertEquals(2, newPixel.hits());
    }
}
