package backendacademy.FractalFlame.Render;

import backendacademy.FractalFlame.Structures.FractalImage;
import backendacademy.FractalFlame.Structures.FractalStructure;

public class SingleThreaded extends backendacademy.FractalFlame.Render.Render {
    private SingleThreaded() {}

    public static FractalImage getFractalImage(FractalStructure fractal) {
        SingleThreaded singleThreaded = new SingleThreaded();
        return singleThreaded.getImage(fractal);
    }

    protected FractalImage getImage(FractalStructure fractal) {
        FractalImage image = FractalImage.create(fractal.width(), fractal.height());
        for (int p = 0; p < fractal.points(); ++p) {
            localRender(fractal, image);
        }
        FractalCorrection.correct(image);
        return image;
    }
}
