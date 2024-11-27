package backendacademy.FractalFlame;

import backendacademy.FractalFlame.Render.MultiThreaded;
import backendacademy.FractalFlame.Render.SingleThreaded;
import backendacademy.FractalFlame.Structures.FractalImage;
import backendacademy.FractalFlame.Structures.FractalStructure;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    private static final int MIN_IMAGE_SIZE = 100;
    private static final int MAX_WIDTH = 3840;
    private static final int MAX_HEIGHT = 2160;
    private static final int MIN_ITERATIONS = 10;
    private static final int MAX_ITERATIONS = 100000;
    private static final int MAX_SYMMETRY_AXES = 4;
    private static final int POINTS = 300;
    private static final int EQ_COUNT = 20;     // количество генерируемых афинных преобразований

    private final static PrintStream OUTPUT = System.out;
    static Scanner in = new Scanner("1920\n1080\n30000\n4\n"); //(System.in);

    // Симметрия

    public static void main(String[] args) {
        OUTPUT.println("Введите параметры изображения:\nШирина: " + segment(MIN_IMAGE_SIZE, MAX_WIDTH));
        int width = correctIntInput(MIN_IMAGE_SIZE, MAX_WIDTH);
        OUTPUT.println("Высота: " + segment(MIN_IMAGE_SIZE, MAX_HEIGHT));
        int height = correctIntInput(MIN_IMAGE_SIZE, MAX_HEIGHT);
        OUTPUT.println("Введите количество итераций: " + segment(MIN_ITERATIONS, MAX_ITERATIONS));
        int iterations = correctIntInput(MIN_ITERATIONS, MAX_ITERATIONS);
        OUTPUT.println("Введите количество осей симметрии: " + segment(0, MAX_SYMMETRY_AXES));
        int symmetryAxes = correctIntInput(0, MAX_SYMMETRY_AXES);

        FractalStructure fractal = FractalStructure.create(POINTS, iterations, EQ_COUNT, width, height, symmetryAxes);

        long startSingle = System.nanoTime();
        FractalImage imageSingle = SingleThreaded.getFractalImage(fractal);
        long singleTime = System.nanoTime() - startSingle;

        long startMulti = System.nanoTime();
        FractalImage imageMulti = MultiThreaded.getFractalImage(fractal);
        long multiTime = System.nanoTime() - startMulti;
        ImageUtils.save(imageMulti, ImageFormat.PNG, Paths.get("IMAGES"));

        OUTPUT.println("Время при однопоточной генерации: " + singleTime + "\nВремя при многопоточной генерации ("
                        + Runtime.getRuntime().availableProcessors() + " потоков): " + multiTime);
    }

    public static int correctIntInput(int min, int max) {
        Optional<Integer> optSize = checkForInt(in.nextLine());
        while (optSize.isEmpty() || optSize.get() < min || optSize.get() > max) {
            OUTPUT.println("Введите число " + segment(min, max));
            optSize = checkForInt(in.nextLine());
        }
        return optSize.get();
    }

    public static Optional<Integer> checkForInt(String str) {
        try {
            return Optional.of(Integer.parseInt(str.trim()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static String segment(int min, int max) {
        return  "от " + min + " до " + max;
    }
}
