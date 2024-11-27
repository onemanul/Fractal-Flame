package backendacademy.FractalFlame.Render;

import backendacademy.FractalFlame.Structures.FractalImage;
import backendacademy.FractalFlame.Structures.FractalStructure;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreaded extends Render {
    private MultiThreaded() {}

    public static FractalImage getFractalImage(FractalStructure fractal) {
        MultiThreaded multiThreaded = new MultiThreaded();
        return multiThreaded.getImage(fractal);
    }

    protected FractalImage getImage(FractalStructure fractal) {
        FractalImage image = FractalImage.create(fractal.width(), fractal.height());
        try (ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())) {
            for (int p = 0; p < fractal.points(); ++p) {
                Runnable task = () -> localRender(fractal, image);
                executor.execute(task);
            }
        }
        FractalCorrection.correct(image);
        return image;
    }
}
