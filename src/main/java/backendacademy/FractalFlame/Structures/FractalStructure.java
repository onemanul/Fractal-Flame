package backendacademy.FractalFlame.Structures;

import backendacademy.FractalFlame.Transformations.Transformation;

public record FractalStructure(
    int points,
    int iterations,
    int width,
    int height,
    int symmetryParts,
    AffineCoefficients[] coefficients,
    Transformation[] variations
) {
    public static FractalStructure create(int points, int iterations, int eqCount, int width, int height,
                                          int symmetryIndex, Transformation[] variations) {
        AffineCoefficients[] coefficients = getAffineArray(eqCount);
        return new FractalStructure(points, iterations, width, height,
                                    (int) Math.pow(2, symmetryIndex), coefficients, variations);
    }

    public static AffineCoefficients[] getAffineArray(int eqCounts) {
        AffineCoefficients[] coefficients = new AffineCoefficients[eqCounts];
        for (int i = 0; i < eqCounts; ++i) {
            coefficients[i] = AffineCoefficients.create();
        }
        return coefficients;
    }
}
