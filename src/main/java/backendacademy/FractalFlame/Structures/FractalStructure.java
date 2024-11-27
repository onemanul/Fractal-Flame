package backendacademy.FractalFlame.Structures;

import backendacademy.FractalFlame.Transformations.Transformation;
import backendacademy.FractalFlame.Transformations.VariationsGetter;

public record FractalStructure(
    int points,
    int iterations,
    int width,
    int height,
    int symmetryAxes,
    AffineCoefficients[] coefficients,
    Transformation[] variations
) {
    public static FractalStructure create(int points, int iterations, int eqCount, int width, int height,
                                          int symmetryAxes) {
        AffineCoefficients[] coefficients = getAffineArray(eqCount);
        Transformation[] variations = VariationsGetter.get();
        return new FractalStructure(points, iterations, width, height, symmetryAxes, coefficients, variations);
    }

    public static AffineCoefficients[] getAffineArray(int eqCounts) {
        AffineCoefficients[] coefficients = new AffineCoefficients[eqCounts];
        for (int i = 0; i < eqCounts; ++i) {
            coefficients[i] = AffineCoefficients.create();
        }
        return coefficients;
    }
}
