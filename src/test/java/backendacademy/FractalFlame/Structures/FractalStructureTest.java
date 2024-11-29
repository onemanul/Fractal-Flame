package backendacademy.FractalFlame.Structures;

import backendacademy.FractalFlame.Transformations.Transformation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FractalStructureTest {
    @Test
    public void testCreate() {
        int points = 10;
        int iterations = 100;
        int eqCount = 5;
        int width = 500;
        int height = 500;
        int symmetryIndex = 3;
        Transformation[] variations = new Transformation[0];

        FractalStructure fractal = FractalStructure.create(points, iterations, eqCount, width, height, symmetryIndex, variations);

        assertNotNull(fractal);
        assertEquals(points, fractal.points());
        assertEquals(iterations, fractal.iterations());
        assertEquals(width, fractal.width());
        assertEquals(height, fractal.height());
        assertEquals((int) Math.pow(2, symmetryIndex), fractal.symmetryParts());
        assertEquals(eqCount, fractal.coefficients().length);
        assertEquals(variations.length, fractal.variations().length);
    }

    @Test
    public void testGetAffineArray() {
        int eqCount = 3;
        AffineCoefficients[] coefficients = FractalStructure.getAffineArray(eqCount);

        assertNotNull(coefficients);
        assertEquals(eqCount, coefficients.length);
        for (AffineCoefficients coeffs : coefficients) {
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
    }

}
